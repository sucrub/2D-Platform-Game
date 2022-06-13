package com.neet.Entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class HUD {
	
	private Player player;
	
	private BufferedImage heart;
	private BufferedImage life;
	
	public HUD(Player p) {
		player = p;
		try {
			BufferedImage imageheart = ImageIO.read(getClass().getResourceAsStream("/HUD/hearts_hud.png"));
			heart = imageheart.getSubimage(0, 0, 16, 16);
			BufferedImage imagelife = ImageIO.read(getClass().getResourceAsStream("/HUD/lifes_icon.png"));
			life = imagelife.getSubimage(0, 0, 16, 16);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void draw(Graphics2D g) {
		for(int i = 0; i < player.getHealth(); i++) {
			g.drawImage(heart, 10 + i * 15, 10, null);
		}
		for(int i = 0; i < player.getLives(); i++) {
			g.drawImage(life, 10 + i * 15, 30, null);
		}
		g.setColor(java.awt.Color.WHITE);
		g.drawString(player.getTimeToString(), 290, 15);
	}
	
}













