package com.example.editme.util.command.syntax.parsers;

import com.example.editme.modules.Module;
import com.example.editme.util.command.syntax.SyntaxChunk;
import com.example.editme.util.module.ModuleManager;

public class ModuleParser extends AbstractParser {
   private static boolean lambda$getChunk$0(String var0, Module var1) {
      return var1.getName().toLowerCase().startsWith(var0.toLowerCase());
   }

   public String getChunk(SyntaxChunk[] var1, SyntaxChunk var2, String[] var3, String var4) {
      if (var4 == null) {
         return this.getDefaultChunk(var2);
      } else {
         Module var5 = (Module)ModuleManager.getModules().stream().filter(ModuleParser::lambda$getChunk$0).findFirst().orElse((Object)null);
         return var5 == null ? null : var5.getName().substring(var4.length());
      }
   }
}
