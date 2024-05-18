/*    */ package net.minecraft.network;
/*    */ 
/*    */ import net.minecraft.util.IThreadListener;
/*    */ 
/*    */ 
/*    */ public class PacketThreadUtil
/*    */ {
/*    */   public static <T extends INetHandler> void checkThreadAndEnqueue(final Packet<T> p_180031_0_, final T p_180031_1_, IThreadListener p_180031_2_) throws ThreadQuickExitException {
/*  9 */     if (!p_180031_2_.isCallingFromMinecraftThread()) {
/*    */       
/* 11 */       p_180031_2_.addScheduledTask(new Runnable()
/*    */           {
/*    */             public void run()
/*    */             {
/* 15 */               p_180031_0_.processPacket(p_180031_1_);
/*    */             }
/*    */           });
/* 18 */       throw ThreadQuickExitException.field_179886_a;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\network\PacketThreadUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */