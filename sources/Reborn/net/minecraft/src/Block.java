package net.minecraft.src;

import me.enrythebest.reborn.cracked.*;
import me.enrythebest.reborn.cracked.mods.*;
import me.enrythebest.reborn.cracked.mods.manager.*;
import java.util.*;

public class Block
{
    private CreativeTabs displayOnCreativeTab;
    public static final StepSound soundPowderFootstep;
    public static final StepSound soundWoodFootstep;
    public static final StepSound soundGravelFootstep;
    public static final StepSound soundGrassFootstep;
    public static final StepSound soundStoneFootstep;
    public static final StepSound soundMetalFootstep;
    public static final StepSound soundGlassFootstep;
    public static final StepSound soundClothFootstep;
    public static final StepSound soundSandFootstep;
    public static final StepSound soundSnowFootstep;
    public static final StepSound soundLadderFootstep;
    public static final StepSound soundAnvilFootstep;
    public static final Block[] blocksList;
    public static final boolean[] opaqueCubeLookup;
    public static final int[] lightOpacity;
    public static final boolean[] canBlockGrass;
    public static final int[] lightValue;
    public static boolean[] useNeighborBrightness;
    public static final Block stone;
    public static final BlockGrass grass;
    public static final Block dirt;
    public static final Block cobblestone;
    public static final Block planks;
    public static final Block sapling;
    public static final Block bedrock;
    public static final BlockFluid waterMoving;
    public static final Block waterStill;
    public static final BlockFluid lavaMoving;
    public static final Block lavaStill;
    public static final Block sand;
    public static final Block gravel;
    public static final Block oreGold;
    public static final Block oreIron;
    public static final Block oreCoal;
    public static final Block wood;
    public static final BlockLeaves leaves;
    public static final Block sponge;
    public static final Block glass;
    public static final Block oreLapis;
    public static final Block blockLapis;
    public static final Block dispenser;
    public static final Block sandStone;
    public static final Block music;
    public static final Block bed;
    public static final Block railPowered;
    public static final Block railDetector;
    public static final BlockPistonBase pistonStickyBase;
    public static final Block web;
    public static final BlockTallGrass tallGrass;
    public static final BlockDeadBush deadBush;
    public static final BlockPistonBase pistonBase;
    public static final BlockPistonExtension pistonExtension;
    public static final Block cloth;
    public static final BlockPistonMoving pistonMoving;
    public static final BlockFlower plantYellow;
    public static final BlockFlower plantRed;
    public static final BlockFlower mushroomBrown;
    public static final BlockFlower mushroomRed;
    public static final Block blockGold;
    public static final Block blockIron;
    public static final BlockHalfSlab stoneDoubleSlab;
    public static final BlockHalfSlab stoneSingleSlab;
    public static final Block brick;
    public static final Block tnt;
    public static final Block bookShelf;
    public static final Block cobblestoneMossy;
    public static final Block obsidian;
    public static final Block torchWood;
    public static final BlockFire fire;
    public static final Block mobSpawner;
    public static final Block stairsWoodOak;
    public static final BlockChest chest;
    public static final BlockRedstoneWire redstoneWire;
    public static final Block oreDiamond;
    public static final Block blockDiamond;
    public static final Block workbench;
    public static final Block crops;
    public static final Block tilledField;
    public static final Block furnaceIdle;
    public static final Block furnaceBurning;
    public static final Block signPost;
    public static final Block doorWood;
    public static final Block ladder;
    public static final Block rail;
    public static final Block stairsCobblestone;
    public static final Block signWall;
    public static final Block lever;
    public static final Block pressurePlateStone;
    public static final Block doorIron;
    public static final Block pressurePlatePlanks;
    public static final Block oreRedstone;
    public static final Block oreRedstoneGlowing;
    public static final Block torchRedstoneIdle;
    public static final Block torchRedstoneActive;
    public static final Block stoneButton;
    public static final Block snow;
    public static final Block ice;
    public static final Block blockSnow;
    public static final Block cactus;
    public static final Block blockClay;
    public static final Block reed;
    public static final Block jukebox;
    public static final Block fence;
    public static final Block pumpkin;
    public static final Block netherrack;
    public static final Block slowSand;
    public static final Block glowStone;
    public static final BlockPortal portal;
    public static final Block pumpkinLantern;
    public static final Block cake;
    public static final BlockRedstoneRepeater redstoneRepeaterIdle;
    public static final BlockRedstoneRepeater redstoneRepeaterActive;
    public static final Block lockedChest;
    public static final Block trapdoor;
    public static final Block silverfish;
    public static final Block stoneBrick;
    public static final Block mushroomCapBrown;
    public static final Block mushroomCapRed;
    public static final Block fenceIron;
    public static final Block thinGlass;
    public static final Block melon;
    public static final Block pumpkinStem;
    public static final Block melonStem;
    public static final Block vine;
    public static final Block fenceGate;
    public static final Block stairsBrick;
    public static final Block stairsStoneBrick;
    public static final BlockMycelium mycelium;
    public static final Block waterlily;
    public static final Block netherBrick;
    public static final Block netherFence;
    public static final Block stairsNetherBrick;
    public static final Block netherStalk;
    public static final Block enchantmentTable;
    public static final Block brewingStand;
    public static final BlockCauldron cauldron;
    public static final Block endPortal;
    public static final Block endPortalFrame;
    public static final Block whiteStone;
    public static final Block dragonEgg;
    public static final Block redstoneLampIdle;
    public static final Block redstoneLampActive;
    public static final BlockHalfSlab woodDoubleSlab;
    public static final BlockHalfSlab woodSingleSlab;
    public static final Block cocoaPlant;
    public static final Block stairsSandStone;
    public static final Block oreEmerald;
    public static final Block enderChest;
    public static final BlockTripWireSource tripWireSource;
    public static final Block tripWire;
    public static final Block blockEmerald;
    public static final Block stairsWoodSpruce;
    public static final Block stairsWoodBirch;
    public static final Block stairsWoodJungle;
    public static final Block commandBlock;
    public static final BlockBeacon beacon;
    public static final Block cobblestoneWall;
    public static final Block flowerPot;
    public static final Block carrot;
    public static final Block potato;
    public static final Block woodenButton;
    public static final Block skull;
    public static final Block anvil;
    public static final Block chestTrapped;
    public static final Block pressurePlateGold;
    public static final Block pressurePlateIron;
    public static final BlockComparator redstoneComparatorIdle;
    public static final BlockComparator redstoneComparatorActive;
    public static final BlockDaylightDetector daylightSensor;
    public static final Block blockRedstone;
    public static final Block oreNetherQuartz;
    public static final BlockHopper hopperBlock;
    public static final Block blockNetherQuartz;
    public static final Block stairsNetherQuartz;
    public static final Block railActivator;
    public static final Block dropper;
    public final int blockID;
    protected float blockHardness;
    protected float blockResistance;
    protected boolean blockConstructorCalled;
    protected boolean enableStats;
    protected boolean needsRandomTick;
    protected boolean isBlockContainer;
    protected double minX;
    protected double minY;
    protected double minZ;
    protected double maxX;
    protected double maxY;
    protected double maxZ;
    public StepSound stepSound;
    public float blockParticleGravity;
    public final Material blockMaterial;
    public float slipperiness;
    private String unlocalizedName;
    protected Icon blockIcon;
    
