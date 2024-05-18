package me.finz0.osiris.command.commands;

import me.finz0.osiris.AuroraMod;
import me.finz0.osiris.command.Command;
import me.finz0.osiris.module.modules.chat.Spammer;

public class LoadSpammerCommand extends Command {
    @Override
    public String[] getAlias() {
        return new String[]{"loadspammer"};
    }

    @Override
    public String getSyntax() {
        return "loadspammer";
    }

    @Override
    public void onCommand(String command, String[] args) throws Exception {
        Spammer.text.clear();
        AuroraMod.getInstance().configUtils.loadSpammer();
        Command.sendClientMessage("Loaded Spammer File");
    }
}
