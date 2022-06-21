package com.neet.Entity.Enemies;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.neet.Entity.Enemy;
import com.neet.Handlers.Content;
import com.neet.TileMap.TileMap;

public class FireBall extends Enemy {
	
	private BufferedImage[] spritesLeft;
	private BufferedImage[] spritesRight;
	
	private boolean start;
	private boolean permanent;
	
	private int type = 0;
	public static int CROSS = 0;
	public static int GRAVITY = 1;
	public static int BOUNCE = 2;
	public static int HORIZONTAL = 3;
	public int bounceCount;
	
	private boolean right;
	
	public FireBall(TileMap tm) {
		
		super(tm);
		
		health = maxHealth = 1;
		
		width = 32;
		height = 40;
		cwidth = 20;
		cheight = 30;
		
		damage = 1;
		moveSpeed = 20;
		

		spritesLeft = Content.FireBallLeft[0];
		spritesRight= Content.FireBallRight[0];
		animation.setFrames(spritesLeft);
		animation.setDelay(1);
		animation.setFrames(spritesRight);
		animation.setDelay(1);
		
		start = true;
		flinching = true;
		permanent = false;
		
	}
	
	public void setType(int i) { type = i; }
	public void setPermanent(boolean b) { permanent = b; }
	public void setVector(boolean c) {right=c;}
	
	public void update() {
		
		if(start) {
			if(animation.hasPlayedOnce() && !right) {
				animation.setFrames(spritesRight);
				animation.setNumFrames(6);
				animation.setDelay(4);
				start = false;
			}
			else if (animation.hasPlayedOnce() && right) {
				animation.setFrames(spritesLeft);
				animation.setNumFrames(6);
				animation.setDelay(4);
				start = false;
			}
		}
		
		if(type == HORIZONTAL) {
			x +=dx;
			bounceCount++;
		}
		else if(type == CROSS) {
			x += dx;
			y += dy;
		}
		else if(type == GRAVITY) {
			dy -= 0.2;
			x += dx;
			y -= dy;
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
//			checkTileMapCollision();
//			if(dx==0) {
//				remove = true;
//			}
		}
		
	}
	
	public void draw(Graphics2D g) {
		super.draw(g);
	}
	

}
