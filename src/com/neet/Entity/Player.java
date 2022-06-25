package com.neet.Entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import com.neet.Audio.JukeBox;
import com.neet.TileMap.TileMap;
import com.neet.Entity.Knife;

public class Player extends MapObject {

	// references
	private ArrayList<Enemy> enemies;

	// player stuff
	private int lives;
	private int health;
	private int maxHealth;
	private int damage;
	private int dashDamage;
	private int mp;
	private int maxMp;

	private boolean knockback;
	private boolean flinching;
	private long flinchCount;
	private int score;
	private boolean doubleJump;
	private boolean alreadyDoubleJump;
	private double doubleJumpStart;
	private ArrayList<EnergyParticle> energyParticles;
	private long time;



	// fireball

	// knife

	private boolean flyingKnife;
	private int knifeCost;
	private int flyingKnifeDamage;
	private ArrayList<Knife> knifes;

	// actions
	private boolean dashing;
	private boolean attacking;

	private boolean teleporting;

	// animations
	private ArrayList<BufferedImage[]> sprites;
	private final int[] NUMFRAMES = {
			8, 6, 6, 3, 4, 4, 3, 3, 3, 3, 2, 4, 4, 4
	};
	private final int[] FRAMEWIDTHS = {
			16, 16, 16, 16, 48, 16, 16, 16, 16, 16, 16, 16, 16, 16
	};
	private final int[] FRAMEHEIGHTS = {
			16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16
	};
	private final int[] SPRITEDELAYS = {
			3, 3, 2, 1, 2, 4, 2, 3, 2, 4, 2, 2, 2, 2
	};

	private Rectangle ar;
	private Rectangle cr;

	// animation actions
	private static final int DEAD = 0;
	private static final int RUNNING = 1;
	private static final int FLYING_KNIFE = 2;
	private static final int DASHING = 3;
	private static final int ATTACKING = 4;
	private static final int IDLE = 5;
	private static final int FALLING = 6;
	private static final int JUMPING = 7;
	private static final int KNOCKBACK = 8;
	// private static final int DOUBLE_JUMP = 9;
	private static final int TELEPORTING = 11;

	// emotes
	private BufferedImage confused;
	private BufferedImage surprised;
	public static final int NONE = 0;
	public static final int CONFUSED = 1;
	public static final int SURPRISED = 2;
	private int emote = NONE;



	public Player(TileMap tm) {

		super(tm);

		ar = new Rectangle(0, 0, 0, 0);
		ar.width = 30;
		ar.height = 20;
		cr = new Rectangle(0, 0, 0, 0);
		cr.width = 50;
		cr.height = 40;

		width = 30;
		height = 30;
		cwidth = 16;
		cheight = 16;

		moveSpeed = 3;
		maxSpeed = 3;
		stopSpeed = 1.6;
		fallSpeed = 0.15;
		maxFallSpeed = 4.0;
		jumpStart = -5;
		stopJumpSpeed = 0.3;
		doubleJumpStart = -3;

		damage = 1;
		dashDamage = 1;

		mp = maxMp = 2000;

		knifeCost = 200;
		flyingKnifeDamage = 2;
		knifes = new ArrayList<Knife>();

		facingRight = true;

		// load sprites
		try {

			BufferedImage spritesheet = ImageIO.read(
					getClass().getResourceAsStream("/Sprites/Player/sprite_sheet.png"));

			int count = 0;
			sprites = new ArrayList<BufferedImage[]>();
			for (int i = 0; i < NUMFRAMES.length; i++) {

				BufferedImage[] bi = new BufferedImage[NUMFRAMES[i]];
				for (int j = 0; j < NUMFRAMES[i]; j++) {

					bi[j] = spritesheet.getSubimage(
							j * FRAMEWIDTHS[i],
							count,
							FRAMEWIDTHS[i],
							FRAMEHEIGHTS[i]);
				}
				sprites.add(bi);
				count += FRAMEHEIGHTS[i];
			}

			// emotes
			spritesheet = ImageIO.read(getClass().getResourceAsStream(
					"/HUD/Emotes.gif"));
			surprised = spritesheet.getSubimage(
					14, 0, 14, 17);
			confused = spritesheet.getSubimage(
					0, 0, 14, 17);
		} catch (Exception e) {
			e.printStackTrace();
		}

		energyParticles = new ArrayList<EnergyParticle>();

		setAnimation(IDLE);

		JukeBox.load("/SFX/playerjump.mp3", "playerjump");
		JukeBox.load("/SFX/playerlands.mp3", "playerlands");
		JukeBox.load("/SFX/playerattack.mp3", "playerattack");
		JukeBox.load("/SFX/playerhit.mp3", "playerhit");
		JukeBox.load("/SFX/playercharge.mp3", "playercharge");

	}

