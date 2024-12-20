/*  1:   */ package net.minecraft.client.resources;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import net.minecraft.client.renderer.texture.TextureUtil;
/*  5:   */ import net.minecraft.util.ResourceLocation;
/*  6:   */ import net.minecraft.world.ColorizerGrass;
/*  7:   */ 
/*  8:   */ public class GrassColorReloadListener
/*  9:   */   implements IResourceManagerReloadListener
/* 10:   */ {
/* 11:10 */   private static final ResourceLocation field_130078_a = new ResourceLocation("textures/colormap/grass.png");
/* 12:   */   private static final String __OBFID = "CL_00001078";
/* 13:   */   
/* 14:   */   public void onResourceManagerReload(IResourceManager par1ResourceManager)
/* 15:   */   {
/* 16:   */     try
/* 17:   */     {
/* 18:17 */       ColorizerGrass.setGrassBiomeColorizer(TextureUtil.readImageData(par1ResourceManager, field_130078_a));
/* 19:   */     }
/* 20:   */     catch (IOException localIOException) {}
/* 21:   */   }
/* 22:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.resources.GrassColorReloadListener
 * JD-Core Version:    0.7.0.1
 */