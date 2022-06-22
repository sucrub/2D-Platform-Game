/*THIS CLASS IS FOR HUD, UI ELEMENT ON THE SCREEN INCLUDING HEALTHS, LIVES AND MANAS*/

package com.neet.Entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class HUD {
	
	private Player player;
	
	private BufferedImage heart;
	private BufferedImage life;
	private BufferedImage mp;
	
	public HUD(Player p) {
		
		player = p;
		try {
			
			BufferedImage imageheart = ImageIO.read(getClass().getResourceAsStream("/HUD/hearts_hud.png"));
			heart = imageheart.getSubimage(0, 0, 16, 16);
			
			BufferedImage imagelife = ImageIO.read(getClass().getResourceAsStream("/HUD/lifes_icon.png"));
			life = imagelife.getSubimage(0, 0, 16, 16);
			
			BufferedImage imagemp = ImageIO.read(getClass().getResourceAsStream("/HUD/orbs_hud.png"));
			mp = imagemp.getSubimage(0,  0, 9, 9);
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
		
		for(int i = 0; i < player.getMp() - 200; i+=200) {
			g.drawImage(mp, 10 + i * 15 / 200, 50, null);
		}
		
//		g.setColor(java.awt.Color.WHITE);
//		g.drawString(player.getTimeToString(), 235, 15);
	}
	
}













