package net.minecraft.client.renderer.vertex;

import com.google.common.collect.Lists;
import java.util.Iterator;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class VertexFormat {
   private List field_177351_f;
   private final List field_177355_b;
   private final List field_177356_c;
   private int field_177352_g;
   private int field_177354_e;
   private static final String __OBFID = "CL_00002401";
   private int field_177353_d;
   private static final Logger field_177357_a = LogManager.getLogger();

   public VertexFormatElement func_177348_c(int var1) {
      return (VertexFormatElement)this.field_177355_b.get(var1);
   }

   public boolean func_177347_a(int var1) {
      return this.field_177351_f.size() - 1 >= var1;
   }

   public boolean func_177350_b() {
      return this.field_177352_g >= 0;
   }

   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (var1 != null && this.getClass() == var1.getClass()) {
         VertexFormat var2 = (VertexFormat)var1;
         return this.field_177353_d != var2.field_177353_d ? false : (!this.field_177355_b.equals(var2.field_177355_b) ? false : this.field_177356_c.equals(var2.field_177356_c));
      } else {
         return false;
      }
   }

   public int func_177342_c() {
      return this.field_177352_g;
   }

   public VertexFormat(VertexFormat var1) {
      this();

      for(int var2 = 0; var2 < var1.func_177345_h(); ++var2) {
         this.func_177349_a(var1.func_177348_c(var2));
      }

      this.field_177353_d = var1.func_177338_f();
   }

   private boolean func_177341_i() {
      Iterator var1 = this.field_177355_b.iterator();

      while(var1.hasNext()) {
         VertexFormatElement var2 = (VertexFormatElement)var1.next();
         if (var2.func_177374_g()) {
            return true;
         }
      }

      return false;
   }

   public int func_177345_h() {
      return this.field_177355_b.size();
   }

   public void clear() {
      this.field_177355_b.clear();
      this.field_177356_c.clear();
      this.field_177354_e = -1;
      this.field_177351_f.clear();
      this.field_177352_g = -1;
      this.field_177353_d = 0;
   }

   public int func_177340_e() {
      return this.field_177354_e;
   }

   public String toString() {
      String var1 = String.valueOf((new StringBuilder("format: ")).append(this.field_177355_b.size()).append(" elements: "));

      for(int var2 = 0; var2 < this.field_177355_b.size(); ++var2) {
         var1 = String.valueOf((new StringBuilder(String.valueOf(var1))).append(((VertexFormatElement)this.field_177355_b.get(var2)).toString()));
         if (var2 != this.field_177355_b.size() - 1) {
            var1 = String.valueOf((new StringBuilder(String.valueOf(var1))).append(" "));
         }
      }

      return var1;
   }

   public boolean func_177346_d() {
      return this.field_177354_e >= 0;
   }

   public VertexFormat() {
      this.field_177355_b = Lists.newArrayList();
      this.field_177356_c = Lists.newArrayList();
      this.field_177353_d = 0;
      this.field_177354_e = -1;
      this.field_177351_f = Lists.newArrayList();
      this.field_177352_g = -1;
   }

   public int func_177344_b(int var1) {
      return (Integer)this.field_177351_f.get(var1);
   }

   public void func_177349_a(VertexFormatElement var1) {
      if (var1.func_177374_g() && this.func_177341_i()) {
         field_177357_a.warn("VertexFormat error: Trying to add a position VertexFormatElement when one already exists, ignoring.");
      } else {
         this.field_177355_b.add(var1);
         this.field_177356_c.add(this.field_177353_d);
         var1.func_177371_a(this.field_177353_d);
         this.field_177353_d += var1.func_177368_f();
         switch(var1.func_177375_c()) {
         case NORMAL:
            this.field_177352_g = var1.func_177373_a();
            break;
         case COLOR:
            this.field_177354_e = var1.func_177373_a();
            break;
         case UV:
            this.field_177351_f.add(var1.func_177369_e(), var1.func_177373_a());
         }
      }

   }

   public int hashCode() {
      int var1 = this.field_177355_b.hashCode();
      var1 = 31 * var1 + this.field_177356_c.hashCode();
      var1 = 31 * var1 + this.field_177353_d;
      return var1;
   }

   public List func_177343_g() {
      return this.field_177355_b;
   }

   public int func_177338_f() {
      return this.field_177353_d;
   }

   static final class SwitchEnumUseage {
      static final int[] field_177382_a = new int[VertexFormatElement.EnumUseage.values().length];
      private static final String __OBFID = "CL_00002400";

      static {
         try {
            field_177382_a[VertexFormatElement.EnumUseage.NORMAL.ordinal()] = 1;
         } catch (NoSuchFieldError var3) {
         }

         try {
            field_177382_a[VertexFormatElement.EnumUseage.COLOR.ordinal()] = 2;
         } catch (NoSuchFieldError var2) {
         }

         try {
            field_177382_a[VertexFormatElement.EnumUseage.UV.ordinal()] = 3;
         } catch (NoSuchFieldError var1) {
         }

      }
   }
}
