package me.mees.remix.modules.movement;

import java.util.*;
import me.satisfactory.base.setting.*;
import me.satisfactory.base.module.*;
import me.mees.remix.modules.movement.longjump.*;
import java.math.*;

public class Longjump extends Module
{
    public Longjump() {
        super("Longjump", 0, Category.MOVE);
        final ArrayList<String> options = new ArrayList<String>();
        options.add("NCP");
        options.add("Guardian");
        options.add("Hypixel");
        options.add("Cubecraft");
        options.add("Mineplex");
        this.addSetting(new Setting("LongjumpMode", this, "NCP", options));
        this.addMode(new NCP(this));
        this.addMode(new Guardian(this));
        this.addMode(new Hypixel(this));
        this.addMode(new Cubecraft(this));
        this.addMode(new Mineplex(this));
    }
    
    public double round(final double value, final int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
