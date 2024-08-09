package net.minecraft.client.gui.chat;

import com.mojang.text2speech.Narrator;

import java.util.UUID;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.toasts.SystemToast;
import net.minecraft.client.gui.toasts.ToastGui;
import net.minecraft.client.settings.NarratorStatus;
import net.minecraft.util.SharedConstants;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NarratorChatListener implements IChatListener {
    public static final ITextComponent EMPTY = StringTextComponent.EMPTY;
    private static final Logger LOGGER = LogManager.getLogger();
    public static final NarratorChatListener INSTANCE = new NarratorChatListener();
    private final Narrator narrator = Narrator.getNarrator();

    /**
     * Called whenever this listener receives a chat message, if this listener is registered to the given type in {@link
     * net.minecraft.client.gui.GuiIngame#chatListeners chatListeners}
     */
    public void say(ChatType chatTypeIn, ITextComponent message, UUID sender) {

    }

    public void say(String msg) {

    }

    private static NarratorStatus getNarratorStatus() {
        return Minecraft.getInstance().gameSettings.narrator;
    }

    private void say(boolean interrupt, String msg) {
    }

    public void announceMode(NarratorStatus status) {
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
