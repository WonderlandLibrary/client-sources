package net.minecraft.client.particle;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.CopyOnWriteArrayList;
import net.TN;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.particle.Barrier;
import net.minecraft.client.particle.Barrier$Factory;
import net.minecraft.client.particle.EntityAuraFX$Factory;
import net.minecraft.client.particle.EntityAuraFX$HappyVillagerFactory;
import net.minecraft.client.particle.EntityBlockDustFX$Factory;
import net.minecraft.client.particle.EntityBreakingFX$Factory;
import net.minecraft.client.particle.EntityBreakingFX$SlimeFactory;
import net.minecraft.client.particle.EntityBreakingFX$SnowballFactory;
import net.minecraft.client.particle.EntityBubbleFX$Factory;
import net.minecraft.client.particle.EntityCloudFX$Factory;
import net.minecraft.client.particle.EntityCrit2FX$Factory;
import net.minecraft.client.particle.EntityCrit2FX$MagicFactory;
import net.minecraft.client.particle.EntityCritFX$Factory;
import net.minecraft.client.particle.EntityDiggingFX;
import net.minecraft.client.particle.EntityDiggingFX$Factory;
import net.minecraft.client.particle.EntityDropParticleFX$LavaFactory;
import net.minecraft.client.particle.EntityDropParticleFX$WaterFactory;
import net.minecraft.client.particle.EntityEnchantmentTableParticleFX$EnchantmentTable;
import net.minecraft.client.particle.EntityExplodeFX$Factory;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.particle.EntityFirework$Factory;
import net.minecraft.client.particle.EntityFirework$SparkFX;
import net.minecraft.client.particle.EntityFishWakeFX$Factory;
import net.minecraft.client.particle.EntityFlameFX$Factory;
import net.minecraft.client.particle.EntityFootStepFX$Factory;
import net.minecraft.client.particle.EntityHeartFX$AngryVillagerFactory;
import net.minecraft.client.particle.EntityHeartFX$Factory;
import net.minecraft.client.particle.EntityHugeExplodeFX$Factory;
import net.minecraft.client.particle.EntityLargeExplodeFX$Factory;
import net.minecraft.client.particle.EntityLavaFX$Factory;
import net.minecraft.client.particle.EntityNoteFX$Factory;
import net.minecraft.client.particle.EntityParticleEmitter;
import net.minecraft.client.particle.EntityPortalFX$Factory;
import net.minecraft.client.particle.EntityRainFX$Factory;
import net.minecraft.client.particle.EntityReddustFX$Factory;
import net.minecraft.client.particle.EntitySmokeFX$Factory;
import net.minecraft.client.particle.EntitySnowShovelFX$Factory;
import net.minecraft.client.particle.EntitySpellParticleFX$AmbientMobFactory;
import net.minecraft.client.particle.EntitySpellParticleFX$Factory;
import net.minecraft.client.particle.EntitySpellParticleFX$InstantFactory;
import net.minecraft.client.particle.EntitySpellParticleFX$MobFactory;
import net.minecraft.client.particle.EntitySpellParticleFX$WitchFactory;
import net.minecraft.client.particle.EntitySplashFX$Factory;
import net.minecraft.client.particle.EntitySuspendFX$Factory;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.MobAppearance$Factory;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ReportedException;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.optifine.Config;
import net.optifine.Reflector;

public class EffectRenderer {
   private static final ResourceLocation particleTextures = new ResourceLocation("textures/particle/particles.png");
   private static final String d = "CL_00000915";
   protected World worldObj;
   private List[][] fxLayers = new CopyOnWriteArrayList[4][];
   private List c = new CopyOnWriteArrayList();
   private TextureManager renderer;
   private Random rand = new Random();
   private Map f = new HashMap();

   public EffectRenderer(World var1, TextureManager var2) {
      this.worldObj = var1;
      this.renderer = var2;

      for(int var3 = 0; var3 < 4; ++var3) {
         this.fxLayers[var3] = new CopyOnWriteArrayList[2];

         for(int var4 = 0; var4 < 2; ++var4) {
            this.fxLayers[var3][var4] = new CopyOnWriteArrayList();
         }
      }

      this.registerVanillaParticles();
   }

