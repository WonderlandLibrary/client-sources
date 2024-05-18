package net.minecraft.client.stream;

import tv.twitch.*;
import org.apache.logging.log4j.*;
import java.util.*;
import tv.twitch.chat.*;
import com.google.common.collect.*;

public class ChatController
{
    protected String field_153006_d;
    protected int field_175994_o;
    protected AuthToken field_153012_j;
    protected String field_153007_e;
    protected ChatEmoticonData field_175996_m;
    protected String field_153004_b;
    protected HashMap<String, ChatChannelListener> field_175998_i;
    private static final String[] I;
    protected Chat field_153008_f;
    protected Core field_175992_e;
    protected EnumEmoticonMode field_175997_k;
    private static final Logger LOGGER;
    protected int field_175993_n;
    protected int field_153015_m;
    protected IChatAPIListener field_175999_p;
    protected ChatState field_153011_i;
    private static int[] $SWITCH_TABLE$net$minecraft$client$stream$ChatController$EnumEmoticonMode;
    protected EnumEmoticonMode field_175995_l;
    protected ChatListener field_153003_a;
    
    public ChatState func_153000_j() {
        return this.field_153011_i;
    }
    
    protected void func_152988_s() {
        if (this.field_175996_m == null) {
            this.field_175996_m = new ChatEmoticonData();
            final ErrorCode emoticonData = this.field_153008_f.getEmoticonData(this.field_175996_m);
            if (ErrorCode.succeeded(emoticonData)) {
                try {
                    if (this.field_153003_a == null) {
                        return;
                    }
                    this.field_153003_a.func_176021_d();
                    "".length();
                    if (-1 >= 4) {
                        throw null;
                    }
                    return;
                }
                catch (Exception ex) {
                    this.func_152995_h(ex.toString());
                    "".length();
                    if (-1 >= 1) {
                        throw null;
                    }
                    return;
                }
            }
            this.func_152995_h(ChatController.I[0x3A ^ 0x36] + ErrorCode.getString(emoticonData));
        }
    }
    
    protected boolean func_175987_a(final String s, final boolean b) {
        if (this.field_153011_i != ChatState.Initialized) {
            return "".length() != 0;
        }
        if (this.field_175998_i.containsKey(s)) {
            this.func_152995_h(ChatController.I[0x8B ^ 0x8E] + s);
            return "".length() != 0;
        }
        if (s != null && !s.equals(ChatController.I[0xA6 ^ 0xA0])) {
            final ChatChannelListener chatChannelListener = new ChatChannelListener(s);
            this.field_175998_i.put(s, chatChannelListener);
            final boolean func_176038_a = chatChannelListener.func_176038_a(b);
            if (!func_176038_a) {
                this.field_175998_i.remove(s);
            }
            return func_176038_a;
        }
        return "".length() != 0;
    }
    
    protected void func_152996_t() {
        if (this.field_175996_m != null) {
            final ErrorCode clearEmoticonData = this.field_153008_f.clearEmoticonData();
            if (ErrorCode.succeeded(clearEmoticonData)) {
                this.field_175996_m = null;
                try {
                    if (this.field_153003_a == null) {
                        return;
                    }
                    this.field_153003_a.func_176024_e();
                    "".length();
                    if (-1 >= 1) {
                        throw null;
                    }
                    return;
                }
                catch (Exception ex) {
                    this.func_152995_h(ex.toString());
                    "".length();
                    if (0 >= 3) {
                        throw null;
                    }
                    return;
                }
            }
            this.func_152995_h(ChatController.I[0x11 ^ 0x1C] + ErrorCode.getString(clearEmoticonData));
        }
    }
    
    public boolean func_175991_l(final String s) {
        if (this.field_153011_i != ChatState.Initialized) {
            return "".length() != 0;
        }
        if (!this.field_175998_i.containsKey(s)) {
            this.func_152995_h(ChatController.I[0x23 ^ 0x24] + s);
            return "".length() != 0;
        }
        return this.field_175998_i.get(s).func_176034_g();
    }
    
    public boolean func_175986_a(final String s, final String s2) {
        if (this.field_153011_i != ChatState.Initialized) {
            return "".length() != 0;
        }
        if (!this.field_175998_i.containsKey(s)) {
            this.func_152995_h(ChatController.I[0xF ^ 0x5] + s);
            return "".length() != 0;
        }
        return this.field_175998_i.get(s).func_176037_b(s2);
    }
    
