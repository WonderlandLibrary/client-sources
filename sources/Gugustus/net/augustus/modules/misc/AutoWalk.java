package net.augustus.modules.misc;

import java.awt.Color;
import java.util.ArrayList;
import net.augustus.events.EventTick;
import net.augustus.modules.Categorys;
import net.augustus.modules.Module;
import net.lenni0451.eventapi.reflection.EventTarget;
import net.minecraft.util.BlockPos;

public class AutoWalk extends Module {
   private static final ArrayList<BlockPos> GROUND_BLOCK_POS = new ArrayList<>();
   private static final ArrayList<BlockPos> GROUND_BLOCK_POS_RENDER = new ArrayList<>();
   private Thread thread;

   public AutoWalk() {
      super("AutoWalk", Color.red, Categorys.MISC);
   }

   @Override
   public void onEnable() {
      super.onEnable();
   }

   @Override
   public void onDisable() {
      super.onDisable();
      mc.gameSettings.keyBindForward.pressed = false;
      GROUND_BLOCK_POS.clear();
      GROUND_BLOCK_POS_RENDER.clear();
   }

   @EventTarget
   public void onEventTick(EventTick eventTick) {
      if (mc.currentScreen == null) {
         mc.gameSettings.keyBindForward.pressed = true;
      }
   }
}
