/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.client.module.modules.auto;

import me.thekirkayt.client.module.Module;
import me.thekirkayt.event.EventTarget;
import me.thekirkayt.event.events.UpdateEvent;
import me.thekirkayt.utils.ClientUtils;
import net.minecraft.client.entity.EntityPlayerSP;

@Module.Mod
public class AutoJump
extends Module {
    @EventTarget
    private void onPreUpdate(UpdateEvent event) {
        if (ClientUtils.player().onGround) {
            ClientUtils.player().jump();
        }
    }
}

