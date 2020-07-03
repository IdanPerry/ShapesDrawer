package com.idan.GUI;

import com.idan.drawables.BoundedShape;
import com.idan.utils.Geometry;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.idan.constants.CustomColor;
import com.idan.drawables.Shape;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * This class represents an editor which control a selected drawing properties.
 *
 * @author Idan Perry
 * @version 22.06.2020
 */

@SuppressWarnings("serial")
public class DrawingEditor extends JFrame implements ActionListener, DocumentListener, ItemListener {
    private static final int WIDTH = 320;
    private static final int HEIGHT = 210;
    private static final int SIZE_FIELDS = 5;
    private static final int COLOR_FIELDS = 5;
    private static final Dimension BTN_SIZE = new Dimension(80, 25);
    private static final Font BTN_FONT = new Font("SansSerif", Font.BOLD, 13);

    private static DrawingEditor instance;
    private JPanel sizeFlowPanel;
    private JPanel sizeGridPanel;
    private JPanel colorFlowPanel;
    private JPanel colorGridPanel;
    private JPanel submitPanel;
    private CustomLabel[] sizeLabels;
    private CustomLabel[] colorLabels;
    private CustomTextField[] sizeFields;
    private CustomTextField[] colorFields;
    private CustomLabel fillLabel;
    private CustomLabel ratioLabel;
    private JCheckBox fillCheck;
    private JCheckBox ratioCheck;
    private CustomButton submitBtn;
    private Canvas canvas;
    private Shape drawing;
    private int ratio;

    /**
     * Constructs an editor which control a selected drawing properties.
     * This is a private constructor as this class is following a singleton
     * design pattern.
     */
    private DrawingEditor() {
    }

    // Initializes an instance of this class.
    static {
        instance = new DrawingEditor();
    }

    /**
     * Initializes this editor components.
     *
     * @param canvas the component which initializes this editor
     */
    public void initEditor(Canvas canvas) {
        this.canvas = canvas;
        sizeFlowPanel = new JPanel();
        colorFlowPanel = new JPanel();
        sizeGridPanel = new JPanel(new GridLayout(SIZE_FIELDS, 2, 2, 2));
        colorGridPanel = new JPanel(new GridLayout(COLOR_FIELDS, 2, 2, 2));
        submitPanel = new JPanel();

        // frame properties
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setResizable(false);
        getContentPane().setBackground(CustomColor.LIGHT_BLACK);
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/abstract.png")));
        setLocationRelativeTo(canvas);

        initSizeComponents();
        initColorComponents();
        initCheckBox();
        initSubmitBtn();
        initPanels();
        pack();
    }

    /**
     * Returns the single instance of this editor.
     *
     * @return the single instance of this editor
     */
    public static DrawingEditor getInstance() {
        return instance;
    }

    public void setDrawing(Shape drawing) {
        this.drawing = drawing;
    }

    public void setSize() {
        // init width and height components for bounded shapes
        if (drawing instanceof BoundedShape) {
            sizeLabels[2].setActive(false);
            sizeFields[0].setText(drawing.getDestX() + "");
            sizeFields[1].setText(drawing.getDestY() + "");
            sizeFields[2].setEnabled(false);

            // init length for lines
        } else {
            sizeLabels[0].setActive(false);
            sizeLabels[1].setActive(false);
            sizeFields[0].setEnabled(false);
            sizeFields[1].setEnabled(false);
            sizeFields[2].setText((int) Geometry.distance(new Point(drawing.getOriginX(), drawing.getOriginY()),
                    new Point(drawing.getDestX(), drawing.getDestY())) + "");
        }
    }

    /**
     * init fields with colors
     */
    public void setColor() {
        colorFields[0].setText(drawing.getColor().getRed() + "");
        colorFields[1].setText(drawing.getColor().getGreen() + "");
        colorFields[2].setText(drawing.getColor().getBlue() + "");
        colorFields[3].setText(drawing.getColor().getAlpha() + "");

        if (drawing instanceof BoundedShape) {
            fillCheck.setSelected(((BoundedShape) drawing).isFilled());
        } else {
            fillLabel.setActive(false);
            fillCheck.setEnabled(false);
            ratioLabel.setActive(false);
            ratioCheck.setEnabled(false);
        }
    }

