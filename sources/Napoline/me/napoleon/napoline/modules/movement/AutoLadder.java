package me.napoleon.napoline.modules.movement;

import org.lwjgl.input.Keyboard;

import me.napoleon.napoline.events.EventUpdate;
import me.napoleon.napoline.junk.values.type.Mode;
import me.napoleon.napoline.junk.values.type.Num;
import me.napoleon.napoline.manager.event.EventTarget;
import me.napoleon.napoline.modules.Mod;
import me.napoleon.napoline.modules.ModCategory;

/**
 * @description: 爪巴藤蔓
 * @author: QianXia
 * @create: 2020/10/7 19:29
 **/
public class AutoLadder extends Mod {
    private Mode<?> bypass = new Mode<>("Bypass", bypassAC.values(), bypassAC.Vanilla);
    private Num<Double> speed = new Num<>("Speed", 0.1, 0.1, 0.4);

    public AutoLadder(){
        super("AutoLadder", ModCategory.Movement,"Auto climb");
        this.addValues(bypass, speed);
    }

    @EventTarget
    public void onUpdate(EventUpdate event){
        if(mc.thePlayer.isOnLadder()){
            if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
                if(bypass.getValue() != bypassAC.AAC){
                    mc.thePlayer.motionY = speed.getValue().floatValue();
                }else{
                    mc.thePlayer.motionY = 0.1F;
                }
            }
        }
    }

    enum bypassAC{
        // 香草有手就行
        Vanilla,
        // 没写检测的大牛反作弊
        Spartan,
        // 几年了还没写检测
        AAC
    }
}
