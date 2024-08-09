/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.inventory.container;

import net.minecraft.entity.merchant.IMerchant;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.MerchantInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MerchantOffer;
import net.minecraft.stats.Stats;

public class MerchantResultSlot
extends Slot {
    private final MerchantInventory merchantInventory;
    private final PlayerEntity player;
    private int removeCount;
    private final IMerchant merchant;

    public MerchantResultSlot(PlayerEntity playerEntity, IMerchant iMerchant, MerchantInventory merchantInventory, int n, int n2, int n3) {
        super(merchantInventory, n, n2, n3);
        this.player = playerEntity;
        this.merchant = iMerchant;
        this.merchantInventory = merchantInventory;
    }

    @Override
    public boolean isItemValid(ItemStack itemStack) {
        return true;
    }

    @Override
    public ItemStack decrStackSize(int n) {
        if (this.getHasStack()) {
            this.removeCount += Math.min(n, this.getStack().getCount());
        }
        return super.decrStackSize(n);
    }

    @Override
    protected void onCrafting(ItemStack itemStack, int n) {
        this.removeCount += n;
        this.onCrafting(itemStack);
    }

    @Override
    protected void onCrafting(ItemStack itemStack) {
        itemStack.onCrafting(this.player.world, this.player, this.removeCount);
        this.removeCount = 0;
    }

    @Override
    public ItemStack onTake(PlayerEntity playerEntity, ItemStack itemStack) {
        this.onCrafting(itemStack);
        MerchantOffer merchantOffer = this.merchantInventory.func_214025_g();
        if (merchantOffer != null) {
            ItemStack itemStack2;
            ItemStack itemStack3 = this.merchantInventory.getStackInSlot(0);
            if (merchantOffer.doTransaction(itemStack3, itemStack2 = this.merchantInventory.getStackInSlot(1)) || merchantOffer.doTransaction(itemStack2, itemStack3)) {
                this.merchant.onTrade(merchantOffer);
                playerEntity.addStat(Stats.TRADED_WITH_VILLAGER);
                this.merchantInventory.setInventorySlotContents(0, itemStack3);
                this.merchantInventory.setInventorySlotContents(1, itemStack2);
            }
            this.merchant.setXP(this.merchant.getXp() + merchantOffer.getGivenExp());
        }
        return itemStack;
    }
}

