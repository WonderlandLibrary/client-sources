package com.viaversion.viabackwards.api.entities.storage;

import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;
import net.cQ;
import org.jetbrains.annotations.Nullable;

public class WrappedMetadata {
   private List a;

   public WrappedMetadata(List var1) {
      this.a = var1;
   }

   public boolean has(Metadata var1) {
      return this.a.contains(var1);
   }

   public void remove(Metadata var1) {
      this.a.remove(var1);
   }

   public void remove(int var1) {
      this.a.removeIf(WrappedMetadata::lambda$remove$0);
   }

   public void add(Metadata var1) {
      this.a.add(var1);
   }

   @Nullable
   public Metadata get(int var1) {
      cQ.d();
      Iterator var3 = this.a.iterator();
      if(var3.hasNext()) {
         Metadata var4 = (Metadata)var3.next();
         if(var1 == var4.id()) {
            return var4;
         }
      }

      return null;
   }

   public Metadata a(int var1, Metadata var2) {
      return this.a(var1, false, var2);
   }

   public Metadata a(int var1, boolean var2, Metadata var3) {
      cQ.a();
      Metadata var5 = this.get(var1);
      if(var5 != null) {
         this.remove(var5);
      }

      return var5 != null?var5:var3;
   }

   public List metadataList() {
      return this.a;
   }

   public void a(List var1) {
      this.a = var1;
   }

   public String toString() {
      return "MetaStorage{metaDataList=" + this.a + '}';
   }

   private static boolean lambda$remove$0(int var0, Metadata var1) {
      boolean var2 = cQ.a();
      return var1.id() == var0;
   }
}
