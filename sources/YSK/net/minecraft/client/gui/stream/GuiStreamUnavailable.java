package net.minecraft.client.gui.stream;

import com.google.common.collect.*;
import net.minecraft.client.resources.*;
import net.minecraft.client.gui.*;
import java.io.*;
import org.apache.logging.log4j.*;
import java.net.*;
import java.lang.reflect.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.stream.*;
import tv.twitch.*;
import net.minecraft.util.*;
import java.util.*;

public class GuiStreamUnavailable extends GuiScreen
{
    private static int[] $SWITCH_TABLE$net$minecraft$client$stream$IStream$AuthFailureReason;
    private static final String[] I;
    private final Reason field_152326_h;
    private final GuiScreen parentScreen;
    private final IChatComponent field_152324_f;
    private static final Logger field_152322_a;
    private final List<ChatComponentTranslation> field_152327_i;
    private static int[] $SWITCH_TABLE$net$minecraft$client$gui$stream$GuiStreamUnavailable$Reason;
    private final List<String> field_152323_r;
    private static int[] $SWITCH_TABLE$net$minecraft$util$Util$EnumOS;
    
    public GuiStreamUnavailable(final GuiScreen parentScreen, final Reason field_152326_h, final List<ChatComponentTranslation> field_152327_i) {
        this.field_152324_f = new ChatComponentTranslation(GuiStreamUnavailable.I["".length()], new Object["".length()]);
        this.field_152323_r = (List<String>)Lists.newArrayList();
        this.parentScreen = parentScreen;
        this.field_152326_h = field_152326_h;
        this.field_152327_i = field_152327_i;
    }
    
    private static void I() {
        (I = new String[0x79 ^ 0x6A])["".length()] = I("1\u0007\u001a&*/]\u001d-*4\u0012\u0001/* \u001f\rm?+\u0007\u0004&", "BshCK");
        GuiStreamUnavailable.I[" ".length()] = I("", "NQgdO");
        GuiStreamUnavailable.I["  ".length()] = I("\u0001 \u0013i\b\u0007;\u0019\"\u0007", "fUzGk");
        GuiStreamUnavailable.I["   ".length()] = I("7\u001d,{\u00161\u0006&0\u0019", "PhEUu");
        GuiStreamUnavailable.I[0xA0 ^ 0xA4] = I("/ \r\u00058}{V\u0014($;\f\u001b?i9\u0016\u001f*)3W\u0016$*{\u0014\u0010d41\r\u0001\")3\n", "GTyuK");
        GuiStreamUnavailable.I[0x2 ^ 0x7] = I("\u001d\u0004#\u001c>O_x\r.\u0016\u001f\"\u00029[\u001d8\u0006,\u001b\u0017y\u000f\"\u0018_:\u0005*\u0007\u0011#\t", "upWlM");
        GuiStreamUnavailable.I[0xB4 ^ 0xB2] = I("\u000b\u000e\u00065NLU\u00052\u0003M\u001b\u00025\u0018\u0006T\u0011*\u0019L\u0015\u0001=[", "czrEt");
        GuiStreamUnavailable.I[0x8D ^ 0x8A] = I("\u000f\u0013=\u0019wHH+\u001c*\u0014I$\u0006'\u0006\t.G.\b\nf\u000b?\b\u0010:\fb*$", "ggIiM");
        GuiStreamUnavailable.I[0x44 ^ 0x4C] = I("\u0004\u0016\u0019'c\u000f\u0000\u001bh\t\u000b\u0004\u00042\"\u001e", "nwoFM");
        GuiStreamUnavailable.I[0xB3 ^ 0xBA] = I("?\u00033.#+\r3\u00056", "XfGjF");
        GuiStreamUnavailable.I[0x7C ^ 0x76] = I("#\n)\u0005\u001a$", "AxFri");
        GuiStreamUnavailable.I[0x93 ^ 0x98] = I("\u000b\u0007\u0007?\u001c&O\u0006s\u00178\r\u001cs\u0014!\u0006\u0019", "HhrSx");
        GuiStreamUnavailable.I[0xB7 ^ 0xBB] = I("6\u001f\u001727(E\u0010973\n\f;7'\u0007\u0000y8*4\u000359k\u001d\u0000%%,\u0004\u000b", "EkeWV");
        GuiStreamUnavailable.I[0x6E ^ 0x63] = I("\u0015&\u0014\u0011+\u000b|\u0013\u001a+\u00103\u000f\u0018+\u0004>\u0003Z$\t\r\u0000\u0016%H0\n\u0011$\u0002", "fRftJ");
        GuiStreamUnavailable.I[0xAA ^ 0xA4] = I("%\u001a+*\u0007;@,!\u0007 \u000f0#\u00074\u0002<a\b91?-\tx\u000f+-", "VnYOf");
        GuiStreamUnavailable.I[0x9F ^ 0x90] = I("#.9\u0002#=t>\t#&;\"\u000b#26.I,?\u0005-\u0005-~?3\u0013", "PZKgB");
        GuiStreamUnavailable.I[0x75 ^ 0x65] = I("\u0016\u0007\u000bt\u0013u\n\n2\u0003u'(\u0017GcRH1\u000e!FK7\u000b9F\n=G4F,\u0012GfTH1\u000e!F\u0015?\u0006!\u0000\n!\n", "UfeSg");
        GuiStreamUnavailable.I[0x3A ^ 0x2B] = I("3%\r\u0010\u0011/\r\u0005\u0007\u0011\"!\u0017;\u0006(9\u0001\n", "GRddr");
        GuiStreamUnavailable.I[0xBE ^ 0xAC] = I(":670\u0013$l0;\u0013?#,9\u0013+. {\u001b'+1<\u0013%+?4\u0006 -+\n\u0014(+) \u0000,l -\u0006;#", "IBEUr");
    }
    
