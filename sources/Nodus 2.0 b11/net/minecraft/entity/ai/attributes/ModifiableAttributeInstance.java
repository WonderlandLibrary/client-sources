/*   1:    */ package net.minecraft.entity.ai.attributes;
/*   2:    */ 
/*   3:    */ import com.google.common.collect.Maps;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import java.util.Collection;
/*   6:    */ import java.util.HashSet;
/*   7:    */ import java.util.Iterator;
/*   8:    */ import java.util.Map;
/*   9:    */ import java.util.Set;
/*  10:    */ import java.util.UUID;
/*  11:    */ 
/*  12:    */ public class ModifiableAttributeInstance
/*  13:    */   implements IAttributeInstance
/*  14:    */ {
/*  15:    */   private final BaseAttributeMap attributeMap;
/*  16:    */   private final IAttribute genericAttribute;
/*  17: 19 */   private final Map mapByOperation = Maps.newHashMap();
/*  18: 20 */   private final Map mapByName = Maps.newHashMap();
/*  19: 21 */   private final Map mapByUUID = Maps.newHashMap();
/*  20:    */   private double baseValue;
/*  21: 23 */   private boolean needsUpdate = true;
/*  22:    */   private double cachedValue;
/*  23:    */   private static final String __OBFID = "CL_00001567";
/*  24:    */   
/*  25:    */   public ModifiableAttributeInstance(BaseAttributeMap par1BaseAttributeMap, IAttribute par2Attribute)
/*  26:    */   {
/*  27: 29 */     this.attributeMap = par1BaseAttributeMap;
/*  28: 30 */     this.genericAttribute = par2Attribute;
/*  29: 31 */     this.baseValue = par2Attribute.getDefaultValue();
/*  30: 33 */     for (int var3 = 0; var3 < 3; var3++) {
/*  31: 35 */       this.mapByOperation.put(Integer.valueOf(var3), new HashSet());
/*  32:    */     }
/*  33:    */   }
/*  34:    */   
/*  35:    */   public IAttribute getAttribute()
/*  36:    */   {
/*  37: 44 */     return this.genericAttribute;
/*  38:    */   }
/*  39:    */   
/*  40:    */   public double getBaseValue()
/*  41:    */   {
/*  42: 49 */     return this.baseValue;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public void setBaseValue(double par1)
/*  46:    */   {
/*  47: 54 */     if (par1 != getBaseValue())
/*  48:    */     {
/*  49: 56 */       this.baseValue = par1;
/*  50: 57 */       flagForUpdate();
/*  51:    */     }
/*  52:    */   }
/*  53:    */   
/*  54:    */   public Collection getModifiersByOperation(int par1)
/*  55:    */   {
/*  56: 63 */     return (Collection)this.mapByOperation.get(Integer.valueOf(par1));
/*  57:    */   }
/*  58:    */   
/*  59:    */   public Collection func_111122_c()
/*  60:    */   {
/*  61: 68 */     HashSet var1 = new HashSet();
/*  62: 70 */     for (int var2 = 0; var2 < 3; var2++) {
/*  63: 72 */       var1.addAll(getModifiersByOperation(var2));
/*  64:    */     }
/*  65: 75 */     return var1;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public AttributeModifier getModifier(UUID par1UUID)
/*  69:    */   {
/*  70: 83 */     return (AttributeModifier)this.mapByUUID.get(par1UUID);
/*  71:    */   }
/*  72:    */   
/*  73:    */   public void applyModifier(AttributeModifier par1AttributeModifier)
/*  74:    */   {
/*  75: 88 */     if (getModifier(par1AttributeModifier.getID()) != null) {
/*  76: 90 */       throw new IllegalArgumentException("Modifier is already applied on this attribute!");
/*  77:    */     }
/*  78: 94 */     Object var2 = (Set)this.mapByName.get(par1AttributeModifier.getName());
/*  79: 96 */     if (var2 == null)
/*  80:    */     {
/*  81: 98 */       var2 = new HashSet();
/*  82: 99 */       this.mapByName.put(par1AttributeModifier.getName(), var2);
/*  83:    */     }
/*  84:102 */     ((Set)this.mapByOperation.get(Integer.valueOf(par1AttributeModifier.getOperation()))).add(par1AttributeModifier);
/*  85:103 */     ((Set)var2).add(par1AttributeModifier);
/*  86:104 */     this.mapByUUID.put(par1AttributeModifier.getID(), par1AttributeModifier);
/*  87:105 */     flagForUpdate();
/*  88:    */   }
/*  89:    */   
/*  90:    */   private void flagForUpdate()
/*  91:    */   {
/*  92:111 */     this.needsUpdate = true;
/*  93:112 */     this.attributeMap.addAttributeInstance(this);
/*  94:    */   }
/*  95:    */   
/*  96:    */   public void removeModifier(AttributeModifier par1AttributeModifier)
/*  97:    */   {
/*  98:117 */     for (int var2 = 0; var2 < 3; var2++)
/*  99:    */     {
/* 100:119 */       Set var3 = (Set)this.mapByOperation.get(Integer.valueOf(var2));
/* 101:120 */       var3.remove(par1AttributeModifier);
/* 102:    */     }
/* 103:123 */     Set var4 = (Set)this.mapByName.get(par1AttributeModifier.getName());
/* 104:125 */     if (var4 != null)
/* 105:    */     {
/* 106:127 */       var4.remove(par1AttributeModifier);
/* 107:129 */       if (var4.isEmpty()) {
/* 108:131 */         this.mapByName.remove(par1AttributeModifier.getName());
/* 109:    */       }
/* 110:    */     }
/* 111:135 */     this.mapByUUID.remove(par1AttributeModifier.getID());
/* 112:136 */     flagForUpdate();
/* 113:    */   }
/* 114:    */   
/* 115:    */   public void removeAllModifiers()
/* 116:    */   {
/* 117:141 */     Collection var1 = func_111122_c();
/* 118:143 */     if (var1 != null)
/* 119:    */     {
/* 120:145 */       ArrayList var4 = new ArrayList(var1);
/* 121:146 */       Iterator var2 = var4.iterator();
/* 122:148 */       while (var2.hasNext())
/* 123:    */       {
/* 124:150 */         AttributeModifier var3 = (AttributeModifier)var2.next();
/* 125:151 */         removeModifier(var3);
/* 126:    */       }
/* 127:    */     }
/* 128:    */   }
/* 129:    */   
/* 130:    */   public double getAttributeValue()
/* 131:    */   {
/* 132:158 */     if (this.needsUpdate)
/* 133:    */     {
/* 134:160 */       this.cachedValue = computeValue();
/* 135:161 */       this.needsUpdate = false;
/* 136:    */     }
/* 137:164 */     return this.cachedValue;
/* 138:    */   }
/* 139:    */   
/* 140:    */   private double computeValue()
/* 141:    */   {
/* 142:169 */     double var1 = getBaseValue();
/* 143:    */     AttributeModifier var4;
/* 144:172 */     for (Iterator var3 = getModifiersByOperation(0).iterator(); var3.hasNext(); var1 += var4.getAmount()) {
/* 145:174 */       var4 = (AttributeModifier)var3.next();
/* 146:    */     }
/* 147:177 */     double var7 = var1;
/* 148:    */     AttributeModifier var6;
/* 149:181 */     for (Iterator var5 = getModifiersByOperation(1).iterator(); var5.hasNext(); var7 += var1 * var6.getAmount()) {
/* 150:183 */       var6 = (AttributeModifier)var5.next();
/* 151:    */     }
/* 152:    */     AttributeModifier var6;
/* 153:186 */     for (var5 = getModifiersByOperation(2).iterator(); var5.hasNext(); var7 *= (1.0D + var6.getAmount())) {
/* 154:188 */       var6 = (AttributeModifier)var5.next();
/* 155:    */     }
/* 156:191 */     return this.genericAttribute.clampValue(var7);
/* 157:    */   }
/* 158:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.entity.ai.attributes.ModifiableAttributeInstance
 * JD-Core Version:    0.7.0.1
 */