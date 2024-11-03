package xyz.cucumber.base.module.feat.movement;

import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import xyz.cucumber.base.events.EventListener;
import xyz.cucumber.base.events.ext.EventMotion;
import xyz.cucumber.base.module.Category;
import xyz.cucumber.base.module.Mod;
import xyz.cucumber.base.module.ModuleInfo;
import xyz.cucumber.base.utils.MovementUtils;

@ModuleInfo(
   category = Category.MOVEMENT,
   description = "Speeds you on Ice",
   name = "Ice Speed"
)
public class IceSpeedModule extends Mod {
   @EventListener
   public void onMotion(EventMotion e) {
      if ((
            this.mc.theWorld.getBlockState(new BlockPos(this.mc.thePlayer.posX, this.mc.thePlayer.posY - 1.0, this.mc.thePlayer.posZ)).getBlock() == Blocks.ice
               || this.mc.theWorld.getBlockState(new BlockPos(this.mc.thePlayer.posX, this.mc.thePlayer.posY - 1.0, this.mc.thePlayer.posZ)).getBlock()
                  == Blocks.packed_ice
         )
         && this.mc.thePlayer.onGround
         && MovementUtils.isMoving()
         && this.mc.thePlayer.ticksExisted % 2 == 0) {
         MovementUtils.strafe(0.4F);
      }
   }
}
