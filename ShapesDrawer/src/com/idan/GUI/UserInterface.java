package com.idan.GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import com.idan.constants.CustomColor;
import com.idan.constants.DrawingTool;
import com.idan.constants.Mode;
import com.idan.constants.SelectedShape;

/**
 * This class represents the user interface for the drawing application.
 * 
 * @author Idan Perry
 * @version 03.05.2020
 */

@SuppressWarnings("serial")
public class UserInterface extends JFrame implements ActionListener {
	private static final int WIDTH = 1100, HEIGHT = 700;
	private static final int THICKNESS_LEVELS = 5;
	private static final Dimension MIN_SIZE = new Dimension(730, 500);
	private static final Dimension COLOR_BTN_SIZE = new Dimension(20, 20);
	private static final Dimension COMBO_BOX_SIZE = new Dimension(50, 30);
	private static final EmptyBorder PANEL_BORDER = new EmptyBorder(10, 10, 10, 10);	
	private static final String[] THICKNESS_VISUAL = { "<html><font size=1>\u25CF</html>",
			"<html><font size=2>\u25CF</html>", "<html><font size=4>\u25CF</html>", "<html><font size=6>\u25CF</html>",
			"<html><font size=8>\u25CF</html>" };

	private final JPanel mainUiPanel;
	private final JPanel colorsPanel;
	private final JPanel shapesPanel;
	private final JPanel toolsPanel;
	private final JPanel editPanel;
	private final Canvas canvas;
	private JButton[] colorsBtn;
	private CustomButton[] shapesBtn;
	private CustomButton[] toolsBtn;
	private CustomButton fillColorBtn;
	private CustomButton undoBtn;
	private CustomButton redoBtn;
	private CustomButton clearBtn;
	private CustomButton modeBtn;
	private JComboBox<String> selectThickness;

	private boolean filled;
	private Mode mode;

	/**
	 * Constructs a user interface panel to contain all buttons and controllers for
	 * drawing and painting.
	 */
	public UserInterface() {
		mainUiPanel = new JPanel();
		editPanel = new JPanel(new GridLayout(2, 0, 2, 2));
		shapesPanel = new JPanel(new GridLayout(2, 4, 2, 2));
		colorsPanel = new JPanel(new GridLayout(5, 12, 2, 2));
		toolsPanel = new JPanel(new GridLayout(2, 0, 2, 2));
		canvas = new Canvas();

		// frame properties
		setTitle("Shapes Drawer");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(WIDTH, HEIGHT);
		setMinimumSize(MIN_SIZE);
		getContentPane().setBackground(CustomColor.LIGHTER_GRAY);
		setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/abstract.png")));

		initPanels();
		initEditTools();
		initShapes();
		initColors();
		initDrawingTools();
		setToolTips();
		validate();
		setVisible(true);
		
		mode = Mode.DRAW;
	}

	/*
	 * Initializes and customizes all panels in this frame. The main panel holds all
	 * other sub panels and is added to the north region of this frame.
	 */
	private void initPanels() {
		// set panels backgrounds
		mainUiPanel.setBackground(CustomColor.LIGHT_BLACK);
		editPanel.setBackground(CustomColor.LIGHT_BLACK);
		shapesPanel.setBackground(CustomColor.LIGHT_BLACK);
		colorsPanel.setBackground(CustomColor.LIGHT_BLACK);
		toolsPanel.setBackground(CustomColor.LIGHT_BLACK);

		// set panels borders
		mainUiPanel.setBorder(new LineBorder(CustomColor.LIGHT_GRAY));
		editPanel.setBorder(PANEL_BORDER);
		shapesPanel.setBorder(PANEL_BORDER);
		colorsPanel.setBorder(PANEL_BORDER);
		toolsPanel.setBorder(PANEL_BORDER);

		// add sub panels to main uiPanel
		mainUiPanel.add(editPanel);
		mainUiPanel.add(shapesPanel);
		mainUiPanel.add(toolsPanel);
		mainUiPanel.add(colorsPanel);

		// add main uiPanel and canvas panel to this frame
		add(mainUiPanel, BorderLayout.NORTH);
		add(canvas, BorderLayout.CENTER);
	}

