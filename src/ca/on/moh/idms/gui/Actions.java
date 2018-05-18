package ca.on.moh.idms.gui;

import java.awt.event.ActionEvent;
import java.awt.Component;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
import javax.swing.Action;

public class Actions{

	/**
	 * Exit from the main menu amainAgentPanelction
	 */
	public static class ExitAction extends AbstractAction{

		static final String NAME="exit";
		private Component p;

		public ExitAction(Component parent){

			super(NAME);
			p=parent;
		}

		public void actionPerformed(ActionEvent e) {

			int response=JOptionPane.showConfirmDialog(p, "Do you really want to exit?");
			switch(response){

			case JOptionPane.YES_OPTION:
				System.exit(0);
			case JOptionPane.NO_OPTION:

			case JOptionPane.CANCEL_OPTION:

			case JOptionPane.CLOSED_OPTION:
			}
		}
	}//ExitAction

	/**
	 * Method simulating JButton/JMenu behaviour (firing of Action) to share
	 * the same Actions delivery mechanism with buttons/menus
	 */  
	public static void fireAction(Object source, Action action){

		action.actionPerformed(new ActionEvent(source, ActionEvent.ACTION_PERFORMED, (String)action.getValue(Action.NAME)));
	}     

}
