package me.gishreload.yukon.gui.account;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import me.gishreload.yukon.Meanings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;

public class Account extends JFrame{

	JButton done;
	JLabel l1, l3, l4;
	JTextField f1;
	String s1, a;
	String key;
	eHandler handler = new eHandler();
	
	public Account(String s){
		super(s);
		setLayout(new FlowLayout());
		done = new JButton("Done");
		l1 = new JLabel("Key:");
		f1 = new JTextField(15);
		l3 = new JLabel("ERROR enter the correct key");
		add(l1);
		add(f1);
		add(done);
		add(l3);
		done.addActionListener(handler);
	}
	
	public class eHandler implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			try{	
			key = String.valueOf(f1.getText());
			if(key.contains("ec922")){
				if(e.getSource()==done){
					a = "Ñonfirmed the key";
					l3.setText(a);
					Meanings.AccountGui = false;
				}
			}
			if(key.contains("ramm4")){
				if(e.getSource()==done){
					a = "Ñonfirmed the key";
					l3.setText(a);
					Meanings.AccountGui = false;
					Meanings.rammstein = true;
				}
			}
			}catch (Exception ex){ JOptionPane.showMessageDialog(null, "ERROR | Type in the number of."); }
		}
	}
}
