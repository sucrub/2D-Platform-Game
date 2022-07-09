package com.neet.TileMap;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import com.neet.Main.GamePanel;

public class Background {
	
	private BufferedImage image;
	
	private double x;
	private double y;
	
	private int width;
	private int height;
	
	public Background(String s) {
		
		try {
			
			image = ImageIO.read(getClass().getResourceAsStream(s));
			width = image.getWidth();
			height = image.getHeight();
		}
		catch(Exception e) {
			
			e.printStackTrace();
		}
	}
	
	public double getx() { 
		return x; 
	}
	
	public double gety() {
		return y;
	}
	
	public void update() {
		
		while(x <= -width) x += width;
		while(x >= width) x -= width;
		while(y <= -height) y += height;
		while(y >= height) y -= height;
	}
	
	public void draw(Graphics2D g) {
		
		g.drawImage(image, (int)x, (int)y, null);
		
		if(x < 0) {
			g.drawImage(image, (int)x + GamePanel.WIDTH, (int)y, null);
		}
		if(x > 0) {
			g.drawImage(image, (int)x - GamePanel.WIDTH, (int)y, null);
		}
		if(y < 0) {
			g.drawImage(image, (int)x, (int)y + GamePanel.HEIGHT, null);
		}
		if(y > 0) {
			g.drawImage(image, (int)x, (int)y - GamePanel.HEIGHT, null);
		}
	}
}







