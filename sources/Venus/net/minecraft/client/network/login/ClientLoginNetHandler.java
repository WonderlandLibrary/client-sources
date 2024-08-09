/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.network.login;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.exceptions.AuthenticationUnavailableException;
import com.mojang.authlib.exceptions.InsufficientPrivilegesException;
import com.mojang.authlib.exceptions.InvalidCredentialsException;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import io.netty.util.concurrent.Future;
import java.math.BigInteger;
import java.security.PublicKey;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.screen.DisconnectedScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.network.login.IClientLoginNetHandler;
import net.minecraft.client.network.play.ClientPlayNetHandler;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.ProtocolType;
import net.minecraft.network.login.client.CCustomPayloadLoginPacket;
import net.minecraft.network.login.client.CEncryptionResponsePacket;
import net.minecraft.network.login.server.SCustomPayloadLoginPacket;
import net.minecraft.network.login.server.SDisconnectLoginPacket;
import net.minecraft.network.login.server.SEnableCompressionPacket;
import net.minecraft.network.login.server.SEncryptionRequestPacket;
import net.minecraft.network.login.server.SLoginSuccessPacket;
import net.minecraft.realms.DisconnectedRealmsScreen;
import net.minecraft.realms.RealmsScreen;
import net.minecraft.util.CryptException;
import net.minecraft.util.CryptManager;
import net.minecraft.util.HTTPUtil;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ClientLoginNetHandler
implements IClientLoginNetHandler {
    private static final Logger LOGGER = LogManager.getLogger();
    private final Minecraft mc;
    @Nullable
    private final Screen previousGuiScreen;
    private final Consumer<ITextComponent> statusMessageConsumer;
    private final NetworkManager networkManager;
    private GameProfile gameProfile;

    public ClientLoginNetHandler(NetworkManager networkManager, Minecraft minecraft, @Nullable Screen screen, Consumer<ITextComponent> consumer) {
        this.networkManager = networkManager;
        this.mc = minecraft;
        this.previousGuiScreen = screen;
        this.statusMessageConsumer = consumer;
    }

    @Override
    public void handleEncryptionRequest(SEncryptionRequestPacket sEncryptionRequestPacket) {
        CEncryptionResponsePacket cEncryptionResponsePacket;
        Cipher cipher;
        Cipher cipher2;
        String string;
        try {
            SecretKey secretKey = CryptManager.createNewSharedKey();
            PublicKey publicKey = sEncryptionRequestPacket.getPublicKey();
            string = new BigInteger(CryptManager.getServerIdHash(sEncryptionRequestPacket.getServerId(), publicKey, secretKey)).toString(16);
            cipher2 = CryptManager.createNetCipherInstance(2, secretKey);
            cipher = CryptManager.createNetCipherInstance(1, secretKey);
            cEncryptionResponsePacket = new CEncryptionResponsePacket(secretKey, publicKey, sEncryptionRequestPacket.getVerifyToken());
        } catch (CryptException cryptException) {
            throw new IllegalStateException("Protocol error", cryptException);
        }
        this.statusMessageConsumer.accept(new TranslationTextComponent("connect.authorizing"));
        HTTPUtil.DOWNLOADER_EXECUTOR.submit(() -> this.lambda$handleEncryptionRequest$1(string, cEncryptionResponsePacket, cipher2, cipher));
    }

    @Nullable
    private ITextComponent joinServer(String string) {
        try {
            this.getSessionService().joinServer(this.mc.getSession().getProfile(), this.mc.getSession().getToken(), string);
            return null;
        } catch (AuthenticationUnavailableException authenticationUnavailableException) {
            return new TranslationTextComponent("disconnect.loginFailedInfo", new TranslationTextComponent("disconnect.loginFailedInfo.serversUnavailable"));
        } catch (InvalidCredentialsException invalidCredentialsException) {
            return new TranslationTextComponent("disconnect.loginFailedInfo", new TranslationTextComponent("disconnect.loginFailedInfo.invalidSession"));
        } catch (InsufficientPrivilegesException insufficientPrivilegesException) {
            return new TranslationTextComponent("disconnect.loginFailedInfo", new TranslationTextComponent("disconnect.loginFailedInfo.insufficientPrivileges"));
        } catch (AuthenticationException authenticationException) {
            return new TranslationTextComponent("disconnect.loginFailedInfo", authenticationException.getMessage());
        }
    }

    private MinecraftSessionService getSessionService() {
        return this.mc.getSessionService();
    }

    @Override
    public void handleLoginSuccess(SLoginSuccessPacket sLoginSuccessPacket) {
        this.statusMessageConsumer.accept(new TranslationTextComponent("connect.joining"));
        this.gameProfile = sLoginSuccessPacket.getProfile();
        this.networkManager.setConnectionState(ProtocolType.PLAY);
        this.networkManager.setNetHandler(new ClientPlayNetHandler(this.mc, this.previousGuiScreen, this.networkManager, this.gameProfile));
    }

    @Override
    public void onDisconnect(ITextComponent iTextComponent) {
        if (this.previousGuiScreen != null && this.previousGuiScreen instanceof RealmsScreen) {
            this.mc.displayGuiScreen(new DisconnectedRealmsScreen(this.previousGuiScreen, DialogTexts.CONNECTION_FAILED, iTextComponent));
        } else {
            this.mc.displayGuiScreen(new DisconnectedScreen(this.previousGuiScreen, DialogTexts.CONNECTION_FAILED, iTextComponent));
        }
    }

    @Override
    public NetworkManager getNetworkManager() {
        return this.networkManager;
    }

    @Override
    public void handleDisconnect(SDisconnectLoginPacket sDisconnectLoginPacket) {
        this.networkManager.closeChannel(sDisconnectLoginPacket.getReason());
    }

    @Override
    public void handleEnableCompression(SEnableCompressionPacket sEnableCompressionPacket) {
        if (!this.networkManager.isLocalChannel()) {
            this.networkManager.setCompressionThreshold(sEnableCompressionPacket.getCompressionThreshold());
        }
    }

    @Override
    public void handleCustomPayloadLogin(SCustomPayloadLoginPacket sCustomPayloadLoginPacket) {
        this.statusMessageConsumer.accept(new TranslationTextComponent("connect.negotiating"));
        this.networkManager.sendPacket(new CCustomPayloadLoginPacket(sCustomPayloadLoginPacket.getTransaction(), null));
    }

    private void lambda$handleEncryptionRequest$1(String string, CEncryptionResponsePacket cEncryptionResponsePacket, Cipher cipher, Cipher cipher2) {
        ITextComponent iTextComponent = this.joinServer(string);
        if (iTextComponent != null) {
            if (this.mc.getCurrentServerData() == null || !this.mc.getCurrentServerData().isOnLAN()) {
                this.networkManager.closeChannel(iTextComponent);
                return;
            }
            LOGGER.warn(iTextComponent.getString());
        }
        this.statusMessageConsumer.accept(new TranslationTextComponent("connect.encrypting"));
        this.networkManager.sendPacket(cEncryptionResponsePacket, arg_0 -> this.lambda$handleEncryptionRequest$0(cipher, cipher2, arg_0));
    }

    private void lambda$handleEncryptionRequest$0(Cipher cipher, Cipher cipher2, Future future) throws Exception {
        this.networkManager.func_244777_a(cipher, cipher2);
    }
}

