package net.minecraft.client.gui;

import net.minecraft.client.multiplayer.*;
import net.minecraft.client.*;
import com.google.common.base.*;
import io.netty.handler.codec.base64.*;
import java.io.*;
import org.apache.commons.lang3.*;
import net.minecraft.client.renderer.texture.*;
import io.netty.buffer.*;
import java.awt.image.*;
import org.apache.logging.log4j.*;
import com.google.common.util.concurrent.*;
import java.util.concurrent.*;
import net.minecraft.util.*;
import java.net.*;
import net.minecraft.client.renderer.*;
import java.util.*;

public class ServerListEntryNormal implements GuiListExtended.IGuiListEntry
{
    private static final ThreadPoolExecutor field_148302_b;
    private DynamicTexture field_148305_h;
    private long field_148298_f;
    private String field_148299_g;
    private static final ResourceLocation UNKNOWN_SERVER;
    private final ServerData field_148301_e;
    private final GuiMultiplayer field_148303_c;
    private static final ResourceLocation SERVER_SELECTION_BUTTONS;
    private static final Logger logger;
    private static final String[] I;
    private final Minecraft mc;
    private final ResourceLocation field_148306_i;
    
    @Override
    public boolean mousePressed(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        if (n5 <= (0xB1 ^ 0x91)) {
            if (n5 < (0x9E ^ 0xBE) && n5 > (0x2A ^ 0x3A) && this.func_178013_b()) {
                this.field_148303_c.selectServer(n);
                this.field_148303_c.connectToSelected();
                return " ".length() != 0;
            }
            if (n5 < (0x3C ^ 0x2C) && n6 < (0xA5 ^ 0xB5) && this.field_148303_c.func_175392_a(this, n)) {
                this.field_148303_c.func_175391_a(this, n, GuiScreen.isShiftKeyDown());
                return " ".length() != 0;
            }
            if (n5 < (0x7F ^ 0x6F) && n6 > (0xF ^ 0x1F) && this.field_148303_c.func_175394_b(this, n)) {
                this.field_148303_c.func_175393_b(this, n, GuiScreen.isShiftKeyDown());
                return " ".length() != 0;
            }
        }
        this.field_148303_c.selectServer(n);
        if (Minecraft.getSystemTime() - this.field_148298_f < 250L) {
            this.field_148303_c.connectToSelected();
        }
        this.field_148298_f = Minecraft.getSystemTime();
        return "".length() != 0;
    }
    
    private void prepareServerIcon() {
        if (this.field_148301_e.getBase64EncodedIconData() == null) {
            this.mc.getTextureManager().deleteTexture(this.field_148306_i);
            this.field_148305_h = null;
            "".length();
            if (-1 >= 1) {
                throw null;
            }
        }
        else {
            final ByteBuf copiedBuffer = Unpooled.copiedBuffer((CharSequence)this.field_148301_e.getBase64EncodedIconData(), Charsets.UTF_8);
            final ByteBuf decode = Base64.decode(copiedBuffer);
            BufferedImage bufferedImage = null;
            Label_0332: {
                try {
                    bufferedImage = TextureUtil.readBufferedImage((InputStream)new ByteBufInputStream(decode));
                    int n;
                    if (bufferedImage.getWidth() == (0xC9 ^ 0x89)) {
                        n = " ".length();
                        "".length();
                        if (3 == 2) {
                            throw null;
                        }
                    }
                    else {
                        n = "".length();
                    }
                    Validate.validState((boolean)(n != 0), ServerListEntryNormal.I[0x45 ^ 0x49], new Object["".length()]);
                    int n2;
                    if (bufferedImage.getHeight() == (0x46 ^ 0x6)) {
                        n2 = " ".length();
                        "".length();
                        if (1 >= 4) {
                            throw null;
                        }
                    }
                    else {
                        n2 = "".length();
                    }
                    Validate.validState((boolean)(n2 != 0), ServerListEntryNormal.I[0x9D ^ 0x90], new Object["".length()]);
                    copiedBuffer.release();
                    decode.release();
                    "".length();
                    if (3 <= -1) {
                        throw null;
                    }
                    break Label_0332;
                }
                catch (Throwable t) {
                    ServerListEntryNormal.logger.error(ServerListEntryNormal.I[0x7 ^ 0x9] + this.field_148301_e.serverName + ServerListEntryNormal.I[0x2E ^ 0x21] + this.field_148301_e.serverIP + ServerListEntryNormal.I[0x31 ^ 0x21], t);
                    this.field_148301_e.setBase64EncodedIconData(null);
                    copiedBuffer.release();
                    decode.release();
                    "".length();
                    if (1 >= 2) {
                        throw null;
                    }
                }
                finally {
                    copiedBuffer.release();
                    decode.release();
                }
                return;
            }
            if (this.field_148305_h == null) {
                this.field_148305_h = new DynamicTexture(bufferedImage.getWidth(), bufferedImage.getHeight());
                this.mc.getTextureManager().loadTexture(this.field_148306_i, this.field_148305_h);
            }
            bufferedImage.getRGB("".length(), "".length(), bufferedImage.getWidth(), bufferedImage.getHeight(), this.field_148305_h.getTextureData(), "".length(), bufferedImage.getWidth());
            this.field_148305_h.updateDynamicTexture();
        }
    }
    
