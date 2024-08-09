/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network.login;

import net.minecraft.network.INetHandler;
import net.minecraft.network.login.client.CCustomPayloadLoginPacket;
import net.minecraft.network.login.client.CEncryptionResponsePacket;
import net.minecraft.network.login.client.CLoginStartPacket;

public interface IServerLoginNetHandler
extends INetHandler {
    public void processLoginStart(CLoginStartPacket var1);

    public void processEncryptionResponse(CEncryptionResponsePacket var1);

    public void processCustomPayloadLogin(CCustomPayloadLoginPacket var1);
}

