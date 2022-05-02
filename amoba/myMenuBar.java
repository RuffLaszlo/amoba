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
		
		file = new JMenu("J�t�k");
			
			newgame = new JMenu("�j j�t�k");
				vsAI = new JMenuItem("Sz�m�t�g�p ellen");
				vsAI.setActionCommand("NewAIGame");
				pvp = new JMenuItem("K�t j�t�kos");
				pvp.setActionCommand("NewPvPGame");
			newgame.add(pvp);
			newgame.add(vsAI);
			file.add(newgame);
			
			quit = new JMenuItem("Kil�p�s");
			quit.addActionListener(new Exiter());
			file.add(quit);
		
		options = new JMenu("Be�ll�t�sok");
			compred = new JCheckBoxMenuItem("G�p kezd");
			compred.setSelected(false);
			options.add(compred);
			
			opponent = new JMenu("V�laszd ki ellens�ged!");
				ButtonGroup group = new ButtonGroup();
				
				random = new JRadioButtonMenuItem("Ronul�d�");
				random.setActionCommand("0");
				group.add(random);
				opponent.add(random);
				
				greedy = new JRadioButtonMenuItem("Agressz�v kismalac");
				greedy.setSelected(true);
				greedy.setActionCommand("1");
				group.add(greedy);
				opponent.add(greedy);
				
				lessgreedy = new JRadioButtonMenuItem("�gyet Lenke");
				lessgreedy.setActionCommand("2");
				group.add(lessgreedy);
				opponent.add(lessgreedy);
				
				oneply = new JRadioButtonMenuItem("Okoska");
				oneply.setActionCommand("3");
				group.add(oneply);
				opponent.add(oneply);
				
			options.add(opponent);
		
		help = new JMenu("S�g�");
			dafuq = new JMenuItem("Mit l�pjek?");
			dafuq.setActionCommand("Help");
			help.add(dafuq);
			
		
		add(file); add(options); add(help);
	}
	
}