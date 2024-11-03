package com.viaversion.viarewind.protocol.protocol1_8to1_9.entityreplacement;

import com.viaversion.viarewind.api.minecraft.EntityModel;
import com.viaversion.viarewind.protocol.protocol1_8to1_9.Protocol1_8To1_9;
import com.viaversion.viarewind.utils.PacketUtil;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.version.Types1_8;
import com.viaversion.viaversion.protocols.protocol1_8.ClientboundPackets1_8;
import java.util.ArrayList;
import java.util.List;

public abstract class EntityModel1_8To1_9 extends EntityModel<Protocol1_8To1_9> {
   public EntityModel1_8To1_9(UserConnection user, Protocol1_8To1_9 protocol) {
      super(user, protocol);
   }

   protected void sendTeleportWithHead(int entityId, double locX, double locY, double locZ, float yaw, float pitch, float headYaw) {
      this.teleportEntity(entityId, locX, locY, locZ, yaw, pitch);
      this.sendHeadYaw(entityId, headYaw);
   }

   protected void teleportEntity(int entityId, double locX, double locY, double locZ, float yaw, float pitch) {
      PacketWrapper teleport = PacketWrapper.create(ClientboundPackets1_8.ENTITY_TELEPORT, null, this.user);
      teleport.write(Type.VAR_INT, entityId);
      teleport.write(Type.INT, (int)(locX * 32.0));
      teleport.write(Type.INT, (int)(locY * 32.0));
      teleport.write(Type.INT, (int)(locZ * 32.0));
      teleport.write(Type.BYTE, (byte)((int)(yaw / 360.0F * 256.0F)));
      teleport.write(Type.BYTE, (byte)((int)(pitch / 360.0F * 256.0F)));
      teleport.write(Type.BOOLEAN, true);
      PacketUtil.sendPacket(teleport, Protocol1_8To1_9.class, true, true);
   }

   protected void sendHeadYaw(int entityId, float headYaw) {
      PacketWrapper head = PacketWrapper.create(ClientboundPackets1_8.ENTITY_HEAD_LOOK, null, this.user);
      head.write(Type.VAR_INT, entityId);
      head.write(Type.BYTE, (byte)((int)(headYaw / 360.0F * 256.0F)));
      PacketUtil.sendPacket(head, Protocol1_8To1_9.class, true, true);
   }

   protected void sendSpawn(int entityId, int type) {
      PacketWrapper spawn = PacketWrapper.create(ClientboundPackets1_8.SPAWN_MOB, null, this.user);
      spawn.write(Type.VAR_INT, entityId);
      spawn.write(Type.UNSIGNED_BYTE, (short)type);
      spawn.write(Type.INT, 0);
      spawn.write(Type.INT, 0);
      spawn.write(Type.INT, 0);
      spawn.write(Type.BYTE, (byte)0);
      spawn.write(Type.BYTE, (byte)0);
      spawn.write(Type.BYTE, (byte)0);
      spawn.write(Type.SHORT, (short)0);
      spawn.write(Type.SHORT, (short)0);
      spawn.write(Type.SHORT, (short)0);
      List<Metadata> list = new ArrayList<>();
      spawn.write(Types1_8.METADATA_LIST, list);
      PacketUtil.sendPacket(spawn, Protocol1_8To1_9.class, true, true);
   }

   protected void sendSpawnEntity(int entityId, int type) {
      PacketWrapper spawn = PacketWrapper.create(ClientboundPackets1_8.SPAWN_ENTITY, null, this.user);
      spawn.write(Type.VAR_INT, entityId);
      spawn.write(Type.BYTE, (byte)type);
      spawn.write(Type.INT, 0);
      spawn.write(Type.INT, 0);
      spawn.write(Type.INT, 0);
      spawn.write(Type.BYTE, (byte)0);
      spawn.write(Type.BYTE, (byte)0);
      spawn.write(Type.INT, 0);
      PacketUtil.sendPacket(spawn, Protocol1_8To1_9.class, true, true);
   }
}
