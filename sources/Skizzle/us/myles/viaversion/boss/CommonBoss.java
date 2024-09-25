/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Preconditions
 */
package us.myles.ViaVersion.boss;

import com.google.common.base.Preconditions;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.WeakHashMap;
import java.util.stream.Collectors;
import us.myles.ViaVersion.api.PacketWrapper;
import us.myles.ViaVersion.api.Via;
import us.myles.ViaVersion.api.boss.BossBar;
import us.myles.ViaVersion.api.boss.BossColor;
import us.myles.ViaVersion.api.boss.BossFlag;
import us.myles.ViaVersion.api.boss.BossStyle;
import us.myles.ViaVersion.api.data.UserConnection;
import us.myles.ViaVersion.api.type.Type;
import us.myles.ViaVersion.protocols.protocol1_9to1_8.Protocol1_9To1_8;

public abstract class CommonBoss<T>
extends BossBar<T> {
    private final UUID uuid;
    private final Set<UserConnection> connections;
    private final Set<BossFlag> flags;
    private String title;
    private float health;
    private BossColor color;
    private BossStyle style;
    private boolean visible;

    public CommonBoss(String title, float health, BossColor color, BossStyle style) {
        Preconditions.checkNotNull((Object)title, (Object)"Title cannot be null");
        Preconditions.checkArgument((health >= 0.0f && health <= 1.0f ? 1 : 0) != 0, (Object)"Health must be between 0 and 1");
        this.uuid = UUID.randomUUID();
        this.title = title;
        this.health = health;
        this.color = color == null ? BossColor.PURPLE : color;
        this.style = style == null ? BossStyle.SOLID : style;
        this.connections = Collections.newSetFromMap(new WeakHashMap());
        this.flags = new HashSet<BossFlag>();
        this.visible = true;
    }

    @Override
    public BossBar setTitle(String title) {
        Preconditions.checkNotNull((Object)title);
        this.title = title;
        this.sendPacket(UpdateAction.UPDATE_TITLE);
        return this;
    }

    @Override
    public BossBar setHealth(float health) {
        Preconditions.checkArgument((health >= 0.0f && health <= 1.0f ? 1 : 0) != 0, (Object)"Health must be between 0 and 1");
        this.health = health;
        this.sendPacket(UpdateAction.UPDATE_HEALTH);
        return this;
    }

    @Override
    public BossColor getColor() {
        return this.color;
    }

    @Override
    public BossBar setColor(BossColor color) {
        Preconditions.checkNotNull((Object)((Object)color));
        this.color = color;
        this.sendPacket(UpdateAction.UPDATE_STYLE);
        return this;
    }

    @Override
    public BossBar setStyle(BossStyle style) {
        Preconditions.checkNotNull((Object)((Object)style));
        this.style = style;
        this.sendPacket(UpdateAction.UPDATE_STYLE);
        return this;
    }

    @Override
    public BossBar addPlayer(UUID player) {
        return this.addConnection(Via.getManager().getConnection(player));
    }

    @Override
    public BossBar addConnection(UserConnection conn) {
        if (this.connections.add(conn) && this.visible) {
            this.sendPacketConnection(conn, this.getPacket(UpdateAction.ADD, conn));
        }
        return this;
    }

    @Override
    public BossBar removePlayer(UUID uuid) {
        return this.removeConnection(Via.getManager().getConnection(uuid));
    }

    @Override
    public BossBar removeConnection(UserConnection conn) {
        if (this.connections.remove(conn)) {
            this.sendPacketConnection(conn, this.getPacket(UpdateAction.REMOVE, conn));
        }
        return this;
    }

    @Override
    public BossBar addFlag(BossFlag flag) {
        Preconditions.checkNotNull((Object)((Object)flag));
        if (!this.hasFlag(flag)) {
            this.flags.add(flag);
        }
        this.sendPacket(UpdateAction.UPDATE_FLAGS);
        return this;
    }

    @Override
    public BossBar removeFlag(BossFlag flag) {
        Preconditions.checkNotNull((Object)((Object)flag));
        if (this.hasFlag(flag)) {
            this.flags.remove((Object)flag);
        }
        this.sendPacket(UpdateAction.UPDATE_FLAGS);
        return this;
    }

    @Override
    public boolean hasFlag(BossFlag flag) {
        Preconditions.checkNotNull((Object)((Object)flag));
        return this.flags.contains((Object)flag);
    }

    @Override
    public Set<UUID> getPlayers() {
        return this.connections.stream().map(conn -> Via.getManager().getConnectedClientId((UserConnection)conn)).filter(Objects::nonNull).collect(Collectors.toSet());
    }

    @Override
    public Set<UserConnection> getConnections() {
        return Collections.unmodifiableSet(this.connections);
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

    private void setVisible(boolean value) {
        if (this.visible != value) {
            this.visible = value;
            this.sendPacket(value ? UpdateAction.ADD : UpdateAction.REMOVE);
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

    private void sendPacket(UpdateAction action) {
        for (UserConnection conn : new ArrayList<UserConnection>(this.connections)) {
            PacketWrapper wrapper = this.getPacket(action, conn);
            this.sendPacketConnection(conn, wrapper);
        }
    }

    private void sendPacketConnection(UserConnection conn, PacketWrapper wrapper) {
        if (conn.getProtocolInfo() == null || !conn.getProtocolInfo().getPipeline().contains(Protocol1_9To1_8.class)) {
            this.connections.remove(conn);
            return;
        }
        try {
            wrapper.send(Protocol1_9To1_8.class);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private PacketWrapper getPacket(UpdateAction action, UserConnection connection) {
        try {
            PacketWrapper wrapper = new PacketWrapper(12, null, connection);
            wrapper.write(Type.UUID, this.uuid);
            wrapper.write(Type.VAR_INT, action.getId());
            switch (action) {
                case ADD: {
                    Protocol1_9To1_8.FIX_JSON.write(wrapper, this.title);
                    wrapper.write(Type.FLOAT, Float.valueOf(this.health));
                    wrapper.write(Type.VAR_INT, this.color.getId());
                    wrapper.write(Type.VAR_INT, this.style.getId());
                    wrapper.write(Type.BYTE, (byte)this.flagToBytes());
                    break;
                }
                case REMOVE: {
                    break;
                }
                case UPDATE_HEALTH: {
                    wrapper.write(Type.FLOAT, Float.valueOf(this.health));
                    break;
                }
                case UPDATE_TITLE: {
                    Protocol1_9To1_8.FIX_JSON.write(wrapper, this.title);
                    break;
                }
                case UPDATE_STYLE: {
                    wrapper.write(Type.VAR_INT, this.color.getId());
                    wrapper.write(Type.VAR_INT, this.style.getId());
                    break;
                }
                case UPDATE_FLAGS: {
                    wrapper.write(Type.BYTE, (byte)this.flagToBytes());
                }
            }
            return wrapper;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private int flagToBytes() {
        int bitmask = 0;
        for (BossFlag flag : this.flags) {
            bitmask |= flag.getId();
        }
        return bitmask;
    }

    private static enum UpdateAction {
        ADD(0),
        REMOVE(1),
        UPDATE_HEALTH(2),
        UPDATE_TITLE(3),
        UPDATE_STYLE(4),
        UPDATE_FLAGS(5);

        private final int id;

        private UpdateAction(int id) {
            this.id = id;
        }

        public int getId() {
            return this.id;
        }
    }
}

