package me.mees.remix.modules.world;

import java.util.*;
import me.satisfactory.base.setting.*;
import me.satisfactory.base.module.*;
import me.mees.remix.modules.world.antibot.*;

public class AntiBot extends Module
{
    public AntiBot() {
        super("AntiBot", 0, Category.COMBAT);
        final ArrayList<String> options = new ArrayList<String>();
        options.add("Hypixel");
        options.add("Mineplex");
        this.addSetting(new Setting("AntibotMode", this, "Hypixel", options));
        this.addMode(new Hypixel(this));
        this.addMode(new Mineplex(this));
    }
}
