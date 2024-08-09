/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.atomic.AtomicInteger;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.client.gui.screen.DisconnectedScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.multiplayer.ServerAddress;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.network.login.ClientLoginNetHandler;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.ProtocolType;
import net.minecraft.network.handshake.client.CHandshakePacket;
import net.minecraft.network.login.client.CLoginStartPacket;
import net.minecraft.util.DefaultUncaughtExceptionHandler;
import net.minecraft.util.Util;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConnectingScreen
extends Screen {
    private static final AtomicInteger CONNECTION_ID = new AtomicInteger(0);
    private static final Logger LOGGER = LogManager.getLogger();
    private NetworkManager networkManager;
    private boolean cancel;
    private final Screen previousGuiScreen;
    private ITextComponent field_209515_s = new TranslationTextComponent("connect.connecting");
    private long field_213000_g = -1L;

    public ConnectingScreen(Screen screen, Minecraft minecraft, ServerData serverData) {
        super(NarratorChatListener.EMPTY);
        this.minecraft = minecraft;
        this.previousGuiScreen = screen;
        ServerAddress serverAddress = ServerAddress.fromString(serverData.serverIP);
        minecraft.unloadWorld();
        minecraft.setServerData(serverData);
        this.connect(serverAddress.getIP(), serverAddress.getPort());
    }

    public ConnectingScreen(Screen screen, Minecraft minecraft, String string, int n) {
        super(NarratorChatListener.EMPTY);
        this.minecraft = minecraft;
        this.previousGuiScreen = screen;
        minecraft.unloadWorld();
        this.connect(string, n);
    }

    private InetAddress resolveViaFabricAddr(String string) throws UnknownHostException {
        return InetAddress.getByName(string);
    }

    private void connect(String string, int n) {
        LOGGER.info("Connecting to {}, {}", (Object)string, (Object)n);
        Thread thread2 = new Thread(this, "Server Connector #" + CONNECTION_ID.incrementAndGet(), string, n){
            final String val$ip;
            final int val$port;
            final ConnectingScreen this$0;
            {
                this.this$0 = connectingScreen;
                this.val$ip = string2;
                this.val$port = n;
                super(string);
            }

            @Override
            public void run() {
                InetAddress inetAddress = null;
                try {
                    if (this.this$0.cancel) {
                        return;
                    }
                    inetAddress = this.this$0.resolveViaFabricAddr(this.val$ip);
                    this.this$0.networkManager = NetworkManager.createNetworkManagerAndConnect(inetAddress, this.val$port, this.this$0.minecraft.gameSettings.isUsingNativeTransport());
                    this.this$0.networkManager.setNetHandler(new ClientLoginNetHandler(this.this$0.networkManager, this.this$0.minecraft, this.this$0.previousGuiScreen, this::lambda$run$0));
                    this.this$0.networkManager.sendPacket(new CHandshakePacket(this.val$ip, this.val$port, ProtocolType.LOGIN));
                    this.this$0.networkManager.sendPacket(new CLoginStartPacket(this.this$0.minecraft.getSession().getProfile()));
                } catch (UnknownHostException unknownHostException) {
                    if (this.this$0.cancel) {
                        return;
                    }
                    LOGGER.error("Couldn't connect to server", (Throwable)unknownHostException);
                    this.this$0.minecraft.execute(this::lambda$run$1);
                } catch (Exception exception) {
                    if (this.this$0.cancel) {
                        return;
                    }
                    LOGGER.error("Couldn't connect to server", (Throwable)exception);
                    String string = inetAddress == null ? exception.toString() : exception.toString().replaceAll(inetAddress + ":" + this.val$port, "");
                    this.this$0.minecraft.execute(() -> this.lambda$run$2(string));
                }
            }

            private void lambda$run$2(String string) {
                this.this$0.minecraft.displayGuiScreen(new DisconnectedScreen(this.this$0.previousGuiScreen, DialogTexts.CONNECTION_FAILED, new TranslationTextComponent("disconnect.genericReason", string)));
            }

            private void lambda$run$1() {
                this.this$0.minecraft.displayGuiScreen(new DisconnectedScreen(this.this$0.previousGuiScreen, DialogTexts.CONNECTION_FAILED, new TranslationTextComponent("disconnect.genericReason", "Unknown host")));
            }

            private void lambda$run$0(ITextComponent iTextComponent) {
                this.this$0.func_209514_a(iTextComponent);
            }
        };
        thread2.setUncaughtExceptionHandler(new DefaultUncaughtExceptionHandler(LOGGER));
        thread2.start();
    }

    private void func_209514_a(ITextComponent iTextComponent) {
        this.field_209515_s = iTextComponent;
    }

    @Override
    public void tick() {
        if (this.networkManager != null) {
            if (this.networkManager.isChannelOpen()) {
                this.networkManager.tick();
            } else {
                this.networkManager.handleDisconnection();
            }
        }
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return true;
    }

    @Override
    protected void init() {
        this.addButton(new Button(this.width / 2 - 100, this.height / 4 + 120 + 12, 200, 20, DialogTexts.GUI_CANCEL, this::lambda$init$0));
    }

    @Override
    public void render(MatrixStack matrixStack, int n, int n2, float f) {
        this.renderBackground(matrixStack);
        long l = Util.milliTime();
        if (l - this.field_213000_g > 2000L) {
            this.field_213000_g = l;
            NarratorChatListener.INSTANCE.say(new TranslationTextComponent("narrator.joining").getString());
        }
        ConnectingScreen.drawCenteredString(matrixStack, this.font, this.field_209515_s, this.width / 2, this.height / 2 - 50, 0xFFFFFF);
        super.render(matrixStack, n, n2, f);
    }

    private void lambda$init$0(Button button) {
        this.cancel = true;
        if (this.networkManager != null) {
            this.networkManager.closeChannel(new TranslationTextComponent("connect.aborted"));
        }
        this.minecraft.displayGuiScreen(this.previousGuiScreen);
    }
}

