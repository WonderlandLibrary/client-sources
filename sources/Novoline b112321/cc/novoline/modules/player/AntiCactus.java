package cc.novoline.modules.player;

import cc.novoline.events.EventTarget;
import cc.novoline.events.events.CollideWithBlockEvent;
import cc.novoline.modules.AbstractModule;
import cc.novoline.modules.EnumModuleType;
import cc.novoline.modules.ModuleManager;
import cc.novoline.modules.player.Freecam;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;

public final class AntiCactus extends AbstractModule {
   public AntiCactus(ModuleManager var1) {
      super(var1, "AntiCactus", "Anti Cactus", EnumModuleType.PLAYER, "");
   }

   @EventTarget
   private void onCollision(CollideWithBlockEvent var1) {
      int[] var2 = Freecam.a();
      if(var1.getBlock() == Blocks.cactus) {
         var1.setBoundingBox(new AxisAlignedBB(var1.getPos(), var1.getPos().a(1, 1, 1)));
      }

   }
}
