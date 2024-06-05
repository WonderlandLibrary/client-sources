package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.world.World;

public class S49PacketUpdateEntityNBT implements Packet {
   private NBTTagCompound field_179765_b;
   private static final String __OBFID = "CL_00002301";
   private int field_179766_a;

   public S49PacketUpdateEntityNBT() {
   }

   public void writePacketData(PacketBuffer var1) throws IOException {
      var1.writeVarIntToBuffer(this.field_179766_a);
      var1.writeNBTTagCompoundToBuffer(this.field_179765_b);
   }

   public void readPacketData(PacketBuffer var1) throws IOException {
      this.field_179766_a = var1.readVarIntFromBuffer();
      this.field_179765_b = var1.readNBTTagCompoundFromBuffer();
   }

   public Entity func_179764_a(World var1) {
      return var1.getEntityByID(this.field_179766_a);
   }

   public NBTTagCompound func_179763_a() {
      return this.field_179765_b;
   }

   public void processPacket(INetHandler var1) {
      this.func_179762_a((INetHandlerPlayClient)var1);
   }

   public void func_179762_a(INetHandlerPlayClient var1) {
      var1.func_175097_a(this);
   }

   public S49PacketUpdateEntityNBT(int var1, NBTTagCompound var2) {
      this.field_179766_a = var1;
      this.field_179765_b = var2;
   }
}
