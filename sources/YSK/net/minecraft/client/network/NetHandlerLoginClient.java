package net.minecraft.client.network;

import net.minecraft.network.login.*;
import com.mojang.authlib.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import java.math.*;
import net.minecraft.util.*;
import com.mojang.authlib.exceptions.*;
import net.minecraft.network.login.client.*;
import io.netty.util.concurrent.*;
import javax.crypto.*;
import java.security.*;
import org.apache.logging.log4j.*;
import com.mojang.authlib.minecraft.*;
import net.minecraft.network.login.server.*;
import net.minecraft.network.*;

public class NetHandlerLoginClient implements INetHandlerLoginClient
{
    private final NetworkManager networkManager;
    private static final Logger logger;
    private GameProfile gameProfile;
    private final Minecraft mc;
    private static final String[] I;
    private final GuiScreen previousGuiScreen;
    
    public NetHandlerLoginClient(final NetworkManager networkManager, final Minecraft mc, final GuiScreen previousGuiScreen) {
        this.networkManager = networkManager;
        this.mc = mc;
        this.previousGuiScreen = previousGuiScreen;
    }
    
    @Override
    public void onDisconnect(final IChatComponent chatComponent) {
        this.mc.displayGuiScreen(new GuiDisconnected(this.previousGuiScreen, NetHandlerLoginClient.I[0xB7 ^ 0xB1], chatComponent));
    }
    
    private static void I() {
        (I = new String[0x78 ^ 0x7F])["".length()] = I("\u0011:#-=<r\"a:=;8$:&u\".y3 \")y!0$7< &v#,&u!(5>u5.7&<84<r!9a3=<8a\u0015\u0013\u001b", "RUVAY");
        NetHandlerLoginClient.I[" ".length()] = I("\n\u0001>\u0019+\u0000\u0006(\u00190@\u0004\"\u001d-\u0000.,\u0013(\u000b\f\u0004\u0014\"\u0001", "nhMzD");
        NetHandlerLoginClient.I["  ".length()] = I("\u0014.!-?\u001e)7-$^+=)9\u001e\u00013'<\u0015#\u001b 6\u001fi!+\"\u0006\" =\u0005\u001e&$/9\u001c&0\"5", "pGRNP");
        NetHandlerLoginClient.I["   ".length()] = I("\u0005:2\u001a\u001c\u000f=$\u001a\u0007O?.\u001e\u001a\u000f\u0015 \u0010\u001f\u00047\b\u0017\u0015\u000e", "aSAys");
        NetHandlerLoginClient.I[0x3D ^ 0x39] = I("\u00010\n\u000e\u0003\u000b7\u001c\u000e\u0018K5\u0016\n\u0005\u000b\u001f\u0018\u0004\u0000\u0000=0\u0003\n\nw\u0010\u0003\u001a\u00045\u0010\t?\u0000*\n\u0004\u0003\u000b", "eYyml");
        NetHandlerLoginClient.I[0x70 ^ 0x75] = I("#0\u001e\u00018)7\b\u0001#i5\u0002\u0005>)\u001f\f\u000b;\"=$\f1(", "GYmbW");
        NetHandlerLoginClient.I[0x96 ^ 0x90] = I("\u000f* \u0019\r\u000f1`\u0011\t\u0005)+\u0013", "lENwh");
    }
    
