package amoba.AI;

import java.util.Random;
import java.util.ArrayList;


public class Ronulade extends AI{
	
	int[][] board;
	int WhoIsComputer;
	Random rnd;
	ArrayList<Integer> MovesToConsider;
	
	public Ronulade(int boardsize, boolean humanbegins){
	
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
		
			MovesToConsider.remove(new Integer(boardsize/2 * boardsize + boardsize/2));
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
		makeMove(toCoordinates(humanmove), human());
		
		//Get a Random move
		int myMove = MovesToConsider.get(rnd.nextInt(MovesToConsider.size()));
		makeMove(toCoordinates(myMove), WhoIsComputer);
		
		return myMove;
	}
	
	protected int[] toCoordinates(int move){
		return new int[]{ move/board.length , move%board.length };
	}
	
}