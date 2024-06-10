/*   1:    */ package net.minecraft.stats;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.Collection;
/*   5:    */ import java.util.HashMap;
/*   6:    */ import java.util.HashSet;
/*   7:    */ import java.util.Iterator;
/*   8:    */ import java.util.List;
/*   9:    */ import java.util.Map;
/*  10:    */ import net.minecraft.block.Block;
/*  11:    */ import net.minecraft.entity.EntityList;
/*  12:    */ import net.minecraft.entity.EntityList.EntityEggInfo;
/*  13:    */ import net.minecraft.init.Blocks;
/*  14:    */ import net.minecraft.item.Item;
/*  15:    */ import net.minecraft.item.ItemBlock;
/*  16:    */ import net.minecraft.item.ItemStack;
/*  17:    */ import net.minecraft.item.crafting.CraftingManager;
/*  18:    */ import net.minecraft.item.crafting.FurnaceRecipes;
/*  19:    */ import net.minecraft.item.crafting.IRecipe;
/*  20:    */ import net.minecraft.util.ChatComponentTranslation;
/*  21:    */ import net.minecraft.util.RegistryNamespaced;
/*  22:    */ 
/*  23:    */ public class StatList
/*  24:    */ {
/*  25: 23 */   protected static Map oneShotStats = new HashMap();
/*  26: 24 */   public static List allStats = new ArrayList();
/*  27: 25 */   public static List generalStats = new ArrayList();
/*  28: 26 */   public static List itemStats = new ArrayList();
/*  29: 29 */   public static List objectMineStats = new ArrayList();
/*  30: 32 */   public static StatBase leaveGameStat = new StatBasic("stat.leaveGame", new ChatComponentTranslation("stat.leaveGame", new Object[0])).initIndependentStat().registerStat();
/*  31: 35 */   public static StatBase minutesPlayedStat = new StatBasic("stat.playOneMinute", new ChatComponentTranslation("stat.playOneMinute", new Object[0]), StatBase.timeStatType).initIndependentStat().registerStat();
/*  32: 38 */   public static StatBase distanceWalkedStat = new StatBasic("stat.walkOneCm", new ChatComponentTranslation("stat.walkOneCm", new Object[0]), StatBase.distanceStatType).initIndependentStat().registerStat();
/*  33: 41 */   public static StatBase distanceSwumStat = new StatBasic("stat.swimOneCm", new ChatComponentTranslation("stat.swimOneCm", new Object[0]), StatBase.distanceStatType).initIndependentStat().registerStat();
/*  34: 44 */   public static StatBase distanceFallenStat = new StatBasic("stat.fallOneCm", new ChatComponentTranslation("stat.fallOneCm", new Object[0]), StatBase.distanceStatType).initIndependentStat().registerStat();
/*  35: 47 */   public static StatBase distanceClimbedStat = new StatBasic("stat.climbOneCm", new ChatComponentTranslation("stat.climbOneCm", new Object[0]), StatBase.distanceStatType).initIndependentStat().registerStat();
/*  36: 50 */   public static StatBase distanceFlownStat = new StatBasic("stat.flyOneCm", new ChatComponentTranslation("stat.flyOneCm", new Object[0]), StatBase.distanceStatType).initIndependentStat().registerStat();
/*  37: 53 */   public static StatBase distanceDoveStat = new StatBasic("stat.diveOneCm", new ChatComponentTranslation("stat.diveOneCm", new Object[0]), StatBase.distanceStatType).initIndependentStat().registerStat();
/*  38: 56 */   public static StatBase distanceByMinecartStat = new StatBasic("stat.minecartOneCm", new ChatComponentTranslation("stat.minecartOneCm", new Object[0]), StatBase.distanceStatType).initIndependentStat().registerStat();
/*  39: 59 */   public static StatBase distanceByBoatStat = new StatBasic("stat.boatOneCm", new ChatComponentTranslation("stat.boatOneCm", new Object[0]), StatBase.distanceStatType).initIndependentStat().registerStat();
/*  40: 62 */   public static StatBase distanceByPigStat = new StatBasic("stat.pigOneCm", new ChatComponentTranslation("stat.pigOneCm", new Object[0]), StatBase.distanceStatType).initIndependentStat().registerStat();
/*  41: 63 */   public static StatBase field_151185_q = new StatBasic("stat.horseOneCm", new ChatComponentTranslation("stat.horseOneCm", new Object[0]), StatBase.distanceStatType).initIndependentStat().registerStat();
/*  42: 66 */   public static StatBase jumpStat = new StatBasic("stat.jump", new ChatComponentTranslation("stat.jump", new Object[0])).initIndependentStat().registerStat();
/*  43: 69 */   public static StatBase dropStat = new StatBasic("stat.drop", new ChatComponentTranslation("stat.drop", new Object[0])).initIndependentStat().registerStat();
/*  44: 72 */   public static StatBase damageDealtStat = new StatBasic("stat.damageDealt", new ChatComponentTranslation("stat.damageDealt", new Object[0]), StatBase.field_111202_k).registerStat();
/*  45: 75 */   public static StatBase damageTakenStat = new StatBasic("stat.damageTaken", new ChatComponentTranslation("stat.damageTaken", new Object[0]), StatBase.field_111202_k).registerStat();
/*  46: 78 */   public static StatBase deathsStat = new StatBasic("stat.deaths", new ChatComponentTranslation("stat.deaths", new Object[0])).registerStat();
/*  47: 81 */   public static StatBase mobKillsStat = new StatBasic("stat.mobKills", new ChatComponentTranslation("stat.mobKills", new Object[0])).registerStat();
/*  48: 82 */   public static StatBase field_151186_x = new StatBasic("stat.animalsBred", new ChatComponentTranslation("stat.animalsBred", new Object[0])).registerStat();
/*  49: 85 */   public static StatBase playerKillsStat = new StatBasic("stat.playerKills", new ChatComponentTranslation("stat.playerKills", new Object[0])).registerStat();
/*  50: 86 */   public static StatBase fishCaughtStat = new StatBasic("stat.fishCaught", new ChatComponentTranslation("stat.fishCaught", new Object[0])).registerStat();
/*  51: 87 */   public static StatBase field_151183_A = new StatBasic("stat.junkFished", new ChatComponentTranslation("stat.junkFished", new Object[0])).registerStat();
/*  52: 88 */   public static StatBase field_151184_B = new StatBasic("stat.treasureFished", new ChatComponentTranslation("stat.treasureFished", new Object[0])).registerStat();
/*  53: 89 */   public static final StatBase[] mineBlockStatArray = new StatBase[4096];
/*  54: 92 */   public static final StatBase[] objectCraftStats = new StatBase[32000];
/*  55: 95 */   public static final StatBase[] objectUseStats = new StatBase[32000];
/*  56: 98 */   public static final StatBase[] objectBreakStats = new StatBase[32000];
/*  57:    */   private static final String __OBFID = "CL_00001480";
/*  58:    */   
/*  59:    */   public static void func_151178_a()
/*  60:    */   {
/*  61:103 */     func_151181_c();
/*  62:104 */     initStats();
/*  63:105 */     func_151179_e();
/*  64:106 */     initCraftableStats();
/*  65:107 */     AchievementList.init();
/*  66:108 */     EntityList.func_151514_a();
/*  67:    */   }
/*  68:    */   
/*  69:    */   private static void initCraftableStats()
/*  70:    */   {
/*  71:117 */     HashSet var0 = new HashSet();
/*  72:118 */     Iterator var1 = CraftingManager.getInstance().getRecipeList().iterator();
/*  73:120 */     while (var1.hasNext())
/*  74:    */     {
/*  75:122 */       IRecipe var2 = (IRecipe)var1.next();
/*  76:124 */       if (var2.getRecipeOutput() != null) {
/*  77:126 */         var0.add(var2.getRecipeOutput().getItem());
/*  78:    */       }
/*  79:    */     }
/*  80:130 */     var1 = FurnaceRecipes.smelting().getSmeltingList().values().iterator();
/*  81:132 */     while (var1.hasNext())
/*  82:    */     {
/*  83:134 */       ItemStack var4 = (ItemStack)var1.next();
/*  84:135 */       var0.add(var4.getItem());
/*  85:    */     }
/*  86:138 */     var1 = var0.iterator();
/*  87:140 */     while (var1.hasNext())
/*  88:    */     {
/*  89:142 */       Item var5 = (Item)var1.next();
/*  90:144 */       if (var5 != null)
/*  91:    */       {
/*  92:146 */         int var3 = Item.getIdFromItem(var5);
/*  93:147 */         objectCraftStats[var3] = new StatCrafting("stat.craftItem." + var3, new ChatComponentTranslation("stat.craftItem", new Object[] { new ItemStack(var5).func_151000_E() }), var5).registerStat();
/*  94:    */       }
/*  95:    */     }
/*  96:151 */     replaceAllSimilarBlocks(objectCraftStats);
/*  97:    */   }
/*  98:    */   
/*  99:    */   private static void func_151181_c()
/* 100:    */   {
/* 101:156 */     Iterator var0 = Block.blockRegistry.iterator();
/* 102:158 */     while (var0.hasNext())
/* 103:    */     {
/* 104:160 */       Block var1 = (Block)var0.next();
/* 105:162 */       if (Item.getItemFromBlock(var1) != null)
/* 106:    */       {
/* 107:164 */         int var2 = Block.getIdFromBlock(var1);
/* 108:166 */         if (var1.getEnableStats())
/* 109:    */         {
/* 110:168 */           mineBlockStatArray[var2] = new StatCrafting("stat.mineBlock." + var2, new ChatComponentTranslation("stat.mineBlock", new Object[] { new ItemStack(var1).func_151000_E() }), Item.getItemFromBlock(var1)).registerStat();
/* 111:169 */           objectMineStats.add((StatCrafting)mineBlockStatArray[var2]);
/* 112:    */         }
/* 113:    */       }
/* 114:    */     }
/* 115:174 */     replaceAllSimilarBlocks(mineBlockStatArray);
/* 116:    */   }
/* 117:    */   
/* 118:    */   private static void initStats()
/* 119:    */   {
/* 120:179 */     Iterator var0 = Item.itemRegistry.iterator();
/* 121:181 */     while (var0.hasNext())
/* 122:    */     {
/* 123:183 */       Item var1 = (Item)var0.next();
/* 124:185 */       if (var1 != null)
/* 125:    */       {
/* 126:187 */         int var2 = Item.getIdFromItem(var1);
/* 127:188 */         objectUseStats[var2] = new StatCrafting("stat.useItem." + var2, new ChatComponentTranslation("stat.useItem", new Object[] { new ItemStack(var1).func_151000_E() }), var1).registerStat();
/* 128:190 */         if (!(var1 instanceof ItemBlock)) {
/* 129:192 */           itemStats.add((StatCrafting)objectUseStats[var2]);
/* 130:    */         }
/* 131:    */       }
/* 132:    */     }
/* 133:197 */     replaceAllSimilarBlocks(objectUseStats);
/* 134:    */   }
/* 135:    */   
/* 136:    */   private static void func_151179_e()
/* 137:    */   {
/* 138:202 */     Iterator var0 = Item.itemRegistry.iterator();
/* 139:204 */     while (var0.hasNext())
/* 140:    */     {
/* 141:206 */       Item var1 = (Item)var0.next();
/* 142:208 */       if (var1 != null)
/* 143:    */       {
/* 144:210 */         int var2 = Item.getIdFromItem(var1);
/* 145:212 */         if (var1.isDamageable()) {
/* 146:214 */           objectBreakStats[var2] = new StatCrafting("stat.breakItem." + var2, new ChatComponentTranslation("stat.breakItem", new Object[] { new ItemStack(var1).func_151000_E() }), var1).registerStat();
/* 147:    */         }
/* 148:    */       }
/* 149:    */     }
/* 150:219 */     replaceAllSimilarBlocks(objectBreakStats);
/* 151:    */   }
/* 152:    */   
/* 153:    */   private static void replaceAllSimilarBlocks(StatBase[] par0ArrayOfStatBase)
/* 154:    */   {
/* 155:227 */     func_151180_a(par0ArrayOfStatBase, Blocks.water, Blocks.flowing_water);
/* 156:228 */     func_151180_a(par0ArrayOfStatBase, Blocks.lava, Blocks.flowing_lava);
/* 157:229 */     func_151180_a(par0ArrayOfStatBase, Blocks.lit_pumpkin, Blocks.pumpkin);
/* 158:230 */     func_151180_a(par0ArrayOfStatBase, Blocks.lit_furnace, Blocks.furnace);
/* 159:231 */     func_151180_a(par0ArrayOfStatBase, Blocks.lit_redstone_ore, Blocks.redstone_ore);
/* 160:232 */     func_151180_a(par0ArrayOfStatBase, Blocks.powered_repeater, Blocks.unpowered_repeater);
/* 161:233 */     func_151180_a(par0ArrayOfStatBase, Blocks.powered_comparator, Blocks.unpowered_comparator);
/* 162:234 */     func_151180_a(par0ArrayOfStatBase, Blocks.redstone_torch, Blocks.unlit_redstone_torch);
/* 163:235 */     func_151180_a(par0ArrayOfStatBase, Blocks.lit_redstone_lamp, Blocks.redstone_lamp);
/* 164:236 */     func_151180_a(par0ArrayOfStatBase, Blocks.red_mushroom, Blocks.brown_mushroom);
/* 165:237 */     func_151180_a(par0ArrayOfStatBase, Blocks.double_stone_slab, Blocks.stone_slab);
/* 166:238 */     func_151180_a(par0ArrayOfStatBase, Blocks.double_wooden_slab, Blocks.wooden_slab);
/* 167:239 */     func_151180_a(par0ArrayOfStatBase, Blocks.grass, Blocks.dirt);
/* 168:240 */     func_151180_a(par0ArrayOfStatBase, Blocks.farmland, Blocks.dirt);
/* 169:    */   }
/* 170:    */   
/* 171:    */   private static void func_151180_a(StatBase[] p_151180_0_, Block p_151180_1_, Block p_151180_2_)
/* 172:    */   {
/* 173:245 */     int var3 = Block.getIdFromBlock(p_151180_1_);
/* 174:246 */     int var4 = Block.getIdFromBlock(p_151180_2_);
/* 175:248 */     if ((p_151180_0_[var3] != null) && (p_151180_0_[var4] == null))
/* 176:    */     {
/* 177:250 */       p_151180_0_[var4] = p_151180_0_[var3];
/* 178:    */     }
/* 179:    */     else
/* 180:    */     {
/* 181:254 */       allStats.remove(p_151180_0_[var3]);
/* 182:255 */       objectMineStats.remove(p_151180_0_[var3]);
/* 183:256 */       generalStats.remove(p_151180_0_[var3]);
/* 184:257 */       p_151180_0_[var3] = p_151180_0_[var4];
/* 185:    */     }
/* 186:    */   }
/* 187:    */   
/* 188:    */   public static StatBase func_151182_a(EntityList.EntityEggInfo p_151182_0_)
/* 189:    */   {
/* 190:263 */     String var1 = EntityList.getStringFromID(p_151182_0_.spawnedID);
/* 191:264 */     return var1 == null ? null : new StatBase("stat.killEntity." + var1, new ChatComponentTranslation("stat.entityKill", new Object[] { new ChatComponentTranslation("entity." + var1 + ".name", new Object[0]) })).registerStat();
/* 192:    */   }
/* 193:    */   
/* 194:    */   public static StatBase func_151176_b(EntityList.EntityEggInfo p_151176_0_)
/* 195:    */   {
/* 196:269 */     String var1 = EntityList.getStringFromID(p_151176_0_.spawnedID);
/* 197:270 */     return var1 == null ? null : new StatBase("stat.entityKilledBy." + var1, new ChatComponentTranslation("stat.entityKilledBy", new Object[] { new ChatComponentTranslation("entity." + var1 + ".name", new Object[0]) })).registerStat();
/* 198:    */   }
/* 199:    */   
/* 200:    */   public static StatBase func_151177_a(String p_151177_0_)
/* 201:    */   {
/* 202:275 */     return (StatBase)oneShotStats.get(p_151177_0_);
/* 203:    */   }
/* 204:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.stats.StatList
 * JD-Core Version:    0.7.0.1
 */