    public boolean func_175990_d(final String s) {
        if (!this.field_175998_i.containsKey(s)) {
            return "".length() != 0;
        }
        if (this.field_175998_i.get(s).func_176040_a() == EnumChannelState.Connected) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public void func_152998_c(final String field_153004_b) {
        this.field_153004_b = field_153004_b;
    }
    
    public void func_152990_a(final ChatListener field_153003_a) {
        this.field_153003_a = field_153003_a;
    }
    
    static int[] $SWITCH_TABLE$net$minecraft$client$stream$ChatController$EnumEmoticonMode() {
        final int[] $switch_TABLE$net$minecraft$client$stream$ChatController$EnumEmoticonMode = ChatController.$SWITCH_TABLE$net$minecraft$client$stream$ChatController$EnumEmoticonMode;
        if ($switch_TABLE$net$minecraft$client$stream$ChatController$EnumEmoticonMode != null) {
            return $switch_TABLE$net$minecraft$client$stream$ChatController$EnumEmoticonMode;
        }
        final int[] $switch_TABLE$net$minecraft$client$stream$ChatController$EnumEmoticonMode2 = new int[EnumEmoticonMode.values().length];
        try {
            $switch_TABLE$net$minecraft$client$stream$ChatController$EnumEmoticonMode2[EnumEmoticonMode.None.ordinal()] = " ".length();
            "".length();
            if (4 == 2) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            $switch_TABLE$net$minecraft$client$stream$ChatController$EnumEmoticonMode2[EnumEmoticonMode.TextureAtlas.ordinal()] = "   ".length();
            "".length();
            if (3 < 0) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError2) {}
        try {
            $switch_TABLE$net$minecraft$client$stream$ChatController$EnumEmoticonMode2[EnumEmoticonMode.Url.ordinal()] = "  ".length();
            "".length();
            if (2 >= 3) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError3) {}
        return ChatController.$SWITCH_TABLE$net$minecraft$client$stream$ChatController$EnumEmoticonMode = $switch_TABLE$net$minecraft$client$stream$ChatController$EnumEmoticonMode2;
    }
    
    private static void I() {
        (I = new String[0x6B ^ 0x64])["".length()] = I("", "ynNXU");
        ChatController.I[" ".length()] = I("", "pLQgf");
        ChatController.I["  ".length()] = I("", "hwfTR");
        ChatController.I["   ".length()] = I("'\u001c\u0015\u00055B\u0007\t\u00033\u000b\u000f\u000b\u0003=\u000b\u0000\u0000J\u0013\u0015\u0007\u0013\t/B\u001d\u0003\u0001}BK\u0014", "bngjG");
        ChatController.I[0x20 ^ 0x24] = I("3;\u001f\f+V \u0003\n-\u001f(\u0001\n#\u001f'\nC\r\u0001 \u0019\u00001V*\u0005\u0002-LiH\u0010", "vImcY");
        ChatController.I[0x8A ^ 0x8F] = I("\r6\u0006\f\r(#T\u0000\u0002l9\u001c\b\u0002\"?\u0018SL", "LZtil");
        ChatController.I[0x5C ^ 0x5A] = I("", "AfbnG");
        ChatController.I[0xAF ^ 0xA8] = I("*>\u0016H\u000b\nq\u0001\u0000\u0003\n?\u0007\u0004XD", "dQbhb");
        ChatController.I[0x81 ^ 0x89] = I("\u00014\u001d#;d5\u00079=0/\u0001+i )\u0018\"i'.\u000e8sdc\u001c", "DFoLI");
        ChatController.I[0x0 ^ 0x9] = I("\n\u0011\u001b&\u000bo\u0005\u0005<\n'\n\u0007.Y,\u000b\b=Y*\u0015\f'\r<YIl\n", "OciIy");
        ChatController.I[0xB0 ^ 0xBA] = I("\r\u0016\u0012k\u0001-Y\u0005#\t-\u0017\u0003'Rc", "CyfKh");
        ChatController.I[0x87 ^ 0x8C] = I("\u0004\u001a\u0002-&a\u001c\u0002;=/\u000fP6;a\f\u001f5:-\u0007\u0011&t$\u0005\u001f6=\"\u0007\u001eb0 \u001c\u0011xtd\u001b", "AhpBT");
        ChatController.I[0x6B ^ 0x67] = I(" ?*\u001d\u001dE=*\u0017\u001f\u0004?1\u001c\bE(5\u001d\u001b\f.7\u001cO\u0001,,\u0013UE", "eMXro");
        ChatController.I[0x46 ^ 0x4B] = I("$\"4$0A3*.#\u00139(,b\u0004=)?+\u0002?(k&\u0000$'qb", "aPFKB");
        ChatController.I[0x1B ^ 0x15] = I("\u0001$\u001d52z\u0004\u001a:2(\b\u00198#(:U/;", "ZguTF");
    }
    
    public void func_152994_a(final AuthToken field_153012_j) {
        this.field_153012_j = field_153012_j;
    }
    
    public void func_175988_p() {
        if (this.func_153000_j() != ChatState.Uninitialized) {
            this.func_152993_m();
            if (this.func_153000_j() == ChatState.ShuttingDown) {
                "".length();
                if (1 == 2) {
                    throw null;
                }
                while (this.func_153000_j() != ChatState.Uninitialized) {
                    try {
                        Thread.sleep(200L);
                        this.func_152997_n();
                        "".length();
                        if (-1 == 3) {
                            throw null;
                        }
                        continue;
                    }
                    catch (InterruptedException ex) {}
                }
            }
        }
    }
    
    public void func_152997_n() {
        if (this.field_153011_i != ChatState.Uninitialized) {
            final ErrorCode flushEvents = this.field_153008_f.flushEvents();
            if (ErrorCode.failed(flushEvents)) {
                final String string = ErrorCode.getString(flushEvents);
                final String s = ChatController.I[0x47 ^ 0x4E];
                final Object[] array = new Object[" ".length()];
                array["".length()] = string;
                this.func_152995_h(String.format(s, array));
            }
        }
    }
    
    static {
        I();
        LOGGER = LogManager.getLogger();
    }
    
    protected void func_153001_r() {
        if (this.field_175995_l != EnumEmoticonMode.None && this.field_175996_m == null) {
            final ErrorCode downloadEmoticonData = this.field_153008_f.downloadEmoticonData();
            if (ErrorCode.failed(downloadEmoticonData)) {
                final String string = ErrorCode.getString(downloadEmoticonData);
                final String s = ChatController.I[0xA0 ^ 0xAB];
                final Object[] array = new Object[" ".length()];
                array["".length()] = string;
                this.func_152995_h(String.format(s, array));
            }
        }
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
            if (0 < -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public boolean func_175984_n() {
        if (this.field_153011_i != ChatState.Uninitialized) {
            return "".length() != 0;
        }
        this.func_175985_a(ChatState.Initializing);
        final ErrorCode initialize = this.field_175992_e.initialize(this.field_153006_d, (String)null);
        if (ErrorCode.failed(initialize)) {
            this.func_175985_a(ChatState.Uninitialized);
            final String string = ErrorCode.getString(initialize);
            final String s = ChatController.I["   ".length()];
            final Object[] array = new Object[" ".length()];
            array["".length()] = string;
            this.func_152995_h(String.format(s, array));
            return "".length() != 0;
        }
        this.field_175995_l = this.field_175997_k;
        final HashSet<ChatTokenizationOption> set = new HashSet<ChatTokenizationOption>();
        switch ($SWITCH_TABLE$net$minecraft$client$stream$ChatController$EnumEmoticonMode()[this.field_175997_k.ordinal()]) {
            case 1: {
                set.add(ChatTokenizationOption.TTV_CHAT_TOKENIZATION_OPTION_NONE);
                "".length();
                if (1 < -1) {
                    throw null;
                }
                break;
            }
            case 2: {
                set.add(ChatTokenizationOption.TTV_CHAT_TOKENIZATION_OPTION_EMOTICON_URLS);
                "".length();
                if (0 < 0) {
                    throw null;
                }
                break;
            }
            case 3: {
                set.add(ChatTokenizationOption.TTV_CHAT_TOKENIZATION_OPTION_EMOTICON_TEXTURES);
                break;
            }
        }
        final ErrorCode initialize2 = this.field_153008_f.initialize((HashSet)set, this.field_175999_p);
        if (ErrorCode.failed(initialize2)) {
            this.field_175992_e.shutdown();
            this.func_175985_a(ChatState.Uninitialized);
            final String string2 = ErrorCode.getString(initialize2);
            final String s2 = ChatController.I[0x52 ^ 0x56];
            final Object[] array2 = new Object[" ".length()];
            array2["".length()] = string2;
            this.func_152995_h(String.format(s2, array2));
            return "".length() != 0;
        }
        this.func_175985_a(ChatState.Initialized);
        return " ".length() != 0;
    }
    
    public void func_152984_a(final String field_153006_d) {
        this.field_153006_d = field_153006_d;
    }
    
    public boolean func_152993_m() {
        if (this.field_153011_i != ChatState.Initialized) {
            return "".length() != 0;
        }
        final ErrorCode shutdown = this.field_153008_f.shutdown();
        if (ErrorCode.failed(shutdown)) {
            final String string = ErrorCode.getString(shutdown);
            final String s = ChatController.I[0xA8 ^ 0xA0];
            final Object[] array = new Object[" ".length()];
            array["".length()] = string;
            this.func_152995_h(String.format(s, array));
            return "".length() != 0;
        }
        this.func_152996_t();
        this.func_175985_a(ChatState.ShuttingDown);
        return " ".length() != 0;
    }
    
    public ChatController() {
        this.field_153003_a = null;
        this.field_153004_b = ChatController.I["".length()];
        this.field_153006_d = ChatController.I[" ".length()];
        this.field_153007_e = ChatController.I["  ".length()];
        this.field_175992_e = null;
        this.field_153008_f = null;
        this.field_153011_i = ChatState.Uninitialized;
        this.field_153012_j = new AuthToken();
        this.field_175998_i = new HashMap<String, ChatChannelListener>();
        this.field_153015_m = 5 + 45 - 18 + 96;
        this.field_175997_k = EnumEmoticonMode.None;
        this.field_175995_l = EnumEmoticonMode.None;
        this.field_175996_m = null;
        this.field_175993_n = 59 + 377 - 198 + 262;
        this.field_175994_o = 50 + 829 - 663 + 1784;
        this.field_175999_p = (IChatAPIListener)new IChatAPIListener() {
            private static final String[] I;
            final ChatController this$0;
            
            private static void I() {
                (I = new String["  ".length()])["".length()] = I("\u000b\b7\u0019$n\t-\u0003\":\u0013+\u0011v*\u00152\u0018v:\u0012 V\u00029\u00131\u0015>n\t!\u001dln_6", "NzEvV");
                ChatController$1.I[" ".length()] = I("\u000e\u0019(#\u001fk\u001829\u0019?\u00024+M/\u0004-\"M\u001f\u001c38\u0005k\b2-\u0019qK\u007f?", "KkZLm");
            }
            
            public void chatEmoticonDataDownloadCallback(final ErrorCode errorCode) {
                if (ErrorCode.succeeded(errorCode)) {
                    this.this$0.func_152988_s();
                }
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
                    if (0 == -1) {
                        throw null;
                    }
                }
                return sb.toString();
            }
            
            static {
                I();
            }
            
            public void chatInitializationCallback(final ErrorCode errorCode) {
                if (ErrorCode.succeeded(errorCode)) {
                    this.this$0.field_153008_f.setMessageFlushInterval(this.this$0.field_175993_n);
                    this.this$0.field_153008_f.setUserChangeEventInterval(this.this$0.field_175994_o);
                    this.this$0.func_153001_r();
                    this.this$0.func_175985_a(ChatState.Initialized);
                    "".length();
                    if (false) {
                        throw null;
                    }
                }
                else {
                    this.this$0.func_175985_a(ChatState.Uninitialized);
                }
                try {
                    if (this.this$0.field_153003_a != null) {
                        this.this$0.field_153003_a.func_176023_d(errorCode);
                        "".length();
                        if (0 >= 3) {
                            throw null;
                        }
                    }
                }
                catch (Exception ex) {
                    this.this$0.func_152995_h(ex.toString());
                }
            }
            
            public void chatShutdownCallback(final ErrorCode errorCode) {
                if (ErrorCode.succeeded(errorCode)) {
                    final ErrorCode shutdown = this.this$0.field_175992_e.shutdown();
                    if (ErrorCode.failed(shutdown)) {
                        final String string = ErrorCode.getString(shutdown);
                        final ChatController this$0 = this.this$0;
                        final String s = ChatController$1.I["".length()];
                        final Object[] array = new Object[" ".length()];
                        array["".length()] = string;
                        this$0.func_152995_h(String.format(s, array));
                    }
                    this.this$0.func_175985_a(ChatState.Uninitialized);
                    "".length();
                    if (3 == 4) {
                        throw null;
                    }
                }
                else {
                    this.this$0.func_175985_a(ChatState.Initialized);
                    final ChatController this$2 = this.this$0;
                    final String s2 = ChatController$1.I[" ".length()];
                    final Object[] array2 = new Object[" ".length()];
                    array2["".length()] = errorCode;
                    this$2.func_152995_h(String.format(s2, array2));
                }
                try {
                    if (this.this$0.field_153003_a != null) {
                        this.this$0.field_153003_a.func_176022_e(errorCode);
                        "".length();
                        if (0 >= 3) {
                            throw null;
                        }
                    }
                }
                catch (Exception ex) {
                    this.this$0.func_152995_h(ex.toString());
                }
            }
        };
        this.field_175992_e = Core.getInstance();
        if (this.field_175992_e == null) {
            this.field_175992_e = new Core((CoreAPI)new StandardCoreAPI());
        }
        this.field_153008_f = new Chat((ChatAPI)new StandardChatAPI());
    }
    
    protected void func_175985_a(final ChatState field_153011_i) {
        if (field_153011_i != this.field_153011_i) {
            this.field_153011_i = field_153011_i;
            try {
                if (this.field_153003_a != null) {
                    this.field_153003_a.func_176017_a(field_153011_i);
                    "".length();
                    if (3 != 3) {
                        throw null;
                    }
                }
            }
            catch (Exception ex) {
                this.func_152995_h(ex.toString());
            }
        }
    }
    
    public boolean func_152986_d(final String s) {
        return this.func_175987_a(s, "".length() != 0);
    }
    
    public EnumChannelState func_175989_e(final String s) {
        if (!this.field_175998_i.containsKey(s)) {
            return EnumChannelState.Disconnected;
        }
        return this.field_175998_i.get(s).func_176040_a();
    }
    
    protected void func_152995_h(final String s) {
        final Logger logger = ChatController.LOGGER;
        final Marker stream_MARKER = TwitchStream.STREAM_MARKER;
        final String s2 = ChatController.I[0x6F ^ 0x61];
        final Object[] array = new Object[" ".length()];
        array["".length()] = s;
        logger.error(stream_MARKER, s2, array);
    }
    
    public enum ChatState
    {
        Uninitialized(ChatState.I["".length()], "".length());
        
        private static final ChatState[] ENUM$VALUES;
        
        ShuttingDown(ChatState.I["   ".length()], "   ".length()), 
        Initialized(ChatState.I["  ".length()], "  ".length());
        
        private static final String[] I;
        
        Initializing(ChatState.I[" ".length()], " ".length());
        
        private static void I() {
            (I = new String[0x78 ^ 0x7C])["".length()] = I(":+\u0013\u0006\n\u001b,\u001b\u0004\n\u0015 \u001e", "oEzhc");
            ChatState.I[" ".length()] = I("\u0013\u001e\u001b%\u001a;\u001c\u001b+\u001a4\u0017", "ZprQs");
            ChatState.I["  ".length()] = I("\f\u0004$-\u0007$\u0006$#\u000b!", "EjMYn");
            ChatState.I["   ".length()] = I("\u001580 3/>\"\u0010(1>", "FPETG");
        }
        
        static {
            I();
            final ChatState[] enum$VALUES = new ChatState[0x6A ^ 0x6E];
            enum$VALUES["".length()] = ChatState.Uninitialized;
            enum$VALUES[" ".length()] = ChatState.Initializing;
            enum$VALUES["  ".length()] = ChatState.Initialized;
            enum$VALUES["   ".length()] = ChatState.ShuttingDown;
            ENUM$VALUES = enum$VALUES;
        }
        
        private ChatState(final String s, final int n) {
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
                if (-1 >= 4) {
                    throw null;
                }
            }
            return sb.toString();
        }
    }
    
    public enum EnumChannelState
    {
        private static final EnumChannelState[] ENUM$VALUES;
        
        Disconnecting(EnumChannelState.I["   ".length()], "   ".length()), 
        Created(EnumChannelState.I["".length()], "".length()), 
        Connecting(EnumChannelState.I[" ".length()], " ".length()), 
        Connected(EnumChannelState.I["  ".length()], "  ".length()), 
        Disconnected(EnumChannelState.I[0x2B ^ 0x2F], 0x4 ^ 0x0);
        
        private static final String[] I;
        
        private EnumChannelState(final String s, final int n) {
        }
        
        static {
            I();
            final EnumChannelState[] enum$VALUES = new EnumChannelState[0x8 ^ 0xD];
            enum$VALUES["".length()] = EnumChannelState.Created;
            enum$VALUES[" ".length()] = EnumChannelState.Connecting;
            enum$VALUES["  ".length()] = EnumChannelState.Connected;
            enum$VALUES["   ".length()] = EnumChannelState.Disconnecting;
            enum$VALUES[0x81 ^ 0x85] = EnumChannelState.Disconnected;
            ENUM$VALUES = enum$VALUES;
        }
        
        private static void I() {
            (I = new String[0xC5 ^ 0xC0])["".length()] = I("5\u0002!\u0002 \u0013\u0014", "vpDcT");
            EnumChannelState.I[" ".length()] = I("\b\r\b<\u0004(\u0016\u000f<\u0006", "KbfRa");
            EnumChannelState.I["  ".length()] = I("%\u001c\u001c\u0002(\u0005\u0007\u0017\b", "fsrlM");
            EnumChannelState.I["   ".length()] = I(">\u00116\n\u0018\u0014\u0016 \n\u0003\u0013\u0016\"", "zxEiw");
            EnumChannelState.I[0x95 ^ 0x91] = I("!-\u0007\r)\u000b*\u0011\r2\u0000 ", "eDtnF");
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
                if (3 <= 1) {
                    throw null;
                }
            }
            return sb.toString();
        }
    }
    
