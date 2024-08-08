package org.yaml.snakeyaml.nodes;

import org.yaml.snakeyaml.error.Mark;

public abstract class Node {
   private Tag tag;
   private Mark startMark;
   protected Mark endMark;
   private Class type;
   private boolean twoStepsConstruction;
   private String anchor;
   protected boolean resolved;
   protected Boolean useClassConstructor;

   public Node(Tag var1, Mark var2, Mark var3) {
      this.setTag(var1);
      this.startMark = var2;
      this.endMark = var3;
      this.type = Object.class;
      this.twoStepsConstruction = false;
      this.resolved = true;
      this.useClassConstructor = null;
   }

   public Tag getTag() {
      return this.tag;
   }

   public Mark getEndMark() {
      return this.endMark;
   }

   public abstract NodeId getNodeId();

   public Mark getStartMark() {
      return this.startMark;
   }

   public void setTag(Tag var1) {
      if (var1 == null) {
         throw new NullPointerException("tag in a Node is required.");
      } else {
         this.tag = var1;
      }
   }

   public final boolean equals(Object var1) {
      return super.equals(var1);
   }

   public Class getType() {
      return this.type;
   }

   public void setType(Class var1) {
      if (!var1.isAssignableFrom(this.type)) {
         this.type = var1;
      }

   }

   public void setTwoStepsConstruction(boolean var1) {
      this.twoStepsConstruction = var1;
   }

   public boolean isTwoStepsConstruction() {
      return this.twoStepsConstruction;
   }

   public final int hashCode() {
      return super.hashCode();
   }

   public boolean useClassConstructor() {
      if (this.useClassConstructor == null) {
         if (!this.tag.isSecondary() && this.isResolved() && !Object.class.equals(this.type) && !this.tag.equals(Tag.NULL)) {
            return true;
         } else {
            return this.tag.isCompatible(this.getType());
         }
      } else {
         return this.useClassConstructor;
      }
   }

   public void setUseClassConstructor(Boolean var1) {
      this.useClassConstructor = var1;
   }

   public boolean isResolved() {
      return this.resolved;
   }

   public String getAnchor() {
      return this.anchor;
   }

   public void setAnchor(String var1) {
      this.anchor = var1;
   }
}
