package me.sleepyfish.smok.rats.impl.other;

import me.sleepyfish.smok.rats.Rat;
import me.sleepyfish.smok.rats.settings.BoolSetting;
import me.sleepyfish.smok.rats.settings.ModeSetting;

// Class from SMok Client by SleepyFish
public class Animations extends Rat {
    public static BoolSetting removeHand;
    public static ModeSetting<Enum<?>> mode;

    public Animations() {
        super("Animations", Rat.Category.Other, "Modify ur First person Hand");
    }

    @Override
    public void setup() {
        this.addSetting(mode = new ModeSetting<>("Mode", Animations.modes.Goober));
        this.addSetting(removeHand = new BoolSetting("Remove hand", "Removes your first pirson Hand", false));
    }

    public enum modes {
        Swing, HighSwing, Reversed, Goober, Sided, Wonky, Spin;
    }

}