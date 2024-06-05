package net.minecraft.src;

import java.util.*;

public class Item
{
    private CreativeTabs tabToDisplayOn;
    protected static Random itemRand;
    public static Item[] itemsList;
    public static Item shovelIron;
    public static Item pickaxeIron;
    public static Item axeIron;
    public static Item flintAndSteel;
    public static Item appleRed;
    public static ItemBow bow;
    public static Item arrow;
    public static Item coal;
    public static Item diamond;
    public static Item ingotIron;
    public static Item ingotGold;
    public static Item swordIron;
    public static Item swordWood;
    public static Item shovelWood;
    public static Item pickaxeWood;
    public static Item axeWood;
    public static Item swordStone;
    public static Item shovelStone;
    public static Item pickaxeStone;
    public static Item axeStone;
    public static Item swordDiamond;
    public static Item shovelDiamond;
    public static Item pickaxeDiamond;
    public static Item axeDiamond;
    public static Item stick;
    public static Item bowlEmpty;
    public static Item bowlSoup;
    public static Item swordGold;
    public static Item shovelGold;
    public static Item pickaxeGold;
    public static Item axeGold;
    public static Item silk;
    public static Item feather;
    public static Item gunpowder;
    public static Item hoeWood;
    public static Item hoeStone;
    public static Item hoeIron;
    public static Item hoeDiamond;
    public static Item hoeGold;
    public static Item seeds;
    public static Item wheat;
    public static Item bread;
    public static ItemArmor helmetLeather;
    public static ItemArmor plateLeather;
    public static ItemArmor legsLeather;
    public static ItemArmor bootsLeather;
    public static ItemArmor helmetChain;
    public static ItemArmor plateChain;
    public static ItemArmor legsChain;
    public static ItemArmor bootsChain;
    public static ItemArmor helmetIron;
    public static ItemArmor plateIron;
    public static ItemArmor legsIron;
    public static ItemArmor bootsIron;
    public static ItemArmor helmetDiamond;
    public static ItemArmor plateDiamond;
    public static ItemArmor legsDiamond;
    public static ItemArmor bootsDiamond;
    public static ItemArmor helmetGold;
    public static ItemArmor plateGold;
    public static ItemArmor legsGold;
    public static ItemArmor bootsGold;
    public static Item flint;
    public static Item porkRaw;
    public static Item porkCooked;
    public static Item painting;
    public static Item appleGold;
    public static Item sign;
    public static Item doorWood;
    public static Item bucketEmpty;
    public static Item bucketWater;
    public static Item bucketLava;
    public static Item minecartEmpty;
    public static Item saddle;
    public static Item doorIron;
    public static Item redstone;
    public static Item snowball;
    public static Item boat;
    public static Item leather;
    public static Item bucketMilk;
    public static Item brick;
    public static Item clay;
    public static Item reed;
    public static Item paper;
    public static Item book;
    public static Item slimeBall;
    public static Item minecartCrate;
    public static Item minecartPowered;
    public static Item egg;
    public static Item compass;
    public static ItemFishingRod fishingRod;
    public static Item pocketSundial;
    public static Item lightStoneDust;
    public static Item fishRaw;
    public static Item fishCooked;
    public static Item dyePowder;
    public static Item bone;
    public static Item sugar;
    public static Item cake;
    public static Item bed;
    public static Item redstoneRepeater;
    public static Item cookie;
    public static ItemMap map;
    public static ItemShears shears;
    public static Item melon;
    public static Item pumpkinSeeds;
    public static Item melonSeeds;
    public static Item beefRaw;
    public static Item beefCooked;
    public static Item chickenRaw;
    public static Item chickenCooked;
    public static Item rottenFlesh;
    public static Item enderPearl;
    public static Item blazeRod;
    public static Item ghastTear;
    public static Item goldNugget;
    public static Item netherStalkSeeds;
    public static ItemPotion potion;
    public static Item glassBottle;
    public static Item spiderEye;
    public static Item fermentedSpiderEye;
    public static Item blazePowder;
    public static Item magmaCream;
    public static Item brewingStand;
    public static Item cauldron;
    public static Item eyeOfEnder;
    public static Item speckledMelon;
    public static Item monsterPlacer;
    public static Item expBottle;
    public static Item fireballCharge;
    public static Item writableBook;
    public static Item writtenBook;
    public static Item emerald;
    public static Item itemFrame;
    public static Item flowerPot;
    public static Item carrot;
    public static Item potato;
    public static Item bakedPotato;
    public static Item poisonousPotato;
    public static ItemEmptyMap emptyMap;
    public static Item goldenCarrot;
    public static Item skull;
    public static Item carrotOnAStick;
    public static Item netherStar;
    public static Item pumpkinPie;
    public static Item firework;
    public static Item fireworkCharge;
    public static ItemEnchantedBook enchantedBook;
    public static Item comparator;
    public static Item netherrackBrick;
    public static Item netherQuartz;
    public static Item minecartTnt;
    public static Item minecartHopper;
    public static Item record13;
    public static Item recordCat;
    public static Item recordBlocks;
    public static Item recordChirp;
    public static Item recordFar;
    public static Item recordMall;
    public static Item recordMellohi;
    public static Item recordStal;
    public static Item recordStrad;
    public static Item recordWard;
    public static Item record11;
    public static Item recordWait;
    public final int itemID;
    protected int maxStackSize;
    private int maxDamage;
    protected boolean bFull3D;
    protected boolean hasSubtypes;
    private Item containerItem;
    private String potionEffect;
    private String unlocalizedName;
    protected Icon itemIcon;
    
