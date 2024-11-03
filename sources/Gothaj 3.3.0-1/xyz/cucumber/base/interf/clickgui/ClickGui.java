package xyz.cucumber.base.interf.clickgui;

import java.io.IOException;
import net.minecraft.client.gui.GuiScreen;
import xyz.cucumber.base.interf.clickgui.content.Content;
import xyz.cucumber.base.interf.clickgui.navbar.Navbar;
import xyz.cucumber.base.utils.RenderUtils;
import xyz.cucumber.base.utils.position.PositionUtils;
import xyz.cucumber.base.utils.render.BlurUtils;

public class ClickGui extends GuiScreen {
   private PositionUtils guiPosition = new PositionUtils(100.0, 100.0, 350.0, 300.0, 1.0F);
   private Navbar navbar = new Navbar(new PositionUtils(102.5, 102.5, 100.0, 295.0, 1.0F));
   private static Content content;
   private float startTime;

   public ClickGui() {
      content = new Content(new PositionUtils(205.0, 102.5, 242.5, 265.0, 1.0F), this.navbar);
   }

   @Override
   public void initGui() {
      this.startTime = (float)System.nanoTime();
   }

   @Override
   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
      BlurUtils.renderBlur(20.0F);
      RenderUtils.drawRoundedRect(this.guiPosition.getX(), this.guiPosition.getY(), this.guiPosition.getX2(), this.guiPosition.getY2(), -299555035, 1.0F);
      this.navbar.getPosition().setX(this.guiPosition.getX() + 2.5);
      this.navbar.getPosition().setY(this.guiPosition.getY() + 2.5);
      content.getPosition().setX(this.guiPosition.getX() + 5.0 + 100.0);
      content.getPosition().setY(this.guiPosition.getY() + 2.5 + 30.0);
      this.navbar.draw(mouseX, mouseY);
      content.draw(mouseX, mouseY);
      super.drawScreen(mouseX, mouseY, partialTicks);
   }

   @Override
   protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
      this.navbar.clicked(mouseX, mouseY, mouseButton);
      content.clicked(mouseX, mouseY, mouseButton);
      super.mouseClicked(mouseX, mouseY, mouseButton);
   }

   @Override
   protected void mouseReleased(int mouseX, int mouseY, int state) {
      content.released(mouseX, mouseY, state);
      super.mouseReleased(mouseX, mouseY, state);
   }

   @Override
   protected void keyTyped(char typedChar, int keyCode) throws IOException {
      content.key(typedChar, keyCode);
      super.keyTyped(typedChar, keyCode);
   }

   @Override
   public boolean doesGuiPauseGame() {
      return false;
   }

   public static Content getContent() {
      return content;
   }

   public static void setContent(Content content) {
      ClickGui.content = content;
   }
}
