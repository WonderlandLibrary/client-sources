package net.optifine;

import com.google.common.collect.Lists;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiOptionButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.optifine.Config;
import net.optifine.MatchBlock;

public class GuiMessage extends GuiScreen {
   private GuiScreen parentScreen;
   private String messageLine1;
   private String messageLine2;
   private final List listLines2 = Lists.newArrayList();
   protected String confirmButtonText;
   private int ticksUntilEnable;

   public GuiMessage(GuiScreen var1, String var2, String var3) {
      this.parentScreen = var1;
      this.messageLine1 = var2;
      this.messageLine2 = var3;
      this.confirmButtonText = I18n.format("gui.done", new Object[0]);
   }

   public void initGui() {
      this.buttonList.add(new GuiOptionButton(0, this.width / 2 - 74, this.height / 6 + 96, this.confirmButtonText));
      this.listLines2.clear();
      this.listLines2.addAll(this.fontRendererObj.listFormattedStringToWidth(this.messageLine2, this.width - 50));
   }

   protected void actionPerformed(GuiButton var1) throws IOException {
      Config.getMinecraft().displayGuiScreen(this.parentScreen);
   }

   public void drawScreen(int var1, int var2, float var3) {
      MatchBlock.b();
      this.drawDefaultBackground();
      this.drawCenteredString(this.fontRendererObj, this.messageLine1, this.width / 2, 70, 16777215);
      byte var5 = 90;
      Iterator var6 = this.listLines2.iterator();
      if(var6.hasNext()) {
         Object var7 = var6.next();
         this.drawCenteredString(this.fontRendererObj, (String)var7, this.width / 2, var5, 16777215);
         int var10000 = var5 + this.fontRendererObj.getHeight();
      }

      super.drawScreen(var1, var2, var3);
   }

   public void setButtonDelay(int var1) {
      this.ticksUntilEnable = var1;
      MatchBlock.b();
      Iterator var3 = this.buttonList.iterator();
      if(var3.hasNext()) {
         GuiButton var4 = (GuiButton)var3.next();
         var4.enabled = false;
      }

   }

   public void updateScreen() {
      MatchBlock.b();
      super.updateScreen();
      if(--this.ticksUntilEnable == 0) {
         Iterator var2 = this.buttonList.iterator();
         if(var2.hasNext()) {
            GuiButton var3 = (GuiButton)var2.next();
            var3.enabled = true;
         }
      }

   }
}
