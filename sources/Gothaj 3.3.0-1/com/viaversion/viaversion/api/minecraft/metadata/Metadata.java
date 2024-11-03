package com.viaversion.viaversion.api.minecraft.metadata;

import com.google.common.base.Preconditions;
import java.util.Objects;
import org.checkerframework.checker.nullness.qual.Nullable;

public final class Metadata {
   private int id;
   private MetaType metaType;
   private Object value;

   public Metadata(int id, MetaType metaType, @Nullable Object value) {
      this.id = id;
      this.metaType = metaType;
      this.value = this.checkValue(metaType, value);
   }

   public int id() {
      return this.id;
   }

   public void setId(int id) {
      this.id = id;
   }

   public MetaType metaType() {
      return this.metaType;
   }

   public void setMetaType(MetaType metaType) {
      this.checkValue(metaType, this.value);
      this.metaType = metaType;
   }

   @Nullable
   public <T> T value() {
      return (T)this.value;
   }

   @Nullable
   public Object getValue() {
      return this.value;
   }

   public void setValue(@Nullable Object value) {
      this.value = this.checkValue(this.metaType, value);
   }

   public void setTypeAndValue(MetaType metaType, @Nullable Object value) {
      this.value = this.checkValue(metaType, value);
      this.metaType = metaType;
   }

   private Object checkValue(MetaType metaType, @Nullable Object value) {
      Preconditions.checkNotNull(metaType);
      if (value != null && !metaType.type().getOutputClass().isAssignableFrom(value.getClass())) {
         throw new IllegalArgumentException(
            "Metadata value and metaType are incompatible. Type=" + metaType + ", value=" + value + " (" + value.getClass().getSimpleName() + ")"
         );
      } else {
         return value;
      }
   }

   @Deprecated
   public void setMetaTypeUnsafe(MetaType type) {
      this.metaType = type;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         Metadata metadata = (Metadata)o;
         if (this.id != metadata.id) {
            return false;
         } else {
            return this.metaType != metadata.metaType ? false : Objects.equals(this.value, metadata.value);
         }
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      int result = this.id;
      result = 31 * result + this.metaType.hashCode();
      return 31 * result + (this.value != null ? this.value.hashCode() : 0);
   }

   @Override
   public String toString() {
      return "Metadata{id=" + this.id + ", metaType=" + this.metaType + ", value=" + this.value + '}';
   }
}
