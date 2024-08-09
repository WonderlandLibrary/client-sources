/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network.login;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.exceptions.AuthenticationUnavailableException;
import io.netty.util.concurrent.Future;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.security.PrivateKey;
import java.util.Arrays;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import javax.annotation.Nullable;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.login.IServerLoginNetHandler;
import net.minecraft.network.login.client.CCustomPayloadLoginPacket;
import net.minecraft.network.login.client.CEncryptionResponsePacket;
import net.minecraft.network.login.client.CLoginStartPacket;
import net.minecraft.network.login.server.SDisconnectLoginPacket;
import net.minecraft.network.login.server.SEnableCompressionPacket;
import net.minecraft.network.login.server.SEncryptionRequestPacket;
import net.minecraft.network.login.server.SLoginSuccessPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.CryptException;
import net.minecraft.util.CryptManager;
import net.minecraft.util.DefaultUncaughtExceptionHandler;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ServerLoginNetHandler
implements IServerLoginNetHandler {
    private static final AtomicInteger AUTHENTICATOR_THREAD_ID = new AtomicInteger(0);
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Random RANDOM = new Random();
    private final byte[] verifyToken = new byte[4];
    private final MinecraftServer server;
    public final NetworkManager networkManager;
    private State currentLoginState = State.HELLO;
    private int connectionTimer;
    private GameProfile loginGameProfile;
    private final String serverId = "";
    private SecretKey secretKey;
    private ServerPlayerEntity player;

    public ServerLoginNetHandler(MinecraftServer minecraftServer, NetworkManager networkManager) {
        this.server = minecraftServer;
        this.networkManager = networkManager;
        RANDOM.nextBytes(this.verifyToken);
    }

    public void tick() {
        ServerPlayerEntity serverPlayerEntity;
        if (this.currentLoginState == State.READY_TO_ACCEPT) {
            this.tryAcceptPlayer();
        } else if (this.currentLoginState == State.DELAY_ACCEPT && (serverPlayerEntity = this.server.getPlayerList().getPlayerByUUID(this.loginGameProfile.getId())) == null) {
            this.currentLoginState = State.READY_TO_ACCEPT;
            this.server.getPlayerList().initializeConnectionToPlayer(this.networkManager, this.player);
            this.player = null;
        }
        if (this.connectionTimer++ == 600) {
            this.disconnect(new TranslationTextComponent("multiplayer.disconnect.slow_login"));
        }
    }

    @Override
    public NetworkManager getNetworkManager() {
        return this.networkManager;
    }

    public void disconnect(ITextComponent iTextComponent) {
        try {
            LOGGER.info("Disconnecting {}: {}", (Object)this.getConnectionInfo(), (Object)iTextComponent.getString());
            this.networkManager.sendPacket(new SDisconnectLoginPacket(iTextComponent));
            this.networkManager.closeChannel(iTextComponent);
        } catch (Exception exception) {
            LOGGER.error("Error whilst disconnecting player", (Throwable)exception);
        }
    }

    public void tryAcceptPlayer() {
        ITextComponent iTextComponent;
        if (!this.loginGameProfile.isComplete()) {
            this.loginGameProfile = this.getOfflineProfile(this.loginGameProfile);
        }
        if ((iTextComponent = this.server.getPlayerList().canPlayerLogin(this.networkManager.getRemoteAddress(), this.loginGameProfile)) != null) {
            this.disconnect(iTextComponent);
        } else {
            this.currentLoginState = State.ACCEPTED;
            if (this.server.getNetworkCompressionThreshold() >= 0 && !this.networkManager.isLocalChannel()) {
                this.networkManager.sendPacket(new SEnableCompressionPacket(this.server.getNetworkCompressionThreshold()), this::lambda$tryAcceptPlayer$0);
            }
            this.networkManager.sendPacket(new SLoginSuccessPacket(this.loginGameProfile));
            ServerPlayerEntity serverPlayerEntity = this.server.getPlayerList().getPlayerByUUID(this.loginGameProfile.getId());
            if (serverPlayerEntity != null) {
                this.currentLoginState = State.DELAY_ACCEPT;
                this.player = this.server.getPlayerList().createPlayerForUser(this.loginGameProfile);
            } else {
                this.server.getPlayerList().initializeConnectionToPlayer(this.networkManager, this.server.getPlayerList().createPlayerForUser(this.loginGameProfile));
            }
        }
    }

    @Override
    public void onDisconnect(ITextComponent iTextComponent) {
        LOGGER.info("{} lost connection: {}", (Object)this.getConnectionInfo(), (Object)iTextComponent.getString());
    }

    public String getConnectionInfo() {
        return this.loginGameProfile != null ? this.loginGameProfile + " (" + this.networkManager.getRemoteAddress() + ")" : String.valueOf(this.networkManager.getRemoteAddress());
    }

    @Override
    public void processLoginStart(CLoginStartPacket cLoginStartPacket) {
        Validate.validState(this.currentLoginState == State.HELLO, "Unexpected hello packet", new Object[0]);
        this.loginGameProfile = cLoginStartPacket.getProfile();
        if (this.server.isServerInOnlineMode() && !this.networkManager.isLocalChannel()) {
            this.currentLoginState = State.KEY;
            this.networkManager.sendPacket(new SEncryptionRequestPacket("", this.server.getKeyPair().getPublic().getEncoded(), this.verifyToken));
        } else {
            this.currentLoginState = State.READY_TO_ACCEPT;
        }
    }

    @Override
    public void processEncryptionResponse(CEncryptionResponsePacket cEncryptionResponsePacket) {
        String string;
        Object object;
        Validate.validState(this.currentLoginState == State.KEY, "Unexpected key packet", new Object[0]);
        PrivateKey privateKey = this.server.getKeyPair().getPrivate();
        try {
            if (!Arrays.equals(this.verifyToken, cEncryptionResponsePacket.getVerifyToken(privateKey))) {
                throw new IllegalStateException("Protocol error");
            }
            this.secretKey = cEncryptionResponsePacket.getSecretKey(privateKey);
            object = CryptManager.createNetCipherInstance(2, this.secretKey);
            Cipher cipher = CryptManager.createNetCipherInstance(1, this.secretKey);
            string = new BigInteger(CryptManager.getServerIdHash("", this.server.getKeyPair().getPublic(), this.secretKey)).toString(16);
            this.currentLoginState = State.AUTHENTICATING;
            this.networkManager.func_244777_a((Cipher)object, cipher);
        } catch (CryptException cryptException) {
            throw new IllegalStateException("Protocol error", cryptException);
        }
        object = new Thread(this, "User Authenticator #" + AUTHENTICATOR_THREAD_ID.incrementAndGet(), string){
            final String val$s;
            final ServerLoginNetHandler this$0;
            {
                this.this$0 = serverLoginNetHandler;
                this.val$s = string2;
                super(string);
            }

            @Override
            public void run() {
                GameProfile gameProfile = this.this$0.loginGameProfile;
                try {
                    this.this$0.loginGameProfile = this.this$0.server.getMinecraftSessionService().hasJoinedServer(new GameProfile(null, gameProfile.getName()), this.val$s, this.getAddress());
                    if (this.this$0.loginGameProfile != null) {
                        LOGGER.info("UUID of player {} is {}", (Object)this.this$0.loginGameProfile.getName(), (Object)this.this$0.loginGameProfile.getId());
                        this.this$0.currentLoginState = State.READY_TO_ACCEPT;
                    } else if (this.this$0.server.isSinglePlayer()) {
                        LOGGER.warn("Failed to verify username but will let them in anyway!");
                        this.this$0.loginGameProfile = this.this$0.getOfflineProfile(gameProfile);
                        this.this$0.currentLoginState = State.READY_TO_ACCEPT;
                    } else {
                        this.this$0.disconnect(new TranslationTextComponent("multiplayer.disconnect.unverified_username"));
                        LOGGER.error("Username '{}' tried to join with an invalid session", (Object)gameProfile.getName());
                    }
                } catch (AuthenticationUnavailableException authenticationUnavailableException) {
                    if (this.this$0.server.isSinglePlayer()) {
                        LOGGER.warn("Authentication servers are down but will let them in anyway!");
                        this.this$0.loginGameProfile = this.this$0.getOfflineProfile(gameProfile);
                        this.this$0.currentLoginState = State.READY_TO_ACCEPT;
                    }
                    this.this$0.disconnect(new TranslationTextComponent("multiplayer.disconnect.authservers_down"));
                    LOGGER.error("Couldn't verify username because servers are unavailable");
                }
            }

            @Nullable
            private InetAddress getAddress() {
                SocketAddress socketAddress = this.this$0.networkManager.getRemoteAddress();
                return this.this$0.server.getPreventProxyConnections() && socketAddress instanceof InetSocketAddress ? ((InetSocketAddress)socketAddress).getAddress() : null;
            }
        };
        ((Thread)object).setUncaughtExceptionHandler(new DefaultUncaughtExceptionHandler(LOGGER));
        ((Thread)object).start();
    }

    @Override
    public void processCustomPayloadLogin(CCustomPayloadLoginPacket cCustomPayloadLoginPacket) {
        this.disconnect(new TranslationTextComponent("multiplayer.disconnect.unexpected_query_response"));
    }

    protected GameProfile getOfflineProfile(GameProfile gameProfile) {
        UUID uUID = PlayerEntity.getOfflineUUID(gameProfile.getName());
        return new GameProfile(uUID, gameProfile.getName());
    }

    private void lambda$tryAcceptPlayer$0(Future future) throws Exception {
        this.networkManager.setCompressionThreshold(this.server.getNetworkCompressionThreshold());
    }

    static enum State {
        HELLO,
        KEY,
        AUTHENTICATING,
        NEGOTIATING,
        READY_TO_ACCEPT,
        DELAY_ACCEPT,
        ACCEPTED;

    }
}

