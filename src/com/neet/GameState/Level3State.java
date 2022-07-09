package com.neet.GameState;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;


import com.neet.Audio.Audio;
import com.neet.Entity.Enemy;
import com.neet.Entity.EnemyProjectile;
import com.neet.Entity.Explosion;
import com.neet.Entity.HUD;
import com.neet.Entity.Player;
import com.neet.Entity.PlayerStatus;

import com.neet.Entity.Enemies.BigBoss;

import com.neet.Handlers.Keys;
import com.neet.Main.GamePanel;
import com.neet.TileMap.Background;
import com.neet.TileMap.TileMap;

public class Level3State extends GameState {

	private Background sky;

	private Player player;
	private TileMap tileMap;
	private ArrayList<Enemy> enemies;
	private ArrayList<EnemyProjectile> eprojectiles;
	private ArrayList<Explosion> explosions;

	private HUD hud;
//	private Teleport teleport;
	
	//CountBoss
	private int CountBoss=1;

	// events
	private boolean blockInput = false;
	private int eventCount = 0;
	private boolean eventStart;
	private ArrayList<Rectangle> tb;
	private boolean eventFinish;
	private boolean eventDead;


	public Level3State(GameStateManager gsm) {
		super(gsm);
		init();
	}

	public void init() {

		// backgrounds
		sky = new Background("/Backgrounds/bossbackground.png");

		// tilemap
		tileMap = new TileMap(32);
		tileMap.loadTiles("/Tilesets/tilesetnewer.png");
		tileMap.loadMap("/Maps/level1c.map");

		// player
		player = new Player(tileMap);
		player.setPosition(250, 61);
		player.setHealth(PlayerStatus.getHealth());
		player.setLives(PlayerStatus.getLives());
		player.setTime(PlayerStatus.getTime());
		player.setmaxHealth(PlayerStatus.getmaxHealth());

		// enemies
		enemies = new ArrayList<Enemy>();
		eprojectiles = new ArrayList<EnemyProjectile>();
		populateEnemies();
		
		player.init(enemies);

		// explosions
		explosions = new ArrayList<Explosion>();

		// hud
		hud = new HUD(player);

//		// teleport
//		teleport = new Teleport(tileMap);
//		//teleport.setPosition(600, 61);

		// start event
		eventStart = true;
		tb = new ArrayList<Rectangle>();
		eventStart();

		// sfx
		Audio.load("/SFX/teleport.mp3", "teleport");
		Audio.load("/SFX/explode.mp3", "explode");
		Audio.load("/SFX/enemyhit.mp3", "enemyhit");

		// music
		Audio.load("/Music/level2.mp3", "level2");
		Audio.stop("level2");
		
		Audio.load("/Music/level3.mp3", "level3");
		Audio.loop("level3", 600, Audio.getFrames("level3") - 2200);

	}

	private void populateEnemies() {
	
		enemies.clear();
		
		BigBoss n;

		n = new BigBoss(tileMap, player, enemies,0);
		n.setPosition(200,88);
		enemies.add(n);
		
		/////HARD///
		if(ChooseDifficultyState.hard()) {
			n = new BigBoss(tileMap, player, enemies,1);
			n.setPosition(450,88);
			enemies.add(n);
			CountBoss=2;
		}
	}

	public void update() {

		
		// check keys
		handleInput();

		// check if player dead
		if (player.getHealth() == 0 || player.gety() > tileMap.getHeight()) {
			eventDead = blockInput = true;
		}
		
		//Check if done Boss
		if (CountBoss==0) {
			eventFinish = blockInput = true;
		}
		
		// play events
		if (eventStart)
			eventStart();
		if (eventDead)
			eventDead();
		if (eventFinish)
			eventFinish();

		// update player
		player.update();

		// update tilemap
		tileMap.setPosition(
				GamePanel.WIDTH / 2 - player.getx(),
				GamePanel.HEIGHT / 2 - player.gety());

		// update enemies
		for (int i = 0; i < enemies.size(); i++) {
			Enemy e = enemies.get(i);
			e.update();
			if (e.isDead()) {
				enemies.remove(i);
				i--;
				explosions.add(new Explosion(tileMap, e.getx(), e.gety()));
				CountBoss--;
			}
		}

		// update enemy projectiles
		for (int i = 0; i < eprojectiles.size(); i++) {
			EnemyProjectile ep = eprojectiles.get(i);
			ep.update();
			if (ep.shouldRemove()) {
				eprojectiles.remove(i);
				i--;
			}
		}

		// update explosions
		for (int i = 0; i < explosions.size(); i++) {
			explosions.get(i).update();
			if (explosions.get(i).shouldRemove()) {
				explosions.remove(i);
				i--;
			}
			
		}

	}

