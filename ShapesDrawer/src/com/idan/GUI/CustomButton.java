package com.idan.GUI;

import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

import com.idan.constants.CustomColor;

/**
 * This class represents a modified JButton component.
 * 
 * @author Idan Perry
 * @version 22.06.2020
 */

@SuppressWarnings("serial")
public class CustomButton extends JButton implements MouseListener {
	private static final Dimension SIZE = new Dimension(30, 30);
	private static final EmptyBorder BORDER = new EmptyBorder(-10, -10, -10, -10);
	
	private boolean clicked;
	
	/**
	 * Constructs a modified JButton component with an associated icon name which
	 * is part of the icon url path. will throw IOException if the icon wasn't found
	 * in the resources folder.
	 * 
	 * @param iconPath the associated icon name
	 * @throws IOException if the associated icon wasn't found in the resources folder
	 */
	public CustomButton(String iconPath) {	
		setPreferredSize(SIZE);
		setBackground(CustomColor.LIGHT_GRAY);
		setBorder(new CompoundBorder(getBorder(), BORDER));
		setContentAreaFilled(false);
		setOpaque(true);
		addMouseListener(this);
		
		try {
			ImageIcon icon = new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/" + iconPath + ".png")));
			setIcon(icon);
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
	/**
	 * Constructs a modified JButton component.
	 */
	public CustomButton() {
		setPreferredSize(SIZE);
		setBackground(CustomColor.LIGHT_GRAY);
		setBorder(new CompoundBorder(getBorder(), BORDER));
		setContentAreaFilled(false);
		setOpaque(true);
		addMouseListener(this);
	}
	
	/**
	 * Changes the state of the button (clicked or not clicked)
	 * to control its appearance.
	 * 
	 * @param clicked true to signal the button was clicked. false otherwise
	 */
	public void setClicked(boolean clicked) {
		this.clicked = clicked;
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		setBackground(CustomColor.LIGHTER_GRAY);	
	}

	@Override
	public void mouseExited(MouseEvent e) {
		if(clicked)
			setBackground(CustomColor.LIGHTER_GRAY);	
		else
			setBackground(CustomColor.LIGHT_GRAY);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		setBackground(CustomColor.GRAY);
	}

	@Override
	public void mouseReleased(MouseEvent e) {	
		if(clicked)
			setBackground(CustomColor.LIGHTER_GRAY);	
		else
			setBackground(CustomColor.LIGHT_GRAY);
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {		
	}
}
