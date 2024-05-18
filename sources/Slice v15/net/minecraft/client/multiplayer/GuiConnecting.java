package net.minecraft.client.multiplayer;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
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
import net.minecraft.util.Session;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GuiConnecting
  extends GuiScreen
{
  private static final AtomicInteger CONNECTION_ID = new AtomicInteger(0);
  private static final Logger logger = LogManager.getLogger();
  private NetworkManager networkManager;
  private boolean cancel;
  private final GuiScreen previousGuiScreen;
  private static final String __OBFID = "CL_00000685";
  
  public GuiConnecting(GuiScreen p_i1181_1_, Minecraft mcIn, ServerData p_i1181_3_)
  {
    mc = mcIn;
    previousGuiScreen = p_i1181_1_;
    ServerAddress var4 = ServerAddress.func_78860_a(serverIP);
    mcIn.loadWorld(null);
    mcIn.setServerData(p_i1181_3_);
    connect(var4.getIP(), var4.getPort());
  }
  
  public GuiConnecting(GuiScreen p_i1182_1_, Minecraft mcIn, String p_i1182_3_, int p_i1182_4_)
  {
    mc = mcIn;
    previousGuiScreen = p_i1182_1_;
    mcIn.loadWorld(null);
    connect(p_i1182_3_, p_i1182_4_);
  }
  
  private void connect(final String ip, final int port)
  {
    logger.info("Connecting to " + ip + ", " + port);
    new Thread("Server Connector #" + CONNECTION_ID.incrementAndGet())
    {
      private static final String __OBFID = "CL_00000686";
      
      public void run()
      {
        InetAddress var1 = null;
        try
        {
          if (cancel) {
            return;
          }
          var1 = InetAddress.getByName(ip);
          networkManager = NetworkManager.provideLanClient(var1, port);
          networkManager.setNetHandler(new NetHandlerLoginClient(networkManager, mc, previousGuiScreen));
          networkManager.sendPacket(new C00Handshake(47, ip, port, EnumConnectionState.LOGIN));
          networkManager.sendPacket(new C00PacketLoginStart(mc.getSession().getProfile()));
        }
        catch (UnknownHostException var5)
        {
          if (cancel) {
            return;
          }
          GuiConnecting.logger.error("Couldn't connect to server", var5);
          mc.displayGuiScreen(new GuiDisconnected(previousGuiScreen, "connect.failed", new ChatComponentTranslation("disconnect.genericReason", new Object[] { "Unknown host" })));
        }
        catch (Exception var6)
        {
          if (cancel) {
            return;
          }
          GuiConnecting.logger.error("Couldn't connect to server", var6);
          String var3 = var6.toString();
          if (var1 != null)
          {
            String var4 = var1.toString() + ":" + port;
            var3 = var3.replaceAll(var4, "");
          }
          mc.displayGuiScreen(new GuiDisconnected(previousGuiScreen, "connect.failed", new ChatComponentTranslation("disconnect.genericReason", new Object[] { var3 })));
        }
      }
    }.start();
  }
  
  public void updateScreen()
  {
    if (networkManager != null) {
      if (networkManager.isChannelOpen()) {
        networkManager.processReceivedPackets();
      } else {
        networkManager.checkDisconnected();
      }
    }
  }
  
  protected void keyTyped(char typedChar, int keyCode)
    throws IOException
  {}
  
  public void initGui()
  {
    buttonList.clear();
    buttonList.add(new GuiButton(0, width / 2 - 100, height / 4 + 120 + 12, I18n.format("gui.cancel", new Object[0])));
  }
  
  protected void actionPerformed(GuiButton button)
    throws IOException
  {
    if (id == 0)
    {
      cancel = true;
      if (networkManager != null) {
        networkManager.closeChannel(new ChatComponentText("Aborted"));
      }
      mc.displayGuiScreen(previousGuiScreen);
    }
  }
  
  public void drawScreen(int mouseX, int mouseY, float partialTicks)
  {
    drawDefaultBackground();
    if (networkManager == null) {
      drawCenteredString(fontRendererObj, I18n.format("connect.connecting", new Object[0]), width / 2, height / 2 - 50, 16777215);
    } else {
      drawCenteredString(fontRendererObj, I18n.format("connect.authorizing", new Object[0]), width / 2, height / 2 - 50, 16777215);
    }
    super.drawScreen(mouseX, mouseY, partialTicks);
  }
}