   private void registerVanillaParticles() {
      this.registerParticle(EnumParticleTypes.EXPLOSION_NORMAL.getParticleID(), new EntityExplodeFX$Factory());
      this.registerParticle(EnumParticleTypes.WATER_BUBBLE.getParticleID(), new EntityBubbleFX$Factory());
      this.registerParticle(EnumParticleTypes.WATER_SPLASH.getParticleID(), new EntitySplashFX$Factory());
      this.registerParticle(EnumParticleTypes.WATER_WAKE.getParticleID(), new EntityFishWakeFX$Factory());
      this.registerParticle(EnumParticleTypes.WATER_DROP.getParticleID(), new EntityRainFX$Factory());
      this.registerParticle(EnumParticleTypes.SUSPENDED.getParticleID(), new EntitySuspendFX$Factory());
      this.registerParticle(EnumParticleTypes.SUSPENDED_DEPTH.getParticleID(), new EntityAuraFX$Factory());
      this.registerParticle(EnumParticleTypes.CRIT.getParticleID(), new EntityCrit2FX$Factory());
      this.registerParticle(EnumParticleTypes.CRIT_MAGIC.getParticleID(), new EntityCrit2FX$MagicFactory());
      this.registerParticle(EnumParticleTypes.SMOKE_NORMAL.getParticleID(), new EntitySmokeFX$Factory());
      this.registerParticle(EnumParticleTypes.SMOKE_LARGE.getParticleID(), new EntityCritFX$Factory());
      this.registerParticle(EnumParticleTypes.SPELL.getParticleID(), new EntitySpellParticleFX$Factory());
      this.registerParticle(EnumParticleTypes.SPELL_INSTANT.getParticleID(), new EntitySpellParticleFX$InstantFactory());
      this.registerParticle(EnumParticleTypes.SPELL_MOB.getParticleID(), new EntitySpellParticleFX$MobFactory());
      this.registerParticle(EnumParticleTypes.SPELL_MOB_AMBIENT.getParticleID(), new EntitySpellParticleFX$AmbientMobFactory());
      this.registerParticle(EnumParticleTypes.SPELL_WITCH.getParticleID(), new EntitySpellParticleFX$WitchFactory());
      this.registerParticle(EnumParticleTypes.DRIP_WATER.getParticleID(), new EntityDropParticleFX$WaterFactory());
      this.registerParticle(EnumParticleTypes.DRIP_LAVA.getParticleID(), new EntityDropParticleFX$LavaFactory());
      this.registerParticle(EnumParticleTypes.VILLAGER_ANGRY.getParticleID(), new EntityHeartFX$AngryVillagerFactory());
      this.registerParticle(EnumParticleTypes.VILLAGER_HAPPY.getParticleID(), new EntityAuraFX$HappyVillagerFactory());
      this.registerParticle(EnumParticleTypes.TOWN_AURA.getParticleID(), new EntityAuraFX$Factory());
      this.registerParticle(EnumParticleTypes.NOTE.getParticleID(), new EntityNoteFX$Factory());
      this.registerParticle(EnumParticleTypes.PORTAL.getParticleID(), new EntityPortalFX$Factory());
      this.registerParticle(EnumParticleTypes.ENCHANTMENT_TABLE.getParticleID(), new EntityEnchantmentTableParticleFX$EnchantmentTable());
      this.registerParticle(EnumParticleTypes.FLAME.getParticleID(), new EntityFlameFX$Factory());
      this.registerParticle(EnumParticleTypes.LAVA.getParticleID(), new EntityLavaFX$Factory());
      this.registerParticle(EnumParticleTypes.FOOTSTEP.getParticleID(), new EntityFootStepFX$Factory());
      this.registerParticle(EnumParticleTypes.CLOUD.getParticleID(), new EntityCloudFX$Factory());
      this.registerParticle(EnumParticleTypes.REDSTONE.getParticleID(), new EntityReddustFX$Factory());
      this.registerParticle(EnumParticleTypes.SNOWBALL.getParticleID(), new EntityBreakingFX$SnowballFactory());
      this.registerParticle(EnumParticleTypes.SNOW_SHOVEL.getParticleID(), new EntitySnowShovelFX$Factory());
      this.registerParticle(EnumParticleTypes.SLIME.getParticleID(), new EntityBreakingFX$SlimeFactory());
      this.registerParticle(EnumParticleTypes.HEART.getParticleID(), new EntityHeartFX$Factory());
      this.registerParticle(EnumParticleTypes.BARRIER.getParticleID(), new Barrier$Factory());
      this.registerParticle(EnumParticleTypes.ITEM_CRACK.getParticleID(), new EntityBreakingFX$Factory());
      this.registerParticle(EnumParticleTypes.BLOCK_CRACK.getParticleID(), new EntityDiggingFX$Factory());
      this.registerParticle(EnumParticleTypes.BLOCK_DUST.getParticleID(), new EntityBlockDustFX$Factory());
      this.registerParticle(EnumParticleTypes.EXPLOSION_HUGE.getParticleID(), new EntityHugeExplodeFX$Factory());
      this.registerParticle(EnumParticleTypes.EXPLOSION_LARGE.getParticleID(), new EntityLargeExplodeFX$Factory());
      this.registerParticle(EnumParticleTypes.FIREWORKS_SPARK.getParticleID(), new EntityFirework$Factory());
      this.registerParticle(EnumParticleTypes.MOB_APPEARANCE.getParticleID(), new MobAppearance$Factory());
   }

