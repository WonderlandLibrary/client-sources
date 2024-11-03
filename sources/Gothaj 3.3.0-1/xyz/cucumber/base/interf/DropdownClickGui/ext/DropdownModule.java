package xyz.cucumber.base.interf.DropdownClickGui.ext;

import java.util.ArrayList;
import org.lwjgl.opengl.GL11;
import xyz.cucumber.base.interf.DropdownClickGui.ext.Settings.DropdownClickGuiBoolean;
import xyz.cucumber.base.interf.DropdownClickGui.ext.Settings.DropdownClickGuiColor;
import xyz.cucumber.base.interf.DropdownClickGui.ext.Settings.DropdownClickGuiKeybind;
import xyz.cucumber.base.interf.DropdownClickGui.ext.Settings.DropdownClickGuiMode;
import xyz.cucumber.base.interf.DropdownClickGui.ext.Settings.DropdownClickGuiNumber;
import xyz.cucumber.base.interf.DropdownClickGui.ext.Settings.DropdownClickGuiSettings;
import xyz.cucumber.base.interf.DropdownClickGui.ext.Settings.DropdownClickGuiString;
import xyz.cucumber.base.module.Mod;
import xyz.cucumber.base.module.settings.BooleanSettings;
import xyz.cucumber.base.module.settings.ColorSettings;
import xyz.cucumber.base.module.settings.ModeSettings;
import xyz.cucumber.base.module.settings.ModuleSettings;
import xyz.cucumber.base.module.settings.NumberSettings;
import xyz.cucumber.base.module.settings.StringSettings;
import xyz.cucumber.base.utils.RenderUtils;
import xyz.cucumber.base.utils.position.PositionUtils;
import xyz.cucumber.base.utils.render.ColorUtils;
import xyz.cucumber.base.utils.render.Fonts;
import xyz.cucumber.base.utils.render.StencilUtils;

public class DropdownModule implements DropdownButton {
   private Mod module;
   private double lastX;
   private double lastY;
   public PositionUtils position = new PositionUtils(0.0, 0.0, 100.0, 15.0, 1.0F);
   private boolean open;
   private double defaultHeight = 15.0;
   private double Coloranimation;
   private double openAnimationArrow;
   private double openAnimation;
   private ArrayList<DropdownClickGuiSettings> settings = new ArrayList<>();

   public DropdownModule(Mod module) {
      this.module = module;
      this.settings.add(new DropdownClickGuiKeybind(module, new PositionUtils(0.0, 0.0, 100.0, 12.0, 1.0F)));

      for (ModuleSettings s : module.getSettings()) {
         if (s instanceof ModeSettings) {
            this.settings.add(new DropdownClickGuiMode(s, new PositionUtils(0.0, 0.0, 100.0, 12.0, 1.0F)));
         }

         if (s instanceof BooleanSettings) {
            this.settings.add(new DropdownClickGuiBoolean(s, new PositionUtils(0.0, 0.0, 100.0, 12.0, 1.0F)));
         }

         if (s instanceof StringSettings) {
            this.settings.add(new DropdownClickGuiString(s, new PositionUtils(0.0, 0.0, 100.0, 12.0, 1.0F)));
         }

         if (s instanceof ColorSettings) {
            this.settings.add(new DropdownClickGuiColor(s, new PositionUtils(0.0, 0.0, 100.0, 12.0, 1.0F)));
         }

         if (s instanceof NumberSettings) {
            this.settings.add(new DropdownClickGuiNumber(s, new PositionUtils(0.0, 0.0, 100.0, 15.0, 1.0F)));
         }
      }
   }

   public Mod getModule() {
      return this.module;
   }

   public void setModule(Mod module) {
      this.module = module;
   }

   public PositionUtils getPosition() {
      return this.position;
   }

   public void setPosition(PositionUtils position) {
      this.position = position;
   }