    static {
        soundPowderFootstep = new StepSound("stone", 1.0f, 1.0f);
        soundWoodFootstep = new StepSound("wood", 1.0f, 1.0f);
        soundGravelFootstep = new StepSound("gravel", 1.0f, 1.0f);
        soundGrassFootstep = new StepSound("grass", 1.0f, 1.0f);
        soundStoneFootstep = new StepSound("stone", 1.0f, 1.0f);
        soundMetalFootstep = new StepSound("stone", 1.0f, 1.5f);
        soundGlassFootstep = new StepSoundStone("stone", 1.0f, 1.0f);
        soundClothFootstep = new StepSound("cloth", 1.0f, 1.0f);
        soundSandFootstep = new StepSound("sand", 1.0f, 1.0f);
        soundSnowFootstep = new StepSound("snow", 1.0f, 1.0f);
        soundLadderFootstep = new StepSoundSand("ladder", 1.0f, 1.0f);
        soundAnvilFootstep = new StepSoundAnvil("anvil", 0.3f, 1.0f);
        blocksList = new Block[4096];
        opaqueCubeLookup = new boolean[4096];
        lightOpacity = new int[4096];
        canBlockGrass = new boolean[4096];
        lightValue = new int[4096];
        Block.useNeighborBrightness = new boolean[4096];
        stone = new BlockStone(1).setHardness(1.5f).setResistance(10.0f).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("stone");
        grass = (BlockGrass)new BlockGrass(2).setHardness(0.6f).setStepSound(Block.soundGrassFootstep).setUnlocalizedName("grass");
        dirt = new BlockDirt(3).setHardness(0.5f).setStepSound(Block.soundGravelFootstep).setUnlocalizedName("dirt");
        cobblestone = new Block(4, Material.rock).setHardness(2.0f).setResistance(10.0f).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("stonebrick").setCreativeTab(CreativeTabs.tabBlock);
        planks = new BlockWood(5).setHardness(2.0f).setResistance(5.0f).setStepSound(Block.soundWoodFootstep).setUnlocalizedName("wood");
        sapling = new BlockSapling(6).setHardness(0.0f).setStepSound(Block.soundGrassFootstep).setUnlocalizedName("sapling");
        bedrock = new Block(7, Material.rock).setBlockUnbreakable().setResistance(6000000.0f).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("bedrock").disableStats().setCreativeTab(CreativeTabs.tabBlock);
        waterMoving = (BlockFluid)new BlockFlowing(8, Material.water).setHardness(100.0f).setLightOpacity(3).setUnlocalizedName("water").disableStats();
        waterStill = new BlockStationary(9, Material.water).setHardness(100.0f).setLightOpacity(3).setUnlocalizedName("water").disableStats();
        lavaMoving = (BlockFluid)new BlockFlowing(10, Material.lava).setHardness(0.0f).setLightValue(1.0f).setUnlocalizedName("lava").disableStats();
        lavaStill = new BlockStationary(11, Material.lava).setHardness(100.0f).setLightValue(1.0f).setUnlocalizedName("lava").disableStats();
        sand = new BlockSand(12).setHardness(0.5f).setStepSound(Block.soundSandFootstep).setUnlocalizedName("sand");
        gravel = new BlockGravel(13).setHardness(0.6f).setStepSound(Block.soundGravelFootstep).setUnlocalizedName("gravel");
        oreGold = new BlockOre(14).setHardness(3.0f).setResistance(5.0f).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("oreGold");
        oreIron = new BlockOre(15).setHardness(3.0f).setResistance(5.0f).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("oreIron");
        oreCoal = new BlockOre(16).setHardness(3.0f).setResistance(5.0f).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("oreCoal");
        wood = new BlockLog(17).setHardness(2.0f).setStepSound(Block.soundWoodFootstep).setUnlocalizedName("log");
        leaves = (BlockLeaves)new BlockLeaves(18).setHardness(0.2f).setLightOpacity(1).setStepSound(Block.soundGrassFootstep).setUnlocalizedName("leaves");
        sponge = new BlockSponge(19).setHardness(0.6f).setStepSound(Block.soundGrassFootstep).setUnlocalizedName("sponge");
        glass = new BlockGlass(20, Material.glass, false).setHardness(0.3f).setStepSound(Block.soundGlassFootstep).setUnlocalizedName("glass");
        oreLapis = new BlockOre(21).setHardness(3.0f).setResistance(5.0f).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("oreLapis");
        blockLapis = new Block(22, Material.rock).setHardness(3.0f).setResistance(5.0f).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("blockLapis").setCreativeTab(CreativeTabs.tabBlock);
        dispenser = new BlockDispenser(23).setHardness(3.5f).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("dispenser");
        sandStone = new BlockSandStone(24).setStepSound(Block.soundStoneFootstep).setHardness(0.8f).setUnlocalizedName("sandStone");
        music = new BlockNote(25).setHardness(0.8f).setUnlocalizedName("musicBlock");
        bed = new BlockBed(26).setHardness(0.2f).setUnlocalizedName("bed").disableStats();
        railPowered = new BlockRailPowered(27).setHardness(0.7f).setStepSound(Block.soundMetalFootstep).setUnlocalizedName("goldenRail");
        railDetector = new BlockDetectorRail(28).setHardness(0.7f).setStepSound(Block.soundMetalFootstep).setUnlocalizedName("detectorRail");
        pistonStickyBase = (BlockPistonBase)new BlockPistonBase(29, true).setUnlocalizedName("pistonStickyBase");
        web = new BlockWeb(30).setLightOpacity(1).setHardness(4.0f).setUnlocalizedName("web");
        tallGrass = (BlockTallGrass)new BlockTallGrass(31).setHardness(0.0f).setStepSound(Block.soundGrassFootstep).setUnlocalizedName("tallgrass");
        deadBush = (BlockDeadBush)new BlockDeadBush(32).setHardness(0.0f).setStepSound(Block.soundGrassFootstep).setUnlocalizedName("deadbush");
        pistonBase = (BlockPistonBase)new BlockPistonBase(33, false).setUnlocalizedName("pistonBase");
        pistonExtension = new BlockPistonExtension(34);
        cloth = new BlockCloth().setHardness(0.8f).setStepSound(Block.soundClothFootstep).setUnlocalizedName("cloth");
        pistonMoving = new BlockPistonMoving(36);
        plantYellow = (BlockFlower)new BlockFlower(37).setHardness(0.0f).setStepSound(Block.soundGrassFootstep).setUnlocalizedName("flower");
        plantRed = (BlockFlower)new BlockFlower(38).setHardness(0.0f).setStepSound(Block.soundGrassFootstep).setUnlocalizedName("rose");
        mushroomBrown = (BlockFlower)new BlockMushroom(39, "mushroom_brown").setHardness(0.0f).setStepSound(Block.soundGrassFootstep).setLightValue(0.125f).setUnlocalizedName("mushroom");
        mushroomRed = (BlockFlower)new BlockMushroom(40, "mushroom_red").setHardness(0.0f).setStepSound(Block.soundGrassFootstep).setUnlocalizedName("mushroom");
        blockGold = new BlockOreStorage(41).setHardness(3.0f).setResistance(10.0f).setStepSound(Block.soundMetalFootstep).setUnlocalizedName("blockGold");
        blockIron = new BlockOreStorage(42).setHardness(5.0f).setResistance(10.0f).setStepSound(Block.soundMetalFootstep).setUnlocalizedName("blockIron");
        stoneDoubleSlab = (BlockHalfSlab)new BlockStep(43, true).setHardness(2.0f).setResistance(10.0f).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("stoneSlab");
        stoneSingleSlab = (BlockHalfSlab)new BlockStep(44, false).setHardness(2.0f).setResistance(10.0f).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("stoneSlab");
        brick = new Block(45, Material.rock).setHardness(2.0f).setResistance(10.0f).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("brick").setCreativeTab(CreativeTabs.tabBlock);
        tnt = new BlockTNT(46).setHardness(0.0f).setStepSound(Block.soundGrassFootstep).setUnlocalizedName("tnt");
        bookShelf = new BlockBookshelf(47).setHardness(1.5f).setStepSound(Block.soundWoodFootstep).setUnlocalizedName("bookshelf");
        cobblestoneMossy = new Block(48, Material.rock).setHardness(2.0f).setResistance(10.0f).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("stoneMoss").setCreativeTab(CreativeTabs.tabBlock);
        obsidian = new BlockObsidian(49).setHardness(50.0f).setResistance(2000.0f).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("obsidian");
        torchWood = new BlockTorch(50).setHardness(0.0f).setLightValue(0.9375f).setStepSound(Block.soundWoodFootstep).setUnlocalizedName("torch");
        fire = (BlockFire)new BlockFire(51).setHardness(0.0f).setLightValue(1.0f).setStepSound(Block.soundWoodFootstep).setUnlocalizedName("fire").disableStats();
        mobSpawner = new BlockMobSpawner(52).setHardness(5.0f).setStepSound(Block.soundMetalFootstep).setUnlocalizedName("mobSpawner").disableStats();
        stairsWoodOak = new BlockStairs(53, Block.planks, 0).setUnlocalizedName("stairsWood");
        chest = (BlockChest)new BlockChest(54, 0).setHardness(2.5f).setStepSound(Block.soundWoodFootstep).setUnlocalizedName("chest");
        redstoneWire = (BlockRedstoneWire)new BlockRedstoneWire(55).setHardness(0.0f).setStepSound(Block.soundPowderFootstep).setUnlocalizedName("redstoneDust").disableStats();
        oreDiamond = new BlockOre(56).setHardness(3.0f).setResistance(5.0f).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("oreDiamond");
        blockDiamond = new BlockOreStorage(57).setHardness(5.0f).setResistance(10.0f).setStepSound(Block.soundMetalFootstep).setUnlocalizedName("blockDiamond");
        workbench = new BlockWorkbench(58).setHardness(2.5f).setStepSound(Block.soundWoodFootstep).setUnlocalizedName("workbench");
        crops = new BlockCrops(59).setUnlocalizedName("crops");
        tilledField = new BlockFarmland(60).setHardness(0.6f).setStepSound(Block.soundGravelFootstep).setUnlocalizedName("farmland");
        furnaceIdle = new BlockFurnace(61, false).setHardness(3.5f).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("furnace").setCreativeTab(CreativeTabs.tabDecorations);
        furnaceBurning = new BlockFurnace(62, true).setHardness(3.5f).setStepSound(Block.soundStoneFootstep).setLightValue(0.875f).setUnlocalizedName("furnace");
        signPost = new BlockSign(63, TileEntitySign.class, true).setHardness(1.0f).setStepSound(Block.soundWoodFootstep).setUnlocalizedName("sign").disableStats();
        doorWood = new BlockDoor(64, Material.wood).setHardness(3.0f).setStepSound(Block.soundWoodFootstep).setUnlocalizedName("doorWood").disableStats();
        ladder = new BlockLadder(65).setHardness(0.4f).setStepSound(Block.soundLadderFootstep).setUnlocalizedName("ladder");
        rail = new BlockRail(66).setHardness(0.7f).setStepSound(Block.soundMetalFootstep).setUnlocalizedName("rail");
        stairsCobblestone = new BlockStairs(67, Block.cobblestone, 0).setUnlocalizedName("stairsStone");
        signWall = new BlockSign(68, TileEntitySign.class, false).setHardness(1.0f).setStepSound(Block.soundWoodFootstep).setUnlocalizedName("sign").disableStats();
        lever = new BlockLever(69).setHardness(0.5f).setStepSound(Block.soundWoodFootstep).setUnlocalizedName("lever");
        pressurePlateStone = new BlockPressurePlate(70, "stone", Material.rock, EnumMobType.mobs).setHardness(0.5f).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("pressurePlate");
        doorIron = new BlockDoor(71, Material.iron).setHardness(5.0f).setStepSound(Block.soundMetalFootstep).setUnlocalizedName("doorIron").disableStats();
        pressurePlatePlanks = new BlockPressurePlate(72, "wood", Material.wood, EnumMobType.everything).setHardness(0.5f).setStepSound(Block.soundWoodFootstep).setUnlocalizedName("pressurePlate");
        oreRedstone = new BlockRedstoneOre(73, false).setHardness(3.0f).setResistance(5.0f).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("oreRedstone").setCreativeTab(CreativeTabs.tabBlock);
        oreRedstoneGlowing = new BlockRedstoneOre(74, true).setLightValue(0.625f).setHardness(3.0f).setResistance(5.0f).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("oreRedstone");
        torchRedstoneIdle = new BlockRedstoneTorch(75, false).setHardness(0.0f).setStepSound(Block.soundWoodFootstep).setUnlocalizedName("notGate");
        torchRedstoneActive = new BlockRedstoneTorch(76, true).setHardness(0.0f).setLightValue(0.5f).setStepSound(Block.soundWoodFootstep).setUnlocalizedName("notGate").setCreativeTab(CreativeTabs.tabRedstone);
        stoneButton = new BlockButtonStone(77).setHardness(0.5f).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("button");
        snow = new BlockSnow(78).setHardness(0.1f).setStepSound(Block.soundSnowFootstep).setUnlocalizedName("snow").setLightOpacity(0);
        ice = new BlockIce(79).setHardness(0.5f).setLightOpacity(3).setStepSound(Block.soundGlassFootstep).setUnlocalizedName("ice");
        blockSnow = new BlockSnowBlock(80).setHardness(0.2f).setStepSound(Block.soundSnowFootstep).setUnlocalizedName("snow");
        cactus = new BlockCactus(81).setHardness(0.4f).setStepSound(Block.soundClothFootstep).setUnlocalizedName("cactus");
        blockClay = new BlockClay(82).setHardness(0.6f).setStepSound(Block.soundGravelFootstep).setUnlocalizedName("clay");
        reed = new BlockReed(83).setHardness(0.0f).setStepSound(Block.soundGrassFootstep).setUnlocalizedName("reeds").disableStats();
        jukebox = new BlockJukeBox(84).setHardness(2.0f).setResistance(10.0f).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("jukebox");
        fence = new BlockFence(85, "wood", Material.wood).setHardness(2.0f).setResistance(5.0f).setStepSound(Block.soundWoodFootstep).setUnlocalizedName("fence");
        pumpkin = new BlockPumpkin(86, false).setHardness(1.0f).setStepSound(Block.soundWoodFootstep).setUnlocalizedName("pumpkin");
        netherrack = new BlockNetherrack(87).setHardness(0.4f).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("hellrock");
        slowSand = new BlockSoulSand(88).setHardness(0.5f).setStepSound(Block.soundSandFootstep).setUnlocalizedName("hellsand");
        glowStone = new BlockGlowStone(89, Material.glass).setHardness(0.3f).setStepSound(Block.soundGlassFootstep).setLightValue(1.0f).setUnlocalizedName("lightgem");
        portal = (BlockPortal)new BlockPortal(90).setHardness(-1.0f).setStepSound(Block.soundGlassFootstep).setLightValue(0.75f).setUnlocalizedName("portal");
        pumpkinLantern = new BlockPumpkin(91, true).setHardness(1.0f).setStepSound(Block.soundWoodFootstep).setLightValue(1.0f).setUnlocalizedName("litpumpkin");
        cake = new BlockCake(92).setHardness(0.5f).setStepSound(Block.soundClothFootstep).setUnlocalizedName("cake").disableStats();
        redstoneRepeaterIdle = (BlockRedstoneRepeater)new BlockRedstoneRepeater(93, false).setHardness(0.0f).setStepSound(Block.soundWoodFootstep).setUnlocalizedName("diode").disableStats();
        redstoneRepeaterActive = (BlockRedstoneRepeater)new BlockRedstoneRepeater(94, true).setHardness(0.0f).setLightValue(0.625f).setStepSound(Block.soundWoodFootstep).setUnlocalizedName("diode").disableStats();
        lockedChest = new BlockLockedChest(95).setHardness(0.0f).setLightValue(1.0f).setStepSound(Block.soundWoodFootstep).setUnlocalizedName("lockedchest").setTickRandomly(true);
        trapdoor = new BlockTrapDoor(96, Material.wood).setHardness(3.0f).setStepSound(Block.soundWoodFootstep).setUnlocalizedName("trapdoor").disableStats();
        silverfish = new BlockSilverfish(97).setHardness(0.75f).setUnlocalizedName("monsterStoneEgg");
        stoneBrick = new BlockStoneBrick(98).setHardness(1.5f).setResistance(10.0f).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("stonebricksmooth");
        mushroomCapBrown = new BlockMushroomCap(99, Material.wood, 0).setHardness(0.2f).setStepSound(Block.soundWoodFootstep).setUnlocalizedName("mushroom");
        mushroomCapRed = new BlockMushroomCap(100, Material.wood, 1).setHardness(0.2f).setStepSound(Block.soundWoodFootstep).setUnlocalizedName("mushroom");
        fenceIron = new BlockPane(101, "fenceIron", "fenceIron", Material.iron, true).setHardness(5.0f).setResistance(10.0f).setStepSound(Block.soundMetalFootstep).setUnlocalizedName("fenceIron");
        thinGlass = new BlockPane(102, "glass", "thinglass_top", Material.glass, false).setHardness(0.3f).setStepSound(Block.soundGlassFootstep).setUnlocalizedName("thinGlass");
        melon = new BlockMelon(103).setHardness(1.0f).setStepSound(Block.soundWoodFootstep).setUnlocalizedName("melon");
        pumpkinStem = new BlockStem(104, Block.pumpkin).setHardness(0.0f).setStepSound(Block.soundWoodFootstep).setUnlocalizedName("pumpkinStem");
        melonStem = new BlockStem(105, Block.melon).setHardness(0.0f).setStepSound(Block.soundWoodFootstep).setUnlocalizedName("pumpkinStem");
        vine = new BlockVine(106).setHardness(0.2f).setStepSound(Block.soundGrassFootstep).setUnlocalizedName("vine");
        fenceGate = new BlockFenceGate(107).setHardness(2.0f).setResistance(5.0f).setStepSound(Block.soundWoodFootstep).setUnlocalizedName("fenceGate");
        stairsBrick = new BlockStairs(108, Block.brick, 0).setUnlocalizedName("stairsBrick");
        stairsStoneBrick = new BlockStairs(109, Block.stoneBrick, 0).setUnlocalizedName("stairsStoneBrickSmooth");
        mycelium = (BlockMycelium)new BlockMycelium(110).setHardness(0.6f).setStepSound(Block.soundGrassFootstep).setUnlocalizedName("mycel");
        waterlily = new BlockLilyPad(111).setHardness(0.0f).setStepSound(Block.soundGrassFootstep).setUnlocalizedName("waterlily");
        netherBrick = new Block(112, Material.rock).setHardness(2.0f).setResistance(10.0f).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("netherBrick").setCreativeTab(CreativeTabs.tabBlock);
        netherFence = new BlockFence(113, "netherBrick", Material.rock).setHardness(2.0f).setResistance(10.0f).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("netherFence");
        stairsNetherBrick = new BlockStairs(114, Block.netherBrick, 0).setUnlocalizedName("stairsNetherBrick");
        netherStalk = new BlockNetherStalk(115).setUnlocalizedName("netherStalk");
        enchantmentTable = new BlockEnchantmentTable(116).setHardness(5.0f).setResistance(2000.0f).setUnlocalizedName("enchantmentTable");
        brewingStand = new BlockBrewingStand(117).setHardness(0.5f).setLightValue(0.125f).setUnlocalizedName("brewingStand");
        cauldron = (BlockCauldron)new BlockCauldron(118).setHardness(2.0f).setUnlocalizedName("cauldron");
        endPortal = new BlockEndPortal(119, Material.portal).setHardness(-1.0f).setResistance(6000000.0f);
        endPortalFrame = new BlockEndPortalFrame(120).setStepSound(Block.soundGlassFootstep).setLightValue(0.125f).setHardness(-1.0f).setUnlocalizedName("endPortalFrame").setResistance(6000000.0f).setCreativeTab(CreativeTabs.tabDecorations);
        whiteStone = new Block(121, Material.rock).setHardness(3.0f).setResistance(15.0f).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("whiteStone").setCreativeTab(CreativeTabs.tabBlock);
        dragonEgg = new BlockDragonEgg(122).setHardness(3.0f).setResistance(15.0f).setStepSound(Block.soundStoneFootstep).setLightValue(0.125f).setUnlocalizedName("dragonEgg");
        redstoneLampIdle = new BlockRedstoneLight(123, false).setHardness(0.3f).setStepSound(Block.soundGlassFootstep).setUnlocalizedName("redstoneLight").setCreativeTab(CreativeTabs.tabRedstone);
        redstoneLampActive = new BlockRedstoneLight(124, true).setHardness(0.3f).setStepSound(Block.soundGlassFootstep).setUnlocalizedName("redstoneLight");
        woodDoubleSlab = (BlockHalfSlab)new BlockWoodSlab(125, true).setHardness(2.0f).setResistance(5.0f).setStepSound(Block.soundWoodFootstep).setUnlocalizedName("woodSlab");
        woodSingleSlab = (BlockHalfSlab)new BlockWoodSlab(126, false).setHardness(2.0f).setResistance(5.0f).setStepSound(Block.soundWoodFootstep).setUnlocalizedName("woodSlab");
        cocoaPlant = new BlockCocoa(127).setHardness(0.2f).setResistance(5.0f).setStepSound(Block.soundWoodFootstep).setUnlocalizedName("cocoa");
        stairsSandStone = new BlockStairs(128, Block.sandStone, 0).setUnlocalizedName("stairsSandStone");
        oreEmerald = new BlockOre(129).setHardness(3.0f).setResistance(5.0f).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("oreEmerald");
        enderChest = new BlockEnderChest(130).setHardness(22.5f).setResistance(1000.0f).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("enderChest").setLightValue(0.5f);
        tripWireSource = (BlockTripWireSource)new BlockTripWireSource(131).setUnlocalizedName("tripWireSource");
        tripWire = new BlockTripWire(132).setUnlocalizedName("tripWire");
        blockEmerald = new BlockOreStorage(133).setHardness(5.0f).setResistance(10.0f).setStepSound(Block.soundMetalFootstep).setUnlocalizedName("blockEmerald");
        stairsWoodSpruce = new BlockStairs(134, Block.planks, 1).setUnlocalizedName("stairsWoodSpruce");
        stairsWoodBirch = new BlockStairs(135, Block.planks, 2).setUnlocalizedName("stairsWoodBirch");
        stairsWoodJungle = new BlockStairs(136, Block.planks, 3).setUnlocalizedName("stairsWoodJungle");
        commandBlock = new BlockCommandBlock(137).setUnlocalizedName("commandBlock");
        beacon = (BlockBeacon)new BlockBeacon(138).setUnlocalizedName("beacon").setLightValue(1.0f);
        cobblestoneWall = new BlockWall(139, Block.cobblestone).setUnlocalizedName("cobbleWall");
        flowerPot = new BlockFlowerPot(140).setHardness(0.0f).setStepSound(Block.soundPowderFootstep).setUnlocalizedName("flowerPot");
        carrot = new BlockCarrot(141).setUnlocalizedName("carrots");
        potato = new BlockPotato(142).setUnlocalizedName("potatoes");
        woodenButton = new BlockButtonWood(143).setHardness(0.5f).setStepSound(Block.soundWoodFootstep).setUnlocalizedName("button");
        skull = new BlockSkull(144).setHardness(1.0f).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("skull");
        anvil = new BlockAnvil(145).setHardness(5.0f).setStepSound(Block.soundAnvilFootstep).setResistance(2000.0f).setUnlocalizedName("anvil");
        chestTrapped = new BlockChest(146, 1).setHardness(2.5f).setStepSound(Block.soundWoodFootstep).setUnlocalizedName("chestTrap");
        pressurePlateGold = new BlockPressurePlateWeighted(147, "blockGold", Material.iron, 64).setHardness(0.5f).setStepSound(Block.soundWoodFootstep).setUnlocalizedName("weightedPlate_light");
        pressurePlateIron = new BlockPressurePlateWeighted(148, "blockIron", Material.iron, 640).setHardness(0.5f).setStepSound(Block.soundWoodFootstep).setUnlocalizedName("weightedPlate_heavy");
        redstoneComparatorIdle = (BlockComparator)new BlockComparator(149, false).setHardness(0.0f).setStepSound(Block.soundWoodFootstep).setUnlocalizedName("comparator").disableStats();
        redstoneComparatorActive = (BlockComparator)new BlockComparator(150, true).setHardness(0.0f).setLightValue(0.625f).setStepSound(Block.soundWoodFootstep).setUnlocalizedName("comparator").disableStats();
        daylightSensor = (BlockDaylightDetector)new BlockDaylightDetector(151).setHardness(0.2f).setStepSound(Block.soundWoodFootstep).setUnlocalizedName("daylightDetector");
        blockRedstone = new BlockPoweredOre(152).setHardness(5.0f).setResistance(10.0f).setStepSound(Block.soundMetalFootstep).setUnlocalizedName("blockRedstone");
        oreNetherQuartz = new BlockOre(153).setHardness(3.0f).setResistance(5.0f).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("netherquartz");
        hopperBlock = (BlockHopper)new BlockHopper(154).setHardness(3.0f).setResistance(8.0f).setStepSound(Block.soundWoodFootstep).setUnlocalizedName("hopper");
        blockNetherQuartz = new BlockQuartz(155).setStepSound(Block.soundStoneFootstep).setHardness(0.8f).setUnlocalizedName("quartzBlock");
        stairsNetherQuartz = new BlockStairs(156, Block.blockNetherQuartz, 0).setUnlocalizedName("stairsQuartz");
        railActivator = new BlockRailPowered(157).setHardness(0.7f).setStepSound(Block.soundMetalFootstep).setUnlocalizedName("activatorRail");
        dropper = new BlockDropper(158).setHardness(3.5f).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("dropper");
        Item.itemsList[Block.cloth.blockID] = new ItemCloth(Block.cloth.blockID - 256).setUnlocalizedName("cloth");
        Item.itemsList[Block.wood.blockID] = new ItemMultiTextureTile(Block.wood.blockID - 256, Block.wood, BlockLog.woodType).setUnlocalizedName("log");
        Item.itemsList[Block.planks.blockID] = new ItemMultiTextureTile(Block.planks.blockID - 256, Block.planks, BlockWood.woodType).setUnlocalizedName("wood");
        Item.itemsList[Block.silverfish.blockID] = new ItemMultiTextureTile(Block.silverfish.blockID - 256, Block.silverfish, BlockSilverfish.silverfishStoneTypes).setUnlocalizedName("monsterStoneEgg");
        Item.itemsList[Block.stoneBrick.blockID] = new ItemMultiTextureTile(Block.stoneBrick.blockID - 256, Block.stoneBrick, BlockStoneBrick.STONE_BRICK_TYPES).setUnlocalizedName("stonebricksmooth");
        Item.itemsList[Block.sandStone.blockID] = new ItemMultiTextureTile(Block.sandStone.blockID - 256, Block.sandStone, BlockSandStone.SAND_STONE_TYPES).setUnlocalizedName("sandStone");
        Item.itemsList[Block.blockNetherQuartz.blockID] = new ItemMultiTextureTile(Block.blockNetherQuartz.blockID - 256, Block.blockNetherQuartz, BlockQuartz.quartzBlockTypes).setUnlocalizedName("quartzBlock");
        Item.itemsList[Block.stoneSingleSlab.blockID] = new ItemSlab(Block.stoneSingleSlab.blockID - 256, Block.stoneSingleSlab, Block.stoneDoubleSlab, false).setUnlocalizedName("stoneSlab");
        Item.itemsList[Block.stoneDoubleSlab.blockID] = new ItemSlab(Block.stoneDoubleSlab.blockID - 256, Block.stoneSingleSlab, Block.stoneDoubleSlab, true).setUnlocalizedName("stoneSlab");
        Item.itemsList[Block.woodSingleSlab.blockID] = new ItemSlab(Block.woodSingleSlab.blockID - 256, Block.woodSingleSlab, Block.woodDoubleSlab, false).setUnlocalizedName("woodSlab");
        Item.itemsList[Block.woodDoubleSlab.blockID] = new ItemSlab(Block.woodDoubleSlab.blockID - 256, Block.woodSingleSlab, Block.woodDoubleSlab, true).setUnlocalizedName("woodSlab");
        Item.itemsList[Block.sapling.blockID] = new ItemMultiTextureTile(Block.sapling.blockID - 256, Block.sapling, BlockSapling.WOOD_TYPES).setUnlocalizedName("sapling");
        Item.itemsList[Block.leaves.blockID] = new ItemLeaves(Block.leaves.blockID - 256).setUnlocalizedName("leaves");
        Item.itemsList[Block.vine.blockID] = new ItemColored(Block.vine.blockID - 256, false);
        Item.itemsList[Block.tallGrass.blockID] = new ItemColored(Block.tallGrass.blockID - 256, true).setBlockNames(new String[] { "shrub", "grass", "fern" });
        Item.itemsList[Block.snow.blockID] = new ItemSnow(Block.snow.blockID - 256, Block.snow);
        Item.itemsList[Block.waterlily.blockID] = new ItemLilyPad(Block.waterlily.blockID - 256);
        Item.itemsList[Block.pistonBase.blockID] = new ItemPiston(Block.pistonBase.blockID - 256);
        Item.itemsList[Block.pistonStickyBase.blockID] = new ItemPiston(Block.pistonStickyBase.blockID - 256);
        Item.itemsList[Block.cobblestoneWall.blockID] = new ItemMultiTextureTile(Block.cobblestoneWall.blockID - 256, Block.cobblestoneWall, BlockWall.types).setUnlocalizedName("cobbleWall");
        Item.itemsList[Block.anvil.blockID] = new ItemAnvilBlock(Block.anvil).setUnlocalizedName("anvil");
        for (int var0 = 0; var0 < 256; ++var0) {
            if (Block.blocksList[var0] != null) {
                if (Item.itemsList[var0] == null) {
                    Item.itemsList[var0] = new ItemBlock(var0 - 256);
                    Block.blocksList[var0].initializeBlock();
                }
                boolean var2 = false;
                if (var0 > 0 && Block.blocksList[var0].getRenderType() == 10) {
                    var2 = true;
                }
                if (var0 > 0 && Block.blocksList[var0] instanceof BlockHalfSlab) {
                    var2 = true;
                }
                if (var0 == Block.tilledField.blockID) {
                    var2 = true;
                }
                if (Block.canBlockGrass[var0]) {
                    var2 = true;
                }
                if (Block.lightOpacity[var0] == 0) {
                    var2 = true;
                }
                Block.useNeighborBrightness[var0] = var2;
            }
        }
        Block.canBlockGrass[0] = true;
        StatList.initBreakableStats();
    }
    
