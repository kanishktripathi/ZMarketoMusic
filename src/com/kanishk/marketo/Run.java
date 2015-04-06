package com.kanishk.marketo;

import java.io.IOException;

import com.kanishk.marketo.navigate.MainMenu;
import com.kanishk.marketo.navigate.Utils;
import com.kanishk.marketo.operations.DataLoader;

/**
 * The Class Run. Class containing the main method
 */
public class Run {

	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void main(String args[]) throws IOException {
		if(args.length == 0) {
			Utils.print("No file specified");
		} else {
			DataLoader loader = new DataLoader();
			loader.loadData(args[0]);
			MainMenu menu = new MainMenu();
			menu.load();
		}
	}
}
