/*THIS CLASS IS FOR THE KNIFE THAT PLAYER THROWS OUT*/

package com.neet.Entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import com.neet.Handlers.Content;
import com.neet.TileMap.TileMap;

public class Knife extends MapObject {

	private boolean hit;
	private boolean remove;
	private BufferedImage[] sprites;

	public Knife(TileMap tm, boolean right) {

		super(tm);

		facingRight = right;

		moveSpeed = 3.8;

		if (right)
			dx = moveSpeed;
		else
			dx = -moveSpeed;

		width = 13;
		height = 6;
		cwidth = 8;
		cheight = 8;

		// load sprites

		sprites = new BufferedImage[1];
		sprites = Content.Knife[0];

		animation.setFrames(sprites);
		animation.setDelay(0);

	}

	public void setHit() {

		if (hit)
			return;
		hit = true;
		dx = 0;
	}

	public boolean isHit() {

		return hit;
	}

	public boolean shouldRemove() {

		return remove;
	}

	public void update() {

		checkTileMapCollision();
		setPosition(xtemp, ytemp);

		if (dx == 0 && !hit) {
			setHit();
			remove = true;
		}

		animation.update();

		if (hit) {
			remove = true;
		}
	}

	public void draw(Graphics2D g) {

		super.draw(g);
	}

}
