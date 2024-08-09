/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity;

import java.util.Random;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.EntityDataManager;

public class BoostHelper {
    private final EntityDataManager manager;
    private final DataParameter<Integer> boostTime;
    private final DataParameter<Boolean> saddled;
    public boolean saddledRaw;
    public int field_233611_b_;
    public int boostTimeRaw;

    public BoostHelper(EntityDataManager entityDataManager, DataParameter<Integer> dataParameter, DataParameter<Boolean> dataParameter2) {
        this.manager = entityDataManager;
        this.boostTime = dataParameter;
        this.saddled = dataParameter2;
    }

    public void updateData() {
        this.saddledRaw = true;
        this.field_233611_b_ = 0;
        this.boostTimeRaw = this.manager.get(this.boostTime);
    }

    public boolean boost(Random random2) {
        if (this.saddledRaw) {
            return true;
        }
        this.saddledRaw = true;
        this.field_233611_b_ = 0;
        this.boostTimeRaw = random2.nextInt(841) + 140;
        this.manager.set(this.boostTime, this.boostTimeRaw);
        return false;
    }

    public void setSaddledToNBT(CompoundNBT compoundNBT) {
        compoundNBT.putBoolean("Saddle", this.getSaddled());
    }

    public void setSaddledFromNBT(CompoundNBT compoundNBT) {
        this.setSaddledFromBoolean(compoundNBT.getBoolean("Saddle"));
    }

    public void setSaddledFromBoolean(boolean bl) {
        this.manager.set(this.saddled, bl);
    }

    public boolean getSaddled() {
        return this.manager.get(this.saddled);
    }
}