    protected Block(final int par1, final Material par2Material) {
        this.blockConstructorCalled = true;
        this.enableStats = true;
        this.stepSound = Block.soundPowderFootstep;
        this.blockParticleGravity = 1.0f;
        this.slipperiness = 0.6f;
        if (Block.blocksList[par1] != null) {
            throw new IllegalArgumentException("Slot " + par1 + " is already occupied by " + Block.blocksList[par1] + " when adding " + this);
        }
        this.blockMaterial = par2Material;
        Block.blocksList[par1] = this;
        this.blockID = par1;
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
        Block.opaqueCubeLookup[par1] = this.isOpaqueCube();
        Block.lightOpacity[par1] = (this.isOpaqueCube() ? 255 : 0);
        Block.canBlockGrass[par1] = !par2Material.getCanBlockGrass();
    }
    
    protected void initializeBlock() {
    }
    
    protected Block setStepSound(final StepSound par1StepSound) {
        this.stepSound = par1StepSound;
        return this;
    }
    
    protected Block setLightOpacity(final int par1) {
        Block.lightOpacity[this.blockID] = par1;
        return this;
    }
    
    protected Block setLightValue(final float par1) {
        Block.lightValue[this.blockID] = (int)(15.0f * par1);
        return this;
    }
    
    protected Block setResistance(final float par1) {
        this.blockResistance = par1 * 3.0f;
        return this;
    }
    
