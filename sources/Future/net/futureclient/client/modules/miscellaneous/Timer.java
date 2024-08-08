package net.futureclient.client.modules.miscellaneous;

import net.futureclient.loader.mixin.common.wrapper.IMinecraft;
import net.futureclient.loader.mixin.common.wrapper.ITimer;
import net.minecraft.client.Minecraft;
import net.futureclient.client.modules.miscellaneous.timer.Listener1;
import net.futureclient.client.n;
import net.futureclient.client.Category;
import net.futureclient.client.utils.NumberValue;
import net.futureclient.client.utils.Value;
import net.futureclient.client.Ea;

public class Timer extends Ea
{
    private Value<Boolean> tPSSync;
    private NumberValue speed;
    
    public Timer() {
        super("Timer", new String[] { "Timer", "Timr", "GameSpeed", "Speedup", "Taimer" }, true, -31470, Category.MISCELLANEOUS);
        this.tPSSync = new Value<Boolean>(false, new String[] { "TPSSync", "Sync", "TPSSynchronize", "Synchronize" });
        this.speed = new NumberValue(1.0f, 0.1f, 50.0f, new String[] { "Speed", "TimerSpeed", "Timer", "GameSpeed" });
        this.M(new Value[] { this.tPSSync, this.speed });
        this.M(new n[] { new Listener1(this) });
    }
    
    public static Minecraft getMinecraft() {
        return Timer.D;
    }
    
    public static Minecraft getMinecraft1() {
        return Timer.D;
    }
    
    public static Minecraft getMinecraft2() {
        return Timer.D;
    }
    
    public void b() {
        ((ITimer)((IMinecraft)Timer.D).getTimer()).setTimerSpeed(1.0f);
        super.b();
    }
    
    public static Minecraft getMinecraft3() {
        return Timer.D;
    }
    
    public static Minecraft getMinecraft4() {
        return Timer.D;
    }
    
    public static Minecraft getMinecraft5() {
        return Timer.D;
    }
    
    public static NumberValue M(final Timer timer) {
        return timer.speed;
    }
    
    public static Value M(final Timer timer) {
        return timer.tPSSync;
    }
}
