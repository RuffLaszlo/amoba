package amoba.AI;

import java.util.Random;
import java.util.ArrayList;


public class Moho extends AI{
	
	int[][] board;
	int WhoIsComputer;
	Random rnd;
	ArrayList<Integer> MovesToConsider;
	
	public Moho(int boardsize, boolean humanbegins){
	
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
				
		rnd = new Random();
		
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
		
		//Choose the move which makes us the longest line possible
		int moveValue[] = new int[] { 0 , 0 };
		for (int Move : MovesToConsider){
			if (lineLengthWithMove(Move, WhoIsComputer) > moveValue[1]){
				moveValue[0] = Move;
				moveValue[1] = lineLengthWithMove(Move, WhoIsComputer);
			}
		}
		
		makeMove(moveValue[0], WhoIsComputer);
		
		return moveValue[0];
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
	
	protected int[] toCoordinates(int move){
		return new int[]{ move/board.length , move%board.length };
	}
	
	
	public static int[] neighbour(int row, int column, int type){
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