package xyz.cucumber.base.module.feat.player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import xyz.cucumber.base.events.EventListener;
import xyz.cucumber.base.events.ext.EventLook;
import xyz.cucumber.base.events.ext.EventMotion;
import xyz.cucumber.base.events.ext.EventMoveFlying;
import xyz.cucumber.base.events.ext.EventRenderRotation;
import xyz.cucumber.base.events.ext.EventTick;
import xyz.cucumber.base.module.Category;
import xyz.cucumber.base.module.Mod;
import xyz.cucumber.base.module.ModuleInfo;
import xyz.cucumber.base.utils.math.RotationUtils;

@ModuleInfo(
   category = Category.PLAYER,
   description = "Automaticaly breaks blocks trough walls",
   name = "Breaker"
)
public class BreakerModule extends Mod {
   public boolean canWork;
   public BlockPos pos;

   @EventListener
   public void onTick(EventTick e) {
      int detectionRange = 3;
      boolean found = false;

      for (int x = (int)this.mc.thePlayer.posX - detectionRange; (double)x <= this.mc.thePlayer.posX + (double)detectionRange; x++) {
         for (int y = (int)this.mc.thePlayer.posY - detectionRange; (double)y <= this.mc.thePlayer.posY + (double)detectionRange; y++) {
            for (int z = (int)this.mc.thePlayer.posZ - detectionRange; (double)z <= this.mc.thePlayer.posZ + (double)detectionRange; z++) {
               BlockPos position = new BlockPos(x, y, z);
               Block block = this.mc.theWorld.getBlockState(position).getBlock();
               List<Block> blocks = new ArrayList<>(Arrays.asList(Blocks.bed, Blocks.cake, Blocks.dragon_egg));
               if (blocks.contains(block)) {
                  this.pos = position;
                  float[] rots = RotationUtils.getRotationFromPosition((double)x, (double)y, (double)z);
                  this.canWork = true;
                  RotationUtils.customRots = true;
                  RotationUtils.serverYaw = rots[0];
                  RotationUtils.serverPitch = rots[1];
                  found = true;
               }
            }
         }
      }

      if (!found && RotationUtils.customRots && this.canWork) {
         this.pos = null;
         this.mc.gameSettings.keyBindAttack.pressed = false;
         this.canWork = false;
         RotationUtils.customRots = false;
         RotationUtils.serverYaw = this.mc.thePlayer.rotationYaw;
         RotationUtils.serverPitch = this.mc.thePlayer.rotationPitch;
      }
   }

   @EventListener
   public void onMoveFlying(EventMoveFlying e) {
      if (RotationUtils.customRots) {
         e.setYaw(RotationUtils.serverYaw);
      }
   }

   @EventListener
   public void onRotationRender(EventRenderRotation e) {
      if (RotationUtils.customRots) {
         e.setYaw(RotationUtils.serverYaw);
      }
   }

   @EventListener
   public void onLook(EventLook e) {
      if (RotationUtils.customRots) {
         int detectionRange = 3;
         e.setYaw(RotationUtils.serverYaw);
         if (this.pos == null) {
            return;
         }

         Block block = this.mc.theWorld.getBlockState(this.pos).getBlock();
         List<Block> blocks = new ArrayList<>(Arrays.asList(Blocks.bed, Blocks.cake, Blocks.dragon_egg));
         if (blocks.contains(block)) {
            this.mc.objectMouseOver.blockPos = this.pos;
         }
      }
   }

   @EventListener
   public void onMotion(EventMotion e) {
      if (RotationUtils.customRots) {
         e.setYaw(RotationUtils.serverYaw);
         e.setPitch(RotationUtils.serverPitch);
      }

      int detectionRange = 3;

      for (int x = (int)this.mc.thePlayer.posX - detectionRange; (double)x <= this.mc.thePlayer.posX + (double)detectionRange; x++) {
         for (int y = (int)this.mc.thePlayer.posY - detectionRange; (double)y <= this.mc.thePlayer.posY + (double)detectionRange; y++) {
            for (int z = (int)this.mc.thePlayer.posZ - detectionRange; (double)z <= this.mc.thePlayer.posZ + (double)detectionRange; z++) {
               BlockPos position = new BlockPos(x, y, z);
               Block block = this.mc.theWorld.getBlockState(position).getBlock();
               List<Block> blocks = new ArrayList<>(Arrays.asList(Blocks.bed, Blocks.cake, Blocks.dragon_egg));
               if (blocks.contains(block)) {
                  this.mc.gameSettings.keyBindAttack.pressed = true;
                  this.mc.objectMouseOver.blockPos = position;
               }
            }
         }
      }
   }
}
