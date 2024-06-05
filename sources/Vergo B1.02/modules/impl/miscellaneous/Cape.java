package xyz.vergoclient.modules.impl.miscellaneous;

import xyz.vergoclient.modules.Module;
import xyz.vergoclient.settings.ModeSetting;

import java.util.Arrays;

public class Cape extends Module {

    public ModeSetting mode = new ModeSetting("Cape", "Red", "Red", "Blue");

    public Cape() {
        super("Cape", Category.MISCELLANEOUS);
    }

    @Override
    public void loadSettings() {
        mode.modes.addAll(Arrays.asList("Red", "Blue"));

        addSettings(mode);
    }

}