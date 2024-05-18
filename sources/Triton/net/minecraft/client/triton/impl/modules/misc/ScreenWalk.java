// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.client.triton.impl.modules.misc;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.triton.management.event.EventTarget;
import net.minecraft.client.triton.management.event.events.Render2DEvent;
import net.minecraft.client.triton.management.module.Module;
import net.minecraft.client.triton.management.module.Module.Mod;
import net.minecraft.client.triton.utils.ClientUtils;

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
