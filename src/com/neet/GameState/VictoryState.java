package com.neet.GameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import com.neet.Handlers.Content;
import com.neet.Handlers.Keys;
import com.neet.Main.GamePanel;

public class VictoryState extends GameState {

	private Font font;
	private BufferedImage victory;
	
	public VictoryState(GameStateManager gsm) {
		super(gsm);
		
		font = new Font("Century Gothic", Font.PLAIN, 20);
	}

	public void init() {}
	
	public void update() {
		
		handleInput();
	}
	
	public void draw(Graphics2D g) {
		
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
		g.setColor(Color.WHITE);
		g.setFont(font);
		g.drawString("YOU WIN!!!", 90, 90);
		try {
			BufferedImage Victory = ImageIO.read(getClass().getResourceAsStream("/Sprites/Other/victory.png"));
			victory = Victory.getSubimage(0, 0, 270, 75);
		}catch(Exception e) {
			e.printStackTrace();
		}
		g.drawImage(victory, 100, 80, null );

	}
	
	public void handleInput() {
		
		if(Keys.isPressed(Keys.ESCAPE))
			gsm.setState(GameStateManager.MENUSTATE);
	}
}
