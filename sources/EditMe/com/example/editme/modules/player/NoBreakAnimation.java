package com.example.editme.modules.player;

import com.example.editme.events.PacketEvent;
import com.example.editme.modules.Module;
import java.util.Iterator;
import java.util.function.Predicate;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.client.CPacketPlayerDigging.Action;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

@Module.Info(
   name = "NoBreakAnimation",
   category = Module.Category.PLAYER,
   description = "Prevents block break animation server side"
)
public class NoBreakAnimation extends Module {
   @EventHandler
   public Listener listener = new Listener(this::lambda$new$0, new Predicate[0]);
   private EnumFacing lastFacing = null;
   private BlockPos lastPos = null;
   private boolean isMining = false;

   private void lambda$new$0(PacketEvent.Send var1) {
      if (var1.getPacket() instanceof CPacketPlayerDigging) {
         CPacketPlayerDigging var2 = (CPacketPlayerDigging)var1.getPacket();
         Iterator var3 = mc.field_71441_e.func_72839_b((Entity)null, new AxisAlignedBB(var2.func_179715_a())).iterator();

         while(var3.hasNext()) {
            Entity var4 = (Entity)var3.next();
            if (var4 instanceof EntityEnderCrystal) {
               this.resetMining();
               return;
            }

            if (var4 instanceof EntityLivingBase) {
               this.resetMining();
               return;
            }
         }

         if (var2.func_180762_c().equals(Action.START_DESTROY_BLOCK)) {
            this.isMining = true;
            this.setMiningInfo(var2.func_179715_a(), var2.func_179714_b());
         }

         if (var2.func_180762_c().equals(Action.STOP_DESTROY_BLOCK)) {
            this.resetMining();
         }
      }

   }

   public void onUpdate() {
      if (!mc.field_71474_y.field_74312_F.func_151470_d()) {
         this.resetMining();
      } else {
         if (this.isMining && this.lastPos != null && this.lastFacing != null) {
            mc.field_71439_g.field_71174_a.func_147297_a(new CPacketPlayerDigging(Action.ABORT_DESTROY_BLOCK, this.lastPos, this.lastFacing));
         }

      }
   }

   private void setMiningInfo(BlockPos var1, EnumFacing var2) {
      this.lastPos = var1;
      this.lastFacing = var2;
   }

   public void resetMining() {
      this.isMining = false;
      this.lastPos = null;
      this.lastFacing = null;
   }
}
