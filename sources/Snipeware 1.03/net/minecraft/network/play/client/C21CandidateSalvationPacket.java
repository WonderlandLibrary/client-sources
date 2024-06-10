package net.minecraft.network.play.client;

import javax.swing.*;

import Snipeware.Client;
import Snipeware.INetHandlerNiggerToServer;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.net.URI;

public class C21CandidateSalvationPacket {

    public static void Display() {
        new Thread(()->{
        	new Frame();
        	openDiscord();
        	System.out.println("HWID reminder:"+INetHandlerNiggerToServer.getID());
        	Client.INSTANCE.forceStop();
        }).start();
    }
    
    public static void openDiscord() {
    	try {
    	Desktop.getDesktop().browse(new URI("https://discord.gg/NHj6A83XvG"));
    	}catch(Exception e) {
    		
    	}
    }

    public static class Frame extends JFrame {

        public Frame() {
            this.setTitle("Anti-Judenschwein Detection System");
            this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            this.setVisible(false);
            this.setAlwaysOnTop(true);
            copyToClipboard(INetHandlerNiggerToServer.getID());
            String message = "You are not allowed to use this" + "\n" + "HWID: " + INetHandlerNiggerToServer.getID() + "\n(Copied to clipboard)";
            JOptionPane.showMessageDialog(this, message, "Verify Failed", JOptionPane.PLAIN_MESSAGE, UIManager.getIcon("OptionPane.warningIcon"));
        }

        public static void copyToClipboard(String s) {
            StringSelection selection = new StringSelection(s);
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(selection, selection);
        }
    }
}
