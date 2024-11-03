package net.augustus.modules.render;

import java.awt.Color;
import net.augustus.modules.Categorys;
import net.augustus.modules.Module;
import net.augustus.settings.StringValue;

public class ItemEsp extends Module {
   public StringValue mode = new StringValue(1, "Mode", this, "Vanilla", new String[]{"Vanilla"});

   public ItemEsp() {
      super("ItemESP", Color.RED, Categorys.RENDER);
   }
}
