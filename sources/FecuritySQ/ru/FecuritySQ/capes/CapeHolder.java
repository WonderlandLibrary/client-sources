package ru.FecuritySQ.capes;


import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.util.math.MathHelper;
import ru.FecuritySQ.capes.StickSimulation.Stick;
import ru.FecuritySQ.capes.StickSimulation.Point;

public interface CapeHolder {
    public StickSimulation getSimulation();
    
    public default void updateSimulation(AbstractClientPlayerEntity abstractClientPlayer, int partCount) {
        StickSimulation simulation = getSimulation();
        boolean dirty = false;
        if(simulation.points.size() != partCount) {
            simulation.points.clear();
            simulation.sticks.clear();
            for (int i = 0; i < partCount; i++) {
                Point point = new Point();
                point.position.y = -i;
                point.locked = i == 0;
                simulation.points.add(point);
                if(i > 0) {
                    simulation.sticks.add(new Stick(simulation.points.get(i-1), point, 1f));
                }
            }
            dirty = true;
        }
        if(dirty) {
            for(int i = 0; i < 10; i++) // quickly doing a few simulation steps to get the cape int a stable configuration
                simulate(abstractClientPlayer);
        }
    }
    
    public default void simulate(AbstractClientPlayerEntity abstractClientPlayer) {
        StickSimulation simulation = getSimulation();
        if(simulation.points.isEmpty()) {
            return; // no cape, nothing to update
        }
        simulation.points.get(0).prevPosition.copy(simulation.points.get(0).position);
        double d = abstractClientPlayer.chasingPosX
                - abstractClientPlayer.getPosX();
        double m = abstractClientPlayer.chasingPosZ
                - abstractClientPlayer.getPosZ();
        float n = abstractClientPlayer.prevRenderYawOffset + abstractClientPlayer.renderYawOffset - abstractClientPlayer.prevRenderYawOffset;
        double o = MathHelper.sin(n * 0.017453292F);
        double p = -MathHelper.cos(n * 0.017453292F);
        float heightMul = 6;
        // gives the cape a small swing when jumping/falling to not clip with itself/simulate some air getting under it
        double fallHack = MathHelper.clamp((simulation.points.get(0).position.y - (abstractClientPlayer.getPosY()*heightMul)), 0, 1);
        simulation.points.get(0).position.x += (d * o + m * p) + fallHack;
        simulation.points.get(0).position.y = (float) (abstractClientPlayer.getPosY()*heightMul + (abstractClientPlayer.isCrouching() ? -4 : 0));
        simulation.simulate();
    }
    
}