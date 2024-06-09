/*    */ package net.minecraft.block.properties;
/*    */ 
/*    */ import com.google.common.base.Predicate;
/*    */ import com.google.common.base.Predicates;
/*    */ import com.google.common.collect.Collections2;
/*    */ import com.google.common.collect.ImmutableSet;
/*    */ import com.google.common.collect.Lists;
/*    */ import com.google.common.collect.Maps;
/*    */ import java.util.Collection;
/*    */ import java.util.Map;
/*    */ import net.minecraft.util.IStringSerializable;
/*    */ 
/*    */ public class PropertyEnum<T extends Enum<T> & IStringSerializable>
/*    */   extends PropertyHelper<T> {
/*    */   private final ImmutableSet<T> allowedValues;
/* 16 */   private final Map<String, T> nameToValue = Maps.newHashMap();
/*    */ 
/*    */   
/*    */   protected PropertyEnum(String name, Class<T> valueClass, Collection<T> allowedValues) {
/* 20 */     super(name, valueClass);
/* 21 */     this.allowedValues = ImmutableSet.copyOf(allowedValues);
/*    */     
/* 23 */     for (Enum enum_ : allowedValues) {
/*    */       
/* 25 */       String s = ((IStringSerializable)enum_).getName();
/*    */       
/* 27 */       if (this.nameToValue.containsKey(s))
/*    */       {
/* 29 */         throw new IllegalArgumentException("Multiple values have the same name '" + s + "'");
/*    */       }
/*    */       
/* 32 */       this.nameToValue.put(s, (T)enum_);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public Collection<T> getAllowedValues() {
/* 38 */     return (Collection<T>)this.allowedValues;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getName(T value) {
/* 46 */     return ((IStringSerializable)value).getName();
/*    */   }
/*    */ 
/*    */   
/*    */   public static <T extends Enum<T> & IStringSerializable> PropertyEnum<T> create(String name, Class<T> clazz) {
/* 51 */     return create(name, clazz, Predicates.alwaysTrue());
/*    */   }
/*    */ 
/*    */   
/*    */   public static <T extends Enum<T> & IStringSerializable> PropertyEnum<T> create(String name, Class<T> clazz, Predicate<T> filter) {
/* 56 */     return create(name, clazz, Collections2.filter(Lists.newArrayList((Object[])clazz.getEnumConstants()), filter));
/*    */   }
/*    */ 
/*    */   
/*    */   public static <T extends Enum<T> & IStringSerializable> PropertyEnum<T> create(String name, Class<T> clazz, Enum... values) {
/* 61 */     return create(name, clazz, Lists.newArrayList((Object[])values));
/*    */   }
/*    */ 
/*    */   
/*    */   public static <T extends Enum<T> & IStringSerializable> PropertyEnum<T> create(String name, Class<T> clazz, Collection<T> values) {
/* 66 */     return new PropertyEnum<T>(name, clazz, values);
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\block\properties\PropertyEnum.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */