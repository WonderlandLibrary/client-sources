/*    */ package net.minecraft.network.play.server;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayClient;
/*    */ import net.minecraft.util.BlockPos;
/*    */ 
/*    */ 
/*    */ public class S05PacketSpawnPosition
/*    */   implements Packet<INetHandlerPlayClient>
/*    */ {
/*    */   private BlockPos spawnBlockPos;
/*    */   
/*    */   public S05PacketSpawnPosition() {}
/*    */   
/*    */   public S05PacketSpawnPosition(BlockPos spawnBlockPosIn) {
/* 19 */     this.spawnBlockPos = spawnBlockPosIn;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) throws IOException {
/* 27 */     this.spawnBlockPos = buf.readBlockPos();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) throws IOException {
/* 35 */     buf.writeBlockPos(this.spawnBlockPos);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayClient handler) {
/* 43 */     handler.handleSpawnPosition(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public BlockPos getSpawnPos() {
/* 48 */     return this.spawnBlockPos;
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\network\play\server\S05PacketSpawnPosition.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */