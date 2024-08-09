/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.merchant;

import net.minecraft.entity.Entity;
import net.minecraft.entity.merchant.IReputationType;

public interface IReputationTracking {
    public void updateReputation(IReputationType var1, Entity var2);
}

