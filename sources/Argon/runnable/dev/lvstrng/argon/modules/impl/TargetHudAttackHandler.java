// 
// Decompiled by the rizzer xd
// 

package dev.lvstrng.argon.modules.impl;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;

record TargetHudAttackHandler(TargetHUD targetHud) implements PlayerInteractEntityC2SPacket.Handler {

    @Override
    public void interact(Hand hand) {
    }

    @Override
    public void interactAt(Hand hand, Vec3d pos) {
    }

    @Override
    public void attack() {
        if (TargetHUD.getMC(this.targetHud).targetedEntity instanceof PlayerEntity)
            this.targetHud.lastUpdateTime = System.currentTimeMillis();
    }
}
