/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.utils;

import me.thekirkayt.client.Client;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;

public class ChatMessage {
    private final ChatComponentText message;

    private ChatMessage(ChatComponentText message) {
        this.message = message;
    }

    public void displayClientSided() {
        Minecraft.getMinecraft();
        Minecraft.thePlayer.addChatMessage(this.message);
    }

    private ChatComponentText getChatComponent() {
        return this.message;
    }

    /* synthetic */ ChatMessage(ChatComponentText chatComponentText, ChatMessage chatMessage) {
        this(chatComponentText);
    }

    public static class ChatMessageBuilder {
        private static final EnumChatFormatting defaultMessageColor = EnumChatFormatting.WHITE;
        private ChatComponentText theMessage = new ChatComponentText("");
        private boolean useDefaultMessageColor = false;
        private ChatStyle workingStyle = new ChatStyle();
        private ChatComponentText workerMessage = new ChatComponentText("");

        public ChatMessageBuilder(boolean prependDefaultPrefix, boolean useDefaultMessageColor) {
            if (prependDefaultPrefix) {
                this.theMessage.appendSibling(new ChatMessageBuilder(false, false).appendText(String.valueOf(String.valueOf(Client.clientName)) + ": ").setColor(EnumChatFormatting.DARK_GRAY).build().getChatComponent());
            }
            this.useDefaultMessageColor = useDefaultMessageColor;
        }

        public ChatMessageBuilder() {
        }

        public ChatMessageBuilder appendText(String text) {
            this.appendSibling();
            this.workerMessage = new ChatComponentText(text);
            this.workingStyle = new ChatStyle();
            if (this.useDefaultMessageColor) {
                this.setColor(defaultMessageColor);
            }
            return this;
        }

        public ChatMessageBuilder setColor(EnumChatFormatting color) {
            this.workingStyle.setColor(color);
            return this;
        }

        public ChatMessageBuilder bold() {
            this.workingStyle.setBold(true);
            return this;
        }

        public ChatMessageBuilder italic() {
            this.workingStyle.setItalic(true);
            return this;
        }

        public ChatMessageBuilder strikethrough() {
            this.workingStyle.setStrikethrough(true);
            return this;
        }

        public ChatMessageBuilder underline() {
            this.workingStyle.setUnderlined(true);
            return this;
        }

        public ChatMessage build() {
            this.appendSibling();
            return new ChatMessage(this.theMessage, null);
        }

        private void appendSibling() {
            this.theMessage.appendSibling(this.workerMessage.setChatStyle(this.workingStyle));
        }
    }

}

