package com.viaversion.viarewind.protocol.protocol1_8to1_9.storage;

import com.viaversion.viarewind.protocol.protocol1_8to1_9.Protocol1_8To1_9;
import com.viaversion.viarewind.utils.PacketUtil;
import com.viaversion.viarewind.utils.Tickable;
import com.viaversion.viaversion.api.connection.StoredObject;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocols.protocol1_8.ClientboundPackets1_8;

public class Levitation extends StoredObject implements Tickable {
   private int amplifier;
   private volatile boolean active = false;

   public Levitation(UserConnection user) {
      super(user);
   }

   @Override
   public void tick() {
      if (this.active) {
         int vY = (this.amplifier + 1) * 360;
         PacketWrapper packet = PacketWrapper.create(ClientboundPackets1_8.ENTITY_VELOCITY, null, this.getUser());
         packet.write(Type.VAR_INT, this.getUser().get(EntityTracker.class).getPlayerId());
         packet.write(Type.SHORT, (short)0);
         packet.write(Type.SHORT, (short)vY);
         packet.write(Type.SHORT, (short)0);
         PacketUtil.sendPacket(packet, Protocol1_8To1_9.class);
      }
   }

   public void setActive(boolean active) {
      this.active = active;
   }

   public void setAmplifier(int amplifier) {
      this.amplifier = amplifier;
   }
}
