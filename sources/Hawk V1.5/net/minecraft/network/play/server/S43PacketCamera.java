package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.entity.Entity;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.world.World;

public class S43PacketCamera implements Packet {
   private static final String __OBFID = "CL_00002289";
   public int field_179781_a;

   public void func_179779_a(INetHandlerPlayClient var1) {
      var1.func_175094_a(this);
   }

   public S43PacketCamera() {
   }

   public void writePacketData(PacketBuffer var1) throws IOException {
      var1.writeVarIntToBuffer(this.field_179781_a);
   }

   public void readPacketData(PacketBuffer var1) throws IOException {
      this.field_179781_a = var1.readVarIntFromBuffer();
   }

   public S43PacketCamera(Entity var1) {
      this.field_179781_a = var1.getEntityId();
   }

   public Entity func_179780_a(World var1) {
      return var1.getEntityByID(this.field_179781_a);
   }

   public void processPacket(INetHandler var1) {
      this.func_179779_a((INetHandlerPlayClient)var1);
   }
}
