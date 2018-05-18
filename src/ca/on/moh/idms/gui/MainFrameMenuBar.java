package ca.on.moh.idms.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class MainFrameMenuBar extends JMenuBar{

	final private JFrame frame;

	public MainFrameMenuBar(JFrame f) throws Exception{

		super();

		this.frame=f;

		JMenu menu=add(new JMenu("File"));

		JMenuItem menuItem;
		
		menu.addSeparator();

		menuItem = menu.add("Exit");
		menuItem.addActionListener(new Actions.ExitAction(null));

		menu.addSeparator();

		menu = add(new JMenu("Help"));

		menuItem = menu.add(new JMenuItem("Help Topics"));
		menuItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				javax.swing.JOptionPane.showMessageDialog(null, "Help is not implemented yet");
			}
		});

		menu.addSeparator();

		menuItem = menu.add(new JMenuItem("About"));
		menuItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "<html><body><center>Drug rebate calculation tool</center></body></html>", "About", JOptionPane.PLAIN_MESSAGE,
						Images.getIcon("icon.GIF"));
				//(new org.sp.gui.dialogs.About(frame)).show();
			}
		});

	}




}
