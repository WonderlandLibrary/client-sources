package koks.command.impl;

import koks.Koks;
import koks.command.Command;

/**
 * @author avox | lmao | kroko
 * @created on 05.09.2020 : 19:32
 */
public class Reload extends Command {

    public Reload() {
        super("Reload", "rl");
    }

    @Override
    public void execute(String[] args) {
        if (args.length != 0)
            return;

        sendmsg("Shutting down Client", true);
        Koks.getKoks().shutdownClient();
        sendmsg("Initialize Client", true);
        Koks.getKoks().initClient();
    }

}