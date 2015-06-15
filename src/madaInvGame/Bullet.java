package madaInvGame;

import java.awt.Rectangle;

public class Bullet {
	
	public int x, y, speedY;
	public boolean visible;
	public Rectangle box = new Rectangle(0,0,0,0);
	public Rectangle hitZone = new Rectangle(0,0,0,0);

	public Bullet(int startX, int startY) {
		x= startX;
		y = startY;
		speedY=-7;
		visible=true;
	}

	public void update(){
		y += speedY;
		box.setBounds(x,y,5,10);
		hitZone.setBounds(x, y, 30, 30);
		if(y>800){
			visible =false;
			box=null;
		}
		if(y<800){
			checkCollision();
		}
	}
	
	private void checkCollision() {
		for(int i=0; i<StartingClass.enemies.size(); i++){
			BasicInvader enemy = (BasicInvader)StartingClass.enemies.get(i);
			if(box.intersects(enemy.box)){
				visible=false;
				
				if(enemy.health > 0){
					enemy.health -= 1;
				}
				
				if(enemy.health<=0){
					StartingClass.score += 5;
					enemy.dead=true;
					StartingClass.enemies.remove(i);
				}
			}
		}
	}
}
