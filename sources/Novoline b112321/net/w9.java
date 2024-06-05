package net;

import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.type.Type;

public abstract class w9 extends Type {
   public w9() {
      super("Chunk", Chunk.class);
   }

   public w9(String var1) {
      super(var1, Chunk.class);
   }

   public Class getBaseClass() {
      return w9.class;
   }
}