    static {
        Item.itemRand = new Random();
        Item.itemsList = new Item[32000];
        Item.shovelIron = new ItemSpade(0, EnumToolMaterial.IRON).setUnlocalizedName("shovelIron");
        Item.pickaxeIron = new ItemPickaxe(1, EnumToolMaterial.IRON).setUnlocalizedName("pickaxeIron");
        Item.axeIron = new ItemAxe(2, EnumToolMaterial.IRON).setUnlocalizedName("hatchetIron");
        Item.flintAndSteel = new ItemFlintAndSteel(3).setUnlocalizedName("flintAndSteel");
        Item.appleRed = new ItemFood(4, 4, 0.3f, false).setUnlocalizedName("apple");
        Item.bow = (ItemBow)new ItemBow(5).setUnlocalizedName("bow");
        Item.arrow = new Item(6).setUnlocalizedName("arrow").setCreativeTab(CreativeTabs.tabCombat);
        Item.coal = new ItemCoal(7).setUnlocalizedName("coal");
        Item.diamond = new Item(8).setUnlocalizedName("diamond").setCreativeTab(CreativeTabs.tabMaterials);
        Item.ingotIron = new Item(9).setUnlocalizedName("ingotIron").setCreativeTab(CreativeTabs.tabMaterials);
        Item.ingotGold = new Item(10).setUnlocalizedName("ingotGold").setCreativeTab(CreativeTabs.tabMaterials);
        Item.swordIron = new ItemSword(11, EnumToolMaterial.IRON).setUnlocalizedName("swordIron");
        Item.swordWood = new ItemSword(12, EnumToolMaterial.WOOD).setUnlocalizedName("swordWood");
        Item.shovelWood = new ItemSpade(13, EnumToolMaterial.WOOD).setUnlocalizedName("shovelWood");
        Item.pickaxeWood = new ItemPickaxe(14, EnumToolMaterial.WOOD).setUnlocalizedName("pickaxeWood");
        Item.axeWood = new ItemAxe(15, EnumToolMaterial.WOOD).setUnlocalizedName("hatchetWood");
        Item.swordStone = new ItemSword(16, EnumToolMaterial.STONE).setUnlocalizedName("swordStone");
        Item.shovelStone = new ItemSpade(17, EnumToolMaterial.STONE).setUnlocalizedName("shovelStone");
        Item.pickaxeStone = new ItemPickaxe(18, EnumToolMaterial.STONE).setUnlocalizedName("pickaxeStone");
        Item.axeStone = new ItemAxe(19, EnumToolMaterial.STONE).setUnlocalizedName("hatchetStone");
        Item.swordDiamond = new ItemSword(20, EnumToolMaterial.EMERALD).setUnlocalizedName("swordDiamond");
        Item.shovelDiamond = new ItemSpade(21, EnumToolMaterial.EMERALD).setUnlocalizedName("shovelDiamond");
        Item.pickaxeDiamond = new ItemPickaxe(22, EnumToolMaterial.EMERALD).setUnlocalizedName("pickaxeDiamond");
        Item.axeDiamond = new ItemAxe(23, EnumToolMaterial.EMERALD).setUnlocalizedName("hatchetDiamond");
        Item.stick = new Item(24).setFull3D().setUnlocalizedName("stick").setCreativeTab(CreativeTabs.tabMaterials);
        Item.bowlEmpty = new Item(25).setUnlocalizedName("bowl").setCreativeTab(CreativeTabs.tabMaterials);
        Item.bowlSoup = new ItemSoup(26, 6).setUnlocalizedName("mushroomStew");
        Item.swordGold = new ItemSword(27, EnumToolMaterial.GOLD).setUnlocalizedName("swordGold");
        Item.shovelGold = new ItemSpade(28, EnumToolMaterial.GOLD).setUnlocalizedName("shovelGold");
        Item.pickaxeGold = new ItemPickaxe(29, EnumToolMaterial.GOLD).setUnlocalizedName("pickaxeGold");
        Item.axeGold = new ItemAxe(30, EnumToolMaterial.GOLD).setUnlocalizedName("hatchetGold");
        Item.silk = new ItemReed(31, Block.tripWire).setUnlocalizedName("string").setCreativeTab(CreativeTabs.tabMaterials);
        Item.feather = new Item(32).setUnlocalizedName("feather").setCreativeTab(CreativeTabs.tabMaterials);
        Item.gunpowder = new Item(33).setUnlocalizedName("sulphur").setPotionEffect(PotionHelper.gunpowderEffect).setCreativeTab(CreativeTabs.tabMaterials);
        Item.hoeWood = new ItemHoe(34, EnumToolMaterial.WOOD).setUnlocalizedName("hoeWood");
        Item.hoeStone = new ItemHoe(35, EnumToolMaterial.STONE).setUnlocalizedName("hoeStone");
        Item.hoeIron = new ItemHoe(36, EnumToolMaterial.IRON).setUnlocalizedName("hoeIron");
        Item.hoeDiamond = new ItemHoe(37, EnumToolMaterial.EMERALD).setUnlocalizedName("hoeDiamond");
        Item.hoeGold = new ItemHoe(38, EnumToolMaterial.GOLD).setUnlocalizedName("hoeGold");
        Item.seeds = new ItemSeeds(39, Block.crops.blockID, Block.tilledField.blockID).setUnlocalizedName("seeds");
        Item.wheat = new Item(40).setUnlocalizedName("wheat").setCreativeTab(CreativeTabs.tabMaterials);
        Item.bread = new ItemFood(41, 5, 0.6f, false).setUnlocalizedName("bread");
        Item.helmetLeather = (ItemArmor)new ItemArmor(42, EnumArmorMaterial.CLOTH, 0, 0).setUnlocalizedName("helmetCloth");
        Item.plateLeather = (ItemArmor)new ItemArmor(43, EnumArmorMaterial.CLOTH, 0, 1).setUnlocalizedName("chestplateCloth");
        Item.legsLeather = (ItemArmor)new ItemArmor(44, EnumArmorMaterial.CLOTH, 0, 2).setUnlocalizedName("leggingsCloth");
        Item.bootsLeather = (ItemArmor)new ItemArmor(45, EnumArmorMaterial.CLOTH, 0, 3).setUnlocalizedName("bootsCloth");
        Item.helmetChain = (ItemArmor)new ItemArmor(46, EnumArmorMaterial.CHAIN, 1, 0).setUnlocalizedName("helmetChain");
        Item.plateChain = (ItemArmor)new ItemArmor(47, EnumArmorMaterial.CHAIN, 1, 1).setUnlocalizedName("chestplateChain");
        Item.legsChain = (ItemArmor)new ItemArmor(48, EnumArmorMaterial.CHAIN, 1, 2).setUnlocalizedName("leggingsChain");
        Item.bootsChain = (ItemArmor)new ItemArmor(49, EnumArmorMaterial.CHAIN, 1, 3).setUnlocalizedName("bootsChain");
        Item.helmetIron = (ItemArmor)new ItemArmor(50, EnumArmorMaterial.IRON, 2, 0).setUnlocalizedName("helmetIron");
        Item.plateIron = (ItemArmor)new ItemArmor(51, EnumArmorMaterial.IRON, 2, 1).setUnlocalizedName("chestplateIron");
        Item.legsIron = (ItemArmor)new ItemArmor(52, EnumArmorMaterial.IRON, 2, 2).setUnlocalizedName("leggingsIron");
        Item.bootsIron = (ItemArmor)new ItemArmor(53, EnumArmorMaterial.IRON, 2, 3).setUnlocalizedName("bootsIron");
        Item.helmetDiamond = (ItemArmor)new ItemArmor(54, EnumArmorMaterial.DIAMOND, 3, 0).setUnlocalizedName("helmetDiamond");
        Item.plateDiamond = (ItemArmor)new ItemArmor(55, EnumArmorMaterial.DIAMOND, 3, 1).setUnlocalizedName("chestplateDiamond");
        Item.legsDiamond = (ItemArmor)new ItemArmor(56, EnumArmorMaterial.DIAMOND, 3, 2).setUnlocalizedName("leggingsDiamond");
        Item.bootsDiamond = (ItemArmor)new ItemArmor(57, EnumArmorMaterial.DIAMOND, 3, 3).setUnlocalizedName("bootsDiamond");
        Item.helmetGold = (ItemArmor)new ItemArmor(58, EnumArmorMaterial.GOLD, 4, 0).setUnlocalizedName("helmetGold");
        Item.plateGold = (ItemArmor)new ItemArmor(59, EnumArmorMaterial.GOLD, 4, 1).setUnlocalizedName("chestplateGold");
        Item.legsGold = (ItemArmor)new ItemArmor(60, EnumArmorMaterial.GOLD, 4, 2).setUnlocalizedName("leggingsGold");
        Item.bootsGold = (ItemArmor)new ItemArmor(61, EnumArmorMaterial.GOLD, 4, 3).setUnlocalizedName("bootsGold");
        Item.flint = new Item(62).setUnlocalizedName("flint").setCreativeTab(CreativeTabs.tabMaterials);
        Item.porkRaw = new ItemFood(63, 3, 0.3f, true).setUnlocalizedName("porkchopRaw");
        Item.porkCooked = new ItemFood(64, 8, 0.8f, true).setUnlocalizedName("porkchopCooked");
        Item.painting = new ItemHangingEntity(65, EntityPainting.class).setUnlocalizedName("painting");
        Item.appleGold = new ItemAppleGold(66, 4, 1.2f, false).setAlwaysEdible().setPotionEffect(Potion.regeneration.id, 5, 0, 1.0f).setUnlocalizedName("appleGold");
        Item.sign = new ItemSign(67).setUnlocalizedName("sign");
        Item.doorWood = new ItemDoor(68, Material.wood).setUnlocalizedName("doorWood");
        Item.bucketEmpty = new ItemBucket(69, 0).setUnlocalizedName("bucket").setMaxStackSize(16);
        Item.bucketWater = new ItemBucket(70, Block.waterMoving.blockID).setUnlocalizedName("bucketWater").setContainerItem(Item.bucketEmpty);
        Item.bucketLava = new ItemBucket(71, Block.lavaMoving.blockID).setUnlocalizedName("bucketLava").setContainerItem(Item.bucketEmpty);
        Item.minecartEmpty = new ItemMinecart(72, 0).setUnlocalizedName("minecart");
        Item.saddle = new ItemSaddle(73).setUnlocalizedName("saddle");
        Item.doorIron = new ItemDoor(74, Material.iron).setUnlocalizedName("doorIron");
        Item.redstone = new ItemRedstone(75).setUnlocalizedName("redstone").setPotionEffect(PotionHelper.redstoneEffect);
        Item.snowball = new ItemSnowball(76).setUnlocalizedName("snowball");
        Item.boat = new ItemBoat(77).setUnlocalizedName("boat");
        Item.leather = new Item(78).setUnlocalizedName("leather").setCreativeTab(CreativeTabs.tabMaterials);
        Item.bucketMilk = new ItemBucketMilk(79).setUnlocalizedName("milk").setContainerItem(Item.bucketEmpty);
        Item.brick = new Item(80).setUnlocalizedName("brick").setCreativeTab(CreativeTabs.tabMaterials);
        Item.clay = new Item(81).setUnlocalizedName("clay").setCreativeTab(CreativeTabs.tabMaterials);
        Item.reed = new ItemReed(82, Block.reed).setUnlocalizedName("reeds").setCreativeTab(CreativeTabs.tabMaterials);
        Item.paper = new Item(83).setUnlocalizedName("paper").setCreativeTab(CreativeTabs.tabMisc);
        Item.book = new ItemBook(84).setUnlocalizedName("book").setCreativeTab(CreativeTabs.tabMisc);
        Item.slimeBall = new Item(85).setUnlocalizedName("slimeball").setCreativeTab(CreativeTabs.tabMisc);
        Item.minecartCrate = new ItemMinecart(86, 1).setUnlocalizedName("minecartChest");
        Item.minecartPowered = new ItemMinecart(87, 2).setUnlocalizedName("minecartFurnace");
        Item.egg = new ItemEgg(88).setUnlocalizedName("egg");
        Item.compass = new Item(89).setUnlocalizedName("compass").setCreativeTab(CreativeTabs.tabTools);
        Item.fishingRod = (ItemFishingRod)new ItemFishingRod(90).setUnlocalizedName("fishingRod");
        Item.pocketSundial = new Item(91).setUnlocalizedName("clock").setCreativeTab(CreativeTabs.tabTools);
        Item.lightStoneDust = new Item(92).setUnlocalizedName("yellowDust").setPotionEffect(PotionHelper.glowstoneEffect).setCreativeTab(CreativeTabs.tabMaterials);
        Item.fishRaw = new ItemFood(93, 2, 0.3f, false).setUnlocalizedName("fishRaw");
        Item.fishCooked = new ItemFood(94, 5, 0.6f, false).setUnlocalizedName("fishCooked");
        Item.dyePowder = new ItemDye(95).setUnlocalizedName("dyePowder");
        Item.bone = new Item(96).setUnlocalizedName("bone").setFull3D().setCreativeTab(CreativeTabs.tabMisc);
        Item.sugar = new Item(97).setUnlocalizedName("sugar").setPotionEffect(PotionHelper.sugarEffect).setCreativeTab(CreativeTabs.tabMaterials);
        Item.cake = new ItemReed(98, Block.cake).setMaxStackSize(1).setUnlocalizedName("cake").setCreativeTab(CreativeTabs.tabFood);
        Item.bed = new ItemBed(99).setMaxStackSize(1).setUnlocalizedName("bed");
        Item.redstoneRepeater = new ItemReed(100, Block.redstoneRepeaterIdle).setUnlocalizedName("diode").setCreativeTab(CreativeTabs.tabRedstone);
        Item.cookie = new ItemFood(101, 2, 0.1f, false).setUnlocalizedName("cookie");
        Item.map = (ItemMap)new ItemMap(102).setUnlocalizedName("map");
        Item.shears = (ItemShears)new ItemShears(103).setUnlocalizedName("shears");
        Item.melon = new ItemFood(104, 2, 0.3f, false).setUnlocalizedName("melon");
        Item.pumpkinSeeds = new ItemSeeds(105, Block.pumpkinStem.blockID, Block.tilledField.blockID).setUnlocalizedName("seeds_pumpkin");
        Item.melonSeeds = new ItemSeeds(106, Block.melonStem.blockID, Block.tilledField.blockID).setUnlocalizedName("seeds_melon");
        Item.beefRaw = new ItemFood(107, 3, 0.3f, true).setUnlocalizedName("beefRaw");
        Item.beefCooked = new ItemFood(108, 8, 0.8f, true).setUnlocalizedName("beefCooked");
        Item.chickenRaw = new ItemFood(109, 2, 0.3f, true).setPotionEffect(Potion.hunger.id, 30, 0, 0.3f).setUnlocalizedName("chickenRaw");
        Item.chickenCooked = new ItemFood(110, 6, 0.6f, true).setUnlocalizedName("chickenCooked");
        Item.rottenFlesh = new ItemFood(111, 4, 0.1f, true).setPotionEffect(Potion.hunger.id, 30, 0, 0.8f).setUnlocalizedName("rottenFlesh");
        Item.enderPearl = new ItemEnderPearl(112).setUnlocalizedName("enderPearl");
        Item.blazeRod = new Item(113).setUnlocalizedName("blazeRod").setCreativeTab(CreativeTabs.tabMaterials);
        Item.ghastTear = new Item(114).setUnlocalizedName("ghastTear").setPotionEffect("+0-1-2-3&4-4+13").setCreativeTab(CreativeTabs.tabBrewing);
        Item.goldNugget = new Item(115).setUnlocalizedName("goldNugget").setCreativeTab(CreativeTabs.tabMaterials);
        Item.netherStalkSeeds = new ItemSeeds(116, Block.netherStalk.blockID, Block.slowSand.blockID).setUnlocalizedName("netherStalkSeeds").setPotionEffect("+4");
        Item.potion = (ItemPotion)new ItemPotion(117).setUnlocalizedName("potion");
        Item.glassBottle = new ItemGlassBottle(118).setUnlocalizedName("glassBottle");
        Item.spiderEye = new ItemFood(119, 2, 0.8f, false).setPotionEffect(Potion.poison.id, 5, 0, 1.0f).setUnlocalizedName("spiderEye").setPotionEffect(PotionHelper.spiderEyeEffect);
        Item.fermentedSpiderEye = new Item(120).setUnlocalizedName("fermentedSpiderEye").setPotionEffect(PotionHelper.fermentedSpiderEyeEffect).setCreativeTab(CreativeTabs.tabBrewing);
        Item.blazePowder = new Item(121).setUnlocalizedName("blazePowder").setPotionEffect(PotionHelper.blazePowderEffect).setCreativeTab(CreativeTabs.tabBrewing);
        Item.magmaCream = new Item(122).setUnlocalizedName("magmaCream").setPotionEffect(PotionHelper.magmaCreamEffect).setCreativeTab(CreativeTabs.tabBrewing);
        Item.brewingStand = new ItemReed(123, Block.brewingStand).setUnlocalizedName("brewingStand").setCreativeTab(CreativeTabs.tabBrewing);
        Item.cauldron = new ItemReed(124, Block.cauldron).setUnlocalizedName("cauldron").setCreativeTab(CreativeTabs.tabBrewing);
        Item.eyeOfEnder = new ItemEnderEye(125).setUnlocalizedName("eyeOfEnder");
        Item.speckledMelon = new Item(126).setUnlocalizedName("speckledMelon").setPotionEffect(PotionHelper.speckledMelonEffect).setCreativeTab(CreativeTabs.tabBrewing);
        Item.monsterPlacer = new ItemMonsterPlacer(127).setUnlocalizedName("monsterPlacer");
        Item.expBottle = new ItemExpBottle(128).setUnlocalizedName("expBottle");
        Item.fireballCharge = new ItemFireball(129).setUnlocalizedName("fireball");
        Item.writableBook = new ItemWritableBook(130).setUnlocalizedName("writingBook").setCreativeTab(CreativeTabs.tabMisc);
        Item.writtenBook = new ItemEditableBook(131).setUnlocalizedName("writtenBook");
        Item.emerald = new Item(132).setUnlocalizedName("emerald").setCreativeTab(CreativeTabs.tabMaterials);
        Item.itemFrame = new ItemHangingEntity(133, EntityItemFrame.class).setUnlocalizedName("frame");
        Item.flowerPot = new ItemReed(134, Block.flowerPot).setUnlocalizedName("flowerPot").setCreativeTab(CreativeTabs.tabDecorations);
        Item.carrot = new ItemSeedFood(135, 4, 0.6f, Block.carrot.blockID, Block.tilledField.blockID).setUnlocalizedName("carrots");
        Item.potato = new ItemSeedFood(136, 1, 0.3f, Block.potato.blockID, Block.tilledField.blockID).setUnlocalizedName("potato");
        Item.bakedPotato = new ItemFood(137, 6, 0.6f, false).setUnlocalizedName("potatoBaked");
        Item.poisonousPotato = new ItemFood(138, 2, 0.3f, false).setPotionEffect(Potion.poison.id, 5, 0, 0.6f).setUnlocalizedName("potatoPoisonous");
        Item.emptyMap = (ItemEmptyMap)new ItemEmptyMap(139).setUnlocalizedName("emptyMap");
        Item.goldenCarrot = new ItemFood(140, 6, 1.2f, false).setUnlocalizedName("carrotGolden").setPotionEffect(PotionHelper.goldenCarrotEffect);
        Item.skull = new ItemSkull(141).setUnlocalizedName("skull");
        Item.carrotOnAStick = new ItemCarrotOnAStick(142).setUnlocalizedName("carrotOnAStick");
        Item.netherStar = new ItemSimpleFoiled(143).setUnlocalizedName("netherStar").setCreativeTab(CreativeTabs.tabMaterials);
        Item.pumpkinPie = new ItemFood(144, 8, 0.3f, false).setUnlocalizedName("pumpkinPie").setCreativeTab(CreativeTabs.tabFood);
        Item.firework = new ItemFirework(145).setUnlocalizedName("fireworks");
        Item.fireworkCharge = new ItemFireworkCharge(146).setUnlocalizedName("fireworksCharge").setCreativeTab(CreativeTabs.tabMisc);
        Item.enchantedBook = (ItemEnchantedBook)new ItemEnchantedBook(147).setMaxStackSize(1).setUnlocalizedName("enchantedBook");
        Item.comparator = new ItemReed(148, Block.redstoneComparatorIdle).setUnlocalizedName("comparator").setCreativeTab(CreativeTabs.tabRedstone);
        Item.netherrackBrick = new Item(149).setUnlocalizedName("netherbrick").setCreativeTab(CreativeTabs.tabMaterials);
        Item.netherQuartz = new Item(150).setUnlocalizedName("netherquartz").setCreativeTab(CreativeTabs.tabMaterials);
        Item.minecartTnt = new ItemMinecart(151, 3).setUnlocalizedName("minecartTnt");
        Item.minecartHopper = new ItemMinecart(152, 5).setUnlocalizedName("minecartHopper");
        Item.record13 = new ItemRecord(2000, "13").setUnlocalizedName("record");
        Item.recordCat = new ItemRecord(2001, "cat").setUnlocalizedName("record");
        Item.recordBlocks = new ItemRecord(2002, "blocks").setUnlocalizedName("record");
        Item.recordChirp = new ItemRecord(2003, "chirp").setUnlocalizedName("record");
        Item.recordFar = new ItemRecord(2004, "far").setUnlocalizedName("record");
        Item.recordMall = new ItemRecord(2005, "mall").setUnlocalizedName("record");
        Item.recordMellohi = new ItemRecord(2006, "mellohi").setUnlocalizedName("record");
        Item.recordStal = new ItemRecord(2007, "stal").setUnlocalizedName("record");
        Item.recordStrad = new ItemRecord(2008, "strad").setUnlocalizedName("record");
        Item.recordWard = new ItemRecord(2009, "ward").setUnlocalizedName("record");
        Item.record11 = new ItemRecord(2010, "11").setUnlocalizedName("record");
        Item.recordWait = new ItemRecord(2011, "wait").setUnlocalizedName("record");
        StatList.initStats();
    }
    