    public class ChatChannelListener implements IChatChannelListener
    {
        protected List<ChatUserInfo> field_176044_d;
        protected String field_176048_a;
        private static int[] $SWITCH_TABLE$net$minecraft$client$stream$ChatController$EnumChannelState;
        protected LinkedList<ChatRawMessage> field_176045_e;
        protected boolean field_176046_b;
        protected EnumChannelState field_176047_c;
        protected ChatBadgeData field_176043_g;
        private static final String[] I;
        protected LinkedList<ChatTokenizedMessage> field_176042_f;
        final ChatController this$0;
        private static int[] $SWITCH_TABLE$tv$twitch$chat$ChatEvent;
        
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
                if (0 >= 3) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        public void func_176032_a(final String s) {
            if (this.this$0.field_175995_l == EnumEmoticonMode.None) {
                this.field_176045_e.clear();
                this.field_176042_f.clear();
                "".length();
                if (0 >= 3) {
                    throw null;
                }
            }
            else {
                if (this.field_176045_e.size() > 0) {
                    final ListIterator<ChatRawMessage> listIterator = (ListIterator<ChatRawMessage>)this.field_176045_e.listIterator();
                    "".length();
                    if (-1 >= 1) {
                        throw null;
                    }
                    while (listIterator.hasNext()) {
                        if (listIterator.next().userName.equals(s)) {
                            listIterator.remove();
                        }
                    }
                }
                if (this.field_176042_f.size() > 0) {
                    final ListIterator<ChatTokenizedMessage> listIterator2 = (ListIterator<ChatTokenizedMessage>)this.field_176042_f.listIterator();
                    "".length();
                    if (-1 == 1) {
                        throw null;
                    }
                    while (listIterator2.hasNext()) {
                        if (listIterator2.next().displayName.equals(s)) {
                            listIterator2.remove();
                        }
                    }
                }
            }
            try {
                if (this.this$0.field_153003_a != null) {
                    this.this$0.field_153003_a.func_176019_a(this.field_176048_a, s);
                    "".length();
                    if (0 >= 4) {
                        throw null;
                    }
                }
            }
            catch (Exception ex) {
                this.this$0.func_152995_h(ex.toString());
            }
        }
        