   @Override
   public void draw(int mouseX, int mouseY) {
      this.position.setWidth(100.0);
      int color = -297323283;
      if (this.module.isEnabled()) {
         this.Coloranimation = (this.Coloranimation * 10.0 + 100.0) / 11.0;
      } else {
         this.Coloranimation = this.Coloranimation < 10.0 ? 0.0 : this.Coloranimation - 10.0;
      }

      RenderUtils.drawRoundedRect(this.position.getX(), this.position.getY(), this.position.getX2(), this.position.getY2(), -15527149, 3.0F);
      color = ColorUtils.getAlphaColor(-10402868, (int)this.Coloranimation);
      Fonts.getFont("rb-m")
         .drawString(this.module.getName(), this.position.getX() + 3.0, this.position.getY() + 6.0, ColorUtils.mix(-5592406, color, this.Coloranimation, 100.0));
      this.renderArrow(this.position.getX2() - 7.0, this.position.getY() + 7.5, 4.0, -5592406, this.openAnimationArrow);
      this.position.setHeight((this.position.getHeight() * 8.0 + this.defaultHeight + (this.open ? this.getSettingsHeight() : 0.0)) / 9.0);
      if (!this.open && !(this.position.getHeight() >= this.defaultHeight + 1.0)) {
         this.openAnimationArrow = this.openAnimationArrow * 10.0 / 11.0;
      } else {
         this.openAnimationArrow = (this.openAnimationArrow * 10.0 + 180.0) / 11.0;
         double s = 0.0;
         StencilUtils.initStencil();
         GL11.glEnable(2960);
         StencilUtils.bindWriteStencilBuffer();
         RenderUtils.drawRect(
            this.position.getX(), this.position.getY() + this.defaultHeight, this.position.getX2(), this.position.getY() + this.position.getHeight(), -1
         );
         StencilUtils.bindReadStencilBuffer(1);

         for (DropdownClickGuiSettings set : this.settings) {
            if (set.getMainSetting() == null || set.getMainSetting().getVisibility().get()) {
               set.getPosition().setX(this.position.getX());
               set.getPosition().setY(this.position.getY() + this.defaultHeight + s);
               set.draw(mouseX, mouseY);
               s += set.getPosition().getHeight();
            }
         }

         StencilUtils.uninitStencilBuffer();
      }
   }

   public double getSettingsHeight() {
      double s = 0.0;

      for (DropdownClickGuiSettings set : this.settings) {
         if (set.getMainSetting() == null || set.getMainSetting().getVisibility().get()) {
            s += set.getPosition().getHeight();
         }
      }

      return s;
   }

   @Override
   public void onClick(int mouseX, int mouseY, int button) {
      if (this.position.isInside(mouseX, mouseY)) {
         for (DropdownClickGuiSettings set : this.settings) {
            if ((set.getMainSetting() == null || set.getMainSetting().getVisibility().get()) && set.getPosition().isInside(mouseX, mouseY)) {
               set.onClick(mouseX, mouseY, button);
               return;
            }
         }

         if (button == 0) {
            this.module.toggle();
         }

         if (button == 1) {
            this.open = !this.open;
         }
      }
   }

   @Override
   public void onRelease(int mouseX, int mouseY, int button) {
      for (DropdownClickGuiSettings set : this.settings) {
         if (set.getMainSetting() == null || set.getMainSetting().getVisibility().get()) {
            set.onRelease(mouseX, mouseY, button);
         }
      }
   }

   @Override
   public void onKey(char chr, int key) {
      for (DropdownClickGuiSettings set : this.settings) {
         if (set.getMainSetting() == null || set.getMainSetting().getVisibility().get()) {
            set.onKey(chr, key);
         }
      }
   }

   public void renderArrow(double x, double y, double size, int color, double angle) {
      GL11.glPushMatrix();
      GL11.glTranslated(x, y, 0.0);
      GL11.glRotated(angle, 0.0, 0.0, 1.0);
      GL11.glTranslated(-x, -y, 0.0);
      RenderUtils.start2D();
      RenderUtils.color(color);
      GL11.glBegin(7);
      GL11.glVertex2d(x - size / 3.0, y - size / 3.0);
      GL11.glVertex2d(x, y);
      GL11.glVertex2d(x + size / 3.0, y - size / 3.0);
      GL11.glVertex2d(x, y + size / 3.0);
      GL11.glEnd();
      RenderUtils.stop2D();
      GL11.glRotated(0.0, 0.0, 0.0, 1.0);
      GL11.glPopMatrix();
   }
}
