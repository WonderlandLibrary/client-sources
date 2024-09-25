/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Joiner
 *  io.netty.channel.ChannelFuture
 */
package us.myles.ViaVersion.protocols.base;

import com.google.common.base.Joiner;
import io.netty.channel.ChannelFuture;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import us.myles.ViaVersion.api.PacketWrapper;
import us.myles.ViaVersion.api.Pair;
import us.myles.ViaVersion.api.Via;
import us.myles.ViaVersion.api.protocol.Protocol;
import us.myles.ViaVersion.api.protocol.ProtocolRegistry;
import us.myles.ViaVersion.api.protocol.ProtocolVersion;
import us.myles.ViaVersion.api.protocol.SimpleProtocol;
import us.myles.ViaVersion.api.remapper.PacketHandler;
import us.myles.ViaVersion.api.remapper.PacketRemapper;
import us.myles.ViaVersion.api.type.Type;
import us.myles.ViaVersion.packets.State;
import us.myles.ViaVersion.protocols.base.BaseProtocol;
import us.myles.ViaVersion.protocols.base.ProtocolInfo;
import us.myles.ViaVersion.protocols.base.VersionProvider;
import us.myles.ViaVersion.protocols.protocol1_9to1_8.Protocol1_9To1_8;
import us.myles.ViaVersion.util.GsonUtil;
import us.myles.viaversion.libs.bungeecordchat.api.ChatColor;
import us.myles.viaversion.libs.gson.JsonElement;
import us.myles.viaversion.libs.gson.JsonObject;
import us.myles.viaversion.libs.gson.JsonParseException;

public class BaseProtocol1_7
extends SimpleProtocol {
    @Override
    protected void registerPackets() {
        this.registerOutgoing(State.STATUS, 0, 0, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.STRING);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper wrapper) throws Exception {
                        ProtocolInfo info = wrapper.user().getProtocolInfo();
                        String originalStatus = wrapper.get(Type.STRING, 0);
                        try {
                            VersionProvider versionProvider;
                            JsonObject version;
                            JsonElement json = GsonUtil.getGson().fromJson(originalStatus, JsonElement.class);
                            int protocolVersion = 0;
                            if (json.isJsonObject()) {
                                if (json.getAsJsonObject().has("version")) {
                                    version = json.getAsJsonObject().get("version").getAsJsonObject();
                                    if (version.has("protocol")) {
                                        protocolVersion = Long.valueOf(version.get("protocol").getAsLong()).intValue();
                                    }
                                } else {
                                    version = new JsonObject();
                                    json.getAsJsonObject().add("version", version);
                                }
                            } else {
                                json = new JsonObject();
                                version = new JsonObject();
                                json.getAsJsonObject().add("version", version);
                            }
                            if (Via.getConfig().isSendSupportedVersions()) {
                                version.add("supportedVersions", GsonUtil.getGson().toJsonTree(Via.getAPI().getSupportedVersions()));
                            }
                            if (ProtocolRegistry.SERVER_PROTOCOL == -1) {
                                ProtocolRegistry.SERVER_PROTOCOL = ProtocolVersion.getProtocol(protocolVersion).getVersion();
                            }
                            if ((versionProvider = Via.getManager().getProviders().get(VersionProvider.class)) == null) {
                                wrapper.user().setActive(false);
                                return;
                            }
                            int protocol = versionProvider.getServerProtocol(wrapper.user());
                            List<Pair<Integer, Protocol>> protocols = null;
                            if (info.getProtocolVersion() >= protocol || Via.getPlatform().isOldClientsAllowed()) {
                                protocols = ProtocolRegistry.getProtocolPath(info.getProtocolVersion(), protocol);
                            }
                            if (protocols != null) {
                                if (protocolVersion == protocol || protocolVersion == 0) {
                                    ProtocolVersion prot = ProtocolVersion.getProtocol(info.getProtocolVersion());
                                    version.addProperty("protocol", prot.getOriginalVersion());
                                }
                            } else {
                                wrapper.user().setActive(false);
                            }
                            if (Via.getConfig().getBlockedProtocols().contains(info.getProtocolVersion())) {
                                version.addProperty("protocol", -1);
                            }
                            wrapper.set(Type.STRING, 0, GsonUtil.getGson().toJson(json));
                        }
                        catch (JsonParseException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
        this.registerOutgoing(State.STATUS, 1, 1);
        this.registerOutgoing(State.LOGIN, 0, 0);
        this.registerOutgoing(State.LOGIN, 1, 1);
        this.registerOutgoing(State.LOGIN, 2, 2, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper wrapper) throws Exception {
                        ProtocolInfo info = wrapper.user().getProtocolInfo();
                        info.setState(State.PLAY);
                        UUID uuid = BaseProtocol1_7.this.passthroughLoginUUID(wrapper);
                        info.setUuid(uuid);
                        String username = wrapper.passthrough(Type.STRING);
                        info.setUsername(username);
                        Via.getManager().handleLoginSuccess(wrapper.user());
                        if (info.getPipeline().pipes().stream().allMatch(ProtocolRegistry::isBaseProtocol)) {
                            wrapper.user().setActive(false);
                        }
                        if (Via.getManager().isDebug()) {
                            Via.getPlatform().getLogger().log(Level.INFO, "{0} logged in with protocol {1}, Route: {2}", new Object[]{username, info.getProtocolVersion(), Joiner.on((String)", ").join(info.getPipeline().pipes(), (Object)", ", new Object[0])});
                        }
                    }
                });
            }
        });
        this.registerOutgoing(State.LOGIN, 3, 3);
        this.registerIncoming(State.LOGIN, 4, 4);
        this.registerIncoming(State.STATUS, 0, 0);
        this.registerIncoming(State.STATUS, 1, 1);
        this.registerIncoming(State.LOGIN, 0, 0, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper wrapper) throws Exception {
                        int protocol = wrapper.user().getProtocolInfo().getProtocolVersion();
                        if (Via.getConfig().getBlockedProtocols().contains(protocol)) {
                            if (!wrapper.user().getChannel().isOpen()) {
                                return;
                            }
                            if (!wrapper.user().shouldApplyBlockProtocol()) {
                                return;
                            }
                            PacketWrapper disconnectPacket = new PacketWrapper(0, null, wrapper.user());
                            Protocol1_9To1_8.FIX_JSON.write(disconnectPacket, ChatColor.translateAlternateColorCodes('&', Via.getConfig().getBlockedDisconnectMsg()));
                            wrapper.cancel();
                            ChannelFuture future = disconnectPacket.sendFuture(BaseProtocol.class);
                            future.addListener(f -> wrapper.user().getChannel().close());
                        }
                    }
                });
            }
        });
        this.registerIncoming(State.LOGIN, 1, 1);
        this.registerIncoming(State.LOGIN, 2, 2);
    }

    public static String addDashes(String trimmedUUID) {
        StringBuilder idBuff = new StringBuilder(trimmedUUID);
        idBuff.insert(20, '-');
        idBuff.insert(16, '-');
        idBuff.insert(12, '-');
        idBuff.insert(8, '-');
        return idBuff.toString();
    }

    protected UUID passthroughLoginUUID(PacketWrapper wrapper) throws Exception {
        String uuidString = wrapper.passthrough(Type.STRING);
        if (uuidString.length() == 32) {
            uuidString = BaseProtocol1_7.addDashes(uuidString);
        }
        return UUID.fromString(uuidString);
    }
}