   public void registerParticle(int var1, IParticleFactory var2) {
      this.f.put(Integer.valueOf(var1), var2);
   }

   public void emitParticleAtEntity(Entity var1, EnumParticleTypes var2) {
      this.c.add(new EntityParticleEmitter(this.worldObj, var1, var2));
   }

   public EntityFX a(int var1, double var2, double var4, double var6, double var8, double var10, double var12, int... var14) {
      IParticleFactory var15 = (IParticleFactory)this.f.get(Integer.valueOf(var1));
      EntityFX var16 = TN.a(var15, var1, this.worldObj, var2, var4, var6, var8, var10, var12, var14);
      this.addEffect(var16);
      return var16;
   }

   public void addEffect(EntityFX var1) {
      if(!(var1 instanceof EntityFirework$SparkFX) || Config.isFireworkParticles()) {
         int var2 = var1.getFXLayer();
         int var3 = var1.getAlpha() != 1.0F?0:1;
         if(this.fxLayers[var2][var3].size() >= 4000) {
            this.fxLayers[var2][var3].remove(0);
         }

         if(!(var1 instanceof Barrier) || !this.reuseBarrierParticle(var1, this.fxLayers[var2][var3])) {
            this.fxLayers[var2][var3].add(var1);
         }
      }

   }

   public void updateEffects() {
      for(int var1 = 0; var1 < 4; ++var1) {
         this.updateEffectLayer(var1);
      }

      CopyOnWriteArrayList var4 = new CopyOnWriteArrayList();

      for(EntityParticleEmitter var3 : this.c) {
         var3.onUpdate();
         if(var3.isDead) {
            var4.add(var3);
         }
      }

      this.c.removeAll(var4);
   }

   private void updateEffectLayer(int var1) {
      for(int var2 = 0; var2 < 2; ++var2) {
         this.updateEffectAlphaLayer(this.fxLayers[var1][var2]);
      }

   }

   private void updateEffectAlphaLayer(List var1) {
      CopyOnWriteArrayList var2 = new CopyOnWriteArrayList();

      for(int var3 = 0; var3 < var1.size(); ++var3) {
         EntityFX var4 = (EntityFX)var1.get(var3);
         this.tickParticle(var4);
         if(var4.isDead) {
            var2.add(var4);
         }
      }

      var1.removeAll(var2);
   }

   private void tickParticle(EntityFX var1) {
      EntityFX var10000 = var1;

      try {
         var10000.onUpdate();
      } catch (Throwable var6) {
         CrashReport var3 = CrashReport.makeCrashReport(var6, "Ticking Particle");
         CrashReportCategory var4 = var3.makeCategory("Particle being ticked");
         int var5 = var1.getFXLayer();
         var4.addCrashSectionCallable("Particle", var1::toString);
         var4.addCrashSectionCallable("Particle Type", EffectRenderer::lambda$tickParticle$0);
         throw new ReportedException(var3);
      }
   }

