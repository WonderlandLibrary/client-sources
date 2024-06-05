package net.minecraft.world;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentProtection;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class Explosion {
   private final boolean isFlaming;
   private final boolean isSmoking;
   private final Random explosionRNG;
   private final World worldObj;
   private final double explosionX;
   private final double explosionY;
   private final double explosionZ;
   private final Entity exploder;
   private final float explosionSize;
   private final List affectedBlockPositions;
   private final Map playerKnockbackMap;

   public Explosion(World var1, Entity var2, double var3, double var5, double var7, float var9, List var10) {
      this(var1, var2, var3, var5, var7, var9, false, true, var10);
   }

   public Explosion(World var1, Entity var2, double var3, double var5, double var7, float var9, boolean var10, boolean var11, List var12) {
      this(var1, var2, var3, var5, var7, var9, var10, var11);
      this.affectedBlockPositions.addAll(var12);
   }

   public Explosion(World var1, Entity var2, double var3, double var5, double var7, float var9, boolean var10, boolean var11) {
      this.explosionRNG = new Random();
      this.affectedBlockPositions = Lists.newArrayList();
      this.playerKnockbackMap = Maps.newHashMap();
      this.worldObj = var1;
      this.exploder = var2;
      this.explosionSize = var9;
      this.explosionX = var3;
      this.explosionY = var5;
      this.explosionZ = var7;
      this.isFlaming = var10;
      this.isSmoking = var11;
   }

   public void doExplosionA() {
      HashSet var1 = Sets.newHashSet();
      boolean var2 = true;

      for(int var3 = 0; var3 < 16; ++var3) {
         for(int var4 = 0; var4 < 16; ++var4) {
            for(int var5 = 0; var5 < 16; ++var5) {
               if(var3 == 15 || var4 == 15 || var5 == 15) {
                  double var6 = (double)((float)var3 / 15.0F * 2.0F - 1.0F);
                  double var8 = (double)((float)var4 / 15.0F * 2.0F - 1.0F);
                  double var10 = (double)((float)var5 / 15.0F * 2.0F - 1.0F);
                  double var12 = Math.sqrt(var6 * var6 + var8 * var8 + var10 * var10);
                  var6 = var6 / var12;
                  var8 = var8 / var12;
                  var10 = var10 / var12;
                  float var14 = this.explosionSize * (0.7F + this.worldObj.rand.nextFloat() * 0.6F);
                  double var15 = this.explosionX;
                  double var17 = this.explosionY;
                  double var19 = this.explosionZ;

                  for(float var21 = 0.3F; var14 > 0.0F; var14 -= 0.22500001F) {
                     BlockPos var22 = new BlockPos(var15, var17, var19);
                     IBlockState var23 = this.worldObj.getBlockState(var22);
                     if(var23.getBlock().getMaterial() != Material.air) {
                        float var24 = this.exploder != null?this.exploder.getExplosionResistance(this, this.worldObj, var22, var23):var23.getBlock().getExplosionResistance((Entity)null);
                        var14 -= (var24 + 0.3F) * 0.3F;
                     }

                     if(var14 > 0.0F && (this.exploder == null || this.exploder.verifyExplosion(this, this.worldObj, var22, var23, var14))) {
                        var1.add(var22);
                     }

                     var15 += var6 * 0.30000001192092896D;
                     var17 += var8 * 0.30000001192092896D;
                     var19 += var10 * 0.30000001192092896D;
                  }
               }
            }
         }
      }

      this.affectedBlockPositions.addAll(var1);
      float var31 = this.explosionSize * 2.0F;
      int var32 = MathHelper.floor_double(this.explosionX - (double)var31 - 1.0D);
      int var33 = MathHelper.floor_double(this.explosionX + (double)var31 + 1.0D);
      int var35 = MathHelper.floor_double(this.explosionY - (double)var31 - 1.0D);
      int var7 = MathHelper.floor_double(this.explosionY + (double)var31 + 1.0D);
      int var37 = MathHelper.floor_double(this.explosionZ - (double)var31 - 1.0D);
      int var9 = MathHelper.floor_double(this.explosionZ + (double)var31 + 1.0D);
      List var39 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this.exploder, new AxisAlignedBB((double)var32, (double)var35, (double)var37, (double)var33, (double)var7, (double)var9));
      Vec3 var11 = new Vec3(this.explosionX, this.explosionY, this.explosionZ);

      for(Entity var13 : var39) {
         if(!var13.isImmuneToExplosions()) {
            double var41 = var13.getDistance(this.explosionX, this.explosionY, this.explosionZ) / (double)var31;
            if(var41 <= 1.0D) {
               double var42 = var13.posX - this.explosionX;
               double var44 = var13.posY + (double)var13.getEyeHeight() - this.explosionY;
               double var46 = var13.posZ - this.explosionZ;
               double var48 = (double)MathHelper.sqrt_double(var42 * var42 + var44 * var44 + var46 * var46);
               if(var48 != 0.0D) {
                  var42 = var42 / var48;
                  var44 = var44 / var48;
                  var46 = var46 / var48;
                  double var25 = (double)this.worldObj.getBlockDensity(var11, var13.getEntityBoundingBox());
                  double var27 = (1.0D - var41) * var25;
                  var13.attackEntityFrom(DamageSource.setExplosionSource(this), (float)((int)((var27 * var27 + var27) / 2.0D * 8.0D * (double)var31 + 1.0D)));
                  double var29 = EnchantmentProtection.func_92092_a(var13, var27);
                  var13.motionX += var42 * var29;
                  var13.motionY += var44 * var29;
                  var13.motionZ += var46 * var29;
                  if(var13 instanceof EntityPlayer && !((EntityPlayer)var13).abilities.isDisabledDamage()) {
                     this.playerKnockbackMap.put((EntityPlayer)var13, new Vec3(var42 * var27, var44 * var27, var46 * var27));
                  }
               }
            }
         }
      }

   }

   public void doExplosionB(boolean var1) {
      this.worldObj.playSoundEffect(this.explosionX, this.explosionY, this.explosionZ, "random.explode", 4.0F, (1.0F + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.2F) * 0.7F);
      if(this.explosionSize >= 2.0F && this.isSmoking) {
         this.worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_HUGE, this.explosionX, this.explosionY, this.explosionZ, 1.0D, 0.0D, 0.0D, new int[0]);
      } else {
         this.worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_LARGE, this.explosionX, this.explosionY, this.explosionZ, 1.0D, 0.0D, 0.0D, new int[0]);
      }

      if(this.isSmoking) {
         for(BlockPos var3 : this.affectedBlockPositions) {
            Block var4 = this.worldObj.getBlockState(var3).getBlock();
            double var5 = (double)((float)var3.getX() + this.worldObj.rand.nextFloat());
            double var7 = (double)((float)var3.getY() + this.worldObj.rand.nextFloat());
            double var9 = (double)((float)var3.getZ() + this.worldObj.rand.nextFloat());
            double var11 = var5 - this.explosionX;
            double var13 = var7 - this.explosionY;
            double var15 = var9 - this.explosionZ;
            double var17 = (double)MathHelper.sqrt_double(var11 * var11 + var13 * var13 + var15 * var15);
            var11 = var11 / var17;
            var13 = var13 / var17;
            var15 = var15 / var17;
            double var19 = 0.5D / (var17 / (double)this.explosionSize + 0.1D);
            var19 = var19 * (double)(this.worldObj.rand.nextFloat() * this.worldObj.rand.nextFloat() + 0.3F);
            var11 = var11 * var19;
            var13 = var13 * var19;
            var15 = var15 * var19;
            this.worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, (var5 + this.explosionX * 1.0D) / 2.0D, (var7 + this.explosionY * 1.0D) / 2.0D, (var9 + this.explosionZ * 1.0D) / 2.0D, var11, var13, var15, new int[0]);
            this.worldObj.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, var5, var7, var9, var11, var13, var15, new int[0]);
            if(var4.getMaterial() != Material.air) {
               if(var4.canDropFromExplosion(this)) {
                  var4.dropBlockAsItemWithChance(this.worldObj, var3, this.worldObj.getBlockState(var3), 1.0F / this.explosionSize, 0);
               }

               this.worldObj.setBlockState(var3, Blocks.air.getDefaultState(), 3);
               var4.onBlockDestroyedByExplosion(this.worldObj, var3, this);
            }
         }
      }

      if(this.isFlaming) {
         for(BlockPos var22 : this.affectedBlockPositions) {
            if(this.worldObj.getBlockState(var22).getBlock().getMaterial() == Material.air && this.worldObj.getBlockState(var22.down()).getBlock().isFullBlock() && this.explosionRNG.nextInt(3) == 0) {
               this.worldObj.setBlockState(var22, Blocks.fire.getDefaultState());
            }
         }
      }

   }

   public Map getPlayerKnockbackMap() {
      return this.playerKnockbackMap;
   }

   public EntityLivingBase getExplosivePlacedBy() {
      return this.exploder == null?null:(this.exploder instanceof EntityTNTPrimed?((EntityTNTPrimed)this.exploder).getTntPlacedBy():(this.exploder instanceof EntityLivingBase?(EntityLivingBase)this.exploder:null));
   }

   public void func_180342_d() {
      this.affectedBlockPositions.clear();
   }

   public List getAffectedBlockPositions() {
      return this.affectedBlockPositions;
   }
}
