// 
// Decompiled by Procyon v0.5.30
// 

package me.chrest.client.module.modules.misc;

import me.chrest.event.EventTarget;
import org.lwjgl.input.Keyboard;
import net.minecraft.client.gui.GuiChat;
import me.chrest.utils.ClientUtils;
import me.chrest.event.events.Render2DEvent;
import me.chrest.client.module.Module;

@Mod(displayName = "InventoryMove")
public class ScreenWalk extends Module
{
    @EventTarget
    private void onRender(final Render2DEvent event) {
        if (ClientUtils.mc().currentScreen != null && !(ClientUtils.mc().currentScreen instanceof GuiChat)) {
            if (Keyboard.isKeyDown(200)) {
                ClientUtils.pitch(ClientUtils.pitch() - 2.0f);
            }
            if (Keyboard.isKeyDown(208)) {
                ClientUtils.pitch(ClientUtils.pitch() + 2.0f);
            }
            if (Keyboard.isKeyDown(203)) {
                ClientUtils.yaw(ClientUtils.yaw() - 3.0f);
            }
            if (Keyboard.isKeyDown(205)) {
                ClientUtils.yaw(ClientUtils.yaw() + 3.0f);
            }
        }
    }
}
