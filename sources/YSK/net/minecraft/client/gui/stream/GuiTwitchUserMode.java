package net.minecraft.client.gui.stream;

import net.minecraft.client.stream.*;
import net.minecraft.client.resources.*;
import net.minecraft.client.gui.*;
import tv.twitch.chat.*;
import com.google.common.collect.*;
import net.minecraft.util.*;
import java.util.*;
import java.io.*;

public class GuiTwitchUserMode extends GuiScreen
{
    private static final EnumChatFormatting field_152336_g;
    private int field_152334_t;
    private final List<IChatComponent> field_152332_r;
    private final IChatComponent field_152338_i;
    private static final EnumChatFormatting field_152335_f;
    private static final String[] I;
    private static final EnumChatFormatting field_152331_a;
    private final IStream stream;
    private final ChatUserInfo field_152337_h;
    
    @Override
    public void initGui() {
        final int n = this.width / "   ".length();
        final int n2 = n - (65 + 127 - 135 + 73);
        this.buttonList.add(new GuiButton(" ".length(), n * "".length() + n2 / "  ".length(), this.height - (0x19 ^ 0x5F), 56 + 31 + 39 + 4, 0x72 ^ 0x66, I18n.format(GuiTwitchUserMode.I[0x7E ^ 0x6F], new Object["".length()])));
        this.buttonList.add(new GuiButton("".length(), n * " ".length() + n2 / "  ".length(), this.height - (0xD1 ^ 0x97), 129 + 73 - 107 + 35, 0x62 ^ 0x76, I18n.format(GuiTwitchUserMode.I[0xD6 ^ 0xC4], new Object["".length()])));
        this.buttonList.add(new GuiButton("  ".length(), n * "  ".length() + n2 / "  ".length(), this.height - (0xF5 ^ 0xB3), 48 + 0 - 16 + 98, 0x75 ^ 0x61, I18n.format(GuiTwitchUserMode.I[0x6A ^ 0x79], new Object["".length()])));
        this.buttonList.add(new GuiButton(0x92 ^ 0x97, n * "".length() + n2 / "  ".length(), this.height - (0x87 ^ 0xAA), 24 + 64 - 31 + 73, 0xB5 ^ 0xA1, I18n.format(GuiTwitchUserMode.I[0x5B ^ 0x4F], new Object["".length()])));
        this.buttonList.add(new GuiButton("   ".length(), n * " ".length() + n2 / "  ".length(), this.height - (0xAC ^ 0x81), 45 + 33 - 59 + 111, 0x21 ^ 0x35, I18n.format(GuiTwitchUserMode.I[0xBA ^ 0xAF], new Object["".length()])));
        this.buttonList.add(new GuiButton(0x7D ^ 0x79, n * "  ".length() + n2 / "  ".length(), this.height - (0x8E ^ 0xA3), 60 + 49 - 35 + 56, 0xBE ^ 0xAA, I18n.format(GuiTwitchUserMode.I[0x69 ^ 0x7F], new Object["".length()])));
        int n3 = "".length();
        final Iterator<IChatComponent> iterator = this.field_152332_r.iterator();
        "".length();
        if (2 == 4) {
            throw null;
        }
        while (iterator.hasNext()) {
            n3 = Math.max(n3, this.fontRendererObj.getStringWidth(iterator.next().getFormattedText()));
        }
        this.field_152334_t = this.width / "  ".length() - n3 / "  ".length();
    }
    