    protected Item(final int par1) {
        this.tabToDisplayOn = null;
        this.maxStackSize = 64;
        this.maxDamage = 0;
        this.bFull3D = false;
        this.hasSubtypes = false;
        this.containerItem = null;
        this.potionEffect = null;
        this.itemID = 256 + par1;
        if (Item.itemsList[256 + par1] != null) {
            System.out.println("CONFLICT @ " + par1);
        }
        Item.itemsList[256 + par1] = this;
    }
    
    public Item setMaxStackSize(final int par1) {
        this.maxStackSize = par1;
        return this;
    }
    
    public int getSpriteNumber() {
        return 1;
    }
    
    public Icon getIconFromDamage(final int par1) {
        return this.itemIcon;
    }
    
    public final Icon getIconIndex(final ItemStack par1ItemStack) {
        return this.getIconFromDamage(par1ItemStack.getItemDamage());
    }
    
    public boolean onItemUse(final ItemStack par1ItemStack, final EntityPlayer par2EntityPlayer, final World par3World, final int par4, final int par5, final int par6, final int par7, final float par8, final float par9, final float par10) {
        return false;
    }
    
    public float getStrVsBlock(final ItemStack par1ItemStack, final Block par2Block) {
        return 1.0f;
    }
    
    public ItemStack onItemRightClick(final ItemStack par1ItemStack, final World par2World, final EntityPlayer par3EntityPlayer) {
        return par1ItemStack;
    }
    
