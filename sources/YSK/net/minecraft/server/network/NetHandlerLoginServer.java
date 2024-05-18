package net.minecraft.server.network;

import net.minecraft.network.login.*;
import com.mojang.authlib.*;
import net.minecraft.entity.player.*;
import net.minecraft.server.*;
import javax.crypto.*;
import java.util.concurrent.atomic.*;
import org.apache.logging.log4j.*;
import org.apache.commons.lang3.*;
import net.minecraft.network.*;
import com.google.common.base.*;
import io.netty.channel.*;
import io.netty.util.concurrent.*;
import net.minecraft.network.login.server.*;
import net.minecraft.network.login.client.*;
import java.util.*;
import net.minecraft.util.*;
import java.math.*;
import com.mojang.authlib.exceptions.*;
import java.security.*;

public class NetHandlerLoginServer implements ITickable, INetHandlerLoginServer
{
    private GameProfile loginGameProfile;
    private final byte[] verifyToken;
    private String serverId;
    private static final Random RANDOM;
    private static final String[] I;
    private static final Logger logger;
    private EntityPlayerMP field_181025_l;
    private LoginState currentLoginState;
    private int connectionTimer;
    private final MinecraftServer server;
    private SecretKey secretKey;
    private static final AtomicInteger AUTHENTICATOR_THREAD_ID;
    public final NetworkManager networkManager;
    
    static Logger access$5() {
        return NetHandlerLoginServer.logger;
    }
    
    static {
        I();
        AUTHENTICATOR_THREAD_ID = new AtomicInteger("".length());
        logger = LogManager.getLogger();
        RANDOM = new Random();
    }
    
    static void access$4(final NetHandlerLoginServer netHandlerLoginServer, final GameProfile loginGameProfile) {
        netHandlerLoginServer.loginGameProfile = loginGameProfile;
    }
    
    @Override
    public void update() {
        if (this.currentLoginState == LoginState.READY_TO_ACCEPT) {
            this.tryAcceptPlayer();
            "".length();
            if (0 <= -1) {
                throw null;
            }
        }
        else if (this.currentLoginState == LoginState.DELAY_ACCEPT && this.server.getConfigurationManager().getPlayerByUUID(this.loginGameProfile.getId()) == null) {
            this.currentLoginState = LoginState.READY_TO_ACCEPT;
            this.server.getConfigurationManager().initializeConnectionToPlayer(this.networkManager, this.field_181025_l);
            this.field_181025_l = null;
        }
        final int connectionTimer = this.connectionTimer;
        this.connectionTimer = connectionTimer + " ".length();
        if (connectionTimer == 391 + 9 - 22 + 222) {
            this.closeConnection(NetHandlerLoginServer.I[" ".length()]);
        }
    }
    
    static void access$6(final NetHandlerLoginServer netHandlerLoginServer, final LoginState currentLoginState) {
        netHandlerLoginServer.currentLoginState = currentLoginState;
    }
    
