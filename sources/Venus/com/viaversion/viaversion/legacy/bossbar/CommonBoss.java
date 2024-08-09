/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.legacy.bossbar;

import com.google.common.base.Preconditions;
import com.google.common.collect.MapMaker;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.legacy.bossbar.BossBar;
import com.viaversion.viaversion.api.legacy.bossbar.BossColor;
import com.viaversion.viaversion.api.legacy.bossbar.BossFlag;
import com.viaversion.viaversion.api.legacy.bossbar.BossStyle;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.ClientboundPackets1_9;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.Protocol1_9To1_8;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class CommonBoss
implements BossBar {
    private final UUID uuid;
    private final Map<UUID, UserConnection> connections;
    private final Set<BossFlag> flags;
    private String title;
    private float health;
    private BossColor color;
    private BossStyle style;
    private boolean visible;

    public CommonBoss(String string, float f, BossColor bossColor, BossStyle bossStyle) {
        Preconditions.checkNotNull(string, "Title cannot be null");
        Preconditions.checkArgument(f >= 0.0f && f <= 1.0f, "Health must be between 0 and 1. Input: " + f);
        this.uuid = UUID.randomUUID();
        this.title = string;
        this.health = f;
        this.color = bossColor == null ? BossColor.PURPLE : bossColor;
        this.style = bossStyle == null ? BossStyle.SOLID : bossStyle;
        this.connections = new MapMaker().weakValues().makeMap();
        this.flags = new HashSet<BossFlag>();
        this.visible = true;
    }

    @Override
    public BossBar setTitle(String string) {
        Preconditions.checkNotNull(string);
        this.title = string;
        this.sendPacket(UpdateAction.UPDATE_TITLE);
        return this;
    }

    @Override
    public BossBar setHealth(float f) {
        Preconditions.checkArgument(f >= 0.0f && f <= 1.0f, "Health must be between 0 and 1. Input: " + f);
        this.health = f;
        this.sendPacket(UpdateAction.UPDATE_HEALTH);
        return this;
    }

    @Override
    public BossColor getColor() {
        return this.color;
    }

    @Override
    public BossBar setColor(BossColor bossColor) {
        Preconditions.checkNotNull(bossColor);
        this.color = bossColor;
        this.sendPacket(UpdateAction.UPDATE_STYLE);
        return this;
    }

    @Override
    public BossBar setStyle(BossStyle bossStyle) {
        Preconditions.checkNotNull(bossStyle);
        this.style = bossStyle;
        this.sendPacket(UpdateAction.UPDATE_STYLE);
        return this;
    }

    @Override
    public BossBar addPlayer(UUID uUID) {
        UserConnection userConnection = Via.getManager().getConnectionManager().getConnectedClient(uUID);
        if (userConnection != null) {
            this.addConnection(userConnection);
        }
        return this;
    }

    @Override
    public BossBar addConnection(UserConnection userConnection) {
        if (this.connections.put(userConnection.getProtocolInfo().getUuid(), userConnection) == null && this.visible) {
            this.sendPacketConnection(userConnection, this.getPacket(UpdateAction.ADD, userConnection));
        }
        return this;
    }

    @Override
    public BossBar removePlayer(UUID uUID) {
        UserConnection userConnection = this.connections.remove(uUID);
        if (userConnection != null) {
            this.sendPacketConnection(userConnection, this.getPacket(UpdateAction.REMOVE, userConnection));
        }
        return this;
    }

    @Override
    public BossBar removeConnection(UserConnection userConnection) {
        this.removePlayer(userConnection.getProtocolInfo().getUuid());
        return this;
    }

    @Override
    public BossBar addFlag(BossFlag bossFlag) {
        Preconditions.checkNotNull(bossFlag);
        if (!this.hasFlag(bossFlag)) {
            this.flags.add(bossFlag);
        }
        this.sendPacket(UpdateAction.UPDATE_FLAGS);
        return this;
    }

    @Override
    public BossBar removeFlag(BossFlag bossFlag) {
        Preconditions.checkNotNull(bossFlag);
        if (this.hasFlag(bossFlag)) {
            this.flags.remove((Object)bossFlag);
        }
        this.sendPacket(UpdateAction.UPDATE_FLAGS);
        return this;
    }

    @Override
    public boolean hasFlag(BossFlag bossFlag) {
        Preconditions.checkNotNull(bossFlag);
        return this.flags.contains((Object)bossFlag);
    }

    @Override
    public Set<UUID> getPlayers() {
        return Collections.unmodifiableSet(this.connections.keySet());
    }

    @Override
    public Set<UserConnection> getConnections() {
        return Collections.unmodifiableSet(new HashSet<UserConnection>(this.connections.values()));
    }

    @Override
    public BossBar show() {
        this.setVisible(true);
        return this;
    }

    @Override
    public BossBar hide() {
        this.setVisible(false);
        return this;
    }

    @Override
    public boolean isVisible() {
        return this.visible;
    }

    private void setVisible(boolean bl) {
        if (this.visible != bl) {
            this.visible = bl;
            this.sendPacket(bl ? UpdateAction.ADD : UpdateAction.REMOVE);
        }
    }

    @Override
    public UUID getId() {
        return this.uuid;
    }

    public UUID getUuid() {
        return this.uuid;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public float getHealth() {
        return this.health;
    }

    @Override
    public BossStyle getStyle() {
        return this.style;
    }

    public Set<BossFlag> getFlags() {
        return this.flags;
    }

    private void sendPacket(UpdateAction updateAction) {
        for (UserConnection userConnection : new ArrayList<UserConnection>(this.connections.values())) {
            PacketWrapper packetWrapper = this.getPacket(updateAction, userConnection);
            this.sendPacketConnection(userConnection, packetWrapper);
        }
    }

    private void sendPacketConnection(UserConnection userConnection, PacketWrapper packetWrapper) {
        if (userConnection.getProtocolInfo() == null || !userConnection.getProtocolInfo().getPipeline().contains(Protocol1_9To1_8.class)) {
            this.connections.remove(userConnection.getProtocolInfo().getUuid());
            return;
        }
        try {
            packetWrapper.scheduleSend(Protocol1_9To1_8.class);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private PacketWrapper getPacket(UpdateAction updateAction, UserConnection userConnection) {
        try {
            PacketWrapper packetWrapper = PacketWrapper.create(ClientboundPackets1_9.BOSSBAR, null, userConnection);
            packetWrapper.write(Type.UUID, this.uuid);
            packetWrapper.write(Type.VAR_INT, updateAction.getId());
            switch (1.$SwitchMap$com$viaversion$viaversion$legacy$bossbar$CommonBoss$UpdateAction[updateAction.ordinal()]) {
                case 1: {
                    Protocol1_9To1_8.FIX_JSON.write(packetWrapper, this.title);
                    packetWrapper.write(Type.FLOAT, Float.valueOf(this.health));
                    packetWrapper.write(Type.VAR_INT, this.color.getId());
                    packetWrapper.write(Type.VAR_INT, this.style.getId());
                    packetWrapper.write(Type.BYTE, (byte)this.flagToBytes());
                    break;
                }
                case 2: {
                    break;
                }
                case 3: {
                    packetWrapper.write(Type.FLOAT, Float.valueOf(this.health));
                    break;
                }
                case 4: {
                    Protocol1_9To1_8.FIX_JSON.write(packetWrapper, this.title);
                    break;
                }
                case 5: {
                    packetWrapper.write(Type.VAR_INT, this.color.getId());
                    packetWrapper.write(Type.VAR_INT, this.style.getId());
                    break;
                }
                case 6: {
                    packetWrapper.write(Type.BYTE, (byte)this.flagToBytes());
                }
            }
            return packetWrapper;
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

    private int flagToBytes() {
        int n = 0;
        for (BossFlag bossFlag : this.flags) {
            n |= bossFlag.getId();
        }
        return n;
    }

    private static enum UpdateAction {
        ADD(0),
        REMOVE(1),
        UPDATE_HEALTH(2),
        UPDATE_TITLE(3),
        UPDATE_STYLE(4),
        UPDATE_FLAGS(5);

        private final int id;

        private UpdateAction(int n2) {
            this.id = n2;
        }

        public int getId() {
            return this.id;
        }
    }
}

