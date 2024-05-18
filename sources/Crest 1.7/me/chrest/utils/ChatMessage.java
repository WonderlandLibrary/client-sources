// 
// Decompiled by Procyon v0.5.30
// 

package me.chrest.utils;

import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;

public class ChatMessage
{
    private final ChatComponentText message;
    
    private ChatMessage(final ChatComponentText message) {
        this.message = message;
    }
    
    public void displayClientSided() {
        Minecraft.getMinecraft().thePlayer.addChatMessage(this.message);
    }
    
    private ChatComponentText getChatComponent() {
        return this.message;
    }
    
    public static class ChatMessageBuilder
    {
        private static final EnumChatFormatting defaultMessageColor;
        private static final ChatComponentText defaultPrefix;
        private ChatComponentText theMessage;
        private boolean useDefaultMessageColor;
        private ChatStyle workingStyle;
        private ChatComponentText workerMessage;
        
        static {
            defaultMessageColor = EnumChatFormatting.WHITE;
            defaultPrefix = new ChatMessageBuilder(false, false).appendText("&1Crest 1.7: ").setColor(EnumChatFormatting.BLUE).build().getChatComponent();
        }
        
        public ChatMessageBuilder(final boolean prependDefaultPrefix, final boolean useDefaultMessageColor) {
            this.theMessage = new ChatComponentText("");
            this.useDefaultMessageColor = false;
            this.workingStyle = new ChatStyle();
            this.workerMessage = new ChatComponentText("");
            if (prependDefaultPrefix) {
                this.theMessage.appendSibling(ChatMessageBuilder.defaultPrefix);
            }
            this.useDefaultMessageColor = useDefaultMessageColor;
        }
        
        public ChatMessageBuilder() {
            this.theMessage = new ChatComponentText("");
            this.useDefaultMessageColor = false;
            this.workingStyle = new ChatStyle();
            this.workerMessage = new ChatComponentText("");
        }
        
        public ChatMessageBuilder appendText(final String text) {
            this.appendSibling();
            this.workerMessage = new ChatComponentText(text);
            this.workingStyle = new ChatStyle();
            if (this.useDefaultMessageColor) {
                this.setColor(ChatMessageBuilder.defaultMessageColor);
            }
            return this;
        }
        
        public ChatMessageBuilder setColor(final EnumChatFormatting color) {
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
