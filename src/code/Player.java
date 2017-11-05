package code;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

public class Player extends Rectangle {
	private String data;

	private static final long serialVersionUID = 1L;

	public boolean rigth, left, up, down;
	public int speed = 4;

	public Player(int x, int y) {
		setBounds(x, y, 32, 32);
	}

	public void tick() {
		 
		if (rigth && canMove(x+speed,y)) {
			x += speed;
		}
		if (left && canMove(x-speed,y)) {
			x -= speed;
		}
		if (up && canMove(x, y-speed)) {
			y -= speed;
		}
		if (down && canMove(x, y+speed)) {
			y += speed;
		}
		
		Level level = Game.level;
		for(int i = 0;i < level.apples.size();i++){
			
			if(this.intersects(level.apples.get(i))){
				level.apples.remove(i);
				break;
			}
		}
		if(level.apples.size() == 0){
			 messageWon();
			 playOrQuit();
		}
		
		for(int i = 0; i < Game.level.enemies.size();i++) {
			Enemy en = Game.level.enemies.get(i);
			if(en.intersects(this)) {
				messageLose();
				playOrQuit();
			}
		}
	}
	
	public void messageWon() {
		JOptionPane.showMessageDialog(null,"You won!!!",null,JOptionPane.PLAIN_MESSAGE);
	}
	
	public void messageLose() {
		JOptionPane.showMessageDialog(null,"You Lose!!!",null,JOptionPane.PLAIN_MESSAGE);
	}
	
	public void playOrQuit(){
		while(true){
			data = JOptionPane.showInputDialog(null,"[P]lay again or [Q]uit???",null,JOptionPane.QUESTION_MESSAGE);
			if(data.equalsIgnoreCase("p")){
				Game.player = new Player(0, 0);
				Game.level = new Level("/map/map_pacman3.png");
				return;
			}
			else if(data.equalsIgnoreCase("q")){
				 System.exit(1);
			}
			else{
				JOptionPane.showMessageDialog(null,"Please press [P] or [Q]","-Error Message-",JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	private boolean canMove(int nextX,int nextY){
		Rectangle bounds = new Rectangle(nextX,nextY,width,height);
		Level level = Game.level;
		
		for(int xx = 0;xx < level.tiles.length;xx++){
			for(int yy = 0;yy < level.tiles[0].length;yy++){
				if(level.tiles[xx][yy] != null){
					if(bounds.intersects(level.tiles[xx][yy])){
						return false;
					}
				}
			}
		}
		return true;
	}

	public void render(Graphics g) {
		BotSheet sheet = Game.botSheet;
		g.drawImage(sheet.getBot(0, 0), x, y, 32, 32, null);
	}
}
