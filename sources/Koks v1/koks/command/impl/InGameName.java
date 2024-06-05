package koks.command.impl;

import koks.command.Command;

import java.awt.*;
import java.awt.datatransfer.StringSelection;

/**
 * @author avox | lmao | kroko
 * @created on 07.09.2020 : 20:50
 */
public class InGameName extends Command {

    public InGameName() {
        super("InGameName", "ign");
    }

    @Override
    public void execute(String[] args) {
        String s = mc.thePlayer.getName();
        sendmsg("Copied " + s, true);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(s), null);
    }

}