package net.minecraft.block;

import cc.novoline.Novoline;
import cc.novoline.events.EventManager;
import cc.novoline.events.events.CollideWithBlockEvent;
import cc.novoline.modules.visual.XRay;
import com.google.common.collect.UnmodifiableIterator;
import java.util.List;
import java.util.Random;
import net.Vp;
import net.Vr;
import net.Vw;
import net.minecraft.block.Block$4;
import net.minecraft.block.Block$EnumOffsetType;
import net.minecraft.block.Block$SoundType;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockAnvil;
import net.minecraft.block.BlockBanner$BlockBannerHanging;
import net.minecraft.block.BlockBanner$BlockBannerStanding;
import net.minecraft.block.BlockBarrier;
import net.minecraft.block.BlockBeacon;
import net.minecraft.block.BlockBed;
import net.minecraft.block.BlockBookshelf;
import net.minecraft.block.BlockBrewingStand;
import net.minecraft.block.BlockButtonStone;
import net.minecraft.block.BlockButtonWood;
import net.minecraft.block.BlockCactus;
import net.minecraft.block.BlockCake;
import net.minecraft.block.BlockCarpet;
import net.minecraft.block.BlockCarrot;
import net.minecraft.block.BlockCauldron;
import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockClay;
import net.minecraft.block.BlockCocoa;
import net.minecraft.block.BlockColored;
import net.minecraft.block.BlockCommandBlock;
import net.minecraft.block.BlockCompressedPowered;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.BlockDaylightDetector;
import net.minecraft.block.BlockDeadBush;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockDispenser;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.block.BlockDoubleStoneSlab;
import net.minecraft.block.BlockDoubleStoneSlabNew;
import net.minecraft.block.BlockDoubleWoodSlab;
import net.minecraft.block.BlockDragonEgg;
import net.minecraft.block.BlockDropper;
import net.minecraft.block.BlockDynamicLiquid;
import net.minecraft.block.BlockEnchantmentTable;
import net.minecraft.block.BlockEndPortal;
import net.minecraft.block.BlockEndPortalFrame;
import net.minecraft.block.BlockEnderChest;
import net.minecraft.block.BlockFarmland;
import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockFire;
import net.minecraft.block.BlockFlowerPot;
import net.minecraft.block.BlockFurnace;
import net.minecraft.block.BlockGlass;
import net.minecraft.block.BlockGlowstone;
import net.minecraft.block.BlockGrass;
import net.minecraft.block.BlockGravel;
import net.minecraft.block.BlockHalfStoneSlab;
import net.minecraft.block.BlockHalfStoneSlabNew;
import net.minecraft.block.BlockHalfWoodSlab;
import net.minecraft.block.BlockHardenedClay;
import net.minecraft.block.BlockHay;
import net.minecraft.block.BlockHopper;
import net.minecraft.block.BlockHugeMushroom;
import net.minecraft.block.BlockIce;
import net.minecraft.block.BlockJukebox;
import net.minecraft.block.BlockLadder;
import net.minecraft.block.BlockLever;
import net.minecraft.block.BlockLilyPad;
import net.minecraft.block.BlockMelon;
import net.minecraft.block.BlockMobSpawner;
import net.minecraft.block.BlockMushroom;
import net.minecraft.block.BlockMycelium;
import net.minecraft.block.BlockNetherBrick;
import net.minecraft.block.BlockNetherWart;
import net.minecraft.block.BlockNetherrack;
import net.minecraft.block.BlockNewLeaf;
import net.minecraft.block.BlockNewLog;
import net.minecraft.block.BlockNote;
import net.minecraft.block.BlockObsidian;
import net.minecraft.block.BlockOldLeaf;
import net.minecraft.block.BlockOldLog;
import net.minecraft.block.BlockOre;
import net.minecraft.block.BlockPackedIce;
import net.minecraft.block.BlockPane;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.BlockPistonExtension;
import net.minecraft.block.BlockPistonMoving;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.BlockPlanks$EnumType;
import net.minecraft.block.BlockPortal;
import net.minecraft.block.BlockPotato;
import net.minecraft.block.BlockPressurePlate;
import net.minecraft.block.BlockPressurePlate$Sensitivity;
import net.minecraft.block.BlockPressurePlateWeighted;
import net.minecraft.block.BlockPrismarine;
import net.minecraft.block.BlockPumpkin;
import net.minecraft.block.BlockQuartz;
import net.minecraft.block.BlockQuartz$EnumType;
import net.minecraft.block.BlockRail;
import net.minecraft.block.BlockRailDetector;
import net.minecraft.block.BlockRailPowered;
import net.minecraft.block.BlockRedFlower;
import net.minecraft.block.BlockRedSandstone;
import net.minecraft.block.BlockRedSandstone$EnumType;
import net.minecraft.block.BlockRedstoneComparator;
import net.minecraft.block.BlockRedstoneLight;
import net.minecraft.block.BlockRedstoneOre;
import net.minecraft.block.BlockRedstoneRepeater;
import net.minecraft.block.BlockRedstoneTorch;
import net.minecraft.block.BlockRedstoneWire;
import net.minecraft.block.BlockReed;
import net.minecraft.block.BlockSand;
import net.minecraft.block.BlockSandStone;
import net.minecraft.block.BlockSandStone$EnumType;
import net.minecraft.block.BlockSapling;
import net.minecraft.block.BlockSeaLantern;
import net.minecraft.block.BlockSilverfish;
import net.minecraft.block.BlockSkull;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.BlockSlime;
import net.minecraft.block.BlockSnow;
import net.minecraft.block.BlockSnowBlock;
import net.minecraft.block.BlockSoulSand;
import net.minecraft.block.BlockSponge;
import net.minecraft.block.BlockStainedGlass;
import net.minecraft.block.BlockStainedGlassPane;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.BlockStandingSign;
import net.minecraft.block.BlockStaticLiquid;
import net.minecraft.block.BlockStem;
import net.minecraft.block.BlockStone;
import net.minecraft.block.BlockStoneBrick;
import net.minecraft.block.BlockStoneBrick$EnumType;
import net.minecraft.block.BlockTNT;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.block.BlockTorch;
import net.minecraft.block.BlockTrapDoor;
import net.minecraft.block.BlockTripWire;
import net.minecraft.block.BlockTripWireHook;
import net.minecraft.block.BlockVine;
import net.minecraft.block.BlockWall;
import net.minecraft.block.BlockWallSign;
import net.minecraft.block.BlockWeb;
import net.minecraft.block.BlockWorkbench;
import net.minecraft.block.BlockYellowFlower;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ObjectIntIdentityMap;
import net.minecraft.util.RegistryNamespacedDefaultedByKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraft.util.Vec3;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import viaversion.viafabric.ViaFabric;

public class Block {
   public static final ObjectIntIdentityMap BLOCK_STATE_IDS = new ObjectIntIdentityMap();
   public static final Block$SoundType soundTypeStone = new Block$SoundType("stone", 1.0F, 1.0F);
   public static final Block$SoundType soundTypeWood = new Block$SoundType("wood", 1.0F, 1.0F);
   public static final Block$SoundType soundTypeGravel = new Block$SoundType("gravel", 1.0F, 1.0F);
   public static final Block$SoundType soundTypeGrass = new Block$SoundType("grass", 1.0F, 1.0F);
   public static final Block$SoundType soundTypePiston = new Block$SoundType("stone", 1.0F, 1.0F);
   public static final Block$SoundType soundTypeMetal = new Block$SoundType("stone", 1.0F, 1.5F);
   public static final Block$SoundType soundTypeGlass = new Vr("stone", 1.0F, 1.0F);
   public static final Block$SoundType soundTypeCloth = new Block$SoundType("cloth", 1.0F, 1.0F);
   public static final Block$SoundType soundTypeSand = new Block$SoundType("sand", 1.0F, 1.0F);
   public static final Block$SoundType soundTypeSnow = new Block$SoundType("snow", 1.0F, 1.0F);
   public static final Block$SoundType soundTypeLadder = new Vp("ladder", 1.0F, 1.0F);
   public static final Block$SoundType soundTypeAnvil = new Vw("anvil", 0.3F, 1.0F);
   public static final Block$SoundType SLIME_SOUND = new Block$4("slime", 1.0F, 1.0F);
   private static final ResourceLocation AIR_ID = new ResourceLocation("air");
   public static final RegistryNamespacedDefaultedByKey blockRegistry = new RegistryNamespacedDefaultedByKey(AIR_ID);
   protected final Material blockMaterial;
   protected final MapColor field_181083_K;
   protected final BlockState blockState;
   public Block$SoundType stepSound;
   public float blockParticleGravity;
   public float slipperiness;
   protected boolean fullBlock;
   protected int lightOpacity;
   protected boolean translucent;
   protected int lightValue;
   protected boolean useNeighborBrightness;
   protected float blockHardness;
   protected float blockResistance;
   protected boolean enableStats;
   protected boolean needsRandomTick;
   protected boolean isBlockContainer;
   protected double minX;
   protected double minY;
   protected double minZ;
   protected double maxX;
   protected double maxY;
   protected double maxZ;
   private CreativeTabs displayOnCreativeTab;
   private IBlockState defaultBlockState;
   private String unlocalizedName;

