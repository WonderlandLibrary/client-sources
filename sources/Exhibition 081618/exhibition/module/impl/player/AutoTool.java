package exhibition.module.impl.player;

import exhibition.event.Event;
import exhibition.event.RegisterEvent;
import exhibition.event.impl.EventTick;
import exhibition.module.Module;
import exhibition.module.data.ModuleData;
import exhibition.util.misc.BlockUtil;
import net.minecraft.util.BlockPos;

public class AutoTool extends Module {
   public AutoTool(ModuleData data) {
      super(data);
   }

   @RegisterEvent(
      events = {EventTick.class}
   )
   public void onEvent(Event event) {
      if (mc.gameSettings.keyBindAttack.getIsKeyPressed()) {
         if (mc.objectMouseOver != null) {
            BlockPos pos = mc.objectMouseOver.getBlockPos();
            if (pos != null) {
               BlockUtil.updateTool(pos);
            }
         }
      }
   }
}
