package com.viaversion.viabackwards.api.rewriters;

import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.rewriter.IdRewriteFunction;

public final class MapColorRewriter {
   public static PacketHandler getRewriteHandler(IdRewriteFunction rewriter) {
      return wrapper -> {
         int iconCount = wrapper.passthrough(Type.VAR_INT);

         for (int i = 0; i < iconCount; i++) {
            wrapper.passthrough(Type.VAR_INT);
            wrapper.passthrough(Type.BYTE);
            wrapper.passthrough(Type.BYTE);
            wrapper.passthrough(Type.BYTE);
            wrapper.passthrough(Type.OPTIONAL_COMPONENT);
         }

         short columns = wrapper.passthrough(Type.UNSIGNED_BYTE);
         if (columns >= 1) {
            wrapper.passthrough(Type.UNSIGNED_BYTE);
            wrapper.passthrough(Type.UNSIGNED_BYTE);
            wrapper.passthrough(Type.UNSIGNED_BYTE);
            byte[] data = wrapper.passthrough(Type.BYTE_ARRAY_PRIMITIVE);

            for (int i = 0; i < data.length; i++) {
               int color = data[i] & 255;
               int mappedColor = rewriter.rewrite(color);
               if (mappedColor != -1) {
                  data[i] = (byte)mappedColor;
               }
            }
         }
      };
   }
}
