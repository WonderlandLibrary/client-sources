package me.mees.remix.modules.movement;

import java.util.*;
import me.satisfactory.base.module.*;
import me.mees.remix.modules.movement.flight.*;
import me.satisfactory.base.setting.*;
import net.minecraft.util.*;

public class Flight extends Module
{
    public Flight() {
        super("Flight", 0, Category.MOVE);
        final ArrayList<String> options = new ArrayList<String>();
        options.add("Hypixel");
        options.add("Cubecraft");
        options.add("Mineplex");
        options.add("Guardian");
        options.add("GuardianFast");
        options.add("Mineverse");
        options.add("Fall");
        this.addMode(new Hypixel(this));
        this.addMode(new Cubecraft(this));
        this.addMode(new Mineplex(this));
        this.addMode(new Guardian(this));
        this.addMode(new GuardianFast(this));
        this.addMode(new Mineverse(this));
        this.addMode(new Fall(this));
        this.addSetting(new Setting("FlightMode", this, "Hypixel", options));
        this.addSetting(new Setting("Hypixel boost", this, true));
        this.addSetting(new Setting("Boost", this, 1.4, 1.0, 2.0, false, 0.05));
        this.addSetting(new Setting("Guardian Speed", this, 6.0, 1.0, 10.0, true, 1.0));
    }
    
    @Override
    public void onDisable() {
        final Timer timer = Flight.mc.timer;
        Timer.timerSpeed = 1.0f;
        super.onDisable();
    }
}
