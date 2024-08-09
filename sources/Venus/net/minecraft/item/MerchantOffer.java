/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.item;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.math.MathHelper;

public class MerchantOffer {
    private final ItemStack buyingStackFirst;
    private final ItemStack buyingStackSecond;
    private final ItemStack sellingStack;
    private int uses;
    private final int maxUses;
    private boolean doesRewardEXP = true;
    private int specialPrice;
    private int demand;
    private float priceMultiplier;
    private int givenEXP = 1;

    public MerchantOffer(CompoundNBT compoundNBT) {
        this.buyingStackFirst = ItemStack.read(compoundNBT.getCompound("buy"));
        this.buyingStackSecond = ItemStack.read(compoundNBT.getCompound("buyB"));
        this.sellingStack = ItemStack.read(compoundNBT.getCompound("sell"));
        this.uses = compoundNBT.getInt("uses");
        this.maxUses = compoundNBT.contains("maxUses", 0) ? compoundNBT.getInt("maxUses") : 4;
        if (compoundNBT.contains("rewardExp", 0)) {
            this.doesRewardEXP = compoundNBT.getBoolean("rewardExp");
        }
        if (compoundNBT.contains("xp", 0)) {
            this.givenEXP = compoundNBT.getInt("xp");
        }
        if (compoundNBT.contains("priceMultiplier", 0)) {
            this.priceMultiplier = compoundNBT.getFloat("priceMultiplier");
        }
        this.specialPrice = compoundNBT.getInt("specialPrice");
        this.demand = compoundNBT.getInt("demand");
    }

    public MerchantOffer(ItemStack itemStack, ItemStack itemStack2, int n, int n2, float f) {
        this(itemStack, ItemStack.EMPTY, itemStack2, n, n2, f);
    }

    public MerchantOffer(ItemStack itemStack, ItemStack itemStack2, ItemStack itemStack3, int n, int n2, float f) {
        this(itemStack, itemStack2, itemStack3, 0, n, n2, f);
    }

    public MerchantOffer(ItemStack itemStack, ItemStack itemStack2, ItemStack itemStack3, int n, int n2, int n3, float f) {
        this(itemStack, itemStack2, itemStack3, n, n2, n3, f, 0);
    }

    public MerchantOffer(ItemStack itemStack, ItemStack itemStack2, ItemStack itemStack3, int n, int n2, int n3, float f, int n4) {
        this.buyingStackFirst = itemStack;
        this.buyingStackSecond = itemStack2;
        this.sellingStack = itemStack3;
        this.uses = n;
        this.maxUses = n2;
        this.givenEXP = n3;
        this.priceMultiplier = f;
        this.demand = n4;
    }

    public ItemStack getBuyingStackFirst() {
        return this.buyingStackFirst;
    }

    public ItemStack getDiscountedBuyingStackFirst() {
        int n = this.buyingStackFirst.getCount();
        ItemStack itemStack = this.buyingStackFirst.copy();
        int n2 = Math.max(0, MathHelper.floor((float)(n * this.demand) * this.priceMultiplier));
        itemStack.setCount(MathHelper.clamp(n + n2 + this.specialPrice, 1, this.buyingStackFirst.getItem().getMaxStackSize()));
        return itemStack;
    }

    public ItemStack getBuyingStackSecond() {
        return this.buyingStackSecond;
    }

    public ItemStack getSellingStack() {
        return this.sellingStack;
    }

    public void calculateDemand() {
        this.demand = this.demand + this.uses - (this.maxUses - this.uses);
    }

    public ItemStack getCopyOfSellingStack() {
        return this.sellingStack.copy();
    }

    public int getUses() {
        return this.uses;
    }

    public void resetUses() {
        this.uses = 0;
    }

    public int getMaxUses() {
        return this.maxUses;
    }

    public void increaseUses() {
        ++this.uses;
    }

    public int getDemand() {
        return this.demand;
    }

    public void increaseSpecialPrice(int n) {
        this.specialPrice += n;
    }

    public void resetSpecialPrice() {
        this.specialPrice = 0;
    }

    public int getSpecialPrice() {
        return this.specialPrice;
    }

    public void setSpecialPrice(int n) {
        this.specialPrice = n;
    }

    public float getPriceMultiplier() {
        return this.priceMultiplier;
    }

    public int getGivenExp() {
        return this.givenEXP;
    }

    public boolean hasNoUsesLeft() {
        return this.uses >= this.maxUses;
    }

    public void makeUnavailable() {
        this.uses = this.maxUses;
    }

    public boolean hasBeenUsed() {
        return this.uses > 0;
    }

    public boolean getDoesRewardExp() {
        return this.doesRewardEXP;
    }

    public CompoundNBT write() {
        CompoundNBT compoundNBT = new CompoundNBT();
        compoundNBT.put("buy", this.buyingStackFirst.write(new CompoundNBT()));
        compoundNBT.put("sell", this.sellingStack.write(new CompoundNBT()));
        compoundNBT.put("buyB", this.buyingStackSecond.write(new CompoundNBT()));
        compoundNBT.putInt("uses", this.uses);
        compoundNBT.putInt("maxUses", this.maxUses);
        compoundNBT.putBoolean("rewardExp", this.doesRewardEXP);
        compoundNBT.putInt("xp", this.givenEXP);
        compoundNBT.putFloat("priceMultiplier", this.priceMultiplier);
        compoundNBT.putInt("specialPrice", this.specialPrice);
        compoundNBT.putInt("demand", this.demand);
        return compoundNBT;
    }

    public boolean matches(ItemStack itemStack, ItemStack itemStack2) {
        return this.equalIgnoringDamage(itemStack, this.getDiscountedBuyingStackFirst()) && itemStack.getCount() >= this.getDiscountedBuyingStackFirst().getCount() && this.equalIgnoringDamage(itemStack2, this.buyingStackSecond) && itemStack2.getCount() >= this.buyingStackSecond.getCount();
    }

    private boolean equalIgnoringDamage(ItemStack itemStack, ItemStack itemStack2) {
        if (itemStack2.isEmpty() && itemStack.isEmpty()) {
            return false;
        }
        ItemStack itemStack3 = itemStack.copy();
        if (itemStack3.getItem().isDamageable()) {
            itemStack3.setDamage(itemStack3.getDamage());
        }
        return ItemStack.areItemsEqual(itemStack3, itemStack2) && (!itemStack2.hasTag() || itemStack3.hasTag() && NBTUtil.areNBTEquals(itemStack2.getTag(), itemStack3.getTag(), false));
    }

    public boolean doTransaction(ItemStack itemStack, ItemStack itemStack2) {
        if (!this.matches(itemStack, itemStack2)) {
            return true;
        }
        itemStack.shrink(this.getDiscountedBuyingStackFirst().getCount());
        if (!this.getBuyingStackSecond().isEmpty()) {
            itemStack2.shrink(this.getBuyingStackSecond().getCount());
        }
        return false;
    }
}

