package net.minecraft.client.renderer.vertex;

import net.minecraft.client.renderer.vertex.VertexFormatElement$EnumType;
import net.minecraft.client.renderer.vertex.VertexFormatElement$EnumUsage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class VertexFormatElement {
   private static final Logger LOGGER = LogManager.getLogger();
   private final VertexFormatElement$EnumType type;
   private final VertexFormatElement$EnumUsage usage;
   private final int index;
   private final int elementCount;

   public VertexFormatElement(int var1, VertexFormatElement$EnumType var2, VertexFormatElement$EnumUsage var3, int var4) {
      if(!this.func_177372_a(var1, var3)) {
         LOGGER.warn("Multiple vertex elements of the same type other than UVs are not supported. Forcing type to UV.");
         this.usage = VertexFormatElement$EnumUsage.UV;
      } else {
         this.usage = var3;
      }

      this.type = var2;
      this.index = var1;
      this.elementCount = var4;
   }

   private boolean func_177372_a(int var1, VertexFormatElement$EnumUsage var2) {
      return var2 == VertexFormatElement$EnumUsage.UV;
   }

   public final VertexFormatElement$EnumType getType() {
      return this.type;
   }

   public final VertexFormatElement$EnumUsage getUsage() {
      return this.usage;
   }

   public final int getElementCount() {
      return this.elementCount;
   }

   public final int getIndex() {
      return this.index;
   }

   public String toString() {
      return this.elementCount + "," + this.usage.getDisplayName() + "," + this.type.getDisplayName();
   }

   public final int getSize() {
      return this.type.getSize() * this.elementCount;
   }

   public final boolean isPositionElement() {
      return this.usage == VertexFormatElement$EnumUsage.POSITION;
   }

   public boolean equals(Object var1) {
      if(this == var1) {
         return true;
      } else if(this.getClass() != var1.getClass()) {
         return false;
      } else {
         VertexFormatElement var2 = (VertexFormatElement)var1;
         return this.elementCount == var2.elementCount && this.index == var2.index && this.type == var2.type && this.usage == var2.usage;
      }
   }

   public int hashCode() {
      int var1 = this.type.hashCode();
      var1 = 31 * var1 + this.usage.hashCode();
      var1 = 31 * var1 + this.index;
      var1 = 31 * var1 + this.elementCount;
      return var1;
   }
}
