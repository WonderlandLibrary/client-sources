package xyz.cucumber.base.interf.config;

import java.util.ArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;
import xyz.cucumber.base.interf.config.ext.ConfigNavButton;
import xyz.cucumber.base.interf.config.ext.panel.Panel;
import xyz.cucumber.base.utils.RenderUtils;
import xyz.cucumber.base.utils.button.CloseButton;
import xyz.cucumber.base.utils.position.PositionUtils;
import xyz.cucumber.base.utils.render.Fonts;

public class ConfigManager {
   private PositionUtils position;
   private PositionUtils configs;
   private PositionUtils openButton;
   public boolean open;
   private ArrayList<ConfigNavButton> buttons = new ArrayList<>();
   private CloseButton closeButton;
   public ConfigNavButton active;
   public GuiTextField search;

   public ConfigManager() {
      this.buttons.clear();
      this.position = new PositionUtils(0.0, 0.0, 400.0, 300.0, 1.0F);
      this.configs = new PositionUtils(2.0, 50.0, 396.0, 248.0, 1.0F);
      this.openButton = new PositionUtils(0.0, 0.0, 25.0, 30.0, 1.0F);
      this.active = new ConfigNavButton(this, 0, "Local", 2.5, 15.0);
      this.buttons.add(this.active);
      this.buttons.add(new ConfigNavButton(this, 1, "Public", 2.5, 15.0));
      this.buttons.add(new ConfigNavButton(this, 2, "Cloud", 2.5, 15.0));
      this.closeButton = new CloseButton(0, 280.0, 5.0, 15.0, 15.0);
      this.search = new GuiTextField(0, Minecraft.getMinecraft().fontRendererObj, 0, 0, 140, 15);
   }

   public void initGui() {
      for (ConfigNavButton b : this.buttons) {
         b.updateValues();
      }

      ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
      this.position.setX(sr.getScaledWidth_double());
      this.position.setY((double)(sr.getScaledHeight() / 2) - this.position.getHeight() / 2.0);
   }

   public void draw(int mouseX, int mouseY) {
      ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
      if (this.open) {
         this.position.setX((this.position.getX() * 20.0 + (sr.getScaledWidth_double() / 2.0 - this.position.getWidth() / 2.0)) / 21.0);
      } else {
         this.position.setX((this.position.getX() * 20.0 + sr.getScaledWidth_double()) / 21.0);
      }

      this.position.setY((double)(sr.getScaledHeight() / 2) - this.position.getHeight() / 2.0);
      this.configs.setX(this.position.getX() + 2.0);
      this.configs.setY(this.position.getY() + 50.0);
      this.openButton.setY(this.position.getY() + this.position.getHeight() / 2.0 - this.openButton.getHeight() / 2.0);
      this.openButton.setX(this.position.getX() - this.openButton.getWidth());
      RenderUtils.drawRoundedRect(this.position.getX(), this.position.getY(), this.position.getX2(), this.position.getY2(), -14671840, 3.0F);
      RenderUtils.drawRoundedRect(this.configs.getX(), this.configs.getY(), this.configs.getX2(), this.configs.getY2(), -15329770, 3.0F);
      RenderUtils.drawRoundedRectWithCorners(
         this.openButton.getX(), this.openButton.getY(), this.openButton.getX2() + 1.0, this.openButton.getY2(), -14671840, 3.0, true, false, true, false
      );
      RenderUtils.drawImage(
         this.openButton.getX() + 1.0,
         this.openButton.getY() + 3.0,
         this.openButton.getWidth(),
         this.openButton.getWidth(),
         new ResourceLocation("client/images/gothaj.png"),
         -1
      );
      RenderUtils.drawImage(this.position.getX() + 2.0, this.position.getY() + 2.0, 18.0, 18.0, new ResourceLocation("client/images/gothaj.png"), -1);
      int i = 0;
      RenderUtils.drawRoundedRect(
         this.position.getX() + this.position.getWidth() / 2.0 - 75.0,
         this.position.getY() + 35.0,
         this.position.getX() + this.position.getWidth() / 2.0 + 75.0,
         this.position.getY() + 48.0,
         -15198184,
         3.0F
      );

      for (ConfigNavButton button : this.buttons) {
         button.getPosition().setX(this.position.getX() + this.position.getWidth() / 2.0 - 75.0 + (double)(50 * i));
         button.getPosition().setY(this.position.getY() + 35.0);
         button.draw(mouseX, mouseY);
         i++;
      }

      this.search.xPosition = (int)(this.position.getX() + this.position.getWidth() / 2.0 - 70.0);
      this.search.yPosition = (int)(this.position.getY() + 4.0);
      this.renderTextField(this.search);
      int x = 0;
      int y = 0;
      System.out.println(this.active.configs);

      for (Panel panel : this.active.configs) {
         if (panel.getName().toLowerCase().contains(this.search.getText().toLowerCase())
            && panel.getCreator().toLowerCase().contains(this.search.getText().toLowerCase())) {
            panel.getPosition().setX(this.configs.getX() + 2.5 + (double)(131 * x));
            panel.getPosition().setY(this.configs.getY() + 2.0 + (double)(41 * y));
            panel.draw(mouseX, mouseY);
            if (++x == 3) {
               x = 0;
               y++;
            }
         }
      }

      this.closeButton.getPosition().setX(this.position.getX() + this.position.getWidth() - 20.0);
      this.closeButton.getPosition().setY(this.position.getY() + 5.0);
      this.closeButton.draw(mouseX, mouseY);
   }

