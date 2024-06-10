/*  1:   */ package net.minecraft.client.renderer.texture;
/*  2:   */ 
/*  3:   */ import java.awt.image.BufferedImage;
/*  4:   */ import java.io.IOException;
/*  5:   */ import net.minecraft.client.resources.IResourceManager;
/*  6:   */ 
/*  7:   */ public class DynamicTexture
/*  8:   */   extends AbstractTexture
/*  9:   */ {
/* 10:   */   private final int[] dynamicTextureData;
/* 11:   */   private final int width;
/* 12:   */   private final int height;
/* 13:   */   private static final String __OBFID = "CL_00001048";
/* 14:   */   
/* 15:   */   public DynamicTexture(BufferedImage par1BufferedImage)
/* 16:   */   {
/* 17:20 */     this(par1BufferedImage.getWidth(), par1BufferedImage.getHeight());
/* 18:21 */     par1BufferedImage.getRGB(0, 0, par1BufferedImage.getWidth(), par1BufferedImage.getHeight(), this.dynamicTextureData, 0, par1BufferedImage.getWidth());
/* 19:22 */     updateDynamicTexture();
/* 20:   */   }
/* 21:   */   
/* 22:   */   public DynamicTexture(int par1, int par2)
/* 23:   */   {
/* 24:27 */     this.width = par1;
/* 25:28 */     this.height = par2;
/* 26:29 */     this.dynamicTextureData = new int[par1 * par2];
/* 27:30 */     TextureUtil.allocateTexture(getGlTextureId(), par1, par2);
/* 28:   */   }
/* 29:   */   
/* 30:   */   public void loadTexture(IResourceManager par1ResourceManager)
/* 31:   */     throws IOException
/* 32:   */   {}
/* 33:   */   
/* 34:   */   public void updateDynamicTexture()
/* 35:   */   {
/* 36:37 */     TextureUtil.uploadTexture(getGlTextureId(), this.dynamicTextureData, this.width, this.height);
/* 37:   */   }
/* 38:   */   
/* 39:   */   public int[] getTextureData()
/* 40:   */   {
/* 41:42 */     return this.dynamicTextureData;
/* 42:   */   }
/* 43:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.renderer.texture.DynamicTexture
 * JD-Core Version:    0.7.0.1
 */