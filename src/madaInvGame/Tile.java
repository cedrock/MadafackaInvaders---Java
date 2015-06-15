package madaInvGame;

import java.awt.Image;
import java.awt.Rectangle;

public class Tile {

	public int tileX, tileY, speedY, type;
	public Image tileImage;
	
	public Madafacka madafacka = StartingClass.madafacka;
	public Background bg = StartingClass.bg1;
	public Rectangle box;

	public Tile(int x, int y, int typeInt) {
		// TODO Auto-generated constructor stub
		tileX = x*40;
		tileY = y*40;
		type = typeInt;
		box = new Rectangle();
		
		switch (type){
		case 1:
			tileImage = StartingClass.tileOcean;
			break;
		case 2:
			tileImage = StartingClass.tileGround;
			break;
			
		default:
			type = 0;
			break;
		}
	}

	public void update(){
		speedY = bg.speedY*5;
		tileY += speedY;
		
		box.setBounds(tileX, tileY, 40, 40);
		
		if(box.intersects(Madafacka.hitZone) && type!=0){
			checkCollision(Madafacka.box);
		}
		for(int i =0; i<StartingClass.madafacka.bullets.size(); i++){
			if(box.intersects(StartingClass.madafacka.bullets.get(i).hitZone) && type!=0)
			{
				checkBulletCollision(StartingClass.madafacka.bullets.get(i));
			}
		}
	}
	
	public void checkCollision(Rectangle r){
		if(box.intersects(r)){
			StartingClass.state = StartingClass.GameState.Dead;
		}
	}
	
	public void checkBulletCollision(Bullet b){
		if(box.intersects(b.box)){
			b.visible=false;
		}
	}
}