    public static boolean isNormalCube(final int par0) {
        final Block var1 = Block.blocksList[par0];
        return var1 != null && (var1.blockMaterial.isOpaque() && var1.renderAsNormalBlock() && !var1.canProvidePower());
    }
    
    public boolean renderAsNormalBlock() {
        Morbid.getManager();
        return !ModManager.findMod(Xray.class).isEnabled();
    }
    
    public boolean getBlocksMovement(final IBlockAccess par1IBlockAccess, final int par2, final int par3, final int par4) {
        return !this.blockMaterial.blocksMovement();
    }
    
    public int getRenderType() {
        return 0;
    }
    
    protected Block setHardness(final float par1) {
        this.blockHardness = par1;
        if (this.blockResistance < par1 * 5.0f) {
            this.blockResistance = par1 * 5.0f;
        }
        return this;
    }
    
    protected Block setBlockUnbreakable() {
        this.setHardness(-1.0f);
        return this;
    }
    
    public float getBlockHardness(final World par1World, final int par2, final int par3, final int par4) {
        return this.blockHardness;
    }
    
    protected Block setTickRandomly(final boolean par1) {
        this.needsRandomTick = par1;
        return this;
    }
    
    public boolean getTickRandomly() {
        return this.needsRandomTick;
    }
    
