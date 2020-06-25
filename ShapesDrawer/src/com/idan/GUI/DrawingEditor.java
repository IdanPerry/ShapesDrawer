package com.idan.GUI;

import com.idan.drawables.BoundedShape;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.idan.constants.CustomColor;
import com.idan.drawables.Shape;

/**
 * This class represents an editor which control a selected drawn shape properties.
 * 
 * @author Idan Perry
 * @version 22.06.2020
 */

@SuppressWarnings("serial")
public class DrawingEditor extends JFrame implements ActionListener {
	private static final int WIDTH = 270;
	private static final int HEIGHT = 210;
	private static final int SIZE_FIELDS = 2;
	private static final int COLOR_FIELDS = 5;
	private static final Dimension FIELD_SIZE = new Dimension(50, 22); 
	private static final Dimension BTN_SIZE = new Dimension(80, 25); 
	private static final Font LABEL_FONT = new Font("SansSerif", Font.PLAIN, 13);
	private static final Font BTN_FONT = new Font("SansSerif", Font.BOLD, 13);
	
	private final JPanel sizeFlowPanel;
	private final JPanel sizeGridPanel;
	private final JPanel colorFlowPanel;
	private final JPanel colorGridPanel;
	private final JPanel submitPanel;
	private JLabel[] sizeLabels;
	private JLabel[] colorLabels;
	private JTextField[] sizeFields;
	private JTextField[] colorFields;
	private JLabel fillLabel;
	private JCheckBox fillCheck;
	private CustomButton submitBtn;
	private final Canvas canvas;
	private final Shape shape;
	
	/**
	 * Constructs an editor which control a selected drawn shape properties.
	 * 
	 * @param canvas the canvas which contains the selected drawing
	 * @param shape the shape to chage and control its properties
	 */
	public DrawingEditor(Canvas canvas, Shape shape) {
		super("Change drawing properties");
		this.canvas = canvas;
		this.shape = shape;
		sizeFlowPanel = new JPanel();
		sizeGridPanel = new JPanel(new GridLayout(SIZE_FIELDS, 2, 2, 2));
		colorFlowPanel = new JPanel();
		colorGridPanel = new JPanel(new GridLayout(COLOR_FIELDS, 2, 2, 2));
		submitPanel = new JPanel();
		
		// frame properties
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setSize(WIDTH, HEIGHT);
		setResizable(false);
		getContentPane().setBackground(CustomColor.LIGHT_BLACK);
		setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/abstract.png")));
		setLocationRelativeTo(canvas);
			
		initSizeComponents();
		initColorComponents();
		initSubmitBtn();
		initPanels();
		
		setVisible(true);
	}
	
	/*
	 * Initializes and customizes the components of the shape size properties.
	 */
	private void initSizeComponents() {
		sizeLabels = new JLabel[SIZE_FIELDS];
		sizeFields = new JTextField[SIZE_FIELDS];
		
		for(int i = 0; i < SIZE_FIELDS; i++) {
			// labels
			sizeLabels[i] = new JLabel();
			sizeLabels[i].setForeground(CustomColor.WHITE);
			sizeLabels[i].setFont(LABEL_FONT);
			// text fields
			sizeFields[i] = new JTextField();
			sizeFields[i].setPreferredSize(FIELD_SIZE);
			sizeFields[i].setForeground(CustomColor.WHITE);
			sizeFields[i].setBackground(CustomColor.DARK_GRAY);
			sizeFields[i].setFont(LABEL_FONT);
			sizeFields[i].setCaretColor(CustomColor.WHITE);
			// panel
			sizeGridPanel.add(sizeLabels[i]);
			sizeGridPanel.add(sizeFields[i]);
		}	
		
		sizeLabels[0].setText("Width: ");
		sizeLabels[1].setText("Height: ");
		
		sizeFields[0].setText(shape.getDestX() + "");
		sizeFields[1].setText(shape.getDestY() + "");
	}
	
