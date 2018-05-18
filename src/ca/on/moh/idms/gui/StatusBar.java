package ca.on.moh.idms.gui;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Status bar for Main Frame
 */
public class StatusBar extends JPanel {

	protected String string;
	protected JLabel label;
	protected int height=20;

	public StatusBar() {

		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		add(Box.createRigidArea(new Dimension(5,0)));

		label=new JLabel();
		label.setForeground(Color.black);
		label.setMinimumSize(new Dimension(20,height));
		label.setPreferredSize(new Dimension(500,height));
		label.setMaximumSize(new Dimension(1000,height));
		label.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
		add(label);
		
		setMinimumSize(new Dimension(30,30));
		setBorder(BorderFactory.createLoweredBevelBorder());
		//label.setText(" For Help, press F1");
	}


	public StatusBar(String newString){
		this();
		showStatus(newString);
	}

	public void showStatus(String newString){
		string=new String(newString);
		label.setText(string);
		repaint();
	}
}