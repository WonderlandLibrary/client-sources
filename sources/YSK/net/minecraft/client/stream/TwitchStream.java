package net.minecraft.client.stream;

import net.minecraft.client.*;
import net.minecraft.client.shader.*;
import org.apache.logging.log4j.*;
import tv.twitch.chat.*;
import net.minecraft.client.gui.stream.*;
import net.minecraft.event.*;
import java.util.*;
import tv.twitch.*;
import com.mojang.authlib.properties.*;
import com.google.common.collect.*;
import com.google.common.base.*;
import java.net.*;
import net.minecraft.util.*;
import java.io.*;
import com.google.gson.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.settings.*;
import tv.twitch.broadcast.*;

public class TwitchStream implements BroadcastController.BroadcastListener, IStream, IngestServerTester.IngestTestListener, ChatController.ChatListener
{
    public static final Marker STREAM_MARKER;
    private final Minecraft mc;
    private int targetFPS;
    private static boolean field_152965_q;
    private boolean field_152960_l;
    private String field_176029_e;
    private final ChatController chatController;
    private static final Logger LOGGER;
    private boolean field_152957_i;
    private final BroadcastController broadcastController;
    private final IChatComponent twitchComponent;
    private final Map<String, ChatUserInfo> field_152955_g;
    private boolean field_152962_n;
    private static final String[] I;
    private Framebuffer framebuffer;
    private long field_152959_k;
    private boolean loggedIn;
    private boolean field_152963_o;
    private AuthFailureReason authFailureReason;
    
    @Override
    public void func_176024_e() {
    }
    
    @Override
    public void func_152893_b(final ErrorCode errorCode) {
        final Logger logger = TwitchStream.LOGGER;
        final Marker stream_MARKER = TwitchStream.STREAM_MARKER;
        final String s = TwitchStream.I[0xA7 ^ 0x84];
        final Object[] array = new Object["  ".length()];
        array["".length()] = ErrorCode.getString(errorCode);
        array[" ".length()] = errorCode.getValue();
        logger.warn(stream_MARKER, s, array);
        this.mc.ingameGUI.getChatGUI().printChatMessageWithOptionalDeletion(new ChatComponentText(TwitchStream.I[0x99 ^ 0xBD] + errorCode + TwitchStream.I[0x79 ^ 0x5C] + ErrorCode.getString(errorCode) + TwitchStream.I[0x38 ^ 0x1E]), "  ".length());
    }
    
    @Override
    public void func_176020_d(final String s) {
    }
    
    @Override
    public void func_152891_a(final BroadcastController.BroadcastState broadcastState) {
        final Logger logger = TwitchStream.LOGGER;
        final Marker stream_MARKER = TwitchStream.STREAM_MARKER;
        final String s = TwitchStream.I[0x6C ^ 0x4C];
        final Object[] array = new Object[" ".length()];
        array["".length()] = broadcastState;
        logger.debug(stream_MARKER, s, array);
        if (broadcastState == BroadcastController.BroadcastState.Initialized) {
            this.broadcastController.func_152827_a(BroadcastController.BroadcastState.Authenticated);
        }
    }
    
