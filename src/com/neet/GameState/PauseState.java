package com.neet.GameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import com.neet.Audio.Audio;
import com.neet.Handlers.Keys;
import com.neet.Main.GamePanel;

public class PauseState extends GameState {
	
	private static int NumState;
	private BufferedImage head;
	
	private int currentChoice = 0;
	private String[] options = {
		"Continue",
		"BackMenu"
	};
	
	private Font font;
	private Font font2;
	
	public static void setNumState(int n) {NumState = n;}
	
	public PauseState(GameStateManager gsm) {
		
		super(gsm);

		try {
			
			// load floating head
			head = ImageIO.read(getClass().getResourceAsStream("/HUD/lifes_icon.png")).getSubimage(0, 0, 16, 16);
			
			// titles and fonts
			font = new Font("Times New Roman", Font.BOLD, 14);
			font2 = new Font("Times New Roman", Font.BOLD, 10);
			
			// load sound fx
			Audio.load("/SFX/menuoption.mp3", "menuoption");
			Audio.load("/SFX/menuselect.mp3", "menuselect");
			
			// load menu sound
			Audio.load("/Music/menusong.mp3", "menusong");
			Audio.loop("menusong", 600, Audio.getFrames("menusong") - 2200);
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}

		
	}
	
	
	public void init() {}
	
	public void update() {
		
		handleInput();
	}
	
public void draw(Graphics2D g) {
		
		// draw bg
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
		
		// draw menu options
		g.setFont(font);
		g.setColor(Color.WHITE);
		g.drawString("Continue", 230, 135);
		g.drawString("Back to Menu", 230, 155);
		
		// draw floating head
		if(currentChoice == 0) g.drawImage(head, 210, 122, null);
		else if(currentChoice == 1) g.drawImage(head, 210, 142, null);
		
		// other
		g.setFont(font2);
		

		//drawTile
		g.setColor(Color.WHITE);
		g.setFont(font);
		g.drawString("Game Paused....", 190, 110);
	}
private void select() {
		
		if(currentChoice == 0) {
			Audio.play("menuselect");
			gsm.setPaused(false);
			switch(NumState) {
			case 1:
				Audio.load("/Music/level1.mp3", "level1");
				Audio.loop("level1", 600, Audio.getFrames("level1") - 2200);
				break;
			case 2:
				Audio.load("/Music/level2.mp3", "level2");
				Audio.loop("level1", 600, Audio.getFrames("level2") - 2200);
				break;
			case 3:
				Audio.load("/Music/level3.mp3", "level3");
				Audio.loop("level1", 600, Audio.getFrames("level3") - 2200);
				break;
			}
		}		
		else if(currentChoice == 1) {
			Audio.play("menuselect");
			Audio.stop(null);
			gsm.setPaused(false);
			gsm.setState(GameStateManager.MENUSTATE);
			
		}
	}
	
	public void handleInput() {
		
		if(Keys.isPressed(Keys.ENTER)) select();
		if(Keys.isPressed(Keys.UP)) {
			if(currentChoice > 0) {
				Audio.play("menuoption", 0);
				currentChoice--;
			}
		}
		if(Keys.isPressed(Keys.DOWN)) {
			if(currentChoice < options.length - 1) {
				Audio.play("menuoption", 0);
				currentChoice++;
			}
		}
	}

}
