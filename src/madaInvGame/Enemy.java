package madaInvGame;

import java.awt.Rectangle;

public class Enemy {

	public int power, speedY, centerX, centerY, sizeX, sizeY;
	public Background bg = StartingClass.bg1;
	public Rectangle box = new Rectangle(0,0,0,0);
	public int health;
	public boolean dead=false;
	public int type;
	
	public Enemy(){
		sizeX=50;
		sizeY=50;
		health=2;
	}
	
	public void update(){
		if(type==1){
		this.speedY = bg.speedY*4;}
		else if(type==2){
			this.speedY = bg.speedY*7;}
		centerY += speedY;
		
		box.setBounds(centerX-sizeX/2-5, centerY-sizeY/2-2, sizeX-5, sizeY-5);
		if(box.intersects(Madafacka.hitZone)){
			checkCollision();
		}
	}
	
	public void checkCollision(){
		if(box.intersects(Madafacka.box)){
			StartingClass.state = StartingClass.GameState.Dead;
		}
	}
	
	public void die(){
		
	}
	
	public void attack(){
		
	}
}
