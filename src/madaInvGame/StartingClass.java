package madaInvGame;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import madaInvGame.framework.Animation;

public class StartingClass extends Applet implements Runnable, KeyListener {
	
	public static enum GameState{
		Running, Dead
	}
	
	public static GameState state = GameState.Running;
	
	public static Madafacka madafacka;
	public static int score = 0;
	public Font font = new Font(null, Font.BOLD, 30);
	public Image image, currentSprite, 
	ship, shipleft1, shipleft2, shipright1, shipright2, background, basicInv1, basicInv2,basicInv3, basicInv4;
	public static Image tileOcean, tileGround;
	public static Background bg1,bg2;
	public URL base;
	public Graphics second;
	public Animation badBoyAnim;
	public Animation badBoyAnim2;
	
	public ArrayList<Tile> tiles = new ArrayList<Tile>();
	public static ArrayList<Enemy> enemies = new ArrayList<Enemy>();

	@Override
	public void init() {
		setSize(480, 800);
		setBackground(Color.BLACK);
		addKeyListener(this);
		setFocusable(true);
		Frame frame = (Frame) this.getParent().getParent();
		frame.setTitle("MadafackaInvaders");
		try {
			base = getDocumentBase();
		} catch (Exception e) {
			// TODO: handle exception
		}

		// Image Setups
		ship = getImage(base, "../ressources/ship.png");
		shipleft1 = getImage(base, "../ressources/shipleft1.png");
		shipleft2 = getImage(base, "../ressources/shipleft2.png");
		shipright1 = getImage(base, "../ressources/shipright1.png");
		shipright2 = getImage(base, "../ressources/shipright2.png"); 
		background = getImage(base, "../ressources/background.png");
		basicInv1 = getImage(base, "../ressources/fuckers1.png");
		basicInv2 = getImage(base, "../ressources/fuckers2.png");
		basicInv3 = getImage(base, "../ressources/ship (14).png");
		basicInv4 = getImage(base, "../ressources/ship (15).png");
		tileOcean = getImage(base,"../ressources/redground.png");
		tileGround = getImage(base,"../ressources/purpleground.png");
		
		badBoyAnim = new Animation();
		badBoyAnim.addFrame(basicInv1, 800);
		badBoyAnim.addFrame(basicInv2, 800);

		badBoyAnim2 = new Animation();
		badBoyAnim2.addFrame(basicInv3, 800);
		badBoyAnim2.addFrame(basicInv4, 800);
		currentSprite = ship;
	}

	@Override
	public void start() {
		bg1 = new Background(0,0);
		bg2 = new Background (0,-2160);
		madafacka = new Madafacka();
		//Initialize tiles
		try{
			loadMap("../ressources/map1.txt");
		}catch(IOException e){
			e.printStackTrace();
		}
		
		Thread thread = new Thread(this);
		thread.start();
	}

	private void loadMap(String filename) throws IOException{
		// TODO Auto-generated method stub
		ArrayList lines = new ArrayList();
        int width = 0;
        int height = 0;

        BufferedReader reader = new BufferedReader(new FileReader(filename));
        while (true) {
            String line = reader.readLine();
            // no more lines to read
            if (line == null) {
                reader.close();
                break;
            }

            if (!line.startsWith("!")) {
                lines.add(line);
                height = Math.max(height, line.length());
            }
        }
        width = lines.size();

        for (int j = 0; j < width; j++) {
            String line = (String) lines.get(j);
            for (int i = 0; i < height; i++) {
                if (i < line.length()) {
                    char ch = line.charAt(i);
                    if(Character.getNumericValue(ch) == 5)
                    {
                    	BasicInvader enemy = new BasicInvader(i*40,-j*40,1);
                    	enemies.add(enemy);
                    }
                    else if(Character.getNumericValue(ch) == 4)
                    {
                    	BasicInvader enemy = new BasicInvader(i*40,-j*40,2);
                    	enemies.add(enemy);
                    }
                    else{
                    Tile t = new Tile(i,-j, Character.getNumericValue(ch));
                    tiles.add(t);
                    }
                }
            }
        }
	}

	@Override
	public void stop() {
	}

	@Override
	public void destroy() {
	}

