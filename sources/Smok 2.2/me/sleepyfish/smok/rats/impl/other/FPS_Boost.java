package me.sleepyfish.smok.rats.impl.other;

import me.sleepyfish.smok.rats.Rat;
import me.sleepyfish.smok.rats.settings.BoolSetting;

// Class from SMok Client by SleepyFish
public class FPS_Boost extends Rat {

    public static BoolSetting removeRainAndSnow;
    public static BoolSetting removeParticles;
    public static BoolSetting removeEntities;

    public FPS_Boost() {
        super("Fps Boost", Rat.Category.Other, "Boosts your FPS");
    }

    @Override
    public void setup() {
        this.addSetting(removeRainAndSnow = new BoolSetting("Remove Rain and snow", true));
        this.addSetting(removeParticles = new BoolSetting("Remove Particles", true));
        this.addSetting(removeEntities = new BoolSetting("Remove Entities", false));
    }

}