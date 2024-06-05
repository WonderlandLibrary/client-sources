package gq.vapu.czfclient.Module.Modules.Blatant;

import gq.vapu.czfclient.API.EventHandler;
import gq.vapu.czfclient.API.Events.World.EventPreUpdate;
import gq.vapu.czfclient.Module.Module;
import gq.vapu.czfclient.Module.ModuleType;

public class AirJump extends Module {
    public AirJump() {
        super("AirJump", new String[]{}, ModuleType.Blatant);
        Chinese = "ø’÷–Ã¯‘æ";
    }

    @Override
    public void onEnable() {
        this.setSuffix("Verus");
        super.onEnable();
    }

    @EventHandler
    public void onUpdate(EventPreUpdate e) {
        if (mc.gameSettings.keyBindJump.isPressed()) {
            mc.thePlayer.jump();
        }
    }
}
