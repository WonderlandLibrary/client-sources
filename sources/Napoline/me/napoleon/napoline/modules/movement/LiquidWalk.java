package me.napoleon.napoline.modules.movement;

import me.napoleon.napoline.events.EventUpdate;
import me.napoleon.napoline.junk.values.type.Mode;
import me.napoleon.napoline.manager.event.EventTarget;
import me.napoleon.napoline.modules.Mod;
import me.napoleon.napoline.modules.ModCategory;

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
