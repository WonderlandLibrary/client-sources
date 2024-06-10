/*  1:   */ package net.minecraft.entity.ai.attributes;
/*  2:   */ 
/*  3:   */ import com.google.common.collect.Sets;
/*  4:   */ import java.util.Collection;
/*  5:   */ import java.util.HashSet;
/*  6:   */ import java.util.Iterator;
/*  7:   */ import java.util.Map;
/*  8:   */ import java.util.Set;
/*  9:   */ import net.minecraft.server.management.LowerStringMap;
/* 10:   */ 
/* 11:   */ public class ServersideAttributeMap
/* 12:   */   extends BaseAttributeMap
/* 13:   */ {
/* 14:13 */   private final Set attributeInstanceSet = Sets.newHashSet();
/* 15:14 */   protected final Map descriptionToAttributeInstanceMap = new LowerStringMap();
/* 16:   */   private static final String __OBFID = "CL_00001569";
/* 17:   */   
/* 18:   */   public ModifiableAttributeInstance getAttributeInstance(IAttribute par1Attribute)
/* 19:   */   {
/* 20:19 */     return (ModifiableAttributeInstance)super.getAttributeInstance(par1Attribute);
/* 21:   */   }
/* 22:   */   
/* 23:   */   public ModifiableAttributeInstance getAttributeInstanceByName(String par1Str)
/* 24:   */   {
/* 25:24 */     IAttributeInstance var2 = super.getAttributeInstanceByName(par1Str);
/* 26:26 */     if (var2 == null) {
/* 27:28 */       var2 = (IAttributeInstance)this.descriptionToAttributeInstanceMap.get(par1Str);
/* 28:   */     }
/* 29:31 */     return (ModifiableAttributeInstance)var2;
/* 30:   */   }
/* 31:   */   
/* 32:   */   public IAttributeInstance registerAttribute(IAttribute par1Attribute)
/* 33:   */   {
/* 34:39 */     if (this.attributesByName.containsKey(par1Attribute.getAttributeUnlocalizedName())) {
/* 35:41 */       throw new IllegalArgumentException("Attribute is already registered!");
/* 36:   */     }
/* 37:45 */     ModifiableAttributeInstance var2 = new ModifiableAttributeInstance(this, par1Attribute);
/* 38:46 */     this.attributesByName.put(par1Attribute.getAttributeUnlocalizedName(), var2);
/* 39:48 */     if (((par1Attribute instanceof RangedAttribute)) && (((RangedAttribute)par1Attribute).getDescription() != null)) {
/* 40:50 */       this.descriptionToAttributeInstanceMap.put(((RangedAttribute)par1Attribute).getDescription(), var2);
/* 41:   */     }
/* 42:53 */     this.attributes.put(par1Attribute, var2);
/* 43:54 */     return var2;
/* 44:   */   }
/* 45:   */   
/* 46:   */   public void addAttributeInstance(ModifiableAttributeInstance par1ModifiableAttributeInstance)
/* 47:   */   {
/* 48:60 */     if (par1ModifiableAttributeInstance.getAttribute().getShouldWatch()) {
/* 49:62 */       this.attributeInstanceSet.add(par1ModifiableAttributeInstance);
/* 50:   */     }
/* 51:   */   }
/* 52:   */   
/* 53:   */   public Set getAttributeInstanceSet()
/* 54:   */   {
/* 55:68 */     return this.attributeInstanceSet;
/* 56:   */   }
/* 57:   */   
/* 58:   */   public Collection getWatchedAttributes()
/* 59:   */   {
/* 60:73 */     HashSet var1 = Sets.newHashSet();
/* 61:74 */     Iterator var2 = getAllAttributes().iterator();
/* 62:76 */     while (var2.hasNext())
/* 63:   */     {
/* 64:78 */       IAttributeInstance var3 = (IAttributeInstance)var2.next();
/* 65:80 */       if (var3.getAttribute().getShouldWatch()) {
/* 66:82 */         var1.add(var3);
/* 67:   */       }
/* 68:   */     }
/* 69:86 */     return var1;
/* 70:   */   }
/* 71:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.entity.ai.attributes.ServersideAttributeMap
 * JD-Core Version:    0.7.0.1
 */