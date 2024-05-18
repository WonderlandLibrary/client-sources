/*    */ package net.minecraft.block.properties;
/*    */ 
/*    */ import com.google.common.base.Predicate;
/*    */ import com.google.common.base.Predicates;
/*    */ import com.google.common.collect.Collections2;
/*    */ import com.google.common.collect.Lists;
/*    */ import java.util.Collection;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ 
/*    */ public class PropertyDirection
/*    */   extends PropertyEnum<EnumFacing>
/*    */ {
/*    */   protected PropertyDirection(String name, Collection<EnumFacing> values) {
/* 14 */     super(name, EnumFacing.class, values);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static PropertyDirection create(String name) {
/* 22 */     return create(name, Predicates.alwaysTrue());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static PropertyDirection create(String name, Predicate<EnumFacing> filter) {
/* 30 */     return create(name, Collections2.filter(Lists.newArrayList((Object[])EnumFacing.values()), filter));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static PropertyDirection create(String name, Collection<EnumFacing> values) {
/* 38 */     return new PropertyDirection(name, values);
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\block\properties\PropertyDirection.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */