package me.napoleon.napoline.modules.world;

import me.napoleon.napoline.junk.values.type.Num;
import me.napoleon.napoline.modules.Mod;
import me.napoleon.napoline.modules.ModCategory;

/**
 * @description: 变速纪狗
 * @author: Qian_Xia
 * @create: 2020-08-29 15:41
 **/
public class Timer extends Mod {
    public Num<Double> speed = new Num<>("TimerSpeed", 1.0, 0.05, 10.0);

    public Timer(){
        super("Timer", ModCategory.World,"Change game speed");
        this.addValues(speed);
    }

    @Override
    public void onEnabled() {
        net.minecraft.util.Timer.timerSpeed = speed.getValue().floatValue();
    }

    @Override
    public void onDisable() {
        net.minecraft.util.Timer.timerSpeed = 1.0F;
    }
}
