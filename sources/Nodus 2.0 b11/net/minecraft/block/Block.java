/*    1:     */ package net.minecraft.block;
/*    2:     */ 
/*    3:     */ import java.util.Iterator;
/*    4:     */ import java.util.List;
/*    5:     */ import java.util.Random;
/*    6:     */ import me.connorm.Nodus.Nodus;
/*    7:     */ import me.connorm.Nodus.module.NodusModuleManager;
/*    8:     */ import me.connorm.Nodus.module.modules.Xray;
/*    9:     */ import me.connorm.Nodus.module.modules.utils.XrayUtils;
/*   10:     */ import net.minecraft.block.material.MapColor;
/*   11:     */ import net.minecraft.block.material.Material;
/*   12:     */ import net.minecraft.client.renderer.texture.IIconRegister;
/*   13:     */ import net.minecraft.creativetab.CreativeTabs;
/*   14:     */ import net.minecraft.enchantment.EnchantmentHelper;
/*   15:     */ import net.minecraft.entity.Entity;
/*   16:     */ import net.minecraft.entity.EntityLivingBase;
/*   17:     */ import net.minecraft.entity.item.EntityItem;
/*   18:     */ import net.minecraft.entity.item.EntityXPOrb;
/*   19:     */ import net.minecraft.entity.player.EntityPlayer;
/*   20:     */ import net.minecraft.item.Item;
/*   21:     */ import net.minecraft.item.ItemStack;
/*   22:     */ import net.minecraft.tileentity.TileEntitySign;
/*   23:     */ import net.minecraft.util.AABBPool;
/*   24:     */ import net.minecraft.util.AxisAlignedBB;
/*   25:     */ import net.minecraft.util.IIcon;
/*   26:     */ import net.minecraft.util.MovingObjectPosition;
/*   27:     */ import net.minecraft.util.RegistryNamespaced;
/*   28:     */ import net.minecraft.util.RegistryNamespacedDefaultedByKey;
/*   29:     */ import net.minecraft.util.StatCollector;
/*   30:     */ import net.minecraft.util.Vec3;
/*   31:     */ import net.minecraft.world.Explosion;
/*   32:     */ import net.minecraft.world.GameRules;
/*   33:     */ import net.minecraft.world.IBlockAccess;
/*   34:     */ import net.minecraft.world.World;
/*   35:     */ 
/*   36:     */ public class Block
/*   37:     */ {
/*   38:  39 */   public static final RegistryNamespaced blockRegistry = new RegistryNamespacedDefaultedByKey("air");
/*   39:     */   private CreativeTabs displayOnCreativeTab;
/*   40:     */   protected String textureName;
/*   41:  42 */   public static final SoundType soundTypeStone = new SoundType("stone", 1.0F, 1.0F);
/*   42:  45 */   public static final SoundType soundTypeWood = new SoundType("wood", 1.0F, 1.0F);
/*   43:  48 */   public static final SoundType soundTypeGravel = new SoundType("gravel", 1.0F, 1.0F);
/*   44:  49 */   public static final SoundType soundTypeGrass = new SoundType("grass", 1.0F, 1.0F);
/*   45:  52 */   public static final SoundType soundTypePiston = new SoundType("stone", 1.0F, 1.0F);
/*   46:  55 */   public static final SoundType soundTypeMetal = new SoundType("stone", 1.0F, 1.5F);
/*   47:  58 */   public static final SoundType soundTypeGlass = new SoundType("stone", 1.0F, 1.0F)
/*   48:     */   {
/*   49:     */     private static final String __OBFID = "CL_00000200";
/*   50:     */     
/*   51:     */     public String func_150495_a()
/*   52:     */     {
/*   53:  63 */       return "dig.glass";
/*   54:     */     }
/*   55:     */     
/*   56:     */     public String func_150496_b()
/*   57:     */     {
/*   58:  67 */       return "step.stone";
/*   59:     */     }
/*   60:     */   };
/*   61:  72 */   public static final SoundType soundTypeCloth = new SoundType("cloth", 1.0F, 1.0F);
/*   62:  73 */   public static final SoundType field_149776_m = new SoundType("sand", 1.0F, 1.0F);
/*   63:  74 */   public static final SoundType soundTypeSnow = new SoundType("snow", 1.0F, 1.0F);
/*   64:  77 */   public static final SoundType soundTypeLadder = new SoundType("ladder", 1.0F, 1.0F)
/*   65:     */   {
/*   66:     */     private static final String __OBFID = "CL_00000201";
/*   67:     */     
/*   68:     */     public String func_150495_a()
/*   69:     */     {
/*   70:  82 */       return "dig.wood";
/*   71:     */     }
/*   72:     */   };
/*   73:  87 */   public static final SoundType soundTypeAnvil = new SoundType("anvil", 0.3F, 1.0F)
/*   74:     */   {
/*   75:     */     private static final String __OBFID = "CL_00000202";
/*   76:     */     
/*   77:     */     public String func_150495_a()
/*   78:     */     {
/*   79:  92 */       return "dig.stone";
/*   80:     */     }
/*   81:     */     
/*   82:     */     public String func_150496_b()
/*   83:     */     {
/*   84:  96 */       return "random.anvil_land";
/*   85:     */     }
/*   86:     */   };
/*   87:     */   protected boolean opaque;
/*   88:     */   protected int lightOpacity;
/*   89:     */   protected boolean canBlockGrass;
/*   90:     */   public static int lightValue;
/*   91:     */   protected boolean field_149783_u;
/*   92:     */   protected float blockHardness;
/*   93:     */   protected float blockResistance;
/*   94: 112 */   protected boolean field_149791_x = true;
/*   95: 113 */   protected boolean enableStats = true;
/*   96:     */   protected boolean needsRandomTick;
/*   97:     */   protected boolean isBlockContainer;
/*   98:     */   protected double field_149759_B;
/*   99:     */   protected double field_149760_C;
/*  100:     */   protected double field_149754_D;
/*  101:     */   protected double field_149755_E;
/*  102:     */   protected double field_149756_F;
/*  103:     */   protected double field_149757_G;
/*  104:     */   public SoundType stepSound;
/*  105:     */   public float blockParticleGravity;
/*  106:     */   protected final Material blockMaterial;
/*  107:     */   public float slipperiness;
/*  108:     */   private String unlocalizedNameBlock;
/*  109:     */   protected IIcon blockIcon;
/*  110:     */   private static final String __OBFID = "CL_00000199";
/*  111:     */   
/*  112:     */   public static int getIdFromBlock(Block p_149682_0_)
/*  113:     */   {
/*  114: 145 */     return blockRegistry.getIDForObject(p_149682_0_);
/*  115:     */   }
/*  116:     */   
/*  117:     */   public static Block getBlockById(int p_149729_0_)
/*  118:     */   {
/*  119: 150 */     return (Block)blockRegistry.getObjectForID(p_149729_0_);
/*  120:     */   }
/*  121:     */   
/*  122:     */   public static Block getBlockFromItem(Item p_149634_0_)
/*  123:     */   {
/*  124: 155 */     return getBlockById(Item.getIdFromItem(p_149634_0_));
/*  125:     */   }
/*  126:     */   
/*  127:     */   public static Block getBlockFromName(String p_149684_0_)
/*  128:     */   {
/*  129: 160 */     if (blockRegistry.containsKey(p_149684_0_)) {
/*  130: 162 */       return (Block)blockRegistry.getObject(p_149684_0_);
/*  131:     */     }
/*  132:     */     try
/*  133:     */     {
/*  134: 168 */       return (Block)blockRegistry.getObjectForID(Integer.parseInt(p_149684_0_));
/*  135:     */     }
/*  136:     */     catch (NumberFormatException var2) {}
/*  137: 172 */     return null;
/*  138:     */   }
/*  139:     */   
/*  140:     */   public boolean func_149730_j()
/*  141:     */   {
/*  142: 179 */     return this.opaque;
/*  143:     */   }
/*  144:     */   
/*  145:     */   public int getLightOpacity()
/*  146:     */   {
/*  147: 184 */     return this.lightOpacity;
/*  148:     */   }
/*  149:     */   
/*  150:     */   public boolean getCanBlockGrass()
/*  151:     */   {
/*  152: 189 */     return this.canBlockGrass;
/*  153:     */   }
/*  154:     */   
/*  155:     */   public int getLightValue()
/*  156:     */   {
/*  157: 194 */     return lightValue;
/*  158:     */   }
/*  159:     */   
/*  160:     */   public boolean func_149710_n()
/*  161:     */   {
/*  162: 199 */     return this.field_149783_u;
/*  163:     */   }
/*  164:     */   
/*  165:     */   public Material getMaterial()
/*  166:     */   {
/*  167: 204 */     return this.blockMaterial;
/*  168:     */   }
/*  169:     */   
/*  170:     */   public MapColor getMapColor(int p_149728_1_)
/*  171:     */   {
/*  172: 209 */     return getMaterial().getMaterialMapColor();
/*  173:     */   }
/*  174:     */   
/*  175:     */   public static void registerBlocks()
/*  176:     */   {
/*  177: 214 */     blockRegistry.addObject(0, "air", new BlockAir().setBlockName("air"));
/*  178: 215 */     blockRegistry.addObject(1, "stone", new BlockStone().setHardness(1.5F).setResistance(10.0F).setStepSound(soundTypePiston).setBlockName("stone").setBlockTextureName("stone"));
/*  179: 216 */     blockRegistry.addObject(2, "grass", new BlockGrass().setHardness(0.6F).setStepSound(soundTypeGrass).setBlockName("grass").setBlockTextureName("grass"));
/*  180: 217 */     blockRegistry.addObject(3, "dirt", new BlockDirt().setHardness(0.5F).setStepSound(soundTypeGravel).setBlockName("dirt").setBlockTextureName("dirt"));
/*  181: 218 */     Block var0 = new Block(Material.rock).setHardness(2.0F).setResistance(10.0F).setStepSound(soundTypePiston).setBlockName("stonebrick").setCreativeTab(CreativeTabs.tabBlock).setBlockTextureName("cobblestone");
/*  182: 219 */     blockRegistry.addObject(4, "cobblestone", var0);
/*  183: 220 */     Block var1 = new BlockWood().setHardness(2.0F).setResistance(5.0F).setStepSound(soundTypeWood).setBlockName("wood").setBlockTextureName("planks");
/*  184: 221 */     blockRegistry.addObject(5, "planks", var1);
/*  185: 222 */     blockRegistry.addObject(6, "sapling", new BlockSapling().setHardness(0.0F).setStepSound(soundTypeGrass).setBlockName("sapling").setBlockTextureName("sapling"));
/*  186: 223 */     blockRegistry.addObject(7, "bedrock", new Block(Material.rock).setBlockUnbreakable().setResistance(6000000.0F).setStepSound(soundTypePiston).setBlockName("bedrock").disableStats().setCreativeTab(CreativeTabs.tabBlock).setBlockTextureName("bedrock"));
/*  187: 224 */     blockRegistry.addObject(8, "flowing_water", new BlockDynamicLiquid(Material.water).setHardness(100.0F).setLightOpacity(3).setBlockName("water").disableStats().setBlockTextureName("water_flow"));
/*  188: 225 */     blockRegistry.addObject(9, "water", new BlockStaticLiquid(Material.water).setHardness(100.0F).setLightOpacity(3).setBlockName("water").disableStats().setBlockTextureName("water_still"));
/*  189: 226 */     blockRegistry.addObject(10, "flowing_lava", new BlockDynamicLiquid(Material.lava).setHardness(100.0F).setLightLevel(1.0F).setBlockName("lava").disableStats().setBlockTextureName("lava_flow"));
/*  190: 227 */     blockRegistry.addObject(11, "lava", new BlockStaticLiquid(Material.lava).setHardness(100.0F).setLightLevel(1.0F).setBlockName("lava").disableStats().setBlockTextureName("lava_still"));
/*  191: 228 */     blockRegistry.addObject(12, "sand", new BlockSand().setHardness(0.5F).setStepSound(field_149776_m).setBlockName("sand").setBlockTextureName("sand"));
/*  192: 229 */     blockRegistry.addObject(13, "gravel", new BlockGravel().setHardness(0.6F).setStepSound(soundTypeGravel).setBlockName("gravel").setBlockTextureName("gravel"));
/*  193: 230 */     blockRegistry.addObject(14, "gold_ore", new BlockOre().setHardness(3.0F).setResistance(5.0F).setStepSound(soundTypePiston).setBlockName("oreGold").setBlockTextureName("gold_ore"));
/*  194: 231 */     blockRegistry.addObject(15, "iron_ore", new BlockOre().setHardness(3.0F).setResistance(5.0F).setStepSound(soundTypePiston).setBlockName("oreIron").setBlockTextureName("iron_ore"));
/*  195: 232 */     blockRegistry.addObject(16, "coal_ore", new BlockOre().setHardness(3.0F).setResistance(5.0F).setStepSound(soundTypePiston).setBlockName("oreCoal").setBlockTextureName("coal_ore"));
/*  196: 233 */     blockRegistry.addObject(17, "log", new BlockOldLog().setBlockName("log").setBlockTextureName("log"));
/*  197: 234 */     blockRegistry.addObject(18, "leaves", new BlockOldLeaf().setBlockName("leaves").setBlockTextureName("leaves"));
/*  198: 235 */     blockRegistry.addObject(19, "sponge", new BlockSponge().setHardness(0.6F).setStepSound(soundTypeGrass).setBlockName("sponge").setBlockTextureName("sponge"));
/*  199: 236 */     blockRegistry.addObject(20, "glass", new BlockGlass(Material.glass, false).setHardness(0.3F).setStepSound(soundTypeGlass).setBlockName("glass").setBlockTextureName("glass"));
/*  200: 237 */     blockRegistry.addObject(21, "lapis_ore", new BlockOre().setHardness(3.0F).setResistance(5.0F).setStepSound(soundTypePiston).setBlockName("oreLapis").setBlockTextureName("lapis_ore"));
/*  201: 238 */     blockRegistry.addObject(22, "lapis_block", new BlockCompressed(MapColor.field_151652_H).setHardness(3.0F).setResistance(5.0F).setStepSound(soundTypePiston).setBlockName("blockLapis").setCreativeTab(CreativeTabs.tabBlock).setBlockTextureName("lapis_block"));
/*  202: 239 */     blockRegistry.addObject(23, "dispenser", new BlockDispenser().setHardness(3.5F).setStepSound(soundTypePiston).setBlockName("dispenser").setBlockTextureName("dispenser"));
/*  203: 240 */     Block var2 = new BlockSandStone().setStepSound(soundTypePiston).setHardness(0.8F).setBlockName("sandStone").setBlockTextureName("sandstone");
/*  204: 241 */     blockRegistry.addObject(24, "sandstone", var2);
/*  205: 242 */     blockRegistry.addObject(25, "noteblock", new BlockNote().setHardness(0.8F).setBlockName("musicBlock").setBlockTextureName("noteblock"));
/*  206: 243 */     blockRegistry.addObject(26, "bed", new BlockBed().setHardness(0.2F).setBlockName("bed").disableStats().setBlockTextureName("bed"));
/*  207: 244 */     blockRegistry.addObject(27, "golden_rail", new BlockRailPowered().setHardness(0.7F).setStepSound(soundTypeMetal).setBlockName("goldenRail").setBlockTextureName("rail_golden"));
/*  208: 245 */     blockRegistry.addObject(28, "detector_rail", new BlockRailDetector().setHardness(0.7F).setStepSound(soundTypeMetal).setBlockName("detectorRail").setBlockTextureName("rail_detector"));
/*  209: 246 */     blockRegistry.addObject(29, "sticky_piston", new BlockPistonBase(true).setBlockName("pistonStickyBase"));
/*  210: 247 */     blockRegistry.addObject(30, "web", new BlockWeb().setLightOpacity(1).setHardness(4.0F).setBlockName("web").setBlockTextureName("web"));
/*  211: 248 */     blockRegistry.addObject(31, "tallgrass", new BlockTallGrass().setHardness(0.0F).setStepSound(soundTypeGrass).setBlockName("tallgrass"));
/*  212: 249 */     blockRegistry.addObject(32, "deadbush", new BlockDeadBush().setHardness(0.0F).setStepSound(soundTypeGrass).setBlockName("deadbush").setBlockTextureName("deadbush"));
/*  213: 250 */     blockRegistry.addObject(33, "piston", new BlockPistonBase(false).setBlockName("pistonBase"));
/*  214: 251 */     blockRegistry.addObject(34, "piston_head", new BlockPistonExtension());
/*  215: 252 */     blockRegistry.addObject(35, "wool", new BlockColored(Material.cloth).setHardness(0.8F).setStepSound(soundTypeCloth).setBlockName("cloth").setBlockTextureName("wool_colored"));
/*  216: 253 */     blockRegistry.addObject(36, "piston_extension", new BlockPistonMoving());
/*  217: 254 */     blockRegistry.addObject(37, "yellow_flower", new BlockFlower(0).setHardness(0.0F).setStepSound(soundTypeGrass).setBlockName("flower1").setBlockTextureName("flower_dandelion"));
/*  218: 255 */     blockRegistry.addObject(38, "red_flower", new BlockFlower(1).setHardness(0.0F).setStepSound(soundTypeGrass).setBlockName("flower2").setBlockTextureName("flower_rose"));
/*  219: 256 */     blockRegistry.addObject(39, "brown_mushroom", new BlockMushroom().setHardness(0.0F).setStepSound(soundTypeGrass).setLightLevel(0.125F).setBlockName("mushroom").setBlockTextureName("mushroom_brown"));
/*  220: 257 */     blockRegistry.addObject(40, "red_mushroom", new BlockMushroom().setHardness(0.0F).setStepSound(soundTypeGrass).setBlockName("mushroom").setBlockTextureName("mushroom_red"));
/*  221: 258 */     blockRegistry.addObject(41, "gold_block", new BlockCompressed(MapColor.field_151647_F).setHardness(3.0F).setResistance(10.0F).setStepSound(soundTypeMetal).setBlockName("blockGold").setBlockTextureName("gold_block"));
/*  222: 259 */     blockRegistry.addObject(42, "iron_block", new BlockCompressed(MapColor.field_151668_h).setHardness(5.0F).setResistance(10.0F).setStepSound(soundTypeMetal).setBlockName("blockIron").setBlockTextureName("iron_block"));
/*  223: 260 */     blockRegistry.addObject(43, "double_stone_slab", new BlockStoneSlab(true).setHardness(2.0F).setResistance(10.0F).setStepSound(soundTypePiston).setBlockName("stoneSlab"));
/*  224: 261 */     blockRegistry.addObject(44, "stone_slab", new BlockStoneSlab(false).setHardness(2.0F).setResistance(10.0F).setStepSound(soundTypePiston).setBlockName("stoneSlab"));
/*  225: 262 */     Block var3 = new Block(Material.rock).setHardness(2.0F).setResistance(10.0F).setStepSound(soundTypePiston).setBlockName("brick").setCreativeTab(CreativeTabs.tabBlock).setBlockTextureName("brick");
/*  226: 263 */     blockRegistry.addObject(45, "brick_block", var3);
/*  227: 264 */     blockRegistry.addObject(46, "tnt", new BlockTNT().setHardness(0.0F).setStepSound(soundTypeGrass).setBlockName("tnt").setBlockTextureName("tnt"));
/*  228: 265 */     blockRegistry.addObject(47, "bookshelf", new BlockBookshelf().setHardness(1.5F).setStepSound(soundTypeWood).setBlockName("bookshelf").setBlockTextureName("bookshelf"));
/*  229: 266 */     blockRegistry.addObject(48, "mossy_cobblestone", new Block(Material.rock).setHardness(2.0F).setResistance(10.0F).setStepSound(soundTypePiston).setBlockName("stoneMoss").setCreativeTab(CreativeTabs.tabBlock).setBlockTextureName("cobblestone_mossy"));
/*  230: 267 */     blockRegistry.addObject(49, "obsidian", new BlockObsidian().setHardness(50.0F).setResistance(2000.0F).setStepSound(soundTypePiston).setBlockName("obsidian").setBlockTextureName("obsidian"));
/*  231: 268 */     blockRegistry.addObject(50, "torch", new BlockTorch().setHardness(0.0F).setLightLevel(0.9375F).setStepSound(soundTypeWood).setBlockName("torch").setBlockTextureName("torch_on"));
/*  232: 269 */     blockRegistry.addObject(51, "fire", new BlockFire().setHardness(0.0F).setLightLevel(1.0F).setStepSound(soundTypeWood).setBlockName("fire").disableStats().setBlockTextureName("fire"));
/*  233: 270 */     blockRegistry.addObject(52, "mob_spawner", new BlockMobSpawner().setHardness(5.0F).setStepSound(soundTypeMetal).setBlockName("mobSpawner").disableStats().setBlockTextureName("mob_spawner"));
/*  234: 271 */     blockRegistry.addObject(53, "oak_stairs", new BlockStairs(var1, 0).setBlockName("stairsWood"));
/*  235: 272 */     blockRegistry.addObject(54, "chest", new BlockChest(0).setHardness(2.5F).setStepSound(soundTypeWood).setBlockName("chest"));
/*  236: 273 */     blockRegistry.addObject(55, "redstone_wire", new BlockRedstoneWire().setHardness(0.0F).setStepSound(soundTypeStone).setBlockName("redstoneDust").disableStats().setBlockTextureName("redstone_dust"));
/*  237: 274 */     blockRegistry.addObject(56, "diamond_ore", new BlockOre().setHardness(3.0F).setResistance(5.0F).setStepSound(soundTypePiston).setBlockName("oreDiamond").setBlockTextureName("diamond_ore"));
/*  238: 275 */     blockRegistry.addObject(57, "diamond_block", new BlockCompressed(MapColor.field_151648_G).setHardness(5.0F).setResistance(10.0F).setStepSound(soundTypeMetal).setBlockName("blockDiamond").setBlockTextureName("diamond_block"));
/*  239: 276 */     blockRegistry.addObject(58, "crafting_table", new BlockWorkbench().setHardness(2.5F).setStepSound(soundTypeWood).setBlockName("workbench").setBlockTextureName("crafting_table"));
/*  240: 277 */     blockRegistry.addObject(59, "wheat", new BlockCrops().setBlockName("crops").setBlockTextureName("wheat"));
/*  241: 278 */     Block var4 = new BlockFarmland().setHardness(0.6F).setStepSound(soundTypeGravel).setBlockName("farmland").setBlockTextureName("farmland");
/*  242: 279 */     blockRegistry.addObject(60, "farmland", var4);
/*  243: 280 */     blockRegistry.addObject(61, "furnace", new BlockFurnace(false).setHardness(3.5F).setStepSound(soundTypePiston).setBlockName("furnace").setCreativeTab(CreativeTabs.tabDecorations));
/*  244: 281 */     blockRegistry.addObject(62, "lit_furnace", new BlockFurnace(true).setHardness(3.5F).setStepSound(soundTypePiston).setLightLevel(0.875F).setBlockName("furnace"));
/*  245: 282 */     blockRegistry.addObject(63, "standing_sign", new BlockSign(TileEntitySign.class, true).setHardness(1.0F).setStepSound(soundTypeWood).setBlockName("sign").disableStats());
/*  246: 283 */     blockRegistry.addObject(64, "wooden_door", new BlockDoor(Material.wood).setHardness(3.0F).setStepSound(soundTypeWood).setBlockName("doorWood").disableStats().setBlockTextureName("door_wood"));
/*  247: 284 */     blockRegistry.addObject(65, "ladder", new BlockLadder().setHardness(0.4F).setStepSound(soundTypeLadder).setBlockName("ladder").setBlockTextureName("ladder"));
/*  248: 285 */     blockRegistry.addObject(66, "rail", new BlockRail().setHardness(0.7F).setStepSound(soundTypeMetal).setBlockName("rail").setBlockTextureName("rail_normal"));
/*  249: 286 */     blockRegistry.addObject(67, "stone_stairs", new BlockStairs(var0, 0).setBlockName("stairsStone"));
/*  250: 287 */     blockRegistry.addObject(68, "wall_sign", new BlockSign(TileEntitySign.class, false).setHardness(1.0F).setStepSound(soundTypeWood).setBlockName("sign").disableStats());
/*  251: 288 */     blockRegistry.addObject(69, "lever", new BlockLever().setHardness(0.5F).setStepSound(soundTypeWood).setBlockName("lever").setBlockTextureName("lever"));
/*  252: 289 */     blockRegistry.addObject(70, "stone_pressure_plate", new BlockPressurePlate("stone", Material.rock, BlockPressurePlate.Sensitivity.mobs).setHardness(0.5F).setStepSound(soundTypePiston).setBlockName("pressurePlate"));
/*  253: 290 */     blockRegistry.addObject(71, "iron_door", new BlockDoor(Material.iron).setHardness(5.0F).setStepSound(soundTypeMetal).setBlockName("doorIron").disableStats().setBlockTextureName("door_iron"));
/*  254: 291 */     blockRegistry.addObject(72, "wooden_pressure_plate", new BlockPressurePlate("planks_oak", Material.wood, BlockPressurePlate.Sensitivity.everything).setHardness(0.5F).setStepSound(soundTypeWood).setBlockName("pressurePlate"));
/*  255: 292 */     blockRegistry.addObject(73, "redstone_ore", new BlockRedstoneOre(false).setHardness(3.0F).setResistance(5.0F).setStepSound(soundTypePiston).setBlockName("oreRedstone").setCreativeTab(CreativeTabs.tabBlock).setBlockTextureName("redstone_ore"));
/*  256: 293 */     blockRegistry.addObject(74, "lit_redstone_ore", new BlockRedstoneOre(true).setLightLevel(0.625F).setHardness(3.0F).setResistance(5.0F).setStepSound(soundTypePiston).setBlockName("oreRedstone").setBlockTextureName("redstone_ore"));
/*  257: 294 */     blockRegistry.addObject(75, "unlit_redstone_torch", new BlockRedstoneTorch(false).setHardness(0.0F).setStepSound(soundTypeWood).setBlockName("notGate").setBlockTextureName("redstone_torch_off"));
/*  258: 295 */     blockRegistry.addObject(76, "redstone_torch", new BlockRedstoneTorch(true).setHardness(0.0F).setLightLevel(0.5F).setStepSound(soundTypeWood).setBlockName("notGate").setCreativeTab(CreativeTabs.tabRedstone).setBlockTextureName("redstone_torch_on"));
/*  259: 296 */     blockRegistry.addObject(77, "stone_button", new BlockButtonStone().setHardness(0.5F).setStepSound(soundTypePiston).setBlockName("button"));
/*  260: 297 */     blockRegistry.addObject(78, "snow_layer", new BlockSnow().setHardness(0.1F).setStepSound(soundTypeSnow).setBlockName("snow").setLightOpacity(0).setBlockTextureName("snow"));
/*  261: 298 */     blockRegistry.addObject(79, "ice", new BlockIce().setHardness(0.5F).setLightOpacity(3).setStepSound(soundTypeGlass).setBlockName("ice").setBlockTextureName("ice"));
/*  262: 299 */     blockRegistry.addObject(80, "snow", new BlockSnowBlock().setHardness(0.2F).setStepSound(soundTypeSnow).setBlockName("snow").setBlockTextureName("snow"));
/*  263: 300 */     blockRegistry.addObject(81, "cactus", new BlockCactus().setHardness(0.4F).setStepSound(soundTypeCloth).setBlockName("cactus").setBlockTextureName("cactus"));
/*  264: 301 */     blockRegistry.addObject(82, "clay", new BlockClay().setHardness(0.6F).setStepSound(soundTypeGravel).setBlockName("clay").setBlockTextureName("clay"));
/*  265: 302 */     blockRegistry.addObject(83, "reeds", new BlockReed().setHardness(0.0F).setStepSound(soundTypeGrass).setBlockName("reeds").disableStats().setBlockTextureName("reeds"));
/*  266: 303 */     blockRegistry.addObject(84, "jukebox", new BlockJukebox().setHardness(2.0F).setResistance(10.0F).setStepSound(soundTypePiston).setBlockName("jukebox").setBlockTextureName("jukebox"));
/*  267: 304 */     blockRegistry.addObject(85, "fence", new BlockFence("planks_oak", Material.wood).setHardness(2.0F).setResistance(5.0F).setStepSound(soundTypeWood).setBlockName("fence"));
/*  268: 305 */     Block var5 = new BlockPumpkin(false).setHardness(1.0F).setStepSound(soundTypeWood).setBlockName("pumpkin").setBlockTextureName("pumpkin");
/*  269: 306 */     blockRegistry.addObject(86, "pumpkin", var5);
/*  270: 307 */     blockRegistry.addObject(87, "netherrack", new BlockNetherrack().setHardness(0.4F).setStepSound(soundTypePiston).setBlockName("hellrock").setBlockTextureName("netherrack"));
/*  271: 308 */     blockRegistry.addObject(88, "soul_sand", new BlockSoulSand().setHardness(0.5F).setStepSound(field_149776_m).setBlockName("hellsand").setBlockTextureName("soul_sand"));
/*  272: 309 */     blockRegistry.addObject(89, "glowstone", new BlockGlowstone(Material.glass).setHardness(0.3F).setStepSound(soundTypeGlass).setLightLevel(1.0F).setBlockName("lightgem").setBlockTextureName("glowstone"));
/*  273: 310 */     blockRegistry.addObject(90, "portal", new BlockPortal().setHardness(-1.0F).setStepSound(soundTypeGlass).setLightLevel(0.75F).setBlockName("portal").setBlockTextureName("portal"));
/*  274: 311 */     blockRegistry.addObject(91, "lit_pumpkin", new BlockPumpkin(true).setHardness(1.0F).setStepSound(soundTypeWood).setLightLevel(1.0F).setBlockName("litpumpkin").setBlockTextureName("pumpkin"));
/*  275: 312 */     blockRegistry.addObject(92, "cake", new BlockCake().setHardness(0.5F).setStepSound(soundTypeCloth).setBlockName("cake").disableStats().setBlockTextureName("cake"));
/*  276: 313 */     blockRegistry.addObject(93, "unpowered_repeater", new BlockRedstoneRepeater(false).setHardness(0.0F).setStepSound(soundTypeWood).setBlockName("diode").disableStats().setBlockTextureName("repeater_off"));
/*  277: 314 */     blockRegistry.addObject(94, "powered_repeater", new BlockRedstoneRepeater(true).setHardness(0.0F).setLightLevel(0.625F).setStepSound(soundTypeWood).setBlockName("diode").disableStats().setBlockTextureName("repeater_on"));
/*  278: 315 */     blockRegistry.addObject(95, "stained_glass", new BlockStainedGlass(Material.glass).setHardness(0.3F).setStepSound(soundTypeGlass).setBlockName("stainedGlass").setBlockTextureName("glass"));
/*  279: 316 */     blockRegistry.addObject(96, "trapdoor", new BlockTrapDoor(Material.wood).setHardness(3.0F).setStepSound(soundTypeWood).setBlockName("trapdoor").disableStats().setBlockTextureName("trapdoor"));
/*  280: 317 */     blockRegistry.addObject(97, "monster_egg", new BlockSilverfish().setHardness(0.75F).setBlockName("monsterStoneEgg"));
/*  281: 318 */     Block var6 = new BlockStoneBrick().setHardness(1.5F).setResistance(10.0F).setStepSound(soundTypePiston).setBlockName("stonebricksmooth").setBlockTextureName("stonebrick");
/*  282: 319 */     blockRegistry.addObject(98, "stonebrick", var6);
/*  283: 320 */     blockRegistry.addObject(99, "brown_mushroom_block", new BlockHugeMushroom(Material.wood, 0).setHardness(0.2F).setStepSound(soundTypeWood).setBlockName("mushroom").setBlockTextureName("mushroom_block"));
/*  284: 321 */     blockRegistry.addObject(100, "red_mushroom_block", new BlockHugeMushroom(Material.wood, 1).setHardness(0.2F).setStepSound(soundTypeWood).setBlockName("mushroom").setBlockTextureName("mushroom_block"));
/*  285: 322 */     blockRegistry.addObject(101, "iron_bars", new BlockPane("iron_bars", "iron_bars", Material.iron, true).setHardness(5.0F).setResistance(10.0F).setStepSound(soundTypeMetal).setBlockName("fenceIron"));
/*  286: 323 */     blockRegistry.addObject(102, "glass_pane", new BlockPane("glass", "glass_pane_top", Material.glass, false).setHardness(0.3F).setStepSound(soundTypeGlass).setBlockName("thinGlass"));
/*  287: 324 */     Block var7 = new BlockMelon().setHardness(1.0F).setStepSound(soundTypeWood).setBlockName("melon").setBlockTextureName("melon");
/*  288: 325 */     blockRegistry.addObject(103, "melon_block", var7);
/*  289: 326 */     blockRegistry.addObject(104, "pumpkin_stem", new BlockStem(var5).setHardness(0.0F).setStepSound(soundTypeWood).setBlockName("pumpkinStem").setBlockTextureName("pumpkin_stem"));
/*  290: 327 */     blockRegistry.addObject(105, "melon_stem", new BlockStem(var7).setHardness(0.0F).setStepSound(soundTypeWood).setBlockName("pumpkinStem").setBlockTextureName("melon_stem"));
/*  291: 328 */     blockRegistry.addObject(106, "vine", new BlockVine().setHardness(0.2F).setStepSound(soundTypeGrass).setBlockName("vine").setBlockTextureName("vine"));
/*  292: 329 */     blockRegistry.addObject(107, "fence_gate", new BlockFenceGate().setHardness(2.0F).setResistance(5.0F).setStepSound(soundTypeWood).setBlockName("fenceGate"));
/*  293: 330 */     blockRegistry.addObject(108, "brick_stairs", new BlockStairs(var3, 0).setBlockName("stairsBrick"));
/*  294: 331 */     blockRegistry.addObject(109, "stone_brick_stairs", new BlockStairs(var6, 0).setBlockName("stairsStoneBrickSmooth"));
/*  295: 332 */     blockRegistry.addObject(110, "mycelium", new BlockMycelium().setHardness(0.6F).setStepSound(soundTypeGrass).setBlockName("mycel").setBlockTextureName("mycelium"));
/*  296: 333 */     blockRegistry.addObject(111, "waterlily", new BlockLilyPad().setHardness(0.0F).setStepSound(soundTypeGrass).setBlockName("waterlily").setBlockTextureName("waterlily"));
/*  297: 334 */     Block var8 = new Block(Material.rock).setHardness(2.0F).setResistance(10.0F).setStepSound(soundTypePiston).setBlockName("netherBrick").setCreativeTab(CreativeTabs.tabBlock).setBlockTextureName("nether_brick");
/*  298: 335 */     blockRegistry.addObject(112, "nether_brick", var8);
/*  299: 336 */     blockRegistry.addObject(113, "nether_brick_fence", new BlockFence("nether_brick", Material.rock).setHardness(2.0F).setResistance(10.0F).setStepSound(soundTypePiston).setBlockName("netherFence"));
/*  300: 337 */     blockRegistry.addObject(114, "nether_brick_stairs", new BlockStairs(var8, 0).setBlockName("stairsNetherBrick"));
/*  301: 338 */     blockRegistry.addObject(115, "nether_wart", new BlockNetherWart().setBlockName("netherStalk").setBlockTextureName("nether_wart"));
/*  302: 339 */     blockRegistry.addObject(116, "enchanting_table", new BlockEnchantmentTable().setHardness(5.0F).setResistance(2000.0F).setBlockName("enchantmentTable").setBlockTextureName("enchanting_table"));
/*  303: 340 */     blockRegistry.addObject(117, "brewing_stand", new BlockBrewingStand().setHardness(0.5F).setLightLevel(0.125F).setBlockName("brewingStand").setBlockTextureName("brewing_stand"));
/*  304: 341 */     blockRegistry.addObject(118, "cauldron", new BlockCauldron().setHardness(2.0F).setBlockName("cauldron").setBlockTextureName("cauldron"));
/*  305: 342 */     blockRegistry.addObject(119, "end_portal", new BlockEndPortal(Material.Portal).setHardness(-1.0F).setResistance(6000000.0F));
/*  306: 343 */     blockRegistry.addObject(120, "end_portal_frame", new BlockEndPortalFrame().setStepSound(soundTypeGlass).setLightLevel(0.125F).setHardness(-1.0F).setBlockName("endPortalFrame").setResistance(6000000.0F).setCreativeTab(CreativeTabs.tabDecorations).setBlockTextureName("endframe"));
/*  307: 344 */     blockRegistry.addObject(121, "end_stone", new Block(Material.rock).setHardness(3.0F).setResistance(15.0F).setStepSound(soundTypePiston).setBlockName("whiteStone").setCreativeTab(CreativeTabs.tabBlock).setBlockTextureName("end_stone"));
/*  308: 345 */     blockRegistry.addObject(122, "dragon_egg", new BlockDragonEgg().setHardness(3.0F).setResistance(15.0F).setStepSound(soundTypePiston).setLightLevel(0.125F).setBlockName("dragonEgg").setBlockTextureName("dragon_egg"));
/*  309: 346 */     blockRegistry.addObject(123, "redstone_lamp", new BlockRedstoneLight(false).setHardness(0.3F).setStepSound(soundTypeGlass).setBlockName("redstoneLight").setCreativeTab(CreativeTabs.tabRedstone).setBlockTextureName("redstone_lamp_off"));
/*  310: 347 */     blockRegistry.addObject(124, "lit_redstone_lamp", new BlockRedstoneLight(true).setHardness(0.3F).setStepSound(soundTypeGlass).setBlockName("redstoneLight").setBlockTextureName("redstone_lamp_on"));
/*  311: 348 */     blockRegistry.addObject(125, "double_wooden_slab", new BlockWoodSlab(true).setHardness(2.0F).setResistance(5.0F).setStepSound(soundTypeWood).setBlockName("woodSlab"));
/*  312: 349 */     blockRegistry.addObject(126, "wooden_slab", new BlockWoodSlab(false).setHardness(2.0F).setResistance(5.0F).setStepSound(soundTypeWood).setBlockName("woodSlab"));
/*  313: 350 */     blockRegistry.addObject(127, "cocoa", new BlockCocoa().setHardness(0.2F).setResistance(5.0F).setStepSound(soundTypeWood).setBlockName("cocoa").setBlockTextureName("cocoa"));
/*  314: 351 */     blockRegistry.addObject(128, "sandstone_stairs", new BlockStairs(var2, 0).setBlockName("stairsSandStone"));
/*  315: 352 */     blockRegistry.addObject(129, "emerald_ore", new BlockOre().setHardness(3.0F).setResistance(5.0F).setStepSound(soundTypePiston).setBlockName("oreEmerald").setBlockTextureName("emerald_ore"));
/*  316: 353 */     blockRegistry.addObject(130, "ender_chest", new BlockEnderChest().setHardness(22.5F).setResistance(1000.0F).setStepSound(soundTypePiston).setBlockName("enderChest").setLightLevel(0.5F));
/*  317: 354 */     blockRegistry.addObject(131, "tripwire_hook", new BlockTripWireHook().setBlockName("tripWireSource").setBlockTextureName("trip_wire_source"));
/*  318: 355 */     blockRegistry.addObject(132, "tripwire", new BlockTripWire().setBlockName("tripWire").setBlockTextureName("trip_wire"));
/*  319: 356 */     blockRegistry.addObject(133, "emerald_block", new BlockCompressed(MapColor.field_151653_I).setHardness(5.0F).setResistance(10.0F).setStepSound(soundTypeMetal).setBlockName("blockEmerald").setBlockTextureName("emerald_block"));
/*  320: 357 */     blockRegistry.addObject(134, "spruce_stairs", new BlockStairs(var1, 1).setBlockName("stairsWoodSpruce"));
/*  321: 358 */     blockRegistry.addObject(135, "birch_stairs", new BlockStairs(var1, 2).setBlockName("stairsWoodBirch"));
/*  322: 359 */     blockRegistry.addObject(136, "jungle_stairs", new BlockStairs(var1, 3).setBlockName("stairsWoodJungle"));
/*  323: 360 */     blockRegistry.addObject(137, "command_block", new BlockCommandBlock().setBlockUnbreakable().setResistance(6000000.0F).setBlockName("commandBlock").setBlockTextureName("command_block"));
/*  324: 361 */     blockRegistry.addObject(138, "beacon", new BlockBeacon().setBlockName("beacon").setLightLevel(1.0F).setBlockTextureName("beacon"));
/*  325: 362 */     blockRegistry.addObject(139, "cobblestone_wall", new BlockWall(var0).setBlockName("cobbleWall"));
/*  326: 363 */     blockRegistry.addObject(140, "flower_pot", new BlockFlowerPot().setHardness(0.0F).setStepSound(soundTypeStone).setBlockName("flowerPot").setBlockTextureName("flower_pot"));
/*  327: 364 */     blockRegistry.addObject(141, "carrots", new BlockCarrot().setBlockName("carrots").setBlockTextureName("carrots"));
/*  328: 365 */     blockRegistry.addObject(142, "potatoes", new BlockPotato().setBlockName("potatoes").setBlockTextureName("potatoes"));
/*  329: 366 */     blockRegistry.addObject(143, "wooden_button", new BlockButtonWood().setHardness(0.5F).setStepSound(soundTypeWood).setBlockName("button"));
/*  330: 367 */     blockRegistry.addObject(144, "skull", new BlockSkull().setHardness(1.0F).setStepSound(soundTypePiston).setBlockName("skull").setBlockTextureName("skull"));
/*  331: 368 */     blockRegistry.addObject(145, "anvil", new BlockAnvil().setHardness(5.0F).setStepSound(soundTypeAnvil).setResistance(2000.0F).setBlockName("anvil"));
/*  332: 369 */     blockRegistry.addObject(146, "trapped_chest", new BlockChest(1).setHardness(2.5F).setStepSound(soundTypeWood).setBlockName("chestTrap"));
/*  333: 370 */     blockRegistry.addObject(147, "light_weighted_pressure_plate", new BlockPressurePlateWeighted("gold_block", Material.iron, 15).setHardness(0.5F).setStepSound(soundTypeWood).setBlockName("weightedPlate_light"));
/*  334: 371 */     blockRegistry.addObject(148, "heavy_weighted_pressure_plate", new BlockPressurePlateWeighted("iron_block", Material.iron, 150).setHardness(0.5F).setStepSound(soundTypeWood).setBlockName("weightedPlate_heavy"));
/*  335: 372 */     blockRegistry.addObject(149, "unpowered_comparator", new BlockRedstoneComparator(false).setHardness(0.0F).setStepSound(soundTypeWood).setBlockName("comparator").disableStats().setBlockTextureName("comparator_off"));
/*  336: 373 */     blockRegistry.addObject(150, "powered_comparator", new BlockRedstoneComparator(true).setHardness(0.0F).setLightLevel(0.625F).setStepSound(soundTypeWood).setBlockName("comparator").disableStats().setBlockTextureName("comparator_on"));
/*  337: 374 */     blockRegistry.addObject(151, "daylight_detector", new BlockDaylightDetector().setHardness(0.2F).setStepSound(soundTypeWood).setBlockName("daylightDetector").setBlockTextureName("daylight_detector"));
/*  338: 375 */     blockRegistry.addObject(152, "redstone_block", new BlockCompressedPowered(MapColor.field_151656_f).setHardness(5.0F).setResistance(10.0F).setStepSound(soundTypeMetal).setBlockName("blockRedstone").setBlockTextureName("redstone_block"));
/*  339: 376 */     blockRegistry.addObject(153, "quartz_ore", new BlockOre().setHardness(3.0F).setResistance(5.0F).setStepSound(soundTypePiston).setBlockName("netherquartz").setBlockTextureName("quartz_ore"));
/*  340: 377 */     blockRegistry.addObject(154, "hopper", new BlockHopper().setHardness(3.0F).setResistance(8.0F).setStepSound(soundTypeWood).setBlockName("hopper").setBlockTextureName("hopper"));
/*  341: 378 */     Block var9 = new BlockQuartz().setStepSound(soundTypePiston).setHardness(0.8F).setBlockName("quartzBlock").setBlockTextureName("quartz_block");
/*  342: 379 */     blockRegistry.addObject(155, "quartz_block", var9);
/*  343: 380 */     blockRegistry.addObject(156, "quartz_stairs", new BlockStairs(var9, 0).setBlockName("stairsQuartz"));
/*  344: 381 */     blockRegistry.addObject(157, "activator_rail", new BlockRailPowered().setHardness(0.7F).setStepSound(soundTypeMetal).setBlockName("activatorRail").setBlockTextureName("rail_activator"));
/*  345: 382 */     blockRegistry.addObject(158, "dropper", new BlockDropper().setHardness(3.5F).setStepSound(soundTypePiston).setBlockName("dropper").setBlockTextureName("dropper"));
/*  346: 383 */     blockRegistry.addObject(159, "stained_hardened_clay", new BlockColored(Material.rock).setHardness(1.25F).setResistance(7.0F).setStepSound(soundTypePiston).setBlockName("clayHardenedStained").setBlockTextureName("hardened_clay_stained"));
/*  347: 384 */     blockRegistry.addObject(160, "stained_glass_pane", new BlockStainedGlassPane().setHardness(0.3F).setStepSound(soundTypeGlass).setBlockName("thinStainedGlass").setBlockTextureName("glass"));
/*  348: 385 */     blockRegistry.addObject(161, "leaves2", new BlockNewLeaf().setBlockName("leaves").setBlockTextureName("leaves"));
/*  349: 386 */     blockRegistry.addObject(162, "log2", new BlockNewLog().setBlockName("log").setBlockTextureName("log"));
/*  350: 387 */     blockRegistry.addObject(163, "acacia_stairs", new BlockStairs(var1, 4).setBlockName("stairsWoodAcacia"));
/*  351: 388 */     blockRegistry.addObject(164, "dark_oak_stairs", new BlockStairs(var1, 5).setBlockName("stairsWoodDarkOak"));
/*  352: 389 */     blockRegistry.addObject(170, "hay_block", new BlockHay().setHardness(0.5F).setStepSound(soundTypeGrass).setBlockName("hayBlock").setCreativeTab(CreativeTabs.tabBlock).setBlockTextureName("hay_block"));
/*  353: 390 */     blockRegistry.addObject(171, "carpet", new BlockCarpet().setHardness(0.1F).setStepSound(soundTypeCloth).setBlockName("woolCarpet").setLightOpacity(0));
/*  354: 391 */     blockRegistry.addObject(172, "hardened_clay", new BlockHardenedClay().setHardness(1.25F).setResistance(7.0F).setStepSound(soundTypePiston).setBlockName("clayHardened").setBlockTextureName("hardened_clay"));
/*  355: 392 */     blockRegistry.addObject(173, "coal_block", new Block(Material.rock).setHardness(5.0F).setResistance(10.0F).setStepSound(soundTypePiston).setBlockName("blockCoal").setCreativeTab(CreativeTabs.tabBlock).setBlockTextureName("coal_block"));
/*  356: 393 */     blockRegistry.addObject(174, "packed_ice", new BlockPackedIce().setHardness(0.5F).setStepSound(soundTypeGlass).setBlockName("icePacked").setBlockTextureName("ice_packed"));
/*  357: 394 */     blockRegistry.addObject(175, "double_plant", new BlockDoublePlant());
/*  358: 395 */     Iterator var10 = blockRegistry.iterator();
/*  359: 397 */     while (var10.hasNext())
/*  360:     */     {
/*  361: 399 */       Block var11 = (Block)var10.next();
/*  362: 401 */       if (var11.blockMaterial == Material.air)
/*  363:     */       {
/*  364: 403 */         var11.field_149783_u = false;
/*  365:     */       }
/*  366:     */       else
/*  367:     */       {
/*  368: 407 */         boolean var12 = false;
/*  369: 408 */         boolean var13 = var11.getRenderType() == 10;
/*  370: 409 */         boolean var14 = var11 instanceof BlockSlab;
/*  371: 410 */         boolean var15 = var11 == var4;
/*  372: 411 */         boolean var16 = var11.canBlockGrass;
/*  373: 412 */         boolean var17 = var11.lightOpacity == 0;
/*  374: 414 */         if ((var13) || (var14) || (var15) || (var16) || (var17)) {
/*  375: 416 */           var12 = true;
/*  376:     */         }
/*  377: 419 */         var11.field_149783_u = var12;
/*  378:     */       }
/*  379:     */     }
/*  380:     */   }
/*  381:     */   
/*  382:     */   protected Block(Material p_i45394_1_)
/*  383:     */   {
/*  384: 426 */     this.stepSound = soundTypeStone;
/*  385: 427 */     this.blockParticleGravity = 1.0F;
/*  386: 428 */     this.slipperiness = 0.6F;
/*  387: 429 */     this.blockMaterial = p_i45394_1_;
/*  388: 430 */     setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*  389: 431 */     this.opaque = isOpaqueCube();
/*  390: 432 */     this.lightOpacity = (isOpaqueCube() ? 255 : 0);
/*  391: 433 */     this.canBlockGrass = (!p_i45394_1_.getCanBlockGrass());
/*  392:     */   }
/*  393:     */   
/*  394:     */   protected Block setStepSound(SoundType p_149672_1_)
/*  395:     */   {
/*  396: 441 */     this.stepSound = p_149672_1_;
/*  397: 442 */     return this;
/*  398:     */   }
/*  399:     */   
/*  400:     */   public Block setLightOpacity(int p_149713_1_)
/*  401:     */   {
/*  402: 450 */     this.lightOpacity = p_149713_1_;
/*  403: 451 */     return this;
/*  404:     */   }
/*  405:     */   
/*  406:     */   protected Block setLightLevel(float p_149715_1_)
/*  407:     */   {
/*  408: 460 */     lightValue = (int)(15.0F * p_149715_1_);
/*  409: 461 */     return this;
/*  410:     */   }
/*  411:     */   
/*  412:     */   protected Block setResistance(float p_149752_1_)
/*  413:     */   {
/*  414: 469 */     this.blockResistance = (p_149752_1_ * 3.0F);
/*  415: 470 */     return this;
/*  416:     */   }
/*  417:     */   
/*  418:     */   public boolean isBlockNormalCube()
/*  419:     */   {
/*  420: 478 */     return (this.blockMaterial.blocksMovement()) && (renderAsNormalBlock());
/*  421:     */   }
/*  422:     */   
/*  423:     */   public boolean isNormalCube()
/*  424:     */   {
/*  425: 483 */     return (this.blockMaterial.isOpaque()) && (renderAsNormalBlock()) && (!canProvidePower());
/*  426:     */   }
/*  427:     */   
/*  428:     */   public boolean renderAsNormalBlock()
/*  429:     */   {
/*  430: 489 */     return !XrayUtils.xrayEnabled;
/*  431:     */   }
/*  432:     */   
/*  433:     */   public boolean getBlocksMovement(IBlockAccess p_149655_1_, int p_149655_2_, int p_149655_3_, int p_149655_4_)
/*  434:     */   {
/*  435: 494 */     return !this.blockMaterial.blocksMovement();
/*  436:     */   }
/*  437:     */   
/*  438:     */   public int getRenderType()
/*  439:     */   {
/*  440: 502 */     return 0;
/*  441:     */   }
/*  442:     */   
/*  443:     */   protected Block setHardness(float p_149711_1_)
/*  444:     */   {
/*  445: 510 */     this.blockHardness = p_149711_1_;
/*  446: 512 */     if (this.blockResistance < p_149711_1_ * 5.0F) {
/*  447: 514 */       this.blockResistance = (p_149711_1_ * 5.0F);
/*  448:     */     }
/*  449: 517 */     return this;
/*  450:     */   }
/*  451:     */   
/*  452:     */   protected Block setBlockUnbreakable()
/*  453:     */   {
/*  454: 522 */     setHardness(-1.0F);
/*  455: 523 */     return this;
/*  456:     */   }
/*  457:     */   
/*  458:     */   public float getBlockHardness(World p_149712_1_, int p_149712_2_, int p_149712_3_, int p_149712_4_)
/*  459:     */   {
/*  460: 528 */     return this.blockHardness;
/*  461:     */   }
/*  462:     */   
/*  463:     */   protected Block setTickRandomly(boolean p_149675_1_)
/*  464:     */   {
/*  465: 536 */     this.needsRandomTick = p_149675_1_;
/*  466: 537 */     return this;
/*  467:     */   }
/*  468:     */   
/*  469:     */   public boolean getTickRandomly()
/*  470:     */   {
/*  471: 546 */     return this.needsRandomTick;
/*  472:     */   }
/*  473:     */   
/*  474:     */   public boolean hasTileEntity()
/*  475:     */   {
/*  476: 551 */     return this.isBlockContainer;
/*  477:     */   }
/*  478:     */   
/*  479:     */   protected final void setBlockBounds(float p_149676_1_, float p_149676_2_, float p_149676_3_, float p_149676_4_, float p_149676_5_, float p_149676_6_)
/*  480:     */   {
/*  481: 556 */     this.field_149759_B = p_149676_1_;
/*  482: 557 */     this.field_149760_C = p_149676_2_;
/*  483: 558 */     this.field_149754_D = p_149676_3_;
/*  484: 559 */     this.field_149755_E = p_149676_4_;
/*  485: 560 */     this.field_149756_F = p_149676_5_;
/*  486: 561 */     this.field_149757_G = p_149676_6_;
/*  487:     */   }
/*  488:     */   
/*  489:     */   public int getBlockBrightness(IBlockAccess p_149677_1_, int p_149677_2_, int p_149677_3_, int p_149677_4_)
/*  490:     */   {
/*  491: 566 */     Block var5 = p_149677_1_.getBlock(p_149677_2_, p_149677_3_, p_149677_4_);
/*  492: 567 */     int var6 = p_149677_1_.getLightBrightnessForSkyBlocks(p_149677_2_, p_149677_3_, p_149677_4_, var5.getLightValue());
/*  493: 569 */     if ((var6 == 0) && ((var5 instanceof BlockSlab)))
/*  494:     */     {
/*  495: 571 */       p_149677_3_--;
/*  496: 572 */       var5 = p_149677_1_.getBlock(p_149677_2_, p_149677_3_, p_149677_4_);
/*  497: 573 */       return p_149677_1_.getLightBrightnessForSkyBlocks(p_149677_2_, p_149677_3_, p_149677_4_, var5.getLightValue());
/*  498:     */     }
/*  499: 577 */     return var6;
/*  500:     */   }
/*  501:     */   
/*  502:     */   public boolean shouldSideBeRendered(IBlockAccess p_149646_1_, int p_149646_2_, int p_149646_3_, int p_149646_4_, int p_149646_5_)
/*  503:     */   {
/*  504: 584 */     if (Nodus.theNodus.moduleManager.xrayModule.isXrayBlock(getLocalizedName())) {
/*  505: 585 */       return true;
/*  506:     */     }
/*  507: 586 */     if (XrayUtils.xrayEnabled) {
/*  508: 587 */       return false;
/*  509:     */     }
/*  510: 588 */     return (p_149646_5_ == 0) && (this.field_149760_C > 0.0D);
/*  511:     */   }
/*  512:     */   
/*  513:     */   public boolean isBlockSolid(IBlockAccess p_149747_1_, int p_149747_2_, int p_149747_3_, int p_149747_4_, int p_149747_5_)
/*  514:     */   {
/*  515: 593 */     return p_149747_1_.getBlock(p_149747_2_, p_149747_3_, p_149747_4_).getMaterial().isSolid();
/*  516:     */   }
/*  517:     */   
/*  518:     */   public IIcon getIcon(IBlockAccess p_149673_1_, int p_149673_2_, int p_149673_3_, int p_149673_4_, int p_149673_5_)
/*  519:     */   {
/*  520: 598 */     return getIcon(p_149673_5_, p_149673_1_.getBlockMetadata(p_149673_2_, p_149673_3_, p_149673_4_));
/*  521:     */   }
/*  522:     */   
/*  523:     */   public IIcon getIcon(int p_149691_1_, int p_149691_2_)
/*  524:     */   {
/*  525: 606 */     return this.blockIcon;
/*  526:     */   }
/*  527:     */   
/*  528:     */   public final IIcon getBlockTextureFromSide(int p_149733_1_)
/*  529:     */   {
/*  530: 614 */     return getIcon(p_149733_1_, 0);
/*  531:     */   }
/*  532:     */   
/*  533:     */   public AxisAlignedBB getSelectedBoundingBoxFromPool(World p_149633_1_, int p_149633_2_, int p_149633_3_, int p_149633_4_)
/*  534:     */   {
/*  535: 622 */     return AxisAlignedBB.getAABBPool().getAABB(p_149633_2_ + this.field_149759_B, p_149633_3_ + this.field_149760_C, p_149633_4_ + this.field_149754_D, p_149633_2_ + this.field_149755_E, p_149633_3_ + this.field_149756_F, p_149633_4_ + this.field_149757_G);
/*  536:     */   }
/*  537:     */   
/*  538:     */   public void addCollisionBoxesToList(World p_149743_1_, int p_149743_2_, int p_149743_3_, int p_149743_4_, AxisAlignedBB p_149743_5_, List p_149743_6_, Entity p_149743_7_)
/*  539:     */   {
/*  540: 627 */     AxisAlignedBB var8 = getCollisionBoundingBoxFromPool(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_);
/*  541: 629 */     if ((var8 != null) && (p_149743_5_.intersectsWith(var8))) {
/*  542: 631 */       p_149743_6_.add(var8);
/*  543:     */     }
/*  544:     */   }
/*  545:     */   
/*  546:     */   public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_149668_1_, int p_149668_2_, int p_149668_3_, int p_149668_4_)
/*  547:     */   {
/*  548: 641 */     return AxisAlignedBB.getAABBPool().getAABB(p_149668_2_ + this.field_149759_B, p_149668_3_ + this.field_149760_C, p_149668_4_ + this.field_149754_D, p_149668_2_ + this.field_149755_E, p_149668_3_ + this.field_149756_F, p_149668_4_ + this.field_149757_G);
/*  549:     */   }
/*  550:     */   
/*  551:     */   public boolean isOpaqueCube()
/*  552:     */   {
/*  553: 646 */     return true;
/*  554:     */   }
/*  555:     */   
/*  556:     */   public boolean canCollideCheck(int p_149678_1_, boolean p_149678_2_)
/*  557:     */   {
/*  558: 655 */     return isCollidable();
/*  559:     */   }
/*  560:     */   
/*  561:     */   public boolean isCollidable()
/*  562:     */   {
/*  563: 660 */     return true;
/*  564:     */   }
/*  565:     */   
/*  566:     */   public void updateTick(World p_149674_1_, int p_149674_2_, int p_149674_3_, int p_149674_4_, Random p_149674_5_) {}
/*  567:     */   
/*  568:     */   public void randomDisplayTick(World p_149734_1_, int p_149734_2_, int p_149734_3_, int p_149734_4_, Random p_149734_5_) {}
/*  569:     */   
/*  570:     */   public void onBlockDestroyedByPlayer(World p_149664_1_, int p_149664_2_, int p_149664_3_, int p_149664_4_, int p_149664_5_) {}
/*  571:     */   
/*  572:     */   public void onNeighborBlockChange(World p_149695_1_, int p_149695_2_, int p_149695_3_, int p_149695_4_, Block p_149695_5_) {}
/*  573:     */   
/*  574:     */   public int func_149738_a(World p_149738_1_)
/*  575:     */   {
/*  576: 679 */     return 10;
/*  577:     */   }
/*  578:     */   
/*  579:     */   public void onBlockAdded(World p_149726_1_, int p_149726_2_, int p_149726_3_, int p_149726_4_) {}
/*  580:     */   
/*  581:     */   public void breakBlock(World p_149749_1_, int p_149749_2_, int p_149749_3_, int p_149749_4_, Block p_149749_5_, int p_149749_6_) {}
/*  582:     */   
/*  583:     */   public int quantityDropped(Random p_149745_1_)
/*  584:     */   {
/*  585: 691 */     return 1;
/*  586:     */   }
/*  587:     */   
/*  588:     */   public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_)
/*  589:     */   {
/*  590: 696 */     return Item.getItemFromBlock(this);
/*  591:     */   }
/*  592:     */   
/*  593:     */   public float getPlayerRelativeBlockHardness(EntityPlayer p_149737_1_, World p_149737_2_, int p_149737_3_, int p_149737_4_, int p_149737_5_)
/*  594:     */   {
/*  595: 701 */     float var6 = getBlockHardness(p_149737_2_, p_149737_3_, p_149737_4_, p_149737_5_);
/*  596: 702 */     return !p_149737_1_.canHarvestBlock(this) ? p_149737_1_.getCurrentPlayerStrVsBlock(this, false) / var6 / 100.0F : var6 < 0.0F ? 0.0F : p_149737_1_.getCurrentPlayerStrVsBlock(this, true) / var6 / 30.0F;
/*  597:     */   }
/*  598:     */   
/*  599:     */   public final void dropBlockAsItem(World p_149697_1_, int p_149697_2_, int p_149697_3_, int p_149697_4_, int p_149697_5_, int p_149697_6_)
/*  600:     */   {
/*  601: 710 */     dropBlockAsItemWithChance(p_149697_1_, p_149697_2_, p_149697_3_, p_149697_4_, p_149697_5_, 1.0F, p_149697_6_);
/*  602:     */   }
/*  603:     */   
/*  604:     */   public void dropBlockAsItemWithChance(World p_149690_1_, int p_149690_2_, int p_149690_3_, int p_149690_4_, int p_149690_5_, float p_149690_6_, int p_149690_7_)
/*  605:     */   {
/*  606: 718 */     if (!p_149690_1_.isClient)
/*  607:     */     {
/*  608: 720 */       int var8 = quantityDroppedWithBonus(p_149690_7_, p_149690_1_.rand);
/*  609: 722 */       for (int var9 = 0; var9 < var8; var9++) {
/*  610: 724 */         if (p_149690_1_.rand.nextFloat() <= p_149690_6_)
/*  611:     */         {
/*  612: 726 */           Item var10 = getItemDropped(p_149690_5_, p_149690_1_.rand, p_149690_7_);
/*  613: 728 */           if (var10 != null) {
/*  614: 730 */             dropBlockAsItem_do(p_149690_1_, p_149690_2_, p_149690_3_, p_149690_4_, new ItemStack(var10, 1, damageDropped(p_149690_5_)));
/*  615:     */           }
/*  616:     */         }
/*  617:     */       }
/*  618:     */     }
/*  619:     */   }
/*  620:     */   
/*  621:     */   protected void dropBlockAsItem_do(World p_149642_1_, int p_149642_2_, int p_149642_3_, int p_149642_4_, ItemStack p_149642_5_)
/*  622:     */   {
/*  623: 742 */     if ((!p_149642_1_.isClient) && (p_149642_1_.getGameRules().getGameRuleBooleanValue("doTileDrops")))
/*  624:     */     {
/*  625: 744 */       float var6 = 0.7F;
/*  626: 745 */       double var7 = p_149642_1_.rand.nextFloat() * var6 + (1.0F - var6) * 0.5D;
/*  627: 746 */       double var9 = p_149642_1_.rand.nextFloat() * var6 + (1.0F - var6) * 0.5D;
/*  628: 747 */       double var11 = p_149642_1_.rand.nextFloat() * var6 + (1.0F - var6) * 0.5D;
/*  629: 748 */       EntityItem var13 = new EntityItem(p_149642_1_, p_149642_2_ + var7, p_149642_3_ + var9, p_149642_4_ + var11, p_149642_5_);
/*  630: 749 */       var13.delayBeforeCanPickup = 10;
/*  631: 750 */       p_149642_1_.spawnEntityInWorld(var13);
/*  632:     */     }
/*  633:     */   }
/*  634:     */   
/*  635:     */   protected void dropXpOnBlockBreak(World p_149657_1_, int p_149657_2_, int p_149657_3_, int p_149657_4_, int p_149657_5_)
/*  636:     */   {
/*  637: 756 */     if (!p_149657_1_.isClient) {
/*  638: 758 */       while (p_149657_5_ > 0)
/*  639:     */       {
/*  640: 760 */         int var6 = EntityXPOrb.getXPSplit(p_149657_5_);
/*  641: 761 */         p_149657_5_ -= var6;
/*  642: 762 */         p_149657_1_.spawnEntityInWorld(new EntityXPOrb(p_149657_1_, p_149657_2_ + 0.5D, p_149657_3_ + 0.5D, p_149657_4_ + 0.5D, var6));
/*  643:     */       }
/*  644:     */     }
/*  645:     */   }
/*  646:     */   
/*  647:     */   public int damageDropped(int p_149692_1_)
/*  648:     */   {
/*  649: 772 */     return 0;
/*  650:     */   }
/*  651:     */   
/*  652:     */   public float getExplosionResistance(Entity p_149638_1_)
/*  653:     */   {
/*  654: 780 */     return this.blockResistance / 5.0F;
/*  655:     */   }
/*  656:     */   
/*  657:     */   public MovingObjectPosition collisionRayTrace(World p_149731_1_, int p_149731_2_, int p_149731_3_, int p_149731_4_, Vec3 p_149731_5_, Vec3 p_149731_6_)
/*  658:     */   {
/*  659: 785 */     setBlockBoundsBasedOnState(p_149731_1_, p_149731_2_, p_149731_3_, p_149731_4_);
/*  660: 786 */     p_149731_5_ = p_149731_5_.addVector(-p_149731_2_, -p_149731_3_, -p_149731_4_);
/*  661: 787 */     p_149731_6_ = p_149731_6_.addVector(-p_149731_2_, -p_149731_3_, -p_149731_4_);
/*  662: 788 */     Vec3 var7 = p_149731_5_.getIntermediateWithXValue(p_149731_6_, this.field_149759_B);
/*  663: 789 */     Vec3 var8 = p_149731_5_.getIntermediateWithXValue(p_149731_6_, this.field_149755_E);
/*  664: 790 */     Vec3 var9 = p_149731_5_.getIntermediateWithYValue(p_149731_6_, this.field_149760_C);
/*  665: 791 */     Vec3 var10 = p_149731_5_.getIntermediateWithYValue(p_149731_6_, this.field_149756_F);
/*  666: 792 */     Vec3 var11 = p_149731_5_.getIntermediateWithZValue(p_149731_6_, this.field_149754_D);
/*  667: 793 */     Vec3 var12 = p_149731_5_.getIntermediateWithZValue(p_149731_6_, this.field_149757_G);
/*  668: 795 */     if (!isVecInsideYZBounds(var7)) {
/*  669: 797 */       var7 = null;
/*  670:     */     }
/*  671: 800 */     if (!isVecInsideYZBounds(var8)) {
/*  672: 802 */       var8 = null;
/*  673:     */     }
/*  674: 805 */     if (!isVecInsideXZBounds(var9)) {
/*  675: 807 */       var9 = null;
/*  676:     */     }
/*  677: 810 */     if (!isVecInsideXZBounds(var10)) {
/*  678: 812 */       var10 = null;
/*  679:     */     }
/*  680: 815 */     if (!isVecInsideXYBounds(var11)) {
/*  681: 817 */       var11 = null;
/*  682:     */     }
/*  683: 820 */     if (!isVecInsideXYBounds(var12)) {
/*  684: 822 */       var12 = null;
/*  685:     */     }
/*  686: 825 */     Vec3 var13 = null;
/*  687: 827 */     if ((var7 != null) && ((var13 == null) || (p_149731_5_.squareDistanceTo(var7) < p_149731_5_.squareDistanceTo(var13)))) {
/*  688: 829 */       var13 = var7;
/*  689:     */     }
/*  690: 832 */     if ((var8 != null) && ((var13 == null) || (p_149731_5_.squareDistanceTo(var8) < p_149731_5_.squareDistanceTo(var13)))) {
/*  691: 834 */       var13 = var8;
/*  692:     */     }
/*  693: 837 */     if ((var9 != null) && ((var13 == null) || (p_149731_5_.squareDistanceTo(var9) < p_149731_5_.squareDistanceTo(var13)))) {
/*  694: 839 */       var13 = var9;
/*  695:     */     }
/*  696: 842 */     if ((var10 != null) && ((var13 == null) || (p_149731_5_.squareDistanceTo(var10) < p_149731_5_.squareDistanceTo(var13)))) {
/*  697: 844 */       var13 = var10;
/*  698:     */     }
/*  699: 847 */     if ((var11 != null) && ((var13 == null) || (p_149731_5_.squareDistanceTo(var11) < p_149731_5_.squareDistanceTo(var13)))) {
/*  700: 849 */       var13 = var11;
/*  701:     */     }
/*  702: 852 */     if ((var12 != null) && ((var13 == null) || (p_149731_5_.squareDistanceTo(var12) < p_149731_5_.squareDistanceTo(var13)))) {
/*  703: 854 */       var13 = var12;
/*  704:     */     }
/*  705: 857 */     if (var13 == null) {
/*  706: 859 */       return null;
/*  707:     */     }
/*  708: 863 */     byte var14 = -1;
/*  709: 865 */     if (var13 == var7) {
/*  710: 867 */       var14 = 4;
/*  711:     */     }
/*  712: 870 */     if (var13 == var8) {
/*  713: 872 */       var14 = 5;
/*  714:     */     }
/*  715: 875 */     if (var13 == var9) {
/*  716: 877 */       var14 = 0;
/*  717:     */     }
/*  718: 880 */     if (var13 == var10) {
/*  719: 882 */       var14 = 1;
/*  720:     */     }
/*  721: 885 */     if (var13 == var11) {
/*  722: 887 */       var14 = 2;
/*  723:     */     }
/*  724: 890 */     if (var13 == var12) {
/*  725: 892 */       var14 = 3;
/*  726:     */     }
/*  727: 895 */     return new MovingObjectPosition(p_149731_2_, p_149731_3_, p_149731_4_, var14, var13.addVector(p_149731_2_, p_149731_3_, p_149731_4_));
/*  728:     */   }
/*  729:     */   
/*  730:     */   private boolean isVecInsideYZBounds(Vec3 p_149654_1_)
/*  731:     */   {
/*  732: 904 */     return p_149654_1_ != null;
/*  733:     */   }
/*  734:     */   
/*  735:     */   private boolean isVecInsideXZBounds(Vec3 p_149687_1_)
/*  736:     */   {
/*  737: 912 */     return p_149687_1_ != null;
/*  738:     */   }
/*  739:     */   
/*  740:     */   private boolean isVecInsideXYBounds(Vec3 p_149661_1_)
/*  741:     */   {
/*  742: 920 */     return p_149661_1_ != null;
/*  743:     */   }
/*  744:     */   
/*  745:     */   public void onBlockDestroyedByExplosion(World p_149723_1_, int p_149723_2_, int p_149723_3_, int p_149723_4_, Explosion p_149723_5_) {}
/*  746:     */   
/*  747:     */   public int getRenderBlockPass()
/*  748:     */   {
/*  749: 933 */     return 0;
/*  750:     */   }
/*  751:     */   
/*  752:     */   public boolean canReplace(World p_149705_1_, int p_149705_2_, int p_149705_3_, int p_149705_4_, int p_149705_5_, ItemStack p_149705_6_)
/*  753:     */   {
/*  754: 938 */     return canPlaceBlockOnSide(p_149705_1_, p_149705_2_, p_149705_3_, p_149705_4_, p_149705_5_);
/*  755:     */   }
/*  756:     */   
/*  757:     */   public boolean canPlaceBlockOnSide(World p_149707_1_, int p_149707_2_, int p_149707_3_, int p_149707_4_, int p_149707_5_)
/*  758:     */   {
/*  759: 946 */     return canPlaceBlockAt(p_149707_1_, p_149707_2_, p_149707_3_, p_149707_4_);
/*  760:     */   }
/*  761:     */   
/*  762:     */   public boolean canPlaceBlockAt(World p_149742_1_, int p_149742_2_, int p_149742_3_, int p_149742_4_)
/*  763:     */   {
/*  764: 951 */     return p_149742_1_.getBlock(p_149742_2_, p_149742_3_, p_149742_4_).blockMaterial.isReplaceable();
/*  765:     */   }
/*  766:     */   
/*  767:     */   public boolean onBlockActivated(World p_149727_1_, int p_149727_2_, int p_149727_3_, int p_149727_4_, EntityPlayer p_149727_5_, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_)
/*  768:     */   {
/*  769: 959 */     return false;
/*  770:     */   }
/*  771:     */   
/*  772:     */   public void onEntityWalking(World p_149724_1_, int p_149724_2_, int p_149724_3_, int p_149724_4_, Entity p_149724_5_) {}
/*  773:     */   
/*  774:     */   public int onBlockPlaced(World p_149660_1_, int p_149660_2_, int p_149660_3_, int p_149660_4_, int p_149660_5_, float p_149660_6_, float p_149660_7_, float p_149660_8_, int p_149660_9_)
/*  775:     */   {
/*  776: 966 */     return p_149660_9_;
/*  777:     */   }
/*  778:     */   
/*  779:     */   public void onBlockClicked(World p_149699_1_, int p_149699_2_, int p_149699_3_, int p_149699_4_, EntityPlayer p_149699_5_) {}
/*  780:     */   
/*  781:     */   public void velocityToAddToEntity(World p_149640_1_, int p_149640_2_, int p_149640_3_, int p_149640_4_, Entity p_149640_5_, Vec3 p_149640_6_) {}
/*  782:     */   
/*  783:     */   public void setBlockBoundsBasedOnState(IBlockAccess p_149719_1_, int p_149719_2_, int p_149719_3_, int p_149719_4_) {}
/*  784:     */   
/*  785:     */   public final double getBlockBoundsMinX()
/*  786:     */   {
/*  787: 983 */     return this.field_149759_B;
/*  788:     */   }
/*  789:     */   
/*  790:     */   public final double getBlockBoundsMaxX()
/*  791:     */   {
/*  792: 991 */     return this.field_149755_E;
/*  793:     */   }
/*  794:     */   
/*  795:     */   public final double getBlockBoundsMinY()
/*  796:     */   {
/*  797: 999 */     return this.field_149760_C;
/*  798:     */   }
/*  799:     */   
/*  800:     */   public final double getBlockBoundsMaxY()
/*  801:     */   {
/*  802:1007 */     return this.field_149756_F;
/*  803:     */   }
/*  804:     */   
/*  805:     */   public final double getBlockBoundsMinZ()
/*  806:     */   {
/*  807:1015 */     return this.field_149754_D;
/*  808:     */   }
/*  809:     */   
/*  810:     */   public final double getBlockBoundsMaxZ()
/*  811:     */   {
/*  812:1023 */     return this.field_149757_G;
/*  813:     */   }
/*  814:     */   
/*  815:     */   public int getBlockColor()
/*  816:     */   {
/*  817:1028 */     return 16777215;
/*  818:     */   }
/*  819:     */   
/*  820:     */   public int getRenderColor(int p_149741_1_)
/*  821:     */   {
/*  822:1036 */     return 16777215;
/*  823:     */   }
/*  824:     */   
/*  825:     */   public int colorMultiplier(IBlockAccess p_149720_1_, int p_149720_2_, int p_149720_3_, int p_149720_4_)
/*  826:     */   {
/*  827:1045 */     return 16777215;
/*  828:     */   }
/*  829:     */   
/*  830:     */   public int isProvidingWeakPower(IBlockAccess p_149709_1_, int p_149709_2_, int p_149709_3_, int p_149709_4_, int p_149709_5_)
/*  831:     */   {
/*  832:1050 */     return 0;
/*  833:     */   }
/*  834:     */   
/*  835:     */   public boolean canProvidePower()
/*  836:     */   {
/*  837:1058 */     return false;
/*  838:     */   }
/*  839:     */   
/*  840:     */   public void onEntityCollidedWithBlock(World p_149670_1_, int p_149670_2_, int p_149670_3_, int p_149670_4_, Entity p_149670_5_) {}
/*  841:     */   
/*  842:     */   public int isProvidingStrongPower(IBlockAccess p_149748_1_, int p_149748_2_, int p_149748_3_, int p_149748_4_, int p_149748_5_)
/*  843:     */   {
/*  844:1065 */     return 0;
/*  845:     */   }
/*  846:     */   
/*  847:     */   public void setBlockBoundsForItemRender() {}
/*  848:     */   
/*  849:     */   public void harvestBlock(World p_149636_1_, EntityPlayer p_149636_2_, int p_149636_3_, int p_149636_4_, int p_149636_5_, int p_149636_6_)
/*  850:     */   {
/*  851:1075 */     p_149636_2_.addStat(net.minecraft.stats.StatList.mineBlockStatArray[getIdFromBlock(this)], 1);
/*  852:1076 */     p_149636_2_.addExhaustion(0.025F);
/*  853:1078 */     if ((canSilkHarvest()) && (EnchantmentHelper.getSilkTouchModifier(p_149636_2_)))
/*  854:     */     {
/*  855:1080 */       ItemStack var8 = createStackedBlock(p_149636_6_);
/*  856:1082 */       if (var8 != null) {
/*  857:1084 */         dropBlockAsItem_do(p_149636_1_, p_149636_3_, p_149636_4_, p_149636_5_, var8);
/*  858:     */       }
/*  859:     */     }
/*  860:     */     else
/*  861:     */     {
/*  862:1089 */       int var7 = EnchantmentHelper.getFortuneModifier(p_149636_2_);
/*  863:1090 */       dropBlockAsItem(p_149636_1_, p_149636_3_, p_149636_4_, p_149636_5_, p_149636_6_, var7);
/*  864:     */     }
/*  865:     */   }
/*  866:     */   
/*  867:     */   protected boolean canSilkHarvest()
/*  868:     */   {
/*  869:1096 */     return (renderAsNormalBlock()) && (!this.isBlockContainer);
/*  870:     */   }
/*  871:     */   
/*  872:     */   protected ItemStack createStackedBlock(int p_149644_1_)
/*  873:     */   {
/*  874:1105 */     int var2 = 0;
/*  875:1106 */     Item var3 = Item.getItemFromBlock(this);
/*  876:1108 */     if ((var3 != null) && (var3.getHasSubtypes())) {
/*  877:1110 */       var2 = p_149644_1_;
/*  878:     */     }
/*  879:1113 */     return new ItemStack(var3, 1, var2);
/*  880:     */   }
/*  881:     */   
/*  882:     */   public int quantityDroppedWithBonus(int p_149679_1_, Random p_149679_2_)
/*  883:     */   {
/*  884:1121 */     return quantityDropped(p_149679_2_);
/*  885:     */   }
/*  886:     */   
/*  887:     */   public boolean canBlockStay(World p_149718_1_, int p_149718_2_, int p_149718_3_, int p_149718_4_)
/*  888:     */   {
/*  889:1129 */     return true;
/*  890:     */   }
/*  891:     */   
/*  892:     */   public void onBlockPlacedBy(World p_149689_1_, int p_149689_2_, int p_149689_3_, int p_149689_4_, EntityLivingBase p_149689_5_, ItemStack p_149689_6_) {}
/*  893:     */   
/*  894:     */   public void onPostBlockPlaced(World p_149714_1_, int p_149714_2_, int p_149714_3_, int p_149714_4_, int p_149714_5_) {}
/*  895:     */   
/*  896:     */   public Block setBlockName(String p_149663_1_)
/*  897:     */   {
/*  898:1147 */     this.unlocalizedNameBlock = p_149663_1_;
/*  899:1148 */     return this;
/*  900:     */   }
/*  901:     */   
/*  902:     */   public String getLocalizedName()
/*  903:     */   {
/*  904:1156 */     return StatCollector.translateToLocal(getUnlocalizedName() + ".name");
/*  905:     */   }
/*  906:     */   
/*  907:     */   public String getUnlocalizedName()
/*  908:     */   {
/*  909:1164 */     return "tile." + this.unlocalizedNameBlock;
/*  910:     */   }
/*  911:     */   
/*  912:     */   public boolean onBlockEventReceived(World p_149696_1_, int p_149696_2_, int p_149696_3_, int p_149696_4_, int p_149696_5_, int p_149696_6_)
/*  913:     */   {
/*  914:1169 */     return false;
/*  915:     */   }
/*  916:     */   
/*  917:     */   public boolean getEnableStats()
/*  918:     */   {
/*  919:1177 */     return this.enableStats;
/*  920:     */   }
/*  921:     */   
/*  922:     */   protected Block disableStats()
/*  923:     */   {
/*  924:1182 */     this.enableStats = false;
/*  925:1183 */     return this;
/*  926:     */   }
/*  927:     */   
/*  928:     */   public int getMobilityFlag()
/*  929:     */   {
/*  930:1188 */     return this.blockMaterial.getMaterialMobility();
/*  931:     */   }
/*  932:     */   
/*  933:     */   public float getAmbientOcclusionLightValue()
/*  934:     */   {
/*  935:1196 */     return isBlockNormalCube() ? 0.2F : 1.0F;
/*  936:     */   }
/*  937:     */   
/*  938:     */   public void onFallenUpon(World p_149746_1_, int p_149746_2_, int p_149746_3_, int p_149746_4_, Entity p_149746_5_, float p_149746_6_) {}
/*  939:     */   
/*  940:     */   public Item getItem(World p_149694_1_, int p_149694_2_, int p_149694_3_, int p_149694_4_)
/*  941:     */   {
/*  942:1209 */     return Item.getItemFromBlock(this);
/*  943:     */   }
/*  944:     */   
/*  945:     */   public int getDamageValue(World p_149643_1_, int p_149643_2_, int p_149643_3_, int p_149643_4_)
/*  946:     */   {
/*  947:1217 */     return damageDropped(p_149643_1_.getBlockMetadata(p_149643_2_, p_149643_3_, p_149643_4_));
/*  948:     */   }
/*  949:     */   
/*  950:     */   public void getSubBlocks(Item p_149666_1_, CreativeTabs p_149666_2_, List p_149666_3_)
/*  951:     */   {
/*  952:1222 */     p_149666_3_.add(new ItemStack(p_149666_1_, 1, 0));
/*  953:     */   }
/*  954:     */   
/*  955:     */   public CreativeTabs getCreativeTabToDisplayOn()
/*  956:     */   {
/*  957:1230 */     return this.displayOnCreativeTab;
/*  958:     */   }
/*  959:     */   
/*  960:     */   public Block setCreativeTab(CreativeTabs p_149647_1_)
/*  961:     */   {
/*  962:1235 */     this.displayOnCreativeTab = p_149647_1_;
/*  963:1236 */     return this;
/*  964:     */   }
/*  965:     */   
/*  966:     */   public void onBlockHarvested(World p_149681_1_, int p_149681_2_, int p_149681_3_, int p_149681_4_, int p_149681_5_, EntityPlayer p_149681_6_) {}
/*  967:     */   
/*  968:     */   public void onBlockPreDestroy(World p_149725_1_, int p_149725_2_, int p_149725_3_, int p_149725_4_, int p_149725_5_) {}
/*  969:     */   
/*  970:     */   public void fillWithRain(World p_149639_1_, int p_149639_2_, int p_149639_3_, int p_149639_4_) {}
/*  971:     */   
/*  972:     */   public boolean isFlowerPot()
/*  973:     */   {
/*  974:1256 */     return false;
/*  975:     */   }
/*  976:     */   
/*  977:     */   public boolean func_149698_L()
/*  978:     */   {
/*  979:1261 */     return true;
/*  980:     */   }
/*  981:     */   
/*  982:     */   public boolean canDropFromExplosion(Explosion p_149659_1_)
/*  983:     */   {
/*  984:1269 */     return true;
/*  985:     */   }
/*  986:     */   
/*  987:     */   public boolean func_149667_c(Block p_149667_1_)
/*  988:     */   {
/*  989:1274 */     return this == p_149667_1_;
/*  990:     */   }
/*  991:     */   
/*  992:     */   public static boolean isEqualTo(Block p_149680_0_, Block p_149680_1_)
/*  993:     */   {
/*  994:1279 */     return p_149680_0_ == p_149680_1_;
/*  995:     */   }
/*  996:     */   
/*  997:     */   public boolean hasComparatorInputOverride()
/*  998:     */   {
/*  999:1284 */     return false;
/* 1000:     */   }
/* 1001:     */   
/* 1002:     */   public int getComparatorInputOverride(World p_149736_1_, int p_149736_2_, int p_149736_3_, int p_149736_4_, int p_149736_5_)
/* 1003:     */   {
/* 1004:1289 */     return 0;
/* 1005:     */   }
/* 1006:     */   
/* 1007:     */   protected Block setBlockTextureName(String p_149658_1_)
/* 1008:     */   {
/* 1009:1294 */     this.textureName = p_149658_1_;
/* 1010:1295 */     return this;
/* 1011:     */   }
/* 1012:     */   
/* 1013:     */   protected String getTextureName()
/* 1014:     */   {
/* 1015:1300 */     return this.textureName == null ? "MISSING_ICON_BLOCK_" + getIdFromBlock(this) + "_" + this.unlocalizedNameBlock : this.textureName;
/* 1016:     */   }
/* 1017:     */   
/* 1018:     */   public IIcon func_149735_b(int p_149735_1_, int p_149735_2_)
/* 1019:     */   {
/* 1020:1305 */     return getIcon(p_149735_1_, p_149735_2_);
/* 1021:     */   }
/* 1022:     */   
/* 1023:     */   public void registerBlockIcons(IIconRegister p_149651_1_)
/* 1024:     */   {
/* 1025:1310 */     this.blockIcon = p_149651_1_.registerIcon(getTextureName());
/* 1026:     */   }
/* 1027:     */   
/* 1028:     */   public String getItemIconName()
/* 1029:     */   {
/* 1030:1318 */     return null;
/* 1031:     */   }
/* 1032:     */   
/* 1033:     */   public static class SoundType
/* 1034:     */   {
/* 1035:     */     public final String field_150501_a;
/* 1036:     */     public final float field_150499_b;
/* 1037:     */     public final float field_150500_c;
/* 1038:     */     private static final String __OBFID = "CL_00000203";
/* 1039:     */     
/* 1040:     */     public SoundType(String p_i45393_1_, float p_i45393_2_, float p_i45393_3_)
/* 1041:     */     {
/* 1042:1330 */       this.field_150501_a = p_i45393_1_;
/* 1043:1331 */       this.field_150499_b = p_i45393_2_;
/* 1044:1332 */       this.field_150500_c = p_i45393_3_;
/* 1045:     */     }
/* 1046:     */     
/* 1047:     */     public float func_150497_c()
/* 1048:     */     {
/* 1049:1337 */       return this.field_150499_b;
/* 1050:     */     }
/* 1051:     */     
/* 1052:     */     public float func_150494_d()
/* 1053:     */     {
/* 1054:1342 */       return this.field_150500_c;
/* 1055:     */     }
/* 1056:     */     
/* 1057:     */     public String func_150495_a()
/* 1058:     */     {
/* 1059:1347 */       return "dig." + this.field_150501_a;
/* 1060:     */     }
/* 1061:     */     
/* 1062:     */     public String func_150498_e()
/* 1063:     */     {
/* 1064:1352 */       return "step." + this.field_150501_a;
/* 1065:     */     }
/* 1066:     */     
/* 1067:     */     public String func_150496_b()
/* 1068:     */     {
/* 1069:1357 */       return func_150495_a();
/* 1070:     */     }
/* 1071:     */   }
/* 1072:     */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.block.Block
 * JD-Core Version:    0.7.0.1
 */