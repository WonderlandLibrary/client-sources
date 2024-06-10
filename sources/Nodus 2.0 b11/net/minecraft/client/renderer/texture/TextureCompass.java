/*   1:    */ package net.minecraft.client.renderer.texture;
/*   2:    */ 
/*   3:    */ import java.util.List;
/*   4:    */ import net.minecraft.client.Minecraft;
/*   5:    */ import net.minecraft.client.entity.EntityClientPlayerMP;
/*   6:    */ import net.minecraft.util.ChunkCoordinates;
/*   7:    */ import net.minecraft.world.World;
/*   8:    */ import net.minecraft.world.WorldProvider;
/*   9:    */ 
/*  10:    */ public class TextureCompass
/*  11:    */   extends TextureAtlasSprite
/*  12:    */ {
/*  13:    */   public double currentAngle;
/*  14:    */   public double angleDelta;
/*  15:    */   private static final String __OBFID = "CL_00001071";
/*  16:    */   
/*  17:    */   public TextureCompass(String par1Str)
/*  18:    */   {
/*  19: 18 */     super(par1Str);
/*  20:    */   }
/*  21:    */   
/*  22:    */   public void updateAnimation()
/*  23:    */   {
/*  24: 23 */     Minecraft var1 = Minecraft.getMinecraft();
/*  25: 25 */     if ((var1.theWorld != null) && (var1.thePlayer != null)) {
/*  26: 27 */       updateCompass(var1.theWorld, var1.thePlayer.posX, var1.thePlayer.posZ, var1.thePlayer.rotationYaw, false, false);
/*  27:    */     } else {
/*  28: 31 */       updateCompass(null, 0.0D, 0.0D, 0.0D, true, false);
/*  29:    */     }
/*  30:    */   }
/*  31:    */   
/*  32:    */   public void updateCompass(World par1World, double par2, double par4, double par6, boolean par8, boolean par9)
/*  33:    */   {
/*  34: 40 */     if (!this.framesTextureData.isEmpty())
/*  35:    */     {
/*  36: 42 */       double var10 = 0.0D;
/*  37: 44 */       if ((par1World != null) && (!par8))
/*  38:    */       {
/*  39: 46 */         ChunkCoordinates var12 = par1World.getSpawnPoint();
/*  40: 47 */         double var13 = var12.posX - par2;
/*  41: 48 */         double var15 = var12.posZ - par4;
/*  42: 49 */         par6 %= 360.0D;
/*  43: 50 */         var10 = -((par6 - 90.0D) * 3.141592653589793D / 180.0D - Math.atan2(var15, var13));
/*  44: 52 */         if (!par1World.provider.isSurfaceWorld()) {
/*  45: 54 */           var10 = Math.random() * 3.141592653589793D * 2.0D;
/*  46:    */         }
/*  47:    */       }
/*  48: 58 */       if (par9)
/*  49:    */       {
/*  50: 60 */         this.currentAngle = var10;
/*  51:    */       }
/*  52:    */       else
/*  53:    */       {
/*  54: 66 */         for (double var17 = var10 - this.currentAngle; var17 < -3.141592653589793D; var17 += 6.283185307179586D) {}
/*  55: 71 */         while (var17 >= 3.141592653589793D) {
/*  56: 73 */           var17 -= 6.283185307179586D;
/*  57:    */         }
/*  58: 76 */         if (var17 < -1.0D) {
/*  59: 78 */           var17 = -1.0D;
/*  60:    */         }
/*  61: 81 */         if (var17 > 1.0D) {
/*  62: 83 */           var17 = 1.0D;
/*  63:    */         }
/*  64: 86 */         this.angleDelta += var17 * 0.1D;
/*  65: 87 */         this.angleDelta *= 0.8D;
/*  66: 88 */         this.currentAngle += this.angleDelta;
/*  67:    */       }
/*  68: 93 */       for (int var18 = (int)((this.currentAngle / 6.283185307179586D + 1.0D) * this.framesTextureData.size()) % this.framesTextureData.size(); var18 < 0; var18 = (var18 + this.framesTextureData.size()) % this.framesTextureData.size()) {}
/*  69: 98 */       if (var18 != this.frameCounter)
/*  70:    */       {
/*  71:100 */         this.frameCounter = var18;
/*  72:101 */         TextureUtil.func_147955_a((int[][])this.framesTextureData.get(this.frameCounter), this.width, this.height, this.originX, this.originY, false, false);
/*  73:    */       }
/*  74:    */     }
/*  75:    */   }
/*  76:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.renderer.texture.TextureCompass
 * JD-Core Version:    0.7.0.1
 */