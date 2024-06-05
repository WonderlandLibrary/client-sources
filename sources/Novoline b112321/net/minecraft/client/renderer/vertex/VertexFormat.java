package net.minecraft.client.renderer.vertex;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.client.renderer.vertex.VertexFormat$1;
import net.minecraft.client.renderer.vertex.VertexFormatElement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class VertexFormat {
   private static final Logger LOGGER = LogManager.getLogger();
   private final List elements;
   private final List offsets;
   private int nextOffset;
   private int colorElementOffset;
   private final List uvOffsetsById;
   private int normalElementOffset;

   public VertexFormat(VertexFormat var1) {
      this();

      for(int var2 = 0; var2 < var1.getElementCount(); ++var2) {
         this.func_181721_a(var1.getElement(var2));
      }

      this.nextOffset = var1.getNextOffset();
   }

   public VertexFormat() {
      this.elements = Lists.newArrayList();
      this.offsets = Lists.newArrayList();
      this.nextOffset = 0;
      this.colorElementOffset = -1;
      this.uvOffsetsById = Lists.newArrayList();
      this.normalElementOffset = -1;
   }

   public void clear() {
      this.elements.clear();
      this.offsets.clear();
      this.colorElementOffset = -1;
      this.uvOffsetsById.clear();
      this.normalElementOffset = -1;
      this.nextOffset = 0;
   }

   public VertexFormat func_181721_a(VertexFormatElement var1) {
      if(var1.isPositionElement() && this.hasPosition()) {
         LOGGER.warn("VertexFormat error: Trying to add a position VertexFormatElement when one already exists, ignoring.");
      } else {
         this.elements.add(var1);
         this.offsets.add(Integer.valueOf(this.nextOffset));
         switch(VertexFormat$1.$SwitchMap$net$minecraft$client$renderer$vertex$VertexFormatElement$EnumUsage[var1.getUsage().ordinal()]) {
         case 1:
            this.normalElementOffset = this.nextOffset;
            break;
         case 2:
            this.colorElementOffset = this.nextOffset;
            break;
         case 3:
            this.uvOffsetsById.add(var1.getIndex(), Integer.valueOf(this.nextOffset));
         }

         this.nextOffset += var1.getSize();
      }

      return this;
   }

   public boolean hasNormal() {
      return this.normalElementOffset >= 0;
   }

   public int getNormalOffset() {
      return this.normalElementOffset;
   }

   public boolean hasColor() {
      return this.colorElementOffset >= 0;
   }

   public int getColorOffset() {
      return this.colorElementOffset;
   }

   public boolean hasUvOffset(int var1) {
      return this.uvOffsetsById.size() - 1 >= var1;
   }

   public int getUvOffsetById(int var1) {
      return ((Integer)this.uvOffsetsById.get(var1)).intValue();
   }

   public String toString() {
      String var1 = "format: " + this.elements.size() + " elements: ";

      for(int var2 = 0; var2 < this.elements.size(); ++var2) {
         var1 = var1 + ((VertexFormatElement)this.elements.get(var2)).toString();
         if(var2 != this.elements.size() - 1) {
            var1 = var1 + " ";
         }
      }

      return var1;
   }

   private boolean hasPosition() {
      int var1 = 0;

      for(int var2 = this.elements.size(); var1 < var2; ++var1) {
         VertexFormatElement var3 = (VertexFormatElement)this.elements.get(var1);
         if(var3.isPositionElement()) {
            return true;
         }
      }

      return false;
   }

   public int func_181719_f() {
      return this.getNextOffset() / 4;
   }

   public int getNextOffset() {
      return this.nextOffset;
   }

   public List getElements() {
      return this.elements;
   }

   public int getElementCount() {
      return this.elements.size();
   }

   public VertexFormatElement getElement(int var1) {
      return (VertexFormatElement)this.elements.get(var1);
   }

   public int func_181720_d(int var1) {
      return ((Integer)this.offsets.get(var1)).intValue();
   }

   public boolean equals(Object var1) {
      if(this == var1) {
         return true;
      } else if(this.getClass() != var1.getClass()) {
         return false;
      } else {
         VertexFormat var2 = (VertexFormat)var1;
         return this.nextOffset == var2.nextOffset && this.elements.equals(var2.elements) && this.offsets.equals(var2.offsets);
      }
   }

   public int hashCode() {
      int var1 = this.elements.hashCode();
      var1 = 31 * var1 + this.offsets.hashCode();
      var1 = 31 * var1 + this.nextOffset;
      return var1;
   }
}
