package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class S0APacketUseBed implements Packet {
   private int playerID;
   private BlockPos bedPos;

   public S0APacketUseBed() {
   }

   public S0APacketUseBed(EntityPlayer var1, BlockPos var2) {
      this.playerID = var1.getEntityID();
      this.bedPos = var2;
   }

   public void readPacketData(PacketBuffer var1) throws IOException {
      this.playerID = var1.readVarIntFromBuffer();
      this.bedPos = var1.readBlockPos();
   }

   public void writePacketData(PacketBuffer var1) throws IOException {
      var1.writeVarIntToBuffer(this.playerID);
      var1.writeBlockPos(this.bedPos);
   }

   public void processPacket(INetHandlerPlayClient var1) {
      var1.handleUseBed(this);
   }

   public EntityPlayer getPlayer(World var1) {
      return (EntityPlayer)var1.getEntityByID(this.playerID);
   }

   public BlockPos getBedPosition() {
      return this.bedPos;
   }
}
