package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class S0APacketUseBed implements Packet {
   private static final String __OBFID = "CL_00001319";
   private int playerID;
   private BlockPos field_179799_b;

   public S0APacketUseBed() {
   }

   public void writePacketData(PacketBuffer var1) throws IOException {
      var1.writeVarIntToBuffer(this.playerID);
      var1.writeBlockPos(this.field_179799_b);
   }

   public BlockPos func_179798_a() {
      return this.field_179799_b;
   }

   public EntityPlayer getPlayer(World var1) {
      return (EntityPlayer)var1.getEntityByID(this.playerID);
   }

   public void processPacket(INetHandler var1) {
      this.func_180744_a((INetHandlerPlayClient)var1);
   }

   public void func_180744_a(INetHandlerPlayClient var1) {
      var1.handleUseBed(this);
   }

   public S0APacketUseBed(EntityPlayer var1, BlockPos var2) {
      this.playerID = var1.getEntityId();
      this.field_179799_b = var2;
   }

   public void readPacketData(PacketBuffer var1) throws IOException {
      this.playerID = var1.readVarIntFromBuffer();
      this.field_179799_b = var1.readBlockPos();
   }
}
