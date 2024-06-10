/*   1:    */ package net.minecraft.enchantment;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import net.minecraft.entity.Entity;
/*   5:    */ import net.minecraft.entity.EntityLivingBase;
/*   6:    */ import net.minecraft.item.ItemStack;
/*   7:    */ import net.minecraft.util.DamageSource;
/*   8:    */ import net.minecraft.util.StatCollector;
/*   9:    */ 
/*  10:    */ public abstract class Enchantment
/*  11:    */ {
/*  12: 12 */   public static final Enchantment[] enchantmentsList = new Enchantment[256];
/*  13:    */   public static final Enchantment[] enchantmentsBookList;
/*  14: 18 */   public static final Enchantment protection = new EnchantmentProtection(0, 10, 0);
/*  15: 21 */   public static final Enchantment fireProtection = new EnchantmentProtection(1, 5, 1);
/*  16: 24 */   public static final Enchantment featherFalling = new EnchantmentProtection(2, 5, 2);
/*  17: 27 */   public static final Enchantment blastProtection = new EnchantmentProtection(3, 2, 3);
/*  18: 30 */   public static final Enchantment projectileProtection = new EnchantmentProtection(4, 5, 4);
/*  19: 35 */   public static final Enchantment respiration = new EnchantmentOxygen(5, 2);
/*  20: 38 */   public static final Enchantment aquaAffinity = new EnchantmentWaterWorker(6, 2);
/*  21: 39 */   public static final Enchantment thorns = new EnchantmentThorns(7, 1);
/*  22: 42 */   public static final Enchantment sharpness = new EnchantmentDamage(16, 10, 0);
/*  23: 45 */   public static final Enchantment smite = new EnchantmentDamage(17, 5, 1);
/*  24: 48 */   public static final Enchantment baneOfArthropods = new EnchantmentDamage(18, 5, 2);
/*  25: 51 */   public static final Enchantment knockback = new EnchantmentKnockback(19, 5);
/*  26: 54 */   public static final Enchantment fireAspect = new EnchantmentFireAspect(20, 2);
/*  27: 57 */   public static final Enchantment looting = new EnchantmentLootBonus(21, 2, EnumEnchantmentType.weapon);
/*  28: 60 */   public static final Enchantment efficiency = new EnchantmentDigging(32, 10);
/*  29: 66 */   public static final Enchantment silkTouch = new EnchantmentUntouching(33, 1);
/*  30: 71 */   public static final Enchantment unbreaking = new EnchantmentDurability(34, 5);
/*  31: 74 */   public static final Enchantment fortune = new EnchantmentLootBonus(35, 2, EnumEnchantmentType.digger);
/*  32: 77 */   public static final Enchantment power = new EnchantmentArrowDamage(48, 10);
/*  33: 82 */   public static final Enchantment punch = new EnchantmentArrowKnockback(49, 2);
/*  34: 87 */   public static final Enchantment flame = new EnchantmentArrowFire(50, 2);
/*  35: 93 */   public static final Enchantment infinity = new EnchantmentArrowInfinite(51, 1);
/*  36: 94 */   public static final Enchantment field_151370_z = new EnchantmentLootBonus(61, 2, EnumEnchantmentType.fishing_rod);
/*  37: 95 */   public static final Enchantment field_151369_A = new EnchantmentFishingSpeed(62, 2, EnumEnchantmentType.fishing_rod);
/*  38:    */   public final int effectId;
/*  39:    */   private final int weight;
/*  40:    */   public EnumEnchantmentType type;
/*  41:    */   protected String name;
/*  42:    */   private static final String __OBFID = "CL_00000105";
/*  43:    */   
/*  44:    */   protected Enchantment(int par1, int par2, EnumEnchantmentType par3EnumEnchantmentType)
/*  45:    */   {
/*  46:108 */     this.effectId = par1;
/*  47:109 */     this.weight = par2;
/*  48:110 */     this.type = par3EnumEnchantmentType;
/*  49:112 */     if (enchantmentsList[par1] != null) {
/*  50:114 */       throw new IllegalArgumentException("Duplicate enchantment id!");
/*  51:    */     }
/*  52:118 */     enchantmentsList[par1] = this;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public int getWeight()
/*  56:    */   {
/*  57:124 */     return this.weight;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public int getMinLevel()
/*  61:    */   {
/*  62:132 */     return 1;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public int getMaxLevel()
/*  66:    */   {
/*  67:140 */     return 1;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public int getMinEnchantability(int par1)
/*  71:    */   {
/*  72:148 */     return 1 + par1 * 10;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public int getMaxEnchantability(int par1)
/*  76:    */   {
/*  77:156 */     return getMinEnchantability(par1) + 5;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public int calcModifierDamage(int par1, DamageSource par2DamageSource)
/*  81:    */   {
/*  82:164 */     return 0;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public float calcModifierLiving(int par1, EntityLivingBase par2EntityLivingBase)
/*  86:    */   {
/*  87:172 */     return 0.0F;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public boolean canApplyTogether(Enchantment par1Enchantment)
/*  91:    */   {
/*  92:180 */     return this != par1Enchantment;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public Enchantment setName(String par1Str)
/*  96:    */   {
/*  97:188 */     this.name = par1Str;
/*  98:189 */     return this;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public String getName()
/* 102:    */   {
/* 103:197 */     return "enchantment." + this.name;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public String getTranslatedName(int par1)
/* 107:    */   {
/* 108:205 */     String var2 = StatCollector.translateToLocal(getName());
/* 109:206 */     return var2 + " " + StatCollector.translateToLocal(new StringBuilder("enchantment.level.").append(par1).toString());
/* 110:    */   }
/* 111:    */   
/* 112:    */   public boolean canApply(ItemStack par1ItemStack)
/* 113:    */   {
/* 114:211 */     return this.type.canEnchantItem(par1ItemStack.getItem());
/* 115:    */   }
/* 116:    */   
/* 117:    */   static
/* 118:    */   {
/* 119:220 */     ArrayList var0 = new ArrayList();
/* 120:221 */     Enchantment[] var1 = enchantmentsList;
/* 121:222 */     int var2 = var1.length;
/* 122:224 */     for (int var3 = 0; var3 < var2; var3++)
/* 123:    */     {
/* 124:226 */       Enchantment var4 = var1[var3];
/* 125:228 */       if (var4 != null) {
/* 126:230 */         var0.add(var4);
/* 127:    */       }
/* 128:    */     }
/* 129:234 */     enchantmentsBookList = (Enchantment[])var0.toArray(new Enchantment[0]);
/* 130:    */   }
/* 131:    */   
/* 132:    */   public void func_151368_a(EntityLivingBase p_151368_1_, Entity p_151368_2_, int p_151368_3_) {}
/* 133:    */   
/* 134:    */   public void func_151367_b(EntityLivingBase p_151367_1_, Entity p_151367_2_, int p_151367_3_) {}
/* 135:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.enchantment.Enchantment
 * JD-Core Version:    0.7.0.1
 */