   public void renderParticles(Entity var1, float var2) {
      float var3 = ActiveRenderInfo.getRotationX();
      float var4 = ActiveRenderInfo.getRotationZ();
      float var5 = ActiveRenderInfo.getRotationYZ();
      float var6 = ActiveRenderInfo.getRotationXY();
      float var7 = ActiveRenderInfo.getRotationXZ();
      EntityFX.interpPosX = var1.lastTickPosX + (var1.posX - var1.lastTickPosX) * (double)var2;
      EntityFX.interpPosY = var1.lastTickPosY + (var1.posY - var1.lastTickPosY) * (double)var2;
      EntityFX.interpPosZ = var1.lastTickPosZ + (var1.posZ - var1.lastTickPosZ) * (double)var2;
      GlStateManager.enableBlend();
      GlStateManager.blendFunc(770, 771);
      GlStateManager.alphaFunc(516, 0.003921569F);

      for(int var8 = 0; var8 < 3; ++var8) {
         int var9 = var8;

         for(int var10 = 0; var10 < 2; ++var10) {
            if(!this.fxLayers[var9][var10].isEmpty()) {
               switch(var10) {
               case 0:
                  GlStateManager.depthMask(false);
                  break;
               case 1:
                  GlStateManager.depthMask(true);
               }

               switch(var9) {
               case 0:
               default:
                  this.renderer.bindTexture(particleTextures);
                  break;
               case 1:
                  this.renderer.bindTexture(TextureMap.locationBlocksTexture);
               }

               GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
               Tessellator var11 = Tessellator.getInstance();
               WorldRenderer var12 = var11.getWorldRenderer();
               var12.begin(7, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);

               for(int var13 = 0; var13 < this.fxLayers[var9][var10].size(); ++var13) {
                  EntityFX var14 = (EntityFX)this.fxLayers[var9][var10].get(var13);
                  EntityFX var10000 = var14;
                  WorldRenderer var10001 = var12;
                  Entity var10002 = var1;
                  float var10003 = var2;
                  float var10004 = var3;
                  float var10005 = var7;
                  float var10006 = var4;
                  float var10007 = var5;
                  float var10008 = var6;

                  try {
                     var10000.renderParticle(var10001, var10002, var10003, var10004, var10005, var10006, var10007, var10008);
                  } catch (Throwable var18) {
                     CrashReport var16 = CrashReport.makeCrashReport(var18, "Rendering Particle");
                     CrashReportCategory var17 = var16.makeCategory("Particle being rendered");
                     var17.addCrashSectionCallable("Particle", var14::toString);
                     var17.addCrashSectionCallable("Particle Type", EffectRenderer::lambda$renderParticles$1);
                     throw new ReportedException(var16);
                  }
               }

               var11.draw();
            }
         }
      }

      GlStateManager.depthMask(true);
      GlStateManager.disableBlend();
      GlStateManager.alphaFunc(516, 0.1F);
   }

   public void renderLitParticles(Entity var1, float var2) {
      float var3 = 0.017453292F;
      float var4 = MathHelper.cos(var1.rotationYaw * 0.017453292F);
      float var5 = MathHelper.sin(var1.rotationYaw * 0.017453292F);
      float var6 = -var5 * MathHelper.sin(var1.rotationPitch * 0.017453292F);
      float var7 = var4 * MathHelper.sin(var1.rotationPitch * 0.017453292F);
      float var8 = MathHelper.cos(var1.rotationPitch * 0.017453292F);

      for(int var9 = 0; var9 < 2; ++var9) {
         List var10 = this.fxLayers[3][var9];
         if(!var10.isEmpty()) {
            Tessellator var11 = Tessellator.getInstance();
            WorldRenderer var12 = var11.getWorldRenderer();

            for(int var13 = 0; var13 < var10.size(); ++var13) {
               EntityFX var14 = (EntityFX)var10.get(var13);
               var14.renderParticle(var12, var1, var2, var4, var8, var5, var6, var7);
            }
         }
      }

   }

   public void clearEffects(World var1) {
      this.worldObj = var1;

      for(int var2 = 0; var2 < 4; ++var2) {
         for(int var3 = 0; var3 < 2; ++var3) {
            this.fxLayers[var2][var3].clear();
         }
      }

      this.c.clear();
   }

   public void addBlockDestroyEffects(BlockPos var1, IBlockState var2) {
      if(Reflector.at.d() && Reflector.bw.d()) {
         Block var4 = var2.getBlock();
         Reflector.d(var4, Reflector.bw, new Object[]{this.worldObj, var1});
         if(!Reflector.d(var4, Reflector.bw, new Object[]{this.worldObj, var1}) && !Reflector.d(var4, Reflector.at, new Object[]{this.worldObj, var1, this})) {
            boolean var16 = true;
         } else {
            boolean var10000 = false;
         }
      } else {
         boolean var3 = var2.getBlock().getMaterial() != Material.air;
      }

      var2 = var2.getBlock().getActualState(var2, this.worldObj, var1);
      byte var15 = 4;

      for(int var5 = 0; var5 < var15; ++var5) {
         for(int var6 = 0; var6 < var15; ++var6) {
            for(int var7 = 0; var7 < var15; ++var7) {
               double var8 = (double)var1.getX() + ((double)var5 + 0.5D) / (double)var15;
               double var10 = (double)var1.getY() + ((double)var6 + 0.5D) / (double)var15;
               double var12 = (double)var1.getZ() + ((double)var7 + 0.5D) / (double)var15;
               this.addEffect((new EntityDiggingFX(this.worldObj, var8, var10, var12, var8 - (double)var1.getX() - 0.5D, var10 - (double)var1.getY() - 0.5D, var12 - (double)var1.getZ() - 0.5D, var2)).func_174846_a(var1));
            }
         }
      }

   }

