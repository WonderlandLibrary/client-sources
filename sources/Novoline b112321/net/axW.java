package net;

import cc.novoline.Novoline;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.FileAttribute;
import net.aSv;
import org.apache.commons.io.IOUtils;

public class axW {
   @aSv
   public void writeFile(String param1, String... param2) {
      // $FF: Couldn't be decompiled
   }

   @aSv
   public void createDirectory(String var1) {
      String var2 = Novoline.getInstance().getPathString() + var1;

      try {
         Files.createDirectory(Paths.get(var2, new String[0]), new FileAttribute[0]);
      } catch (IOException var4) {
         var4.printStackTrace();
      }

   }

   @aSv
   public String readFile(String var1) {
      try {
         return IOUtils.toString(new FileReader(Novoline.getInstance().getPathString() + var1));
      } catch (IOException var3) {
         var3.printStackTrace();
         return "CAN\'T READ NIGGER";
      }
   }

   private static IOException a(IOException var0) {
      return var0;
   }
}