    public static IChatComponent func_152330_a(final ChatUserSubscription chatUserSubscription, final String s, final boolean b) {
        IChatComponent chatComponent = null;
        if (chatUserSubscription == ChatUserSubscription.TTV_CHAT_USERSUB_SUBSCRIBER) {
            if (s == null) {
                chatComponent = new ChatComponentTranslation(GuiTwitchUserMode.I["  ".length()], new Object["".length()]);
                "".length();
                if (-1 >= 2) {
                    throw null;
                }
            }
            else if (b) {
                chatComponent = new ChatComponentTranslation(GuiTwitchUserMode.I["   ".length()], new Object["".length()]);
                "".length();
                if (2 == 0) {
                    throw null;
                }
            }
            else {
                final String s2 = GuiTwitchUserMode.I[0x9B ^ 0x9F];
                final Object[] array = new Object[" ".length()];
                array["".length()] = s;
                chatComponent = new ChatComponentTranslation(s2, array);
            }
            chatComponent.getChatStyle().setColor(GuiTwitchUserMode.field_152331_a);
            "".length();
            if (false == true) {
                throw null;
            }
        }
        else if (chatUserSubscription == ChatUserSubscription.TTV_CHAT_USERSUB_TURBO) {
            chatComponent = new ChatComponentTranslation(GuiTwitchUserMode.I[0x7E ^ 0x7B], new Object["".length()]);
            chatComponent.getChatStyle().setColor(GuiTwitchUserMode.field_152336_g);
        }
        return chatComponent;
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
            if (4 == 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public static List<IChatComponent> func_152328_a(final Set<ChatUserMode> set, final Set<ChatUserSubscription> set2, final IStream stream) {
        String func_152921_C;
        if (stream == null) {
            func_152921_C = null;
            "".length();
            if (2 == 4) {
                throw null;
            }
        }
        else {
            func_152921_C = stream.func_152921_C();
        }
        final String s = func_152921_C;
        int n;
        if (stream != null && stream.func_152927_B()) {
            n = " ".length();
            "".length();
            if (2 <= 0) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        final int n2 = n;
        final ArrayList arrayList = Lists.newArrayList();
        final Iterator<ChatUserMode> iterator = set.iterator();
        "".length();
        if (3 <= 0) {
            throw null;
        }
        while (iterator.hasNext()) {
            final IChatComponent func_152329_a = func_152329_a(iterator.next(), s, n2 != 0);
            if (func_152329_a != null) {
                final ChatComponentText chatComponentText = new ChatComponentText(GuiTwitchUserMode.I["".length()]);
                chatComponentText.appendSibling(func_152329_a);
                arrayList.add(chatComponentText);
            }
        }
        final Iterator<ChatUserSubscription> iterator2 = set2.iterator();
        "".length();
        if (4 < 4) {
            throw null;
        }
        while (iterator2.hasNext()) {
            final IChatComponent func_152330_a = func_152330_a(iterator2.next(), s, n2 != 0);
            if (func_152330_a != null) {
                final ChatComponentText chatComponentText2 = new ChatComponentText(GuiTwitchUserMode.I[" ".length()]);
                chatComponentText2.appendSibling(func_152330_a);
                arrayList.add(chatComponentText2);
            }
        }
        return (List<IChatComponent>)arrayList;
    }
    
    public GuiTwitchUserMode(final IStream stream, final ChatUserInfo field_152337_h) {
        this.field_152332_r = (List<IChatComponent>)Lists.newArrayList();
        this.stream = stream;
        this.field_152337_h = field_152337_h;
        this.field_152338_i = new ChatComponentText(field_152337_h.displayName);
        this.field_152332_r.addAll(func_152328_a(field_152337_h.modes, field_152337_h.subscriptions, stream));
    }
    
    @Override
    protected void actionPerformed(final GuiButton guiButton) throws IOException {
        if (guiButton.enabled) {
            if (guiButton.id == 0) {
                this.stream.func_152917_b(GuiTwitchUserMode.I[0x47 ^ 0x50] + this.field_152337_h.displayName);
                "".length();
                if (2 < 0) {
                    throw null;
                }
            }
            else if (guiButton.id == "   ".length()) {
                this.stream.func_152917_b(GuiTwitchUserMode.I[0xD ^ 0x15] + this.field_152337_h.displayName);
                "".length();
                if (false) {
                    throw null;
                }
            }
            else if (guiButton.id == "  ".length()) {
                this.stream.func_152917_b(GuiTwitchUserMode.I[0x8 ^ 0x11] + this.field_152337_h.displayName);
                "".length();
                if (1 == -1) {
                    throw null;
                }
            }
            else if (guiButton.id == (0xA7 ^ 0xA3)) {
                this.stream.func_152917_b(GuiTwitchUserMode.I[0x38 ^ 0x22] + this.field_152337_h.displayName);
                "".length();
                if (false) {
                    throw null;
                }
            }
            else if (guiButton.id == " ".length()) {
                this.stream.func_152917_b(GuiTwitchUserMode.I[0x29 ^ 0x32] + this.field_152337_h.displayName);
            }
            this.mc.displayGuiScreen(null);
        }
    }
    
    static {
        I();
        field_152331_a = EnumChatFormatting.DARK_GREEN;
        field_152335_f = EnumChatFormatting.RED;
        field_152336_g = EnumChatFormatting.DARK_PURPLE;
    }
    
    private static void I() {
        (I = new String[0xBA ^ 0xA6])["".length()] = I("uL", "XlRmb");
        GuiTwitchUserMode.I[" ".length()] = I("iD", "DdfmK");
        GuiTwitchUserMode.I["  ".length()] = I(">\u0010\u001d'. J\u001a1*?J\u001c7->\u0007\u001d+?9\r\u0000,a>\u0011\r1,?\r\r'=", "MdoBO");
        GuiTwitchUserMode.I["   ".length()] = I("\u0015<4\u000e\u0002\u000bf3\u0018\u0006\u0014f5\u001e\u0001\u0015+4\u0002\u0013\u0012!)\u0005M\u0015=$\u0018\u0000\u0014!$\u000e\u0011H;#\u0007\u0005", "fHFkc");
        GuiTwitchUserMode.I[0xB6 ^ 0xB2] = I("28%*;,b\"<?3b$:82/%&*5%8!t295<93%5*(o##'?3", "ALWOZ");
        GuiTwitchUserMode.I[0x96 ^ 0x93] = I("\u001b\u0019;\u0006.\u0005C<\u0010*\u001aC:\u0016-\u001b\u000e;\n?\u001c\u0004&\ra\u001c\u0018;\u0001 ", "hmIcO");
        GuiTwitchUserMode.I[0x1C ^ 0x1A] = I("7\u0016\u0018\u001f\u000e)L\u001f\t\n6L\u0007\u0015\u000b!L\u000b\u001e\u0002-\f\u0003\t\u001b6\u0003\u001e\u0015\u001d", "Dbjzo");
        GuiTwitchUserMode.I[0x3A ^ 0x3D] = I("\u001a!?\u0017\u0006\u0004{8\u0001\u0002\u001b{ \u001d\u0003\f{/\u0013\t\u00070)", "iUMrg");
        GuiTwitchUserMode.I[0x25 ^ 0x2D] = I("#:\u001c\u0003\u0010=`\u001b\u0015\u0014\"`\u0003\t\u00155`\f\u0007\u001f>+\nH\u00025\"\b", "PNnfq");
        GuiTwitchUserMode.I[0xA4 ^ 0xAD] = I("5\u0005\u0017+\t+_\u0010=\r4_\b!\f#_\u0007/\u0006(\u0014\u0001`\u00072\u0019\u0000<", "FqeNh");
        GuiTwitchUserMode.I[0x8E ^ 0x84] = I("2\u00177\u0014\r,M0\u0002\t3M(\u001e\b$M'\u0003\u0003 \u0007&\u0010\u001f5\u00067", "AcEql");
        GuiTwitchUserMode.I[0x8 ^ 0x3] = I("2\r8\r\f,W?\u001b\b3W'\u0007\t$W(\u001a\u0002 \u001d)\t\u001e5\u001c8F\u001e$\u0015,", "AyJhm");
        GuiTwitchUserMode.I[0x23 ^ 0x2F] = I("\u0011=\u001b<)\u000fg\u001c*-\u0010g\u00046,\u0007g\u000b+'\u0003-\n8;\u0016,\u001bw'\u0016!\f+", "bIiYH");
        GuiTwitchUserMode.I[0x16 ^ 0x1B] = I("03\u0003\b3.i\u0004\u001e71i\u001c\u00026&i\u001c\u00026&5\u0010\u0019=1", "CGqmR");
        GuiTwitchUserMode.I[0x63 ^ 0x6D] = I("\u0010%3,2\u000e\u007f4:6\u0011\u007f,&7\u0006\u007f,&7\u0006# =<\u0011\u007f2,?\u0005", "cQAIS");
        GuiTwitchUserMode.I[0x53 ^ 0x5C] = I("\u000b\u000e&\u0001\u0005\u0015T!\u0017\u0001\nT9\u000b\u0000\u001dT9\u000b\u0000\u001d\b5\u0010\u000b\nT;\u0010\f\u001d\b", "xzTdd");
        GuiTwitchUserMode.I[0x7F ^ 0x6F] = I("#\u000e$/.=T#9*\"T;%+5T%>.6\u001c", "PzVJO");
        GuiTwitchUserMode.I[0x2F ^ 0x3E] = I("\u0011=\u00113#\u000fg\u0016%'\u0010 \r0-L=\n;'\r<\u0017", "bIcVB");
        GuiTwitchUserMode.I[0xD3 ^ 0xC1] = I("$;6\u0013\u0018:a1\u0005\u001c%&*\u0010\u0016y-%\u0018", "WODvy");
        GuiTwitchUserMode.I[0x86 ^ 0x95] = I("#6 \u000e\u0000=l'\u0018\u0004\"+<\r\u000e~/=\u000f", "PBRka");
        GuiTwitchUserMode.I[0x82 ^ 0x96] = I("\u00129.I0\u0014\"$\u0002?", "uLGgS");
        GuiTwitchUserMode.I[0x92 ^ 0x87] = I("\u0014\u0019;\u000f%\nC<\u0019!\u0015\u0004'\f+I\u0018'\b%\t", "gmIjD");
        GuiTwitchUserMode.I[0x6E ^ 0x78] = I("\u0001\u0001\u001450\u001f[\u0013#4\u0000\u001c\b6>\\\u0000\b=>\u0016", "rufPQ");
        GuiTwitchUserMode.I[0x8D ^ 0x9A] = I("j82>T", "EZSPt");
        GuiTwitchUserMode.I[0x9B ^ 0x83] = I("h\u0003$\u00118)V", "GvJsY");
        GuiTwitchUserMode.I[0x43 ^ 0x5A] = I("n\u0003 \nR", "AnOnr");
        GuiTwitchUserMode.I[0x6C ^ 0x76] = I("_\u0012/7\u001d\u0014G", "pgAZr");
        GuiTwitchUserMode.I[0x3F ^ 0x24] = I("Y\u000e*>=\u0019\u000f7s", "vzCSX");
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, this.field_152338_i.getUnformattedText(), this.width / "  ".length(), 0x54 ^ 0x12, 9649910 + 9170390 - 9005214 + 6962129);
        int n4 = 0xED ^ 0xBD;
        final Iterator<IChatComponent> iterator = this.field_152332_r.iterator();
        "".length();
        if (2 == 3) {
            throw null;
        }
        while (iterator.hasNext()) {
            this.drawString(this.fontRendererObj, iterator.next().getFormattedText(), this.field_152334_t, n4, 3955714 + 2104722 + 9246778 + 1470001);
            n4 += this.fontRendererObj.FONT_HEIGHT;
        }
        super.drawScreen(n, n2, n3);
    }
    
    public static IChatComponent func_152329_a(final ChatUserMode chatUserMode, final String s, final boolean b) {
        IChatComponent chatComponent = null;
        if (chatUserMode == ChatUserMode.TTV_CHAT_USERMODE_ADMINSTRATOR) {
            chatComponent = new ChatComponentTranslation(GuiTwitchUserMode.I[0x2 ^ 0x4], new Object["".length()]);
            chatComponent.getChatStyle().setColor(GuiTwitchUserMode.field_152336_g);
            "".length();
            if (1 < -1) {
                throw null;
            }
        }
        else if (chatUserMode == ChatUserMode.TTV_CHAT_USERMODE_BANNED) {
            if (s == null) {
                chatComponent = new ChatComponentTranslation(GuiTwitchUserMode.I[0x32 ^ 0x35], new Object["".length()]);
                "".length();
                if (3 <= 0) {
                    throw null;
                }
            }
            else if (b) {
                chatComponent = new ChatComponentTranslation(GuiTwitchUserMode.I[0x61 ^ 0x69], new Object["".length()]);
                "".length();
                if (3 < -1) {
                    throw null;
                }
            }
            else {
                final String s2 = GuiTwitchUserMode.I[0x61 ^ 0x68];
                final Object[] array = new Object[" ".length()];
                array["".length()] = s;
                chatComponent = new ChatComponentTranslation(s2, array);
            }
            chatComponent.getChatStyle().setColor(GuiTwitchUserMode.field_152335_f);
            "".length();
            if (2 >= 4) {
                throw null;
            }
        }
        else if (chatUserMode == ChatUserMode.TTV_CHAT_USERMODE_BROADCASTER) {
            if (s == null) {
                chatComponent = new ChatComponentTranslation(GuiTwitchUserMode.I[0xBE ^ 0xB4], new Object["".length()]);
                "".length();
                if (-1 == 0) {
                    throw null;
                }
            }
            else if (b) {
                chatComponent = new ChatComponentTranslation(GuiTwitchUserMode.I[0x33 ^ 0x38], new Object["".length()]);
                "".length();
                if (1 <= 0) {
                    throw null;
                }
            }
            else {
                chatComponent = new ChatComponentTranslation(GuiTwitchUserMode.I[0x4B ^ 0x47], new Object["".length()]);
            }
            chatComponent.getChatStyle().setColor(GuiTwitchUserMode.field_152331_a);
            "".length();
            if (3 <= 0) {
                throw null;
            }
        }
        else if (chatUserMode == ChatUserMode.TTV_CHAT_USERMODE_MODERATOR) {
            if (s == null) {
                chatComponent = new ChatComponentTranslation(GuiTwitchUserMode.I[0x36 ^ 0x3B], new Object["".length()]);
                "".length();
                if (false) {
                    throw null;
                }
            }
            else if (b) {
                chatComponent = new ChatComponentTranslation(GuiTwitchUserMode.I[0x7F ^ 0x71], new Object["".length()]);
                "".length();
                if (-1 == 4) {
                    throw null;
                }
            }
            else {
                final String s3 = GuiTwitchUserMode.I[0x3F ^ 0x30];
                final Object[] array2 = new Object[" ".length()];
                array2["".length()] = s;
                chatComponent = new ChatComponentTranslation(s3, array2);
            }
            chatComponent.getChatStyle().setColor(GuiTwitchUserMode.field_152331_a);
            "".length();
            if (0 == -1) {
                throw null;
            }
        }
        else if (chatUserMode == ChatUserMode.TTV_CHAT_USERMODE_STAFF) {
            chatComponent = new ChatComponentTranslation(GuiTwitchUserMode.I[0x38 ^ 0x28], new Object["".length()]);
            chatComponent.getChatStyle().setColor(GuiTwitchUserMode.field_152336_g);
        }
        return chatComponent;
    }
}
