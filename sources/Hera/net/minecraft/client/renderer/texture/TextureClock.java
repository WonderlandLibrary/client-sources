/*    */ package net.minecraft.client.renderer.texture;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.util.MathHelper;
/*    */ 
/*    */ public class TextureClock
/*    */   extends TextureAtlasSprite
/*    */ {
/*    */   private double field_94239_h;
/*    */   private double field_94240_i;
/*    */   
/*    */   public TextureClock(String iconName) {
/* 13 */     super(iconName);
/*    */   }
/*    */ 
/*    */   
/*    */   public void updateAnimation() {
/* 18 */     if (!this.framesTextureData.isEmpty()) {
/*    */       
/* 20 */       Minecraft minecraft = Minecraft.getMinecraft();
/* 21 */       double d0 = 0.0D;
/*    */       
/* 23 */       if (minecraft.theWorld != null && minecraft.thePlayer != null) {
/*    */         
/* 25 */         d0 = minecraft.theWorld.getCelestialAngle(1.0F);
/*    */         
/* 27 */         if (!minecraft.theWorld.provider.isSurfaceWorld())
/*    */         {
/* 29 */           d0 = Math.random();
/*    */         }
/*    */       } 
/*    */       
/*    */       double d1;
/*    */       
/* 35 */       for (d1 = d0 - this.field_94239_h; d1 < -0.5D; d1++);
/*    */ 
/*    */ 
/*    */ 
/*    */       
/* 40 */       while (d1 >= 0.5D)
/*    */       {
/* 42 */         d1--;
/*    */       }
/*    */       
/* 45 */       d1 = MathHelper.clamp_double(d1, -1.0D, 1.0D);
/* 46 */       this.field_94240_i += d1 * 0.1D;
/* 47 */       this.field_94240_i *= 0.8D;
/* 48 */       this.field_94239_h += this.field_94240_i;
/*    */       
/*    */       int i;
/* 51 */       for (i = (int)((this.field_94239_h + 1.0D) * this.framesTextureData.size()) % this.framesTextureData.size(); i < 0; i = (i + this.framesTextureData.size()) % this.framesTextureData.size());
/*    */ 
/*    */ 
/*    */ 
/*    */       
/* 56 */       if (i != this.frameCounter) {
/*    */         
/* 58 */         this.frameCounter = i;
/* 59 */         TextureUtil.uploadTextureMipmap(this.framesTextureData.get(this.frameCounter), this.width, this.height, this.originX, this.originY, false, false);
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\client\renderer\texture\TextureClock.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */