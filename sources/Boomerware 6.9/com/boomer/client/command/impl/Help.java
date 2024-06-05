package com.boomer.client.command.impl;

import org.apache.commons.lang3.text.WordUtils;

import com.boomer.client.Client;
import com.boomer.client.command.Command;
import com.boomer.client.utils.Printer;

public class Help extends Command {

	public Help() {
		super("Help", new String[]{"h", "help"});
	}

	@Override
	public void onRun(final String[] s) {
		Client.INSTANCE.getCommandManager().getCommandMap().values().forEach(command -> {
			Printer.print(WordUtils.capitalizeFully(command.getLabel()));
		});
	}
}