    @Override
    public void func_152892_c(final ErrorCode errorCode) {
        if (errorCode == ErrorCode.TTV_EC_SOUNDFLOWER_NOT_INSTALLED) {
            final ChatComponentTranslation chatComponentTranslation = new ChatComponentTranslation(TwitchStream.I[0x51 ^ 0x78], new Object["".length()]);
            chatComponentTranslation.getChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, TwitchStream.I[0x90 ^ 0xBA]));
            chatComponentTranslation.getChatStyle().setUnderlined(" ".length() != 0);
            final String s = TwitchStream.I[0xED ^ 0xC6];
            final Object[] array = new Object[" ".length()];
            array["".length()] = chatComponentTranslation;
            final ChatComponentTranslation chatComponentTranslation2 = new ChatComponentTranslation(s, array);
            chatComponentTranslation2.getChatStyle().setColor(EnumChatFormatting.DARK_RED);
            this.mc.ingameGUI.getChatGUI().printChatMessage(chatComponentTranslation2);
            "".length();
            if (-1 != -1) {
                throw null;
            }
        }
        else {
            final String s2 = TwitchStream.I[0xB5 ^ 0x99];
            final Object[] array2 = new Object[" ".length()];
            array2["".length()] = ErrorCode.getString(errorCode);
            final ChatComponentTranslation chatComponentTranslation3 = new ChatComponentTranslation(s2, array2);
            chatComponentTranslation3.getChatStyle().setColor(EnumChatFormatting.DARK_RED);
            this.mc.ingameGUI.getChatGUI().printChatMessage(chatComponentTranslation3);
        }
    }
    
    @Override
    public boolean func_152936_l() {
        return this.broadcastController.func_152849_q();
    }
    
    @Override
    public void func_176017_a(final ChatController.ChatState chatState) {
    }
    
    static BroadcastController access$1(final TwitchStream twitchStream) {
        return twitchStream.broadcastController;
    }
    
    @Override
    public boolean isBroadcasting() {
        return this.broadcastController.isBroadcasting();
    }
    
    static void access$3(final TwitchStream twitchStream, final AuthFailureReason authFailureReason) {
        twitchStream.authFailureReason = authFailureReason;
    }
    
    @Override
    public AuthFailureReason func_152918_H() {
        return this.authFailureReason;
    }
    
    @Override
    public void func_176019_a(final String s, final String s2) {
    }
    
    @Override
    public void func_176025_a(final String s, final ChatTokenizedMessage[] array) {
    }
    
    static {
        I();
        LOGGER = LogManager.getLogger();
        STREAM_MARKER = MarkerManager.getMarker(TwitchStream.I["".length()]);
        try {
            if (Util.getOSType() == Util.EnumOS.WINDOWS) {
                System.loadLibrary(TwitchStream.I[" ".length()]);
                System.loadLibrary(TwitchStream.I["  ".length()]);
                System.loadLibrary(TwitchStream.I["   ".length()]);
                if (System.getProperty(TwitchStream.I[0x4F ^ 0x4B]).contains(TwitchStream.I[0x98 ^ 0x9D])) {
                    System.loadLibrary(TwitchStream.I[0x4A ^ 0x4C]);
                    "".length();
                    if (2 <= -1) {
                        throw null;
                    }
                }
                else {
                    System.loadLibrary(TwitchStream.I[0x39 ^ 0x3E]);
                }
            }
            TwitchStream.field_152965_q = (" ".length() != 0);
            "".length();
            if (4 < 1) {
                throw null;
            }
        }
        catch (Throwable t) {
            TwitchStream.field_152965_q = ("".length() != 0);
        }
    }
    
    @Override
    public boolean func_152929_G() {
        int n;
        if (this.mc.gameSettings.streamMicToggleBehavior == " ".length()) {
            n = " ".length();
            "".length();
            if (false) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        final int n2 = n;
        if (!this.field_152962_n && this.mc.gameSettings.streamMicVolume > 0.0f && n2 == (this.field_152963_o ? 1 : 0)) {
            return "".length() != 0;
        }
        return " ".length() != 0;
    }
    
    @Override
    public void func_180605_a(final String s, final ChatRawMessage[] array) {
        final int length = array.length;
        int i = "".length();
        "".length();
        if (2 != 2) {
            throw null;
        }
        while (i < length) {
            final ChatRawMessage chatRawMessage = array[i];
            this.func_176027_a(chatRawMessage.userName, chatRawMessage);
            if (this.func_176028_a(chatRawMessage.modes, chatRawMessage.subscriptions, this.mc.gameSettings.streamChatUserFilter)) {
                final ChatComponentText chatComponentText = new ChatComponentText(chatRawMessage.userName);
                final StringBuilder sb = new StringBuilder(TwitchStream.I[0x26 ^ 0x16]);
                String s2;
                if (chatRawMessage.action) {
                    s2 = TwitchStream.I[0xAD ^ 0x9C];
                    "".length();
                    if (-1 >= 0) {
                        throw null;
                    }
                }
                else {
                    s2 = TwitchStream.I[0x9C ^ 0xAE];
                }
                final String string = sb.append(s2).toString();
                final Object[] array2 = new Object["   ".length()];
                array2["".length()] = this.twitchComponent;
                array2[" ".length()] = chatComponentText;
                array2["  ".length()] = EnumChatFormatting.getTextWithoutFormattingCodes(chatRawMessage.message);
                final ChatComponentTranslation chatComponentTranslation = new ChatComponentTranslation(string, array2);
                if (chatRawMessage.action) {
                    chatComponentTranslation.getChatStyle().setItalic(" ".length() != 0);
                }
                final ChatComponentText chatComponentText2 = new ChatComponentText(TwitchStream.I[0x6 ^ 0x35]);
                chatComponentText2.appendSibling(new ChatComponentTranslation(TwitchStream.I[0x65 ^ 0x51], new Object["".length()]));
                final Iterator<IChatComponent> iterator = GuiTwitchUserMode.func_152328_a(chatRawMessage.modes, chatRawMessage.subscriptions, null).iterator();
                "".length();
                if (true != true) {
                    throw null;
                }
                while (iterator.hasNext()) {
                    final IChatComponent chatComponent = iterator.next();
                    chatComponentText2.appendText(TwitchStream.I[0xBF ^ 0x8A]);
                    chatComponentText2.appendSibling(chatComponent);
                }
                chatComponentText.getChatStyle().setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, chatComponentText2));
                chatComponentText.getChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.TWITCH_USER_INFO, chatRawMessage.userName));
                this.mc.ingameGUI.getChatGUI().printChatMessage(chatComponentTranslation);
            }
            ++i;
        }
    }
    
    @Override
    public boolean func_152913_F() {
        return this.loggedIn;
    }
    
    @Override
    public int func_152920_A() {
        int n;
        if (this.isBroadcasting()) {
            n = this.broadcastController.getStreamInfo().viewers;
            "".length();
            if (3 >= 4) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        return n;
    }
    
    public static int formatStreamFps(final float n) {
        return MathHelper.floor_float(10.0f + n * 50.0f);
    }
    
    @Override
    public void func_152894_a(final StreamInfo streamInfo) {
        final Logger logger = TwitchStream.LOGGER;
        final Marker stream_MARKER = TwitchStream.STREAM_MARKER;
        final String s = TwitchStream.I[0x27 ^ 0x5];
        final Object[] array = new Object["  ".length()];
        array["".length()] = streamInfo.viewers;
        array[" ".length()] = streamInfo.streamId;
        logger.debug(stream_MARKER, s, array);
    }
    
    @Override
    public void func_152896_a(final IngestList list) {
    }
    
    @Override
    public boolean isReadyToBroadcast() {
        return this.broadcastController.isReadyToBroadcast();
    }
    
    @Override
    public void func_152898_a(final ErrorCode errorCode, final GameInfo[] array) {
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
            if (0 == 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public void func_176026_a(final Metadata metadata, final long n, final long n2) {
        if (this.isBroadcasting() && this.field_152957_i) {
            final long func_152844_x = this.broadcastController.func_152844_x();
            final String func_152809_a = metadata.func_152809_a();
            final String func_152806_b = metadata.func_152806_b();
            final long func_177946_b = this.broadcastController.func_177946_b(metadata.func_152810_c(), func_152844_x + n, func_152809_a, func_152806_b);
            if (func_177946_b < 0L) {
                final Logger logger = TwitchStream.LOGGER;
                final Marker stream_MARKER = TwitchStream.STREAM_MARKER;
                final String s = TwitchStream.I[0x62 ^ 0x77];
                final Object[] array = new Object["   ".length()];
                array["".length()] = func_152844_x + n;
                array[" ".length()] = func_152844_x + n2;
                array["  ".length()] = metadata;
                logger.warn(stream_MARKER, s, array);
                "".length();
                if (1 == 3) {
                    throw null;
                }
            }
            else if (this.broadcastController.func_177947_a(metadata.func_152810_c(), func_152844_x + n2, func_177946_b, func_152809_a, func_152806_b)) {
                final Logger logger2 = TwitchStream.LOGGER;
                final Marker stream_MARKER2 = TwitchStream.STREAM_MARKER;
                final String s2 = TwitchStream.I[0x41 ^ 0x57];
                final Object[] array2 = new Object["   ".length()];
                array2["".length()] = func_152844_x + n;
                array2[" ".length()] = func_152844_x + n2;
                array2["  ".length()] = metadata;
                logger2.debug(stream_MARKER2, s2, array2);
                "".length();
                if (0 >= 3) {
                    throw null;
                }
            }
            else {
                final Logger logger3 = TwitchStream.LOGGER;
                final Marker stream_MARKER3 = TwitchStream.STREAM_MARKER;
                final String s3 = TwitchStream.I[0x90 ^ 0x87];
                final Object[] array3 = new Object["   ".length()];
                array3["".length()] = func_152844_x + n;
                array3[" ".length()] = func_152844_x + n2;
                array3["  ".length()] = metadata;
                logger3.warn(stream_MARKER3, s3, array3);
            }
        }
    }
    
    protected void func_152942_I() {
        final ChatController.ChatState func_153000_j = this.chatController.func_153000_j();
        final String name = this.broadcastController.getChannelInfo().name;
        this.field_176029_e = name;
        if (func_153000_j != ChatController.ChatState.Initialized) {
            final Logger logger = TwitchStream.LOGGER;
            final String s = TwitchStream.I[0xA5 ^ 0xB4];
            final Object[] array = new Object[" ".length()];
            array["".length()] = func_153000_j;
            logger.warn(s, array);
            "".length();
            if (false) {
                throw null;
            }
        }
        else if (this.chatController.func_175989_e(this.field_176029_e) == ChatController.EnumChannelState.Disconnected) {
            this.chatController.func_152986_d(name);
            "".length();
            if (3 <= 1) {
                throw null;
            }
        }
        else {
            final Logger logger2 = TwitchStream.LOGGER;
            final String s2 = TwitchStream.I[0x31 ^ 0x23];
            final Object[] array2 = new Object[" ".length()];
            array2["".length()] = func_153000_j;
            logger2.warn(s2, array2);
        }
    }
    
    @Override
    public void unpause() {
        this.broadcastController.func_152854_G();
        this.field_152962_n = ("".length() != 0);
        this.updateStreamVolume();
    }
    
    private static void I() {
        (I = new String[0x99 ^ 0xA1])["".length()] = I("\u001e0 \u0010\u0012\u0000", "MdrUS");
        TwitchStream.I[" ".length()] = I("\f\u001a\u001a'#\u0001A\u001b'<@Y^", "mloSJ");
        TwitchStream.I["  ".length()] = I("\u0017\u0010\u001e4*\u0005\n\u001c=<I\u0013\u0018'tT", "dglQY");
        TwitchStream.I["   ".length()] = I(" $6><\u007f!5>)a9 %", "LMTSL");
        TwitchStream.I[0x28 ^ 0x2C] = I("\n9Z49\u0006\"", "eJtUK");
        TwitchStream.I[0x91 ^ 0x94] = I("L[", "zowDs");
        TwitchStream.I[0xD ^ 0xB] = I(":\u0003'(2.\u00192s`", "VjEET");
        TwitchStream.I[0x40 ^ 0x47] = I("!\u001a.\u0014\u00155\u0000;JA", "MsLys");
        TwitchStream.I[0x91 ^ 0x99] = I("\u0012\u001c\u0005\u00009.", "FkltZ");
        TwitchStream.I[0x61 ^ 0x68] = I("&\u0004!IP9\u000b9\u001e\u0006{_%\f\b&\u0006#\u001e\f*\u0006%\u0000\u00012\u001ef\r\u000b9", "HiUzg");
        TwitchStream.I[0x71 ^ 0x7B] = I("9;?zf&4'-0d`;?>99=-:59;37-!x>=&", "WVKIQ");
        TwitchStream.I[0x1F ^ 0x14] = I(",&\u0007\u001b\u0001\u0010q\u000f\u001a\u0016\u00104\u0000\u001b\u000b\u001b0\u001a\u0000\u0010", "xQnob");
        TwitchStream.I[0x37 ^ 0x3B] = I("\u001c\u001e2\r, \u0001)Y;;\u0004\"\u0018%&\u0018 ", "OvGyH");
        TwitchStream.I[0x19 ^ 0x14] = I("\u001d$#\u0017\f7#5\u0017\u00170#7T\u0005+\"=T\u0017.$$\u0017\u000by.8\u0015\u0017y=5\u0006C,>5\u0006C6=$\u001d\f7>", "YMPtc");
        TwitchStream.I[0x38 ^ 0x36] = I("\r\u0006\f%)-\u001d\u000b%+n\u001d\rk89\u0000\u0016($n\n\n*8n\u0019\u00079l;\u001a\u00079l!\u0019\u0016\"# \u001a", "NibKL");
        TwitchStream.I[0x9F ^ 0x90] = I("/\u0019\u0011\u0001\u0000\u0005\u001e\u0007\u0001\u001b\u0002\u001e\u0005B\t\u0019\u001f\u000fB\u001b\u001c\u0019\u0016\u0001\u0007K\u0013\n\u0003\u001bK\u0011\u0011B\u001a\u0018\u0015\u0010B\u0006\u0018P\f\rO\u0007\u001f\f\u0005\n\u0019P\u0011\u0016\u001d\u000e\u0011\u000f\u000b\u0001\f", "kpbbo");
        TwitchStream.I[0x73 ^ 0x63] = I("\u00149\u000f\u0017 4\"\b\u0017\"w\"\u000eY1 ?\u0015\u001a-w5\t\u00181w7\u0012Y0$3\u0013Y,$v\u0012\r727\f\u0010+0", "WVayE");
        TwitchStream.I[0x7C ^ 0x6D] = I(":69\u001b\u000e\u001a<o\u000e\u0015\u001a,,\u0012B\u00100.\u000eB\u0000,.\u000e\u0007S#2", "sXOzb");
        TwitchStream.I[0xBE ^ 0xAC] = I("%4\u0004\u0011\u001e\u0005>R\u0004\u0005\u0005.\u0011\u0018R\u000f2\u0013\u0004R\u001f.\u0013\u0004\u0017L!\u000f", "lZrpr");
        TwitchStream.I[0x9E ^ 0x8D] = I("\u0016\u0004\"(\u0015;L#d\u00020\u00053d\u0002!\u00192%\u001cu\u000620\u00101\n#%Q4\b#-\u001e;K60Q.\u0016md\n(", "UkWDq");
        TwitchStream.I[0x74 ^ 0x60] = I("\u0002\u0002\u0002\u001eO\"\u0013\u001e\u000f\u000e<G\u0001\u000f\u001b0\u0003\r\u001e\u000eq\u0006\u000f\u001e\u0006>\tL\u000b\u001bq\u001c\u0011PO*\u001a", "Qgljo");
        TwitchStream.I[0xA9 ^ 0xBC] = I("\";\u0014\"\u000fA:\u000e:K\u00121\u000f*K\u0012 \u0013+\n\ft\f+\u001f\u00000\u0000:\nA'\u0004?\u001e\u0004:\u0002+K\u0007&\u000e#K\u001a)A:\u0004A/\u001ctK\u001a)", "aTaNk");
        TwitchStream.I[0x2 ^ 0x14] = I(")-9.C\t<%?\u0002\u0017h:?\u0017\u001b,6.\u0002Z;2+\u0016\u001f&4?C\u001c:87C\u00015w.\fZ3*`C\u00015", "zHWZc");
        TwitchStream.I[0x45 ^ 0x52] = I("+\f;-i\u0010\b9?d\u0010\u0019%.%\u000eM:.0\u0002\t6?%C\u001e2:1\u0006\u00034.d\u0005\u001f8&d\u0018\u0010w?+C\u0016*qd\u0018\u0010", "cmWKD");
        TwitchStream.I[0x54 ^ 0x4C] = I("\u001a\u0012\b\u001c.;\u0003\u001c\rk+\u0018\u0014\u0004.:\u0014\u0010\b'h\u0011\u000b\u0006&h#\u000e\u0000?+\u001f", "HwyiK");
        TwitchStream.I[0x67 ^ 0x7E] = I("28\u0014\u001e6Q9\u000e\u0006r\u00032\u0010\u00077\u0002#A\u0011=\u001c:\u0004\u00001\u00186\rR4\u00038\fR\u0006\u0006>\u0015\u0011:", "qWarR");
        TwitchStream.I[0x4A ^ 0x50] = I("\u001f%1\t\u001b!8-\u000bZ-%c\u0017\u0007c*>L\u001b8q8\u0011Z'33\u001fZ8>c\u0017\u0007", "LQClz");
        TwitchStream.I[0x87 ^ 0x9C] = I("5\n\u0006\u0000 \n\u0002\u000e\u0011", "xcheC");
        TwitchStream.I[0xA7 ^ 0xBB] = I("4\u0001\u000b\u0001:\u0002\u0011D\u0002>\u0015\u0010\u0005\u001c#\t\u0012D\u0005%G!\u0013\u0018>\u0004\u001d", "gudqJ");
        TwitchStream.I[0xA2 ^ 0xBF] = I("\u000e(\u0002)<m)\u00181x>3\u00185x>3\u0005 9 .\u0019\"x9(W\u0011/$3\u0014-", "MGwEX");
        TwitchStream.I[0x28 ^ 0x36] = I("\n.*+*f 96!+19b73\".'75'8.", "FAMBD");
        TwitchStream.I[0x41 ^ 0x5E] = I("$8\u001e\u0019\u0006H6\r\u0004\r\u0005'\rP\u001d\u0006$\f\u0013\u000b\r$\n\u0016\u001d\u0004mY\u000b\u0015H\u007f\u001c\u0002\u001a\u0007%Y\u0013\u0007\f2Y\u000b\u0015A", "hWyph");
        TwitchStream.I[0x1 ^ 0x21] = I("\u0013\u001e\b\u0000/2\r\u0014\u0015k\"\u0018\u0006\u0015.q\u000f\u000f\u0000%6\t\u0003A?>L\u001c\u001c", "QlgaK");
        TwitchStream.I[0x6F ^ 0x4E] = I("\u0003\u0001\u001e/&+N\u0016=7o\u0001\u001fh78\u0007\r++", "OnyHC");
        TwitchStream.I[0x99 ^ 0xBB] = I("\u0007?!3\u00189k:8\u001f;k&&\u001d5?62Bt0.v\u000f=.$3\u000b'k<8Y'?!3\u00189k\u001a\u0012Y/6", "TKSVy");
        TwitchStream.I[0xAA ^ 0x89] = I("3>\t\u0007*Z>\u000f\u0010\"\u00139\u000e\u001b!\u001dm\u001c\u0000.\u0017(@R4\u0007mR7=\b\"\bR,\u0015)\u001fR4\u0007d", "zMzrO");
        TwitchStream.I[0x83 ^ 0xA7] = I("3\u001b;\f\u0004Z\u001b<\u000b\u0004\u001b\u0005!\u0017\u0006Z\u000e:\u0018\f\u001fRh", "zhHya");
        TwitchStream.I[0x47 ^ 0x62] = I("NX", "npDgt");
        TwitchStream.I[0x88 ^ 0xAE] = I("o", "FKzTG");
        TwitchStream.I[0x19 ^ 0x3E] = I(")\u001a</.\b\t :j\u001f\u0007s\u001a=\u0002\u001c0&j\u0003\t n9\u001f\t!:/\u000f", "khSNJ");
        TwitchStream.I[0x12 ^ 0x3A] = I("\f\u0016\n\u0017\u000b-\u0005\u0016\u0002O:\u000bE\"\u0018'\u0010\u0006\u001eO&\u0005\u0016V\u001c:\u000b\u0015\u0006\n*", "Ndevo");
        TwitchStream.I[0x93 ^ 0xBA] = I("\u001c;',\u0019\u0002a '\u0019\u0019.<%\u0019\r#0g\u000b\u0000:;-\u001e\u0003 \",\nA,=(\fA#<'\u0013", "oOUIx");
        TwitchStream.I[0x65 ^ 0x4F] = I(")2,>1{iw&'-6v#-+'6)l\")5a!45,!/$4w>-329\"m 4,'!-#+asrqlvuvk;!,'/?;0((?c1.36*$-)/+0l 7<o22*+#,/6)o.(u/21*=c!.+(;6$4+", "AFXNB");
        TwitchStream.I[0x9B ^ 0xB0] = I("+\f\u0002-(5V\u0005&(.\u0019\u0019$(:\u0014\u0015f:7\r\u001e,/4\u0017\u0007-;v\u001b\u0018)=", "XxpHI");
        TwitchStream.I[0x4C ^ 0x60] = I("\u0015?048\u000be7?8\u0010*+=8\u0004''\u007f,\b ,>.\be!98\u0012", "fKBQY");
        TwitchStream.I[0x24 ^ 0x9] = I("\n\u001b\u0016\u001c\u001d7U\u0005\u001c\u001d7U\u0002\r\u000f7\u0010Q\u001a\u0006\"\u001b\u0016\u001c\nc\u0001\u001eY\u0015>", "Cuqyn");
        TwitchStream.I[0xB4 ^ 0x9A] = I("7'\u0010\u000eW\u0012.\u0018\u0016\u0012\u0010o\u0005\u0015W\u001d!\u0018\u000e\u001e\u0015#\u0018\u0000\u0012", "tOqzw");
        TwitchStream.I[0x1F ^ 0x30] = I("\t++\u0003m,\"#\u001b(.c>\u0018m9+?\u0003)%4$", "JCJwM");
        TwitchStream.I[0x6F ^ 0x5F] = I("'\t\u0016?j7\u0015\u0005.%)O", "DawKD");
        TwitchStream.I[0x79 ^ 0x48] = I("\u0004\u001b\u00072\u0002", "avhFg");
        TwitchStream.I[0xAE ^ 0x9C] = I("6\u00129 ", "BwATH");
        TwitchStream.I[0xB3 ^ 0x80] = I("", "yrLOL");
        TwitchStream.I[0x70 ^ 0x44] = I("$ =\u001c2:z:\n6%=!\u001f<y7'\u0018'\u0003; \u0015'>$", "WTOyS");
        TwitchStream.I[0x63 ^ 0x56] = I("R", "XojbS");
        TwitchStream.I[0x27 ^ 0x11] = I("\u00140\u0013\u001aR47\u001c\u0000\u00174,\u0017\n", "WXrnr");
        TwitchStream.I[0xB7 ^ 0x80] = I("48*:B\u001398-\r\u0019>.-\u0016\u00124", "wPKNb");
    }
    
    private boolean func_176028_a(final Set<ChatUserMode> set, final Set<ChatUserSubscription> set2, final int n) {
        int n2;
        if (set.contains(ChatUserMode.TTV_CHAT_USERMODE_BANNED)) {
            n2 = "".length();
            "".length();
            if (0 < -1) {
                throw null;
            }
        }
        else if (set.contains(ChatUserMode.TTV_CHAT_USERMODE_ADMINSTRATOR)) {
            n2 = " ".length();
            "".length();
            if (2 != 2) {
                throw null;
            }
        }
        else if (set.contains(ChatUserMode.TTV_CHAT_USERMODE_MODERATOR)) {
            n2 = " ".length();
            "".length();
            if (4 <= 1) {
                throw null;
            }
        }
        else if (set.contains(ChatUserMode.TTV_CHAT_USERMODE_STAFF)) {
            n2 = " ".length();
            "".length();
            if (-1 >= 0) {
                throw null;
            }
        }
        else if (n == 0) {
            n2 = " ".length();
            "".length();
            if (3 != 3) {
                throw null;
            }
        }
        else if (n == " ".length()) {
            n2 = (set2.contains(ChatUserSubscription.TTV_CHAT_USERSUB_SUBSCRIBER) ? 1 : 0);
            "".length();
            if (3 < 1) {
                throw null;
            }
        }
        else {
            n2 = "".length();
        }
        return n2 != 0;
    }
    
    @Override
    public void func_176021_d() {
    }
    
    @Override
    public void muteMicrophone(final boolean field_152963_o) {
        this.field_152963_o = field_152963_o;
        this.updateStreamVolume();
    }
    
    static ChatController access$2(final TwitchStream twitchStream) {
        return twitchStream.chatController;
    }
    
    public static float formatStreamBps(final float n) {
        return 0.1f + n * 0.1f;
    }
    
    @Override
    public boolean func_152927_B() {
        if (this.field_176029_e != null && this.field_176029_e.equals(this.broadcastController.getChannelInfo().name)) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    @Override
    public void updateStreamVolume() {
        if (this.isBroadcasting()) {
            final float streamGameVolume = this.mc.gameSettings.streamGameVolume;
            int n;
            if (!this.field_152962_n && streamGameVolume > 0.0f) {
                n = "".length();
                "".length();
                if (3 == 1) {
                    throw null;
                }
            }
            else {
                n = " ".length();
            }
            final int n2 = n;
            final BroadcastController broadcastController = this.broadcastController;
            float playbackDeviceVolume;
            if (n2 != 0) {
                playbackDeviceVolume = 0.0f;
                "".length();
                if (-1 >= 4) {
                    throw null;
                }
            }
            else {
                playbackDeviceVolume = streamGameVolume;
            }
            broadcastController.setPlaybackDeviceVolume(playbackDeviceVolume);
            final BroadcastController broadcastController2 = this.broadcastController;
            float streamMicVolume;
            if (this.func_152929_G()) {
                streamMicVolume = 0.0f;
                "".length();
                if (false == true) {
                    throw null;
                }
            }
            else {
                streamMicVolume = this.mc.gameSettings.streamMicVolume;
            }
            broadcastController2.setRecordingDeviceVolume(streamMicVolume);
        }
    }
    
    @Override
    public void func_180606_a(final String s) {
        TwitchStream.LOGGER.debug(TwitchStream.STREAM_MARKER, TwitchStream.I[0x88 ^ 0xBE]);
    }
    
    @Override
    public void func_152899_b() {
        this.updateStreamVolume();
        TwitchStream.LOGGER.info(TwitchStream.STREAM_MARKER, TwitchStream.I[0x4F ^ 0x68]);
    }
    
    @Override
    public IngestServerTester func_152932_y() {
        return this.broadcastController.isReady();
    }
    
    @Override
    public ErrorCode func_152912_E() {
        ErrorCode errorCode;
        if (!TwitchStream.field_152965_q) {
            errorCode = ErrorCode.TTV_EC_OS_TOO_OLD;
            "".length();
            if (1 <= 0) {
                throw null;
            }
        }
        else {
            errorCode = this.broadcastController.getErrorCode();
        }
        return errorCode;
    }
    
    @Override
    public void shutdownStream() {
        TwitchStream.LOGGER.debug(TwitchStream.STREAM_MARKER, TwitchStream.I[0x9D ^ 0x91]);
        this.broadcastController.statCallback();
        this.chatController.func_175988_p();
    }
    
    @Override
    public boolean func_152928_D() {
        if (TwitchStream.field_152965_q && this.broadcastController.func_152858_b()) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    @Override
    public void func_152900_a(final ErrorCode errorCode, final AuthToken authToken) {
    }
    
    @Override
    public IngestServer[] func_152925_v() {
        return this.broadcastController.func_152855_t().getServers();
    }
    
    @Override
    public boolean isPaused() {
        return this.broadcastController.isBroadcastPaused();
    }
    
    @Override
    public void func_152907_a(final IngestServerTester ingestServerTester, final IngestServerTester.IngestTestState ingestTestState) {
        final Logger logger = TwitchStream.LOGGER;
        final Marker stream_MARKER = TwitchStream.STREAM_MARKER;
        final String s = TwitchStream.I[0x3B ^ 0x16];
        final Object[] array = new Object[" ".length()];
        array["".length()] = ingestTestState;
        logger.debug(stream_MARKER, s, array);
        if (ingestTestState == IngestServerTester.IngestTestState.Finished) {
            this.field_152960_l = (" ".length() != 0);
        }
    }
    
    @Override
    public void func_152935_j() {
        final int streamChatEnabled = this.mc.gameSettings.streamChatEnabled;
        int n;
        if (this.field_176029_e != null && this.chatController.func_175990_d(this.field_176029_e)) {
            n = " ".length();
            "".length();
            if (3 != 3) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        final int n2 = n;
        int n3;
        if (this.chatController.func_153000_j() == ChatController.ChatState.Initialized && (this.field_176029_e == null || this.chatController.func_175989_e(this.field_176029_e) == ChatController.EnumChannelState.Disconnected)) {
            n3 = " ".length();
            "".length();
            if (1 == -1) {
                throw null;
            }
        }
        else {
            n3 = "".length();
        }
        final int n4 = n3;
        if (streamChatEnabled == "  ".length()) {
            if (n2 != 0) {
                TwitchStream.LOGGER.debug(TwitchStream.STREAM_MARKER, TwitchStream.I[0x58 ^ 0x55]);
                this.chatController.func_175991_l(this.field_176029_e);
                "".length();
                if (4 == 0) {
                    throw null;
                }
            }
        }
        else if (streamChatEnabled == " ".length()) {
            if (n4 != 0 && this.broadcastController.func_152849_q()) {
                TwitchStream.LOGGER.debug(TwitchStream.STREAM_MARKER, TwitchStream.I[0x9C ^ 0x92]);
                this.func_152942_I();
                "".length();
                if (3 <= 1) {
                    throw null;
                }
            }
        }
        else if (streamChatEnabled == 0) {
            if (n2 != 0 && !this.isBroadcasting()) {
                TwitchStream.LOGGER.debug(TwitchStream.STREAM_MARKER, TwitchStream.I[0x51 ^ 0x5E]);
                this.chatController.func_175991_l(this.field_176029_e);
                "".length();
                if (1 >= 2) {
                    throw null;
                }
            }
            else if (n4 != 0 && this.isBroadcasting()) {
                TwitchStream.LOGGER.debug(TwitchStream.STREAM_MARKER, TwitchStream.I[0x3E ^ 0x2E]);
                this.func_152942_I();
            }
        }
        this.broadcastController.func_152821_H();
        this.chatController.func_152997_n();
    }
    
    @Override
    public boolean func_152908_z() {
        return this.broadcastController.isIngestTesting();
    }
    
    @Override
    public void func_176023_d(final ErrorCode errorCode) {
        if (ErrorCode.failed(errorCode)) {
            TwitchStream.LOGGER.error(TwitchStream.STREAM_MARKER, TwitchStream.I[0x71 ^ 0x5F]);
        }
    }
    
    @Override
    public void requestCommercial() {
        if (this.broadcastController.requestCommercial()) {
            TwitchStream.LOGGER.debug(TwitchStream.STREAM_MARKER, TwitchStream.I[0xF ^ 0x17]);
            "".length();
            if (0 == 4) {
                throw null;
            }
        }
        else {
            TwitchStream.LOGGER.warn(TwitchStream.STREAM_MARKER, TwitchStream.I[0x7C ^ 0x65]);
        }
    }
    
    public TwitchStream(final Minecraft mc, final Property property) {
        this.twitchComponent = new ChatComponentText(TwitchStream.I[0x97 ^ 0x9F]);
        this.field_152955_g = (Map<String, ChatUserInfo>)Maps.newHashMap();
        this.targetFPS = (0x48 ^ 0x56);
        this.field_152959_k = 0L;
        this.field_152960_l = ("".length() != 0);
        this.authFailureReason = AuthFailureReason.ERROR;
        this.mc = mc;
        this.broadcastController = new BroadcastController();
        this.chatController = new ChatController();
        this.broadcastController.func_152841_a(this);
        this.chatController.func_152990_a(this);
        this.broadcastController.func_152842_a(TwitchStream.I[0xA8 ^ 0xA1]);
        this.chatController.func_152984_a(TwitchStream.I[0x20 ^ 0x2A]);
        this.twitchComponent.getChatStyle().setColor(EnumChatFormatting.DARK_PURPLE);
        if (property != null && !Strings.isNullOrEmpty(property.getValue()) && OpenGlHelper.framebufferSupported) {
            final Thread thread = new Thread(this, TwitchStream.I[0x4D ^ 0x46], property) {
                private static final String[] I;
                private final Property val$streamProperty;
                final TwitchStream this$0;
                
                static TwitchStream access$0(final TwitchStream$1 thread) {
                    return thread.this$0;
                }
                
                @Override
                public void run() {
                    try {
                        final JsonObject jsonObject = JsonUtils.getJsonObject(JsonUtils.getJsonObject(new JsonParser().parse(HttpUtil.get(new URL(TwitchStream$1.I["".length()] + URLEncoder.encode(this.val$streamProperty.getValue(), TwitchStream$1.I[" ".length()])))), TwitchStream$1.I["  ".length()]), TwitchStream$1.I["   ".length()]);
                        if (JsonUtils.getBoolean(jsonObject, TwitchStream$1.I[0x4 ^ 0x0])) {
                            final String string = JsonUtils.getString(jsonObject, TwitchStream$1.I[0x94 ^ 0x91]);
                            final Logger access$0 = TwitchStream.access$0();
                            final Marker stream_MARKER = TwitchStream.STREAM_MARKER;
                            final String s = TwitchStream$1.I[0x99 ^ 0x9F];
                            final Object[] array = new Object[" ".length()];
                            array["".length()] = string;
                            access$0.debug(stream_MARKER, s, array);
                            final AuthToken authToken = new AuthToken();
                            authToken.data = this.val$streamProperty.getValue();
                            TwitchStream.access$1(this.this$0).func_152818_a(string, authToken);
                            TwitchStream.access$2(this.this$0).func_152998_c(string);
                            TwitchStream.access$2(this.this$0).func_152994_a(authToken);
                            Runtime.getRuntime().addShutdownHook(new Thread(this, TwitchStream$1.I[0x47 ^ 0x40]) {
                                final TwitchStream$1 this$1;
                                
                                @Override
                                public void run() {
                                    TwitchStream$1.access$0(this.this$1).shutdownStream();
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
                                        if (2 <= 1) {
                                            throw null;
                                        }
                                    }
                                    return sb.toString();
                                }
                            });
                            TwitchStream.access$1(this.this$0).func_152817_A();
                            TwitchStream.access$2(this.this$0).func_175984_n();
                            "".length();
                            if (4 == 2) {
                                throw null;
                            }
                        }
                        else {
                            TwitchStream.access$3(this.this$0, AuthFailureReason.INVALID_TOKEN);
                            TwitchStream.access$0().error(TwitchStream.STREAM_MARKER, TwitchStream$1.I[0x32 ^ 0x3A]);
                            "".length();
                            if (0 < -1) {
                                throw null;
                            }
                        }
                    }
                    catch (IOException ex) {
                        TwitchStream.access$3(this.this$0, AuthFailureReason.ERROR);
                        TwitchStream.access$0().error(TwitchStream.STREAM_MARKER, TwitchStream$1.I[0x6D ^ 0x64], (Throwable)ex);
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
                        if (1 < 0) {
                            throw null;
                        }
                    }
                    return sb.toString();
                }
                
                private static void I() {
                    (I = new String[0x58 ^ 0x52])["".length()] = I("\f\u0016\u0004(\u0018^M_9\u001b\rL\u0004/\u0002\u0010\u0001\u0018v\u001f\u0012M\u001b*\n\u000f\u0007\u001eg\u0004\u0005\u0017\u000404\u0010\r\u001b=\u0005Y", "dbpXk");
                    TwitchStream$1.I[" ".length()] = I("\u001e\u0013?~h", "KGySP");
                    TwitchStream$1.I["  ".length()] = I("\b$'\u00186421", "ZAThY");
                    TwitchStream$1.I["   ".length()] = I("\u0006% +\f", "rJKNb");
                    TwitchStream$1.I[0x65 ^ 0x61] = I("\u000188\u0001+", "wYThO");
                    TwitchStream$1.I[0x8D ^ 0x88] = I("\u00108(*\u0013\u000b* =", "eKMXL");
                    TwitchStream$1.I[0x30 ^ 0x36] = I("&\f\u0018\t(\t\r\u0005\u0002,\u0013\u001c\bA:\u000e\r\u0004A9\u0010\u0010\u0018\u0002%\\Y\u0019\u0012(\u0015\u0017\r\f(G\u0010\u001fA6\u001a", "gylaM");
                    TwitchStream$1.I[0x2B ^ 0x2C] = I("8\u001b?\u0004\u0013\u0004L%\u0018\u0005\u0018\b9\u0007\u001eL\u00049\u001f\u001b", "llVpp");
                    TwitchStream$1.I[0x5D ^ 0x55] = I("\u00040\u0006=*c-\u000710 1P9' <\u0003+d76\u001b=*c0\u0003x--/\u00114-'", "CYpXD");
                    TwitchStream$1.I[0x0 ^ 0x9] = I(")\u001c\u001e\u001c5J\u001d\u0004\u0004q\u000b\u0006\u001f\u00184\u0004\u0007\u0002\u00130\u001e\u0016K\u00078\u001e\u001bK\u0004&\u0003\u0007\b\u0018", "jskpQ");
                }
                
                static {
                    I();
                }
            };
            thread.setDaemon(" ".length() != 0);
            thread.start();
        }
    }
    
    @Override
    public void pause() {
        this.broadcastController.func_152847_F();
        this.field_152962_n = (" ".length() != 0);
        this.updateStreamVolume();
    }
    
    @Override
    public void func_152922_k() {
        if (this.broadcastController.isBroadcasting() && !this.broadcastController.isBroadcastPaused()) {
            final long nanoTime = System.nanoTime();
            int n;
            if (nanoTime - this.field_152959_k >= (222139630 + 258002644 + 211391703 + 308466023) / this.targetFPS) {
                n = " ".length();
                "".length();
                if (4 != 4) {
                    throw null;
                }
            }
            else {
                n = "".length();
            }
            if (n != 0) {
                final FrameBuffer func_152822_N = this.broadcastController.func_152822_N();
                final Framebuffer framebuffer = this.mc.getFramebuffer();
                this.framebuffer.bindFramebuffer(" ".length() != 0);
                GlStateManager.matrixMode(1991 + 4311 - 3073 + 2660);
                GlStateManager.pushMatrix();
                GlStateManager.loadIdentity();
                GlStateManager.ortho(0.0, this.framebuffer.framebufferWidth, this.framebuffer.framebufferHeight, 0.0, 1000.0, 3000.0);
                GlStateManager.matrixMode(5440 + 5645 - 6926 + 1729);
                GlStateManager.pushMatrix();
                GlStateManager.loadIdentity();
                GlStateManager.translate(0.0f, 0.0f, -2000.0f);
                GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
                GlStateManager.viewport("".length(), "".length(), this.framebuffer.framebufferWidth, this.framebuffer.framebufferHeight);
                GlStateManager.enableTexture2D();
                GlStateManager.disableAlpha();
                GlStateManager.disableBlend();
                final float n2 = this.framebuffer.framebufferWidth;
                final float n3 = this.framebuffer.framebufferHeight;
                final float n4 = framebuffer.framebufferWidth / framebuffer.framebufferTextureWidth;
                final float n5 = framebuffer.framebufferHeight / framebuffer.framebufferTextureHeight;
                framebuffer.bindFramebufferTexture();
                GL11.glTexParameterf(6 + 2222 - 750 + 2075, 1643 + 4965 + 828 + 2805, 9729.0f);
                GL11.glTexParameterf(1059 + 1711 - 450 + 1233, 8443 + 5953 - 13737 + 9581, 9729.0f);
                final Tessellator instance = Tessellator.getInstance();
                final WorldRenderer worldRenderer = instance.getWorldRenderer();
                worldRenderer.begin(0x94 ^ 0x93, DefaultVertexFormats.POSITION_TEX);
                worldRenderer.pos(0.0, n3, 0.0).tex(0.0, n5).endVertex();
                worldRenderer.pos(n2, n3, 0.0).tex(n4, n5).endVertex();
                worldRenderer.pos(n2, 0.0, 0.0).tex(n4, 0.0).endVertex();
                worldRenderer.pos(0.0, 0.0, 0.0).tex(0.0, 0.0).endVertex();
                instance.draw();
                framebuffer.unbindFramebufferTexture();
                GlStateManager.popMatrix();
                GlStateManager.matrixMode(5269 + 688 - 2352 + 2284);
                GlStateManager.popMatrix();
                GlStateManager.matrixMode(1377 + 2779 + 265 + 1467);
                this.broadcastController.captureFramebuffer(func_152822_N);
                this.framebuffer.unbindFramebuffer();
                this.broadcastController.submitStreamFrame(func_152822_N);
                this.field_152959_k = nanoTime;
            }
        }
    }
    
    @Override
    public void func_152901_c() {
        TwitchStream.LOGGER.info(TwitchStream.STREAM_MARKER, TwitchStream.I[0x50 ^ 0x78]);
    }
    
    @Override
    public void stopBroadcasting() {
        if (this.broadcastController.stopBroadcasting()) {
            TwitchStream.LOGGER.info(TwitchStream.STREAM_MARKER, TwitchStream.I[0xB8 ^ 0xA4]);
            "".length();
            if (-1 >= 2) {
                throw null;
            }
        }
        else {
            TwitchStream.LOGGER.warn(TwitchStream.STREAM_MARKER, TwitchStream.I[0x25 ^ 0x38]);
        }
    }
    
    @Override
    public void func_176018_a(final String s, final ChatUserInfo[] array, final ChatUserInfo[] array2, final ChatUserInfo[] array3) {
        final int length = array2.length;
        int i = "".length();
        "".length();
        if (4 < 4) {
            throw null;
        }
        while (i < length) {
            this.field_152955_g.remove(array2[i].displayName);
            ++i;
        }
        final int length2 = array3.length;
        int j = "".length();
        "".length();
        if (1 <= -1) {
            throw null;
        }
        while (j < length2) {
            final ChatUserInfo chatUserInfo = array3[j];
            this.field_152955_g.put(chatUserInfo.displayName, chatUserInfo);
            ++j;
        }
        final int length3 = array.length;
        int k = "".length();
        "".length();
        if (3 <= -1) {
            throw null;
        }
        while (k < length3) {
            final ChatUserInfo chatUserInfo2 = array[k];
            this.field_152955_g.put(chatUserInfo2.displayName, chatUserInfo2);
            ++k;
        }
    }
    
    @Override
    public void func_152930_t() {
        final GameSettings gameSettings = this.mc.gameSettings;
        final VideoParams func_152834_a = this.broadcastController.func_152834_a(formatStreamKbps(gameSettings.streamKbps), formatStreamFps(gameSettings.streamFps), formatStreamBps(gameSettings.streamBytesPerPixel), this.mc.displayWidth / this.mc.displayHeight);
        switch (gameSettings.streamCompression) {
            case 0: {
                func_152834_a.encodingCpuUsage = EncodingCpuUsage.TTV_ECU_LOW;
                "".length();
                if (1 <= -1) {
                    throw null;
                }
                break;
            }
            case 1: {
                func_152834_a.encodingCpuUsage = EncodingCpuUsage.TTV_ECU_MEDIUM;
                "".length();
                if (1 >= 3) {
                    throw null;
                }
                break;
            }
            case 2: {
                func_152834_a.encodingCpuUsage = EncodingCpuUsage.TTV_ECU_HIGH;
                break;
            }
        }
        if (this.framebuffer == null) {
            this.framebuffer = new Framebuffer(func_152834_a.outputWidth, func_152834_a.outputHeight, "".length() != 0);
            "".length();
            if (4 <= 1) {
                throw null;
            }
        }
        else {
            this.framebuffer.createBindFramebuffer(func_152834_a.outputWidth, func_152834_a.outputHeight);
        }
        if (gameSettings.streamPreferredServer != null && gameSettings.streamPreferredServer.length() > 0) {
            final IngestServer[] func_152925_v;
            final int length = (func_152925_v = this.func_152925_v()).length;
            int i = "".length();
            "".length();
            if (4 <= 3) {
                throw null;
            }
            while (i < length) {
                final IngestServer ingestServer = func_152925_v[i];
                if (ingestServer.serverUrl.equals(gameSettings.streamPreferredServer)) {
                    this.broadcastController.func_152824_a(ingestServer);
                    "".length();
                    if (4 != 4) {
                        throw null;
                    }
                    break;
                }
                else {
                    ++i;
                }
            }
        }
        this.targetFPS = func_152834_a.targetFps;
        this.field_152957_i = gameSettings.streamSendMetadata;
        this.broadcastController.func_152836_a(func_152834_a);
        final Logger logger = TwitchStream.LOGGER;
        final Marker stream_MARKER = TwitchStream.STREAM_MARKER;
        final String s = TwitchStream.I[0xB9 ^ 0xA3];
        final Object[] array = new Object[0x41 ^ 0x45];
        array["".length()] = func_152834_a.outputWidth;
        array[" ".length()] = func_152834_a.outputHeight;
        array["  ".length()] = func_152834_a.maxKbps;
        array["   ".length()] = this.broadcastController.func_152833_s().serverUrl;
        logger.info(stream_MARKER, s, array);
        this.broadcastController.func_152828_a(null, TwitchStream.I[0xBE ^ 0xA5], null);
    }
    
    @Override
    public void func_152895_a() {
        TwitchStream.LOGGER.info(TwitchStream.STREAM_MARKER, TwitchStream.I[0x94 ^ 0xB5]);
    }
    
    @Override
    public String func_152921_C() {
        return this.field_176029_e;
    }
    
    @Override
    public void func_180607_b(final String s) {
        TwitchStream.LOGGER.debug(TwitchStream.STREAM_MARKER, TwitchStream.I[0xF ^ 0x38]);
        this.field_152955_g.clear();
    }
    
    @Override
    public void func_152917_b(final String s) {
        this.chatController.func_175986_a(this.field_176029_e, s);
    }
    
    private void func_176027_a(final String displayName, final ChatRawMessage chatRawMessage) {
        ChatUserInfo chatUserInfo = this.field_152955_g.get(displayName);
        if (chatUserInfo == null) {
            chatUserInfo = new ChatUserInfo();
            chatUserInfo.displayName = displayName;
            this.field_152955_g.put(displayName, chatUserInfo);
        }
        chatUserInfo.subscriptions = chatRawMessage.subscriptions;
        chatUserInfo.modes = chatRawMessage.modes;
        chatUserInfo.nameColorARGB = chatRawMessage.nameColorARGB;
    }
    
    @Override
    public void func_176022_e(final ErrorCode errorCode) {
        if (ErrorCode.failed(errorCode)) {
            TwitchStream.LOGGER.error(TwitchStream.STREAM_MARKER, TwitchStream.I[0x5 ^ 0x2A]);
        }
    }
    
    @Override
    public ChatUserInfo func_152926_a(final String s) {
        return this.field_152955_g.get(s);
    }
    
    public static int formatStreamKbps(final float n) {
        return MathHelper.floor_float(230.0f + n * 3270.0f);
    }
    
    static Logger access$0() {
        return TwitchStream.LOGGER;
    }
    
    @Override
    public void func_152911_a(final Metadata metadata, final long n) {
        if (this.isBroadcasting() && this.field_152957_i) {
            final long func_152844_x = this.broadcastController.func_152844_x();
            if (!this.broadcastController.func_152840_a(metadata.func_152810_c(), func_152844_x + n, metadata.func_152809_a(), metadata.func_152806_b())) {
                final Logger logger = TwitchStream.LOGGER;
                final Marker stream_MARKER = TwitchStream.STREAM_MARKER;
                final String s = TwitchStream.I[0x32 ^ 0x21];
                final Object[] array = new Object["  ".length()];
                array["".length()] = func_152844_x + n;
                array[" ".length()] = metadata;
                logger.warn(stream_MARKER, s, array);
                "".length();
                if (3 >= 4) {
                    throw null;
                }
            }
            else {
                final Logger logger2 = TwitchStream.LOGGER;
                final Marker stream_MARKER2 = TwitchStream.STREAM_MARKER;
                final String s2 = TwitchStream.I[0xB8 ^ 0xAC];
                final Object[] array2 = new Object["  ".length()];
                array2["".length()] = func_152844_x + n;
                array2[" ".length()] = metadata;
                logger2.debug(stream_MARKER2, s2, array2);
            }
        }
    }
    
    @Override
    public void func_152909_x() {
        final IngestServerTester func_152838_J = this.broadcastController.func_152838_J();
        if (func_152838_J != null) {
            func_152838_J.func_153042_a(this);
        }
    }
    
    @Override
    public void func_152897_a(final ErrorCode errorCode) {
        if (ErrorCode.succeeded(errorCode)) {
            TwitchStream.LOGGER.debug(TwitchStream.STREAM_MARKER, TwitchStream.I[0xAF ^ 0xB1]);
            this.loggedIn = (" ".length() != 0);
            "".length();
            if (2 == 0) {
                throw null;
            }
        }
        else {
            final Logger logger = TwitchStream.LOGGER;
            final Marker stream_MARKER = TwitchStream.STREAM_MARKER;
            final String s = TwitchStream.I[0x11 ^ 0xE];
            final Object[] array = new Object["  ".length()];
            array["".length()] = ErrorCode.getString(errorCode);
            array[" ".length()] = errorCode.getValue();
            logger.warn(stream_MARKER, s, array);
            this.loggedIn = ("".length() != 0);
        }
    }
    
    @Override
    public void func_176016_c(final String s) {
    }
}
