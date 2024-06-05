package ninja.leaping.configurate.loader;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Collection;
import java.util.Optional;
import net.Jr;
import net.Rr;
import net.awr;
import ninja.leaping.configurate.loader.CommentHandler;

public enum CommentHandlers implements CommentHandler {
   HASH(new Jr("#")),
   DOUBLE_SLASH(new Jr("//")),
   SLASH_BLOCK(new Rr("/*", "*/", "*")),
   XML_STYLE(new Rr("<!--", "-->", "~"));

   private static final int READAHEAD_LEN = 4096;
   private final CommentHandler delegate;
   private static final CommentHandlers[] $VALUES = new CommentHandlers[]{HASH, DOUBLE_SLASH, SLASH_BLOCK, XML_STYLE};

   private CommentHandlers(CommentHandler var3) {
      this.delegate = var3;
   }

   public Optional extractHeader(BufferedReader var1) throws IOException {
      return this.delegate.extractHeader(var1);
   }

   public Collection toComment(Collection var1) {
      return this.delegate.toComment(var1);
   }

   public static String extractComment(BufferedReader var0, CommentHandler... var1) throws IOException {
      awr.d();
      var0.mark(4096);
      int var4 = var1.length;
      byte var5 = 0;
      if(var5 < var4) {
         CommentHandler var6 = var1[var5];
         Optional var7 = var6.extractHeader(var0);
         if(!var7.isPresent()) {
            var0.reset();
         }

         return (String)var7.get();
      } else {
         return null;
      }
   }

   private static IOException a(IOException var0) {
      return var0;
   }
}
