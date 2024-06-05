package net;

import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.ThreadQuickExitException;
import net.minecraft.util.IThreadListener;

public class aYh {
   public static void a(Packet var0, INetHandler var1, IThreadListener var2) throws ThreadQuickExitException {
      if(!var2.isCallingFromMinecraftThread()) {
         var2.addScheduledTask(aYh::lambda$checkThreadAndEnqueue$0);
         throw ThreadQuickExitException.field_179886_a;
      }
   }

   private static void lambda$checkThreadAndEnqueue$0(Packet var0, INetHandler var1) {
      var0.processPacket(var1);
   }

   private static ThreadQuickExitException a(ThreadQuickExitException var0) {
      return var0;
   }
}
