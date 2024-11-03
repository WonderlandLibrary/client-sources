package com.viaversion.viaversion.api.minecraft.metadata;

import com.viaversion.viaversion.api.type.Type;

public interface MetaType {
   Type type();

   int typeId();

   static MetaType create(int typeId, Type<?> type) {
      return new MetaType.MetaTypeImpl(typeId, type);
   }

   public static final class MetaTypeImpl implements MetaType {
      private final int typeId;
      private final Type<?> type;

      MetaTypeImpl(int typeId, Type<?> type) {
         this.typeId = typeId;
         this.type = type;
      }

      @Override
      public int typeId() {
         return this.typeId;
      }

      @Override
      public Type<?> type() {
         return this.type;
      }

      @Override
      public String toString() {
         return "MetaType{typeId=" + this.typeId + ", type=" + this.type + '}';
      }

      @Override
      public boolean equals(Object o) {
         if (this == o) {
            return true;
         } else if (o != null && this.getClass() == o.getClass()) {
            MetaType.MetaTypeImpl metaType = (MetaType.MetaTypeImpl)o;
            return this.typeId != metaType.typeId ? false : this.type.equals(metaType.type);
         } else {
            return false;
         }
      }

      @Override
      public int hashCode() {
         int result = this.typeId;
         return 31 * result + this.type.hashCode();
      }
   }
}
