/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.item;

import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.RedstoneDiodeBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.Pose;
import net.minecraft.entity.item.HangingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FilledMapItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.play.server.SSpawnObjectPacket;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapData;
import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ItemFrameEntity
extends HangingEntity {
    private static final Logger PRIVATE_LOGGER = LogManager.getLogger();
    private static final DataParameter<ItemStack> ITEM = EntityDataManager.createKey(ItemFrameEntity.class, DataSerializers.ITEMSTACK);
    private static final DataParameter<Integer> ROTATION = EntityDataManager.createKey(ItemFrameEntity.class, DataSerializers.VARINT);
    private float itemDropChance = 1.0f;
    private boolean fixed;

    public ItemFrameEntity(EntityType<? extends ItemFrameEntity> entityType, World world) {
        super((EntityType<? extends HangingEntity>)entityType, world);
    }

    public ItemFrameEntity(World world, BlockPos blockPos, Direction direction) {
        super(EntityType.ITEM_FRAME, world, blockPos);
        this.updateFacingWithBoundingBox(direction);
    }

    @Override
    protected float getEyeHeight(Pose pose, EntitySize entitySize) {
        return 0.0f;
    }

    @Override
    protected void registerData() {
        this.getDataManager().register(ITEM, ItemStack.EMPTY);
        this.getDataManager().register(ROTATION, 0);
    }

    @Override
    protected void updateFacingWithBoundingBox(Direction direction) {
        Validate.notNull(direction);
        this.facingDirection = direction;
        if (direction.getAxis().isHorizontal()) {
            this.rotationPitch = 0.0f;
            this.rotationYaw = this.facingDirection.getHorizontalIndex() * 90;
        } else {
            this.rotationPitch = -90 * direction.getAxisDirection().getOffset();
            this.rotationYaw = 0.0f;
        }
        this.prevRotationPitch = this.rotationPitch;
        this.prevRotationYaw = this.rotationYaw;
        this.updateBoundingBox();
    }

    @Override
    protected void updateBoundingBox() {
        if (this.facingDirection != null) {
            double d = 0.46875;
            double d2 = (double)this.hangingPosition.getX() + 0.5 - (double)this.facingDirection.getXOffset() * 0.46875;
            double d3 = (double)this.hangingPosition.getY() + 0.5 - (double)this.facingDirection.getYOffset() * 0.46875;
            double d4 = (double)this.hangingPosition.getZ() + 0.5 - (double)this.facingDirection.getZOffset() * 0.46875;
            this.setRawPosition(d2, d3, d4);
            double d5 = this.getWidthPixels();
            double d6 = this.getHeightPixels();
            double d7 = this.getWidthPixels();
            Direction.Axis axis = this.facingDirection.getAxis();
            switch (1.$SwitchMap$net$minecraft$util$Direction$Axis[axis.ordinal()]) {
                case 1: {
                    d5 = 1.0;
                    break;
                }
                case 2: {
                    d6 = 1.0;
                    break;
                }
                case 3: {
                    d7 = 1.0;
                }
            }
            this.setBoundingBox(new AxisAlignedBB(d2 - (d5 /= 32.0), d3 - (d6 /= 32.0), d4 - (d7 /= 32.0), d2 + d5, d3 + d6, d4 + d7));
        }
    }

    @Override
    public boolean onValidSurface() {
        if (this.fixed) {
            return false;
        }
        if (!this.world.hasNoCollisions(this)) {
            return true;
        }
        BlockState blockState = this.world.getBlockState(this.hangingPosition.offset(this.facingDirection.getOpposite()));
        return blockState.getMaterial().isSolid() || this.facingDirection.getAxis().isHorizontal() && RedstoneDiodeBlock.isDiode(blockState) ? this.world.getEntitiesInAABBexcluding(this, this.getBoundingBox(), IS_HANGING_ENTITY).isEmpty() : false;
    }

    @Override
    public void move(MoverType moverType, Vector3d vector3d) {
        if (!this.fixed) {
            super.move(moverType, vector3d);
        }
    }

    @Override
    public void addVelocity(double d, double d2, double d3) {
        if (!this.fixed) {
            super.addVelocity(d, d2, d3);
        }
    }

    @Override
    public float getCollisionBorderSize() {
        return 0.0f;
    }

    @Override
    public void onKillCommand() {
        this.removeItem(this.getDisplayedItem());
        super.onKillCommand();
    }

    @Override
    public boolean attackEntityFrom(DamageSource damageSource, float f) {
        if (this.fixed) {
            return damageSource != DamageSource.OUT_OF_WORLD && !damageSource.isCreativePlayer() ? false : super.attackEntityFrom(damageSource, f);
        }
        if (this.isInvulnerableTo(damageSource)) {
            return true;
        }
        if (!damageSource.isExplosion() && !this.getDisplayedItem().isEmpty()) {
            if (!this.world.isRemote) {
                this.dropItemOrSelf(damageSource.getTrueSource(), true);
                this.playSound(SoundEvents.ENTITY_ITEM_FRAME_REMOVE_ITEM, 1.0f, 1.0f);
            }
            return false;
        }
        return super.attackEntityFrom(damageSource, f);
    }

    @Override
    public int getWidthPixels() {
        return 1;
    }

    @Override
    public int getHeightPixels() {
        return 1;
    }

    @Override
    public boolean isInRangeToRenderDist(double d) {
        double d2 = 16.0;
        return d < (d2 = d2 * 64.0 * ItemFrameEntity.getRenderDistanceWeight()) * d2;
    }

    @Override
    public void onBroken(@Nullable Entity entity2) {
        this.playSound(SoundEvents.ENTITY_ITEM_FRAME_BREAK, 1.0f, 1.0f);
        this.dropItemOrSelf(entity2, false);
    }

    @Override
    public void playPlaceSound() {
        this.playSound(SoundEvents.ENTITY_ITEM_FRAME_PLACE, 1.0f, 1.0f);
    }

    private void dropItemOrSelf(@Nullable Entity entity2, boolean bl) {
        if (!this.fixed) {
            ItemStack itemStack = this.getDisplayedItem();
            this.setDisplayedItem(ItemStack.EMPTY);
            if (!this.world.getGameRules().getBoolean(GameRules.DO_ENTITY_DROPS)) {
                if (entity2 == null) {
                    this.removeItem(itemStack);
                }
            } else {
                if (entity2 instanceof PlayerEntity) {
                    PlayerEntity playerEntity = (PlayerEntity)entity2;
                    if (playerEntity.abilities.isCreativeMode) {
                        this.removeItem(itemStack);
                        return;
                    }
                }
                if (bl) {
                    this.entityDropItem(Items.ITEM_FRAME);
                }
                if (!itemStack.isEmpty()) {
                    itemStack = itemStack.copy();
                    this.removeItem(itemStack);
                    if (this.rand.nextFloat() < this.itemDropChance) {
                        this.entityDropItem(itemStack);
                    }
                }
            }
        }
    }

    private void removeItem(ItemStack itemStack) {
        if (itemStack.getItem() == Items.FILLED_MAP) {
            MapData mapData = FilledMapItem.getMapData(itemStack, this.world);
            mapData.removeItemFrame(this.hangingPosition, this.getEntityId());
            mapData.setDirty(false);
        }
        itemStack.setAttachedEntity(null);
    }

    public ItemStack getDisplayedItem() {
        return this.getDataManager().get(ITEM);
    }

    public void setDisplayedItem(ItemStack itemStack) {
        this.setDisplayedItemWithUpdate(itemStack, false);
    }

    public void setDisplayedItemWithUpdate(ItemStack itemStack, boolean bl) {
        if (!itemStack.isEmpty()) {
            itemStack = itemStack.copy();
            itemStack.setCount(1);
            itemStack.setAttachedEntity(this);
        }
        this.getDataManager().set(ITEM, itemStack);
        if (!itemStack.isEmpty()) {
            this.playSound(SoundEvents.ENTITY_ITEM_FRAME_ADD_ITEM, 1.0f, 1.0f);
        }
        if (bl && this.hangingPosition != null) {
            this.world.updateComparatorOutputLevel(this.hangingPosition, Blocks.AIR);
        }
    }

    @Override
    public boolean replaceItemInInventory(int n, ItemStack itemStack) {
        if (n == 0) {
            this.setDisplayedItem(itemStack);
            return false;
        }
        return true;
    }

    @Override
    public void notifyDataManagerChange(DataParameter<?> dataParameter) {
        ItemStack itemStack;
        if (dataParameter.equals(ITEM) && !(itemStack = this.getDisplayedItem()).isEmpty() && itemStack.getItemFrame() != this) {
            itemStack.setAttachedEntity(this);
        }
    }

    public int getRotation() {
        return this.getDataManager().get(ROTATION);
    }

    public void setItemRotation(int n) {
        this.setRotation(n, false);
    }

    private void setRotation(int n, boolean bl) {
        this.getDataManager().set(ROTATION, n % 8);
        if (bl && this.hangingPosition != null) {
            this.world.updateComparatorOutputLevel(this.hangingPosition, Blocks.AIR);
        }
    }

    @Override
    public void writeAdditional(CompoundNBT compoundNBT) {
        super.writeAdditional(compoundNBT);
        if (!this.getDisplayedItem().isEmpty()) {
            compoundNBT.put("Item", this.getDisplayedItem().write(new CompoundNBT()));
            compoundNBT.putByte("ItemRotation", (byte)this.getRotation());
            compoundNBT.putFloat("ItemDropChance", this.itemDropChance);
        }
        compoundNBT.putByte("Facing", (byte)this.facingDirection.getIndex());
        compoundNBT.putBoolean("Invisible", this.isInvisible());
        compoundNBT.putBoolean("Fixed", this.fixed);
    }

    @Override
    public void readAdditional(CompoundNBT compoundNBT) {
        super.readAdditional(compoundNBT);
        CompoundNBT compoundNBT2 = compoundNBT.getCompound("Item");
        if (compoundNBT2 != null && !compoundNBT2.isEmpty()) {
            ItemStack itemStack;
            ItemStack itemStack2 = ItemStack.read(compoundNBT2);
            if (itemStack2.isEmpty()) {
                PRIVATE_LOGGER.warn("Unable to load item from: {}", (Object)compoundNBT2);
            }
            if (!(itemStack = this.getDisplayedItem()).isEmpty() && !ItemStack.areItemStacksEqual(itemStack2, itemStack)) {
                this.removeItem(itemStack);
            }
            this.setDisplayedItemWithUpdate(itemStack2, true);
            this.setRotation(compoundNBT.getByte("ItemRotation"), true);
            if (compoundNBT.contains("ItemDropChance", 0)) {
                this.itemDropChance = compoundNBT.getFloat("ItemDropChance");
            }
        }
        this.updateFacingWithBoundingBox(Direction.byIndex(compoundNBT.getByte("Facing")));
        this.setInvisible(compoundNBT.getBoolean("Invisible"));
        this.fixed = compoundNBT.getBoolean("Fixed");
    }

    @Override
    public ActionResultType processInitialInteract(PlayerEntity playerEntity, Hand hand) {
        boolean bl;
        ItemStack itemStack = playerEntity.getHeldItem(hand);
        boolean bl2 = !this.getDisplayedItem().isEmpty();
        boolean bl3 = bl = !itemStack.isEmpty();
        if (this.fixed) {
            return ActionResultType.PASS;
        }
        if (!this.world.isRemote) {
            if (!bl2) {
                if (bl && !this.removed) {
                    this.setDisplayedItem(itemStack);
                    if (!playerEntity.abilities.isCreativeMode) {
                        itemStack.shrink(1);
                    }
                }
            } else {
                this.playSound(SoundEvents.ENTITY_ITEM_FRAME_ROTATE_ITEM, 1.0f, 1.0f);
                this.setItemRotation(this.getRotation() + 1);
            }
            return ActionResultType.CONSUME;
        }
        return !bl2 && !bl ? ActionResultType.PASS : ActionResultType.SUCCESS;
    }

    public int getAnalogOutput() {
        return this.getDisplayedItem().isEmpty() ? 0 : this.getRotation() % 8 + 1;
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return new SSpawnObjectPacket(this, this.getType(), this.facingDirection.getIndex(), this.getHangingPosition());
    }
}

