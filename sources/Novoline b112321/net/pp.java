package net;

import com.google.common.util.concurrent.ListenableFuture;
import net.minecraft.util.IThreadListener;

public class pp {
   public static boolean a(IThreadListener var0) {
      return var0.isCallingFromMinecraftThread();
   }

   public static ListenableFuture a(IThreadListener var0, Runnable var1) {
      return var0.addScheduledTask(var1);
   }
}
