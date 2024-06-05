package net.minecraft.realms;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.renderer.vertex.VertexFormatElement;
import net.minecraft.realms.RealmsVertexFormatElement;

public class RealmsVertexFormat {
   private VertexFormat v;

   public RealmsVertexFormat(VertexFormat var1) {
      this.v = var1;
   }

   public RealmsVertexFormat from(VertexFormat var1) {
      this.v = var1;
      return this;
   }

   public VertexFormat getVertexFormat() {
      return this.v;
   }

   public void clear() {
      this.v.clear();
   }

   public int getUvOffset(int var1) {
      return this.v.getUvOffsetById(var1);
   }

   public int getElementCount() {
      return this.v.getElementCount();
   }

   public boolean hasColor() {
      return this.v.hasColor();
   }

   public boolean hasUv(int var1) {
      return this.v.hasUvOffset(var1);
   }

   public RealmsVertexFormatElement getElement(int var1) {
      return new RealmsVertexFormatElement(this.v.getElement(var1));
   }

   public RealmsVertexFormat addElement(RealmsVertexFormatElement var1) {
      return this.from(this.v.func_181721_a(var1.getVertexFormatElement()));
   }

   public int getColorOffset() {
      return this.v.getColorOffset();
   }

   public List getElements() {
      ArrayList var1 = new ArrayList();

      for(VertexFormatElement var3 : this.v.getElements()) {
         var1.add(new RealmsVertexFormatElement(var3));
      }

      return var1;
   }

   public boolean hasNormal() {
      return this.v.hasNormal();
   }

   public int getVertexSize() {
      return this.v.getNextOffset();
   }

   public int getOffset(int var1) {
      return this.v.func_181720_d(var1);
   }

   public int getNormalOffset() {
      return this.v.getNormalOffset();
   }

   public int getIntegerSize() {
      return this.v.func_181719_f();
   }

   public boolean equals(Object var1) {
      return this.v.equals(var1);
   }

   public int hashCode() {
      return this.v.hashCode();
   }

   public String toString() {
      return this.v.toString();
   }
}
