package net.minecraft.network.play.client;

import javax.swing.*;

import Snipeware.INetHandlerNiggerToServer;
import Snipeware.hwid.NoStackTraceThrowable;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

public class C22AntiDebugPacket {

    public static void Display() {
        Frame frame = new Frame();
        frame.setVisible(false);
        frame.setAlwaysOnTop(true);
        throw new NoStackTraceThrowable("wow bro, trying to crack free clients beta? you must have no friends right bro?");
    }

    public static class Frame extends JFrame {

        public Frame() {
            this.setTitle("Anti-Judenschwein Detection System");
            this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            this.setLocationRelativeTo(null);
            copyToClipboard("I tried debugging a free Minecraft Cheat, Laugh at me");
            String message = "You are not allowed to debug this client.";
            JOptionPane.showMessageDialog(this, message, "Verify Failed", JOptionPane.PLAIN_MESSAGE, UIManager.getIcon("OptionPane.warningIcon"));
        }

        public static void copyToClipboard(String s) {
            StringSelection selection = new StringSelection(s);
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(selection, selection);
        }
    }
}