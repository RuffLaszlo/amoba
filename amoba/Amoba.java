package amoba;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import amoba.AI.*;

public class Amoba implements ActionListener, ItemListener{
	
	JPanel mainPane;
	Board board;
	myMenuBar menuBar;
	BotListener AIListener;
		
	
	final int size=20;
	
	public Amoba(){
	
		//Create Board
		board = new Board(size, false, true);
		
		//Creating myMenuBar and adding Listeners
		AIListener = new BotListener();
		
		menuBar = new myMenuBar();
			menuBar.vsAI.addActionListener(this);
			menuBar.pvp.addActionListener(this);
			menuBar.dafuq.addActionListener(this);
			menuBar.compred.addItemListener(this);

			menuBar.random.addActionListener(AIListener);
			menuBar.greedy.addActionListener(AIListener);
			menuBar.lessgreedy.addActionListener(AIListener);
			menuBar.oneply.addActionListener(AIListener);
		
		//Create the contentpane
		mainPane = new JPanel();
		mainPane.add(board);
	
	}
	
	private static void createAndShowGUI() {
        //Set the look and feel.
        //initLookAndFeel();

        //Create and set up the window.
        JFrame frame = new JFrame("Amõba");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
		Amoba amoba = new Amoba();
        amoba.mainPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(amoba.mainPane);

		frame.setJMenuBar(amoba.menuBar);
        //Display the window.
        //frame.pack();
		frame.setSize(600,600);
		frame.pack();
        frame.setVisible(true);
    }
	
	public void actionPerformed(ActionEvent e){
		if (e.getActionCommand().equals("NewAIGame")){
			//Clears the Board
			for (int i=0;i<size*size;i++){
				board.buttons[i].setBackground(null);
			}
			
			//Adds AI
			board.computer = BotChooser(AIListener.activeBot());
			board.AIisOn = true;
			
			//Creates new Game
			board.game = new Game(size);

			
			//Makes move if AI is Red Player
			if (!board.HumanIsRed) {
				board.game.move(board.game.Tabla.size() / 2 , board.game.Tabla.size() / 2);
				board.buttons[board.game.Tabla.size() / 2 * board.game.Tabla.size() + board.game.Tabla.size() / 2].setBackground(board.PlayertoColor());
			}
		}
		else if(e.getActionCommand().equals("NewPvPGame")){
			//Clears the Board
			for (int i=0;i<size*size;i++){
				board.buttons[i].setBackground(null);
			}
			//Creates new Game
			board.game = new Game(size);
			//turns AI off
			board.AIisOn = false;
		}
		else if(e.getActionCommand().equals("Help")){
			JOptionPane.showMessageDialog(board,"Segíts magadon, Isten is megsegít.","Napi bölcsesség",JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	public void itemStateChanged(ItemEvent e){		
			board.HumanIsRed = e.getStateChange() == ItemEvent.DESELECTED ;
	}
	
	public static void main(String[] args){
		try {
			UIManager.setLookAndFeel(
				UIManagaer.getSystemLookAndFeelClassName());
		}
		catch (Exception e){
			//handle exception
		}
		createAndShowGUI();
	}
	
	private AI BotChooser(int i){
		switch (i){
			case 0:
			return new Ronulade(size, board.HumanIsRed);
			case 1:
			return new Moho(size, board.HumanIsRed);
			case 2:
			return new Moho2(size, board.HumanIsRed);
			case 3:
			return new Bot(size, board.HumanIsRed);
		} return new Ronulade(size, board.HumanIsRed);
	}
}
