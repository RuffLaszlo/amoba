package amoba;

public class Game{

	protected VBoard Tabla;
	private boolean CrossOnTurn;
	private boolean finished;
	
	public Game(int boardsize){
		Tabla = new VBoard(boardsize);
		CrossOnTurn = true;
		finished = false;
	}
	
	public Game(int[][] gamestate,boolean crossonturn){
		Tabla = new VBoard(gamestate);
		CrossOnTurn = crossonturn;
		finished = false;
	}
	
	private int PlayerOnTurn(){
		if (CrossOnTurn == true) return 1;
		else return 2;
	}
	
	public boolean WhoIsOnTurn(){
		return CrossOnTurn;
	}
	
	public boolean isFinished(){ return finished;};
	
	private void AMoveHasBeenMade(){
		if (CrossOnTurn) {CrossOnTurn = false;}
		else {CrossOnTurn = true;}
	}
	
	public int winner(){
		//Not finished yet
		if (!finished){ return -1;}		
		//Draw
		if ( Tabla.isFull() ) {return 0;}
		//Return Winner
		return PlayerOnTurn();
	}
	
	public void move(int row, int column) throws IllegalArgumentException{
		
		if (Tabla.getSquare(row, column) == 0){
			//Make the move on Tabla
			Tabla.setSquare(row, column, PlayerOnTurn());
			
			//Check if game is finished
			if (this.isThereAWin(row, column)){finished = true;}
			if ( Tabla.isFull() ) {finished = true;}
			
			//Change the player on turn
			this.AMoveHasBeenMade();
			
		} else{
			throw new IllegalArgumentException("The square is already taken");
		}
	}
	
	public void move(int index){
		move(index / Tabla.size() , index % Tabla.size() );
	}
	
	public boolean isThereAWin(int row, int column){
			
		for (int i=0;i<4;i++){
			int counter = 1;
			int[] valami = new int[]{row, column};
			
			int neighbourtype = i;
			try{
			while ( Tabla.getSquare(Tabla.neighbour(valami[0],valami[1],neighbourtype)[0],Tabla.neighbour(valami[0],valami[1],neighbourtype)[1]) == PlayerOnTurn()){
				//try{
				valami = Tabla.neighbour(valami[0],valami[1],neighbourtype);
				counter++;
				if (counter>=5) {return true;}
				//}catch(ArrayIndexOutOfBoundsException e){}					
								
			}}catch(ArrayIndexOutOfBoundsException e){}
			
			neighbourtype +=4;
			valami[0]=row; valami[1]=column;
			
			try{
			while ( Tabla.getSquare(Tabla.neighbour(valami[0],valami[1],neighbourtype)[0],Tabla.neighbour(valami[0],valami[1],neighbourtype)[1]) == PlayerOnTurn() ){
				//try{
				valami = Tabla.neighbour(valami[0],valami[1],neighbourtype);
				counter++;
				if (counter>=5) {return true;}
				//}catch(ArrayIndexOutOfBoundsException e){}					
				
			}}catch(ArrayIndexOutOfBoundsException e){}
		}
			
		return false;
		
	}
	
}