    @Override
    public void processLoginStart(final C00PacketLoginStart c00PacketLoginStart) {
        int n;
        if (this.currentLoginState == LoginState.HELLO) {
            n = " ".length();
            "".length();
            if (2 < 2) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        Validate.validState((boolean)(n != 0), NetHandlerLoginServer.I[0x6A ^ 0x62], new Object["".length()]);
        this.loginGameProfile = c00PacketLoginStart.getProfile();
        if (this.server.isServerInOnlineMode() && !this.networkManager.isLocalChannel()) {
            this.currentLoginState = LoginState.KEY;
            this.networkManager.sendPacket(new S01PacketEncryptionRequest(this.serverId, this.server.getKeyPair().getPublic(), this.verifyToken));
            "".length();
            if (4 <= 3) {
                throw null;
            }
        }
        else {
            this.currentLoginState = LoginState.READY_TO_ACCEPT;
        }
    }
    
    protected GameProfile getOfflineProfile(final GameProfile gameProfile) {
        return new GameProfile(UUID.nameUUIDFromBytes((NetHandlerLoginServer.I[0x68 ^ 0x64] + gameProfile.getName()).getBytes(Charsets.UTF_8)), gameProfile.getName());
    }
    
    static GameProfile access$1(final NetHandlerLoginServer netHandlerLoginServer) {
        return netHandlerLoginServer.loginGameProfile;
    }
    
    static SecretKey access$3(final NetHandlerLoginServer netHandlerLoginServer) {
        return netHandlerLoginServer.secretKey;
    }
    
    private static void I() {
        (I = new String[0x0 ^ 0xD])["".length()] = I("", "oLTGT");
        NetHandlerLoginServer.I[" ".length()] = I("\u001d6#\rI=6#F\u0005&7+F\u001d&y \t\u000ei0\"", "IYLfi");
        NetHandlerLoginServer.I["  ".length()] = I(">\u001d\t\u00066\u0014\u001a\u001f\u0006-\u0013\u001a\u001dE", "ztzeY");
        NetHandlerLoginServer.I["   ".length()] = I("rT", "HtlOt");
        NetHandlerLoginServer.I[0xAE ^ 0xAA] = I("*\u001a\u0002*\u001bO\u001f\u0018,\u0005\u001c\u001cP!\u0000\u001c\u000b\u001f+\u0007\n\u000b\u0004,\u0007\bH\u0000)\b\u0016\r\u0002", "ohpEi");
        NetHandlerLoginServer.I[0x1F ^ 0x1A] = I("Y9*)?Y6*4%\u001c613$\u0017oe", "yUEZK");
        NetHandlerLoginServer.I[0x92 ^ 0x94] = I("A}", "aUgXE");
        NetHandlerLoginServer.I[0x7B ^ 0x7C] = I("E", "ldhwo");
        NetHandlerLoginServer.I[0x58 ^ 0x50] = I("\u001f\u0016\u000e31/\u001b\u001f.%j\u0010\u000e'-%X\u001b*\"!\u001d\u001f", "JxkKA");
        NetHandlerLoginServer.I[0x43 ^ 0x4A] = I("<)\u001f\n=\f$\u000e\u0017)I,\u001f\u000bm\u0019&\u0019\u0019(\u001d", "iGzrM");
        NetHandlerLoginServer.I[0x3D ^ 0x37] = I("\">#\u0006!\u00024u\t\"\u000530F", "kPUgM");
        NetHandlerLoginServer.I[0x9A ^ 0x91] = I("\u001f\u001f\u0006!L\u000b\u0019\u0017;\t$\u0018\n0\r>\u0003\u0011sO", "JlcSl");
        NetHandlerLoginServer.I[0x30 ^ 0x3C] = I("\u0000\t\u00169?!\n 976\n\u0002o", "OopUV");
    }
    
    public NetHandlerLoginServer(final MinecraftServer server, final NetworkManager networkManager) {
        this.verifyToken = new byte[0x9E ^ 0x9A];
        this.currentLoginState = LoginState.HELLO;
        this.serverId = NetHandlerLoginServer.I["".length()];
        this.server = server;
        this.networkManager = networkManager;
        NetHandlerLoginServer.RANDOM.nextBytes(this.verifyToken);
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
            if (1 < -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public void closeConnection(final String s) {
        try {
            NetHandlerLoginServer.logger.info(NetHandlerLoginServer.I["  ".length()] + this.getConnectionInfo() + NetHandlerLoginServer.I["   ".length()] + s);
            final ChatComponentText chatComponentText = new ChatComponentText(s);
            this.networkManager.sendPacket(new S00PacketDisconnect(chatComponentText));
            this.networkManager.closeChannel(chatComponentText);
            "".length();
            if (2 < 0) {
                throw null;
            }
        }
        catch (Exception ex) {
            NetHandlerLoginServer.logger.error(NetHandlerLoginServer.I[0x97 ^ 0x93], (Throwable)ex);
        }
    }
    
    static String access$2(final NetHandlerLoginServer netHandlerLoginServer) {
        return netHandlerLoginServer.serverId;
    }
    
    @Override
    public void onDisconnect(final IChatComponent chatComponent) {
        NetHandlerLoginServer.logger.info(String.valueOf(this.getConnectionInfo()) + NetHandlerLoginServer.I[0x46 ^ 0x43] + chatComponent.getUnformattedText());
    }
    
    static MinecraftServer access$0(final NetHandlerLoginServer netHandlerLoginServer) {
        return netHandlerLoginServer.server;
    }
    
    public void tryAcceptPlayer() {
        if (!this.loginGameProfile.isComplete()) {
            this.loginGameProfile = this.getOfflineProfile(this.loginGameProfile);
        }
        final String allowUserToConnect = this.server.getConfigurationManager().allowUserToConnect(this.networkManager.getRemoteAddress(), this.loginGameProfile);
        if (allowUserToConnect != null) {
            this.closeConnection(allowUserToConnect);
            "".length();
            if (0 == 4) {
                throw null;
            }
        }
        else {
            this.currentLoginState = LoginState.ACCEPTED;
            if (this.server.getNetworkCompressionTreshold() >= 0 && !this.networkManager.isLocalChannel()) {
                this.networkManager.sendPacket(new S03PacketEnableCompression(this.server.getNetworkCompressionTreshold()), (GenericFutureListener<? extends Future<? super Void>>)new ChannelFutureListener(this) {
                    final NetHandlerLoginServer this$0;
                    
                    public void operationComplete(final Future future) throws Exception {
                        this.operationComplete((ChannelFuture)future);
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
                    
                    public void operationComplete(final ChannelFuture channelFuture) throws Exception {
                        this.this$0.networkManager.setCompressionTreshold(NetHandlerLoginServer.access$0(this.this$0).getNetworkCompressionTreshold());
                    }
                }, (GenericFutureListener<? extends Future<? super Void>>[])new GenericFutureListener["".length()]);
            }
            this.networkManager.sendPacket(new S02PacketLoginSuccess(this.loginGameProfile));
            if (this.server.getConfigurationManager().getPlayerByUUID(this.loginGameProfile.getId()) != null) {
                this.currentLoginState = LoginState.DELAY_ACCEPT;
                this.field_181025_l = this.server.getConfigurationManager().createPlayerForUser(this.loginGameProfile);
                "".length();
                if (2 == 0) {
                    throw null;
                }
            }
            else {
                this.server.getConfigurationManager().initializeConnectionToPlayer(this.networkManager, this.server.getConfigurationManager().createPlayerForUser(this.loginGameProfile));
            }
        }
    }
    
    @Override
    public void processEncryptionResponse(final C01PacketEncryptionResponse c01PacketEncryptionResponse) {
        int n;
        if (this.currentLoginState == LoginState.KEY) {
            n = " ".length();
            "".length();
            if (3 == 4) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        Validate.validState((boolean)(n != 0), NetHandlerLoginServer.I[0x6D ^ 0x64], new Object["".length()]);
        final PrivateKey private1 = this.server.getKeyPair().getPrivate();
        if (!Arrays.equals(this.verifyToken, c01PacketEncryptionResponse.getVerifyToken(private1))) {
            throw new IllegalStateException(NetHandlerLoginServer.I[0x8B ^ 0x81]);
        }
        this.secretKey = c01PacketEncryptionResponse.getSecretKey(private1);
        this.currentLoginState = LoginState.AUTHENTICATING;
        this.networkManager.enableEncryption(this.secretKey);
        new Thread(this, NetHandlerLoginServer.I[0xCD ^ 0xC6] + NetHandlerLoginServer.AUTHENTICATOR_THREAD_ID.incrementAndGet()) {
            private static final String[] I;
            final NetHandlerLoginServer this$0;
            
            @Override
            public void run() {
                final GameProfile access$1 = NetHandlerLoginServer.access$1(this.this$0);
                try {
                    NetHandlerLoginServer.access$4(this.this$0, NetHandlerLoginServer.access$0(this.this$0).getMinecraftSessionService().hasJoinedServer(new GameProfile((UUID)null, access$1.getName()), new BigInteger(CryptManager.getServerIdHash(NetHandlerLoginServer.access$2(this.this$0), NetHandlerLoginServer.access$0(this.this$0).getKeyPair().getPublic(), NetHandlerLoginServer.access$3(this.this$0))).toString(0x18 ^ 0x8)));
                    if (NetHandlerLoginServer.access$1(this.this$0) != null) {
                        NetHandlerLoginServer.access$5().info(NetHandlerLoginServer$2.I["".length()] + NetHandlerLoginServer.access$1(this.this$0).getName() + NetHandlerLoginServer$2.I[" ".length()] + NetHandlerLoginServer.access$1(this.this$0).getId());
                        NetHandlerLoginServer.access$6(this.this$0, LoginState.READY_TO_ACCEPT);
                        "".length();
                        if (1 >= 4) {
                            throw null;
                        }
                    }
                    else if (NetHandlerLoginServer.access$0(this.this$0).isSinglePlayer()) {
                        NetHandlerLoginServer.access$5().warn(NetHandlerLoginServer$2.I["  ".length()]);
                        NetHandlerLoginServer.access$4(this.this$0, this.this$0.getOfflineProfile(access$1));
                        NetHandlerLoginServer.access$6(this.this$0, LoginState.READY_TO_ACCEPT);
                        "".length();
                        if (false) {
                            throw null;
                        }
                    }
                    else {
                        this.this$0.closeConnection(NetHandlerLoginServer$2.I["   ".length()]);
                        NetHandlerLoginServer.access$5().error(NetHandlerLoginServer$2.I[0x8C ^ 0x88] + NetHandlerLoginServer.access$1(this.this$0).getName() + NetHandlerLoginServer$2.I[0xAE ^ 0xAB]);
                        "".length();
                        if (1 == -1) {
                            throw null;
                        }
                    }
                }
                catch (AuthenticationUnavailableException ex) {
                    if (NetHandlerLoginServer.access$0(this.this$0).isSinglePlayer()) {
                        NetHandlerLoginServer.access$5().warn(NetHandlerLoginServer$2.I[0x4C ^ 0x4A]);
                        NetHandlerLoginServer.access$4(this.this$0, this.this$0.getOfflineProfile(access$1));
                        NetHandlerLoginServer.access$6(this.this$0, LoginState.READY_TO_ACCEPT);
                        "".length();
                        if (2 >= 4) {
                            throw null;
                        }
                    }
                    else {
                        this.this$0.closeConnection(NetHandlerLoginServer$2.I[0x4B ^ 0x4C]);
                        NetHandlerLoginServer.access$5().error(NetHandlerLoginServer$2.I[0xA7 ^ 0xAF]);
                    }
                }
            }
            
            private static void I() {
                (I = new String[0x5F ^ 0x56])["".length()] = I("!9$.l\u001b\nM\u001a \u0015\u0015\b\u0018l", "tlmjL");
                NetHandlerLoginServer$2.I[" ".length()] = I("o-\u0018F", "ODkfy");
                NetHandlerLoginServer$2.I["  ".length()] = I("\u0000%#8?\"d>;z0!8=<?d?'?4*+9?f&? z1-&8z*!>t..!'t3(d+:#1%3u", "FDJTZ");
                NetHandlerLoginServer$2.I["   ".length()] = I("5\u0014\u001a?\u0003\u0017U\u0007<F\u0005\u0010\u0001:\u0000\nU\u0006 \u0003\u0001\u001b\u0012>\u0003R", "susSf");
                NetHandlerLoginServer$2.I[0x9 ^ 0xD] = I("#+\r:!\u00175\rhh", "vXhHO");
                NetHandlerLoginServer$2.I[0xB1 ^ 0xB4] = I("Iw\u001c%\u0019\u000b3H#\u001fN=\u0007>\u001eN \u0001#\u0018N6\u0006w\u0019\u0000!\t;\u0019\nw\u001b2\u0003\u001d>\u00079", "nWhWp");
                NetHandlerLoginServer$2.I[0x98 ^ 0x9E] = I("\u0013,\f\u001d\u0016<-\u0011\u0016\u0012&0\u0017\u001bS!<\n\u0003\u0016 *X\u0014\u00017y\u001c\u001a\u0004<y\u001a\u0000\u0007r.\u0011\u0019\u001fr5\u001d\u0001S&1\u001d\u0018S;7X\u0014\u001d+.\u0019\fR", "RYxus");
                NetHandlerLoginServer$2.I[0xB ^ 0xC] = I("\u0019\u00198\"\u00146\u0018%)\u0010,\u0005#$Q+\t><\u0014*\u001fl+\u0003=L(%\u00066Bl\u001a\u001d=\r?/Q,\u001e5j\u0010?\r%$Q4\r8/\u0003tL?%\u0003*\u0015m", "XlLJq");
                NetHandlerLoginServer$2.I[0x1C ^ 0x14] = I("\u0017\u000b<\u001e\u0003:C=R\u00111\u0016 \u0014\u001et\u0011:\u0017\u0015:\u0005$\u0017G6\u0001*\u0013\u0012'\u0001i\u0001\u0002&\u0012,\u0000\u0014t\u0005;\u0017G!\n(\u0004\u0006=\b(\u0010\u000b1", "TdIrg");
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
                    if (2 < 1) {
                        throw null;
                    }
                }
                return sb.toString();
            }
            
            static {
                I();
            }
        }.start();
    }
    
    public String getConnectionInfo() {
        String s;
        if (this.loginGameProfile != null) {
            s = String.valueOf(this.loginGameProfile.toString()) + NetHandlerLoginServer.I[0x44 ^ 0x42] + this.networkManager.getRemoteAddress().toString() + NetHandlerLoginServer.I[0x5 ^ 0x2];
            "".length();
            if (2 < 1) {
                throw null;
            }
        }
        else {
            s = String.valueOf(this.networkManager.getRemoteAddress());
        }
        return s;
    }
    
    enum LoginState
    {
        private static final String[] I;
        
        DELAY_ACCEPT(LoginState.I[0xC ^ 0x8], 0x15 ^ 0x11), 
        AUTHENTICATING(LoginState.I["  ".length()], "  ".length()), 
        HELLO(LoginState.I["".length()], "".length()), 
        KEY(LoginState.I[" ".length()], " ".length());
        
        private static final LoginState[] ENUM$VALUES;
        
        ACCEPTED(LoginState.I[0x5A ^ 0x5F], 0x48 ^ 0x4D), 
        READY_TO_ACCEPT(LoginState.I["   ".length()], "   ".length());
        
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
        
        private static void I() {
            (I = new String[0x32 ^ 0x34])["".length()] = I("\u001c\u0017\n\u0000\u001b", "TRFLT");
            LoginState.I[" ".length()] = I("\u0006\u00106", "MUonS");
            LoginState.I["  ".length()] = I("\"\u00011$\u001d-\u0000,/\u00197\u001d++", "cTelX");
            LoginState.I["   ".length()] = I("\u0004,\u0007&*\t=\t=2\u0015*\u00032'", "ViFbs");
            LoginState.I[0x8F ^ 0x8B] = I("\u00140/88\u000f4 :$\u0000!", "Pucya");
            LoginState.I[0x4 ^ 0x1] = I("\u0002\u000e1\b\"\u0017\b6", "CMrMr");
        }
        
        private LoginState(final String s, final int n) {
        }
        
        static {
            I();
            final LoginState[] enum$VALUES = new LoginState[0x27 ^ 0x21];
            enum$VALUES["".length()] = LoginState.HELLO;
            enum$VALUES[" ".length()] = LoginState.KEY;
            enum$VALUES["  ".length()] = LoginState.AUTHENTICATING;
            enum$VALUES["   ".length()] = LoginState.READY_TO_ACCEPT;
            enum$VALUES[0x25 ^ 0x21] = LoginState.DELAY_ACCEPT;
            enum$VALUES[0x39 ^ 0x3C] = LoginState.ACCEPTED;
            ENUM$VALUES = enum$VALUES;
        }
    }
}
