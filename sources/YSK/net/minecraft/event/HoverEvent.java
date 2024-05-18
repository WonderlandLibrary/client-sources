package net.minecraft.event;

import net.minecraft.util.*;
import java.util.*;
import com.google.common.collect.*;

public class HoverEvent
{
    private final Action action;
    private final IChatComponent value;
    private static final String[] I;
    
    public Action getAction() {
        return this.action;
    }
    
    static {
        I();
    }
    
    public IChatComponent getValue() {
        return this.value;
    }
    
    @Override
    public int hashCode() {
        final int n = (0x68 ^ 0x77) * this.action.hashCode();
        int n2;
        if (this.value != null) {
            n2 = this.value.hashCode();
            "".length();
            if (4 <= 1) {
                throw null;
            }
        }
        else {
            n2 = "".length();
        }
        return n + n2;
    }
    
    public HoverEvent(final Action action, final IChatComponent value) {
        this.action = action;
        this.value = value;
    }
    
    @Override
    public String toString() {
        return HoverEvent.I["".length()] + this.action + HoverEvent.I[" ".length()] + this.value + (char)(0x4 ^ 0x23) + (char)(0xF1 ^ 0x8C);
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return " ".length() != 0;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return "".length() != 0;
        }
        final HoverEvent hoverEvent = (HoverEvent)o;
        if (this.action != hoverEvent.action) {
            return "".length() != 0;
        }
        if (this.value != null) {
            if (!this.value.equals(hoverEvent.value)) {
                return "".length() != 0;
            }
        }
        else if (hoverEvent.value != null) {
            return "".length() != 0;
        }
        return " ".length() != 0;
    }
    
    private static void I() {
        (I = new String["  ".length()])["".length()] = I("\u0012;\u001b6\u0016\u001f\"\b=\u0010!5\u000e'\r5:P", "ZTmSd");
        HoverEvent.I[" ".length()] = I("mh\u001e(\u00074-Un", "AHhIk");
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
            if (0 >= 1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public enum Action
    {
        private final String canonicalName;
        private static final String[] I;
        
        SHOW_TEXT(Action.I["".length()], "".length(), Action.I[" ".length()], (boolean)(" ".length() != 0));
        
        private static final Action[] ENUM$VALUES;
        private static final Map<String, Action> nameMapping;
        
        SHOW_ACHIEVEMENT(Action.I["  ".length()], " ".length(), Action.I["   ".length()], (boolean)(" ".length() != 0)), 
        SHOW_ITEM(Action.I[0xA ^ 0xE], "  ".length(), Action.I[0x76 ^ 0x73], (boolean)(" ".length() != 0)), 
        SHOW_ENTITY(Action.I[0x33 ^ 0x35], "   ".length(), Action.I[0x14 ^ 0x13], (boolean)(" ".length() != 0));
        
        private final boolean allowedInChat;
        
        private Action(final String s, final int n, final String canonicalName, final boolean allowedInChat) {
            this.canonicalName = canonicalName;
            this.allowedInChat = allowedInChat;
        }
        
        public boolean shouldAllowInChat() {
            return this.allowedInChat;
        }
        
        public String getCanonicalName() {
            return this.canonicalName;
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
                if (1 < 1) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        public static Action getValueByCanonicalName(final String s) {
            return Action.nameMapping.get(s);
        }
        
        static {
            I();
            final Action[] enum$VALUES = new Action[0x15 ^ 0x11];
            enum$VALUES["".length()] = Action.SHOW_TEXT;
            enum$VALUES[" ".length()] = Action.SHOW_ACHIEVEMENT;
            enum$VALUES["  ".length()] = Action.SHOW_ITEM;
            enum$VALUES["   ".length()] = Action.SHOW_ENTITY;
            ENUM$VALUES = enum$VALUES;
            nameMapping = Maps.newHashMap();
            final Action[] values;
            final int length = (values = values()).length;
            int i = "".length();
            "".length();
            if (3 == -1) {
                throw null;
            }
            while (i < length) {
                final Action action = values[i];
                Action.nameMapping.put(action.getCanonicalName(), action);
                ++i;
            }
        }
        
        private static void I() {
            (I = new String[0x4A ^ 0x42])["".length()] = I("\u0015\f\u001e-\u0015\u0012\u0001\t.", "FDQzJ");
            Action.I[" ".length()] = I("%\t=.\u0013\"\u0004*-", "VaRYL");
            Action.I["  ".length()] = I("%\f\u001a667\u0007\u001d(, \u0001\u0018$'\"", "vDUai");
            Action.I["   ".length()] = I("\t8\u000517\u001b3\u0002/\r\f5\u0007#\u0006\u000e", "zPjFh");
            Action.I[0x9 ^ 0xD] = I("+>\u001d\u0018+1\"\u0017\u0002", "xvROt");
            Action.I[0xC ^ 0x9] = I("+1\u00038\u00121-\t\"", "XYlOM");
            Action.I[0xB3 ^ 0xB5] = I("\u001a\u000b?0\u0005\f\r$.\u000e\u0010", "ICpgZ");
            Action.I[0x52 ^ 0x55] = I("'\u000b%#\u00071\r>=,-", "TcJTX");
        }
    }
}