    static GuiMultiplayer access$0(final ServerListEntryNormal serverListEntryNormal) {
        return serverListEntryNormal.field_148303_c;
    }
    
    @Override
    public void setSelected(final int n, final int n2, final int n3) {
    }
    
    protected ServerListEntryNormal(final GuiMultiplayer field_148303_c, final ServerData field_148301_e) {
        this.field_148303_c = field_148303_c;
        this.field_148301_e = field_148301_e;
        this.mc = Minecraft.getMinecraft();
        this.field_148306_i = new ResourceLocation(ServerListEntryNormal.I["   ".length()] + field_148301_e.serverIP + ServerListEntryNormal.I[0xB ^ 0xF]);
        this.field_148305_h = (DynamicTexture)this.mc.getTextureManager().getTexture(this.field_148306_i);
    }
    
    static ServerData access$1(final ServerListEntryNormal serverListEntryNormal) {
        return serverListEntryNormal.field_148301_e;
    }
    
    @Override
    public void mouseReleased(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
    }
    
    public ServerData getServerData() {
        return this.field_148301_e;
    }
    
    private boolean func_178013_b() {
        return " ".length() != 0;
    }
    
    static {
        I();
        logger = LogManager.getLogger();
        field_148302_b = new ScheduledThreadPoolExecutor(0x1B ^ 0x1E, new ThreadFactoryBuilder().setNameFormat(ServerListEntryNormal.I["".length()]).setDaemon((boolean)(" ".length() != 0)).build());
        UNKNOWN_SERVER = new ResourceLocation(ServerListEntryNormal.I[" ".length()]);
        SERVER_SELECTION_BUTTONS = new ResourceLocation(ServerListEntryNormal.I["  ".length()]);
    }
    
    private static void I() {
        (I = new String[0x49 ^ 0x58])["".length()] = I("\u0003\u0016\u000612\"S$.97\u0016\u0006gtu\u0017", "PstGW");
        ServerListEntryNormal.I[" ".length()] = I("\u001a\u000b:3\u0004\u001c\u000b1h\u001c\u0007\u001d!h\u0004\u0000\u0005,(\u0006\u000011\"\u0003\u0018\u000b0i\u0001\u0000\t", "nnBGq");
        ServerListEntryNormal.I["  ".length()] = I("7\u0016\u0019<41\u0016\u0012g&6\u001aN;$1\u0005\u0004:\u001e0\u0016\r-\"7\u001a\u000e&o3\u001d\u0006", "CsaHA");
        ServerListEntryNormal.I["   ".length()] = I("\u001b\n3&5\u001a\u001cn", "hoAPP");
        ServerListEntryNormal.I[0x37 ^ 0x33] = I("V\u0010\u0011\u0017\u0006", "yyrxh");
        ServerListEntryNormal.I[0x71 ^ 0x74] = I("", "cYNTy");
        ServerListEntryNormal.I[0x62 ^ 0x64] = I("", "gJUZC");
        ServerListEntryNormal.I[0x10 ^ 0x17] = I("+&\u0010\f\f\u001cj\u0016\u001c\u0016H%\u001fI\u0006\t>\u001cH", "hJyib");
        ServerListEntryNormal.I[0x74 ^ 0x7C] = I("6&\b:!\u0017c\u001590E,\u001cl \u00047\u001fm", "eCzLD");
        ServerListEntryNormal.I[0xAD ^ 0xA4] = I("}??Q\u0011:?>\u0014\u0011!8?\u001f[", "UQPqr");
        ServerListEntryNormal.I[0x1A ^ 0x10] = I("5\u0014", "XgjoZ");
        ServerListEntryNormal.I[0x3A ^ 0x31] = I("\u0015$7#\u000b+*wjL", "EMYDb");
        ServerListEntryNormal.I[0x96 ^ 0x9A] = I("\b\u0004\u000b\u0017G'\u0014XUSe\u0001\u0011\u001b\u0002)\u0002X\u0014\u000e!\u0014", "Eqxcg");
        ServerListEntryNormal.I[0x6D ^ 0x60] = I("\u0001/2\rZ.?aONl*(\u0001\u001f )a\u0011\u0013+2", "LZAyz");
        ServerListEntryNormal.I[0x62 ^ 0x6C] = I("\u0019\u0004\u001f3&9\u000eI;)?\u0004I4%\"J\u001a78&\u000f\u001br", "PjiRJ");
        ServerListEntryNormal.I[0x3A ^ 0x35] = I("G\u007f", "gWZCI");
        ServerListEntryNormal.I[0x94 ^ 0x84] = I("s", "ZZjLI");
    }
    
