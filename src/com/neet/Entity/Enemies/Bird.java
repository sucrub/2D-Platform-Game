package com.neet.Entity.Enemies;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.neet.Entity.Enemy;
import com.neet.Handlers.Content;
import com.neet.TileMap.TileMap;


public class Bird extends Enemy {
	
	private BufferedImage[] idleSprites;
	
	private int tick;
	private double a;
	private double b;
	
	public Bird(TileMap tm) {
		
		super(tm);
		
		 //SetDifficult
		if(com.neet.GameState.ChooseDifficultyState.Hard()) {
			health = maxHealth =4;
		}
		else {
			health = maxHealth = 2;
		}
		
		width = 8;
		height = 8;
		cwidth = 8;
		cheight = 8;
		
		damage = 1;
		moveSpeed = 5;
		
		idleSprites = Content.Bird[0];
		
		animation.setFrames(idleSprites);
		animation.setDelay(4);
		
		tick = 0;
		a = Math.random() * 0.06 + 0.07;
		b = Math.random() * 0.06 + 0.07;
		
	}
	
	public void update() {
		
		// check if done flinching
		if(flinching) {
			flinchCount++;
			if(flinchCount == 6) flinching = false;
		}
		
		tick++;
		x = Math.sin(a * tick) + x;
		y = Math.sin(b * tick) + y;
		
		// update animation
		animation.update();
		
	}
	
	public void draw(Graphics2D g) {
		
		if (flinching) {
			if (flinchCount % 10 < 10)
				return;
		}
		
		super.draw(g);
		
	}
	
}
