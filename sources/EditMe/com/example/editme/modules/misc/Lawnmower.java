package com.example.editme.modules.misc;

import com.example.editme.events.EventPlayerMotionUpdate;
import com.example.editme.modules.Module;
import com.example.editme.settings.Setting;
import com.example.editme.util.client.BlockInteractionHelper;
import com.example.editme.util.client.EntityUtil;
import com.example.editme.util.client.PlayerUtil;
import com.example.editme.util.setting.SettingsManager;
import java.util.Comparator;
import java.util.function.Predicate;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;

@Module.Info(
   name = "Lawnmower",
   category = Module.Category.MISC
)
public class Lawnmower extends Module {
   @EventHandler
   private Listener onPlayerMotionUpdate = new Listener(this::lambda$new$2, new Predicate[0]);
   public Setting radius = this.register(SettingsManager.integerBuilder("Radius").withMinimum(1).withMaximum(8).withValue((int)4).build());

   private boolean lambda$null$0(BlockPos var1) {
      return this.IsValidBlockPos(var1);
   }

   private boolean IsValidBlockPos(BlockPos var1) {
      IBlockState var2 = mc.field_71441_e.func_180495_p(var1);
      return var2.func_177230_c() instanceof BlockTallGrass || var2.func_177230_c() instanceof BlockDoublePlant;
   }

   private void lambda$new$2(EventPlayerMotionUpdate var1) {
      BlockPos var2 = (BlockPos)BlockInteractionHelper.getSphere(PlayerUtil.GetLocalPlayerPosFloored(), (float)(Integer)this.radius.getValue(), (Integer)this.radius.getValue(), false, true, 0).stream().filter(this::lambda$null$0).min(Comparator.comparing(Lawnmower::lambda$null$1)).orElse((Object)null);
      if (var2 != null) {
         var1.cancel();
         double[] var3 = EntityUtil.calculateLookAt((double)var2.func_177958_n() + 0.5D, (double)var2.func_177956_o() - 0.5D, (double)var2.func_177952_p() + 0.5D, mc.field_71439_g);
         PlayerUtil.PacketFacePitchAndYaw((float)var3[1], (float)var3[0]);
         mc.field_71439_g.func_184609_a(EnumHand.MAIN_HAND);
         mc.field_71442_b.func_180511_b(var2, EnumFacing.UP);
      }

   }

   private static Double lambda$null$1(BlockPos var0) {
      return EntityUtil.GetDistanceOfEntityToBlock(mc.field_71439_g, var0);
   }
}
