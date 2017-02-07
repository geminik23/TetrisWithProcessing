package gemin.processing.tetris;

import processing.core.PApplet;

public class TetrisMain extends PApplet implements IBlockDrawer{
	final static int[] BlockCount = {10,20};
	final static long DropInterval = (long)(1000/1.5);
	
	private GameController m_gameController;
	
	private long m_lastTime;
	private int m_eachSize;
	private int m_xoffset;
	private boolean m_redrawRequest = false;
	
	
	public void settings(){
		size(800,800);
		
		m_gameController = new GameController(BlockCount[0], BlockCount[1]);
		
		m_eachSize = height/BlockCount[1];
		m_xoffset = (width - (BlockCount[0]*m_eachSize))/2;
		
		

	}
	
	public void setup(){
//		frameRate(10);		
	}
	
	private void render(){
		background(255);
		m_gameController.render(this);

		m_redrawRequest = false;
	}
	public void draw(){
		if(m_redrawRequest){render();return;}
		
		long now = System.currentTimeMillis();
		
		if(m_gameController.isPlaying() && (now - m_lastTime) >= DropInterval){
			render();
			m_gameController.moveDown();
			m_lastTime += DropInterval;
		}
	}
	
	@Override
	public void drawBlock(int posx, int posy, int color) {
		this.fill(color,255);
		this.stroke(0);
		this.strokeWeight(1.f);
		this.rect(m_xoffset + (BlockCount[0] - posx)*m_eachSize, 
				posy*m_eachSize, 
				m_eachSize,
				m_eachSize);
	}
	
	
	public void keyPressed(){
		Commands cmd = Commands.Nothing;
		switch(keyCode){
		case 81: // 'q'
			cmd = Commands.End;
			break;
		case 32: // space
			if(m_gameController.isPlaying()) cmd = Commands.Drop;
			break;
		case 10: // enter
			if(!m_gameController.isPlaying()){ 
				cmd = Commands.Start;
				m_lastTime = System.currentTimeMillis();
			}
			break;
		case 39: //right
			if(m_gameController.isPlaying()) cmd = Commands.MoveRight;
			break;
		case 37: //left
			if(m_gameController.isPlaying()) cmd = Commands.MoveLeft;
			break;
		case 38: //up
			if(m_gameController.isPlaying()) cmd = Commands.Rotate;
			break;
		case 40: //down
			if(m_gameController.isPlaying()) cmd = Commands.MoveDown;
			break;
		}		
		m_redrawRequest = (m_gameController.onCommand(cmd));
	}
	
	
	/** Main */
	public static void main(String[] args) {
		PApplet.main("gemin.processing.tetris.TetrisMain");
	}



}
