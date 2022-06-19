package com.neet.Handlers;

import java.awt.event.KeyEvent;
import java.security.Key;

// this class contains a boolean array of current and previous key states
// for the 10 keys that are used for this game.
// a key k is down when keyState[k] is true.

public class Keys {

	public static final int NUM_KEYS = 16;

	public static boolean keyState[] = new boolean[NUM_KEYS];
	public static boolean prevKeyState[] = new boolean[NUM_KEYS];

	public static int UP = 0;
	public static int LEFT = 1;
	public static int DOWN = 2;
	public static int RIGHT = 3;
	public static int BUTTON_E = 4;
	public static int BUTTON_R = 5;
	public static int BUTTON_F = 6;
	public static int BUTTON_G = 7;
	public static int ENTER = 8;
	public static int ESCAPE = 9;

	public static void keySet(int i, boolean b) {
		if (i == KeyEvent.VK_UP || i == KeyEvent.VK_W)
			keyState[UP] = b;
		else if (i == KeyEvent.VK_LEFT || i == KeyEvent.VK_A)
			keyState[LEFT] = b;
		else if (i == KeyEvent.VK_DOWN || i == KeyEvent.VK_S)
			keyState[DOWN] = b;
		else if (i == KeyEvent.VK_RIGHT || i == KeyEvent.VK_D)
			keyState[RIGHT] = b;
		else if (i == KeyEvent.VK_E)
			keyState[BUTTON_E] = b;

		else if (i == KeyEvent.VK_R)
			keyState[BUTTON_R] = b;
		else if (i == KeyEvent.VK_F)
			keyState[BUTTON_F] = b;
		else if (i == KeyEvent.VK_ENTER)
			keyState[ENTER] = b;
		else if (i == KeyEvent.VK_ESCAPE)
			keyState[ESCAPE] = b;
	}

	public static void update() {
		for (int i = 0; i < NUM_KEYS; i++) {
			prevKeyState[i] = keyState[i];
		}
	}

	public static boolean isPressed(int i) {

		return keyState[i] && !prevKeyState[i];
	}

	public static boolean anyKeyPress() {
		for (int i = 0; i < NUM_KEYS; i++) {
			if (keyState[i])
				return true;
		}
		return false;
	}

}
