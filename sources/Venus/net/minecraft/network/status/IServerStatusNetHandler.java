/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network.status;

import net.minecraft.network.INetHandler;
import net.minecraft.network.status.client.CPingPacket;
import net.minecraft.network.status.client.CServerQueryPacket;

public interface IServerStatusNetHandler
extends INetHandler {
    public void processPing(CPingPacket var1);

    public void processServerQuery(CServerQueryPacket var1);
}

