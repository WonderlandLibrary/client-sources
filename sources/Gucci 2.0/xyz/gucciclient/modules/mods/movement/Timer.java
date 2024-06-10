package xyz.gucciclient.modules.mods.movement;

import xyz.gucciclient.modules.*;
import java.lang.reflect.*;
import xyz.gucciclient.values.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.minecraft.client.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class Timer extends Module
{
    private long lastRateChange;
    private Field timerField;
    private Field speedField;
    private NumberValue timerspeed;
    
    public Timer() {
        super("Timer", 0, Category.OTHER);
        this.addValue(this.timerspeed = new NumberValue("Timer speed", 0.5, 0.1, 2.0));
    }
    
    @SubscribeEvent
    public void onTick(final TickEvent.PlayerTickEvent ev3nt) throws Exception {
        try {
            this.timerField = Minecraft.class.getDeclaredField("timer");
            this.speedField = net.minecraft.util.Timer.class.getDeclaredField("timerSpeed");
        }
        catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        if (System.currentTimeMillis() > this.lastRateChange + 1000L) {
            this.setTimerRate((float)this.timerspeed.getValue());
            this.lastRateChange = System.currentTimeMillis();
        }
    }
    
    @Override
    public void onDisable() {
        this.setTimerRate(1.0f);
    }
    
    private void setTimerRate(final float rate) {
        try {
            this.timerField.setAccessible(true);
            final net.minecraft.util.Timer timer = (net.minecraft.util.Timer)this.timerField.get(this.mc);
            this.timerField.setAccessible(false);
            this.speedField.setAccessible(true);
            this.speedField.set(timer, rate);
            this.speedField.setAccessible(false);
        }
        catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
