/*   1:    */ package net.minecraft.item;
/*   2:    */ 
/*   3:    */ import com.google.common.collect.Maps;
/*   4:    */ import java.util.List;
/*   5:    */ import java.util.Map;
/*   6:    */ import net.minecraft.client.renderer.texture.IIconRegister;
/*   7:    */ import net.minecraft.creativetab.CreativeTabs;
/*   8:    */ import net.minecraft.entity.player.EntityPlayer;
/*   9:    */ import net.minecraft.potion.Potion;
/*  10:    */ import net.minecraft.potion.PotionEffect;
/*  11:    */ import net.minecraft.potion.PotionHelper;
/*  12:    */ import net.minecraft.util.IIcon;
/*  13:    */ import net.minecraft.world.World;
/*  14:    */ 
/*  15:    */ public class ItemFishFood
/*  16:    */   extends ItemFood
/*  17:    */ {
/*  18:    */   private final boolean field_150907_b;
/*  19:    */   private static final String __OBFID = "CL_00000032";
/*  20:    */   
/*  21:    */   public ItemFishFood(boolean p_i45338_1_)
/*  22:    */   {
/*  23: 22 */     super(0, 0.0F, false);
/*  24: 23 */     this.field_150907_b = p_i45338_1_;
/*  25:    */   }
/*  26:    */   
/*  27:    */   public int func_150905_g(ItemStack p_150905_1_)
/*  28:    */   {
/*  29: 28 */     FishType var2 = FishType.func_150978_a(p_150905_1_);
/*  30: 29 */     return (this.field_150907_b) && (var2.func_150973_i()) ? var2.func_150970_e() : var2.func_150975_c();
/*  31:    */   }
/*  32:    */   
/*  33:    */   public float func_150906_h(ItemStack p_150906_1_)
/*  34:    */   {
/*  35: 34 */     FishType var2 = FishType.func_150978_a(p_150906_1_);
/*  36: 35 */     return (this.field_150907_b) && (var2.func_150973_i()) ? var2.func_150977_f() : var2.func_150967_d();
/*  37:    */   }
/*  38:    */   
/*  39:    */   public String getPotionEffect(ItemStack p_150896_1_)
/*  40:    */   {
/*  41: 40 */     return FishType.func_150978_a(p_150896_1_) == FishType.PUFFERFISH ? PotionHelper.field_151423_m : null;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public void registerIcons(IIconRegister par1IconRegister)
/*  45:    */   {
/*  46: 45 */     FishType[] var2 = FishType.values();
/*  47: 46 */     int var3 = var2.length;
/*  48: 48 */     for (int var4 = 0; var4 < var3; var4++)
/*  49:    */     {
/*  50: 50 */       FishType var5 = var2[var4];
/*  51: 51 */       var5.func_150968_a(par1IconRegister);
/*  52:    */     }
/*  53:    */   }
/*  54:    */   
/*  55:    */   protected void onFoodEaten(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
/*  56:    */   {
/*  57: 57 */     FishType var4 = FishType.func_150978_a(par1ItemStack);
/*  58: 59 */     if (var4 == FishType.PUFFERFISH)
/*  59:    */     {
/*  60: 61 */       par3EntityPlayer.addPotionEffect(new PotionEffect(Potion.poison.id, 1200, 3));
/*  61: 62 */       par3EntityPlayer.addPotionEffect(new PotionEffect(Potion.hunger.id, 300, 2));
/*  62: 63 */       par3EntityPlayer.addPotionEffect(new PotionEffect(Potion.confusion.id, 300, 1));
/*  63:    */     }
/*  64: 66 */     super.onFoodEaten(par1ItemStack, par2World, par3EntityPlayer);
/*  65:    */   }
/*  66:    */   
/*  67:    */   public IIcon getIconFromDamage(int par1)
/*  68:    */   {
/*  69: 74 */     FishType var2 = FishType.func_150974_a(par1);
/*  70: 75 */     return (this.field_150907_b) && (var2.func_150973_i()) ? var2.func_150979_h() : var2.func_150971_g();
/*  71:    */   }
/*  72:    */   
/*  73:    */   public void getSubItems(Item p_150895_1_, CreativeTabs p_150895_2_, List p_150895_3_)
/*  74:    */   {
/*  75: 83 */     FishType[] var4 = FishType.values();
/*  76: 84 */     int var5 = var4.length;
/*  77: 86 */     for (int var6 = 0; var6 < var5; var6++)
/*  78:    */     {
/*  79: 88 */       FishType var7 = var4[var6];
/*  80: 90 */       if ((!this.field_150907_b) || (var7.func_150973_i())) {
/*  81: 92 */         p_150895_3_.add(new ItemStack(this, 1, var7.func_150976_a()));
/*  82:    */       }
/*  83:    */     }
/*  84:    */   }
/*  85:    */   
/*  86:    */   public String getUnlocalizedName(ItemStack par1ItemStack)
/*  87:    */   {
/*  88:103 */     FishType var2 = FishType.func_150978_a(par1ItemStack);
/*  89:104 */     return getUnlocalizedName() + "." + var2.func_150972_b() + "." + ((this.field_150907_b) && (var2.func_150973_i()) ? "cooked" : "raw");
/*  90:    */   }
/*  91:    */   
/*  92:    */   public static enum FishType
/*  93:    */   {
/*  94:109 */     COD("COD", 0, 0, "cod", 2, 0.1F, 5, 0.6F),  SALMON("SALMON", 1, 1, "salmon", 2, 0.1F, 6, 0.8F),  CLOWNFISH("CLOWNFISH", 2, 2, "clownfish", 1, 0.1F),  PUFFERFISH("PUFFERFISH", 3, 3, "pufferfish", 1, 0.1F);
/*  95:    */     
/*  96:    */     private static final Map field_150983_e;
/*  97:    */     private final int field_150980_f;
/*  98:    */     private final String field_150981_g;
/*  99:    */     private IIcon field_150993_h;
/* 100:    */     private IIcon field_150994_i;
/* 101:    */     private final int field_150991_j;
/* 102:    */     private final float field_150992_k;
/* 103:    */     private final int field_150989_l;
/* 104:    */     private final float field_150990_m;
/* 105:122 */     private boolean field_150987_n = false;
/* 106:    */     private static final FishType[] $VALUES;
/* 107:    */     private static final String __OBFID = "CL_00000033";
/* 108:    */     
/* 109:    */     private FishType(String p_i45336_1_, int p_i45336_2_, int p_i45336_3_, String p_i45336_4_, int p_i45336_5_, float p_i45336_6_, int p_i45336_7_, float p_i45336_8_)
/* 110:    */     {
/* 111:129 */       this.field_150980_f = p_i45336_3_;
/* 112:130 */       this.field_150981_g = p_i45336_4_;
/* 113:131 */       this.field_150991_j = p_i45336_5_;
/* 114:132 */       this.field_150992_k = p_i45336_6_;
/* 115:133 */       this.field_150989_l = p_i45336_7_;
/* 116:134 */       this.field_150990_m = p_i45336_8_;
/* 117:135 */       this.field_150987_n = true;
/* 118:    */     }
/* 119:    */     
/* 120:    */     private FishType(String p_i45337_1_, int p_i45337_2_, int p_i45337_3_, String p_i45337_4_, int p_i45337_5_, float p_i45337_6_)
/* 121:    */     {
/* 122:140 */       this.field_150980_f = p_i45337_3_;
/* 123:141 */       this.field_150981_g = p_i45337_4_;
/* 124:142 */       this.field_150991_j = p_i45337_5_;
/* 125:143 */       this.field_150992_k = p_i45337_6_;
/* 126:144 */       this.field_150989_l = 0;
/* 127:145 */       this.field_150990_m = 0.0F;
/* 128:146 */       this.field_150987_n = false;
/* 129:    */     }
/* 130:    */     
/* 131:    */     public int func_150976_a()
/* 132:    */     {
/* 133:151 */       return this.field_150980_f;
/* 134:    */     }
/* 135:    */     
/* 136:    */     public String func_150972_b()
/* 137:    */     {
/* 138:156 */       return this.field_150981_g;
/* 139:    */     }
/* 140:    */     
/* 141:    */     public int func_150975_c()
/* 142:    */     {
/* 143:161 */       return this.field_150991_j;
/* 144:    */     }
/* 145:    */     
/* 146:    */     public float func_150967_d()
/* 147:    */     {
/* 148:166 */       return this.field_150992_k;
/* 149:    */     }
/* 150:    */     
/* 151:    */     public int func_150970_e()
/* 152:    */     {
/* 153:171 */       return this.field_150989_l;
/* 154:    */     }
/* 155:    */     
/* 156:    */     public float func_150977_f()
/* 157:    */     {
/* 158:176 */       return this.field_150990_m;
/* 159:    */     }
/* 160:    */     
/* 161:    */     public void func_150968_a(IIconRegister p_150968_1_)
/* 162:    */     {
/* 163:181 */       this.field_150993_h = p_150968_1_.registerIcon("fish_" + this.field_150981_g + "_raw");
/* 164:183 */       if (this.field_150987_n) {
/* 165:185 */         this.field_150994_i = p_150968_1_.registerIcon("fish_" + this.field_150981_g + "_cooked");
/* 166:    */       }
/* 167:    */     }
/* 168:    */     
/* 169:    */     public IIcon func_150971_g()
/* 170:    */     {
/* 171:191 */       return this.field_150993_h;
/* 172:    */     }
/* 173:    */     
/* 174:    */     public IIcon func_150979_h()
/* 175:    */     {
/* 176:196 */       return this.field_150994_i;
/* 177:    */     }
/* 178:    */     
/* 179:    */     public boolean func_150973_i()
/* 180:    */     {
/* 181:201 */       return this.field_150987_n;
/* 182:    */     }
/* 183:    */     
/* 184:    */     public static FishType func_150974_a(int p_150974_0_)
/* 185:    */     {
/* 186:206 */       FishType var1 = (FishType)field_150983_e.get(Integer.valueOf(p_150974_0_));
/* 187:207 */       return var1 == null ? COD : var1;
/* 188:    */     }
/* 189:    */     
/* 190:    */     public static FishType func_150978_a(ItemStack p_150978_0_)
/* 191:    */     {
/* 192:212 */       return (p_150978_0_.getItem() instanceof ItemFishFood) ? func_150974_a(p_150978_0_.getItemDamage()) : COD;
/* 193:    */     }
/* 194:    */     
/* 195:    */     static
/* 196:    */     {
/* 197:113 */       field_150983_e = Maps.newHashMap();
/* 198:    */       
/* 199:    */ 
/* 200:    */ 
/* 201:    */ 
/* 202:    */ 
/* 203:    */ 
/* 204:    */ 
/* 205:    */ 
/* 206:    */ 
/* 207:    */ 
/* 208:124 */       $VALUES = new FishType[] { COD, SALMON, CLOWNFISH, PUFFERFISH };
/* 209:    */       
/* 210:    */ 
/* 211:    */ 
/* 212:    */ 
/* 213:    */ 
/* 214:    */ 
/* 215:    */ 
/* 216:    */ 
/* 217:    */ 
/* 218:    */ 
/* 219:    */ 
/* 220:    */ 
/* 221:    */ 
/* 222:    */ 
/* 223:    */ 
/* 224:    */ 
/* 225:    */ 
/* 226:    */ 
/* 227:    */ 
/* 228:    */ 
/* 229:    */ 
/* 230:    */ 
/* 231:    */ 
/* 232:    */ 
/* 233:    */ 
/* 234:    */ 
/* 235:    */ 
/* 236:    */ 
/* 237:    */ 
/* 238:    */ 
/* 239:    */ 
/* 240:    */ 
/* 241:    */ 
/* 242:    */ 
/* 243:    */ 
/* 244:    */ 
/* 245:    */ 
/* 246:    */ 
/* 247:    */ 
/* 248:    */ 
/* 249:    */ 
/* 250:    */ 
/* 251:    */ 
/* 252:    */ 
/* 253:    */ 
/* 254:    */ 
/* 255:    */ 
/* 256:    */ 
/* 257:    */ 
/* 258:    */ 
/* 259:    */ 
/* 260:    */ 
/* 261:    */ 
/* 262:    */ 
/* 263:    */ 
/* 264:    */ 
/* 265:    */ 
/* 266:    */ 
/* 267:    */ 
/* 268:    */ 
/* 269:    */ 
/* 270:    */ 
/* 271:    */ 
/* 272:    */ 
/* 273:    */ 
/* 274:    */ 
/* 275:    */ 
/* 276:    */ 
/* 277:    */ 
/* 278:    */ 
/* 279:    */ 
/* 280:    */ 
/* 281:    */ 
/* 282:    */ 
/* 283:    */ 
/* 284:    */ 
/* 285:    */ 
/* 286:    */ 
/* 287:    */ 
/* 288:    */ 
/* 289:    */ 
/* 290:    */ 
/* 291:    */ 
/* 292:    */ 
/* 293:    */ 
/* 294:    */ 
/* 295:    */ 
/* 296:    */ 
/* 297:    */ 
/* 298:    */ 
/* 299:    */ 
/* 300:216 */       FishType[] var0 = values();
/* 301:217 */       int var1 = var0.length;
/* 302:219 */       for (int var2 = 0; var2 < var1; var2++)
/* 303:    */       {
/* 304:221 */         FishType var3 = var0[var2];
/* 305:222 */         field_150983_e.put(Integer.valueOf(var3.func_150976_a()), var3);
/* 306:    */       }
/* 307:    */     }
/* 308:    */   }
/* 309:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.item.ItemFishFood
 * JD-Core Version:    0.7.0.1
 */