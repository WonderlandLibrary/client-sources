/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.passive.horse;

import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.block.SoundType;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.horse.AbstractHorseEntity;
import net.minecraft.entity.passive.horse.CoatColors;
import net.minecraft.entity.passive.horse.CoatTypes;
import net.minecraft.entity.passive.horse.DonkeyEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.HorseArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.Util;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class HorseEntity
extends AbstractHorseEntity {
    private static final UUID ARMOR_MODIFIER_UUID = UUID.fromString("556E1665-8B10-40C8-8F9D-CF9B1667F295");
    private static final DataParameter<Integer> HORSE_VARIANT = EntityDataManager.createKey(HorseEntity.class, DataSerializers.VARINT);

    public HorseEntity(EntityType<? extends HorseEntity> entityType, World world) {
        super((EntityType<? extends AbstractHorseEntity>)entityType, world);
    }

    @Override
    protected void func_230273_eI_() {
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(this.getModifiedMaxHealth());
        this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(this.getModifiedMovementSpeed());
        this.getAttribute(Attributes.HORSE_JUMP_STRENGTH).setBaseValue(this.getModifiedJumpStrength());
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(HORSE_VARIANT, 0);
    }

    @Override
    public void writeAdditional(CompoundNBT compoundNBT) {
        super.writeAdditional(compoundNBT);
        compoundNBT.putInt("Variant", this.func_234241_eS_());
        if (!this.horseChest.getStackInSlot(1).isEmpty()) {
            compoundNBT.put("ArmorItem", this.horseChest.getStackInSlot(1).write(new CompoundNBT()));
        }
    }

    public ItemStack func_213803_dV() {
        return this.getItemStackFromSlot(EquipmentSlotType.CHEST);
    }

    private void func_213805_k(ItemStack itemStack) {
        this.setItemStackToSlot(EquipmentSlotType.CHEST, itemStack);
        this.setDropChance(EquipmentSlotType.CHEST, 0.0f);
    }

    @Override
    public void readAdditional(CompoundNBT compoundNBT) {
        ItemStack itemStack;
        super.readAdditional(compoundNBT);
        this.func_234242_w_(compoundNBT.getInt("Variant"));
        if (compoundNBT.contains("ArmorItem", 1) && !(itemStack = ItemStack.read(compoundNBT.getCompound("ArmorItem"))).isEmpty() && this.isArmor(itemStack)) {
            this.horseChest.setInventorySlotContents(1, itemStack);
        }
        this.func_230275_fc_();
    }

    private void func_234242_w_(int n) {
        this.dataManager.set(HORSE_VARIANT, n);
    }

    private int func_234241_eS_() {
        return this.dataManager.get(HORSE_VARIANT);
    }

    private void func_234238_a_(CoatColors coatColors, CoatTypes coatTypes) {
        this.func_234242_w_(coatColors.getId() & 0xFF | coatTypes.getId() << 8 & 0xFF00);
    }

    public CoatColors func_234239_eK_() {
        return CoatColors.func_234254_a_(this.func_234241_eS_() & 0xFF);
    }

    public CoatTypes func_234240_eM_() {
        return CoatTypes.func_234248_a_((this.func_234241_eS_() & 0xFF00) >> 8);
    }

    @Override
    protected void func_230275_fc_() {
        if (!this.world.isRemote) {
            super.func_230275_fc_();
            this.func_213804_l(this.horseChest.getStackInSlot(1));
            this.setDropChance(EquipmentSlotType.CHEST, 0.0f);
        }
    }

    private void func_213804_l(ItemStack itemStack) {
        this.func_213805_k(itemStack);
        if (!this.world.isRemote) {
            int n;
            this.getAttribute(Attributes.ARMOR).removeModifier(ARMOR_MODIFIER_UUID);
            if (this.isArmor(itemStack) && (n = ((HorseArmorItem)itemStack.getItem()).getArmorValue()) != 0) {
                this.getAttribute(Attributes.ARMOR).applyNonPersistentModifier(new AttributeModifier(ARMOR_MODIFIER_UUID, "Horse armor bonus", (double)n, AttributeModifier.Operation.ADDITION));
            }
        }
    }

    @Override
    public void onInventoryChanged(IInventory iInventory) {
        ItemStack itemStack = this.func_213803_dV();
        super.onInventoryChanged(iInventory);
        ItemStack itemStack2 = this.func_213803_dV();
        if (this.ticksExisted > 20 && this.isArmor(itemStack2) && itemStack != itemStack2) {
            this.playSound(SoundEvents.ENTITY_HORSE_ARMOR, 0.5f, 1.0f);
        }
    }

    @Override
    protected void playGallopSound(SoundType soundType) {
        super.playGallopSound(soundType);
        if (this.rand.nextInt(10) == 0) {
            this.playSound(SoundEvents.ENTITY_HORSE_BREATHE, soundType.getVolume() * 0.6f, soundType.getPitch());
        }
    }

    @Override
    protected SoundEvent getAmbientSound() {
        super.getAmbientSound();
        return SoundEvents.ENTITY_HORSE_AMBIENT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        super.getDeathSound();
        return SoundEvents.ENTITY_HORSE_DEATH;
    }

    @Override
    @Nullable
    protected SoundEvent func_230274_fe_() {
        return SoundEvents.ENTITY_HORSE_EAT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        super.getHurtSound(damageSource);
        return SoundEvents.ENTITY_HORSE_HURT;
    }

    @Override
    protected SoundEvent getAngrySound() {
        super.getAngrySound();
        return SoundEvents.ENTITY_HORSE_ANGRY;
    }

    @Override
    public ActionResultType func_230254_b_(PlayerEntity playerEntity, Hand hand) {
        ItemStack itemStack = playerEntity.getHeldItem(hand);
        if (!this.isChild()) {
            if (this.isTame() && playerEntity.isSecondaryUseActive()) {
                this.openGUI(playerEntity);
                return ActionResultType.func_233537_a_(this.world.isRemote);
            }
            if (this.isBeingRidden()) {
                return super.func_230254_b_(playerEntity, hand);
            }
        }
        if (!itemStack.isEmpty()) {
            boolean bl;
            if (this.isBreedingItem(itemStack)) {
                return this.func_241395_b_(playerEntity, itemStack);
            }
            ActionResultType actionResultType = itemStack.interactWithEntity(playerEntity, this, hand);
            if (actionResultType.isSuccessOrConsume()) {
                return actionResultType;
            }
            if (!this.isTame()) {
                this.makeMad();
                return ActionResultType.func_233537_a_(this.world.isRemote);
            }
            boolean bl2 = bl = !this.isChild() && !this.isHorseSaddled() && itemStack.getItem() == Items.SADDLE;
            if (this.isArmor(itemStack) || bl) {
                this.openGUI(playerEntity);
                return ActionResultType.func_233537_a_(this.world.isRemote);
            }
        }
        if (this.isChild()) {
            return super.func_230254_b_(playerEntity, hand);
        }
        this.mountTo(playerEntity);
        return ActionResultType.func_233537_a_(this.world.isRemote);
    }

    @Override
    public boolean canMateWith(AnimalEntity animalEntity) {
        if (animalEntity == this) {
            return true;
        }
        if (!(animalEntity instanceof DonkeyEntity) && !(animalEntity instanceof HorseEntity)) {
            return true;
        }
        return this.canMate() && ((AbstractHorseEntity)animalEntity).canMate();
    }

    @Override
    public AgeableEntity func_241840_a(ServerWorld serverWorld, AgeableEntity ageableEntity) {
        AbstractHorseEntity abstractHorseEntity;
        if (ageableEntity instanceof DonkeyEntity) {
            abstractHorseEntity = EntityType.MULE.create(serverWorld);
        } else {
            HorseEntity horseEntity = (HorseEntity)ageableEntity;
            abstractHorseEntity = EntityType.HORSE.create(serverWorld);
            int n = this.rand.nextInt(9);
            CoatColors coatColors = n < 4 ? this.func_234239_eK_() : (n < 8 ? horseEntity.func_234239_eK_() : Util.getRandomObject(CoatColors.values(), this.rand));
            int n2 = this.rand.nextInt(5);
            CoatTypes coatTypes = n2 < 2 ? this.func_234240_eM_() : (n2 < 4 ? horseEntity.func_234240_eM_() : Util.getRandomObject(CoatTypes.values(), this.rand));
            ((HorseEntity)abstractHorseEntity).func_234238_a_(coatColors, coatTypes);
        }
        this.setOffspringAttributes(ageableEntity, abstractHorseEntity);
        return abstractHorseEntity;
    }

    @Override
    public boolean func_230276_fq_() {
        return false;
    }

    @Override
    public boolean isArmor(ItemStack itemStack) {
        return itemStack.getItem() instanceof HorseArmorItem;
    }

    @Override
    @Nullable
    public ILivingEntityData onInitialSpawn(IServerWorld iServerWorld, DifficultyInstance difficultyInstance, SpawnReason spawnReason, @Nullable ILivingEntityData iLivingEntityData, @Nullable CompoundNBT compoundNBT) {
        CoatColors coatColors;
        if (iLivingEntityData instanceof HorseData) {
            coatColors = ((HorseData)iLivingEntityData).variant;
        } else {
            coatColors = Util.getRandomObject(CoatColors.values(), this.rand);
            iLivingEntityData = new HorseData(coatColors);
        }
        this.func_234238_a_(coatColors, Util.getRandomObject(CoatTypes.values(), this.rand));
        return super.onInitialSpawn(iServerWorld, difficultyInstance, spawnReason, iLivingEntityData, compoundNBT);
    }

    public static class HorseData
    extends AgeableEntity.AgeableData {
        public final CoatColors variant;

        public HorseData(CoatColors coatColors) {
            super(true);
            this.variant = coatColors;
        }
    }
}

