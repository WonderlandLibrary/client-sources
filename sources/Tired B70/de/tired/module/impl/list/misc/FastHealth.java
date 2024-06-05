package de.tired.module.impl.list.misc;

import de.tired.api.annotations.ModuleAnnotation;
import de.tired.api.logger.impl.IngameChatLog;
import de.tired.event.EventTarget;
import de.tired.event.events.PacketEvent;
import de.tired.event.events.UpdateEvent;
import de.tired.module.Module;
import de.tired.module.ModuleCategory;
import com.github.creeper123123321.viafabric.ViaFabric;
import net.minecraft.network.play.client.C03PacketPlayer;

@ModuleAnnotation(name = "FastHealth", category = ModuleCategory.MISC)
public class FastHealth extends Module {

    @EventTarget
    public void onPacket(PacketEvent e) {

        if (e.getPacket() instanceof C03PacketPlayer) {
            if (MC.thePlayer.getFoodStats().getFoodLevel() > 15 && MC.thePlayer.getHealth() < 15 && MC.thePlayer.getHealth() != 0) {
                for (int i = 0; i < 2; i++) {
                    e.setPacket(new C03PacketPlayer());
                }
            }
        }
    }

    @EventTarget
    public void onUpdate(UpdateEvent e) {

    }


    @Override
    public void onState() {

        if (ViaFabric.clientSideVersion == 47 || ViaFabric.clientSideVersion < 47) {

            IngameChatLog.INGAME_CHAT_LOG.doLog("Need 1.9 or higher.");

        }

    }

    @Override
    public void onUndo() {

    }
}
