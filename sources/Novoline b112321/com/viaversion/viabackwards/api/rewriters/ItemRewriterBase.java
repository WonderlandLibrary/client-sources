package com.viaversion.viabackwards.api.rewriters;

import com.github.steveice10.opennbt.conversion.builtin.CompoundTagConverter;
import com.github.steveice10.opennbt.tag.builtin.CompoundTag;
import com.github.steveice10.opennbt.tag.builtin.ListTag;
import com.github.steveice10.opennbt.tag.builtin.StringTag;
import com.github.steveice10.opennbt.tag.builtin.Tag;
import com.viaversion.viabackwards.api.BackwardsProtocol;
import net.aMz;
import net.aqu;
import org.jetbrains.annotations.Nullable;

public abstract class ItemRewriterBase extends aqu {
   protected static final CompoundTagConverter d = new CompoundTagConverter();
   protected final String nbtTagName;
   protected final boolean jsonNameFormat;
   private static boolean e;

   protected ItemRewriterBase(BackwardsProtocol var1, boolean var2) {
      super(var1);
      this.jsonNameFormat = var2;
      this.nbtTagName = "VB|" + var1.getClass().getCanonicalName();
   }

   @Nullable
   public aMz a(aMz var1) {
      int var2 = aqu.d();
      return null;
   }

   @Nullable
   public aMz c(aMz var1) {
      int var2 = aqu.e();
      return null;
   }

   protected void a(CompoundTag var1, StringTag var2) {
      var1.put(new StringTag(this.nbtTagName + "|o" + var2.getName(), var2.getValue()));
   }

   protected void a(CompoundTag var1, ListTag var2) {
      var1.put(new ListTag(this.nbtTagName + "|o" + var2.getName(), var2.getValue()));
   }

   protected void b(aMz var1) {
      int var2 = aqu.d();
      if(var1.d() != null) {
         CompoundTag var3 = (CompoundTag)var1.d().get("display");
         if(var3 != null) {
            if(var3.remove(this.nbtTagName + "|customName") != null) {
               var3.remove("Name");
            }

            this.a(var3, "Name");
            this.a(var3, "Lore");
         }

      }
   }

   protected void a(CompoundTag var1, String var2) {
      aqu.d();
      Tag var4 = var1.remove(this.nbtTagName + "|o" + var2);
      if(var4 != null) {
         var1.put(var4);
      }

   }

   static {
      a(false);
   }

   public static void a(boolean var0) {
      e = var0;
   }

   public static boolean b() {
      return e;
   }

   public static boolean a() {
      boolean var0 = b();
      return true;
   }
}
