package amoba.AI;

import java.util.ArrayList;


public class Bot extends AI{
	
	int[][] board;
	int WhoIsComputer;

	ArrayList<Integer> MovesToConsider;
	
	public Bot(int boardsize, boolean humanbegins){
		
		MovesToConsider = new ArrayList<Integer>(boardsize*boardsize);
		
		board = new int[boardsize][boardsize];
		for (int i=0;i<boardsize;i++){
			for (int j=0;j<boardsize;j++){
				board[i][j] = -1;
			}
		}
	
		if (!humanbegins){
		
		WhoIsComputer = 1;
		//Make the first move
		for ( int i = boardsize/2-1 ; i <= boardsize/2+1; i++){
			for (int j = boardsize/2-1 ; j <= boardsize/2+1; j++){
				
				board[i][j] = 0;
				
				MovesToConsider.add(i*boardsize+j);
			}
		}
			MovesToConsider.remove( new Integer(boardsize/2 * boardsize + boardsize/2) );
			board[boardsize/2][boardsize/2] = WhoIsComputer;			
		}
		else{WhoIsComputer = 2;}
		
	}
	
	protected void makeMove(int[] move, int player){
		
		for ( int i = move[0]-1 ; i <= move[0]+1; i++){
			for (int j = move[1]-1 ; j <= move[1]+1; j++){
			try{
				if (board[i][j] == -1) {
				board[i][j] = 0;
				MovesToConsider.add(i*board.length+j);
				}
			}catch(ArrayIndexOutOfBoundsException e){}
			}
		}
		
		board[move[0]][move[1]] = player;
		MovesToConsider.remove(new Integer((move[0]*board.length+move[1])));
	}
	
	protected void makeMove(int move, int player){
		
		for ( int i = move/board.length - 2 ; i <= move/board.length + 2; i++){
			for (int j = move % board.length - 2 ; j <= move % board.length + 2; j++){
				try{
					if (board[i][j] == -1) {
					board[i][j] = 0;
					MovesToConsider.add(i*board.length + j);
					}
				}catch(ArrayIndexOutOfBoundsException e){}
			}
		}
		
		
		int[] loszar = toCoordinates(move);
		board[loszar[0]][loszar[1]] = player;
		MovesToConsider.remove( new Integer(move));
		
	}
	
	private int human(){
		if (WhoIsComputer == 1){return 2;}
		else {return 1;}
	}
	
	public int move(int humanmove){
		makeMove(humanmove, human());
		
		//Search for the best evaluated move
		int moveValue[] = new int[] { 0 , 0 , 0, 0}; //AIMove, length of line, HumanMove, length of line
		for (int Move : MovesToConsider){
			if (evaluateMove(Move, WhoIsComputer) > moveValue[1]){
				moveValue[0] = Move;
				moveValue[1] = evaluateMove(Move, WhoIsComputer);
			}
		}
		
		//Search the best evaluated move for opponent
		for (int Move : MovesToConsider){
			if (evaluateMove(Move, human()) > moveValue[3]){
				moveValue[2] = Move;
				moveValue[3] = evaluateMove(Move, human());
			}
		}

		
		//Choose and make move
		if ( moveValue[1] > moveValue[3] ){
			makeMove(moveValue[0], WhoIsComputer);
			return moveValue[0];
		}else{
			makeMove(moveValue[2], WhoIsComputer);
			return moveValue[2];
		}
		
	}
	
	private int lineLengthWithMove(int move, int player){
		int LongestLine = 0;
		
		for (int i=0;i<4;i++){
		
		int neighbourtype = i;
		int[] currentposition = toCoordinates(move);
		int counter = 1;
		
		try{
		while ( board[neighbour(currentposition[0],currentposition[1],neighbourtype)[0]][neighbour(currentposition[0],currentposition[1],neighbourtype)[1]] == player){
			currentposition = neighbour(currentposition[0],currentposition[1],neighbourtype);
			counter++;
		}
		}catch(ArrayIndexOutOfBoundsException e){}
		
		neighbourtype+=4; currentposition = toCoordinates(move);
		
		try{
		while ( board[neighbour(currentposition[0],currentposition[1],neighbourtype)[0]][neighbour(currentposition[0],currentposition[1],neighbourtype)[1]] == player){
			currentposition = neighbour(currentposition[0],currentposition[1],neighbourtype);
			counter++;
		}
		}catch(ArrayIndexOutOfBoundsException e){}
			if (counter > LongestLine){
				LongestLine = counter;
			}
		}
		
		return LongestLine;
	}
	
	private int evaluateMove(int move, int player){
		int value = 0; 
		
		for (int i=0;i<4;i++){
		
		int neighbourtype = i;
		int[] currentposition = toCoordinates(move);
		int counter = 1;
		boolean ClosedLeft = true; boolean ClosedRight = true;
		
		try{
		while ( board[neighbour(currentposition[0],currentposition[1],neighbourtype)[0]][neighbour(currentposition[0],currentposition[1],neighbourtype)[1]] == player){
			currentposition = neighbour(currentposition[0],currentposition[1],neighbourtype);
			counter++;
		}
		if ( board[neighbour(currentposition[0],currentposition[1],neighbourtype)[0]][neighbour(currentposition[0],currentposition[1],neighbourtype)[1]] == 0){
			ClosedRight = false;
		}
		}catch(ArrayIndexOutOfBoundsException e){}
		
		neighbourtype+=4; currentposition = toCoordinates(move);
		
		try{
		while ( board[neighbour(currentposition[0],currentposition[1],neighbourtype)[0]][neighbour(currentposition[0],currentposition[1],neighbourtype)[1]] == player){
			currentposition = neighbour(currentposition[0],currentposition[1],neighbourtype);
			counter++;
		}
		if ( board[neighbour(currentposition[0],currentposition[1],neighbourtype)[0]][neighbour(currentposition[0],currentposition[1],neighbourtype)[1]] == 0){
			ClosedLeft = false;
		}
		}catch(ArrayIndexOutOfBoundsException e){}
			/*if (ClosedLeft && ClosedRight){counter = 0;}
			else if (ClosedLeft){counter--;}
			else if (ClosedRight) {counter--;}
			if (counter > value){
				value = counter;
			}*/
			
			if ( evaluateLine(counter, ClosedLeft, ClosedRight) > value){
				value = counter;
			}
		}
		
		return value;
	}
	
	private int evaluateLine(int length, boolean closedleft, boolean closedright){
		if (closedleft && closedright){return 0;}
		else if (closedleft || closedright){
			switch (length){
				case 1: {
					return 1;
				}
				
				case 2: {
					return 3;
				}
				
				case 3: {
					return 5;
				}
				
				case 4: {
					return 6;
				}
				
				case 5: {
					return 9;
				}
				default: {
					System.out.println(length); return 1000; 
				}
			}
		}
		else{
			switch (length){
				case 1: {
					return 2;
				}
				
				case 2: {
					return 4;
				}
				
				case 3: {
					return 7;
				}
				
				case 4: {
					return 8;
				}
				
				case 5: {
					return 10;
				}
				
				default: {
					System.out.println(length); return 1000;
				}
			}
		}
	}
	
	private int[] toCoordinates(int move){
		return new int[]{ move/board.length , move%board.length };
	}
	
	
	public static int[] neighbour(int row, int column, int type) throws IllegalArgumentException{
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
}