// 
// Decompiled by Procyon v0.5.30
// 

package com.klintos.twelve.mod.cmd;

import net.minecraft.client.Minecraft;

public class ClearChat extends Cmd
{
    public ClearChat() {
        super("cc", "Clears the chat.", "cc");
    }
    
    @Override
    public void runCmd(final String msg, final String[] args) {
        Minecraft.getMinecraft().ingameGUI.persistantChatGUI.clearChatMessages();
    }
}
