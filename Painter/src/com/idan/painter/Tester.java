package com.idan.painter;

import javax.swing.SwingUtilities;

/**
 * Tester for the drawing application.
 * 
 * @author Idan Perry
 * @version 03.05.2020
 */

public class Tester {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {	
			
			@Override
			public void run() {
				new UserInterface();
			}
		});	
	}
}
