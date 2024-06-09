/*    */ package net.minecraft.client.renderer.texture;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.util.MathHelper;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TextureCompass
/*    */   extends TextureAtlasSprite
/*    */ {
/*    */   public double currentAngle;
/*    */   public double angleDelta;
/*    */   public static String field_176608_l;
/*    */   
/*    */   public TextureCompass(String iconName) {
/* 19 */     super(iconName);
/* 20 */     field_176608_l = iconName;
/*    */   }
/*    */ 
/*    */   
/*    */   public void updateAnimation() {
/* 25 */     Minecraft minecraft = Minecraft.getMinecraft();
/*    */     
/* 27 */     if (minecraft.theWorld != null && minecraft.thePlayer != null) {
/*    */       
/* 29 */       updateCompass((World)minecraft.theWorld, minecraft.thePlayer.posX, minecraft.thePlayer.posZ, minecraft.thePlayer.rotationYaw, false, false);
/*    */     }
/*    */     else {
/*    */       
/* 33 */       updateCompass((World)null, 0.0D, 0.0D, 0.0D, true, false);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void updateCompass(World worldIn, double p_94241_2_, double p_94241_4_, double p_94241_6_, boolean p_94241_8_, boolean p_94241_9_) {
/* 42 */     if (!this.framesTextureData.isEmpty()) {
/*    */       
/* 44 */       double d0 = 0.0D;
/*    */       
/* 46 */       if (worldIn != null && !p_94241_8_) {
/*    */         
/* 48 */         BlockPos blockpos = worldIn.getSpawnPoint();
/* 49 */         double d1 = blockpos.getX() - p_94241_2_;
/* 50 */         double d2 = blockpos.getZ() - p_94241_4_;
/* 51 */         p_94241_6_ %= 360.0D;
/* 52 */         d0 = -((p_94241_6_ - 90.0D) * Math.PI / 180.0D - Math.atan2(d2, d1));
/*    */         
/* 54 */         if (!worldIn.provider.isSurfaceWorld())
/*    */         {
/* 56 */           d0 = Math.random() * Math.PI * 2.0D;
/*    */         }
/*    */       } 
/*    */       
/* 60 */       if (p_94241_9_) {
/*    */         
/* 62 */         this.currentAngle = d0;
/*    */       } else {
/*    */         double d3;
/*    */ 
/*    */ 
/*    */         
/* 68 */         for (d3 = d0 - this.currentAngle; d3 < -3.141592653589793D; d3 += 6.283185307179586D);
/*    */ 
/*    */ 
/*    */ 
/*    */         
/* 73 */         while (d3 >= Math.PI)
/*    */         {
/* 75 */           d3 -= 6.283185307179586D;
/*    */         }
/*    */         
/* 78 */         d3 = MathHelper.clamp_double(d3, -1.0D, 1.0D);
/* 79 */         this.angleDelta += d3 * 0.1D;
/* 80 */         this.angleDelta *= 0.8D;
/* 81 */         this.currentAngle += this.angleDelta;
/*    */       } 
/*    */       
/*    */       int i;
/*    */       
/* 86 */       for (i = (int)((this.currentAngle / 6.283185307179586D + 1.0D) * this.framesTextureData.size()) % this.framesTextureData.size(); i < 0; i = (i + this.framesTextureData.size()) % this.framesTextureData.size());
/*    */ 
/*    */ 
/*    */ 
/*    */       
/* 91 */       if (i != this.frameCounter) {
/*    */         
/* 93 */         this.frameCounter = i;
/* 94 */         TextureUtil.uploadTextureMipmap(this.framesTextureData.get(this.frameCounter), this.width, this.height, this.originX, this.originY, false, false);
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\client\renderer\texture\TextureCompass.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */