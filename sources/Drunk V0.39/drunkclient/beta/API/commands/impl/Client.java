/*
 * Decompiled with CFR 0.152.
 */
package drunkclient.beta.API.commands.impl;

import drunkclient.beta.API.commands.Command;
import drunkclient.beta.UTILS.helper.Helper;

public class Client
extends Command {
    public Client() {
        super("Client", new String[]{"cl"}, "", "");
    }

    @Override
    public String execute(String[] var1) {
        if (var1.length <= 0) {
            Helper.sendMessage("Correct Usage .cl desc <Name>");
            return null;
        }
        if (!var1[0].equalsIgnoreCase("desc")) {
            Helper.sendMessage("Correct Usage .cl desc <Name>");
            return null;
        }
        if (!var1[1].equalsIgnoreCase("clear")) {
            drunkclient.beta.Client.ClientDesc = var1[1];
            Helper.sendMessage("Setted Client Description to " + var1[1] + ", .cl des clear to delete description");
            return null;
        }
        drunkclient.beta.Client.ClientDesc = "";
        Helper.sendMessage("Deleted Desc");
        return null;
    }
}

