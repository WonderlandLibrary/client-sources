package net.augustus.modules.world;

import java.awt.Color;
import net.augustus.events.EventClick;
import net.augustus.modules.Categorys;
import net.augustus.modules.Module;
import net.augustus.settings.DoubleValue;
import net.augustus.utils.RandomUtil;
import net.augustus.utils.TimeHelper;
import net.lenni0451.eventapi.reflection.EventTarget;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.MovingObjectPosition;
import org.lwjgl.input.Mouse;

public class FastPlace extends Module {
   private final TimeHelper timeHelper = new TimeHelper();
   public DoubleValue delay = new DoubleValue(1, "Delay", this, 50.0, 0.0, 300.0, 0);

   public FastPlace() {
      super("FastPlace", new Color(54, 144, 217), Categorys.WORLD);
   }

   @EventTarget
   public void onEventClick(EventClick eventClick) {
      if (mc.currentScreen == null
         && Mouse.isButtonDown(1)
         && mc.objectMouseOver != null
         && mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK
         && mc.thePlayer.getCurrentEquippedItem() != null
         && mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemBlock) {
         long random = this.delay.getValue() == 0.0 ? 0L : (long)(this.delay.getValue() + (double)RandomUtil.nextLong(-30L, 30L));
         if (this.timeHelper.reached(random)) {
            mc.rightClickMouse();
            this.timeHelper.reset();
         }

         eventClick.setCanceled(true);
      }
   }
}
