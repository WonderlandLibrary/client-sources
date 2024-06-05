package win.sightclient.cmd.commands;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

import win.sightclient.Sight;
import win.sightclient.cmd.Command;
import win.sightclient.utils.minecraft.ChatUtils;

public class NameCommand extends Command {

	public NameCommand() {
		super(new String[] {"name", "n"});
	}

	@Override
	public void onCommand(String message) {
		if (mc.thePlayer == null) {
			return;
		}
		ChatUtils.sendMessage("Copied your name to clipboard.");
		StringSelection stringSelection = new StringSelection(mc.thePlayer.getName());
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.setContents(stringSelection, null);
	}
}
