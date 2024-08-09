/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.dispenser;

import net.minecraft.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.dispenser.IBlockSource;

public abstract class OptionalDispenseBehavior
extends DefaultDispenseItemBehavior {
    private boolean successful = true;

    public boolean isSuccessful() {
        return this.successful;
    }

    public void setSuccessful(boolean bl) {
        this.successful = bl;
    }

    @Override
    protected void playDispenseSound(IBlockSource iBlockSource) {
        iBlockSource.getWorld().playEvent(this.isSuccessful() ? 1000 : 1001, iBlockSource.getBlockPos(), 0);
    }
}

