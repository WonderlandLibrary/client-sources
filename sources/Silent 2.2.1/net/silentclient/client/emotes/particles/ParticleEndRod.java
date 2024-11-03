package net.silentclient.client.emotes.particles;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class ParticleEndRod extends EntityFX {
   public static final ResourceLocation TEXTURE = new ResourceLocation("silentclient/emotes/particles/mc-particles.png");
   private final int numAgingFrames = 8;

   public ParticleEndRod(World world, double d0, double d1, double d2, double d3, double d4, double d5) {
      super(world, d0, d1, d2, d3, d4, d5);
      this.motionX = d3;
      this.motionY = d4;
      this.motionZ = d5;
      this.particleScale *= 0.75F;
      this.particleMaxAge = 60 + this.rand.nextInt(12);
      this.particleTextureIndexX = 0;
      this.particleTextureIndexY = 11;
   }

   @Override
   public void renderParticle(WorldRenderer worldrenderer, Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      Minecraft.getMinecraft().getTextureManager().bindTexture(TEXTURE);
      if (worldrenderer != null) {
         super.renderParticle(worldrenderer, entity, f, f1, f2, f3, f4, f5);
      }
   }

   @Override
   public void onUpdate() {
      super.onUpdate();
      this.particleTextureIndexX = this.numAgingFrames - 1 - this.particleAge * this.numAgingFrames / this.particleMaxAge;
   }

   @Override
   public int getFXLayer() {
      return 2;
   }
}
