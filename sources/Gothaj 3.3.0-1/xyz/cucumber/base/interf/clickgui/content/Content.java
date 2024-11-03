package xyz.cucumber.base.interf.clickgui.content;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import xyz.cucumber.base.interf.clickgui.content.ex.ConfigButton;
import xyz.cucumber.base.interf.clickgui.content.ex.ModuleButton;
import xyz.cucumber.base.interf.clickgui.navbar.Navbar;
import xyz.cucumber.base.interf.clickgui.navbar.ex.CategoryButton;
import xyz.cucumber.base.interf.clickgui.navbar.ex.ClientButtons;
import xyz.cucumber.base.interf.clickgui.navbar.ex.NavbarButtons;
import xyz.cucumber.base.utils.RenderUtils;
import xyz.cucumber.base.utils.position.PositionUtils;
import xyz.cucumber.base.utils.render.StencilUtils;

public class Content {
   private PositionUtils position;
   private Navbar navbar;
   ArrayList<ConfigButton> cfgButtons = new ArrayList<>();
   private List<String> alphabet = new ArrayList<>();
   private double scrollY;
   private double temp;
   public double openAnimation;
   NavbarButtons last = null;

   public Content(PositionUtils position, Navbar navbar) {
      this.position = position;
      this.navbar = navbar;
      this.openAnimation = 0.0;
   }

   public void draw(int mouseX, int mouseY) {
      RenderUtils.drawRoundedRect(this.position.getX(), this.position.getY(), this.position.getX2(), this.position.getY2(), 1880495638, 2.0);
      this.openAnimation = (this.openAnimation * 9.0 + this.position.getWidth() / 2.0) / 10.0;
      GL11.glPushMatrix();
      StencilUtils.initStencil();
      GL11.glEnable(2960);
      StencilUtils.bindWriteStencilBuffer();
      RenderUtils.drawRect(this.position.getX(), this.position.getY(), this.position.getX2(), this.position.getY2(), -1876942816);
      StencilUtils.bindReadStencilBuffer(1);
      if (this.navbar.active instanceof CategoryButton) {
         CategoryButton category = (CategoryButton)this.navbar.active;
         double s = 0.0;

         for (ModuleButton module : category.getModules()) {
            module.getPosition().setX(this.position.getX() + 2.5);
            module.getPosition().setY(this.position.getY() + 2.5 + s - this.scrollY);
            module.draw(mouseX, mouseY);
            s += module.getPosition().getHeight() + 2.5;
         }
      }

      this.last = this.navbar.active;
      StencilUtils.uninitStencilBuffer();
      GL11.glPopMatrix();
      double save = this.position.getHeight();
      if (save < this.getBigger()) {
         float g = (float)Mouse.getEventDWheel();
         double maxScrollY = this.getBigger() - save;
         double size = (double)(Mouse.getDWheel() / 60);
         if (size != 0.0) {
            this.temp += size;
         }

         if (Math.round(this.temp) != 0L) {
            this.temp = this.temp * 9.0 / 10.0;
            double l = this.scrollY;
            this.scrollY = this.scrollY - this.temp;
            if (this.scrollY < 0.0) {
               this.scrollY = 0.0;
            } else if (this.scrollY > maxScrollY) {
               this.scrollY = maxScrollY;
            }
         } else {
            this.temp = 0.0;
         }
      } else {
         this.scrollY = 0.0;
      }
   }

   private String getFileExtension(File file) {
      String name = file.getName();
      int lastIndexOf = name.lastIndexOf(".");
      return lastIndexOf == -1 ? "" : name.substring(lastIndexOf);
   }

   public double getBigger() {
      if (!(this.navbar.active instanceof CategoryButton)) {
         if (this.navbar.active instanceof ClientButtons) {
            ClientButtons clientB = (ClientButtons)this.navbar.active;
            ArrayList<ConfigButton> cfgButtons = new ArrayList<>();
            double h = 0.0;
            double v = 0.0;

            for (ConfigButton cb : cfgButtons) {
               if (++h == 3.0) {
                  h = 0.0;
                  v += cb.getPosition().getHeight() + 2.5;
               }
            }

            return v;
         } else {
            return 0.0;
         }
      } else {
         CategoryButton category = (CategoryButton)this.navbar.active;
         double s = 0.0;

         for (ModuleButton module : category.getModules()) {
            s += module.getPosition().getHeight() + 2.5;
         }

         return s;
      }
   }

   public void clicked(int mouseX, int mouseY, int button) {
      if (this.position.isInside(mouseX, mouseY)) {
         if (this.navbar.active instanceof CategoryButton) {
            CategoryButton category = (CategoryButton)this.navbar.active;

            for (ModuleButton module : category.getModules()) {
               module.clicked(mouseX, mouseY, button);
            }
         }

         if (this.navbar.active instanceof ClientButtons) {
            ClientButtons clientB = (ClientButtons)this.navbar.active;
            double h = 0.0;
            double v = 0.0;

            for (ConfigButton cb : this.cfgButtons) {
               cb.onClick(mouseX, mouseY, button);
            }
         }
      }
   }

   public void released(int mouseX, int mouseY, int button) {
      if (this.navbar.active instanceof CategoryButton) {
         CategoryButton category = (CategoryButton)this.navbar.active;

         for (ModuleButton module : category.getModules()) {
            module.released(mouseX, mouseY, button);
         }
      }
   }

   public void key(char typedChar, int keyCode) {
      if (this.navbar.active instanceof CategoryButton) {
         CategoryButton category = (CategoryButton)this.navbar.active;

         for (ModuleButton module : category.getModules()) {
            module.key(typedChar, keyCode);
         }
      }
   }

   public PositionUtils getPosition() {
      return this.position;
   }

   public void setPosition(PositionUtils position) {
      this.position = position;
   }

   public Navbar getNavbar() {
      return this.navbar;
   }

   public void setNavbar(Navbar navbar) {
      this.navbar = navbar;
   }
}
