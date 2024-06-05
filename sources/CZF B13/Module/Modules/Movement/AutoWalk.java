package gq.vapu.czfclient.Module.Modules.Movement;

import gq.vapu.czfclient.Module.Module;
import gq.vapu.czfclient.Module.ModuleType;

public class AutoWalk extends Module {

    public AutoWalk() {
        super("AutoWalk", new String[]{"AutoWalk"}, ModuleType.Movement);
    }

    @Override
    public void onDisable() {

        mc.gameSettings.keyBindForward.unpressKey();
        super.onDisable();
    }

    @Override
    public void onEnable() {

        super.onEnable();
    }

}
