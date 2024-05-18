package me.Emir.Karaguc.module.world;

import me.Emir.Karaguc.event.Event;
import me.Emir.Karaguc.event.EventTarget;
import me.Emir.Karaguc.module.Category;
import me.Emir.Karaguc.module.Module;
import net.minecraft.client.Minecraft;

import java.lang.reflect.Field;

public class RetarderMC extends Module {  //dont open to module u opened ?  oh ripp ur mc XDXDXD

    public RetarderMC() {
        super("RetarderMC", 0, Category.WORLD);
    }

    @EventTarget
    public void onUpdate(Event e){
        mc.thePlayer.capabilities.isFlying = true;
        setTimerSpeed(0.505F);
    }

    @Override
    public void onDisable(){
        super.onDisable();
        mc.thePlayer.capabilities.isFlying = true;
        setTimerSpeed(155.0F);
    }
    public static void setTimerSpeed(float timerSpeed) {
        try {
            Class<?> mcClass = Minecraft.class;
            Field timerField = mcClass.getDeclaredField("timer"); // mc.timer
            timerField.setAccessible(true);
            try {
                Object timer = timerField.get(Minecraft.getMinecraft());
                Class<?> timerClass = timer.getClass();
                Field timerSpeedField = timerClass.getDeclaredField("timerSpeed"); // mc.timer.timerSpeed
                timerSpeedField.setAccessible(true);
                timerSpeedField.setFloat(timer, timerSpeed); // mc.timer.timerSpeed = timerSpeed
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
}