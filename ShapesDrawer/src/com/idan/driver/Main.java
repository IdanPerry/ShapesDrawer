package com.idan.driver;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.idan.GUI.UserInterface;

/**
 * Main driver for the shapes drawing application.
 * 
 * @author Idan Perry
 * @version 03.05.2020
 */

public class Main {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {	
			try {
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
					| UnsupportedLookAndFeelException e) {
				e.printStackTrace();
			}
				
			new UserInterface();
		});	
	}
}
