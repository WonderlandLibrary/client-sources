package net.minecraft.client.multiplayer;

import java.util.concurrent.atomic.*;
import java.io.*;
import net.minecraft.client.network.*;
import net.minecraft.network.handshake.client.*;
import net.minecraft.network.*;
import net.minecraft.network.login.client.*;
import net.minecraft.util.*;
import net.minecraft.client.gui.*;
import java.net.*;
import net.minecraft.client.*;
import net.minecraft.client.resources.*;
import org.apache.logging.log4j.*;

public class GuiConnecting extends GuiScreen
{
    private final GuiScreen previousGuiScreen;
    private NetworkManager networkManager;
    private static final AtomicInteger CONNECTION_ID;
    private static final String[] I;
    private static final Logger logger;
    private boolean cancel;
    
    static boolean access$0(final GuiConnecting guiConnecting) {
        return guiConnecting.cancel;
    }
    
    @Override
    protected void actionPerformed(final GuiButton guiButton) throws IOException {
        if (guiButton.id == 0) {
            this.cancel = (" ".length() != 0);
            if (this.networkManager != null) {
                this.networkManager.closeChannel(new ChatComponentText(GuiConnecting.I[0x2F ^ 0x2B]));
            }
            this.mc.displayGuiScreen(this.previousGuiScreen);
        }
    }
    
    private void connect(final String s, final int n) {
        GuiConnecting.logger.info(GuiConnecting.I["".length()] + s + GuiConnecting.I[" ".length()] + n);
        new Thread(this, GuiConnecting.I["  ".length()] + GuiConnecting.CONNECTION_ID.incrementAndGet(), s, n) {
            private static final String[] I;
            final GuiConnecting this$0;
            private final int val$port;
            private final String val$ip;
            
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
                    if (-1 == 0) {
                        throw null;
                    }
                }
                return sb.toString();
            }
            
            private static void I() {
                (I = new String[0x9D ^ 0x94])["".length()] = I("3(\u0006\u001f=\u001e`\u0007S:\u001f)\u001d\u0016:\u0004g\u0007\u001cy\u0003\"\u0001\u0005<\u0002", "pGssY");
                GuiConnecting$1.I[" ".length()] = I("\u0014\u0002\u0006\u0002#\u0014\u0019F\n'\u001e\u0001\r\b", "wmhlF");
                GuiConnecting$1.I["  ".length()] = I("1'# %; 5 >{)5-/''3\u0011/4=?-", "UNPCJ");
                GuiConnecting$1.I["   ".length()] = I("\u0003\t/\u001b?!\td\u001d?%\u0013", "VgDuP");
                GuiConnecting$1.I[0x80 ^ 0x84] = I("\")\u00199<\u000fa\u0018u;\u000e(\u00020;\u0015f\u0018:x\u0012#\u001e#=\u0013", "aFlUX");
                GuiConnecting$1.I[0x55 ^ 0x50] = I("O", "ujavK");
                GuiConnecting$1.I[0x55 ^ 0x53] = I("", "WVXAF");
                GuiConnecting$1.I[0x61 ^ 0x66] = I(";\",?\u0016;9l7\u00121!'5", "XMBQs");
                GuiConnecting$1.I[0x66 ^ 0x6E] = I("4:\u0000::>=\u0016:!~4\u001670\":\u0010\u000b01 \u001c7", "PSsYU");
            }
            
            @Override
            public void run() {
                InetAddress byName = null;
                try {
                    if (GuiConnecting.access$0(this.this$0)) {
                        return;
                    }
                    byName = InetAddress.getByName(this.val$ip);
                    GuiConnecting.access$2(this.this$0, NetworkManager.func_181124_a(byName, this.val$port, GuiConnecting.access$1(this.this$0).gameSettings.func_181148_f()));
                    GuiConnecting.access$3(this.this$0).setNetHandler(new NetHandlerLoginClient(GuiConnecting.access$3(this.this$0), GuiConnecting.access$1(this.this$0), GuiConnecting.access$4(this.this$0)));
                    GuiConnecting.access$3(this.this$0).sendPacket(new C00Handshake(0x21 ^ 0xE, this.val$ip, this.val$port, EnumConnectionState.LOGIN));
                    GuiConnecting.access$3(this.this$0).sendPacket(new C00PacketLoginStart(GuiConnecting.access$1(this.this$0).getSession().getProfile()));
                    "".length();
                    if (2 <= -1) {
                        throw null;
                    }
                }
                catch (UnknownHostException ex) {
                    if (GuiConnecting.access$0(this.this$0)) {
                        return;
                    }
                    GuiConnecting.access$5().error(GuiConnecting$1.I["".length()], (Throwable)ex);
                    final Minecraft access$1 = GuiConnecting.access$1(this.this$0);
                    final GuiScreen access$2 = GuiConnecting.access$4(this.this$0);
                    final String s = GuiConnecting$1.I[" ".length()];
                    final String s2 = GuiConnecting$1.I["  ".length()];
                    final Object[] array = new Object[" ".length()];
                    array["".length()] = GuiConnecting$1.I["   ".length()];
                    access$1.displayGuiScreen(new GuiDisconnected(access$2, s, new ChatComponentTranslation(s2, array)));
                    "".length();
                    if (-1 == 3) {
                        throw null;
                    }
                }
                catch (Exception ex2) {
                    if (GuiConnecting.access$0(this.this$0)) {
                        return;
                    }
                    GuiConnecting.access$5().error(GuiConnecting$1.I[0x67 ^ 0x63], (Throwable)ex2);
                    String s3 = ex2.toString();
                    if (byName != null) {
                        s3 = s3.replaceAll(String.valueOf(byName.toString()) + GuiConnecting$1.I[0xB ^ 0xE] + this.val$port, GuiConnecting$1.I[0x2 ^ 0x4]);
                    }
                    final Minecraft access$3 = GuiConnecting.access$1(this.this$0);
                    final GuiScreen access$4 = GuiConnecting.access$4(this.this$0);
                    final String s4 = GuiConnecting$1.I[0xB1 ^ 0xB6];
                    final String s5 = GuiConnecting$1.I[0x30 ^ 0x38];
                    final Object[] array2 = new Object[" ".length()];
                    array2["".length()] = s3;
                    access$3.displayGuiScreen(new GuiDisconnected(access$4, s4, new ChatComponentTranslation(s5, array2)));
                }
            }
            
