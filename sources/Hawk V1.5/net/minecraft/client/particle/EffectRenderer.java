package net.minecraft.client.particle;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.Callable;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
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
import optifine.Config;
import optifine.Reflector;

public class EffectRenderer {
   private List[][] fxLayers = new List[4][];
   private TextureManager renderer;
   private Map field_178932_g = Maps.newHashMap();
   protected World worldObj;
   private static final String __OBFID = "CL_00000915";
   private static final ResourceLocation particleTextures = new ResourceLocation("textures/particle/particles.png");
   private List field_178933_d = Lists.newArrayList();
   private Random rand = new Random();

   public EffectRenderer(World var1, TextureManager var2) {
      this.worldObj = var1;
      this.renderer = var2;

      for(int var3 = 0; var3 < 4; ++var3) {
         this.fxLayers[var3] = new List[2];

         for(int var4 = 0; var4 < 2; ++var4) {
            this.fxLayers[var3][var4] = Lists.newArrayList();
         }
      }

      this.func_178930_c();
   }

   private void func_178924_a(EntityFX var1, int var2, int var3) {
      for(int var4 = 0; var4 < 4; ++var4) {
         if (this.fxLayers[var4][var2].contains(var1)) {
            this.fxLayers[var4][var2].remove(var1);
            this.fxLayers[var4][var3].add(var1);
         }
      }

   }

   private void func_178925_a(List var1) {
      ArrayList var2 = Lists.newArrayList();

      for(int var3 = 0; var3 < var1.size(); ++var3) {
         EntityFX var4 = (EntityFX)var1.get(var3);
         this.func_178923_d(var4);
         if (var4.isDead) {
            var2.add(var4);
         }
      }

      var1.removeAll(var2);
   }

   public EntityFX func_178927_a(int var1, double var2, double var4, double var6, double var8, double var10, double var12, int... var14) {
      IParticleFactory var15 = (IParticleFactory)this.field_178932_g.get(var1);
      if (var15 != null) {
         EntityFX var16 = var15.func_178902_a(var1, this.worldObj, var2, var4, var6, var8, var10, var12, var14);
         if (var16 != null) {
            this.addEffect(var16);
            return var16;
         }
      }

      return null;
   }

   public String getStatistics() {
      int var1 = 0;

      for(int var2 = 0; var2 < 4; ++var2) {
         for(int var3 = 0; var3 < 2; ++var3) {
            var1 += this.fxLayers[var2][var3].size();
         }
      }

      return String.valueOf((new StringBuilder()).append(var1));
   }

   private boolean reuseBarrierParticle(EntityFX var1, List<EntityFX> var2) {
      Iterator var3 = var2.iterator();

      EntityFX var4;
      do {
         if (!var3.hasNext()) {
            return false;
         }

         var4 = (EntityFX)var3.next();
      } while(!(var4 instanceof Barrier) || var1.posX != var4.posX || var1.posY != var4.posY || var1.posZ != var4.posZ);

      var4.particleAge = 0;
      return true;
   }