    @Override
    public void initGui() {
        if (this.field_152323_r.isEmpty()) {
            this.field_152323_r.addAll(this.fontRendererObj.listFormattedStringToWidth(this.field_152326_h.func_152561_a().getFormattedText(), (int)(this.width * 0.75f)));
            if (this.field_152327_i != null) {
                this.field_152323_r.add(GuiStreamUnavailable.I[" ".length()]);
                final Iterator<ChatComponentTranslation> iterator = this.field_152327_i.iterator();
                "".length();
                if (false) {
                    throw null;
                }
                while (iterator.hasNext()) {
                    this.field_152323_r.add(iterator.next().getUnformattedTextForChat());
                }
            }
        }
        if (this.field_152326_h.func_152559_b() != null) {
            this.buttonList.add(new GuiButton("".length(), this.width / "  ".length() - (32 + 55 - 19 + 87), this.height - (0x48 ^ 0x7A), 47 + 55 - 57 + 105, 0xA0 ^ 0xB4, I18n.format(GuiStreamUnavailable.I["  ".length()], new Object["".length()])));
            this.buttonList.add(new GuiButton(" ".length(), this.width / "  ".length() - (1 + 54 + 76 + 24) + (137 + 133 - 221 + 111), this.height - (0x88 ^ 0xBA), 86 + 89 - 53 + 28, 0x1B ^ 0xF, I18n.format(this.field_152326_h.func_152559_b().getFormattedText(), new Object["".length()])));
            "".length();
            if (3 < 2) {
                throw null;
            }
        }
        else {
            this.buttonList.add(new GuiButton("".length(), this.width / "  ".length() - (0xFB ^ 0xB0), this.height - (0x95 ^ 0xA7), 25 + 69 + 45 + 11, 0xA4 ^ 0xB0, I18n.format(GuiStreamUnavailable.I["   ".length()], new Object["".length()])));
        }
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        this.drawDefaultBackground();
        int max = Math.max((int)(this.height * 0.85 / 2.0 - this.field_152323_r.size() * this.fontRendererObj.FONT_HEIGHT / 2.0f), 0x75 ^ 0x47);
        this.drawCenteredString(this.fontRendererObj, this.field_152324_f.getFormattedText(), this.width / "  ".length(), max - this.fontRendererObj.FONT_HEIGHT * "  ".length(), 9584018 + 1344507 + 206683 + 5642007);
        final Iterator<String> iterator = this.field_152323_r.iterator();
        "".length();
        if (2 != 2) {
            throw null;
        }
        while (iterator.hasNext()) {
            this.drawCenteredString(this.fontRendererObj, iterator.next(), this.width / "  ".length(), max, 5293049 + 8199906 - 3256017 + 289942);
            max += this.fontRendererObj.FONT_HEIGHT;
        }
        super.drawScreen(n, n2, n3);
    }
    
