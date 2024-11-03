package xyz.cucumber.base.interf.clickgui.navbar;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import xyz.cucumber.base.interf.clickgui.navbar.ex.CategoryButton;
import xyz.cucumber.base.interf.clickgui.navbar.ex.ClientButtons;
import xyz.cucumber.base.interf.clickgui.navbar.ex.NavbarButtons;
import xyz.cucumber.base.module.Category;
import xyz.cucumber.base.utils.RenderUtils;
import xyz.cucumber.base.utils.position.PositionUtils;
import xyz.cucumber.base.utils.render.BloomUtils;
import xyz.cucumber.base.utils.render.Fonts;
import xyz.cucumber.base.utils.render.StencilUtils;

public class Navbar {
   private PositionUtils position;
   public List<CategoryButton> categories = new ArrayList<>();
   public List<ClientButtons> client = new ArrayList<>();
   public NavbarButtons active;
   private double yanimation;
   BloomUtils bloom = new BloomUtils();

   public Navbar(PositionUtils position) {
      this.position = position;
      this.categories.clear();

      Category[] var5;
      for (Category c : var5 = Category.values()) {
         this.categories.add(new CategoryButton(c, this));
      }

      this.active = this.categories.get(0);
      this.client.add(new ClientButtons("Configs", this));
   }

   public void draw(int mouseX, int mouseY) {
      StencilUtils.initStencil();
      GL11.glEnable(2960);
      StencilUtils.bindWriteStencilBuffer();
      RenderUtils.drawRoundedRect(this.position.getX(), this.position.getY(), this.position.getX2(), this.position.getY2(), -14342363, 1.0F);
      StencilUtils.bindReadStencilBuffer(1);
      RenderUtils.drawRect(this.position.getX(), this.position.getY(), this.position.getX2(), this.position.getY2(), -299555035);
      RenderUtils.drawRect(this.position.getX(), this.position.getY2() - 25.0, this.position.getX2(), this.position.getY2(), -14540254);
      RenderUtils.drawRect(this.position.getY(), this.position.getY(), this.position.getX2(), this.position.getY() + 25.0, -14540254);
      RenderUtils.drawCircle(this.position.getX() + 14.0, this.position.getY() + 12.0, 9.0, -15132391, 5.0);
      RenderUtils.drawImage(
         this.position.getX() + 14.0 - 9.0, this.position.getY() + 12.0 - 8.0, 18.0, 18.0, new ResourceLocation("client/images/gothaj.png"), -1
      );
      Fonts.getFont("rb-b")
         .drawString(
            "GOTHAJ", this.position.getX() + 14.0 + 18.0 + 5.0, this.position.getY() + 25.0 - Fonts.getFont("rb-b").getWidth("GOTHAJ") / 2.0, -12424715
         );
      double i = 15.0;
      RenderUtils.drawRoundedRect(
         this.active.getPosition().getX(),
         this.yanimation,
         this.active.getPosition().getX2(),
         this.yanimation + this.active.getPosition().getHeight(),
         -12424715,
         2.0F
      );
      i += this.drawCategory(mouseY, mouseY, this.position.getX(), this.position.getY() + 30.0 + i, this.position.getX2()) + 10.0;
      this.drawClientButtons(mouseY, mouseY, this.position.getX(), this.position.getY() + 30.0 + i, this.position.getX2());
      StencilUtils.uninitStencilBuffer();
   }

   public void clicked(int mouseX, int mouseY, int button) {
      if (this.position.isInside(mouseX, mouseY)) {
         for (CategoryButton c : this.categories) {
            c.clicked(mouseX, mouseY, button);
         }

         for (NavbarButtons c : this.client) {
            c.clicked(mouseX, mouseY, button);
         }
      }
   }

   public PositionUtils getPosition() {
      return this.position;
   }

   public void setPosition(PositionUtils position) {
      this.position = position;
   }

   private double drawCategory(int mouseX, int mouseY, double startX, double startY, double endX) {
      double s = 0.0;
      double w = endX - startX - 10.0;
      RenderUtils.drawLine(startX + 5.0, startY, startX + 5.0 + w / 2.0 - Fonts.getFont("rb-r").getWidth("Category") / 2.0 - 2.0, startY, -5592406, 1.0F);
      RenderUtils.drawLine(startX + 5.0 + w / 2.0 + Fonts.getFont("rb-r").getWidth("Category") / 2.0 + 2.0, startY, endX - 5.0, startY, -5592406, 1.0F);
      Fonts.getFont("rb-r").drawString("Category", startX + 5.0 + w / 2.0 - Fonts.getFont("rb-r").getWidth("Category") / 2.0, startY - 2.5, -5592406);
      s += 5.0;
      this.yanimation = (this.yanimation * 9.0 + this.active.getPosition().getY()) / 10.0;

      for (CategoryButton c : this.categories) {
         c.getPosition().setX(startX + 5.0);
         c.getPosition().setY(startY + s);
         c.draw(mouseX, mouseY);
         s += c.getPosition().getHeight();
      }

      return s;
   }

   private double drawClientButtons(int mouseX, int mouseY, double startX, double startY, double endX) {
      double s = 0.0;
      double w = endX - startX - 10.0;
      RenderUtils.drawLine(startX + 5.0, startY, startX + 5.0 + w / 2.0 - Fonts.getFont("rb-r").getWidth("Client") / 2.0 - 2.0, startY, -5592406, 1.0F);
      RenderUtils.drawLine(startX + 5.0 + w / 2.0 + Fonts.getFont("rb-r").getWidth("Client") / 2.0 + 2.0, startY, endX - 5.0, startY, -5592406, 1.0F);
      Fonts.getFont("rb-r").drawString("Client", startX + 5.0 + w / 2.0 - Fonts.getFont("rb-r").getWidth("Client") / 2.0, startY - 2.5, -5592406);
      s += 5.0;
      this.yanimation = (this.yanimation * 9.0 + this.active.getPosition().getY()) / 10.0;

      for (ClientButtons c : this.client) {
         c.getPosition().setX(startX + 5.0);
         c.getPosition().setY(startY + s);
         c.draw(mouseX, mouseY);
         s += c.getPosition().getHeight();
      }

      return s;
   }
}
