package ninja.leaping.configurate.loader;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.concurrent.Callable;
import net.awr;
import net.uU;

public final class AtomicFiles {
   public static Callable createAtomicWriterFactory(Path var0, Charset var1) {
      Objects.requireNonNull(var0, "path");
      return AtomicFiles::lambda$createAtomicWriterFactory$0;
   }

   public static BufferedWriter createAtomicBufferedWriter(Path var0, Charset var1) throws IOException {
      var0 = var0.toAbsolutePath();
      awr.d();
      Path var3 = getTemporaryPath(var0.getParent(), var0.getFileName().toString());
      if(Files.exists(var0, new LinkOption[0])) {
         Files.copy(var0, var3, new CopyOption[]{StandardCopyOption.COPY_ATTRIBUTES, StandardCopyOption.REPLACE_EXISTING});
      }

      BufferedWriter var4 = Files.newBufferedWriter(var3, var1, new OpenOption[0]);
      return new BufferedWriter(new uU(var3, var0, var4));
   }

   private static Path getTemporaryPath(Path var0, String var1) {
      String var2 = System.nanoTime() + ((String)Objects.requireNonNull(var1, "key")).replaceAll("\\\\|/|:", "-") + ".tmp";
      return var0.resolve(var2);
   }

   private static BufferedWriter lambda$createAtomicWriterFactory$0(Path var0, Charset var1) throws Exception {
      return createAtomicBufferedWriter(var0, var1);
   }

   private static IOException a(IOException var0) {
      return var0;
   }
}
