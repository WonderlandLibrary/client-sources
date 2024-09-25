package eze.modules.player;

import eze.modules.*;
import eze.util.*;
import eze.settings.*;
import eze.events.*;
import eze.events.listeners.*;

public class AutoSetting extends Module
{
    public static boolean enabled;
    public static boolean isHypixel;
    public static boolean isMineplex;
    public static boolean isRedesky;
    public static boolean isOldVerus;
    Timer timer;
    public ModeSetting server;
    
    static {
        AutoSetting.enabled = false;
        AutoSetting.isHypixel = false;
        AutoSetting.isMineplex = false;
        AutoSetting.isRedesky = false;
        AutoSetting.isOldVerus = false;
    }
    
    public AutoSetting() {
        super("AutoSetting", 0, Category.PLAYER);
        this.timer = new Timer();
        this.server = new ModeSetting("Server", "Hypixel", new String[] { "Hypixel", "Mineplex", "Redesky", "OldVerus" });
        this.addSettings(this.server);
    }
    
    @Override
    public void onEnable() {
        AutoSetting.enabled = true;
    }
    
    @Override
    public void onDisable() {
        AutoSetting.enabled = false;
    }
    
    @Override
    public void onEvent(final Event e) {
        if (e instanceof EventUpdate) {
            if (this.server.is("Hypixel")) {
                AutoSetting.isHypixel = true;
            }
            else {
                AutoSetting.isHypixel = false;
            }
            if (this.server.is("Mineplex")) {
                AutoSetting.isMineplex = true;
            }
            else {
                AutoSetting.isMineplex = false;
            }
            if (this.server.is("Redesky")) {
                AutoSetting.isRedesky = true;
            }
            else {
                AutoSetting.isRedesky = false;
            }
            if (this.server.is("OldVerus")) {
                AutoSetting.isOldVerus = true;
            }
            else {
                AutoSetting.isOldVerus = false;
            }
        }
    }
}
