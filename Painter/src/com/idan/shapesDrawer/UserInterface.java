package com.idan.shapesDrawer;

import static com.idan.shapesDrawer.SelectedShape.*;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

/**
 * This class represents the user interface for the drawing application.
 * 
 * @author Idan Perry
 * @version 03.05.2020
 */

@SuppressWarnings("serial")
public class UserInterface extends JFrame implements ActionListener, MouseListener {
	private static final int FRAME_WIDTH = 1100, FRAME_HEIGHT = 700;
	private static final int MAX_SHAPES = 8;
	private static final int MAX_COLORS = 36;
	private static final int SHAPE_BTN_SIZE_W = 38, SHAPE_BTN_SIZE_H = 38;
	private static final int COLOR_BTN_SIZE = 24;

	// undo, clear canvas and fill - marks on buttons surface
	private static final String UNDO_SYMBOL = "\u2BA8";
	private static final String CLEAR_SYMBOL = "\u2718";
	private static final String FILL_SYMBOL = "\u2728";
	private static final String[] THICKNESS_VISUAL = { "<html><font size=1>\u25CF</html>",
			"<html><font size=2>\u25CF</html>", "<html><font size=4>\u25CF</html>", "<html><font size=6>\u25CF</html>",
			"<html><font size=8>\u25CF</html>" };
	private static final int THICKNESS_LEVELS = 5;
	private static final String[] DRAWING_TOOLS_VISUAL = { "Pen", "Brush" };

	// dimention constants
	private static final Dimension EDIT_BTN_SIZE = new Dimension(60, 25);
	private static final Dimension COMBO_BOX_SIZE = new Dimension(70, 50);
	private static final Dimension FILL_BTN_SIZE = new Dimension(50, 50);

	// fonts constants
	private static final Font UNDO_BTN_FONT = new Font("", Font.BOLD, 30);
	private static final Font CLEAR_BTN_FONT = new Font("", Font.BOLD, 24);
	private static final Font SHAPES_BTN_FONT = new Font("", Font.PLAIN, 26);
	private static final Font FILL_BTN_FONT = new Font("", Font.BOLD, 28);

	// border constants
	private static final EmptyBorder PANEL_BORDER = new EmptyBorder(10, 10, 10, 10);
	private static final EmptyBorder BTN_BORDER = new EmptyBorder(-10, -10, -10, -10);

	private final JPanel mainUiPanel;
	private final JPanel colorsPanel;
	private final JPanel shapesPanel;
	private final JPanel toolsPanel;
	private final JPanel editPanel;
	private final Canvas canvas;
	private final JButton[] colorsBtn;
	private final JButton[] shapesBtn;
	private final JButton fillColorBtn;
	private final JButton undoBtn;
	private final JButton clearCanvasBtn;
	private final JComboBox<String> selectThickness;
	private final JComboBox<String> drawingTool;
	private boolean filled;

	/**
	 * Constructs a user interface panel to contain all buttons and controllers for
	 * drawing and painting.
	 */
	public UserInterface() {
		mainUiPanel = new JPanel();
		editPanel = new JPanel(new GridLayout(2, 1, 2, 2));
		shapesPanel = new JPanel(new GridLayout(2, 4, 2, 2));
		colorsPanel = new JPanel(new GridLayout(3, 12, 2, 2));
		toolsPanel = new JPanel();
		canvas = new Canvas();
		shapesBtn = new JButton[MAX_SHAPES];
		colorsBtn = new JButton[MAX_COLORS];
		fillColorBtn = new JButton();
		undoBtn = new JButton();
		clearCanvasBtn = new JButton();
		selectThickness = new JComboBox<String>(THICKNESS_VISUAL);
		drawingTool = new JComboBox<String>(DRAWING_TOOLS_VISUAL);

		// frame properties
		setTitle("Shapes Drawer");
		setLayout(new BorderLayout(20, 20));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(FRAME_WIDTH, FRAME_HEIGHT);
		getContentPane().setBackground(CustomColor.CREAM);
		setVisible(true);

		initPanels();
		initEditTools();
		initShapes();
		initColors();
		initDrawingTools();
		validate();
	}

