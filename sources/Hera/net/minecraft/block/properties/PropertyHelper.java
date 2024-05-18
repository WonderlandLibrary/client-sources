/*    */ package net.minecraft.block.properties;
/*    */ 
/*    */ import com.google.common.base.Objects;
/*    */ 
/*    */ public abstract class PropertyHelper<T extends Comparable<T>>
/*    */   implements IProperty<T>
/*    */ {
/*    */   private final Class<T> valueClass;
/*    */   private final String name;
/*    */   
/*    */   protected PropertyHelper(String name, Class<T> valueClass) {
/* 12 */     this.valueClass = valueClass;
/* 13 */     this.name = name;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getName() {
/* 18 */     return this.name;
/*    */   }
/*    */ 
/*    */   
/*    */   public Class<T> getValueClass() {
/* 23 */     return this.valueClass;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 28 */     return Objects.toStringHelper(this).add("name", this.name).add("clazz", this.valueClass).add("values", getAllowedValues()).toString();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object p_equals_1_) {
/* 33 */     if (this == p_equals_1_)
/*    */     {
/* 35 */       return true;
/*    */     }
/* 37 */     if (p_equals_1_ != null && getClass() == p_equals_1_.getClass()) {
/*    */       
/* 39 */       PropertyHelper propertyhelper = (PropertyHelper)p_equals_1_;
/* 40 */       return (this.valueClass.equals(propertyhelper.valueClass) && this.name.equals(propertyhelper.name));
/*    */     } 
/*    */ 
/*    */     
/* 44 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 50 */     return 31 * this.valueClass.hashCode() + this.name.hashCode();
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\block\properties\PropertyHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */