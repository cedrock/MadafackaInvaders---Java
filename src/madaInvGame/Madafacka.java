package madaInvGame;

import java.awt.Rectangle;
import java.util.ArrayList;

public class Madafacka {
	// In Java, Class Variables should be private so that only its methods can
	// change them.
	final int moveSpeed = -5;
	
	public int centerX = 240;
	public int centerY = 650;
	public boolean movingLeft=false;
	public boolean movingRight = false;
	public boolean shooting=false;
	public boolean readyToFire = true;
	public ArrayList<Bullet> bullets = new ArrayList<Bullet>();

	public static Background bg1 = StartingClass.bg1;
	public static Background bg2 = StartingClass.bg2;

	public int speedX = 0;
	public int speedY = moveSpeed;
	public static Rectangle box = new Rectangle(0,0,0,0);
	public static Rectangle hitZone = new Rectangle(0,0,0,0);
	
	public void update() {

		// Moves Character or Scrolls Background accordingly.
		if (speedX != 0) {
			centerX += speedX;
		} 
		if (speedY == 0 || speedY<0) {
			bg1.speedY = 0;
			bg2.speedY=0;

		} 

		if (speedY<0){
			bg1.speedY = -moveSpeed/5;
			bg2.speedY = -moveSpeed/5;
		}
		
		box.setRect(centerX-15, centerY-15, 30, 30);
		hitZone.setRect(centerX-70, centerY-70, 140, 140);
		
	}

	public void moveRight() {
		speedX = -moveSpeed;
	}

	public void moveLeft() {
		speedX = moveSpeed;
	}

	public void stopRight() {
		movingRight = false;
		stop();
	}
	
	public void stopLeft() {
		movingLeft = false;
		stop();
	}

	public void stop(){
		if(!movingRight && !movingLeft){
			speedX=0;
		}
		else if (movingRight && !movingLeft){
			moveRight();
		}
		else if (!movingRight && movingLeft){
			moveLeft();
		}
	}
	
	
	public void shoot(){
		if(readyToFire){
			shooting=true;
			Bullet b = new Bullet(centerX, centerY-25);
			bullets.add(b);
		}
	}
}
