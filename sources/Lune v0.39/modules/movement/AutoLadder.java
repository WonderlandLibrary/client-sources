package me.superskidder.lune.modules.movement;

import me.superskidder.lune.modules.Mod;
import me.superskidder.lune.modules.ModCategory;
import me.superskidder.lune.events.EventUpdate;
import me.superskidder.lune.manager.event.EventTarget;
import me.superskidder.lune.values.type.Mode;
import me.superskidder.lune.values.type.Num;
import org.lwjgl.input.Keyboard;

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
