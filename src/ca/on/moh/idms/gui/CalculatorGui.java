package ca.on.moh.idms.gui;


import gov.moh.app.db.DBConnectionManager;
import gov.moh.config.ConfigFromDB;
import gov.moh.config.PropertyConfig;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.Connection;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;

import ca.on.moh.idms.engine.RebateCalculator;
import ca.on.moh.idms.util.RebateConstant;

public class CalculatorGui extends JPanel{

	private JPanel centralPanel;
	private RebateCalculator rc;
	
	private JTextField rebateFileSavePath = new JTextField();
	
	private static final Insets WEST_INSETS = new Insets(5, 0, 5, 5);
	private static final Insets EAST_INSETS = new Insets(5, 5, 5, 0);

	private static Logger log = Logger.getLogger(CalculatorGui.class);
	
	public CalculatorGui()throws Exception{
		centralPanel = constructCenterPanel();
	}


	private JPanel constructCenterPanel()throws Exception{


		GridBagLayout gridbag = new GridBagLayout();
		this.setLayout(gridbag);
		setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Drug Rebate Calculator"), BorderFactory.createEmptyBorder(5, 5, 5, 5)));

		//line 0: Manufacture Code
		GridBagConstraints gbc1 = createGbc(0, 0,2,1);
		JLabel label1 = new JLabel("Manufacturer Code: ",JLabel.LEFT);
		add(label1,gbc1);
		gbc1 = createGbc(1, 0,2,1);
		final JTextField manufacturerCodeField = new JTextField(10);
		manufacturerCodeField.setText("LEO");
		add(manufacturerCodeField, gbc1);
		
		
		//line 1: Claim calculation start and end date
		final String historyStartDate = ConfigFromDB.getConfigPropertyFromDB(RebateConstant.HISTORY_START_DATE); // JUN 29,2015
		final String historyEndDate = ConfigFromDB.getConfigPropertyFromDB(RebateConstant.HISTORY_END_DATE); // JUN 30, 2015
		
		GridBagConstraints gbc2 = createGbc(0, 1,1,1);
		JLabel label2 = new JLabel("Claim Start Date:",JLabel.LEFT);
		add(label2,gbc2);
		gbc2 = createGbc(1,1,1,1);
		JTextField startDateField = new JTextField(10);
		startDateField.setText(historyStartDate);
		add(startDateField, gbc2);
		gbc2 = createGbc(2,1,1,1);
		JLabel label3 = new JLabel("Claim End Date:",JLabel.RIGHT);
		add(label3,gbc2);
		gbc2 = createGbc(3,1,1,1);
		JTextField endDateField = new JTextField(10);
		endDateField.setText(historyEndDate);
		add(endDateField, gbc2);
		
		//line 2: Claim quarter start and end date
		final String quarterStartDate = "04-01-2015";
		final String quarterEndDate = "06-30-2015";
		
		GridBagConstraints gbc3 = createGbc(0, 2,1,1);
		JLabel label4 = new JLabel("Claim Quarter Start Date:",JLabel.LEFT);
		add(label4,gbc3);
		gbc3 = createGbc(1,2,1,1);
		JTextField quarterStartDateField = new JTextField(10);
		quarterStartDateField.setText(quarterStartDate);
		quarterStartDateField.setEditable(false);
		add(quarterStartDateField, gbc3);
		gbc3 = createGbc(2,2,1,1);
		JLabel label5 = new JLabel("Claim Quarter End Date:",JLabel.RIGHT);
		add(label5,gbc3);
		gbc3 = createGbc(3,2,1,1);
		JTextField quarterEndDateField = new JTextField(10);
		quarterEndDateField.setText(quarterEndDate);
		quarterEndDateField.setEditable(false);
		add(quarterEndDateField, gbc3);
		

		//Line 3: Calculation first price date and YY price date
		final String firstPriceDate = ConfigFromDB.getConfigPropertyFromDB(RebateConstant.FIRST_PRICE_DATE); //10-23-2006
		final String yyPriceDate = ConfigFromDB.getConfigPropertyFromDB(RebateConstant.YYYY_PRICE_DATE); //06-30-2007
		
		GridBagConstraints gbc4 = createGbc(0, 3,1,1);
		JLabel lable6 = new JLabel("First Price Date:",JLabel.LEFT);
		add(lable6,gbc4);
		gbc4 = createGbc(1,3,1,1);
		JTextField firstPriceField = new JTextField(10);
		firstPriceField.setText(firstPriceDate);
		firstPriceField.setEditable(false);
		add(firstPriceField, gbc4);
		gbc4 = createGbc(2,3,1,1);
		JLabel lable7 = new JLabel("YYYY Price Date:",JLabel.RIGHT);
		add(lable7,gbc4);
		gbc4 = createGbc(3,3,1,1);
		JTextField yyDateField = new JTextField(10);
		yyDateField.setText(yyPriceDate);
		yyDateField.setEditable(false);
		add(yyDateField, gbc4);
		
		
		
		
		
		//line 4: Calculation result saving path
		GridBagConstraints gbc5 = createGbc(0, 4,1,1);
		JLabel label8 = new JLabel("Summary Save Path:",JLabel.LEFT);
		add(label8,gbc5);
		gbc5 = createGbc(1, 4,2,1);
		final JTextField savePathField = new JTextField(10);
		final String inputDir = "C:\\TEMP\\IDMS\\data";
		savePathField.setText(inputDir);
		add(savePathField, gbc5);
		
		gbc5 = createGbc(3,4,1,1);
		JButton inputDirBrowseButton = new JButton("Browse");
		add(inputDirBrowseButton,gbc5);
		
		inputDirBrowseButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				GUIManager.statusBar.showStatus("");
				JFileChooser chooser = new JFileChooser();
				chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				chooser.setDialogTitle("Rebate File Save Path");
								
				File dir = new File(inputDir);
				chooser.setCurrentDirectory(dir);

				File file = new File(inputDir);
				chooser.setSelectedFile(file);

				int status = chooser.showOpenDialog(null);
				if(status == JFileChooser.APPROVE_OPTION){
					File f = chooser.getSelectedFile();
					if(!f.isDirectory()){
						f = chooser.getCurrentDirectory();
					}
					rebateFileSavePath.setText(f.getAbsolutePath());
				}
			}
		});

		
		//line 5: Calculation submit button
		GridBagConstraints gbc6 = createGbc(1, 5,2,1);
		JButton calculationButton4 = new JButton("Calculate Now");
		add(calculationButton4,gbc6);

		calculationButton4.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				GUIManager.statusBar.showStatus("Calculating in progress ......");

				new SwingWorker(){
				    public Object construct() {
				    	rc = new RebateCalculator();
						String manufacturerCode = manufacturerCodeField.getText();
						String path = savePathField.getText();
						Connection conn1 = null;
						Connection conn2 = null;
						try{
							conn1 = DBConnectionManager.getManager().getConnection();
							String username = PropertyConfig.getProperty("app.config.db.username2");
							String pwd = PropertyConfig.getProperty("app.config.db.password2");
							String url = PropertyConfig.getProperty("app.config.db.dbUrl2");
							conn1 = DBConnectionManager.getManager().getConnection();
							conn2 = DBConnectionManager.getManager().getConnection(username,pwd,url);
					    	rc.calculateRebate(manufacturerCode, conn1,conn2,path);
						}catch(Exception e){
							e.printStackTrace();
							log.error("Error in rebate calculation: ", e);
						}finally{
							DBConnectionManager.getManager().closeConnection(conn1);
							DBConnectionManager.getManager().closeConnection(conn2);
						}
						SwingUtilities.invokeLater(new Runnable(){
							public void run(){
								GUIManager.statusBar.showStatus("Calculation completed. please see the summary in the folder.");
								JOptionPane.showMessageDialog(centralPanel, "Completed");
							}
						});  							
						return new Object();
				    }
				}.start(); 				
			}
		});	 	
	
		
		//line 6 Space
		GridBagConstraints gbc7 = createGbc(0, 6,2,1);
		JLabel lempty = new JLabel(" ",JLabel.LEFT);
		add(lempty,gbc7);
		
		//Line 7: DIN #
		GridBagConstraints gbc8 = createGbc(0, 7,2,1);
		JLabel l10 = new JLabel("DIN Number: ",JLabel.LEFT);
		add(l10,gbc8);
		gbc8 = createGbc(1, 7,2,1);
		final JTextField dinField = new JTextField(10);
		dinField.setText("02418401");
		dinField.setEditable(false);
		add(dinField, gbc8);
		
		// Line 8: submit button
		GridBagConstraints gbc9= createGbc(1, 8,2,1);
		JButton getDinDetailButton = new JButton("Get the Din Details Now");
		add(getDinDetailButton,gbc9);
		getDinDetailButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				GUIManager.statusBar.showStatus("Get DIN calculation details ......");

				if(rc == null){
					rc = new RebateCalculator();
				}
				String din = dinField.getText();
				String path = savePathField.getText();
				
				try{
					rc.getDinDetail(din,path);
				}catch(Exception e){
					log.error("Error in getting DIN details", e);
				}

				GUIManager.statusBar.showStatus("Finished: please see the summary excel sheet");
				JOptionPane.showMessageDialog(centralPanel, "Completed");
					
			}
		});			

		return this;
	}
	
	private GridBagConstraints createGbc(int x, int y,int width, int height) {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = x;
		gbc.gridy = y;
		gbc.gridwidth = width;
		gbc.gridheight = height;

		gbc.anchor = (x == 0) ? GridBagConstraints.WEST : GridBagConstraints.EAST;
		gbc.fill = (x == 0) ? GridBagConstraints.BOTH : GridBagConstraints.HORIZONTAL;

		gbc.insets = (x == 0) ? WEST_INSETS : EAST_INSETS;
		gbc.weightx = (x == 0) ? 0.1 : 1.0;
		gbc.weighty = 1.0;
		return gbc;
	}

}

