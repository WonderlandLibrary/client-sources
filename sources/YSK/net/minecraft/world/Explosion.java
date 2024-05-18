package net.minecraft.world;

import net.minecraft.entity.player.*;
import net.minecraft.block.material.*;
import net.minecraft.init.*;
import net.minecraft.block.*;
import net.minecraft.entity.*;
import net.minecraft.entity.item.*;
import com.google.common.collect.*;
import net.minecraft.util.*;
import net.minecraft.enchantment.*;
import java.util.*;
import net.minecraft.block.state.*;

public class Explosion
{
    private final Random explosionRNG;
    private final double explosionX;
    private final float explosionSize;
    private final Entity exploder;
    private static final String[] I;
    private final boolean isFlaming;
    private final List<BlockPos> affectedBlockPositions;
    private final Map<EntityPlayer, Vec3> playerKnockbackMap;
    private final double explosionY;
    private final double explosionZ;
    private final boolean isSmoking;
    private final World worldObj;
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (2 != 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public Map<EntityPlayer, Vec3> getPlayerKnockbackMap() {
        return this.playerKnockbackMap;
    }
    
    public void doExplosionB(final boolean b) {
        this.worldObj.playSoundEffect(this.explosionX, this.explosionY, this.explosionZ, Explosion.I["".length()], 4.0f, (1.0f + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.2f) * 0.7f);
        if (this.explosionSize >= 2.0f && this.isSmoking) {
            this.worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_HUGE, this.explosionX, this.explosionY, this.explosionZ, 1.0, 0.0, 0.0, new int["".length()]);
            "".length();
            if (-1 != -1) {
                throw null;
            }
        }
        else {
            this.worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_LARGE, this.explosionX, this.explosionY, this.explosionZ, 1.0, 0.0, 0.0, new int["".length()]);
        }
        if (this.isSmoking) {
            final Iterator<BlockPos> iterator = this.affectedBlockPositions.iterator();
            "".length();
            if (0 == 4) {
                throw null;
            }
            while (iterator.hasNext()) {
                final BlockPos blockPos = iterator.next();
                final Block block = this.worldObj.getBlockState(blockPos).getBlock();
                if (b) {
                    final double n = blockPos.getX() + this.worldObj.rand.nextFloat();
                    final double n2 = blockPos.getY() + this.worldObj.rand.nextFloat();
                    final double n3 = blockPos.getZ() + this.worldObj.rand.nextFloat();
                    final double n4 = n - this.explosionX;
                    final double n5 = n2 - this.explosionY;
                    final double n6 = n3 - this.explosionZ;
                    final double n7 = MathHelper.sqrt_double(n4 * n4 + n5 * n5 + n6 * n6);
                    final double n8 = n4 / n7;
                    final double n9 = n5 / n7;
                    final double n10 = n6 / n7;
                    final double n11 = 0.5 / (n7 / this.explosionSize + 0.1) * (this.worldObj.rand.nextFloat() * this.worldObj.rand.nextFloat() + 0.3f);
                    final double n12 = n8 * n11;
                    final double n13 = n9 * n11;
                    final double n14 = n10 * n11;
                    this.worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, (n + this.explosionX * 1.0) / 2.0, (n2 + this.explosionY * 1.0) / 2.0, (n3 + this.explosionZ * 1.0) / 2.0, n12, n13, n14, new int["".length()]);
                    this.worldObj.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, n, n2, n3, n12, n13, n14, new int["".length()]);
                }
                if (block.getMaterial() != Material.air) {
                    if (block.canDropFromExplosion(this)) {
                        block.dropBlockAsItemWithChance(this.worldObj, blockPos, this.worldObj.getBlockState(blockPos), 1.0f / this.explosionSize, "".length());
                    }
                    this.worldObj.setBlockState(blockPos, Blocks.air.getDefaultState(), "   ".length());
                    block.onBlockDestroyedByExplosion(this.worldObj, blockPos, this);
                }
            }
        }
        if (this.isFlaming) {
            final Iterator<BlockPos> iterator2 = this.affectedBlockPositions.iterator();
            "".length();
            if (2 <= 1) {
                throw null;
            }
            while (iterator2.hasNext()) {
                final BlockPos blockPos2 = iterator2.next();
                if (this.worldObj.getBlockState(blockPos2).getBlock().getMaterial() == Material.air && this.worldObj.getBlockState(blockPos2.down()).getBlock().isFullBlock() && this.explosionRNG.nextInt("   ".length()) == 0) {
                    this.worldObj.setBlockState(blockPos2, Blocks.fire.getDefaultState());
                }
            }
        }
    }
    
    public Explosion(final World worldObj, final Entity exploder, final double explosionX, final double explosionY, final double explosionZ, final float explosionSize, final boolean isFlaming, final boolean isSmoking) {
        this.explosionRNG = new Random();
        this.affectedBlockPositions = (List<BlockPos>)Lists.newArrayList();
        this.playerKnockbackMap = (Map<EntityPlayer, Vec3>)Maps.newHashMap();
        this.worldObj = worldObj;
        this.exploder = exploder;
        this.explosionSize = explosionSize;
        this.explosionX = explosionX;
        this.explosionY = explosionY;
        this.explosionZ = explosionZ;
        this.isFlaming = isFlaming;
        this.isSmoking = isSmoking;
    }
    
    public EntityLivingBase getExplosivePlacedBy() {
        EntityLivingBase tntPlacedBy;
        if (this.exploder == null) {
            tntPlacedBy = null;
            "".length();
            if (-1 >= 2) {
                throw null;
            }
        }
        else if (this.exploder instanceof EntityTNTPrimed) {
            tntPlacedBy = ((EntityTNTPrimed)this.exploder).getTntPlacedBy();
            "".length();
            if (1 < 0) {
                throw null;
            }
        }
        else if (this.exploder instanceof EntityLivingBase) {
            tntPlacedBy = (EntityLivingBase)this.exploder;
            "".length();
            if (0 <= -1) {
                throw null;
            }
        }
        else {
            tntPlacedBy = null;
        }
        return tntPlacedBy;
    }
    
    static {
        I();
    }
    
    public Explosion(final World world, final Entity entity, final double n, final double n2, final double n3, final float n4, final boolean b, final boolean b2, final List<BlockPos> list) {
        this(world, entity, n, n2, n3, n4, b, b2);
        this.affectedBlockPositions.addAll(list);
    }
    
    public Explosion(final World world, final Entity entity, final double n, final double n2, final double n3, final float n4, final List<BlockPos> list) {
        this(world, entity, n, n2, n3, n4, "".length() != 0, " ".length() != 0, list);
    }
    
    public void func_180342_d() {
        this.affectedBlockPositions.clear();
    }
    
    public void doExplosionA() {
        final HashSet hashSet = Sets.newHashSet();
        int i = "".length();
        "".length();
        if (1 == 3) {
            throw null;
        }
        while (i < (0x3C ^ 0x2C)) {
            int j = "".length();
            "".length();
            if (3 <= 2) {
                throw null;
            }
            while (j < (0x99 ^ 0x89)) {
                int k = "".length();
                "".length();
                if (3 == 2) {
                    throw null;
                }
                while (k < (0xB2 ^ 0xA2)) {
                    if (i == 0 || i == (0x8D ^ 0x82) || j == 0 || j == (0xCF ^ 0xC0) || k == 0 || k == (0x4F ^ 0x40)) {
                        final double n = i / 15.0f * 2.0f - 1.0f;
                        final double n2 = j / 15.0f * 2.0f - 1.0f;
                        final double n3 = k / 15.0f * 2.0f - 1.0f;
                        final double sqrt = Math.sqrt(n * n + n2 * n2 + n3 * n3);
                        final double n4 = n / sqrt;
                        final double n5 = n2 / sqrt;
                        final double n6 = n3 / sqrt;
                        float n7 = this.explosionSize * (0.7f + this.worldObj.rand.nextFloat() * 0.6f);
                        double explosionX = this.explosionX;
                        double explosionY = this.explosionY;
                        double explosionZ = this.explosionZ;
                        "".length();
                        if (3 <= 2) {
                            throw null;
                        }
                        while (n7 > 0.0f) {
                            final BlockPos blockPos = new BlockPos(explosionX, explosionY, explosionZ);
                            final IBlockState blockState = this.worldObj.getBlockState(blockPos);
                            if (blockState.getBlock().getMaterial() != Material.air) {
                                float n8;
                                if (this.exploder != null) {
                                    n8 = this.exploder.getExplosionResistance(this, this.worldObj, blockPos, blockState);
                                    "".length();
                                    if (1 < 0) {
                                        throw null;
                                    }
                                }
                                else {
                                    n8 = blockState.getBlock().getExplosionResistance(null);
                                }
                                n7 -= (n8 + 0.3f) * 0.3f;
                            }
                            if (n7 > 0.0f && (this.exploder == null || this.exploder.verifyExplosion(this, this.worldObj, blockPos, blockState, n7))) {
                                hashSet.add(blockPos);
                            }
                            explosionX += n4 * 0.30000001192092896;
                            explosionY += n5 * 0.30000001192092896;
                            explosionZ += n6 * 0.30000001192092896;
                            n7 -= 0.22500001f;
                        }
                    }
                    ++k;
                }
                ++j;
            }
            ++i;
        }
        this.affectedBlockPositions.addAll(hashSet);
        final float n9 = this.explosionSize * 2.0f;
        final List<Entity> entitiesWithinAABBExcludingEntity = this.worldObj.getEntitiesWithinAABBExcludingEntity(this.exploder, new AxisAlignedBB(MathHelper.floor_double(this.explosionX - n9 - 1.0), MathHelper.floor_double(this.explosionY - n9 - 1.0), MathHelper.floor_double(this.explosionZ - n9 - 1.0), MathHelper.floor_double(this.explosionX + n9 + 1.0), MathHelper.floor_double(this.explosionY + n9 + 1.0), MathHelper.floor_double(this.explosionZ + n9 + 1.0)));
        final Vec3 vec3 = new Vec3(this.explosionX, this.explosionY, this.explosionZ);
        int l = "".length();
        "".length();
        if (true != true) {
            throw null;
        }
        while (l < entitiesWithinAABBExcludingEntity.size()) {
            final Entity entity = entitiesWithinAABBExcludingEntity.get(l);
            if (!entity.isImmuneToExplosions()) {
                final double n10 = entity.getDistance(this.explosionX, this.explosionY, this.explosionZ) / n9;
                if (n10 <= 1.0) {
                    final double n11 = entity.posX - this.explosionX;
                    final double n12 = entity.posY + entity.getEyeHeight() - this.explosionY;
                    final double n13 = entity.posZ - this.explosionZ;
                    final double n14 = MathHelper.sqrt_double(n11 * n11 + n12 * n12 + n13 * n13);
                    if (n14 != 0.0) {
                        final double n15 = n11 / n14;
                        final double n16 = n12 / n14;
                        final double n17 = n13 / n14;
                        final double n18 = (1.0 - n10) * this.worldObj.getBlockDensity(vec3, entity.getEntityBoundingBox());
                        entity.attackEntityFrom(DamageSource.setExplosionSource(this), (int)((n18 * n18 + n18) / 2.0 * 8.0 * n9 + 1.0));
                        final double func_92092_a = EnchantmentProtection.func_92092_a(entity, n18);
                        final Entity entity2 = entity;
                        entity2.motionX += n15 * func_92092_a;
                        final Entity entity3 = entity;
                        entity3.motionY += n16 * func_92092_a;
                        final Entity entity4 = entity;
                        entity4.motionZ += n17 * func_92092_a;
                        if (entity instanceof EntityPlayer && !((EntityPlayer)entity).capabilities.disableDamage) {
                            this.playerKnockbackMap.put((EntityPlayer)entity, new Vec3(n15 * n18, n16 * n18, n17 * n18));
                        }
                    }
                }
            }
            ++l;
        }
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("4\u000b\u0018=\u001c+D\u0013!\u0003*\u0005\u0012<", "FjvYs");
    }
    
    public List<BlockPos> getAffectedBlockPositions() {
        return this.affectedBlockPositions;
    }
}
