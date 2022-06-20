package com.neet.Entity.Enemies;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.neet.Entity.Enemy;
import com.neet.Entity.Player;
import com.neet.Handlers.Content;
import com.neet.Main.GamePanel;
import com.neet.TileMap.TileMap;

public class Mushroom1 extends Enemy{
	
	private BufferedImage[] sprites;
	private Player player;
	private boolean active;
	
	public Mushroom1(TileMap tm, Player p) {
		
		super(tm);
		player = p;
		
		health = maxHealth = 4;
		
		width = 16;
		height = 16;
		cwidth = 8;
		cheight = 8;
		
		damage = 1;
		
		sprites = Content.Mushroom1[0];
		
		animation.setFrames(sprites);
		animation.setDelay(4);
		
		left = true;
		facingRight = false;
		
	}
	
	private void getNextPosition() {}
	
	public void update() {
		
		if(!active) {
			if(Math.abs(player.getx() - x) < GamePanel.WIDTH) active = true;
			return;
		}
		
		// check if done flinching
		if(flinching) {
			flinchCount++;
			if(flinchCount == 6) flinching = false;
		}
		
		getNextPosition();
		checkTileMapCollision();
		calculateCorners(x, ydest + 1);
		if(!bottomLeft) {
			left = false;
			right = facingRight = true;
		}
		if(!bottomRight) {
			left = true;
			right = facingRight = false;
		}
		setPosition(xtemp, ytemp);
		
		if(dx == 0) {
			left = !left;
			right = !right;
			facingRight = !facingRight;
		}
		
		// update animation
		animation.update();
		
	}
	
	public void draw(Graphics2D g) {
		
		if(flinching) {
			if(flinchCount == 0 || flinchCount == 2) return;
		}
		
		super.draw(g);
		
	}


}
