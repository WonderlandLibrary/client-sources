package com.wikihacks.drm;

import com.wikihacks.WikiHacks;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

/**
 * Obfuscated code so that crystalinnqq cant crack
 *
 * @author cookiedragon234 22/Apr/2020
 */
public class Drm{public static void checkUserLicense(){new PasswordForm(WikiHacks.validUsers);}}class PasswordForm extends Frame{private JLabel userLabel;private JTextField userText;private JLabel passwordLabel;private JPasswordField passwordText;private JButton loginButton;private static String user="User";private static String pass="Password";private static String login="login";private Map<String,String>validPassword;public PasswordForm(Map<String,String>validPassword){super("Password Form");this.validPassword=validPassword;setLayout(null);userLabel=new JLabel(user);userLabel.setBounds(10,20,80,25);add(userLabel);userText=new JTextField(20);userText.setBounds(100,20,165,25);add(userText);passwordLabel=new JLabel(pass);passwordLabel.setBounds(10,50,80,25);add(passwordLabel);passwordText=new JPasswordField(20);passwordText.setBounds(100,50,165,25);add(passwordText);loginButton=new JButton(login);loginButton.setBounds(10,80,80,25);add(loginButton);EpicActionListener handler=new EpicActionListener();userText.addActionListener(handler);passwordText.addActionListener(handler);loginButton.addActionListener(handler);setSize(350,200);setVisible(true);setAlwaysOnTop(true);}private class EpicActionListener implements ActionListener{public void actionPerformed(ActionEvent event){String user=userText.getText();String pass=new String(passwordText.getPassword());if(validPassword.get(user).equals(pass)){JOptionPane.showMessageDialog(null,"nice");}else{JOptionPane.showMessageDialog(null,"Sorry you are not authenticated. Please close minecraft as you do not own wiki hacks :tm:. Not closing minecraft will result in legal action");}}}}
