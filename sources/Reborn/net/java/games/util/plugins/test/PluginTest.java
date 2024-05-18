package net.java.games.util.plugins.test;

import net.java.games.util.plugins.*;
import java.io.*;
import java.awt.*;
import javax.swing.*;

public class PluginTest
{
    static final boolean DEBUG = false;
    Plugins plugins;
    JList plist;
    Class[] piList;
    
    public PluginTest() {
        try {
            this.plugins = new Plugins(new File("test_plugins"));
        }
        catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        final JFrame f = new JFrame("PluginTest");
        (this.plist = new JList((ListModel<E>)new DefaultListModel<Object>())).setCellRenderer(new ClassRenderer());
        final Container c = f.getContentPane();
        c.setLayout(new BorderLayout());
        c.add(new JScrollPane(this.plist), "Center");
        final JPanel p = new JPanel();
        c.add(p, "South");
        p.setLayout(new FlowLayout());
        f.pack();
        f.setDefaultCloseOperation(3);
        f.setVisible(true);
        this.doListAll();
    }
    
    private void doListAll() {
        SwingUtilities.invokeLater(new ListUpdater(this.plist, this.plugins.get()));
    }
    
    public static void main(final String[] args) {
        new PluginTest();
    }
}