	public void init(
			ArrayList<Enemy> enemies,
			ArrayList<EnergyParticle> energyParticles) {
		this.enemies = enemies;
		this.energyParticles = energyParticles;
	}

	public int getHealth() {
		return health;
	}

	public int getMaxHealth() {
		return maxHealth;
	}

	public int getMp() {
		return mp;
	}

	public int getMaxMp() {
		return maxMp;
	}

	public void setEmote(int i) {
		emote = i;
	}

	public void setTeleporting(boolean b) {
		teleporting = b;
	}

	public void setJumping(boolean b) {
		if (knockback)
			return;
		if (b && !jumping && falling && !alreadyDoubleJump) {
			doubleJump = true;
			// jumping = false;
		} // else
		jumping = b;

	}

	public void setAttacking() {
		
		if (knockback)
			return;

		else
			attacking = true;
	}

	public void setFlyingKnife() {
		
		if (knockback)
			return;
		else
			flyingKnife = true;
	}

	public void setDashing() {
		
		if (knockback)
			return;
		
		if (!attacking && !dashing) {
			dashing = true;
			JukeBox.play("playercharge");
			// chargingTick = 0;
		}
	}

	public boolean isDashing() {
		return dashing;
	}

	public void setDead() {
		health = 0;
		stop();
	}

	public String getTimeToString() {
		
		int minutes = (int) (time / 3600);
		int seconds = (int) ((time % 3600) / 60);
		return seconds < 10 ? minutes + ":0" + seconds : minutes + ":" + seconds;
	}

	public long getTime() {
		
		return time;
	}

	public void setTime(long t) {
		
		time = t;
	}

	public void setHealth(int i) {
		
		health = i;
	}

	public void setmaxHealth(int i) {
		
		maxHealth = i;
	}

	public void setLives(int i) {
		
		lives = i;
	}
	
	public void setmaxMp(int i) {
		
		maxMp = i;
	}
	
	public void setMp(int i) {
		
		mp = i;
	}

	public void loseLife() {
		
		lives--;
	}

	public int getLives() {
		
		return lives;
	}

	public void increaseScore(int score) {
		
		this.score += score;
	}

	public int getScore() {
		
		return score;
	}

	public void hit(int damage) {
		
		if (flinching)
			return;
		JukeBox.play("playerhit");
		stop();
		health -= damage;
		if (health < 0)
			health = 0;
		flinching = true;
		flinchCount = 0;
		if (facingRight)
			dx = -1;
		else
			dx = 1;
		dy = -3;
		knockback = true;
		falling = true;
		jumping = false;
	}

	public void reset() {
		
		health = maxHealth;
		facingRight = true;
		currentAction = -1;
		stop();
	}

	public void stop() {
		
		left = right = up = down = flinching = dashing = jumping = attacking = false;
	}

