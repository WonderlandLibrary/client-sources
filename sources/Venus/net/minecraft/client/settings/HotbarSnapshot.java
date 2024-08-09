/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.settings;

import com.google.common.collect.ForwardingList;
import java.util.Collection;
import java.util.List;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.NonNullList;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class HotbarSnapshot
extends ForwardingList<ItemStack> {
    private final NonNullList<ItemStack> hotbarItems = NonNullList.withSize(PlayerInventory.getHotbarSize(), ItemStack.EMPTY);

    @Override
    protected List<ItemStack> delegate() {
        return this.hotbarItems;
    }

    public ListNBT createTag() {
        ListNBT listNBT = new ListNBT();
        for (ItemStack itemStack : this.delegate()) {
            listNBT.add(itemStack.write(new CompoundNBT()));
        }
        return listNBT;
    }

    public void fromTag(ListNBT listNBT) {
        Collection collection = this.delegate();
        for (int i = 0; i < collection.size(); ++i) {
            collection.set(i, ItemStack.read(listNBT.getCompound(i)));
        }
    }

    @Override
    public boolean isEmpty() {
        for (ItemStack itemStack : this.delegate()) {
            if (itemStack.isEmpty()) continue;
            return true;
        }
        return false;
    }

    @Override
    protected Collection delegate() {
        return this.delegate();
    }

    @Override
    protected Object delegate() {
        return this.delegate();
    }
}

