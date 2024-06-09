package me.finz0.osiris.module.modules.gui;

import de.Hero.settings.Setting;
import me.finz0.osiris.module.Module;

import java.util.ArrayList;

public class CurrentHole extends Module {
    public CurrentHole() {
        super("Hole", Category.GUI);
        ArrayList<String> modes = new ArrayList<>();
        modes.add("Texture");
        modes.add("Block");
        modes.add("Safe/Unsafe");
        rSetting(mode = new Setting("Mode", this, "Block", modes, "CurrentHoleComponentMode"));
        rSetting(customFont = new Setting("CFont", this, false, "CurrentHoleComponentCustomFont"));
    }

    public Setting mode;
    public Setting customFont;
}
