package com.neet.Handlers;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

// this class loads resources on boot.
// spritesheets are taken from here.

public class Content {
	
	public static BufferedImage[][] EnergyParticle = load("/Sprites/Player/EnergyParticle.gif", 5, 5);
	public static BufferedImage[][] Explosion = load("/Sprites/Enemies/Explosion.gif", 16, 16);
	
	public static BufferedImage[][] Mushroom1 = load("/Sprites/Enemies/Mushroom1.gif", 16, 16);
	public static BufferedImage[][] Mushroom = load("/Sprites/Enemies/Mushroom.gif", 16, 16);

	public static BufferedImage[][] Bird = load("/Sprites/Enemies/Bird.gif", 8, 8);
	public static BufferedImage[][] Bomb = load("/Sprites/Enemies/Bomb.gif", 30, 30);
	public static BufferedImage[][] Goblin = load("/Sprites/Enemies/Goblin.gif", 16, 16);

	public static BufferedImage[][] DarkEnergy = load("/Sprites/Enemies/DarkEnergy.gif", 20, 20);
	public static BufferedImage[][] BigBoss = load("/Sprites/Enemies/BigBoss.png", 64, 62);
	public static BufferedImage[][] SpitBullets = load ("/Sprites/Enemies/BulletsBoss.gif", 16, 25);
	public static BufferedImage[][] FireBallLeft = load("/Sprites/Enemies/FireBallLeft.gif",40, 32);
	public static BufferedImage[][] FireBallRight = load("/Sprites/Enemies/FireBallRight.png",40, 32);
	

	

	
	
	public static BufferedImage[][] load(String s, int w, int h) {
		BufferedImage[][] ret;
		try {
			BufferedImage spritesheet = ImageIO.read(Content.class.getResourceAsStream(s));
			int width = spritesheet.getWidth() / w;
			int height = spritesheet.getHeight() / h;
			ret = new BufferedImage[height][width];
			for(int i = 0; i < height; i++) {
				for(int j = 0; j < width; j++) {
					ret[i][j] = spritesheet.getSubimage(j * w, i * h, w, h);
				}
			}
			return ret;
		}
		catch(Exception e) {
			e.printStackTrace();
			System.out.println("Error loading graphics.");
			System.exit(0);
		}
		return null;
	}
	
}
