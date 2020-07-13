package com.idan.GUI;

import com.idan.constants.CustomColor;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JTextField;

public class CustomTextField extends JTextField {
    private static final Dimension SIZE = new Dimension(50, 22);
    private static final Font FONT = new Font("SansSerif", Font.PLAIN, 13);

    public CustomTextField() {
        setPreferredSize(SIZE);
        setForeground(CustomColor.LIGHT_GRAY);
        setBackground(CustomColor.DARK_GRAY);
        setCaretColor(CustomColor.LIGHT_GRAY);
        setFont(FONT);
    }

    @Override
    public void setEnabled(boolean bool) {
        super.setEnabled(bool);
        if(!bool)
            setText("");
    }
}
