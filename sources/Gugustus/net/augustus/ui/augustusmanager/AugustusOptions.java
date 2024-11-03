package net.augustus.ui.augustusmanager;

import java.awt.Color;
import java.io.IOException;
import net.augustus.Augustus;
import net.augustus.ui.widgets.CustomButton;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.opengl.GL11;
import viamcp.gui.GuiProtocolSelector;

public class AugustusOptions extends GuiScreen {
   private GuiScreen parent;
   private final AugustusBackgrounds augustusBackgrounds;
   private final AugustusSounds augustusSounds;
   private final AugustusProxy augustusProxy;
   private final OnlineConfigs configs;
   private final CreditsScreen credits;

   public AugustusOptions(GuiScreen parent) {
      this.parent = parent;
      this.augustusBackgrounds = new AugustusBackgrounds(this);
      this.augustusSounds = new AugustusSounds(this);
      this.augustusProxy = new AugustusProxy(this);
      this.configs = new OnlineConfigs(this);
      this.credits = new CreditsScreen(this);
   }

   public GuiScreen start(GuiScreen parent) {
      this.parent = parent;
      return this;
   }

   @Override
   public void initGui() {
      super.initGui();
      ScaledResolution sr = new ScaledResolution(this.mc);
      int scaledWidth = sr.getScaledWidth();
      int scaledHeight = sr.getScaledHeight();
      int startHeight = Math.min(40 + scaledHeight / 7, 135);
      this.buttonList.add(new CustomButton(1, scaledWidth / 2 - 100, startHeight, 200, 20, "Backgrounds", Augustus.getInstance().getClientColor()));
      this.buttonList
         .add(new CustomButton(2, scaledWidth / 2 - 100, scaledHeight - scaledHeight / 10, 200, 20, "Back", Augustus.getInstance().getClientColor()));
      this.buttonList.add(new CustomButton(3, scaledWidth / 2 - 100, startHeight + 30, 200, 20, "SoundOptions", Augustus.getInstance().getClientColor()));
      this.buttonList.add(new CustomButton(4, scaledWidth / 2 - 100, startHeight + 60, 200, 20, "ProxyManager", Augustus.getInstance().getClientColor()));
      this.buttonList.add(new CustomButton(5, scaledWidth / 2 - 100, startHeight + 90, 200, 20, "ViaMCP", Augustus.getInstance().getClientColor()));
      this.buttonList.add(new CustomButton(6, scaledWidth / 2 - 100, startHeight + 120, 200, 20, "Online Configs", Augustus.getInstance().getClientColor()));
      this.buttonList.add(new CustomButton(7, scaledWidth / 2 - 100, startHeight + 150, 200, 20, "Credits", Augustus.getInstance().getClientColor()));
   }

   @Override
   protected void actionPerformed(GuiButton button) throws IOException {
      if (button.id == 5) {
         this.mc.displayGuiScreen(new GuiProtocolSelector(this));
      }

      if (button.id == 1) {
         this.mc.displayGuiScreen(this.augustusBackgrounds.start(this));
      }

      if (button.id == 2) {
         this.mc.displayGuiScreen(this.parent);
      }

      if (button.id == 3) {
         this.mc.displayGuiScreen(this.augustusSounds.start(this));
      }

      if (button.id == 4) {
         this.mc.displayGuiScreen(this.augustusProxy.start(this));
      }

      if (button.id == 6) {
         this.mc.displayGuiScreen(this.configs.start(this));
      }

      if (button.id == 7) {
         this.mc.displayGuiScreen(this.credits.start(this));
      }

      super.actionPerformed(button);
   }

   @Override
   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
      super.drawBackground(0);
      super.drawScreen(mouseX, mouseY, partialTicks);
      ScaledResolution sr = new ScaledResolution(this.mc);
      GL11.glScaled(2.0, 2.0, 1.0);
      this.fontRendererObj
         .drawStringWithShadow(
            "Client Manager",
            (float)sr.getScaledWidth() / 4.0F - (float)this.fontRendererObj.getStringWidth("Client Manager") / 2.0F,
            10.0F,
            Color.lightGray.getRGB()
         );
      GL11.glScaled(1.0, 1.0, 1.0);
   }
}
