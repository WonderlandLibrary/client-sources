package net;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.FileAttribute;
import net.acE;
import net.awr;

class uU extends FilterWriter {
   private final Path b;
   private final Path a;

   protected uU(Path var1, Path var2, Writer var3) {
      super(var3);
      this.a = var1;
      this.b = var2;
   }

   public void close() throws IOException {
      super.close();
      Files.createDirectories(this.b.getParent(), new FileAttribute[0]);
      awr.d();
      Files.move(this.a, this.b, new CopyOption[]{StandardCopyOption.ATOMIC_MOVE, StandardCopyOption.REPLACE_EXISTING});
      if(acE.b() == null) {
         awr.b(false);
      }

   }

   private static IOException a(IOException var0) {
      return var0;
   }
}
