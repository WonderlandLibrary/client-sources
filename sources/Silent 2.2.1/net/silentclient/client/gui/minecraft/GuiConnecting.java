package net.silentclient.client.gui.minecraft;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import net.minecraft.client.multiplayer.ServerAddress;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.network.NetHandlerLoginClient;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.network.EnumConnectionState;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.handshake.client.C00Handshake;
import net.minecraft.network.login.client.C00PacketLoginStart;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.ResourceLocation;
import net.silentclient.client.Client;
import net.silentclient.client.event.impl.ConnectToServerEvent;
import net.silentclient.client.gui.SilentScreen;
import net.silentclient.client.gui.elements.Button;
import net.silentclient.client.gui.font.SilentFontRenderer;
import net.silentclient.client.gui.util.RenderUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.*;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.atomic.AtomicInteger;

public class GuiConnecting extends SilentScreen
{
    private static final AtomicInteger CONNECTION_ID = new AtomicInteger(0);
    private static final Logger logger = LogManager.getLogger();
    private NetworkManager networkManager;
    private boolean cancel;
    private final GuiScreen previousGuiScreen;

    public GuiConnecting(GuiScreen p_i1181_1_, Minecraft mcIn, ServerData p_i1181_3_)
    {
        this.mc = mcIn;
        this.previousGuiScreen = p_i1181_1_;
        mcIn.loadWorld(null);
        mcIn.setServerData(p_i1181_3_);
        this.connectServerData(p_i1181_3_); //Put ServerData instead of IP, Port directly.
    }

    public GuiConnecting(GuiScreen p_i1182_1_, Minecraft mcIn, String hostName, int port)
    {
        this.mc = mcIn;
        this.previousGuiScreen = p_i1182_1_;
        mcIn.loadWorld((WorldClient)null);
        this.connect(hostName, port);
    }
    
    private void connectServerData(final ServerData serverData)
    {
        logger.info("Connecting to " + serverData.serverIP);
        Client.getInstance().lastServerData = serverData;
        (new Thread("Server Connector #" + CONNECTION_ID.incrementAndGet())
        {
            public void run()
            {
                InetAddress inetaddress = null;
                ServerAddress serveraddress = ServerAddress.fromString(serverData.serverIP);
                String ip = serveraddress.getIP();
                int port = serveraddress.getPort();
                
                try
                {
                    if (GuiConnecting.this.cancel)
                    {
                        return;
                    }
                    
                    try {
                    	Minecraft.getMinecraft().isIntegratedServerRunning();
                        Minecraft.getMinecraft().getIntegratedServer();
                        Minecraft.getMinecraft().theWorld.sendQuittingDisconnectingPacket();
                        Minecraft.getMinecraft().loadWorld((WorldClient)null);
                    } catch(Exception err) {
                    	
                    }

                    inetaddress = InetAddress.getByName(ip);
                    GuiConnecting.this.networkManager = NetworkManager.createNetworkManagerAndConnect(inetaddress, port, GuiConnecting.this.mc.gameSettings.useNativeTransport);
                    GuiConnecting.this.networkManager.setNetHandler(new NetHandlerLoginClient(GuiConnecting.this.networkManager, GuiConnecting.this.mc, GuiConnecting.this.previousGuiScreen));
                    GuiConnecting.this.networkManager.sendPacket(new C00Handshake(47, ip, port, EnumConnectionState.LOGIN));
                    GuiConnecting.this.networkManager.sendPacket(new C00PacketLoginStart(GuiConnecting.this.mc.getSession().getProfile()));
                    new ConnectToServerEvent(serverData).call();
                }
                catch (UnknownHostException unknownhostexception)
                {
                    Client.logger.catching(unknownhostexception);
                    if (GuiConnecting.this.cancel)
                    {
                        return;
                    }

                    GuiConnecting.logger.error((String)"Couldn\'t connect to server", (Throwable)unknownhostexception);
                    GuiConnecting.this.mc.displayGuiScreen(new GuiDisconnected(GuiConnecting.this.previousGuiScreen, "connect.failed", new ChatComponentTranslation("disconnect.genericReason", new Object[] {"Unknown host"})));
                }
                catch (Exception exception)
                {
                    Client.logger.catching(exception);
                    if (GuiConnecting.this.cancel)
                    {
                        return;
                    }

                    GuiConnecting.logger.error((String)"Couldn\'t connect to server", (Throwable)exception);
                    String s = exception.toString();

                    if (inetaddress != null)
                    {
                        String s1 = inetaddress.toString() + ":" + port;
                        s = s.replaceAll(s1, "");
                    }

                    GuiConnecting.this.mc.displayGuiScreen(new GuiDisconnected(GuiConnecting.this.previousGuiScreen, "connect.failed", new ChatComponentTranslation("disconnect.genericReason", new Object[] {s})));
                }
            }
        }).start();
    }

