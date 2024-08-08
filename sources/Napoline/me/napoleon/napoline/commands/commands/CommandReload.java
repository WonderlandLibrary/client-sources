package me.napoleon.napoline.commands.commands;

import me.napoleon.napoline.commands.Command;
import me.napoleon.napoline.utils.json.JsonUtil;

public class CommandReload extends Command {
    public CommandReload() {
        super("reload");
    }

    @Override
    public void run(String[] args) {
        JsonUtil.load();

    }
}
