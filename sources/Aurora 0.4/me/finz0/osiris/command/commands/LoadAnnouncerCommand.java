package me.finz0.osiris.command.commands;

import me.finz0.osiris.AuroraMod;
import me.finz0.osiris.command.Command;

public class LoadAnnouncerCommand extends Command {
    @Override
    public String[] getAlias() {
        return new String[]{
                "loadannouncer"
        };
    }

    @Override
    public String getSyntax() {
        return "loadannouncer";
    }

    @Override
    public void onCommand(String command, String[] args) throws Exception {
        AuroraMod.getInstance().configUtils.loadAnnouncer();
        sendClientMessage("Loaded Announcer file");
    }
}
