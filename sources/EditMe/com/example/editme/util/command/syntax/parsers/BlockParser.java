package com.example.editme.util.command.syntax.parsers;

import com.example.editme.util.command.syntax.SyntaxChunk;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;
import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;

public class BlockParser extends AbstractParser {
   private static HashMap blockNames = new HashMap();

   public static Object getKeyFromValue(Map var0, Object var1) {
      Iterator var2 = var0.keySet().iterator();

      Object var3;
      do {
         if (!var2.hasNext()) {
            return null;
         }

         var3 = var2.next();
      } while(!var0.get(var3).equals(var1));

      return var3;
   }

   public static String getNameFromBlock(Block var0) {
      return !blockNames.containsValue(var0) ? null : (String)getKeyFromValue(blockNames, var0);
   }

   public String getChunk(SyntaxChunk[] var1, SyntaxChunk var2, String[] var3, String var4) {
      try {
         if (var4 == null) {
            return String.valueOf((new StringBuilder()).append(var2.isHeadless() ? "" : var2.getHead()).append(var2.isNecessary() ? "<" : "[").append(var2.getType()).append(var2.isNecessary() ? ">" : "]"));
         } else {
            HashMap var5 = new HashMap();
            Iterator var6 = blockNames.keySet().iterator();

            while(var6.hasNext()) {
               String var7 = (String)var6.next();
               if (var7.toLowerCase().startsWith(var4.toLowerCase().replace("minecraft:", "").replace("_", ""))) {
                  var5.put(var7, blockNames.get(var7));
               }
            }

            if (var5.isEmpty()) {
               return "";
            } else {
               TreeMap var9 = new TreeMap(var5);
               Entry var10 = var9.firstEntry();
               return ((String)var10.getKey()).substring(var4.length());
            }
         }
      } catch (Exception var8) {
         return "";
      }
   }

   public BlockParser() {
      if (blockNames.isEmpty()) {
         Iterator var1 = Block.field_149771_c.func_148742_b().iterator();

         while(var1.hasNext()) {
            ResourceLocation var2 = (ResourceLocation)var1.next();
            blockNames.put(var2.toString().replace("minecraft:", "").replace("_", ""), Block.field_149771_c.func_82594_a(var2));
         }

      }
   }

   public static Block getBlockFromName(String var0) {
      return !blockNames.containsKey(var0) ? null : (Block)blockNames.get(var0);
   }
}
