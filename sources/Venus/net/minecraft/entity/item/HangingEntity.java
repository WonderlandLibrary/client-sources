/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.item;

import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.block.RedstoneDiodeBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import org.apache.commons.lang3.Validate;

public abstract class HangingEntity
extends Entity {
    protected static final Predicate<Entity> IS_HANGING_ENTITY = HangingEntity::lambda$static$0;
    private int tickCounter1;
    protected BlockPos hangingPosition;
    protected Direction facingDirection = Direction.SOUTH;

    protected HangingEntity(EntityType<? extends HangingEntity> entityType, World world) {
        super(entityType, world);
    }

    protected HangingEntity(EntityType<? extends HangingEntity> entityType, World world, BlockPos blockPos) {
        this(entityType, world);
        this.hangingPosition = blockPos;
    }

    @Override
    protected void registerData() {
    }

    protected void updateFacingWithBoundingBox(Direction direction) {
        Validate.notNull(direction);
        Validate.isTrue(direction.getAxis().isHorizontal());
        this.facingDirection = direction;
        this.prevRotationYaw = this.rotationYaw = (float)(this.facingDirection.getHorizontalIndex() * 90);
        this.updateBoundingBox();
    }

    protected void updateBoundingBox() {
        if (this.facingDirection != null) {
            double d = (double)this.hangingPosition.getX() + 0.5;
            double d2 = (double)this.hangingPosition.getY() + 0.5;
            double d3 = (double)this.hangingPosition.getZ() + 0.5;
            double d4 = 0.46875;
            double d5 = this.offs(this.getWidthPixels());
            double d6 = this.offs(this.getHeightPixels());
            d -= (double)this.facingDirection.getXOffset() * 0.46875;
            d3 -= (double)this.facingDirection.getZOffset() * 0.46875;
            Direction direction = this.facingDirection.rotateYCCW();
            this.setRawPosition(d += d5 * (double)direction.getXOffset(), d2 += d6, d3 += d5 * (double)direction.getZOffset());
            double d7 = this.getWidthPixels();
            double d8 = this.getHeightPixels();
            double d9 = this.getWidthPixels();
            if (this.facingDirection.getAxis() == Direction.Axis.Z) {
                d9 = 1.0;
            } else {
                d7 = 1.0;
            }
            this.setBoundingBox(new AxisAlignedBB(d - (d7 /= 32.0), d2 - (d8 /= 32.0), d3 - (d9 /= 32.0), d + d7, d2 + d8, d3 + d9));
        }
    }

    private double offs(int n) {
        return n % 32 == 0 ? 0.5 : 0.0;
    }

    @Override
    public void tick() {
        if (!this.world.isRemote) {
            if (this.getPosY() < -64.0) {
                this.outOfWorld();
            }
            if (this.tickCounter1++ == 100) {
                this.tickCounter1 = 0;
                if (!this.removed && !this.onValidSurface()) {
                    this.remove();
                    this.onBroken(null);
                }
            }
        }
    }

    public boolean onValidSurface() {
        if (!this.world.hasNoCollisions(this)) {
            return true;
        }
        int n = Math.max(1, this.getWidthPixels() / 16);
        int n2 = Math.max(1, this.getHeightPixels() / 16);
        BlockPos blockPos = this.hangingPosition.offset(this.facingDirection.getOpposite());
        Direction direction = this.facingDirection.rotateYCCW();
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n2; ++j) {
                int n3 = (n - 1) / -2;
                int n4 = (n2 - 1) / -2;
                mutable.setPos(blockPos).move(direction, i + n3).move(Direction.UP, j + n4);
                BlockState blockState = this.world.getBlockState(mutable);
                if (blockState.getMaterial().isSolid() || RedstoneDiodeBlock.isDiode(blockState)) continue;
                return true;
            }
        }
        return this.world.getEntitiesInAABBexcluding(this, this.getBoundingBox(), IS_HANGING_ENTITY).isEmpty();
    }

    @Override
    public boolean canBeCollidedWith() {
        return false;
    }

    @Override
    public boolean hitByEntity(Entity entity2) {
        if (entity2 instanceof PlayerEntity) {
            PlayerEntity playerEntity = (PlayerEntity)entity2;
            return !this.world.isBlockModifiable(playerEntity, this.hangingPosition) ? true : this.attackEntityFrom(DamageSource.causePlayerDamage(playerEntity), 0.0f);
        }
        return true;
    }

    @Override
    public Direction getHorizontalFacing() {
        return this.facingDirection;
    }

    @Override
    public boolean attackEntityFrom(DamageSource damageSource, float f) {
        if (this.isInvulnerableTo(damageSource)) {
            return true;
        }
        if (!this.removed && !this.world.isRemote) {
            this.remove();
            this.markVelocityChanged();
            this.onBroken(damageSource.getTrueSource());
        }
        return false;
    }

    @Override
    public void move(MoverType moverType, Vector3d vector3d) {
        if (!this.world.isRemote && !this.removed && vector3d.lengthSquared() > 0.0) {
            this.remove();
            this.onBroken(null);
        }
    }

    @Override
    public void addVelocity(double d, double d2, double d3) {
        if (!this.world.isRemote && !this.removed && d * d + d2 * d2 + d3 * d3 > 0.0) {
            this.remove();
            this.onBroken(null);
        }
    }

    @Override
    public void writeAdditional(CompoundNBT compoundNBT) {
        BlockPos blockPos = this.getHangingPosition();
        compoundNBT.putInt("TileX", blockPos.getX());
        compoundNBT.putInt("TileY", blockPos.getY());
        compoundNBT.putInt("TileZ", blockPos.getZ());
    }

    @Override
    public void readAdditional(CompoundNBT compoundNBT) {
        this.hangingPosition = new BlockPos(compoundNBT.getInt("TileX"), compoundNBT.getInt("TileY"), compoundNBT.getInt("TileZ"));
    }

    public abstract int getWidthPixels();

    public abstract int getHeightPixels();

    public abstract void onBroken(@Nullable Entity var1);

    public abstract void playPlaceSound();

    @Override
    public ItemEntity entityDropItem(ItemStack itemStack, float f) {
        ItemEntity itemEntity = new ItemEntity(this.world, this.getPosX() + (double)((float)this.facingDirection.getXOffset() * 0.15f), this.getPosY() + (double)f, this.getPosZ() + (double)((float)this.facingDirection.getZOffset() * 0.15f), itemStack);
        itemEntity.setDefaultPickupDelay();
        this.world.addEntity(itemEntity);
        return itemEntity;
    }

    @Override
    protected boolean shouldSetPosAfterLoading() {
        return true;
    }

    @Override
    public void setPosition(double d, double d2, double d3) {
        this.hangingPosition = new BlockPos(d, d2, d3);
        this.updateBoundingBox();
        this.isAirBorne = true;
    }

    public BlockPos getHangingPosition() {
        return this.hangingPosition;
    }

    @Override
    public float getRotatedYaw(Rotation rotation) {
        if (this.facingDirection.getAxis() != Direction.Axis.Y) {
            switch (1.$SwitchMap$net$minecraft$util$Rotation[rotation.ordinal()]) {
                case 1: {
                    this.facingDirection = this.facingDirection.getOpposite();
                    break;
                }
                case 2: {
                    this.facingDirection = this.facingDirection.rotateYCCW();
                    break;
                }
                case 3: {
                    this.facingDirection = this.facingDirection.rotateY();
                }
            }
        }
        float f = MathHelper.wrapDegrees(this.rotationYaw);
        switch (1.$SwitchMap$net$minecraft$util$Rotation[rotation.ordinal()]) {
            case 1: {
                return f + 180.0f;
            }
            case 2: {
                return f + 90.0f;
            }
            case 3: {
                return f + 270.0f;
            }
        }
        return f;
    }

    @Override
    public float getMirroredYaw(Mirror mirror) {
        return this.getRotatedYaw(mirror.toRotation(this.facingDirection));
    }

    @Override
    public void func_241841_a(ServerWorld serverWorld, LightningBoltEntity lightningBoltEntity) {
    }

    @Override
    public void recalculateSize() {
    }

    private static boolean lambda$static$0(Entity entity2) {
        return entity2 instanceof HangingEntity;
    }
}

