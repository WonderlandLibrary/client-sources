/*   1:    */ package net.minecraft.potion;
/*   2:    */ 
/*   3:    */ import com.google.common.collect.Maps;
/*   4:    */ import java.util.Iterator;
/*   5:    */ import java.util.Map;
/*   6:    */ import java.util.Map.Entry;
/*   7:    */ import java.util.Set;
/*   8:    */ import java.util.UUID;
/*   9:    */ import net.minecraft.entity.EntityLivingBase;
/*  10:    */ import net.minecraft.entity.SharedMonsterAttributes;
/*  11:    */ import net.minecraft.entity.ai.attributes.AttributeModifier;
/*  12:    */ import net.minecraft.entity.ai.attributes.BaseAttributeMap;
/*  13:    */ import net.minecraft.entity.ai.attributes.IAttribute;
/*  14:    */ import net.minecraft.entity.ai.attributes.IAttributeInstance;
/*  15:    */ import net.minecraft.entity.player.EntityPlayer;
/*  16:    */ import net.minecraft.util.DamageSource;
/*  17:    */ import net.minecraft.util.FoodStats;
/*  18:    */ import net.minecraft.util.StringUtils;
/*  19:    */ import net.minecraft.world.World;
/*  20:    */ 
/*  21:    */ public class Potion
/*  22:    */ {
/*  23: 21 */   public static final Potion[] potionTypes = new Potion[32];
/*  24: 22 */   public static final Potion field_76423_b = null;
/*  25: 23 */   public static final Potion moveSpeed = new Potion(1, false, 8171462).setPotionName("potion.moveSpeed").setIconIndex(0, 0).func_111184_a(SharedMonsterAttributes.movementSpeed, "91AEAA56-376B-4498-935B-2F7F68070635", 0.2000000029802322D, 2);
/*  26: 24 */   public static final Potion moveSlowdown = new Potion(2, true, 5926017).setPotionName("potion.moveSlowdown").setIconIndex(1, 0).func_111184_a(SharedMonsterAttributes.movementSpeed, "7107DE5E-7CE8-4030-940E-514C1F160890", -0.1500000059604645D, 2);
/*  27: 25 */   public static final Potion digSpeed = new Potion(3, false, 14270531).setPotionName("potion.digSpeed").setIconIndex(2, 0).setEffectiveness(1.5D);
/*  28: 26 */   public static final Potion digSlowdown = new Potion(4, true, 4866583).setPotionName("potion.digSlowDown").setIconIndex(3, 0);
/*  29: 27 */   public static final Potion damageBoost = new PotionAttackDamage(5, false, 9643043).setPotionName("potion.damageBoost").setIconIndex(4, 0).func_111184_a(SharedMonsterAttributes.attackDamage, "648D7064-6A60-4F59-8ABE-C2C23A6DD7A9", 3.0D, 2);
/*  30: 28 */   public static final Potion heal = new PotionHealth(6, false, 16262179).setPotionName("potion.heal");
/*  31: 29 */   public static final Potion harm = new PotionHealth(7, true, 4393481).setPotionName("potion.harm");
/*  32: 30 */   public static final Potion jump = new Potion(8, false, 7889559).setPotionName("potion.jump").setIconIndex(2, 1);
/*  33: 31 */   public static final Potion confusion = new Potion(9, true, 5578058).setPotionName("potion.confusion").setIconIndex(3, 1).setEffectiveness(0.25D);
/*  34: 34 */   public static final Potion regeneration = new Potion(10, false, 13458603).setPotionName("potion.regeneration").setIconIndex(7, 0).setEffectiveness(0.25D);
/*  35: 35 */   public static final Potion resistance = new Potion(11, false, 10044730).setPotionName("potion.resistance").setIconIndex(6, 1);
/*  36: 38 */   public static final Potion fireResistance = new Potion(12, false, 14981690).setPotionName("potion.fireResistance").setIconIndex(7, 1);
/*  37: 41 */   public static final Potion waterBreathing = new Potion(13, false, 3035801).setPotionName("potion.waterBreathing").setIconIndex(0, 2);
/*  38: 44 */   public static final Potion invisibility = new Potion(14, false, 8356754).setPotionName("potion.invisibility").setIconIndex(0, 1);
/*  39: 47 */   public static final Potion blindness = new Potion(15, true, 2039587).setPotionName("potion.blindness").setIconIndex(5, 1).setEffectiveness(0.25D);
/*  40: 50 */   public static final Potion nightVision = new Potion(16, false, 2039713).setPotionName("potion.nightVision").setIconIndex(4, 1);
/*  41: 53 */   public static final Potion hunger = new Potion(17, true, 5797459).setPotionName("potion.hunger").setIconIndex(1, 1);
/*  42: 56 */   public static final Potion weakness = new PotionAttackDamage(18, true, 4738376).setPotionName("potion.weakness").setIconIndex(5, 0).func_111184_a(SharedMonsterAttributes.attackDamage, "22653B89-116E-49DC-9B6B-9971489B5BE5", 2.0D, 0);
/*  43: 59 */   public static final Potion poison = new Potion(19, true, 5149489).setPotionName("potion.poison").setIconIndex(6, 0).setEffectiveness(0.25D);
/*  44: 62 */   public static final Potion wither = new Potion(20, true, 3484199).setPotionName("potion.wither").setIconIndex(1, 2).setEffectiveness(0.25D);
/*  45: 63 */   public static final Potion field_76434_w = new PotionHealthBoost(21, false, 16284963).setPotionName("potion.healthBoost").setIconIndex(2, 2).func_111184_a(SharedMonsterAttributes.maxHealth, "5D6F0BA2-1186-46AC-B896-C61C5CEE99CC", 4.0D, 0);
/*  46: 64 */   public static final Potion field_76444_x = new PotionAbsoption(22, false, 2445989).setPotionName("potion.absorption").setIconIndex(2, 2);
/*  47: 65 */   public static final Potion field_76443_y = new PotionHealth(23, false, 16262179).setPotionName("potion.saturation");
/*  48: 66 */   public static final Potion field_76442_z = null;
/*  49: 67 */   public static final Potion field_76409_A = null;
/*  50: 68 */   public static final Potion field_76410_B = null;
/*  51: 69 */   public static final Potion field_76411_C = null;
/*  52: 70 */   public static final Potion field_76405_D = null;
/*  53: 71 */   public static final Potion field_76406_E = null;
/*  54: 72 */   public static final Potion field_76407_F = null;
/*  55: 73 */   public static final Potion field_76408_G = null;
/*  56:    */   public final int id;
/*  57: 77 */   private final Map field_111188_I = Maps.newHashMap();
/*  58:    */   private final boolean isBadEffect;
/*  59:    */   private final int liquidColor;
/*  60: 88 */   private String name = "";
/*  61: 91 */   private int statusIconIndex = -1;
/*  62:    */   private double effectiveness;
/*  63:    */   private boolean usable;
/*  64:    */   private static final String __OBFID = "CL_00001528";
/*  65:    */   
/*  66:    */   protected Potion(int par1, boolean par2, int par3)
/*  67:    */   {
/*  68: 98 */     this.id = par1;
/*  69: 99 */     potionTypes[par1] = this;
/*  70:100 */     this.isBadEffect = par2;
/*  71:102 */     if (par2) {
/*  72:104 */       this.effectiveness = 0.5D;
/*  73:    */     } else {
/*  74:108 */       this.effectiveness = 1.0D;
/*  75:    */     }
/*  76:111 */     this.liquidColor = par3;
/*  77:    */   }
/*  78:    */   
/*  79:    */   protected Potion setIconIndex(int par1, int par2)
/*  80:    */   {
/*  81:119 */     this.statusIconIndex = (par1 + par2 * 8);
/*  82:120 */     return this;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public int getId()
/*  86:    */   {
/*  87:128 */     return this.id;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public void performEffect(EntityLivingBase par1EntityLivingBase, int par2)
/*  91:    */   {
/*  92:133 */     if (this.id == regeneration.id)
/*  93:    */     {
/*  94:135 */       if (par1EntityLivingBase.getHealth() < par1EntityLivingBase.getMaxHealth()) {
/*  95:137 */         par1EntityLivingBase.heal(1.0F);
/*  96:    */       }
/*  97:    */     }
/*  98:140 */     else if (this.id == poison.id)
/*  99:    */     {
/* 100:142 */       if (par1EntityLivingBase.getHealth() > 1.0F) {
/* 101:144 */         par1EntityLivingBase.attackEntityFrom(DamageSource.magic, 1.0F);
/* 102:    */       }
/* 103:    */     }
/* 104:147 */     else if (this.id == wither.id) {
/* 105:149 */       par1EntityLivingBase.attackEntityFrom(DamageSource.wither, 1.0F);
/* 106:151 */     } else if ((this.id == hunger.id) && ((par1EntityLivingBase instanceof EntityPlayer))) {
/* 107:153 */       ((EntityPlayer)par1EntityLivingBase).addExhaustion(0.025F * (par2 + 1));
/* 108:155 */     } else if ((this.id == field_76443_y.id) && ((par1EntityLivingBase instanceof EntityPlayer)))
/* 109:    */     {
/* 110:157 */       if (!par1EntityLivingBase.worldObj.isClient) {
/* 111:159 */         ((EntityPlayer)par1EntityLivingBase).getFoodStats().addStats(par2 + 1, 1.0F);
/* 112:    */       }
/* 113:    */     }
/* 114:162 */     else if (((this.id != heal.id) || (par1EntityLivingBase.isEntityUndead())) && ((this.id != harm.id) || (!par1EntityLivingBase.isEntityUndead())))
/* 115:    */     {
/* 116:164 */       if (((this.id == harm.id) && (!par1EntityLivingBase.isEntityUndead())) || ((this.id == heal.id) && (par1EntityLivingBase.isEntityUndead()))) {
/* 117:166 */         par1EntityLivingBase.attackEntityFrom(DamageSource.magic, 6 << par2);
/* 118:    */       }
/* 119:    */     }
/* 120:    */     else {
/* 121:171 */       par1EntityLivingBase.heal(Math.max(4 << par2, 0));
/* 122:    */     }
/* 123:    */   }
/* 124:    */   
/* 125:    */   public void affectEntity(EntityLivingBase par1EntityLivingBase, EntityLivingBase par2EntityLivingBase, int par3, double par4)
/* 126:    */   {
/* 127:182 */     if (((this.id != heal.id) || (par2EntityLivingBase.isEntityUndead())) && ((this.id != harm.id) || (!par2EntityLivingBase.isEntityUndead())))
/* 128:    */     {
/* 129:184 */       if (((this.id == harm.id) && (!par2EntityLivingBase.isEntityUndead())) || ((this.id == heal.id) && (par2EntityLivingBase.isEntityUndead())))
/* 130:    */       {
/* 131:186 */         int var6 = (int)(par4 * (6 << par3) + 0.5D);
/* 132:188 */         if (par1EntityLivingBase == null) {
/* 133:190 */           par2EntityLivingBase.attackEntityFrom(DamageSource.magic, var6);
/* 134:    */         } else {
/* 135:194 */           par2EntityLivingBase.attackEntityFrom(DamageSource.causeIndirectMagicDamage(par2EntityLivingBase, par1EntityLivingBase), var6);
/* 136:    */         }
/* 137:    */       }
/* 138:    */     }
/* 139:    */     else
/* 140:    */     {
/* 141:200 */       int var6 = (int)(par4 * (4 << par3) + 0.5D);
/* 142:201 */       par2EntityLivingBase.heal(var6);
/* 143:    */     }
/* 144:    */   }
/* 145:    */   
/* 146:    */   public boolean isInstant()
/* 147:    */   {
/* 148:210 */     return false;
/* 149:    */   }
/* 150:    */   
/* 151:    */   public boolean isReady(int par1, int par2)
/* 152:    */   {
/* 153:220 */     if (this.id == regeneration.id)
/* 154:    */     {
/* 155:222 */       int var3 = 50 >> par2;
/* 156:223 */       return par1 % var3 == 0;
/* 157:    */     }
/* 158:225 */     if (this.id == poison.id)
/* 159:    */     {
/* 160:227 */       int var3 = 25 >> par2;
/* 161:228 */       return par1 % var3 == 0;
/* 162:    */     }
/* 163:230 */     if (this.id == wither.id)
/* 164:    */     {
/* 165:232 */       int var3 = 40 >> par2;
/* 166:233 */       return par1 % var3 == 0;
/* 167:    */     }
/* 168:237 */     return this.id == hunger.id;
/* 169:    */   }
/* 170:    */   
/* 171:    */   public Potion setPotionName(String par1Str)
/* 172:    */   {
/* 173:246 */     this.name = par1Str;
/* 174:247 */     return this;
/* 175:    */   }
/* 176:    */   
/* 177:    */   public String getName()
/* 178:    */   {
/* 179:255 */     return this.name;
/* 180:    */   }
/* 181:    */   
/* 182:    */   public boolean hasStatusIcon()
/* 183:    */   {
/* 184:263 */     return this.statusIconIndex >= 0;
/* 185:    */   }
/* 186:    */   
/* 187:    */   public int getStatusIconIndex()
/* 188:    */   {
/* 189:271 */     return this.statusIconIndex;
/* 190:    */   }
/* 191:    */   
/* 192:    */   public boolean isBadEffect()
/* 193:    */   {
/* 194:279 */     return this.isBadEffect;
/* 195:    */   }
/* 196:    */   
/* 197:    */   public static String getDurationString(PotionEffect par0PotionEffect)
/* 198:    */   {
/* 199:284 */     if (par0PotionEffect.getIsPotionDurationMax()) {
/* 200:286 */       return "**:**";
/* 201:    */     }
/* 202:290 */     int var1 = par0PotionEffect.getDuration();
/* 203:291 */     return StringUtils.ticksToElapsedTime(var1);
/* 204:    */   }
/* 205:    */   
/* 206:    */   protected Potion setEffectiveness(double par1)
/* 207:    */   {
/* 208:297 */     this.effectiveness = par1;
/* 209:298 */     return this;
/* 210:    */   }
/* 211:    */   
/* 212:    */   public double getEffectiveness()
/* 213:    */   {
/* 214:303 */     return this.effectiveness;
/* 215:    */   }
/* 216:    */   
/* 217:    */   public boolean isUsable()
/* 218:    */   {
/* 219:308 */     return this.usable;
/* 220:    */   }
/* 221:    */   
/* 222:    */   public int getLiquidColor()
/* 223:    */   {
/* 224:316 */     return this.liquidColor;
/* 225:    */   }
/* 226:    */   
/* 227:    */   public Potion func_111184_a(IAttribute par1Attribute, String par2Str, double par3, int par5)
/* 228:    */   {
/* 229:321 */     AttributeModifier var6 = new AttributeModifier(UUID.fromString(par2Str), getName(), par3, par5);
/* 230:322 */     this.field_111188_I.put(par1Attribute, var6);
/* 231:323 */     return this;
/* 232:    */   }
/* 233:    */   
/* 234:    */   public Map func_111186_k()
/* 235:    */   {
/* 236:328 */     return this.field_111188_I;
/* 237:    */   }
/* 238:    */   
/* 239:    */   public void removeAttributesModifiersFromEntity(EntityLivingBase par1EntityLivingBase, BaseAttributeMap par2BaseAttributeMap, int par3)
/* 240:    */   {
/* 241:333 */     Iterator var4 = this.field_111188_I.entrySet().iterator();
/* 242:335 */     while (var4.hasNext())
/* 243:    */     {
/* 244:337 */       Map.Entry var5 = (Map.Entry)var4.next();
/* 245:338 */       IAttributeInstance var6 = par2BaseAttributeMap.getAttributeInstance((IAttribute)var5.getKey());
/* 246:340 */       if (var6 != null) {
/* 247:342 */         var6.removeModifier((AttributeModifier)var5.getValue());
/* 248:    */       }
/* 249:    */     }
/* 250:    */   }
/* 251:    */   
/* 252:    */   public void applyAttributesModifiersToEntity(EntityLivingBase par1EntityLivingBase, BaseAttributeMap par2BaseAttributeMap, int par3)
/* 253:    */   {
/* 254:349 */     Iterator var4 = this.field_111188_I.entrySet().iterator();
/* 255:351 */     while (var4.hasNext())
/* 256:    */     {
/* 257:353 */       Map.Entry var5 = (Map.Entry)var4.next();
/* 258:354 */       IAttributeInstance var6 = par2BaseAttributeMap.getAttributeInstance((IAttribute)var5.getKey());
/* 259:356 */       if (var6 != null)
/* 260:    */       {
/* 261:358 */         AttributeModifier var7 = (AttributeModifier)var5.getValue();
/* 262:359 */         var6.removeModifier(var7);
/* 263:360 */         var6.applyModifier(new AttributeModifier(var7.getID(), getName() + " " + par3, func_111183_a(par3, var7), var7.getOperation()));
/* 264:    */       }
/* 265:    */     }
/* 266:    */   }
/* 267:    */   
/* 268:    */   public double func_111183_a(int par1, AttributeModifier par2AttributeModifier)
/* 269:    */   {
/* 270:367 */     return par2AttributeModifier.getAmount() * (par1 + 1);
/* 271:    */   }
/* 272:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.potion.Potion
 * JD-Core Version:    0.7.0.1
 */