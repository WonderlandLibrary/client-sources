/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.client.module.modules.player;

import me.thekirkayt.client.module.Module;
import me.thekirkayt.event.EventTarget;
import me.thekirkayt.event.events.Render2DEvent;
import me.thekirkayt.utils.ClientUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.input.Keyboard;

@Module.Mod(displayName="InventoryMove")
public class InventoryMove
extends Module {
    @EventTarget
    private void onRender(Render2DEvent event) {
        ClientUtils.mc();
        if (Minecraft.currentScreen != null) {
            ClientUtils.mc();
            if (!(Minecraft.currentScreen instanceof GuiChat)) {
                if (Keyboard.isKeyDown((int)200)) {
                    ClientUtils.pitch(ClientUtils.pitch() - 2.0f);
                }
                if (Keyboard.isKeyDown((int)208)) {
                    ClientUtils.pitch(ClientUtils.pitch() + 2.0f);
                }
                if (Keyboard.isKeyDown((int)203)) {
                    ClientUtils.yaw(ClientUtils.yaw() - 3.0f);
                }
                if (Keyboard.isKeyDown((int)205)) {
                    ClientUtils.yaw(ClientUtils.yaw() + 3.0f);
                }
            }
        }
    }
}