    @Override
    public void handleEncryptionRequest(final S01PacketEncryptionRequest s01PacketEncryptionRequest) {
        final SecretKey newSharedKey = CryptManager.createNewSharedKey();
        final String serverId = s01PacketEncryptionRequest.getServerId();
        final PublicKey publicKey = s01PacketEncryptionRequest.getPublicKey();
        final String string = new BigInteger(CryptManager.getServerIdHash(serverId, publicKey, newSharedKey)).toString(0xA5 ^ 0xB5);
        Label_0360: {
            if (this.mc.getCurrentServerData() != null && this.mc.getCurrentServerData().func_181041_d()) {
                try {
                    this.getSessionService().joinServer(this.mc.getSession().getProfile(), this.mc.getSession().getToken(), string);
                    "".length();
                    if (4 < 3) {
                        throw null;
                    }
                    break Label_0360;
                }
                catch (AuthenticationException ex2) {
                    NetHandlerLoginClient.logger.warn(NetHandlerLoginClient.I["".length()]);
                    "".length();
                    if (3 < 0) {
                        throw null;
                    }
                    break Label_0360;
                }
            }
            try {
                this.getSessionService().joinServer(this.mc.getSession().getProfile(), this.mc.getSession().getToken(), string);
                "".length();
                if (0 >= 4) {
                    throw null;
                }
            }
            catch (AuthenticationUnavailableException ex3) {
                final NetworkManager networkManager = this.networkManager;
                final String s = NetHandlerLoginClient.I[" ".length()];
                final Object[] array = new Object[" ".length()];
                array["".length()] = new ChatComponentTranslation(NetHandlerLoginClient.I["  ".length()], new Object["".length()]);
                networkManager.closeChannel(new ChatComponentTranslation(s, array));
                return;
            }
            catch (InvalidCredentialsException ex4) {
                final NetworkManager networkManager2 = this.networkManager;
                final String s2 = NetHandlerLoginClient.I["   ".length()];
                final Object[] array2 = new Object[" ".length()];
                array2["".length()] = new ChatComponentTranslation(NetHandlerLoginClient.I[0x60 ^ 0x64], new Object["".length()]);
                networkManager2.closeChannel(new ChatComponentTranslation(s2, array2));
                return;
            }
            catch (AuthenticationException ex) {
                final NetworkManager networkManager3 = this.networkManager;
                final String s3 = NetHandlerLoginClient.I[0xC ^ 0x9];
                final Object[] array3 = new Object[" ".length()];
                array3["".length()] = ex.getMessage();
                networkManager3.closeChannel(new ChatComponentTranslation(s3, array3));
                return;
            }
        }
        this.networkManager.sendPacket(new C01PacketEncryptionResponse(newSharedKey, publicKey, s01PacketEncryptionRequest.getVerifyToken()), (GenericFutureListener<? extends Future<? super Void>>)new GenericFutureListener<Future<? super Void>>(this, newSharedKey) {
            private final SecretKey val$secretkey;
            final NetHandlerLoginClient this$0;
            
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
                    if (2 <= -1) {
                        throw null;
                    }
                }
                return sb.toString();
            }
            
            public void operationComplete(final Future<? super Void> future) throws Exception {
                NetHandlerLoginClient.access$0(this.this$0).enableEncryption(this.val$secretkey);
            }
        }, (GenericFutureListener<? extends Future<? super Void>>[])new GenericFutureListener["".length()]);
    }
    
    static NetworkManager access$0(final NetHandlerLoginClient netHandlerLoginClient) {
        return netHandlerLoginClient.networkManager;
    }
    
    static {
        I();
        logger = LogManager.getLogger();
    }
    
    private MinecraftSessionService getSessionService() {
        return this.mc.getSessionService();
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
            if (3 < 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public void handleEnableCompression(final S03PacketEnableCompression s03PacketEnableCompression) {
        if (!this.networkManager.isLocalChannel()) {
            this.networkManager.setCompressionTreshold(s03PacketEnableCompression.getCompressionTreshold());
        }
    }
    
    @Override
    public void handleDisconnect(final S00PacketDisconnect s00PacketDisconnect) {
        this.networkManager.closeChannel(s00PacketDisconnect.func_149603_c());
    }
    
    @Override
    public void handleLoginSuccess(final S02PacketLoginSuccess s02PacketLoginSuccess) {
        this.gameProfile = s02PacketLoginSuccess.getProfile();
        this.networkManager.setConnectionState(EnumConnectionState.PLAY);
        this.networkManager.setNetHandler(new NetHandlerPlayClient(this.mc, this.previousGuiScreen, this.networkManager, this.gameProfile));
    }
}
