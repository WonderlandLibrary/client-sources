/*  1:   */ package net.minecraft.client.renderer.entity;
/*  2:   */ 
/*  3:   */ import net.minecraft.entity.Entity;
/*  4:   */ import net.minecraft.util.ResourceLocation;
/*  5:   */ import org.lwjgl.opengl.GL11;
/*  6:   */ 
/*  7:   */ public class RenderEntity
/*  8:   */   extends Render
/*  9:   */ {
/* 10:   */   private static final String __OBFID = "CL_00000986";
/* 11:   */   
/* 12:   */   public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
/* 13:   */   {
/* 14:19 */     GL11.glPushMatrix();
/* 15:20 */     renderOffsetAABB(par1Entity.boundingBox, par2 - par1Entity.lastTickPosX, par4 - par1Entity.lastTickPosY, par6 - par1Entity.lastTickPosZ);
/* 16:21 */     GL11.glPopMatrix();
/* 17:   */   }
/* 18:   */   
/* 19:   */   protected ResourceLocation getEntityTexture(Entity par1Entity)
/* 20:   */   {
/* 21:29 */     return null;
/* 22:   */   }
/* 23:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.renderer.entity.RenderEntity
 * JD-Core Version:    0.7.0.1
 */