/*  1:   */ package net.minecraft.entity.ai.attributes;
/*  2:   */ 
/*  3:   */ import com.google.common.collect.Multimap;
/*  4:   */ import java.util.Collection;
/*  5:   */ import java.util.HashMap;
/*  6:   */ import java.util.Iterator;
/*  7:   */ import java.util.Map;
/*  8:   */ import java.util.Map.Entry;
/*  9:   */ import net.minecraft.server.management.LowerStringMap;
/* 10:   */ 
/* 11:   */ public abstract class BaseAttributeMap
/* 12:   */ {
/* 13:13 */   protected final Map attributes = new HashMap();
/* 14:14 */   protected final Map attributesByName = new LowerStringMap();
/* 15:   */   private static final String __OBFID = "CL_00001566";
/* 16:   */   
/* 17:   */   public IAttributeInstance getAttributeInstance(IAttribute par1Attribute)
/* 18:   */   {
/* 19:19 */     return (IAttributeInstance)this.attributes.get(par1Attribute);
/* 20:   */   }
/* 21:   */   
/* 22:   */   public IAttributeInstance getAttributeInstanceByName(String par1Str)
/* 23:   */   {
/* 24:24 */     return (IAttributeInstance)this.attributesByName.get(par1Str);
/* 25:   */   }
/* 26:   */   
/* 27:   */   public abstract IAttributeInstance registerAttribute(IAttribute paramIAttribute);
/* 28:   */   
/* 29:   */   public Collection getAllAttributes()
/* 30:   */   {
/* 31:34 */     return this.attributesByName.values();
/* 32:   */   }
/* 33:   */   
/* 34:   */   public void addAttributeInstance(ModifiableAttributeInstance par1ModifiableAttributeInstance) {}
/* 35:   */   
/* 36:   */   public void removeAttributeModifiers(Multimap par1Multimap)
/* 37:   */   {
/* 38:41 */     Iterator var2 = par1Multimap.entries().iterator();
/* 39:43 */     while (var2.hasNext())
/* 40:   */     {
/* 41:45 */       Map.Entry var3 = (Map.Entry)var2.next();
/* 42:46 */       IAttributeInstance var4 = getAttributeInstanceByName((String)var3.getKey());
/* 43:48 */       if (var4 != null) {
/* 44:50 */         var4.removeModifier((AttributeModifier)var3.getValue());
/* 45:   */       }
/* 46:   */     }
/* 47:   */   }
/* 48:   */   
/* 49:   */   public void applyAttributeModifiers(Multimap par1Multimap)
/* 50:   */   {
/* 51:57 */     Iterator var2 = par1Multimap.entries().iterator();
/* 52:59 */     while (var2.hasNext())
/* 53:   */     {
/* 54:61 */       Map.Entry var3 = (Map.Entry)var2.next();
/* 55:62 */       IAttributeInstance var4 = getAttributeInstanceByName((String)var3.getKey());
/* 56:64 */       if (var4 != null)
/* 57:   */       {
/* 58:66 */         var4.removeModifier((AttributeModifier)var3.getValue());
/* 59:67 */         var4.applyModifier((AttributeModifier)var3.getValue());
/* 60:   */       }
/* 61:   */     }
/* 62:   */   }
/* 63:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.entity.ai.attributes.BaseAttributeMap
 * JD-Core Version:    0.7.0.1
 */