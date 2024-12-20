/*    */ package net.minecraft.entity.ai.attributes;
/*    */ 
/*    */ import com.google.common.collect.HashMultimap;
/*    */ import com.google.common.collect.Maps;
/*    */ import com.google.common.collect.Multimap;
/*    */ import java.util.Collection;
/*    */ import java.util.Map;
/*    */ import net.minecraft.server.management.LowerStringMap;
/*    */ 
/*    */ 
/*    */ public abstract class BaseAttributeMap
/*    */ {
/* 13 */   protected final Map<IAttribute, IAttributeInstance> attributes = Maps.newHashMap();
/* 14 */   protected final Map<String, IAttributeInstance> attributesByName = (Map<String, IAttributeInstance>)new LowerStringMap();
/* 15 */   protected final Multimap<IAttribute, IAttribute> field_180377_c = (Multimap<IAttribute, IAttribute>)HashMultimap.create();
/*    */ 
/*    */   
/*    */   public IAttributeInstance getAttributeInstance(IAttribute attribute) {
/* 19 */     return this.attributes.get(attribute);
/*    */   }
/*    */ 
/*    */   
/*    */   public IAttributeInstance getAttributeInstanceByName(String attributeName) {
/* 24 */     return this.attributesByName.get(attributeName);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public IAttributeInstance registerAttribute(IAttribute attribute) {
/* 32 */     if (this.attributesByName.containsKey(attribute.getAttributeUnlocalizedName()))
/*    */     {
/* 34 */       throw new IllegalArgumentException("Attribute is already registered!");
/*    */     }
/*    */ 
/*    */     
/* 38 */     IAttributeInstance iattributeinstance = func_180376_c(attribute);
/* 39 */     this.attributesByName.put(attribute.getAttributeUnlocalizedName(), iattributeinstance);
/* 40 */     this.attributes.put(attribute, iattributeinstance);
/*    */     
/* 42 */     for (IAttribute iattribute = attribute.func_180372_d(); iattribute != null; iattribute = iattribute.func_180372_d())
/*    */     {
/* 44 */       this.field_180377_c.put(iattribute, attribute);
/*    */     }
/*    */     
/* 47 */     return iattributeinstance;
/*    */   }
/*    */ 
/*    */   
/*    */   protected abstract IAttributeInstance func_180376_c(IAttribute paramIAttribute);
/*    */ 
/*    */   
/*    */   public Collection<IAttributeInstance> getAllAttributes() {
/* 55 */     return this.attributesByName.values();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void func_180794_a(IAttributeInstance p_180794_1_) {}
/*    */ 
/*    */   
/*    */   public void removeAttributeModifiers(Multimap<String, AttributeModifier> p_111148_1_) {
/* 64 */     for (Map.Entry<String, AttributeModifier> entry : (Iterable<Map.Entry<String, AttributeModifier>>)p_111148_1_.entries()) {
/*    */       
/* 66 */       IAttributeInstance iattributeinstance = getAttributeInstanceByName(entry.getKey());
/*    */       
/* 68 */       if (iattributeinstance != null)
/*    */       {
/* 70 */         iattributeinstance.removeModifier(entry.getValue());
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void applyAttributeModifiers(Multimap<String, AttributeModifier> p_111147_1_) {
/* 77 */     for (Map.Entry<String, AttributeModifier> entry : (Iterable<Map.Entry<String, AttributeModifier>>)p_111147_1_.entries()) {
/*    */       
/* 79 */       IAttributeInstance iattributeinstance = getAttributeInstanceByName(entry.getKey());
/*    */       
/* 81 */       if (iattributeinstance != null) {
/*    */         
/* 83 */         iattributeinstance.removeModifier(entry.getValue());
/* 84 */         iattributeinstance.applyModifier(entry.getValue());
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\entity\ai\attributes\BaseAttributeMap.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */