package xyz.cucumber.base.interf.DropdownClickGui.ext.Settings;

import net.minecraft.util.ResourceLocation;
import xyz.cucumber.base.module.settings.BooleanSettings;
import xyz.cucumber.base.module.settings.ModuleSettings;
import xyz.cucumber.base.utils.RenderUtils;
import xyz.cucumber.base.utils.position.PositionUtils;
import xyz.cucumber.base.utils.render.Fonts;

public class DropdownClickGuiBoolean extends DropdownClickGuiSettings {
   private BooleanSettings settings;
   private PositionUtils checkMark = new PositionUtils(0.0, 0.0, 8.0, 8.0, 1.0F);
   private double animation;

   public DropdownClickGuiBoolean(ModuleSettings settings, PositionUtils position) {
      this.settings = (BooleanSettings)settings;
      this.position = position;
      this.mainSetting = settings;
   }

   @Override
   public void draw(int mouseX, int mouseY) {
      this.checkMark.setX(this.position.getX2() - 12.0);
      this.checkMark.setY(this.position.getY() + this.position.getHeight() / 2.0 - this.checkMark.getHeight() / 2.0);
      Fonts.getFont("rb-m-13").drawString(this.settings.getName(), this.position.getX() + 3.0, this.position.getY() + 3.0, -1);
      RenderUtils.drawRoundedRectWithCorners(
         this.checkMark.getX(), this.checkMark.getY(), this.checkMark.getX2(), this.checkMark.getY2(), 805306368, 4.0, true, true, true, true
      );
      RenderUtils.drawRoundedRectWithCorners(
         this.checkMark.getX() + this.checkMark.getWidth() / 2.0 - this.animation,
         this.checkMark.getY() + this.checkMark.getHeight() / 2.0 - this.animation,
         this.checkMark.getX() + this.checkMark.getWidth() / 2.0 + this.animation,
         this.checkMark.getY() + this.checkMark.getHeight() / 2.0 + this.animation,
         -13354432,
         4.0 / (this.checkMark.getWidth() / 2.0) * this.animation,
         true,
         true,
         true,
         true
      );
      if (this.settings.isEnabled()) {
         this.animation = (this.animation * 9.0 + this.checkMark.getWidth() / 2.0) / 10.0;
         RenderUtils.drawImage(this.checkMark.getX() + 1.0, this.checkMark.getY() + 1.0, 6.0, 6.0, new ResourceLocation("client/images/check.png"), -1);
      } else {
         this.animation = this.animation * 9.0 / 10.0;
      }
   }

   @Override
   public void onClick(int mouseX, int mouseY, int button) {
      if (this.checkMark.isInside(mouseX, mouseY) && button == 0) {
         this.settings.setEnabled(!this.settings.isEnabled());
      }
   }

   @Override
   public void onRelease(int mouseX, int mouseY, int button) {
   }

   @Override
   public void onKey(char chr, int key) {
   }
}
