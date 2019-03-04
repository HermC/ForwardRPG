'use strict';

var view = document.getElementById('game');
var viewWidth = 1200;
var viewHeight = 800;
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
var chestData = [

];
var blacksmithShopData = [

];

var playerTexture;
var otherPlayerTexture;
var monsterTexture;
var chestTexture;
var blacksmithShopTexture;


var game = new Phaser.Game(viewWidth, viewHeight, Phaser.CANVAS, view);

game.states = {};

var player;

var cursors;

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

        player.body.velocity.x = 0;
        player.body.velocity.y = 0;

        if (cursors.left.isDown) {
            player.body.velocity.x = -this.moveSpeed;
            player.scale.x = 1;
        } else if (cursors.right.isDown) {
            player.body.velocity.x = this.moveSpeed;
            player.scale.x = -1;
        }

        if (cursors.up.isDown) {
            player.body.velocity.y = -this.moveSpeed;
            player.scale.y = -1;
        } else if (cursors.down.isDown) {
            player.body.velocity.y = this.moveSpeed;
            player.scale.y = 1;
        }
    };
};

game.state.add('boot', game.states.boot);
game.state.add('preload', game.states.preload);
game.state.add('start', game.states.start);
game.state.start('boot');
