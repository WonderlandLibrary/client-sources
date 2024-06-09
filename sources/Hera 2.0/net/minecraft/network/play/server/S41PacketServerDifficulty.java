/*    */ package net.minecraft.network.play.server;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayClient;
/*    */ import net.minecraft.world.EnumDifficulty;
/*    */ 
/*    */ 
/*    */ public class S41PacketServerDifficulty
/*    */   implements Packet<INetHandlerPlayClient>
/*    */ {
/*    */   private EnumDifficulty difficulty;
/*    */   private boolean difficultyLocked;
/*    */   
/*    */   public S41PacketServerDifficulty() {}
/*    */   
/*    */   public S41PacketServerDifficulty(EnumDifficulty difficultyIn, boolean lockedIn) {
/* 20 */     this.difficulty = difficultyIn;
/* 21 */     this.difficultyLocked = lockedIn;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayClient handler) {
/* 29 */     handler.handleServerDifficulty(this);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) throws IOException {
/* 37 */     this.difficulty = EnumDifficulty.getDifficultyEnum(buf.readUnsignedByte());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) throws IOException {
/* 45 */     buf.writeByte(this.difficulty.getDifficultyId());
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isDifficultyLocked() {
/* 50 */     return this.difficultyLocked;
/*    */   }
/*    */ 
/*    */   
/*    */   public EnumDifficulty getDifficulty() {
/* 55 */     return this.difficulty;
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\network\play\server\S41PacketServerDifficulty.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */