package net.minecraft.client.gui;

import cc.novoline.Novoline;
import cc.novoline.gui.screen.alt.repository.Alt;
import cc.novoline.gui.screen.alt.repository.AltRepositoryGUI;
import cc.novoline.utils.notifications.NotificationType;
import com.thealtening.api.TheAlteningException;
import com.thealtening.api.response.Account;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ForkJoinPool;
import java.util.function.BiConsumer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.Session;
import net.skidunion.J;
import org.apache.commons.lang3.StringUtils;

public class GuiDisconnected extends GuiScreen {
   private final String reason;
   private final IChatComponent message;
   private List multilineMessage;
   private final GuiScreen parentScreen;
   private int field_175353_i;
   private String x = "Resolving ban type...";
   private GuiButton randomAltButton;
   private GuiButton generateAndLogInButton;
   private GuiButton reconnectButton;
   private ServerData lastServer;

   public GuiDisconnected(GuiScreen var1, String var2, IChatComponent var3) {
      this.parentScreen = var1;
      this.reason = I18n.format(var2, new Object[0]);
      this.message = var3;
   }

   protected void keyTyped(char var1, int var2) throws IOException {
   }

   public boolean b() {
      int var1 = Integer.parseInt(J.aK);
      return var1 == 1 || var1 == 2 || var1 == 4 || var1 == 5;
   }

   public void initGui() {
      // $FF: Couldn't be decompiled
   }

   protected void actionPerformed(GuiButton var1) throws IOException {
      Novoline var2 = this.mc.getNovoline();
      AltRepositoryGUI var3 = var2.getAltRepositoryGUI();
      switch(var1.id) {
      case 0:
         this.mc.displayGuiScreen(this.parentScreen);
         break;
      case 1:
         this.reconnect();
         break;
      case 2:
         if(var3.getCurrentAlt() != null) {
            var3.removeAlt(var3.getCurrentAlt());
         }
         break;
      case 3:
         Alt var4 = var3.getRandomAlt();
         Novoline.getInstance().getNotificationManager().pop("No alts in repository!", NotificationType.ERROR);
         return;
      case 4:
         this.a(var2, var3);
      }

   }

   private void a(Novoline var1, AltRepositoryGUI var2) {
      String var3 = var2.getTokenContent();
      if(StringUtils.isBlank(var3)) {
         Novoline.getInstance().getNotificationManager().pop("No TheAltening token!", NotificationType.ERROR);
      } else {
         var1.getDataRetriever().updateKey(var3);
         this.setEnabledLogInButtons(false);
         CompletableFuture.runAsync(this::lambda$generateAndLogin$2, ForkJoinPool.commonPool()).whenComplete(this::lambda$generateAndLogin$3);
      }
   }

   private void reconnect() {
      this.mc.displayGuiScreen(new GuiConnecting(this.parentScreen, this.mc, this.lastServer));
   }

   private void setEnabledLogInButtons(boolean var1) {
      this.reconnectButton.enabled = var1;
      this.generateAndLogInButton.enabled = var1;
      this.randomAltButton.enabled = var1;
   }

   public void drawScreen(int var1, int var2, float var3) {
      this.drawDefaultBackground();
      this.drawCenteredString(this.fontRendererObj, this.reason, this.width / 2, this.height / 2 - this.field_175353_i / 2 - this.fontRendererObj.getHeight() * 2, 11184810);
      int var4 = this.height / 2 - this.field_175353_i / 2;
      if(this.multilineMessage != null) {
         for(String var6 : this.multilineMessage) {
            this.drawCenteredString(this.fontRendererObj, var6, this.width / 2, var4, 16777215);
            var4 += this.fontRendererObj.getHeight();
         }
      }

      int var7 = this.height / 2 + this.field_175353_i / 2 + this.fontRendererObj.getHeight();
      if(this.mc.session != null && this.lastServer != null) {
         this.fontRendererObj.drawString("Playing on: " + this.mc.session.getUsername() + " | " + this.lastServer.serverIP, (float)(this.width / 2 - this.fontRendererObj.d("Playing on: " + this.mc.session.getUsername() + " | " + this.lastServer.serverIP) / 2), (float)(var7 + 85), -1);
      }

      this.drawCenteredString(this.fontRendererObj, this.x, this.width / 2, 200, -1);
      super.drawScreen(var1, var2, var3);
   }

   public void a(String var1) {
      this.x = var1;
   }

   public List c() {
      return this.multilineMessage;
   }

   private void lambda$generateAndLogin$3(Void var1, Throwable var2) {
      this.reconnect();
   }

   private void lambda$generateAndLogin$2(Novoline var1, AltRepositoryGUI var2) {
      var1.generateAlteningAlt().whenCompleteAsync(this::lambda$null$1);
   }

   private void lambda$null$1(AltRepositoryGUI var1, Account var2, Throwable var3) {
      Novoline.getLogger().warn("An error occurred while generating an account!", var3);
      if(var3 instanceof TheAlteningException) {
         Novoline.getInstance().getNotificationManager().pop("Invalid TheAltening token!", NotificationType.ERROR);
      } else {
         Novoline.getLogger().warn("An unexpected error occurred while generating an alt!", var3);
         Novoline.getInstance().getNotificationManager().pop("Unexpected error occurred!", NotificationType.ERROR);
      }

      this.setEnabledLogInButtons(true);
   }

   private void lambda$actionPerformed$0(Session var1, Throwable var2) {
      this.setEnabledLogInButtons(true);
      Novoline.getLogger().warn("An unexpected error occurred while logging into an alt!", var2);
      Novoline.getInstance().getNotificationManager().pop("Unexpected error occurred!", NotificationType.ERROR);
   }

   private static Throwable a(Throwable var0) {
      return var0;
   }
}
