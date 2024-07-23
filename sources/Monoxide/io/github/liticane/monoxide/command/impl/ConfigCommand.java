package io.github.liticane.monoxide.command.impl;

import io.github.liticane.monoxide.command.Command;
import io.github.liticane.monoxide.command.data.CommandInfo;

@CommandInfo(name = "value", description = "change values")
public class ConfigCommand extends Command {

    @Override
    public boolean execute(String[] args) {
		return false;
	}

}
