package com.neet.GameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import com.neet.Handlers.Keys;
import com.neet.Main.GamePanel;

public class HelpState extends GameState{

	private Font font;
	
	public HelpState(GameStateManager gsm) {
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
		g.drawString("Moving: A W S D (You can double jump)", 60, 60);
		g.drawString("F: Knifing", 60, 80);
		g.drawString("E: Dashing (There is cooldown)", 60, 100);
		g.drawString("R: Attacking", 60, 120);
		g.drawString("Knife cant go through boss's bullet", 60, 140);
		g.drawString("Tips: Don't die", 60, 200);
	}
	
	public void handleInput() {
		
		if(Keys.isPressed(Keys.ESCAPE))
			gsm.setState(GameStateManager.MENUSTATE);
	}
}
