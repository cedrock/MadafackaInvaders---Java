package madaInvGame;

public class BasicInvader extends Enemy {

	public BasicInvader(int centX, int centY, int typ) {
		this.centerX=centX;
		this.centerY=centY;
		this.sizeX=50;
		this.sizeY=50;
		this.type=typ;
		
		if(type==1){
			this.health=3;}
		else if(type==2){
			this.health=1;
		}
	}

}