    public ItemStack onEaten(final ItemStack par1ItemStack, final World par2World, final EntityPlayer par3EntityPlayer) {
        return par1ItemStack;
    }
    
    public int getItemStackLimit() {
        return this.maxStackSize;
    }
    
    public int getMetadata(final int par1) {
        return 0;
    }
    
    public boolean getHasSubtypes() {
        return this.hasSubtypes;
    }
    
    protected Item setHasSubtypes(final boolean par1) {
        this.hasSubtypes = par1;
        return this;
    }
    
    public int getMaxDamage() {
        return this.maxDamage;
    }
    
    protected Item setMaxDamage(final int par1) {
        this.maxDamage = par1;
        return this;
    }
    
    public boolean isDamageable() {
        return this.maxDamage > 0 && !this.hasSubtypes;
    }
    
    public boolean hitEntity(final ItemStack par1ItemStack, final EntityLiving par2EntityLiving, final EntityLiving par3EntityLiving) {
        return false;
    }
    
    public boolean onBlockDestroyed(final ItemStack par1ItemStack, final World par2World, final int par3, final int par4, final int par5, final int par6, final EntityLiving par7EntityLiving) {
        return false;
    }
    
    public int getDamageVsEntity(final Entity par1Entity) {
        return 1;
    }
    
