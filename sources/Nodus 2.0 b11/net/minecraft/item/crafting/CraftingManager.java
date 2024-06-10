/*   1:    */ package net.minecraft.item.crafting;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.Collections;
/*   5:    */ import java.util.Comparator;
/*   6:    */ import java.util.HashMap;
/*   7:    */ import java.util.List;
/*   8:    */ import net.minecraft.block.Block;
/*   9:    */ import net.minecraft.init.Blocks;
/*  10:    */ import net.minecraft.init.Items;
/*  11:    */ import net.minecraft.inventory.InventoryCrafting;
/*  12:    */ import net.minecraft.item.Item;
/*  13:    */ import net.minecraft.item.ItemStack;
/*  14:    */ import net.minecraft.world.World;
/*  15:    */ 
/*  16:    */ public class CraftingManager
/*  17:    */ {
/*  18: 19 */   private static final CraftingManager instance = new CraftingManager();
/*  19: 22 */   private List recipes = new ArrayList();
/*  20:    */   private static final String __OBFID = "CL_00000090";
/*  21:    */   
/*  22:    */   public static final CraftingManager getInstance()
/*  23:    */   {
/*  24: 30 */     return instance;
/*  25:    */   }
/*  26:    */   
/*  27:    */   private CraftingManager()
/*  28:    */   {
/*  29: 35 */     new RecipesTools().addRecipes(this);
/*  30: 36 */     new RecipesWeapons().addRecipes(this);
/*  31: 37 */     new RecipesIngots().addRecipes(this);
/*  32: 38 */     new RecipesFood().addRecipes(this);
/*  33: 39 */     new RecipesCrafting().addRecipes(this);
/*  34: 40 */     new RecipesArmor().addRecipes(this);
/*  35: 41 */     new RecipesDyes().addRecipes(this);
/*  36: 42 */     this.recipes.add(new RecipesArmorDyes());
/*  37: 43 */     this.recipes.add(new RecipeBookCloning());
/*  38: 44 */     this.recipes.add(new RecipesMapCloning());
/*  39: 45 */     this.recipes.add(new RecipesMapExtending());
/*  40: 46 */     this.recipes.add(new RecipeFireworks());
/*  41: 47 */     addRecipe(new ItemStack(Items.paper, 3), new Object[] { "###", Character.valueOf('#'), Items.reeds });
/*  42: 48 */     addShapelessRecipe(new ItemStack(Items.book, 1), new Object[] { Items.paper, Items.paper, Items.paper, Items.leather });
/*  43: 49 */     addShapelessRecipe(new ItemStack(Items.writable_book, 1), new Object[] { Items.book, new ItemStack(Items.dye, 1, 0), Items.feather });
/*  44: 50 */     addRecipe(new ItemStack(Blocks.fence, 2), new Object[] { "###", "###", Character.valueOf('#'), Items.stick });
/*  45: 51 */     addRecipe(new ItemStack(Blocks.cobblestone_wall, 6, 0), new Object[] { "###", "###", Character.valueOf('#'), Blocks.cobblestone });
/*  46: 52 */     addRecipe(new ItemStack(Blocks.cobblestone_wall, 6, 1), new Object[] { "###", "###", Character.valueOf('#'), Blocks.mossy_cobblestone });
/*  47: 53 */     addRecipe(new ItemStack(Blocks.nether_brick_fence, 6), new Object[] { "###", "###", Character.valueOf('#'), Blocks.nether_brick });
/*  48: 54 */     addRecipe(new ItemStack(Blocks.fence_gate, 1), new Object[] { "#W#", "#W#", Character.valueOf('#'), Items.stick, Character.valueOf('W'), Blocks.planks });
/*  49: 55 */     addRecipe(new ItemStack(Blocks.jukebox, 1), new Object[] { "###", "#X#", "###", Character.valueOf('#'), Blocks.planks, Character.valueOf('X'), Items.diamond });
/*  50: 56 */     addRecipe(new ItemStack(Items.lead, 2), new Object[] { "~~ ", "~O ", "  ~", Character.valueOf('~'), Items.string, Character.valueOf('O'), Items.slime_ball });
/*  51: 57 */     addRecipe(new ItemStack(Blocks.noteblock, 1), new Object[] { "###", "#X#", "###", Character.valueOf('#'), Blocks.planks, Character.valueOf('X'), Items.redstone });
/*  52: 58 */     addRecipe(new ItemStack(Blocks.bookshelf, 1), new Object[] { "###", "XXX", "###", Character.valueOf('#'), Blocks.planks, Character.valueOf('X'), Items.book });
/*  53: 59 */     addRecipe(new ItemStack(Blocks.snow, 1), new Object[] { "##", "##", Character.valueOf('#'), Items.snowball });
/*  54: 60 */     addRecipe(new ItemStack(Blocks.snow_layer, 6), new Object[] { "###", Character.valueOf('#'), Blocks.snow });
/*  55: 61 */     addRecipe(new ItemStack(Blocks.clay, 1), new Object[] { "##", "##", Character.valueOf('#'), Items.clay_ball });
/*  56: 62 */     addRecipe(new ItemStack(Blocks.brick_block, 1), new Object[] { "##", "##", Character.valueOf('#'), Items.brick });
/*  57: 63 */     addRecipe(new ItemStack(Blocks.glowstone, 1), new Object[] { "##", "##", Character.valueOf('#'), Items.glowstone_dust });
/*  58: 64 */     addRecipe(new ItemStack(Blocks.quartz_block, 1), new Object[] { "##", "##", Character.valueOf('#'), Items.quartz });
/*  59: 65 */     addRecipe(new ItemStack(Blocks.wool, 1), new Object[] { "##", "##", Character.valueOf('#'), Items.string });
/*  60: 66 */     addRecipe(new ItemStack(Blocks.tnt, 1), new Object[] { "X#X", "#X#", "X#X", Character.valueOf('X'), Items.gunpowder, Character.valueOf('#'), Blocks.sand });
/*  61: 67 */     addRecipe(new ItemStack(Blocks.stone_slab, 6, 3), new Object[] { "###", Character.valueOf('#'), Blocks.cobblestone });
/*  62: 68 */     addRecipe(new ItemStack(Blocks.stone_slab, 6, 0), new Object[] { "###", Character.valueOf('#'), Blocks.stone });
/*  63: 69 */     addRecipe(new ItemStack(Blocks.stone_slab, 6, 1), new Object[] { "###", Character.valueOf('#'), Blocks.sandstone });
/*  64: 70 */     addRecipe(new ItemStack(Blocks.stone_slab, 6, 4), new Object[] { "###", Character.valueOf('#'), Blocks.brick_block });
/*  65: 71 */     addRecipe(new ItemStack(Blocks.stone_slab, 6, 5), new Object[] { "###", Character.valueOf('#'), Blocks.stonebrick });
/*  66: 72 */     addRecipe(new ItemStack(Blocks.stone_slab, 6, 6), new Object[] { "###", Character.valueOf('#'), Blocks.nether_brick });
/*  67: 73 */     addRecipe(new ItemStack(Blocks.stone_slab, 6, 7), new Object[] { "###", Character.valueOf('#'), Blocks.quartz_block });
/*  68: 74 */     addRecipe(new ItemStack(Blocks.wooden_slab, 6, 0), new Object[] { "###", Character.valueOf('#'), new ItemStack(Blocks.planks, 1, 0) });
/*  69: 75 */     addRecipe(new ItemStack(Blocks.wooden_slab, 6, 2), new Object[] { "###", Character.valueOf('#'), new ItemStack(Blocks.planks, 1, 2) });
/*  70: 76 */     addRecipe(new ItemStack(Blocks.wooden_slab, 6, 1), new Object[] { "###", Character.valueOf('#'), new ItemStack(Blocks.planks, 1, 1) });
/*  71: 77 */     addRecipe(new ItemStack(Blocks.wooden_slab, 6, 3), new Object[] { "###", Character.valueOf('#'), new ItemStack(Blocks.planks, 1, 3) });
/*  72: 78 */     addRecipe(new ItemStack(Blocks.wooden_slab, 6, 4), new Object[] { "###", Character.valueOf('#'), new ItemStack(Blocks.planks, 1, 4) });
/*  73: 79 */     addRecipe(new ItemStack(Blocks.wooden_slab, 6, 5), new Object[] { "###", Character.valueOf('#'), new ItemStack(Blocks.planks, 1, 5) });
/*  74: 80 */     addRecipe(new ItemStack(Blocks.ladder, 3), new Object[] { "# #", "###", "# #", Character.valueOf('#'), Items.stick });
/*  75: 81 */     addRecipe(new ItemStack(Items.wooden_door, 1), new Object[] { "##", "##", "##", Character.valueOf('#'), Blocks.planks });
/*  76: 82 */     addRecipe(new ItemStack(Blocks.trapdoor, 2), new Object[] { "###", "###", Character.valueOf('#'), Blocks.planks });
/*  77: 83 */     addRecipe(new ItemStack(Items.iron_door, 1), new Object[] { "##", "##", "##", Character.valueOf('#'), Items.iron_ingot });
/*  78: 84 */     addRecipe(new ItemStack(Items.sign, 3), new Object[] { "###", "###", " X ", Character.valueOf('#'), Blocks.planks, Character.valueOf('X'), Items.stick });
/*  79: 85 */     addRecipe(new ItemStack(Items.cake, 1), new Object[] { "AAA", "BEB", "CCC", Character.valueOf('A'), Items.milk_bucket, Character.valueOf('B'), Items.sugar, Character.valueOf('C'), Items.wheat, Character.valueOf('E'), Items.egg });
/*  80: 86 */     addRecipe(new ItemStack(Items.sugar, 1), new Object[] { "#", Character.valueOf('#'), Items.reeds });
/*  81: 87 */     addRecipe(new ItemStack(Blocks.planks, 4, 0), new Object[] { "#", Character.valueOf('#'), new ItemStack(Blocks.log, 1, 0) });
/*  82: 88 */     addRecipe(new ItemStack(Blocks.planks, 4, 1), new Object[] { "#", Character.valueOf('#'), new ItemStack(Blocks.log, 1, 1) });
/*  83: 89 */     addRecipe(new ItemStack(Blocks.planks, 4, 2), new Object[] { "#", Character.valueOf('#'), new ItemStack(Blocks.log, 1, 2) });
/*  84: 90 */     addRecipe(new ItemStack(Blocks.planks, 4, 3), new Object[] { "#", Character.valueOf('#'), new ItemStack(Blocks.log, 1, 3) });
/*  85: 91 */     addRecipe(new ItemStack(Blocks.planks, 4, 4), new Object[] { "#", Character.valueOf('#'), new ItemStack(Blocks.log2, 1, 0) });
/*  86: 92 */     addRecipe(new ItemStack(Blocks.planks, 4, 5), new Object[] { "#", Character.valueOf('#'), new ItemStack(Blocks.log2, 1, 1) });
/*  87: 93 */     addRecipe(new ItemStack(Items.stick, 4), new Object[] { "#", "#", Character.valueOf('#'), Blocks.planks });
/*  88: 94 */     addRecipe(new ItemStack(Blocks.torch, 4), new Object[] { "X", "#", Character.valueOf('X'), Items.coal, Character.valueOf('#'), Items.stick });
/*  89: 95 */     addRecipe(new ItemStack(Blocks.torch, 4), new Object[] { "X", "#", Character.valueOf('X'), new ItemStack(Items.coal, 1, 1), Character.valueOf('#'), Items.stick });
/*  90: 96 */     addRecipe(new ItemStack(Items.bowl, 4), new Object[] { "# #", " # ", Character.valueOf('#'), Blocks.planks });
/*  91: 97 */     addRecipe(new ItemStack(Items.glass_bottle, 3), new Object[] { "# #", " # ", Character.valueOf('#'), Blocks.glass });
/*  92: 98 */     addRecipe(new ItemStack(Blocks.rail, 16), new Object[] { "X X", "X#X", "X X", Character.valueOf('X'), Items.iron_ingot, Character.valueOf('#'), Items.stick });
/*  93: 99 */     addRecipe(new ItemStack(Blocks.golden_rail, 6), new Object[] { "X X", "X#X", "XRX", Character.valueOf('X'), Items.gold_ingot, Character.valueOf('R'), Items.redstone, Character.valueOf('#'), Items.stick });
/*  94:100 */     addRecipe(new ItemStack(Blocks.activator_rail, 6), new Object[] { "XSX", "X#X", "XSX", Character.valueOf('X'), Items.iron_ingot, Character.valueOf('#'), Blocks.redstone_torch, Character.valueOf('S'), Items.stick });
/*  95:101 */     addRecipe(new ItemStack(Blocks.detector_rail, 6), new Object[] { "X X", "X#X", "XRX", Character.valueOf('X'), Items.iron_ingot, Character.valueOf('R'), Items.redstone, Character.valueOf('#'), Blocks.stone_pressure_plate });
/*  96:102 */     addRecipe(new ItemStack(Items.minecart, 1), new Object[] { "# #", "###", Character.valueOf('#'), Items.iron_ingot });
/*  97:103 */     addRecipe(new ItemStack(Items.cauldron, 1), new Object[] { "# #", "# #", "###", Character.valueOf('#'), Items.iron_ingot });
/*  98:104 */     addRecipe(new ItemStack(Items.brewing_stand, 1), new Object[] { " B ", "###", Character.valueOf('#'), Blocks.cobblestone, Character.valueOf('B'), Items.blaze_rod });
/*  99:105 */     addRecipe(new ItemStack(Blocks.lit_pumpkin, 1), new Object[] { "A", "B", Character.valueOf('A'), Blocks.pumpkin, Character.valueOf('B'), Blocks.torch });
/* 100:106 */     addRecipe(new ItemStack(Items.chest_minecart, 1), new Object[] { "A", "B", Character.valueOf('A'), Blocks.chest, Character.valueOf('B'), Items.minecart });
/* 101:107 */     addRecipe(new ItemStack(Items.furnace_minecart, 1), new Object[] { "A", "B", Character.valueOf('A'), Blocks.furnace, Character.valueOf('B'), Items.minecart });
/* 102:108 */     addRecipe(new ItemStack(Items.tnt_minecart, 1), new Object[] { "A", "B", Character.valueOf('A'), Blocks.tnt, Character.valueOf('B'), Items.minecart });
/* 103:109 */     addRecipe(new ItemStack(Items.hopper_minecart, 1), new Object[] { "A", "B", Character.valueOf('A'), Blocks.hopper, Character.valueOf('B'), Items.minecart });
/* 104:110 */     addRecipe(new ItemStack(Items.boat, 1), new Object[] { "# #", "###", Character.valueOf('#'), Blocks.planks });
/* 105:111 */     addRecipe(new ItemStack(Items.bucket, 1), new Object[] { "# #", " # ", Character.valueOf('#'), Items.iron_ingot });
/* 106:112 */     addRecipe(new ItemStack(Items.flower_pot, 1), new Object[] { "# #", " # ", Character.valueOf('#'), Items.brick });
/* 107:113 */     addShapelessRecipe(new ItemStack(Items.flint_and_steel, 1), new Object[] { new ItemStack(Items.iron_ingot, 1), new ItemStack(Items.flint, 1) });
/* 108:114 */     addRecipe(new ItemStack(Items.bread, 1), new Object[] { "###", Character.valueOf('#'), Items.wheat });
/* 109:115 */     addRecipe(new ItemStack(Blocks.oak_stairs, 4), new Object[] { "#  ", "## ", "###", Character.valueOf('#'), new ItemStack(Blocks.planks, 1, 0) });
/* 110:116 */     addRecipe(new ItemStack(Blocks.birch_stairs, 4), new Object[] { "#  ", "## ", "###", Character.valueOf('#'), new ItemStack(Blocks.planks, 1, 2) });
/* 111:117 */     addRecipe(new ItemStack(Blocks.spruce_stairs, 4), new Object[] { "#  ", "## ", "###", Character.valueOf('#'), new ItemStack(Blocks.planks, 1, 1) });
/* 112:118 */     addRecipe(new ItemStack(Blocks.jungle_stairs, 4), new Object[] { "#  ", "## ", "###", Character.valueOf('#'), new ItemStack(Blocks.planks, 1, 3) });
/* 113:119 */     addRecipe(new ItemStack(Blocks.acacia_stairs, 4), new Object[] { "#  ", "## ", "###", Character.valueOf('#'), new ItemStack(Blocks.planks, 1, 4) });
/* 114:120 */     addRecipe(new ItemStack(Blocks.dark_oak_stairs, 4), new Object[] { "#  ", "## ", "###", Character.valueOf('#'), new ItemStack(Blocks.planks, 1, 5) });
/* 115:121 */     addRecipe(new ItemStack(Items.fishing_rod, 1), new Object[] { "  #", " #X", "# X", Character.valueOf('#'), Items.stick, Character.valueOf('X'), Items.string });
/* 116:122 */     addRecipe(new ItemStack(Items.carrot_on_a_stick, 1), new Object[] { "# ", " X", Character.valueOf('#'), Items.fishing_rod, Character.valueOf('X'), Items.carrot }).func_92100_c();
/* 117:123 */     addRecipe(new ItemStack(Blocks.stone_stairs, 4), new Object[] { "#  ", "## ", "###", Character.valueOf('#'), Blocks.cobblestone });
/* 118:124 */     addRecipe(new ItemStack(Blocks.brick_stairs, 4), new Object[] { "#  ", "## ", "###", Character.valueOf('#'), Blocks.brick_block });
/* 119:125 */     addRecipe(new ItemStack(Blocks.stone_brick_stairs, 4), new Object[] { "#  ", "## ", "###", Character.valueOf('#'), Blocks.stonebrick });
/* 120:126 */     addRecipe(new ItemStack(Blocks.nether_brick_stairs, 4), new Object[] { "#  ", "## ", "###", Character.valueOf('#'), Blocks.nether_brick });
/* 121:127 */     addRecipe(new ItemStack(Blocks.sandstone_stairs, 4), new Object[] { "#  ", "## ", "###", Character.valueOf('#'), Blocks.sandstone });
/* 122:128 */     addRecipe(new ItemStack(Blocks.quartz_stairs, 4), new Object[] { "#  ", "## ", "###", Character.valueOf('#'), Blocks.quartz_block });
/* 123:129 */     addRecipe(new ItemStack(Items.painting, 1), new Object[] { "###", "#X#", "###", Character.valueOf('#'), Items.stick, Character.valueOf('X'), Blocks.wool });
/* 124:130 */     addRecipe(new ItemStack(Items.item_frame, 1), new Object[] { "###", "#X#", "###", Character.valueOf('#'), Items.stick, Character.valueOf('X'), Items.leather });
/* 125:131 */     addRecipe(new ItemStack(Items.golden_apple, 1, 0), new Object[] { "###", "#X#", "###", Character.valueOf('#'), Items.gold_ingot, Character.valueOf('X'), Items.apple });
/* 126:132 */     addRecipe(new ItemStack(Items.golden_apple, 1, 1), new Object[] { "###", "#X#", "###", Character.valueOf('#'), Blocks.gold_block, Character.valueOf('X'), Items.apple });
/* 127:133 */     addRecipe(new ItemStack(Items.golden_carrot, 1, 0), new Object[] { "###", "#X#", "###", Character.valueOf('#'), Items.gold_nugget, Character.valueOf('X'), Items.carrot });
/* 128:134 */     addRecipe(new ItemStack(Items.speckled_melon, 1), new Object[] { "###", "#X#", "###", Character.valueOf('#'), Items.gold_nugget, Character.valueOf('X'), Items.melon });
/* 129:135 */     addRecipe(new ItemStack(Blocks.lever, 1), new Object[] { "X", "#", Character.valueOf('#'), Blocks.cobblestone, Character.valueOf('X'), Items.stick });
/* 130:136 */     addRecipe(new ItemStack(Blocks.tripwire_hook, 2), new Object[] { "I", "S", "#", Character.valueOf('#'), Blocks.planks, Character.valueOf('S'), Items.stick, Character.valueOf('I'), Items.iron_ingot });
/* 131:137 */     addRecipe(new ItemStack(Blocks.redstone_torch, 1), new Object[] { "X", "#", Character.valueOf('#'), Items.stick, Character.valueOf('X'), Items.redstone });
/* 132:138 */     addRecipe(new ItemStack(Items.repeater, 1), new Object[] { "#X#", "III", Character.valueOf('#'), Blocks.redstone_torch, Character.valueOf('X'), Items.redstone, Character.valueOf('I'), Blocks.stone });
/* 133:139 */     addRecipe(new ItemStack(Items.comparator, 1), new Object[] { " # ", "#X#", "III", Character.valueOf('#'), Blocks.redstone_torch, Character.valueOf('X'), Items.quartz, Character.valueOf('I'), Blocks.stone });
/* 134:140 */     addRecipe(new ItemStack(Items.clock, 1), new Object[] { " # ", "#X#", " # ", Character.valueOf('#'), Items.gold_ingot, Character.valueOf('X'), Items.redstone });
/* 135:141 */     addRecipe(new ItemStack(Items.compass, 1), new Object[] { " # ", "#X#", " # ", Character.valueOf('#'), Items.iron_ingot, Character.valueOf('X'), Items.redstone });
/* 136:142 */     addRecipe(new ItemStack(Items.map, 1), new Object[] { "###", "#X#", "###", Character.valueOf('#'), Items.paper, Character.valueOf('X'), Items.compass });
/* 137:143 */     addRecipe(new ItemStack(Blocks.stone_button, 1), new Object[] { "#", Character.valueOf('#'), Blocks.stone });
/* 138:144 */     addRecipe(new ItemStack(Blocks.wooden_button, 1), new Object[] { "#", Character.valueOf('#'), Blocks.planks });
/* 139:145 */     addRecipe(new ItemStack(Blocks.stone_pressure_plate, 1), new Object[] { "##", Character.valueOf('#'), Blocks.stone });
/* 140:146 */     addRecipe(new ItemStack(Blocks.wooden_pressure_plate, 1), new Object[] { "##", Character.valueOf('#'), Blocks.planks });
/* 141:147 */     addRecipe(new ItemStack(Blocks.heavy_weighted_pressure_plate, 1), new Object[] { "##", Character.valueOf('#'), Items.iron_ingot });
/* 142:148 */     addRecipe(new ItemStack(Blocks.light_weighted_pressure_plate, 1), new Object[] { "##", Character.valueOf('#'), Items.gold_ingot });
/* 143:149 */     addRecipe(new ItemStack(Blocks.dispenser, 1), new Object[] { "###", "#X#", "#R#", Character.valueOf('#'), Blocks.cobblestone, Character.valueOf('X'), Items.bow, Character.valueOf('R'), Items.redstone });
/* 144:150 */     addRecipe(new ItemStack(Blocks.dropper, 1), new Object[] { "###", "# #", "#R#", Character.valueOf('#'), Blocks.cobblestone, Character.valueOf('R'), Items.redstone });
/* 145:151 */     addRecipe(new ItemStack(Blocks.piston, 1), new Object[] { "TTT", "#X#", "#R#", Character.valueOf('#'), Blocks.cobblestone, Character.valueOf('X'), Items.iron_ingot, Character.valueOf('R'), Items.redstone, Character.valueOf('T'), Blocks.planks });
/* 146:152 */     addRecipe(new ItemStack(Blocks.sticky_piston, 1), new Object[] { "S", "P", Character.valueOf('S'), Items.slime_ball, Character.valueOf('P'), Blocks.piston });
/* 147:153 */     addRecipe(new ItemStack(Items.bed, 1), new Object[] { "###", "XXX", Character.valueOf('#'), Blocks.wool, Character.valueOf('X'), Blocks.planks });
/* 148:154 */     addRecipe(new ItemStack(Blocks.enchanting_table, 1), new Object[] { " B ", "D#D", "###", Character.valueOf('#'), Blocks.obsidian, Character.valueOf('B'), Items.book, Character.valueOf('D'), Items.diamond });
/* 149:155 */     addRecipe(new ItemStack(Blocks.anvil, 1), new Object[] { "III", " i ", "iii", Character.valueOf('I'), Blocks.iron_block, Character.valueOf('i'), Items.iron_ingot });
/* 150:156 */     addShapelessRecipe(new ItemStack(Items.ender_eye, 1), new Object[] { Items.ender_pearl, Items.blaze_powder });
/* 151:157 */     addShapelessRecipe(new ItemStack(Items.fire_charge, 3), new Object[] { Items.gunpowder, Items.blaze_powder, Items.coal });
/* 152:158 */     addShapelessRecipe(new ItemStack(Items.fire_charge, 3), new Object[] { Items.gunpowder, Items.blaze_powder, new ItemStack(Items.coal, 1, 1) });
/* 153:159 */     addRecipe(new ItemStack(Blocks.daylight_detector), new Object[] { "GGG", "QQQ", "WWW", Character.valueOf('G'), Blocks.glass, Character.valueOf('Q'), Items.quartz, Character.valueOf('W'), Blocks.wooden_slab });
/* 154:160 */     addRecipe(new ItemStack(Blocks.hopper), new Object[] { "I I", "ICI", " I ", Character.valueOf('I'), Items.iron_ingot, Character.valueOf('C'), Blocks.chest });
/* 155:161 */     Collections.sort(this.recipes, new Comparator()
/* 156:    */     {
/* 157:    */       private static final String __OBFID = "CL_00000091";
/* 158:    */       
/* 159:    */       public int compare(IRecipe par1IRecipe, IRecipe par2IRecipe)
/* 160:    */       {
/* 161:166 */         return par2IRecipe.getRecipeSize() > par1IRecipe.getRecipeSize() ? 1 : par2IRecipe.getRecipeSize() < par1IRecipe.getRecipeSize() ? -1 : ((par2IRecipe instanceof ShapelessRecipes)) && ((par1IRecipe instanceof ShapedRecipes)) ? -1 : ((par1IRecipe instanceof ShapelessRecipes)) && ((par2IRecipe instanceof ShapedRecipes)) ? 1 : 0;
/* 162:    */       }
/* 163:    */       
/* 164:    */       public int compare(Object par1Obj, Object par2Obj)
/* 165:    */       {
/* 166:170 */         return compare((IRecipe)par1Obj, (IRecipe)par2Obj);
/* 167:    */       }
/* 168:    */     });
/* 169:    */   }
/* 170:    */   
/* 171:    */   ShapedRecipes addRecipe(ItemStack par1ItemStack, Object... par2ArrayOfObj)
/* 172:    */   {
/* 173:177 */     String var3 = "";
/* 174:178 */     int var4 = 0;
/* 175:179 */     int var5 = 0;
/* 176:180 */     int var6 = 0;
/* 177:182 */     if ((par2ArrayOfObj[var4] instanceof String[]))
/* 178:    */     {
/* 179:184 */       var7 = (String[])par2ArrayOfObj[(var4++)];
/* 180:186 */       for (var8 = 0; var8 < var7.length; var8++)
/* 181:    */       {
/* 182:188 */         var9 = var7[var8];
/* 183:189 */         var6++;
/* 184:190 */         var5 = var9.length();
/* 185:191 */         var3 = var3 + var9;
/* 186:    */       }
/* 187:    */     }
/* 188:    */     else
/* 189:    */     {
/* 190:196 */       while ((par2ArrayOfObj[var4] instanceof String))
/* 191:    */       {
/* 192:    */         String[] var7;
/* 193:    */         int var8;
/* 194:    */         String var9;
/* 195:198 */         String var11 = (String)par2ArrayOfObj[(var4++)];
/* 196:199 */         var6++;
/* 197:200 */         var5 = var11.length();
/* 198:201 */         var3 = var3 + var11;
/* 199:    */       }
/* 200:    */     }
/* 201:207 */     for (HashMap var12 = new HashMap(); var4 < par2ArrayOfObj.length; var4 += 2)
/* 202:    */     {
/* 203:209 */       Character var13 = (Character)par2ArrayOfObj[var4];
/* 204:210 */       ItemStack var14 = null;
/* 205:212 */       if ((par2ArrayOfObj[(var4 + 1)] instanceof Item)) {
/* 206:214 */         var14 = new ItemStack((Item)par2ArrayOfObj[(var4 + 1)]);
/* 207:216 */       } else if ((par2ArrayOfObj[(var4 + 1)] instanceof Block)) {
/* 208:218 */         var14 = new ItemStack((Block)par2ArrayOfObj[(var4 + 1)], 1, 32767);
/* 209:220 */       } else if ((par2ArrayOfObj[(var4 + 1)] instanceof ItemStack)) {
/* 210:222 */         var14 = (ItemStack)par2ArrayOfObj[(var4 + 1)];
/* 211:    */       }
/* 212:225 */       var12.put(var13, var14);
/* 213:    */     }
/* 214:228 */     ItemStack[] var15 = new ItemStack[var5 * var6];
/* 215:230 */     for (int var16 = 0; var16 < var5 * var6; var16++)
/* 216:    */     {
/* 217:232 */       char var10 = var3.charAt(var16);
/* 218:234 */       if (var12.containsKey(Character.valueOf(var10))) {
/* 219:236 */         var15[var16] = ((ItemStack)var12.get(Character.valueOf(var10))).copy();
/* 220:    */       } else {
/* 221:240 */         var15[var16] = null;
/* 222:    */       }
/* 223:    */     }
/* 224:244 */     ShapedRecipes var17 = new ShapedRecipes(var5, var6, var15, par1ItemStack);
/* 225:245 */     this.recipes.add(var17);
/* 226:246 */     return var17;
/* 227:    */   }
/* 228:    */   
/* 229:    */   void addShapelessRecipe(ItemStack par1ItemStack, Object... par2ArrayOfObj)
/* 230:    */   {
/* 231:251 */     ArrayList var3 = new ArrayList();
/* 232:252 */     Object[] var4 = par2ArrayOfObj;
/* 233:253 */     int var5 = par2ArrayOfObj.length;
/* 234:255 */     for (int var6 = 0; var6 < var5; var6++)
/* 235:    */     {
/* 236:257 */       Object var7 = var4[var6];
/* 237:259 */       if ((var7 instanceof ItemStack))
/* 238:    */       {
/* 239:261 */         var3.add(((ItemStack)var7).copy());
/* 240:    */       }
/* 241:263 */       else if ((var7 instanceof Item))
/* 242:    */       {
/* 243:265 */         var3.add(new ItemStack((Item)var7));
/* 244:    */       }
/* 245:    */       else
/* 246:    */       {
/* 247:269 */         if (!(var7 instanceof Block)) {
/* 248:271 */           throw new RuntimeException("Invalid shapeless recipy!");
/* 249:    */         }
/* 250:274 */         var3.add(new ItemStack((Block)var7));
/* 251:    */       }
/* 252:    */     }
/* 253:278 */     this.recipes.add(new ShapelessRecipes(par1ItemStack, var3));
/* 254:    */   }
/* 255:    */   
/* 256:    */   public ItemStack findMatchingRecipe(InventoryCrafting par1InventoryCrafting, World par2World)
/* 257:    */   {
/* 258:283 */     int var3 = 0;
/* 259:284 */     ItemStack var4 = null;
/* 260:285 */     ItemStack var5 = null;
/* 261:288 */     for (int var6 = 0; var6 < par1InventoryCrafting.getSizeInventory(); var6++)
/* 262:    */     {
/* 263:290 */       ItemStack var7 = par1InventoryCrafting.getStackInSlot(var6);
/* 264:292 */       if (var7 != null)
/* 265:    */       {
/* 266:294 */         if (var3 == 0) {
/* 267:296 */           var4 = var7;
/* 268:    */         }
/* 269:299 */         if (var3 == 1) {
/* 270:301 */           var5 = var7;
/* 271:    */         }
/* 272:304 */         var3++;
/* 273:    */       }
/* 274:    */     }
/* 275:308 */     if ((var3 == 2) && (var4.getItem() == var5.getItem()) && (var4.stackSize == 1) && (var5.stackSize == 1) && (var4.getItem().isDamageable()))
/* 276:    */     {
/* 277:310 */       Item var11 = var4.getItem();
/* 278:311 */       int var13 = var11.getMaxDamage() - var4.getItemDamageForDisplay();
/* 279:312 */       int var8 = var11.getMaxDamage() - var5.getItemDamageForDisplay();
/* 280:313 */       int var9 = var13 + var8 + var11.getMaxDamage() * 5 / 100;
/* 281:314 */       int var10 = var11.getMaxDamage() - var9;
/* 282:316 */       if (var10 < 0) {
/* 283:318 */         var10 = 0;
/* 284:    */       }
/* 285:321 */       return new ItemStack(var4.getItem(), 1, var10);
/* 286:    */     }
/* 287:325 */     for (var6 = 0; var6 < this.recipes.size(); var6++)
/* 288:    */     {
/* 289:327 */       IRecipe var12 = (IRecipe)this.recipes.get(var6);
/* 290:329 */       if (var12.matches(par1InventoryCrafting, par2World)) {
/* 291:331 */         return var12.getCraftingResult(par1InventoryCrafting);
/* 292:    */       }
/* 293:    */     }
/* 294:335 */     return null;
/* 295:    */   }
/* 296:    */   
/* 297:    */   public List getRecipeList()
/* 298:    */   {
/* 299:344 */     return this.recipes;
/* 300:    */   }
/* 301:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.item.crafting.CraftingManager
 * JD-Core Version:    0.7.0.1
 */