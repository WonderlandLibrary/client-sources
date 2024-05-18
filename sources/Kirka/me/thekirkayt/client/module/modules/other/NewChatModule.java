/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.client.module.modules.other;

import me.thekirkayt.client.module.Module;
import me.thekirkayt.utils.SonusChat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.GuiNewChat;

@Module.Mod(displayName="NewChat")
public class NewChatModule
extends Module {
    private Minecraft mc = Minecraft.getMinecraft();

    @Override
    public void enable() {
        this.mc.ingameGUI.persistantChatGUI = new SonusChat(this.mc);
        super.enable();
    }

    @Override
    public void disable() {
        this.mc.ingameGUI.persistantChatGUI = new GuiNewChat(this.mc);
        super.disable();
    }
}

