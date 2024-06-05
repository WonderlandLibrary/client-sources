package me.mees.remix.modules.movement;

import me.satisfactory.base.utils.timer.*;
import java.util.*;
import me.satisfactory.base.setting.*;
import me.satisfactory.base.module.*;
import me.mees.remix.modules.movement.speed.*;
import java.math.*;
import me.satisfactory.base.utils.*;
import net.minecraft.util.*;

public class Speed extends Module
{
    public static double speed;
    public static int stage;
    public static double moveSpeed;
    public static TimerUtil timer;
    public static double x;
    public static double z;
    public static float ground;
    public static int ticks;
    
    public Speed() {
        super("Speed", 0, Category.MOVE);
        final ArrayList<String> options = new ArrayList<String>();
        options.add("BHop");
        options.add("Lowhop");
        options.add("Hypixel");
        options.add("Mineplex");
        options.add("GuardianHop");
        options.add("Mineman");
        options.add("AAC");
        options.add("DEV");
        this.addSetting(new Setting("SpeedMode", this, "BHop", options));
        this.addMode(new BHop(this));
        this.addMode(new Lowhop(this));
        this.addMode(new Hypixel(this));
        this.addMode(new Mineplex(this));
        this.addMode(new GuardianHop(this));
        this.addMode(new Mineman(this));
        this.addMode(new AAC(this));
        this.addMode(new DEV(this));
    }
    
    public double round(final double value, final int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
    
    @Override
    public void onEnable() {
        Speed.timer.reset();
        Speed.moveSpeed = MiscellaneousUtil.getBaseMoveSpeed();
        Speed.speed = 1.0;
        Speed.stage = 2;
        super.onEnable();
    }
    
    @Override
    public void onDisable() {
        final Timer timer = Speed.mc.timer;
        Timer.timerSpeed = 1.0f;
        Speed.mc.thePlayer.speedInAir = 0.02f;
        Speed.timer.reset();
        Speed.moveSpeed = MiscellaneousUtil.getBaseMoveSpeed();
        Speed.speed = 1.0;
        Speed.stage = 2;
        super.onDisable();
    }
    
    static {
        Speed.timer = new TimerUtil();
        Speed.ground = 0.0f;
    }
}