        protected void func_176039_i() {
            if (this.field_176043_g == null) {
                this.field_176043_g = new ChatBadgeData();
                final ErrorCode badgeData = this.this$0.field_153008_f.getBadgeData(this.field_176048_a, this.field_176043_g);
                if (ErrorCode.succeeded(badgeData)) {
                    try {
                        if (this.this$0.field_153003_a == null) {
                            return;
                        }
                        this.this$0.field_153003_a.func_176016_c(this.field_176048_a);
                        "".length();
                        if (1 == 3) {
                            throw null;
                        }
                        return;
                    }
                    catch (Exception ex) {
                        this.this$0.func_152995_h(ex.toString());
                        "".length();
                        if (-1 < -1) {
                            throw null;
                        }
                        return;
                    }
                }
                this.this$0.func_152995_h(ChatChannelListener.I[0x51 ^ 0x55] + ErrorCode.getString(badgeData));
            }
        }
        
        public EnumChannelState func_176040_a() {
            return this.field_176047_c;
        }
        
        protected void func_176033_j() {
            if (this.field_176043_g != null) {
                final ErrorCode clearBadgeData = this.this$0.field_153008_f.clearBadgeData(this.field_176048_a);
                if (ErrorCode.succeeded(clearBadgeData)) {
                    this.field_176043_g = null;
                    try {
                        if (this.this$0.field_153003_a == null) {
                            return;
                        }
                        this.this$0.field_153003_a.func_176020_d(this.field_176048_a);
                        "".length();
                        if (3 == -1) {
                            throw null;
                        }
                        return;
                    }
                    catch (Exception ex) {
                        this.this$0.func_152995_h(ex.toString());
                        "".length();
                        if (3 >= 4) {
                            throw null;
                        }
                        return;
                    }
                }
                this.this$0.func_152995_h(ChatChannelListener.I[0xBC ^ 0xB9] + ErrorCode.getString(clearBadgeData));
            }
        }
        