   public Block(Material var1, MapColor var2) {
      this.enableStats = true;
      this.stepSound = soundTypeStone;
      this.blockParticleGravity = 1.0F;
      this.slipperiness = 0.6F;
      this.blockMaterial = var1;
      this.field_181083_K = var2;
      this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
      this.fullBlock = this.isOpaqueCube();
      this.lightOpacity = this.isOpaqueCube()?255:0;
      this.translucent = !var1.blocksLight();
      this.blockState = this.createBlockState();
      this.setDefaultState(this.blockState.getBaseState());
   }

   protected Block(Material var1) {
      this(var1, var1.getMaterialMapColor());
   }

   public static int getIdFromBlock(Block var0) {
      return blockRegistry.getIDForObject(var0);
   }

   public static int getStateId(IBlockState var0) {
      Block var1 = var0.getBlock();
      return getIdFromBlock(var1) + (var1.getMetaFromState(var0) << 12);
   }

   public static Block getBlockById(int var0) {
      return (Block)blockRegistry.getObjectById(var0);
   }

   public static IBlockState getStateById(int var0) {
      int var1 = var0 & 4095;
      int var2 = var0 >> 12 & 15;
      return getBlockById(var1).getStateFromMeta(var2);
   }

   public static Block getBlockFromItem(Item var0) {
      return var0 instanceof ItemBlock?((ItemBlock)var0).getBlock():null;
   }

   public static Block getBlockFromName(String var0) {
      ResourceLocation var1 = new ResourceLocation(var0);
      if(blockRegistry.containsKey(var1)) {
         return (Block)blockRegistry.getObject(var1);
      } else {
         try {
            return (Block)blockRegistry.getObjectById(Integer.parseInt(var0));
         } catch (NumberFormatException var3) {
            return null;
         }
      }
   }

   public static boolean isEqualTo(Block var0, Block var1) {
      return var0 == var1 || var0.isAssociatedBlock(var1);
   }

