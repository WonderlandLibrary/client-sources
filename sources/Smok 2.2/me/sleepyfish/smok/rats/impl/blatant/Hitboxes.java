package me.sleepyfish.smok.rats.impl.blatant;

import me.sleepyfish.smok.rats.Rat;
import me.sleepyfish.smok.rats.settings.DoubleSetting;

public class Hitboxes extends Rat {

    public static DoubleSetting expand;

    public Hitboxes() {
        super("Hitboxes", Category.Blatant, "");
    }

    @Override
    public void setup() {
        this.addSetting(expand = new DoubleSetting("Expand", 0.2, 0.0, 2.0, 0.2));
    }

}