	/*
	 * Initializes the edit tools panel and buttons.
	 */
	private void initEditTools() {
		undoBtn = new CustomButton("undo");
		redoBtn = new CustomButton("redo");
		clearBtn = new CustomButton("clear");
		modeBtn = new CustomButton("click");
		
		// add listeners
		undoBtn.addActionListener(this);
		redoBtn.addActionListener(this);
		clearBtn.addActionListener(this);
		modeBtn.addActionListener(this);
		
		// add edit tools to edit panel
		editPanel.add(undoBtn);
		editPanel.add(redoBtn);
		editPanel.add(clearBtn);
		editPanel.add(modeBtn);
	}

	/*
	 * Initializes and customizes the shapes buttons selection.
	 */
	private void initShapes() {
		shapesBtn = new CustomButton[SelectedShape.values().length];
		
		int i = 0;
		for (SelectedShape shape : SelectedShape.values()) {
			shapesBtn[i] = new CustomButton(shape.toString());
			shapesBtn[i].addActionListener(this);
			shapesPanel.add(shapesBtn[i++]);
		}
	}

	/*
	 * Initializes and customizes the colors buttons selection and the fill color
	 * button.
	 */
	private void initColors() {
		colorsBtn = new JButton[CustomColor.COLORS.length];

		for (int i = 0; i < CustomColor.COLORS.length; i++) {
			colorsBtn[i] = new JButton();
			colorsBtn[i].setPreferredSize(COLOR_BTN_SIZE);
			colorsBtn[i].setBackground(CustomColor.COLORS[i]);
			colorsBtn[i].setContentAreaFilled(false);
			colorsBtn[i].setOpaque(true);
			colorsBtn[i].setBorder(BorderFactory.createLineBorder(CustomColor.LIGHT_GRAY));
			colorsBtn[i].addActionListener(this);
			colorsPanel.add(colorsBtn[i]);

			colorsBtn[i].addMouseListener(new MouseHandler());
		}
	}