	@Override
	public void run() {
		if(state == GameState.Running){
			while(true){
				madafacka.update();
				updateTiles();
				if(madafacka.shooting){
					//currentSprite = characterShoot;
					madafacka.shooting=false;
				}
				
				ArrayList<Bullet> bullets = madafacka.bullets;
				for(int i=0; i<bullets.size(); i++){
					Bullet b = (Bullet)bullets.get(i);
					if(b.visible){
						b.update();
					}
					else{
						bullets.remove(i);
					}
				}
				for(int i=0; i<enemies.size(); i++){
					((BasicInvader)enemies.get(i)).update();
				}
				bg1.update();
				bg2.update();
				animate();
				repaint();
				try {
					Thread.sleep(17);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
			}
		}
	}
	
	public void animate(){
		badBoyAnim.update(50);
		badBoyAnim2.update(50);
	}
	
	@Override 
	public void update(Graphics g){
		if(image==null){
			image = createImage(this.getWidth(), this.getHeight());
			second = image.getGraphics();
		}
		
		second.setColor(getBackground());
		second.fillRect(0, 0, getWidth(), getHeight());
		second.setColor(getForeground());
		paint(second);
		
		g.drawImage(image, 0, 0, this);
	}
	
	@Override
	public void paint(Graphics g){
		if(state == GameState.Running){
			g.drawImage(background, bg1.bgX, bg1.bgY, this);
			g.drawImage(background, bg2.bgX, bg2.bgY, this);
			paintTiles(g);
			
			ArrayList<Bullet> bullets = madafacka.bullets;
			for(int i=0; i<bullets.size(); i++){
				Bullet b = bullets.get(i);
				g.setColor(Color.YELLOW);
				g.fillRect(b.x, b.y, 5, 10);
			}
			g.drawImage(currentSprite, madafacka.centerX-25, madafacka.centerY-25,  this);
			for(int i=0; i<enemies.size(); i++){
				if(enemies.get(i).type==1){
				g.drawImage(badBoyAnim.getImage(), ((Enemy)enemies.get(i)).centerX-50, ((Enemy)enemies.get(i)).centerY-50, this);
				}
				else if(enemies.get(i).type==2){
				g.drawImage(badBoyAnim2.getImage(), ((Enemy)enemies.get(i)).centerX-50, ((Enemy)enemies.get(i)).centerY-50, this);
				}
			}
			g.setFont(font);
			g.setColor(Color.WHITE);
			g.drawString(Integer.toString(score), 740, 30);
		}else if (state == GameState.Dead) {
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, 800, 480);
			g.setColor(Color.WHITE);
			g.drawString("Dead", 360, 240);
		}
	}
	
	public void updateTiles(){
		for(int i=0; i<tiles.size(); i++){
			Tile t = (Tile)tiles.get(i);
			t.update();
		}
	}
	
	public void paintTiles(Graphics g){
		for(int i=0; i<tiles.size(); i++){
			Tile t = (Tile)tiles.get(i);
			g.drawImage(t.tileImage, t.tileX, t.tileY, this);
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		switch (e.getKeyCode()){
			case KeyEvent.VK_UP:
				break;
			
			case KeyEvent.VK_DOWN:
				currentSprite = ship;
				break;
			
			case KeyEvent.VK_RIGHT:
				currentSprite = shipright2;
				madafacka.moveRight();
				madafacka.movingRight=true;
				break;
			
			case KeyEvent.VK_LEFT:
				currentSprite = shipleft2;
				madafacka.moveLeft();
				madafacka.movingLeft=true;
				break;
				
			case KeyEvent.VK_SPACE:
				madafacka.shoot();
				madafacka.readyToFire = false;
				break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		switch (e.getKeyCode()){
		case KeyEvent.VK_UP:
			break;
		
		case KeyEvent.VK_DOWN:
			break;
		
		case KeyEvent.VK_RIGHT:
			currentSprite = ship;
			madafacka.stopRight();
			break;
		
		case KeyEvent.VK_LEFT:
			currentSprite = ship;
			madafacka.stopLeft();
			break;
			
		case KeyEvent.VK_SPACE:
			madafacka.readyToFire=true;
			break;
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