    public boolean hasTileEntity() {
        return this.isBlockContainer;
    }
    
    protected final void setBlockBounds(final float par1, final float par2, final float par3, final float par4, final float par5, final float par6) {
        this.minX = par1;
        this.minY = par2;
        this.minZ = par3;
        this.maxX = par4;
        this.maxY = par5;
        this.maxZ = par6;
    }
    
    public float getBlockBrightness(final IBlockAccess par1IBlockAccess, final int par2, final int par3, final int par4) {
        Morbid.getManager();
        return ModManager.findMod(Xray.class).isEnabled() ? 1000.0f : par1IBlockAccess.getBrightness(par2, par3, par4, Block.lightValue[par1IBlockAccess.getBlockId(par2, par3, par4)]);
    }
    
    public int getMixedBrightnessForBlock(final IBlockAccess par1IBlockAccess, final int par2, final int par3, final int par4) {
        Morbid.getManager();
        return ModManager.findMod(Xray.class).isEnabled() ? 1000 : par1IBlockAccess.getLightBrightnessForSkyBlocks(par2, par3, par4, Block.lightValue[par1IBlockAccess.getBlockId(par2, par3, par4)]);
    }
    
    public boolean shouldSideBeRendered(final IBlockAccess par1IBlockAccess, final int par2, final int par3, final int par4, final int par5) {
        Morbid.getManager();
        return (ModManager.findMod(Xray.class).isEnabled() && Xray.opacity == 0) ? Xray.isXrayBlock(this.blockID) : ((par5 == 0 && this.minY > 0.0) || (par5 == 1 && this.maxY < 1.0) || (par5 == 2 && this.minZ > 0.0) || (par5 == 3 && this.maxZ < 1.0) || (par5 == 4 && this.minX > 0.0) || (par5 == 5 && this.maxX < 1.0) || !par1IBlockAccess.isBlockOpaqueCube(par2, par3, par4));
    }
    
