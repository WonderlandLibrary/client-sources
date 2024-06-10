/*   1:    */ package net.minecraft.item;
/*   2:    */ 
/*   3:    */ import com.google.common.collect.HashMultimap;
/*   4:    */ import com.google.common.collect.Multimap;
/*   5:    */ import com.google.common.collect.Sets;
/*   6:    */ import java.util.HashSet;
/*   7:    */ import java.util.Iterator;
/*   8:    */ import java.util.List;
/*   9:    */ import java.util.Random;
/*  10:    */ import java.util.Set;
/*  11:    */ import java.util.UUID;
/*  12:    */ import net.minecraft.block.Block;
/*  13:    */ import net.minecraft.block.BlockDirt;
/*  14:    */ import net.minecraft.block.BlockDoublePlant;
/*  15:    */ import net.minecraft.block.BlockFlower;
/*  16:    */ import net.minecraft.block.BlockNewLog;
/*  17:    */ import net.minecraft.block.BlockOldLog;
/*  18:    */ import net.minecraft.block.BlockQuartz;
/*  19:    */ import net.minecraft.block.BlockSand;
/*  20:    */ import net.minecraft.block.BlockSandStone;
/*  21:    */ import net.minecraft.block.BlockSapling;
/*  22:    */ import net.minecraft.block.BlockSilverfish;
/*  23:    */ import net.minecraft.block.BlockStoneBrick;
/*  24:    */ import net.minecraft.block.BlockWall;
/*  25:    */ import net.minecraft.block.BlockWood;
/*  26:    */ import net.minecraft.block.material.Material;
/*  27:    */ import net.minecraft.client.renderer.texture.IIconRegister;
/*  28:    */ import net.minecraft.creativetab.CreativeTabs;
/*  29:    */ import net.minecraft.entity.Entity;
/*  30:    */ import net.minecraft.entity.EntityLivingBase;
/*  31:    */ import net.minecraft.entity.item.EntityItemFrame;
/*  32:    */ import net.minecraft.entity.item.EntityPainting;
/*  33:    */ import net.minecraft.entity.player.EntityPlayer;
/*  34:    */ import net.minecraft.init.Blocks;
/*  35:    */ import net.minecraft.init.Items;
/*  36:    */ import net.minecraft.potion.Potion;
/*  37:    */ import net.minecraft.potion.PotionHelper;
/*  38:    */ import net.minecraft.util.IIcon;
/*  39:    */ import net.minecraft.util.MathHelper;
/*  40:    */ import net.minecraft.util.MovingObjectPosition;
/*  41:    */ import net.minecraft.util.RegistryNamespaced;
/*  42:    */ import net.minecraft.util.StatCollector;
/*  43:    */ import net.minecraft.util.Vec3;
/*  44:    */ import net.minecraft.util.Vec3Pool;
/*  45:    */ import net.minecraft.world.World;
/*  46:    */ 
/*  47:    */ public class Item
/*  48:    */ {
/*  49: 47 */   public static final RegistryNamespaced itemRegistry = new RegistryNamespaced();
/*  50: 48 */   protected static final UUID field_111210_e = UUID.fromString("CB3F55D3-645C-4F38-A497-9C13A33DB5CF");
/*  51:    */   private CreativeTabs tabToDisplayOn;
/*  52: 52 */   protected static Random itemRand = new Random();
/*  53: 55 */   protected int maxStackSize = 64;
/*  54:    */   private int maxDamage;
/*  55:    */   protected boolean bFull3D;
/*  56:    */   protected boolean hasSubtypes;
/*  57:    */   private Item containerItem;
/*  58:    */   private String potionEffect;
/*  59:    */   private String unlocalizedName;
/*  60:    */   protected IIcon itemIcon;
/*  61:    */   protected String iconString;
/*  62:    */   private static final String __OBFID = "CL_00000041";
/*  63:    */   
/*  64:    */   public static int getIdFromItem(Item p_150891_0_)
/*  65:    */   {
/*  66: 82 */     return p_150891_0_ == null ? 0 : itemRegistry.getIDForObject(p_150891_0_);
/*  67:    */   }
/*  68:    */   
/*  69:    */   public static Item getItemById(int p_150899_0_)
/*  70:    */   {
/*  71: 87 */     return (Item)itemRegistry.getObjectForID(p_150899_0_);
/*  72:    */   }
/*  73:    */   
/*  74:    */   public static Item getItemFromBlock(Block p_150898_0_)
/*  75:    */   {
/*  76: 92 */     return getItemById(Block.getIdFromBlock(p_150898_0_));
/*  77:    */   }
/*  78:    */   
/*  79:    */   public static void registerItems()
/*  80:    */   {
/*  81: 97 */     itemRegistry.addObject(256, "iron_shovel", new ItemSpade(ToolMaterial.IRON).setUnlocalizedName("shovelIron").setTextureName("iron_shovel"));
/*  82: 98 */     itemRegistry.addObject(257, "iron_pickaxe", new ItemPickaxe(ToolMaterial.IRON).setUnlocalizedName("pickaxeIron").setTextureName("iron_pickaxe"));
/*  83: 99 */     itemRegistry.addObject(258, "iron_axe", new ItemAxe(ToolMaterial.IRON).setUnlocalizedName("hatchetIron").setTextureName("iron_axe"));
/*  84:100 */     itemRegistry.addObject(259, "flint_and_steel", new ItemFlintAndSteel().setUnlocalizedName("flintAndSteel").setTextureName("flint_and_steel"));
/*  85:101 */     itemRegistry.addObject(260, "apple", new ItemFood(4, 0.3F, false).setUnlocalizedName("apple").setTextureName("apple"));
/*  86:102 */     itemRegistry.addObject(261, "bow", new ItemBow().setUnlocalizedName("bow").setTextureName("bow"));
/*  87:103 */     itemRegistry.addObject(262, "arrow", new Item().setUnlocalizedName("arrow").setCreativeTab(CreativeTabs.tabCombat).setTextureName("arrow"));
/*  88:104 */     itemRegistry.addObject(263, "coal", new ItemCoal().setUnlocalizedName("coal").setTextureName("coal"));
/*  89:105 */     itemRegistry.addObject(264, "diamond", new Item().setUnlocalizedName("diamond").setCreativeTab(CreativeTabs.tabMaterials).setTextureName("diamond"));
/*  90:106 */     itemRegistry.addObject(265, "iron_ingot", new Item().setUnlocalizedName("ingotIron").setCreativeTab(CreativeTabs.tabMaterials).setTextureName("iron_ingot"));
/*  91:107 */     itemRegistry.addObject(266, "gold_ingot", new Item().setUnlocalizedName("ingotGold").setCreativeTab(CreativeTabs.tabMaterials).setTextureName("gold_ingot"));
/*  92:108 */     itemRegistry.addObject(267, "iron_sword", new ItemSword(ToolMaterial.IRON).setUnlocalizedName("swordIron").setTextureName("iron_sword"));
/*  93:109 */     itemRegistry.addObject(268, "wooden_sword", new ItemSword(ToolMaterial.WOOD).setUnlocalizedName("swordWood").setTextureName("wood_sword"));
/*  94:110 */     itemRegistry.addObject(269, "wooden_shovel", new ItemSpade(ToolMaterial.WOOD).setUnlocalizedName("shovelWood").setTextureName("wood_shovel"));
/*  95:111 */     itemRegistry.addObject(270, "wooden_pickaxe", new ItemPickaxe(ToolMaterial.WOOD).setUnlocalizedName("pickaxeWood").setTextureName("wood_pickaxe"));
/*  96:112 */     itemRegistry.addObject(271, "wooden_axe", new ItemAxe(ToolMaterial.WOOD).setUnlocalizedName("hatchetWood").setTextureName("wood_axe"));
/*  97:113 */     itemRegistry.addObject(272, "stone_sword", new ItemSword(ToolMaterial.STONE).setUnlocalizedName("swordStone").setTextureName("stone_sword"));
/*  98:114 */     itemRegistry.addObject(273, "stone_shovel", new ItemSpade(ToolMaterial.STONE).setUnlocalizedName("shovelStone").setTextureName("stone_shovel"));
/*  99:115 */     itemRegistry.addObject(274, "stone_pickaxe", new ItemPickaxe(ToolMaterial.STONE).setUnlocalizedName("pickaxeStone").setTextureName("stone_pickaxe"));
/* 100:116 */     itemRegistry.addObject(275, "stone_axe", new ItemAxe(ToolMaterial.STONE).setUnlocalizedName("hatchetStone").setTextureName("stone_axe"));
/* 101:117 */     itemRegistry.addObject(276, "diamond_sword", new ItemSword(ToolMaterial.EMERALD).setUnlocalizedName("swordDiamond").setTextureName("diamond_sword"));
/* 102:118 */     itemRegistry.addObject(277, "diamond_shovel", new ItemSpade(ToolMaterial.EMERALD).setUnlocalizedName("shovelDiamond").setTextureName("diamond_shovel"));
/* 103:119 */     itemRegistry.addObject(278, "diamond_pickaxe", new ItemPickaxe(ToolMaterial.EMERALD).setUnlocalizedName("pickaxeDiamond").setTextureName("diamond_pickaxe"));
/* 104:120 */     itemRegistry.addObject(279, "diamond_axe", new ItemAxe(ToolMaterial.EMERALD).setUnlocalizedName("hatchetDiamond").setTextureName("diamond_axe"));
/* 105:121 */     itemRegistry.addObject(280, "stick", new Item().setFull3D().setUnlocalizedName("stick").setCreativeTab(CreativeTabs.tabMaterials).setTextureName("stick"));
/* 106:122 */     itemRegistry.addObject(281, "bowl", new Item().setUnlocalizedName("bowl").setCreativeTab(CreativeTabs.tabMaterials).setTextureName("bowl"));
/* 107:123 */     itemRegistry.addObject(282, "mushroom_stew", new ItemSoup(6).setUnlocalizedName("mushroomStew").setTextureName("mushroom_stew"));
/* 108:124 */     itemRegistry.addObject(283, "golden_sword", new ItemSword(ToolMaterial.GOLD).setUnlocalizedName("swordGold").setTextureName("gold_sword"));
/* 109:125 */     itemRegistry.addObject(284, "golden_shovel", new ItemSpade(ToolMaterial.GOLD).setUnlocalizedName("shovelGold").setTextureName("gold_shovel"));
/* 110:126 */     itemRegistry.addObject(285, "golden_pickaxe", new ItemPickaxe(ToolMaterial.GOLD).setUnlocalizedName("pickaxeGold").setTextureName("gold_pickaxe"));
/* 111:127 */     itemRegistry.addObject(286, "golden_axe", new ItemAxe(ToolMaterial.GOLD).setUnlocalizedName("hatchetGold").setTextureName("gold_axe"));
/* 112:128 */     itemRegistry.addObject(287, "string", new ItemReed(Blocks.tripwire).setUnlocalizedName("string").setCreativeTab(CreativeTabs.tabMaterials).setTextureName("string"));
/* 113:129 */     itemRegistry.addObject(288, "feather", new Item().setUnlocalizedName("feather").setCreativeTab(CreativeTabs.tabMaterials).setTextureName("feather"));
/* 114:130 */     itemRegistry.addObject(289, "gunpowder", new Item().setUnlocalizedName("sulphur").setPotionEffect(PotionHelper.gunpowderEffect).setCreativeTab(CreativeTabs.tabMaterials).setTextureName("gunpowder"));
/* 115:131 */     itemRegistry.addObject(290, "wooden_hoe", new ItemHoe(ToolMaterial.WOOD).setUnlocalizedName("hoeWood").setTextureName("wood_hoe"));
/* 116:132 */     itemRegistry.addObject(291, "stone_hoe", new ItemHoe(ToolMaterial.STONE).setUnlocalizedName("hoeStone").setTextureName("stone_hoe"));
/* 117:133 */     itemRegistry.addObject(292, "iron_hoe", new ItemHoe(ToolMaterial.IRON).setUnlocalizedName("hoeIron").setTextureName("iron_hoe"));
/* 118:134 */     itemRegistry.addObject(293, "diamond_hoe", new ItemHoe(ToolMaterial.EMERALD).setUnlocalizedName("hoeDiamond").setTextureName("diamond_hoe"));
/* 119:135 */     itemRegistry.addObject(294, "golden_hoe", new ItemHoe(ToolMaterial.GOLD).setUnlocalizedName("hoeGold").setTextureName("gold_hoe"));
/* 120:136 */     itemRegistry.addObject(295, "wheat_seeds", new ItemSeeds(Blocks.wheat, Blocks.farmland).setUnlocalizedName("seeds").setTextureName("seeds_wheat"));
/* 121:137 */     itemRegistry.addObject(296, "wheat", new Item().setUnlocalizedName("wheat").setCreativeTab(CreativeTabs.tabMaterials).setTextureName("wheat"));
/* 122:138 */     itemRegistry.addObject(297, "bread", new ItemFood(5, 0.6F, false).setUnlocalizedName("bread").setTextureName("bread"));
/* 123:139 */     itemRegistry.addObject(298, "leather_helmet", new ItemArmor(ItemArmor.ArmorMaterial.CLOTH, 0, 0).setUnlocalizedName("helmetCloth").setTextureName("leather_helmet"));
/* 124:140 */     itemRegistry.addObject(299, "leather_chestplate", new ItemArmor(ItemArmor.ArmorMaterial.CLOTH, 0, 1).setUnlocalizedName("chestplateCloth").setTextureName("leather_chestplate"));
/* 125:141 */     itemRegistry.addObject(300, "leather_leggings", new ItemArmor(ItemArmor.ArmorMaterial.CLOTH, 0, 2).setUnlocalizedName("leggingsCloth").setTextureName("leather_leggings"));
/* 126:142 */     itemRegistry.addObject(301, "leather_boots", new ItemArmor(ItemArmor.ArmorMaterial.CLOTH, 0, 3).setUnlocalizedName("bootsCloth").setTextureName("leather_boots"));
/* 127:143 */     itemRegistry.addObject(302, "chainmail_helmet", new ItemArmor(ItemArmor.ArmorMaterial.CHAIN, 1, 0).setUnlocalizedName("helmetChain").setTextureName("chainmail_helmet"));
/* 128:144 */     itemRegistry.addObject(303, "chainmail_chestplate", new ItemArmor(ItemArmor.ArmorMaterial.CHAIN, 1, 1).setUnlocalizedName("chestplateChain").setTextureName("chainmail_chestplate"));
/* 129:145 */     itemRegistry.addObject(304, "chainmail_leggings", new ItemArmor(ItemArmor.ArmorMaterial.CHAIN, 1, 2).setUnlocalizedName("leggingsChain").setTextureName("chainmail_leggings"));
/* 130:146 */     itemRegistry.addObject(305, "chainmail_boots", new ItemArmor(ItemArmor.ArmorMaterial.CHAIN, 1, 3).setUnlocalizedName("bootsChain").setTextureName("chainmail_boots"));
/* 131:147 */     itemRegistry.addObject(306, "iron_helmet", new ItemArmor(ItemArmor.ArmorMaterial.IRON, 2, 0).setUnlocalizedName("helmetIron").setTextureName("iron_helmet"));
/* 132:148 */     itemRegistry.addObject(307, "iron_chestplate", new ItemArmor(ItemArmor.ArmorMaterial.IRON, 2, 1).setUnlocalizedName("chestplateIron").setTextureName("iron_chestplate"));
/* 133:149 */     itemRegistry.addObject(308, "iron_leggings", new ItemArmor(ItemArmor.ArmorMaterial.IRON, 2, 2).setUnlocalizedName("leggingsIron").setTextureName("iron_leggings"));
/* 134:150 */     itemRegistry.addObject(309, "iron_boots", new ItemArmor(ItemArmor.ArmorMaterial.IRON, 2, 3).setUnlocalizedName("bootsIron").setTextureName("iron_boots"));
/* 135:151 */     itemRegistry.addObject(310, "diamond_helmet", new ItemArmor(ItemArmor.ArmorMaterial.DIAMOND, 3, 0).setUnlocalizedName("helmetDiamond").setTextureName("diamond_helmet"));
/* 136:152 */     itemRegistry.addObject(311, "diamond_chestplate", new ItemArmor(ItemArmor.ArmorMaterial.DIAMOND, 3, 1).setUnlocalizedName("chestplateDiamond").setTextureName("diamond_chestplate"));
/* 137:153 */     itemRegistry.addObject(312, "diamond_leggings", new ItemArmor(ItemArmor.ArmorMaterial.DIAMOND, 3, 2).setUnlocalizedName("leggingsDiamond").setTextureName("diamond_leggings"));
/* 138:154 */     itemRegistry.addObject(313, "diamond_boots", new ItemArmor(ItemArmor.ArmorMaterial.DIAMOND, 3, 3).setUnlocalizedName("bootsDiamond").setTextureName("diamond_boots"));
/* 139:155 */     itemRegistry.addObject(314, "golden_helmet", new ItemArmor(ItemArmor.ArmorMaterial.GOLD, 4, 0).setUnlocalizedName("helmetGold").setTextureName("gold_helmet"));
/* 140:156 */     itemRegistry.addObject(315, "golden_chestplate", new ItemArmor(ItemArmor.ArmorMaterial.GOLD, 4, 1).setUnlocalizedName("chestplateGold").setTextureName("gold_chestplate"));
/* 141:157 */     itemRegistry.addObject(316, "golden_leggings", new ItemArmor(ItemArmor.ArmorMaterial.GOLD, 4, 2).setUnlocalizedName("leggingsGold").setTextureName("gold_leggings"));
/* 142:158 */     itemRegistry.addObject(317, "golden_boots", new ItemArmor(ItemArmor.ArmorMaterial.GOLD, 4, 3).setUnlocalizedName("bootsGold").setTextureName("gold_boots"));
/* 143:159 */     itemRegistry.addObject(318, "flint", new Item().setUnlocalizedName("flint").setCreativeTab(CreativeTabs.tabMaterials).setTextureName("flint"));
/* 144:160 */     itemRegistry.addObject(319, "porkchop", new ItemFood(3, 0.3F, true).setUnlocalizedName("porkchopRaw").setTextureName("porkchop_raw"));
/* 145:161 */     itemRegistry.addObject(320, "cooked_porkchop", new ItemFood(8, 0.8F, true).setUnlocalizedName("porkchopCooked").setTextureName("porkchop_cooked"));
/* 146:162 */     itemRegistry.addObject(321, "painting", new ItemHangingEntity(EntityPainting.class).setUnlocalizedName("painting").setTextureName("painting"));
/* 147:163 */     itemRegistry.addObject(322, "golden_apple", new ItemAppleGold(4, 1.2F, false).setAlwaysEdible().setPotionEffect(Potion.regeneration.id, 5, 1, 1.0F).setUnlocalizedName("appleGold").setTextureName("apple_golden"));
/* 148:164 */     itemRegistry.addObject(323, "sign", new ItemSign().setUnlocalizedName("sign").setTextureName("sign"));
/* 149:165 */     itemRegistry.addObject(324, "wooden_door", new ItemDoor(Material.wood).setUnlocalizedName("doorWood").setTextureName("door_wood"));
/* 150:166 */     Item var0 = new ItemBucket(Blocks.air).setUnlocalizedName("bucket").setMaxStackSize(16).setTextureName("bucket_empty");
/* 151:167 */     itemRegistry.addObject(325, "bucket", var0);
/* 152:168 */     itemRegistry.addObject(326, "water_bucket", new ItemBucket(Blocks.flowing_water).setUnlocalizedName("bucketWater").setContainerItem(var0).setTextureName("bucket_water"));
/* 153:169 */     itemRegistry.addObject(327, "lava_bucket", new ItemBucket(Blocks.flowing_lava).setUnlocalizedName("bucketLava").setContainerItem(var0).setTextureName("bucket_lava"));
/* 154:170 */     itemRegistry.addObject(328, "minecart", new ItemMinecart(0).setUnlocalizedName("minecart").setTextureName("minecart_normal"));
/* 155:171 */     itemRegistry.addObject(329, "saddle", new ItemSaddle().setUnlocalizedName("saddle").setTextureName("saddle"));
/* 156:172 */     itemRegistry.addObject(330, "iron_door", new ItemDoor(Material.iron).setUnlocalizedName("doorIron").setTextureName("door_iron"));
/* 157:173 */     itemRegistry.addObject(331, "redstone", new ItemRedstone().setUnlocalizedName("redstone").setPotionEffect(PotionHelper.redstoneEffect).setTextureName("redstone_dust"));
/* 158:174 */     itemRegistry.addObject(332, "snowball", new ItemSnowball().setUnlocalizedName("snowball").setTextureName("snowball"));
/* 159:175 */     itemRegistry.addObject(333, "boat", new ItemBoat().setUnlocalizedName("boat").setTextureName("boat"));
/* 160:176 */     itemRegistry.addObject(334, "leather", new Item().setUnlocalizedName("leather").setCreativeTab(CreativeTabs.tabMaterials).setTextureName("leather"));
/* 161:177 */     itemRegistry.addObject(335, "milk_bucket", new ItemBucketMilk().setUnlocalizedName("milk").setContainerItem(var0).setTextureName("bucket_milk"));
/* 162:178 */     itemRegistry.addObject(336, "brick", new Item().setUnlocalizedName("brick").setCreativeTab(CreativeTabs.tabMaterials).setTextureName("brick"));
/* 163:179 */     itemRegistry.addObject(337, "clay_ball", new Item().setUnlocalizedName("clay").setCreativeTab(CreativeTabs.tabMaterials).setTextureName("clay_ball"));
/* 164:180 */     itemRegistry.addObject(338, "reeds", new ItemReed(Blocks.reeds).setUnlocalizedName("reeds").setCreativeTab(CreativeTabs.tabMaterials).setTextureName("reeds"));
/* 165:181 */     itemRegistry.addObject(339, "paper", new Item().setUnlocalizedName("paper").setCreativeTab(CreativeTabs.tabMisc).setTextureName("paper"));
/* 166:182 */     itemRegistry.addObject(340, "book", new ItemBook().setUnlocalizedName("book").setCreativeTab(CreativeTabs.tabMisc).setTextureName("book_normal"));
/* 167:183 */     itemRegistry.addObject(341, "slime_ball", new Item().setUnlocalizedName("slimeball").setCreativeTab(CreativeTabs.tabMisc).setTextureName("slimeball"));
/* 168:184 */     itemRegistry.addObject(342, "chest_minecart", new ItemMinecart(1).setUnlocalizedName("minecartChest").setTextureName("minecart_chest"));
/* 169:185 */     itemRegistry.addObject(343, "furnace_minecart", new ItemMinecart(2).setUnlocalizedName("minecartFurnace").setTextureName("minecart_furnace"));
/* 170:186 */     itemRegistry.addObject(344, "egg", new ItemEgg().setUnlocalizedName("egg").setTextureName("egg"));
/* 171:187 */     itemRegistry.addObject(345, "compass", new Item().setUnlocalizedName("compass").setCreativeTab(CreativeTabs.tabTools).setTextureName("compass"));
/* 172:188 */     itemRegistry.addObject(346, "fishing_rod", new ItemFishingRod().setUnlocalizedName("fishingRod").setTextureName("fishing_rod"));
/* 173:189 */     itemRegistry.addObject(347, "clock", new Item().setUnlocalizedName("clock").setCreativeTab(CreativeTabs.tabTools).setTextureName("clock"));
/* 174:190 */     itemRegistry.addObject(348, "glowstone_dust", new Item().setUnlocalizedName("yellowDust").setPotionEffect(PotionHelper.glowstoneEffect).setCreativeTab(CreativeTabs.tabMaterials).setTextureName("glowstone_dust"));
/* 175:191 */     itemRegistry.addObject(349, "fish", new ItemFishFood(false).setUnlocalizedName("fish").setTextureName("fish_raw").setHasSubtypes(true));
/* 176:192 */     itemRegistry.addObject(350, "cooked_fished", new ItemFishFood(true).setUnlocalizedName("fish").setTextureName("fish_cooked").setHasSubtypes(true));
/* 177:193 */     itemRegistry.addObject(351, "dye", new ItemDye().setUnlocalizedName("dyePowder").setTextureName("dye_powder"));
/* 178:194 */     itemRegistry.addObject(352, "bone", new Item().setUnlocalizedName("bone").setFull3D().setCreativeTab(CreativeTabs.tabMisc).setTextureName("bone"));
/* 179:195 */     itemRegistry.addObject(353, "sugar", new Item().setUnlocalizedName("sugar").setPotionEffect(PotionHelper.sugarEffect).setCreativeTab(CreativeTabs.tabMaterials).setTextureName("sugar"));
/* 180:196 */     itemRegistry.addObject(354, "cake", new ItemReed(Blocks.cake).setMaxStackSize(1).setUnlocalizedName("cake").setCreativeTab(CreativeTabs.tabFood).setTextureName("cake"));
/* 181:197 */     itemRegistry.addObject(355, "bed", new ItemBed().setMaxStackSize(1).setUnlocalizedName("bed").setTextureName("bed"));
/* 182:198 */     itemRegistry.addObject(356, "repeater", new ItemReed(Blocks.unpowered_repeater).setUnlocalizedName("diode").setCreativeTab(CreativeTabs.tabRedstone).setTextureName("repeater"));
/* 183:199 */     itemRegistry.addObject(357, "cookie", new ItemFood(2, 0.1F, false).setUnlocalizedName("cookie").setTextureName("cookie"));
/* 184:200 */     itemRegistry.addObject(358, "filled_map", new ItemMap().setUnlocalizedName("map").setTextureName("map_filled"));
/* 185:201 */     itemRegistry.addObject(359, "shears", new ItemShears().setUnlocalizedName("shears").setTextureName("shears"));
/* 186:202 */     itemRegistry.addObject(360, "melon", new ItemFood(2, 0.3F, false).setUnlocalizedName("melon").setTextureName("melon"));
/* 187:203 */     itemRegistry.addObject(361, "pumpkin_seeds", new ItemSeeds(Blocks.pumpkin_stem, Blocks.farmland).setUnlocalizedName("seeds_pumpkin").setTextureName("seeds_pumpkin"));
/* 188:204 */     itemRegistry.addObject(362, "melon_seeds", new ItemSeeds(Blocks.melon_stem, Blocks.farmland).setUnlocalizedName("seeds_melon").setTextureName("seeds_melon"));
/* 189:205 */     itemRegistry.addObject(363, "beef", new ItemFood(3, 0.3F, true).setUnlocalizedName("beefRaw").setTextureName("beef_raw"));
/* 190:206 */     itemRegistry.addObject(364, "cooked_beef", new ItemFood(8, 0.8F, true).setUnlocalizedName("beefCooked").setTextureName("beef_cooked"));
/* 191:207 */     itemRegistry.addObject(365, "chicken", new ItemFood(2, 0.3F, true).setPotionEffect(Potion.hunger.id, 30, 0, 0.3F).setUnlocalizedName("chickenRaw").setTextureName("chicken_raw"));
/* 192:208 */     itemRegistry.addObject(366, "cooked_chicken", new ItemFood(6, 0.6F, true).setUnlocalizedName("chickenCooked").setTextureName("chicken_cooked"));
/* 193:209 */     itemRegistry.addObject(367, "rotten_flesh", new ItemFood(4, 0.1F, true).setPotionEffect(Potion.hunger.id, 30, 0, 0.8F).setUnlocalizedName("rottenFlesh").setTextureName("rotten_flesh"));
/* 194:210 */     itemRegistry.addObject(368, "ender_pearl", new ItemEnderPearl().setUnlocalizedName("enderPearl").setTextureName("ender_pearl"));
/* 195:211 */     itemRegistry.addObject(369, "blaze_rod", new Item().setUnlocalizedName("blazeRod").setCreativeTab(CreativeTabs.tabMaterials).setTextureName("blaze_rod"));
/* 196:212 */     itemRegistry.addObject(370, "ghast_tear", new Item().setUnlocalizedName("ghastTear").setPotionEffect("+0-1-2-3&4-4+13").setCreativeTab(CreativeTabs.tabBrewing).setTextureName("ghast_tear"));
/* 197:213 */     itemRegistry.addObject(371, "gold_nugget", new Item().setUnlocalizedName("goldNugget").setCreativeTab(CreativeTabs.tabMaterials).setTextureName("gold_nugget"));
/* 198:214 */     itemRegistry.addObject(372, "nether_wart", new ItemSeeds(Blocks.nether_wart, Blocks.soul_sand).setUnlocalizedName("netherStalkSeeds").setPotionEffect("+4").setTextureName("nether_wart"));
/* 199:215 */     itemRegistry.addObject(373, "potion", new ItemPotion().setUnlocalizedName("potion").setTextureName("potion"));
/* 200:216 */     itemRegistry.addObject(374, "glass_bottle", new ItemGlassBottle().setUnlocalizedName("glassBottle").setTextureName("potion_bottle_empty"));
/* 201:217 */     itemRegistry.addObject(375, "spider_eye", new ItemFood(2, 0.8F, false).setPotionEffect(Potion.poison.id, 5, 0, 1.0F).setUnlocalizedName("spiderEye").setPotionEffect(PotionHelper.spiderEyeEffect).setTextureName("spider_eye"));
/* 202:218 */     itemRegistry.addObject(376, "fermented_spider_eye", new Item().setUnlocalizedName("fermentedSpiderEye").setPotionEffect(PotionHelper.fermentedSpiderEyeEffect).setCreativeTab(CreativeTabs.tabBrewing).setTextureName("spider_eye_fermented"));
/* 203:219 */     itemRegistry.addObject(377, "blaze_powder", new Item().setUnlocalizedName("blazePowder").setPotionEffect(PotionHelper.blazePowderEffect).setCreativeTab(CreativeTabs.tabBrewing).setTextureName("blaze_powder"));
/* 204:220 */     itemRegistry.addObject(378, "magma_cream", new Item().setUnlocalizedName("magmaCream").setPotionEffect(PotionHelper.magmaCreamEffect).setCreativeTab(CreativeTabs.tabBrewing).setTextureName("magma_cream"));
/* 205:221 */     itemRegistry.addObject(379, "brewing_stand", new ItemReed(Blocks.brewing_stand).setUnlocalizedName("brewingStand").setCreativeTab(CreativeTabs.tabBrewing).setTextureName("brewing_stand"));
/* 206:222 */     itemRegistry.addObject(380, "cauldron", new ItemReed(Blocks.cauldron).setUnlocalizedName("cauldron").setCreativeTab(CreativeTabs.tabBrewing).setTextureName("cauldron"));
/* 207:223 */     itemRegistry.addObject(381, "ender_eye", new ItemEnderEye().setUnlocalizedName("eyeOfEnder").setTextureName("ender_eye"));
/* 208:224 */     itemRegistry.addObject(382, "speckled_melon", new Item().setUnlocalizedName("speckledMelon").setPotionEffect(PotionHelper.speckledMelonEffect).setCreativeTab(CreativeTabs.tabBrewing).setTextureName("melon_speckled"));
/* 209:225 */     itemRegistry.addObject(383, "spawn_egg", new ItemMonsterPlacer().setUnlocalizedName("monsterPlacer").setTextureName("spawn_egg"));
/* 210:226 */     itemRegistry.addObject(384, "experience_bottle", new ItemExpBottle().setUnlocalizedName("expBottle").setTextureName("experience_bottle"));
/* 211:227 */     itemRegistry.addObject(385, "fire_charge", new ItemFireball().setUnlocalizedName("fireball").setTextureName("fireball"));
/* 212:228 */     itemRegistry.addObject(386, "writable_book", new ItemWritableBook().setUnlocalizedName("writingBook").setCreativeTab(CreativeTabs.tabMisc).setTextureName("book_writable"));
/* 213:229 */     itemRegistry.addObject(387, "written_book", new ItemEditableBook().setUnlocalizedName("writtenBook").setTextureName("book_written").setMaxStackSize(16));
/* 214:230 */     itemRegistry.addObject(388, "emerald", new Item().setUnlocalizedName("emerald").setCreativeTab(CreativeTabs.tabMaterials).setTextureName("emerald"));
/* 215:231 */     itemRegistry.addObject(389, "item_frame", new ItemHangingEntity(EntityItemFrame.class).setUnlocalizedName("frame").setTextureName("item_frame"));
/* 216:232 */     itemRegistry.addObject(390, "flower_pot", new ItemReed(Blocks.flower_pot).setUnlocalizedName("flowerPot").setCreativeTab(CreativeTabs.tabDecorations).setTextureName("flower_pot"));
/* 217:233 */     itemRegistry.addObject(391, "carrot", new ItemSeedFood(4, 0.6F, Blocks.carrots, Blocks.farmland).setUnlocalizedName("carrots").setTextureName("carrot"));
/* 218:234 */     itemRegistry.addObject(392, "potato", new ItemSeedFood(1, 0.3F, Blocks.potatoes, Blocks.farmland).setUnlocalizedName("potato").setTextureName("potato"));
/* 219:235 */     itemRegistry.addObject(393, "baked_potato", new ItemFood(6, 0.6F, false).setUnlocalizedName("potatoBaked").setTextureName("potato_baked"));
/* 220:236 */     itemRegistry.addObject(394, "poisonous_potato", new ItemFood(2, 0.3F, false).setPotionEffect(Potion.poison.id, 5, 0, 0.6F).setUnlocalizedName("potatoPoisonous").setTextureName("potato_poisonous"));
/* 221:237 */     itemRegistry.addObject(395, "map", new ItemEmptyMap().setUnlocalizedName("emptyMap").setTextureName("map_empty"));
/* 222:238 */     itemRegistry.addObject(396, "golden_carrot", new ItemFood(6, 1.2F, false).setUnlocalizedName("carrotGolden").setPotionEffect(PotionHelper.goldenCarrotEffect).setTextureName("carrot_golden"));
/* 223:239 */     itemRegistry.addObject(397, "skull", new ItemSkull().setUnlocalizedName("skull").setTextureName("skull"));
/* 224:240 */     itemRegistry.addObject(398, "carrot_on_a_stick", new ItemCarrotOnAStick().setUnlocalizedName("carrotOnAStick").setTextureName("carrot_on_a_stick"));
/* 225:241 */     itemRegistry.addObject(399, "nether_star", new ItemSimpleFoiled().setUnlocalizedName("netherStar").setCreativeTab(CreativeTabs.tabMaterials).setTextureName("nether_star"));
/* 226:242 */     itemRegistry.addObject(400, "pumpkin_pie", new ItemFood(8, 0.3F, false).setUnlocalizedName("pumpkinPie").setCreativeTab(CreativeTabs.tabFood).setTextureName("pumpkin_pie"));
/* 227:243 */     itemRegistry.addObject(401, "fireworks", new ItemFirework().setUnlocalizedName("fireworks").setTextureName("fireworks"));
/* 228:244 */     itemRegistry.addObject(402, "firework_charge", new ItemFireworkCharge().setUnlocalizedName("fireworksCharge").setCreativeTab(CreativeTabs.tabMisc).setTextureName("fireworks_charge"));
/* 229:245 */     itemRegistry.addObject(403, "enchanted_book", new ItemEnchantedBook().setMaxStackSize(1).setUnlocalizedName("enchantedBook").setTextureName("book_enchanted"));
/* 230:246 */     itemRegistry.addObject(404, "comparator", new ItemReed(Blocks.unpowered_comparator).setUnlocalizedName("comparator").setCreativeTab(CreativeTabs.tabRedstone).setTextureName("comparator"));
/* 231:247 */     itemRegistry.addObject(405, "netherbrick", new Item().setUnlocalizedName("netherbrick").setCreativeTab(CreativeTabs.tabMaterials).setTextureName("netherbrick"));
/* 232:248 */     itemRegistry.addObject(406, "quartz", new Item().setUnlocalizedName("netherquartz").setCreativeTab(CreativeTabs.tabMaterials).setTextureName("quartz"));
/* 233:249 */     itemRegistry.addObject(407, "tnt_minecart", new ItemMinecart(3).setUnlocalizedName("minecartTnt").setTextureName("minecart_tnt"));
/* 234:250 */     itemRegistry.addObject(408, "hopper_minecart", new ItemMinecart(5).setUnlocalizedName("minecartHopper").setTextureName("minecart_hopper"));
/* 235:251 */     itemRegistry.addObject(417, "iron_horse_armor", new Item().setUnlocalizedName("horsearmormetal").setMaxStackSize(1).setCreativeTab(CreativeTabs.tabMisc).setTextureName("iron_horse_armor"));
/* 236:252 */     itemRegistry.addObject(418, "golden_horse_armor", new Item().setUnlocalizedName("horsearmorgold").setMaxStackSize(1).setCreativeTab(CreativeTabs.tabMisc).setTextureName("gold_horse_armor"));
/* 237:253 */     itemRegistry.addObject(419, "diamond_horse_armor", new Item().setUnlocalizedName("horsearmordiamond").setMaxStackSize(1).setCreativeTab(CreativeTabs.tabMisc).setTextureName("diamond_horse_armor"));
/* 238:254 */     itemRegistry.addObject(420, "lead", new ItemLead().setUnlocalizedName("leash").setTextureName("lead"));
/* 239:255 */     itemRegistry.addObject(421, "name_tag", new ItemNameTag().setUnlocalizedName("nameTag").setTextureName("name_tag"));
/* 240:256 */     itemRegistry.addObject(422, "command_block_minecart", new ItemMinecart(6).setUnlocalizedName("minecartCommandBlock").setTextureName("minecart_command_block").setCreativeTab(null));
/* 241:257 */     itemRegistry.addObject(2256, "record_13", new ItemRecord("13").setUnlocalizedName("record").setTextureName("record_13"));
/* 242:258 */     itemRegistry.addObject(2257, "record_cat", new ItemRecord("cat").setUnlocalizedName("record").setTextureName("record_cat"));
/* 243:259 */     itemRegistry.addObject(2258, "record_blocks", new ItemRecord("blocks").setUnlocalizedName("record").setTextureName("record_blocks"));
/* 244:260 */     itemRegistry.addObject(2259, "record_chirp", new ItemRecord("chirp").setUnlocalizedName("record").setTextureName("record_chirp"));
/* 245:261 */     itemRegistry.addObject(2260, "record_far", new ItemRecord("far").setUnlocalizedName("record").setTextureName("record_far"));
/* 246:262 */     itemRegistry.addObject(2261, "record_mall", new ItemRecord("mall").setUnlocalizedName("record").setTextureName("record_mall"));
/* 247:263 */     itemRegistry.addObject(2262, "record_mellohi", new ItemRecord("mellohi").setUnlocalizedName("record").setTextureName("record_mellohi"));
/* 248:264 */     itemRegistry.addObject(2263, "record_stal", new ItemRecord("stal").setUnlocalizedName("record").setTextureName("record_stal"));
/* 249:265 */     itemRegistry.addObject(2264, "record_strad", new ItemRecord("strad").setUnlocalizedName("record").setTextureName("record_strad"));
/* 250:266 */     itemRegistry.addObject(2265, "record_ward", new ItemRecord("ward").setUnlocalizedName("record").setTextureName("record_ward"));
/* 251:267 */     itemRegistry.addObject(2266, "record_11", new ItemRecord("11").setUnlocalizedName("record").setTextureName("record_11"));
/* 252:268 */     itemRegistry.addObject(2267, "record_wait", new ItemRecord("wait").setUnlocalizedName("record").setTextureName("record_wait"));
/* 253:269 */     HashSet var1 = Sets.newHashSet(new Block[] { Blocks.air, Blocks.brewing_stand, Blocks.bed, Blocks.nether_wart, Blocks.cauldron, Blocks.flower_pot, Blocks.wheat, Blocks.reeds, Blocks.cake, Blocks.skull, Blocks.piston_head, Blocks.piston_extension, Blocks.lit_redstone_ore, Blocks.powered_repeater, Blocks.pumpkin_stem, Blocks.standing_sign, Blocks.powered_comparator, Blocks.tripwire, Blocks.lit_redstone_lamp, Blocks.melon_stem, Blocks.unlit_redstone_torch, Blocks.unpowered_comparator, Blocks.redstone_wire, Blocks.wall_sign, Blocks.unpowered_repeater, Blocks.iron_door, Blocks.wooden_door });
/* 254:270 */     Iterator var2 = Block.blockRegistry.getKeys().iterator();
/* 255:272 */     while (var2.hasNext())
/* 256:    */     {
/* 257:274 */       String var3 = (String)var2.next();
/* 258:275 */       Block var4 = (Block)Block.blockRegistry.getObject(var3);
/* 259:    */       Object var5;
/* 260:    */       Object var5;
/* 261:278 */       if (var4 == Blocks.wool)
/* 262:    */       {
/* 263:280 */         var5 = new ItemCloth(Blocks.wool).setUnlocalizedName("cloth");
/* 264:    */       }
/* 265:    */       else
/* 266:    */       {
/* 267:    */         Object var5;
/* 268:282 */         if (var4 == Blocks.stained_hardened_clay)
/* 269:    */         {
/* 270:284 */           var5 = new ItemCloth(Blocks.stained_hardened_clay).setUnlocalizedName("clayHardenedStained");
/* 271:    */         }
/* 272:    */         else
/* 273:    */         {
/* 274:    */           Object var5;
/* 275:286 */           if (var4 == Blocks.stained_glass)
/* 276:    */           {
/* 277:288 */             var5 = new ItemCloth(Blocks.stained_glass).setUnlocalizedName("stainedGlass");
/* 278:    */           }
/* 279:    */           else
/* 280:    */           {
/* 281:    */             Object var5;
/* 282:290 */             if (var4 == Blocks.stained_glass_pane)
/* 283:    */             {
/* 284:292 */               var5 = new ItemCloth(Blocks.stained_glass_pane).setUnlocalizedName("stainedGlassPane");
/* 285:    */             }
/* 286:    */             else
/* 287:    */             {
/* 288:    */               Object var5;
/* 289:294 */               if (var4 == Blocks.carpet)
/* 290:    */               {
/* 291:296 */                 var5 = new ItemCloth(Blocks.carpet).setUnlocalizedName("woolCarpet");
/* 292:    */               }
/* 293:    */               else
/* 294:    */               {
/* 295:    */                 Object var5;
/* 296:298 */                 if (var4 == Blocks.dirt)
/* 297:    */                 {
/* 298:300 */                   var5 = new ItemMultiTexture(Blocks.dirt, Blocks.dirt, BlockDirt.field_150009_a).setUnlocalizedName("dirt");
/* 299:    */                 }
/* 300:    */                 else
/* 301:    */                 {
/* 302:    */                   Object var5;
/* 303:302 */                   if (var4 == Blocks.sand)
/* 304:    */                   {
/* 305:304 */                     var5 = new ItemMultiTexture(Blocks.sand, Blocks.sand, BlockSand.field_149838_a).setUnlocalizedName("sand");
/* 306:    */                   }
/* 307:    */                   else
/* 308:    */                   {
/* 309:    */                     Object var5;
/* 310:306 */                     if (var4 == Blocks.log)
/* 311:    */                     {
/* 312:308 */                       var5 = new ItemMultiTexture(Blocks.log, Blocks.log, BlockOldLog.field_150168_M).setUnlocalizedName("log");
/* 313:    */                     }
/* 314:    */                     else
/* 315:    */                     {
/* 316:    */                       Object var5;
/* 317:310 */                       if (var4 == Blocks.log2)
/* 318:    */                       {
/* 319:312 */                         var5 = new ItemMultiTexture(Blocks.log2, Blocks.log2, BlockNewLog.field_150169_M).setUnlocalizedName("log");
/* 320:    */                       }
/* 321:    */                       else
/* 322:    */                       {
/* 323:    */                         Object var5;
/* 324:314 */                         if (var4 == Blocks.planks)
/* 325:    */                         {
/* 326:316 */                           var5 = new ItemMultiTexture(Blocks.planks, Blocks.planks, BlockWood.field_150096_a).setUnlocalizedName("wood");
/* 327:    */                         }
/* 328:    */                         else
/* 329:    */                         {
/* 330:    */                           Object var5;
/* 331:318 */                           if (var4 == Blocks.monster_egg)
/* 332:    */                           {
/* 333:320 */                             var5 = new ItemMultiTexture(Blocks.monster_egg, Blocks.monster_egg, BlockSilverfish.field_150198_a).setUnlocalizedName("monsterStoneEgg");
/* 334:    */                           }
/* 335:    */                           else
/* 336:    */                           {
/* 337:    */                             Object var5;
/* 338:322 */                             if (var4 == Blocks.stonebrick)
/* 339:    */                             {
/* 340:324 */                               var5 = new ItemMultiTexture(Blocks.stonebrick, Blocks.stonebrick, BlockStoneBrick.field_150142_a).setUnlocalizedName("stonebricksmooth");
/* 341:    */                             }
/* 342:    */                             else
/* 343:    */                             {
/* 344:    */                               Object var5;
/* 345:326 */                               if (var4 == Blocks.sandstone)
/* 346:    */                               {
/* 347:328 */                                 var5 = new ItemMultiTexture(Blocks.sandstone, Blocks.sandstone, BlockSandStone.field_150157_a).setUnlocalizedName("sandStone");
/* 348:    */                               }
/* 349:    */                               else
/* 350:    */                               {
/* 351:    */                                 Object var5;
/* 352:330 */                                 if (var4 == Blocks.quartz_block)
/* 353:    */                                 {
/* 354:332 */                                   var5 = new ItemMultiTexture(Blocks.quartz_block, Blocks.quartz_block, BlockQuartz.field_150191_a).setUnlocalizedName("quartzBlock");
/* 355:    */                                 }
/* 356:    */                                 else
/* 357:    */                                 {
/* 358:    */                                   Object var5;
/* 359:334 */                                   if (var4 == Blocks.stone_slab)
/* 360:    */                                   {
/* 361:336 */                                     var5 = new ItemSlab(Blocks.stone_slab, Blocks.stone_slab, Blocks.double_stone_slab, false).setUnlocalizedName("stoneSlab");
/* 362:    */                                   }
/* 363:    */                                   else
/* 364:    */                                   {
/* 365:    */                                     Object var5;
/* 366:338 */                                     if (var4 == Blocks.double_stone_slab)
/* 367:    */                                     {
/* 368:340 */                                       var5 = new ItemSlab(Blocks.double_stone_slab, Blocks.stone_slab, Blocks.double_stone_slab, true).setUnlocalizedName("stoneSlab");
/* 369:    */                                     }
/* 370:    */                                     else
/* 371:    */                                     {
/* 372:    */                                       Object var5;
/* 373:342 */                                       if (var4 == Blocks.wooden_slab)
/* 374:    */                                       {
/* 375:344 */                                         var5 = new ItemSlab(Blocks.wooden_slab, Blocks.wooden_slab, Blocks.double_wooden_slab, false).setUnlocalizedName("woodSlab");
/* 376:    */                                       }
/* 377:    */                                       else
/* 378:    */                                       {
/* 379:    */                                         Object var5;
/* 380:346 */                                         if (var4 == Blocks.double_wooden_slab)
/* 381:    */                                         {
/* 382:348 */                                           var5 = new ItemSlab(Blocks.double_wooden_slab, Blocks.wooden_slab, Blocks.double_wooden_slab, true).setUnlocalizedName("woodSlab");
/* 383:    */                                         }
/* 384:    */                                         else
/* 385:    */                                         {
/* 386:    */                                           Object var5;
/* 387:350 */                                           if (var4 == Blocks.sapling)
/* 388:    */                                           {
/* 389:352 */                                             var5 = new ItemMultiTexture(Blocks.sapling, Blocks.sapling, BlockSapling.field_149882_a).setUnlocalizedName("sapling");
/* 390:    */                                           }
/* 391:    */                                           else
/* 392:    */                                           {
/* 393:    */                                             Object var5;
/* 394:354 */                                             if (var4 == Blocks.leaves)
/* 395:    */                                             {
/* 396:356 */                                               var5 = new ItemLeaves(Blocks.leaves).setUnlocalizedName("leaves");
/* 397:    */                                             }
/* 398:    */                                             else
/* 399:    */                                             {
/* 400:    */                                               Object var5;
/* 401:358 */                                               if (var4 == Blocks.leaves2)
/* 402:    */                                               {
/* 403:360 */                                                 var5 = new ItemLeaves(Blocks.leaves2).setUnlocalizedName("leaves");
/* 404:    */                                               }
/* 405:    */                                               else
/* 406:    */                                               {
/* 407:    */                                                 Object var5;
/* 408:362 */                                                 if (var4 == Blocks.vine)
/* 409:    */                                                 {
/* 410:364 */                                                   var5 = new ItemColored(Blocks.vine, false);
/* 411:    */                                                 }
/* 412:    */                                                 else
/* 413:    */                                                 {
/* 414:    */                                                   Object var5;
/* 415:366 */                                                   if (var4 == Blocks.tallgrass)
/* 416:    */                                                   {
/* 417:368 */                                                     var5 = new ItemColored(Blocks.tallgrass, true).func_150943_a(new String[] { "shrub", "grass", "fern" });
/* 418:    */                                                   }
/* 419:    */                                                   else
/* 420:    */                                                   {
/* 421:    */                                                     Object var5;
/* 422:370 */                                                     if (var4 == Blocks.yellow_flower)
/* 423:    */                                                     {
/* 424:372 */                                                       var5 = new ItemMultiTexture(Blocks.yellow_flower, Blocks.yellow_flower, BlockFlower.field_149858_b).setUnlocalizedName("flower");
/* 425:    */                                                     }
/* 426:    */                                                     else
/* 427:    */                                                     {
/* 428:    */                                                       Object var5;
/* 429:374 */                                                       if (var4 == Blocks.red_flower)
/* 430:    */                                                       {
/* 431:376 */                                                         var5 = new ItemMultiTexture(Blocks.red_flower, Blocks.red_flower, BlockFlower.field_149859_a).setUnlocalizedName("rose");
/* 432:    */                                                       }
/* 433:    */                                                       else
/* 434:    */                                                       {
/* 435:    */                                                         Object var5;
/* 436:378 */                                                         if (var4 == Blocks.snow_layer)
/* 437:    */                                                         {
/* 438:380 */                                                           var5 = new ItemSnow(Blocks.snow_layer, Blocks.snow_layer);
/* 439:    */                                                         }
/* 440:    */                                                         else
/* 441:    */                                                         {
/* 442:    */                                                           Object var5;
/* 443:382 */                                                           if (var4 == Blocks.waterlily)
/* 444:    */                                                           {
/* 445:384 */                                                             var5 = new ItemLilyPad(Blocks.waterlily);
/* 446:    */                                                           }
/* 447:    */                                                           else
/* 448:    */                                                           {
/* 449:    */                                                             Object var5;
/* 450:386 */                                                             if (var4 == Blocks.piston)
/* 451:    */                                                             {
/* 452:388 */                                                               var5 = new ItemPiston(Blocks.piston);
/* 453:    */                                                             }
/* 454:    */                                                             else
/* 455:    */                                                             {
/* 456:    */                                                               Object var5;
/* 457:390 */                                                               if (var4 == Blocks.sticky_piston)
/* 458:    */                                                               {
/* 459:392 */                                                                 var5 = new ItemPiston(Blocks.sticky_piston);
/* 460:    */                                                               }
/* 461:    */                                                               else
/* 462:    */                                                               {
/* 463:    */                                                                 Object var5;
/* 464:394 */                                                                 if (var4 == Blocks.cobblestone_wall)
/* 465:    */                                                                 {
/* 466:396 */                                                                   var5 = new ItemMultiTexture(Blocks.cobblestone_wall, Blocks.cobblestone_wall, BlockWall.field_150092_a).setUnlocalizedName("cobbleWall");
/* 467:    */                                                                 }
/* 468:    */                                                                 else
/* 469:    */                                                                 {
/* 470:    */                                                                   Object var5;
/* 471:398 */                                                                   if (var4 == Blocks.anvil)
/* 472:    */                                                                   {
/* 473:400 */                                                                     var5 = new ItemAnvilBlock(Blocks.anvil).setUnlocalizedName("anvil");
/* 474:    */                                                                   }
/* 475:    */                                                                   else
/* 476:    */                                                                   {
/* 477:    */                                                                     Object var5;
/* 478:402 */                                                                     if (var4 == Blocks.double_plant)
/* 479:    */                                                                     {
/* 480:404 */                                                                       var5 = new ItemDoublePlant(Blocks.double_plant, Blocks.double_plant, BlockDoublePlant.field_149892_a).setUnlocalizedName("doublePlant");
/* 481:    */                                                                     }
/* 482:    */                                                                     else
/* 483:    */                                                                     {
/* 484:408 */                                                                       if (var1.contains(var4)) {
/* 485:    */                                                                         continue;
/* 486:    */                                                                       }
/* 487:413 */                                                                       var5 = new ItemBlock(var4);
/* 488:    */                                                                     }
/* 489:    */                                                                   }
/* 490:    */                                                                 }
/* 491:    */                                                               }
/* 492:    */                                                             }
/* 493:    */                                                           }
/* 494:    */                                                         }
/* 495:    */                                                       }
/* 496:    */                                                     }
/* 497:    */                                                   }
/* 498:    */                                                 }
/* 499:    */                                               }
/* 500:    */                                             }
/* 501:    */                                           }
/* 502:    */                                         }
/* 503:    */                                       }
/* 504:    */                                     }
/* 505:    */                                   }
/* 506:    */                                 }
/* 507:    */                               }
/* 508:    */                             }
/* 509:    */                           }
/* 510:    */                         }
/* 511:    */                       }
/* 512:    */                     }
/* 513:    */                   }
/* 514:    */                 }
/* 515:    */               }
/* 516:    */             }
/* 517:    */           }
/* 518:    */         }
/* 519:    */       }
/* 520:416 */       itemRegistry.addObject(Block.getIdFromBlock(var4), var3, var5);
/* 521:    */     }
/* 522:    */   }
/* 523:    */   
/* 524:    */   public Item setMaxStackSize(int par1)
/* 525:    */   {
/* 526:422 */     this.maxStackSize = par1;
/* 527:423 */     return this;
/* 528:    */   }
/* 529:    */   
/* 530:    */   public int getSpriteNumber()
/* 531:    */   {
/* 532:431 */     return 1;
/* 533:    */   }
/* 534:    */   
/* 535:    */   public IIcon getIconFromDamage(int par1)
/* 536:    */   {
/* 537:439 */     return this.itemIcon;
/* 538:    */   }
/* 539:    */   
/* 540:    */   public final IIcon getIconIndex(ItemStack par1ItemStack)
/* 541:    */   {
/* 542:447 */     return getIconFromDamage(par1ItemStack.getItemDamage());
/* 543:    */   }
/* 544:    */   
/* 545:    */   public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
/* 546:    */   {
/* 547:456 */     return false;
/* 548:    */   }
/* 549:    */   
/* 550:    */   public float func_150893_a(ItemStack p_150893_1_, Block p_150893_2_)
/* 551:    */   {
/* 552:461 */     return 1.0F;
/* 553:    */   }
/* 554:    */   
/* 555:    */   public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
/* 556:    */   {
/* 557:469 */     return par1ItemStack;
/* 558:    */   }
/* 559:    */   
/* 560:    */   public ItemStack onEaten(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
/* 561:    */   {
/* 562:474 */     return par1ItemStack;
/* 563:    */   }
/* 564:    */   
/* 565:    */   public int getItemStackLimit()
/* 566:    */   {
/* 567:482 */     return this.maxStackSize;
/* 568:    */   }
/* 569:    */   
/* 570:    */   public int getMetadata(int par1)
/* 571:    */   {
/* 572:490 */     return 0;
/* 573:    */   }
/* 574:    */   
/* 575:    */   public boolean getHasSubtypes()
/* 576:    */   {
/* 577:495 */     return this.hasSubtypes;
/* 578:    */   }
/* 579:    */   
/* 580:    */   protected Item setHasSubtypes(boolean par1)
/* 581:    */   {
/* 582:500 */     this.hasSubtypes = par1;
/* 583:501 */     return this;
/* 584:    */   }
/* 585:    */   
/* 586:    */   public int getMaxDamage()
/* 587:    */   {
/* 588:509 */     return this.maxDamage;
/* 589:    */   }
/* 590:    */   
/* 591:    */   protected Item setMaxDamage(int par1)
/* 592:    */   {
/* 593:517 */     this.maxDamage = par1;
/* 594:518 */     return this;
/* 595:    */   }
/* 596:    */   
/* 597:    */   public boolean isDamageable()
/* 598:    */   {
/* 599:523 */     return (this.maxDamage > 0) && (!this.hasSubtypes);
/* 600:    */   }
/* 601:    */   
/* 602:    */   public boolean hitEntity(ItemStack par1ItemStack, EntityLivingBase par2EntityLivingBase, EntityLivingBase par3EntityLivingBase)
/* 603:    */   {
/* 604:532 */     return false;
/* 605:    */   }
/* 606:    */   
/* 607:    */   public boolean onBlockDestroyed(ItemStack p_150894_1_, World p_150894_2_, Block p_150894_3_, int p_150894_4_, int p_150894_5_, int p_150894_6_, EntityLivingBase p_150894_7_)
/* 608:    */   {
/* 609:537 */     return false;
/* 610:    */   }
/* 611:    */   
/* 612:    */   public boolean func_150897_b(Block p_150897_1_)
/* 613:    */   {
/* 614:542 */     return false;
/* 615:    */   }
/* 616:    */   
/* 617:    */   public boolean itemInteractionForEntity(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, EntityLivingBase par3EntityLivingBase)
/* 618:    */   {
/* 619:550 */     return false;
/* 620:    */   }
/* 621:    */   
/* 622:    */   public Item setFull3D()
/* 623:    */   {
/* 624:558 */     this.bFull3D = true;
/* 625:559 */     return this;
/* 626:    */   }
/* 627:    */   
/* 628:    */   public boolean isFull3D()
/* 629:    */   {
/* 630:567 */     return this.bFull3D;
/* 631:    */   }
/* 632:    */   
/* 633:    */   public boolean shouldRotateAroundWhenRendering()
/* 634:    */   {
/* 635:576 */     return false;
/* 636:    */   }
/* 637:    */   
/* 638:    */   public Item setUnlocalizedName(String par1Str)
/* 639:    */   {
/* 640:584 */     this.unlocalizedName = par1Str;
/* 641:585 */     return this;
/* 642:    */   }
/* 643:    */   
/* 644:    */   public String getUnlocalizedNameInefficiently(ItemStack par1ItemStack)
/* 645:    */   {
/* 646:594 */     String var2 = getUnlocalizedName(par1ItemStack);
/* 647:595 */     return var2 == null ? "" : StatCollector.translateToLocal(var2);
/* 648:    */   }
/* 649:    */   
/* 650:    */   public String getUnlocalizedName()
/* 651:    */   {
/* 652:603 */     return "item." + this.unlocalizedName;
/* 653:    */   }
/* 654:    */   
/* 655:    */   public String getUnlocalizedName(ItemStack par1ItemStack)
/* 656:    */   {
/* 657:612 */     return "item." + this.unlocalizedName;
/* 658:    */   }
/* 659:    */   
/* 660:    */   public Item setContainerItem(Item par1Item)
/* 661:    */   {
/* 662:617 */     this.containerItem = par1Item;
/* 663:618 */     return this;
/* 664:    */   }
/* 665:    */   
/* 666:    */   public boolean doesContainerItemLeaveCraftingGrid(ItemStack par1ItemStack)
/* 667:    */   {
/* 668:627 */     return true;
/* 669:    */   }
/* 670:    */   
/* 671:    */   public boolean getShareTag()
/* 672:    */   {
/* 673:635 */     return true;
/* 674:    */   }
/* 675:    */   
/* 676:    */   public Item getContainerItem()
/* 677:    */   {
/* 678:640 */     return this.containerItem;
/* 679:    */   }
/* 680:    */   
/* 681:    */   public boolean hasContainerItem()
/* 682:    */   {
/* 683:648 */     return this.containerItem != null;
/* 684:    */   }
/* 685:    */   
/* 686:    */   public int getColorFromItemStack(ItemStack par1ItemStack, int par2)
/* 687:    */   {
/* 688:653 */     return 16777215;
/* 689:    */   }
/* 690:    */   
/* 691:    */   public void onUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5) {}
/* 692:    */   
/* 693:    */   public void onCreated(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {}
/* 694:    */   
/* 695:    */   public boolean isMap()
/* 696:    */   {
/* 697:672 */     return false;
/* 698:    */   }
/* 699:    */   
/* 700:    */   public EnumAction getItemUseAction(ItemStack par1ItemStack)
/* 701:    */   {
/* 702:680 */     return EnumAction.none;
/* 703:    */   }
/* 704:    */   
/* 705:    */   public int getMaxItemUseDuration(ItemStack par1ItemStack)
/* 706:    */   {
/* 707:688 */     return 0;
/* 708:    */   }
/* 709:    */   
/* 710:    */   public void onPlayerStoppedUsing(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer, int par4) {}
/* 711:    */   
/* 712:    */   protected Item setPotionEffect(String par1Str)
/* 713:    */   {
/* 714:701 */     this.potionEffect = par1Str;
/* 715:702 */     return this;
/* 716:    */   }
/* 717:    */   
/* 718:    */   public String getPotionEffect(ItemStack p_150896_1_)
/* 719:    */   {
/* 720:707 */     return this.potionEffect;
/* 721:    */   }
/* 722:    */   
/* 723:    */   public boolean isPotionIngredient(ItemStack p_150892_1_)
/* 724:    */   {
/* 725:712 */     return getPotionEffect(p_150892_1_) != null;
/* 726:    */   }
/* 727:    */   
/* 728:    */   public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {}
/* 729:    */   
/* 730:    */   public String getItemStackDisplayName(ItemStack par1ItemStack)
/* 731:    */   {
/* 732:722 */     return StatCollector.translateToLocal(new StringBuilder(String.valueOf(getUnlocalizedNameInefficiently(par1ItemStack))).append(".name").toString()).trim();
/* 733:    */   }
/* 734:    */   
/* 735:    */   public boolean hasEffect(ItemStack par1ItemStack)
/* 736:    */   {
/* 737:727 */     return par1ItemStack.isItemEnchanted();
/* 738:    */   }
/* 739:    */   
/* 740:    */   public EnumRarity getRarity(ItemStack par1ItemStack)
/* 741:    */   {
/* 742:735 */     return par1ItemStack.isItemEnchanted() ? EnumRarity.rare : EnumRarity.common;
/* 743:    */   }
/* 744:    */   
/* 745:    */   public boolean isItemTool(ItemStack par1ItemStack)
/* 746:    */   {
/* 747:743 */     return (getItemStackLimit() == 1) && (isDamageable());
/* 748:    */   }
/* 749:    */   
/* 750:    */   protected MovingObjectPosition getMovingObjectPositionFromPlayer(World par1World, EntityPlayer par2EntityPlayer, boolean par3)
/* 751:    */   {
/* 752:748 */     float var4 = 1.0F;
/* 753:749 */     float var5 = par2EntityPlayer.prevRotationPitch + (par2EntityPlayer.rotationPitch - par2EntityPlayer.prevRotationPitch) * var4;
/* 754:750 */     float var6 = par2EntityPlayer.prevRotationYaw + (par2EntityPlayer.rotationYaw - par2EntityPlayer.prevRotationYaw) * var4;
/* 755:751 */     double var7 = par2EntityPlayer.prevPosX + (par2EntityPlayer.posX - par2EntityPlayer.prevPosX) * var4;
/* 756:752 */     double var9 = par2EntityPlayer.prevPosY + (par2EntityPlayer.posY - par2EntityPlayer.prevPosY) * var4 + 1.62D - par2EntityPlayer.yOffset;
/* 757:753 */     double var11 = par2EntityPlayer.prevPosZ + (par2EntityPlayer.posZ - par2EntityPlayer.prevPosZ) * var4;
/* 758:754 */     Vec3 var13 = par1World.getWorldVec3Pool().getVecFromPool(var7, var9, var11);
/* 759:755 */     float var14 = MathHelper.cos(-var6 * 0.01745329F - 3.141593F);
/* 760:756 */     float var15 = MathHelper.sin(-var6 * 0.01745329F - 3.141593F);
/* 761:757 */     float var16 = -MathHelper.cos(-var5 * 0.01745329F);
/* 762:758 */     float var17 = MathHelper.sin(-var5 * 0.01745329F);
/* 763:759 */     float var18 = var15 * var16;
/* 764:760 */     float var20 = var14 * var16;
/* 765:761 */     double var21 = 5.0D;
/* 766:762 */     Vec3 var23 = var13.addVector(var18 * var21, var17 * var21, var20 * var21);
/* 767:763 */     return par1World.func_147447_a(var13, var23, par3, !par3, false);
/* 768:    */   }
/* 769:    */   
/* 770:    */   public int getItemEnchantability()
/* 771:    */   {
/* 772:771 */     return 0;
/* 773:    */   }
/* 774:    */   
/* 775:    */   public boolean requiresMultipleRenderPasses()
/* 776:    */   {
/* 777:776 */     return false;
/* 778:    */   }
/* 779:    */   
/* 780:    */   public IIcon getIconFromDamageForRenderPass(int par1, int par2)
/* 781:    */   {
/* 782:784 */     return getIconFromDamage(par1);
/* 783:    */   }
/* 784:    */   
/* 785:    */   public void getSubItems(Item p_150895_1_, CreativeTabs p_150895_2_, List p_150895_3_)
/* 786:    */   {
/* 787:792 */     p_150895_3_.add(new ItemStack(p_150895_1_, 1, 0));
/* 788:    */   }
/* 789:    */   
/* 790:    */   public CreativeTabs getCreativeTab()
/* 791:    */   {
/* 792:800 */     return this.tabToDisplayOn;
/* 793:    */   }
/* 794:    */   
/* 795:    */   public Item setCreativeTab(CreativeTabs par1CreativeTabs)
/* 796:    */   {
/* 797:808 */     this.tabToDisplayOn = par1CreativeTabs;
/* 798:809 */     return this;
/* 799:    */   }
/* 800:    */   
/* 801:    */   public boolean canItemEditBlocks()
/* 802:    */   {
/* 803:818 */     return true;
/* 804:    */   }
/* 805:    */   
/* 806:    */   public boolean getIsRepairable(ItemStack par1ItemStack, ItemStack par2ItemStack)
/* 807:    */   {
/* 808:826 */     return false;
/* 809:    */   }
/* 810:    */   
/* 811:    */   public void registerIcons(IIconRegister par1IconRegister)
/* 812:    */   {
/* 813:831 */     this.itemIcon = par1IconRegister.registerIcon(getIconString());
/* 814:    */   }
/* 815:    */   
/* 816:    */   public Multimap getItemAttributeModifiers()
/* 817:    */   {
/* 818:839 */     return HashMultimap.create();
/* 819:    */   }
/* 820:    */   
/* 821:    */   protected Item setTextureName(String par1Str)
/* 822:    */   {
/* 823:844 */     this.iconString = par1Str;
/* 824:845 */     return this;
/* 825:    */   }
/* 826:    */   
/* 827:    */   protected String getIconString()
/* 828:    */   {
/* 829:853 */     return this.iconString == null ? "MISSING_ICON_ITEM_" + itemRegistry.getIDForObject(this) + "_" + this.unlocalizedName : this.iconString;
/* 830:    */   }
/* 831:    */   
/* 832:    */   public static enum ToolMaterial
/* 833:    */   {
/* 834:858 */     WOOD("WOOD", 0, 0, 59, 2.0F, 0.0F, 15),  STONE("STONE", 1, 1, 131, 4.0F, 1.0F, 5),  IRON("IRON", 2, 2, 250, 6.0F, 2.0F, 14),  EMERALD("EMERALD", 3, 3, 1561, 8.0F, 3.0F, 10),  GOLD("GOLD", 4, 0, 32, 12.0F, 0.0F, 22);
/* 835:    */     
/* 836:    */     private final int harvestLevel;
/* 837:    */     private final int maxUses;
/* 838:    */     private final float efficiencyOnProperMaterial;
/* 839:    */     private final float damageVsEntity;
/* 840:    */     private final int enchantability;
/* 841:869 */     private static final ToolMaterial[] $VALUES = { WOOD, STONE, IRON, EMERALD, GOLD };
/* 842:    */     private static final String __OBFID = "CL_00000042";
/* 843:    */     
/* 844:    */     private ToolMaterial(String par1Str, int par2, int par3, int par4, float par5, float par6, int par7)
/* 845:    */     {
/* 846:874 */       this.harvestLevel = par3;
/* 847:875 */       this.maxUses = par4;
/* 848:876 */       this.efficiencyOnProperMaterial = par5;
/* 849:877 */       this.damageVsEntity = par6;
/* 850:878 */       this.enchantability = par7;
/* 851:    */     }
/* 852:    */     
/* 853:    */     public int getMaxUses()
/* 854:    */     {
/* 855:883 */       return this.maxUses;
/* 856:    */     }
/* 857:    */     
/* 858:    */     public float getEfficiencyOnProperMaterial()
/* 859:    */     {
/* 860:888 */       return this.efficiencyOnProperMaterial;
/* 861:    */     }
/* 862:    */     
/* 863:    */     public float getDamageVsEntity()
/* 864:    */     {
/* 865:893 */       return this.damageVsEntity;
/* 866:    */     }
/* 867:    */     
/* 868:    */     public int getHarvestLevel()
/* 869:    */     {
/* 870:898 */       return this.harvestLevel;
/* 871:    */     }
/* 872:    */     
/* 873:    */     public int getEnchantability()
/* 874:    */     {
/* 875:903 */       return this.enchantability;
/* 876:    */     }
/* 877:    */     
/* 878:    */     public Item func_150995_f()
/* 879:    */     {
/* 880:908 */       return this == EMERALD ? Items.diamond : this == IRON ? Items.iron_ingot : this == GOLD ? Items.gold_ingot : this == STONE ? Item.getItemFromBlock(Blocks.cobblestone) : this == WOOD ? Item.getItemFromBlock(Blocks.planks) : null;
/* 881:    */     }
/* 882:    */   }
/* 883:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.item.Item
 * JD-Core Version:    0.7.0.1
 */