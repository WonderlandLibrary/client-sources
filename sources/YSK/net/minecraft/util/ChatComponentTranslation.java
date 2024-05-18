package net.minecraft.util;

import java.util.*;
import java.util.regex.*;
import com.google.common.collect.*;

public class ChatComponentTranslation extends ChatComponentStyle
{
    private static final String[] I;
    private long lastTranslationUpdateTimeInMilliseconds;
    public static final Pattern stringVariablePattern;
    private final Object syncLock;
    private final Object[] formatArgs;
    private final String key;
    List<IChatComponent> children;
    
    public String getKey() {
        return this.key;
    }
    
    @Override
    public Iterator<IChatComponent> iterator() {
        this.ensureInitialized();
        return (Iterator<IChatComponent>)Iterators.concat((Iterator)ChatComponentStyle.createDeepCopyIterator(this.children), (Iterator)ChatComponentStyle.createDeepCopyIterator(this.siblings));
    }
    
    public Object[] getFormatArgs() {
        return this.formatArgs;
    }
    
    @Override
    public int hashCode() {
        return (0x1C ^ 0x3) * ((0x2 ^ 0x1D) * super.hashCode() + this.key.hashCode()) + Arrays.hashCode(this.formatArgs);
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
    
    protected void initializeFromFormat(final String s) {
        "".length();
        final Matcher matcher = ChatComponentTranslation.stringVariablePattern.matcher(s);
        int length = "".length();
        int length2 = "".length();
        try {
            "".length();
            if (3 < 0) {
                throw null;
            }
            while (matcher.find(length2)) {
                final int start = matcher.start();
                final int end = matcher.end();
                if (start > length2) {
                    final ChatComponentText chatComponentText = new ChatComponentText(String.format(s.substring(length2, start), new Object["".length()]));
                    chatComponentText.getChatStyle().setParentStyle(this.getChatStyle());
                    this.children.add(chatComponentText);
                }
                final String group = matcher.group("  ".length());
                final String substring = s.substring(start, end);
                if (ChatComponentTranslation.I[" ".length()].equals(group) && ChatComponentTranslation.I["  ".length()].equals(substring)) {
                    final ChatComponentText chatComponentText2 = new ChatComponentText(ChatComponentTranslation.I["   ".length()]);
                    chatComponentText2.getChatStyle().setParentStyle(this.getChatStyle());
                    this.children.add(chatComponentText2);
                    "".length();
                    if (-1 < -1) {
                        throw null;
                    }
                }
                else {
                    if (!ChatComponentTranslation.I[0x8D ^ 0x89].equals(group)) {
                        throw new ChatComponentTranslationFormatException(this, ChatComponentTranslation.I[0xC0 ^ 0xC5] + substring + ChatComponentTranslation.I[0x43 ^ 0x45]);
                    }
                    final String group2 = matcher.group(" ".length());
                    int n;
                    if (group2 != null) {
                        n = Integer.parseInt(group2) - " ".length();
                        "".length();
                        if (2 == 1) {
                            throw null;
                        }
                    }
                    else {
                        n = length++;
                    }
                    final int n2 = n;
                    if (n2 < this.formatArgs.length) {
                        this.children.add(this.getFormatArgumentAsComponent(n2));
                    }
                }
                length2 = end;
            }
            if (length2 < s.length()) {
                final ChatComponentText chatComponentText3 = new ChatComponentText(String.format(s.substring(length2), new Object["".length()]));
                chatComponentText3.getChatStyle().setParentStyle(this.getChatStyle());
                this.children.add(chatComponentText3);
                "".length();
                if (0 == 2) {
                    throw null;
                }
            }
        }
        catch (IllegalFormatException ex) {
            throw new ChatComponentTranslationFormatException(this, ex);
        }
    }
    
    @Override
    public IChatComponent setChatStyle(final ChatStyle chatStyle) {
        super.setChatStyle(chatStyle);
        final Object[] formatArgs;
        final int length = (formatArgs = this.formatArgs).length;
        int i = "".length();
        "".length();
        if (2 <= 1) {
            throw null;
        }
        while (i < length) {
            final Object o = formatArgs[i];
            if (o instanceof IChatComponent) {
                ((IChatComponent)o).getChatStyle().setParentStyle(this.getChatStyle());
            }
            ++i;
        }
        if (this.lastTranslationUpdateTimeInMilliseconds > -1L) {
            final Iterator<IChatComponent> iterator = this.children.iterator();
            "".length();
            if (0 <= -1) {
                throw null;
            }
            while (iterator.hasNext()) {
                iterator.next().getChatStyle().setParentStyle(chatStyle);
            }
        }
        return this;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return " ".length() != 0;
        }
        if (!(o instanceof ChatComponentTranslation)) {
            return "".length() != 0;
        }
        final ChatComponentTranslation chatComponentTranslation = (ChatComponentTranslation)o;
        if (Arrays.equals(this.formatArgs, chatComponentTranslation.formatArgs) && this.key.equals(chatComponentTranslation.key) && super.equals(o)) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    static {
        I();
        stringVariablePattern = Pattern.compile(ChatComponentTranslation.I["".length()]);
    }
    
    @Override
    public ChatComponentTranslation createCopy() {
        final Object[] array = new Object[this.formatArgs.length];
        int i = "".length();
        "".length();
        if (3 <= 0) {
            throw null;
        }
        while (i < this.formatArgs.length) {
            if (this.formatArgs[i] instanceof IChatComponent) {
                array[i] = ((IChatComponent)this.formatArgs[i]).createCopy();
                "".length();
                if (2 >= 4) {
                    throw null;
                }
            }
            else {
                array[i] = this.formatArgs[i];
            }
            ++i;
        }
        final ChatComponentTranslation chatComponentTranslation = new ChatComponentTranslation(this.key, array);
        chatComponentTranslation.setChatStyle(this.getChatStyle().createShallowCopy());
        final Iterator<IChatComponent> iterator = this.getSiblings().iterator();
        "".length();
        if (0 >= 3) {
            throw null;
        }
        while (iterator.hasNext()) {
            chatComponentTranslation.appendSibling(iterator.next().createCopy());
        }
        return chatComponentTranslation;
    }
    
    public ChatComponentTranslation(final String key, final Object... formatArgs) {
        this.syncLock = new Object();
        this.lastTranslationUpdateTimeInMilliseconds = -1L;
        this.children = (List<IChatComponent>)Lists.newArrayList();
        this.key = key;
        this.formatArgs = formatArgs;
        final int length = formatArgs.length;
        int i = "".length();
        "".length();
        if (-1 >= 2) {
            throw null;
        }
        while (i < length) {
            final Object o = formatArgs[i];
            if (o instanceof IChatComponent) {
                ((IChatComponent)o).getChatStyle().setParentStyle(this.getChatStyle());
            }
            ++i;
        }
    }
    
    private IChatComponent getFormatArgumentAsComponent(final int n) {
        if (n >= this.formatArgs.length) {
            throw new ChatComponentTranslationFormatException(this, n);
        }
        final Object o = this.formatArgs[n];
        IChatComponent chatComponent;
        if (o instanceof IChatComponent) {
            chatComponent = (IChatComponent)o;
            "".length();
            if (1 == 3) {
                throw null;
            }
        }
        else {
            String string;
            if (o == null) {
                string = ChatComponentTranslation.I[0x3 ^ 0x4];
                "".length();
                if (1 <= -1) {
                    throw null;
                }
            }
            else {
                string = o.toString();
            }
            chatComponent = new ChatComponentText(string);
            chatComponent.getChatStyle().setParentStyle(this.getChatStyle());
        }
        return chatComponent;
    }
    
    private static void I() {
        (I = new String[0xA ^ 0x6])["".length()] = I("PKwcB)\u0007cp6QJwq14N\u00128G\u000fF\u0015%N\\", "ucHYj");
        ChatComponentTranslation.I[" ".length()] = I("F", "cygUv");
        ChatComponentTranslation.I["  ".length()] = I("\\J", "yoGvi");
        ChatComponentTranslation.I["   ".length()] = I("\u007f", "ZSApj");
        ChatComponentTranslation.I[0x62 ^ 0x66] = I("!", "RIdlG");
        ChatComponentTranslation.I[0xB2 ^ 0xB7] = I("\u001e,;,#;-:-6/b.6!&#<csl", "KBHYS");
        ChatComponentTranslation.I[0x4A ^ 0x4C] = I("`", "GLGzo");
        ChatComponentTranslation.I[0xA ^ 0xD] = I("\u001f>\u001d;", "qKqWg");
        ChatComponentTranslation.I[0x43 ^ 0x4B] = I(",!-6&\u00142897\u00146\u000f78\b<\"=;\f('=,Et", "xSLXU");
        ChatComponentTranslation.I[0xA1 ^ 0xA8] = I("TT 0\u0002\u000bI", "xtABe");
        ChatComponentTranslation.I[0x65 ^ 0x6F] = I("OF\u001e\u00020\u000f\u000f\u0003\f!^", "cfmkR");
        ChatComponentTranslation.I[0x68 ^ 0x63] = I("Ea\u0005\u001c?\u0005$K", "iAvhF");
    }
    
    synchronized void ensureInitialized() {
        synchronized (this.syncLock) {
            final long lastTranslationUpdateTimeInMilliseconds = StatCollector.getLastTranslationUpdateTimeInMilliseconds();
            if (lastTranslationUpdateTimeInMilliseconds == this.lastTranslationUpdateTimeInMilliseconds) {
                // monitorexit(this.syncLock)
                return;
            }
            this.lastTranslationUpdateTimeInMilliseconds = lastTranslationUpdateTimeInMilliseconds;
            this.children.clear();
            // monitorexit(this.syncLock)
            "".length();
            if (2 < 1) {
                throw null;
            }
        }
        try {
            this.initializeFromFormat(StatCollector.translateToLocal(this.key));
            "".length();
            if (-1 >= 0) {
                throw null;
            }
        }
        catch (ChatComponentTranslationFormatException ex) {
            this.children.clear();
            try {
                this.initializeFromFormat(StatCollector.translateToFallback(this.key));
                "".length();
                if (false == true) {
                    throw null;
                }
            }
            catch (ChatComponentTranslationFormatException ex2) {
                throw ex;
            }
        }
    }
    
    @Override
    public String toString() {
        return ChatComponentTranslation.I[0xCD ^ 0xC5] + this.key + (char)(0x55 ^ 0x72) + ChatComponentTranslation.I[0xAE ^ 0xA7] + Arrays.toString(this.formatArgs) + ChatComponentTranslation.I[0x56 ^ 0x5C] + this.siblings + ChatComponentTranslation.I[0x22 ^ 0x29] + this.getChatStyle() + (char)(0x4 ^ 0x79);
    }
    
    @Override
    public String getUnformattedTextForChat() {
        this.ensureInitialized();
        final StringBuilder sb = new StringBuilder();
        final Iterator<IChatComponent> iterator = this.children.iterator();
        "".length();
        if (0 >= 3) {
            throw null;
        }
        while (iterator.hasNext()) {
            sb.append(iterator.next().getUnformattedTextForChat());
        }
        return sb.toString();
    }
    
    @Override
    public IChatComponent createCopy() {
        return this.createCopy();
    }
}
