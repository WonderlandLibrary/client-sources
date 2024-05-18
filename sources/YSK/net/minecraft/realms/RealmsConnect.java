package net.minecraft.realms;

import org.apache.logging.log4j.*;
import net.minecraft.client.*;
import net.minecraft.client.network.*;
import net.minecraft.client.gui.*;
import net.minecraft.network.handshake.client.*;
import net.minecraft.network.*;
import net.minecraft.network.login.client.*;
import net.minecraft.util.*;
import java.net.*;

public class RealmsConnect
{
    private static final Logger LOGGER;
    private NetworkManager connection;
    private final RealmsScreen onlineScreen;
    private boolean aborted;
    private static final String[] I;
    
    public void abort() {
        this.aborted = (" ".length() != 0);
    }
    
    public RealmsConnect(final RealmsScreen onlineScreen) {
        this.aborted = ("".length() != 0);
        this.onlineScreen = onlineScreen;
    }
    
    static {
        I();
        LOGGER = LogManager.getLogger();
    }
    
    static Logger access$4() {
        return RealmsConnect.LOGGER;
    }
    
    static boolean access$0(final RealmsConnect realmsConnect) {
        return realmsConnect.aborted;
    }
    
    static void access$1(final RealmsConnect realmsConnect, final NetworkManager connection) {
        realmsConnect.connection = connection;
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
            if (3 < -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public void connect(final String s, final int n) {
        Realms.setConnectedToRealms(" ".length() != 0);
        new Thread(this, RealmsConnect.I["".length()], s, n) {
            private final String val$p_connect_1_;
            private final int val$p_connect_2_;
            final RealmsConnect this$0;
            private static final String[] I;
            
            static {
                I();
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
                    if (-1 != -1) {
                        throw null;
                    }
                }
                return sb.toString();
            }
            
            @Override
            public void run() {
                InetAddress byName = null;
                try {
                    byName = InetAddress.getByName(this.val$p_connect_1_);
                    if (RealmsConnect.access$0(this.this$0)) {
                        return;
                    }
                    RealmsConnect.access$1(this.this$0, NetworkManager.func_181124_a(byName, this.val$p_connect_2_, Minecraft.getMinecraft().gameSettings.func_181148_f()));
                    if (RealmsConnect.access$0(this.this$0)) {
                        return;
                    }
                    RealmsConnect.access$2(this.this$0).setNetHandler(new NetHandlerLoginClient(RealmsConnect.access$2(this.this$0), Minecraft.getMinecraft(), RealmsConnect.access$3(this.this$0).getProxy()));
                    if (RealmsConnect.access$0(this.this$0)) {
                        return;
                    }
                    RealmsConnect.access$2(this.this$0).sendPacket(new C00Handshake(0x35 ^ 0x1A, this.val$p_connect_1_, this.val$p_connect_2_, EnumConnectionState.LOGIN));
                    if (RealmsConnect.access$0(this.this$0)) {
                        return;
                    }
                    RealmsConnect.access$2(this.this$0).sendPacket(new C00PacketLoginStart(Minecraft.getMinecraft().getSession().getProfile()));
                    "".length();
                    if (-1 >= 4) {
                        throw null;
                    }
                }
                catch (UnknownHostException ex) {
                    Realms.clearResourcePack();
                    if (RealmsConnect.access$0(this.this$0)) {
                        return;
                    }
                    RealmsConnect.access$4().error(RealmsConnect$1.I["".length()], (Throwable)ex);
                    Minecraft.getMinecraft().getResourcePackRepository().func_148529_f();
                    final RealmsScreen access$3 = RealmsConnect.access$3(this.this$0);
                    final String s = RealmsConnect$1.I[" ".length()];
                    final String s2 = RealmsConnect$1.I["  ".length()];
                    final Object[] array = new Object[" ".length()];
                    array["".length()] = RealmsConnect$1.I["   ".length()] + this.val$p_connect_1_ + RealmsConnect$1.I[0xB1 ^ 0xB5];
                    Realms.setScreen(new DisconnectedRealmsScreen(access$3, s, new ChatComponentTranslation(s2, array)));
                    "".length();
                    if (3 < 1) {
                        throw null;
                    }
                }
                catch (Exception ex2) {
                    Realms.clearResourcePack();
                    if (RealmsConnect.access$0(this.this$0)) {
                        return;
                    }
                    RealmsConnect.access$4().error(RealmsConnect$1.I[0x26 ^ 0x23], (Throwable)ex2);
                    String s3 = ex2.toString();
                    if (byName != null) {
                        s3 = s3.replaceAll(String.valueOf(byName.toString()) + RealmsConnect$1.I[0xA5 ^ 0xA3] + this.val$p_connect_2_, RealmsConnect$1.I[0x98 ^ 0x9F]);
                    }
                    final RealmsScreen access$4 = RealmsConnect.access$3(this.this$0);
                    final String s4 = RealmsConnect$1.I[0x7 ^ 0xF];
                    final String s5 = RealmsConnect$1.I[0x36 ^ 0x3F];
                    final Object[] array2 = new Object[" ".length()];
                    array2["".length()] = s3;
                    Realms.setScreen(new DisconnectedRealmsScreen(access$4, s4, new ChatComponentTranslation(s5, array2)));
                }
            }
            
            private static void I() {
                (I = new String[0x93 ^ 0x99])["".length()] = I(",\u001e=.#\u0001V<b$\u0000\u001f&'$\u001bQ<-g\u0018\u001e:.#", "oqHBG");
                RealmsConnect$1.I[" ".length()] = I("/\u001c(4\u0013/\u0007h<\u0017%\u001f#>", "LsFZv");
                RealmsConnect$1.I["  ".length()] = I("\u0014;?\u0011<\u001e<)\u0011'^5)\u001c6\u0002;/ 6\u0011!#\u001c", "pRLrS");
                RealmsConnect$1.I["   ".length()] = I("/\u001e\u0003':\r\u001eH!:\t\u0004Hn", "zphIU");
                RealmsConnect$1.I[0x2B ^ 0x2F] = I("i", "NiMKA");
                RealmsConnect$1.I[0x1C ^ 0x19] = I("\u0010\r-:\n=E,v\r<\f63\r'B,9N$\r*:\n", "SbXVn");
                RealmsConnect$1.I[0x12 ^ 0x14] = I("~", "DlFxn");
                RealmsConnect$1.I[0x1C ^ 0x1B] = I("", "IlbEE");
                RealmsConnect$1.I[0x44 ^ 0x4C] = I("!;'\"=! g*9+8,(", "BTILX");
                RealmsConnect$1.I[0x26 ^ 0x2F] = I("\u0007\u001c\n!\u001b\r\u001b\u001c!\u0000M\u0012\u001c,\u0011\u0011\u001c\u001a\u0010\u0011\u0002\u0006\u0016,", "cuyBt");
            }
        }.start();
    }
    
    public void tick() {
        if (this.connection != null) {
            if (this.connection.isChannelOpen()) {
                this.connection.processReceivedPackets();
                "".length();
                if (0 == -1) {
                    throw null;
                }
            }
            else {
                this.connection.checkDisconnected();
            }
        }
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("\u000b7\u0016\u001a\u0007*\u007f\u0014\u0019\u000477\u0014\u0002G-3\u0004\u001d", "YRwvj");
    }
    
    static RealmsScreen access$3(final RealmsConnect realmsConnect) {
        return realmsConnect.onlineScreen;
    }
    
    static NetworkManager access$2(final RealmsConnect realmsConnect) {
        return realmsConnect.connection;
    }
}
