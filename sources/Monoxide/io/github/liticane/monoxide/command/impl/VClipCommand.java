package io.github.liticane.monoxide.command.impl;

import io.github.liticane.monoxide.command.data.CommandInfo;
import io.github.liticane.monoxide.command.Command;

@CommandInfo(name = "vclip", description = "clip vertically")
public class VClipCommand extends Command {
    @Override
    public boolean execute(String[] args) {
        if (args.length == 1) {
            try {
                setPosition(getX(), getY() + Double.parseDouble(args[0]), getZ());
            } catch (NumberFormatException e) {
                sendMessage("§cPlease write a number!");
            }
            return true;
        } else
            return false;
    }
}