package xyz.cucumber.base.interf.DropdownClickGui.ext.Settings;

import java.util.LinkedHashMap;
import java.util.Map.Entry;
import xyz.cucumber.base.module.settings.ModeSettings;
import xyz.cucumber.base.module.settings.ModuleSettings;
import xyz.cucumber.base.utils.RenderUtils;
import xyz.cucumber.base.utils.position.PositionUtils;
import xyz.cucumber.base.utils.render.Fonts;

public class DropdownClickGuiMode extends DropdownClickGuiSettings {
   private ModeSettings settings;
   private double defheight = 12.0;
   private LinkedHashMap<String, PositionUtils> modes = new LinkedHashMap<>();

   public DropdownClickGuiMode(ModuleSettings settings, PositionUtils position) {
      this.settings = (ModeSettings)settings;
      this.position = position;
      this.mainSetting = settings;
      this.modes.clear();

      for (String mode : this.settings.getModes()) {
         this.modes.put(mode, new PositionUtils(0.0, 0.0, 1.0, 9.0, 1.0F));
      }
   }

   @Override
   public void draw(int mouseX, int mouseY) {
      Fonts.getFont("rb-m-13").drawString(this.settings.getName(), this.position.getX() + 3.0, this.position.getY() + 4.0, -1);
      double x = Fonts.getFont("rb-m-13").getWidth(this.settings.getName()) + 5.0 + 3.0;
      int y = 0;

      for (Entry<String, PositionUtils> pos : this.modes.entrySet()) {
         pos.getValue().setWidth(Fonts.getFont("rb-m-13").getWidth(pos.getKey()) + 2.0);
         if (x + Fonts.getFont("rb-m-13").getWidth(pos.getKey()) + 3.0 <= 100.0) {
            pos.getValue().setX(this.position.getX() + x);
            pos.getValue().setY(this.position.getY() + 1.0 + (double)(y * 10));
            x += Fonts.getFont("rb-m-13").getWidth(pos.getKey()) + 3.0;
         } else {
            x = 3.0;
            y++;
            pos.getValue().setX(this.position.getX() + x);
            pos.getValue().setY(this.position.getY() + 1.0 + (double)(y * 10));
            x += Fonts.getFont("rb-m-13").getWidth(pos.getKey()) + 3.0;
         }

         int color = -13354432;
         if (this.settings.getMode().equals(pos.getKey())) {
            color = -10402868;
         }

         RenderUtils.drawRoundedRect(pos.getValue().getX(), pos.getValue().getY(), pos.getValue().getX2(), pos.getValue().getY2(), color, 1.0F);
         Fonts.getFont("rb-m-13").drawString(pos.getKey(), pos.getValue().getX() + 2.0, pos.getValue().getY() + 3.0, -1);
      }

      this.position.setHeight(this.defheight + (double)(10 * y) + 4.0);
   }

   @Override
   public void onClick(int mouseX, int mouseY, int button) {
      for (Entry<String, PositionUtils> pos : this.modes.entrySet()) {
         if (pos.getValue().isInside(mouseX, mouseY) && button == 0) {
            this.settings.setMode(pos.getKey());
         }
      }
   }

   @Override
   public void onRelease(int mouseX, int mouseY, int button) {
   }

   @Override
   public void onKey(char chr, int key) {
   }
}
