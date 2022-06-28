package com.neet.GameState;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import com.neet.Audio.Audio;
import com.neet.Entity.Enemy;
import com.neet.Entity.EnemyProjectile;
import com.neet.Entity.Explosion;
import com.neet.Entity.HUD;
import com.neet.Entity.Player;
import com.neet.Entity.PlayerStatus;
import com.neet.Entity.Teleport;

import com.neet.Entity.Enemies.BigBoss;
import com.neet.Entity.Enemies.Bird;
import com.neet.Entity.Enemies.Goblin;
import com.neet.Entity.Enemies.Mushroom;
import com.neet.Entity.Enemies.Mushroom1;
import com.neet.Entity.Enemies.Bomber;

import com.neet.Handlers.Keys;
import com.neet.Main.GamePanel;
import com.neet.TileMap.Background;
import com.neet.TileMap.TileMap;

public class Level1State extends GameState {

	private Background sky;

	private Player player;
	private TileMap tileMap;
	private ArrayList<Enemy> enemies;
	private ArrayList<EnemyProjectile> eprojectiles;
	private ArrayList<Explosion> explosions;

	private HUD hud;
	private Teleport teleport;

	// events
	private boolean blockInput = false;
	private int eventCount = 0;
	private boolean eventStart;
	private ArrayList<Rectangle> tb;
	private boolean eventFinish;
	private boolean eventDead;

	public Level1State(GameStateManager gsm) {
		
		super(gsm);
		init();
	}

	public void init() {

		// backgrounds
		sky = new Background("/Backgrounds/level1background.png", 0);

		// tilemap
		tileMap = new TileMap(32);
		tileMap.loadTiles("/Tilesets/tilesetnewer.png");
		tileMap.loadMap("/Maps/level1a.map");

		// player
		player = new Player(tileMap);
		player.setPosition(400, 61); //400, 61
		player.setHealth(PlayerStatus.getHealth());
		player.setLives(PlayerStatus.getLives());
		player.setTime(PlayerStatus.getTime());
		player.setmaxHealth(PlayerStatus.getmaxHealth());

		// enemies
		enemies = new ArrayList<Enemy>();
		eprojectiles = new ArrayList<EnemyProjectile>();
		populateEnemies();

		// init player
		player.init(enemies);

		// explosions
		explosions = new ArrayList<Explosion>();

		// hud
		hud = new HUD(player);

		// teleport
		teleport = new Teleport(tileMap);
		teleport.setPosition(4200, 51);

		// start event
		eventStart = true;
		tb = new ArrayList<Rectangle>();
		eventStart();

		// sfx
		Audio.load("/SFX/teleport.mp3", "teleport");
		Audio.load("/SFX/explode.mp3", "explode");
		Audio.load("/SFX/enemyhit.mp3", "enemyhit");

		// music
		Audio.load("/Music/level1.mp3", "level1");
		Audio.loop("level1", 600, Audio.getFrames("level1") - 2200);

	}

