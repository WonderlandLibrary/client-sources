package net.minecraft.client.multiplayer;

import java.util.concurrent.atomic.*;
import net.minecraft.client.*;
import net.minecraft.client.network.*;
import net.minecraft.network.handshake.client.*;
import net.minecraft.network.*;
import net.minecraft.network.login.client.*;
import java.net.*;
import net.minecraft.client.resources.*;
import net.minecraft.util.*;
import net.minecraft.client.gui.*;
import org.apache.logging.log4j.*;

public class GuiConnecting extends GuiScreen
{
    private static final AtomicInteger CONNECTION_ID;
    private static final Logger logger;
    private final GuiScreen previousGuiScreen;
    private NetworkManager networkManager;
    private boolean cancel;
    
    public GuiConnecting(final GuiScreen p_i1181_1_, final Minecraft mcIn, final ServerData p_i1181_3_) {
        GuiConnecting.mc = mcIn;
        this.previousGuiScreen = p_i1181_1_;
        final ServerAddress var4 = ServerAddress.func_78860_a(p_i1181_3_.serverIP);
        mcIn.loadWorld(null);
        mcIn.setServerData(p_i1181_3_);
        this.connect(var4.getIP(), var4.getPort());
    }
    
    public GuiConnecting(final GuiScreen p_i1182_1_, final Minecraft mcIn, final String p_i1182_3_, final int p_i1182_4_) {
        GuiConnecting.mc = mcIn;
        this.previousGuiScreen = p_i1182_1_;
        mcIn.loadWorld(null);
        this.connect(p_i1182_3_, p_i1182_4_);
    }
    
    private void connect(final String ip, final int port) {
        GuiConnecting.logger.info("Connecting to " + ip + ", " + port);
        new Thread("Server Connector #" + GuiConnecting.CONNECTION_ID.incrementAndGet()) {
            @Override
            public void run() {
                InetAddress var1 = null;
                try {
                    if (GuiConnecting.this.cancel) {
                        return;
                    }
                    var1 = InetAddress.getByName(ip);
                    GuiConnecting.this.networkManager = NetworkManager.provideLanClient(var1, port);
                    GuiConnecting.this.networkManager.setNetHandler(new NetHandlerLoginClient(GuiConnecting.this.networkManager, GuiConnecting.mc, GuiConnecting.this.previousGuiScreen));
                    GuiConnecting.this.networkManager.sendPacket(new C00Handshake(47, ip, port, EnumConnectionState.LOGIN));
                    GuiConnecting.this.networkManager.sendPacket(new C00PacketLoginStart(GuiConnecting.mc.getSession().getProfile()));
                }
                catch (UnknownHostException var2) {
                    if (GuiConnecting.this.cancel) {
                        return;
                    }
                    GuiConnecting.logger.error("Couldn't connect to server", (Throwable)var2);
                    GuiConnecting.mc.displayGuiScreen(new GuiDisconnected(GuiConnecting.this.previousGuiScreen, "connect.failed", new ChatComponentTranslation("disconnect.genericReason", new Object[] { "Unknown host" })));
                }
                catch (Exception var3) {
                    if (GuiConnecting.this.cancel) {
                        return;
                    }
                    GuiConnecting.logger.error("Couldn't connect to server", (Throwable)var3);
                    String var4 = var3.toString();
                    if (var1 != null) {
                        final String var5 = var1.toString() + ":" + port;
                        var4 = var4.replaceAll(var5, "");
                    }
                    GuiConnecting.mc.displayGuiScreen(new GuiDisconnected(GuiConnecting.this.previousGuiScreen, "connect.failed", new ChatComponentTranslation("disconnect.genericReason", new Object[] { var4 })));
                }
            }
        }.start();
    }
    
    @Override
    public void updateScreen() {
        if (this.networkManager != null) {
            if (this.networkManager.isChannelOpen()) {
                this.networkManager.processReceivedPackets();
            }
            else {
                this.networkManager.checkDisconnected();
            }
        }
    }
    
    @Override
    protected void keyTyped(final char typedChar, final int keyCode) {
    }
    
    @Override
    public void initGui() {
        this.buttonList.clear();
        this.buttonList.add(new GuiButton(0, GuiConnecting.width / 2 - 100, GuiConnecting.height / 4 + 120 + 12, I18n.format("gui.cancel", new Object[0])));
    }
    
    @Override
    protected void actionPerformed(final GuiButton button) {
        if (button.id == 0) {
            this.cancel = true;
            if (this.networkManager != null) {
                this.networkManager.closeChannel(new ChatComponentText("Aborted"));
            }
            GuiConnecting.mc.displayGuiScreen(this.previousGuiScreen);
        }
    }
    
    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        this.drawDefaultBackground();
        if (this.networkManager == null) {
            Gui.drawCenteredString(this.fontRendererObj, I18n.format("connect.connecting", new Object[0]), GuiConnecting.width / 2, GuiConnecting.height / 2 - 50, 16777215);
        }
        else {
            Gui.drawCenteredString(this.fontRendererObj, I18n.format("connect.authorizing", new Object[0]), GuiConnecting.width / 2, GuiConnecting.height / 2 - 50, 16777215);
        }
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
    
    static {
        CONNECTION_ID = new AtomicInteger(0);
        logger = LogManager.getLogger();
    }
}
