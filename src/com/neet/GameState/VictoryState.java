package com.neet.GameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import com.neet.Handlers.Keys;
import com.neet.Main.GamePanel;

public class VictoryState extends GameState {

	private Font font;
	
	public VictoryState(GameStateManager gsm) {
		super(gsm);
		
		font = new Font("Century Gothic", Font.PLAIN, 14);
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
		g.drawString("VICTORY", 90, 90);
	}
	
	public void handleInput() {
		
		if(Keys.isPressed(Keys.ESCAPE))
			gsm.setState(GameStateManager.MENUSTATE);
	}
}
