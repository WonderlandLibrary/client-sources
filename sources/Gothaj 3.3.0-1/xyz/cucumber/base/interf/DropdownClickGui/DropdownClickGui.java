package xyz.cucumber.base.interf.DropdownClickGui;

import java.io.IOException;
import java.util.ArrayList;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import xyz.cucumber.base.Client;
import xyz.cucumber.base.interf.DropdownClickGui.ext.DropdownButton;
import xyz.cucumber.base.interf.DropdownClickGui.ext.DropdownCategory;
import xyz.cucumber.base.module.Category;
import xyz.cucumber.base.utils.RenderUtils;
import xyz.cucumber.base.utils.position.PositionUtils;
import xyz.cucumber.base.utils.render.BlurUtils;

public class DropdownClickGui extends GuiScreen {
   private ArrayList<DropdownButton> buttons = new ArrayList<>();
   private double initTimer = 0.0;
   private PositionUtils position = new PositionUtils(0.0, 0.0, 25.0, 25.0, 1.0F);
   private double rotateAnimation;

   public DropdownClickGui() {
      double centerX = 20.0;
      double centerY = 20.0;
      this.buttons.clear();
      int i = 0;

      Category[] var9;
      for (Category c : var9 = Category.values()) {
         DropdownCategory category = new DropdownCategory(c);
         category.getPosition().setX(centerX + (double)(112 * i));
         category.getPosition().setY(centerY);
         category.getPosition().setHeight(20.0);
         this.buttons.add(category);
         i++;
      }
   }

   @Override
   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
      if (this.initTimer < 7.0) {
         this.initTimer += 0.5;
      }

      this.position.setX(10.0);
      this.position.setY((double)(this.height - 35));
      BlurUtils.renderBlur((float)this.initTimer);

      for (DropdownButton b : this.buttons) {
         b.draw(mouseX, mouseY);
      }

      Client.INSTANCE.getClientSettings().draw(mouseX, mouseY);
      RenderUtils.drawCircle(
         this.position.getX() + this.position.getWidth() / 2.0,
         this.position.getY() + this.position.getHeight() / 2.0,
         this.position.getHeight() / 2.0,
         -12877341,
         10.0
      );
      if (Client.INSTANCE.getClientSettings().open) {
         this.rotateAnimation = (this.rotateAnimation * 9.0 + 360.0) / 10.0;
      } else {
         this.rotateAnimation = this.rotateAnimation * 9.0 / 10.0;
      }

      GL11.glPushMatrix();
      GL11.glTranslated(this.position.getX() + 12.5, this.position.getY() + 12.5, 0.0);
      GL11.glRotated(this.rotateAnimation, 0.0, 0.0, 1.0);
      GL11.glTranslated(-this.position.getX() - 12.5, -this.position.getY() - 12.5, 0.0);
      RenderUtils.drawImage(this.position.getX() + 5.0, this.position.getY() + 5.0, 15.0, 15.0, new ResourceLocation("client/images/cs.png"), -1);
      GL11.glPopMatrix();
      super.drawScreen(mouseX, mouseY, partialTicks);
   }

   @Override
   protected void keyTyped(char typedChar, int keyCode) throws IOException {
      super.keyTyped(typedChar, keyCode);
   }

   @Override
   protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
      Client.INSTANCE.getClientSettings().onClick(mouseX, mouseY, mouseButton);
      if (!Client.INSTANCE.getClientSettings().open) {
         if (this.position.isInside(mouseX, mouseY)) {
            Client.INSTANCE.getClientSettings().init();
            Client.INSTANCE.getClientSettings().open = true;
         }

         for (DropdownButton b : this.buttons) {
            b.onClick(mouseX, mouseY, mouseButton);
         }

         super.mouseClicked(mouseX, mouseY, mouseButton);
      }
   }

   @Override
   protected void mouseReleased(int mouseX, int mouseY, int state) {
      Client.INSTANCE.getClientSettings().onRelease(mouseX, mouseY, state);
      if (!Client.INSTANCE.getClientSettings().open) {
         for (DropdownButton b : this.buttons) {
            b.onRelease(mouseX, mouseY, state);
         }

         super.mouseReleased(mouseX, mouseY, state);
      }
   }

   @Override
   public void initGui() {
      this.initTimer = 1.0;
   }

   @Override
   public boolean doesGuiPauseGame() {
      return false;
   }
}
