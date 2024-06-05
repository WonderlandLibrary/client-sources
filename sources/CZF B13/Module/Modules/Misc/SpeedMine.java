package gq.vapu.czfclient.Module.Modules.Misc;

import gq.vapu.czfclient.API.EventHandler;
import gq.vapu.czfclient.API.Events.World.EventPreUpdate;
import gq.vapu.czfclient.API.Value.Numbers;
import gq.vapu.czfclient.Module.Module;
import gq.vapu.czfclient.Module.ModuleType;
import net.minecraft.client.Minecraft;

import java.awt.*;

public class SpeedMine
        extends Module {
    public static Numbers<Double> speed = new Numbers<Double>("Speed", "Speed", 0.7, 0.0, 1.0, 0.1);

    public SpeedMine() {
        super("SpeedMine", new String[]{"SpeedMine", "SpeedMine"}, ModuleType.World);
        this.setColor(new Color(223, 233, 233).getRGB());
        this.addValues(speed);
    }

    @EventHandler
    private void onUpdate(EventPreUpdate e) {
        Minecraft.playerController.blockHitDelay = 0;
        if (Minecraft.playerController.curBlockDamageMP < speed.getValue()) return;
        Minecraft.playerController.curBlockDamageMP = 1.0f;
    }
}
