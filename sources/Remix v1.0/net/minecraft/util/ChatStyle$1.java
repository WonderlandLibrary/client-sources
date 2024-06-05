package net.minecraft.util;

import net.minecraft.event.*;

static final class ChatStyle$1 extends ChatStyle {
    @Override
    public EnumChatFormatting getColor() {
        return null;
    }
    
    @Override
    public boolean getBold() {
        return false;
    }
    
    @Override
    public boolean getItalic() {
        return false;
    }
    
    @Override
    public boolean getStrikethrough() {
        return false;
    }
    
    @Override
    public boolean getUnderlined() {
        return false;
    }
    
    @Override
    public boolean getObfuscated() {
        return false;
    }
    
    @Override
    public ClickEvent getChatClickEvent() {
        return null;
    }
    
    @Override
    public HoverEvent getChatHoverEvent() {
        return null;
    }
    
    @Override
    public String getInsertion() {
        return null;
    }
    
    @Override
    public ChatStyle setColor(final EnumChatFormatting color) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public ChatStyle setBold(final Boolean p_150227_1_) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public ChatStyle setItalic(final Boolean italic) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public ChatStyle setStrikethrough(final Boolean strikethrough) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public ChatStyle setUnderlined(final Boolean underlined) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public ChatStyle setObfuscated(final Boolean obfuscated) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public ChatStyle setChatClickEvent(final ClickEvent event) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public ChatStyle setChatHoverEvent(final HoverEvent event) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public ChatStyle setParentStyle(final ChatStyle parent) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public String toString() {
        return "Style.ROOT";
    }
    
    @Override
    public ChatStyle createShallowCopy() {
        return this;
    }
    
    @Override
    public ChatStyle createDeepCopy() {
        return this;
    }
    
    @Override
    public String getFormattingCode() {
        return "";
    }
}