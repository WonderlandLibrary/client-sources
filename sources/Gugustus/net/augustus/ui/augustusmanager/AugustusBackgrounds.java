package net.augustus.ui.augustusmanager;

import java.awt.Color;
import java.io.IOException;
import net.augustus.Augustus;
import net.augustus.ui.widgets.BGShaderButton;
import net.augustus.ui.widgets.CustomButton;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.opengl.GL11;

public class AugustusBackgrounds extends GuiScreen {
   private GuiScreen parent;

   public AugustusBackgrounds(GuiScreen parent) {
      this.parent = parent;
   }

   public GuiScreen start(GuiScreen parent) {
      this.parent = parent;
      return this;
   }

   @Override
   public void initGui() {
      super.initGui();
      ScaledResolution sr = new ScaledResolution(this.mc);
      this.buttonList
         .add(
            new CustomButton(
               1, sr.getScaledWidth() / 2 - 100, sr.getScaledHeight() - sr.getScaledHeight() / 10, 200, 20, "Back", Augustus.getInstance().getClientColor()
            )
         );
      int i = 0;
      int j = 2;

      for(String s : Augustus.getInstance().getBackgroundShaderUtil().getShaderNames()) {
         float x = (float)sr.getScaledWidth() / 2.0F - (float)this.fontRendererObj.getStringWidth(s) / 2.0F;
         float y = (float)(60 + i);
         float widthh = (float)this.fontRendererObj.getStringWidth(s);
         float heightt = 11.0F;
         this.buttonList.add(new BGShaderButton(j, (int)x, (int)y, (int)widthh, (int)heightt, s, new Color(139, 141, 145, 255), new Color(67, 122, 163, 255)));
         i += 13;
         ++j;
      }
   }

   @Override
   protected void actionPerformed(GuiButton button) throws IOException {
      if (button.id == 1) {
         this.mc.displayGuiScreen(this.parent);
      }

      for(String s : Augustus.getInstance().getBackgroundShaderUtil().getShaderNames()) {
         if (button.displayString.equals(s)) {
            Augustus.getInstance().getBackgroundShaderUtil().setCurrentShader(s);
         }
      }
   }

   @Override
   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
      this.drawBackground(0);
      super.drawScreen(mouseX, mouseY, partialTicks);
      ScaledResolution sr = new ScaledResolution(this.mc);
      GL11.glPushMatrix();
      GL11.glScaled(2.0, 2.0, 1.0);
      this.fontRendererObj
         .drawStringWithShadow(
            "Backgrounds",
            (float)sr.getScaledWidth() / 4.0F - (float)this.fontRendererObj.getStringWidth("Backgrounds") / 2.0F,
            10.0F,
            Color.lightGray.getRGB()
         );
      GL11.glScaled(1.0, 1.0, 1.0);
      GL11.glPopMatrix();
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
