package com.example.editme.util.command.syntax.parsers;

import com.example.editme.modules.Module;
import com.example.editme.settings.Setting;
import com.example.editme.util.command.syntax.SyntaxChunk;
import com.example.editme.util.module.ModuleManager;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeMap;

public class ValueParser extends AbstractParser {
   int moduleIndex;

   public ValueParser(int var1) {
      this.moduleIndex = var1;
   }

   public String getChunk(SyntaxChunk[] var1, SyntaxChunk var2, String[] var3, String var4) {
      if (this.moduleIndex <= var3.length - 1 && var4 != null) {
         String var5 = var3[this.moduleIndex];
         Module var6 = ModuleManager.getModuleByName(var5);
         if (var6 == null) {
            return "";
         } else {
            HashMap var7 = new HashMap();
            Iterator var8 = var6.settingList.iterator();

            Setting var9;
            while(var8.hasNext()) {
               var9 = (Setting)var8.next();
               if (var9.getName().toLowerCase().startsWith(var4.toLowerCase())) {
                  var7.put(var9.getName(), var9);
               }
            }

            if (var7.isEmpty()) {
               return "";
            } else {
               TreeMap var10 = new TreeMap(var7);
               var9 = (Setting)var10.firstEntry().getValue();
               return var9.getName().substring(var4.length());
            }
         }
      } else {
         return this.getDefaultChunk(var2);
      }
   }
}
