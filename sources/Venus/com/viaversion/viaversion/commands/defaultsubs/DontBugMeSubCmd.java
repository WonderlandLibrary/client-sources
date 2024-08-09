/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.commands.defaultsubs;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.command.ViaCommandSender;
import com.viaversion.viaversion.api.command.ViaSubCommand;
import com.viaversion.viaversion.api.configuration.ConfigurationProvider;

public class DontBugMeSubCmd
extends ViaSubCommand {
    @Override
    public String name() {
        return "dontbugme";
    }

    @Override
    public String description() {
        return "Toggle checking for updates";
    }

    @Override
    public boolean execute(ViaCommandSender viaCommandSender, String[] stringArray) {
        ConfigurationProvider configurationProvider = Via.getPlatform().getConfigurationProvider();
        boolean bl = !Via.getConfig().isCheckForUpdates();
        Via.getConfig().setCheckForUpdates(bl);
        configurationProvider.saveConfig();
        DontBugMeSubCmd.sendMessage(viaCommandSender, "&6We will %snotify you about updates.", bl ? "&a" : "&cnot ");
        return false;
    }
}

