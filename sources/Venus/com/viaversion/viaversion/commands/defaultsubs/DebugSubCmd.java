/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.commands.defaultsubs;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.command.ViaCommandSender;
import com.viaversion.viaversion.api.command.ViaSubCommand;
import com.viaversion.viaversion.api.debug.DebugHandler;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class DebugSubCmd
extends ViaSubCommand {
    @Override
    public String name() {
        return "debug";
    }

    @Override
    public String description() {
        return "Toggle debug mode";
    }

    @Override
    public boolean execute(ViaCommandSender viaCommandSender, String[] stringArray) {
        DebugHandler debugHandler = Via.getManager().debugHandler();
        if (stringArray.length == 0) {
            Via.getManager().debugHandler().setEnabled(!Via.getManager().debugHandler().enabled());
            DebugSubCmd.sendMessage(viaCommandSender, "&6Debug mode is now %s", Via.getManager().debugHandler().enabled() ? "&aenabled" : "&cdisabled");
            return false;
        }
        if (stringArray.length == 1) {
            if (stringArray[0].equalsIgnoreCase("clear")) {
                debugHandler.clearPacketTypesToLog();
                DebugSubCmd.sendMessage(viaCommandSender, "&6Cleared packet types to log", new Object[0]);
                return false;
            }
            if (stringArray[0].equalsIgnoreCase("logposttransform")) {
                debugHandler.setLogPostPacketTransform(!debugHandler.logPostPacketTransform());
                DebugSubCmd.sendMessage(viaCommandSender, "&6Post transform packet logging is now %s", debugHandler.logPostPacketTransform() ? "&aenabled" : "&cdisabled");
                return false;
            }
        } else if (stringArray.length == 2) {
            if (stringArray[0].equalsIgnoreCase("add")) {
                debugHandler.addPacketTypeNameToLog(stringArray[5].toUpperCase(Locale.ROOT));
                DebugSubCmd.sendMessage(viaCommandSender, "&6Added packet type %s to debug logging", stringArray[5]);
                return false;
            }
            if (stringArray[0].equalsIgnoreCase("remove")) {
                debugHandler.removePacketTypeNameToLog(stringArray[5].toUpperCase(Locale.ROOT));
                DebugSubCmd.sendMessage(viaCommandSender, "&6Removed packet type %s from debug logging", stringArray[5]);
                return false;
            }
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(ViaCommandSender viaCommandSender, String[] stringArray) {
        if (stringArray.length == 1) {
            return Arrays.asList("clear", "logposttransform", "add", "remove");
        }
        return Collections.emptyList();
    }
}

