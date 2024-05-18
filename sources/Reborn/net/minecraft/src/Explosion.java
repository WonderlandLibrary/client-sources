package net.minecraft.src;

import java.util.*;

public class Explosion
{
    public boolean isFlaming;
    public boolean isSmoking;
    private int field_77289_h;
    private Random explosionRNG;
    private World worldObj;
    public double explosionX;
    public double explosionY;
    public double explosionZ;
    public Entity exploder;
    public float explosionSize;
    public List affectedBlockPositions;
    private Map field_77288_k;
    
    public Explosion(final World par1World, final Entity par2Entity, final double par3, final double par5, final double par7, final float par9) {
        this.isFlaming = false;
        this.isSmoking = true;
        this.field_77289_h = 16;
        this.explosionRNG = new Random();
        this.affectedBlockPositions = new ArrayList();
        this.field_77288_k = new HashMap();
        this.worldObj = par1World;
        this.exploder = par2Entity;
        this.explosionSize = par9;
        this.explosionX = par3;
        this.explosionY = par5;
        this.explosionZ = par7;
    }
    
    public void doExplosionA() {
        final float var1 = this.explosionSize;
        final HashSet var2 = new HashSet();
        for (int var3 = 0; var3 < this.field_77289_h; ++var3) {
            for (int var4 = 0; var4 < this.field_77289_h; ++var4) {
                for (int var5 = 0; var5 < this.field_77289_h; ++var5) {
                    if (var3 == 0 || var3 == this.field_77289_h - 1 || var4 == 0 || var4 == this.field_77289_h - 1 || var5 == 0 || var5 == this.field_77289_h - 1) {
                        double var6 = var3 / (this.field_77289_h - 1.0f) * 2.0f - 1.0f;
                        double var7 = var4 / (this.field_77289_h - 1.0f) * 2.0f - 1.0f;
                        double var8 = var5 / (this.field_77289_h - 1.0f) * 2.0f - 1.0f;
                        final double var9 = Math.sqrt(var6 * var6 + var7 * var7 + var8 * var8);
                        var6 /= var9;
                        var7 /= var9;
                        var8 /= var9;
                        float var10 = this.explosionSize * (0.7f + this.worldObj.rand.nextFloat() * 0.6f);
                        double var11 = this.explosionX;
                        double var12 = this.explosionY;
                        double var13 = this.explosionZ;
                        for (float var14 = 0.3f; var10 > 0.0f; var10 -= var14 * 0.75f) {
                            final int var15 = MathHelper.floor_double(var11);
                            final int var16 = MathHelper.floor_double(var12);
                            final int var17 = MathHelper.floor_double(var13);
                            final int var18 = this.worldObj.getBlockId(var15, var16, var17);
                            if (var18 > 0) {
                                final Block var19 = Block.blocksList[var18];
                                final float var20 = (this.exploder != null) ? this.exploder.func_82146_a(this, this.worldObj, var15, var16, var17, var19) : var19.getExplosionResistance(this.exploder);
                                var10 -= (var20 + 0.3f) * var14;
                            }
                            if (var10 > 0.0f && (this.exploder == null || this.exploder.func_96091_a(this, this.worldObj, var15, var16, var17, var18, var10))) {
                                var2.add(new ChunkPosition(var15, var16, var17));
                            }
                            var11 += var6 * var14;
                            var12 += var7 * var14;
                            var13 += var8 * var14;
                        }
                    }
                }
            }
        }
        this.affectedBlockPositions.addAll(var2);
        this.explosionSize *= 2.0f;
        int var3 = MathHelper.floor_double(this.explosionX - this.explosionSize - 1.0);
        int var4 = MathHelper.floor_double(this.explosionX + this.explosionSize + 1.0);
        int var5 = MathHelper.floor_double(this.explosionY - this.explosionSize - 1.0);
        final int var21 = MathHelper.floor_double(this.explosionY + this.explosionSize + 1.0);
        final int var22 = MathHelper.floor_double(this.explosionZ - this.explosionSize - 1.0);
        final int var23 = MathHelper.floor_double(this.explosionZ + this.explosionSize + 1.0);
        final List var24 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this.exploder, AxisAlignedBB.getAABBPool().getAABB(var3, var5, var22, var4, var21, var23));
        final Vec3 var25 = this.worldObj.getWorldVec3Pool().getVecFromPool(this.explosionX, this.explosionY, this.explosionZ);
        for (int var26 = 0; var26 < var24.size(); ++var26) {
            final Entity var27 = var24.get(var26);
            final double var28 = var27.getDistance(this.explosionX, this.explosionY, this.explosionZ) / this.explosionSize;
            if (var28 <= 1.0) {
                double var11 = var27.posX - this.explosionX;
                double var12 = var27.posY + var27.getEyeHeight() - this.explosionY;
                double var13 = var27.posZ - this.explosionZ;
                final double var29 = MathHelper.sqrt_double(var11 * var11 + var12 * var12 + var13 * var13);
                if (var29 != 0.0) {
                    var11 /= var29;
                    var12 /= var29;
                    var13 /= var29;
                    final double var30 = this.worldObj.getBlockDensity(var25, var27.boundingBox);
                    final double var31 = (1.0 - var28) * var30;
                    var27.attackEntityFrom(DamageSource.setExplosionSource(this), (int)((var31 * var31 + var31) / 2.0 * 8.0 * this.explosionSize + 1.0));
                    final double var32 = EnchantmentProtection.func_92092_a(var27, var31);
                    final Entity entity = var27;
                    entity.motionX += var11 * var32;
                    final Entity entity2 = var27;
                    entity2.motionY += var12 * var32;
                    final Entity entity3 = var27;
                    entity3.motionZ += var13 * var32;
                    if (var27 instanceof EntityPlayer) {
                        this.field_77288_k.put(var27, this.worldObj.getWorldVec3Pool().getVecFromPool(var11 * var31, var12 * var31, var13 * var31));
                    }
                }
            }
        }
        this.explosionSize = var1;
    }
    
    public void doExplosionB(final boolean par1) {
        this.worldObj.playSoundEffect(this.explosionX, this.explosionY, this.explosionZ, "random.explode", 4.0f, (1.0f + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.2f) * 0.7f);
        if (this.explosionSize >= 2.0f && this.isSmoking) {
            this.worldObj.spawnParticle("hugeexplosion", this.explosionX, this.explosionY, this.explosionZ, 1.0, 0.0, 0.0);
        }
        else {
            this.worldObj.spawnParticle("largeexplode", this.explosionX, this.explosionY, this.explosionZ, 1.0, 0.0, 0.0);
        }
        if (this.isSmoking) {
            for (final ChunkPosition var3 : this.affectedBlockPositions) {
                final int var4 = var3.x;
                final int var5 = var3.y;
                final int var6 = var3.z;
                final int var7 = this.worldObj.getBlockId(var4, var5, var6);
                if (par1) {
                    final double var8 = var4 + this.worldObj.rand.nextFloat();
                    final double var9 = var5 + this.worldObj.rand.nextFloat();
                    final double var10 = var6 + this.worldObj.rand.nextFloat();
                    double var11 = var8 - this.explosionX;
                    double var12 = var9 - this.explosionY;
                    double var13 = var10 - this.explosionZ;
                    final double var14 = MathHelper.sqrt_double(var11 * var11 + var12 * var12 + var13 * var13);
                    var11 /= var14;
                    var12 /= var14;
                    var13 /= var14;
                    double var15 = 0.5 / (var14 / this.explosionSize + 0.1);
                    var15 *= this.worldObj.rand.nextFloat() * this.worldObj.rand.nextFloat() + 0.3f;
                    var11 *= var15;
                    var12 *= var15;
                    var13 *= var15;
                    this.worldObj.spawnParticle("explode", (var8 + this.explosionX * 1.0) / 2.0, (var9 + this.explosionY * 1.0) / 2.0, (var10 + this.explosionZ * 1.0) / 2.0, var11, var12, var13);
                    this.worldObj.spawnParticle("smoke", var8, var9, var10, var11, var12, var13);
                }
                if (var7 > 0) {
                    final Block var16 = Block.blocksList[var7];
                    if (var16.canDropFromExplosion(this)) {
                        var16.dropBlockAsItemWithChance(this.worldObj, var4, var5, var6, this.worldObj.getBlockMetadata(var4, var5, var6), 1.0f / this.explosionSize, 0);
                    }
                    this.worldObj.setBlock(var4, var5, var6, 0, 0, 3);
                    var16.onBlockDestroyedByExplosion(this.worldObj, var4, var5, var6, this);
                }
            }
        }
        if (this.isFlaming) {
            for (final ChunkPosition var3 : this.affectedBlockPositions) {
                final int var4 = var3.x;
                final int var5 = var3.y;
                final int var6 = var3.z;
                final int var7 = this.worldObj.getBlockId(var4, var5, var6);
                final int var17 = this.worldObj.getBlockId(var4, var5 - 1, var6);
                if (var7 == 0 && Block.opaqueCubeLookup[var17] && this.explosionRNG.nextInt(3) == 0) {
                    this.worldObj.setBlock(var4, var5, var6, Block.fire.blockID);
                }
            }
        }
    }
    
    public Map func_77277_b() {
        return this.field_77288_k;
    }
    
    public EntityLiving func_94613_c() {
        return (this.exploder == null) ? null : ((this.exploder instanceof EntityTNTPrimed) ? ((EntityTNTPrimed)this.exploder).getTntPlacedBy() : ((this.exploder instanceof EntityLiving) ? ((EntityLiving)this.exploder) : null));
    }
}
