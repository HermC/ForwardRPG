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

    var cursors;

    var game;
    var socket;

    function initGame() {
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

                player = game.add.sprite(40 * scale, 8 * scale, playerTexture);
                player.anchor.set(0.5);
                game.physics.arcade.enable(player);
                player.body.collideWorldBounds = true;
                game.camera.follow(player);
                cursors = game.input.keyboard.createCursorKeys();

                this.monster = game.add.sprite(40 * scale, 100 * scale, monsterTexture);
                this.monster.anchor.set(0.5);
                game.physics.arcade.enable(this.monster);
                this.monster.body.immovable = true;
            };

            this.update = function() {
                game.physics.arcade.collide(player, this.layer);
                game.physics.arcade.collide(player, this.monster, function(player, monster) {
                    console.log('collide!');
                }, null, this);

                for(var i = 0; i < otherPlayers.length; i++) {
                    game.physics.arcade.collide(player, otherPlayers[i]);
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
            console.log(ev.data);
            var msg = JSON.parse(ev.data);

            switch (msg['code'] - msg['code'] % 1000) {
                case 0:
                    handlerAuthorization(msg);
                    break;
                case 1000:
                    refreshPosition(msg['data']);
                    break;
                case 3000:
                    initPlayerState();
                    break;
                case 4000:

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
    }

    function initPlayerState() {

    }

    function initFight() {
        $('#fight-wrapper').show();
        setSkills();
        setEnemyState();
        setPlayerState();
    }

    function setHistory(text) {
        var history = $('#history').html();
        $('#history').html(text + '<br>' + history);
    }

    function setSkills() {
        $('#skill1').html();
        $('#skill2').html();
        $('#skill3').html();
    }

    function setEnemyState(state) {
        $('#enemy-name').html();
        $('#enemy-hp').html();
        $('#enemy-mp').html();
        $('#enemy-ap').html();
        $('#enemy-buff').html();
    }

    function setPlayerState(state) {
        $('#player-name').html();
        $('#player-hp').html();
        $('#player-mp').html();
        $('#player-ap').html();
        $('#player-buff').html();
    }

    function chooseCareer(msg) {
        username = msg['data'];
        $('#login_form').hide();
        $('#choose_career_form').show();
    }

    function handlerAuthorization(msg) {
        console.log(msg);
        switch (msg['code']) {
            case 4:
                chooseCareer(msg);
                break;
            default:
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
            otherPlayer = game.add.sprite(x, y, otherPlayerTexture);
            otherPlayer.anchor.set(0.5);
            game.physics.arcade.enable(otherPlayer);
            otherPlayer.body.immovable = true;
            otherPlayer['clientId'] = clientId;
            otherPlayers.push(otherPlayer);
        } else {
            otherPlayer.reset(x, y)
        }
    }

    initSocket();
    initListener();
    initGame();
}());