	private void getNextPosition() {

		if (knockback) {
			dy += fallSpeed * 2;
			if (!falling)
				knockback = false;
			return;
		}

		// movement

		if (left) {
			dx -= moveSpeed;
			if (dx < -maxSpeed) {
				dx = -maxSpeed;
			}
		} else if (right) {
			dx += moveSpeed;
			if (dx > maxSpeed) {
				dx = maxSpeed;
			}
		} else {
			if (dx > 0) {
				dx -= stopSpeed;
				if (dx < 0) {
					dx = 0;
				}
			} else if (dx < 0) {
				dx += stopSpeed;
				if (dx > 0) {
					dx = 0;
				}
			}
		}

		// cannot move while attacking
		if ((attacking) &&
				!(jumping || falling)) {
			dx = 0;
		}
		// dashing
		if (dashing) {
			dy = 0;
			left = right = false;
			if (facingRight)
				dx = moveSpeed * 3;
			else
				dx = -moveSpeed * 3;
		}

		// jumping
		if (jumping && !falling) {
			// sfx.get("jump").play();
			dy = jumpStart;
			falling = true;
			JukeBox.play("playerjump");
		}

		if (doubleJump) {
			dy = doubleJumpStart;
			// alreadyDoubleJump = true;
			doubleJump = false;

			JukeBox.play("playerjump");
			for (int i = 0; i < 6; i++) {
				energyParticles.add(
						new EnergyParticle(
								tileMap,
								x,
								y + cheight / 4,
								EnergyParticle.UP));
			}
		}

		if (!falling)
			alreadyDoubleJump = false;

		// falling
		if (falling) {
			dy += fallSpeed;
			if (dy < 0 && !jumping)
				dy += stopJumpSpeed;
			if (dy > maxFallSpeed)
				dy = maxFallSpeed;
		}

	}

	private void setAnimation(int i) {
		
		currentAction = i;
		animation.setFrames(sprites.get(currentAction));
		animation.setDelay(SPRITEDELAYS[currentAction]);
		width = FRAMEWIDTHS[currentAction];
		height = FRAMEHEIGHTS[currentAction];
	}