        public void chatStatusCallback(final String s, final ErrorCode errorCode) {
            if (!ErrorCode.succeeded(errorCode)) {
                this.this$0.field_175998_i.remove(s);
                this.func_176030_k();
            }
        }
        
        public void chatBadgeDataDownloadCallback(final String s, final ErrorCode errorCode) {
            if (ErrorCode.succeeded(errorCode)) {
                this.func_176039_i();
            }
        }
        
        public void chatChannelUserChangeCallback(final String s, final ChatUserInfo[] array, final ChatUserInfo[] array2, final ChatUserInfo[] array3) {
            int i = "".length();
            "".length();
            if (-1 >= 3) {
                throw null;
            }
            while (i < array2.length) {
                final int index = this.field_176044_d.indexOf(array2[i]);
                if (index >= 0) {
                    this.field_176044_d.remove(index);
                }
                ++i;
            }
            int j = "".length();
            "".length();
            if (2 < 2) {
                throw null;
            }
            while (j < array3.length) {
                final int index2 = this.field_176044_d.indexOf(array3[j]);
                if (index2 >= 0) {
                    this.field_176044_d.remove(index2);
                }
                this.field_176044_d.add(array3[j]);
                ++j;
            }
            int k = "".length();
            "".length();
            if (2 < 2) {
                throw null;
            }
            while (k < array.length) {
                this.field_176044_d.add(array[k]);
                ++k;
            }
            try {
                if (this.this$0.field_153003_a != null) {
                    this.this$0.field_153003_a.func_176018_a(this.field_176048_a, array, array2, array3);
                    "".length();
                    if (4 != 4) {
                        throw null;
                    }
                }
            }
            catch (Exception ex) {
                this.this$0.func_152995_h(ex.toString());
            }
        }
        
