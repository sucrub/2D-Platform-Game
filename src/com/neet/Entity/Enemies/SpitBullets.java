package com.neet.Entity.Enemies;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.neet.Entity.Enemy;
import com.neet.Handlers.Content;
import com.neet.TileMap.TileMap;


public class SpitBullets extends Enemy {
	

	private BufferedImage[] sprites;
	
	private boolean start;
	private boolean permanent;
	
	private int type = 0;
	public static int VECTOR = 0;
	public static int GRAVITY = 1;
	public static int BOUNCE = 5;
	
	private int bounceCount = 0;
	
	public SpitBullets(TileMap tm) {
		
		super(tm);
		
		health = maxHealth = 1;
		
		width = 16;
		height = 25;
		cwidth = 20;
		cheight = 30;
		
		damage = 1;
		moveSpeed = 10;
		

		sprites = Content.SpitBullets[0];
		
		animation.setFrames(sprites);
		animation.setDelay(5);
		
		start = true;
		flinching = true;
		permanent = false;
		
	}
	
	public void setType(int i) { type = i; }
	public void setPermanent(boolean b) { permanent = b; }
	
	public void update() {
		
		if(start) {
			if(animation.hasPlayedOnce()) {
				animation.setFrames(sprites);
				animation.setNumFrames(7);
				animation.setDelay(4);
				start = false;
			}
		}
		
		if(type == VECTOR) {
			x += dx;
			y += dy;
		}
		else if(type == GRAVITY) {
			dy += 0.2;
			x += dx;
			y += dy;
		}
		else if(type == BOUNCE) {
			double dx2 = dx;
			double dy2 = dy;
			checkTileMapCollision();
			if(dx == 0) {
				dx = -dx2;
				bounceCount++;
			}
			if(dy == 0) {
				dy = -dy2;
				bounceCount++;
			}
			x += dx;
			y += dy;
		}
		
		// update animation
		animation.update();
		
		if(!permanent) {
			if(x < 0 || x > tileMap.getWidth() || y < 0 || y > tileMap.getHeight()) {
				remove = true;
			}
			if(bounceCount == 3) {
				remove = true;
			}
		}
		
	}
	
	public void draw(Graphics2D g) {
		super.draw(g);
	}
	
}