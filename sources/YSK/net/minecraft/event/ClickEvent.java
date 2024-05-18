package net.minecraft.event;

import java.util.*;
import com.google.common.collect.*;

public class ClickEvent
{
    private static final String[] I;
    private final String value;
    private final Action action;
    
    private static void I() {
        (I = new String["  ".length()])["".length()] = I("\u0016(\u0006 \"\u00102\n-=.%\f7 :*R", "UDoCI");
        ClickEvent.I[" ".length()] = I("df 2\u000b=#kt", "HFVSg");
    }
    
    public String getValue() {
        return this.value;
    }
    
    public ClickEvent(final Action action, final String value) {
        this.action = action;
        this.value = value;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return " ".length() != 0;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return "".length() != 0;
        }
        final ClickEvent clickEvent = (ClickEvent)o;
        if (this.action != clickEvent.action) {
            return "".length() != 0;
        }
        if (this.value != null) {
            if (!this.value.equals(clickEvent.value)) {
                return "".length() != 0;
            }
        }
        else if (clickEvent.value != null) {
            return "".length() != 0;
        }
        return " ".length() != 0;
    }
    
    static {
        I();
    }
    
    @Override
    public int hashCode() {
        final int n = (0xD ^ 0x12) * this.action.hashCode();
        int n2;
        if (this.value != null) {
            n2 = this.value.hashCode();
            "".length();
            if (3 != 3) {
                throw null;
            }
        }
        else {
            n2 = "".length();
        }
        return n + n2;
    }
    
    @Override
    public String toString() {
        return ClickEvent.I["".length()] + this.action + ClickEvent.I[" ".length()] + this.value + (char)(0x87 ^ 0xA0) + (char)(0xDD ^ 0xA0);
    }
    
    public Action getAction() {
        return this.action;
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
            if (-1 == 1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public enum Action
    {
        private static final String[] I;
        
        SUGGEST_COMMAND(Action.I[0xB8 ^ 0xB0], 0x2E ^ 0x2A, Action.I[0x12 ^ 0x1B], (boolean)(" ".length() != 0)), 
        OPEN_URL(Action.I["".length()], "".length(), Action.I[" ".length()], (boolean)(" ".length() != 0)), 
        OPEN_FILE(Action.I["  ".length()], " ".length(), Action.I["   ".length()], (boolean)("".length() != 0));
        
        private final boolean allowedInChat;
        private final String canonicalName;
        
        TWITCH_USER_INFO(Action.I[0x83 ^ 0x85], "   ".length(), Action.I[0x14 ^ 0x13], (boolean)("".length() != 0)), 
        RUN_COMMAND(Action.I[0x73 ^ 0x77], "  ".length(), Action.I[0x66 ^ 0x63], (boolean)(" ".length() != 0)), 
        CHANGE_PAGE(Action.I[0x1B ^ 0x11], 0x3A ^ 0x3F, Action.I[0xCD ^ 0xC6], (boolean)(" ".length() != 0));
        
        private static final Map<String, Action> nameMapping;
        private static final Action[] ENUM$VALUES;
        
        static {
            I();
            final Action[] enum$VALUES = new Action[0x3D ^ 0x3B];
            enum$VALUES["".length()] = Action.OPEN_URL;
            enum$VALUES[" ".length()] = Action.OPEN_FILE;
            enum$VALUES["  ".length()] = Action.RUN_COMMAND;
            enum$VALUES["   ".length()] = Action.TWITCH_USER_INFO;
            enum$VALUES[0x56 ^ 0x52] = Action.SUGGEST_COMMAND;
            enum$VALUES[0xA1 ^ 0xA4] = Action.CHANGE_PAGE;
            ENUM$VALUES = enum$VALUES;
            nameMapping = Maps.newHashMap();
            final Action[] values;
            final int length = (values = values()).length;
            int i = "".length();
            "".length();
            if (-1 >= 3) {
                throw null;
            }
            while (i < length) {
                final Action action = values[i];
                Action.nameMapping.put(action.getCanonicalName(), action);
                ++i;
            }
        }
        
        private static void I() {
            (I = new String[0x14 ^ 0x18])["".length()] = I("\u00184\u000b=%\u00026\u0002", "WdNsz");
            Action.I[" ".length()] = I("\r<,*9\u0017>%", "bLIDf");
            Action.I["  ".length()] = I("\u001b\u0017\u000f\u001f)\u0012\u000e\u0006\u0014", "TGJQv");
            Action.I["   ".length()] = I("%\u0013\u0011\u0002),\n\u0018\t", "Jctlv");
            Action.I[0x3A ^ 0x3E] = I("\u001f-97\u000b\u00025:)\u0006\t", "MxwhH");
            Action.I[0x23 ^ 0x26] = I("#\u0006)\u000e\u0019>\u001e*0\u00145", "QsGQz");
            Action.I[0x26 ^ 0x20] = I("%\u001c;;)9\u0014'</#\u0014;!,>", "qKroj");
            Action.I[0x39 ^ 0x3E] = I("3\u000f+\u0019\u0019/'7\u001e\u001f5'+\u0003\u001c(", "GxBmz");
            Action.I[0x6D ^ 0x65] = I("\u00067\r1&\u00066\u00155,\u0018/\u000b8'", "UbJvc");
            Action.I[0x94 ^ 0x9D] = I("&#\u0003\u0017*&\";\u0013 8;\u0005\u001e+", "UVdpO");
            Action.I[0x2D ^ 0x27] = I("\u001b \u0003$,\u001d7\u0012+,\u001d", "XhBjk");
            Action.I[0x1B ^ 0x10] = I("\u000f\f'\u0001\t\t;6\u000e\t\t", "ldFon");
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
                if (1 >= 4) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        public static Action getValueByCanonicalName(final String s) {
            return Action.nameMapping.get(s);
        }
        
        private Action(final String s, final int n, final String canonicalName, final boolean allowedInChat) {
            this.canonicalName = canonicalName;
            this.allowedInChat = allowedInChat;
        }
    }
}
