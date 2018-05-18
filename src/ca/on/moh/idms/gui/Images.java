package ca.on.moh.idms.gui;

import java.util.Hashtable;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import org.apache.log4j.Logger;


public class Images{

	private static Logger log = Logger.getLogger(Images.class);

	/**
	 * Cache for images
	 */
	private static Hashtable cache=new Hashtable();

	/**
	 * Tries to get the icon from the cache. If not found - creates it, putting
	 * into the cache for subsequent use;
	 */
	public static Icon getIcon(String name){

		try{
			Icon icon = (Icon)cache.get(name);
			if(icon != null){
				return icon;
			}

			icon=createIcon(name);
			cache.put(name, icon);
			return icon;
		}
		catch(Exception e){
			log.error("", e);
		}
		return null;
	}

	/**
	 * Creates an icon not saving it in the cache;
	 */
	private static Icon createIcon(String name) throws Exception{

		return new ImageIcon("images/"+name);
	}


}
