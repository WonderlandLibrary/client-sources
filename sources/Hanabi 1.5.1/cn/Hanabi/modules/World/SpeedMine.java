package cn.Hanabi.modules.World;

import cn.Hanabi.value.*;
import cn.Hanabi.modules.*;
import cn.Hanabi.events.*;
import cn.Hanabi.injection.interfaces.*;
import cn.Hanabi.*;
import com.darkmagician6.eventapi.*;

public class SpeedMine extends Mod
{
    public Value<Double> speed;
    
    
    public SpeedMine() {
        super("SpeedMine", Category.WORLD);
        this.speed = new Value<Double>("SpeedMine_Speed", 0.3, 0.1, 1.0, 0.01);
    }
    
    @EventTarget
    public void onUpdate(final EventUpdate eventUpdate) {
        final IPlayerControllerMP playerControllerMP = (IPlayerControllerMP)SpeedMine.mc.field_71442_b;
        playerControllerMP.setBlockHitDelay(0);
        if (playerControllerMP.getCurBlockDamageMP() >= 1.0 - this.speed.getValueState()) {
            if (Hanabi.flag < 0) {
                playerControllerMP.setCurBlockDamageMP(0.9f);
            }
            else {
                playerControllerMP.setCurBlockDamageMP(1.0f);
            }
        }
    }
}
