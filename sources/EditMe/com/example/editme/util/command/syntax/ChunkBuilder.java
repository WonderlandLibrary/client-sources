package com.example.editme.util.command.syntax;

import java.util.ArrayList;
import java.util.List;

public class ChunkBuilder {
   private static final SyntaxChunk[] EXAMPLE = new SyntaxChunk[0];
   List chunks = new ArrayList();

   public ChunkBuilder append(String var1, boolean var2, SyntaxParser var3) {
      SyntaxChunk var4 = new SyntaxChunk(var1, var2);
      var4.setParser(var3);
      this.append(var4);
      return this;
   }

   public ChunkBuilder append(String var1) {
      return this.append(var1, true);
   }

   public ChunkBuilder append(SyntaxChunk var1) {
      this.chunks.add(var1);
      return this;
   }

   public SyntaxChunk[] build() {
      return (SyntaxChunk[])this.chunks.toArray(EXAMPLE);
   }

   public ChunkBuilder append(String var1, boolean var2) {
      this.append(new SyntaxChunk(var1, var2));
      return this;
   }
}
