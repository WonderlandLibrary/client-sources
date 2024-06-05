/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.input.Keyboard
 */
package drunkclient.beta.API.commands.impl;

import drunkclient.beta.API.commands.Command;
import drunkclient.beta.Client;
import drunkclient.beta.IMPL.Module.Module;
import drunkclient.beta.UTILS.helper.Helper;
import net.minecraft.util.EnumChatFormatting;
import org.lwjgl.input.Keyboard;

public class Bind
extends Command {
    public Bind() {
        super("Bind", new String[]{"b"}, "", "");
    }

    @Override
    public String execute(String[] args) {
        if (args.length < 2) {
            Helper.sendMessage("Correct usage .bind <module> <key>");
            return null;
        }
        Module m = Client.instance.getModuleManager().getAlias(args[0]);
        if (m == null) {
            Helper.sendMessage("Module name " + (Object)((Object)EnumChatFormatting.RED) + args[0] + (Object)((Object)EnumChatFormatting.GRAY) + " is invalid");
            return null;
        }
        int k = Keyboard.getKeyIndex((String)args[1].toUpperCase());
        m.setKey(k);
        Object[] arrobject = new Object[]{m.getName(), k == 0 ? "none" : args[1].toUpperCase()};
        Helper.sendMessage(String.format("Bound %s to %s", arrobject));
        return null;
    }
}

