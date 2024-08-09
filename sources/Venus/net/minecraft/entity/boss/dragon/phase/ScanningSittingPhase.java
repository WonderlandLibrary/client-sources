/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.boss.dragon.phase;

import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.boss.dragon.phase.PhaseType;
import net.minecraft.entity.boss.dragon.phase.SittingPhase;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;

public class ScanningSittingPhase
extends SittingPhase {
    private static final EntityPredicate field_221115_b = new EntityPredicate().setDistance(150.0);
    private final EntityPredicate field_221116_c = new EntityPredicate().setDistance(20.0).setCustomPredicate(arg_0 -> ScanningSittingPhase.lambda$new$0(enderDragonEntity, arg_0));
    private int scanningTime;

    public ScanningSittingPhase(EnderDragonEntity enderDragonEntity) {
        super(enderDragonEntity);
    }

    @Override
    public void serverTick() {
        ++this.scanningTime;
        PlayerEntity playerEntity = this.dragon.world.getClosestPlayer(this.field_221116_c, this.dragon, this.dragon.getPosX(), this.dragon.getPosY(), this.dragon.getPosZ());
        if (playerEntity != null) {
            if (this.scanningTime > 25) {
                this.dragon.getPhaseManager().setPhase(PhaseType.SITTING_ATTACKING);
            } else {
                Vector3d vector3d = new Vector3d(playerEntity.getPosX() - this.dragon.getPosX(), 0.0, playerEntity.getPosZ() - this.dragon.getPosZ()).normalize();
                Vector3d vector3d2 = new Vector3d(MathHelper.sin(this.dragon.rotationYaw * ((float)Math.PI / 180)), 0.0, -MathHelper.cos(this.dragon.rotationYaw * ((float)Math.PI / 180))).normalize();
                float f = (float)vector3d2.dotProduct(vector3d);
                float f2 = (float)(Math.acos(f) * 57.2957763671875) + 0.5f;
                if (f2 < 0.0f || f2 > 10.0f) {
                    float f3;
                    double d = playerEntity.getPosX() - this.dragon.dragonPartHead.getPosX();
                    double d2 = playerEntity.getPosZ() - this.dragon.dragonPartHead.getPosZ();
                    double d3 = MathHelper.clamp(MathHelper.wrapDegrees(180.0 - MathHelper.atan2(d, d2) * 57.2957763671875 - (double)this.dragon.rotationYaw), -100.0, 100.0);
                    this.dragon.field_226525_bB_ *= 0.8f;
                    float f4 = f3 = MathHelper.sqrt(d * d + d2 * d2) + 1.0f;
                    if (f3 > 40.0f) {
                        f3 = 40.0f;
                    }
                    this.dragon.field_226525_bB_ = (float)((double)this.dragon.field_226525_bB_ + d3 * (double)(0.7f / f3 / f4));
                    this.dragon.rotationYaw += this.dragon.field_226525_bB_;
                }
            }
        } else if (this.scanningTime >= 100) {
            playerEntity = this.dragon.world.getClosestPlayer(field_221115_b, this.dragon, this.dragon.getPosX(), this.dragon.getPosY(), this.dragon.getPosZ());
            this.dragon.getPhaseManager().setPhase(PhaseType.TAKEOFF);
            if (playerEntity != null) {
                this.dragon.getPhaseManager().setPhase(PhaseType.CHARGING_PLAYER);
                this.dragon.getPhaseManager().getPhase(PhaseType.CHARGING_PLAYER).setTarget(new Vector3d(playerEntity.getPosX(), playerEntity.getPosY(), playerEntity.getPosZ()));
            }
        }
    }

    @Override
    public void initPhase() {
        this.scanningTime = 0;
    }

    public PhaseType<ScanningSittingPhase> getType() {
        return PhaseType.SITTING_SCANNING;
    }

    private static boolean lambda$new$0(EnderDragonEntity enderDragonEntity, LivingEntity livingEntity) {
        return Math.abs(livingEntity.getPosY() - enderDragonEntity.getPosY()) <= 10.0;
    }
}