    public boolean isBlockSolid(final IBlockAccess par1IBlockAccess, final int par2, final int par3, final int par4, final int par5) {
        return par1IBlockAccess.getBlockMaterial(par2, par3, par4).isSolid();
    }
    
    public Icon getBlockTexture(final IBlockAccess par1IBlockAccess, final int par2, final int par3, final int par4, final int par5) {
        return this.getIcon(par5, par1IBlockAccess.getBlockMetadata(par2, par3, par4));
    }
    
    public Icon getIcon(final int par1, final int par2) {
        return this.blockIcon;
    }
    
    public final Icon getBlockTextureFromSide(final int par1) {
        return this.getIcon(par1, 0);
    }
    
    public AxisAlignedBB getSelectedBoundingBoxFromPool(final World par1World, final int par2, final int par3, final int par4) {
        return AxisAlignedBB.getAABBPool().getAABB(par2 + this.minX, par3 + this.minY, par4 + this.minZ, par2 + this.maxX, par3 + this.maxY, par4 + this.maxZ);
    }
    
    public void addCollisionBoxesToList(final World par1World, final int par2, final int par3, final int par4, final AxisAlignedBB par5AxisAlignedBB, final List par6List, final Entity par7Entity) {
        final AxisAlignedBB var8 = this.getCollisionBoundingBoxFromPool(par1World, par2, par3, par4);
        if (var8 != null && par5AxisAlignedBB.intersectsWith(var8)) {
            par6List.add(var8);
        }
    }
    
    public AxisAlignedBB getCollisionBoundingBoxFromPool(final World par1World, final int par2, final int par3, final int par4) {
        return AxisAlignedBB.getAABBPool().getAABB(par2 + this.minX, par3 + this.minY, par4 + this.minZ, par2 + this.maxX, par3 + this.maxY, par4 + this.maxZ);
    }
    
    public boolean isOpaqueCube() {
        return true;
    }
    
    public boolean canCollideCheck(final int par1, final boolean par2) {
        return this.isCollidable();
    }
    
    public boolean isCollidable() {
        return true;
    }
    
    public void updateTick(final World par1World, final int par2, final int par3, final int par4, final Random par5Random) {
    }
    
    public void randomDisplayTick(final World par1World, final int par2, final int par3, final int par4, final Random par5Random) {
    }
    
    public void onBlockDestroyedByPlayer(final World par1World, final int par2, final int par3, final int par4, final int par5) {
    }
    
    public void onNeighborBlockChange(final World par1World, final int par2, final int par3, final int par4, final int par5) {
    }
    
