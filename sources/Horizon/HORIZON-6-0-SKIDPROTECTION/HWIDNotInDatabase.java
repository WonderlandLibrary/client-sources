package HORIZON-6-0-SKIDPROTECTION;

import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.StringSelection;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import java.awt.Component;
import java.awt.Font;
import javax.swing.JLabel;
import java.awt.LayoutManager;
import java.awt.Container;
import java.awt.Color;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.JPanel;
import javax.swing.JFrame;

public class HWIDNotInDatabase extends JFrame
{
    private JPanel HorizonCode_Horizon_È;
    
    public HWIDNotInDatabase() {
        this.setResizable(false);
        this.setTitle("HWID NOT IN DATABASE");
        this.setDefaultCloseOperation(3);
        this.setBounds(100, 100, 774, 414);
        (this.HorizonCode_Horizon_È = new JPanel()).setToolTipText("");
        this.HorizonCode_Horizon_È.setName("HWID NOT IN DATABASE");
        this.HorizonCode_Horizon_È.setBorder(new EmptyBorder(5, 5, 5, 5));
        this.HorizonCode_Horizon_È.setBackground(new Color(16, 16, 16));
        this.setContentPane(this.HorizonCode_Horizon_È);
        this.HorizonCode_Horizon_È.setLayout(null);
        final JLabel lblNotInDatabase = new JLabel("Not in Database");
        lblNotInDatabase.setFont(new Font("Segoe UI", 0, 55));
        lblNotInDatabase.setForeground(Color.WHITE);
        lblNotInDatabase.setBounds(188, 11, 392, 74);
        this.HorizonCode_Horizon_È.add(lblNotInDatabase);
        final JLabel lblYourHwid = new JLabel("Your HWID");
        lblYourHwid.setForeground(Color.WHITE);
        lblYourHwid.setFont(new Font("Segoe UI", 0, 25));
        lblYourHwid.setBounds(321, 96, 125, 48);
        this.HorizonCode_Horizon_È.add(lblYourHwid);
        final JLabel label = new JLabel(HWID.HorizonCode_Horizon_È);
        label.setFont(new Font("Segoe UI", 0, 30));
        label.setForeground(Color.WHITE);
        label.setBounds(50, 168, 667, 48);
        this.HorizonCode_Horizon_È.add(label);
        final JButton btnCopyHwidTo = new JButton("Copy HWID to Clipboard");
        btnCopyHwidTo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent arg0) {
                final Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                final StringSelection str = new StringSelection(HWID.HorizonCode_Horizon_È);
                clipboard.setContents(str, null);
                System.exit(0);
            }
        });
        btnCopyHwidTo.setFont(new Font("Segoe UI", 0, 19));
        btnCopyHwidTo.setForeground(Color.GRAY);
        btnCopyHwidTo.setBackground(new Color(30, 30, 30, 30));
        btnCopyHwidTo.setBounds(264, 252, 239, 48);
        this.HorizonCode_Horizon_È.add(btnCopyHwidTo);
    }
}
