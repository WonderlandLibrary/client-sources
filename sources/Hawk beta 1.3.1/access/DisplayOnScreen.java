package eze.access;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import eze.*;
import java.io.*;

public class DisplayOnScreen extends JFrame implements ActionListener
{
    JButton button;
    JTextField text;
    JLabel information;
    public static String KeyTyped;
    public static String CorrectKey;
    
    static {
        DisplayOnScreen.CorrectKey = "RFWU4OTRHUY49HUGNRE5HUOIU5JY048764UGTDH";
    }
    
    public DisplayOnScreen() {
        super("Register key for premium modules (private)");
        this.button = new JButton("Register key");
        this.text = new JTextField("", 25);
        this.information = new JLabel("If you want to access to all settings and all modules, enter the key here");
        this.setSize(500, 500);
        this.setDefaultCloseOperation(1);
        this.setVisible(true);
        final Container container = this.getContentPane();
        final FlowLayout flm = new FlowLayout();
        container.setLayout(flm);
        this.button.addActionListener(this);
        container.add(this.information);
        container.add(this.text);
        container.add(this.button);
        this.setContentPane(container);
    }
    
    @Override
    public void actionPerformed(final ActionEvent evenement) {
        DisplayOnScreen.KeyTyped = this.text.getText();
        if (DisplayOnScreen.KeyTyped.equals(DisplayOnScreen.CorrectKey)) {
            new DisplayValid();
            Client.IsRegistered = true;
            try {
                final PrintWriter printer = new PrintWriter(new FileOutputStream("gie5hg8u4toihu45.hawkclient"));
                printer.print(DisplayOnScreen.CorrectKey);
                printer.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            new DisplayInvalid();
        }
    }
}
