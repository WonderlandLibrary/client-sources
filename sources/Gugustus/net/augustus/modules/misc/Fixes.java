package net.augustus.modules.misc;

import java.awt.Color;

import net.augustus.events.EventWorld;
import net.augustus.modules.Categorys;
import net.augustus.modules.Module;
import net.augustus.settings.BooleanValue;
import net.augustus.utils.TimeHelper;
import net.lenni0451.eventapi.reflection.EventTarget;

public class Fixes extends Module {
   public final BooleanValue mouseDelayFix = new BooleanValue(1, "MouseDelayFix", this, true);
   public final BooleanValue hitDelayFix = new BooleanValue(2, "HitDelayFix", this, false);
   public final BooleanValue memoryFix = new BooleanValue(2, "MemoryFix", this, false);
   public final TimeHelper timeHelper = new TimeHelper();

   public Fixes() {
      super("Fixes", new Color(169, 66, 237), Categorys.MISC);
   }

   @EventTarget
   public void onWorldChange(EventWorld eventWorld) {
      System.gc();
   }
}
