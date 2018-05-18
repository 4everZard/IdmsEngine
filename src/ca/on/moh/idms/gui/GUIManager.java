package ca.on.moh.idms.gui;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.apache.log4j.Logger;

public class GUIManager {

	public static final String releaseVersionString = "ver 0.1";

	public static StatusBar statusBar;

	private static Logger log = Logger.getLogger(GUIManager.class);
	boolean isRunning = false;

	private static GUIManager instance;

	private boolean finalizedAlready;

	/**
	 * Static initialization routine which must be called once at the very
	 * beginning by user class
	 */
	public static void initialize() {

		if (instance == null) {
			instance = new GUIManager();
		}

		try {
			setSystemLookAndFeel();
			Images.getIcon("mohltc_logo.GIF");

			Runtime.getRuntime().addShutdownHook(new Thread("ShutdownHookThread") {
				public void run() {
					instance.onShutDownAsync();
				}
			});

			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					RebateGui mmf = new RebateGui();
					mmf.show();
				}
			});
		} catch (Exception e) {
			System.err.println("initInternal:" + e);
			e.printStackTrace();
		}
	}

	/**
	 * This method is invoked on ShutDownHookup (when user Ctrl-C the app from
	 * command line and any valid exiting from the application using gui
	 * controls: close buttons, 'Exit' buttons). Notice that in the case of
	 * normal 'Exit', additional operations can be done - that is why this
	 * method is called from onShutDownFromGUI() which has those additional
	 * finalization methods (saving of user preferences etc)
	 */
	private void onShutDownAsync() {

		if (finalizedAlready)
			return;
		finalizedAlready = true;

		// Just log the info
		if (isRunning)
			if (log.isDebugEnabled())
				log.debug("Shutting down ...");

		if (!isRunning) {
			if (log.isDebugEnabled())
				log.debug("Completed");
		}
	}

	/**
	 * Tries to setup a look and feel according to user's preferences
	 */
	private static void setSystemLookAndFeel() {

		// Force SwingApplet to come up in the System L&F vs default Metal
		String laf = UIManager.getSystemLookAndFeelClassName();
		try {
			UIManager.setLookAndFeel(laf);
			// UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		} catch (UnsupportedLookAndFeelException exc) {
			System.err.println("Unsupported LookAndFeel: " + laf);
		} catch (Exception exc) {
			System.err.println("Error loading " + laf + ": " + exc);
		}

	}

}
