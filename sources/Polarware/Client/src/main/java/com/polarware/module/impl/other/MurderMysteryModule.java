package com.polarware.module.impl.other;

import com.polarware.module.Module;
import com.polarware.module.api.Category;
import com.polarware.module.api.ModuleInfo;
import com.polarware.event.bus.Listener;
import com.polarware.event.annotations.EventLink;
import com.polarware.event.impl.motion.PreMotionEvent;
import com.polarware.event.impl.other.WorldChangeEvent;
import com.polarware.util.chat.ChatUtil;
import com.polarware.util.player.PlayerUtil;
import net.minecraft.entity.player.EntityPlayer;

@ModuleInfo(name = "module.other.murdermystery.name", description = "module.other.murdermystery.description", category = Category.OTHER)
public final class MurderMysteryModule extends Module {

    private EntityPlayer murderer;

    @EventLink()
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {
        // no need to waste performance so every second tick is enough
        if (mc.thePlayer.ticksExisted % 2 == 0 || this.murderer != null) {
            return;
        }

        for (EntityPlayer player : mc.theWorld.playerEntities) {
            if (player.getHeldItem() != null) {
                if (player.getHeldItem().getDisplayName().contains("Knife")) { // TODO: add other languages
                    ChatUtil.display(PlayerUtil.name(player) + " is The Murderer.");
                    this.murderer = player;
                }
            }
        }
    };

    @EventLink()
    public final Listener<WorldChangeEvent> onWorldChange = event -> this.murderer = null;
}
