package amoba;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*; 
import javax.swing.ImageIcon;
import amoba.AI.*;
  
public class Board extends JPanel implements ActionListener{  
	
	Square[] buttons;
	boolean HumanIsRed;
	Game game;
	boolean AIisOn;
	AI computer;
	
	public Board(int n){
		this( n , false);
	}
	
	public Board(int n, boolean AI){
		this( n, AI, true);
	}
	
	public Board(int n, boolean AI, boolean humanisred){    
    
		buttons = new Square[n*n];
		game = new Game(n);
		AIisOn = AI;
		HumanIsRed = humanisred;
		
		//computer = new Bot(n , false );
	
		for (int i=0;i<n*n;i++){
			buttons[i] = new Square(i);
			this.add(buttons[i]);
			buttons[i].addActionListener(this);
		}
	
  
		this.setLayout(new GridLayout(n,n));  
		//setting grid layout of 3 rows and 3 columns  
  
		this.setPreferredSize(new Dimension(500,500));  
		this.setVisible(true);
	}  
	
	protected Color PlayertoColor(){
		if (game.WhoIsOnTurn()) {return Color.black;}
		else {return Color.red;}
	}
	
	public void actionPerformed(ActionEvent e){		
		
		if(game.isFinished()){}
		
		else{
		//Make the move in game
		try{
			game.move( ((Square)(e.getSource())).index);
		}catch(IllegalArgumentException exception){
			JOptionPane.showMessageDialog(this,"A mezõ már foglalt","Szabálytalan lépés",JOptionPane.ERROR_MESSAGE);
			return;
		}
		// Make the move visible on Board
		((Square)(e.getSource())).setBackground(PlayertoColor()); 
		
		if (game.isFinished() ){
			if (game.WhoIsOnTurn()){
				JOptionPane.showMessageDialog(this,"Fekete játékos nyert!","Vége a játéknak",JOptionPane.INFORMATION_MESSAGE);
			}
			else{
				JOptionPane.showMessageDialog(this,"Piros játékos nyert!","Vége a játéknak",JOptionPane.INFORMATION_MESSAGE);
			}
		}else{
		
		/*
		//Computer move
		if (AIisOn){
			int[] AImove = computer.move(game.Tabla.gameState());
			game.move(AImove[0],AImove[1]);
			(buttons[ AImove[0] * game.Tabla.size() + AImove[1] ]).setBackground(PlayertoColor());
		}	*/	
		
		if (AIisOn){
			int AImove = computer.move(((Square)(e.getSource())).index);
			game.move(AImove);
			buttons[AImove].setBackground(PlayertoColor());
		}
		
		if (game.isFinished() ){
			if (game.WhoIsOnTurn()){
				JOptionPane.showMessageDialog(this,"Fekete játékos nyert!");
			}
			else{
				JOptionPane.showMessageDialog(this,"Piros játékos nyert!");
			}
		}
		
		}
		}
	}
}  