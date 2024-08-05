package fr.dog.command.impl;

import fr.dog.command.Command;
import fr.dog.util.player.ChatUtil;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

public class IgnCommand extends Command {
    public IgnCommand() {
        super("ign", "n");
    }
    @Override
    public void execute(String[] args, String message) {

        StringSelection selection = new StringSelection(mc.thePlayer.getGameProfile().getName());
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(selection, selection);


        ChatUtil.display("Your IGN is now in your clipboard !");
    }
}
