/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui.chat;

import java.util.UUID;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.chat.IChatListener;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.ITextComponent;

public class OverlayChatListener
implements IChatListener {
    private final Minecraft mc;

    public OverlayChatListener(Minecraft minecraft) {
        this.mc = minecraft;
    }

    @Override
    public void say(ChatType chatType, ITextComponent iTextComponent, UUID uUID) {
        this.mc.ingameGUI.setOverlayMessage(iTextComponent, true);
    }
}

