package me.superskidder.lune.modules.movement;

import me.superskidder.lune.modules.Mod;
import me.superskidder.lune.modules.ModCategory;
import me.superskidder.lune.events.EventUpdate;
import me.superskidder.lune.manager.event.EventTarget;
import me.superskidder.lune.values.type.Mode;

/**
 * @description: 水上溜达
 * @author: QianXia
 * @create: 2020/10/8 16:53
 **/
public class LiquidWalk extends Mod {
    private Mode<?> bypass = new Mode<>("Mode", BypassMode.values(), BypassMode.Vanilla);

    public LiquidWalk(){
        super("LiquidWalk", ModCategory.Movement,"Walk on Liquid");
        this.addValues(bypass);
    }

    @EventTarget
    public void onUpdate(EventUpdate event){
        if(mc.thePlayer.isInWater()){
            mc.thePlayer.motionY = 0.2F;
        }
    }

    enum BypassMode{
        Vanilla,
    }
}
