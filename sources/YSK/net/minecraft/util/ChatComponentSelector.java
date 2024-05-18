package net.minecraft.util;

import java.util.*;

public class ChatComponentSelector extends ChatComponentStyle
{
    private static final String[] I;
    private final String selector;
    
    public ChatComponentSelector(final String selector) {
        this.selector = selector;
    }
    
    @Override
    public ChatComponentSelector createCopy() {
        final ChatComponentSelector chatComponentSelector = new ChatComponentSelector(this.selector);
        chatComponentSelector.setChatStyle(this.getChatStyle().createShallowCopy());
        final Iterator<IChatComponent> iterator = this.getSiblings().iterator();
        "".length();
        if (3 < 3) {
            throw null;
        }
        while (iterator.hasNext()) {
            chatComponentSelector.appendSibling(iterator.next().createCopy());
        }
        return chatComponentSelector;
    }
    
    public String getSelector() {
        return this.selector;
    }
    
    @Override
    public String toString() {
        return ChatComponentSelector.I["".length()] + this.selector + (char)(0xF ^ 0x28) + ChatComponentSelector.I[" ".length()] + this.siblings + ChatComponentSelector.I["  ".length()] + this.getChatStyle() + (char)(0x39 ^ 0x44);
    }
    
    private static void I() {
        (I = new String["   ".length()])["".length()] = I(" \u0012.\u001c.\u0007\u00180:\"\u001e\u0007-\u0017(\u001d\u00039\t,\u0007\u0003'\u000b#NP", "swByM");
        ChatComponentSelector.I[" ".length()] = I("Mr\u001b\"4\r;\u0006,%\\", "aRhKV");
        ChatComponentSelector.I["  ".length()] = I("bb7<!\"'y", "NBDHX");
    }
    
    @Override
    public IChatComponent createCopy() {
        return this.createCopy();
    }
    
    static {
        I();
    }
    
    @Override
    public String getUnformattedTextForChat() {
        return this.selector;
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
            if (4 != 4) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return " ".length() != 0;
        }
        if (!(o instanceof ChatComponentSelector)) {
            return "".length() != 0;
        }
        if (this.selector.equals(((ChatComponentSelector)o).selector) && super.equals(o)) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
}
