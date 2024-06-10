package me.sleepyfish.smok.rats.impl.visual;

import me.sleepyfish.smok.rats.Rat;
import me.sleepyfish.smok.rats.settings.DoubleSetting;

// Class from SMok Client by SleepyFish
public class Chams extends Rat {

    public static DoubleSetting red;
    public static DoubleSetting blue;
    public static DoubleSetting green;

    // Mixin module
    public Chams() {
        super("Chams", Rat.Category.Visuals, "Render players through walls");
    }

    @Override
    public void setup() {
        this.addSetting(red = new DoubleSetting("Red", 0.0, 0.0, 255.0, 1.0));
        this.addSetting(green = new DoubleSetting("Green", 0.0, 0.0, 255.0, 1.0));
        this.addSetting(blue = new DoubleSetting("Blue", 0.0, 0.0, 255.0, 1.0));
    }

}