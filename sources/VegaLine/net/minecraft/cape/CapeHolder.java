/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.cape;

import net.minecraft.cape.sim.StickSimulation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.MathHelper;

public interface CapeHolder {
    public StickSimulation getSimulation();

    default public void updateSimulation(EntityPlayer abstractClientPlayer, int partCount) {
        StickSimulation simulation = this.getSimulation();
        boolean dirty = false;
        if (simulation.points.size() != partCount) {
            simulation.points.clear();
            simulation.sticks.clear();
            for (int i = 0; i < partCount; ++i) {
                StickSimulation.Point point = new StickSimulation.Point();
                point.position.y = -i;
                point.locked = i == 0;
                simulation.points.add(point);
                if (i <= 0) continue;
                simulation.sticks.add(new StickSimulation.Stick(simulation.points.get(i - 1), point, 1.0f));
            }
            dirty = true;
        }
        if (dirty) {
            this.simulate(abstractClientPlayer);
        }
    }

    default public void simulate(EntityPlayer abstractClientPlayer) {
        StickSimulation simulation = this.getSimulation();
        if (simulation == null) {
            return;
        }
        if (simulation.points.isEmpty()) {
            return;
        }
        simulation.points.get((int)0).prevPosition.copy(simulation.points.get((int)0).position);
        double d = abstractClientPlayer.chasingPosX - abstractClientPlayer.posX;
        double m = abstractClientPlayer.chasingPosZ - abstractClientPlayer.posZ;
        float n = abstractClientPlayer.prevRenderYawOffset + abstractClientPlayer.renderYawOffset - abstractClientPlayer.prevRenderYawOffset;
        double o = Math.sin(n * ((float)Math.PI / 180));
        double p = -Math.cos(n * ((float)Math.PI / 180));
        float heightMul = 5.0f;
        double fallHack = MathHelper.clamp((double)simulation.points.get((int)0).position.y - abstractClientPlayer.posY * (double)heightMul, 0.0, 1.0);
        simulation.points.get((int)0).position.x = (float)((double)simulation.points.get((int)0).position.x + ((d * o + m * p) / 8.0 + fallHack * 2.0));
        simulation.points.get((int)0).position.y = (float)(abstractClientPlayer.posY * (double)heightMul + (double)(abstractClientPlayer.isSneaking() ? -4 : 0));
        simulation.simulate();
    }
}

