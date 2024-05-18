package HORIZON-6-0-SKIDPROTECTION;

import com.mojang.authlib.minecraft.MinecraftSessionService;
import java.security.PublicKey;
import io.netty.util.concurrent.Future;
import javax.crypto.SecretKey;
import io.netty.util.concurrent.GenericFutureListener;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.exceptions.InvalidCredentialsException;
import com.mojang.authlib.exceptions.AuthenticationUnavailableException;
import java.math.BigInteger;
import org.apache.logging.log4j.LogManager;
import com.mojang.authlib.GameProfile;
import org.apache.logging.log4j.Logger;

public class NetHandlerLoginClient implements INetHandlerLoginClient
{
    private static final Logger HorizonCode_Horizon_È;
    private final Minecraft Â;
    private final GuiScreen Ý;
    private final NetworkManager Ø­áŒŠá;
    private GameProfile Âµá€;
    private static final String Ó = "CL_00000876";
    
    static {
        HorizonCode_Horizon_È = LogManager.getLogger();
    }
    
    public NetHandlerLoginClient(final NetworkManager p_i45059_1_, final Minecraft mcIn, final GuiScreen p_i45059_3_) {
        this.Ø­áŒŠá = p_i45059_1_;
        this.Â = mcIn;
        this.Ý = p_i45059_3_;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final S01PacketEncryptionRequest packetIn) {
        final SecretKey var2 = CryptManager.HorizonCode_Horizon_È();
        final String var3 = packetIn.HorizonCode_Horizon_È();
        final PublicKey var4 = packetIn.Â();
        final String var5 = new BigInteger(CryptManager.HorizonCode_Horizon_È(var3, var4, var2)).toString(16);
        try {
            this.HorizonCode_Horizon_È().joinServer(this.Â.Õ().Âµá€(), this.Â.Õ().Ø­áŒŠá(), var5);
        }
        catch (AuthenticationUnavailableException var7) {
            this.Ø­áŒŠá.HorizonCode_Horizon_È(new ChatComponentTranslation("disconnect.loginFailedInfo", new Object[] { new ChatComponentTranslation("disconnect.loginFailedInfo.serversUnavailable", new Object[0]) }));
            return;
        }
        catch (InvalidCredentialsException var8) {
            this.Ø­áŒŠá.HorizonCode_Horizon_È(new ChatComponentTranslation("disconnect.loginFailedInfo", new Object[] { new ChatComponentTranslation("disconnect.loginFailedInfo.invalidSession", new Object[0]) }));
            return;
        }
        catch (AuthenticationException var6) {
            this.Ø­áŒŠá.HorizonCode_Horizon_È(new ChatComponentTranslation("disconnect.loginFailedInfo", new Object[] { var6.getMessage() }));
            return;
        }
        this.Ø­áŒŠá.HorizonCode_Horizon_È(new C01PacketEncryptionResponse(var2, var4, packetIn.Ý()), (GenericFutureListener)new GenericFutureListener() {
            private static final String Â = "CL_00000877";
            
            public void operationComplete(final Future p_operationComplete_1_) {
                NetHandlerLoginClient.this.Ø­áŒŠá.HorizonCode_Horizon_È(var2);
            }
        }, new GenericFutureListener[0]);
    }
    
    private MinecraftSessionService HorizonCode_Horizon_È() {
        return this.Â.Ï­à();
    }
    
    @Override
    public void HorizonCode_Horizon_È(final S02PacketLoginSuccess packetIn) {
        this.Âµá€ = packetIn.HorizonCode_Horizon_È();
        this.Ø­áŒŠá.HorizonCode_Horizon_È(EnumConnectionState.Â);
        this.Ø­áŒŠá.HorizonCode_Horizon_È(new NetHandlerPlayClient(this.Â, this.Ý, this.Ø­áŒŠá, this.Âµá€));
    }
    
    @Override
    public void HorizonCode_Horizon_È(final IChatComponent reason) {
        this.Â.HorizonCode_Horizon_È(new GuiDisconnected(this.Ý, "connect.failed", reason));
    }
    
    @Override
    public void HorizonCode_Horizon_È(final S00PacketDisconnect packetIn) {
        this.Ø­áŒŠá.HorizonCode_Horizon_È(packetIn.HorizonCode_Horizon_È());
    }
    
    @Override
    public void HorizonCode_Horizon_È(final S03PacketEnableCompression p_180464_1_) {
        if (!this.Ø­áŒŠá.Ý()) {
            this.Ø­áŒŠá.HorizonCode_Horizon_È(p_180464_1_.HorizonCode_Horizon_È());
        }
    }
}
