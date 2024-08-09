/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.item;

import java.util.ArrayList;
import javax.annotation.Nullable;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MerchantOffer;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.network.PacketBuffer;

public class MerchantOffers
extends ArrayList<MerchantOffer> {
    public MerchantOffers() {
    }

    public MerchantOffers(CompoundNBT compoundNBT) {
        ListNBT listNBT = compoundNBT.getList("Recipes", 10);
        for (int i = 0; i < listNBT.size(); ++i) {
            this.add(new MerchantOffer(listNBT.getCompound(i)));
        }
    }

    @Nullable
    public MerchantOffer func_222197_a(ItemStack itemStack, ItemStack itemStack2, int n) {
        if (n > 0 && n < this.size()) {
            MerchantOffer merchantOffer = (MerchantOffer)this.get(n);
            return merchantOffer.matches(itemStack, itemStack2) ? merchantOffer : null;
        }
        for (int i = 0; i < this.size(); ++i) {
            MerchantOffer merchantOffer = (MerchantOffer)this.get(i);
            if (!merchantOffer.matches(itemStack, itemStack2)) continue;
            return merchantOffer;
        }
        return null;
    }

    public void write(PacketBuffer packetBuffer) {
        packetBuffer.writeByte((byte)(this.size() & 0xFF));
        for (int i = 0; i < this.size(); ++i) {
            MerchantOffer merchantOffer = (MerchantOffer)this.get(i);
            packetBuffer.writeItemStack(merchantOffer.getBuyingStackFirst());
            packetBuffer.writeItemStack(merchantOffer.getSellingStack());
            ItemStack itemStack = merchantOffer.getBuyingStackSecond();
            packetBuffer.writeBoolean(!itemStack.isEmpty());
            if (!itemStack.isEmpty()) {
                packetBuffer.writeItemStack(itemStack);
            }
            packetBuffer.writeBoolean(merchantOffer.hasNoUsesLeft());
            packetBuffer.writeInt(merchantOffer.getUses());
            packetBuffer.writeInt(merchantOffer.getMaxUses());
            packetBuffer.writeInt(merchantOffer.getGivenExp());
            packetBuffer.writeInt(merchantOffer.getSpecialPrice());
            packetBuffer.writeFloat(merchantOffer.getPriceMultiplier());
            packetBuffer.writeInt(merchantOffer.getDemand());
        }
    }

    public static MerchantOffers read(PacketBuffer packetBuffer) {
        MerchantOffers merchantOffers = new MerchantOffers();
        int n = packetBuffer.readByte() & 0xFF;
        for (int i = 0; i < n; ++i) {
            ItemStack itemStack = packetBuffer.readItemStack();
            ItemStack itemStack2 = packetBuffer.readItemStack();
            ItemStack itemStack3 = ItemStack.EMPTY;
            if (packetBuffer.readBoolean()) {
                itemStack3 = packetBuffer.readItemStack();
            }
            boolean bl = packetBuffer.readBoolean();
            int n2 = packetBuffer.readInt();
            int n3 = packetBuffer.readInt();
            int n4 = packetBuffer.readInt();
            int n5 = packetBuffer.readInt();
            float f = packetBuffer.readFloat();
            int n6 = packetBuffer.readInt();
            MerchantOffer merchantOffer = new MerchantOffer(itemStack, itemStack3, itemStack2, n2, n3, n4, f, n6);
            if (bl) {
                merchantOffer.makeUnavailable();
            }
            merchantOffer.setSpecialPrice(n5);
            merchantOffers.add(merchantOffer);
        }
        return merchantOffers;
    }

    public CompoundNBT write() {
        CompoundNBT compoundNBT = new CompoundNBT();
        ListNBT listNBT = new ListNBT();
        for (int i = 0; i < this.size(); ++i) {
            MerchantOffer merchantOffer = (MerchantOffer)this.get(i);
            listNBT.add(merchantOffer.write());
        }
        compoundNBT.put("Recipes", listNBT);
        return compoundNBT;
    }
}

