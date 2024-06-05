package me.mees.remix.modules.player;

import java.util.*;
import me.satisfactory.base.setting.*;
import me.satisfactory.base.module.*;
import me.mees.remix.modules.player.regen.*;

public class Regen extends Module
{
    public Regen() {
        super("Regen", 0, Category.PLAYER);
        final ArrayList<String> options = new ArrayList<String>();
        options.add("Packet");
        options.add("Guardian");
        this.addSetting(new Setting("RegenMode", this, "Packet", options));
        this.addSetting(new Setting("RegenPackets", this, 100.0, 10.0, 1000.0, false, 10.0));
        this.addMode(new Packet(this));
        this.addMode(new Guardian(this));
    }
}