    public boolean canHarvestBlock(final Block par1Block) {
        return false;
    }
    
    public boolean itemInteractionForEntity(final ItemStack par1ItemStack, final EntityLiving par2EntityLiving) {
        return false;
    }
    
    public Item setFull3D() {
        this.bFull3D = true;
        return this;
    }
    
    public boolean isFull3D() {
        return this.bFull3D;
    }
    
    public boolean shouldRotateAroundWhenRendering() {
        return false;
    }
    
    public Item setUnlocalizedName(final String par1Str) {
        this.unlocalizedName = par1Str;
        return this;
    }
    
    public String getLocalizedName(final ItemStack par1ItemStack) {
        final String var2 = this.getUnlocalizedName(par1ItemStack);
        return (var2 == null) ? "" : StatCollector.translateToLocal(var2);
    }
    
    public String getUnlocalizedName() {
        return "item." + this.unlocalizedName;
    }
    
    public String getUnlocalizedName(final ItemStack par1ItemStack) {
        return "item." + this.unlocalizedName;
    }
    
    public Item setContainerItem(final Item par1Item) {
        this.containerItem = par1Item;
        return this;
    }
    
    public boolean doesContainerItemLeaveCraftingGrid(final ItemStack par1ItemStack) {
        return true;
    }
    
