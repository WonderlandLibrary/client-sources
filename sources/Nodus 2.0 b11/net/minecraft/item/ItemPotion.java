/*   1:    */ package net.minecraft.item;
/*   2:    */ 
/*   3:    */ import com.google.common.collect.HashMultimap;
/*   4:    */ import java.text.DecimalFormat;
/*   5:    */ import java.util.ArrayList;
/*   6:    */ import java.util.Collection;
/*   7:    */ import java.util.HashMap;
/*   8:    */ import java.util.Iterator;
/*   9:    */ import java.util.LinkedHashMap;
/*  10:    */ import java.util.List;
/*  11:    */ import java.util.Map;
/*  12:    */ import java.util.Map.Entry;
/*  13:    */ import java.util.Random;
/*  14:    */ import java.util.Set;
/*  15:    */ import net.minecraft.client.renderer.texture.IIconRegister;
/*  16:    */ import net.minecraft.creativetab.CreativeTabs;
/*  17:    */ import net.minecraft.entity.ai.attributes.AttributeModifier;
/*  18:    */ import net.minecraft.entity.ai.attributes.IAttribute;
/*  19:    */ import net.minecraft.entity.player.EntityPlayer;
/*  20:    */ import net.minecraft.entity.player.InventoryPlayer;
/*  21:    */ import net.minecraft.entity.player.PlayerCapabilities;
/*  22:    */ import net.minecraft.entity.projectile.EntityPotion;
/*  23:    */ import net.minecraft.init.Items;
/*  24:    */ import net.minecraft.nbt.NBTTagCompound;
/*  25:    */ import net.minecraft.nbt.NBTTagList;
/*  26:    */ import net.minecraft.potion.Potion;
/*  27:    */ import net.minecraft.potion.PotionEffect;
/*  28:    */ import net.minecraft.potion.PotionHelper;
/*  29:    */ import net.minecraft.util.EnumChatFormatting;
/*  30:    */ import net.minecraft.util.IIcon;
/*  31:    */ import net.minecraft.util.StatCollector;
/*  32:    */ import net.minecraft.world.World;
/*  33:    */ 
/*  34:    */ public class ItemPotion
/*  35:    */   extends Item
/*  36:    */ {
/*  37: 34 */   private HashMap effectCache = new HashMap();
/*  38: 35 */   private static final Map field_77835_b = new LinkedHashMap();
/*  39:    */   private IIcon field_94591_c;
/*  40:    */   private IIcon field_94590_d;
/*  41:    */   private IIcon field_94592_ct;
/*  42:    */   private static final String __OBFID = "CL_00000055";
/*  43:    */   
/*  44:    */   public ItemPotion()
/*  45:    */   {
/*  46: 43 */     setMaxStackSize(1);
/*  47: 44 */     setHasSubtypes(true);
/*  48: 45 */     setMaxDamage(0);
/*  49: 46 */     setCreativeTab(CreativeTabs.tabBrewing);
/*  50:    */   }
/*  51:    */   
/*  52:    */   public List getEffects(ItemStack par1ItemStack)
/*  53:    */   {
/*  54: 54 */     if ((par1ItemStack.hasTagCompound()) && (par1ItemStack.getTagCompound().func_150297_b("CustomPotionEffects", 9)))
/*  55:    */     {
/*  56: 56 */       ArrayList var7 = new ArrayList();
/*  57: 57 */       NBTTagList var3 = par1ItemStack.getTagCompound().getTagList("CustomPotionEffects", 10);
/*  58: 59 */       for (int var4 = 0; var4 < var3.tagCount(); var4++)
/*  59:    */       {
/*  60: 61 */         NBTTagCompound var5 = var3.getCompoundTagAt(var4);
/*  61: 62 */         PotionEffect var6 = PotionEffect.readCustomPotionEffectFromNBT(var5);
/*  62: 64 */         if (var6 != null) {
/*  63: 66 */           var7.add(var6);
/*  64:    */         }
/*  65:    */       }
/*  66: 70 */       return var7;
/*  67:    */     }
/*  68: 74 */     List var2 = (List)this.effectCache.get(Integer.valueOf(par1ItemStack.getItemDamage()));
/*  69: 76 */     if (var2 == null)
/*  70:    */     {
/*  71: 78 */       var2 = PotionHelper.getPotionEffects(par1ItemStack.getItemDamage(), false);
/*  72: 79 */       this.effectCache.put(Integer.valueOf(par1ItemStack.getItemDamage()), var2);
/*  73:    */     }
/*  74: 82 */     return var2;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public List getEffects(int par1)
/*  78:    */   {
/*  79: 91 */     List var2 = (List)this.effectCache.get(Integer.valueOf(par1));
/*  80: 93 */     if (var2 == null)
/*  81:    */     {
/*  82: 95 */       var2 = PotionHelper.getPotionEffects(par1, false);
/*  83: 96 */       this.effectCache.put(Integer.valueOf(par1), var2);
/*  84:    */     }
/*  85: 99 */     return var2;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public ItemStack onEaten(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
/*  89:    */   {
/*  90:104 */     if (!par3EntityPlayer.capabilities.isCreativeMode) {
/*  91:106 */       par1ItemStack.stackSize -= 1;
/*  92:    */     }
/*  93:109 */     if (!par2World.isClient)
/*  94:    */     {
/*  95:111 */       List var4 = getEffects(par1ItemStack);
/*  96:113 */       if (var4 != null)
/*  97:    */       {
/*  98:115 */         Iterator var5 = var4.iterator();
/*  99:117 */         while (var5.hasNext())
/* 100:    */         {
/* 101:119 */           PotionEffect var6 = (PotionEffect)var5.next();
/* 102:120 */           par3EntityPlayer.addPotionEffect(new PotionEffect(var6));
/* 103:    */         }
/* 104:    */       }
/* 105:    */     }
/* 106:125 */     if (!par3EntityPlayer.capabilities.isCreativeMode)
/* 107:    */     {
/* 108:127 */       if (par1ItemStack.stackSize <= 0) {
/* 109:129 */         return new ItemStack(Items.glass_bottle);
/* 110:    */       }
/* 111:132 */       par3EntityPlayer.inventory.addItemStackToInventory(new ItemStack(Items.glass_bottle));
/* 112:    */     }
/* 113:135 */     return par1ItemStack;
/* 114:    */   }
/* 115:    */   
/* 116:    */   public int getMaxItemUseDuration(ItemStack par1ItemStack)
/* 117:    */   {
/* 118:143 */     return 32;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public EnumAction getItemUseAction(ItemStack par1ItemStack)
/* 122:    */   {
/* 123:151 */     return EnumAction.drink;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
/* 127:    */   {
/* 128:159 */     if (isSplash(par1ItemStack.getItemDamage()))
/* 129:    */     {
/* 130:161 */       if (!par3EntityPlayer.capabilities.isCreativeMode) {
/* 131:163 */         par1ItemStack.stackSize -= 1;
/* 132:    */       }
/* 133:166 */       par2World.playSoundAtEntity(par3EntityPlayer, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
/* 134:168 */       if (!par2World.isClient) {
/* 135:170 */         par2World.spawnEntityInWorld(new EntityPotion(par2World, par3EntityPlayer, par1ItemStack));
/* 136:    */       }
/* 137:173 */       return par1ItemStack;
/* 138:    */     }
/* 139:177 */     par3EntityPlayer.setItemInUse(par1ItemStack, getMaxItemUseDuration(par1ItemStack));
/* 140:178 */     return par1ItemStack;
/* 141:    */   }
/* 142:    */   
/* 143:    */   public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
/* 144:    */   {
/* 145:188 */     return false;
/* 146:    */   }
/* 147:    */   
/* 148:    */   public IIcon getIconFromDamage(int par1)
/* 149:    */   {
/* 150:196 */     return isSplash(par1) ? this.field_94591_c : this.field_94590_d;
/* 151:    */   }
/* 152:    */   
/* 153:    */   public IIcon getIconFromDamageForRenderPass(int par1, int par2)
/* 154:    */   {
/* 155:204 */     return par2 == 0 ? this.field_94592_ct : super.getIconFromDamageForRenderPass(par1, par2);
/* 156:    */   }
/* 157:    */   
/* 158:    */   public static boolean isSplash(int par0)
/* 159:    */   {
/* 160:212 */     return (par0 & 0x4000) != 0;
/* 161:    */   }
/* 162:    */   
/* 163:    */   public int getColorFromDamage(int par1)
/* 164:    */   {
/* 165:217 */     return PotionHelper.func_77915_a(par1, false);
/* 166:    */   }
/* 167:    */   
/* 168:    */   public int getColorFromItemStack(ItemStack par1ItemStack, int par2)
/* 169:    */   {
/* 170:222 */     return par2 > 0 ? 16777215 : getColorFromDamage(par1ItemStack.getItemDamage());
/* 171:    */   }
/* 172:    */   
/* 173:    */   public boolean requiresMultipleRenderPasses()
/* 174:    */   {
/* 175:227 */     return true;
/* 176:    */   }
/* 177:    */   
/* 178:    */   public boolean isEffectInstant(int par1)
/* 179:    */   {
/* 180:232 */     List var2 = getEffects(par1);
/* 181:234 */     if ((var2 != null) && (!var2.isEmpty()))
/* 182:    */     {
/* 183:236 */       Iterator var3 = var2.iterator();
/* 184:    */       PotionEffect var4;
/* 185:    */       do
/* 186:    */       {
/* 187:241 */         if (!var3.hasNext()) {
/* 188:243 */           return false;
/* 189:    */         }
/* 190:246 */         var4 = (PotionEffect)var3.next();
/* 191:248 */       } while (!Potion.potionTypes[var4.getPotionID()].isInstant());
/* 192:250 */       return true;
/* 193:    */     }
/* 194:254 */     return false;
/* 195:    */   }
/* 196:    */   
/* 197:    */   public String getItemStackDisplayName(ItemStack par1ItemStack)
/* 198:    */   {
/* 199:260 */     if (par1ItemStack.getItemDamage() == 0) {
/* 200:262 */       return StatCollector.translateToLocal("item.emptyPotion.name").trim();
/* 201:    */     }
/* 202:266 */     String var2 = "";
/* 203:268 */     if (isSplash(par1ItemStack.getItemDamage())) {
/* 204:270 */       var2 = StatCollector.translateToLocal("potion.prefix.grenade").trim() + " ";
/* 205:    */     }
/* 206:273 */     List var3 = Items.potionitem.getEffects(par1ItemStack);
/* 207:276 */     if ((var3 != null) && (!var3.isEmpty()))
/* 208:    */     {
/* 209:278 */       String var4 = ((PotionEffect)var3.get(0)).getEffectName();
/* 210:279 */       var4 = var4 + ".postfix";
/* 211:280 */       return var2 + StatCollector.translateToLocal(var4).trim();
/* 212:    */     }
/* 213:284 */     String var4 = PotionHelper.func_77905_c(par1ItemStack.getItemDamage());
/* 214:285 */     return StatCollector.translateToLocal(var4).trim() + " " + super.getItemStackDisplayName(par1ItemStack);
/* 215:    */   }
/* 216:    */   
/* 217:    */   public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
/* 218:    */   {
/* 219:295 */     if (par1ItemStack.getItemDamage() != 0)
/* 220:    */     {
/* 221:297 */       List var5 = Items.potionitem.getEffects(par1ItemStack);
/* 222:298 */       HashMultimap var6 = HashMultimap.create();
/* 223:301 */       if ((var5 != null) && (!var5.isEmpty()))
/* 224:    */       {
/* 225:303 */         Iterator var16 = var5.iterator();
/* 226:305 */         while (var16.hasNext())
/* 227:    */         {
/* 228:307 */           PotionEffect var8 = (PotionEffect)var16.next();
/* 229:308 */           String var9 = StatCollector.translateToLocal(var8.getEffectName()).trim();
/* 230:309 */           Potion var10 = Potion.potionTypes[var8.getPotionID()];
/* 231:310 */           Map var11 = var10.func_111186_k();
/* 232:312 */           if ((var11 != null) && (var11.size() > 0))
/* 233:    */           {
/* 234:314 */             Iterator var12 = var11.entrySet().iterator();
/* 235:316 */             while (var12.hasNext())
/* 236:    */             {
/* 237:318 */               Map.Entry var13 = (Map.Entry)var12.next();
/* 238:319 */               AttributeModifier var14 = (AttributeModifier)var13.getValue();
/* 239:320 */               AttributeModifier var15 = new AttributeModifier(var14.getName(), var10.func_111183_a(var8.getAmplifier(), var14), var14.getOperation());
/* 240:321 */               var6.put(((IAttribute)var13.getKey()).getAttributeUnlocalizedName(), var15);
/* 241:    */             }
/* 242:    */           }
/* 243:325 */           if (var8.getAmplifier() > 0) {
/* 244:327 */             var9 = var9 + " " + StatCollector.translateToLocal(new StringBuilder("potion.potency.").append(var8.getAmplifier()).toString()).trim();
/* 245:    */           }
/* 246:330 */           if (var8.getDuration() > 20) {
/* 247:332 */             var9 = var9 + " (" + Potion.getDurationString(var8) + ")";
/* 248:    */           }
/* 249:335 */           if (var10.isBadEffect()) {
/* 250:337 */             par3List.add(EnumChatFormatting.RED + var9);
/* 251:    */           } else {
/* 252:341 */             par3List.add(EnumChatFormatting.GRAY + var9);
/* 253:    */           }
/* 254:    */         }
/* 255:    */       }
/* 256:    */       else
/* 257:    */       {
/* 258:347 */         String var7 = StatCollector.translateToLocal("potion.empty").trim();
/* 259:348 */         par3List.add(EnumChatFormatting.GRAY + var7);
/* 260:    */       }
/* 261:351 */       if (!var6.isEmpty())
/* 262:    */       {
/* 263:353 */         par3List.add("");
/* 264:354 */         par3List.add(EnumChatFormatting.DARK_PURPLE + StatCollector.translateToLocal("potion.effects.whenDrank"));
/* 265:355 */         Iterator var16 = var6.entries().iterator();
/* 266:357 */         while (var16.hasNext())
/* 267:    */         {
/* 268:359 */           Map.Entry var17 = (Map.Entry)var16.next();
/* 269:360 */           AttributeModifier var18 = (AttributeModifier)var17.getValue();
/* 270:361 */           double var19 = var18.getAmount();
/* 271:    */           double var20;
/* 272:    */           double var20;
/* 273:364 */           if ((var18.getOperation() != 1) && (var18.getOperation() != 2)) {
/* 274:366 */             var20 = var18.getAmount();
/* 275:    */           } else {
/* 276:370 */             var20 = var18.getAmount() * 100.0D;
/* 277:    */           }
/* 278:373 */           if (var19 > 0.0D)
/* 279:    */           {
/* 280:375 */             par3List.add(EnumChatFormatting.BLUE + StatCollector.translateToLocalFormatted(new StringBuilder("attribute.modifier.plus.").append(var18.getOperation()).toString(), new Object[] { ItemStack.field_111284_a.format(var20), StatCollector.translateToLocal("attribute.name." + (String)var17.getKey()) }));
/* 281:    */           }
/* 282:377 */           else if (var19 < 0.0D)
/* 283:    */           {
/* 284:379 */             var20 *= -1.0D;
/* 285:380 */             par3List.add(EnumChatFormatting.RED + StatCollector.translateToLocalFormatted(new StringBuilder("attribute.modifier.take.").append(var18.getOperation()).toString(), new Object[] { ItemStack.field_111284_a.format(var20), StatCollector.translateToLocal("attribute.name." + (String)var17.getKey()) }));
/* 286:    */           }
/* 287:    */         }
/* 288:    */       }
/* 289:    */     }
/* 290:    */   }
/* 291:    */   
/* 292:    */   public boolean hasEffect(ItemStack par1ItemStack)
/* 293:    */   {
/* 294:389 */     List var2 = getEffects(par1ItemStack);
/* 295:390 */     return (var2 != null) && (!var2.isEmpty());
/* 296:    */   }
/* 297:    */   
/* 298:    */   public void getSubItems(Item p_150895_1_, CreativeTabs p_150895_2_, List p_150895_3_)
/* 299:    */   {
/* 300:398 */     super.getSubItems(p_150895_1_, p_150895_2_, p_150895_3_);
/* 301:401 */     if (field_77835_b.isEmpty()) {
/* 302:403 */       for (int var4 = 0; var4 <= 15; var4++) {
/* 303:405 */         for (int var5 = 0; var5 <= 1; var5++)
/* 304:    */         {
/* 305:    */           int var6;
/* 306:    */           int var6;
/* 307:409 */           if (var5 == 0) {
/* 308:411 */             var6 = var4 | 0x2000;
/* 309:    */           } else {
/* 310:415 */             var6 = var4 | 0x4000;
/* 311:    */           }
/* 312:418 */           for (int var7 = 0; var7 <= 2; var7++)
/* 313:    */           {
/* 314:420 */             int var8 = var6;
/* 315:422 */             if (var7 != 0) {
/* 316:424 */               if (var7 == 1) {
/* 317:426 */                 var8 = var6 | 0x20;
/* 318:428 */               } else if (var7 == 2) {
/* 319:430 */                 var8 = var6 | 0x40;
/* 320:    */               }
/* 321:    */             }
/* 322:434 */             List var9 = PotionHelper.getPotionEffects(var8, false);
/* 323:436 */             if ((var9 != null) && (!var9.isEmpty())) {
/* 324:438 */               field_77835_b.put(var9, Integer.valueOf(var8));
/* 325:    */             }
/* 326:    */           }
/* 327:    */         }
/* 328:    */       }
/* 329:    */     }
/* 330:445 */     Iterator var10 = field_77835_b.values().iterator();
/* 331:447 */     while (var10.hasNext())
/* 332:    */     {
/* 333:449 */       int var5 = ((Integer)var10.next()).intValue();
/* 334:450 */       p_150895_3_.add(new ItemStack(p_150895_1_, 1, var5));
/* 335:    */     }
/* 336:    */   }
/* 337:    */   
/* 338:    */   public void registerIcons(IIconRegister par1IconRegister)
/* 339:    */   {
/* 340:456 */     this.field_94590_d = par1IconRegister.registerIcon(getIconString() + "_" + "bottle_drinkable");
/* 341:457 */     this.field_94591_c = par1IconRegister.registerIcon(getIconString() + "_" + "bottle_splash");
/* 342:458 */     this.field_94592_ct = par1IconRegister.registerIcon(getIconString() + "_" + "overlay");
/* 343:    */   }
/* 344:    */   
/* 345:    */   public static IIcon func_94589_d(String par0Str)
/* 346:    */   {
/* 347:463 */     return par0Str.equals("overlay") ? Items.potionitem.field_94592_ct : par0Str.equals("bottle_splash") ? Items.potionitem.field_94591_c : par0Str.equals("bottle_drinkable") ? Items.potionitem.field_94590_d : null;
/* 348:    */   }
/* 349:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.item.ItemPotion
 * JD-Core Version:    0.7.0.1
 */