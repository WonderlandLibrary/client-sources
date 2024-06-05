package net.minecraft.network;

import net.minecraft.util.IThreadListener;

public class PacketThreadUtil {
   private static final String __OBFID = "CL_00002306";

   public static void func_180031_a(Packet var0, INetHandler var1, IThreadListener var2) {
      if (!var2.isCallingFromMinecraftThread()) {
         var2.addScheduledTask(new Runnable(var0, var1) {
            private static final String __OBFID = "CL_00002305";
            private final INetHandler val$p_180031_1_;
            private final Packet val$p_180031_0_;

            {
               this.val$p_180031_0_ = var1;
               this.val$p_180031_1_ = var2;
            }

            public void run() {
               this.val$p_180031_0_.processPacket(this.val$p_180031_1_);
            }
         });
         throw ThreadQuickExitException.field_179886_a;
      }
   }
}