        public boolean func_176038_a(final boolean field_176046_b) {
            this.field_176046_b = field_176046_b;
            final ErrorCode ttv_EC_SUCCESS = ErrorCode.TTV_EC_SUCCESS;
            ErrorCode errorCode;
            if (field_176046_b) {
                errorCode = this.this$0.field_153008_f.connectAnonymous(this.field_176048_a, (IChatChannelListener)this);
                "".length();
                if (3 == 4) {
                    throw null;
                }
            }
            else {
                errorCode = this.this$0.field_153008_f.connect(this.field_176048_a, this.this$0.field_153004_b, this.this$0.field_153012_j.data, (IChatChannelListener)this);
            }
            if (ErrorCode.failed(errorCode)) {
                final String string = ErrorCode.getString(errorCode);
                final ChatController this$0 = this.this$0;
                final String s = ChatChannelListener.I["".length()];
                final Object[] array = new Object[" ".length()];
                array["".length()] = string;
                this$0.func_152995_h(String.format(s, array));
                this.func_176036_d(this.field_176048_a);
                return "".length() != 0;
            }
            this.func_176035_a(EnumChannelState.Connecting);
            this.func_176041_h();
            return " ".length() != 0;
        }
        
        private static void I() {
            (I = new String[0xC6 ^ 0xC0])["".length()] = I("\u0010\n&\u001f3u\u001b;\u001e/0\u001b \u0019/2BtU2", "UxTpA");
            ChatChannelListener.I[" ".length()] = I("=\u001f\u0016'\u0006X\t\r;\u0017\u0017\u0003\n-\u0017\f\u0004\n/NXH\u0017", "xmdHt");
            ChatChannelListener.I["  ".length()] = I("\u0007\u00049\b>b\u0005.\t(+\u0018,G/*\u0017?G!'\u00058\u0006+'LkB?", "BvKgL");
            ChatChannelListener.I["   ".length()] = I("\u001f5\u0015$\u0011z3\u00152\n4 G?\fz#\b<\r6(\u0006/C8&\u0003,\u0006z#\u0006?\u0002`gB8", "ZGgKc");
            ChatChannelListener.I[0xB2 ^ 0xB6] = I("\u001f\u001a&\u00034z\u0018&\t6;\u001a=\u0002!z\n5\b!?H0\r2;Rt", "ZhTlF");
            ChatChannelListener.I[0x50 ^ 0x55] = I(" \u00146\u000e\u0006E\u0014!\r\u0011\u0004\u0015-\u000f\u0013E\u0004%\u0005\u0013\u0000F \u0000\u0000\u0004\\d", "efDat");
        }
        
        public void chatClearCallback(final String s, final String s2) {
            this.func_176032_a(s2);
        }
        
        protected void func_176036_d(final String s) {
            try {
                if (this.this$0.field_153003_a != null) {
                    this.this$0.field_153003_a.func_180607_b(s);
                    "".length();
                    if (2 < 1) {
                        throw null;
                    }
                }
            }
            catch (Exception ex) {
                this.this$0.func_152995_h(ex.toString());
            }
        }
        
        public void chatChannelMembershipCallback(final String s, final ChatEvent chatEvent, final ChatChannelInfo chatChannelInfo) {
            switch ($SWITCH_TABLE$tv$twitch$chat$ChatEvent()[chatEvent.ordinal()]) {
                case 1: {
                    this.func_176035_a(EnumChannelState.Connected);
                    this.func_176031_c(s);
                    "".length();
                    if (4 <= 1) {
                        throw null;
                    }
                    break;
                }
                case 2: {
                    this.func_176030_k();
                    break;
                }
            }
        }
        
