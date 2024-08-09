package dev.excellent.client.module.impl.player;

import dev.excellent.api.event.impl.player.RemoveEntityEvent;
import dev.excellent.api.interfaces.event.Listener;
import dev.excellent.client.module.api.Category;
import dev.excellent.client.module.api.Module;
import dev.excellent.client.module.api.ModuleInfo;
import dev.excellent.impl.util.chat.ChatUtil;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.entity.player.ClientPlayerEntity;

@ModuleInfo(name = "Leave Tracker", description = "Отображает координаты куда телепортировался игрок", category = Category.PLAYER)
public class LeaveTracker extends Module {
    private final Listener<RemoveEntityEvent> onRemove = event -> {
        if (event.getEntity() instanceof AbstractClientPlayerEntity clientPlayer) {
            if (clientPlayer instanceof ClientPlayerEntity || mc.player.getDistance(clientPlayer) < 100) {
                return;
            }
            String x = String.format("%.1f", clientPlayer.getPosX());
            String y = String.format("%.1f", clientPlayer.getPosY());
            String z = String.format("%.1f", clientPlayer.getPosZ());

            ChatUtil.addText(String.format("Игрок %s телепортировался на координаты x: %s y: %s z: %s", clientPlayer.getNameClear(), x, y, z));
        }
    };
}
