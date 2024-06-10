/*  1:   */ package net.minecraft.client.renderer.texture;
/*  2:   */ 
/*  3:   */ import java.util.List;
/*  4:   */ import net.minecraft.client.Minecraft;
/*  5:   */ import net.minecraft.client.multiplayer.WorldClient;
/*  6:   */ import net.minecraft.world.WorldProvider;
/*  7:   */ 
/*  8:   */ public class TextureClock
/*  9:   */   extends TextureAtlasSprite
/* 10:   */ {
/* 11:   */   private double field_94239_h;
/* 12:   */   private double field_94240_i;
/* 13:   */   private static final String __OBFID = "CL_00001070";
/* 14:   */   
/* 15:   */   public TextureClock(String par1Str)
/* 16:   */   {
/* 17:13 */     super(par1Str);
/* 18:   */   }
/* 19:   */   
/* 20:   */   public void updateAnimation()
/* 21:   */   {
/* 22:18 */     if (!this.framesTextureData.isEmpty())
/* 23:   */     {
/* 24:20 */       Minecraft var1 = Minecraft.getMinecraft();
/* 25:21 */       double var2 = 0.0D;
/* 26:23 */       if ((var1.theWorld != null) && (var1.thePlayer != null))
/* 27:   */       {
/* 28:25 */         float var4 = var1.theWorld.getCelestialAngle(1.0F);
/* 29:26 */         var2 = var4;
/* 30:28 */         if (!var1.theWorld.provider.isSurfaceWorld()) {
/* 31:30 */           var2 = Math.random();
/* 32:   */         }
/* 33:   */       }
/* 34:36 */       for (double var7 = var2 - this.field_94239_h; var7 < -0.5D; var7 += 1.0D) {}
/* 35:41 */       while (var7 >= 0.5D) {
/* 36:43 */         var7 -= 1.0D;
/* 37:   */       }
/* 38:46 */       if (var7 < -1.0D) {
/* 39:48 */         var7 = -1.0D;
/* 40:   */       }
/* 41:51 */       if (var7 > 1.0D) {
/* 42:53 */         var7 = 1.0D;
/* 43:   */       }
/* 44:56 */       this.field_94240_i += var7 * 0.1D;
/* 45:57 */       this.field_94240_i *= 0.8D;
/* 46:58 */       this.field_94239_h += this.field_94240_i;
/* 47:61 */       for (int var6 = (int)((this.field_94239_h + 1.0D) * this.framesTextureData.size()) % this.framesTextureData.size(); var6 < 0; var6 = (var6 + this.framesTextureData.size()) % this.framesTextureData.size()) {}
/* 48:66 */       if (var6 != this.frameCounter)
/* 49:   */       {
/* 50:68 */         this.frameCounter = var6;
/* 51:69 */         TextureUtil.func_147955_a((int[][])this.framesTextureData.get(this.frameCounter), this.width, this.height, this.originX, this.originY, false, false);
/* 52:   */       }
/* 53:   */     }
/* 54:   */   }
/* 55:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.renderer.texture.TextureClock
 * JD-Core Version:    0.7.0.1
 */