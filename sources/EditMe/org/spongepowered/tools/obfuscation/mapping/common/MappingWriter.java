package org.spongepowered.tools.obfuscation.mapping.common;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.tools.FileObject;
import javax.tools.StandardLocation;
import javax.tools.Diagnostic.Kind;
import org.spongepowered.tools.obfuscation.mapping.IMappingWriter;

public abstract class MappingWriter implements IMappingWriter {
   private final Messager messager;
   private final Filer filer;

   public MappingWriter(Messager var1, Filer var2) {
      this.messager = var1;
      this.filer = var2;
   }

   protected PrintWriter openFileWriter(String var1, String var2) throws IOException {
      if (var1.matches("^.*[\\\\/:].*$")) {
         File var4 = new File(var1);
         var4.getParentFile().mkdirs();
         this.messager.printMessage(Kind.NOTE, "Writing " + var2 + " to " + var4.getAbsolutePath());
         return new PrintWriter(var4);
      } else {
         FileObject var3 = this.filer.createResource(StandardLocation.CLASS_OUTPUT, "", var1);
         this.messager.printMessage(Kind.NOTE, "Writing " + var2 + " to " + (new File(var3.toUri())).getAbsolutePath());
         return new PrintWriter(var3.openWriter());
      }
   }
}
