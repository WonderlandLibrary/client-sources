/*
 * Decompiled with CFR 0_122.
 */
package Monix.Commands.commands;

import Monix.Commands.Command;
import Monix.Monix;

public class Help
extends Command {
    @Override
    public String getAlias() {
        return "help";
    }

    @Override
    public String getSyntax() {
        return "-help";
    }

    @Override
    public void onCommand(String command, String[] args) throws Exception {
        Monix.addChatMessage("Add Bind: -bind [Mod] [Key]");
        Monix.addChatMessage("Remove Bind: -bind del [Key]");
    }
}

