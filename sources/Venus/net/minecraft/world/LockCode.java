/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world;

import javax.annotation.concurrent.Immutable;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;

@Immutable
public class LockCode {
    public static final LockCode EMPTY_CODE = new LockCode("");
    private final String lock;

    public LockCode(String string) {
        this.lock = string;
    }

    public boolean func_219964_a(ItemStack itemStack) {
        return this.lock.isEmpty() || !itemStack.isEmpty() && itemStack.hasDisplayName() && this.lock.equals(itemStack.getDisplayName().getString());
    }

    public void write(CompoundNBT compoundNBT) {
        if (!this.lock.isEmpty()) {
            compoundNBT.putString("Lock", this.lock);
        }
    }

    public static LockCode read(CompoundNBT compoundNBT) {
        return compoundNBT.contains("Lock", 1) ? new LockCode(compoundNBT.getString("Lock")) : EMPTY_CODE;
    }
}

