package gemin.processing.tetris;

public class GridBoard implements IDrawElement {

	int[][] m_grid;
	int[] m_blockSize = new int[2];

	public void Initialize(int width, int height){
		m_grid = new int[height+2][width+2];
		m_blockSize[0] = width;
		m_blockSize[1] = height;

		this.reset();
	}

	public int getWidth(){return m_blockSize[0];}
	public int getHeight(){return m_blockSize[1];}    

	public void reset(){
		for(int i=0;i<m_blockSize[1]+2;++i)
			for(int j=0;j<m_blockSize[0]+2;++j)
				m_grid[i][j] = -1;

		for(int i=0;i<m_blockSize[0]+2;++i) m_grid[0][i] = m_grid[m_blockSize[1]+1][i] = 0;
	}

	public void freezeShape(int[] coords, int[] pos, int c){
		for(int i=0;i<4;++i) m_grid[coords[i*2 + 1] + pos[1] + 1][coords[i*2] + pos[0] + 1] = c;


		// and check
		int cx,cy,t; //current pos, temp pos 

		// looping
		for( cy = 1; cy<m_blockSize[1]+1;++cy){
			for(cx = 1; cx<m_blockSize[0]+1;++cx)
				if(m_grid[cy][cx] == -1) break; // if empty then break


			if(cx == m_blockSize[0] + 1){ // remove filled row
				System.out.println("a");
				for(t = cy;t>1;--t){ //move row content
					for(cx = 1;cx<m_blockSize[0]+1;++cx){ 
						m_grid[t][cx] = m_grid[t-1][cx];
					}
				}
			}
		}
	}

	public boolean isBlocked(int[] coords, int posx, int posy){ return this.isBlocked(coords, posx, posy, false);}
	public boolean isBlocked(int[] coords, int posx, int posy, boolean rot){
		boolean retval = false;
		if(rot) Shape.RotateCCW(coords); //rotate
		int py=0, px=0;

		for(int i=0;i<4;++i){
			py = coords[i*2 + 1] + posy+ 1;
			px = coords[i*2] + posx + 1;

			if(	px<1 || px>=(m_blockSize[0]+1)|| // check range
					py<1 || py>=(m_blockSize[1]+1)|| // check range
					m_grid[py][px] != -1){ // check is wall
				retval = true;
				break;
			}
		}

		if(rot) Shape.RotateCW(coords); // back rotate

		return retval;
	}

	/** Interface(IDrawElement) implements */
	@Override
	public void render(IBlockDrawer draw){
		for(int i=0;i<m_blockSize[1];++i)
			for(int j=0;j<m_blockSize[0];++j)
				draw.drawBlock(j, i, (m_grid[i+1][j+1] != -1) ? m_grid[i+1][j+1] : 0xff888888); 

	}


}
