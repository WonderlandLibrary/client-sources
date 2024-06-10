/*  1:   */ package net.minecraft.client.renderer.texture;
/*  2:   */ 
/*  3:   */ public abstract class AbstractTexture
/*  4:   */   implements ITextureObject
/*  5:   */ {
/*  6: 5 */   protected int glTextureId = -1;
/*  7:   */   private static final String __OBFID = "CL_00001047";
/*  8:   */   
/*  9:   */   public int getGlTextureId()
/* 10:   */   {
/* 11:10 */     if (this.glTextureId == -1) {
/* 12:12 */       this.glTextureId = TextureUtil.glGenTextures();
/* 13:   */     }
/* 14:15 */     return this.glTextureId;
/* 15:   */   }
/* 16:   */   
/* 17:   */   public void func_147631_c()
/* 18:   */   {
/* 19:20 */     if (this.glTextureId != -1)
/* 20:   */     {
/* 21:22 */       TextureUtil.deleteTexture(this.glTextureId);
/* 22:23 */       this.glTextureId = -1;
/* 23:   */     }
/* 24:   */   }
/* 25:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.renderer.texture.AbstractTexture
 * JD-Core Version:    0.7.0.1
 */