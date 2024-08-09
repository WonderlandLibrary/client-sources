/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.passive.horse;

import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.passive.horse.AbstractHorseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

public abstract class AbstractChestedHorseEntity
extends AbstractHorseEntity {
    private static final DataParameter<Boolean> DATA_ID_CHEST = EntityDataManager.createKey(AbstractChestedHorseEntity.class, DataSerializers.BOOLEAN);

    protected AbstractChestedHorseEntity(EntityType<? extends AbstractChestedHorseEntity> entityType, World world) {
        super((EntityType<? extends AbstractHorseEntity>)entityType, world);
        this.canGallop = false;
    }

    @Override
    protected void func_230273_eI_() {
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(this.getModifiedMaxHealth());
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(DATA_ID_CHEST, false);
    }

    public static AttributeModifierMap.MutableAttribute func_234234_eJ_() {
        return AbstractChestedHorseEntity.func_234237_fg_().createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.175f).createMutableAttribute(Attributes.HORSE_JUMP_STRENGTH, 0.5);
    }

    public boolean hasChest() {
        return this.dataManager.get(DATA_ID_CHEST);
    }

    public void setChested(boolean bl) {
        this.dataManager.set(DATA_ID_CHEST, bl);
    }

    @Override
    protected int getInventorySize() {
        return this.hasChest() ? 17 : super.getInventorySize();
    }

    @Override
    public double getMountedYOffset() {
        return super.getMountedYOffset() - 0.25;
    }

    @Override
    protected void dropInventory() {
        super.dropInventory();
        if (this.hasChest()) {
            if (!this.world.isRemote) {
                this.entityDropItem(Blocks.CHEST);
            }
            this.setChested(true);
        }
    }

    @Override
    public void writeAdditional(CompoundNBT compoundNBT) {
        super.writeAdditional(compoundNBT);
        compoundNBT.putBoolean("ChestedHorse", this.hasChest());
        if (this.hasChest()) {
            ListNBT listNBT = new ListNBT();
            for (int i = 2; i < this.horseChest.getSizeInventory(); ++i) {
                ItemStack itemStack = this.horseChest.getStackInSlot(i);
                if (itemStack.isEmpty()) continue;
                CompoundNBT compoundNBT2 = new CompoundNBT();
                compoundNBT2.putByte("Slot", (byte)i);
                itemStack.write(compoundNBT2);
                listNBT.add(compoundNBT2);
            }
            compoundNBT.put("Items", listNBT);
        }
    }

    @Override
    public void readAdditional(CompoundNBT compoundNBT) {
        super.readAdditional(compoundNBT);
        this.setChested(compoundNBT.getBoolean("ChestedHorse"));
        if (this.hasChest()) {
            ListNBT listNBT = compoundNBT.getList("Items", 10);
            this.initHorseChest();
            for (int i = 0; i < listNBT.size(); ++i) {
                CompoundNBT compoundNBT2 = listNBT.getCompound(i);
                int n = compoundNBT2.getByte("Slot") & 0xFF;
                if (n < 2 || n >= this.horseChest.getSizeInventory()) continue;
                this.horseChest.setInventorySlotContents(n, ItemStack.read(compoundNBT2));
            }
        }
        this.func_230275_fc_();
    }

    @Override
    public boolean replaceItemInInventory(int n, ItemStack itemStack) {
        if (n == 499) {
            if (this.hasChest() && itemStack.isEmpty()) {
                this.setChested(true);
                this.initHorseChest();
                return false;
            }
            if (!this.hasChest() && itemStack.getItem() == Blocks.CHEST.asItem()) {
                this.setChested(false);
                this.initHorseChest();
                return false;
            }
        }
        return super.replaceItemInInventory(n, itemStack);
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
            if (this.isBreedingItem(itemStack)) {
                return this.func_241395_b_(playerEntity, itemStack);
            }
            if (!this.isTame()) {
                this.makeMad();
                return ActionResultType.func_233537_a_(this.world.isRemote);
            }
            if (!this.hasChest() && itemStack.getItem() == Blocks.CHEST.asItem()) {
                this.setChested(false);
                this.playChestEquipSound();
                if (!playerEntity.abilities.isCreativeMode) {
                    itemStack.shrink(1);
                }
                this.initHorseChest();
                return ActionResultType.func_233537_a_(this.world.isRemote);
            }
            if (!this.isChild() && !this.isHorseSaddled() && itemStack.getItem() == Items.SADDLE) {
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

    protected void playChestEquipSound() {
        this.playSound(SoundEvents.ENTITY_DONKEY_CHEST, 1.0f, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f + 1.0f);
    }

    public int getInventoryColumns() {
        return 0;
    }
}