    public int tickRate(final World par1World) {
        return 10;
    }
    
    public void onBlockAdded(final World par1World, final int par2, final int par3, final int par4) {
    }
    
    public void breakBlock(final World par1World, final int par2, final int par3, final int par4, final int par5, final int par6) {
    }
    
    public int quantityDropped(final Random par1Random) {
        return 1;
    }
    
    public int idDropped(final int par1, final Random par2Random, final int par3) {
        return this.blockID;
    }
    
    public float getPlayerRelativeBlockHardness(final EntityPlayer par1EntityPlayer, final World par2World, final int par3, final int par4, final int par5) {
        final float var6 = this.getBlockHardness(par2World, par3, par4, par5);
        return (var6 < 0.0f) ? 0.0f : (par1EntityPlayer.canHarvestBlock(this) ? (par1EntityPlayer.getCurrentPlayerStrVsBlock(this, true) / var6 / 30.0f) : (par1EntityPlayer.getCurrentPlayerStrVsBlock(this, false) / var6 / 100.0f));
    }
    
    public final void dropBlockAsItem(final World par1World, final int par2, final int par3, final int par4, final int par5, final int par6) {
        this.dropBlockAsItemWithChance(par1World, par2, par3, par4, par5, 1.0f, par6);
    }
    
    public void dropBlockAsItemWithChance(final World par1World, final int par2, final int par3, final int par4, final int par5, final float par6, final int par7) {
        if (!par1World.isRemote) {
            for (int var8 = this.quantityDroppedWithBonus(par7, par1World.rand), var9 = 0; var9 < var8; ++var9) {
                if (par1World.rand.nextFloat() <= par6) {
                    final int var10 = this.idDropped(par5, par1World.rand, par7);
                    if (var10 > 0) {
                        this.dropBlockAsItem_do(par1World, par2, par3, par4, new ItemStack(var10, 1, this.damageDropped(par5)));
                    }
                }
            }
        }
    }
    
    protected void dropBlockAsItem_do(final World par1World, final int par2, final int par3, final int par4, final ItemStack par5ItemStack) {
        if (!par1World.isRemote && par1World.getGameRules().getGameRuleBooleanValue("doTileDrops")) {
            final float var6 = 0.7f;
            final double var7 = par1World.rand.nextFloat() * var6 + (1.0f - var6) * 0.5;
            final double var8 = par1World.rand.nextFloat() * var6 + (1.0f - var6) * 0.5;
            final double var9 = par1World.rand.nextFloat() * var6 + (1.0f - var6) * 0.5;
            final EntityItem var10 = new EntityItem(par1World, par2 + var7, par3 + var8, par4 + var9, par5ItemStack);
            var10.delayBeforeCanPickup = 10;
            par1World.spawnEntityInWorld(var10);
        }
    }
    
    protected void dropXpOnBlockBreak(final World par1World, final int par2, final int par3, final int par4, int par5) {
        if (!par1World.isRemote) {
            while (par5 > 0) {
                final int var6 = EntityXPOrb.getXPSplit(par5);
                par5 -= var6;
                par1World.spawnEntityInWorld(new EntityXPOrb(par1World, par2 + 0.5, par3 + 0.5, par4 + 0.5, var6));
            }
        }
    }
    
    public int damageDropped(final int par1) {
        return 0;
    }
    
    public float getExplosionResistance(final Entity par1Entity) {
        return this.blockResistance / 5.0f;
    }
    
    public MovingObjectPosition collisionRayTrace(final World par1World, final int par2, final int par3, final int par4, Vec3 par5Vec3, Vec3 par6Vec3) {
        this.setBlockBoundsBasedOnState(par1World, par2, par3, par4);
        par5Vec3 = par5Vec3.addVector(-par2, -par3, -par4);
        par6Vec3 = par6Vec3.addVector(-par2, -par3, -par4);
        Vec3 var7 = par5Vec3.getIntermediateWithXValue(par6Vec3, this.minX);
        Vec3 var8 = par5Vec3.getIntermediateWithXValue(par6Vec3, this.maxX);
        Vec3 var9 = par5Vec3.getIntermediateWithYValue(par6Vec3, this.minY);
        Vec3 var10 = par5Vec3.getIntermediateWithYValue(par6Vec3, this.maxY);
        Vec3 var11 = par5Vec3.getIntermediateWithZValue(par6Vec3, this.minZ);
        Vec3 var12 = par5Vec3.getIntermediateWithZValue(par6Vec3, this.maxZ);
        if (!this.isVecInsideYZBounds(var7)) {
            var7 = null;
        }
        if (!this.isVecInsideYZBounds(var8)) {
            var8 = null;
        }
        if (!this.isVecInsideXZBounds(var9)) {
            var9 = null;
        }
        if (!this.isVecInsideXZBounds(var10)) {
            var10 = null;
        }
        if (!this.isVecInsideXYBounds(var11)) {
            var11 = null;
        }
        if (!this.isVecInsideXYBounds(var12)) {
            var12 = null;
        }
        Vec3 var13 = null;
        if (var7 != null && (var13 == null || par5Vec3.squareDistanceTo(var7) < par5Vec3.squareDistanceTo(var13))) {
            var13 = var7;
        }
        if (var8 != null && (var13 == null || par5Vec3.squareDistanceTo(var8) < par5Vec3.squareDistanceTo(var13))) {
            var13 = var8;
        }
        if (var9 != null && (var13 == null || par5Vec3.squareDistanceTo(var9) < par5Vec3.squareDistanceTo(var13))) {
            var13 = var9;
        }
        if (var10 != null && (var13 == null || par5Vec3.squareDistanceTo(var10) < par5Vec3.squareDistanceTo(var13))) {
            var13 = var10;
        }
        if (var11 != null && (var13 == null || par5Vec3.squareDistanceTo(var11) < par5Vec3.squareDistanceTo(var13))) {
            var13 = var11;
        }
        if (var12 != null && (var13 == null || par5Vec3.squareDistanceTo(var12) < par5Vec3.squareDistanceTo(var13))) {
            var13 = var12;
        }
        if (var13 == null) {
            return null;
        }
        byte var14 = -1;
        if (var13 == var7) {
            var14 = 4;
        }
        if (var13 == var8) {
            var14 = 5;
        }
        if (var13 == var9) {
            var14 = 0;
        }
        if (var13 == var10) {
            var14 = 1;
        }
        if (var13 == var11) {
            var14 = 2;
        }
        if (var13 == var12) {
            var14 = 3;
        }
        return new MovingObjectPosition(par2, par3, par4, var14, var13.addVector(par2, par3, par4));
    }
    
    private boolean isVecInsideYZBounds(final Vec3 par1Vec3) {
        return par1Vec3 != null && (par1Vec3.yCoord >= this.minY && par1Vec3.yCoord <= this.maxY && par1Vec3.zCoord >= this.minZ && par1Vec3.zCoord <= this.maxZ);
    }
    
    private boolean isVecInsideXZBounds(final Vec3 par1Vec3) {
        return par1Vec3 != null && (par1Vec3.xCoord >= this.minX && par1Vec3.xCoord <= this.maxX && par1Vec3.zCoord >= this.minZ && par1Vec3.zCoord <= this.maxZ);
    }
    
    private boolean isVecInsideXYBounds(final Vec3 par1Vec3) {
        return par1Vec3 != null && (par1Vec3.xCoord >= this.minX && par1Vec3.xCoord <= this.maxX && par1Vec3.yCoord >= this.minY && par1Vec3.yCoord <= this.maxY);
    }
    
    public void onBlockDestroyedByExplosion(final World par1World, final int par2, final int par3, final int par4, final Explosion par5Explosion) {
    }
    
