// 
// Decompiled by the rizzer xd
// 

package dev.lvstrng.argon.modules.impl;

import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;

class CrystalOptimizerAttackHandler implements PlayerInteractEntityC2SPacket.Handler {
    final CrystalOptimizer crystalOptimizer;

    CrystalOptimizerAttackHandler(final CrystalOptimizer field514) {
        this.crystalOptimizer = field514;
    }

    @Override
    public void interact(Hand hand) {
    }

    @Override
    public void interactAt(Hand hand, Vec3d pos) {
    }

    @Override
    public void attack() {
        if (CrystalOptimizer.getMinecraftClient(this.crystalOptimizer).crosshairTarget == null) {
            return;
        }
        if (CrystalOptimizer.getMinecraftClient(this.crystalOptimizer).crosshairTarget.getType() == HitResult.Type.ENTITY) {
            final HitResult crosshairTarget = CrystalOptimizer.getMinecraftClient(this.crystalOptimizer).crosshairTarget;
            if (crosshairTarget instanceof final EntityHitResult entityHitResult) {
                if (entityHitResult.getEntity() instanceof EndCrystalEntity) {
                    final StatusEffectInstance method_6112 = CrystalOptimizer.getMinecraftClient(this.crystalOptimizer).player.getStatusEffect(StatusEffects.WEAKNESS);
                    final StatusEffectInstance method_6113 = CrystalOptimizer.getMinecraftClient(this.crystalOptimizer).player.getStatusEffect(StatusEffects.STRENGTH);
                    if (method_6112 != null && (method_6113 == null || method_6113.getAmplifier() <= method_6112.getAmplifier()) && !this.crystalOptimizer.isToolValid(CrystalOptimizer.getMinecraftClient(this.crystalOptimizer).player.getMainHandStack())) {
                        return;
                    }
                    entityHitResult.getEntity().kill();
                    entityHitResult.getEntity().remove(Entity.RemovalReason.KILLED);
                    entityHitResult.getEntity().onRemoved();
                }
            }
        }
    }
}