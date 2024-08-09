/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.gui;

import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.screen.VideoSettingsScreen;
import net.optifine.Config;
import net.optifine.shaders.Shaders;

public class GuiChatOF
extends ChatScreen {
    private static final String CMD_RELOAD_SHADERS = "/reloadShaders";
    private static final String CMD_RELOAD_CHUNKS = "/reloadChunks";

    public GuiChatOF(ChatScreen chatScreen) {
        super(VideoSettingsScreen.getGuiChatText(chatScreen));
    }

    @Override
    public void sendMessage(String string) {
        if (this.checkCustomCommand(string)) {
            this.minecraft.ingameGUI.getChatGUI().addToSentMessages(string);
        } else {
            super.sendMessage(string);
        }
    }

    private boolean checkCustomCommand(String string) {
        if (string == null) {
            return true;
        }
        if ((string = string.trim()).equals(CMD_RELOAD_SHADERS)) {
            if (Config.isShaders()) {
                Shaders.uninit();
                Shaders.loadShaderPack();
            }
            return false;
        }
        if (string.equals(CMD_RELOAD_CHUNKS)) {
            this.minecraft.worldRenderer.loadRenderers();
            return false;
        }
        return true;
    }
}

