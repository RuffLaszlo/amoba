package amoba;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BotListener implements ActionListener{

	private int activebot;
	
	public BotListener(){
		activebot = 1;
	}
	
	public int activeBot(){
		return activebot;
	}
	
	public void actionPerformed(ActionEvent e){
		activebot = Integer.parseInt(e.getActionCommand());
	}

}