/*
 * Decompiled with CFR 0.150.
 */
package us.myles.ViaVersion.api.command;

import java.util.Collections;
import java.util.List;
import us.myles.ViaVersion.api.command.ViaCommandSender;
import us.myles.ViaVersion.commands.ViaCommandHandler;

public abstract class ViaSubCommand {
    public abstract String name();

    public abstract String description();

    public String usage() {
        return this.name();
    }

    public String permission() {
        return "viaversion.admin";
    }

    public abstract boolean execute(ViaCommandSender var1, String[] var2);

    public List<String> onTabComplete(ViaCommandSender sender, String[] args) {
        return Collections.emptyList();
    }

    public String color(String s) {
        return ViaCommandHandler.color(s);
    }

    public void sendMessage(ViaCommandSender sender, String message, Object ... args) {
        ViaCommandHandler.sendMessage(sender, message, args);
    }
}

