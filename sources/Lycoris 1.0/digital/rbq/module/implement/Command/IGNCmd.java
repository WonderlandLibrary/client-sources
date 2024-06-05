package digital.rbq.module.implement.Command;

import digital.rbq.Lycoris;
import digital.rbq.module.Command;
import digital.rbq.utility.ChatUtils;
import digital.rbq.utility.PlayerUtils;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;


@Command.Info(name = "ign", syntax = { "" }, help = "Copy your in game name.")
public class IGNCmd extends Command {
    @Override
    public void execute(String[] args) throws Error {
        if (mc.thePlayer != null) {
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            String text = mc.thePlayer.getName();
            Transferable trans = new StringSelection(text);
            clipboard.setContents(trans, null);
            PlayerUtils.tellPlayer("Copied!");
        } else {
            PlayerUtils.tellPlayer("Failed to get in game name!");
        }
    }
}
