/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network.handshake;

import net.minecraft.network.NetworkManager;
import net.minecraft.network.ProtocolType;
import net.minecraft.network.handshake.IHandshakeNetHandler;
import net.minecraft.network.handshake.client.CHandshakePacket;
import net.minecraft.network.login.ServerLoginNetHandler;
import net.minecraft.network.login.server.SDisconnectLoginPacket;
import net.minecraft.network.status.ServerStatusNetHandler;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.SharedConstants;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class ServerHandshakeNetHandler
implements IHandshakeNetHandler {
    private static final ITextComponent field_241169_a_ = new StringTextComponent("Ignoring status request");
    private final MinecraftServer server;
    private final NetworkManager networkManager;

    public ServerHandshakeNetHandler(MinecraftServer minecraftServer, NetworkManager networkManager) {
        this.server = minecraftServer;
        this.networkManager = networkManager;
    }

    @Override
    public void processHandshake(CHandshakePacket cHandshakePacket) {
        switch (1.$SwitchMap$net$minecraft$network$ProtocolType[cHandshakePacket.getRequestedState().ordinal()]) {
            case 1: {
                this.networkManager.setConnectionState(ProtocolType.LOGIN);
                if (cHandshakePacket.getProtocolVersion() != SharedConstants.getVersion().getProtocolVersion()) {
                    TranslationTextComponent translationTextComponent = cHandshakePacket.getProtocolVersion() < 754 ? new TranslationTextComponent("multiplayer.disconnect.outdated_client", SharedConstants.getVersion().getName()) : new TranslationTextComponent("multiplayer.disconnect.incompatible", SharedConstants.getVersion().getName());
                    this.networkManager.sendPacket(new SDisconnectLoginPacket(translationTextComponent));
                    this.networkManager.closeChannel(translationTextComponent);
                    break;
                }
                this.networkManager.setNetHandler(new ServerLoginNetHandler(this.server, this.networkManager));
                break;
            }
            case 2: {
                if (this.server.func_230541_aj_()) {
                    this.networkManager.setConnectionState(ProtocolType.STATUS);
                    this.networkManager.setNetHandler(new ServerStatusNetHandler(this.server, this.networkManager));
                    break;
                }
                this.networkManager.closeChannel(field_241169_a_);
                break;
            }
            default: {
                throw new UnsupportedOperationException("Invalid intention " + cHandshakePacket.getRequestedState());
            }
        }
    }

    @Override
    public void onDisconnect(ITextComponent iTextComponent) {
    }

    @Override
    public NetworkManager getNetworkManager() {
        return this.networkManager;
    }
}

