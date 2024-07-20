/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viarewind.protocol.protocol1_8to1_9.entityreplacement;

import com.viaversion.viarewind.protocol.protocol1_8to1_9.Protocol1_8To1_9;
import com.viaversion.viarewind.replacement.EntityReplacement;
import com.viaversion.viarewind.utils.PacketUtil;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.version.Types1_8;
import com.viaversion.viaversion.protocols.protocol1_8.ClientboundPackets1_8;
import java.util.ArrayList;

public abstract class EntityReplacement1_8to1_9
implements EntityReplacement {
    protected final UserConnection user;

    protected EntityReplacement1_8to1_9(UserConnection user) {
        this.user = user;
    }

    protected void sendTeleportWithHead(int entityId, double locX, double locY, double locZ, float yaw, float pitch, float headYaw) {
        this.sendTeleport(entityId, locX, locY, locZ, yaw, pitch);
        this.sendHeadYaw(entityId, headYaw);
    }

    protected void sendTeleport(int entityId, double locX, double locY, double locZ, float yaw, float pitch) {
        PacketWrapper teleport = PacketWrapper.create(ClientboundPackets1_8.ENTITY_TELEPORT, null, this.user);
        teleport.write(Type.VAR_INT, entityId);
        teleport.write(Type.INT, (int)(locX * 32.0));
        teleport.write(Type.INT, (int)(locY * 32.0));
        teleport.write(Type.INT, (int)(locZ * 32.0));
        teleport.write(Type.BYTE, (byte)(yaw / 360.0f * 256.0f));
        teleport.write(Type.BYTE, (byte)(pitch / 360.0f * 256.0f));
        teleport.write(Type.BOOLEAN, true);
        PacketUtil.sendPacket(teleport, Protocol1_8To1_9.class, true, true);
    }

    protected void sendHeadYaw(int entityId, float headYaw) {
        PacketWrapper head = PacketWrapper.create(ClientboundPackets1_8.ENTITY_HEAD_LOOK, null, this.user);
        head.write(Type.VAR_INT, entityId);
        head.write(Type.BYTE, (byte)(headYaw / 360.0f * 256.0f));
        PacketUtil.sendPacket(head, Protocol1_8To1_9.class, true, true);
    }

    protected void sendSpawn(int entityId, int type2) {
        PacketWrapper spawn = PacketWrapper.create(ClientboundPackets1_8.SPAWN_MOB, null, this.user);
        spawn.write(Type.VAR_INT, entityId);
        spawn.write(Type.UNSIGNED_BYTE, (short)type2);
        spawn.write(Type.INT, 0);
        spawn.write(Type.INT, 0);
        spawn.write(Type.INT, 0);
        spawn.write(Type.BYTE, (byte)0);
        spawn.write(Type.BYTE, (byte)0);
        spawn.write(Type.BYTE, (byte)0);
        spawn.write(Type.SHORT, (short)0);
        spawn.write(Type.SHORT, (short)0);
        spawn.write(Type.SHORT, (short)0);
        ArrayList list = new ArrayList();
        spawn.write(Types1_8.METADATA_LIST, list);
        PacketUtil.sendPacket(spawn, Protocol1_8To1_9.class, true, true);
    }

    protected void sendSpawnEntity(int entityId, int type2) {
        PacketWrapper spawn = PacketWrapper.create(ClientboundPackets1_8.SPAWN_ENTITY, null, this.user);
        spawn.write(Type.VAR_INT, entityId);
        spawn.write(Type.BYTE, (byte)type2);
        spawn.write(Type.INT, 0);
        spawn.write(Type.INT, 0);
        spawn.write(Type.INT, 0);
        spawn.write(Type.BYTE, (byte)0);
        spawn.write(Type.BYTE, (byte)0);
        spawn.write(Type.INT, 0);
        PacketUtil.sendPacket(spawn, Protocol1_8To1_9.class, true, true);
    }
}

