package com.neet.GameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import com.neet.Audio.Audio;
import com.neet.Handlers.Keys;
import com.neet.Main.GamePanel;

public class PauseState extends GameState {
	
	private int NumState;
	
	private Font font;
	
	public PauseState(GameStateManager gsm) {
		
		super(gsm);
		
		// fonts
		font = new Font("Times New Roman", Font.BOLD, 14);
		
	}
	
	public void getNumState(int n) {NumState=n;}
	
	public void init() {}
	
	public void update() {
		
		handleInput();
	}
	
	public void draw(Graphics2D g) {
		
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
		g.setColor(Color.WHITE);
		g.setFont(font);
		g.drawString("Game Paused....", 190, 110);
		g.drawString("Press Escape to return", 190, 130);
		g.drawString("Press W to return to Menu", 190, 150);
	}
	
	public void handleInput() {
		
		if(Keys.isPressed(Keys.ESCAPE)) gsm.setPaused(false);
		if(Keys.isPressed(Keys.UP)) {
			Audio.stop("level1");
			Audio.stop("level2");
			Audio.stop("level3");
			gsm.setPaused(false);
			gsm.setState(GameStateManager.MENUSTATE);
		}
	}

}
