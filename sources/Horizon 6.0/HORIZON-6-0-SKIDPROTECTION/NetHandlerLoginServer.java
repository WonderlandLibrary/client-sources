package HORIZON-6-0-SKIDPROTECTION;

import com.google.common.base.Charsets;
import java.security.PrivateKey;
import com.mojang.authlib.exceptions.AuthenticationUnavailableException;
import java.util.UUID;
import java.math.BigInteger;
import java.util.Arrays;
import org.apache.commons.lang3.Validate;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.concurrent.Future;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import org.apache.logging.log4j.LogManager;
import javax.crypto.SecretKey;
import com.mojang.authlib.GameProfile;
import java.util.Random;
import org.apache.logging.log4j.Logger;
import java.util.concurrent.atomic.AtomicInteger;

public class NetHandlerLoginServer implements INetHandlerLoginServer, IUpdatePlayerListBox
{
    private static final AtomicInteger Â;
    private static final Logger Ý;
    private static final Random Ø­áŒŠá;
    private final byte[] Âµá€;
    private final MinecraftServer Ó;
    public final NetworkManager HorizonCode_Horizon_È;
    private HorizonCode_Horizon_È à;
    private int Ø;
    private GameProfile áŒŠÆ;
    private String áˆºÑ¢Õ;
    private SecretKey ÂµÈ;
    private static final String á = "CL_00001458";
    
    static {
        Â = new AtomicInteger(0);
        Ý = LogManager.getLogger();
        Ø­áŒŠá = new Random();
    }
    
    public NetHandlerLoginServer(final MinecraftServer p_i45298_1_, final NetworkManager p_i45298_2_) {
        this.Âµá€ = new byte[4];
        this.à = NetHandlerLoginServer.HorizonCode_Horizon_È.HorizonCode_Horizon_È;
        this.áˆºÑ¢Õ = "";
        this.Ó = p_i45298_1_;
        this.HorizonCode_Horizon_È = p_i45298_2_;
        NetHandlerLoginServer.Ø­áŒŠá.nextBytes(this.Âµá€);
    }
    
    @Override
    public void HorizonCode_Horizon_È() {
        if (this.à == NetHandlerLoginServer.HorizonCode_Horizon_È.Ø­áŒŠá) {
            this.Â();
        }
        if (this.Ø++ == 600) {
            this.HorizonCode_Horizon_È("Took too long to log in");
        }
    }
    
    public void HorizonCode_Horizon_È(final String reason) {
        try {
            NetHandlerLoginServer.Ý.info("Disconnecting " + this.Ý() + ": " + reason);
            final ChatComponentText var2 = new ChatComponentText(reason);
            this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(new S00PacketDisconnect(var2));
            this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(var2);
        }
        catch (Exception var3) {
            NetHandlerLoginServer.Ý.error("Error whilst disconnecting player", (Throwable)var3);
        }
    }
    
