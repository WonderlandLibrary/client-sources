package net.minecraft.client.multiplayer;

import cc.novoline.Novoline;
import cc.novoline.modules.visual.ClickGUI;
import java.io.IOException;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;
import net.a82;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.ServerAddress;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.resources.I18n;
import net.minecraft.network.NetworkManager;
import net.minecraft.util.ChatComponentText;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GuiConnecting extends GuiScreen {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final AtomicInteger CONNECTION_ID = new AtomicInteger(0);
   private final GuiScreen previousGuiScreen;
   private NetworkManager networkManager;
   private boolean cancel;
   private static final HashMap HYPIXEL_IP_RANGES = new HashMap();

   public GuiConnecting(GuiScreen var1, Minecraft var2, ServerData var3) {
      this.mc = var2;
      this.previousGuiScreen = var1;
      ServerAddress var4 = ServerAddress.b(var3.serverIP);
      var2.loadWorld((WorldClient)null);
      var2.setServerData(var3);
      this.connect(var4.getIP(), var4.getPort());
      ClickGUI var5 = (ClickGUI)Novoline.getInstance().getModuleManager().getModule(ClickGUI.class);
      var5.a(System.currentTimeMillis());
      var5.b();
   }

   public GuiConnecting(GuiScreen var1, Minecraft var2, String var3, int var4) {
      this.mc = var2;
      this.previousGuiScreen = var1;
      var2.loadWorld((WorldClient)null);
      this.connect(var3, var4);
   }

   private boolean isIpInSubnet(String var1, String var2, int var3) {
      String var10000 = var1;

      byte[] var4;
      byte[] var5;
      try {
         var4 = InetAddress.getByName(var10000).getAddress();
         var5 = InetAddress.getByName(var2).getAddress();
         if(var4.length != var5.length) {
            return false;
         }
      } catch (Throwable var9) {
         return false;
      }

      int var6 = var3;

      int var7;
      for(var7 = 0; var6 >= 8; var6 -= 8) {
         if(var4[var7] != var5[var7]) {
            return false;
         }

         ++var7;
      }

      int var8 = '\uff00' >> var6 & 255;
      return (var4[var7] & var8) == (var5[var7] & var8);
   }

   public void connect(String var1, int var2) {
      LOGGER.info("Connecting to {}, {}", new Object[]{var1, Integer.valueOf(var2)});
      (new a82(this, "Server Connector #" + CONNECTION_ID.incrementAndGet(), var1, var2)).start();
   }

   public void updateScreen() {
      if(this.networkManager != null) {
         if(this.networkManager.isChannelOpen()) {
            this.networkManager.processReceivedPackets();
         } else {
            this.networkManager.checkDisconnected();
         }
      }

   }

   protected void keyTyped(char var1, int var2) throws IOException {
   }

   public void initGui() {
      this.buttonList.clear();
      this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 120 + 12, I18n.format("gui.cancel", new Object[0])));
      super.initGui();
   }

   protected void actionPerformed(GuiButton var1) throws IOException {
      if(var1.id == 0) {
         this.cancel = true;
         if(this.networkManager != null) {
            this.networkManager.closeChannel(new ChatComponentText("Aborted"));
         }

         this.mc.displayGuiScreen(this.previousGuiScreen);
      }

   }

   public void drawScreen(int var1, int var2, float var3) {
      this.drawDefaultBackground();
      if(this.networkManager == null) {
         this.drawCenteredString(this.fontRendererObj, I18n.format("connect.connecting", new Object[0]), this.width / 2, this.height / 2 - 50, 16777215);
      } else {
         this.drawCenteredString(this.fontRendererObj, I18n.format("connect.authorizing", new Object[0]), this.width / 2, this.height / 2 - 50, 16777215);
      }

      super.drawScreen(var1, var2, var3);
   }

   static boolean access$000(GuiConnecting var0) {
      return var0.cancel;
   }

   static HashMap access$100() {
      return HYPIXEL_IP_RANGES;
   }

   static boolean access$200(GuiConnecting var0, String var1, String var2, int var3) {
      return var0.isIpInSubnet(var1, var2, var3);
   }

   static Logger access$300() {
      return LOGGER;
   }

   static Minecraft a(GuiConnecting var0) {
      return var0.mc;
   }

   static NetworkManager access$502(GuiConnecting var0, NetworkManager var1) {
      return var0.networkManager = var1;
   }

   static Minecraft e(GuiConnecting var0) {
      return var0.mc;
   }

   static NetworkManager access$500(GuiConnecting var0) {
      return var0.networkManager;
   }

   static Minecraft g(GuiConnecting var0) {
      return var0.mc;
   }

   static GuiScreen access$800(GuiConnecting var0) {
      return var0.previousGuiScreen;
   }

   static Minecraft i(GuiConnecting var0) {
      return var0.mc;
   }

   static Minecraft d(GuiConnecting var0) {
      return var0.mc;
   }

   static Minecraft b(GuiConnecting var0) {
      return var0.mc;
   }

   static {
      HYPIXEL_IP_RANGES.put("209.222.114.0", Integer.valueOf(23));
      HYPIXEL_IP_RANGES.put("173.245.48.0", Integer.valueOf(20));
      HYPIXEL_IP_RANGES.put("103.21.244.0", Integer.valueOf(22));
      HYPIXEL_IP_RANGES.put("103.22.200.0", Integer.valueOf(22));
      HYPIXEL_IP_RANGES.put("103.31.4.0", Integer.valueOf(22));
      HYPIXEL_IP_RANGES.put("141.101.64.0", Integer.valueOf(18));
      HYPIXEL_IP_RANGES.put("108.162.192.0", Integer.valueOf(20));
      HYPIXEL_IP_RANGES.put("190.93.240.0", Integer.valueOf(20));
      HYPIXEL_IP_RANGES.put("188.114.96.0", Integer.valueOf(20));
      HYPIXEL_IP_RANGES.put("197.234.240.0", Integer.valueOf(22));
      HYPIXEL_IP_RANGES.put("198.41.128.0", Integer.valueOf(17));
      HYPIXEL_IP_RANGES.put("162.158.0.0", Integer.valueOf(15));
      HYPIXEL_IP_RANGES.put("104.16.0.0", Integer.valueOf(12));
      HYPIXEL_IP_RANGES.put("172.64.0.0", Integer.valueOf(13));
      HYPIXEL_IP_RANGES.put("131.0.72.0", Integer.valueOf(22));
   }

   private static Throwable a(Throwable var0) {
      return var0;
   }
}
