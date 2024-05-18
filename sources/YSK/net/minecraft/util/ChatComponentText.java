package net.minecraft.util;

import java.util.*;

public class ChatComponentText extends ChatComponentStyle
{
    private final String text;
    private static final String[] I;
    
    @Override
    public IChatComponent createCopy() {
        return this.createCopy();
    }
    
    static {
        I();
    }
    
    public ChatComponentText(final String text) {
        this.text = text;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return " ".length() != 0;
        }
        if (!(o instanceof ChatComponentText)) {
            return "".length() != 0;
        }
        if (this.text.equals(((ChatComponentText)o).getChatComponentText_TextValue()) && super.equals(o)) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    @Override
    public ChatComponentText createCopy() {
        final ChatComponentText chatComponentText = new ChatComponentText(this.text);
        chatComponentText.setChatStyle(this.getChatStyle().createShallowCopy());
        final Iterator<IChatComponent> iterator = this.getSiblings().iterator();
        "".length();
        if (4 != 4) {
            throw null;
        }
        while (iterator.hasNext()) {
            chatComponentText.appendSibling(iterator.next().createCopy());
        }
        return chatComponentText;
    }
    
    private static void I() {
        (I = new String["   ".length()])["".length()] = I("\u001b+>3\u0007 #6(** 2<0*62zc", "ONFGD");
        ChatComponentText.I[" ".length()] = I("EO\u0012\u0003,\u0005\u0006\u000f\r=T", "ioajN");
        ChatComponentText.I["  ".length()] = I("{s<\u0011\u0001;6r", "WSOex");
    }
    
    public String getChatComponentText_TextValue() {
        return this.text;
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (4 <= 1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public String getUnformattedTextForChat() {
        return this.text;
    }
    
    @Override
    public String toString() {
        return ChatComponentText.I["".length()] + this.text + (char)(0x75 ^ 0x52) + ChatComponentText.I[" ".length()] + this.siblings + ChatComponentText.I["  ".length()] + this.getChatStyle() + (char)(0x20 ^ 0x5D);
    }
}
