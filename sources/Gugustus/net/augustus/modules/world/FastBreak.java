package net.augustus.modules.world;

import java.awt.Color;
import net.augustus.modules.Categorys;
import net.augustus.modules.Module;
import net.augustus.settings.BooleanValue;
import net.augustus.settings.DoubleValue;

public class FastBreak extends Module {
   public BooleanValue instant = new BooleanValue(1, "Instant", this, false);
   public DoubleValue multiplier = new DoubleValue(2, "Multiplier", this, 2.0, 1.0, 2.0, 2);

   public FastBreak() {
      super("FastBreak", new Color(52, 152, 219), Categorys.WORLD);
   }
}
