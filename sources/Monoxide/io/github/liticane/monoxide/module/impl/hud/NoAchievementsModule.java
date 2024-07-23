package io.github.liticane.monoxide.module.impl.hud;

import io.github.liticane.monoxide.module.api.Module;
import io.github.liticane.monoxide.module.api.data.ModuleData;
import io.github.liticane.monoxide.module.api.ModuleCategory;
import io.github.liticane.monoxide.listener.event.minecraft.network.PacketEvent;
import io.github.liticane.monoxide.listener.radbus.Listen;
import net.minecraft.network.play.server.S37PacketStatistics;

@ModuleData(name = "NoAchievements", description = "Removes the annoying achievements.", category = ModuleCategory.HUD)
public class NoAchievementsModule extends Module {

    @Listen
    public void onPacketEvent(PacketEvent event) {
        if(mc.thePlayer == null || mc.theWorld == null) {
            return;
        }

        if (event.getType() == PacketEvent.Type.INCOMING) {
            if(event.getPacket() instanceof S37PacketStatistics) {
                event.setCancelled(true);
            }
        }
    }

    @Override
    public void onEnable() {}

    @Override
    public void onDisable() {}

}
