package gemin.processing.tetris;

import java.util.Random;

public class Shape implements IDrawElement {


	/*=============== Class Members ============*/
	private final static int InitialYPosition = 1;

	final static int NumberOfShapeTypes = 7;
	final static int[] ShapeColors 
	= {0xff00ffff, // cyon
			0xff0000ff, // blue 
			0xffffa500, // orange
			0xffffff00, // yellow
			0xff00ff00, // lime
			0xff800080, // purple
			0xffff0000}; // red

	final static int[][] ShapeCoords 
	= { {0,0,2,0,1,0,-1,0},//I
			{0,0,-1,0,-1,-1,1,0}, //J
			{0,0,1,0,-1,0,1,-1}, //L
			{0,0,1,0,1,1,0,1}, //O
			{0,0,-1,0,0,-1,1,-1}, //S
			{0,0,-1,0,1,0,0,1}, //T
			{0,0,-1,-1,0,-1,1,0}}; //Z


	static Random r = new Random();
	private static int RandomType(){ 
		return r.nextInt(NumberOfShapeTypes);
	}

	public static void RotateCW(int[] coords){
		int temp;
		for(int i=1;i<4;++i){
			temp = coords[i*2 + 1];
			coords[i*2 + 1]  = coords[i*2];
			coords[i*2] = -temp;
		}
	}

	public static void RotateCCW(int[] coords){
		int temp;
		for(int i=1;i<4;++i){
			temp = coords[i*2 + 1];
			coords[i*2 + 1]  = -coords[i*2];
			coords[i*2] = temp;
		}
	}




	/*==========================================*/

	int[] m_pos = {0,0};   // current position {xPos, yPos}
	int[] m_shapeCoords = new int[8];
	int m_currentType;

	public Shape(int xPos){
		m_currentType = RandomType();
		System.arraycopy(ShapeCoords[m_currentType], 0, m_shapeCoords, 0, 8);

		m_pos[0] = xPos;
		m_pos[1] = InitialYPosition;
	}

	public int[] getCoords(){return m_shapeCoords;}
	public int[] getPos(){return m_pos;}
	public int getPosX(){return m_pos[0];}
	public int getPosY(){return m_pos[1];}
	public int getColor(){return ShapeColors[m_currentType];}

	/** commands */
	public void moveLeft(){ m_pos[0] -= 1;}
	public void moveRight(){ m_pos[0] += 1;}
	public void moveDown(int amount){ m_pos[1] += amount; }
	public void moveDown(){this.moveDown(1);}
	public void rotate(){ RotateCCW(m_shapeCoords); }


	/** Interface(IDrawElement) implements */
	@Override
	public void render(IBlockDrawer drawer){
		int c = getColor();
		for(int i=0;i<4;++i) 
			drawer.drawBlock(m_shapeCoords[i*2] +  getPosX(), m_shapeCoords[i*2+1] + getPosY(), c);
	}

}