   private void func_178930_c() {
      this.func_178929_a(EnumParticleTypes.EXPLOSION_NORMAL.func_179348_c(), new EntityExplodeFX.Factory());
      this.func_178929_a(EnumParticleTypes.WATER_BUBBLE.func_179348_c(), new EntityBubbleFX.Factory());
      this.func_178929_a(EnumParticleTypes.WATER_SPLASH.func_179348_c(), new EntitySplashFX.Factory());
      this.func_178929_a(EnumParticleTypes.WATER_WAKE.func_179348_c(), new EntityFishWakeFX.Factory());
      this.func_178929_a(EnumParticleTypes.WATER_DROP.func_179348_c(), new EntityRainFX.Factory());
      this.func_178929_a(EnumParticleTypes.SUSPENDED.func_179348_c(), new EntitySuspendFX.Factory());
      this.func_178929_a(EnumParticleTypes.SUSPENDED_DEPTH.func_179348_c(), new EntityAuraFX.Factory());
      this.func_178929_a(EnumParticleTypes.CRIT.func_179348_c(), new EntityCrit2FX.Factory());
      this.func_178929_a(EnumParticleTypes.CRIT_MAGIC.func_179348_c(), new EntityCrit2FX.MagicFactory());
      this.func_178929_a(EnumParticleTypes.SMOKE_NORMAL.func_179348_c(), new EntitySmokeFX.Factory());
      this.func_178929_a(EnumParticleTypes.SMOKE_LARGE.func_179348_c(), new EntityCritFX.Factory());
      this.func_178929_a(EnumParticleTypes.SPELL.func_179348_c(), new EntitySpellParticleFX.Factory());
      this.func_178929_a(EnumParticleTypes.SPELL_INSTANT.func_179348_c(), new EntitySpellParticleFX.InstantFactory());
      this.func_178929_a(EnumParticleTypes.SPELL_MOB.func_179348_c(), new EntitySpellParticleFX.MobFactory());
      this.func_178929_a(EnumParticleTypes.SPELL_MOB_AMBIENT.func_179348_c(), new EntitySpellParticleFX.AmbientMobFactory());
      this.func_178929_a(EnumParticleTypes.SPELL_WITCH.func_179348_c(), new EntitySpellParticleFX.WitchFactory());
      this.func_178929_a(EnumParticleTypes.DRIP_WATER.func_179348_c(), new EntityDropParticleFX.WaterFactory());
      this.func_178929_a(EnumParticleTypes.DRIP_LAVA.func_179348_c(), new EntityDropParticleFX.LavaFactory());
      this.func_178929_a(EnumParticleTypes.VILLAGER_ANGRY.func_179348_c(), new EntityHeartFX.AngryVillagerFactory());
      this.func_178929_a(EnumParticleTypes.VILLAGER_HAPPY.func_179348_c(), new EntityAuraFX.HappyVillagerFactory());
      this.func_178929_a(EnumParticleTypes.TOWN_AURA.func_179348_c(), new EntityAuraFX.Factory());
      this.func_178929_a(EnumParticleTypes.NOTE.func_179348_c(), new EntityNoteFX.Factory());
      this.func_178929_a(EnumParticleTypes.PORTAL.func_179348_c(), new EntityPortalFX.Factory());
      this.func_178929_a(EnumParticleTypes.ENCHANTMENT_TABLE.func_179348_c(), new EntityEnchantmentTableParticleFX.EnchantmentTable());
      this.func_178929_a(EnumParticleTypes.FLAME.func_179348_c(), new EntityFlameFX.Factory());
      this.func_178929_a(EnumParticleTypes.LAVA.func_179348_c(), new EntityLavaFX.Factory());
      this.func_178929_a(EnumParticleTypes.FOOTSTEP.func_179348_c(), new EntityFootStepFX.Factory());
      this.func_178929_a(EnumParticleTypes.CLOUD.func_179348_c(), new EntityCloudFX.Factory());
      this.func_178929_a(EnumParticleTypes.REDSTONE.func_179348_c(), new EntityReddustFX.Factory());
      this.func_178929_a(EnumParticleTypes.SNOWBALL.func_179348_c(), new EntityBreakingFX.SnowballFactory());
      this.func_178929_a(EnumParticleTypes.SNOW_SHOVEL.func_179348_c(), new EntitySnowShovelFX.Factory());
      this.func_178929_a(EnumParticleTypes.SLIME.func_179348_c(), new EntityBreakingFX.SlimeFactory());
      this.func_178929_a(EnumParticleTypes.HEART.func_179348_c(), new EntityHeartFX.Factory());
      this.func_178929_a(EnumParticleTypes.BARRIER.func_179348_c(), new Barrier.Factory());
      this.func_178929_a(EnumParticleTypes.ITEM_CRACK.func_179348_c(), new EntityBreakingFX.Factory());
      this.func_178929_a(EnumParticleTypes.BLOCK_CRACK.func_179348_c(), new EntityDiggingFX.Factory());
      this.func_178929_a(EnumParticleTypes.BLOCK_DUST.func_179348_c(), new EntityBlockDustFX.Factory());
      this.func_178929_a(EnumParticleTypes.EXPLOSION_HUGE.func_179348_c(), new EntityHugeExplodeFX.Factory());
      this.func_178929_a(EnumParticleTypes.EXPLOSION_LARGE.func_179348_c(), new EntityLargeExplodeFX.Factory());
      this.func_178929_a(EnumParticleTypes.FIREWORKS_SPARK.func_179348_c(), new EntityFireworkStarterFX_Factory());
      this.func_178929_a(EnumParticleTypes.MOB_APPEARANCE.func_179348_c(), new MobAppearance.Factory());
   }

