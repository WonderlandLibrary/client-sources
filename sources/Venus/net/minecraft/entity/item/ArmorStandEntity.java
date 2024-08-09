/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.item;

import java.util.List;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.PushReaction;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.Pose;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.item.minecart.AbstractMinecartEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.BlockParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.HandSide;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Rotations;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class ArmorStandEntity
extends LivingEntity {
    private static final Rotations DEFAULT_HEAD_ROTATION = new Rotations(0.0f, 0.0f, 0.0f);
    private static final Rotations DEFAULT_BODY_ROTATION = new Rotations(0.0f, 0.0f, 0.0f);
    private static final Rotations DEFAULT_LEFTARM_ROTATION = new Rotations(-10.0f, 0.0f, -10.0f);
    private static final Rotations DEFAULT_RIGHTARM_ROTATION = new Rotations(-15.0f, 0.0f, 10.0f);
    private static final Rotations DEFAULT_LEFTLEG_ROTATION = new Rotations(-1.0f, 0.0f, -1.0f);
    private static final Rotations DEFAULT_RIGHTLEG_ROTATION = new Rotations(1.0f, 0.0f, 1.0f);
    private static final EntitySize field_242328_bp = new EntitySize(0.0f, 0.0f, true);
    private static final EntitySize field_242329_bq = EntityType.ARMOR_STAND.getSize().scale(0.5f);
    public static final DataParameter<Byte> STATUS = EntityDataManager.createKey(ArmorStandEntity.class, DataSerializers.BYTE);
    public static final DataParameter<Rotations> HEAD_ROTATION = EntityDataManager.createKey(ArmorStandEntity.class, DataSerializers.ROTATIONS);
    public static final DataParameter<Rotations> BODY_ROTATION = EntityDataManager.createKey(ArmorStandEntity.class, DataSerializers.ROTATIONS);
    public static final DataParameter<Rotations> LEFT_ARM_ROTATION = EntityDataManager.createKey(ArmorStandEntity.class, DataSerializers.ROTATIONS);
    public static final DataParameter<Rotations> RIGHT_ARM_ROTATION = EntityDataManager.createKey(ArmorStandEntity.class, DataSerializers.ROTATIONS);
    public static final DataParameter<Rotations> LEFT_LEG_ROTATION = EntityDataManager.createKey(ArmorStandEntity.class, DataSerializers.ROTATIONS);
    public static final DataParameter<Rotations> RIGHT_LEG_ROTATION = EntityDataManager.createKey(ArmorStandEntity.class, DataSerializers.ROTATIONS);
    private static final Predicate<Entity> IS_RIDEABLE_MINECART = ArmorStandEntity::lambda$static$0;
    private final NonNullList<ItemStack> handItems = NonNullList.withSize(2, ItemStack.EMPTY);
    private final NonNullList<ItemStack> armorItems = NonNullList.withSize(4, ItemStack.EMPTY);
    private boolean canInteract;
    public long punchCooldown;
    private int disabledSlots;
    private Rotations headRotation = DEFAULT_HEAD_ROTATION;
    private Rotations bodyRotation = DEFAULT_BODY_ROTATION;
    private Rotations leftArmRotation = DEFAULT_LEFTARM_ROTATION;
    private Rotations rightArmRotation = DEFAULT_RIGHTARM_ROTATION;
    private Rotations leftLegRotation = DEFAULT_LEFTLEG_ROTATION;
    private Rotations rightLegRotation = DEFAULT_RIGHTLEG_ROTATION;

    public ArmorStandEntity(EntityType<? extends ArmorStandEntity> entityType, World world) {
        super((EntityType<? extends LivingEntity>)entityType, world);
        this.stepHeight = 0.0f;
    }

    public ArmorStandEntity(World world, double d, double d2, double d3) {
        this((EntityType<? extends ArmorStandEntity>)EntityType.ARMOR_STAND, world);
        this.setPosition(d, d2, d3);
    }

    @Override
    public void recalculateSize() {
        double d = this.getPosX();
        double d2 = this.getPosY();
        double d3 = this.getPosZ();
        super.recalculateSize();
        this.setPosition(d, d2, d3);
    }

    private boolean func_213814_A() {
        return !this.hasMarker() && !this.hasNoGravity();
    }

    @Override
    public boolean isServerWorld() {
        return super.isServerWorld() && this.func_213814_A();
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(STATUS, (byte)0);
        this.dataManager.register(HEAD_ROTATION, DEFAULT_HEAD_ROTATION);
        this.dataManager.register(BODY_ROTATION, DEFAULT_BODY_ROTATION);
        this.dataManager.register(LEFT_ARM_ROTATION, DEFAULT_LEFTARM_ROTATION);
        this.dataManager.register(RIGHT_ARM_ROTATION, DEFAULT_RIGHTARM_ROTATION);
        this.dataManager.register(LEFT_LEG_ROTATION, DEFAULT_LEFTLEG_ROTATION);
        this.dataManager.register(RIGHT_LEG_ROTATION, DEFAULT_RIGHTLEG_ROTATION);
    }

    @Override
    public Iterable<ItemStack> getHeldEquipment() {
        return this.handItems;
    }

    @Override
    public Iterable<ItemStack> getArmorInventoryList() {
        return this.armorItems;
    }

    @Override
    public ItemStack getItemStackFromSlot(EquipmentSlotType equipmentSlotType) {
        switch (1.$SwitchMap$net$minecraft$inventory$EquipmentSlotType$Group[equipmentSlotType.getSlotType().ordinal()]) {
            case 1: {
                return this.handItems.get(equipmentSlotType.getIndex());
            }
            case 2: {
                return this.armorItems.get(equipmentSlotType.getIndex());
            }
        }
        return ItemStack.EMPTY;
    }

    @Override
    public void setItemStackToSlot(EquipmentSlotType equipmentSlotType, ItemStack itemStack) {
        switch (1.$SwitchMap$net$minecraft$inventory$EquipmentSlotType$Group[equipmentSlotType.getSlotType().ordinal()]) {
            case 1: {
                this.playEquipSound(itemStack);
                this.handItems.set(equipmentSlotType.getIndex(), itemStack);
                break;
            }
            case 2: {
                this.playEquipSound(itemStack);
                this.armorItems.set(equipmentSlotType.getIndex(), itemStack);
            }
        }
    }

    @Override
    public boolean replaceItemInInventory(int n, ItemStack itemStack) {
        EquipmentSlotType equipmentSlotType;
        if (n == 98) {
            equipmentSlotType = EquipmentSlotType.MAINHAND;
        } else if (n == 99) {
            equipmentSlotType = EquipmentSlotType.OFFHAND;
        } else if (n == 100 + EquipmentSlotType.HEAD.getIndex()) {
            equipmentSlotType = EquipmentSlotType.HEAD;
        } else if (n == 100 + EquipmentSlotType.CHEST.getIndex()) {
            equipmentSlotType = EquipmentSlotType.CHEST;
        } else if (n == 100 + EquipmentSlotType.LEGS.getIndex()) {
            equipmentSlotType = EquipmentSlotType.LEGS;
        } else {
            if (n != 100 + EquipmentSlotType.FEET.getIndex()) {
                return true;
            }
            equipmentSlotType = EquipmentSlotType.FEET;
        }
        if (!itemStack.isEmpty() && !MobEntity.isItemStackInSlot(equipmentSlotType, itemStack) && equipmentSlotType != EquipmentSlotType.HEAD) {
            return true;
        }
        this.setItemStackToSlot(equipmentSlotType, itemStack);
        return false;
    }

    @Override
    public boolean canPickUpItem(ItemStack itemStack) {
        EquipmentSlotType equipmentSlotType = MobEntity.getSlotForItemStack(itemStack);
        return this.getItemStackFromSlot(equipmentSlotType).isEmpty() && !this.isDisabled(equipmentSlotType);
    }

    @Override
    public void writeAdditional(CompoundNBT compoundNBT) {
        super.writeAdditional(compoundNBT);
        ListNBT listNBT = new ListNBT();
        for (ItemStack object : this.armorItems) {
            CompoundNBT compoundNBT2 = new CompoundNBT();
            if (!object.isEmpty()) {
                object.write(compoundNBT2);
            }
            listNBT.add(compoundNBT2);
        }
        compoundNBT.put("ArmorItems", listNBT);
        ListNBT listNBT2 = new ListNBT();
        for (ItemStack itemStack : this.handItems) {
            CompoundNBT compoundNBT3 = new CompoundNBT();
            if (!itemStack.isEmpty()) {
                itemStack.write(compoundNBT3);
            }
            listNBT2.add(compoundNBT3);
        }
        compoundNBT.put("HandItems", listNBT2);
        compoundNBT.putBoolean("Invisible", this.isInvisible());
        compoundNBT.putBoolean("Small", this.isSmall());
        compoundNBT.putBoolean("ShowArms", this.getShowArms());
        compoundNBT.putInt("DisabledSlots", this.disabledSlots);
        compoundNBT.putBoolean("NoBasePlate", this.hasNoBasePlate());
        if (this.hasMarker()) {
            compoundNBT.putBoolean("Marker", this.hasMarker());
        }
        compoundNBT.put("Pose", this.writePose());
    }

    @Override
    public void readAdditional(CompoundNBT compoundNBT) {
        int n;
        INBT iNBT;
        super.readAdditional(compoundNBT);
        if (compoundNBT.contains("ArmorItems", 0)) {
            iNBT = compoundNBT.getList("ArmorItems", 10);
            for (n = 0; n < this.armorItems.size(); ++n) {
                this.armorItems.set(n, ItemStack.read(iNBT.getCompound(n)));
            }
        }
        if (compoundNBT.contains("HandItems", 0)) {
            iNBT = compoundNBT.getList("HandItems", 10);
            for (n = 0; n < this.handItems.size(); ++n) {
                this.handItems.set(n, ItemStack.read(iNBT.getCompound(n)));
            }
        }
        this.setInvisible(compoundNBT.getBoolean("Invisible"));
        this.setSmall(compoundNBT.getBoolean("Small"));
        this.setShowArms(compoundNBT.getBoolean("ShowArms"));
        this.disabledSlots = compoundNBT.getInt("DisabledSlots");
        this.setNoBasePlate(compoundNBT.getBoolean("NoBasePlate"));
        this.setMarker(compoundNBT.getBoolean("Marker"));
        this.noClip = !this.func_213814_A();
        iNBT = compoundNBT.getCompound("Pose");
        this.readPose((CompoundNBT)iNBT);
    }

    private void readPose(CompoundNBT compoundNBT) {
        ListNBT listNBT = compoundNBT.getList("Head", 5);
        this.setHeadRotation(listNBT.isEmpty() ? DEFAULT_HEAD_ROTATION : new Rotations(listNBT));
        ListNBT listNBT2 = compoundNBT.getList("Body", 5);
        this.setBodyRotation(listNBT2.isEmpty() ? DEFAULT_BODY_ROTATION : new Rotations(listNBT2));
        ListNBT listNBT3 = compoundNBT.getList("LeftArm", 5);
        this.setLeftArmRotation(listNBT3.isEmpty() ? DEFAULT_LEFTARM_ROTATION : new Rotations(listNBT3));
        ListNBT listNBT4 = compoundNBT.getList("RightArm", 5);
        this.setRightArmRotation(listNBT4.isEmpty() ? DEFAULT_RIGHTARM_ROTATION : new Rotations(listNBT4));
        ListNBT listNBT5 = compoundNBT.getList("LeftLeg", 5);
        this.setLeftLegRotation(listNBT5.isEmpty() ? DEFAULT_LEFTLEG_ROTATION : new Rotations(listNBT5));
        ListNBT listNBT6 = compoundNBT.getList("RightLeg", 5);
        this.setRightLegRotation(listNBT6.isEmpty() ? DEFAULT_RIGHTLEG_ROTATION : new Rotations(listNBT6));
    }

    private CompoundNBT writePose() {
        CompoundNBT compoundNBT = new CompoundNBT();
        if (!DEFAULT_HEAD_ROTATION.equals(this.headRotation)) {
            compoundNBT.put("Head", this.headRotation.writeToNBT());
        }
        if (!DEFAULT_BODY_ROTATION.equals(this.bodyRotation)) {
            compoundNBT.put("Body", this.bodyRotation.writeToNBT());
        }
        if (!DEFAULT_LEFTARM_ROTATION.equals(this.leftArmRotation)) {
            compoundNBT.put("LeftArm", this.leftArmRotation.writeToNBT());
        }
        if (!DEFAULT_RIGHTARM_ROTATION.equals(this.rightArmRotation)) {
            compoundNBT.put("RightArm", this.rightArmRotation.writeToNBT());
        }
        if (!DEFAULT_LEFTLEG_ROTATION.equals(this.leftLegRotation)) {
            compoundNBT.put("LeftLeg", this.leftLegRotation.writeToNBT());
        }
        if (!DEFAULT_RIGHTLEG_ROTATION.equals(this.rightLegRotation)) {
            compoundNBT.put("RightLeg", this.rightLegRotation.writeToNBT());
        }
        return compoundNBT;
    }

    @Override
    public boolean canBePushed() {
        return true;
    }

    @Override
    protected void collideWithEntity(Entity entity2) {
    }

    @Override
    protected void collideWithNearbyEntities() {
        List<Entity> list = this.world.getEntitiesInAABBexcluding(this, this.getBoundingBox(), IS_RIDEABLE_MINECART);
        for (int i = 0; i < list.size(); ++i) {
            Entity entity2 = list.get(i);
            if (!(this.getDistanceSq(entity2) <= 0.2)) continue;
            entity2.applyEntityCollision(this);
        }
    }

    @Override
    public ActionResultType applyPlayerInteraction(PlayerEntity playerEntity, Vector3d vector3d, Hand hand) {
        ItemStack itemStack = playerEntity.getHeldItem(hand);
        if (!this.hasMarker() && itemStack.getItem() != Items.NAME_TAG) {
            if (playerEntity.isSpectator()) {
                return ActionResultType.SUCCESS;
            }
            if (playerEntity.world.isRemote) {
                return ActionResultType.CONSUME;
            }
            EquipmentSlotType equipmentSlotType = MobEntity.getSlotForItemStack(itemStack);
            if (itemStack.isEmpty()) {
                EquipmentSlotType equipmentSlotType2;
                EquipmentSlotType equipmentSlotType3 = this.getClickedSlot(vector3d);
                EquipmentSlotType equipmentSlotType4 = equipmentSlotType2 = this.isDisabled(equipmentSlotType3) ? equipmentSlotType : equipmentSlotType3;
                if (this.hasItemInSlot(equipmentSlotType2) && this.equipOrSwap(playerEntity, equipmentSlotType2, itemStack, hand)) {
                    return ActionResultType.SUCCESS;
                }
            } else {
                if (this.isDisabled(equipmentSlotType)) {
                    return ActionResultType.FAIL;
                }
                if (equipmentSlotType.getSlotType() == EquipmentSlotType.Group.HAND && !this.getShowArms()) {
                    return ActionResultType.FAIL;
                }
                if (this.equipOrSwap(playerEntity, equipmentSlotType, itemStack, hand)) {
                    return ActionResultType.SUCCESS;
                }
            }
            return ActionResultType.PASS;
        }
        return ActionResultType.PASS;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private EquipmentSlotType getClickedSlot(Vector3d vector3d) {
        EquipmentSlotType equipmentSlotType = EquipmentSlotType.MAINHAND;
        boolean bl = this.isSmall();
        double d = bl ? vector3d.y * 2.0 : vector3d.y;
        EquipmentSlotType equipmentSlotType2 = EquipmentSlotType.FEET;
        if (d >= 0.1) {
            double d2 = bl ? 0.8 : 0.45;
            if (d < 0.1 + d2 && this.hasItemInSlot(equipmentSlotType2)) {
                return EquipmentSlotType.FEET;
            }
        }
        double d3 = bl ? 0.3 : 0.0;
        if (d >= 0.9 + d3) {
            double d4 = bl ? 1.0 : 0.7;
            if (d < 0.9 + d4 && this.hasItemInSlot(EquipmentSlotType.CHEST)) {
                return EquipmentSlotType.CHEST;
            }
        }
        if (d >= 0.4) {
            double d5 = bl ? 1.0 : 0.8;
            if (d < 0.4 + d5 && this.hasItemInSlot(EquipmentSlotType.LEGS)) {
                return EquipmentSlotType.LEGS;
            }
        }
        if (d >= 1.6 && this.hasItemInSlot(EquipmentSlotType.HEAD)) {
            return EquipmentSlotType.HEAD;
        }
        if (this.hasItemInSlot(EquipmentSlotType.MAINHAND)) return equipmentSlotType;
        if (!this.hasItemInSlot(EquipmentSlotType.OFFHAND)) return equipmentSlotType;
        return EquipmentSlotType.OFFHAND;
    }

    private boolean isDisabled(EquipmentSlotType equipmentSlotType) {
        return (this.disabledSlots & 1 << equipmentSlotType.getSlotIndex()) != 0 || equipmentSlotType.getSlotType() == EquipmentSlotType.Group.HAND && !this.getShowArms();
    }

    private boolean equipOrSwap(PlayerEntity playerEntity, EquipmentSlotType equipmentSlotType, ItemStack itemStack, Hand hand) {
        ItemStack itemStack2 = this.getItemStackFromSlot(equipmentSlotType);
        if (!itemStack2.isEmpty() && (this.disabledSlots & 1 << equipmentSlotType.getSlotIndex() + 8) != 0) {
            return true;
        }
        if (itemStack2.isEmpty() && (this.disabledSlots & 1 << equipmentSlotType.getSlotIndex() + 16) != 0) {
            return true;
        }
        if (playerEntity.abilities.isCreativeMode && itemStack2.isEmpty() && !itemStack.isEmpty()) {
            ItemStack itemStack3 = itemStack.copy();
            itemStack3.setCount(1);
            this.setItemStackToSlot(equipmentSlotType, itemStack3);
            return false;
        }
        if (!itemStack.isEmpty() && itemStack.getCount() > 1) {
            if (!itemStack2.isEmpty()) {
                return true;
            }
            ItemStack itemStack4 = itemStack.copy();
            itemStack4.setCount(1);
            this.setItemStackToSlot(equipmentSlotType, itemStack4);
            itemStack.shrink(1);
            return false;
        }
        this.setItemStackToSlot(equipmentSlotType, itemStack);
        playerEntity.setHeldItem(hand, itemStack2);
        return false;
    }

    @Override
    public boolean attackEntityFrom(DamageSource damageSource, float f) {
        if (!this.world.isRemote && !this.removed) {
            if (DamageSource.OUT_OF_WORLD.equals(damageSource)) {
                this.remove();
                return true;
            }
            if (!(this.isInvulnerableTo(damageSource) || this.canInteract || this.hasMarker())) {
                if (damageSource.isExplosion()) {
                    this.func_213816_g(damageSource);
                    this.remove();
                    return true;
                }
                if (DamageSource.IN_FIRE.equals(damageSource)) {
                    if (this.isBurning()) {
                        this.damageArmorStand(damageSource, 0.15f);
                    } else {
                        this.setFire(5);
                    }
                    return true;
                }
                if (DamageSource.ON_FIRE.equals(damageSource) && this.getHealth() > 0.5f) {
                    this.damageArmorStand(damageSource, 4.0f);
                    return true;
                }
                boolean bl = damageSource.getImmediateSource() instanceof AbstractArrowEntity;
                boolean bl2 = bl && ((AbstractArrowEntity)damageSource.getImmediateSource()).getPierceLevel() > 0;
                boolean bl3 = "player".equals(damageSource.getDamageType());
                if (!bl3 && !bl) {
                    return true;
                }
                if (damageSource.getTrueSource() instanceof PlayerEntity && !((PlayerEntity)damageSource.getTrueSource()).abilities.allowEdit) {
                    return true;
                }
                if (damageSource.isCreativePlayer()) {
                    this.playBrokenSound();
                    this.playParticles();
                    this.remove();
                    return bl2;
                }
                long l = this.world.getGameTime();
                if (l - this.punchCooldown > 5L && !bl) {
                    this.world.setEntityState(this, (byte)32);
                    this.punchCooldown = l;
                } else {
                    this.breakArmorStand(damageSource);
                    this.playParticles();
                    this.remove();
                }
                return false;
            }
            return true;
        }
        return true;
    }

    @Override
    public void handleStatusUpdate(byte by) {
        if (by == 32) {
            if (this.world.isRemote) {
                this.world.playSound(this.getPosX(), this.getPosY(), this.getPosZ(), SoundEvents.ENTITY_ARMOR_STAND_HIT, this.getSoundCategory(), 0.3f, 1.0f, true);
                this.punchCooldown = this.world.getGameTime();
            }
        } else {
            super.handleStatusUpdate(by);
        }
    }

    @Override
    public boolean isInRangeToRenderDist(double d) {
        double d2 = this.getBoundingBox().getAverageEdgeLength() * 4.0;
        if (Double.isNaN(d2) || d2 == 0.0) {
            d2 = 4.0;
        }
        return d < (d2 *= 64.0) * d2;
    }

    private void playParticles() {
        if (this.world instanceof ServerWorld) {
            ((ServerWorld)this.world).spawnParticle(new BlockParticleData(ParticleTypes.BLOCK, Blocks.OAK_PLANKS.getDefaultState()), this.getPosX(), this.getPosYHeight(0.6666666666666666), this.getPosZ(), 10, this.getWidth() / 4.0f, this.getHeight() / 4.0f, this.getWidth() / 4.0f, 0.05);
        }
    }

    private void damageArmorStand(DamageSource damageSource, float f) {
        float f2 = this.getHealth();
        if ((f2 -= f) <= 0.5f) {
            this.func_213816_g(damageSource);
            this.remove();
        } else {
            this.setHealth(f2);
        }
    }

    private void breakArmorStand(DamageSource damageSource) {
        Block.spawnAsEntity(this.world, this.getPosition(), new ItemStack(Items.ARMOR_STAND));
        this.func_213816_g(damageSource);
    }

    private void func_213816_g(DamageSource damageSource) {
        ItemStack itemStack;
        int n;
        this.playBrokenSound();
        this.spawnDrops(damageSource);
        for (n = 0; n < this.handItems.size(); ++n) {
            itemStack = this.handItems.get(n);
            if (itemStack.isEmpty()) continue;
            Block.spawnAsEntity(this.world, this.getPosition().up(), itemStack);
            this.handItems.set(n, ItemStack.EMPTY);
        }
        for (n = 0; n < this.armorItems.size(); ++n) {
            itemStack = this.armorItems.get(n);
            if (itemStack.isEmpty()) continue;
            Block.spawnAsEntity(this.world, this.getPosition().up(), itemStack);
            this.armorItems.set(n, ItemStack.EMPTY);
        }
    }

    private void playBrokenSound() {
        this.world.playSound(null, this.getPosX(), this.getPosY(), this.getPosZ(), SoundEvents.ENTITY_ARMOR_STAND_BREAK, this.getSoundCategory(), 1.0f, 1.0f);
    }

    @Override
    protected float updateDistance(float f, float f2) {
        this.prevRenderYawOffset = this.prevRotationYaw;
        this.renderYawOffset = this.rotationYaw;
        return 0.0f;
    }

    @Override
    protected float getStandingEyeHeight(Pose pose, EntitySize entitySize) {
        return entitySize.height * (this.isChild() ? 0.5f : 0.9f);
    }

    @Override
    public double getYOffset() {
        return this.hasMarker() ? 0.0 : (double)0.1f;
    }

    @Override
    public void travel(Vector3d vector3d) {
        if (this.func_213814_A()) {
            super.travel(vector3d);
        }
    }

    @Override
    public void setRenderYawOffset(float f) {
        this.prevRenderYawOffset = this.prevRotationYaw = f;
        this.prevRotationYawHead = this.rotationYawHead = f;
    }

    @Override
    public void setRotationYawHead(float f) {
        this.prevRenderYawOffset = this.prevRotationYaw = f;
        this.prevRotationYawHead = this.rotationYawHead = f;
    }

    @Override
    public void tick() {
        Rotations rotations;
        Rotations rotations2;
        Rotations rotations3;
        Rotations rotations4;
        Rotations rotations5;
        super.tick();
        Rotations rotations6 = this.dataManager.get(HEAD_ROTATION);
        if (!this.headRotation.equals(rotations6)) {
            this.setHeadRotation(rotations6);
        }
        if (!this.bodyRotation.equals(rotations5 = this.dataManager.get(BODY_ROTATION))) {
            this.setBodyRotation(rotations5);
        }
        if (!this.leftArmRotation.equals(rotations4 = this.dataManager.get(LEFT_ARM_ROTATION))) {
            this.setLeftArmRotation(rotations4);
        }
        if (!this.rightArmRotation.equals(rotations3 = this.dataManager.get(RIGHT_ARM_ROTATION))) {
            this.setRightArmRotation(rotations3);
        }
        if (!this.leftLegRotation.equals(rotations2 = this.dataManager.get(LEFT_LEG_ROTATION))) {
            this.setLeftLegRotation(rotations2);
        }
        if (!this.rightLegRotation.equals(rotations = this.dataManager.get(RIGHT_LEG_ROTATION))) {
            this.setRightLegRotation(rotations);
        }
    }

    @Override
    protected void updatePotionMetadata() {
        this.setInvisible(this.canInteract);
    }

    @Override
    public void setInvisible(boolean bl) {
        this.canInteract = bl;
        super.setInvisible(bl);
    }

    @Override
    public boolean isChild() {
        return this.isSmall();
    }

    @Override
    public void onKillCommand() {
        this.remove();
    }

    @Override
    public boolean isImmuneToExplosions() {
        return this.isInvisible();
    }

    @Override
    public PushReaction getPushReaction() {
        return this.hasMarker() ? PushReaction.IGNORE : super.getPushReaction();
    }

    private void setSmall(boolean bl) {
        this.dataManager.set(STATUS, this.setBit(this.dataManager.get(STATUS), 1, bl));
    }

    public boolean isSmall() {
        return (this.dataManager.get(STATUS) & 1) != 0;
    }

    private void setShowArms(boolean bl) {
        this.dataManager.set(STATUS, this.setBit(this.dataManager.get(STATUS), 4, bl));
    }

    public boolean getShowArms() {
        return (this.dataManager.get(STATUS) & 4) != 0;
    }

    private void setNoBasePlate(boolean bl) {
        this.dataManager.set(STATUS, this.setBit(this.dataManager.get(STATUS), 8, bl));
    }

    public boolean hasNoBasePlate() {
        return (this.dataManager.get(STATUS) & 8) != 0;
    }

    private void setMarker(boolean bl) {
        this.dataManager.set(STATUS, this.setBit(this.dataManager.get(STATUS), 16, bl));
    }

    public boolean hasMarker() {
        return (this.dataManager.get(STATUS) & 0x10) != 0;
    }

    private byte setBit(byte by, int n, boolean bl) {
        by = bl ? (byte)(by | n) : (byte)(by & ~n);
        return by;
    }

    public void setHeadRotation(Rotations rotations) {
        this.headRotation = rotations;
        this.dataManager.set(HEAD_ROTATION, rotations);
    }

    public void setBodyRotation(Rotations rotations) {
        this.bodyRotation = rotations;
        this.dataManager.set(BODY_ROTATION, rotations);
    }

    public void setLeftArmRotation(Rotations rotations) {
        this.leftArmRotation = rotations;
        this.dataManager.set(LEFT_ARM_ROTATION, rotations);
    }

    public void setRightArmRotation(Rotations rotations) {
        this.rightArmRotation = rotations;
        this.dataManager.set(RIGHT_ARM_ROTATION, rotations);
    }

    public void setLeftLegRotation(Rotations rotations) {
        this.leftLegRotation = rotations;
        this.dataManager.set(LEFT_LEG_ROTATION, rotations);
    }

    public void setRightLegRotation(Rotations rotations) {
        this.rightLegRotation = rotations;
        this.dataManager.set(RIGHT_LEG_ROTATION, rotations);
    }

    public Rotations getHeadRotation() {
        return this.headRotation;
    }

    public Rotations getBodyRotation() {
        return this.bodyRotation;
    }

    public Rotations getLeftArmRotation() {
        return this.leftArmRotation;
    }

    public Rotations getRightArmRotation() {
        return this.rightArmRotation;
    }

    public Rotations getLeftLegRotation() {
        return this.leftLegRotation;
    }

    public Rotations getRightLegRotation() {
        return this.rightLegRotation;
    }

    @Override
    public boolean canBeCollidedWith() {
        return super.canBeCollidedWith() && !this.hasMarker();
    }

    @Override
    public boolean hitByEntity(Entity entity2) {
        return entity2 instanceof PlayerEntity && !this.world.isBlockModifiable((PlayerEntity)entity2, this.getPosition());
    }

    @Override
    public HandSide getPrimaryHand() {
        return HandSide.RIGHT;
    }

    @Override
    protected SoundEvent getFallSound(int n) {
        return SoundEvents.ENTITY_ARMOR_STAND_FALL;
    }

    @Override
    @Nullable
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.ENTITY_ARMOR_STAND_HIT;
    }

    @Override
    @Nullable
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_ARMOR_STAND_BREAK;
    }

    @Override
    public void func_241841_a(ServerWorld serverWorld, LightningBoltEntity lightningBoltEntity) {
    }

    @Override
    public boolean canBeHitWithPotion() {
        return true;
    }

    @Override
    public void notifyDataManagerChange(DataParameter<?> dataParameter) {
        if (STATUS.equals(dataParameter)) {
            this.recalculateSize();
            this.preventEntitySpawning = !this.hasMarker();
        }
        super.notifyDataManagerChange(dataParameter);
    }

    @Override
    public boolean attackable() {
        return true;
    }

    @Override
    public EntitySize getSize(Pose pose) {
        return this.func_242330_s(this.hasMarker());
    }

    private EntitySize func_242330_s(boolean bl) {
        if (bl) {
            return field_242328_bp;
        }
        return this.isChild() ? field_242329_bq : this.getType().getSize();
    }

    @Override
    public Vector3d func_241842_k(float f) {
        if (this.hasMarker()) {
            AxisAlignedBB axisAlignedBB = this.func_242330_s(true).func_242286_a(this.getPositionVec());
            BlockPos blockPos = this.getPosition();
            int n = Integer.MIN_VALUE;
            for (BlockPos blockPos2 : BlockPos.getAllInBoxMutable(new BlockPos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ), new BlockPos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ))) {
                int n2 = Math.max(this.world.getLightFor(LightType.BLOCK, blockPos2), this.world.getLightFor(LightType.SKY, blockPos2));
                if (n2 == 15) {
                    return Vector3d.copyCentered(blockPos2);
                }
                if (n2 <= n) continue;
                n = n2;
                blockPos = blockPos2.toImmutable();
            }
            return Vector3d.copyCentered(blockPos);
        }
        return super.func_241842_k(f);
    }

    private static boolean lambda$static$0(Entity entity2) {
        return entity2 instanceof AbstractMinecartEntity && ((AbstractMinecartEntity)entity2).getMinecartType() == AbstractMinecartEntity.Type.RIDEABLE;
    }
}

