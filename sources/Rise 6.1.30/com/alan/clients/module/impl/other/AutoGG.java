package com.alan.clients.module.impl.other;

import com.alan.clients.event.Listener;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.motion.PreMotionEvent;
import com.alan.clients.event.impl.other.WorldChangeEvent;
import com.alan.clients.module.Module;
import com.alan.clients.module.api.Category;
import com.alan.clients.module.api.ModuleInfo;
import com.alan.clients.value.impl.StringValue;

@ModuleInfo(aliases = {"module.other.autogg.name"}, description = "module.other.autogg.description", category = Category.PLAYER)
public final class AutoGG extends Module {

    private final StringValue message = new StringValue("Message", this, "Why waste another game without Rise?");
    private boolean active, worldChanged;

    @EventLink
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {
        if (mc.thePlayer.ticksExisted % 18 != 0 || !mc.thePlayer.sendQueue.doneLoadingTerrain || mc.isIntegratedServerRunning()) {
            return;
        }

        if (mc.theWorld.playerEntities.stream().filter(entityPlayer -> !entityPlayer.isInvisible() || entityPlayer == mc.thePlayer).count() <= 1) {
            if (active) {
                mc.thePlayer.sendChatMessage(message.getValue());
                active = false;
            }
        } else if (worldChanged) {
            active = true;
            worldChanged = false;
        }
    };

    @EventLink
    public final Listener<WorldChangeEvent> onWorldChange = event -> {
        worldChanged = true;
    };

    @Override
    public void onEnable() {
        worldChanged = true;
    }
}