   public static void registerBlocks() {
      registerBlock(0, (ResourceLocation)AIR_ID, (new BlockAir()).setUnlocalizedName("air"));
      registerBlock(1, (String)"stone", (new BlockStone()).setHardness(1.5F).setResistance(10.0F).setStepSound(soundTypePiston).setUnlocalizedName("stone"));
      registerBlock(2, (String)"grass", (new BlockGrass()).setHardness(0.6F).setStepSound(soundTypeGrass).setUnlocalizedName("grass"));
      registerBlock(3, (String)"dirt", (new BlockDirt()).setHardness(0.5F).setStepSound(soundTypeGravel).setUnlocalizedName("dirt"));
      Block var0 = (new Block(Material.rock)).setHardness(2.0F).setResistance(10.0F).setStepSound(soundTypePiston).setUnlocalizedName("stonebrick").setCreativeTab(CreativeTabs.tabBlock);
      registerBlock(4, (String)"cobblestone", var0);
      Block var1 = (new BlockPlanks()).setHardness(2.0F).setResistance(5.0F).setStepSound(soundTypeWood).setUnlocalizedName("wood");
      registerBlock(5, (String)"planks", var1);
      registerBlock(6, (String)"sapling", (new BlockSapling()).setHardness(0.0F).setStepSound(soundTypeGrass).setUnlocalizedName("sapling"));
      registerBlock(7, (String)"bedrock", (new Block(Material.rock)).setBlockUnbreakable().setResistance(6000000.0F).setStepSound(soundTypePiston).setUnlocalizedName("bedrock").disableStats().setCreativeTab(CreativeTabs.tabBlock));
      registerBlock(8, (String)"flowing_water", (new BlockDynamicLiquid(Material.water)).setHardness(100.0F).setLightOpacity(3).setUnlocalizedName("water").disableStats());
      registerBlock(9, (String)"water", (new BlockStaticLiquid(Material.water)).setHardness(100.0F).setLightOpacity(3).setUnlocalizedName("water").disableStats());
      registerBlock(10, (String)"flowing_lava", (new BlockDynamicLiquid(Material.lava)).setHardness(100.0F).setLightLevel(1.0F).setUnlocalizedName("lava").disableStats());
      registerBlock(11, (String)"lava", (new BlockStaticLiquid(Material.lava)).setHardness(100.0F).setLightLevel(1.0F).setUnlocalizedName("lava").disableStats());
      registerBlock(12, (String)"sand", (new BlockSand()).setHardness(0.5F).setStepSound(soundTypeSand).setUnlocalizedName("sand"));
      registerBlock(13, (String)"gravel", (new BlockGravel()).setHardness(0.6F).setStepSound(soundTypeGravel).setUnlocalizedName("gravel"));
      registerBlock(14, (String)"gold_ore", (new BlockOre()).setHardness(3.0F).setResistance(5.0F).setStepSound(soundTypePiston).setUnlocalizedName("oreGold"));
      registerBlock(15, (String)"iron_ore", (new BlockOre()).setHardness(3.0F).setResistance(5.0F).setStepSound(soundTypePiston).setUnlocalizedName("oreIron"));
      registerBlock(16, (String)"coal_ore", (new BlockOre()).setHardness(3.0F).setResistance(5.0F).setStepSound(soundTypePiston).setUnlocalizedName("oreCoal"));
      registerBlock(17, (String)"log", (new BlockOldLog()).setUnlocalizedName("log"));
      registerBlock(18, (String)"leaves", (new BlockOldLeaf()).setUnlocalizedName("leaves"));
      registerBlock(19, (String)"sponge", (new BlockSponge()).setHardness(0.6F).setStepSound(soundTypeGrass).setUnlocalizedName("sponge"));
      registerBlock(20, (String)"glass", (new BlockGlass(Material.glass, false)).setHardness(0.3F).setStepSound(soundTypeGlass).setUnlocalizedName("glass"));
      registerBlock(21, (String)"lapis_ore", (new BlockOre()).setHardness(3.0F).setResistance(5.0F).setStepSound(soundTypePiston).setUnlocalizedName("oreLapis"));
      registerBlock(22, (String)"lapis_block", (new Block(Material.iron, MapColor.lapisColor)).setHardness(3.0F).setResistance(5.0F).setStepSound(soundTypePiston).setUnlocalizedName("blockLapis").setCreativeTab(CreativeTabs.tabBlock));
      registerBlock(23, (String)"dispenser", (new BlockDispenser()).setHardness(3.5F).setStepSound(soundTypePiston).setUnlocalizedName("dispenser"));
      Block var2 = (new BlockSandStone()).setStepSound(soundTypePiston).setHardness(0.8F).setUnlocalizedName("sandStone");
      registerBlock(24, (String)"sandstone", var2);
      registerBlock(25, (String)"noteblock", (new BlockNote()).setHardness(0.8F).setUnlocalizedName("musicBlock"));
      registerBlock(26, (String)"bed", (new BlockBed()).setStepSound(soundTypeWood).setHardness(0.2F).setUnlocalizedName("bed").disableStats());
      registerBlock(27, (String)"golden_rail", (new BlockRailPowered()).setHardness(0.7F).setStepSound(soundTypeMetal).setUnlocalizedName("goldenRail"));
      registerBlock(28, (String)"detector_rail", (new BlockRailDetector()).setHardness(0.7F).setStepSound(soundTypeMetal).setUnlocalizedName("detectorRail"));
      registerBlock(29, (String)"sticky_piston", (new BlockPistonBase(true)).setUnlocalizedName("pistonStickyBase"));
      registerBlock(30, (String)"web", (new BlockWeb()).setLightOpacity(1).setHardness(4.0F).setUnlocalizedName("web"));
      registerBlock(31, (String)"tallgrass", (new BlockTallGrass()).setHardness(0.0F).setStepSound(soundTypeGrass).setUnlocalizedName("tallgrass"));
      registerBlock(32, (String)"deadbush", (new BlockDeadBush()).setHardness(0.0F).setStepSound(soundTypeGrass).setUnlocalizedName("deadbush"));
      registerBlock(33, (String)"piston", (new BlockPistonBase(false)).setUnlocalizedName("pistonBase"));
      registerBlock(34, (String)"piston_head", (new BlockPistonExtension()).setUnlocalizedName("pistonBase"));
      registerBlock(35, (String)"wool", (new BlockColored(Material.cloth)).setHardness(0.8F).setStepSound(soundTypeCloth).setUnlocalizedName("cloth"));
      registerBlock(36, (String)"piston_extension", new BlockPistonMoving());
      registerBlock(37, (String)"yellow_flower", (new BlockYellowFlower()).setHardness(0.0F).setStepSound(soundTypeGrass).setUnlocalizedName("flower1"));
      registerBlock(38, (String)"red_flower", (new BlockRedFlower()).setHardness(0.0F).setStepSound(soundTypeGrass).setUnlocalizedName("flower2"));
      Block var3 = (new BlockMushroom()).setHardness(0.0F).setStepSound(soundTypeGrass).setLightLevel(0.125F).setUnlocalizedName("mushroom");
      registerBlock(39, (String)"brown_mushroom", var3);
      Block var4 = (new BlockMushroom()).setHardness(0.0F).setStepSound(soundTypeGrass).setUnlocalizedName("mushroom");
      registerBlock(40, (String)"red_mushroom", var4);
      registerBlock(41, (String)"gold_block", (new Block(Material.iron, MapColor.goldColor)).setHardness(3.0F).setResistance(10.0F).setStepSound(soundTypeMetal).setUnlocalizedName("blockGold").setCreativeTab(CreativeTabs.tabBlock));
      registerBlock(42, (String)"iron_block", (new Block(Material.iron, MapColor.ironColor)).setHardness(5.0F).setResistance(10.0F).setStepSound(soundTypeMetal).setUnlocalizedName("blockIron").setCreativeTab(CreativeTabs.tabBlock));
      registerBlock(43, (String)"double_stone_slab", (new BlockDoubleStoneSlab()).setHardness(2.0F).setResistance(10.0F).setStepSound(soundTypePiston).setUnlocalizedName("stoneSlab"));
      registerBlock(44, (String)"stone_slab", (new BlockHalfStoneSlab()).setHardness(2.0F).setResistance(10.0F).setStepSound(soundTypePiston).setUnlocalizedName("stoneSlab"));
      Block var5 = (new Block(Material.rock, MapColor.redColor)).setHardness(2.0F).setResistance(10.0F).setStepSound(soundTypePiston).setUnlocalizedName("brick").setCreativeTab(CreativeTabs.tabBlock);
      registerBlock(45, (String)"brick_block", var5);
      registerBlock(46, (String)"tnt", (new BlockTNT()).setHardness(0.0F).setStepSound(soundTypeGrass).setUnlocalizedName("tnt"));
      registerBlock(47, (String)"bookshelf", (new BlockBookshelf()).setHardness(1.5F).setStepSound(soundTypeWood).setUnlocalizedName("bookshelf"));
      registerBlock(48, (String)"mossy_cobblestone", (new Block(Material.rock)).setHardness(2.0F).setResistance(10.0F).setStepSound(soundTypePiston).setUnlocalizedName("stoneMoss").setCreativeTab(CreativeTabs.tabBlock));
      registerBlock(49, (String)"obsidian", (new BlockObsidian()).setHardness(50.0F).setResistance(2000.0F).setStepSound(soundTypePiston).setUnlocalizedName("obsidian"));
      registerBlock(50, (String)"torch", (new BlockTorch()).setHardness(0.0F).setLightLevel(0.9375F).setStepSound(soundTypeWood).setUnlocalizedName("torch"));
      registerBlock(51, (String)"fire", (new BlockFire()).setHardness(0.0F).setLightLevel(1.0F).setStepSound(soundTypeCloth).setUnlocalizedName("fire").disableStats());
      registerBlock(52, (String)"mob_spawner", (new BlockMobSpawner()).setHardness(5.0F).setStepSound(soundTypeMetal).setUnlocalizedName("mobSpawner").disableStats());
      registerBlock(53, (String)"oak_stairs", (new BlockStairs(var1.getDefaultState().withProperty(BlockPlanks.VARIANT, BlockPlanks$EnumType.OAK))).setUnlocalizedName("stairsWood"));
      registerBlock(54, (String)"chest", (new BlockChest(0)).setHardness(2.5F).setStepSound(soundTypeWood).setUnlocalizedName("chest"));
      registerBlock(55, (String)"redstone_wire", (new BlockRedstoneWire()).setHardness(0.0F).setStepSound(soundTypeStone).setUnlocalizedName("redstoneDust").disableStats());
      registerBlock(56, (String)"diamond_ore", (new BlockOre()).setHardness(3.0F).setResistance(5.0F).setStepSound(soundTypePiston).setUnlocalizedName("oreDiamond"));
      registerBlock(57, (String)"diamond_block", (new Block(Material.iron, MapColor.diamondColor)).setHardness(5.0F).setResistance(10.0F).setStepSound(soundTypeMetal).setUnlocalizedName("blockDiamond").setCreativeTab(CreativeTabs.tabBlock));
      registerBlock(58, (String)"crafting_table", (new BlockWorkbench()).setHardness(2.5F).setStepSound(soundTypeWood).setUnlocalizedName("workbench"));
      registerBlock(59, (String)"wheat", (new BlockCrops()).setUnlocalizedName("crops"));
      Block var6 = (new BlockFarmland()).setHardness(0.6F).setStepSound(soundTypeGravel).setUnlocalizedName("farmland");
      registerBlock(60, (String)"farmland", var6);
      registerBlock(61, (String)"furnace", (new BlockFurnace(false)).setHardness(3.5F).setStepSound(soundTypePiston).setUnlocalizedName("furnace").setCreativeTab(CreativeTabs.tabDecorations));
      registerBlock(62, (String)"lit_furnace", (new BlockFurnace(true)).setHardness(3.5F).setStepSound(soundTypePiston).setLightLevel(0.875F).setUnlocalizedName("furnace"));
      registerBlock(63, (String)"standing_sign", (new BlockStandingSign()).setHardness(1.0F).setStepSound(soundTypeWood).setUnlocalizedName("sign").disableStats());
      registerBlock(64, (String)"wooden_door", (new BlockDoor(Material.wood)).setHardness(3.0F).setStepSound(soundTypeWood).setUnlocalizedName("doorOak").disableStats());
      registerBlock(65, (String)"ladder", (new BlockLadder()).setHardness(0.4F).setStepSound(soundTypeLadder).setUnlocalizedName("ladder"));
      registerBlock(66, (String)"rail", (new BlockRail()).setHardness(0.7F).setStepSound(soundTypeMetal).setUnlocalizedName("rail"));
      registerBlock(67, (String)"stone_stairs", (new BlockStairs(var0.getDefaultState())).setUnlocalizedName("stairsStone"));
      registerBlock(68, (String)"wall_sign", (new BlockWallSign()).setHardness(1.0F).setStepSound(soundTypeWood).setUnlocalizedName("sign").disableStats());
      registerBlock(69, (String)"lever", (new BlockLever()).setHardness(0.5F).setStepSound(soundTypeWood).setUnlocalizedName("lever"));
      registerBlock(70, (String)"stone_pressure_plate", (new BlockPressurePlate(Material.rock, BlockPressurePlate$Sensitivity.MOBS)).setHardness(0.5F).setStepSound(soundTypePiston).setUnlocalizedName("pressurePlateStone"));
      registerBlock(71, (String)"iron_door", (new BlockDoor(Material.iron)).setHardness(5.0F).setStepSound(soundTypeMetal).setUnlocalizedName("doorIron").disableStats());
      registerBlock(72, (String)"wooden_pressure_plate", (new BlockPressurePlate(Material.wood, BlockPressurePlate$Sensitivity.EVERYTHING)).setHardness(0.5F).setStepSound(soundTypeWood).setUnlocalizedName("pressurePlateWood"));
      registerBlock(73, (String)"redstone_ore", (new BlockRedstoneOre(false)).setHardness(3.0F).setResistance(5.0F).setStepSound(soundTypePiston).setUnlocalizedName("oreRedstone").setCreativeTab(CreativeTabs.tabBlock));
      registerBlock(74, (String)"lit_redstone_ore", (new BlockRedstoneOre(true)).setLightLevel(0.625F).setHardness(3.0F).setResistance(5.0F).setStepSound(soundTypePiston).setUnlocalizedName("oreRedstone"));
      registerBlock(75, (String)"unlit_redstone_torch", (new BlockRedstoneTorch(false)).setHardness(0.0F).setStepSound(soundTypeWood).setUnlocalizedName("notGate"));
      registerBlock(76, (String)"redstone_torch", (new BlockRedstoneTorch(true)).setHardness(0.0F).setLightLevel(0.5F).setStepSound(soundTypeWood).setUnlocalizedName("notGate").setCreativeTab(CreativeTabs.tabRedstone));
      registerBlock(77, (String)"stone_button", (new BlockButtonStone()).setHardness(0.5F).setStepSound(soundTypePiston).setUnlocalizedName("button"));
      registerBlock(78, (String)"snow_layer", (new BlockSnow()).setHardness(0.1F).setStepSound(soundTypeSnow).setUnlocalizedName("snow").setLightOpacity(0));
      registerBlock(79, (String)"ice", (new BlockIce()).setHardness(0.5F).setLightOpacity(3).setStepSound(soundTypeGlass).setUnlocalizedName("ice"));
      registerBlock(80, (String)"snow", (new BlockSnowBlock()).setHardness(0.2F).setStepSound(soundTypeSnow).setUnlocalizedName("snow"));
      registerBlock(81, (String)"cactus", (new BlockCactus()).setHardness(0.4F).setStepSound(soundTypeCloth).setUnlocalizedName("cactus"));
      registerBlock(82, (String)"clay", (new BlockClay()).setHardness(0.6F).setStepSound(soundTypeGravel).setUnlocalizedName("clay"));
      registerBlock(83, (String)"reeds", (new BlockReed()).setHardness(0.0F).setStepSound(soundTypeGrass).setUnlocalizedName("reeds").disableStats());
      registerBlock(84, (String)"jukebox", (new BlockJukebox()).setHardness(2.0F).setResistance(10.0F).setStepSound(soundTypePiston).setUnlocalizedName("jukebox"));
      registerBlock(85, (String)"fence", (new BlockFence(Material.wood, BlockPlanks$EnumType.OAK.func_181070_c())).setHardness(2.0F).setResistance(5.0F).setStepSound(soundTypeWood).setUnlocalizedName("fence"));
      Block var7 = (new BlockPumpkin()).setHardness(1.0F).setStepSound(soundTypeWood).setUnlocalizedName("pumpkin");
      registerBlock(86, (String)"pumpkin", var7);
      registerBlock(87, (String)"netherrack", (new BlockNetherrack()).setHardness(0.4F).setStepSound(soundTypePiston).setUnlocalizedName("hellrock"));
      registerBlock(88, (String)"soul_sand", (new BlockSoulSand()).setHardness(0.5F).setStepSound(soundTypeSand).setUnlocalizedName("hellsand"));
      registerBlock(89, (String)"glowstone", (new BlockGlowstone(Material.glass)).setHardness(0.3F).setStepSound(soundTypeGlass).setLightLevel(1.0F).setUnlocalizedName("lightgem"));
      registerBlock(90, (String)"portal", (new BlockPortal()).setHardness(-1.0F).setStepSound(soundTypeGlass).setLightLevel(0.75F).setUnlocalizedName("portal"));
      registerBlock(91, (String)"lit_pumpkin", (new BlockPumpkin()).setHardness(1.0F).setStepSound(soundTypeWood).setLightLevel(1.0F).setUnlocalizedName("litpumpkin"));
      registerBlock(92, (String)"cake", (new BlockCake()).setHardness(0.5F).setStepSound(soundTypeCloth).setUnlocalizedName("cake").disableStats());
      registerBlock(93, (String)"unpowered_repeater", (new BlockRedstoneRepeater(false)).setHardness(0.0F).setStepSound(soundTypeWood).setUnlocalizedName("diode").disableStats());
      registerBlock(94, (String)"powered_repeater", (new BlockRedstoneRepeater(true)).setHardness(0.0F).setStepSound(soundTypeWood).setUnlocalizedName("diode").disableStats());
      registerBlock(95, (String)"stained_glass", (new BlockStainedGlass(Material.glass)).setHardness(0.3F).setStepSound(soundTypeGlass).setUnlocalizedName("stainedGlass"));
      registerBlock(96, (String)"trapdoor", (new BlockTrapDoor(Material.wood)).setHardness(3.0F).setStepSound(soundTypeWood).setUnlocalizedName("trapdoor").disableStats());
      registerBlock(97, (String)"monster_egg", (new BlockSilverfish()).setHardness(0.75F).setUnlocalizedName("monsterStoneEgg"));
      Block var8 = (new BlockStoneBrick()).setHardness(1.5F).setResistance(10.0F).setStepSound(soundTypePiston).setUnlocalizedName("stonebricksmooth");
      registerBlock(98, (String)"stonebrick", var8);
      registerBlock(99, (String)"brown_mushroom_block", (new BlockHugeMushroom(Material.wood, MapColor.dirtColor, var3)).setHardness(0.2F).setStepSound(soundTypeWood).setUnlocalizedName("mushroom"));
      registerBlock(100, (String)"red_mushroom_block", (new BlockHugeMushroom(Material.wood, MapColor.redColor, var4)).setHardness(0.2F).setStepSound(soundTypeWood).setUnlocalizedName("mushroom"));
      registerBlock(101, (String)"iron_bars", (new BlockPane(Material.iron, true)).setHardness(5.0F).setResistance(10.0F).setStepSound(soundTypeMetal).setUnlocalizedName("fenceIron"));
      registerBlock(102, (String)"glass_pane", (new BlockPane(Material.glass, false)).setHardness(0.3F).setStepSound(soundTypeGlass).setUnlocalizedName("thinGlass"));
      Block var9 = (new BlockMelon()).setHardness(1.0F).setStepSound(soundTypeWood).setUnlocalizedName("melon");
      registerBlock(103, (String)"melon_block", var9);
      registerBlock(104, (String)"pumpkin_stem", (new BlockStem(var7)).setHardness(0.0F).setStepSound(soundTypeWood).setUnlocalizedName("pumpkinStem"));
      registerBlock(105, (String)"melon_stem", (new BlockStem(var9)).setHardness(0.0F).setStepSound(soundTypeWood).setUnlocalizedName("pumpkinStem"));
      registerBlock(106, (String)"vine", (new BlockVine()).setHardness(0.2F).setStepSound(soundTypeGrass).setUnlocalizedName("vine"));
      registerBlock(107, (String)"fence_gate", (new BlockFenceGate(BlockPlanks$EnumType.OAK)).setHardness(2.0F).setResistance(5.0F).setStepSound(soundTypeWood).setUnlocalizedName("fenceGate"));
      registerBlock(108, (String)"brick_stairs", (new BlockStairs(var5.getDefaultState())).setUnlocalizedName("stairsBrick"));
      registerBlock(109, (String)"stone_brick_stairs", (new BlockStairs(var8.getDefaultState().withProperty(BlockStoneBrick.VARIANT, BlockStoneBrick$EnumType.DEFAULT))).setUnlocalizedName("stairsStoneBrickSmooth"));
      registerBlock(110, (String)"mycelium", (new BlockMycelium()).setHardness(0.6F).setStepSound(soundTypeGrass).setUnlocalizedName("mycel"));
      registerBlock(111, (String)"waterlily", (new BlockLilyPad()).setHardness(0.0F).setStepSound(soundTypeGrass).setUnlocalizedName("waterlily"));
      Block var10 = (new BlockNetherBrick()).setHardness(2.0F).setResistance(10.0F).setStepSound(soundTypePiston).setUnlocalizedName("netherBrick").setCreativeTab(CreativeTabs.tabBlock);
      registerBlock(112, (String)"nether_brick", var10);
      registerBlock(113, (String)"nether_brick_fence", (new BlockFence(Material.rock, MapColor.netherrackColor)).setHardness(2.0F).setResistance(10.0F).setStepSound(soundTypePiston).setUnlocalizedName("netherFence"));
      registerBlock(114, (String)"nether_brick_stairs", (new BlockStairs(var10.getDefaultState())).setUnlocalizedName("stairsNetherBrick"));
      registerBlock(115, (String)"nether_wart", (new BlockNetherWart()).setUnlocalizedName("netherStalk"));
      registerBlock(116, (String)"enchanting_table", (new BlockEnchantmentTable()).setHardness(5.0F).setResistance(2000.0F).setUnlocalizedName("enchantmentTable"));
      registerBlock(117, (String)"brewing_stand", (new BlockBrewingStand()).setHardness(0.5F).setLightLevel(0.125F).setUnlocalizedName("brewingStand"));
      registerBlock(118, (String)"cauldron", (new BlockCauldron()).setHardness(2.0F).setUnlocalizedName("cauldron"));
      registerBlock(119, (String)"end_portal", (new BlockEndPortal(Material.portal)).setHardness(-1.0F).setResistance(6000000.0F));
      registerBlock(120, (String)"end_portal_frame", (new BlockEndPortalFrame()).setStepSound(soundTypeGlass).setLightLevel(0.125F).setHardness(-1.0F).setUnlocalizedName("endPortalFrame").setResistance(6000000.0F).setCreativeTab(CreativeTabs.tabDecorations));
      registerBlock(121, (String)"end_stone", (new Block(Material.rock, MapColor.sandColor)).setHardness(3.0F).setResistance(15.0F).setStepSound(soundTypePiston).setUnlocalizedName("whiteStone").setCreativeTab(CreativeTabs.tabBlock));
      registerBlock(122, (String)"dragon_egg", (new BlockDragonEgg()).setHardness(3.0F).setResistance(15.0F).setStepSound(soundTypePiston).setLightLevel(0.125F).setUnlocalizedName("dragonEgg"));
      registerBlock(123, (String)"redstone_lamp", (new BlockRedstoneLight(false)).setHardness(0.3F).setStepSound(soundTypeGlass).setUnlocalizedName("redstoneLight").setCreativeTab(CreativeTabs.tabRedstone));
      registerBlock(124, (String)"lit_redstone_lamp", (new BlockRedstoneLight(true)).setHardness(0.3F).setStepSound(soundTypeGlass).setUnlocalizedName("redstoneLight"));
      registerBlock(125, (String)"double_wooden_slab", (new BlockDoubleWoodSlab()).setHardness(2.0F).setResistance(5.0F).setStepSound(soundTypeWood).setUnlocalizedName("woodSlab"));
      registerBlock(126, (String)"wooden_slab", (new BlockHalfWoodSlab()).setHardness(2.0F).setResistance(5.0F).setStepSound(soundTypeWood).setUnlocalizedName("woodSlab"));
      registerBlock(127, (String)"cocoa", (new BlockCocoa()).setHardness(0.2F).setResistance(5.0F).setStepSound(soundTypeWood).setUnlocalizedName("cocoa"));
      registerBlock(128, (String)"sandstone_stairs", (new BlockStairs(var2.getDefaultState().withProperty(BlockSandStone.TYPE, BlockSandStone$EnumType.SMOOTH))).setUnlocalizedName("stairsSandStone"));
      registerBlock(129, (String)"emerald_ore", (new BlockOre()).setHardness(3.0F).setResistance(5.0F).setStepSound(soundTypePiston).setUnlocalizedName("oreEmerald"));
      registerBlock(130, (String)"ender_chest", (new BlockEnderChest()).setHardness(22.5F).setResistance(1000.0F).setStepSound(soundTypePiston).setUnlocalizedName("enderChest").setLightLevel(0.5F));
      registerBlock(131, (String)"tripwire_hook", (new BlockTripWireHook()).setUnlocalizedName("tripWireSource"));
      registerBlock(132, (String)"tripwire", (new BlockTripWire()).setUnlocalizedName("tripWire"));
      registerBlock(133, (String)"emerald_block", (new Block(Material.iron, MapColor.emeraldColor)).setHardness(5.0F).setResistance(10.0F).setStepSound(soundTypeMetal).setUnlocalizedName("blockEmerald").setCreativeTab(CreativeTabs.tabBlock));
      registerBlock(134, (String)"spruce_stairs", (new BlockStairs(var1.getDefaultState().withProperty(BlockPlanks.VARIANT, BlockPlanks$EnumType.SPRUCE))).setUnlocalizedName("stairsWoodSpruce"));
      registerBlock(135, (String)"birch_stairs", (new BlockStairs(var1.getDefaultState().withProperty(BlockPlanks.VARIANT, BlockPlanks$EnumType.BIRCH))).setUnlocalizedName("stairsWoodBirch"));
      registerBlock(136, (String)"jungle_stairs", (new BlockStairs(var1.getDefaultState().withProperty(BlockPlanks.VARIANT, BlockPlanks$EnumType.JUNGLE))).setUnlocalizedName("stairsWoodJungle"));
      registerBlock(137, (String)"command_block", (new BlockCommandBlock()).setBlockUnbreakable().setResistance(6000000.0F).setUnlocalizedName("commandBlock"));
      registerBlock(138, (String)"beacon", (new BlockBeacon()).setUnlocalizedName("beacon").setLightLevel(1.0F));
      registerBlock(139, (String)"cobblestone_wall", (new BlockWall(var0)).setUnlocalizedName("cobbleWall"));
      registerBlock(140, (String)"flower_pot", (new BlockFlowerPot()).setHardness(0.0F).setStepSound(soundTypeStone).setUnlocalizedName("flowerPot"));
      registerBlock(141, (String)"carrots", (new BlockCarrot()).setUnlocalizedName("carrots"));
      registerBlock(142, (String)"potatoes", (new BlockPotato()).setUnlocalizedName("potatoes"));
      registerBlock(143, (String)"wooden_button", (new BlockButtonWood()).setHardness(0.5F).setStepSound(soundTypeWood).setUnlocalizedName("button"));
      registerBlock(144, (String)"skull", (new BlockSkull()).setHardness(1.0F).setStepSound(soundTypePiston).setUnlocalizedName("skull"));
      registerBlock(145, (String)"anvil", (new BlockAnvil()).setHardness(5.0F).setStepSound(soundTypeAnvil).setResistance(2000.0F).setUnlocalizedName("anvil"));
      registerBlock(146, (String)"trapped_chest", (new BlockChest(1)).setHardness(2.5F).setStepSound(soundTypeWood).setUnlocalizedName("chestTrap"));
      registerBlock(147, (String)"light_weighted_pressure_plate", (new BlockPressurePlateWeighted(Material.iron, 15, MapColor.goldColor)).setHardness(0.5F).setStepSound(soundTypeWood).setUnlocalizedName("weightedPlate_light"));
      registerBlock(148, (String)"heavy_weighted_pressure_plate", (new BlockPressurePlateWeighted(Material.iron, 150)).setHardness(0.5F).setStepSound(soundTypeWood).setUnlocalizedName("weightedPlate_heavy"));
      registerBlock(149, (String)"unpowered_comparator", (new BlockRedstoneComparator(false)).setHardness(0.0F).setStepSound(soundTypeWood).setUnlocalizedName("comparator").disableStats());
      registerBlock(150, (String)"powered_comparator", (new BlockRedstoneComparator(true)).setHardness(0.0F).setLightLevel(0.625F).setStepSound(soundTypeWood).setUnlocalizedName("comparator").disableStats());
      registerBlock(151, (String)"daylight_detector", new BlockDaylightDetector(false));
      registerBlock(152, (String)"redstone_block", (new BlockCompressedPowered(Material.iron, MapColor.tntColor)).setHardness(5.0F).setResistance(10.0F).setStepSound(soundTypeMetal).setUnlocalizedName("blockRedstone").setCreativeTab(CreativeTabs.tabRedstone));
      registerBlock(153, (String)"quartz_ore", (new BlockOre(MapColor.netherrackColor)).setHardness(3.0F).setResistance(5.0F).setStepSound(soundTypePiston).setUnlocalizedName("netherquartz"));
      registerBlock(154, (String)"hopper", (new BlockHopper()).setHardness(3.0F).setResistance(8.0F).setStepSound(soundTypeMetal).setUnlocalizedName("hopper"));
      Block var11 = (new BlockQuartz()).setStepSound(soundTypePiston).setHardness(0.8F).setUnlocalizedName("quartzBlock");
      registerBlock(155, (String)"quartz_block", var11);
      registerBlock(156, (String)"quartz_stairs", (new BlockStairs(var11.getDefaultState().withProperty(BlockQuartz.VARIANT, BlockQuartz$EnumType.DEFAULT))).setUnlocalizedName("stairsQuartz"));
      registerBlock(157, (String)"activator_rail", (new BlockRailPowered()).setHardness(0.7F).setStepSound(soundTypeMetal).setUnlocalizedName("activatorRail"));
      registerBlock(158, (String)"dropper", (new BlockDropper()).setHardness(3.5F).setStepSound(soundTypePiston).setUnlocalizedName("dropper"));
      registerBlock(159, (String)"stained_hardened_clay", (new BlockColored(Material.rock)).setHardness(1.25F).setResistance(7.0F).setStepSound(soundTypePiston).setUnlocalizedName("clayHardenedStained"));
      registerBlock(160, (String)"stained_glass_pane", (new BlockStainedGlassPane()).setHardness(0.3F).setStepSound(soundTypeGlass).setUnlocalizedName("thinStainedGlass"));
      registerBlock(161, (String)"leaves2", (new BlockNewLeaf()).setUnlocalizedName("leaves"));
      registerBlock(162, (String)"log2", (new BlockNewLog()).setUnlocalizedName("log"));
      registerBlock(163, (String)"acacia_stairs", (new BlockStairs(var1.getDefaultState().withProperty(BlockPlanks.VARIANT, BlockPlanks$EnumType.ACACIA))).setUnlocalizedName("stairsWoodAcacia"));
      registerBlock(164, (String)"dark_oak_stairs", (new BlockStairs(var1.getDefaultState().withProperty(BlockPlanks.VARIANT, BlockPlanks$EnumType.DARK_OAK))).setUnlocalizedName("stairsWoodDarkOak"));
      registerBlock(165, (String)"slime", (new BlockSlime()).setUnlocalizedName("slime").setStepSound(SLIME_SOUND));
      registerBlock(166, (String)"barrier", (new BlockBarrier()).setUnlocalizedName("barrier"));
      registerBlock(167, (String)"iron_trapdoor", (new BlockTrapDoor(Material.iron)).setHardness(5.0F).setStepSound(soundTypeMetal).setUnlocalizedName("ironTrapdoor").disableStats());
      registerBlock(168, (String)"prismarine", (new BlockPrismarine()).setHardness(1.5F).setResistance(10.0F).setStepSound(soundTypePiston).setUnlocalizedName("prismarine"));
      registerBlock(169, (String)"sea_lantern", (new BlockSeaLantern(Material.glass)).setHardness(0.3F).setStepSound(soundTypeGlass).setLightLevel(1.0F).setUnlocalizedName("seaLantern"));
      registerBlock(170, (String)"hay_block", (new BlockHay()).setHardness(0.5F).setStepSound(soundTypeGrass).setUnlocalizedName("hayBlock").setCreativeTab(CreativeTabs.tabBlock));
      registerBlock(171, (String)"carpet", (new BlockCarpet()).setHardness(0.1F).setStepSound(soundTypeCloth).setUnlocalizedName("woolCarpet").setLightOpacity(0));
      registerBlock(172, (String)"hardened_clay", (new BlockHardenedClay()).setHardness(1.25F).setResistance(7.0F).setStepSound(soundTypePiston).setUnlocalizedName("clayHardened"));
      registerBlock(173, (String)"coal_block", (new Block(Material.rock, MapColor.blackColor)).setHardness(5.0F).setResistance(10.0F).setStepSound(soundTypePiston).setUnlocalizedName("blockCoal").setCreativeTab(CreativeTabs.tabBlock));
      registerBlock(174, (String)"packed_ice", (new BlockPackedIce()).setHardness(0.5F).setStepSound(soundTypeGlass).setUnlocalizedName("icePacked"));
      registerBlock(175, (String)"double_plant", new BlockDoublePlant());
      registerBlock(176, (String)"standing_banner", (new BlockBanner$BlockBannerStanding()).setHardness(1.0F).setStepSound(soundTypeWood).setUnlocalizedName("banner").disableStats());
      registerBlock(177, (String)"wall_banner", (new BlockBanner$BlockBannerHanging()).setHardness(1.0F).setStepSound(soundTypeWood).setUnlocalizedName("banner").disableStats());
      registerBlock(178, (String)"daylight_detector_inverted", new BlockDaylightDetector(true));
      Block var12 = (new BlockRedSandstone()).setStepSound(soundTypePiston).setHardness(0.8F).setUnlocalizedName("redSandStone");
      registerBlock(179, (String)"red_sandstone", var12);
      registerBlock(180, (String)"red_sandstone_stairs", (new BlockStairs(var12.getDefaultState().withProperty(BlockRedSandstone.TYPE, BlockRedSandstone$EnumType.SMOOTH))).setUnlocalizedName("stairsRedSandStone"));
      registerBlock(181, (String)"double_stone_slab2", (new BlockDoubleStoneSlabNew()).setHardness(2.0F).setResistance(10.0F).setStepSound(soundTypePiston).setUnlocalizedName("stoneSlab2"));
      registerBlock(182, (String)"stone_slab2", (new BlockHalfStoneSlabNew()).setHardness(2.0F).setResistance(10.0F).setStepSound(soundTypePiston).setUnlocalizedName("stoneSlab2"));
      registerBlock(183, (String)"spruce_fence_gate", (new BlockFenceGate(BlockPlanks$EnumType.SPRUCE)).setHardness(2.0F).setResistance(5.0F).setStepSound(soundTypeWood).setUnlocalizedName("spruceFenceGate"));
      registerBlock(184, (String)"birch_fence_gate", (new BlockFenceGate(BlockPlanks$EnumType.BIRCH)).setHardness(2.0F).setResistance(5.0F).setStepSound(soundTypeWood).setUnlocalizedName("birchFenceGate"));
      registerBlock(185, (String)"jungle_fence_gate", (new BlockFenceGate(BlockPlanks$EnumType.JUNGLE)).setHardness(2.0F).setResistance(5.0F).setStepSound(soundTypeWood).setUnlocalizedName("jungleFenceGate"));
      registerBlock(186, (String)"dark_oak_fence_gate", (new BlockFenceGate(BlockPlanks$EnumType.DARK_OAK)).setHardness(2.0F).setResistance(5.0F).setStepSound(soundTypeWood).setUnlocalizedName("darkOakFenceGate"));
      registerBlock(187, (String)"acacia_fence_gate", (new BlockFenceGate(BlockPlanks$EnumType.ACACIA)).setHardness(2.0F).setResistance(5.0F).setStepSound(soundTypeWood).setUnlocalizedName("acaciaFenceGate"));
      registerBlock(188, (String)"spruce_fence", (new BlockFence(Material.wood, BlockPlanks$EnumType.SPRUCE.func_181070_c())).setHardness(2.0F).setResistance(5.0F).setStepSound(soundTypeWood).setUnlocalizedName("spruceFence"));
      registerBlock(189, (String)"birch_fence", (new BlockFence(Material.wood, BlockPlanks$EnumType.BIRCH.func_181070_c())).setHardness(2.0F).setResistance(5.0F).setStepSound(soundTypeWood).setUnlocalizedName("birchFence"));
      registerBlock(190, (String)"jungle_fence", (new BlockFence(Material.wood, BlockPlanks$EnumType.JUNGLE.func_181070_c())).setHardness(2.0F).setResistance(5.0F).setStepSound(soundTypeWood).setUnlocalizedName("jungleFence"));
      registerBlock(191, (String)"dark_oak_fence", (new BlockFence(Material.wood, BlockPlanks$EnumType.DARK_OAK.func_181070_c())).setHardness(2.0F).setResistance(5.0F).setStepSound(soundTypeWood).setUnlocalizedName("darkOakFence"));
      registerBlock(192, (String)"acacia_fence", (new BlockFence(Material.wood, BlockPlanks$EnumType.ACACIA.func_181070_c())).setHardness(2.0F).setResistance(5.0F).setStepSound(soundTypeWood).setUnlocalizedName("acaciaFence"));
      registerBlock(193, (String)"spruce_door", (new BlockDoor(Material.wood)).setHardness(3.0F).setStepSound(soundTypeWood).setUnlocalizedName("doorSpruce").disableStats());
      registerBlock(194, (String)"birch_door", (new BlockDoor(Material.wood)).setHardness(3.0F).setStepSound(soundTypeWood).setUnlocalizedName("doorBirch").disableStats());
      registerBlock(195, (String)"jungle_door", (new BlockDoor(Material.wood)).setHardness(3.0F).setStepSound(soundTypeWood).setUnlocalizedName("doorJungle").disableStats());
      registerBlock(196, (String)"acacia_door", (new BlockDoor(Material.wood)).setHardness(3.0F).setStepSound(soundTypeWood).setUnlocalizedName("doorAcacia").disableStats());
      registerBlock(197, (String)"dark_oak_door", (new BlockDoor(Material.wood)).setHardness(3.0F).setStepSound(soundTypeWood).setUnlocalizedName("doorDarkOak").disableStats());
      blockRegistry.validateKey();

      for(Block var14 : blockRegistry) {
         if(var14.blockMaterial == Material.air) {
            var14.useNeighborBrightness = false;
         } else {
            boolean var15 = false;
            boolean var16 = var14 instanceof BlockStairs;
            boolean var17 = var14 instanceof BlockSlab;
            boolean var18 = var14 == var6;
            boolean var19 = var14.translucent;
            boolean var20 = var14.lightOpacity == 0;
            var15 = true;
            var14.useNeighborBrightness = var15;
         }
      }

      for(Block var22 : blockRegistry) {
         UnmodifiableIterator var24 = var22.getBlockState().getValidStates().iterator();

         while(var24.hasNext()) {
            IBlockState var25 = (IBlockState)var24.next();
            int var26 = blockRegistry.getIDForObject(var22) << 4 | var22.getMetaFromState(var25);
            BLOCK_STATE_IDS.put(var25, var26);
         }
      }

   }

