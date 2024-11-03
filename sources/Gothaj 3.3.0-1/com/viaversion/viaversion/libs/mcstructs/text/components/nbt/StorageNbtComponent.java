package com.viaversion.viaversion.libs.mcstructs.text.components.nbt;

import com.viaversion.viaversion.libs.mcstructs.core.Identifier;
import com.viaversion.viaversion.libs.mcstructs.text.ATextComponent;
import com.viaversion.viaversion.libs.mcstructs.text.components.NbtComponent;
import java.util.Objects;

public class StorageNbtComponent extends NbtComponent {
   private final Identifier id;

   public StorageNbtComponent(String component, boolean resolve, Identifier id) {
      super(component, resolve);
      this.id = id;
   }

   public StorageNbtComponent(String component, boolean resolve, ATextComponent separator, Identifier id) {
      super(component, resolve, separator);
      this.id = id;
   }

   public Identifier getId() {
      return this.id;
   }

   @Override
   public ATextComponent copy() {
      return this.getSeparator() == null
         ? this.putMetaCopy(new StorageNbtComponent(this.getComponent(), this.isResolve(), null, this.id))
         : this.putMetaCopy(new StorageNbtComponent(this.getComponent(), this.isResolve(), this.getSeparator(), this.id));
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         StorageNbtComponent that = (StorageNbtComponent)o;
         return Objects.equals(this.getSiblings(), that.getSiblings()) && Objects.equals(this.getStyle(), that.getStyle()) && Objects.equals(this.id, that.id);
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.getSiblings(), this.getStyle(), this.id);
   }

   @Override
   public String toString() {
      return "StorageNbtComponent{siblings=" + this.getSiblings() + ", style=" + this.getStyle() + ", id=" + this.id + '}';
   }
}
