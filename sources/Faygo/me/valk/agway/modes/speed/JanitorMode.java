package me.valk.agway.modes.speed;

import me.valk.agway.modules.movement.SpeedMod;
import me.valk.event.EventListener;
import me.valk.event.EventType;
import me.valk.event.events.player.EventMotion;
import me.valk.module.ModMode;

/**
 * Created by Zeb on 4/21/2016.
 */
public class JanitorMode extends ModMode<SpeedMod> {

    private boolean nextTick;
    private int ticks;
    private int cooldown = 0;

    public JanitorMode(SpeedMod parent){
        super(parent, "Janitor");
    }

    @EventListener
    public void onMotion(EventMotion event){
        if(event.getType() != EventType.PRE) return;

        if(p.isInWater() || p.isOnLiquid() || p.isUnderBlock()){
            return;
        }
        if(event.getType() == EventType.PRE && p.onGround){
            boolean hack = p.ticksExisted % 2 == 0;
            event.setLocation(event.getLocation().add(0, p.ticksExisted % 2 != 0 ? 0.41999998688697815 : 0, 0));
            event.setLocation(event.getLocation().subtract(0, p.ticksExisted % 2 != 0 ? 0.1 : 0, 0));
            p.setSpeed(p.getSpeed() * (hack ? 0f : 25));
        }
    }
}