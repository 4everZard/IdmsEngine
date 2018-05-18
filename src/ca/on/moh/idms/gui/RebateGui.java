package ca.on.moh.idms.gui;

import gov.moh.config.PropertyConfig;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;

public class RebateGui extends JFrame {

	protected MainFrameMenuBar menuBar;
	private StatusBar statusBar;
	private JProgressBar progressBar;
	private static Logger log = Logger.getLogger(RebateGui.class);

	RebateGui(){
		try{
			String rootPath = System.getProperty("user.dir");
			PropertyConfig.setPropertyPath(rootPath + "\\conf\\system.properties");
			setTitle("Drug Rebate Calculator");

			Container contentPane = getContentPane();
			contentPane.setLayout(new BorderLayout());

			contentPane.add(new CalculatorGui(), BorderLayout.CENTER);

			setDefaultCloseOperation(DO_NOTHING_ON_CLOSE); 

			final RebateGui instance=this;
			addWindowListener(new WindowAdapter(){
				public void windowClosing(WindowEvent evt){
					Actions.fireAction(instance, new Actions.ExitAction(null)); 
				}
				public void windowActivated(WindowEvent evt){
				}               
			});      

			int sizeX=620;
			int sizeY=450;
			this.setResizable(false);
			this.setSize(new Dimension(sizeX,sizeY));

			menuBar = new MainFrameMenuBar(this);
			setJMenuBar(menuBar);      

			JPanel toolBarsWrappingPanel=new JPanel();
			toolBarsWrappingPanel.setLayout(new BoxLayout(toolBarsWrappingPanel, BoxLayout.Y_AXIS));
			contentPane.add(toolBarsWrappingPanel, BorderLayout.NORTH);

			JPanel wp = new JPanel();
			wp.setLayout(new BoxLayout(wp, BoxLayout.Y_AXIS));
			

			statusBar=new StatusBar();
			contentPane.add(statusBar, BorderLayout.SOUTH);
			GUIManager.statusBar = statusBar;
			
			progressBar = new JProgressBar();
			progressBar.setIndeterminate(true);
		    progressBar.setStringPainted(true);
		    GUIManager.statusBar.add(progressBar);
		    progressBar.setVisible(false);
		    
		
			
			int x=0,y=0; 
			Dimension dim=Toolkit.getDefaultToolkit().getScreenSize();
			int screenW=dim.width;
			int screenH=dim.height;
			int w=this.getSize().width;
			int h=this.getSize().height;
			if(w<screenW){ 
				x=(screenW-w)/2;
			}else {
				x=0;
			}
			if(h<screenH) {
				y=(screenH-h)/2;
			}else{
				y=0;
			}
			this.setLocation(x,y);

			setIconImage(((ImageIcon)Images.getIcon("icon.GIF")).getImage());
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void start(final int num){
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				progressBar.setMaximum(num);
				progressBar.setValue(1);
				progressBar.setIndeterminate(false);
				progressBar.setVisible(true);
			}
		});    		
	}
	
	public void updateStatus(){
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				progressBar.setValue(progressBar.getValue()+1);
			}
		}); 		
	}
	
	public void finish(){
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				progressBar.setIndeterminate(true);
				progressBar.setVisible(false);
			}
		}); 		
	}
	

	/**
	 * Override super's method - to add additional (debug currently) title suffix
	 */
	public void setTitle(String title){

		super.setTitle(title+" "+GUIManager.releaseVersionString);
	}  

	/**
	 * Entry point of the application if without Login
	 */
	public static void main(String args[]) throws Exception{
		
		try{
			GUIManager.initialize();
		}catch (Throwable t) {
			log.error("", t);
		}
	}

}
