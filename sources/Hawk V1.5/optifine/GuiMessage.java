package optifine;

import com.google.common.collect.Lists;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiOptionButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;

public class GuiMessage extends GuiScreen {
   private final List listLines2 = Lists.newArrayList();
   protected String confirmButtonText;
   private GuiScreen parentScreen;
   private String messageLine1;
   private String messageLine2;
   private int ticksUntilEnable;

   public void setButtonDelay(int var1) {
      this.ticksUntilEnable = var1;

      GuiButton var2;
      for(Iterator var3 = this.buttonList.iterator(); var3.hasNext(); var2.enabled = false) {
         var2 = (GuiButton)var3.next();
      }

   }

   protected void actionPerformed(GuiButton var1) throws IOException {
      Config.getMinecraft().displayGuiScreen(this.parentScreen);
   }

   public void drawScreen(int var1, int var2, float var3) {
      this.drawDefaultBackground();
      this.drawCenteredString(this.fontRendererObj, this.messageLine1, this.width / 2, 70, 16777215);
      int var4 = 90;

      for(Iterator var5 = this.listLines2.iterator(); var5.hasNext(); var4 += this.fontRendererObj.FONT_HEIGHT) {
         String var6 = (String)var5.next();
         this.drawCenteredString(this.fontRendererObj, var6, this.width / 2, var4, 16777215);
      }

      super.drawScreen(var1, var2, var3);
   }

   public void updateScreen() {
      super.updateScreen();
      GuiButton var1;
      if (--this.ticksUntilEnable == 0) {
         for(Iterator var2 = this.buttonList.iterator(); var2.hasNext(); var1.enabled = true) {
            var1 = (GuiButton)var2.next();
         }
      }

   }

   public void initGui() {
      this.buttonList.add(new GuiOptionButton(0, this.width / 2 - 74, this.height / 6 + 96, this.confirmButtonText));
      this.listLines2.clear();
      this.listLines2.addAll(this.fontRendererObj.listFormattedStringToWidth(this.messageLine2, this.width - 50));
   }

   public GuiMessage(GuiScreen var1, String var2, String var3) {
      this.parentScreen = var1;
      this.messageLine1 = var2;
      this.messageLine2 = var3;
      this.confirmButtonText = I18n.format("gui.done");
   }
}