    public void Â() {
        if (!this.áŒŠÆ.isComplete()) {
            this.áŒŠÆ = this.HorizonCode_Horizon_È(this.áŒŠÆ);
        }
        final String var1 = this.Ó.Œ().HorizonCode_Horizon_È(this.HorizonCode_Horizon_È.Â(), this.áŒŠÆ);
        if (var1 != null) {
            this.HorizonCode_Horizon_È(var1);
        }
        else {
            this.à = NetHandlerLoginServer.HorizonCode_Horizon_È.Âµá€;
            if (this.Ó.ÇªÔ() >= 0 && !this.HorizonCode_Horizon_È.Ý()) {
                this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(new S03PacketEnableCompression(this.Ó.ÇªÔ()), (GenericFutureListener)new ChannelFutureListener() {
                    private static final String Â = "CL_00001459";
                    
                    public void HorizonCode_Horizon_È(final ChannelFuture p_operationComplete_1_) {
                        NetHandlerLoginServer.this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(NetHandlerLoginServer.this.Ó.ÇªÔ());
                    }
                }, new GenericFutureListener[0]);
            }
            this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(new S02PacketLoginSuccess(this.áŒŠÆ));
            this.Ó.Œ().HorizonCode_Horizon_È(this.HorizonCode_Horizon_È, this.Ó.Œ().HorizonCode_Horizon_È(this.áŒŠÆ));
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final IChatComponent reason) {
        NetHandlerLoginServer.Ý.info(String.valueOf(this.Ý()) + " lost connection: " + reason.Ø());
    }
    
    public String Ý() {
        return (this.áŒŠÆ != null) ? (String.valueOf(this.áŒŠÆ.toString()) + " (" + this.HorizonCode_Horizon_È.Â().toString() + ")") : String.valueOf(this.HorizonCode_Horizon_È.Â());
    }
    
    @Override
    public void HorizonCode_Horizon_È(final C00PacketLoginStart packetIn) {
        Validate.validState(this.à == NetHandlerLoginServer.HorizonCode_Horizon_È.HorizonCode_Horizon_È, "Unexpected hello packet", new Object[0]);
        this.áŒŠÆ = packetIn.HorizonCode_Horizon_È();
        if (this.Ó.Ñ¢Â() && !this.HorizonCode_Horizon_È.Ý()) {
            this.à = NetHandlerLoginServer.HorizonCode_Horizon_È.Â;
            this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(new S01PacketEncryptionRequest(this.áˆºÑ¢Õ, this.Ó.à¢().getPublic(), this.Âµá€));
        }
        else {
            this.à = NetHandlerLoginServer.HorizonCode_Horizon_È.Ø­áŒŠá;
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final C01PacketEncryptionResponse packetIn) {
        Validate.validState(this.à == NetHandlerLoginServer.HorizonCode_Horizon_È.Â, "Unexpected key packet", new Object[0]);
        final PrivateKey var2 = this.Ó.à¢().getPrivate();
        if (!Arrays.equals(this.Âµá€, packetIn.Â(var2))) {
            throw new IllegalStateException("Invalid nonce!");
        }
        this.ÂµÈ = packetIn.HorizonCode_Horizon_È(var2);
        this.à = NetHandlerLoginServer.HorizonCode_Horizon_È.Ý;
        this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(this.ÂµÈ);
        new Thread("User Authenticator #" + NetHandlerLoginServer.Â.incrementAndGet()) {
            private static final String Â = "CL_00002268";
            
            @Override
            public void run() {
                final GameProfile var1 = NetHandlerLoginServer.this.áŒŠÆ;
                try {
                    final String var2 = new BigInteger(CryptManager.HorizonCode_Horizon_È(NetHandlerLoginServer.this.áˆºÑ¢Õ, NetHandlerLoginServer.this.Ó.à¢().getPublic(), NetHandlerLoginServer.this.ÂµÈ)).toString(16);
                    NetHandlerLoginServer.HorizonCode_Horizon_È(NetHandlerLoginServer.this, NetHandlerLoginServer.this.Ó.áˆºÇŽØ().hasJoinedServer(new GameProfile((UUID)null, var1.getName()), var2));
                    if (NetHandlerLoginServer.this.áŒŠÆ != null) {
                        NetHandlerLoginServer.Ý.info("UUID of player " + NetHandlerLoginServer.this.áŒŠÆ.getName() + " is " + NetHandlerLoginServer.this.áŒŠÆ.getId());
                        NetHandlerLoginServer.HorizonCode_Horizon_È(NetHandlerLoginServer.this, NetHandlerLoginServer.HorizonCode_Horizon_È.Ø­áŒŠá);
                    }
                    else if (NetHandlerLoginServer.this.Ó.¥à()) {
                        NetHandlerLoginServer.Ý.warn("Failed to verify username but will let them in anyway!");
                        NetHandlerLoginServer.HorizonCode_Horizon_È(NetHandlerLoginServer.this, NetHandlerLoginServer.this.HorizonCode_Horizon_È(var1));
                        NetHandlerLoginServer.HorizonCode_Horizon_È(NetHandlerLoginServer.this, NetHandlerLoginServer.HorizonCode_Horizon_È.Ø­áŒŠá);
                    }
                    else {
                        NetHandlerLoginServer.this.HorizonCode_Horizon_È("Failed to verify username!");
                        NetHandlerLoginServer.Ý.error("Username '" + NetHandlerLoginServer.this.áŒŠÆ.getName() + "' tried to join with an invalid session");
                    }
                }
                catch (AuthenticationUnavailableException var3) {
                    if (NetHandlerLoginServer.this.Ó.¥à()) {
                        NetHandlerLoginServer.Ý.warn("Authentication servers are down but will let them in anyway!");
                        NetHandlerLoginServer.HorizonCode_Horizon_È(NetHandlerLoginServer.this, NetHandlerLoginServer.this.HorizonCode_Horizon_È(var1));
                        NetHandlerLoginServer.HorizonCode_Horizon_È(NetHandlerLoginServer.this, NetHandlerLoginServer.HorizonCode_Horizon_È.Ø­áŒŠá);
                    }
                    else {
                        NetHandlerLoginServer.this.HorizonCode_Horizon_È("Authentication servers are down. Please try again later, sorry!");
                        NetHandlerLoginServer.Ý.error("Couldn't verify username because servers are unavailable");
                    }
                }
            }
        }.start();
    }
    
    protected GameProfile HorizonCode_Horizon_È(final GameProfile original) {
        final UUID var2 = UUID.nameUUIDFromBytes(("OfflinePlayer:" + original.getName()).getBytes(Charsets.UTF_8));
        return new GameProfile(var2, original.getName());
    }
    
    static /* synthetic */ void HorizonCode_Horizon_È(final NetHandlerLoginServer netHandlerLoginServer, final GameProfile áœšæ) {
        netHandlerLoginServer.áŒŠÆ = áœšæ;
    }
    
    static /* synthetic */ void HorizonCode_Horizon_È(final NetHandlerLoginServer netHandlerLoginServer, final HorizonCode_Horizon_È à) {
        netHandlerLoginServer.à = à;
    }
    
    enum HorizonCode_Horizon_È
    {
        HorizonCode_Horizon_È("HELLO", 0, "HELLO", 0), 
        Â("KEY", 1, "KEY", 1), 
        Ý("AUTHENTICATING", 2, "AUTHENTICATING", 2), 
        Ø­áŒŠá("READY_TO_ACCEPT", 3, "READY_TO_ACCEPT", 3), 
        Âµá€("ACCEPTED", 4, "ACCEPTED", 4);
        
        private static final HorizonCode_Horizon_È[] Ó;
        private static final String à = "CL_00001463";
        
        static {
            Ø = new HorizonCode_Horizon_È[] { HorizonCode_Horizon_È.HorizonCode_Horizon_È, HorizonCode_Horizon_È.Â, HorizonCode_Horizon_È.Ý, HorizonCode_Horizon_È.Ø­áŒŠá, HorizonCode_Horizon_È.Âµá€ };
            Ó = new HorizonCode_Horizon_È[] { HorizonCode_Horizon_È.HorizonCode_Horizon_È, HorizonCode_Horizon_È.Â, HorizonCode_Horizon_È.Ý, HorizonCode_Horizon_È.Ø­áŒŠá, HorizonCode_Horizon_È.Âµá€ };
        }
        
        private HorizonCode_Horizon_È(final String s, final int n, final String p_i45297_1_, final int p_i45297_2_) {
        }
    }
}
