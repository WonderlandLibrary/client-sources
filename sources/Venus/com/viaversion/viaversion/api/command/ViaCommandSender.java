/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.api.command;

import java.util.UUID;

public interface ViaCommandSender {
    public boolean hasPermission(String var1);

    public void sendMessage(String var1);

    public UUID getUUID();

    public String getName();
}

