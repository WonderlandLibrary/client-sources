/*
 * Decompiled with CFR 0.150.
 */
package skizzle.modules.other;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import skizzle.events.Event;
import skizzle.modules.Module;
import skizzle.modules.ModuleManager;
import skizzle.util.Timer;

public class AutoModule
extends Module {
    public Timer timer2;
    public Timer timer = new Timer();

    public static {
        throw throwable;
    }

    public AutoModule() {
        super(Qprot0.0("\u68d5\u71de\u539c\ua7eb\u4226\ued41\u8c2b\u04a4\u570e\ue944"), 0, Module.Category.OTHER);
        AutoModule Nigga;
        Nigga.timer2 = new Timer();
        Nigga.onDisable();
    }

    @Override
    public void onEvent(Event Nigga) {
        AutoModule Nigga2;
        if (Nigga2.mc.thePlayer != null) {
            for (Object Nigga3 : Minecraft.theWorld.playerEntities) {
                EntityPlayer Nigga4 = (EntityPlayer)Nigga3;
                if (!ModuleManager.targeting.isTarget(Nigga4) || !(Nigga4.getDistanceToEntity(Nigga2.mc.thePlayer) < Float.intBitsToFloat(1.04196608E9f ^ 0x7EBB2415)) || ModuleManager.killaura.isEnabled()) continue;
                ModuleManager.killaura.toggle();
            }
        }
    }
}

