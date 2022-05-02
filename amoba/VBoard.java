package amoba;

public class VBoard{

	private final int boardsize;
	private int[][] squares;

	/** Creates empty board with specified size
	*/
	public VBoard(int size){
		this.boardsize = size;
		squares = new int[size][size];

		for (int i=0;i<size;i++){
			for (int j=0;j<size;j++){
				squares[i][j] = 0;
			}
		}
	}
	
	/** Uses the given gamestate as board
	*/
	public VBoard(int[][] gamestate){
		boardsize = gamestate.length;
		squares = gamestate;
	}
	
	public int[][] gameState(){
		return squares;
	}
	
	public int getSquare(int row, int column){
		return squares[row][column];
	}
	
	public void setSquare(int row, int column, int piece){
		squares[row][column] = piece;
	}
	
	public int size(){
		return boardsize;
	}
	
	public boolean isFull(){
		for (int i=0;i<boardsize;i++){
			for (int j=0;j<boardsize;j++){
				if (squares[i][j] == 0) {return false;}
			}
		}
		return true;
	}
	
	public int[] neighbour(int row, int column, int type){
		switch (type){
			case 0:{
				return new int[]{row-1,column-1};
			}
			case 1:{
				return new int[]{row-1,column};
			}
			case 2:{
				return new int[]{row-1,column+1};
			}
			case 3:{
				return new int[]{row,column+1};
			}
			case 4:{
				return new int[]{row+1,column+1};
			}
			case 5:{
				return new int[]{row+1,column};
			}
			case 6:{
				return new int[]{row+1,column-1};
			}
			case 7:{
				return new int[]{row,column-1};
			}
			default:{
				throw new IllegalArgumentException("No such neighbourhood");
			}
		}
	}
	
	public void draw(){
		for (int i=0;i<boardsize;i++){
			for (int j=0;j<boardsize;j++){
				System.out.print(squares[i][j]);
			}
			System.out.println("");
		}
	}
}