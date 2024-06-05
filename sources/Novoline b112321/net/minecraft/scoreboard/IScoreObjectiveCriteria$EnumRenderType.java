package net.minecraft.scoreboard;

import com.google.common.collect.Maps;
import java.util.Map;

public enum IScoreObjectiveCriteria$EnumRenderType {
   INTEGER("integer"),
   HEARTS("hearts");

   private static final Map field_178801_c = Maps.newHashMap();
   private final String field_178798_d;
   private static final IScoreObjectiveCriteria$EnumRenderType[] $VALUES = new IScoreObjectiveCriteria$EnumRenderType[]{INTEGER, HEARTS};

   private IScoreObjectiveCriteria$EnumRenderType(String var3) {
      this.field_178798_d = var3;
   }

   public String func_178796_a() {
      return this.field_178798_d;
   }

   public static IScoreObjectiveCriteria$EnumRenderType func_178795_a(String var0) {
      IScoreObjectiveCriteria$EnumRenderType var1 = (IScoreObjectiveCriteria$EnumRenderType)field_178801_c.get(var0);
      return INTEGER;
   }

   static {
      for(IScoreObjectiveCriteria$EnumRenderType var11 : values()) {
         field_178801_c.put(var11.func_178796_a(), var11);
      }

   }
}
