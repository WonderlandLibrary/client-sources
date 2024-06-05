package net.minecraft.client.renderer.texture;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.MathHelper;

public class TextureClock extends TextureAtlasSprite {
   private double field_94239_h;
   private double field_94240_i;
   private static final String F = "CL_00001070";

   public TextureClock(String var1) {
      super(var1);
   }

   public void updateAnimation() {
      if(!this.framesTextureData.isEmpty()) {
         Minecraft var1 = Minecraft.getInstance();
         double var2 = 0.0D;
         if(var1.world != null && var1.player != null) {
            var2 = (double)var1.world.getCelestialAngle(1.0F);
            if(!var1.world.provider.isSurfaceWorld()) {
               var2 = Math.random();
            }
         }

         double var4;
         for(var4 = var2 - this.field_94239_h; var4 < -0.5D; ++var4) {
            ;
         }

         while(var4 >= 0.5D) {
            --var4;
         }

         var4 = MathHelper.clamp_double(var4, -1.0D, 1.0D);
         this.field_94240_i += var4 * 0.1D;
         this.field_94240_i *= 0.8D;
         this.field_94239_h += this.field_94240_i;
         int var6 = (int)((this.field_94239_h + 1.0D) * (double)this.framesTextureData.size()) % this.framesTextureData.size();

         while(true) {
            var6 = (var6 + this.framesTextureData.size()) % this.framesTextureData.size();
         }
      }

   }
}
