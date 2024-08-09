/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.protocols.base;

import com.google.common.base.Joiner;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.ProtocolInfo;
import com.viaversion.viaversion.api.protocol.AbstractProtocol;
import com.viaversion.viaversion.api.protocol.ProtocolPathEntry;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.packet.State;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import com.viaversion.viaversion.api.protocol.version.VersionProvider;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.libs.gson.JsonParseException;
import com.viaversion.viaversion.protocol.ProtocolManagerImpl;
import com.viaversion.viaversion.protocol.ServerProtocolVersionSingleton;
import com.viaversion.viaversion.protocols.base.BaseProtocol;
import com.viaversion.viaversion.protocols.base.ClientboundLoginPackets;
import com.viaversion.viaversion.protocols.base.ClientboundStatusPackets;
import com.viaversion.viaversion.protocols.base.ServerboundLoginPackets;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.Protocol1_9To1_8;
import com.viaversion.viaversion.util.ChatColorUtil;
import com.viaversion.viaversion.util.GsonUtil;
import io.netty.channel.ChannelFuture;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;

public class BaseProtocol1_7
extends AbstractProtocol {
    @Override
    protected void registerPackets() {
        this.registerClientbound(ClientboundStatusPackets.STATUS_RESPONSE, new PacketHandlers(this){
            final BaseProtocol1_7 this$0;
            {
                this.this$0 = baseProtocol1_7;
            }

            @Override
            public void register() {
                this.map(Type.STRING);
                this.handler(1::lambda$register$0);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                ProtocolInfo protocolInfo = packetWrapper.user().getProtocolInfo();
                String string = packetWrapper.get(Type.STRING, 0);
                try {
                    Object object;
                    JsonObject jsonObject;
                    JsonElement jsonElement = GsonUtil.getGson().fromJson(string, JsonElement.class);
                    int n = 0;
                    if (jsonElement.isJsonObject()) {
                        if (jsonElement.getAsJsonObject().has("version")) {
                            jsonObject = jsonElement.getAsJsonObject().get("version").getAsJsonObject();
                            if (jsonObject.has("protocol")) {
                                n = Long.valueOf(jsonObject.get("protocol").getAsLong()).intValue();
                            }
                        } else {
                            jsonObject = new JsonObject();
                            jsonElement.getAsJsonObject().add("version", jsonObject);
                        }
                    } else {
                        jsonElement = new JsonObject();
                        jsonObject = new JsonObject();
                        jsonElement.getAsJsonObject().add("version", jsonObject);
                    }
                    if (Via.getConfig().isSendSupportedVersions()) {
                        jsonObject.add("supportedVersions", GsonUtil.getGson().toJsonTree(Via.getAPI().getSupportedVersions()));
                    }
                    if (!Via.getAPI().getServerVersion().isKnown()) {
                        object = (ProtocolManagerImpl)Via.getManager().getProtocolManager();
                        ((ProtocolManagerImpl)object).setServerProtocol(new ServerProtocolVersionSingleton(ProtocolVersion.getProtocol(n).getVersion()));
                    }
                    if ((object = Via.getManager().getProviders().get(VersionProvider.class)) == null) {
                        packetWrapper.user().setActive(false);
                        return;
                    }
                    int n2 = object.getClosestServerProtocol(packetWrapper.user());
                    List<ProtocolPathEntry> list = null;
                    if (protocolInfo.getProtocolVersion() >= n2 || Via.getPlatform().isOldClientsAllowed()) {
                        list = Via.getManager().getProtocolManager().getProtocolPath(protocolInfo.getProtocolVersion(), n2);
                    }
                    if (list != null) {
                        if (n == n2 || n == 0) {
                            ProtocolVersion protocolVersion = ProtocolVersion.getProtocol(protocolInfo.getProtocolVersion());
                            jsonObject.addProperty("protocol", protocolVersion.getOriginalVersion());
                        }
                    } else {
                        packetWrapper.user().setActive(false);
                    }
                    if (Via.getConfig().blockedProtocolVersions().contains(protocolInfo.getProtocolVersion())) {
                        jsonObject.addProperty("protocol", -1);
                    }
                    packetWrapper.set(Type.STRING, 0, GsonUtil.getGson().toJson(jsonElement));
                } catch (JsonParseException jsonParseException) {
                    jsonParseException.printStackTrace();
                }
            }
        });
        this.registerClientbound(ClientboundLoginPackets.GAME_PROFILE, this::lambda$registerPackets$0);
        this.registerServerbound(ServerboundLoginPackets.HELLO, BaseProtocol1_7::lambda$registerPackets$2);
    }

    @Override
    public boolean isBaseProtocol() {
        return false;
    }

    public static String addDashes(String string) {
        StringBuilder stringBuilder = new StringBuilder(string);
        stringBuilder.insert(20, '-');
        stringBuilder.insert(16, '-');
        stringBuilder.insert(12, '-');
        stringBuilder.insert(8, '-');
        return stringBuilder.toString();
    }

    protected UUID passthroughLoginUUID(PacketWrapper packetWrapper) throws Exception {
        String string = packetWrapper.passthrough(Type.STRING);
        if (string.length() == 32) {
            string = BaseProtocol1_7.addDashes(string);
        }
        return UUID.fromString(string);
    }

    private static void lambda$registerPackets$2(PacketWrapper packetWrapper) throws Exception {
        int n = packetWrapper.user().getProtocolInfo().getProtocolVersion();
        if (Via.getConfig().blockedProtocolVersions().contains(n)) {
            if (!packetWrapper.user().getChannel().isOpen()) {
                return;
            }
            if (!packetWrapper.user().shouldApplyBlockProtocol()) {
                return;
            }
            PacketWrapper packetWrapper2 = PacketWrapper.create(ClientboundLoginPackets.LOGIN_DISCONNECT, packetWrapper.user());
            Protocol1_9To1_8.FIX_JSON.write(packetWrapper2, ChatColorUtil.translateAlternateColorCodes(Via.getConfig().getBlockedDisconnectMsg()));
            packetWrapper.cancel();
            ChannelFuture channelFuture = packetWrapper2.sendFuture(BaseProtocol.class);
            channelFuture.addListener((GenericFutureListener<? extends Future<? super Void>>)((GenericFutureListener<Future>)arg_0 -> BaseProtocol1_7.lambda$null$1(packetWrapper, arg_0)));
        }
    }

    private static void lambda$null$1(PacketWrapper packetWrapper, Future future) throws Exception {
        packetWrapper.user().getChannel().close();
    }

    private void lambda$registerPackets$0(PacketWrapper packetWrapper) throws Exception {
        ProtocolInfo protocolInfo = packetWrapper.user().getProtocolInfo();
        protocolInfo.setState(State.PLAY);
        UUID uUID = this.passthroughLoginUUID(packetWrapper);
        protocolInfo.setUuid(uUID);
        String string = packetWrapper.passthrough(Type.STRING);
        protocolInfo.setUsername(string);
        Via.getManager().getConnectionManager().onLoginSuccess(packetWrapper.user());
        if (!protocolInfo.getPipeline().hasNonBaseProtocols()) {
            packetWrapper.user().setActive(false);
        }
        if (Via.getManager().isDebug()) {
            Via.getPlatform().getLogger().log(Level.INFO, "{0} logged in with protocol {1}, Route: {2}", new Object[]{string, protocolInfo.getProtocolVersion(), Joiner.on(", ").join(protocolInfo.getPipeline().pipes(), ", ", new Object[0])});
        }
    }
}

