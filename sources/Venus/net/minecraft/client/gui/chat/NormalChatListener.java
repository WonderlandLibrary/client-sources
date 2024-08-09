/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui.chat;

import java.util.UUID;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.chat.IChatListener;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.ITextComponent;

public class NormalChatListener
implements IChatListener {
    private final Minecraft mc;

    public NormalChatListener(Minecraft minecraft) {
        this.mc = minecraft;
    }

    @Override
    public void say(ChatType chatType, ITextComponent iTextComponent, UUID uUID) {
        if (chatType != ChatType.CHAT) {
            this.mc.ingameGUI.getChatGUI().printChatMessage(iTextComponent);
        } else {
            this.mc.ingameGUI.getChatGUI().func_238495_b_(iTextComponent);
        }
    }
}

