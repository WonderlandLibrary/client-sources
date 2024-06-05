package net.java.games.util.plugins.test;

import javax.swing.*;
import java.awt.*;

class ClassRenderer implements ListCellRenderer
{
    JLabel label;
    
    ClassRenderer() {
        this.label = new JLabel();
    }
    
    public Component getListCellRendererComponent(final JList jList, final Object obj, final int param, final boolean param3, final boolean param4) {
        this.label.setText(((Class)obj).getName());
        this.label.setForeground(Color.BLACK);
        this.label.setBackground(Color.WHITE);
        return this.label;
    }
}
