/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.velocity.command.subs;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.command.ViaCommandSender;
import com.viaversion.viaversion.api.command.ViaSubCommand;
import com.viaversion.viaversion.velocity.platform.VelocityViaConfig;
import com.viaversion.viaversion.velocity.service.ProtocolDetectorService;

public class ProbeSubCmd
extends ViaSubCommand {
    @Override
    public String name() {
        return "probe";
    }

    @Override
    public String description() {
        String string;
        StringBuilder stringBuilder = new StringBuilder().append("Forces ViaVersion to scan server protocol versions ");
        if (((VelocityViaConfig)Via.getConfig()).getVelocityPingInterval() == -1) {
            string = "";
            return stringBuilder.append(string).toString();
        }
        string = "(Also happens at an interval)";
        return stringBuilder.append(string).toString();
    }

    @Override
    public boolean execute(ViaCommandSender sender, String[] args) {
        ProtocolDetectorService.getInstance().run();
        ProbeSubCmd.sendMessage(sender, "&6Started searching for protocol versions", new Object[0]);
        return true;
    }
}

