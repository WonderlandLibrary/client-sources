package net.minecraft.client.renderer.texture;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class TextureCompass extends TextureAtlasSprite {
   public double currentAngle;
   public double angleDelta;
   public static String field_176608_l;
   private static final String F = "CL_00001071";

   public TextureCompass(String var1) {
      super(var1);
      field_176608_l = var1;
   }

   public void updateAnimation() {
      Minecraft var1 = Minecraft.getInstance();
      if(var1.world != null && var1.player != null) {
         this.a(var1.world, var1.player.posX, var1.player.posZ, (double)var1.player.rotationYaw, false, false);
      } else {
         this.a((World)null, 0.0D, 0.0D, 0.0D, true, false);
      }

   }

   public void a(World var1, double var2, double var4, double var6, boolean var8, boolean var9) {
      if(!this.framesTextureData.isEmpty()) {
         double var10 = 0.0D;
         BlockPos var12 = var1.getSpawnPoint();
         double var13 = (double)var12.getX() - var2;
         double var15 = (double)var12.getZ() - var4;
         var6 = var6 % 360.0D;
         var10 = -((var6 - 90.0D) * 3.141592653589793D / 180.0D - Math.atan2(var15, var13));
         if(!var1.provider.isSurfaceWorld()) {
            var10 = Math.random() * 3.141592653589793D * 2.0D;
         }

         this.currentAngle = var10;
         int var19 = (int)((this.currentAngle / 6.283185307179586D + 1.0D) * (double)this.framesTextureData.size()) % this.framesTextureData.size();

         while(true) {
            var19 = (var19 + this.framesTextureData.size()) % this.framesTextureData.size();
         }
      }

   }
}
