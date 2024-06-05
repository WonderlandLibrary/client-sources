package client.metaware.impl.module.movmeent;

import client.metaware.api.event.painfulniggerrapist.Listener;
import client.metaware.api.event.painfulniggerrapist.annotations.EventHandler;
import client.metaware.api.module.api.Category;
import client.metaware.api.module.api.Module;
import client.metaware.api.module.api.ModuleInfo;
import client.metaware.impl.event.impl.player.MovePlayerEvent;
import client.metaware.impl.utils.util.player.ACType;
import client.metaware.impl.utils.util.player.MovementUtils;

@ModuleInfo(name = "HighJump", renderName = "High Jump", category = Category.MOVEMENT)
public class HighJump extends Module {


    @EventHandler
    private final Listener<MovePlayerEvent> eventListener = event -> {
        if(mc.thePlayer.fallDistance - mc.thePlayer.motionY >= 2.5F && MovementUtils.isOverVoid()){
            mc.timer.timerSpeed = 0.67f;
            mc.thePlayer.fallDistance = -mc.thePlayer.fallDistance;
            event.setY(mc.thePlayer.motionY = 1.2F);
            
            MovementUtils.setSpeed(event, MovementUtils.getBaseSpeed(ACType.VERUS) * 5);
            if(MovementUtils.getMotion(mc.thePlayer) > MovementUtils.getSpeed()){
                mc.timer.timerSpeed = 1.0f;
            }
        }else if(!MovementUtils.isOverVoid()){
            mc.timer.timerSpeed = 1.0f;
        }
    };
}