    public boolean getShareTag() {
        return true;
    }
    
    public Item getContainerItem() {
        return this.containerItem;
    }
    
    public boolean hasContainerItem() {
        return this.containerItem != null;
    }
    
    public String getStatName() {
        return StatCollector.translateToLocal(String.valueOf(this.getUnlocalizedName()) + ".name");
    }
    
    public String func_77653_i(final ItemStack par1ItemStack) {
        return StatCollector.translateToLocal(String.valueOf(this.getUnlocalizedName(par1ItemStack)) + ".name");
    }
    
    public int getColorFromItemStack(final ItemStack par1ItemStack, final int par2) {
        return 16777215;
    }
    
    public void onUpdate(final ItemStack par1ItemStack, final World par2World, final Entity par3Entity, final int par4, final boolean par5) {
    }
    
    public void onCreated(final ItemStack par1ItemStack, final World par2World, final EntityPlayer par3EntityPlayer) {
    }
    
    public boolean isMap() {
        return false;
    }
    
    public EnumAction getItemUseAction(final ItemStack par1ItemStack) {
        return EnumAction.none;
    }
    
    public int getMaxItemUseDuration(final ItemStack par1ItemStack) {
        return 0;
    }
    
    public void onPlayerStoppedUsing(final ItemStack par1ItemStack, final World par2World, final EntityPlayer par3EntityPlayer, final int par4) {
    }
    
