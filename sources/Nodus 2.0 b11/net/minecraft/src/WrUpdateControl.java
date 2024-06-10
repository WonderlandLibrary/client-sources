/*  1:   */ package net.minecraft.src;
/*  2:   */ 
/*  3:   */ import net.minecraft.client.Minecraft;
/*  4:   */ import net.minecraft.client.multiplayer.WorldClient;
/*  5:   */ import net.minecraft.util.AABBPool;
/*  6:   */ import net.minecraft.util.AxisAlignedBB;
/*  7:   */ import net.minecraft.util.Vec3Pool;
/*  8:   */ 
/*  9:   */ public class WrUpdateControl
/* 10:   */   implements IWrUpdateControl
/* 11:   */ {
/* 12:   */   private boolean hasForge;
/* 13:   */   private int renderPass;
/* 14:   */   
/* 15:   */   public WrUpdateControl()
/* 16:   */   {
/* 17:13 */     this.hasForge = Reflector.ForgeHooksClient.exists();
/* 18:14 */     this.renderPass = 0;
/* 19:   */   }
/* 20:   */   
/* 21:   */   public void resume() {}
/* 22:   */   
/* 23:   */   public void pause()
/* 24:   */   {
/* 25:21 */     AxisAlignedBB.getAABBPool().cleanPool();
/* 26:22 */     WorldClient theWorld = Config.getMinecraft().theWorld;
/* 27:24 */     if (theWorld != null) {
/* 28:26 */       theWorld.getWorldVec3Pool().clear();
/* 29:   */     }
/* 30:   */   }
/* 31:   */   
/* 32:   */   public void setRenderPass(int renderPass)
/* 33:   */   {
/* 34:32 */     this.renderPass = renderPass;
/* 35:   */   }
/* 36:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.src.WrUpdateControl
 * JD-Core Version:    0.7.0.1
 */