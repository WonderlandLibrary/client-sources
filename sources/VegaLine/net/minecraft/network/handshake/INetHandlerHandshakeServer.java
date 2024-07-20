/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network.handshake;

import net.minecraft.network.INetHandler;
import net.minecraft.network.handshake.client.C00Handshake;

public interface INetHandlerHandshakeServer
extends INetHandler {
    public void processHandshake(C00Handshake var1);
}