   public static void spawnAsEntity(World var0, BlockPos var1, ItemStack var2) {
      if(!var0.isRemote && var0.getGameRules().getBoolean("doTileDrops")) {
         float var3 = 0.5F;
         double var4 = (double)(var0.rand.nextFloat() * 0.5F) + 0.25D;
         double var6 = (double)(var0.rand.nextFloat() * 0.5F) + 0.25D;
         double var8 = (double)(var0.rand.nextFloat() * 0.5F) + 0.25D;
         EntityItem var10 = new EntityItem(var0, (double)var1.getX() + var4, (double)var1.getY() + var6, (double)var1.getZ() + var8, var2);
         var10.setDefaultPickupDelay();
         var0.spawnEntityInWorld(var10);
      }

   }

   private static void registerBlock(int var0, ResourceLocation var1, Block var2) {
      blockRegistry.register(var0, var1, var2);
   }

   private static void registerBlock(int var0, String var1, Block var2) {
      registerBlock(var0, new ResourceLocation(var1), var2);
   }

   public boolean isFullBlock() {
      return this.fullBlock;
   }

   public int getLightOpacity() {
      return this.lightOpacity;
   }

   protected Block setLightOpacity(int var1) {
      this.lightOpacity = var1;
      return this;
   }

   public boolean isTranslucent() {
      return this.translucent;
   }

