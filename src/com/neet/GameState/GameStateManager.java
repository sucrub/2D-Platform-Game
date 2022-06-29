package com.neet.GameState;

import com.neet.Audio.Audio;
import com.neet.Main.GamePanel;

public class GameStateManager {
	
	private GameState[] gameStates;
	private int currentState;
	
	private PauseState pauseState;
	private boolean paused;
	
	public static final int NUMGAMESTATES = 10;
	public static final int MENUSTATE = 0;
	public static final int HELPSTATE = 1;
	public static final int CHOOSEDIFFICULTYSTATE = 2;
	public static final int LEVEL1STATE = 3;
	public static final int LEVEL2STATE = 4;
	public static final int LEVEL3STATE = 5;
	public static final int VICTORYSTATE = 6;
	public static final int LOSTSTATE = 7;
	
	public GameStateManager() {
		
		Audio.init();
		
		gameStates = new GameState[NUMGAMESTATES];
		
		pauseState = new PauseState(this);
		paused = false;
		
		currentState = MENUSTATE;
		loadState(currentState);
	}
	
	private void loadState(int state) {
		
		if(state == MENUSTATE)
			gameStates[state] = new MenuState(this);
		else if(state == HELPSTATE)
			gameStates[state] = new HelpState(this);
		else if(state == CHOOSEDIFFICULTYSTATE)
			gameStates[state] = new ChooseDifficultyState(this);
		else if(state == LEVEL1STATE)
			gameStates[state] = new Level1State(this);
		else if(state == LEVEL2STATE)
			gameStates[state] = new Level2State(this);
		else if(state == LEVEL3STATE)
			gameStates[state] = new Level3State(this);
		else if(state == VICTORYSTATE)
			gameStates[state] = new VictoryState(this);
		else if(state == LOSTSTATE)
			gameStates[state] = new LostState(this);
	}
	
	private void unloadState(int state) {
		
		gameStates[state] = null;
	}
	
	public void setState(int state) {
		
		unloadState(currentState);
		currentState = state;
		loadState(currentState);
	}
	
	public void setPaused(boolean b) { 
		
		paused = b; 
	}
	
	public void update() {
		
		if(paused) {
			pauseState.update();
			return;
		}
		
		if(gameStates[currentState] != null) gameStates[currentState].update();
	}
	
	public void draw(java.awt.Graphics2D g) {
		
		if(paused) {
			pauseState.draw(g);
			return;
		}
		
		if(gameStates[currentState] != null) gameStates[currentState].draw(g);
		
		else {
			
			g.setColor(java.awt.Color.BLACK);
			g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
		}
	}
	
}