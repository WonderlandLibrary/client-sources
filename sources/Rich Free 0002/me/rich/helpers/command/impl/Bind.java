package me.rich.helpers.command.impl;

import org.lwjgl.input.Keyboard;

import me.rich.Main;
import me.rich.helpers.command.Command;
import me.rich.module.Feature;
import net.minecraft.util.text.TextFormatting;

public class Bind extends Command {
	public Bind() {
		super("Bind", new String[] { "bind", "b" });
	}

	@Override
	public void onCommand(String[] args) {
		for (Feature f : Main.moduleManager.getModules()) {
			if (args.length >= 2) {
				if (f.getName().equalsIgnoreCase(args[1])) {
					if (args[2].equalsIgnoreCase("null") || args[2].equalsIgnoreCase("none")) {
						f.setKey(0);
						Main.msg(TextFormatting.GRAY + f.getName().toLowerCase() + " unbinded" + ".", true);
					} else {
						f.setKey(Keyboard.getKeyIndex(args[2].toUpperCase()));
						Main.msg(TextFormatting.GRAY + f.getName().toLowerCase() + " binded to " + args[2].toLowerCase() + ".", true);
					}
					return;
				}
			}
		}
		Main.msg(TextFormatting.GRAY + "invalid message.", true);
	}
}