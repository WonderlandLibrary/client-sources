package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.entity.Entity;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.world.World;

public class S19PacketEntityStatus implements Packet {
   private int field_149164_a;
   private static final String __OBFID = "CL_00001299";
   private byte field_149163_b;

   public void writePacketData(PacketBuffer var1) throws IOException {
      var1.writeInt(this.field_149164_a);
      var1.writeByte(this.field_149163_b);
   }

   public S19PacketEntityStatus(Entity var1, byte var2) {
      this.field_149164_a = var1.getEntityId();
      this.field_149163_b = var2;
   }

   public void readPacketData(PacketBuffer var1) throws IOException {
      this.field_149164_a = var1.readInt();
      this.field_149163_b = var1.readByte();
   }

   public S19PacketEntityStatus() {
   }

   public Entity func_149161_a(World var1) {
      return var1.getEntityByID(this.field_149164_a);
   }

   public void func_180736_a(INetHandlerPlayClient var1) {
      var1.handleEntityStatus(this);
   }

   public void processPacket(INetHandler var1) {
      this.func_180736_a((INetHandlerPlayClient)var1);
   }

   public byte func_149160_c() {
      return this.field_149163_b;
   }
}
