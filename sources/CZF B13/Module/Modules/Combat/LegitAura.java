package gq.vapu.czfclient.Module.Modules.Combat;

import gq.vapu.czfclient.API.EventUpdate;
import gq.vapu.czfclient.API.eventapi.EventTarget;
import gq.vapu.czfclient.Module.Module;
import gq.vapu.czfclient.Module.ModuleType;
import gq.vapu.czfclient.Util.TimerUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;

import java.util.Random;

public class LegitAura extends Module {
    public LegitAura() {
        super("LegitAura", new String[]{"la"}, ModuleType.Combat);
    }

    @EventTarget
    public void ms(EventUpdate e) {
        TimerUtil t = new TimerUtil();
        boolean Bool;
        Bool = new Random().nextBoolean();
        if (mc.objectMouseOver.entityHit instanceof Entity) {
            if (t.delay(new Random(1).nextInt(100 - 50 + 1) + 50))
                mc.thePlayer.swingItem();
            Minecraft.playerController.attackEntity(mc.thePlayer, Minecraft.getMinecraft().objectMouseOver.entityHit);
        }
    }
}
