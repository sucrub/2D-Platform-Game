package com.neet.GameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import com.neet.Audio.Audio;
import com.neet.Entity.PlayerStatus;
import com.neet.Handlers.Keys;
import com.neet.Main.GamePanel;

public class ChooseDifficultyState extends GameState{

	private BufferedImage head;
	
	private int currentChoice = 0;
	private String[] options = {
		"Normal",
		"Hard"
	};
	
	public static boolean hard;
	
	private Font font, titlefont;
	
	public ChooseDifficultyState(GameStateManager gsm) {
		super(gsm);
		
		try {
			
			// load floating head
			head = ImageIO.read(getClass().getResourceAsStream("/HUD/lifes_icon.png")).getSubimage(0, 0, 16, 16);
			
			//font
			font = new Font("Arial", Font.PLAIN, 14);
			titlefont = new Font("Times New Roman", Font.PLAIN, 18);
			
			//load sounds fx
			Audio.load("/SFX/menuoption.mp3", "menuoption");
			Audio.load("/SFX/menuselect.mp3", "menuselect");
			Audio.load("/Music/menusong.mp3", "menusong");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void init() {}
	
	public void update() {
		
		// check keys
		handleInput();
		
	}
	
	public void draw(Graphics2D g) {
		
		// draw bg
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
		
		// draw the "choose difficulty"
		g.setFont(titlefont);
		g.setColor(Color.WHITE);
		g.drawString("Choose difficulty", 185, 95);
		
		// draw options
		g.setFont(font);
		g.setColor(Color.WHITE);
		g.drawString("Easy", 230, 145);
		g.drawString("Hard", 230, 165);
		
		// draw floating head
		if(currentChoice == 0) g.drawImage(head, 210, 132, null);
		else if(currentChoice == 1) g.drawImage(head, 210, 152, null);
	}
	
	public void select() {
		
		if(currentChoice == 0) {

			Audio.stop("menusong");
			Audio.play("menuselect");
			PlayerStatus.init();
			gsm.setState(GameStateManager.LEVEL1STATE);
			hard= false;
		}
		else if(currentChoice == 1) {
			Audio.stop("menusong");
			Audio.play("menuselect");
			PlayerStatus.init();
			gsm.setState(GameStateManager.LEVEL1STATE);
			hard=true;
		}
	}
	
	public static boolean hard() {
		
		return hard;
	}
	
	public void handleInput() {
		
		if(Keys.isPressed(Keys.ESCAPE))
			gsm.setState(GameStateManager.MENUSTATE);
		
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
