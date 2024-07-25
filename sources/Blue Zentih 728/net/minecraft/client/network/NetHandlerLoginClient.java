package net.minecraft.client.network;

import club.bluezenith.core.data.alt.info.AccountType;
import club.bluezenith.events.impl.DisconnectEvent;
import club.bluezenith.events.impl.ServerConnectEvent;
import club.bluezenith.ui.alt.rewrite.actions.GuiAccountInfo;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.exceptions.AuthenticationUnavailableException;
import com.mojang.authlib.exceptions.InvalidCredentialsException;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiDisconnected;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.network.EnumConnectionState;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.login.INetHandlerLoginClient;
import net.minecraft.network.login.client.C01PacketEncryptionResponse;
import net.minecraft.network.login.server.S00PacketDisconnect;
import net.minecraft.network.login.server.S01PacketEncryptionRequest;
import net.minecraft.network.login.server.S02PacketLoginSuccess;
import net.minecraft.network.login.server.S03PacketEnableCompression;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.CryptManager;
import net.minecraft.util.IChatComponent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.crypto.SecretKey;
import java.math.BigInteger;
import java.security.PublicKey;

import static club.bluezenith.BlueZenith.getBlueZenith;

public class NetHandlerLoginClient implements INetHandlerLoginClient
{
    private static final Logger logger = LogManager.getLogger();
    private final Minecraft mc;
    private final GuiScreen previousGuiScreen;
    private final NetworkManager networkManager;
    private GameProfile gameProfile;

    public NetHandlerLoginClient(NetworkManager p_i45059_1_, Minecraft mcIn, GuiScreen p_i45059_3_)
    {
        this.networkManager = p_i45059_1_;
        this.mc = mcIn;
        this.previousGuiScreen = p_i45059_3_;
    }

    public void handleEncryptionRequest(S01PacketEncryptionRequest packetIn)
    {
        final SecretKey secretkey = CryptManager.createNewSharedKey();
        String s = packetIn.getServerId();
        PublicKey publickey = packetIn.getPublicKey();
        String s1 = (new BigInteger(CryptManager.getServerIdHash(s, publickey, secretkey))).toString(16);

        if (this.mc.getCurrentServerData() != null && this.mc.getCurrentServerData().func_181041_d())
        {
            try
            {
                this.getSessionService().joinServer(this.mc.getSession().getProfile(), this.mc.getSession().getToken(), s1);
            }
            catch (AuthenticationException var10)
            {
                logger.warn("Couldn\'t connect to auth servers but will continue to join LAN");
            }
        }
        else
        {
            try
            {
                this.getSessionService().joinServer(this.mc.getSession().getProfile(), this.mc.getSession().getToken(), s1);
            }
            catch (AuthenticationUnavailableException var7)
            {
                this.networkManager.closeChannel(new ChatComponentTranslation("disconnect.loginFailedInfo", new Object[] {new ChatComponentTranslation("disconnect.loginFailedInfo.serversUnavailable", new Object[0])}));
                return;
            }
            catch (InvalidCredentialsException var8)
            {
                if(getBlueZenith().getAccountRepository().getCurrentAccount().getAccountType().equals(AccountType.MICROSOFT)) {
                    if (previousGuiScreen == null || previousGuiScreen.getClass() != GuiAccountInfo.class)
                        getBlueZenith().getNotificationPublisher().postWarning("Account Manager", "This error often occurs if your microsoft session token has expired.\nRefresh it in the alt manager.\n(Select account -> Info/Details -> Refresh)", 5000);
                } else if(previousGuiScreen == null || previousGuiScreen.getClass() != GuiAccountInfo.class) {
                    getBlueZenith().getNotificationPublisher().postError("Ban checker", "Couldn't check microsoft account. Please re-add it.\nIf this is a mojang account, it's been forced to migrate.", 3500);
                }
                this.networkManager.closeChannel(new ChatComponentTranslation("disconnect.loginFailedInfo", new Object[] {new ChatComponentTranslation("disconnect.loginFailedInfo.invalidSession", new Object[0])}));
                return;
            }
            catch (AuthenticationException authenticationexception)
            {
                this.networkManager.closeChannel(new ChatComponentTranslation("disconnect.loginFailedInfo", new Object[] {authenticationexception.getMessage()}));
                return;
            }
        }

        this.networkManager.sendPacket(new C01PacketEncryptionResponse(secretkey, publickey, packetIn.getVerifyToken()), new GenericFutureListener < Future <? super Void >> ()
        {
            public void operationComplete(Future <? super Void > p_operationComplete_1_) throws Exception
            {
                NetHandlerLoginClient.this.networkManager.enableEncryption(secretkey);
            }
        }, new GenericFutureListener[0]);
    }

    private MinecraftSessionService getSessionService()
    {
        return this.mc.getSessionService();
    }

    public void handleLoginSuccess(S02PacketLoginSuccess packetIn)
    {
        if(previousGuiScreen == null || previousGuiScreen.getClass() != GuiAccountInfo.class) {
            this.gameProfile = packetIn.getProfile();
            this.networkManager.setConnectionState(EnumConnectionState.PLAY);
            this.networkManager.setNetHandler(new NetHandlerPlayClient(this.mc, this.previousGuiScreen, this.networkManager, this.gameProfile));
            getBlueZenith().postEvent(new ServerConnectEvent(getBlueZenith().getCurrentServerIP()));
        }
    }

    /**
     * Invoked when disconnecting, the parameter is a ChatComponent describing the reason for termination
     */
    public void onDisconnect(IChatComponent reason)
    {
        final DisconnectEvent event = new DisconnectEvent(reason);
        if(getBlueZenith() != null)
            getBlueZenith().postEvent(event);

        if(previousGuiScreen == null || previousGuiScreen.getClass() != GuiAccountInfo.class)
            this.mc.displayGuiScreen(new GuiDisconnected(this.previousGuiScreen, "connect.failed", event.reason));
    }

    public void handleDisconnect(S00PacketDisconnect packetIn)
    {
        this.networkManager.closeChannel(packetIn.func_149603_c());
    }

    protected NetworkManager getNetworkManager() {
        return this.networkManager;
    }

    public void handleEnableCompression(S03PacketEnableCompression packetIn)
    {
        if (!this.networkManager.isLocalChannel())
        {
            this.networkManager.setCompressionTreshold(packetIn.getCompressionTreshold());
        }
    }
}