   public int getLightValue() {
      return this.lightValue;
   }

   public boolean getUseNeighborBrightness() {
      return this.useNeighborBrightness;
   }

   public Material getMaterial() {
      return this.blockMaterial;
   }

   public MapColor getMapColor(IBlockState var1) {
      return this.field_181083_K;
   }

   public IBlockState getStateFromMeta(int var1) {
      return this.getDefaultState();
   }

   public int getMetaFromState(IBlockState var1) {
      if(!var1.getPropertyNames().isEmpty()) {
         throw new IllegalArgumentException("Don\'t know how to convert " + var1 + " back into data...");
      } else {
         return 0;
      }
   }

   public IBlockState getActualState(IBlockState var1, IBlockAccess var2, BlockPos var3) {
      return var1;
   }

   protected Block setStepSound(Block$SoundType var1) {
      this.stepSound = var1;
      return this;
   }

   protected Block setLightLevel(float var1) {
      this.lightValue = (int)(15.0F * var1);
      return this;
   }

   protected Block setResistance(float var1) {
      this.blockResistance = var1 * 3.0F;
      return this;
   }

   public boolean isBlockNormalCube() {
      return this.blockMaterial.blocksMovement() && this.isFullCube();
   }

   public boolean isNormalCube() {
      return this.blockMaterial.isOpaque() && this.isFullCube() && !this.canProvidePower();
   }

