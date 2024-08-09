/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.inventory.container;

import java.util.Map;
import net.minecraft.block.AnvilBlock;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.AbstractRepairContainer;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.IntReferenceHolder;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RepairContainer
extends AbstractRepairContainer {
    private static final Logger LOGGER = LogManager.getLogger();
    private int materialCost;
    private String repairedItemName;
    private final IntReferenceHolder maximumCost = IntReferenceHolder.single();

    public RepairContainer(int n, PlayerInventory playerInventory) {
        this(n, playerInventory, IWorldPosCallable.DUMMY);
    }

    public RepairContainer(int n, PlayerInventory playerInventory, IWorldPosCallable iWorldPosCallable) {
        super(ContainerType.ANVIL, n, playerInventory, iWorldPosCallable);
        this.trackInt(this.maximumCost);
    }

    @Override
    protected boolean func_230302_a_(BlockState blockState) {
        return blockState.isIn(BlockTags.ANVIL);
    }

    @Override
    protected boolean func_230303_b_(PlayerEntity playerEntity, boolean bl) {
        return (playerEntity.abilities.isCreativeMode || playerEntity.experienceLevel >= this.maximumCost.get()) && this.maximumCost.get() > 0;
    }

    @Override
    protected ItemStack func_230301_a_(PlayerEntity playerEntity, ItemStack itemStack) {
        if (!playerEntity.abilities.isCreativeMode) {
            playerEntity.addExperienceLevel(-this.maximumCost.get());
        }
        this.field_234643_d_.setInventorySlotContents(0, ItemStack.EMPTY);
        if (this.materialCost > 0) {
            ItemStack itemStack2 = this.field_234643_d_.getStackInSlot(1);
            if (!itemStack2.isEmpty() && itemStack2.getCount() > this.materialCost) {
                itemStack2.shrink(this.materialCost);
                this.field_234643_d_.setInventorySlotContents(1, itemStack2);
            } else {
                this.field_234643_d_.setInventorySlotContents(1, ItemStack.EMPTY);
            }
        } else {
            this.field_234643_d_.setInventorySlotContents(1, ItemStack.EMPTY);
        }
        this.maximumCost.set(0);
        this.field_234644_e_.consume((arg_0, arg_1) -> RepairContainer.lambda$func_230301_a_$0(playerEntity, arg_0, arg_1));
        return itemStack;
    }

    @Override
    public void updateRepairOutput() {
        ItemStack itemStack = this.field_234643_d_.getStackInSlot(0);
        this.maximumCost.set(1);
        int n = 0;
        int n2 = 0;
        int n3 = 0;
        if (itemStack.isEmpty()) {
            this.field_234642_c_.setInventorySlotContents(0, ItemStack.EMPTY);
            this.maximumCost.set(0);
        } else {
            int n4;
            ItemStack itemStack2 = itemStack.copy();
            ItemStack itemStack3 = this.field_234643_d_.getStackInSlot(1);
            Map<Enchantment, Integer> map = EnchantmentHelper.getEnchantments(itemStack2);
            n2 = n2 + itemStack.getRepairCost() + (itemStack3.isEmpty() ? 0 : itemStack3.getRepairCost());
            this.materialCost = 0;
            if (!itemStack3.isEmpty()) {
                int n5 = n4 = itemStack3.getItem() == Items.ENCHANTED_BOOK && !EnchantedBookItem.getEnchantments(itemStack3).isEmpty() ? 1 : 0;
                if (itemStack2.isDamageable() && itemStack2.getItem().getIsRepairable(itemStack, itemStack3)) {
                    int n6;
                    int n7 = Math.min(itemStack2.getDamage(), itemStack2.getMaxDamage() / 4);
                    if (n7 <= 0) {
                        this.field_234642_c_.setInventorySlotContents(0, ItemStack.EMPTY);
                        this.maximumCost.set(0);
                        return;
                    }
                    for (n6 = 0; n7 > 0 && n6 < itemStack3.getCount(); ++n6) {
                        int n8 = itemStack2.getDamage() - n7;
                        itemStack2.setDamage(n8);
                        ++n;
                        n7 = Math.min(itemStack2.getDamage(), itemStack2.getMaxDamage() / 4);
                    }
                    this.materialCost = n6;
                } else {
                    int n9;
                    int n10;
                    if (!(n4 != 0 || itemStack2.getItem() == itemStack3.getItem() && itemStack2.isDamageable())) {
                        this.field_234642_c_.setInventorySlotContents(0, ItemStack.EMPTY);
                        this.maximumCost.set(0);
                        return;
                    }
                    if (itemStack2.isDamageable() && n4 == 0) {
                        int n11 = itemStack.getMaxDamage() - itemStack.getDamage();
                        n10 = itemStack3.getMaxDamage() - itemStack3.getDamage();
                        n9 = n10 + itemStack2.getMaxDamage() * 12 / 100;
                        int n12 = n11 + n9;
                        int n13 = itemStack2.getMaxDamage() - n12;
                        if (n13 < 0) {
                            n13 = 0;
                        }
                        if (n13 < itemStack2.getDamage()) {
                            itemStack2.setDamage(n13);
                            n += 2;
                        }
                    }
                    Map<Enchantment, Integer> map2 = EnchantmentHelper.getEnchantments(itemStack3);
                    n10 = 0;
                    n9 = 0;
                    for (Enchantment enchantment : map2.keySet()) {
                        int n14;
                        if (enchantment == null) continue;
                        int n15 = map.getOrDefault(enchantment, 0);
                        n14 = n15 == (n14 = map2.get(enchantment).intValue()) ? n14 + 1 : Math.max(n14, n15);
                        boolean bl = enchantment.canApply(itemStack);
                        if (this.field_234645_f_.abilities.isCreativeMode || itemStack.getItem() == Items.ENCHANTED_BOOK) {
                            bl = true;
                        }
                        for (Enchantment enchantment2 : map.keySet()) {
                            if (enchantment2 == enchantment || enchantment.isCompatibleWith(enchantment2)) continue;
                            bl = false;
                            ++n;
                        }
                        if (!bl) {
                            n9 = 1;
                            continue;
                        }
                        n10 = 1;
                        if (n14 > enchantment.getMaxLevel()) {
                            n14 = enchantment.getMaxLevel();
                        }
                        map.put(enchantment, n14);
                        int n16 = 0;
                        switch (1.$SwitchMap$net$minecraft$enchantment$Enchantment$Rarity[enchantment.getRarity().ordinal()]) {
                            case 1: {
                                n16 = 1;
                                break;
                            }
                            case 2: {
                                n16 = 2;
                                break;
                            }
                            case 3: {
                                n16 = 4;
                                break;
                            }
                            case 4: {
                                n16 = 8;
                            }
                        }
                        if (n4 != 0) {
                            n16 = Math.max(1, n16 / 2);
                        }
                        n += n16 * n14;
                        if (itemStack.getCount() <= 1) continue;
                        n = 40;
                    }
                    if (n9 != 0 && n10 == 0) {
                        this.field_234642_c_.setInventorySlotContents(0, ItemStack.EMPTY);
                        this.maximumCost.set(0);
                        return;
                    }
                }
            }
            if (StringUtils.isBlank(this.repairedItemName)) {
                if (itemStack.hasDisplayName()) {
                    n3 = 1;
                    n += n3;
                    itemStack2.clearCustomName();
                }
            } else if (!this.repairedItemName.equals(itemStack.getDisplayName().getString())) {
                n3 = 1;
                n += n3;
                itemStack2.setDisplayName(new StringTextComponent(this.repairedItemName));
            }
            this.maximumCost.set(n2 + n);
            if (n <= 0) {
                itemStack2 = ItemStack.EMPTY;
            }
            if (n3 == n && n3 > 0 && this.maximumCost.get() >= 40) {
                this.maximumCost.set(39);
            }
            if (this.maximumCost.get() >= 40 && !this.field_234645_f_.abilities.isCreativeMode) {
                itemStack2 = ItemStack.EMPTY;
            }
            if (!itemStack2.isEmpty()) {
                n4 = itemStack2.getRepairCost();
                if (!itemStack3.isEmpty() && n4 < itemStack3.getRepairCost()) {
                    n4 = itemStack3.getRepairCost();
                }
                if (n3 != n || n3 == 0) {
                    n4 = RepairContainer.getNewRepairCost(n4);
                }
                itemStack2.setRepairCost(n4);
                EnchantmentHelper.setEnchantments(map, itemStack2);
            }
            this.field_234642_c_.setInventorySlotContents(0, itemStack2);
            this.detectAndSendChanges();
        }
    }

    public static int getNewRepairCost(int n) {
        return n * 2 + 1;
    }

    public void updateItemName(String string) {
        this.repairedItemName = string;
        if (this.getSlot(2).getHasStack()) {
            ItemStack itemStack = this.getSlot(2).getStack();
            if (StringUtils.isBlank(string)) {
                itemStack.clearCustomName();
            } else {
                itemStack.setDisplayName(new StringTextComponent(this.repairedItemName));
            }
        }
        this.updateRepairOutput();
    }

    public int getMaximumCost() {
        return this.maximumCost.get();
    }

    private static void lambda$func_230301_a_$0(PlayerEntity playerEntity, World world, BlockPos blockPos) {
        BlockState blockState = world.getBlockState(blockPos);
        if (!playerEntity.abilities.isCreativeMode && blockState.isIn(BlockTags.ANVIL) && playerEntity.getRNG().nextFloat() < 0.12f) {
            BlockState blockState2 = AnvilBlock.damage(blockState);
            if (blockState2 == null) {
                world.removeBlock(blockPos, true);
                world.playEvent(1029, blockPos, 0);
            } else {
                world.setBlockState(blockPos, blockState2, 1);
                world.playEvent(1030, blockPos, 0);
            }
        } else {
            world.playEvent(1030, blockPos, 0);
        }
    }
}

