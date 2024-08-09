/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.player;

import net.minecraft.nbt.CompoundNBT;

public class PlayerAbilities {
    public boolean disableDamage;
    public boolean isFlying;
    public boolean allowFlying;
    public boolean isCreativeMode;
    public boolean allowEdit = true;
    private float flySpeed = 0.05f;
    private float walkSpeed = 0.1f;

    public void write(CompoundNBT compoundNBT) {
        CompoundNBT compoundNBT2 = new CompoundNBT();
        compoundNBT2.putBoolean("invulnerable", this.disableDamage);
        compoundNBT2.putBoolean("flying", this.isFlying);
        compoundNBT2.putBoolean("mayfly", this.allowFlying);
        compoundNBT2.putBoolean("instabuild", this.isCreativeMode);
        compoundNBT2.putBoolean("mayBuild", this.allowEdit);
        compoundNBT2.putFloat("flySpeed", this.flySpeed);
        compoundNBT2.putFloat("walkSpeed", this.walkSpeed);
        compoundNBT.put("abilities", compoundNBT2);
    }

    public void read(CompoundNBT compoundNBT) {
        if (compoundNBT.contains("abilities", 1)) {
            CompoundNBT compoundNBT2 = compoundNBT.getCompound("abilities");
            this.disableDamage = compoundNBT2.getBoolean("invulnerable");
            this.isFlying = compoundNBT2.getBoolean("flying");
            this.allowFlying = compoundNBT2.getBoolean("mayfly");
            this.isCreativeMode = compoundNBT2.getBoolean("instabuild");
            if (compoundNBT2.contains("flySpeed", 0)) {
                this.flySpeed = compoundNBT2.getFloat("flySpeed");
                this.walkSpeed = compoundNBT2.getFloat("walkSpeed");
            }
            if (compoundNBT2.contains("mayBuild", 0)) {
                this.allowEdit = compoundNBT2.getBoolean("mayBuild");
            }
        }
    }

    public float getFlySpeed() {
        return this.flySpeed;
    }

    public void setFlySpeed(float f) {
        this.flySpeed = f;
    }

    public float getWalkSpeed() {
        return this.walkSpeed;
    }

    public void setWalkSpeed(float f) {
        this.walkSpeed = f;
    }
}

