package net.augustus.ui.augustusmanager;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import net.augustus.Augustus;
import net.augustus.ui.widgets.CustomButton;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.opengl.GL11;

public class AugustusSounds extends GuiScreen {
   private GuiScreen parent;
   public static String currentSound;
   private final ArrayList<String> sounds = new ArrayList<>();

   public AugustusSounds(GuiScreen parent) {
      this.parent = parent;
      this.sounds.add("Vanilla");
      this.sounds.add("Sigma");
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
      this.buttonList
         .add(
            new CustomButton(
               1, scaledWidth / 2 - 100, startHeight, 200, 20, Augustus.getInstance().getConverter().readSound(), Augustus.getInstance().getClientColor()
            )
         );
      this.buttonList
         .add(new CustomButton(2, scaledWidth / 2 - 100, scaledHeight - scaledHeight / 10, 200, 20, "Back", Augustus.getInstance().getClientColor()));
   }

   @Override
   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
      super.drawBackground(0);
      super.drawScreen(mouseX, mouseY, partialTicks);
      ScaledResolution sr = new ScaledResolution(this.mc);
      GL11.glScaled(2.0, 2.0, 1.0);
      this.fontRendererObj
         .drawStringWithShadow(
            "Sounds",
            (float)sr.getScaledWidth() / 4.0F - (float)this.fontRendererObj.getStringWidth("Sounds") / 2.0F,
            10.0F,
            Color.lightGray.getRGB()
         );
      GL11.glScaled(1.0, 1.0, 1.0);
   }

   @Override
   protected void actionPerformed(GuiButton button) throws IOException {
      super.actionPerformed(button);
      if (button.id == 1) {
         int id = this.sounds.indexOf(button.displayString) + 1;
         if (id >= this.sounds.size()) {
            id = 0;
         }

         button.displayString = this.sounds.get(id);
         Augustus.getInstance().getConverter().soundSaver(button.displayString);
         currentSound = button.displayString;
      }

      if (button.id == 2) {
         this.mc.displayGuiScreen(this.parent);
      }
   }

   @Override
   protected void keyTyped(char typedChar, int keyCode) throws IOException {
      if (keyCode == 1 && this.mc.theWorld == null) {
         this.mc.displayGuiScreen(this.parent);
      } else {
         super.keyTyped(typedChar, keyCode);
      }
   }
}
