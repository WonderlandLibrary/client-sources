package de.tired.module.impl.list.misc;

import de.tired.api.annotations.ModuleAnnotation;
import de.tired.event.EventTarget;
import de.tired.event.events.UpdateEvent;
import de.tired.module.ModuleCategory;
import de.tired.module.Module;
import net.minecraft.network.play.client.C03PacketPlayer;

import java.util.Random;

@ModuleAnnotation(name = "HawkGodMode", category = ModuleCategory.MISC, clickG = "Gives you godmode on hawk")
public class HawkGodMode extends Module {

    @EventTarget
    public void onUpdate(UpdateEvent e) {
        final Random random = new Random();
        if (MC.thePlayer.ticksExisted % 32 == 0) {
            sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(MC.thePlayer.posX, Math.toRadians(MC.thePlayer.posY) / (12 - random.nextInt(12)), MC.thePlayer.posZ, MC.thePlayer.ticksExisted % 12 == 0));
        }

    }

    @Override
    public void onState() {

    }

    @Override
    public void onUndo() {

    }
}
