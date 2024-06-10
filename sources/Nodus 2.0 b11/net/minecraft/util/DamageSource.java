/*   1:    */ package net.minecraft.util;
/*   2:    */ 
/*   3:    */ import net.minecraft.entity.Entity;
/*   4:    */ import net.minecraft.entity.EntityLivingBase;
/*   5:    */ import net.minecraft.entity.player.EntityPlayer;
/*   6:    */ import net.minecraft.entity.projectile.EntityArrow;
/*   7:    */ import net.minecraft.entity.projectile.EntityFireball;
/*   8:    */ import net.minecraft.world.Explosion;
/*   9:    */ 
/*  10:    */ public class DamageSource
/*  11:    */ {
/*  12: 12 */   public static DamageSource inFire = new DamageSource("inFire").setFireDamage();
/*  13: 13 */   public static DamageSource onFire = new DamageSource("onFire").setDamageBypassesArmor().setFireDamage();
/*  14: 14 */   public static DamageSource lava = new DamageSource("lava").setFireDamage();
/*  15: 15 */   public static DamageSource inWall = new DamageSource("inWall").setDamageBypassesArmor();
/*  16: 16 */   public static DamageSource drown = new DamageSource("drown").setDamageBypassesArmor();
/*  17: 17 */   public static DamageSource starve = new DamageSource("starve").setDamageBypassesArmor().setDamageIsAbsolute();
/*  18: 18 */   public static DamageSource cactus = new DamageSource("cactus");
/*  19: 19 */   public static DamageSource fall = new DamageSource("fall").setDamageBypassesArmor();
/*  20: 20 */   public static DamageSource outOfWorld = new DamageSource("outOfWorld").setDamageBypassesArmor().setDamageAllowedInCreativeMode();
/*  21: 21 */   public static DamageSource generic = new DamageSource("generic").setDamageBypassesArmor();
/*  22: 22 */   public static DamageSource magic = new DamageSource("magic").setDamageBypassesArmor().setMagicDamage();
/*  23: 23 */   public static DamageSource wither = new DamageSource("wither").setDamageBypassesArmor();
/*  24: 24 */   public static DamageSource anvil = new DamageSource("anvil");
/*  25: 25 */   public static DamageSource fallingBlock = new DamageSource("fallingBlock");
/*  26:    */   private boolean isUnblockable;
/*  27:    */   private boolean isDamageAllowedInCreativeMode;
/*  28:    */   private boolean damageIsAbsolute;
/*  29: 35 */   private float hungerDamage = 0.3F;
/*  30:    */   private boolean fireDamage;
/*  31:    */   private boolean projectile;
/*  32:    */   private boolean difficultyScaled;
/*  33:    */   private boolean magicDamage;
/*  34:    */   private boolean explosion;
/*  35:    */   public String damageType;
/*  36:    */   private static final String __OBFID = "CL_00001521";
/*  37:    */   
/*  38:    */   public static DamageSource causeMobDamage(EntityLivingBase par0EntityLivingBase)
/*  39:    */   {
/*  40: 54 */     return new EntityDamageSource("mob", par0EntityLivingBase);
/*  41:    */   }
/*  42:    */   
/*  43:    */   public static DamageSource causePlayerDamage(EntityPlayer par0EntityPlayer)
/*  44:    */   {
/*  45: 62 */     return new EntityDamageSource("player", par0EntityPlayer);
/*  46:    */   }
/*  47:    */   
/*  48:    */   public static DamageSource causeArrowDamage(EntityArrow par0EntityArrow, Entity par1Entity)
/*  49:    */   {
/*  50: 70 */     return new EntityDamageSourceIndirect("arrow", par0EntityArrow, par1Entity).setProjectile();
/*  51:    */   }
/*  52:    */   
/*  53:    */   public static DamageSource causeFireballDamage(EntityFireball par0EntityFireball, Entity par1Entity)
/*  54:    */   {
/*  55: 78 */     return par1Entity == null ? new EntityDamageSourceIndirect("onFire", par0EntityFireball, par0EntityFireball).setFireDamage().setProjectile() : new EntityDamageSourceIndirect("fireball", par0EntityFireball, par1Entity).setFireDamage().setProjectile();
/*  56:    */   }
/*  57:    */   
/*  58:    */   public static DamageSource causeThrownDamage(Entity par0Entity, Entity par1Entity)
/*  59:    */   {
/*  60: 83 */     return new EntityDamageSourceIndirect("thrown", par0Entity, par1Entity).setProjectile();
/*  61:    */   }
/*  62:    */   
/*  63:    */   public static DamageSource causeIndirectMagicDamage(Entity par0Entity, Entity par1Entity)
/*  64:    */   {
/*  65: 88 */     return new EntityDamageSourceIndirect("indirectMagic", par0Entity, par1Entity).setDamageBypassesArmor().setMagicDamage();
/*  66:    */   }
/*  67:    */   
/*  68:    */   public static DamageSource causeThornsDamage(Entity par0Entity)
/*  69:    */   {
/*  70: 96 */     return new EntityDamageSource("thorns", par0Entity).setMagicDamage();
/*  71:    */   }
/*  72:    */   
/*  73:    */   public static DamageSource setExplosionSource(Explosion par0Explosion)
/*  74:    */   {
/*  75:101 */     return (par0Explosion != null) && (par0Explosion.getExplosivePlacedBy() != null) ? new EntityDamageSource("explosion.player", par0Explosion.getExplosivePlacedBy()).setDifficultyScaled().setExplosion() : new DamageSource("explosion").setDifficultyScaled().setExplosion();
/*  76:    */   }
/*  77:    */   
/*  78:    */   public boolean isProjectile()
/*  79:    */   {
/*  80:109 */     return this.projectile;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public DamageSource setProjectile()
/*  84:    */   {
/*  85:117 */     this.projectile = true;
/*  86:118 */     return this;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public boolean isExplosion()
/*  90:    */   {
/*  91:123 */     return this.explosion;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public DamageSource setExplosion()
/*  95:    */   {
/*  96:128 */     this.explosion = true;
/*  97:129 */     return this;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public boolean isUnblockable()
/* 101:    */   {
/* 102:134 */     return this.isUnblockable;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public float getHungerDamage()
/* 106:    */   {
/* 107:142 */     return this.hungerDamage;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public boolean canHarmInCreative()
/* 111:    */   {
/* 112:147 */     return this.isDamageAllowedInCreativeMode;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public boolean isDamageAbsolute()
/* 116:    */   {
/* 117:155 */     return this.damageIsAbsolute;
/* 118:    */   }
/* 119:    */   
/* 120:    */   protected DamageSource(String par1Str)
/* 121:    */   {
/* 122:160 */     this.damageType = par1Str;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public Entity getSourceOfDamage()
/* 126:    */   {
/* 127:165 */     return getEntity();
/* 128:    */   }
/* 129:    */   
/* 130:    */   public Entity getEntity()
/* 131:    */   {
/* 132:170 */     return null;
/* 133:    */   }
/* 134:    */   
/* 135:    */   protected DamageSource setDamageBypassesArmor()
/* 136:    */   {
/* 137:175 */     this.isUnblockable = true;
/* 138:176 */     this.hungerDamage = 0.0F;
/* 139:177 */     return this;
/* 140:    */   }
/* 141:    */   
/* 142:    */   protected DamageSource setDamageAllowedInCreativeMode()
/* 143:    */   {
/* 144:182 */     this.isDamageAllowedInCreativeMode = true;
/* 145:183 */     return this;
/* 146:    */   }
/* 147:    */   
/* 148:    */   protected DamageSource setDamageIsAbsolute()
/* 149:    */   {
/* 150:192 */     this.damageIsAbsolute = true;
/* 151:193 */     this.hungerDamage = 0.0F;
/* 152:194 */     return this;
/* 153:    */   }
/* 154:    */   
/* 155:    */   protected DamageSource setFireDamage()
/* 156:    */   {
/* 157:202 */     this.fireDamage = true;
/* 158:203 */     return this;
/* 159:    */   }
/* 160:    */   
/* 161:    */   public IChatComponent func_151519_b(EntityLivingBase p_151519_1_)
/* 162:    */   {
/* 163:208 */     EntityLivingBase var2 = p_151519_1_.func_94060_bK();
/* 164:209 */     String var3 = "death.attack." + this.damageType;
/* 165:210 */     String var4 = var3 + ".player";
/* 166:211 */     return (var2 != null) && (StatCollector.canTranslate(var4)) ? new ChatComponentTranslation(var4, new Object[] { p_151519_1_.func_145748_c_(), var2.func_145748_c_() }) : new ChatComponentTranslation(var3, new Object[] { p_151519_1_.func_145748_c_() });
/* 167:    */   }
/* 168:    */   
/* 169:    */   public boolean isFireDamage()
/* 170:    */   {
/* 171:219 */     return this.fireDamage;
/* 172:    */   }
/* 173:    */   
/* 174:    */   public String getDamageType()
/* 175:    */   {
/* 176:227 */     return this.damageType;
/* 177:    */   }
/* 178:    */   
/* 179:    */   public DamageSource setDifficultyScaled()
/* 180:    */   {
/* 181:235 */     this.difficultyScaled = true;
/* 182:236 */     return this;
/* 183:    */   }
/* 184:    */   
/* 185:    */   public boolean isDifficultyScaled()
/* 186:    */   {
/* 187:244 */     return this.difficultyScaled;
/* 188:    */   }
/* 189:    */   
/* 190:    */   public boolean isMagicDamage()
/* 191:    */   {
/* 192:252 */     return this.magicDamage;
/* 193:    */   }
/* 194:    */   
/* 195:    */   public DamageSource setMagicDamage()
/* 196:    */   {
/* 197:260 */     this.magicDamage = true;
/* 198:261 */     return this;
/* 199:    */   }
/* 200:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.util.DamageSource
 * JD-Core Version:    0.7.0.1
 */