   public boolean isVisuallyOpaque() {
      return this.blockMaterial.blocksMovement() && this.isFullCube();
   }

   public boolean isFullCube() {
      return true;
   }

   public boolean isPassable(IBlockAccess var1, BlockPos var2) {
      return !this.blockMaterial.blocksMovement();
   }

   public int getRenderType() {
      return 3;
   }

   public boolean isReplaceable(World var1, BlockPos var2) {
      return false;
   }

   protected Block setHardness(float var1) {
      this.blockHardness = var1;
      if(this.blockResistance < var1 * 5.0F) {
         this.blockResistance = var1 * 5.0F;
      }

      return this;
   }

   protected Block setBlockUnbreakable() {
      this.setHardness(-1.0F);
      return this;
   }

   public float getBlockHardness(World var1, BlockPos var2) {
      return this.blockHardness;
   }

   public boolean getTickRandomly() {
      return this.needsRandomTick;
   }

   protected Block setTickRandomly(boolean var1) {
      this.needsRandomTick = var1;
      return this;
   }

   public boolean hasTileEntity() {
      return this.isBlockContainer;
   }

   public int getMixedBrightnessForBlock(IBlockAccess var1, BlockPos var2) {
      Block var3 = var1.getBlockState(var2).getBlock();
      int var4 = var1.getCombinedLight(var2, var3.getLightValue());
      if(var3 instanceof BlockSlab) {
         var2 = var2.down();
         var3 = var1.getBlockState(var2).getBlock();
         return var1.getCombinedLight(var2, var3.getLightValue());
      } else {
         return var4;
      }
   }