	/*
	 * Initializes and customizes all panels in this frame. The main panel holds all
	 * other sub panels and is added to the north region of this frame.
	 */
	private void initPanels() {
		// set panels backgrounds
		mainUiPanel.setBackground(CustomColor.LIGHT_PEACH);
		editPanel.setBackground(CustomColor.LIGHT_PEACH);
		shapesPanel.setBackground(CustomColor.LIGHT_PEACH);
		colorsPanel.setBackground(CustomColor.LIGHT_PEACH);
		toolsPanel.setBackground(CustomColor.LIGHT_PEACH);

		// set panels borders
		mainUiPanel.setBorder(new LineBorder(CustomColor.LIGHT_ORANGE));
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
	 * Initializes and customizes the undo and clear buttons. The undo button
	 * undrowing shapes one by one and the clear button removes all drawings from
	 * canvas.
	 */
	private void initEditTools() {
		// undo button properties
		undoBtn.setPreferredSize(EDIT_BTN_SIZE);
		undoBtn.setBackground(CustomColor.LIGHT_CREAM);
		undoBtn.setForeground(CustomColor.LIGHT_BLUE);
		undoBtn.setBorder(new CompoundBorder(undoBtn.getBorder(), BTN_BORDER));
		undoBtn.setFont(UNDO_BTN_FONT);
		undoBtn.setText(UNDO_SYMBOL);
		undoBtn.addActionListener(this);
		undoBtn.addMouseListener(this);

		// clear button properties
		clearCanvasBtn.setPreferredSize(EDIT_BTN_SIZE);
		clearCanvasBtn.setBackground(CustomColor.LIGHT_CREAM);
		clearCanvasBtn.setForeground(CustomColor.RED);
		clearCanvasBtn.setBorder(new CompoundBorder(clearCanvasBtn.getBorder(), BTN_BORDER));
		clearCanvasBtn.setFont(CLEAR_BTN_FONT);
		clearCanvasBtn.setText(CLEAR_SYMBOL);
		clearCanvasBtn.addActionListener(this);
		clearCanvasBtn.addMouseListener(this);

		// add edit tools to panel
		editPanel.add(undoBtn);
		editPanel.add(clearCanvasBtn);
	}

	/*
	 * Initializes and customizes the shapes buttons selection.
	 */
	private void initShapes() {
		int i = 0;
		for (SelectedShape shape : SelectedShape.values()) {
			shapesBtn[i] = new JButton();
			shapesBtn[i].setPreferredSize(new Dimension(SHAPE_BTN_SIZE_W, SHAPE_BTN_SIZE_H));
			shapesBtn[i].setFont(SHAPES_BTN_FONT);
			shapesBtn[i].setBackground(CustomColor.LIGHT_CREAM);
			shapesBtn[i].setForeground(CustomColor.DARK_ORANGE);
			shapesBtn[i].setBorder(new CompoundBorder(shapesBtn[i].getBorder(), BTN_BORDER));
			shapesBtn[i].setText(shape.toString());
			shapesBtn[i].addActionListener(this);
			shapesBtn[i].addMouseListener(this);
			shapesPanel.add(shapesBtn[i]);
			i++;
		}
	}

	/*
	 * Initializes and customizes the colors buttons selection and the fill color
	 * button.
	 */
	private void initColors() {
		for (int i = 0; i < MAX_COLORS; i++) {
			colorsBtn[i] = new JButton();
			colorsBtn[i].setPreferredSize(new Dimension(COLOR_BTN_SIZE, COLOR_BTN_SIZE));
			colorsBtn[i].setBackground(CustomColor.COLORS[i]);
			colorsBtn[i].addActionListener(this);
			colorsPanel.add(colorsBtn[i]);
		}
	}

	/*
	 * Initializes and customizes the fill color button and the line thickness
	 * selection button.
	 */
	private void initDrawingTools() {
		drawingTool.setPreferredSize(COMBO_BOX_SIZE);
		drawingTool.setBackground(CustomColor.LIGHT_CREAM);
		((JLabel) drawingTool.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
		drawingTool.addActionListener(this);
		toolsPanel.add(drawingTool);

		// fill color button properties
		fillColorBtn.setPreferredSize(FILL_BTN_SIZE);
		fillColorBtn.setBackground(CustomColor.LIGHT_CREAM);
		fillColorBtn.setBorder(new CompoundBorder(fillColorBtn.getBorder(), BTN_BORDER));
		fillColorBtn.setFont(FILL_BTN_FONT);
		fillColorBtn.setText(FILL_SYMBOL);
		fillColorBtn.addActionListener(this);
		fillColorBtn.addMouseListener(this);
		toolsPanel.add(fillColorBtn);

		// thickness select properties
		selectThickness.setBackground(CustomColor.LIGHT_CREAM);
		selectThickness.setPreferredSize(COMBO_BOX_SIZE);
		selectThickness.setMaximumRowCount(THICKNESS_LEVELS);
		((JLabel) selectThickness.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
		selectThickness.addActionListener(this);
		toolsPanel.add(selectThickness);
	}

	/*
	 * Reset the shapes buttons to its unpressed station color.
	 */
	private void resetBtnsBackgroundColor() {
		for (JButton btn : shapesBtn)
			btn.setBackground(CustomColor.LIGHT_CREAM);
	}

	/*
	 * Set the pressed button to a darker color to distinguish it from other
	 * buttons. Parameter btn the JButton that was pressed.
	 */
	private void setPressedBtnColor(JButton btn) {
		btn.setBackground(CustomColor.LIGHT_ORANGE);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// rectangle drawing
		if (e.getSource() == shapesBtn[RECTENGLE.getIndex()]) {
			resetBtnsBackgroundColor();
			setPressedBtnColor(shapesBtn[RECTENGLE.getIndex()]);
			canvas.setSelectedShape(RECTENGLE);
		}

		// round rectangle drawing
		if (e.getSource() == shapesBtn[ROUND_RECTANGLE.getIndex()]) {
			resetBtnsBackgroundColor();
			setPressedBtnColor(shapesBtn[ROUND_RECTANGLE.getIndex()]);
			canvas.setSelectedShape(ROUND_RECTANGLE);
		}

		// oval drawing
		if (e.getSource() == shapesBtn[OVAL.getIndex()]) {
			resetBtnsBackgroundColor();
			setPressedBtnColor(shapesBtn[OVAL.getIndex()]);
			canvas.setSelectedShape(OVAL);
		}

		// isosceles triangle drawing
		if (e.getSource() == shapesBtn[ISO_TRIANGLE.getIndex()]) {
			resetBtnsBackgroundColor();
			setPressedBtnColor(shapesBtn[ISO_TRIANGLE.getIndex()]);
			canvas.setSelectedShape(ISO_TRIANGLE);
		}

		// right triangle drawing
		if (e.getSource() == shapesBtn[RIGHT_TRIANGLE.getIndex()]) {
			resetBtnsBackgroundColor();
			setPressedBtnColor(shapesBtn[RIGHT_TRIANGLE.getIndex()]);
			canvas.setSelectedShape(RIGHT_TRIANGLE);
		}

		// line drawing
		if (e.getSource() == shapesBtn[LINE.getIndex()]) {
			resetBtnsBackgroundColor();
			setPressedBtnColor(shapesBtn[LINE.getIndex()]);
			canvas.setSelectedShape(LINE);
		}

		// hexagon drawing
		if (e.getSource() == shapesBtn[HEXAGON.getIndex()]) {
			resetBtnsBackgroundColor();
			setPressedBtnColor(shapesBtn[HEXAGON.getIndex()]);
			canvas.setSelectedShape(HEXAGON);
		}

		// free hand drawing
		if (e.getSource() == shapesBtn[FREE_HAND.getIndex()]) {
			resetBtnsBackgroundColor();
			setPressedBtnColor(shapesBtn[FREE_HAND.getIndex()]);
			canvas.setSelectedShape(FREE_HAND);
		}

		// fill shape with color selection
		if (e.getSource() == fillColorBtn) {
			if (!filled) {
				fillColorBtn.setBackground(CustomColor.LIGHT_ORANGE);
				canvas.setIsFilled(true);
				filled = true;

			} else {
				fillColorBtn.setBackground(CustomColor.LIGHT_CREAM);
				canvas.setIsFilled(false);
				filled = false;
			}
		}

		// line thickness selection
		if (e.getSource() == selectThickness) {
			for (int i = 0; i < THICKNESS_LEVELS; i++) {
				if (selectThickness.getSelectedIndex() == i)
					canvas.setThickness(i + 1);
			}
		}

		// free hand drawing tool selection
		if (e.getSource() == drawingTool) {
			if (drawingTool.getSelectedIndex() == 0)
				canvas.setSelectedDrawingTool(DrawingType.LINES);

			else if (drawingTool.getSelectedIndex() == 1)
				canvas.setSelectedDrawingTool(DrawingType.OVALS);
		}

		// undo drawings
		if (e.getSource() == undoBtn && !canvas.getShapesList().isEmpty()) {
			canvas.getShapesList().remove(canvas.getShapesList().size() - 1);
			canvas.repaint();
		}

		// clear all shapes from canvas
		if (e.getSource() == clearCanvasBtn) {
			canvas.getShapesList().clear();
			canvas.repaint();
		}

		// drawing color selection
		for (int i = 0; i < MAX_COLORS; i++) {
			if (e.getSource() == colorsBtn[i]) {
				canvas.setColor(CustomColor.COLORS[i]);
				fillColorBtn.setForeground(CustomColor.COLORS[i]);
			}
		}
	}

	/*
	 * Sets tool tips text popup for each button.
	 */
	@Override
	public void mouseEntered(MouseEvent e) {
		if (e.getSource() == shapesBtn[RECTENGLE.getIndex()])
			shapesBtn[0].setToolTipText("Paint a rectangle");

		if (e.getSource() == shapesBtn[ROUND_RECTANGLE.getIndex()])
			shapesBtn[1].setToolTipText("Paint a round rectangle");

		if (e.getSource() == shapesBtn[OVAL.getIndex()])
			shapesBtn[2].setToolTipText("Paint an oval");

		if (e.getSource() == shapesBtn[ISO_TRIANGLE.getIndex()])
			shapesBtn[3].setToolTipText("Paint an isosceles triangle");

		if (e.getSource() == shapesBtn[RIGHT_TRIANGLE.getIndex()])
			shapesBtn[4].setToolTipText("Paint a right triangle");

		if (e.getSource() == shapesBtn[HEXAGON.getIndex()])
			shapesBtn[5].setToolTipText("paint an hexagon");

		if (e.getSource() == shapesBtn[LINE.getIndex()])
			shapesBtn[6].setToolTipText("draw a line");

		if (e.getSource() == shapesBtn[FREE_HAND.getIndex()])
			shapesBtn[7].setToolTipText("free hand drawing");

		if (e.getSource() == fillColorBtn)
			fillColorBtn.setToolTipText("Fill with color");

		if (e.getSource() == undoBtn)
			undoBtn.setToolTipText("Undo last painted shape");

		if (e.getSource() == clearCanvasBtn)
			clearCanvasBtn.setToolTipText("Clear the canvas");

		if (e.getSource() == selectThickness)
			selectThickness.setToolTipText("Select line thickness");

		if (e.getSource() == drawingTool)
			drawingTool.setToolTipText("Select free hand drawing tool");
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}
}
