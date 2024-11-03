package net.silentclient.client.emotes.animation;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

public class AnimationMeshConfig {
   public ResourceLocation texture;
   public boolean normals = false;
   public boolean visible = true;
   public boolean smooth = false;
   public int color = 16777215;
   public float alpha = 1.0F;

   public void bindTexture() {
      if (this.texture != null) {
         Minecraft.getMinecraft().getTextureManager().bindTexture(this.texture);
      }
   }

   public AnimationMeshConfig clone() {
      AnimationMeshConfig animationmeshconfig = new AnimationMeshConfig();
      animationmeshconfig.texture = this.texture;
      animationmeshconfig.normals = this.normals;
      animationmeshconfig.smooth = this.smooth;
      animationmeshconfig.visible = this.visible;
      animationmeshconfig.color = this.color;
      animationmeshconfig.alpha = this.alpha;
      return animationmeshconfig;
   }
}
