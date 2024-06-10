package me.kaimson.melonclient.features.modules;

import me.kaimson.melonclient.features.*;
import me.kaimson.melonclient.utils.*;

public class TimeChangerModule extends Module
{
    public static TimeChangerModule INSTANCE;
    public final Setting time;
    
    public TimeChangerModule() {
        super("Time Changer");
        new Setting(this, "Time Options");
        this.time = new Setting(this, "Time").setDefault(0).setRange("Vanilla", "Day", "Sunset", "Night");
        TimeChangerModule.INSTANCE = this;
    }
}
