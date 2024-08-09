/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.merchant;

import java.util.OptionalInt;
import javax.annotation.Nullable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.MerchantContainer;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MerchantOffer;
import net.minecraft.item.MerchantOffers;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

public interface IMerchant {
    public void setCustomer(@Nullable PlayerEntity var1);

    @Nullable
    public PlayerEntity getCustomer();

    public MerchantOffers getOffers();

    public void setClientSideOffers(@Nullable MerchantOffers var1);

    public void onTrade(MerchantOffer var1);

    public void verifySellingItem(ItemStack var1);

    public World getWorld();

    public int getXp();

    public void setXP(int var1);

    public boolean hasXPBar();

    public SoundEvent getYesSound();

    default public boolean canRestockTrades() {
        return true;
    }

    default public void openMerchantContainer(PlayerEntity playerEntity, ITextComponent iTextComponent, int n) {
        MerchantOffers merchantOffers;
        OptionalInt optionalInt = playerEntity.openContainer(new SimpleNamedContainerProvider(this::lambda$openMerchantContainer$0, iTextComponent));
        if (optionalInt.isPresent() && !(merchantOffers = this.getOffers()).isEmpty()) {
            playerEntity.openMerchantContainer(optionalInt.getAsInt(), merchantOffers, n, this.getXp(), this.hasXPBar(), this.canRestockTrades());
        }
    }

    private Container lambda$openMerchantContainer$0(int n, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return new MerchantContainer(n, playerInventory, this);
    }
}