    static int[] $SWITCH_TABLE$net$minecraft$client$gui$stream$GuiStreamUnavailable$Reason() {
        final int[] $switch_TABLE$net$minecraft$client$gui$stream$GuiStreamUnavailable$Reason = GuiStreamUnavailable.$SWITCH_TABLE$net$minecraft$client$gui$stream$GuiStreamUnavailable$Reason;
        if ($switch_TABLE$net$minecraft$client$gui$stream$GuiStreamUnavailable$Reason != null) {
            return $switch_TABLE$net$minecraft$client$gui$stream$GuiStreamUnavailable$Reason;
        }
        final int[] $switch_TABLE$net$minecraft$client$gui$stream$GuiStreamUnavailable$Reason2 = new int[Reason.values().length];
        try {
            $switch_TABLE$net$minecraft$client$gui$stream$GuiStreamUnavailable$Reason2[Reason.ACCOUNT_NOT_BOUND.ordinal()] = (0x47 ^ 0x4F);
            "".length();
            if (3 <= 1) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            $switch_TABLE$net$minecraft$client$gui$stream$GuiStreamUnavailable$Reason2[Reason.ACCOUNT_NOT_MIGRATED.ordinal()] = (0x2F ^ 0x28);
            "".length();
            if (0 <= -1) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError2) {}
        try {
            $switch_TABLE$net$minecraft$client$gui$stream$GuiStreamUnavailable$Reason2[Reason.FAILED_TWITCH_AUTH.ordinal()] = (0x10 ^ 0x19);
            "".length();
            if (false) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError3) {}
        try {
            $switch_TABLE$net$minecraft$client$gui$stream$GuiStreamUnavailable$Reason2[Reason.FAILED_TWITCH_AUTH_ERROR.ordinal()] = (0x9B ^ 0x91);
            "".length();
            if (3 <= 2) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError4) {}
        try {
            $switch_TABLE$net$minecraft$client$gui$stream$GuiStreamUnavailable$Reason2[Reason.INITIALIZATION_FAILURE.ordinal()] = (0x7B ^ 0x70);
            "".length();
            if (2 < 1) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError5) {}
        try {
            $switch_TABLE$net$minecraft$client$gui$stream$GuiStreamUnavailable$Reason2[Reason.LIBRARY_ARCH_MISMATCH.ordinal()] = "  ".length();
            "".length();
            if (3 != 3) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError6) {}
        try {
            $switch_TABLE$net$minecraft$client$gui$stream$GuiStreamUnavailable$Reason2[Reason.LIBRARY_FAILURE.ordinal()] = "   ".length();
            "".length();
            if (2 >= 3) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError7) {}
        try {
            $switch_TABLE$net$minecraft$client$gui$stream$GuiStreamUnavailable$Reason2[Reason.NO_FBO.ordinal()] = " ".length();
            "".length();
            if (0 < -1) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError8) {}
        try {
            $switch_TABLE$net$minecraft$client$gui$stream$GuiStreamUnavailable$Reason2[Reason.UNKNOWN.ordinal()] = (0xB0 ^ 0xBC);
            "".length();
            if (true != true) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError9) {}
        try {
            $switch_TABLE$net$minecraft$client$gui$stream$GuiStreamUnavailable$Reason2[Reason.UNSUPPORTED_OS_MAC.ordinal()] = (0x79 ^ 0x7C);
            "".length();
            if (0 >= 4) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError10) {}
        try {
            $switch_TABLE$net$minecraft$client$gui$stream$GuiStreamUnavailable$Reason2[Reason.UNSUPPORTED_OS_OTHER.ordinal()] = (0x13 ^ 0x15);
            "".length();
            if (-1 >= 4) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError11) {}
        try {
            $switch_TABLE$net$minecraft$client$gui$stream$GuiStreamUnavailable$Reason2[Reason.UNSUPPORTED_OS_WINDOWS.ordinal()] = (0x4D ^ 0x49);
            "".length();
            if (0 < 0) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError12) {}
        return GuiStreamUnavailable.$SWITCH_TABLE$net$minecraft$client$gui$stream$GuiStreamUnavailable$Reason = $switch_TABLE$net$minecraft$client$gui$stream$GuiStreamUnavailable$Reason2;
    }
    
    static int[] $SWITCH_TABLE$net$minecraft$util$Util$EnumOS() {
        final int[] $switch_TABLE$net$minecraft$util$Util$EnumOS = GuiStreamUnavailable.$SWITCH_TABLE$net$minecraft$util$Util$EnumOS;
        if ($switch_TABLE$net$minecraft$util$Util$EnumOS != null) {
            return $switch_TABLE$net$minecraft$util$Util$EnumOS;
        }
        final int[] $switch_TABLE$net$minecraft$util$Util$EnumOS2 = new int[Util.EnumOS.values().length];
        try {
            $switch_TABLE$net$minecraft$util$Util$EnumOS2[Util.EnumOS.LINUX.ordinal()] = " ".length();
            "".length();
            if (0 >= 3) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            $switch_TABLE$net$minecraft$util$Util$EnumOS2[Util.EnumOS.OSX.ordinal()] = (0x2D ^ 0x29);
            "".length();
            if (3 < 1) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError2) {}
        try {
            $switch_TABLE$net$minecraft$util$Util$EnumOS2[Util.EnumOS.SOLARIS.ordinal()] = "  ".length();
            "".length();
            if (true != true) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError3) {}
        try {
            $switch_TABLE$net$minecraft$util$Util$EnumOS2[Util.EnumOS.UNKNOWN.ordinal()] = (0x22 ^ 0x27);
            "".length();
            if (4 <= 0) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError4) {}
        try {
            $switch_TABLE$net$minecraft$util$Util$EnumOS2[Util.EnumOS.WINDOWS.ordinal()] = "   ".length();
            "".length();
            if (1 <= -1) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError5) {}
        return GuiStreamUnavailable.$SWITCH_TABLE$net$minecraft$util$Util$EnumOS = $switch_TABLE$net$minecraft$util$Util$EnumOS2;
    }
    
