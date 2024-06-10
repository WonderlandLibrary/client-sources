/*   1:    */ package net.minecraft.entity;
/*   2:    */ 
/*   3:    */ import java.util.Collection;
/*   4:    */ import java.util.Iterator;
/*   5:    */ import java.util.UUID;
/*   6:    */ import net.minecraft.entity.ai.attributes.AttributeModifier;
/*   7:    */ import net.minecraft.entity.ai.attributes.BaseAttributeMap;
/*   8:    */ import net.minecraft.entity.ai.attributes.IAttribute;
/*   9:    */ import net.minecraft.entity.ai.attributes.IAttributeInstance;
/*  10:    */ import net.minecraft.entity.ai.attributes.RangedAttribute;
/*  11:    */ import net.minecraft.nbt.NBTTagCompound;
/*  12:    */ import net.minecraft.nbt.NBTTagList;
/*  13:    */ import org.apache.logging.log4j.LogManager;
/*  14:    */ import org.apache.logging.log4j.Logger;
/*  15:    */ 
/*  16:    */ public class SharedMonsterAttributes
/*  17:    */ {
/*  18: 18 */   private static final Logger logger = ;
/*  19: 19 */   public static final IAttribute maxHealth = new RangedAttribute("generic.maxHealth", 20.0D, 0.0D, 1.7976931348623157E+308D).setDescription("Max Health").setShouldWatch(true);
/*  20: 20 */   public static final IAttribute followRange = new RangedAttribute("generic.followRange", 32.0D, 0.0D, 2048.0D).setDescription("Follow Range");
/*  21: 21 */   public static final IAttribute knockbackResistance = new RangedAttribute("generic.knockbackResistance", 0.0D, 0.0D, 1.0D).setDescription("Knockback Resistance");
/*  22: 22 */   public static final IAttribute movementSpeed = new RangedAttribute("generic.movementSpeed", 0.699999988079071D, 0.0D, 1.7976931348623157E+308D).setDescription("Movement Speed").setShouldWatch(true);
/*  23: 23 */   public static final IAttribute attackDamage = new RangedAttribute("generic.attackDamage", 2.0D, 0.0D, 1.7976931348623157E+308D);
/*  24:    */   private static final String __OBFID = "CL_00001695";
/*  25:    */   
/*  26:    */   public static NBTTagList writeBaseAttributeMapToNBT(BaseAttributeMap par0BaseAttributeMap)
/*  27:    */   {
/*  28: 31 */     NBTTagList var1 = new NBTTagList();
/*  29: 32 */     Iterator var2 = par0BaseAttributeMap.getAllAttributes().iterator();
/*  30: 34 */     while (var2.hasNext())
/*  31:    */     {
/*  32: 36 */       IAttributeInstance var3 = (IAttributeInstance)var2.next();
/*  33: 37 */       var1.appendTag(writeAttributeInstanceToNBT(var3));
/*  34:    */     }
/*  35: 40 */     return var1;
/*  36:    */   }
/*  37:    */   
/*  38:    */   private static NBTTagCompound writeAttributeInstanceToNBT(IAttributeInstance par0AttributeInstance)
/*  39:    */   {
/*  40: 48 */     NBTTagCompound var1 = new NBTTagCompound();
/*  41: 49 */     IAttribute var2 = par0AttributeInstance.getAttribute();
/*  42: 50 */     var1.setString("Name", var2.getAttributeUnlocalizedName());
/*  43: 51 */     var1.setDouble("Base", par0AttributeInstance.getBaseValue());
/*  44: 52 */     Collection var3 = par0AttributeInstance.func_111122_c();
/*  45: 54 */     if ((var3 != null) && (!var3.isEmpty()))
/*  46:    */     {
/*  47: 56 */       NBTTagList var4 = new NBTTagList();
/*  48: 57 */       Iterator var5 = var3.iterator();
/*  49: 59 */       while (var5.hasNext())
/*  50:    */       {
/*  51: 61 */         AttributeModifier var6 = (AttributeModifier)var5.next();
/*  52: 63 */         if (var6.isSaved()) {
/*  53: 65 */           var4.appendTag(writeAttributeModifierToNBT(var6));
/*  54:    */         }
/*  55:    */       }
/*  56: 69 */       var1.setTag("Modifiers", var4);
/*  57:    */     }
/*  58: 72 */     return var1;
/*  59:    */   }
/*  60:    */   
/*  61:    */   private static NBTTagCompound writeAttributeModifierToNBT(AttributeModifier par0AttributeModifier)
/*  62:    */   {
/*  63: 80 */     NBTTagCompound var1 = new NBTTagCompound();
/*  64: 81 */     var1.setString("Name", par0AttributeModifier.getName());
/*  65: 82 */     var1.setDouble("Amount", par0AttributeModifier.getAmount());
/*  66: 83 */     var1.setInteger("Operation", par0AttributeModifier.getOperation());
/*  67: 84 */     var1.setLong("UUIDMost", par0AttributeModifier.getID().getMostSignificantBits());
/*  68: 85 */     var1.setLong("UUIDLeast", par0AttributeModifier.getID().getLeastSignificantBits());
/*  69: 86 */     return var1;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public static void func_151475_a(BaseAttributeMap p_151475_0_, NBTTagList p_151475_1_)
/*  73:    */   {
/*  74: 91 */     for (int var2 = 0; var2 < p_151475_1_.tagCount(); var2++)
/*  75:    */     {
/*  76: 93 */       NBTTagCompound var3 = p_151475_1_.getCompoundTagAt(var2);
/*  77: 94 */       IAttributeInstance var4 = p_151475_0_.getAttributeInstanceByName(var3.getString("Name"));
/*  78: 96 */       if (var4 != null) {
/*  79: 98 */         applyModifiersToAttributeInstance(var4, var3);
/*  80:    */       } else {
/*  81:102 */         logger.warn("Ignoring unknown attribute '" + var3.getString("Name") + "'");
/*  82:    */       }
/*  83:    */     }
/*  84:    */   }
/*  85:    */   
/*  86:    */   private static void applyModifiersToAttributeInstance(IAttributeInstance par0AttributeInstance, NBTTagCompound par1NBTTagCompound)
/*  87:    */   {
/*  88:109 */     par0AttributeInstance.setBaseValue(par1NBTTagCompound.getDouble("Base"));
/*  89:111 */     if (par1NBTTagCompound.func_150297_b("Modifiers", 9))
/*  90:    */     {
/*  91:113 */       NBTTagList var2 = par1NBTTagCompound.getTagList("Modifiers", 10);
/*  92:115 */       for (int var3 = 0; var3 < var2.tagCount(); var3++)
/*  93:    */       {
/*  94:117 */         AttributeModifier var4 = readAttributeModifierFromNBT(var2.getCompoundTagAt(var3));
/*  95:118 */         AttributeModifier var5 = par0AttributeInstance.getModifier(var4.getID());
/*  96:120 */         if (var5 != null) {
/*  97:122 */           par0AttributeInstance.removeModifier(var5);
/*  98:    */         }
/*  99:125 */         par0AttributeInstance.applyModifier(var4);
/* 100:    */       }
/* 101:    */     }
/* 102:    */   }
/* 103:    */   
/* 104:    */   public static AttributeModifier readAttributeModifierFromNBT(NBTTagCompound par0NBTTagCompound)
/* 105:    */   {
/* 106:135 */     UUID var1 = new UUID(par0NBTTagCompound.getLong("UUIDMost"), par0NBTTagCompound.getLong("UUIDLeast"));
/* 107:136 */     return new AttributeModifier(var1, par0NBTTagCompound.getString("Name"), par0NBTTagCompound.getDouble("Amount"), par0NBTTagCompound.getInteger("Operation"));
/* 108:    */   }
/* 109:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.entity.SharedMonsterAttributes
 * JD-Core Version:    0.7.0.1
 */