package net;

import java.io.File;
import java.io.FilenameFilter;
import net.minecraft.world.chunk.storage.AnvilSaveConverter;

class jv implements FilenameFilter {
   final AnvilSaveConverter a;

   jv(AnvilSaveConverter var1) {
      this.a = var1;
   }

   public boolean accept(File var1, String var2) {
      return var2.endsWith(".mcr");
   }
}