    @Override
    public void onGuiClosed() {
    }
    
    @Override
    protected void actionPerformed(final GuiButton guiButton) throws IOException {
        if (guiButton.enabled) {
            if (guiButton.id == " ".length()) {
                switch ($SWITCH_TABLE$net$minecraft$client$gui$stream$GuiStreamUnavailable$Reason()[this.field_152326_h.ordinal()]) {
                    case 8:
                    case 9: {
                        this.func_152320_a(GuiStreamUnavailable.I[0xC5 ^ 0xC1]);
                        "".length();
                        if (-1 != -1) {
                            throw null;
                        }
                        break;
                    }
                    case 7: {
                        this.func_152320_a(GuiStreamUnavailable.I[0x55 ^ 0x50]);
                        "".length();
                        if (3 <= -1) {
                            throw null;
                        }
                        break;
                    }
                    case 5: {
                        this.func_152320_a(GuiStreamUnavailable.I[0x3D ^ 0x3B]);
                        "".length();
                        if (-1 >= 1) {
                            throw null;
                        }
                        break;
                    }
                    case 3:
                    case 11:
                    case 12: {
                        this.func_152320_a(GuiStreamUnavailable.I[0xA3 ^ 0xA4]);
                        break;
                    }
                }
            }
            this.mc.displayGuiScreen(this.parentScreen);
        }
    }
    
    static {
        I();
        field_152322_a = LogManager.getLogger();
    }
    
