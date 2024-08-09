/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.network.login;

import net.minecraft.network.INetHandler;
import net.minecraft.network.login.server.SCustomPayloadLoginPacket;
import net.minecraft.network.login.server.SDisconnectLoginPacket;
import net.minecraft.network.login.server.SEnableCompressionPacket;
import net.minecraft.network.login.server.SEncryptionRequestPacket;
import net.minecraft.network.login.server.SLoginSuccessPacket;

public interface IClientLoginNetHandler
extends INetHandler {
    public void handleEncryptionRequest(SEncryptionRequestPacket var1);

    public void handleLoginSuccess(SLoginSuccessPacket var1);

    public void handleDisconnect(SDisconnectLoginPacket var1);

    public void handleEnableCompression(SEnableCompressionPacket var1);

    public void handleCustomPayloadLogin(SCustomPayloadLoginPacket var1);
}

