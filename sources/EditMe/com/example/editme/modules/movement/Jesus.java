package com.example.editme.modules.movement;

import com.example.editme.events.AddCollisionBoxToListEvent;
import com.example.editme.events.EditmeEvent;
import com.example.editme.events.PacketEvent;
import com.example.editme.modules.Module;
import com.example.editme.util.client.EntityUtil;
import com.example.editme.util.client.Wrapper;
import com.example.editme.util.module.ModuleManager;
import java.util.function.Predicate;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.block.BlockLiquid;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

@Module.Info(
   name = "Jesus",
   description = "Allows you to walk on water",
   category = Module.Category.MOVEMENT
)
public class Jesus extends Module {
   @EventHandler
   Listener packetEventSendListener = new Listener(Jesus::lambda$new$1, new Predicate[0]);
   @EventHandler
   Listener addCollisionBoxToListEventListener = new Listener(Jesus::lambda$new$0, new Predicate[0]);
   private static final AxisAlignedBB WATER_WALK_AA = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.99D, 1.0D);

   private static boolean isAboveBlock(Entity var0, BlockPos var1) {
      return var0.field_70163_u >= (double)var1.func_177956_o();
   }

   private static boolean isAboveLand(Entity var0) {
      if (var0 == null) {
         return false;
      } else {
         double var1 = var0.field_70163_u - 0.01D;

         for(int var3 = MathHelper.func_76128_c(var0.field_70165_t); var3 < MathHelper.func_76143_f(var0.field_70165_t); ++var3) {
            for(int var4 = MathHelper.func_76128_c(var0.field_70161_v); var4 < MathHelper.func_76143_f(var0.field_70161_v); ++var4) {
               BlockPos var5 = new BlockPos(var3, MathHelper.func_76128_c(var1), var4);
               if (Wrapper.getWorld().func_180495_p(var5).func_177230_c().func_149730_j(Wrapper.getWorld().func_180495_p(var5))) {
                  return true;
               }
            }
         }

         return false;
      }
   }

   private static void lambda$new$0(AddCollisionBoxToListEvent var0) {
      if (mc.field_71439_g != null && var0.getBlock() instanceof BlockLiquid && (EntityUtil.isDrivenByPlayer(var0.getEntity()) || var0.getEntity() == mc.field_71439_g) && !(var0.getEntity() instanceof EntityBoat) && !mc.field_71439_g.func_70093_af() && mc.field_71439_g.field_70143_R < 3.0F && !EntityUtil.isInWater(mc.field_71439_g) && (EntityUtil.isAboveWater(mc.field_71439_g, false) || EntityUtil.isAboveWater(mc.field_71439_g.func_184187_bx(), false)) && isAboveBlock(mc.field_71439_g, var0.getPos())) {
         AxisAlignedBB var1 = WATER_WALK_AA.func_186670_a(var0.getPos());
         if (var0.getEntityBox().func_72326_a(var1)) {
            var0.getCollidingBoxes().add(var1);
         }

         var0.cancel();
      }

   }

   public void onUpdate() {
      if (!ModuleManager.isModuleEnabled("Freecam") && EntityUtil.isInWater(mc.field_71439_g) && !mc.field_71439_g.func_70093_af()) {
         mc.field_71439_g.field_70181_x = 0.1D;
         if (mc.field_71439_g.func_184187_bx() != null && !(mc.field_71439_g.func_184187_bx() instanceof EntityBoat)) {
            mc.field_71439_g.func_184187_bx().field_70181_x = 0.3D;
         }
      }

   }

   private static void lambda$new$1(PacketEvent.Send var0) {
      if (var0.getEra() == EditmeEvent.Era.PRE && var0.getPacket() instanceof CPacketPlayer && EntityUtil.isAboveWater(mc.field_71439_g, true) && !EntityUtil.isInWater(mc.field_71439_g) && !isAboveLand(mc.field_71439_g)) {
         int var1 = mc.field_71439_g.field_70173_aa % 2;
         if (var1 == 0) {
            CPacketPlayer var10000 = (CPacketPlayer)var0.getPacket();
            var10000.field_149477_b += 0.02D;
         }
      }

   }
}
