package net.augustus.modules.render;

import java.awt.Color;
import net.augustus.events.EventItemGlint;
import net.augustus.modules.Categorys;
import net.augustus.modules.Module;
import net.augustus.settings.BooleanValue;
import net.augustus.settings.ColorSetting;
import net.augustus.settings.DoubleValue;
import net.lenni0451.eventapi.reflection.EventTarget;

public class CustomGlint extends Module {
   public BooleanValue customColor = new BooleanValue(4, "CustomColor", this, false);
   public ColorSetting color = new ColorSetting(1, "Color", this, new Color(128, 64, 204));
   public BooleanValue removeGlint = new BooleanValue(2, "Remove", this, false);
   public DoubleValue glintSpeed = new DoubleValue(3, "Speed", this, 1.0, 0.01, 10.0, 2);

   public CustomGlint() {
      super("CustomGlint", new Color(128, 64, 204), Categorys.RENDER);
   }

   @EventTarget
   public void onEventItemGlint(EventItemGlint eventItemGlint) {
      if (this.customColor.getBoolean()) {
         eventItemGlint.setColor(this.color.getColor().getRGB());
      }

      eventItemGlint.setCanceled(this.removeGlint.getBoolean());
      eventItemGlint.setSpeed(this.glintSpeed.getValue());
   }
}