   public void onClick(int mouseX, int mouseY, int b) {
      if (this.openButton.isInside(mouseX, mouseY) && b == 0) {
         this.open = !this.open;
      }

      if (this.open) {
         for (ConfigNavButton button : this.buttons) {
            button.onClick(mouseX, mouseY, b);
         }

         if (this.configs.isInside(mouseX, mouseY)) {
            for (Panel panel : this.active.configs) {
               if (panel.getName().toLowerCase().contains(this.search.getText().toLowerCase())
                  && panel.getCreator().toLowerCase().contains(this.search.getText().toLowerCase())) {
                  panel.onClick(mouseX, mouseY, b);
               }
            }
         }

         if (this.closeButton.getPosition().isInside(mouseX, mouseY) && b == 0) {
            this.open = false;
         }

         this.search.mouseClicked(mouseX, mouseY, b);
      }
   }

   public void onRelease(int mouseX, int mouseY, int b) {
      if (this.open) {
         ;
      }
   }

   public void renderTextField(GuiTextField field) {
      field.setMaxStringLength(25);
      RenderUtils.drawRoundedRect(
         (double)field.xPosition, (double)field.yPosition, (double)(field.xPosition + field.width), (double)(field.yPosition + field.height), -1791806669, 3.0F
      );
      if (field.isFocused()) {
         Fonts.getFont("rb-r").drawString("|", (double)(field.xPosition + 4) + this.spacing(field), (double)(field.yPosition + 6), -1);
      }

      if (field.getText().equals("")) {
         Fonts.getFont("rb-r").drawString("Search...", (double)(field.xPosition + 4), (double)(field.yPosition + 6), -5592406);
      } else {
         int color = -5592406;
         if (field.isFocused()) {
            color = -1;
         }

         Fonts.getFont("rb-r").drawString(field.getText(), (double)(field.xPosition + 4), (double)(field.yPosition + 6), color);
      }
   }

   private double spacing(GuiTextField field) {
      String[] text = field.getText().split("");
      double d = 0.0;
      int i = 0;

      for (String t : text) {
         if (field.getCursorPosition() == i) {
            return d;
         }

         i++;
         d += Fonts.getFont("rb-r").getWidth(t);
      }

      return d;
   }

   public void onKey(int keycode, char chr) {
      if (this.open) {
         this.search.textboxKeyTyped(chr, keycode);
      }
   }
}
