package net.minecraft.client.network.login;

import net.minecraft.network.INetHandler;
import net.minecraft.network.login.server.*;

public interface IClientLoginNetHandler extends INetHandler
{
    void handleEncryptionRequest(SEncryptionRequestPacket packetIn);

    void handleLoginSuccess(SLoginSuccessPacket packetIn);

    void handleDisconnect(SDisconnectLoginPacket packetIn);

    void handleEnableCompression(SEnableCompressionPacket packetIn);

    void handleCustomPayloadLogin(SCustomPayloadLoginPacket packetIn);
}
