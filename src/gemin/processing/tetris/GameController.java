package gemin.processing.tetris;

public class GameController implements IDrawElement {
	enum GameState{ Stopped, Playing }

	private GridBoard m_grid;
	private Shape m_shape;
	private GameState m_gameState = GameState.Stopped;

	public GameController(int width, int height){
		/* initialize */
		m_grid = new GridBoard();
		m_grid.Initialize(width, height);

		m_shape = null;
	}


	private void reset(){
		m_grid.reset();
		m_shape = null;
	}

	private boolean newShape(){
		m_shape = new Shape(m_grid.getWidth()/2);
		return !m_grid.isBlocked(m_shape.getCoords(), m_shape.getPosX(), m_shape.getPosY());
	}

	// check is playing
	public boolean isPlaying(){ return m_gameState == GameState.Playing; }


	/** Game States */
	public void start(){
		this.reset();
		this.newShape();
		this.m_gameState = GameState.Playing;
	}

	public void stop(){
		this.m_gameState = GameState.Stopped;
	}    

	/** Interface(IDrawElement) implements */
	@Override
	public void render(IBlockDrawer drawer){
		m_grid.render(drawer);
		m_shape.render(drawer);

	}    


	/** shape freeze on grid */
	private void shapeToGrid(){
		// move to grid 
		m_grid.freezeShape(m_shape.getCoords(), m_shape.getPos(), m_shape.getColor());
		if(!this.newShape()) this.stop(); // if can not create shape then end game.
	}

	public void moveDown(){
		if(m_grid.isBlocked(m_shape.getCoords(), m_shape.getPosX(), m_shape.getPosY()+1)){
			this.shapeToGrid(); 
		}else 
			m_shape.moveDown();
	}

	/** handle Commands events */
	public void onCommand(Commands cmd){
		switch(cmd){
		case MoveDown:
			this.moveDown();
			break;
		case MoveLeft:
			if(!m_grid.isBlocked(m_shape.getCoords(), m_shape.getPosX()+1, m_shape.getPosY()))
				m_shape.moveLeft();
			break;
		case MoveRight:
			if(!m_grid.isBlocked(m_shape.getCoords(), m_shape.getPosX()-1, m_shape.getPosY()))
				m_shape.moveRight(); 
			break;
		case Drop: 
			int dy = 1;
			while(!m_grid.isBlocked(m_shape.getCoords(), m_shape.getPosX(), m_shape.getPosY() + (dy++))){}
			dy -= 2;
			m_shape.moveDown(dy);

			this.shapeToGrid();			
			break;
		case Rotate:
			if(!m_grid.isBlocked(m_shape.getCoords(), m_shape.getPosX(), m_shape.getPosY(), true))
				m_shape.rotate();   
			break;
		case Start:
			this.start();
			break;
		case End:
			this.stop();
			break;
		}
	}

}