	private void populateEnemies() {

		enemies.clear();

		Goblin go;
		Bomber bo;
		Bird bi;
		Mushroom m;
		
		go = new Goblin(tileMap, player);
		go.setPosition(800, 100);
		enemies.add(go);
		
		go = new Goblin(tileMap, player);
		go.setPosition(850, 100);
		enemies.add(go);
		
		go = new Goblin(tileMap, player);
		go.setPosition(1000, 100);
		enemies.add(go);
		
		go = new Goblin(tileMap, player);
		go.setPosition(1400, 100);
		enemies.add(go);
		
		go = new Goblin(tileMap, player);
		go.setPosition(1600, 150);
		enemies.add(go);
		
		go = new Goblin(tileMap, player);
		go.setPosition(1630, 150);
		enemies.add(go);
		
		go = new Goblin(tileMap, player);
		go.setPosition(1660, 150);
		enemies.add(go);
		
		go = new Goblin(tileMap, player);
		go.setPosition(1720, 150);
		enemies.add(go);
		
		go = new Goblin(tileMap, player);
		go.setPosition(2550, 100);
		enemies.add(go);
		
		go = new Goblin(tileMap, player);
		go.setPosition(2630, 140);
		enemies.add(go);
		
		go = new Goblin(tileMap, player);
		go.setPosition(2830, 120);
		enemies.add(go);
		
		go = new Goblin(tileMap, player);
		go.setPosition(2870, 120);
		enemies.add(go);
		
		go = new Goblin(tileMap, player);
		go.setPosition(3200, 120);
		enemies.add(go);
		
		go = new Goblin(tileMap, player);
		go.setPosition(3600, 120);
		enemies.add(go);
		
		go = new Goblin(tileMap, player);
		go.setPosition(3540, 120);
		enemies.add(go);
		
		m = new Mushroom(tileMap, player);
		m.setPosition(590, 215);
		enemies.add(m);
		
		m = new Mushroom(tileMap, player);
		m.setPosition(980, 88);
		enemies.add(m);
		
		m = new Mushroom(tileMap, player);
		m.setPosition(1650, 185);
		enemies.add(m);
		
		m = new Mushroom(tileMap, player);
		m.setPosition(2790, 120);
		enemies.add(m);
		
		m = new Mushroom(tileMap, player);
		m.setPosition(3180, 120);
		enemies.add(m);
		
		m = new Mushroom(tileMap, player);
		m.setPosition(3350, 153);
		enemies.add(m);
		
		bi = new Bird(tileMap);
		bi.setPosition(1900, 80);
		enemies.add(bi);
		
		bi = new Bird(tileMap);
		bi.setPosition(2100, 50);
		enemies.add(bi);
		
		bi = new Bird(tileMap);
		bi.setPosition(2350, 70);
		enemies.add(bi);
		
		bi = new Bird(tileMap);
		bi.setPosition(3230, 170);
		enemies.add(bi);
		
		bi = new Bird(tileMap);
		bi.setPosition(3280, 170);
		enemies.add(bi);
		
		bo = new Bomber(tileMap, player, enemies); 
		bo.setPosition(2630, 50);
		enemies.add(bo);
		
		bo = new Bomber(tileMap, player, enemies); 
		bo.setPosition(3900, 50);
		enemies.add(bo);
		
		////////HARD////////
		if(ChooseDifficultyState.hard()) {
			
			go = new Goblin(tileMap, player);
			go.setPosition(3560, 120);
			enemies.add(go);
			
			go = new Goblin(tileMap, player);
			go.setPosition(3580, 120);
			enemies.add(go);
			
			bo = new Bomber(tileMap, player, enemies); 
			bo.setPosition(1250, 40);
			enemies.add(bo);
			
			bo = new Bomber(tileMap, player, enemies); 
			bo.setPosition(3280, 50);
			enemies.add(bo);
		}
	}

	public void update() {
		
		// check keys
		handleInput();

		// check if end of level
		if (teleport.contains(player)) {
			eventFinish = blockInput = true;
		}

		// check if player dead
		if (player.getHealth() == 0 || player.gety() > tileMap.getHeight()) {
			eventDead = blockInput = true;
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
		tileMap.setPosition(GamePanel.WIDTH / 2 - player.getx(), GamePanel.HEIGHT / 2 - player.gety());

		// update enemies
		for (int i = 0; i < enemies.size(); i++) {
			
			Enemy e = enemies.get(i);
			e.update();
			if (e.isDead()) {
				
				enemies.remove(i);
				i--;
				explosions.add(new Explosion(tileMap, e.getx(), e.gety()));
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

		// update teleport
		teleport.update();

	}

	public void draw(Graphics2D g) {

		// draw background
		sky.draw(g);

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

		// draw teleport
		teleport.draw(g);
		
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

		if (Keys.isPressed(Keys.ESCAPE))
			gsm.setPaused(true);
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
		player.setPosition(400, 61);
		populateEnemies();
		blockInput = true;
		eventCount = 0;

		eventStart = true;
		eventStart();
	}

	// level started
	private void eventStart() {
		
		eventCount++;
		//transition
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
		//transition
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
				gsm.setState(GameStateManager.MENUSTATE);
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
			//transition
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

			gsm.setState(GameStateManager.LEVEL2STATE);
		}

	}

}