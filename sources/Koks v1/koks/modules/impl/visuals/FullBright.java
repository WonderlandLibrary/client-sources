package koks.modules.impl.visuals;

import koks.event.Event;
import koks.modules.Module;

/**
 * @author avox | lmao | kroko
 * @created on 07.09.2020 : 10:32
 */
public class FullBright extends Module {

    private float savedGammaSetting = mc.gameSettings.gammaSetting;

    public FullBright() {
        super("FullBright", "You can see in darkness", Category.VISUALS);
    }

    @Override
    public void onEvent(Event event) {
        mc.gameSettings.gammaSetting = 100000F;
    }

    @Override
    public void onEnable() {
        savedGammaSetting = mc.gameSettings.gammaSetting;
    }

    @Override
    public void onDisable() {
        mc.gameSettings.gammaSetting = savedGammaSetting;
    }


}
