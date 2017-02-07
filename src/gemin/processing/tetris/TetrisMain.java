package gemin.processing.tetris;

import processing.core.PApplet;

public class TetrisMain extends PApplet implements IBlockDrawer{
	final static int[] BlockCount = {10,20};

	private GameController m_gameController;

	private long m_lastTime;
	private int m_eachSize;
	private int m_xoffset;
	long m_dropInterval = (long)(1000/1.5);

	public void settings(){
		size(800,800);
		m_gameController = new GameController(BlockCount[0], BlockCount[1]);

		m_eachSize = height/BlockCount[1];
		m_xoffset = (width - (BlockCount[0]*m_eachSize))/2;
	}

	private void render(){
		background(255);
		m_gameController.render(this);
	}

	public void draw(){
		if(!m_gameController.isPlaying()){
			background(0);
			return;
		}
		
		render();
		long now = System.currentTimeMillis();
		if( (now - m_lastTime) >= m_dropInterval){
			m_gameController.moveDown();
			m_lastTime += m_dropInterval;
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


	//TODO use event listener
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
		m_gameController.onCommand(cmd);
	}


	/** Main */
	public static void main(String[] args) {
		PApplet.main("gemin.processing.tetris.TetrisMain");
	}



}
