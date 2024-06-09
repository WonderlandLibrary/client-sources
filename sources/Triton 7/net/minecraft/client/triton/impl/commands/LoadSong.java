package net.minecraft.client.triton.impl.commands;

import java.io.File;
import java.io.FileReader;

import net.minecraft.client.Minecraft;
import net.minecraft.client.triton.impl.modules.misc.NoteBot;
import net.minecraft.client.triton.management.command.Com;
import net.minecraft.client.triton.management.command.Command;
import net.minecraft.client.triton.utils.ClientUtils;
import net.minecraft.util.Util;

@Com(names = { "loadsong" })
public class LoadSong extends Command {
	@Override
	public void runCommand(final String[] args) {
		if (args.length < 1) {
			ClientUtils.sendMessage(this.getHelp());
			return;
		}
		try {
			File file = null;
			if (Util.getOSType() == Util.EnumOS.LINUX) {
				file = new File(Minecraft.getMinecraft().mcDataDir + "/Triton/songs/" + args[1] + ".groovy");
			} else {
				file = new File(Minecraft.getMinecraft().mcDataDir + "\\Triton\\songs\\" + args[1] + ".groovy");
			}
			NoteBot.note = 0;
			NoteBot.notes.clear();
			NoteBot.groovyShell.evaluate(new FileReader(file));
			ClientUtils.sendMessage("The song " + file.getName() + " was loaded successfully.");
		} catch (Exception e) {
			ClientUtils.sendMessage(this.getHelp());
			e.printStackTrace();
		}
	}

	@Override
	public String getHelp() {
		return "LoadSong - loadsong <ls> songname <File name no .groovy>";
	}
}
