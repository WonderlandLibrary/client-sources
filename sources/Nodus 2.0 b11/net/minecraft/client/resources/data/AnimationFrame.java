/*  1:   */ package net.minecraft.client.resources.data;
/*  2:   */ 
/*  3:   */ public class AnimationFrame
/*  4:   */ {
/*  5:   */   private final int frameIndex;
/*  6:   */   private final int frameTime;
/*  7:   */   private static final String __OBFID = "CL_00001104";
/*  8:   */   
/*  9:   */   public AnimationFrame(int par1)
/* 10:   */   {
/* 11:11 */     this(par1, -1);
/* 12:   */   }
/* 13:   */   
/* 14:   */   public AnimationFrame(int par1, int par2)
/* 15:   */   {
/* 16:16 */     this.frameIndex = par1;
/* 17:17 */     this.frameTime = par2;
/* 18:   */   }
/* 19:   */   
/* 20:   */   public boolean hasNoTime()
/* 21:   */   {
/* 22:22 */     return this.frameTime == -1;
/* 23:   */   }
/* 24:   */   
/* 25:   */   public int getFrameTime()
/* 26:   */   {
/* 27:27 */     return this.frameTime;
/* 28:   */   }
/* 29:   */   
/* 30:   */   public int getFrameIndex()
/* 31:   */   {
/* 32:32 */     return this.frameIndex;
/* 33:   */   }
/* 34:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.resources.data.AnimationFrame
 * JD-Core Version:    0.7.0.1
 */