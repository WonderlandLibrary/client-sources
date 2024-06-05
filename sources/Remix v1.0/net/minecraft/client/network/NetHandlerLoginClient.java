package net.minecraft.client.network;

import net.minecraft.network.login.*;
import net.minecraft.client.*;
import com.mojang.authlib.*;
import java.math.*;
import net.minecraft.util.*;
import com.mojang.authlib.exceptions.*;
import net.minecraft.network.login.client.*;
import javax.crypto.*;
import io.netty.util.concurrent.*;
import java.security.*;
import com.mojang.authlib.minecraft.*;
import net.minecraft.network.*;
import net.minecraft.client.gui.*;
import net.minecraft.network.login.server.*;
import org.apache.logging.log4j.*;

public class NetHandlerLoginClient implements INetHandlerLoginClient
{
    private static final Logger logger;
    private final Minecraft field_147394_b;
    private final GuiScreen field_147395_c;
    private final NetworkManager field_147393_d;
    private GameProfile field_175091_e;
    
    public NetHandlerLoginClient(final NetworkManager p_i45059_1_, final Minecraft mcIn, final GuiScreen p_i45059_3_) {
        this.field_147393_d = p_i45059_1_;
        this.field_147394_b = mcIn;
        this.field_147395_c = p_i45059_3_;
    }
    
    @Override
    public void handleEncryptionRequest(final S01PacketEncryptionRequest packetIn) {
        final SecretKey var2 = CryptManager.createNewSharedKey();
        final String var3 = packetIn.func_149609_c();
        final PublicKey var4 = packetIn.func_149608_d();
        final String var5 = new BigInteger(CryptManager.getServerIdHash(var3, var4, var2)).toString(16);
        try {
            this.func_147391_c().joinServer(this.field_147394_b.getSession().getProfile(), this.field_147394_b.getSession().getToken(), var5);
        }
        catch (AuthenticationUnavailableException var7) {
            this.field_147393_d.closeChannel(new ChatComponentTranslation("disconnect.loginFailedInfo", new Object[] { new ChatComponentTranslation("disconnect.loginFailedInfo.serversUnavailable", new Object[0]) }));
            return;
        }
        catch (InvalidCredentialsException var8) {
            this.field_147393_d.closeChannel(new ChatComponentTranslation("disconnect.loginFailedInfo", new Object[] { new ChatComponentTranslation("disconnect.loginFailedInfo.invalidSession", new Object[0]) }));
            return;
        }
        catch (AuthenticationException var6) {
            this.field_147393_d.closeChannel(new ChatComponentTranslation("disconnect.loginFailedInfo", new Object[] { var6.getMessage() }));
            return;
        }
        this.field_147393_d.sendPacket(new C01PacketEncryptionResponse(var2, var4, packetIn.func_149607_e()), (GenericFutureListener)new GenericFutureListener() {
            public void operationComplete(final Future p_operationComplete_1_) {
                NetHandlerLoginClient.this.field_147393_d.enableEncryption(var2);
            }
        }, new GenericFutureListener[0]);
    }
    
    private MinecraftSessionService func_147391_c() {
        return this.field_147394_b.getSessionService();
    }
    
    @Override
    public void handleLoginSuccess(final S02PacketLoginSuccess packetIn) {
        this.field_175091_e = packetIn.func_179730_a();
        this.field_147393_d.setConnectionState(EnumConnectionState.PLAY);
        this.field_147393_d.setNetHandler(new NetHandlerPlayClient(this.field_147394_b, this.field_147395_c, this.field_147393_d, this.field_175091_e));
    }
    
    @Override
    public void onDisconnect(final IChatComponent reason) {
        this.field_147394_b.displayGuiScreen(new GuiDisconnected(this.field_147395_c, "connect.failed", reason));
    }
    
    @Override
    public void handleDisconnect(final S00PacketDisconnect packetIn) {
        this.field_147393_d.closeChannel(packetIn.func_149603_c());
    }
    
    @Override
    public void func_180464_a(final S03PacketEnableCompression p_180464_1_) {
        if (!this.field_147393_d.isLocalChannel()) {
            this.field_147393_d.setCompressionTreshold(p_180464_1_.func_179731_a());
        }
    }
    
    static {
        logger = LogManager.getLogger();
    }
}
