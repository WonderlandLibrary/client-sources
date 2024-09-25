/*
 * Decompiled with CFR 0.150.
 */
package us.myles.ViaVersion.api.command;

import java.util.UUID;

public interface ViaCommandSender {
    public boolean hasPermission(String var1);

    public void sendMessage(String var1);

    public UUID getUUID();

    public String getName();
}

