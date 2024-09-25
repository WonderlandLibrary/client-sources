package eze.access;

import javax.swing.*;
import java.awt.*;

public class DisplayValid extends JFrame
{
    JLabel information;
    
    public DisplayValid() {
        super("Valid key");
        this.information = new JLabel("The key is valid ! You can now use private modules.");
        this.setSize(500, 500);
        this.setDefaultCloseOperation(1);
        this.setVisible(true);
        final Container container = this.getContentPane();
        final FlowLayout flm = new FlowLayout();
        container.setLayout(flm);
        container.add(this.information);
        this.setContentPane(container);
    }
}