   protected final void setBlockBounds(float var1, float var2, float var3, float var4, float var5, float var6) {
      this.minX = (double)var1;
      this.minY = (double)var2;
      this.minZ = (double)var3;
      this.maxX = (double)var4;
      this.maxY = (double)var5;
      this.maxZ = (double)var6;
   }

   public boolean isBlockSolid(IBlockAccess var1, BlockPos var2, EnumFacing var3) {
      return var1.getBlockState(var2).getBlock().getMaterial().isSolid();
   }

   public AxisAlignedBB getSelectedBoundingBox(World var1, BlockPos var2) {
      return new AxisAlignedBB((double)var2.getX() + this.minX, (double)var2.getY() + this.minY, (double)var2.getZ() + this.minZ, (double)var2.getX() + this.maxX, (double)var2.getY() + this.maxY, (double)var2.getZ() + this.maxZ);
   }

   public AxisAlignedBB getCollisionBoundingBox(World var1, BlockPos var2, IBlockState var3) {
      return new AxisAlignedBB((double)var2.getX() + this.minX, (double)var2.getY() + this.minY, (double)var2.getZ() + this.minZ, (double)var2.getX() + this.maxX, (double)var2.getY() + this.maxY, (double)var2.getZ() + this.maxZ);
   }

   public boolean isOpaqueCube() {
      return true;
   }

   public boolean canCollideCheck(IBlockState var1, boolean var2) {
      return this.isCollidable();
   }

   public boolean isCollidable() {
      return true;
   }

   public void randomTick(World var1, BlockPos var2, IBlockState var3, Random var4) {
      this.updateTick(var1, var2, var3, var4);
   }

   public void updateTick(World var1, BlockPos var2, IBlockState var3, Random var4) {
   }

   public void randomDisplayTick(World var1, BlockPos var2, IBlockState var3, Random var4) {
   }

   public void onBlockDestroyedByPlayer(World var1, BlockPos var2, IBlockState var3) {
   }

   public void onNeighborBlockChange(World var1, BlockPos var2, IBlockState var3, Block var4) {
   }

   public int tickRate(World var1) {
      return 10;
   }

   public void onBlockAdded(World var1, BlockPos var2, IBlockState var3) {
   }

   public void breakBlock(World var1, BlockPos var2, IBlockState var3) {
   }

   public int quantityDropped(Random var1) {
      return 1;
   }

   public Item getItemDropped(IBlockState var1, Random var2, int var3) {
      return Item.getItemFromBlock(this);
   }

   public boolean shouldSideBeRendered_(IBlockAccess var1, BlockPos var2, EnumFacing var3) {
      return var3 == EnumFacing.DOWN && this.minY > 0.0D || var3 == EnumFacing.UP && this.maxY < 1.0D || var3 == EnumFacing.NORTH && this.minZ > 0.0D || var3 == EnumFacing.SOUTH && this.maxZ < 1.0D || var3 == EnumFacing.WEST && this.minX > 0.0D || var3 == EnumFacing.EAST && this.maxX < 1.0D || !var1.getBlockState(var2).getBlock().isOpaqueCube();
   }

   public boolean shouldSideBeRendered(IBlockAccess var1, BlockPos var2, EnumFacing var3) {
      if(ViaFabric.clientSideVersion != Novoline.getInstance().viaVersion() && XRay.isEnabled && XRay.blockIdList.contains(Integer.valueOf(getIdFromBlock(this)))) {
         EnumFacing[] var4 = EnumFacing.VALUES;
         int var5 = EnumFacing.VALUES.length;
         int var6 = 0;

         while(true) {
            EnumFacing var7 = var4[var6];
            if(this.shouldSideBeRendered_(var1, var2.offset(var7), var7)) {
               return true;
            }

            ++var6;
            if(var6 >= var5) {
               break;
            }
         }
      }

      return this.shouldSideBeRendered_(var1, var2, var3);
   }

   public final void dropBlockAsItem(World var1, BlockPos var2, IBlockState var3, int var4) {
      this.dropBlockAsItemWithChance(var1, var2, var3, 1.0F, var4);
   }

   public void dropBlockAsItemWithChance(World var1, BlockPos var2, IBlockState var3, float var4, int var5) {
      if(!var1.isRemote) {
         int var6 = this.quantityDroppedWithBonus(var5, var1.rand);

         for(int var7 = 0; var7 < var6; ++var7) {
            if(var1.rand.nextFloat() <= var4) {
               Item var8 = this.getItemDropped(var3, var1.rand, var5);
               spawnAsEntity(var1, var2, new ItemStack(var8, 1, this.damageDropped(var3)));
            }
         }
      }

   }

   protected void dropXpOnBlockBreak(World var1, BlockPos var2, int var3) {
      if(!var1.isRemote) {
         while(true) {
            int var4 = EntityXPOrb.getXPSplit(var3);
            var3 -= var4;
            var1.spawnEntityInWorld(new EntityXPOrb(var1, (double)var2.getX() + 0.5D, (double)var2.getY() + 0.5D, (double)var2.getZ() + 0.5D, var4));
         }
      }

   }

   public int damageDropped(IBlockState var1) {
      return 0;
   }

   public float getExplosionResistance(Entity var1) {
      return this.blockResistance / 5.0F;
   }

   public void addCollisionBoxesToList(World var1, BlockPos var2, IBlockState var3, AxisAlignedBB var4, List var5, Entity var6) {
      AxisAlignedBB var7 = this.getCollisionBoundingBox(var1, var2, var3);
      if(var6 == Minecraft.getInstance().player) {
         CollideWithBlockEvent var8 = new CollideWithBlockEvent(this, var2, var7);
         EventManager.call(var8);
         if(var8.isCancelled()) {
            return;
         }

         var7 = var8.getBoundingBox();
      }

      if(var4.intersectsWith(var7)) {
         var5.add(var7);
      }

   }

   public MovingObjectPosition collisionRayTrace(World var1, BlockPos var2, Vec3 var3, Vec3 var4) {
      this.setBlockBoundsBasedOnState(var1, var2);
      var3 = var3.addVector((double)(-var2.getX()), (double)(-var2.getY()), (double)(-var2.getZ()));
      var4 = var4.addVector((double)(-var2.getX()), (double)(-var2.getY()), (double)(-var2.getZ()));
      Vec3 var5 = var3.getIntermediateWithXValue(var4, this.minX);
      Vec3 var6 = var3.getIntermediateWithXValue(var4, this.maxX);
      Vec3 var7 = var3.getIntermediateWithYValue(var4, this.minY);
      Vec3 var8 = var3.getIntermediateWithYValue(var4, this.maxY);
      Vec3 var9 = var3.getIntermediateWithZValue(var4, this.minZ);
      Vec3 var10 = var3.getIntermediateWithZValue(var4, this.maxZ);
      if(!this.isVecInsideYZBounds(var5)) {
         var5 = null;
      }

      if(!this.isVecInsideYZBounds(var6)) {
         var6 = null;
      }

      if(!this.isVecInsideXZBounds(var7)) {
         var7 = null;
      }

      if(!this.isVecInsideXZBounds(var8)) {
         var8 = null;
      }

      if(!this.isVecInsideXYBounds(var9)) {
         var9 = null;
      }

      if(!this.isVecInsideXYBounds(var10)) {
         var10 = null;
      }

      Vec3 var11 = null;
      if(var3.squareDistanceTo(var5) < var3.squareDistanceTo(var11)) {
         var11 = var5;
      }

      if(var3.squareDistanceTo(var6) < var3.squareDistanceTo(var11)) {
         var11 = var6;
      }

      if(var3.squareDistanceTo(var7) < var3.squareDistanceTo(var11)) {
         var11 = var7;
      }

      if(var3.squareDistanceTo(var8) < var3.squareDistanceTo(var11)) {
         var11 = var8;
      }

      if(var3.squareDistanceTo(var9) < var3.squareDistanceTo(var11)) {
         var11 = var9;
      }

      if(var3.squareDistanceTo(var10) < var3.squareDistanceTo(var11)) {
         ;
      }

      return null;
   }

   public float getPlayerRelativeBlockHardness(EntityPlayer var1, World var2, BlockPos var3) {
      float var4 = this.getBlockHardness(var2, var3);
      return var4 < 0.0F?0.0F:(var1.canHarvestBlock(this)?var1.getToolDigEfficiency(this) / var4 / 30.0F:var1.getToolDigEfficiency(this) / var4 / 100.0F);
   }