	/*
	 * Initializes and customizes the components of the shape color properties.
	 */
	private void initColorComponents() {
		colorLabels = new JLabel[COLOR_FIELDS];
		colorFields = new JTextField[COLOR_FIELDS];
		fillLabel = new JLabel("Fill color: ");
		fillCheck = new JCheckBox();
		
		for(int i = 0; i < COLOR_FIELDS - 1; i++) {
			// labels
			colorLabels[i] = new JLabel();
			colorLabels[i].setForeground(CustomColor.WHITE);
			colorLabels[i].setFont(LABEL_FONT);
			// text fields
			colorFields[i] = new JTextField();
			colorFields[i].setPreferredSize(FIELD_SIZE);
			colorFields[i].setForeground(CustomColor.WHITE);
			colorFields[i].setBackground(CustomColor.DARK_GRAY);
			colorFields[i].setFont(LABEL_FONT);
			colorFields[i].setCaretColor(CustomColor.WHITE);
			// panel
			colorGridPanel.add(colorLabels[i]);
			colorGridPanel.add(colorFields[i]);
		}

		// check box
		fillLabel.setForeground(CustomColor.WHITE);
		fillLabel.setFont(LABEL_FONT);
		fillCheck.setBackground(CustomColor.LIGHT_BLACK);

		if(shape instanceof BoundedShape)
			fillCheck.setSelected(((BoundedShape)shape).isFilled());
		else
			fillCheck.setEnabled(false);

		colorGridPanel.add(fillLabel);
		colorGridPanel.add(fillCheck);

		colorLabels[0].setText("Red: ");
		colorLabels[1].setText("Green: ");
		colorLabels[2].setText("Blue: ");
		colorLabels[3].setText("Opaque: ");
		
		colorFields[0].setText(shape.getColor().getRed() + "");
		colorFields[1].setText(shape.getColor().getGreen() + "");
		colorFields[2].setText(shape.getColor().getBlue() + "");
		colorFields[3].setText(shape.getColor().getAlpha() + "");
	}
	
	/*
	 * Initializes and customizes the panels of this editor.
	 */
	private void initPanels() {
		sizeFlowPanel.setBackground(CustomColor.LIGHT_BLACK);
		sizeGridPanel.setBackground(CustomColor.LIGHT_BLACK);
		colorFlowPanel.setBackground(CustomColor.LIGHT_BLACK);
		colorGridPanel.setBackground(CustomColor.LIGHT_BLACK);
		submitPanel.setBackground(CustomColor.LIGHT_BLACK);
		
		sizeFlowPanel.add(sizeGridPanel);
		colorFlowPanel.add(colorGridPanel);
		submitPanel.add(submitBtn);
		add(sizeFlowPanel, BorderLayout.WEST);
		add(colorFlowPanel, BorderLayout.EAST);
		add(submitPanel, BorderLayout.SOUTH);
	}
	
	/*
	 * Initializes and customizes the submit button of this editor.
	 */
	private void initSubmitBtn() {
		submitBtn = new CustomButton();
		submitBtn.addActionListener(this);
		submitBtn.setPreferredSize(BTN_SIZE);
		submitBtn.setForeground(CustomColor.LIGHT_BLACK);
		submitBtn.setFont(BTN_FONT);
		submitBtn.setText("Submit");
	}
	
	/*
	 * Apply the size properties from the user input.
	 */
	private void applyNewSize() {
		int width = Integer.parseInt(sizeFields[0].getText());
		int height = Integer.parseInt(sizeFields[1].getText());
		shape.setDestX(width);
		shape.setDestY(height);
	}
	
	/*
	 * Apply the color properties from the user input.
	 */
	private void applyNewColor() {
		int r = Integer.parseInt(colorFields[0].getText());
		int g = Integer.parseInt(colorFields[1].getText());
		int b = Integer.parseInt(colorFields[2].getText());
		int alpha = Integer.parseInt(colorFields[3].getText());
		
		Color color = new Color(r, g, b, alpha);
		shape.setColor(color);

		if(shape instanceof BoundedShape)
			((BoundedShape)shape).setFilled(fillCheck.isSelected());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		applyNewSize();
		applyNewColor();
		canvas.repaint();
		dispose();
	}
}
