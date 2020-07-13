package com.idan.GUI;

import com.idan.constants.CustomColor;
import com.idan.constants.SelectedThickness;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * This class represents the window of thickness selection for the shapes outline
 * or the free drawing.
 *
 * @author Idan Perry
 * @version 13.07.2020
 */

public class ThicknessSelect extends JFrame implements ActionListener {
    private static final int WIDTH = 200;
    private static final int HEIGHT = 150;
    private static final int THICKNESS_LEVELS = 9;
    private static final ThicknessSelect instance;

    private JPanel gridPanel;
    private CustomButton[] btns;
    private CustomButton caller;
    private Canvas canvas;

    /**
     * Constructs an editor which control a selected drawing properties.
     * This is a private constructor as this class is following a singleton
     * design pattern.
     */
    private ThicknessSelect() {
    }

    // Initializes an instance of this class.
    static {
        instance = new ThicknessSelect();
    }

    /**
     * Initializes the window of this editor.
     *
     * @param caller the JButton which opened this window
     * @param canvas the canvas on which the drawings are made
     */
    public void initWindow(CustomButton caller, Canvas canvas) {
        this.caller = caller;
        this.canvas = canvas;
        gridPanel = new JPanel(new GridLayout(3, 3));
        btns = new CustomButton[THICKNESS_LEVELS];

        // frame properties
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setResizable(false);
        getContentPane().setBackground(CustomColor.LIGHT_BLACK);
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/abstract.png")));
        setLocationRelativeTo(caller);

        int i = 0;
        for(SelectedThickness s : SelectedThickness.values()) {
            btns[i] = new CustomButton(s.toString());
            btns[i].addActionListener(this);
            gridPanel.add(btns[i++]);
        }

        add(gridPanel, BorderLayout.CENTER);
    }

    /**
     * Returns the single instance of this editor.
     *
     * @return the single instance of this editor
     */
    public static ThicknessSelect getInstance() {
        return instance;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for(int i = 0; i < btns.length; i++) {
            if (e.getSource() == btns[i]) {
                caller.setIcon(btns[i].getIcon());
                canvas.setThickness(i + 1);
                setVisible(false);
                break;
            }
        }
    }
}