            static {
                I();
            }
        }.start();
    }
    
    static Minecraft access$1(final GuiConnecting guiConnecting) {
        return guiConnecting.mc;
    }
    
    public GuiConnecting(final GuiScreen previousGuiScreen, final Minecraft mc, final ServerData serverData) {
        this.mc = mc;
        this.previousGuiScreen = previousGuiScreen;
        final ServerAddress func_78860_a = ServerAddress.func_78860_a(serverData.serverIP);
        mc.loadWorld(null);
        mc.setServerData(serverData);
        this.connect(func_78860_a.getIP(), func_78860_a.getPort());
    }
    
    static NetworkManager access$3(final GuiConnecting guiConnecting) {
        return guiConnecting.networkManager;
    }
    
    static Logger access$5() {
        return GuiConnecting.logger;
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        this.drawDefaultBackground();
        if (this.networkManager == null) {
            this.drawCenteredString(this.fontRendererObj, I18n.format(GuiConnecting.I[0x2E ^ 0x2B], new Object["".length()]), this.width / "  ".length(), this.height / "  ".length() - (0x36 ^ 0x4), 5033318 + 10975909 - 4026014 + 4794002);
            "".length();
            if (4 <= 0) {
                throw null;
            }
        }
        else {
            this.drawCenteredString(this.fontRendererObj, I18n.format(GuiConnecting.I[0xA3 ^ 0xA5], new Object["".length()]), this.width / "  ".length(), this.height / "  ".length() - (0x4F ^ 0x7D), 14980734 + 6458394 - 9200546 + 4538633);
        }
        super.drawScreen(n, n2, n3);
    }
    
    static {
        I();
        CONNECTION_ID = new AtomicInteger("".length());
        logger = LogManager.getLogger();
    }
    
    static void access$2(final GuiConnecting guiConnecting, final NetworkManager networkManager) {
        guiConnecting.networkManager = networkManager;
    }
    
    private static void I() {
        (I = new String[0x38 ^ 0x3F])["".length()] = I("'\u0003;\u0006*\u0007\u0018<\u0006(D\u0018:H", "dlUhO");
        GuiConnecting.I[" ".length()] = I("nv", "BVztV");
        GuiConnecting.I["  ".length()] = I("\t'\u000b\u0003\u0010(b:\u001a\u001b4'\u001a\u0001\u001a(bZ", "ZByuu");
        GuiConnecting.I["   ".length()] = I("0\u0014(B96\u000f\"\t6", "WaAlZ");
        GuiConnecting.I[0xA7 ^ 0xA3] = I("22\u001c'\f\u00164", "sPsUx");
        GuiConnecting.I[0x4D ^ 0x48] = I("\u0001)\u001f%\u0006\u00012_(\f\f(\u0014(\u0017\u000b(\u0016", "bFqKc");
        GuiConnecting.I[0x89 ^ 0x8F] = I("\u0011\u0016\f\u0001\u0014\u0011\rL\u000e\u0004\u0006\u0011\r\u001d\u0018\b\u0010\f\b", "ryboq");
    }
    
    static GuiScreen access$4(final GuiConnecting guiConnecting) {
        return guiConnecting.previousGuiScreen;
    }
    
    @Override
    public void updateScreen() {
        if (this.networkManager != null) {
            if (this.networkManager.isChannelOpen()) {
                this.networkManager.processReceivedPackets();
                "".length();
                if (3 == 4) {
                    throw null;
                }
            }
            else {
                this.networkManager.checkDisconnected();
            }
        }
    }
    
    public GuiConnecting(final GuiScreen previousGuiScreen, final Minecraft mc, final String s, final int n) {
        this.mc = mc;
        this.previousGuiScreen = previousGuiScreen;
        mc.loadWorld(null);
        this.connect(s, n);
    }
    
    @Override
    protected void keyTyped(final char c, final int n) throws IOException {
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
            if (3 == 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public void initGui() {
        this.buttonList.clear();
        this.buttonList.add(new GuiButton("".length(), this.width / "  ".length() - (0x5D ^ 0x39), this.height / (0x78 ^ 0x7C) + (0xC3 ^ 0xBB) + (0x54 ^ 0x58), I18n.format(GuiConnecting.I["   ".length()], new Object["".length()])));
    }
}
