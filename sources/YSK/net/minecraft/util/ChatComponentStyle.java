package net.minecraft.util;

import java.util.*;
import com.google.common.base.*;
import com.google.common.collect.*;

public abstract class ChatComponentStyle implements IChatComponent
{
    private static final String[] I;
    protected List<IChatComponent> siblings;
    private ChatStyle style;
    
    @Override
    public ChatStyle getChatStyle() {
        if (this.style == null) {
            this.style = new ChatStyle();
            final Iterator<IChatComponent> iterator = this.siblings.iterator();
            "".length();
            if (3 <= 1) {
                throw null;
            }
            while (iterator.hasNext()) {
                iterator.next().getChatStyle().setParentStyle(this.style);
            }
        }
        return this.style;
    }
    
    @Override
    public IChatComponent setChatStyle(final ChatStyle style) {
        this.style = style;
        final Iterator<IChatComponent> iterator = this.siblings.iterator();
        "".length();
        if (1 >= 3) {
            throw null;
        }
        while (iterator.hasNext()) {
            iterator.next().getChatStyle().setParentStyle(this.getChatStyle());
        }
        return this;
    }
    
    @Override
    public List<IChatComponent> getSiblings() {
        return this.siblings;
    }
    
    @Override
    public String toString() {
        return ChatComponentStyle.I["".length()] + this.style + ChatComponentStyle.I[" ".length()] + this.siblings + (char)(0x33 ^ 0x4E);
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
            if (false) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public final String getUnformattedText() {
        final StringBuilder sb = new StringBuilder();
        final Iterator<IChatComponent> iterator = this.iterator();
        "".length();
        if (1 >= 3) {
            throw null;
        }
        while (iterator.hasNext()) {
            sb.append(iterator.next().getUnformattedTextForChat());
        }
        return sb.toString();
    }
    
    private static void I() {
        (I = new String["  ".length()])["".length()] = I("1\b\u001e\u0010\u0015\u001c\u0004\u001d\u001a8\u0016\u0007\u0019\u000e%\u0007\u0010\u0001\u0010k", "simuV");
        ChatComponentStyle.I[" ".length()] = I("Iz6\u0003,\t3+\r=X", "eZEjN");
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return " ".length() != 0;
        }
        if (!(o instanceof ChatComponentStyle)) {
            return "".length() != 0;
        }
        final ChatComponentStyle chatComponentStyle = (ChatComponentStyle)o;
        if (this.siblings.equals(chatComponentStyle.siblings) && this.getChatStyle().equals(chatComponentStyle.getChatStyle())) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    @Override
    public final String getFormattedText() {
        final StringBuilder sb = new StringBuilder();
        final Iterator<IChatComponent> iterator = this.iterator();
        "".length();
        if (4 <= 1) {
            throw null;
        }
        while (iterator.hasNext()) {
            final IChatComponent chatComponent = iterator.next();
            sb.append(chatComponent.getChatStyle().getFormattingCode());
            sb.append(chatComponent.getUnformattedTextForChat());
            sb.append(EnumChatFormatting.RESET);
        }
        return sb.toString();
    }
    
    @Override
    public IChatComponent appendSibling(final IChatComponent chatComponent) {
        chatComponent.getChatStyle().setParentStyle(this.getChatStyle());
        this.siblings.add(chatComponent);
        return this;
    }
    
    @Override
    public Iterator<IChatComponent> iterator() {
        final ChatComponentStyle[] array = new ChatComponentStyle[" ".length()];
        array["".length()] = this;
        return (Iterator<IChatComponent>)Iterators.concat((Iterator)Iterators.forArray((Object[])array), (Iterator)createDeepCopyIterator(this.siblings));
    }
    
    public static Iterator<IChatComponent> createDeepCopyIterator(final Iterable<IChatComponent> iterable) {
        return (Iterator<IChatComponent>)Iterators.transform(Iterators.concat(Iterators.transform((Iterator)iterable.iterator(), (Function)new Function<IChatComponent, Iterator<IChatComponent>>() {
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
                    if (2 < -1) {
                        throw null;
                    }
                }
                return sb.toString();
            }
            
            public Iterator<IChatComponent> apply(final IChatComponent chatComponent) {
                return chatComponent.iterator();
            }
            
            public Object apply(final Object o) {
                return this.apply((IChatComponent)o);
            }
        })), (Function)new Function<IChatComponent, IChatComponent>() {
            public IChatComponent apply(final IChatComponent chatComponent) {
                final IChatComponent copy = chatComponent.createCopy();
                copy.setChatStyle(copy.getChatStyle().createDeepCopy());
                return copy;
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
                    if (true != true) {
                        throw null;
                    }
                }
                return sb.toString();
            }
            
            public Object apply(final Object o) {
                return this.apply((IChatComponent)o);
            }
        });
    }
    
    public ChatComponentStyle() {
        this.siblings = (List<IChatComponent>)Lists.newArrayList();
    }
    
    @Override
    public IChatComponent appendText(final String s) {
        return this.appendSibling(new ChatComponentText(s));
    }
    
    static {
        I();
    }
    
    @Override
    public int hashCode() {
        return (0x24 ^ 0x3B) * this.style.hashCode() + this.siblings.hashCode();
    }
}
