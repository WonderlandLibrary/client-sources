package me.valk.agway.modes.speed;

import me.valk.agway.modules.movement.SpeedMod;
import me.valk.event.EventListener;
import me.valk.event.EventType;
import me.valk.event.events.player.EventMotion;
import me.valk.module.ModMode;

/**
 * Created by Zeb on 4/21/2016.
 */
public class NCPMode extends ModMode<SpeedMod> {

    private boolean nextTick;
    private int ticks;
    private int cooldown = 0;

    public NCPMode(SpeedMod parent){
        super(parent, "NCP");
    }

    @Override
    public void onDisable(){
        mc.timer.timerSpeed = 1f;
    }

    @EventListener
    public void onMotion(EventMotion event){
        if(event.getType() != EventType.PRE) return;

        if(p.isOnLiquid() || p.isOnLiquid() || p.isUnderBlock()){
            return;
        }
        if(event.getType() == EventType.PRE && p.onGround && p.isMoving()){
            if(p.ticksExisted % 2 != 0) event.setLocation(event.getLocation().add(0, 0.4, 0));
            p.setSpeed(p.ticksExisted % 2 == 0 ? 0.15f * 2.32f : 0.15f);
            mc.timer.timerSpeed = 1.095f;
        }
    }
}