	/*
	 * Initializes and customizes the fill color button and the line thickness
	 * selection button.
	 */
	private void initDrawingTools() {
		toolsBtn = new CustomButton[DrawingTool.values().length];
		
		int i = 0;
		for(DrawingTool tool : DrawingTool.values()) {
			toolsBtn[i] = new CustomButton(tool.toString());
			toolsBtn[i].addActionListener(this);
			toolsPanel.add(toolsBtn[i++]);
		}

		// fill color button
		fillColorBtn = new CustomButton("fill");
		fillColorBtn.addActionListener(this);
		toolsPanel.add(fillColorBtn);

		// thickness select
		selectThickness = new JComboBox<String>(THICKNESS_VISUAL);
		selectThickness.setPreferredSize(COMBO_BOX_SIZE);
		selectThickness.setMaximumRowCount(THICKNESS_LEVELS);
		((JLabel) selectThickness.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
		selectThickness.addActionListener(this);
		toolsPanel.add(selectThickness);
	}

	/*
	 * Set tool tips text for all buttons and controlers in this interface.
	 */
	private void setToolTips() {
		shapesBtn[SelectedShape.RECTANGLE.getIndex()].setToolTipText("Paint a rectangle");
		shapesBtn[SelectedShape.ROUND_RECTANGLE.getIndex()].setToolTipText("Paint a round rectangle");
		shapesBtn[SelectedShape.OVAL.getIndex()].setToolTipText("Paint an oval");
		shapesBtn[SelectedShape.ISO_TRIANGLE.getIndex()].setToolTipText("Paint an isosceles triangle");
		shapesBtn[SelectedShape.RIGHT_TRIANGLE.getIndex()].setToolTipText("Paint a right triangle");
		shapesBtn[SelectedShape.HEXAGON.getIndex()].setToolTipText("paint an hexagon");
		shapesBtn[SelectedShape.LINE.getIndex()].setToolTipText("draw a line");
		shapesBtn[SelectedShape.FREE_HAND.getIndex()].setToolTipText("free hand drawing");
		fillColorBtn.setToolTipText("Fill with color");
		undoBtn.setToolTipText("Undo last painted shape");
		redoBtn.setToolTipText("Repaint last deleted shape");
		clearBtn.setToolTipText("Clear the canvas");
		modeBtn.setToolTipText("Switch between selection and drawing");
		selectThickness.setToolTipText("Select line thickness");
	}

	/*
	 * Check if one of the shapes selection buttons was clicked.
	 */
	private void selectShape(ActionEvent e) {
		for (SelectedShape shape : SelectedShape.values()) {
			shapesBtn[shape.getIndex()].setClicked(false);
			shapesBtn[shape.getIndex()].setBackground(CustomColor.LIGHT_GRAY);
			
			if (e.getSource() == shapesBtn[shape.getIndex()]) {
				shapesBtn[shape.getIndex()].setClicked(true);
				canvas.setSelectedShape(shape);
			}
		}
	}
	
	/*
	 * Check if one of the drawing tools buttons was clicked.
	 */
	private void selectTool(ActionEvent e) {
		// fill shape with color selection
		if (e.getSource() == fillColorBtn) {
			if (!filled) {
				fillColorBtn.setClicked(true);
				canvas.setIsFilled(filled = true);		

			} else {
				fillColorBtn.setClicked(false);
				canvas.setIsFilled(filled = false);
			}

			// line thickness selection
		} else if (e.getSource() == selectThickness) {
			for (int i = 0; i < THICKNESS_LEVELS; i++) {
				if (selectThickness.getSelectedIndex() == i)
					canvas.setThickness(i + 1);
			}
		}
	
		// drawing tools selection
		for (DrawingTool tool : DrawingTool.values()) {
			toolsBtn[tool.getIndex()].setClicked(false);
			toolsBtn[tool.getIndex()].setBackground(CustomColor.LIGHT_GRAY);
			
			if (e.getSource() == toolsBtn[tool.getIndex()]) {
				toolsBtn[tool.getIndex()].setClicked(true);
				canvas.setSelectedDrawingTool(tool);
			}
		}
	}
	
	/*
	 * Check if one of the color selection buttons was clicked.
	 */
	private void selectColor(ActionEvent e) {
		for (int i = 0; i < CustomColor.COLORS.length; i++) {
			if (e.getSource() == colorsBtn[i]) {
				canvas.setColor(CustomColor.COLORS[i]);
				selectThickness.setForeground(CustomColor.COLORS[i]);
				break;
			}
		}
	}
	
	/*
	 * Check if one of the edit buttons was clicked.
	 */
	private void selectEdit(ActionEvent e) {
		if (e.getSource() == undoBtn)
			canvas.remove();

		// redo last drawing
		else if (e.getSource() == redoBtn)
			canvas.restore();

		// clear all drawings from canvas
		else if (e.getSource() == clearBtn)
			canvas.clear();
		
		else if (e.getSource() == modeBtn) {
			mode = mode == Mode.SELECT? Mode.DRAW : Mode.SELECT;
			canvas.setMode(mode);
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {	
		selectColor(e);
		selectShape(e);		
		selectTool(e);
		selectEdit(e);
	}

	private class MouseHandler extends MouseAdapter{
		@Override
		public void mouseEntered(MouseEvent e) {
			for(JButton btn : colorsBtn) {
				if(e.getSource() == btn)
					btn.setBorder(BorderFactory.createLineBorder(CustomColor.WHITE));
			}
		}

		@Override
		public void mouseExited(MouseEvent e) {
			for(JButton btn : colorsBtn) {
				if(e.getSource() == btn)
					btn.setBorder(BorderFactory.createLineBorder(CustomColor.LIGHT_GRAY));
			}
		}
	}
}