    public GuiStreamUnavailable(final GuiScreen guiScreen, final Reason reason) {
        this(guiScreen, reason, null);
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
            if (false == true) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    private void func_152320_a(final String s) {
        try {
            final Class<?> forName = Class.forName(GuiStreamUnavailable.I[0x7F ^ 0x77]);
            final Object invoke = forName.getMethod(GuiStreamUnavailable.I[0x97 ^ 0x9E], (Class<?>[])new Class["".length()]).invoke(null, new Object["".length()]);
            final Class<?> clazz = forName;
            final String s2 = GuiStreamUnavailable.I[0xAB ^ 0xA1];
            final Class[] array = new Class[" ".length()];
            array["".length()] = URI.class;
            final Method method = clazz.getMethod(s2, (Class<?>[])array);
            final Object o = invoke;
            final Object[] array2 = new Object[" ".length()];
            array2["".length()] = new URI(s);
            method.invoke(o, array2);
            "".length();
            if (false) {
                throw null;
            }
        }
        catch (Throwable t) {
            GuiStreamUnavailable.field_152322_a.error(GuiStreamUnavailable.I[0x13 ^ 0x18], t);
        }
    }
    
    static int[] $SWITCH_TABLE$net$minecraft$client$stream$IStream$AuthFailureReason() {
        final int[] $switch_TABLE$net$minecraft$client$stream$IStream$AuthFailureReason = GuiStreamUnavailable.$SWITCH_TABLE$net$minecraft$client$stream$IStream$AuthFailureReason;
        if ($switch_TABLE$net$minecraft$client$stream$IStream$AuthFailureReason != null) {
            return $switch_TABLE$net$minecraft$client$stream$IStream$AuthFailureReason;
        }
        final int[] $switch_TABLE$net$minecraft$client$stream$IStream$AuthFailureReason2 = new int[IStream.AuthFailureReason.values().length];
        try {
            $switch_TABLE$net$minecraft$client$stream$IStream$AuthFailureReason2[IStream.AuthFailureReason.ERROR.ordinal()] = " ".length();
            "".length();
            if (2 < 0) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            $switch_TABLE$net$minecraft$client$stream$IStream$AuthFailureReason2[IStream.AuthFailureReason.INVALID_TOKEN.ordinal()] = "  ".length();
            "".length();
            if (2 >= 4) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError2) {}
        return GuiStreamUnavailable.$SWITCH_TABLE$net$minecraft$client$stream$IStream$AuthFailureReason = $switch_TABLE$net$minecraft$client$stream$IStream$AuthFailureReason2;
    }
    
    public static void func_152321_a(final GuiScreen guiScreen) {
        final Minecraft minecraft = Minecraft.getMinecraft();
        final IStream twitchStream = minecraft.getTwitchStream();
        if (!OpenGlHelper.framebufferSupported) {
            final ArrayList arrayList;
            final ArrayList list = arrayList = Lists.newArrayList();
            final String s = GuiStreamUnavailable.I[0x26 ^ 0x2A];
            final Object[] array = new Object[" ".length()];
            array["".length()] = GL11.glGetString(6106 + 4244 - 7791 + 5379);
            arrayList.add(new ChatComponentTranslation(s, array));
            final ArrayList list2 = list;
            final String s2 = GuiStreamUnavailable.I[0x95 ^ 0x98];
            final Object[] array2 = new Object[" ".length()];
            array2["".length()] = GLContext.getCapabilities().GL_EXT_blend_func_separate;
            list2.add(new ChatComponentTranslation(s2, array2));
            final ArrayList list3 = list;
            final String s3 = GuiStreamUnavailable.I[0x56 ^ 0x58];
            final Object[] array3 = new Object[" ".length()];
            array3["".length()] = GLContext.getCapabilities().GL_ARB_framebuffer_object;
            list3.add(new ChatComponentTranslation(s3, array3));
            final ArrayList list4 = list;
            final String s4 = GuiStreamUnavailable.I[0xC8 ^ 0xC7];
            final Object[] array4 = new Object[" ".length()];
            array4["".length()] = GLContext.getCapabilities().GL_EXT_framebuffer_object;
            list4.add(new ChatComponentTranslation(s4, array4));
            minecraft.displayGuiScreen(new GuiStreamUnavailable(guiScreen, Reason.NO_FBO, list));
            "".length();
            if (false) {
                throw null;
            }
        }
        else if (twitchStream instanceof NullStream) {
            if (((NullStream)twitchStream).func_152937_a().getMessage().contains(GuiStreamUnavailable.I[0x14 ^ 0x4])) {
                minecraft.displayGuiScreen(new GuiStreamUnavailable(guiScreen, Reason.LIBRARY_ARCH_MISMATCH));
                "".length();
                if (3 <= -1) {
                    throw null;
                }
            }
            else {
                minecraft.displayGuiScreen(new GuiStreamUnavailable(guiScreen, Reason.LIBRARY_FAILURE));
                "".length();
                if (true != true) {
                    throw null;
                }
            }
        }
        else if (!twitchStream.func_152928_D() && twitchStream.func_152912_E() == ErrorCode.TTV_EC_OS_TOO_OLD) {
            switch ($SWITCH_TABLE$net$minecraft$util$Util$EnumOS()[Util.getOSType().ordinal()]) {
                case 3: {
                    minecraft.displayGuiScreen(new GuiStreamUnavailable(guiScreen, Reason.UNSUPPORTED_OS_WINDOWS));
                    "".length();
                    if (0 < -1) {
                        throw null;
                    }
                    break;
                }
                case 4: {
                    minecraft.displayGuiScreen(new GuiStreamUnavailable(guiScreen, Reason.UNSUPPORTED_OS_MAC));
                    "".length();
                    if (0 >= 2) {
                        throw null;
                    }
                    break;
                }
                default: {
                    minecraft.displayGuiScreen(new GuiStreamUnavailable(guiScreen, Reason.UNSUPPORTED_OS_OTHER));
                    "".length();
                    if (1 >= 2) {
                        throw null;
                    }
                    break;
                }
            }
        }
        else if (!minecraft.getTwitchDetails().containsKey((Object)GuiStreamUnavailable.I[0x37 ^ 0x26])) {
            if (minecraft.getSession().getSessionType() == Session.Type.LEGACY) {
                minecraft.displayGuiScreen(new GuiStreamUnavailable(guiScreen, Reason.ACCOUNT_NOT_MIGRATED));
                "".length();
                if (false == true) {
                    throw null;
                }
            }
            else {
                minecraft.displayGuiScreen(new GuiStreamUnavailable(guiScreen, Reason.ACCOUNT_NOT_BOUND));
                "".length();
                if (3 == 1) {
                    throw null;
                }
            }
        }
        else if (!twitchStream.func_152913_F()) {
            switch ($SWITCH_TABLE$net$minecraft$client$stream$IStream$AuthFailureReason()[twitchStream.func_152918_H().ordinal()]) {
                case 2: {
                    minecraft.displayGuiScreen(new GuiStreamUnavailable(guiScreen, Reason.FAILED_TWITCH_AUTH));
                    "".length();
                    if (2 == 4) {
                        throw null;
                    }
                    break;
                }
                default: {
                    minecraft.displayGuiScreen(new GuiStreamUnavailable(guiScreen, Reason.FAILED_TWITCH_AUTH_ERROR));
                    "".length();
                    if (4 == 2) {
                        throw null;
                    }
                    break;
                }
            }
        }
        else if (twitchStream.func_152912_E() != null) {
            final ChatComponentTranslation[] array5 = new ChatComponentTranslation[" ".length()];
            final int length = "".length();
            final String s5 = GuiStreamUnavailable.I[0xA0 ^ 0xB2];
            final Object[] array6 = new Object[" ".length()];
            array6["".length()] = ErrorCode.getString(twitchStream.func_152912_E());
            array5[length] = new ChatComponentTranslation(s5, array6);
            minecraft.displayGuiScreen(new GuiStreamUnavailable(guiScreen, Reason.INITIALIZATION_FAILURE, Arrays.asList(array5)));
            "".length();
            if (4 <= 3) {
                throw null;
            }
        }
        else {
            minecraft.displayGuiScreen(new GuiStreamUnavailable(guiScreen, Reason.UNKNOWN));
        }
    }
    
    public enum Reason
    {
        private final IChatComponent field_152575_n;
        
        FAILED_TWITCH_AUTH_ERROR(Reason.I[0x1C ^ 0xB], 0xA8 ^ 0xA1, (IChatComponent)new ChatComponentTranslation(Reason.I[0x1E ^ 0x6], new Object["".length()])), 
        ACCOUNT_NOT_BOUND(Reason.I[0x97 ^ 0x86], 0x12 ^ 0x15, (IChatComponent)new ChatComponentTranslation(Reason.I[0x5C ^ 0x4E], new Object["".length()]), (IChatComponent)new ChatComponentTranslation(Reason.I[0x2B ^ 0x38], new Object["".length()])), 
        INITIALIZATION_FAILURE(Reason.I[0x38 ^ 0x21], 0xA8 ^ 0xA2, (IChatComponent)new ChatComponentTranslation(Reason.I[0x54 ^ 0x4E], new Object["".length()]), (IChatComponent)new ChatComponentTranslation(Reason.I[0xB8 ^ 0xA3], new Object["".length()])), 
        UNSUPPORTED_OS_OTHER(Reason.I[0x44 ^ 0x48], 0xAA ^ 0xAF, (IChatComponent)new ChatComponentTranslation(Reason.I[0x7A ^ 0x77], new Object["".length()])), 
        UNSUPPORTED_OS_MAC(Reason.I[0x83 ^ 0x8A], 0x46 ^ 0x42, (IChatComponent)new ChatComponentTranslation(Reason.I[0x9 ^ 0x3], new Object["".length()]), (IChatComponent)new ChatComponentTranslation(Reason.I[0x79 ^ 0x72], new Object["".length()])), 
        UNSUPPORTED_OS_WINDOWS(Reason.I[0xB2 ^ 0xB5], "   ".length(), (IChatComponent)new ChatComponentTranslation(Reason.I[0x1F ^ 0x17], new Object["".length()]));
        
        private static final String[] I;
        
        LIBRARY_ARCH_MISMATCH(Reason.I["  ".length()], " ".length(), (IChatComponent)new ChatComponentTranslation(Reason.I["   ".length()], new Object["".length()])), 
        NO_FBO(Reason.I["".length()], "".length(), (IChatComponent)new ChatComponentTranslation(Reason.I[" ".length()], new Object["".length()])), 
        LIBRARY_FAILURE(Reason.I[0xB0 ^ 0xB4], "  ".length(), (IChatComponent)new ChatComponentTranslation(Reason.I[0x1F ^ 0x1A], new Object["".length()]), (IChatComponent)new ChatComponentTranslation(Reason.I[0x1F ^ 0x19], new Object["".length()]));
        
        private final IChatComponent field_152574_m;
        private static final Reason[] ENUM$VALUES;
        
        FAILED_TWITCH_AUTH(Reason.I[0xAC ^ 0xB8], 0x1A ^ 0x12, (IChatComponent)new ChatComponentTranslation(Reason.I[0x18 ^ 0xD], new Object["".length()]), (IChatComponent)new ChatComponentTranslation(Reason.I[0x78 ^ 0x6E], new Object["".length()])), 
        UNKNOWN(Reason.I[0x91 ^ 0x8D], 0xB ^ 0x0, (IChatComponent)new ChatComponentTranslation(Reason.I[0x13 ^ 0xE], new Object["".length()]), (IChatComponent)new ChatComponentTranslation(Reason.I[0x15 ^ 0xB], new Object["".length()])), 
        ACCOUNT_NOT_MIGRATED(Reason.I[0x4C ^ 0x42], 0xBF ^ 0xB9, (IChatComponent)new ChatComponentTranslation(Reason.I[0xA9 ^ 0xA6], new Object["".length()]), (IChatComponent)new ChatComponentTranslation(Reason.I[0xB4 ^ 0xA4], new Object["".length()]));
        
        private Reason(final String s, final int n, final IChatComponent field_152574_m, final IChatComponent field_152575_n) {
            this.field_152574_m = field_152574_m;
            this.field_152575_n = field_152575_n;
        }
        
        public IChatComponent func_152561_a() {
            return this.field_152574_m;
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
                if (4 <= 3) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        private Reason(final String s, final int n, final IChatComponent chatComponent) {
            this(s, n, chatComponent, null);
        }
        
        public IChatComponent func_152559_b() {
            return this.field_152575_n;
        }
        
        private static void I() {
            (I = new String[0x6A ^ 0x75])["".length()] = I("\u0004*\b\u000b(\u0005", "JeWMj");
            Reason.I[" ".length()] = I("\t\u0019+77\u0017C,<7\f\f0>7\u0018\u0001<|8\u00152?09", "zmYRV");
            Reason.I["  ".length()] = I("\u001a\u0019\u001b&\u0005\u0004\t\u00065\u0016\u0015\u0018\u00069\r\u0005\u001d\u0018 \u0007\u001e", "VPYtD");
            Reason.I["   ".length()] = I("61\u00180\u001b(k\u001f;\u001b3$\u00039\u001b')\u000f{\u0016,'\u00184\b<\u001a\u000b'\u0019-\u001a\u0007<\t($\u001e6\u0012", "EEjUz");
            Reason.I[0x92 ^ 0x96] = I("\"=\u0010\u0007*<-\r\u0013*'8\u0007\u0007.", "ntRUk");
            Reason.I[0x53 ^ 0x56] = I("\u0003!? \u000f\u001d{8+\u000f\u00064$)\u000f\u00129(k\u0002\u00197?$\u001c\t\n+$\u0007\u001c ? ", "pUMEn");
            Reason.I[0xA ^ 0xC] = I("=5\u0016\f9#o\u0011\u000798 \r\u00059,-\u0001G*+1\u000b\u001b,\u00115\u000b65!+\u0005\u0007?", "NAdiX");
            Reason.I[0xB4 ^ 0xB3] = I("\u001b\"8:\u0000\u001e#9;\u0015\n3$<\u000f\u0019%%+\u001f\u0019?", "NlkoP");
            Reason.I[0xC8 ^ 0xC0] = I("\u0012%\u0011\"8\f\u007f\u0016)8\u00170\n+8\u0003=\u0006i7\u000e%<4,\u0011!\f5-\u00045M00\u000f5\f0*", "aQcGY");
            Reason.I[0xCC ^ 0xC5] = I(";\b\u0010\u001d\u0018>\t\u0011\u001c\r*\u0019\f\u001b\u0017#\u0007\u0000", "nFCHH");
            Reason.I[0x10 ^ 0x1A] = I("28*\u000e$,b-\u0005$7-1\u0007$# =E+.8\u0007\u001801<7\u00191$(v\u0006$\"", "ALXkE");
            Reason.I[0x29 ^ 0x22] = I("\u0003\u0001\u001e?\u001b\u001d[\u00194\u001b\u0006\u0014\u00056\u001b\u0012\u0019\tt\u0014\u001f\u00013)\u000f\u0000\u0005\u0003(\u000e\u0015\u0011B7\u001b\u0013[\u00031\u001b\t", "pulZz");
            Reason.I[0x80 ^ 0x8C] = I("\u0013%\u0014\r\u0000\u0016$\u0015\f\u0015\u00024\b\u000b\u000f\t?\u000f\u001d\u0002", "FkGXP");
            Reason.I[0x95 ^ 0x98] = I("$=9\u000b\u0000:g>\u0000\u0000!(\"\u0002\u00005%.@\u000f8=\u0014\u001d\u0014'9$\u001c\u00152-e\u0001\u0015?,9", "WIKna");
            Reason.I[0x48 ^ 0x46] = I("\u0004,\u0005\"\u0014\u000b;\u0019#\u000e\u00110\u000b$\u0006\u0017.\u0012(\u0005", "EoFmA");
            Reason.I[0x39 ^ 0x36] = I("\u0002#*)\r\u001cy-\"\r\u000761 \r\u0013;=b\r\u0012479\u0002\u0005\b6#\u0018.:1+\u001e\u0010#=(", "qWXLl");
            Reason.I[0x33 ^ 0x23] = I(")\"=,\u00117x:'\u0011,7&%\u00118:*g\u001195 <\u001e.\t!&\u0004\u0005;&.\u0002;\"*-^5=.0", "ZVOIp");
            Reason.I[0x9 ^ 0x18] = I("\u00026\u0016\u00068\r!\n\u0007\"\u0017*\u0017\u00068\r1", "CuUIm");
            Reason.I[0x28 ^ 0x3A] = I("1\u0007\u0011#//]\u0016(/4\u0012\n*/ \u001f\u0006h/!\u0010\f3 6,\r):\u001d\u0011\f3 &", "BscFN");
            Reason.I[0x62 ^ 0x71] = I("'\u0015'0\u00029O ;\u0002\"\u0000<9\u00026\r0{\u00027\u0002: \r >;:\u0017\u000b\u0003: \r0O:>\u0002-", "TaUUc");
            Reason.I[0x87 ^ 0x93] = I("\r0\n8#\u000f.\u0017#/\u001f2\u000b+'\u001e%\u000b", "KqCtf");
            Reason.I[0xAF ^ 0xBA] = I("\u001e2\u001e4\f\u0000h\u0019?\f\u001b'\u0005=\f\u000f*\t\u007f\u000b\f/\u00004\t2'\u0019%\u0005", "mFlQm");
            Reason.I[0x52 ^ 0x44] = I("&\u00079$*8]>/*#\u0012\"-*7\u001f.o-4\u001a'$/\n\u0012>5#{\u001c  2", "UsKAK");
            Reason.I[0x8C ^ 0x9B] = I("3\u0007%\u001a\u00111\u00198\u0001\u001d!\u0005$\t\u0015 \u0012$\t\u0011'\u0014#\u0004", "uFlVT");
            Reason.I[0xA9 ^ 0xB1] = I("\u00163>\"\u000b\bi9)\u000b\u0013&%+\u000b\u0007+)i\f\u0004. \"\u000e:&93\u0002:\">5\u0005\u0017", "eGLGj");
            Reason.I[0x3D ^ 0x24] = I("\u0011\u0018-\u0018\u001c\u0019\u001a-\u0016\u0014\f\u001f+\u0002\n\u001e\u0017-\u0000\u0000\n\u0013", "XVdLU");
            Reason.I[0x8 ^ 0x12] = I("\u00142\u0005\u000b\f\nh\u0002\u0000\f\u0011'\u001e\u0002\f\u0005*\u0012@\u0004\t/\u0003\u0007\f\u000b/\r\u000f\u0019\u000e)\u00191\u000b\u0006/\u001b\u001b\u001f\u0002", "gFwnm");
            Reason.I[0x75 ^ 0x6E] = I("> #\u0010\u000b z$\u001b\u000b;58\u0019\u000b/84[\u0018($>\u0007\u001e\u0012 >*\u0007\">0\u001b\r", "MTQuj");
            Reason.I[0xA3 ^ 0xBF] = I("7\u0007%\u000695\u0007", "bInHv");
            Reason.I[0x0 ^ 0x1D] = I("90\u0019\u0000\u001b'j\u001e\u000b\u001b<%\u0002\t\u001b((\u000eK\u000f$/\u0005\n\r$", "JDkez");
            Reason.I[0x6A ^ 0x74] = I("\u0019\u00038\u001f8\u0007Y?\u00148\u001c\u0016#\u00168\b\u001b/T+\u000f\u0007%\b-5\u0003%%4\u0005\u001d+\u0014>", "jwJzY");
        }
        
        static {
            I();
            final Reason[] enum$VALUES = new Reason[0x5A ^ 0x56];
            enum$VALUES["".length()] = Reason.NO_FBO;
            enum$VALUES[" ".length()] = Reason.LIBRARY_ARCH_MISMATCH;
            enum$VALUES["  ".length()] = Reason.LIBRARY_FAILURE;
            enum$VALUES["   ".length()] = Reason.UNSUPPORTED_OS_WINDOWS;
            enum$VALUES[0x86 ^ 0x82] = Reason.UNSUPPORTED_OS_MAC;
            enum$VALUES[0x9C ^ 0x99] = Reason.UNSUPPORTED_OS_OTHER;
            enum$VALUES[0xA7 ^ 0xA1] = Reason.ACCOUNT_NOT_MIGRATED;
            enum$VALUES[0x2B ^ 0x2C] = Reason.ACCOUNT_NOT_BOUND;
            enum$VALUES[0xD ^ 0x5] = Reason.FAILED_TWITCH_AUTH;
            enum$VALUES[0x7F ^ 0x76] = Reason.FAILED_TWITCH_AUTH_ERROR;
            enum$VALUES[0x5B ^ 0x51] = Reason.INITIALIZATION_FAILURE;
            enum$VALUES[0x94 ^ 0x9F] = Reason.UNKNOWN;
            ENUM$VALUES = enum$VALUES;
        }
    }
}
