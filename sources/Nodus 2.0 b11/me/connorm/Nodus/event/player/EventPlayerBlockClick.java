/*  1:   */ package me.connorm.Nodus.event.player;
/*  2:   */ 
/*  3:   */ import net.minecraft.entity.player.EntityPlayer;
/*  4:   */ 
/*  5:   */ public class EventPlayerBlockClick
/*  6:   */   extends EventPlayer
/*  7:   */ {
/*  8:   */   private int blockX;
/*  9:   */   private int blockY;
/* 10:   */   private int blockZ;
/* 11:   */   
/* 12:   */   public EventPlayerBlockClick(EntityPlayer thePlayer, int blockX, int blockY, int blockZ)
/* 13:   */   {
/* 14:11 */     super(thePlayer);
/* 15:12 */     this.blockX = blockX;
/* 16:13 */     this.blockY = blockY;
/* 17:14 */     this.blockZ = blockZ;
/* 18:   */   }
/* 19:   */   
/* 20:   */   public int getX()
/* 21:   */   {
/* 22:19 */     return this.blockX;
/* 23:   */   }
/* 24:   */   
/* 25:   */   public int getY()
/* 26:   */   {
/* 27:24 */     return this.blockY;
/* 28:   */   }
/* 29:   */   
/* 30:   */   public int getZ()
/* 31:   */   {
/* 32:29 */     return this.blockZ;
/* 33:   */   }
/* 34:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     me.connorm.Nodus.event.player.EventPlayerBlockClick
 * JD-Core Version:    0.7.0.1
 */