   public void addBlockHitEffects(BlockPos var1, EnumFacing var2) {
      IBlockState var3 = this.worldObj.getBlockState(var1);
      Block var4 = var3.getBlock();
      if(var4.getRenderType() != -1) {
         int var5 = var1.getX();
         int var6 = var1.getY();
         int var7 = var1.getZ();
         float var8 = 0.1F;
         double var9 = (double)var5 + this.rand.nextDouble() * (var4.getBlockBoundsMaxX() - var4.getBlockBoundsMinX() - (double)(var8 * 2.0F)) + (double)var8 + var4.getBlockBoundsMinX();
         double var11 = (double)var6 + this.rand.nextDouble() * (var4.getBlockBoundsMaxY() - var4.getBlockBoundsMinY() - (double)(var8 * 2.0F)) + (double)var8 + var4.getBlockBoundsMinY();
         double var13 = (double)var7 + this.rand.nextDouble() * (var4.getBlockBoundsMaxZ() - var4.getBlockBoundsMinZ() - (double)(var8 * 2.0F)) + (double)var8 + var4.getBlockBoundsMinZ();
         if(var2 == EnumFacing.DOWN) {
            var11 = (double)var6 + var4.getBlockBoundsMinY() - (double)var8;
         }

         if(var2 == EnumFacing.UP) {
            var11 = (double)var6 + var4.getBlockBoundsMaxY() + (double)var8;
         }

         if(var2 == EnumFacing.NORTH) {
            var13 = (double)var7 + var4.getBlockBoundsMinZ() - (double)var8;
         }

         if(var2 == EnumFacing.SOUTH) {
            var13 = (double)var7 + var4.getBlockBoundsMaxZ() + (double)var8;
         }

         if(var2 == EnumFacing.WEST) {
            var9 = (double)var5 + var4.getBlockBoundsMinX() - (double)var8;
         }

         if(var2 == EnumFacing.EAST) {
            var9 = (double)var5 + var4.getBlockBoundsMaxX() + (double)var8;
         }

         this.addEffect((new EntityDiggingFX(this.worldObj, var9, var11, var13, 0.0D, 0.0D, 0.0D, var3)).func_174846_a(var1).multiplyVelocity(0.2F).multipleParticleScaleBy(0.6F));
      }

   }

   public void moveToAlphaLayer(EntityFX var1) {
      this.moveToLayer(var1, 1, 0);
   }

   public void moveToNoAlphaLayer(EntityFX var1) {
      this.moveToLayer(var1, 0, 1);
   }

   private void moveToLayer(EntityFX var1, int var2, int var3) {
      for(int var4 = 0; var4 < 4; ++var4) {
         if(this.fxLayers[var4][var2].contains(var1)) {
            this.fxLayers[var4][var2].remove(var1);
            this.fxLayers[var4][var3].add(var1);
         }
      }

   }

   public String getStatistics() {
      int var1 = 0;

      for(int var2 = 0; var2 < 4; ++var2) {
         for(int var3 = 0; var3 < 2; ++var3) {
            var1 += this.fxLayers[var2][var3].size();
         }
      }

      return "" + var1;
   }

   private boolean reuseBarrierParticle(EntityFX var1, List var2) {
      for(EntityFX var4 : var2) {
         if(var4 instanceof Barrier && var1.posX == var4.posX && var1.posY == var4.posY && var1.posZ == var4.posZ) {
            var4.particleAge = 0;
            return true;
         }
      }

      return false;
   }

   public void addBlockHitEffects(BlockPos var1, MovingObjectPosition var2) {
      Block var3 = this.worldObj.getBlockState(var1).getBlock();
      boolean var4 = Reflector.d(var3, Reflector.bJ, new Object[]{this.worldObj, var2, this});
      this.addBlockHitEffects(var1, var2.facing);
   }

   private static String lambda$renderParticles$1(int var0) throws Exception {
      return "MISC_TEXTURE";
   }

   private static String lambda$tickParticle$0(int var0) throws Exception {
      return "MISC_TEXTURE";
   }

   private static Throwable a(Throwable var0) {
      return var0;
   }
}