    @Override
    public void drawEntry(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final boolean b) {
        if (!this.field_148301_e.field_78841_f) {
            this.field_148301_e.field_78841_f = (" ".length() != 0);
            this.field_148301_e.pingToServer = -2L;
            this.field_148301_e.serverMOTD = ServerListEntryNormal.I[0x23 ^ 0x26];
            this.field_148301_e.populationInfo = ServerListEntryNormal.I[0xA4 ^ 0xA2];
            ServerListEntryNormal.field_148302_b.submit(new Runnable(this) {
                private static final String[] I;
                final ServerListEntryNormal this$0;
                
                private static void I() {
                    (I = new String["  ".length()])["".length()] = I("72*}8T!!)#\u0018%!z$\u001b 04-\u00196", "tSDZL");
                    ServerListEntryNormal$1.I[" ".length()] = I("\u000b\")@\rh (\t\u0017- 3G\r'c4\u0002\u000b>&5I", "HCGgy");
                }
                
                static {
                    I();
                }
                
                @Override
                public void run() {
                    try {
                        ServerListEntryNormal.access$0(this.this$0).getOldServerPinger().ping(ServerListEntryNormal.access$1(this.this$0));
                        "".length();
                        if (4 == -1) {
                            throw null;
                        }
                    }
                    catch (UnknownHostException ex) {
                        ServerListEntryNormal.access$1(this.this$0).pingToServer = -1L;
                        ServerListEntryNormal.access$1(this.this$0).serverMOTD = EnumChatFormatting.DARK_RED + ServerListEntryNormal$1.I["".length()];
                        "".length();
                        if (-1 >= 4) {
                            throw null;
                        }
                    }
                    catch (Exception ex2) {
                        ServerListEntryNormal.access$1(this.this$0).pingToServer = -1L;
                        ServerListEntryNormal.access$1(this.this$0).serverMOTD = EnumChatFormatting.DARK_RED + ServerListEntryNormal$1.I[" ".length()];
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
                        if (4 < -1) {
                            throw null;
                        }
                    }
                    return sb.toString();
                }
            });
        }
        int n8;
        if (this.field_148301_e.version > (0x93 ^ 0xBC)) {
            n8 = " ".length();
            "".length();
            if (0 < 0) {
                throw null;
            }
        }
        else {
            n8 = "".length();
        }
        final int n9 = n8;
        int n10;
        if (this.field_148301_e.version < (0xAC ^ 0x83)) {
            n10 = " ".length();
            "".length();
            if (0 >= 2) {
                throw null;
            }
        }
        else {
            n10 = "".length();
        }
        final int n11 = n10;
        int n12;
        if (n9 == 0 && n11 == 0) {
            n12 = "".length();
            "".length();
            if (0 >= 1) {
                throw null;
            }
        }
        else {
            n12 = " ".length();
        }
        final int n13 = n12;
        this.mc.fontRendererObj.drawString(this.field_148301_e.serverName, n2 + (0x99 ^ 0xB9) + "   ".length(), n3 + " ".length(), 4561473 + 8377271 - 4521826 + 8360297);
        final List listFormattedStringToWidth = this.mc.fontRendererObj.listFormattedStringToWidth(this.field_148301_e.serverMOTD, n4 - (0x9 ^ 0x29) - "  ".length());
        int i = "".length();
        "".length();
        if (2 < -1) {
            throw null;
        }
        while (i < Math.min(listFormattedStringToWidth.size(), "  ".length())) {
            this.mc.fontRendererObj.drawString(listFormattedStringToWidth.get(i), n2 + (0x6D ^ 0x4D) + "   ".length(), n3 + (0xAE ^ 0xA2) + this.mc.fontRendererObj.FONT_HEIGHT * i, 6711030 + 4063771 - 4780985 + 2427688);
            ++i;
        }
        String s;
        if (n13 != 0) {
            s = EnumChatFormatting.DARK_RED + this.field_148301_e.gameVersion;
            "".length();
            if (1 >= 2) {
                throw null;
            }
        }
        else {
            s = this.field_148301_e.populationInfo;
        }
        final String s2 = s;
        final int stringWidth = this.mc.fontRendererObj.getStringWidth(s2);
        this.mc.fontRendererObj.drawString(s2, n2 + n4 - stringWidth - (0x7C ^ 0x73) - "  ".length(), n3 + " ".length(), 648301 + 3580524 + 2930646 + 1262033);
        int n14 = "".length();
        String hoveringText = null;
        int n15;
        String string;
        if (n13 != 0) {
            n15 = (0xA9 ^ 0xAC);
            String s3;
            if (n9 != 0) {
                s3 = ServerListEntryNormal.I[0xA1 ^ 0xA6];
                "".length();
                if (3 < -1) {
                    throw null;
                }
            }
            else {
                s3 = ServerListEntryNormal.I[0xB3 ^ 0xBB];
            }
            string = s3;
            hoveringText = this.field_148301_e.playerList;
            "".length();
            if (true != true) {
                throw null;
            }
        }
        else if (this.field_148301_e.field_78841_f && this.field_148301_e.pingToServer != -2L) {
            if (this.field_148301_e.pingToServer < 0L) {
                n15 = (0x5F ^ 0x5A);
                "".length();
                if (4 < 2) {
                    throw null;
                }
            }
            else if (this.field_148301_e.pingToServer < 150L) {
                n15 = "".length();
                "".length();
                if (1 >= 4) {
                    throw null;
                }
            }
            else if (this.field_148301_e.pingToServer < 300L) {
                n15 = " ".length();
                "".length();
                if (2 < -1) {
                    throw null;
                }
            }
            else if (this.field_148301_e.pingToServer < 600L) {
                n15 = "  ".length();
                "".length();
                if (4 < -1) {
                    throw null;
                }
            }
            else if (this.field_148301_e.pingToServer < 1000L) {
                n15 = "   ".length();
                "".length();
                if (0 == 2) {
                    throw null;
                }
            }
            else {
                n15 = (0x17 ^ 0x13);
            }
            if (this.field_148301_e.pingToServer < 0L) {
                string = ServerListEntryNormal.I[0x18 ^ 0x11];
                "".length();
                if (true != true) {
                    throw null;
                }
            }
            else {
                string = String.valueOf(this.field_148301_e.pingToServer) + ServerListEntryNormal.I[0x38 ^ 0x32];
                hoveringText = this.field_148301_e.playerList;
                "".length();
                if (3 <= 1) {
                    throw null;
                }
            }
        }
        else {
            n14 = " ".length();
            n15 = (int)(Minecraft.getSystemTime() / 100L + n * "  ".length() & 0x7L);
            if (n15 > (0x13 ^ 0x17)) {
                n15 = (0x20 ^ 0x28) - n15;
            }
            string = ServerListEntryNormal.I[0x76 ^ 0x7D];
        }
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        this.mc.getTextureManager().bindTexture(Gui.icons);
        Gui.drawModalRectWithCustomSizedTexture(n2 + n4 - (0x95 ^ 0x9A), n3, n14 * (0x72 ^ 0x78), 174 + 23 - 147 + 126 + n15 * (0x8 ^ 0x0), 0x64 ^ 0x6E, 0x3 ^ 0xB, 256.0f, 256.0f);
        if (this.field_148301_e.getBase64EncodedIconData() != null && !this.field_148301_e.getBase64EncodedIconData().equals(this.field_148299_g)) {
            this.field_148299_g = this.field_148301_e.getBase64EncodedIconData();
            this.prepareServerIcon();
            this.field_148303_c.getServerList().saveServerList();
        }
        if (this.field_148305_h != null) {
            this.func_178012_a(n2, n3, this.field_148306_i);
            "".length();
            if (2 == 1) {
                throw null;
            }
        }
        else {
            this.func_178012_a(n2, n3, ServerListEntryNormal.UNKNOWN_SERVER);
        }
        final int n16 = n6 - n2;
        final int n17 = n7 - n3;
        if (n16 >= n4 - (0x5F ^ 0x50) && n16 <= n4 - (0x9B ^ 0x9E) && n17 >= 0 && n17 <= (0x8D ^ 0x85)) {
            this.field_148303_c.setHoveringText(string);
            "".length();
            if (1 == -1) {
                throw null;
            }
        }
        else if (n16 >= n4 - stringWidth - (0x1A ^ 0x15) - "  ".length() && n16 <= n4 - (0x97 ^ 0x98) - "  ".length() && n17 >= 0 && n17 <= (0xCF ^ 0xC7)) {
            this.field_148303_c.setHoveringText(hoveringText);
        }
        if (this.mc.gameSettings.touchscreen || b) {
            this.mc.getTextureManager().bindTexture(ServerListEntryNormal.SERVER_SELECTION_BUTTONS);
            Gui.drawRect(n2, n3, n2 + (0x35 ^ 0x15), n3 + (0xBD ^ 0x9D), -(796988947 + 1319710518 - 2058079462 + 1542518541));
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            final int n18 = n6 - n2;
            final int n19 = n7 - n3;
            if (this.func_178013_b()) {
                if (n18 < (0x68 ^ 0x48) && n18 > (0x5A ^ 0x4A)) {
                    Gui.drawModalRectWithCustomSizedTexture(n2, n3, 0.0f, 32.0f, 0x41 ^ 0x61, 0x50 ^ 0x70, 256.0f, 256.0f);
                    "".length();
                    if (3 == 4) {
                        throw null;
                    }
                }
                else {
                    Gui.drawModalRectWithCustomSizedTexture(n2, n3, 0.0f, 0.0f, 0x6E ^ 0x4E, 0x6A ^ 0x4A, 256.0f, 256.0f);
                }
            }
            if (this.field_148303_c.func_175392_a(this, n)) {
                if (n18 < (0xB6 ^ 0xA6) && n19 < (0xA0 ^ 0xB0)) {
                    Gui.drawModalRectWithCustomSizedTexture(n2, n3, 96.0f, 32.0f, 0x49 ^ 0x69, 0xBF ^ 0x9F, 256.0f, 256.0f);
                    "".length();
                    if (4 == 0) {
                        throw null;
                    }
                }
                else {
                    Gui.drawModalRectWithCustomSizedTexture(n2, n3, 96.0f, 0.0f, 0x67 ^ 0x47, 0x8C ^ 0xAC, 256.0f, 256.0f);
                }
            }
            if (this.field_148303_c.func_175394_b(this, n)) {
                if (n18 < (0xAB ^ 0xBB) && n19 > (0x6E ^ 0x7E)) {
                    Gui.drawModalRectWithCustomSizedTexture(n2, n3, 64.0f, 32.0f, 0x32 ^ 0x12, 0x8C ^ 0xAC, 256.0f, 256.0f);
                    "".length();
                    if (3 == 2) {
                        throw null;
                    }
                }
                else {
                    Gui.drawModalRectWithCustomSizedTexture(n2, n3, 64.0f, 0.0f, 0xB7 ^ 0x97, 0x29 ^ 0x9, 256.0f, 256.0f);
                }
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
            if (4 <= 1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    protected void func_178012_a(final int n, final int n2, final ResourceLocation resourceLocation) {
        this.mc.getTextureManager().bindTexture(resourceLocation);
        GlStateManager.enableBlend();
        Gui.drawModalRectWithCustomSizedTexture(n, n2, 0.0f, 0.0f, 0x8F ^ 0xAF, 0x6B ^ 0x4B, 32.0f, 32.0f);
        GlStateManager.disableBlend();
    }
}