    public int getRenderBlockPass() {
        Morbid.getManager();
        return (ModManager.findMod(Xray.class).isEnabled() && Xray.opacity != 0 && !Xray.isXrayBlock(this.blockID)) ? 1 : 0;
    }
    
    public boolean canPlaceBlockOnSide(final World par1World, final int par2, final int par3, final int par4, final int par5, final ItemStack par6ItemStack) {
        return this.canPlaceBlockOnSide(par1World, par2, par3, par4, par5);
    }
    
    public boolean canPlaceBlockOnSide(final World par1World, final int par2, final int par3, final int par4, final int par5) {
        return this.canPlaceBlockAt(par1World, par2, par3, par4);
    }
    
    public boolean canPlaceBlockAt(final World par1World, final int par2, final int par3, final int par4) {
        final int var5 = par1World.getBlockId(par2, par3, par4);
        return var5 == 0 || Block.blocksList[var5].blockMaterial.isReplaceable();
    }
    
    public boolean onBlockActivated(final World par1World, final int par2, final int par3, final int par4, final EntityPlayer par5EntityPlayer, final int par6, final float par7, final float par8, final float par9) {
        return false;
    }
    
    public void onEntityWalking(final World par1World, final int par2, final int par3, final int par4, final Entity par5Entity) {
    }
    
    public int onBlockPlaced(final World par1World, final int par2, final int par3, final int par4, final int par5, final float par6, final float par7, final float par8, final int par9) {
        return par9;
    }
    
    public void onBlockClicked(final World par1World, final int par2, final int par3, final int par4, final EntityPlayer par5EntityPlayer) {
    }
    
    public void velocityToAddToEntity(final World par1World, final int par2, final int par3, final int par4, final Entity par5Entity, final Vec3 par6Vec3) {
    }
    
    public void setBlockBoundsBasedOnState(final IBlockAccess par1IBlockAccess, final int par2, final int par3, final int par4) {
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
    
    public int getRenderColor(final int par1) {
        return 16777215;
    }
    
    public int colorMultiplier(final IBlockAccess par1IBlockAccess, final int par2, final int par3, final int par4) {
        return 16777215;
    }
    
    public int isProvidingWeakPower(final IBlockAccess par1IBlockAccess, final int par2, final int par3, final int par4, final int par5) {
        return 0;
    }
    
    public boolean canProvidePower() {
        return false;
    }
    
    public void onEntityCollidedWithBlock(final World par1World, final int par2, final int par3, final int par4, final Entity par5Entity) {
    }
    
    public int isProvidingStrongPower(final IBlockAccess par1IBlockAccess, final int par2, final int par3, final int par4, final int par5) {
        return 0;
    }
    
    public void setBlockBoundsForItemRender() {
    }
    
    public void harvestBlock(final World par1World, final EntityPlayer par2EntityPlayer, final int par3, final int par4, final int par5, final int par6) {
        par2EntityPlayer.addStat(StatList.mineBlockStatArray[this.blockID], 1);
        par2EntityPlayer.addExhaustion(0.025f);
        if (this.canSilkHarvest() && EnchantmentHelper.getSilkTouchModifier(par2EntityPlayer)) {
            final ItemStack var8 = this.createStackedBlock(par6);
            if (var8 != null) {
                this.dropBlockAsItem_do(par1World, par3, par4, par5, var8);
            }
        }
        else {
            final int var9 = EnchantmentHelper.getFortuneModifier(par2EntityPlayer);
            this.dropBlockAsItem(par1World, par3, par4, par5, par6, var9);
        }
    }
    
    protected boolean canSilkHarvest() {
        return this.renderAsNormalBlock() && !this.isBlockContainer;
    }
    
    protected ItemStack createStackedBlock(final int par1) {
        int var2 = 0;
        if (this.blockID >= 0 && this.blockID < Item.itemsList.length && Item.itemsList[this.blockID].getHasSubtypes()) {
            var2 = par1;
        }
        return new ItemStack(this.blockID, 1, var2);
    }
    
    public int quantityDroppedWithBonus(final int par1, final Random par2Random) {
        return this.quantityDropped(par2Random);
    }
    
    public boolean canBlockStay(final World par1World, final int par2, final int par3, final int par4) {
        return true;
    }
    
    public void onBlockPlacedBy(final World par1World, final int par2, final int par3, final int par4, final EntityLiving par5EntityLiving, final ItemStack par6ItemStack) {
    }
    
    public void onPostBlockPlaced(final World par1World, final int par2, final int par3, final int par4, final int par5) {
    }
    
    public Block setUnlocalizedName(final String par1Str) {
        this.unlocalizedName = par1Str;
        return this;
    }
    
    public String getLocalizedName() {
        return StatCollector.translateToLocal(String.valueOf(this.getUnlocalizedName()) + ".name");
    }
    
    public String getUnlocalizedName() {
        return "tile." + this.unlocalizedName;
    }
    
    public String getUnlocalizedName2() {
        return this.unlocalizedName;
    }
    
    public boolean onBlockEventReceived(final World par1World, final int par2, final int par3, final int par4, final int par5, final int par6) {
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
    
    public float getAmbientOcclusionLightValue(final IBlockAccess par1IBlockAccess, final int par2, final int par3, final int par4) {
        return par1IBlockAccess.isBlockNormalCube(par2, par3, par4) ? 0.2f : 1.0f;
    }
    
    public void onFallenUpon(final World par1World, final int par2, final int par3, final int par4, final Entity par5Entity, final float par6) {
    }
    
    public int idPicked(final World par1World, final int par2, final int par3, final int par4) {
        return this.blockID;
    }
    
    public int getDamageValue(final World par1World, final int par2, final int par3, final int par4) {
        return this.damageDropped(par1World.getBlockMetadata(par2, par3, par4));
    }
    
    public void getSubBlocks(final int par1, final CreativeTabs par2CreativeTabs, final List par3List) {
        par3List.add(new ItemStack(par1, 1, 0));
    }
    
    public CreativeTabs getCreativeTabToDisplayOn() {
        return this.displayOnCreativeTab;
    }
    
    public Block setCreativeTab(final CreativeTabs par1CreativeTabs) {
        this.displayOnCreativeTab = par1CreativeTabs;
        return this;
    }
    
    public void onBlockHarvested(final World par1World, final int par2, final int par3, final int par4, final int par5, final EntityPlayer par6EntityPlayer) {
    }
    
    public void onSetBlockIDWithMetaData(final World par1World, final int par2, final int par3, final int par4, final int par5) {
    }
    
    public void fillWithRain(final World par1World, final int par2, final int par3, final int par4) {
    }
    
    public boolean isFlowerPot() {
        return false;
    }
    
    public boolean func_82506_l() {
        return true;
    }
    
    public boolean canDropFromExplosion(final Explosion par1Explosion) {
        return true;
    }
    
    public boolean isAssociatedBlockID(final int par1) {
        return this.blockID == par1;
    }
    
    public static boolean isAssociatedBlockID(final int par0, final int par1) {
        return par0 == par1 || (par0 != 0 && par1 != 0 && Block.blocksList[par0] != null && Block.blocksList[par1] != null && Block.blocksList[par0].isAssociatedBlockID(par1));
    }
    
    public boolean hasComparatorInputOverride() {
        return false;
    }
    
    public int getComparatorInputOverride(final World par1World, final int par2, final int par3, final int par4, final int par5) {
        return 0;
    }
    
    public void registerIcons(final IconRegister par1IconRegister) {
        this.blockIcon = par1IconRegister.registerIcon(this.unlocalizedName);
    }
    
    public String getItemIconName() {
        return null;
    }
}
