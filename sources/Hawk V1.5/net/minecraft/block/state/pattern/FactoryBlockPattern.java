package net.minecraft.block.state.pattern;

import com.google.common.base.Joiner;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

public class FactoryBlockPattern {
   private final Map field_177666_c = Maps.newHashMap();
   private static final Joiner field_177667_a = Joiner.on(",");
   private final List field_177665_b = Lists.newArrayList();
   private static final String __OBFID = "CL_00002021";
   private int field_177663_d;
   private int field_177664_e;

   private Predicate[][][] func_177658_c() {
      this.func_177657_d();
      Predicate[][][] var1 = (Predicate[][][])Array.newInstance(Predicate.class, new int[]{this.field_177665_b.size(), this.field_177663_d, this.field_177664_e});

      for(int var2 = 0; var2 < this.field_177665_b.size(); ++var2) {
         for(int var3 = 0; var3 < this.field_177663_d; ++var3) {
            for(int var4 = 0; var4 < this.field_177664_e; ++var4) {
               var1[var2][var3][var4] = (Predicate)this.field_177666_c.get(((String[])this.field_177665_b.get(var2))[var3].charAt(var4));
            }
         }
      }

      return var1;
   }

   public BlockPattern build() {
      return new BlockPattern(this.func_177658_c());
   }

   private FactoryBlockPattern() {
      this.field_177666_c.put(' ', Predicates.alwaysTrue());
   }

   private void func_177657_d() {
      ArrayList var1 = Lists.newArrayList();
      Iterator var2 = this.field_177666_c.entrySet().iterator();

      while(var2.hasNext()) {
         Entry var3 = (Entry)var2.next();
         if (var3.getValue() == null) {
            var1.add(var3.getKey());
         }
      }

      if (!var1.isEmpty()) {
         throw new IllegalStateException(String.valueOf((new StringBuilder("Predicates for character(s) ")).append(field_177667_a.join(var1)).append(" are missing")));
      }
   }

   public FactoryBlockPattern where(char var1, Predicate var2) {
      this.field_177666_c.put(var1, var2);
      return this;
   }

   public static FactoryBlockPattern start() {
      return new FactoryBlockPattern();
   }

   public FactoryBlockPattern aisle(String... var1) {
      if (!ArrayUtils.isEmpty(var1) && !StringUtils.isEmpty(var1[0])) {
         if (this.field_177665_b.isEmpty()) {
            this.field_177663_d = var1.length;
            this.field_177664_e = var1[0].length();
         }

         if (var1.length != this.field_177663_d) {
            throw new IllegalArgumentException(String.valueOf((new StringBuilder("Expected aisle with height of ")).append(this.field_177663_d).append(", but was given one with a height of ").append(var1.length).append(")")));
         } else {
            String[] var2 = var1;
            int var3 = var1.length;

            for(int var4 = 0; var4 < var3; ++var4) {
               String var5 = var2[var4];
               if (var5.length() != this.field_177664_e) {
                  throw new IllegalArgumentException(String.valueOf((new StringBuilder("Not all rows in the given aisle are the correct width (expected ")).append(this.field_177664_e).append(", found one with ").append(var5.length()).append(")")));
               }

               char[] var6 = var5.toCharArray();
               int var7 = var6.length;

               for(int var8 = 0; var8 < var7; ++var8) {
                  char var9 = var6[var8];
                  if (!this.field_177666_c.containsKey(var9)) {
                     this.field_177666_c.put(var9, (Object)null);
                  }
               }
            }

            this.field_177665_b.add(var1);
            return this;
         }
      } else {
         throw new IllegalArgumentException("Empty pattern for aisle");
      }
   }
}
