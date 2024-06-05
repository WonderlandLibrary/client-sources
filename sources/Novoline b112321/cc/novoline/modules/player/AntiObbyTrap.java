package cc.novoline.modules.player;

import cc.novoline.events.EventTarget;
import cc.novoline.events.events.EventState;
import cc.novoline.events.events.MotionUpdateEvent;
import cc.novoline.modules.AbstractModule;
import cc.novoline.modules.EnumModuleType;
import cc.novoline.modules.ModuleManager;
import cc.novoline.modules.player.Freecam;
import java.util.concurrent.ThreadLocalRandom;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFurnace;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C07PacketPlayerDigging$Action;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public final class AntiObbyTrap extends AbstractModule {
   private float currentDamage;
   private boolean digging;

   public AntiObbyTrap(ModuleManager var1) {
      super(var1, "AntiObbyTrap", "Anti Obby Trap", 0, EnumModuleType.PLAYER, "Stop being a bot and falling for obby traps");
   }

   @EventTarget
   public void onPre(MotionUpdateEvent var1) {
      int[] var2 = Freecam.a();
      if(var1.getState().equals(EventState.PRE)) {
         if(this.mc.world.getBlockState(new BlockPos(var1.getX(), var1.getY() + 1.0D, var1.getZ())).getBlock() == Blocks.obsidian || this.mc.world.getBlockState(new BlockPos(var1.getX(), var1.getY() + 1.0D, var1.getZ())).getBlock() == Blocks.cobblestone || this.mc.world.getBlockState(new BlockPos(var1.getX(), var1.getY() + 2.0D, var1.getZ())).getBlock() instanceof BlockFurnace) {
            var1.setPitch(89.0F + ThreadLocalRandom.current().nextFloat());
            Block var3 = this.mc.world.getBlockState(new BlockPos(var1.getX(), var1.getY() - 1.0D, var1.getZ())).getBlock();
            BlockPos var4 = new BlockPos(var1.getX(), var1.getY() - 1.0D, var1.getZ());
            if(this.currentDamage == 0.0F) {
               this.digging = true;
               this.sendPacket(new C07PacketPlayerDigging(C07PacketPlayerDigging$Action.START_DESTROY_BLOCK, var4, EnumFacing.UP));
            }

            this.mc.player.updateTool(var4);
            this.sendPacket(new C0APacketAnimation());
            this.currentDamage += var3.getPlayerRelativeBlockHardness(this.mc.player, this.mc.world, var4);
            this.mc.world.sendBlockBreakProgress(this.mc.player.getEntityID(), var4, (int)(this.currentDamage * 10.0F) - 1);
            if(this.currentDamage >= 1.0F) {
               this.sendPacket(new C07PacketPlayerDigging(C07PacketPlayerDigging$Action.STOP_DESTROY_BLOCK, var4, EnumFacing.UP));
               this.mc.at.c(var4, EnumFacing.UP);
               this.currentDamage = 0.0F;
               this.digging = false;
            }
         }

         this.currentDamage = 0.0F;
         this.digging = false;
      }

   }

   public boolean isDigging() {
      return this.digging;
   }
}
