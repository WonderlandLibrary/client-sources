package exhibition.module.impl.player;

import exhibition.Wrapper;
import exhibition.event.Event;
import exhibition.event.RegisterEvent;
import exhibition.event.impl.EventMotionUpdate;
import exhibition.event.impl.EventTick;
import exhibition.module.Module;
import exhibition.module.data.ModuleData;
import exhibition.util.misc.BlockUtil;
import net.minecraft.block.BlockAir;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.BlockPos;

public class AutoEagle extends Module {
   public AutoEagle(ModuleData data) {
      super(data);
   }

   @RegisterEvent(
      events = {EventMotionUpdate.class}
   )
   @Override
   public void onEvent(Event event) {
      if ( Wrapper.getWorld().getBlockState(new BlockPos(Wrapper.getPlayer().posX, Wrapper.getPlayer().posY - 1.0D, Wrapper.getPlayer().posZ)).getBlock() instanceof BlockAir) {
         if (Wrapper.getPlayer().onGround) {
            KeyBinding.setKeyBindState(mc.gameSettings.keyBindSneak.getKeyCode(), true);
         }
      } else if (Wrapper.getPlayer().onGround) {
         KeyBinding.setKeyBindState(mc.gameSettings.keyBindSneak.getKeyCode(), false);
      }
   }

   @Override
   public void onDisable(){
      KeyBinding.setKeyBindState(mc.gameSettings.keyBindSneak.getKeyCode(), false);
   }
}
