package net;

import net.minecraft.world.storage.IThreadedFileIO;
import net.minecraft.world.storage.ThreadedFileIOBase;

public class Zl {
   public static ThreadedFileIOBase a() {
      return ThreadedFileIOBase.getThreadedIOInstance();
   }

   public static void a(ThreadedFileIOBase var0, IThreadedFileIO var1) {
      var0.queueIO(var1);
   }

   public static void a(ThreadedFileIOBase var0) {
      var0.waitForFinish();
   }
}
