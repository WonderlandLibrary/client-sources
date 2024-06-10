/*  1:   */ package net.minecraft.client.renderer.texture;
/*  2:   */ 
/*  3:   */ import com.google.common.collect.Lists;
/*  4:   */ import java.awt.Graphics;
/*  5:   */ import java.awt.image.BufferedImage;
/*  6:   */ import java.io.IOException;
/*  7:   */ import java.io.InputStream;
/*  8:   */ import java.util.Iterator;
/*  9:   */ import java.util.List;
/* 10:   */ import javax.imageio.ImageIO;
/* 11:   */ import net.minecraft.client.resources.IResource;
/* 12:   */ import net.minecraft.client.resources.IResourceManager;
/* 13:   */ import net.minecraft.util.ResourceLocation;
/* 14:   */ import org.apache.logging.log4j.LogManager;
/* 15:   */ import org.apache.logging.log4j.Logger;
/* 16:   */ 
/* 17:   */ public class LayeredTexture
/* 18:   */   extends AbstractTexture
/* 19:   */ {
/* 20:18 */   private static final Logger logger = ;
/* 21:   */   public final List layeredTextureNames;
/* 22:   */   private static final String __OBFID = "CL_00001051";
/* 23:   */   
/* 24:   */   public LayeredTexture(String... par1ArrayOfStr)
/* 25:   */   {
/* 26:24 */     this.layeredTextureNames = Lists.newArrayList(par1ArrayOfStr);
/* 27:   */   }
/* 28:   */   
/* 29:   */   public void loadTexture(IResourceManager par1ResourceManager)
/* 30:   */     throws IOException
/* 31:   */   {
/* 32:29 */     func_147631_c();
/* 33:30 */     BufferedImage var2 = null;
/* 34:   */     try
/* 35:   */     {
/* 36:34 */       Iterator var3 = this.layeredTextureNames.iterator();
/* 37:36 */       while (var3.hasNext())
/* 38:   */       {
/* 39:38 */         String var4 = (String)var3.next();
/* 40:40 */         if (var4 != null)
/* 41:   */         {
/* 42:42 */           InputStream var5 = par1ResourceManager.getResource(new ResourceLocation(var4)).getInputStream();
/* 43:43 */           BufferedImage var6 = ImageIO.read(var5);
/* 44:45 */           if (var2 == null) {
/* 45:47 */             var2 = new BufferedImage(var6.getWidth(), var6.getHeight(), 2);
/* 46:   */           }
/* 47:50 */           var2.getGraphics().drawImage(var6, 0, 0, null);
/* 48:   */         }
/* 49:   */       }
/* 50:   */     }
/* 51:   */     catch (IOException var7)
/* 52:   */     {
/* 53:56 */       logger.error("Couldn't load layered image", var7);
/* 54:57 */       return;
/* 55:   */     }
/* 56:60 */     TextureUtil.uploadTextureImage(getGlTextureId(), var2);
/* 57:   */   }
/* 58:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.renderer.texture.LayeredTexture
 * JD-Core Version:    0.7.0.1
 */