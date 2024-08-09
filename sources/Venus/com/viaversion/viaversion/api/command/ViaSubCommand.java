/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.api.command;

import com.viaversion.viaversion.api.command.ViaCommandSender;
import com.viaversion.viaversion.util.ChatColorUtil;
import java.util.Collections;
import java.util.List;

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

    public List<String> onTabComplete(ViaCommandSender viaCommandSender, String[] stringArray) {
        return Collections.emptyList();
    }

    public static String color(String string) {
        return ChatColorUtil.translateAlternateColorCodes(string);
    }

    public static void sendMessage(ViaCommandSender viaCommandSender, String string, Object ... objectArray) {
        viaCommandSender.sendMessage(ViaSubCommand.color(objectArray == null ? string : String.format(string, objectArray)));
    }
}

