package me.superskidder.lune.commands.commands;

import me.superskidder.lune.commands.Command;
import me.superskidder.lune.utils.json.JsonUtil;

public class CommandReload extends Command {
    public CommandReload() {
        super("reload");
    }

    @Override
    public void run(String[] args) {
        JsonUtil.load();

    }
}
