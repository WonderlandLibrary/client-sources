/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui.chat;

import com.mojang.text2speech.Narrator;
import java.util.UUID;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.chat.IChatListener;
import net.minecraft.client.settings.NarratorStatus;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NarratorChatListener
implements IChatListener {
    public static final ITextComponent EMPTY = StringTextComponent.EMPTY;
    private static final Logger LOGGER = LogManager.getLogger();
    public static final NarratorChatListener INSTANCE = new NarratorChatListener();
    private final Narrator narrator = Narrator.getNarrator();

    @Override
    public void say(ChatType chatType, ITextComponent iTextComponent, UUID uUID) {
    }

    public void say(String string) {
    }

    private static NarratorStatus getNarratorStatus() {
        return Minecraft.getInstance().gameSettings.narrator;
    }

    private void say(boolean bl, String string) {
    }

    public void announceMode(NarratorStatus narratorStatus) {
    }

    public boolean isActive() {
        return this.narrator.active();
    }

    public void clear() {
    }

    public void close() {
        this.narrator.destroy();
    }
}