    /*
     * Initializes and customizes the components of the shape size properties.
     */
    private void initSizeComponents() {
        sizeLabels = new CustomLabel[SIZE_FIELDS];
        sizeFields = new CustomTextField[SIZE_FIELDS];

        for (int i = 0; i < SIZE_FIELDS - 1; i++) {
            sizeLabels[i] = new CustomLabel();
            sizeFields[i] = new CustomTextField();
            sizeFields[i].getDocument().addDocumentListener(this);
            // add to panel
            sizeGridPanel.add(sizeLabels[i]);
            sizeGridPanel.add(sizeFields[i]);
        }

        // set label texts
        sizeLabels[0].setText("Width");
        sizeLabels[1].setText("Height");
        sizeLabels[2].setText("Length");
        sizeLabels[3].setText("Thick");
    }

    /*
     * Initializes and customizes the components of the shape color properties.
     */
    private void initColorComponents() {
        colorLabels = new CustomLabel[COLOR_FIELDS];
        colorFields = new CustomTextField[COLOR_FIELDS];

        for (int i = 0; i < COLOR_FIELDS - 1; i++) {
            colorLabels[i] = new CustomLabel();
            colorFields[i] = new CustomTextField();
            // add to panel
            colorGridPanel.add(colorLabels[i]);
            colorGridPanel.add(colorFields[i]);
        }

        // set labels texts
        colorLabels[0].setText("Red");
        colorLabels[1].setText("Green");
        colorLabels[2].setText("Blue");
        colorLabels[3].setText("Alpha");
    }

    /*
     * Initializes and customizes the fill color checkbox.
     */
    private void initCheckBox() {
        fillCheck = new JCheckBox();
        ratioCheck = new JCheckBox();
        fillLabel = new CustomLabel("Fill");
        ratioLabel = new CustomLabel("Ratio");
        ratioCheck.addItemListener(this);

        // customize check boxes
        fillCheck.setBackground(CustomColor.LIGHT_BLACK);
        ratioCheck.setBackground(CustomColor.LIGHT_BLACK);

        // add to panels
        colorGridPanel.add(fillLabel);
        colorGridPanel.add(fillCheck);
        sizeGridPanel.add(ratioLabel);
        sizeGridPanel.add(ratioCheck);
    }

    /*
     * Initializes and customizes the panels of this editor.
     */
    private void initPanels() {
        // background color
        sizeFlowPanel.setBackground(CustomColor.LIGHT_BLACK);
        sizeGridPanel.setBackground(CustomColor.LIGHT_BLACK);
        colorFlowPanel.setBackground(CustomColor.LIGHT_BLACK);
        colorGridPanel.setBackground(CustomColor.LIGHT_BLACK);
        submitPanel.setBackground(CustomColor.LIGHT_BLACK);

        // border
        sizeFlowPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        colorFlowPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        // build hierarchy
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
        try {
            if (drawing instanceof BoundedShape) {
                int width = Integer.parseInt(sizeFields[0].getText());
                int height = Integer.parseInt(sizeFields[1].getText());
                drawing.setDestX(width);
                drawing.setDestY(height);
            } else {
                int length = Integer.parseInt(sizeFields[2].getText());

            }
        } catch (NumberFormatException e) {
            // do nothing
        }
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
        drawing.setColor(color);

        if (drawing instanceof BoundedShape)
            ((BoundedShape) drawing).setFilled(fillCheck.isSelected());
    }

    /*
     * Keeps the ratio between width and height when resizing.
     */
    private void keepAspectRatio(DocumentEvent e) {
        if (ratioCheck != null && ratioCheck.isSelected()) {
            try {
                int width = Integer.parseInt(sizeFields[0].getText());
                int height = Integer.parseInt(sizeFields[1].getText());

                // while changing width
                if (e.getDocument() == sizeFields[0].getDocument()) {
                    int x = width > height ? (height * ratio) : (width * ratio);
                    System.out.println(x);
//				sizeFields[1].setText((int)x + "");
                }

                // while changing height
                if (e.getDocument() == sizeFields[1].getDocument()) {
                    int x = height > width ? width * ratio : height * ratio;
                    System.out.println(x);
//				sizeFields[0].setText((int)x + "");
                }

            } catch (NumberFormatException ex) {
                // do nothing
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
            applyNewSize();
            applyNewColor();
            canvas.repaint();
            setVisible(false);
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if(ratioCheck.isSelected()) {
            int width = Integer.parseInt(sizeFields[0].getText());
            int height = Integer.parseInt(sizeFields[1].getText());
            ratio = width > height ? width / height : height / width;
        }
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        keepAspectRatio(e);
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        keepAspectRatio(e);
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        keepAspectRatio(e);
    }
}
