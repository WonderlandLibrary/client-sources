/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network.status;

import net.minecraft.network.INetHandler;
import net.minecraft.network.status.server.SPacketPong;
import net.minecraft.network.status.server.SPacketServerInfo;

public interface INetHandlerStatusClient
extends INetHandler {
    public void handleServerInfo(SPacketServerInfo var1);

    public void handlePong(SPacketPong var1);
}