    protected Item setPotionEffect(final String par1Str) {
        this.potionEffect = par1Str;
        return this;
    }
    
    public String getPotionEffect() {
        return this.potionEffect;
    }
    
    public boolean isPotionIngredient() {
        return this.potionEffect != null;
    }
    
    public void addInformation(final ItemStack par1ItemStack, final EntityPlayer par2EntityPlayer, final List par3List, final boolean par4) {
    }
    
    public String getItemDisplayName(final ItemStack par1ItemStack) {
        return new StringBuilder().append(StringTranslate.getInstance().translateNamedKey(this.getLocalizedName(par1ItemStack))).toString().trim();
    }
    
    public boolean hasEffect(final ItemStack par1ItemStack) {
        return par1ItemStack.isItemEnchanted();
    }
    
    public EnumRarity getRarity(final ItemStack par1ItemStack) {
        return par1ItemStack.isItemEnchanted() ? EnumRarity.rare : EnumRarity.common;
    }
    
    public boolean isItemTool(final ItemStack par1ItemStack) {
        return this.getItemStackLimit() == 1 && this.isDamageable();
    }
    
    protected MovingObjectPosition getMovingObjectPositionFromPlayer(final World par1World, final EntityPlayer par2EntityPlayer, final boolean par3) {
        final float var4 = 1.0f;
        final float var5 = par2EntityPlayer.prevRotationPitch + (par2EntityPlayer.rotationPitch - par2EntityPlayer.prevRotationPitch) * var4;
        final float var6 = par2EntityPlayer.prevRotationYaw + (par2EntityPlayer.rotationYaw - par2EntityPlayer.prevRotationYaw) * var4;
        final double var7 = par2EntityPlayer.prevPosX + (par2EntityPlayer.posX - par2EntityPlayer.prevPosX) * var4;
        final double var8 = par2EntityPlayer.prevPosY + (par2EntityPlayer.posY - par2EntityPlayer.prevPosY) * var4 + 1.62 - par2EntityPlayer.yOffset;
        final double var9 = par2EntityPlayer.prevPosZ + (par2EntityPlayer.posZ - par2EntityPlayer.prevPosZ) * var4;
        final Vec3 var10 = par1World.getWorldVec3Pool().getVecFromPool(var7, var8, var9);
        final float var11 = MathHelper.cos(-var6 * 0.017453292f - 3.1415927f);
        final float var12 = MathHelper.sin(-var6 * 0.017453292f - 3.1415927f);
        final float var13 = -MathHelper.cos(-var5 * 0.017453292f);
        final float var14 = MathHelper.sin(-var5 * 0.017453292f);
        final float var15 = var12 * var13;
        final float var16 = var11 * var13;
        final double var17 = 5.0;
        final Vec3 var18 = var10.addVector(var15 * var17, var14 * var17, var16 * var17);
        return par1World.rayTraceBlocks_do_do(var10, var18, par3, !par3);
    }
    
    public int getItemEnchantability() {
        return 0;
    }
    
    public boolean requiresMultipleRenderPasses() {
        return false;
    }
    
    public Icon getIconFromDamageForRenderPass(final int par1, final int par2) {
        return this.getIconFromDamage(par1);
    }
    
    public void getSubItems(final int par1, final CreativeTabs par2CreativeTabs, final List par3List) {
        par3List.add(new ItemStack(par1, 1, 0));
    }
    
    public CreativeTabs getCreativeTab() {
        return this.tabToDisplayOn;
    }
    
    public Item setCreativeTab(final CreativeTabs par1CreativeTabs) {
        this.tabToDisplayOn = par1CreativeTabs;
        return this;
    }
    
    public boolean func_82788_x() {
        return true;
    }
    
    public static int getIdFromItem(final Item var0) {
        return (var0 == null) ? 0 : var0.itemID;
    }
    
    public boolean getIsRepairable(final ItemStack par1ItemStack, final ItemStack par2ItemStack) {
        return false;
    }
    
    public void registerIcons(final IconRegister par1IconRegister) {
        this.itemIcon = par1IconRegister.registerIcon(this.unlocalizedName);
    }
}
