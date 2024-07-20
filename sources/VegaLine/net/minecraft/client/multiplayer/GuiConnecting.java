/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.multiplayer;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.atomic.AtomicInteger;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiDisconnected;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.multiplayer.ServerAddress;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.network.NetHandlerLoginClient;
import net.minecraft.client.resources.I18n;
import net.minecraft.network.EnumConnectionState;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.handshake.client.C00Handshake;
import net.minecraft.network.login.client.CPacketLoginStart;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.govno.client.utils.ClientRP;
import ru.govno.client.utils.Command.impl.Panic;
import ru.govno.client.utils.Render.RenderUtils;

public class GuiConnecting
extends GuiScreen {
    private final long initTime = System.currentTimeMillis();
    private static final AtomicInteger CONNECTION_ID = new AtomicInteger(0);
    private static final Logger LOGGER = LogManager.getLogger();
    private NetworkManager networkManager;
    private boolean cancel;
    private final GuiScreen previousGuiScreen;

    public GuiConnecting(GuiScreen parent, Minecraft mcIn, ServerData serverDataIn) {
        this.mc = mcIn;
        this.previousGuiScreen = parent;
        ServerAddress serveraddress = ServerAddress.fromString(serverDataIn.serverIP);
        mcIn.loadWorld(null);
        mcIn.setServerData(serverDataIn);
        this.connect(serveraddress.getIP(), serveraddress.getPort());
        GuiDisconnected.lastServer = serveraddress.getIP();
    }

    public GuiConnecting(GuiScreen parent, Minecraft mcIn, String hostName, int port) {
        this.mc = mcIn;
        this.previousGuiScreen = parent;
        mcIn.loadWorld(null);
        this.connect(hostName, port);
    }

    private void connect(final String ip, final int port) {
        LOGGER.info("Connecting to {}, {}", (Object)ip, (Object)port);
        new Thread("Server Connector #" + CONNECTION_ID.incrementAndGet()){

            @Override
            public void run() {
                InetAddress inetaddress = null;
                try {
                    if (GuiConnecting.this.cancel) {
                        return;
                    }
                    if (ip != null && InetAddress.getByName(ip) != null) {
                        inetaddress = InetAddress.getByName(ip);
                    }
                    GuiConnecting.this.networkManager = NetworkManager.createNetworkManagerAndConnect(inetaddress, port, ((GuiConnecting)GuiConnecting.this).mc.gameSettings.isUsingNativeTransport());
                    GuiConnecting.this.networkManager.setNetHandler(new NetHandlerLoginClient(GuiConnecting.this.networkManager, GuiConnecting.this.mc, GuiConnecting.this.previousGuiScreen));
                    GuiConnecting.this.networkManager.sendPacket(new C00Handshake(ip, port, EnumConnectionState.LOGIN));
                    GuiConnecting.this.networkManager.sendPacket(new CPacketLoginStart(GuiConnecting.this.mc.getSession().getProfile()));
                    ClientRP.getInstance().getDiscordRP().update("\u0418\u0433\u0440\u0430\u0435\u0442 \u043d\u0430 " + (ip + (String)(port != 25565 ? ":" + port : "")).replace(".ru", "").replace(".space", "").replace("mc.", "").replace("play.", "").replace(".ru", "").replace(".com", "").replace(".net", "").replace(".su", "").replace(".eu", "").replace("creative.", "").replace(".xyz", "").replace(".org", "").replace(".gg", "").replace(".me", "").replace("join.", "").replace("localhost", "LocalServer"), "\u0412 \u043e\u043d\u043b\u0430\u0439\u043d\u0435");
                } catch (UnknownHostException unknownhostexception) {
                    if (GuiConnecting.this.cancel) {
                        return;
                    }
                    LOGGER.error("Couldn't connect to server", (Throwable)unknownhostexception);
                    GuiConnecting.this.mc.displayGuiScreen(new GuiDisconnected(GuiConnecting.this.previousGuiScreen, "connect.failed", new TextComponentTranslation("disconnect.genericReason", "Unknown host")));
                } catch (Exception exception) {
                    if (GuiConnecting.this.cancel) {
                        return;
                    }
                    LOGGER.error("Couldn't connect to server", (Throwable)exception);
                    String s = exception.toString();
                    if (inetaddress != null) {
                        String s1 = inetaddress + ":" + port;
                        s = s.replaceAll(s1, "");
                    }
                    GuiConnecting.this.mc.displayGuiScreen(new GuiDisconnected(GuiConnecting.this.previousGuiScreen, "connect.failed", new TextComponentTranslation("disconnect.genericReason", s)));
                }
            }
        }.start();
    }

    @Override
    public void updateScreen() {
        if (this.networkManager != null) {
            if (this.networkManager.isChannelOpen()) {
                this.networkManager.processReceivedPackets();
            } else {
                this.networkManager.checkDisconnected();
            }
        }
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
    }

    @Override
    public void initGui() {
        this.buttonList.clear();
        this.buttonList.add(new GuiButton(0, width / 2 - 100, height / 4 + 120 + 12, I18n.format("gui.cancel", new Object[0])));
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if (button.id == 0) {
            this.cancel = true;
            if (this.networkManager != null) {
                this.networkManager.closeChannel(new TextComponentString("Aborted"));
            }
            this.mc.displayGuiScreen(this.previousGuiScreen);
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        ScaledResolution sr = new ScaledResolution(this.mc);
        if (!Panic.stop) {
            RenderUtils.drawScreenShaderBackground(sr, mouseX, mouseY);
        } else {
            this.drawDefaultBackground();
        }
        if (this.networkManager == null) {
            this.drawCenteredString(this.fontRendererObj, I18n.format("connect.connecting", new Object[0]), width / 2, height / 2 - 70, 0xFFFFFF);
        } else {
            this.drawCenteredString(this.fontRendererObj, I18n.format("connect.authorizing", new Object[0]), width / 2, height / 2 - 70, 0xFFFFFF);
        }
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
}

