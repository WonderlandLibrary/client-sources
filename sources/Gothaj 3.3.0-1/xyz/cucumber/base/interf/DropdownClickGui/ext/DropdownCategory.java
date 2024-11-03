package xyz.cucumber.base.interf.DropdownClickGui.ext;

import java.util.ArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Mouse;
import xyz.cucumber.base.Client;
import xyz.cucumber.base.module.Category;
import xyz.cucumber.base.module.Mod;
import xyz.cucumber.base.utils.RenderUtils;
import xyz.cucumber.base.utils.position.PositionUtils;
import xyz.cucumber.base.utils.render.Fonts;

public class DropdownCategory implements DropdownButton {
   private Category category;
   public PositionUtils position = new PositionUtils(0.0, 0.0, 110.0, 0.0, 1.0F);
   private double dragX;
   private double dragY;
   private boolean dragging;
   private double scrollY;
   private double temp;
   private ArrayList<DropdownButton> buttons = new ArrayList<>();
   private boolean open = true;
   private double maxHeight = 200.0;

   public DropdownCategory(Category category) {
      this.category = category;
      double h = 0.0;

      for (Mod m : Client.INSTANCE.getModuleManager().getModulesByCategory(category)) {
         DropdownModule button = new DropdownModule(m);
         button.getPosition().setX(this.position.getX() + 5.0);
         button.getPosition().setY(this.position.getY() + 20.0 + h - this.scrollY);
         h += button.getPosition().getHeight() + 2.0;
         this.buttons.add(button);
      }
   }

   @Override
   public void draw(int mouseX, int mouseY) {
      if (this.position.isInside(mouseX, mouseY)) {
         double save = this.position.getHeight() - 20.0;
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

      if (this.dragging) {
         this.position.setX((double)mouseX - this.dragX);
         this.position.setY((double)mouseY - this.dragY);
      }

      this.position.setHeight((this.position.getHeight() * 9.0 + this.getModHeight()) / 10.0);
      RenderUtils.drawOutlinedRoundedRect(this.position.getX(), this.position.getY(), this.position.getX2(), this.position.getY2(), 805306368, 4.0, 2.0);
      RenderUtils.drawRoundedRect(this.position.getX(), this.position.getY(), this.position.getX2(), this.position.getY2(), -300410856, 4.0F);
      RenderUtils.drawImage(
         this.position.getX() + 3.0,
         this.position.getY() + 3.0,
         15.0,
         15.0,
         new ResourceLocation("client/images/" + this.category.name().toLowerCase() + ".png"),
         -1
      );
      Fonts.getFont("rb-b")
         .drawString(
            this.category.name(),
            this.position.getX() + this.position.getWidth() / 2.0 - Fonts.getFont("rb-b").getWidth(this.category.name()) / 2.0,
            this.position.getY() + 8.0,
            -1
         );
      double h = 0.0;
      if (this.open || this.position.getHeight() >= 16.0) {
         RenderUtils.enableScisor();
         RenderUtils.scissor(
            new ScaledResolution(Minecraft.getMinecraft()), this.position.getX() + 5.0, this.position.getY() + 20.0, 100.0, this.position.getHeight() - 21.0
         );

         for (DropdownButton b : this.buttons) {
            DropdownModule button = (DropdownModule)b;
            button.getPosition().setX(this.position.getX() + 5.0);
            button.getPosition().setY(this.position.getY() + 20.0 + h - this.scrollY);
            h += button.getPosition().getHeight() + 1.0;
            button.draw(mouseX, mouseY);
         }

         RenderUtils.disableScisor();
      }
   }

   public double getBigger() {
      double h = 0.0;

      for (DropdownButton b : this.buttons) {
         DropdownModule button = (DropdownModule)b;
         h += button.getPosition().getHeight() + 1.0;
      }

      return h;
   }

   public double getModHeight() {
      if (!this.open) {
         return 20.0;
      } else {
         double h = 0.0;

         for (DropdownButton b : this.buttons) {
            DropdownModule button = (DropdownModule)b;
            h += button.getPosition().getHeight() + 1.0;
         }

         return h >= 178.0 ? this.maxHeight : h + 20.0 + 2.0;
      }
   }

   @Override
   public void onClick(int mouseX, int mouseY, int button) {
      if (this.position.isInside(mouseX, mouseY)) {
         if ((double)mouseX > this.position.getX()
            && this.position.getX2() > (double)mouseX
            && (double)mouseY > this.position.getY() + 15.0
            && (double)mouseY < this.position.getY2()) {
            for (DropdownButton b : this.buttons) {
               DropdownModule but = (DropdownModule)b;
               if (but.getPosition().isInside(mouseX, mouseY)) {
                  but.onClick(mouseX, mouseY, button);
                  return;
               }
            }
         }

         if (button == 0) {
            this.dragging = true;
            this.dragX = (double)mouseX - this.position.getX();
            this.dragY = (double)mouseY - this.position.getY();
         }

         if (button == 1) {
            this.open = !this.open;
         }
      }
   }

   @Override
   public void onRelease(int mouseX, int mouseY, int button) {
      this.dragging = false;

      for (DropdownButton b : this.buttons) {
         DropdownModule but = (DropdownModule)b;
         but.onRelease(mouseX, mouseY, button);
      }
   }

   @Override
   public void onKey(char chr, int key) {
      for (DropdownButton b : this.buttons) {
         DropdownModule but = (DropdownModule)b;
         but.onKey(chr, key);
      }
   }

   public Category getCategory() {
      return this.category;
   }

   public void setCategory(Category category) {
      this.category = category;
   }

   public boolean isOpen() {
      return this.open;
   }

   public void setOpen(boolean open) {
      this.open = open;
   }

   public double getMaxHeight() {
      return this.maxHeight;
   }

   public void setMaxHeight(double maxHeight) {
      this.maxHeight = maxHeight;
   }

   public PositionUtils getPosition() {
      return this.position;
   }
}
