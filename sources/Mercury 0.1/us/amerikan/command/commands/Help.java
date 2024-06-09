/*
 * Decompiled with CFR 0.145.
 */
package us.amerikan.command.commands;

import us.amerikan.command.Command;

public class Help
extends Command {
    public Help() {
        super("help", "Helps");
    }

    @Override
    public void execute(String[] args) {
        if (args.length != 1) {
            Help.messageWithPrefix(":\nType \u00a7a.toggle\u00a7f to Toggle a Module.\n\nType \u00a7a.bind\u00a7f to Bind a Module.\n\nType \u00a7a.autosettings\u00a7f to load pre-made configs. \n\nType \u00a7a.friend\u00a7f to add/remove friends.");
            return;
        }
    }
}

