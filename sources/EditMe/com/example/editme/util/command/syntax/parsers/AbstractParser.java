package com.example.editme.util.command.syntax.parsers;

import com.example.editme.util.command.syntax.SyntaxChunk;
import com.example.editme.util.command.syntax.SyntaxParser;

public abstract class AbstractParser implements SyntaxParser {
   public abstract String getChunk(SyntaxChunk[] var1, SyntaxChunk var2, String[] var3, String var4);

   protected String getDefaultChunk(SyntaxChunk var1) {
      return String.valueOf((new StringBuilder()).append(var1.isHeadless() ? "" : var1.getHead()).append(var1.isNecessary() ? "<" : "[").append(var1.getType()).append(var1.isNecessary() ? ">" : "]"));
   }
}
