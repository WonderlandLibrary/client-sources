package com.viaversion.viaversion.api.minecraft.metadata;

import com.viaversion.viaversion.api.minecraft.metadata.MetaType;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import java.util.Objects;

public class Metadata {
   private int id;
   private MetaType metaType;
   private Object value;
   private static PacketRemapper[] d;

   public Metadata(int var1, MetaType var2, Object var3) {
      this.id = var1;
      this.metaType = var2;
      this.value = var3;
   }

   public int id() {
      return this.id;
   }

   public void setId(int var1) {
      this.id = var1;
   }

   public MetaType metaType() {
      return this.metaType;
   }

   public void setMetaTypeUnsafe(MetaType var1) {
      this.metaType = var1;
   }

   public Object getValue() {
      return this.value;
   }

   public Object value() {
      return this.value;
   }

   public void a(Object var1) {
      this.value = var1;
   }

   public boolean equals(Object var1) {
      PacketRemapper[] var2 = b();
      if(this == var1) {
         return true;
      } else if(var1 != null && this.getClass() == var1.getClass()) {
         Metadata var3 = (Metadata)var1;
         return this.id != var3.id?false:(!Objects.equals(this.metaType, var3.metaType)?false:Objects.equals(this.value, var3.value));
      } else {
         return false;
      }
   }

   public int hashCode() {
      b();
      int var2 = this.id;
      var2 = 31 * var2 + (this.metaType != null?this.metaType.hashCode():0);
      var2 = 31 * var2 + (this.value != null?this.value.hashCode():0);
      return var2;
   }

   public String toString() {
      PacketRemapper[] var1 = b();
      String var10000 = "Metadata{id=" + this.id + ", metaType=" + this.metaType + ", value=" + this.value + '}';
      if(PacketRemapper.b() == null) {
         b(new PacketRemapper[5]);
      }

      return var10000;
   }

   public static void b(PacketRemapper[] var0) {
      d = var0;
   }

   public static PacketRemapper[] b() {
      return d;
   }

   static {
      b(new PacketRemapper[4]);
   }
}
