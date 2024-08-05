package net.minecraft.client.multiplayer;

import java.awt.*;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

import fr.dog.util.input.MouseUtil;
import fr.dog.util.render.RenderUtil;
import fr.dog.util.render.animation.Animation;
import fr.dog.util.render.animation.Easing;
import fr.dog.util.render.font.Fonts;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiDisconnected;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.network.NetHandlerLoginClient;
import net.minecraft.client.resources.I18n;
import net.minecraft.network.EnumConnectionState;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.handshake.client.C00Handshake;
import net.minecraft.network.login.client.C00PacketLoginStart;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GuiConnecting extends GuiScreen {
    private static final AtomicInteger CONNECTION_ID = new AtomicInteger(0);
    private static final Logger logger = LogManager.getLogger();
    private NetworkManager networkManager;
    private boolean cancel;
    private final GuiScreen previousGuiScreen;
    private ServerData p_i1181_3_;
    private final Animation animation = new Animation(Easing.EASE_OUT_QUAD, 250);
    public GuiConnecting(GuiScreen p_i1181_1_, Minecraft mcIn, ServerData p_i1181_3_) {
        this.p_i1181_3_ = p_i1181_3_;
        this.mc = mcIn;
        this.previousGuiScreen = p_i1181_1_;
        ServerAddress serveraddress = ServerAddress.fromString(p_i1181_3_.serverIP);
        mcIn.loadWorld(null);
        mcIn.setServerData(p_i1181_3_);
        this.connect(serveraddress.getIP(), serveraddress.getPort());
    }

    public GuiConnecting(GuiScreen p_i1182_1_, Minecraft mcIn, String hostName, int port) {
        this.mc = mcIn;
        this.previousGuiScreen = p_i1182_1_;
        mcIn.loadWorld(null);
        this.connect(hostName, port);
    }

    private void connect(final String ip, final int port) {
        RenderUtil.onPlayerJoin();
        logger.info("Connecting to " + ip + ", " + port);
        (new Thread("Server Connector #" + CONNECTION_ID.incrementAndGet()) {
            public void run() {
                InetAddress inetaddress = null;

                try {
                    if (GuiConnecting.this.cancel) {
                        return;
                    }

                    inetaddress = InetAddress.getByName(ip);
                    GuiConnecting.this.networkManager = NetworkManager.createNetworkManagerAndConnect(inetaddress, port, GuiConnecting.this.mc.gameSettings.isUsingNativeTransport());
                    GuiConnecting.this.networkManager.setNetHandler(new NetHandlerLoginClient(GuiConnecting.this.networkManager, GuiConnecting.this.mc, GuiConnecting.this.previousGuiScreen));
                    GuiConnecting.this.networkManager.sendPacket(new C00Handshake(47, ip, port, EnumConnectionState.LOGIN));
                    GuiConnecting.this.networkManager.sendPacket(new C00PacketLoginStart(GuiConnecting.this.mc.getSession().getProfile()));
                } catch (UnknownHostException unknownhostexception) {
                    if (GuiConnecting.this.cancel) {
                        return;
                    }

                    GuiConnecting.logger.error("Couldn't connect to server", unknownhostexception);
                    GuiConnecting.this.mc.displayGuiScreen(new GuiDisconnected(GuiConnecting.this.previousGuiScreen, "connect.failed", new ChatComponentTranslation("disconnect.genericReason", "Unknown host")));
                } catch (Exception exception) {
                    if (GuiConnecting.this.cancel) {
                        return;
                    }

                    GuiConnecting.logger.error("Couldn't connect to server", exception);
                    String s = exception.toString();

                    if (inetaddress != null) {
                        String s1 = inetaddress + ":" + port;
                        s = s.replaceAll(s1, "");
                    }

                    GuiConnecting.this.mc.displayGuiScreen(new GuiDisconnected(GuiConnecting.this.previousGuiScreen, "connect.failed", new ChatComponentTranslation("disconnect.genericReason", s)));
                }
            }
        }).start();
    }

    public void updateScreen() {
        if (this.networkManager != null) {
            if (this.networkManager.isChannelOpen()) {
                this.networkManager.processReceivedPackets();
            } else {
                this.networkManager.checkDisconnected();
            }
        }
    }

    protected void keyTyped(char typedChar, int keyCode) throws IOException {
    }

    public void initGui() {
        this.buttonList.clear();
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        boolean hovered = MouseUtil.isHovering(MouseUtil.getMouseX(), MouseUtil.getMouseY(), (float) width /2 - 15, (float) height /2 + 64, 25,10);

        if (hovered) {
            this.cancel = true;

            if (this.networkManager != null) {
                this.networkManager.closeChannel(new ChatComponentText("Aborted"));
            }

            this.mc.displayGuiScreen(this.previousGuiScreen);
        }
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        boolean hovered = MouseUtil.isHovering(mouseX, mouseY, (float) width /2 - 15, (float) height /2 + 64, 25,10);
        animation.run(hovered ? 1 : 0);

        RenderUtil.drawRect(0,0,width,height, new Color(0,0,0));
        Fonts.getOpenSansRegular(18).drawCenteredString(Objects.equals(p_i1181_3_.serverName, "Minecraft Server") ? "Connecting to " + p_i1181_3_.serverIP + "..." : "Connecting to " + p_i1181_3_.serverName + "...", (float) width /2, (float) height /2+50, -1);
        Fonts.getOpenSansRegular(12).drawCenteredString("Cancel", (float) width /2, (float) height /2+64, new Color(255,255,255,(int) (150 + 100*animation.getValue())).getRGB());

        RenderUtil.drawCircleLoading2((float) width /2 - 50, (float) height /2 - 50,100);

        super.drawScreen(mouseX, mouseY, partialTicks);
    }
}
