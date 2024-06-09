package me.protocol_client.commands.all;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

import net.minecraft.client.Minecraft;
import net.minecraft.util.MathHelper;
import me.protocol_client.Wrapper;
import me.protocol_client.commands.Command;

public class Coords extends Command {

	@Override
	public String getAlias() {
		return "GC";
	}

	@Override
	public String getDescription() {
		return "Copy Coordinates to clipboard";
	}

	@Override
	public String getSyntax() {
		return ".gc";
	}

	@Override
	public void onCommandSent(String command, String[] args) throws Exception {
		final Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		final int x = MathHelper.floor_double(Minecraft.getMinecraft().thePlayer.posX);
		final int y = MathHelper.floor_double(Minecraft.getMinecraft().thePlayer.posY);
		final int z = MathHelper.floor_double(Minecraft.getMinecraft().thePlayer.posZ);
		final StringSelection coords = new StringSelection(String.valueOf(x) + " " + y + " " + z);
		clipboard.setContents(coords, null);
		Wrapper.tellPlayer("\2477Coords copied to cliboard");
	}

}
