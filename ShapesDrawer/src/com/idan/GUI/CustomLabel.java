package com.idan.GUI;

import com.idan.constants.CustomColor;
import java.awt.Font;
import javax.swing.JLabel;

public class CustomLabel extends JLabel {
    private static final Font FONT = new Font("SansSerif", Font.PLAIN, 13);

    public CustomLabel() {
        setFont(FONT);
        setForeground(CustomColor.LIGHT_GRAY);
    }

    public CustomLabel(String text) {
        super(text);
        setFont(FONT);
        setForeground(CustomColor.LIGHT_GRAY);
    }

    /**
     * Changes the appearance of this component to hint whether
     * the text field attached to this component is active or not.
     *
     * @param active the state of the text field attached to this component
     */
    public void setActive(boolean active) {
        if (active)
            setForeground(CustomColor.LIGHT_GRAY);
        else
            setForeground(CustomColor.GRAY);
    }
}
