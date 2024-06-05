package net;

import cc.novoline.events.events.EventState;
import java.util.concurrent.ThreadLocalRandom;
import net.UW;
import net.a2V;
import net.aG1;
import net.agu;
import net.as0;
import net.avq;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFurnace;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C07PacketPlayerDigging$Action;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public final class avw extends as0 {
   private float y;
   private boolean x;

   public avw(UW var1) {
      super(var1, "AntiObbyTrap", "Anti Obby Trap", 0, a2V.PLAYER, "Stop being a bot and falling for obby traps");
   }

   @agu
   public void a(aG1 var1) {
      int[] var2 = avq.a();
      if(var1.h().equals(EventState.PRE)) {
         if(this.w.theWorld.getBlockState(new BlockPos(var1.d(), var1.f() + 1.0D, var1.i())).getBlock() == Blocks.obsidian || this.w.theWorld.getBlockState(new BlockPos(var1.d(), var1.f() + 1.0D, var1.i())).getBlock() == Blocks.cobblestone || this.w.theWorld.getBlockState(new BlockPos(var1.d(), var1.f() + 2.0D, var1.i())).getBlock() instanceof BlockFurnace) {
            var1.a(89.0F + ThreadLocalRandom.current().nextFloat());
            Block var3 = this.w.theWorld.getBlockState(new BlockPos(var1.d(), var1.f() - 1.0D, var1.i())).getBlock();
            BlockPos var4 = new BlockPos(var1.d(), var1.f() - 1.0D, var1.i());
            if(this.y == 0.0F) {
               this.x = true;
               this.b(new C07PacketPlayerDigging(C07PacketPlayerDigging$Action.START_DESTROY_BLOCK, var4, EnumFacing.UP));
            }

            this.w.thePlayer.b(var4);
            this.b(new C0APacketAnimation());
            this.y += var3.getPlayerRelativeBlockHardness(this.w.thePlayer, this.w.theWorld, var4);
            this.w.theWorld.sendBlockBreakProgress(this.w.thePlayer.getEntityId(), var4, (int)(this.y * 10.0F) - 1);
            if(this.y >= 1.0F) {
               this.b(new C07PacketPlayerDigging(C07PacketPlayerDigging$Action.STOP_DESTROY_BLOCK, var4, EnumFacing.UP));
               this.w.playerController.c(var4, EnumFacing.UP);
               this.y = 0.0F;
               this.x = false;
            }
         }

         this.y = 0.0F;
         this.x = false;
      }

   }

   public boolean a() {
      return this.x;
   }
}