   public void addBlockHitEffects(BlockPos var1, MovingObjectPosition var2) {
      Block var3 = this.worldObj.getBlockState(var1).getBlock();
      boolean var4 = Reflector.callBoolean(var3, Reflector.ForgeBlock_addHitEffects, this.worldObj, var2, this);
      if (var3 != null && !var4) {
         this.func_180532_a(var1, var2.field_178784_b);
      }

   }

   private void func_178922_a(int var1) {
      for(int var2 = 0; var2 < 2; ++var2) {
         this.func_178925_a(this.fxLayers[var1][var2]);
      }

   }

   public void clearEffects(World var1) {
      this.worldObj = var1;

      for(int var2 = 0; var2 < 4; ++var2) {
         for(int var3 = 0; var3 < 2; ++var3) {
            this.fxLayers[var2][var3].clear();
         }
      }

      this.field_178933_d.clear();
   }

   public void func_178929_a(int var1, IParticleFactory var2) {
      this.field_178932_g.put(var1, var2);
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
         if (!var10.isEmpty()) {
            Tessellator var11 = Tessellator.getInstance();
            WorldRenderer var12 = var11.getWorldRenderer();

            for(int var13 = 0; var13 < var10.size(); ++var13) {
               EntityFX var14 = (EntityFX)var10.get(var13);
               var12.func_178963_b(var14.getBrightnessForRender(var2));
               var14.func_180434_a(var12, var1, var2, var4, var8, var5, var6, var7);
            }
         }
      }

   }

   public void func_180533_a(BlockPos var1, IBlockState var2) {
      boolean var3;
      if (Reflector.ForgeBlock_addDestroyEffects.exists() && Reflector.ForgeBlock_isAir.exists()) {
         Block var4 = var2.getBlock();
         Reflector.callBoolean(var4, Reflector.ForgeBlock_isAir, this.worldObj, var1);
         var3 = !Reflector.callBoolean(var4, Reflector.ForgeBlock_isAir, this.worldObj, var1) && !Reflector.callBoolean(var4, Reflector.ForgeBlock_addDestroyEffects, this.worldObj, var1, this);
      } else {
         var3 = var2.getBlock().getMaterial() != Material.air;
      }

      if (var3) {
         var2 = var2.getBlock().getActualState(var2, this.worldObj, var1);
         byte var14 = 4;

         for(int var5 = 0; var5 < var14; ++var5) {
            for(int var6 = 0; var6 < var14; ++var6) {
               for(int var7 = 0; var7 < var14; ++var7) {
                  double var8 = (double)var1.getX() + ((double)var5 + 0.5D) / (double)var14;
                  double var10 = (double)var1.getY() + ((double)var6 + 0.5D) / (double)var14;
                  double var12 = (double)var1.getZ() + ((double)var7 + 0.5D) / (double)var14;
                  this.addEffect((new EntityDiggingFX(this.worldObj, var8, var10, var12, var8 - (double)var1.getX() - 0.5D, var10 - (double)var1.getY() - 0.5D, var12 - (double)var1.getZ() - 0.5D, var2)).func_174846_a(var1));
               }
            }
         }
      }

   }

   public void func_178926_a(Entity var1, EnumParticleTypes var2) {
      this.field_178933_d.add(new EntityParticleEmitter(this.worldObj, var1, var2));
   }

   public void func_178928_b(EntityFX var1) {
      this.func_178924_a(var1, 1, 0);
   }

   public void func_178931_c(EntityFX var1) {
      this.func_178924_a(var1, 0, 1);
   }

   private void func_178923_d(EntityFX var1) {
      try {
         var1.onUpdate();
      } catch (Throwable var6) {
         CrashReport var3 = CrashReport.makeCrashReport(var6, "Ticking Particle");
         CrashReportCategory var4 = var3.makeCategory("Particle being ticked");
         int var5 = var1.getFXLayer();
         var4.addCrashSectionCallable("Particle", new Callable(this, var1) {
            private final EntityFX val$p_178923_1_;
            final EffectRenderer this$0;
            private static final String __OBFID = "CL_00000916";

            public Object call() throws Exception {
               return this.call();
            }

            {
               this.this$0 = var1;
               this.val$p_178923_1_ = var2;
            }

            public String call() {
               return this.val$p_178923_1_.toString();
            }
         });
         var4.addCrashSectionCallable("Particle Type", new Callable(this, var5) {
            final EffectRenderer this$0;
            private final int val$var5;
            private static final String __OBFID = "CL_00000917";

            public Object call() throws Exception {
               return this.call();
            }

            public String call() {
               return this.val$var5 == 0 ? "MISC_TEXTURE" : (this.val$var5 == 1 ? "TERRAIN_TEXTURE" : (this.val$var5 == 3 ? "ENTITY_PARTICLE_TEXTURE" : String.valueOf((new StringBuilder("Unknown - ")).append(this.val$var5))));
            }

            {
               this.this$0 = var1;
               this.val$var5 = var2;
            }
         });
         throw new ReportedException(var3);
      }
   }

   public void updateEffects() {
      for(int var1 = 0; var1 < 4; ++var1) {
         this.func_178922_a(var1);
      }

      ArrayList var4 = Lists.newArrayList();
      Iterator var2 = this.field_178933_d.iterator();

      while(var2.hasNext()) {
         EntityParticleEmitter var3 = (EntityParticleEmitter)var2.next();
         var3.onUpdate();
         if (var3.isDead) {
            var4.add(var3);
         }
      }

      this.field_178933_d.removeAll(var4);
   }

   public void func_180532_a(BlockPos var1, EnumFacing var2) {
      IBlockState var3 = this.worldObj.getBlockState(var1);
      Block var4 = var3.getBlock();
      if (var4.getRenderType() != -1) {
         int var5 = var1.getX();
         int var6 = var1.getY();
         int var7 = var1.getZ();
         float var8 = 0.1F;
         double var9 = (double)var5 + this.rand.nextDouble() * (var4.getBlockBoundsMaxX() - var4.getBlockBoundsMinX() - (double)(var8 * 2.0F)) + (double)var8 + var4.getBlockBoundsMinX();
         double var11 = (double)var6 + this.rand.nextDouble() * (var4.getBlockBoundsMaxY() - var4.getBlockBoundsMinY() - (double)(var8 * 2.0F)) + (double)var8 + var4.getBlockBoundsMinY();
         double var13 = (double)var7 + this.rand.nextDouble() * (var4.getBlockBoundsMaxZ() - var4.getBlockBoundsMinZ() - (double)(var8 * 2.0F)) + (double)var8 + var4.getBlockBoundsMinZ();
         if (var2 == EnumFacing.DOWN) {
            var11 = (double)var6 + var4.getBlockBoundsMinY() - (double)var8;
         }

         if (var2 == EnumFacing.UP) {
            var11 = (double)var6 + var4.getBlockBoundsMaxY() + (double)var8;
         }

         if (var2 == EnumFacing.NORTH) {
            var13 = (double)var7 + var4.getBlockBoundsMinZ() - (double)var8;
         }

         if (var2 == EnumFacing.SOUTH) {
            var13 = (double)var7 + var4.getBlockBoundsMaxZ() + (double)var8;
         }

         if (var2 == EnumFacing.WEST) {
            var9 = (double)var5 + var4.getBlockBoundsMinX() - (double)var8;
         }

         if (var2 == EnumFacing.EAST) {
            var9 = (double)var5 + var4.getBlockBoundsMaxX() + (double)var8;
         }

         this.addEffect((new EntityDiggingFX(this.worldObj, var9, var11, var13, 0.0D, 0.0D, 0.0D, var3)).func_174846_a(var1).multiplyVelocity(0.2F).multipleParticleScaleBy(0.6F));
      }

   }

   public void addEffect(EntityFX var1) {
      if (var1 != null && (!(var1 instanceof EntityFireworkSparkFX) || Config.isFireworkParticles())) {
         int var2 = var1.getFXLayer();
         int var3 = var1.func_174838_j() != 1.0F ? 0 : 1;
         if (this.fxLayers[var2][var3].size() >= 4000) {
            this.fxLayers[var2][var3].remove(0);
         }

         if (!(var1 instanceof Barrier) || !this.reuseBarrierParticle(var1, this.fxLayers[var2][var3])) {
            this.fxLayers[var2][var3].add(var1);
         }
      }

   }

   public void renderParticles(Entity var1, float var2) {
      float var3 = ActiveRenderInfo.func_178808_b();
      float var4 = ActiveRenderInfo.func_178803_d();
      float var5 = ActiveRenderInfo.func_178805_e();
      float var6 = ActiveRenderInfo.func_178807_f();
      float var7 = ActiveRenderInfo.func_178809_c();
      EntityFX.interpPosX = var1.lastTickPosX + (var1.posX - var1.lastTickPosX) * (double)var2;
      EntityFX.interpPosY = var1.lastTickPosY + (var1.posY - var1.lastTickPosY) * (double)var2;
      EntityFX.interpPosZ = var1.lastTickPosZ + (var1.posZ - var1.lastTickPosZ) * (double)var2;
      GlStateManager.enableBlend();
      GlStateManager.blendFunc(770, 771);
      GlStateManager.alphaFunc(516, 0.003921569F);

      for(int var8 = 0; var8 < 3; ++var8) {
         int var9 = var8;

         for(int var10 = 0; var10 < 2; ++var10) {
            if (!this.fxLayers[var9][var10].isEmpty()) {
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
               var12.startDrawingQuads();

               for(int var13 = 0; var13 < this.fxLayers[var9][var10].size(); ++var13) {
                  EntityFX var14 = (EntityFX)this.fxLayers[var9][var10].get(var13);
                  var12.func_178963_b(var14.getBrightnessForRender(var2));

                  try {
                     var14.func_180434_a(var12, var1, var2, var3, var7, var4, var5, var6);
                  } catch (Throwable var18) {
                     CrashReport var16 = CrashReport.makeCrashReport(var18, "Rendering Particle");
                     CrashReportCategory var17 = var16.makeCategory("Particle being rendered");
                     var17.addCrashSectionCallable("Particle", new Callable(this, var14) {
                        final EffectRenderer this$0;
                        private final EntityFX val$var13;
                        private static final String __OBFID = "CL_00000918";

                        public Object call() throws Exception {
                           return this.call();
                        }

                        {
                           this.this$0 = var1;
                           this.val$var13 = var2;
                        }

                        public String call() {
                           return this.val$var13.toString();
                        }
                     });
                     var17.addCrashSectionCallable("Particle Type", new Callable(this, var9) {
                        private final int val$var8;
                        final EffectRenderer this$0;
                        private static final String __OBFID = "CL_00000919";

                        {
                           this.this$0 = var1;
                           this.val$var8 = var2;
                        }

                        public Object call() throws Exception {
                           return this.call();
                        }

                        public String call() {
                           return this.val$var8 == 0 ? "MISC_TEXTURE" : (this.val$var8 == 1 ? "TERRAIN_TEXTURE" : (this.val$var8 == 3 ? "ENTITY_PARTICLE_TEXTURE" : String.valueOf((new StringBuilder("Unknown - ")).append(this.val$var8))));
                        }
                     });
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
}
