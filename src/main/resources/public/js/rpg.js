'use strict';
(function() {
    var view = document.getElementById('game');
    var viewWidth = 800;
    var viewHeight = 600;
    var scale = 2;

    var playerData = [
        '3333',
        '3443',
        '3443',
        '3333'
    ];
    var otherPlayerData = [
        '4444',
        '4334',
        '4334',
        '4444'
    ];
    var monsterData = [
        '.77.',
        '7667',
        '7667',
        '.77.'
    ];

    var playerTexture;
    var otherPlayerTexture;
    var monsterTexture;

    var username;
    var player;
    var otherPlayers = [];

    var monsters = [];

    var cursors;

    var game;
    var socket;

    var isFighting = false;
    var fightId = null;

    function initGame(msg) {
        game = new Phaser.Game(viewWidth, viewHeight, Phaser.CANVAS, view);

        game.states = {};

        game.states.boot = function() {
            this.preload = function() {
                // console.log('loading...');
            };
            this.create = function() {
                this.state.start('preload');
            }
        };

        game.states.preload = function() {
            this.preload = function() {
                game.stage.backgroundColor = '#2d2d2d';
                game.load.tilemap('level1', 'assets/tilemaps/csv/catastrophi_level2.csv', null, Phaser.Tilemap.CSV);
                game.load.image('tiles', 'assets/tilemaps/tiles/catastrophi_tiles_16.png');
            };
            this.create = function() {
                this.state.start('start');
            }
        };

        game.states.start = function() {
            this.preload = function() {
                console.log('loading...');

                this.moveSpeed = 200 * scale;
                playerTexture = game.create.texture('player', playerData, 4, 4, 0, false);
                otherPlayerTexture = game.create.texture('otherPlayer', otherPlayerData, 4, 4, 0, false);
                monsterTexture = game.create.texture('monster', monsterData, 4, 4, 0, false);
                // chestTexture = game.create.texture('chest', chestData, 4, 4, 0, false);
                // blacksmithShopTexture = game.create.texture('blacksmithShop', blacksmithShopData, 4, 4, 0, false);
            };
            this.create = function() {
                console.log('create...');

                game.stage.backgroundColor = '#2d2d2d';

                this.map = this.add.tilemap('level1', 16, 16);
                this.map.addTilesetImage('tiles');
                this.map.setCollisionBetween(54, 55);
                this.map.setCollisionBetween(56, 83);
                this.map.setCollisionBetween(90, 167);

                this.layer = this.map.createLayer('layer');
                this.layer.setScale(scale, scale);
                this.layer.resizeWorld();

                game.physics.startSystem(Phaser.Physics.ARCADE);

                if (msg) {
                    player = game.add.sprite(msg['data']['player']['x'] + 8, msg['data']['player']['y'] + 8, playerTexture);
                    player['objectId'] = msg['data']['player']['objectId'];
                } else {
                    player = game.add.sprite(40 * scale, 8 * scale, playerTexture);
                    player['objectId'] = 'player';
                }
                player.anchor.set(0.5);
                game.physics.arcade.enable(player);
                player.body.collideWorldBounds = true;
                game.camera.follow(player);
                cursors = game.input.keyboard.createCursorKeys();

                var monstersInfo = msg['data']['map'];
                for (var i = 0; i < monstersInfo.length; i++) {
                    var monster = game.add.sprite(monstersInfo[i]['x'], monstersInfo[i]['y'], monsterTexture);
                    monster['objectId'] = monstersInfo[i]['objectId'];
                    monster.anchor.set(0.5);
                    game.physics.arcade.enable(monster);
                    monster.body.immovable = true;
                    monsters.push(monster);
                }
            };

            this.update = function() {

                if (isFighting) return;

                game.physics.arcade.collide(player, this.layer);
                for(var i = 0; i < monsters.length; i++) {
                    game.physics.arcade.collide(player, monsters[i], function(player, monster) {
                        // console.log('collide', player['objectId'], monster['objectId']);
                        socket.send(JSON.stringify({
                            code: 2001,
                            data: {
                                initiator: player['objectId'],
                                target: monster['objectId']
                            }
                        }));
                    });
                }

                for(var i = 0; i < otherPlayers.length; i++) {
                    game.physics.arcade.collide(player, otherPlayers[i], function(player, otherPlayer) {
                      console.log(otherPlayer['clientId']);
                        socket.send(JSON.stringify({
                            code: 2001,
                            data: {
                                initiator: player['objectId'],
                                target: otherPlayer['clientId']
                            }
                        }))
                    });
                }

                player.body.velocity.x = 0;
                player.body.velocity.y = 0;

                if (cursors.left.isDown) {
                    player.body.velocity.x = -this.moveSpeed;
                    player.scale.x = 1;

                    socket.send(JSON.stringify({
                        code: 1000,
                        data: {
                            x: player.body.position.x,
                            y: player.body.position.y
                        }
                    }));
                } else if (cursors.right.isDown) {
                    player.body.velocity.x = this.moveSpeed;
                    player.scale.x = -1;

                    socket.send(JSON.stringify({
                        code: 1000,
                        data: {
                            x: player.body.position.x,
                            y: player.body.position.y
                        }
                    }));
                }

                if (cursors.up.isDown) {
                    player.body.velocity.y = -this.moveSpeed;
                    player.scale.y = -1;

                    socket.send(JSON.stringify({
                        code: 1000,
                        data: {
                            x: player.body.position.x,
                            y: player.body.position.y
                        }
                    }));
                } else if (cursors.down.isDown) {
                    player.body.velocity.y = this.moveSpeed;
                    player.scale.y = 1;

                    socket.send(JSON.stringify({
                        code: 1000,
                        data: {
                            x: player.body.position.x,
                            y: player.body.position.y
                        }
                    }));
                }
            };
        };

        game.state.fight = function() {
            this.preload = function() {
                console.log('prepare fight!');
            };
            this.create = function() {
                console.log('fighting!')
            }
        };

        game.state.add('boot', game.states.boot);
        game.state.add('preload', game.states.preload);
        game.state.add('start', game.states.start);
        game.state.add('fight', game.states.fight);
        game.state.start('boot');
    }

    function initSocket() {
        socket = new WebSocket('ws://localhost:7000/ws');
        socket.onopen = function() {
            console.log('建立连接!');
        };
        socket.onmessage = function(ev) {
            console.log(JSON.parse(ev.data));
            var msg = JSON.parse(ev.data);

            switch (msg['code'] - msg['code'] % 1000) {
                case 0:
                    handlerAuthorization(msg);
                    break;
                case 1000:
                    refreshPosition(msg['data']);
                    break;
                case 2000:
                    handlerFight(msg);
                    break;
                case 3000:
                    // initPlayerState();
                    break;
                case 4000:
                    handlerStrengthen(msg);
                    break;
                default:
                    break;
            }
        };
    }

    function initListener() {
        $('#login_submit').on('click', function() {
            username = $('#username').val();
            if (!username) {
                alert('角色名不能为空!');
                return;
            }
            socket.send(JSON.stringify({
                code: 0,
                data: {
                    username: username
                }
            }));
        });
        $('#choose_submit').on('click', function() {
            if (!username) {
                alert('角色名不能为空!');
                return;
            }
            var career = $('#career').val();
            socket.send(JSON.stringify({
                code: 4,
                data: career
            }));
        });
        $('#strengthen-weapon').on('click', function() {
            socket.send(JSON.stringify({
                code: 4000,
                data: {
                    username: player['objectId']
                }
            }));
        });
    }

    function setPlayerGameState(state) {
        $('#player-game-state-name').html(state['objectId']);
        $('#player-game-state-career').html(state['career']);
        $('#player-game-state-level').html(state['level']);
        $('#player-game-state-hp').html(state['current_hp'] + '/' + state['hp']);
        $('#player-game-state-mp').html(state['current_mp'] + '/' + state['mp']);
        $('#player-game-state-ap').html(state['current_ap'] + '/' + state['ap']);

        if (player) {
            player.kill();
            if (state) {
                player = game.add.sprite(state['x'] + 8, state['y'] + 8, playerTexture);
                player['objectId'] = state['objectId'];
            } else {
                player = game.add.sprite(40 * scale, 8 * scale, playerTexture);
                player['objectId'] = 'player';
            }
            player.anchor.set(0.5);
            game.physics.arcade.enable(player);
            player.body.collideWorldBounds = true;
            game.camera.follow(player);
            cursors = game.input.keyboard.createCursorKeys();
        }

        setBag(state['bag']);
    }

    function setBag(bag) {
        $('#coin').html(bag['coin']);
        $('#prop').html(bag['myProp']['name'] + ' level ' + bag['myProp']['level']);
    }

    function handlerFight(msg) {
        switch (msg['code']) {
            case 2003:
                break;
            case 2002:
                setFightInfo(msg);
                break;
            case 2001:
                initFight(msg);
                break;
            case 2004:
                setHistory('不是你的回合!');
                break;
            case 2005:
                endFight(msg);
                break;
        }
    }

    function initFight(msg) {
        var data = msg['data'];
        isFighting = true;
        fightId = msg['data']['fightId'];
        $('#fight-wrapper').show();
        $('#fight-wrapper').focus();
        setSkills(data['ourSide']['skillList']);
        console.log(data);
        setEnemyState(data['otherSide']);
        setPlayerState(data['ourSide']);
        // cursors = null;

        $('#history').html('');
        setHistory('与[' + data['otherSide']['objectId'] + ']的战斗开始了!');
    }

    function endFight(msg) {
        var data = msg['data'];
        $('#fight-wrapper').hide();
        setPlayerGameState(data['ourSide']);
        isFighting = false;
    }

    function setFightInfo(msg) {
        var data = msg['data'];
        setEnemyState(data['otherSide']);
        setPlayerState(data['ourSide']);
        setHistory(data['result']);
    }

    function setHistory(text) {
        var history = $('#history').html();
        $('#history').html(text + '<br>' + history);
    }

    function setSkills(skills) {
        $('#skill1').html(skills[0]['name']);
        $('#skill2').html(skills[1]['name']);
        $('#skill3').html(skills[2]['name']);

        $('#skill1').unbind();
        $('#skill2').unbind();
        $('#skill3').unbind();

        $('#skill1').on('click', function() {
            if (!fightId) return;
            socket.send(JSON.stringify({
                code: 2002,
                data: {
                    fightId: fightId,
                    username: player['objectId'],
                    skillId: 0
                }
            }));
        });
        $('#skill2').on('click', function() {
            if (!fightId) return;
            socket.send(JSON.stringify({
                code: 2002,
                data: {
                    fightId: fightId,
                    username: player['objectId'],
                    skillId: 1
                }
            }));
        });
        $('#skill3').on('click', function() {
            if (!fightId) return;
            socket.send(JSON.stringify({
                code: 2002,
                data: {
                    fightId: fightId,
                    username: player['objectId'],
                    skillId: 2
                }
            }));
        });
    }

    function setEnemyState(state) {
        $('#enemy-name').html(state['objectId']);
        $('#enemy-career').html(state['career']);
        $('#enemy-hp').html(state['current_hp'] + '/' + state['hp']);
        $('#enemy-mp').html(state['current_mp'] + '/' + state['mp']);
        $('#enemy-ap').html(state['current_ap'] + '/' + state['ap']);
        $('#enemy-buff').html(makeBuff(state['buff']));
    }

    function setPlayerState(state) {
        $('#player-name').html(state['objectId']);
        $('#player-career').html(state['career']);
        $('#player-hp').html(state['current_hp'] + '/' + state['hp']);
        $('#player-mp').html(state['current_mp'] + '/' + state['mp']);
        $('#player-ap').html(state['current_ap'] + '/' + state['ap']);
        $('#player-buff').html(makeBuff(state['buff']));
    }

    function makeBuff(buff) {
        if (!buff) return '';
        if (buff['nextBuff']) {
            return buff['name'] + ', ' + makeBuff(buff['nextBuff']);
        } else {
            return buff['name'];
        }
    }

    function chooseCareer(msg) {
        username = msg['data'];
        $('#login_form').hide();
        $('#choose_career_form').show();
    }

    function handlerAuthorization(msg) {
        switch (msg['code']) {
            case 4:
                chooseCareer(msg);
                break;
            case 1:
                initGame(msg);
                $('#login').hide();
                setPlayerGameState(msg['data']['player']);
                break;
            case 5:
                logout(msg['data']);
            default:
                break;
        }
    }

    function logout(data) {
        var index = -1;
        for (var i = 0; i < otherPlayers.length; i++) {
            if (otherPlayers[i]['clientId'] === data['objectId']) {
                index = i;
                break;
            }
        }
        if (index !== -1) {
            otherPlayers[index].kill();
            otherPlayers.splice(index, 1);
        }
    }

    function handlerStrengthen(msg) {
        switch(msg['code']) {
            case 4000:
                alert(msg['data']['result']);
                setPlayerGameState(msg['data']['player']);
                break;
            case 4001:
                alert(msg['data']['result']);
                break;
        }
    }


    function refreshPosition(data) {
        // console.log(data);
        var clientId = data['clientId'];
        var x = data['x'];
        var y = data['y'];
        var otherPlayer = null;
        for (var i = 0; i < otherPlayers.length; i++) {
            if (otherPlayers[i]['clientId'] === clientId) {
                otherPlayer = otherPlayers[i];
            }
        }
        console.log(otherPlayer);
        if (otherPlayer === null) {
            otherPlayer = game.add.sprite(x + 8, y + 8, otherPlayerTexture);
            otherPlayer.anchor.set(0.5);
            game.physics.arcade.enable(otherPlayer);
            otherPlayer.body.immovable = true;
            otherPlayer['clientId'] = clientId;
            otherPlayers.push(otherPlayer);
        } else {
            otherPlayer.reset(x + 8, y + 8)
        }
    }

    initSocket();
    initListener();
    // initGame();
}());
