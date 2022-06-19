package com.neet.GameState;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import com.neet.Audio.JukeBox;
import com.neet.Entity.Enemy;
import com.neet.Entity.EnemyProjectile;
import com.neet.Entity.EnergyParticle;
import com.neet.Entity.Explosion;
import com.neet.Entity.HUD;
import com.neet.Entity.Player;
import com.neet.Entity.PlayerSave;
import com.neet.Entity.Teleport;
import com.neet.Entity.Title;

import com.neet.Entity.Enemies.BigBoss;
import com.neet.Entity.Enemies.Bird;
import com.neet.Entity.Enemies.Goblin;
import com.neet.Entity.Enemies.Mushroom;
import com.neet.Entity.Enemies.Mushroom1;
import com.neet.Entity.Enemies.Bomb;

import com.neet.Handlers.Keys;
import com.neet.Main.GamePanel;
import com.neet.TileMap.Background;
import com.neet.TileMap.TileMap;

public class Level1AState extends GameState {

	private Background sky;
	private Background mountains;
	
	private Player player;
	private TileMap tileMap;
	private ArrayList<Enemy> enemies;
	private ArrayList<EnemyProjectile> eprojectiles;
	private ArrayList<EnergyParticle> energyParticles;
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

	public Level1AState(GameStateManager gsm) {
		super(gsm);
		init();
	}

	public void init() {

		// backgrounds
		sky = new Background("/Backgrounds/background2.png", 0);
		mountains = new Background("/Backgrounds/mountain.png", 0.2);

		// tilemap
		tileMap = new TileMap(32);
		tileMap.loadTiles("/Tilesets/tilesetnewer.png");
		tileMap.loadMap("/Maps/level1a.map");
		//tileMap.setPosition(100, 0);
		//tileMap.setBounds(
		//		tileMap.getWidth() - 1 * tileMap.getTileSize(),
		//		tileMap.getHeight() - 2 * tileMap.getTileSize(),
		//		0, 0);
//		tileMap.setTween(1);

		// player
		player = new Player(tileMap);
		player.setPosition(400, 61);
		player.setHealth(PlayerSave.getHealth());
		player.setLives(PlayerSave.getLives());
		player.setTime(PlayerSave.getTime());
		player.setmaxHealth(PlayerSave.getmaxHealth());

		// enemies
		enemies = new ArrayList<Enemy>();
		eprojectiles = new ArrayList<EnemyProjectile>();
		populateEnemies();

		// energy particle
		energyParticles = new ArrayList<EnergyParticle>();

		// init player
		player.init(enemies, energyParticles);

		// explosions
		explosions = new ArrayList<Explosion>();

		// hud
		hud = new HUD(player);

		// teleport
		teleport = new Teleport(tileMap);
		teleport.setPosition(600, 61);

		// start event
		eventStart = true;
		tb = new ArrayList<Rectangle>();
		eventStart();

		// sfx
		JukeBox.load("/SFX/teleport.mp3", "teleport");
		JukeBox.load("/SFX/explode.mp3", "explode");
		JukeBox.load("/SFX/enemyhit.mp3", "enemyhit");

		// music
		JukeBox.load("/Music/level1.mp3", "level1");
		JukeBox.loop("level1", 600, JukeBox.getFrames("level1") - 2200);

	}

	private void populateEnemies() {
	
		enemies.clear();
	
		GelPop gp;
		Gazer g;
		Mushroom m;
		Tengu t;
		BigBoss n;

		Bomb bo = new Bomb(tileMap, player, enemies);
		bo.setPosition(1300, 100);
		enemies.add(bo);
		bo = new Bomb(tileMap, player, enemies);
		bo.setPosition(1330, 100);
		enemies.add(bo);
		bo = new Bomb(tileMap, player, enemies);
		bo.setPosition(1360, 100);
		enemies.add(bo);
		Goblin go;
		Bird bi;
		Mushroom m;
		Mushroom1 m1;

		go = new Goblin(tileMap, player);
		go.setPosition(1300, 100);
		enemies.add(go);
		go = new Goblin(tileMap, player);
		go.setPosition(1320, 100);
		enemies.add(go);
		go = new Goblin(tileMap, player);
		go.setPosition(1340, 100);
		enemies.add(go);
		go = new Goblin(tileMap, player);
		go.setPosition(1660, 100);
		enemies.add(go);
		go = new Goblin(tileMap, player);
		go.setPosition(1680, 100);
		enemies.add(go);
		go = new Goblin(tileMap, player);
		go.setPosition(1700, 100);
		enemies.add(go);
		go = new Goblin(tileMap, player);
		go.setPosition(2177, 100);
		enemies.add(go);
		go = new Goblin(tileMap, player);
		go.setPosition(2960, 100);
		enemies.add(go);
		go = new Goblin(tileMap, player);
		go.setPosition(2980, 100);
		enemies.add(go);
		go = new Goblin(tileMap, player);
		go.setPosition(3000, 100);
		enemies.add(go);
		
		bi = new Bird(tileMap);
		bi.setPosition(2600, 100);
		enemies.add(bi);
		bi = new Bird(tileMap);
		bi.setPosition(3500, 100);
		enemies.add(bi);
		
		m = new Mushroom(tileMap, player);
		m.setPosition(700, 184);
		enemies.add(m);
		m = new Mushroom(tileMap, player);
		m.setPosition(1000, 88);
		enemies.add(m);
		m = new Mushroom(tileMap, player);
		m.setPosition(2050, 88);
		enemies.add(m);
		m = new Mushroom(tileMap, player);
		m.setPosition(2150, 57);
		enemies.add(m);
		
		n = new BigBoss(tileMap, player, enemies);
		n.setPosition(800,88);
		enemies.add(n);

		m1 = new Mushroom1(tileMap, player);
		m1.setPosition(700, 100);
		enemies.add(m1);

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

		// move backgrounds
		mountains.setPosition(tileMap.getx(), tileMap.gety());

		// update player
		player.update();

		// update tilemap
		tileMap.setPosition(
				GamePanel.WIDTH / 2 - player.getx(),
				GamePanel.HEIGHT / 2 - player.gety());

		tileMap.fixBounds();

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
		mountains.draw(g);

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

		// draw teleport
		teleport.draw(g);

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

	}

	///////////////////////////////////////////////////////
	//////////////////// EVENTS
	///////////////////////////////////////////////////////

	// reset level
	private void reset() {
		
		player.reset();
		player.setPosition(300, 61);
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
			JukeBox.play("teleport");
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
			JukeBox.stop("teleport");
		}
		if (eventCount == 180) {
			PlayerSave.setHealth(player.getHealth());
			PlayerSave.setLives(player.getLives());
			PlayerSave.setTime(player.getTime());
			gsm.setState(GameStateManager.HELPSTATE);
		}

	}

}