    private void connect(final String ip, final int port)
    {
        logger.info("Connecting to " + ip + ", " + port);
        Client.getInstance().lastServerData = new ServerData("Minecraft Server", ip, false);
        (new Thread("Server Connector #" + CONNECTION_ID.incrementAndGet())
        {
            public void run()
            {
                InetAddress inetaddress = null;

                try
                {
                    if (GuiConnecting.this.cancel)
                    {
                        return;
                    }
                    
                    try {
                    	Minecraft.getMinecraft().isIntegratedServerRunning();
                        Minecraft.getMinecraft().getIntegratedServer();
                        Minecraft.getMinecraft().theWorld.sendQuittingDisconnectingPacket();
                        Minecraft.getMinecraft().loadWorld((WorldClient)null);
                    } catch(Exception err) {
                    	
                    }

                    inetaddress = InetAddress.getByName(ip);
                    GuiConnecting.this.networkManager = NetworkManager.createNetworkManagerAndConnect(inetaddress, port, GuiConnecting.this.mc.gameSettings.useNativeTransport);
                    GuiConnecting.this.networkManager.setNetHandler(new NetHandlerLoginClient(GuiConnecting.this.networkManager, GuiConnecting.this.mc, GuiConnecting.this.previousGuiScreen));
                    GuiConnecting.this.networkManager.sendPacket(new C00Handshake(47, ip, port, EnumConnectionState.LOGIN));
                    GuiConnecting.this.networkManager.sendPacket(new C00PacketLoginStart(GuiConnecting.this.mc.getSession().getProfile()));
                    new ConnectToServerEvent(new ServerData("Minecraft Server", ip, false)).call();
                }
                catch (UnknownHostException unknownhostexception)
                {
                    if (GuiConnecting.this.cancel)
                    {
                        return;
                    }

                    GuiConnecting.logger.error((String)"Couldn\'t connect to server", (Throwable)unknownhostexception);
                    GuiConnecting.this.mc.displayGuiScreen(new GuiDisconnected(GuiConnecting.this.previousGuiScreen, "connect.failed", new ChatComponentTranslation("disconnect.genericReason", new Object[] {"Unknown host"})));
                }
                catch (Exception exception)
                {
                    if (GuiConnecting.this.cancel)
                    {
                        return;
                    }

                    GuiConnecting.logger.error((String)"Couldn\'t connect to server", (Throwable)exception);
                    String s = exception.toString();

                    if (inetaddress != null)
                    {
                        String s1 = inetaddress.toString() + ":" + port;
                        s = s.replaceAll(s1, "");
                    }

                    GuiConnecting.this.mc.displayGuiScreen(new GuiDisconnected(GuiConnecting.this.previousGuiScreen, "connect.failed", new ChatComponentTranslation("disconnect.genericReason", new Object[] {s})));
                }
            }
        }).start();
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
        if (this.networkManager != null)
        {
            if (this.networkManager.isChannelOpen())
            {
                this.networkManager.processReceivedPackets();
            }
            else
            {
                this.networkManager.checkDisconnected();
            }
        }
    }

    /**
     * Fired when a key is typed (except F11 which toggles full screen). This is the equivalent of
     * KeyListener.keyTyped(KeyEvent e). Args : character (character on the key), keyCode (lwjgl Keyboard key code)
     */
    protected void keyTyped(char typedChar, int keyCode) throws IOException
    {
    }

    /**
     * Adds the buttons (and other controls) to the screen in question. Called when the GUI is displayed and when the
     * window resizes, the buttonList is cleared beforehand.
     */
    public void initGui()
    {
    	ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
		scaledResolution.getScaleFactor();
        this.buttonList.clear();
        this.buttonList.add(new Button(0, this.width / 2 - 50, scaledResolution.getScaledHeight() - 70, 100, 20, "Cancel"));
    }

    /**
     * Called by the controls from the buttonList when activated. (Mouse pressed for buttons)
     */
    protected void actionPerformed(GuiButton button) throws IOException
    {
        if (button.id == 0)
        {
            this.cancel = true;

            if (this.networkManager != null)
            {
                this.networkManager.closeChannel(new ChatComponentText("Aborted"));
            }

            this.mc.displayGuiScreen(this.previousGuiScreen);
        }
    }

    /**
     * Draws the screen and all the components in it. Args : mouseX, mouseY, renderPartialTicks
     */
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.drawDefaultBackground();
        ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
		scaledResolution.getScaleFactor();
		Gui.drawRect(0, 0, scaledResolution.getScaledWidth(), scaledResolution.getScaledHeight(), new Color(19, 19, 19).getRGB());
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		RenderUtil.drawCentredImage(new ResourceLocation("silentclient/logos/logo.png"), scaledResolution.getScaledWidth() / 2, scaledResolution.getScaledHeight() / 3, 300, 58);
		Client.getInstance().getSilentFontRenderer().drawCenteredString(this.networkManager == null ? "Connecting..." : "Authorizing...", scaledResolution.getScaledWidth() / 2, scaledResolution.getScaledHeight() - 100 - 3, 14, SilentFontRenderer.FontType.TITLE);

//        if (this.networkManager == null)
//        {
//            this.drawCenteredString(this.fontRendererObj, I18n.format("connect.connecting", new Object[0]), this.width / 2, this.height / 2 - 50, 16777215);
//        }
//        else
//        {
//            this.drawCenteredString(this.fontRendererObj, I18n.format("connect.authorizing", new Object[0]), this.width / 2, this.height / 2 - 50, 16777215);
//        }

        super.drawScreen(mouseX, mouseY, partialTicks);
    }
}