   private boolean isVecInsideYZBounds(Vec3 var1) {
      return var1.yCoord >= this.minY && var1.yCoord <= this.maxY && var1.zCoord >= this.minZ && var1.zCoord <= this.maxZ;
   }

   public void onBlockDestroyedByExplosion(World var1, BlockPos var2, Explosion var3) {
   }

   private boolean isVecInsideXZBounds(Vec3 var1) {
      return var1.xCoord >= this.minX && var1.xCoord <= this.maxX && var1.zCoord >= this.minZ && var1.zCoord <= this.maxZ;
   }

   public boolean canReplace(World var1, BlockPos var2, EnumFacing var3, ItemStack var4) {
      return this.canPlaceBlockOnSide(var1, var2, var3);
   }

   public boolean canPlaceBlockOnSide(World var1, BlockPos var2, EnumFacing var3) {
      return this.canPlaceBlockAt(var1, var2);
   }

   public boolean canPlaceBlockAt(World var1, BlockPos var2) {
      return var1.getBlockState(var2).getBlock().blockMaterial.isReplaceable();
   }

   public boolean onBlockActivated(World var1, BlockPos var2, IBlockState var3, EntityPlayer var4, EnumFacing var5, float var6, float var7, float var8) {
      return false;
   }

   public void onEntityCollidedWithBlock(World var1, BlockPos var2, Entity var3) {
   }

   public IBlockState onBlockPlaced(World var1, BlockPos var2, EnumFacing var3, float var4, float var5, float var6, int var7, EntityLivingBase var8) {
      return this.getStateFromMeta(var7);
   }

   public void onBlockClicked(World var1, BlockPos var2, EntityPlayer var3) {
   }

   public Vec3 modifyAcceleration(World var1, BlockPos var2, Entity var3, Vec3 var4) {
      return var4;
   }

   public void setBlockBoundsBasedOnState(IBlockAccess var1, BlockPos var2) {
   }

   public final double getBlockBoundsMinX() {
      return this.minX;
   }

   public final double getBlockBoundsMaxX() {
      return this.maxX;
   }

   public final double getBlockBoundsMinY() {
      return this.minY;
   }

   public final double getBlockBoundsMaxY() {
      return this.maxY;
   }

   public final double getBlockBoundsMinZ() {
      return this.minZ;
   }

   public final double getBlockBoundsMaxZ() {
      return this.maxZ;
   }

   public int getBlockColor() {
      return 16777215;
   }

   public int getRenderColor(IBlockState var1) {
      return 16777215;
   }

   public int colorMultiplier(IBlockAccess var1, BlockPos var2, int var3) {
      return 16777215;
   }

   public final int colorMultiplier(IBlockAccess var1, BlockPos var2) {
      return this.colorMultiplier(var1, var2, 0);
   }

   public int getWeakPower(IBlockAccess var1, BlockPos var2, IBlockState var3, EnumFacing var4) {
      return 0;
   }

   public boolean canProvidePower() {
      return false;
   }

   public void onEntityCollidedWithBlock(World var1, BlockPos var2, IBlockState var3, Entity var4) {
   }

   public int getStrongPower(IBlockAccess var1, BlockPos var2, IBlockState var3, EnumFacing var4) {
      return 0;
   }

   public void setBlockBoundsForItemRender() {
   }

   public void harvestBlock(World var1, EntityPlayer var2, BlockPos var3, IBlockState var4, TileEntity var5) {
      var2.triggerAchievement(StatList.mineBlockStatArray[getIdFromBlock(this)]);
      var2.addExhaustion(0.025F);
      if(this.canSilkHarvest() && EnchantmentHelper.getSilkTouchModifier(var2)) {
         ItemStack var7 = this.createStackedBlock(var4);
         spawnAsEntity(var1, var3, var7);
      } else {
         int var6 = EnchantmentHelper.getFortuneModifier(var2);
         this.dropBlockAsItem(var1, var3, var4, var6);
      }

   }

   protected boolean canSilkHarvest() {
      return this.isFullCube() && !this.isBlockContainer;
   }

   protected ItemStack createStackedBlock(IBlockState var1) {
      int var2 = 0;
      Item var3 = Item.getItemFromBlock(this);
      if(var3.getHasSubtypes()) {
         var2 = this.getMetaFromState(var1);
      }

      return new ItemStack(var3, 1, var2);
   }

   public int quantityDroppedWithBonus(int var1, Random var2) {
      return this.quantityDropped(var2);
   }

   public void onBlockPlacedBy(World var1, BlockPos var2, IBlockState var3, EntityLivingBase var4, ItemStack var5) {
   }

   public boolean func_181623_g() {
      return !this.blockMaterial.isSolid() && !this.blockMaterial.isLiquid();
   }

   public boolean isSolidFullCube() {
      return this.blockMaterial.blocksMovement() && this.isFullCube();
   }

   public String getLocalizedName() {
      return StatCollector.translateToLocal(this.getUnlocalizedName() + ".name");
   }

   public String getUnlocalizedName() {
      return "tile." + this.unlocalizedName;
   }

   public Block setUnlocalizedName(String var1) {
      this.unlocalizedName = var1;
      return this;
   }

   public boolean onBlockEventReceived(World var1, BlockPos var2, IBlockState var3, int var4, int var5) {
      return false;
   }

   public boolean getEnableStats() {
      return this.enableStats;
   }

   protected Block disableStats() {
      this.enableStats = false;
      return this;
   }

   public int getMobilityFlag() {
      return this.blockMaterial.getMaterialMobility();
   }

   public EnumWorldBlockLayer getBlockLayer() {
      return XRay.isEnabled?(XRay.blockIdList.contains(Integer.valueOf(getIdFromBlock(this)))?EnumWorldBlockLayer.SOLID:EnumWorldBlockLayer.TRANSLUCENT):EnumWorldBlockLayer.SOLID;
   }

   public void onFallenUpon(World var1, BlockPos var2, Entity var3, float var4) {
      var3.fall(var4, 1.0F);
   }

   public void onLanded(World var1, Entity var2) {
      var2.motionY = 0.0D;
   }

   public Item getItem(World var1, BlockPos var2) {
      return Item.getItemFromBlock(this);
   }

   public int getDamageValue(World var1, BlockPos var2) {
      return this.damageDropped(var1.getBlockState(var2));
   }

   public void getSubBlocks(Item var1, CreativeTabs var2, List var3) {
      var3.add(new ItemStack(var1, 1, 0));
   }

   public CreativeTabs getCreativeTabToDisplayOn() {
      return this.displayOnCreativeTab;
   }

   public Block setCreativeTab(CreativeTabs var1) {
      this.displayOnCreativeTab = var1;
      return this;
   }

   public void onBlockHarvested(World var1, BlockPos var2, IBlockState var3, EntityPlayer var4) {
   }

   public void fillWithRain(World var1, BlockPos var2) {
   }

   public boolean isFlowerPot() {
      return false;
   }

   public boolean requiresUpdates() {
      return true;
   }

   public boolean canDropFromExplosion(Explosion var1) {
      return true;
   }

   public boolean isAssociatedBlock(Block var1) {
      return this == var1;
   }

   public float getAmbientOcclusionLightValue() {
      return this.isBlockNormalCube()?(XRay.isEnabled?1.0F:0.2F):1.0F;
   }

   public boolean hasComparatorInputOverride() {
      return false;
   }

   public int getComparatorInputOverride(World var1, BlockPos var2) {
      return 0;
   }

   public IBlockState getStateForEntityRender(IBlockState var1) {
      return var1;
   }

   protected BlockState createBlockState() {
      return new BlockState(this, new IProperty[0]);
   }

   public BlockState getBlockState() {
      return this.blockState;
   }

   public final IBlockState getDefaultState() {
      return this.defaultBlockState;
   }

   protected final void setDefaultState(IBlockState var1) {
      this.defaultBlockState = var1;
   }

   public Block$EnumOffsetType getOffsetType() {
      return Block$EnumOffsetType.NONE;
   }

   public String toString() {
      return "Block{" + blockRegistry.getNameForObject(this) + "}";
   }

   private boolean isVecInsideXYBounds(Vec3 var1) {
      return var1.xCoord >= this.minX && var1.xCoord <= this.maxX && var1.yCoord >= this.minY && var1.yCoord <= this.maxY;
   }

   public static EnumFacing getFacingDirection(BlockPos var0) {
      EnumFacing var1 = null;
      Minecraft var2 = Minecraft.getInstance();
      if(!var2.world.getBlockState(var0.a(0, 1, 0)).getBlock().isSolidFullCube()) {
         var1 = EnumFacing.UP;
      }

      MovingObjectPosition var3 = var2.world.rayTraceBlocks(new Vec3(var2.player.posX, var2.player.posY + (double)var2.player.getEyeHeight(), var2.player.posZ), new Vec3((double)var0.getX() + 0.5D, (double)var0.getY(), (double)var0.getZ() + 0.5D));
      return var3.facing;
   }

   private static NumberFormatException a(NumberFormatException var0) {
      return var0;
   }
}
