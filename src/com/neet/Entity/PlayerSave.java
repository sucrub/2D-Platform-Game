package com.neet.Entity;

public class PlayerSave {

	private static int lives = 3;
	private static int health = 2;
	private static int maxHealth = 6;
	private static long time = 0;
	private static int mp = 2000;
	private static int maxMp = 2000;

	public static void init() {
		
		lives = 3;
		mp = maxMp;
		health = maxHealth;
		time = 0;
	}

	public static int getLives() {
		return lives;
	}

	public static void setLives(int i) {
		lives = i;
	}

	public static int getHealth() {
		return health;
	}

	public static void setHealth(int i) {
		health = i;
	}

	public static int getmaxHealth() {
		return maxHealth;
	}

	public static void setmaxHealth(int i) {
		maxHealth = i;
	}

	public static int getMp() {
		return mp;
	}

	public static void setMp(int i) {
		mp = i;
	}

	public static int getMaxMp() {
		return maxMp;
	}

	public static void setMaxMp(int i) {
		maxMp = i;
	}

	public static long getTime() {
		return time;
	}

	public static void setTime(long t) {
		time = t;
	}

}
