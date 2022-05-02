package amoba;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

public class myMenuBar extends JMenuBar{
	
	JMenu file; JMenu help; JMenu options;JMenu newgame; JMenu opponent;
			JMenuItem quit;JMenuItem dafuq;JMenuItem vsAI; JMenuItem pvp; JCheckBoxMenuItem compred;
			JRadioButtonMenuItem random; JRadioButtonMenuItem greedy; JRadioButtonMenuItem lessgreedy; JRadioButtonMenuItem oneply;
	
	public myMenuBar(){
		
		file = new JMenu("Játék");
			
			newgame = new JMenu("Új játék");
				vsAI = new JMenuItem("Számítógép ellen");
				vsAI.setActionCommand("NewAIGame");
				pvp = new JMenuItem("Két játékos");
				pvp.setActionCommand("NewPvPGame");
			newgame.add(pvp);
			newgame.add(vsAI);
			file.add(newgame);
			
			quit = new JMenuItem("Kilépés");
			quit.addActionListener(new Exiter());
			file.add(quit);
		
		options = new JMenu("Beállítások");
			compred = new JCheckBoxMenuItem("Gép kezd");
			compred.setSelected(false);
			options.add(compred);
			
			opponent = new JMenu("Válaszd ki ellenséged!");
				ButtonGroup group = new ButtonGroup();
				
				random = new JRadioButtonMenuItem("Ronuládé");
				random.setActionCommand("0");
				group.add(random);
				opponent.add(random);
				
				greedy = new JRadioButtonMenuItem("Agresszív kismalac");
				greedy.setSelected(true);
				greedy.setActionCommand("1");
				group.add(greedy);
				opponent.add(greedy);
				
				lessgreedy = new JRadioButtonMenuItem("Ügyet Lenke");
				lessgreedy.setActionCommand("2");
				group.add(lessgreedy);
				opponent.add(lessgreedy);
				
				oneply = new JRadioButtonMenuItem("Okoska");
				oneply.setActionCommand("3");
				group.add(oneply);
				opponent.add(oneply);
				
			options.add(opponent);
		
		help = new JMenu("Súgó");
			dafuq = new JMenuItem("Mit lépjek?");
			dafuq.setActionCommand("Help");
			help.add(dafuq);
			
		
		add(file); add(options); add(help);
	}
	
}