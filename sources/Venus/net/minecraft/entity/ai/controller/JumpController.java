/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai.controller;

import net.minecraft.entity.MobEntity;

public class JumpController {
    private final MobEntity mob;
    protected boolean isJumping;

    public JumpController(MobEntity mobEntity) {
        this.mob = mobEntity;
    }

    public void setJumping() {
        this.isJumping = true;
    }

    public void tick() {
        this.mob.setJumping(this.isJumping);
        this.isJumping = false;
    }
}

