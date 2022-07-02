package com.neet.Entity.Enemies;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import com.neet.Entity.Enemy;
import com.neet.Entity.Player;
import com.neet.Handlers.Content;
import com.neet.TileMap.TileMap;

public class BigBoss extends Enemy {
	
	private Player player;
	
	private int BossNumber;
	
	private ArrayList<Enemy> enemies;
	
	private BufferedImage[] jumpSprites;
	private BufferedImage[] idleSprites;
	private BufferedImage[] attackSprites;
	private BufferedImage	HeartBarBoss;
	
	private boolean jumping;
	
	private static final int IDLE = 0;
	private static final int JUMPING = 1;
	private static final int ATTACKING = 2;
	
	private int attackTick;
	private int attackDelay = 30;
	private int step=0;
	private int timeDelay=0;
	private int chooseNextSkill=0;
	private double percentHealth;


	public BigBoss(TileMap tm, Player p, ArrayList<Enemy> en, int t) {

		super(tm);
		player = p;
		enemies = en;
		BossNumber=t;
		
		try {
			
			BufferedImage Bar = ImageIO.read(getClass().getResourceAsStream("/Sprites/Enemies/HeartBarBoss.png"));
			HeartBarBoss = Bar.getSubimage(0, 0, 224, 24);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		health = maxHealth = 20;
		
		//image size
		width = 64;
		height = 62;
		//hitbox
		cwidth = 50;
		cheight = 48;
		
		damage = 2;
		moveSpeed = 1.5;
		fallSpeed = 0.15;
		maxFallSpeed = 4.0;
		jumpStart = -5;
		
		idleSprites = Content.BigBoss[0];
		jumpSprites = Content.BigBoss[0];
		attackSprites = Content.BigBoss[0];
		
		animation.setFrames(idleSprites);
		animation.setDelay(4);
		
		left = true;
		facingRight = false;
		
		attackTick =0;
	}
	
	private void getNextPosition() {
		
		if(left) dx = -moveSpeed;
		else if(right) dx = moveSpeed;
		else dx = 0;
		
		if(falling) {
			
			dy += fallSpeed;
			if(dy > maxFallSpeed) dy = maxFallSpeed;
		}
		
		if(jumping && !falling) {
			
			dy = jumpStart;
		}
	}
	
	
	public int getRandomNumber(int min, int max) {
		
	    return (int) ((Math.random() * (max - min)) + min);
	}
	
	public void update() {
		
	//percentHealth
		percentHealth = 210/maxHealth;

	// check if done flinching
		if(flinching) {
			flinchCount++;
			if(flinchCount == 9) flinching = false;
		}
		
		getNextPosition();
		checkTileMapCollision();
		setPosition(xtemp, ytemp);
		
		// update animation
		animation.update();
		
		if(player.getx() < x) facingRight = false;
		else facingRight = true;
		
		if(timeDelay<1) {
			timeDelay++;
		}
		
		else {
			
			timeDelay=0;
			// idle
			if(step == 0) {
				if(currentAction != IDLE) {
					currentAction = IDLE;
					animation.setFrames(idleSprites);
					animation.setDelay(3);
				}
				attackTick++;
				if(attackTick >= attackDelay ) {
					step++;
					attackTick = 0;
					chooseNextSkill = getRandomNumber(0,2);
				}
			}
			
			if(chooseNextSkill==0) {
				
				// jump away
				if(step == 1) {
					if(currentAction != JUMPING) {
						currentAction = JUMPING;
						animation.setFrames(jumpSprites);
						animation.setDelay(3);
					}
					jumping = true;
					if(player.getx()-xtemp < 0 ) left = true;
					else right = true;
					if(falling) {
						step++;
					}
				}
				
				// attack
				if(step == 2) {
					
					if(dy > 0 && currentAction != ATTACKING) {
						
						currentAction = ATTACKING;
						animation.setFrames(attackSprites);
						animation.setDelay(3);
						SpitBullets de = new SpitBullets(tileMap);
						de.setPosition(x, y);
						de.setType(getRandomNumber(0,3));
						if(facingRight) de.setVector(3, 3);
						else de.setVector(-3, 3);
						enemies.add(de);
						}
					
					if(currentAction == ATTACKING && animation.hasPlayedOnce()) {
						
						step++;
						currentAction = JUMPING;
						animation.setFrames(jumpSprites);
						animation.setDelay(3);
					}
				}
				
				// done attacking
				if(step == 3) {
					
					if(dy == 0) step++;
				}
				
				// land
				if(step == 4) {
					
					step = 0;
					left = right = jumping = false;
				}
			}
			
			else {
				
				if(step == 1) {
					
					if(dy ==0 && currentAction != ATTACKING) {
						currentAction = ATTACKING;
						animation.setFrames(attackSprites);
						animation.setDelay(3);
						
						FireBall fb = new FireBall(tileMap);
						fb.setVector(facingRight);
						fb.setPosition(x, y);
						fb.setType(3);
						if(facingRight) fb.setVector(3, 3);
						else fb.setVector(-3, 3);
						enemies.add(fb);
					}
					
					if(currentAction == ATTACKING && animation.hasPlayedOnce()) {
						
						step+=2;
						currentAction = IDLE;
						animation.setFrames(idleSprites);
						animation.setDelay(3);
						}
				}
				
				// done attacking
				if(step == 3) {
					
					if(dy == 0) step++;
				}
				
				// land
				if(step == 4) {
					
					step = 0;
					left = right = jumping = false;
				}
			}
	}			

}
	
	public void draw(Graphics2D g) {
		
		if (flinching) 
			if (flinchCount % 10 < 10)
				return;

		g.drawImage(HeartBarBoss,149, 10+BossNumber*24, null);
		g.setColor(Color.RED);
		g.fillRect(149+14, 10+15+BossNumber*24, (int)percentHealth*health , 4);
		
		if(flinching) {
			if(flinchCount == 0 || flinchCount == 2) return;
		}
		
		super.draw(g);
		
	}
	
}