	public void draw(Graphics2D g) {

		// draw background
		sky.draw(g);
//		mountains.draw(g);

		// draw tilemap
		tileMap.draw(g);

		// draw enemies
		for (int i = 0; i < enemies.size(); i++) {
			enemies.get(i).draw(g);
		}

		// draw enemy projectiles
		for (int i = 0; i < eprojectiles.size(); i++) {
			eprojectiles.get(i).draw(g);
		}

		// draw explosions
		for (int i = 0; i < explosions.size(); i++) {
			explosions.get(i).draw(g);
		}

		// draw player
		player.draw(g);

		// draw hud
		hud.draw(g);

		// draw transition boxes
		g.setColor(java.awt.Color.BLACK);
		for (int i = 0; i < tb.size(); i++) {
			g.fill(tb.get(i));
		}

	}

	public void handleInput() {
		
		if (Keys.isPressed(Keys.ESCAPE)) {
			Audio.stop("level3");
			gsm.setPaused(true);
			PauseState.setNumState(3);
		}
		if (blockInput || player.getHealth() == 0)
			return;
		player.setUp(Keys.keyState[Keys.UP]);
		player.setLeft(Keys.keyState[Keys.LEFT]);
		player.setDown(Keys.keyState[Keys.DOWN]);
		player.setRight(Keys.keyState[Keys.RIGHT]);
		player.setJumping(Keys.keyState[Keys.UP]);

		if (Keys.isPressed(Keys.BUTTON_E))
			player.setDashing();
		if (Keys.isPressed(Keys.BUTTON_R))
			player.setAttacking();
		if (Keys.isPressed(Keys.BUTTON_F))
			player.setFlyingKnife();

	}

	///////////////////////////////////////////////////////
	//////////////////// EVENTS
	///////////////////////////////////////////////////////

	// reset level
	private void reset() {
		
		player.reset();
		player.setPosition(250, 61);
		populateEnemies();
		blockInput = true;
		eventCount = 0;
		
		eventStart = true;
		eventStart();
	}

	// level started
	private void eventStart() {
		eventCount++;
		if (eventCount == 1) {
			tb.clear();
			tb.add(new Rectangle(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT / 2));
			tb.add(new Rectangle(0, 0, GamePanel.WIDTH / 2, GamePanel.HEIGHT));
			tb.add(new Rectangle(0, GamePanel.HEIGHT / 2, GamePanel.WIDTH, GamePanel.HEIGHT / 2));
			tb.add(new Rectangle(GamePanel.WIDTH / 2, 0, GamePanel.WIDTH / 2, GamePanel.HEIGHT));
		}
		if (eventCount > 1 && eventCount < 60) {
			tb.get(0).height -= 4;
			tb.get(1).width -= 6;
			tb.get(2).y += 4;
			tb.get(3).x += 6;
		}
		if (eventCount == 60) {
			eventStart = blockInput = false;
			eventCount = 0;
			tb.clear();
		}
	}

	// player has died
	private void eventDead() {
		eventCount++;
		if (eventCount == 1) {
			player.setDead();
			player.stop();
		}
		if (eventCount == 60) {
			tb.clear();
			tb.add(new Rectangle(
					GamePanel.WIDTH / 2, GamePanel.HEIGHT / 2, 0, 0));
		} else if (eventCount > 60) {
			tb.get(0).x -= 6;
			tb.get(0).y -= 4;
			tb.get(0).width += 12;
			tb.get(0).height += 8;
		}
		if (eventCount >= 120) {
			if (player.getLives() == 0) {
				Audio.stop("level3");
				gsm.setState(GameStateManager.LOSTSTATE);
			} else {
				eventDead = blockInput = false;
				eventCount = 0;
				player.loseLife();
				reset();
			}
		}
	}

	// finished level
	private void eventFinish() {
		eventCount++;
		if (eventCount == 1) {
			Audio.play("teleport");
			player.setTeleporting(true);
			player.stop();
		} else if (eventCount == 120) {
			tb.clear();
			tb.add(new Rectangle(
					GamePanel.WIDTH / 2, GamePanel.HEIGHT / 2, 0, 0));
		} else if (eventCount > 120) {
			tb.get(0).x -= 6;
			tb.get(0).y -= 4;
			tb.get(0).width += 12;
			tb.get(0).height += 8;
			Audio.stop("teleport");
		}
		if (eventCount == 180) {
			PlayerStatus.setHealth(player.getHealth());
			PlayerStatus.setLives(player.getLives());
			PlayerStatus.setTime(player.getTime());
			Audio.stop("level3");
			gsm.setState(GameStateManager.VICTORYSTATE);
		}

	}

}