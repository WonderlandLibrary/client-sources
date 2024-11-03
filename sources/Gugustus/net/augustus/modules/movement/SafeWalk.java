package net.augustus.modules.movement;

import java.awt.Color;
import net.augustus.events.EventSaveWalk;
import net.augustus.modules.Categorys;
import net.augustus.modules.Module;
import net.augustus.settings.BooleanValue;
import net.lenni0451.eventapi.reflection.EventTarget;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.util.BlockPos;

public class SafeWalk extends Module {
   public final BooleanValue inAir = new BooleanValue(1, "InAir", this, true);
   public final BooleanValue minemen = new BooleanValue(2, "Minemen", this, true);

   public SafeWalk() {
      super("SafeWalk", new Color(115, 135, 26), Categorys.MOVEMENT);
   }

   @EventTarget
   public void onEventSaveWalk(EventSaveWalk eventSaveWalk) {
      Block blockUnder1 = mc.theWorld.getBlockState(new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1.0, mc.thePlayer.posZ)).getBlock();
      Block blockUnder2 = mc.theWorld.getBlockState(new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 2.0, mc.thePlayer.posZ)).getBlock();
      if (mc.thePlayer.onGround) {
         eventSaveWalk.setSaveWalk(true);
      } else if (this.inAir.getBoolean() && (blockUnder1 instanceof BlockAir && blockUnder2 instanceof BlockAir || !this.minemen.getBoolean())) {
         eventSaveWalk.setSaveWalk(true);
      }
   }
}
