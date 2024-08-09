package dev.waveycapes;

import dev.waveycapes.math.Vector2;
import dev.waveycapes.math.Vector3;
import dev.waveycapes.sim.StickSimulation;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.util.math.MathHelper;

public interface CapeHolder {
    StickSimulation getSimulation();

    default void updateSimulation(final AbstractClientPlayerEntity abstractClientPlayer, final int partCount) {
        StickSimulation simulation = this.getSimulation();

        if (simulation == null) {
            return;
        }

        final boolean dirty = simulation.init(partCount);
        if (dirty) {
            simulation.applyMovement(new Vector3(1.0f, 1.0f, 0.0f));
            for (int i = 0; i < 5; ++i) {
                this.simulate(abstractClientPlayer);
            }
        }
    }

    default void simulate(AbstractClientPlayerEntity abstractClientPlayer) {
        final StickSimulation simulation = this.getSimulation();
        if (simulation == null || simulation.empty()) {
            return;
        }
        final double d = abstractClientPlayer.chasingPosX - abstractClientPlayer.getPosX();
        final double m = abstractClientPlayer.chasingPosZ - abstractClientPlayer.getPosZ();
        final float n = abstractClientPlayer.prevRenderYawOffset + abstractClientPlayer.renderYawOffset - abstractClientPlayer.prevRenderYawOffset;
        final double o = MathHelper.sin(n * 0.017453292f);
        final double p = -MathHelper.cos(n * 0.017453292f);
        float heightMul = (float) WaveyCapesBase.config.heightMultiplier;
        final float straveMul = (float) WaveyCapesBase.config.straveMultiplier;
        if (abstractClientPlayer.canSwim()) {
            heightMul *= 2.0f;
        }
        final double fallHack = MathHelper.clamp((abstractClientPlayer.prevPosY - abstractClientPlayer.getPosY()) * 10.0, 0.0, 1.0);
        if (abstractClientPlayer.canSwim()) {
            simulation.setGravity(WaveyCapesBase.config.gravity / 10.0f);
        } else {
            simulation.setGravity((float) WaveyCapesBase.config.gravity);
        }
        final Vector3 gravity = new Vector3(0.0f, -1.0f, 0.0f);
        final Vector2 strave = new Vector2((float) (abstractClientPlayer.getPosX() - abstractClientPlayer.prevPosX), (float) (abstractClientPlayer.getPosZ() - abstractClientPlayer.prevPosZ));
        strave.rotateDegrees(-abstractClientPlayer.rotationYaw);
        final double changeX = d * o + m * p + fallHack + ((abstractClientPlayer.isCrouching() && !simulation.isSneaking()) ? 3 : 0);
        final double changeY = (abstractClientPlayer.getPosY() - abstractClientPlayer.prevPosY) * heightMul + ((abstractClientPlayer.isCrouching() && !simulation.isSneaking()) ? 1 : 0);
        final double changeZ = -strave.x * straveMul;
        simulation.setSneaking(abstractClientPlayer.isCrouching());
        final Vector3 change = new Vector3((float) changeX, (float) changeY, (float) changeZ);
        if (abstractClientPlayer.isActualySwimming()) {
            float rotation = abstractClientPlayer.rotationPitch;
            rotation += 90.0f;
            gravity.rotateDegrees(rotation);
            change.rotateDegrees(rotation);
        }
        simulation.setGravityDirection(gravity);
        simulation.applyMovement(change);
        simulation.simulate();
    }

}