	public void update() {

		time++;
//		Number time delay
//		if(delayTime==0) {delayTime++;}
//		else if (delayTime==300) {delayTime=0;}

		// check teleporting
		if (teleporting) {
			energyParticles.add(
					new EnergyParticle(tileMap, x, y, EnergyParticle.UP));
		}

		// update position
		boolean isFalling = falling;
		getNextPosition();
		checkTileMapCollision();
		setPosition(xtemp, ytemp);
		if (isFalling && !falling) {
			JukeBox.play("playerlands");
		}
		if (dx == 0)
			x = (int) x;

		// check done flinching
		if (flinching) {
			flinchCount++;
			if (flinchCount > 120) {
				flinching = false;
			}
		}

		// energy particles
		for (int i = 0; i < energyParticles.size(); i++) {
			energyParticles.get(i).update();
			if (energyParticles.get(i).shouldRemove()) {
				energyParticles.remove(i);
				i--;
			}
		}

		// check attack finished
		if (currentAction == ATTACKING) {
			if (animation.hasPlayedOnce()) {
				attacking = false;

			}
		}
		if (currentAction == FLYING_KNIFE) {
			if (animation.hasPlayedOnce())
				flyingKnife = false;
		}

		// fireball attack
		mp += 1; //increase mp
		if (mp > maxMp)
			mp = maxMp;
		if (flyingKnife && currentAction != FLYING_KNIFE) {
			if (mp > knifeCost) {
				mp -= knifeCost; //decrease mp
				Knife kn = new Knife(tileMap, facingRight);
				kn.setPosition(x, y);
				knifes.add(kn);
			}
		}

		// update fireballs
		for (int i = 0; i < knifes.size(); i++) {
			knifes.get(i).update();
			if (knifes.get(i).shouldRemove()) {
				knifes.remove(i);
				i--;
			}
		}

		// check dashing finish
		if (currentAction == DASHING) {

			if (animation.hasPlayed(4)) {
				dashing = false;

			}
			cr.y = (int) y - 20;
			if (facingRight)
				cr.x = (int) x - 15;
			else
				cr.x = (int) x - 35;
		}

		// check enemy interaction
		for (int i = 0; i < enemies.size(); i++) {

			Enemy e = enemies.get(i);

			// check attack
			if (currentAction == ATTACKING &&
					animation.getFrame() == 3 && animation.getCount() == 0) {
				if (e.intersects(ar)) {
					e.hit(damage);
				}
			}

			if (currentAction == DASHING) {

				if (animation.getCount() == 0) {
					if (e.intersects(cr)) {
						e.hit(dashDamage);
					}
				}
			}

			// flying knife
			for (int j = 0; j < knifes.size(); j++) {
				if (knifes.get(j).intersects(e)) {
					e.hit(flyingKnifeDamage);
					knifes.get(j).setHit();
					break;
				}
			}
			// collision with enemy
			if (!e.isDead() && intersects(e) && !dashing) {
				hit(e.getDamage());
			}

			if (e.isDead()) {
				JukeBox.play("explode", 2000);
			}

		}

		// set animation, ordered by priority
		if (teleporting) {
			if (currentAction != TELEPORTING) {
				setAnimation(TELEPORTING);
			}
		} else if (knockback) {
			if (currentAction != KNOCKBACK) {
				setAnimation(KNOCKBACK);
			}
		} else if (health == 0) {
			if (currentAction != DEAD) {
				setAnimation(DEAD);
			}
		} else if (attacking) {
			if (currentAction != ATTACKING) {
				JukeBox.play("playerattack");

				setAnimation(ATTACKING);
				ar.y = (int) y - 2;
				if (facingRight)
					ar.x = (int) x + 10;
				else
					ar.x = (int) x - 40;
			} else {
				if (animation.getFrame() == 4 && animation.getCount() == 0) {
					for (int c = 0; c < 3; c++) {
						if (facingRight)
							energyParticles.add(
									new EnergyParticle(
											tileMap,
											ar.x + ar.width - 4,
											ar.y + ar.height / 2,
											EnergyParticle.RIGHT));
						else
							energyParticles.add(
									new EnergyParticle(
											tileMap,
											ar.x + 4,
											ar.y + ar.height / 2,
											EnergyParticle.LEFT));
					}
				}
			}
		} else if (flyingKnife) {
			if (currentAction != FLYING_KNIFE) {
				setAnimation(FLYING_KNIFE);
			}
		} else if (dashing) {
			if (currentAction != DASHING) {
				setAnimation(DASHING);
			}

		} else if (dy < 0) {

			if (currentAction != JUMPING) {
				setAnimation(JUMPING);
			}
		} else if (dy > 0) {
			if (currentAction != FALLING) {
				setAnimation(FALLING);
			}
		} else if (left || right) {
			if (currentAction != RUNNING) {
				setAnimation(RUNNING);
			}
		} else if (currentAction != IDLE) {
			setAnimation(IDLE);
		}

		animation.update();

		// set direction
		if (!attacking && !knockback && currentAction != FLYING_KNIFE) {
			if (right)
				facingRight = true;
			if (left)
				facingRight = false;
		}

	}

	public void draw(Graphics2D g) {
		// draw knife
		for (int i = 0; i < knifes.size(); i++) {
			knifes.get(i).draw(g);
		}
		// draw emote
		if (emote == CONFUSED) {
			g.drawImage(confused, (int) (x + xmap - cwidth / 2), (int) (y + ymap - 40), null);
		} else if (emote == SURPRISED) {
			g.drawImage(surprised, (int) (x + xmap - cwidth / 2), (int) (y + ymap - 40), null);
		}

		// draw energy particles
		for (int i = 0; i < energyParticles.size(); i++) {
			energyParticles.get(i).draw(g);
		}

		// flinch
		if (flinching && !knockback) {
			if (flinchCount % 10 < 5)
				return;
		}

		super.draw(g);

	}

}