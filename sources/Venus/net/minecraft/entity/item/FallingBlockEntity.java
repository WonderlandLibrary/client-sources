/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.item;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.AnvilBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ConcretePowderBlock;
import net.minecraft.block.FallingBlock;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MoverType;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.DirectionalPlaceContext;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.play.server.SSpawnObjectPacket;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

public class FallingBlockEntity
extends Entity {
    private BlockState fallTile = Blocks.SAND.getDefaultState();
    public int fallTime;
    public boolean shouldDropItem = true;
    private boolean dontSetBlock;
    private boolean hurtEntities;
    private int fallHurtMax = 40;
    private float fallHurtAmount = 2.0f;
    public CompoundNBT tileEntityData;
    protected static final DataParameter<BlockPos> ORIGIN = EntityDataManager.createKey(FallingBlockEntity.class, DataSerializers.BLOCK_POS);

    public FallingBlockEntity(EntityType<? extends FallingBlockEntity> entityType, World world) {
        super(entityType, world);
    }

    public FallingBlockEntity(World world, double d, double d2, double d3, BlockState blockState) {
        this((EntityType<? extends FallingBlockEntity>)EntityType.FALLING_BLOCK, world);
        this.fallTile = blockState;
        this.preventEntitySpawning = true;
        this.setPosition(d, d2 + (double)((1.0f - this.getHeight()) / 2.0f), d3);
        this.setMotion(Vector3d.ZERO);
        this.prevPosX = d;
        this.prevPosY = d2;
        this.prevPosZ = d3;
        this.setOrigin(this.getPosition());
    }

    @Override
    public boolean canBeAttackedWithItem() {
        return true;
    }

    public void setOrigin(BlockPos blockPos) {
        this.dataManager.set(ORIGIN, blockPos);
    }

    public BlockPos getOrigin() {
        return this.dataManager.get(ORIGIN);
    }

    @Override
    protected boolean canTriggerWalking() {
        return true;
    }

    @Override
    protected void registerData() {
        this.dataManager.register(ORIGIN, BlockPos.ZERO);
    }

    @Override
    public boolean canBeCollidedWith() {
        return !this.removed;
    }

    @Override
    public void tick() {
        if (this.fallTile.isAir()) {
            this.remove();
        } else {
            BlockPos blockPos;
            Block block = this.fallTile.getBlock();
            if (this.fallTime++ == 0) {
                blockPos = this.getPosition();
                if (this.world.getBlockState(blockPos).isIn(block)) {
                    this.world.removeBlock(blockPos, true);
                } else if (!this.world.isRemote) {
                    this.remove();
                    return;
                }
            }
            if (!this.hasNoGravity()) {
                this.setMotion(this.getMotion().add(0.0, -0.04, 0.0));
            }
            this.move(MoverType.SELF, this.getMotion());
            if (!this.world.isRemote) {
                Object object;
                blockPos = this.getPosition();
                boolean bl = this.fallTile.getBlock() instanceof ConcretePowderBlock;
                boolean bl2 = bl && this.world.getFluidState(blockPos).isTagged(FluidTags.WATER);
                double d = this.getMotion().lengthSquared();
                if (bl && d > 1.0 && ((BlockRayTraceResult)(object = this.world.rayTraceBlocks(new RayTraceContext(new Vector3d(this.prevPosX, this.prevPosY, this.prevPosZ), this.getPositionVec(), RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.SOURCE_ONLY, this)))).getType() != RayTraceResult.Type.MISS && this.world.getFluidState(((BlockRayTraceResult)object).getPos()).isTagged(FluidTags.WATER)) {
                    blockPos = ((BlockRayTraceResult)object).getPos();
                    bl2 = true;
                }
                if (!this.onGround && !bl2) {
                    if (!(this.world.isRemote || (this.fallTime <= 100 || blockPos.getY() >= 1 && blockPos.getY() <= 256) && this.fallTime <= 600)) {
                        if (this.shouldDropItem && this.world.getGameRules().getBoolean(GameRules.DO_ENTITY_DROPS)) {
                            this.entityDropItem(block);
                        }
                        this.remove();
                    }
                } else {
                    object = this.world.getBlockState(blockPos);
                    this.setMotion(this.getMotion().mul(0.7, -0.5, 0.7));
                    if (!((AbstractBlock.AbstractBlockState)object).isIn(Blocks.MOVING_PISTON)) {
                        this.remove();
                        if (!this.dontSetBlock) {
                            boolean bl3;
                            boolean bl4 = ((AbstractBlock.AbstractBlockState)object).isReplaceable(new DirectionalPlaceContext(this.world, blockPos, Direction.DOWN, ItemStack.EMPTY, Direction.UP));
                            boolean bl5 = FallingBlock.canFallThrough(this.world.getBlockState(blockPos.down())) && (!bl || !bl2);
                            boolean bl6 = bl3 = this.fallTile.isValidPosition(this.world, blockPos) && !bl5;
                            if (bl4 && bl3) {
                                if (this.fallTile.hasProperty(BlockStateProperties.WATERLOGGED) && this.world.getFluidState(blockPos).getFluid() == Fluids.WATER) {
                                    this.fallTile = (BlockState)this.fallTile.with(BlockStateProperties.WATERLOGGED, true);
                                }
                                if (this.world.setBlockState(blockPos, this.fallTile, 0)) {
                                    TileEntity tileEntity;
                                    if (block instanceof FallingBlock) {
                                        ((FallingBlock)block).onEndFalling(this.world, blockPos, this.fallTile, (BlockState)object, this);
                                    }
                                    if (this.tileEntityData != null && block instanceof ITileEntityProvider && (tileEntity = this.world.getTileEntity(blockPos)) != null) {
                                        CompoundNBT compoundNBT = tileEntity.write(new CompoundNBT());
                                        for (String string : this.tileEntityData.keySet()) {
                                            INBT iNBT = this.tileEntityData.get(string);
                                            if ("x".equals(string) || "y".equals(string) || "z".equals(string)) continue;
                                            compoundNBT.put(string, iNBT.copy());
                                        }
                                        tileEntity.read(this.fallTile, compoundNBT);
                                        tileEntity.markDirty();
                                    }
                                } else if (this.shouldDropItem && this.world.getGameRules().getBoolean(GameRules.DO_ENTITY_DROPS)) {
                                    this.entityDropItem(block);
                                }
                            } else if (this.shouldDropItem && this.world.getGameRules().getBoolean(GameRules.DO_ENTITY_DROPS)) {
                                this.entityDropItem(block);
                            }
                        } else if (block instanceof FallingBlock) {
                            ((FallingBlock)block).onBroken(this.world, blockPos, this);
                        }
                    }
                }
            }
            this.setMotion(this.getMotion().scale(0.98));
        }
    }

    @Override
    public boolean onLivingFall(float f, float f2) {
        int n;
        if (this.hurtEntities && (n = MathHelper.ceil(f - 1.0f)) > 0) {
            ArrayList<Entity> arrayList = Lists.newArrayList(this.world.getEntitiesWithinAABBExcludingEntity(this, this.getBoundingBox()));
            boolean bl = this.fallTile.isIn(BlockTags.ANVIL);
            DamageSource damageSource = bl ? DamageSource.ANVIL : DamageSource.FALLING_BLOCK;
            for (Entity entity2 : arrayList) {
                entity2.attackEntityFrom(damageSource, Math.min(MathHelper.floor((float)n * this.fallHurtAmount), this.fallHurtMax));
            }
            if (bl && (double)this.rand.nextFloat() < (double)0.05f + (double)n * 0.05) {
                BlockState blockState = AnvilBlock.damage(this.fallTile);
                if (blockState == null) {
                    this.dontSetBlock = true;
                } else {
                    this.fallTile = blockState;
                }
            }
        }
        return true;
    }

    @Override
    protected void writeAdditional(CompoundNBT compoundNBT) {
        compoundNBT.put("BlockState", NBTUtil.writeBlockState(this.fallTile));
        compoundNBT.putInt("Time", this.fallTime);
        compoundNBT.putBoolean("DropItem", this.shouldDropItem);
        compoundNBT.putBoolean("HurtEntities", this.hurtEntities);
        compoundNBT.putFloat("FallHurtAmount", this.fallHurtAmount);
        compoundNBT.putInt("FallHurtMax", this.fallHurtMax);
        if (this.tileEntityData != null) {
            compoundNBT.put("TileEntityData", this.tileEntityData);
        }
    }

    @Override
    protected void readAdditional(CompoundNBT compoundNBT) {
        this.fallTile = NBTUtil.readBlockState(compoundNBT.getCompound("BlockState"));
        this.fallTime = compoundNBT.getInt("Time");
        if (compoundNBT.contains("HurtEntities", 0)) {
            this.hurtEntities = compoundNBT.getBoolean("HurtEntities");
            this.fallHurtAmount = compoundNBT.getFloat("FallHurtAmount");
            this.fallHurtMax = compoundNBT.getInt("FallHurtMax");
        } else if (this.fallTile.isIn(BlockTags.ANVIL)) {
            this.hurtEntities = true;
        }
        if (compoundNBT.contains("DropItem", 0)) {
            this.shouldDropItem = compoundNBT.getBoolean("DropItem");
        }
        if (compoundNBT.contains("TileEntityData", 1)) {
            this.tileEntityData = compoundNBT.getCompound("TileEntityData");
        }
        if (this.fallTile.isAir()) {
            this.fallTile = Blocks.SAND.getDefaultState();
        }
    }

    public World getWorldObj() {
        return this.world;
    }

    public void setHurtEntities(boolean bl) {
        this.hurtEntities = bl;
    }

    @Override
    public boolean canRenderOnFire() {
        return true;
    }

    @Override
    public void fillCrashReport(CrashReportCategory crashReportCategory) {
        super.fillCrashReport(crashReportCategory);
        crashReportCategory.addDetail("Immitating BlockState", this.fallTile.toString());
    }

    public BlockState getBlockState() {
        return this.fallTile;
    }

    @Override
    public boolean ignoreItemEntityData() {
        return false;
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return new SSpawnObjectPacket(this, Block.getStateId(this.getBlockState()));
    }
}