        static int[] $SWITCH_TABLE$tv$twitch$chat$ChatEvent() {
            final int[] $switch_TABLE$tv$twitch$chat$ChatEvent = ChatChannelListener.$SWITCH_TABLE$tv$twitch$chat$ChatEvent;
            if ($switch_TABLE$tv$twitch$chat$ChatEvent != null) {
                return $switch_TABLE$tv$twitch$chat$ChatEvent;
            }
            final int[] $switch_TABLE$tv$twitch$chat$ChatEvent2 = new int[ChatEvent.values().length];
            try {
                $switch_TABLE$tv$twitch$chat$ChatEvent2[ChatEvent.TTV_CHAT_JOINED_CHANNEL.ordinal()] = " ".length();
                "".length();
                if (3 >= 4) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                $switch_TABLE$tv$twitch$chat$ChatEvent2[ChatEvent.TTV_CHAT_LEFT_CHANNEL.ordinal()] = "  ".length();
                "".length();
                if (-1 < -1) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
            return ChatChannelListener.$SWITCH_TABLE$tv$twitch$chat$ChatEvent = $switch_TABLE$tv$twitch$chat$ChatEvent2;
        }
        
        private void func_176030_k() {
            if (this.field_176047_c != EnumChannelState.Disconnected) {
                this.func_176035_a(EnumChannelState.Disconnected);
                this.func_176036_d(this.field_176048_a);
                this.func_176033_j();
            }
        }
        
        public ChatChannelListener(final ChatController this$0, final String field_176048_a) {
            this.this$0 = this$0;
            this.field_176048_a = null;
            this.field_176046_b = ("".length() != 0);
            this.field_176047_c = EnumChannelState.Created;
            this.field_176044_d = (List<ChatUserInfo>)Lists.newArrayList();
            this.field_176045_e = new LinkedList<ChatRawMessage>();
            this.field_176042_f = new LinkedList<ChatTokenizedMessage>();
            this.field_176043_g = null;
            this.field_176048_a = field_176048_a;
        }
        
        public void chatChannelRawMessageCallback(final String s, final ChatRawMessage[] array) {
            int i = "".length();
            "".length();
            if (0 < 0) {
                throw null;
            }
            while (i < array.length) {
                this.field_176045_e.addLast(array[i]);
                ++i;
            }
            while (true) {
                Label_0111: {
                    try {
                        if (this.this$0.field_153003_a == null) {
                            break Label_0111;
                        }
                        this.this$0.field_153003_a.func_180605_a(this.field_176048_a, array);
                        "".length();
                        if (false) {
                            throw null;
                        }
                        break Label_0111;
                    }
                    catch (Exception ex) {
                        this.this$0.func_152995_h(ex.toString());
                        "".length();
                        if (4 < 4) {
                            throw null;
                        }
                        break Label_0111;
                    }
                    this.field_176045_e.removeFirst();
                }
                if (this.field_176045_e.size() <= this.this$0.field_153015_m) {
                    return;
                }
                continue;
            }
        }
        
        static int[] $SWITCH_TABLE$net$minecraft$client$stream$ChatController$EnumChannelState() {
            final int[] $switch_TABLE$net$minecraft$client$stream$ChatController$EnumChannelState = ChatChannelListener.$SWITCH_TABLE$net$minecraft$client$stream$ChatController$EnumChannelState;
            if ($switch_TABLE$net$minecraft$client$stream$ChatController$EnumChannelState != null) {
                return $switch_TABLE$net$minecraft$client$stream$ChatController$EnumChannelState;
            }
            final int[] $switch_TABLE$net$minecraft$client$stream$ChatController$EnumChannelState2 = new int[EnumChannelState.values().length];
            try {
                $switch_TABLE$net$minecraft$client$stream$ChatController$EnumChannelState2[EnumChannelState.Connected.ordinal()] = "   ".length();
                "".length();
                if (false) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                $switch_TABLE$net$minecraft$client$stream$ChatController$EnumChannelState2[EnumChannelState.Connecting.ordinal()] = "  ".length();
                "".length();
                if (-1 != -1) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
            try {
                $switch_TABLE$net$minecraft$client$stream$ChatController$EnumChannelState2[EnumChannelState.Created.ordinal()] = " ".length();
                "".length();
                if (-1 >= 1) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError3) {}
            try {
                $switch_TABLE$net$minecraft$client$stream$ChatController$EnumChannelState2[EnumChannelState.Disconnected.ordinal()] = (0xB ^ 0xE);
                "".length();
                if (1 == -1) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError4) {}
            try {
                $switch_TABLE$net$minecraft$client$stream$ChatController$EnumChannelState2[EnumChannelState.Disconnecting.ordinal()] = (0x76 ^ 0x72);
                "".length();
                if (1 >= 2) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError5) {}
            return ChatChannelListener.$SWITCH_TABLE$net$minecraft$client$stream$ChatController$EnumChannelState = $switch_TABLE$net$minecraft$client$stream$ChatController$EnumChannelState2;
        }
        
        public void chatChannelTokenizedMessageCallback(final String s, final ChatTokenizedMessage[] array) {
            int i = "".length();
            "".length();
            if (0 == 2) {
                throw null;
            }
            while (i < array.length) {
                this.field_176042_f.addLast(array[i]);
                ++i;
            }
            while (true) {
                Label_0111: {
                    try {
                        if (this.this$0.field_153003_a == null) {
                            break Label_0111;
                        }
                        this.this$0.field_153003_a.func_176025_a(this.field_176048_a, array);
                        "".length();
                        if (3 != 3) {
                            throw null;
                        }
                        break Label_0111;
                    }
                    catch (Exception ex) {
                        this.this$0.func_152995_h(ex.toString());
                        "".length();
                        if (3 == 0) {
                            throw null;
                        }
                        break Label_0111;
                    }
                    this.field_176042_f.removeFirst();
                }
                if (this.field_176042_f.size() <= this.this$0.field_153015_m) {
                    return;
                }
                continue;
            }
        }
        
        static {
            I();
        }
        
        protected void func_176035_a(final EnumChannelState field_176047_c) {
            if (field_176047_c != this.field_176047_c) {
                this.field_176047_c = field_176047_c;
            }
        }
        
        protected void func_176041_h() {
            if (this.this$0.field_175995_l != EnumEmoticonMode.None && this.field_176043_g == null) {
                final ErrorCode downloadBadgeData = this.this$0.field_153008_f.downloadBadgeData(this.field_176048_a);
                if (ErrorCode.failed(downloadBadgeData)) {
                    final String string = ErrorCode.getString(downloadBadgeData);
                    final ChatController this$0 = this.this$0;
                    final String s = ChatChannelListener.I["   ".length()];
                    final Object[] array = new Object[" ".length()];
                    array["".length()] = string;
                    this$0.func_152995_h(String.format(s, array));
                }
            }
        }
        
        public boolean func_176034_g() {
            switch ($SWITCH_TABLE$net$minecraft$client$stream$ChatController$EnumChannelState()[this.field_176047_c.ordinal()]) {
                case 2:
                case 3: {
                    final ErrorCode disconnect = this.this$0.field_153008_f.disconnect(this.field_176048_a);
                    if (ErrorCode.failed(disconnect)) {
                        final String string = ErrorCode.getString(disconnect);
                        final ChatController this$0 = this.this$0;
                        final String s = ChatChannelListener.I[" ".length()];
                        final Object[] array = new Object[" ".length()];
                        array["".length()] = string;
                        this$0.func_152995_h(String.format(s, array));
                        return "".length() != 0;
                    }
                    this.func_176035_a(EnumChannelState.Disconnecting);
                    return " ".length() != 0;
                }
                default: {
                    return "".length() != 0;
                }
            }
        }
        
        public boolean func_176037_b(final String s) {
            if (this.field_176047_c != EnumChannelState.Connected) {
                return "".length() != 0;
            }
            final ErrorCode sendMessage = this.this$0.field_153008_f.sendMessage(this.field_176048_a, s);
            if (ErrorCode.failed(sendMessage)) {
                final String string = ErrorCode.getString(sendMessage);
                final ChatController this$0 = this.this$0;
                final String s2 = ChatChannelListener.I["  ".length()];
                final Object[] array = new Object[" ".length()];
                array["".length()] = string;
                this$0.func_152995_h(String.format(s2, array));
                return "".length() != 0;
            }
            return " ".length() != 0;
        }
        
        protected void func_176031_c(final String s) {
            try {
                if (this.this$0.field_153003_a != null) {
                    this.this$0.field_153003_a.func_180606_a(s);
                    "".length();
                    if (1 == -1) {
                        throw null;
                    }
                }
            }
            catch (Exception ex) {
                this.this$0.func_152995_h(ex.toString());
            }
        }
    }
    
    public interface ChatListener
    {
        void func_176018_a(final String p0, final ChatUserInfo[] p1, final ChatUserInfo[] p2, final ChatUserInfo[] p3);
        
        void func_176022_e(final ErrorCode p0);
        
        void func_180605_a(final String p0, final ChatRawMessage[] p1);
        
        void func_176023_d(final ErrorCode p0);
        
        void func_176019_a(final String p0, final String p1);
        
        void func_180606_a(final String p0);
        
        void func_176016_c(final String p0);
        
        void func_176017_a(final ChatState p0);
        
        void func_176020_d(final String p0);
        
        void func_180607_b(final String p0);
        
        void func_176024_e();
        
        void func_176025_a(final String p0, final ChatTokenizedMessage[] p1);
        
        void func_176021_d();
    }
    
    public enum EnumEmoticonMode
    {
        Url(EnumEmoticonMode.I[" ".length()], " ".length());
        
        private static final EnumEmoticonMode[] ENUM$VALUES;
        
        None(EnumEmoticonMode.I["".length()], "".length()), 
        TextureAtlas(EnumEmoticonMode.I["  ".length()], "  ".length());
        
        private static final String[] I;
        
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
                if (4 == 3) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        private EnumEmoticonMode(final String s, final int n) {
        }
        
        static {
            I();
            final EnumEmoticonMode[] enum$VALUES = new EnumEmoticonMode["   ".length()];
            enum$VALUES["".length()] = EnumEmoticonMode.None;
            enum$VALUES[" ".length()] = EnumEmoticonMode.Url;
            enum$VALUES["  ".length()] = EnumEmoticonMode.TextureAtlas;
            ENUM$VALUES = enum$VALUES;
        }
        
        private static void I() {
            (I = new String["   ".length()])["".length()] = I("\f\u0006)\u0017", "BiGrQ");
            EnumEmoticonMode.I[" ".length()] = I("\f\u001a\u0019", "Yhubl");
            EnumEmoticonMode.I["  ".length()] = I("\u0019\n:\u0006\u0006?\n\u0003\u0006\u001f,\u001c", "MoBrs");
        }
    }
}
