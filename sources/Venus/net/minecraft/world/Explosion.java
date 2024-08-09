/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.AbstractFireBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.ProtectionEnchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.item.TNTEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameters;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.EntityExplosionContext;
import net.minecraft.world.ExplosionContext;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class Explosion {
    private static final ExplosionContext DEFAULT_CONTEXT = new ExplosionContext();
    private final boolean causesFire;
    private final Mode mode;
    private final Random random = new Random();
    private final World world;
    private final double x;
    private final double y;
    private final double z;
    @Nullable
    private final Entity exploder;
    private final float size;
    private final DamageSource damageSource;
    private final ExplosionContext context;
    private final List<BlockPos> affectedBlockPositions = Lists.newArrayList();
    private final Map<PlayerEntity, Vector3d> playerKnockbackMap = Maps.newHashMap();

    public Explosion(World world, @Nullable Entity entity2, double d, double d2, double d3, float f, List<BlockPos> list) {
        this(world, entity2, d, d2, d3, f, false, Mode.DESTROY, list);
    }

    public Explosion(World world, @Nullable Entity entity2, double d, double d2, double d3, float f, boolean bl, Mode mode, List<BlockPos> list) {
        this(world, entity2, d, d2, d3, f, bl, mode);
        this.affectedBlockPositions.addAll(list);
    }

    public Explosion(World world, @Nullable Entity entity2, double d, double d2, double d3, float f, boolean bl, Mode mode) {
        this(world, entity2, null, null, d, d2, d3, f, bl, mode);
    }

    public Explosion(World world, @Nullable Entity entity2, @Nullable DamageSource damageSource, @Nullable ExplosionContext explosionContext, double d, double d2, double d3, float f, boolean bl, Mode mode) {
        this.world = world;
        this.exploder = entity2;
        this.size = f;
        this.x = d;
        this.y = d2;
        this.z = d3;
        this.causesFire = bl;
        this.mode = mode;
        this.damageSource = damageSource == null ? DamageSource.causeExplosionDamage(this) : damageSource;
        this.context = explosionContext == null ? this.getEntityExplosionContext(entity2) : explosionContext;
    }

    private ExplosionContext getEntityExplosionContext(@Nullable Entity entity2) {
        return entity2 == null ? DEFAULT_CONTEXT : new EntityExplosionContext(entity2);
    }

    public static float getBlockDensity(Vector3d vector3d, Entity entity2) {
        AxisAlignedBB axisAlignedBB = entity2.getBoundingBox();
        double d = 1.0 / ((axisAlignedBB.maxX - axisAlignedBB.minX) * 2.0 + 1.0);
        double d2 = 1.0 / ((axisAlignedBB.maxY - axisAlignedBB.minY) * 2.0 + 1.0);
        double d3 = 1.0 / ((axisAlignedBB.maxZ - axisAlignedBB.minZ) * 2.0 + 1.0);
        double d4 = (1.0 - Math.floor(1.0 / d) * d) / 2.0;
        double d5 = (1.0 - Math.floor(1.0 / d3) * d3) / 2.0;
        if (!(d < 0.0 || d2 < 0.0 || d3 < 0.0)) {
            int n = 0;
            int n2 = 0;
            float f = 0.0f;
            while (f <= 1.0f) {
                float f2 = 0.0f;
                while (f2 <= 1.0f) {
                    float f3 = 0.0f;
                    while (f3 <= 1.0f) {
                        double d6;
                        double d7;
                        double d8 = MathHelper.lerp((double)f, axisAlignedBB.minX, axisAlignedBB.maxX);
                        Vector3d vector3d2 = new Vector3d(d8 + d4, d7 = MathHelper.lerp((double)f2, axisAlignedBB.minY, axisAlignedBB.maxY), (d6 = MathHelper.lerp((double)f3, axisAlignedBB.minZ, axisAlignedBB.maxZ)) + d5);
                        if (entity2.world.rayTraceBlocks(new RayTraceContext(vector3d2, vector3d, RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, entity2)).getType() == RayTraceResult.Type.MISS) {
                            ++n;
                        }
                        ++n2;
                        f3 = (float)((double)f3 + d3);
                    }
                    f2 = (float)((double)f2 + d2);
                }
                f = (float)((double)f + d);
            }
            return (float)n / (float)n2;
        }
        return 0.0f;
    }

    public void doExplosionA() {
        int n;
        int n2;
        HashSet<BlockPos> hashSet = Sets.newHashSet();
        int n3 = 16;
        for (int i = 0; i < 16; ++i) {
            for (n2 = 0; n2 < 16; ++n2) {
                for (n = 0; n < 16; ++n) {
                    if (i != 0 && i != 15 && n2 != 0 && n2 != 15 && n != 0 && n != 15) continue;
                    double d = (float)i / 15.0f * 2.0f - 1.0f;
                    double d2 = (float)n2 / 15.0f * 2.0f - 1.0f;
                    double d3 = (float)n / 15.0f * 2.0f - 1.0f;
                    double d4 = Math.sqrt(d * d + d2 * d2 + d3 * d3);
                    d /= d4;
                    d2 /= d4;
                    d3 /= d4;
                    double d5 = this.x;
                    double d6 = this.y;
                    double d7 = this.z;
                    float f = 0.3f;
                    for (float f2 = this.size * (0.7f + this.world.rand.nextFloat() * 0.6f); f2 > 0.0f; f2 -= 0.22500001f) {
                        FluidState fluidState;
                        BlockPos blockPos = new BlockPos(d5, d6, d7);
                        BlockState blockState = this.world.getBlockState(blockPos);
                        Optional<Float> optional = this.context.getExplosionResistance(this, this.world, blockPos, blockState, fluidState = this.world.getFluidState(blockPos));
                        if (optional.isPresent()) {
                            f2 -= (optional.get().floatValue() + 0.3f) * 0.3f;
                        }
                        if (f2 > 0.0f && this.context.canExplosionDestroyBlock(this, this.world, blockPos, blockState, f2)) {
                            hashSet.add(blockPos);
                        }
                        d5 += d * (double)0.3f;
                        d6 += d2 * (double)0.3f;
                        d7 += d3 * (double)0.3f;
                    }
                }
            }
        }
        this.affectedBlockPositions.addAll(hashSet);
        float f = this.size * 2.0f;
        n2 = MathHelper.floor(this.x - (double)f - 1.0);
        n = MathHelper.floor(this.x + (double)f + 1.0);
        int n4 = MathHelper.floor(this.y - (double)f - 1.0);
        int n5 = MathHelper.floor(this.y + (double)f + 1.0);
        int n6 = MathHelper.floor(this.z - (double)f - 1.0);
        int n7 = MathHelper.floor(this.z + (double)f + 1.0);
        List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this.exploder, new AxisAlignedBB(n2, n4, n6, n, n5, n7));
        Vector3d vector3d = new Vector3d(this.x, this.y, this.z);
        for (int i = 0; i < list.size(); ++i) {
            PlayerEntity playerEntity;
            double d;
            double d8;
            double d9;
            double d10;
            double d11;
            Entity entity2 = list.get(i);
            if (entity2.isImmuneToExplosions() || !((d11 = (double)(MathHelper.sqrt(entity2.getDistanceSq(vector3d)) / f)) <= 1.0) || (d10 = (double)MathHelper.sqrt((d9 = entity2.getPosX() - this.x) * d9 + (d8 = (entity2 instanceof TNTEntity ? entity2.getPosY() : entity2.getPosYEye()) - this.y) * d8 + (d = entity2.getPosZ() - this.z) * d)) == 0.0) continue;
            d9 /= d10;
            d8 /= d10;
            d /= d10;
            double d12 = Explosion.getBlockDensity(vector3d, entity2);
            double d13 = (1.0 - d11) * d12;
            entity2.attackEntityFrom(this.getDamageSource(), (int)((d13 * d13 + d13) / 2.0 * 7.0 * (double)f + 1.0));
            double d14 = d13;
            if (entity2 instanceof LivingEntity) {
                d14 = ProtectionEnchantment.getBlastDamageReduction((LivingEntity)entity2, d13);
            }
            entity2.setMotion(entity2.getMotion().add(d9 * d14, d8 * d14, d * d14));
            if (!(entity2 instanceof PlayerEntity) || (playerEntity = (PlayerEntity)entity2).isSpectator() || playerEntity.isCreative() && playerEntity.abilities.isFlying) continue;
            this.playerKnockbackMap.put(playerEntity, new Vector3d(d9 * d13, d8 * d13, d * d13));
        }
    }

    public void doExplosionB(boolean bl) {
        boolean bl2;
        if (this.world.isRemote) {
            this.world.playSound(this.x, this.y, this.z, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.BLOCKS, 4.0f, (1.0f + (this.world.rand.nextFloat() - this.world.rand.nextFloat()) * 0.2f) * 0.7f, true);
        }
        boolean bl3 = bl2 = this.mode != Mode.NONE;
        if (bl) {
            if (!(this.size < 2.0f) && bl2) {
                this.world.addParticle(ParticleTypes.EXPLOSION_EMITTER, this.x, this.y, this.z, 1.0, 0.0, 0.0);
            } else {
                this.world.addParticle(ParticleTypes.EXPLOSION, this.x, this.y, this.z, 1.0, 0.0, 0.0);
            }
        }
        if (bl2) {
            ObjectArrayList objectArrayList = new ObjectArrayList();
            Collections.shuffle(this.affectedBlockPositions, this.world.rand);
            for (BlockPos blockPos : this.affectedBlockPositions) {
                BlockState blockState = this.world.getBlockState(blockPos);
                Block block = blockState.getBlock();
                if (blockState.isAir()) continue;
                BlockPos blockPos2 = blockPos.toImmutable();
                this.world.getProfiler().startSection("explosion_blocks");
                if (block.canDropFromExplosion(this) && this.world instanceof ServerWorld) {
                    TileEntity tileEntity = block.isTileEntityProvider() ? this.world.getTileEntity(blockPos) : null;
                    LootContext.Builder builder = new LootContext.Builder((ServerWorld)this.world).withRandom(this.world.rand).withParameter(LootParameters.field_237457_g_, Vector3d.copyCentered(blockPos)).withParameter(LootParameters.TOOL, ItemStack.EMPTY).withNullableParameter(LootParameters.BLOCK_ENTITY, tileEntity).withNullableParameter(LootParameters.THIS_ENTITY, this.exploder);
                    if (this.mode == Mode.DESTROY) {
                        builder.withParameter(LootParameters.EXPLOSION_RADIUS, Float.valueOf(this.size));
                    }
                    blockState.getDrops(builder).forEach(arg_0 -> Explosion.lambda$doExplosionB$0(objectArrayList, blockPos2, arg_0));
                }
                this.world.setBlockState(blockPos, Blocks.AIR.getDefaultState(), 0);
                block.onExplosionDestroy(this.world, blockPos, this);
                this.world.getProfiler().endSection();
            }
            ObjectIterator objectIterator = objectArrayList.iterator();
            while (objectIterator.hasNext()) {
                Pair pair = (Pair)objectIterator.next();
                Block.spawnAsEntity(this.world, (BlockPos)pair.getSecond(), (ItemStack)pair.getFirst());
            }
        }
        if (this.causesFire) {
            for (BlockPos blockPos : this.affectedBlockPositions) {
                if (this.random.nextInt(3) != 0 || !this.world.getBlockState(blockPos).isAir() || !this.world.getBlockState(blockPos.down()).isOpaqueCube(this.world, blockPos.down())) continue;
                this.world.setBlockState(blockPos, AbstractFireBlock.getFireForPlacement(this.world, blockPos));
            }
        }
    }

    private static void handleExplosionDrops(ObjectArrayList<Pair<ItemStack, BlockPos>> objectArrayList, ItemStack itemStack, BlockPos blockPos) {
        int n = objectArrayList.size();
        for (int i = 0; i < n; ++i) {
            Pair<ItemStack, BlockPos> pair = objectArrayList.get(i);
            ItemStack itemStack2 = pair.getFirst();
            if (!ItemEntity.canMergeStacks(itemStack2, itemStack)) continue;
            ItemStack itemStack3 = ItemEntity.mergeStacks(itemStack2, itemStack, 16);
            objectArrayList.set(i, Pair.of(itemStack3, pair.getSecond()));
            if (!itemStack.isEmpty()) continue;
            return;
        }
        objectArrayList.add(Pair.of(itemStack, blockPos));
    }

    public DamageSource getDamageSource() {
        return this.damageSource;
    }

    public Map<PlayerEntity, Vector3d> getPlayerKnockbackMap() {
        return this.playerKnockbackMap;
    }

    @Nullable
    public LivingEntity getExplosivePlacedBy() {
        Entity entity2;
        if (this.exploder == null) {
            return null;
        }
        if (this.exploder instanceof TNTEntity) {
            return ((TNTEntity)this.exploder).getTntPlacedBy();
        }
        if (this.exploder instanceof LivingEntity) {
            return (LivingEntity)this.exploder;
        }
        if (this.exploder instanceof ProjectileEntity && (entity2 = ((ProjectileEntity)this.exploder).func_234616_v_()) instanceof LivingEntity) {
            return (LivingEntity)entity2;
        }
        return null;
    }

    public void clearAffectedBlockPositions() {
        this.affectedBlockPositions.clear();
    }

    public List<BlockPos> getAffectedBlockPositions() {
        return this.affectedBlockPositions;
    }

    private static void lambda$doExplosionB$0(ObjectArrayList objectArrayList, BlockPos blockPos, ItemStack itemStack) {
        Explosion.handleExplosionDrops(objectArrayList, itemStack, blockPos);
    }

    public static enum Mode {
        NONE,
        BREAK,
        DESTROY;

    }
}

