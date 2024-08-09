/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block;

import java.util.function.ToIntFunction;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.AirBlock;
import net.minecraft.block.AnvilBlock;
import net.minecraft.block.AttachedStemBlock;
import net.minecraft.block.BambooBlock;
import net.minecraft.block.BambooSaplingBlock;
import net.minecraft.block.BannerBlock;
import net.minecraft.block.BarrelBlock;
import net.minecraft.block.BarrierBlock;
import net.minecraft.block.BeaconBlock;
import net.minecraft.block.BedBlock;
import net.minecraft.block.BeehiveBlock;
import net.minecraft.block.BeetrootBlock;
import net.minecraft.block.BellBlock;
import net.minecraft.block.BlastFurnaceBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BreakableBlock;
import net.minecraft.block.BrewingStandBlock;
import net.minecraft.block.BubbleColumnBlock;
import net.minecraft.block.CactusBlock;
import net.minecraft.block.CakeBlock;
import net.minecraft.block.CampfireBlock;
import net.minecraft.block.CarpetBlock;
import net.minecraft.block.CarrotBlock;
import net.minecraft.block.CartographyTableBlock;
import net.minecraft.block.CarvedPumpkinBlock;
import net.minecraft.block.CauldronBlock;
import net.minecraft.block.ChainBlock;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.ChorusFlowerBlock;
import net.minecraft.block.ChorusPlantBlock;
import net.minecraft.block.CocoaBlock;
import net.minecraft.block.CommandBlockBlock;
import net.minecraft.block.ComparatorBlock;
import net.minecraft.block.ComposterBlock;
import net.minecraft.block.ConcretePowderBlock;
import net.minecraft.block.ConduitBlock;
import net.minecraft.block.CoralBlock;
import net.minecraft.block.CoralFanBlock;
import net.minecraft.block.CoralFinBlock;
import net.minecraft.block.CoralPlantBlock;
import net.minecraft.block.CoralWallFanBlock;
import net.minecraft.block.CraftingTableBlock;
import net.minecraft.block.CropsBlock;
import net.minecraft.block.CryingObsidianBlock;
import net.minecraft.block.DaylightDetectorBlock;
import net.minecraft.block.DeadBushBlock;
import net.minecraft.block.DeadCoralPlantBlock;
import net.minecraft.block.DeadCoralWallFanBlock;
import net.minecraft.block.DetectorRailBlock;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.DoublePlantBlock;
import net.minecraft.block.DragonEggBlock;
import net.minecraft.block.DropperBlock;
import net.minecraft.block.EnchantingTableBlock;
import net.minecraft.block.EndGatewayBlock;
import net.minecraft.block.EndPortalBlock;
import net.minecraft.block.EndPortalFrameBlock;
import net.minecraft.block.EndRodBlock;
import net.minecraft.block.EnderChestBlock;
import net.minecraft.block.FarmlandBlock;
import net.minecraft.block.FenceBlock;
import net.minecraft.block.FenceGateBlock;
import net.minecraft.block.FireBlock;
import net.minecraft.block.FletchingTableBlock;
import net.minecraft.block.FlowerBlock;
import net.minecraft.block.FlowerPotBlock;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.block.FrostedIceBlock;
import net.minecraft.block.FungusBlock;
import net.minecraft.block.FurnaceBlock;
import net.minecraft.block.GlassBlock;
import net.minecraft.block.GlazedTerracottaBlock;
import net.minecraft.block.GrassBlock;
import net.minecraft.block.GrassPathBlock;
import net.minecraft.block.GravelBlock;
import net.minecraft.block.GrindstoneBlock;
import net.minecraft.block.HayBlock;
import net.minecraft.block.HoneyBlock;
import net.minecraft.block.HopperBlock;
import net.minecraft.block.HugeMushroomBlock;
import net.minecraft.block.IceBlock;
import net.minecraft.block.JigsawBlock;
import net.minecraft.block.JukeboxBlock;
import net.minecraft.block.KelpBlock;
import net.minecraft.block.KelpTopBlock;
import net.minecraft.block.LadderBlock;
import net.minecraft.block.LanternBlock;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.LecternBlock;
import net.minecraft.block.LeverBlock;
import net.minecraft.block.LilyPadBlock;
import net.minecraft.block.LoomBlock;
import net.minecraft.block.MagmaBlock;
import net.minecraft.block.MelonBlock;
import net.minecraft.block.MovingPistonBlock;
import net.minecraft.block.MushroomBlock;
import net.minecraft.block.MyceliumBlock;
import net.minecraft.block.NetherPortalBlock;
import net.minecraft.block.NetherRootsBlock;
import net.minecraft.block.NetherSproutsBlock;
import net.minecraft.block.NetherWartBlock;
import net.minecraft.block.NetherrackBlock;
import net.minecraft.block.NoteBlock;
import net.minecraft.block.NyliumBlock;
import net.minecraft.block.ObserverBlock;
import net.minecraft.block.OreBlock;
import net.minecraft.block.PaneBlock;
import net.minecraft.block.PistonBlock;
import net.minecraft.block.PistonHeadBlock;
import net.minecraft.block.PotatoBlock;
import net.minecraft.block.PoweredRailBlock;
import net.minecraft.block.PressurePlateBlock;
import net.minecraft.block.PumpkinBlock;
import net.minecraft.block.RailBlock;
import net.minecraft.block.RedstoneBlock;
import net.minecraft.block.RedstoneLampBlock;
import net.minecraft.block.RedstoneOreBlock;
import net.minecraft.block.RedstoneTorchBlock;
import net.minecraft.block.RedstoneWallTorchBlock;
import net.minecraft.block.RedstoneWireBlock;
import net.minecraft.block.RepeaterBlock;
import net.minecraft.block.RespawnAnchorBlock;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.block.SandBlock;
import net.minecraft.block.SaplingBlock;
import net.minecraft.block.ScaffoldingBlock;
import net.minecraft.block.SeaGrassBlock;
import net.minecraft.block.SeaPickleBlock;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.block.SilverfishBlock;
import net.minecraft.block.SkullBlock;
import net.minecraft.block.SkullPlayerBlock;
import net.minecraft.block.SkullWallPlayerBlock;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.SlimeBlock;
import net.minecraft.block.SmithingTableBlock;
import net.minecraft.block.SmokerBlock;
import net.minecraft.block.SnowBlock;
import net.minecraft.block.SnowyDirtBlock;
import net.minecraft.block.SoulFireBlock;
import net.minecraft.block.SoulSandBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.SpawnerBlock;
import net.minecraft.block.SpongeBlock;
import net.minecraft.block.StainedGlassBlock;
import net.minecraft.block.StainedGlassPaneBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.StandingSignBlock;
import net.minecraft.block.StemBlock;
import net.minecraft.block.StemGrownBlock;
import net.minecraft.block.StoneButtonBlock;
import net.minecraft.block.StonecutterBlock;
import net.minecraft.block.StructureBlock;
import net.minecraft.block.StructureVoidBlock;
import net.minecraft.block.SugarCaneBlock;
import net.minecraft.block.SweetBerryBushBlock;
import net.minecraft.block.TNTBlock;
import net.minecraft.block.TallFlowerBlock;
import net.minecraft.block.TallGrassBlock;
import net.minecraft.block.TallSeaGrassBlock;
import net.minecraft.block.TargetBlock;
import net.minecraft.block.TorchBlock;
import net.minecraft.block.TrapDoorBlock;
import net.minecraft.block.TrappedChestBlock;
import net.minecraft.block.TripWireBlock;
import net.minecraft.block.TripWireHookBlock;
import net.minecraft.block.TurtleEggBlock;
import net.minecraft.block.TwistingVinesBlock;
import net.minecraft.block.TwistingVinesTopBlock;
import net.minecraft.block.VineBlock;
import net.minecraft.block.WallBannerBlock;
import net.minecraft.block.WallBlock;
import net.minecraft.block.WallSignBlock;
import net.minecraft.block.WallSkullBlock;
import net.minecraft.block.WallTorchBlock;
import net.minecraft.block.WebBlock;
import net.minecraft.block.WeepingVinesBlock;
import net.minecraft.block.WeepingVinesTopBlock;
import net.minecraft.block.WeightedPressurePlateBlock;
import net.minecraft.block.WetSpongeBlock;
import net.minecraft.block.WitherRoseBlock;
import net.minecraft.block.WitherSkeletonSkullBlock;
import net.minecraft.block.WitherSkeletonWallSkullBlock;
import net.minecraft.block.WoodButtonBlock;
import net.minecraft.block.WoodType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.block.trees.AcaciaTree;
import net.minecraft.block.trees.BirchTree;
import net.minecraft.block.trees.DarkOakTree;
import net.minecraft.block.trees.JungleTree;
import net.minecraft.block.trees.OakTree;
import net.minecraft.block.trees.SpruceTree;
import net.minecraft.entity.EntityType;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.DyeColor;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.Effects;
import net.minecraft.state.properties.BedPart;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.ShulkerBoxTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Features;

public class Blocks {
    public static final Block AIR = Blocks.register("air", new AirBlock(AbstractBlock.Properties.create(Material.AIR).doesNotBlockMovement().noDrops().setAir()));
    public static final Block STONE = Blocks.register("stone", new Block(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.STONE).setRequiresTool().hardnessAndResistance(1.5f, 6.0f)));
    public static final Block GRANITE = Blocks.register("granite", new Block(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.DIRT).setRequiresTool().hardnessAndResistance(1.5f, 6.0f)));
    public static final Block POLISHED_GRANITE = Blocks.register("polished_granite", new Block(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.DIRT).setRequiresTool().hardnessAndResistance(1.5f, 6.0f)));
    public static final Block DIORITE = Blocks.register("diorite", new Block(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.QUARTZ).setRequiresTool().hardnessAndResistance(1.5f, 6.0f)));
    public static final Block POLISHED_DIORITE = Blocks.register("polished_diorite", new Block(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.QUARTZ).setRequiresTool().hardnessAndResistance(1.5f, 6.0f)));
    public static final Block ANDESITE = Blocks.register("andesite", new Block(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.STONE).setRequiresTool().hardnessAndResistance(1.5f, 6.0f)));
    public static final Block POLISHED_ANDESITE = Blocks.register("polished_andesite", new Block(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.STONE).setRequiresTool().hardnessAndResistance(1.5f, 6.0f)));
    public static final Block GRASS_BLOCK = Blocks.register("grass_block", new GrassBlock(AbstractBlock.Properties.create(Material.ORGANIC).tickRandomly().hardnessAndResistance(0.6f).sound(SoundType.PLANT)));
    public static final Block DIRT = Blocks.register("dirt", new Block(AbstractBlock.Properties.create(Material.EARTH, MaterialColor.DIRT).hardnessAndResistance(0.5f).sound(SoundType.GROUND)));
    public static final Block COARSE_DIRT = Blocks.register("coarse_dirt", new Block(AbstractBlock.Properties.create(Material.EARTH, MaterialColor.DIRT).hardnessAndResistance(0.5f).sound(SoundType.GROUND)));
    public static final Block PODZOL = Blocks.register("podzol", new SnowyDirtBlock(AbstractBlock.Properties.create(Material.EARTH, MaterialColor.OBSIDIAN).hardnessAndResistance(0.5f).sound(SoundType.GROUND)));
    public static final Block COBBLESTONE = Blocks.register("cobblestone", new Block(AbstractBlock.Properties.create(Material.ROCK).setRequiresTool().hardnessAndResistance(2.0f, 6.0f)));
    public static final Block OAK_PLANKS = Blocks.register("oak_planks", new Block(AbstractBlock.Properties.create(Material.WOOD, MaterialColor.WOOD).hardnessAndResistance(2.0f, 3.0f).sound(SoundType.WOOD)));
    public static final Block SPRUCE_PLANKS = Blocks.register("spruce_planks", new Block(AbstractBlock.Properties.create(Material.WOOD, MaterialColor.OBSIDIAN).hardnessAndResistance(2.0f, 3.0f).sound(SoundType.WOOD)));
    public static final Block BIRCH_PLANKS = Blocks.register("birch_planks", new Block(AbstractBlock.Properties.create(Material.WOOD, MaterialColor.SAND).hardnessAndResistance(2.0f, 3.0f).sound(SoundType.WOOD)));
    public static final Block JUNGLE_PLANKS = Blocks.register("jungle_planks", new Block(AbstractBlock.Properties.create(Material.WOOD, MaterialColor.DIRT).hardnessAndResistance(2.0f, 3.0f).sound(SoundType.WOOD)));
    public static final Block ACACIA_PLANKS = Blocks.register("acacia_planks", new Block(AbstractBlock.Properties.create(Material.WOOD, MaterialColor.ADOBE).hardnessAndResistance(2.0f, 3.0f).sound(SoundType.WOOD)));
    public static final Block DARK_OAK_PLANKS = Blocks.register("dark_oak_planks", new Block(AbstractBlock.Properties.create(Material.WOOD, MaterialColor.BROWN).hardnessAndResistance(2.0f, 3.0f).sound(SoundType.WOOD)));
    public static final Block OAK_SAPLING = Blocks.register("oak_sapling", new SaplingBlock(new OakTree(), AbstractBlock.Properties.create(Material.PLANTS).doesNotBlockMovement().tickRandomly().zeroHardnessAndResistance().sound(SoundType.PLANT)));
    public static final Block SPRUCE_SAPLING = Blocks.register("spruce_sapling", new SaplingBlock(new SpruceTree(), AbstractBlock.Properties.create(Material.PLANTS).doesNotBlockMovement().tickRandomly().zeroHardnessAndResistance().sound(SoundType.PLANT)));
    public static final Block BIRCH_SAPLING = Blocks.register("birch_sapling", new SaplingBlock(new BirchTree(), AbstractBlock.Properties.create(Material.PLANTS).doesNotBlockMovement().tickRandomly().zeroHardnessAndResistance().sound(SoundType.PLANT)));
    public static final Block JUNGLE_SAPLING = Blocks.register("jungle_sapling", new SaplingBlock(new JungleTree(), AbstractBlock.Properties.create(Material.PLANTS).doesNotBlockMovement().tickRandomly().zeroHardnessAndResistance().sound(SoundType.PLANT)));
    public static final Block ACACIA_SAPLING = Blocks.register("acacia_sapling", new SaplingBlock(new AcaciaTree(), AbstractBlock.Properties.create(Material.PLANTS).doesNotBlockMovement().tickRandomly().zeroHardnessAndResistance().sound(SoundType.PLANT)));
    public static final Block DARK_OAK_SAPLING = Blocks.register("dark_oak_sapling", new SaplingBlock(new DarkOakTree(), AbstractBlock.Properties.create(Material.PLANTS).doesNotBlockMovement().tickRandomly().zeroHardnessAndResistance().sound(SoundType.PLANT)));
    public static final Block BEDROCK = Blocks.register("bedrock", new Block(AbstractBlock.Properties.create(Material.ROCK).hardnessAndResistance(-1.0f, 3600000.0f).noDrops().setAllowsSpawn(Blocks::neverAllowSpawn)));
    public static final Block WATER = Blocks.register("water", new FlowingFluidBlock(Fluids.WATER, AbstractBlock.Properties.create(Material.WATER).doesNotBlockMovement().hardnessAndResistance(100.0f).noDrops()));
    public static final Block LAVA = Blocks.register("lava", new FlowingFluidBlock(Fluids.LAVA, AbstractBlock.Properties.create(Material.LAVA).doesNotBlockMovement().tickRandomly().hardnessAndResistance(100.0f).setLightLevel(Blocks::lambda$static$0).noDrops()));
    public static final Block SAND = Blocks.register("sand", new SandBlock(14406560, AbstractBlock.Properties.create(Material.SAND, MaterialColor.SAND).hardnessAndResistance(0.5f).sound(SoundType.SAND)));
    public static final Block RED_SAND = Blocks.register("red_sand", new SandBlock(11098145, AbstractBlock.Properties.create(Material.SAND, MaterialColor.ADOBE).hardnessAndResistance(0.5f).sound(SoundType.SAND)));
    public static final Block GRAVEL = Blocks.register("gravel", new GravelBlock(AbstractBlock.Properties.create(Material.SAND, MaterialColor.STONE).hardnessAndResistance(0.6f).sound(SoundType.GROUND)));
    public static final Block GOLD_ORE = Blocks.register("gold_ore", new OreBlock(AbstractBlock.Properties.create(Material.ROCK).setRequiresTool().hardnessAndResistance(3.0f, 3.0f)));
    public static final Block IRON_ORE = Blocks.register("iron_ore", new OreBlock(AbstractBlock.Properties.create(Material.ROCK).setRequiresTool().hardnessAndResistance(3.0f, 3.0f)));
    public static final Block COAL_ORE = Blocks.register("coal_ore", new OreBlock(AbstractBlock.Properties.create(Material.ROCK).setRequiresTool().hardnessAndResistance(3.0f, 3.0f)));
    public static final Block NETHER_GOLD_ORE = Blocks.register("nether_gold_ore", new OreBlock(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.NETHERRACK).setRequiresTool().hardnessAndResistance(3.0f, 3.0f).sound(SoundType.NETHER_GOLD)));
    public static final Block OAK_LOG = Blocks.register("oak_log", Blocks.createLogBlock(MaterialColor.WOOD, MaterialColor.OBSIDIAN));
    public static final Block SPRUCE_LOG = Blocks.register("spruce_log", Blocks.createLogBlock(MaterialColor.OBSIDIAN, MaterialColor.BROWN));
    public static final Block BIRCH_LOG = Blocks.register("birch_log", Blocks.createLogBlock(MaterialColor.SAND, MaterialColor.QUARTZ));
    public static final Block JUNGLE_LOG = Blocks.register("jungle_log", Blocks.createLogBlock(MaterialColor.DIRT, MaterialColor.OBSIDIAN));
    public static final Block ACACIA_LOG = Blocks.register("acacia_log", Blocks.createLogBlock(MaterialColor.ADOBE, MaterialColor.STONE));
    public static final Block DARK_OAK_LOG = Blocks.register("dark_oak_log", Blocks.createLogBlock(MaterialColor.BROWN, MaterialColor.BROWN));
    public static final Block STRIPPED_SPRUCE_LOG = Blocks.register("stripped_spruce_log", Blocks.createLogBlock(MaterialColor.OBSIDIAN, MaterialColor.OBSIDIAN));
    public static final Block STRIPPED_BIRCH_LOG = Blocks.register("stripped_birch_log", Blocks.createLogBlock(MaterialColor.SAND, MaterialColor.SAND));
    public static final Block STRIPPED_JUNGLE_LOG = Blocks.register("stripped_jungle_log", Blocks.createLogBlock(MaterialColor.DIRT, MaterialColor.DIRT));
    public static final Block STRIPPED_ACACIA_LOG = Blocks.register("stripped_acacia_log", Blocks.createLogBlock(MaterialColor.ADOBE, MaterialColor.ADOBE));
    public static final Block STRIPPED_DARK_OAK_LOG = Blocks.register("stripped_dark_oak_log", Blocks.createLogBlock(MaterialColor.BROWN, MaterialColor.BROWN));
    public static final Block STRIPPED_OAK_LOG = Blocks.register("stripped_oak_log", Blocks.createLogBlock(MaterialColor.WOOD, MaterialColor.WOOD));
    public static final Block OAK_WOOD = Blocks.register("oak_wood", new RotatedPillarBlock(AbstractBlock.Properties.create(Material.WOOD, MaterialColor.WOOD).hardnessAndResistance(2.0f).sound(SoundType.WOOD)));
    public static final Block SPRUCE_WOOD = Blocks.register("spruce_wood", new RotatedPillarBlock(AbstractBlock.Properties.create(Material.WOOD, MaterialColor.OBSIDIAN).hardnessAndResistance(2.0f).sound(SoundType.WOOD)));
    public static final Block BIRCH_WOOD = Blocks.register("birch_wood", new RotatedPillarBlock(AbstractBlock.Properties.create(Material.WOOD, MaterialColor.SAND).hardnessAndResistance(2.0f).sound(SoundType.WOOD)));
    public static final Block JUNGLE_WOOD = Blocks.register("jungle_wood", new RotatedPillarBlock(AbstractBlock.Properties.create(Material.WOOD, MaterialColor.DIRT).hardnessAndResistance(2.0f).sound(SoundType.WOOD)));
    public static final Block ACACIA_WOOD = Blocks.register("acacia_wood", new RotatedPillarBlock(AbstractBlock.Properties.create(Material.WOOD, MaterialColor.GRAY).hardnessAndResistance(2.0f).sound(SoundType.WOOD)));
    public static final Block DARK_OAK_WOOD = Blocks.register("dark_oak_wood", new RotatedPillarBlock(AbstractBlock.Properties.create(Material.WOOD, MaterialColor.BROWN).hardnessAndResistance(2.0f).sound(SoundType.WOOD)));
    public static final Block STRIPPED_OAK_WOOD = Blocks.register("stripped_oak_wood", new RotatedPillarBlock(AbstractBlock.Properties.create(Material.WOOD, MaterialColor.WOOD).hardnessAndResistance(2.0f).sound(SoundType.WOOD)));
    public static final Block STRIPPED_SPRUCE_WOOD = Blocks.register("stripped_spruce_wood", new RotatedPillarBlock(AbstractBlock.Properties.create(Material.WOOD, MaterialColor.OBSIDIAN).hardnessAndResistance(2.0f).sound(SoundType.WOOD)));
    public static final Block STRIPPED_BIRCH_WOOD = Blocks.register("stripped_birch_wood", new RotatedPillarBlock(AbstractBlock.Properties.create(Material.WOOD, MaterialColor.SAND).hardnessAndResistance(2.0f).sound(SoundType.WOOD)));
    public static final Block STRIPPED_JUNGLE_WOOD = Blocks.register("stripped_jungle_wood", new RotatedPillarBlock(AbstractBlock.Properties.create(Material.WOOD, MaterialColor.DIRT).hardnessAndResistance(2.0f).sound(SoundType.WOOD)));
    public static final Block STRIPPED_ACACIA_WOOD = Blocks.register("stripped_acacia_wood", new RotatedPillarBlock(AbstractBlock.Properties.create(Material.WOOD, MaterialColor.ADOBE).hardnessAndResistance(2.0f).sound(SoundType.WOOD)));
    public static final Block STRIPPED_DARK_OAK_WOOD = Blocks.register("stripped_dark_oak_wood", new RotatedPillarBlock(AbstractBlock.Properties.create(Material.WOOD, MaterialColor.BROWN).hardnessAndResistance(2.0f).sound(SoundType.WOOD)));
    public static final Block OAK_LEAVES = Blocks.register("oak_leaves", Blocks.createLeavesBlock());
    public static final Block SPRUCE_LEAVES = Blocks.register("spruce_leaves", Blocks.createLeavesBlock());
    public static final Block BIRCH_LEAVES = Blocks.register("birch_leaves", Blocks.createLeavesBlock());
    public static final Block JUNGLE_LEAVES = Blocks.register("jungle_leaves", Blocks.createLeavesBlock());
    public static final Block ACACIA_LEAVES = Blocks.register("acacia_leaves", Blocks.createLeavesBlock());
    public static final Block DARK_OAK_LEAVES = Blocks.register("dark_oak_leaves", Blocks.createLeavesBlock());
    public static final Block SPONGE = Blocks.register("sponge", new SpongeBlock(AbstractBlock.Properties.create(Material.SPONGE).hardnessAndResistance(0.6f).sound(SoundType.PLANT)));
    public static final Block WET_SPONGE = Blocks.register("wet_sponge", new WetSpongeBlock(AbstractBlock.Properties.create(Material.SPONGE).hardnessAndResistance(0.6f).sound(SoundType.PLANT)));
    public static final Block GLASS = Blocks.register("glass", new GlassBlock(AbstractBlock.Properties.create(Material.GLASS).hardnessAndResistance(0.3f).sound(SoundType.GLASS).notSolid().setAllowsSpawn(Blocks::neverAllowSpawn).setOpaque(Blocks::isntSolid).setSuffocates(Blocks::isntSolid).setBlocksVision(Blocks::isntSolid)));
    public static final Block LAPIS_ORE = Blocks.register("lapis_ore", new OreBlock(AbstractBlock.Properties.create(Material.ROCK).setRequiresTool().hardnessAndResistance(3.0f, 3.0f)));
    public static final Block LAPIS_BLOCK = Blocks.register("lapis_block", new Block(AbstractBlock.Properties.create(Material.IRON, MaterialColor.LAPIS).setRequiresTool().hardnessAndResistance(3.0f, 3.0f)));
    public static final Block DISPENSER = Blocks.register("dispenser", new DispenserBlock(AbstractBlock.Properties.create(Material.ROCK).setRequiresTool().hardnessAndResistance(3.5f)));
    public static final Block SANDSTONE = Blocks.register("sandstone", new Block(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.SAND).setRequiresTool().hardnessAndResistance(0.8f)));
    public static final Block CHISELED_SANDSTONE = Blocks.register("chiseled_sandstone", new Block(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.SAND).setRequiresTool().hardnessAndResistance(0.8f)));
    public static final Block CUT_SANDSTONE = Blocks.register("cut_sandstone", new Block(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.SAND).setRequiresTool().hardnessAndResistance(0.8f)));
    public static final Block NOTE_BLOCK = Blocks.register("note_block", new NoteBlock(AbstractBlock.Properties.create(Material.WOOD).sound(SoundType.WOOD).hardnessAndResistance(0.8f)));
    public static final Block WHITE_BED = Blocks.register("white_bed", Blocks.createBedFromColor(DyeColor.WHITE));
    public static final Block ORANGE_BED = Blocks.register("orange_bed", Blocks.createBedFromColor(DyeColor.ORANGE));
    public static final Block MAGENTA_BED = Blocks.register("magenta_bed", Blocks.createBedFromColor(DyeColor.MAGENTA));
    public static final Block LIGHT_BLUE_BED = Blocks.register("light_blue_bed", Blocks.createBedFromColor(DyeColor.LIGHT_BLUE));
    public static final Block YELLOW_BED = Blocks.register("yellow_bed", Blocks.createBedFromColor(DyeColor.YELLOW));
    public static final Block LIME_BED = Blocks.register("lime_bed", Blocks.createBedFromColor(DyeColor.LIME));
    public static final Block PINK_BED = Blocks.register("pink_bed", Blocks.createBedFromColor(DyeColor.PINK));
    public static final Block GRAY_BED = Blocks.register("gray_bed", Blocks.createBedFromColor(DyeColor.GRAY));
    public static final Block LIGHT_GRAY_BED = Blocks.register("light_gray_bed", Blocks.createBedFromColor(DyeColor.LIGHT_GRAY));
    public static final Block CYAN_BED = Blocks.register("cyan_bed", Blocks.createBedFromColor(DyeColor.CYAN));
    public static final Block PURPLE_BED = Blocks.register("purple_bed", Blocks.createBedFromColor(DyeColor.PURPLE));
    public static final Block BLUE_BED = Blocks.register("blue_bed", Blocks.createBedFromColor(DyeColor.BLUE));
    public static final Block BROWN_BED = Blocks.register("brown_bed", Blocks.createBedFromColor(DyeColor.BROWN));
    public static final Block GREEN_BED = Blocks.register("green_bed", Blocks.createBedFromColor(DyeColor.GREEN));
    public static final Block RED_BED = Blocks.register("red_bed", Blocks.createBedFromColor(DyeColor.RED));
    public static final Block BLACK_BED = Blocks.register("black_bed", Blocks.createBedFromColor(DyeColor.BLACK));
    public static final Block POWERED_RAIL = Blocks.register("powered_rail", new PoweredRailBlock(AbstractBlock.Properties.create(Material.MISCELLANEOUS).doesNotBlockMovement().hardnessAndResistance(0.7f).sound(SoundType.METAL)));
    public static final Block DETECTOR_RAIL = Blocks.register("detector_rail", new DetectorRailBlock(AbstractBlock.Properties.create(Material.MISCELLANEOUS).doesNotBlockMovement().hardnessAndResistance(0.7f).sound(SoundType.METAL)));
    public static final Block STICKY_PISTON = Blocks.register("sticky_piston", Blocks.createPiston(true));
    public static final Block COBWEB = Blocks.register("cobweb", new WebBlock(AbstractBlock.Properties.create(Material.WEB).doesNotBlockMovement().setRequiresTool().hardnessAndResistance(4.0f)));
    public static final Block GRASS = Blocks.register("grass", new TallGrassBlock(AbstractBlock.Properties.create(Material.TALL_PLANTS).doesNotBlockMovement().zeroHardnessAndResistance().sound(SoundType.PLANT)));
    public static final Block FERN = Blocks.register("fern", new TallGrassBlock(AbstractBlock.Properties.create(Material.TALL_PLANTS).doesNotBlockMovement().zeroHardnessAndResistance().sound(SoundType.PLANT)));
    public static final Block DEAD_BUSH = Blocks.register("dead_bush", new DeadBushBlock(AbstractBlock.Properties.create(Material.TALL_PLANTS, MaterialColor.WOOD).doesNotBlockMovement().zeroHardnessAndResistance().sound(SoundType.PLANT)));
    public static final Block SEAGRASS = Blocks.register("seagrass", new SeaGrassBlock(AbstractBlock.Properties.create(Material.SEA_GRASS).doesNotBlockMovement().zeroHardnessAndResistance().sound(SoundType.WET_GRASS)));
    public static final Block TALL_SEAGRASS = Blocks.register("tall_seagrass", new TallSeaGrassBlock(AbstractBlock.Properties.create(Material.SEA_GRASS).doesNotBlockMovement().zeroHardnessAndResistance().sound(SoundType.WET_GRASS)));
    public static final Block PISTON = Blocks.register("piston", Blocks.createPiston(false));
    public static final Block PISTON_HEAD = Blocks.register("piston_head", new PistonHeadBlock(AbstractBlock.Properties.create(Material.PISTON).hardnessAndResistance(1.5f).noDrops()));
    public static final Block WHITE_WOOL = Blocks.register("white_wool", new Block(AbstractBlock.Properties.create(Material.WOOL, MaterialColor.SNOW).hardnessAndResistance(0.8f).sound(SoundType.CLOTH)));
    public static final Block ORANGE_WOOL = Blocks.register("orange_wool", new Block(AbstractBlock.Properties.create(Material.WOOL, MaterialColor.ADOBE).hardnessAndResistance(0.8f).sound(SoundType.CLOTH)));
    public static final Block MAGENTA_WOOL = Blocks.register("magenta_wool", new Block(AbstractBlock.Properties.create(Material.WOOL, MaterialColor.MAGENTA).hardnessAndResistance(0.8f).sound(SoundType.CLOTH)));
    public static final Block LIGHT_BLUE_WOOL = Blocks.register("light_blue_wool", new Block(AbstractBlock.Properties.create(Material.WOOL, MaterialColor.LIGHT_BLUE).hardnessAndResistance(0.8f).sound(SoundType.CLOTH)));
    public static final Block YELLOW_WOOL = Blocks.register("yellow_wool", new Block(AbstractBlock.Properties.create(Material.WOOL, MaterialColor.YELLOW).hardnessAndResistance(0.8f).sound(SoundType.CLOTH)));
    public static final Block LIME_WOOL = Blocks.register("lime_wool", new Block(AbstractBlock.Properties.create(Material.WOOL, MaterialColor.LIME).hardnessAndResistance(0.8f).sound(SoundType.CLOTH)));
    public static final Block PINK_WOOL = Blocks.register("pink_wool", new Block(AbstractBlock.Properties.create(Material.WOOL, MaterialColor.PINK).hardnessAndResistance(0.8f).sound(SoundType.CLOTH)));
    public static final Block GRAY_WOOL = Blocks.register("gray_wool", new Block(AbstractBlock.Properties.create(Material.WOOL, MaterialColor.GRAY).hardnessAndResistance(0.8f).sound(SoundType.CLOTH)));
    public static final Block LIGHT_GRAY_WOOL = Blocks.register("light_gray_wool", new Block(AbstractBlock.Properties.create(Material.WOOL, MaterialColor.LIGHT_GRAY).hardnessAndResistance(0.8f).sound(SoundType.CLOTH)));
    public static final Block CYAN_WOOL = Blocks.register("cyan_wool", new Block(AbstractBlock.Properties.create(Material.WOOL, MaterialColor.CYAN).hardnessAndResistance(0.8f).sound(SoundType.CLOTH)));
    public static final Block PURPLE_WOOL = Blocks.register("purple_wool", new Block(AbstractBlock.Properties.create(Material.WOOL, MaterialColor.PURPLE).hardnessAndResistance(0.8f).sound(SoundType.CLOTH)));
    public static final Block BLUE_WOOL = Blocks.register("blue_wool", new Block(AbstractBlock.Properties.create(Material.WOOL, MaterialColor.BLUE).hardnessAndResistance(0.8f).sound(SoundType.CLOTH)));
    public static final Block BROWN_WOOL = Blocks.register("brown_wool", new Block(AbstractBlock.Properties.create(Material.WOOL, MaterialColor.BROWN).hardnessAndResistance(0.8f).sound(SoundType.CLOTH)));
    public static final Block GREEN_WOOL = Blocks.register("green_wool", new Block(AbstractBlock.Properties.create(Material.WOOL, MaterialColor.GREEN).hardnessAndResistance(0.8f).sound(SoundType.CLOTH)));
    public static final Block RED_WOOL = Blocks.register("red_wool", new Block(AbstractBlock.Properties.create(Material.WOOL, MaterialColor.RED).hardnessAndResistance(0.8f).sound(SoundType.CLOTH)));
    public static final Block BLACK_WOOL = Blocks.register("black_wool", new Block(AbstractBlock.Properties.create(Material.WOOL, MaterialColor.BLACK).hardnessAndResistance(0.8f).sound(SoundType.CLOTH)));
    public static final Block MOVING_PISTON = Blocks.register("moving_piston", new MovingPistonBlock(AbstractBlock.Properties.create(Material.PISTON).hardnessAndResistance(-1.0f).variableOpacity().noDrops().notSolid().setOpaque(Blocks::isntSolid).setSuffocates(Blocks::isntSolid).setBlocksVision(Blocks::isntSolid)));
    public static final Block DANDELION = Blocks.register("dandelion", new FlowerBlock(Effects.SATURATION, 7, AbstractBlock.Properties.create(Material.PLANTS).doesNotBlockMovement().zeroHardnessAndResistance().sound(SoundType.PLANT)));
    public static final Block POPPY = Blocks.register("poppy", new FlowerBlock(Effects.NIGHT_VISION, 5, AbstractBlock.Properties.create(Material.PLANTS).doesNotBlockMovement().zeroHardnessAndResistance().sound(SoundType.PLANT)));
    public static final Block BLUE_ORCHID = Blocks.register("blue_orchid", new FlowerBlock(Effects.SATURATION, 7, AbstractBlock.Properties.create(Material.PLANTS).doesNotBlockMovement().zeroHardnessAndResistance().sound(SoundType.PLANT)));
    public static final Block ALLIUM = Blocks.register("allium", new FlowerBlock(Effects.FIRE_RESISTANCE, 4, AbstractBlock.Properties.create(Material.PLANTS).doesNotBlockMovement().zeroHardnessAndResistance().sound(SoundType.PLANT)));
    public static final Block AZURE_BLUET = Blocks.register("azure_bluet", new FlowerBlock(Effects.BLINDNESS, 8, AbstractBlock.Properties.create(Material.PLANTS).doesNotBlockMovement().zeroHardnessAndResistance().sound(SoundType.PLANT)));
    public static final Block RED_TULIP = Blocks.register("red_tulip", new FlowerBlock(Effects.WEAKNESS, 9, AbstractBlock.Properties.create(Material.PLANTS).doesNotBlockMovement().zeroHardnessAndResistance().sound(SoundType.PLANT)));
    public static final Block ORANGE_TULIP = Blocks.register("orange_tulip", new FlowerBlock(Effects.WEAKNESS, 9, AbstractBlock.Properties.create(Material.PLANTS).doesNotBlockMovement().zeroHardnessAndResistance().sound(SoundType.PLANT)));
    public static final Block WHITE_TULIP = Blocks.register("white_tulip", new FlowerBlock(Effects.WEAKNESS, 9, AbstractBlock.Properties.create(Material.PLANTS).doesNotBlockMovement().zeroHardnessAndResistance().sound(SoundType.PLANT)));
    public static final Block PINK_TULIP = Blocks.register("pink_tulip", new FlowerBlock(Effects.WEAKNESS, 9, AbstractBlock.Properties.create(Material.PLANTS).doesNotBlockMovement().zeroHardnessAndResistance().sound(SoundType.PLANT)));
    public static final Block OXEYE_DAISY = Blocks.register("oxeye_daisy", new FlowerBlock(Effects.REGENERATION, 8, AbstractBlock.Properties.create(Material.PLANTS).doesNotBlockMovement().zeroHardnessAndResistance().sound(SoundType.PLANT)));
    public static final Block CORNFLOWER = Blocks.register("cornflower", new FlowerBlock(Effects.JUMP_BOOST, 6, AbstractBlock.Properties.create(Material.PLANTS).doesNotBlockMovement().zeroHardnessAndResistance().sound(SoundType.PLANT)));
    public static final Block WITHER_ROSE = Blocks.register("wither_rose", new WitherRoseBlock(Effects.WITHER, AbstractBlock.Properties.create(Material.PLANTS).doesNotBlockMovement().zeroHardnessAndResistance().sound(SoundType.PLANT)));
    public static final Block LILY_OF_THE_VALLEY = Blocks.register("lily_of_the_valley", new FlowerBlock(Effects.POISON, 12, AbstractBlock.Properties.create(Material.PLANTS).doesNotBlockMovement().zeroHardnessAndResistance().sound(SoundType.PLANT)));
    public static final Block BROWN_MUSHROOM = Blocks.register("brown_mushroom", new MushroomBlock(AbstractBlock.Properties.create(Material.PLANTS, MaterialColor.BROWN).doesNotBlockMovement().tickRandomly().zeroHardnessAndResistance().sound(SoundType.PLANT).setLightLevel(Blocks::lambda$static$1).setNeedsPostProcessing(Blocks::needsPostProcessing)));
    public static final Block RED_MUSHROOM = Blocks.register("red_mushroom", new MushroomBlock(AbstractBlock.Properties.create(Material.PLANTS, MaterialColor.RED).doesNotBlockMovement().tickRandomly().zeroHardnessAndResistance().sound(SoundType.PLANT).setNeedsPostProcessing(Blocks::needsPostProcessing)));
    public static final Block GOLD_BLOCK = Blocks.register("gold_block", new Block(AbstractBlock.Properties.create(Material.IRON, MaterialColor.GOLD).setRequiresTool().hardnessAndResistance(3.0f, 6.0f).sound(SoundType.METAL)));
    public static final Block IRON_BLOCK = Blocks.register("iron_block", new Block(AbstractBlock.Properties.create(Material.IRON, MaterialColor.IRON).setRequiresTool().hardnessAndResistance(5.0f, 6.0f).sound(SoundType.METAL)));
    public static final Block BRICKS = Blocks.register("bricks", new Block(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.RED).setRequiresTool().hardnessAndResistance(2.0f, 6.0f)));
    public static final Block TNT = Blocks.register("tnt", new TNTBlock(AbstractBlock.Properties.create(Material.TNT).zeroHardnessAndResistance().sound(SoundType.PLANT)));
    public static final Block BOOKSHELF = Blocks.register("bookshelf", new Block(AbstractBlock.Properties.create(Material.WOOD).hardnessAndResistance(1.5f).sound(SoundType.WOOD)));
    public static final Block MOSSY_COBBLESTONE = Blocks.register("mossy_cobblestone", new Block(AbstractBlock.Properties.create(Material.ROCK).setRequiresTool().hardnessAndResistance(2.0f, 6.0f)));
    public static final Block OBSIDIAN = Blocks.register("obsidian", new Block(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.BLACK).setRequiresTool().hardnessAndResistance(50.0f, 1200.0f)));
    public static final Block TORCH = Blocks.register("torch", new TorchBlock(AbstractBlock.Properties.create(Material.MISCELLANEOUS).doesNotBlockMovement().zeroHardnessAndResistance().setLightLevel(Blocks::lambda$static$2).sound(SoundType.WOOD), ParticleTypes.FLAME));
    public static final Block WALL_TORCH = Blocks.register("wall_torch", new WallTorchBlock(AbstractBlock.Properties.create(Material.MISCELLANEOUS).doesNotBlockMovement().zeroHardnessAndResistance().setLightLevel(Blocks::lambda$static$3).sound(SoundType.WOOD).lootFrom(TORCH), ParticleTypes.FLAME));
    public static final Block FIRE = Blocks.register("fire", new FireBlock(AbstractBlock.Properties.create(Material.FIRE, MaterialColor.TNT).doesNotBlockMovement().zeroHardnessAndResistance().setLightLevel(Blocks::lambda$static$4).sound(SoundType.CLOTH)));
    public static final Block SOUL_FIRE = Blocks.register("soul_fire", new SoulFireBlock(AbstractBlock.Properties.create(Material.FIRE, MaterialColor.LIGHT_BLUE).doesNotBlockMovement().zeroHardnessAndResistance().setLightLevel(Blocks::lambda$static$5).sound(SoundType.CLOTH)));
    public static final Block SPAWNER = Blocks.register("spawner", new SpawnerBlock(AbstractBlock.Properties.create(Material.ROCK).setRequiresTool().hardnessAndResistance(5.0f).sound(SoundType.METAL).notSolid()));
    public static final Block OAK_STAIRS = Blocks.register("oak_stairs", new StairsBlock(OAK_PLANKS.getDefaultState(), AbstractBlock.Properties.from(OAK_PLANKS)));
    public static final Block CHEST = Blocks.register("chest", new ChestBlock(AbstractBlock.Properties.create(Material.WOOD).hardnessAndResistance(2.5f).sound(SoundType.WOOD), Blocks::lambda$static$6));
    public static final Block REDSTONE_WIRE = Blocks.register("redstone_wire", new RedstoneWireBlock(AbstractBlock.Properties.create(Material.MISCELLANEOUS).doesNotBlockMovement().zeroHardnessAndResistance()));
    public static final Block DIAMOND_ORE = Blocks.register("diamond_ore", new OreBlock(AbstractBlock.Properties.create(Material.ROCK).setRequiresTool().hardnessAndResistance(3.0f, 3.0f)));
    public static final Block DIAMOND_BLOCK = Blocks.register("diamond_block", new Block(AbstractBlock.Properties.create(Material.IRON, MaterialColor.DIAMOND).setRequiresTool().hardnessAndResistance(5.0f, 6.0f).sound(SoundType.METAL)));
    public static final Block CRAFTING_TABLE = Blocks.register("crafting_table", new CraftingTableBlock(AbstractBlock.Properties.create(Material.WOOD).hardnessAndResistance(2.5f).sound(SoundType.WOOD)));
    public static final Block WHEAT = Blocks.register("wheat", new CropsBlock(AbstractBlock.Properties.create(Material.PLANTS).doesNotBlockMovement().tickRandomly().zeroHardnessAndResistance().sound(SoundType.CROP)));
    public static final Block FARMLAND = Blocks.register("farmland", new FarmlandBlock(AbstractBlock.Properties.create(Material.EARTH).tickRandomly().hardnessAndResistance(0.6f).sound(SoundType.GROUND).setBlocksVision(Blocks::needsPostProcessing).setSuffocates(Blocks::needsPostProcessing)));
    public static final Block FURNACE = Blocks.register("furnace", new FurnaceBlock(AbstractBlock.Properties.create(Material.ROCK).setRequiresTool().hardnessAndResistance(3.5f).setLightLevel(Blocks.getLightValueLit(13))));
    public static final Block OAK_SIGN = Blocks.register("oak_sign", new StandingSignBlock(AbstractBlock.Properties.create(Material.WOOD).doesNotBlockMovement().hardnessAndResistance(1.0f).sound(SoundType.WOOD), WoodType.OAK));
    public static final Block SPRUCE_SIGN = Blocks.register("spruce_sign", new StandingSignBlock(AbstractBlock.Properties.create(Material.WOOD, SPRUCE_LOG.getMaterialColor()).doesNotBlockMovement().hardnessAndResistance(1.0f).sound(SoundType.WOOD), WoodType.SPRUCE));
    public static final Block BIRCH_SIGN = Blocks.register("birch_sign", new StandingSignBlock(AbstractBlock.Properties.create(Material.WOOD, MaterialColor.SAND).doesNotBlockMovement().hardnessAndResistance(1.0f).sound(SoundType.WOOD), WoodType.BIRCH));
    public static final Block ACACIA_SIGN = Blocks.register("acacia_sign", new StandingSignBlock(AbstractBlock.Properties.create(Material.WOOD, MaterialColor.ADOBE).doesNotBlockMovement().hardnessAndResistance(1.0f).sound(SoundType.WOOD), WoodType.ACACIA));
    public static final Block JUNGLE_SIGN = Blocks.register("jungle_sign", new StandingSignBlock(AbstractBlock.Properties.create(Material.WOOD, JUNGLE_LOG.getMaterialColor()).doesNotBlockMovement().hardnessAndResistance(1.0f).sound(SoundType.WOOD), WoodType.JUNGLE));
    public static final Block DARK_OAK_SIGN = Blocks.register("dark_oak_sign", new StandingSignBlock(AbstractBlock.Properties.create(Material.WOOD, DARK_OAK_LOG.getMaterialColor()).doesNotBlockMovement().hardnessAndResistance(1.0f).sound(SoundType.WOOD), WoodType.DARK_OAK));
    public static final Block OAK_DOOR = Blocks.register("oak_door", new DoorBlock(AbstractBlock.Properties.create(Material.WOOD, OAK_PLANKS.getMaterialColor()).hardnessAndResistance(3.0f).sound(SoundType.WOOD).notSolid()));
    public static final Block LADDER = Blocks.register("ladder", new LadderBlock(AbstractBlock.Properties.create(Material.MISCELLANEOUS).hardnessAndResistance(0.4f).sound(SoundType.LADDER).notSolid()));
    public static final Block RAIL = Blocks.register("rail", new RailBlock(AbstractBlock.Properties.create(Material.MISCELLANEOUS).doesNotBlockMovement().hardnessAndResistance(0.7f).sound(SoundType.METAL)));
    public static final Block COBBLESTONE_STAIRS = Blocks.register("cobblestone_stairs", new StairsBlock(COBBLESTONE.getDefaultState(), AbstractBlock.Properties.from(COBBLESTONE)));
    public static final Block OAK_WALL_SIGN = Blocks.register("oak_wall_sign", new WallSignBlock(AbstractBlock.Properties.create(Material.WOOD).doesNotBlockMovement().hardnessAndResistance(1.0f).sound(SoundType.WOOD).lootFrom(OAK_SIGN), WoodType.OAK));
    public static final Block SPRUCE_WALL_SIGN = Blocks.register("spruce_wall_sign", new WallSignBlock(AbstractBlock.Properties.create(Material.WOOD, SPRUCE_LOG.getMaterialColor()).doesNotBlockMovement().hardnessAndResistance(1.0f).sound(SoundType.WOOD).lootFrom(SPRUCE_SIGN), WoodType.SPRUCE));
    public static final Block BIRCH_WALL_SIGN = Blocks.register("birch_wall_sign", new WallSignBlock(AbstractBlock.Properties.create(Material.WOOD, MaterialColor.SAND).doesNotBlockMovement().hardnessAndResistance(1.0f).sound(SoundType.WOOD).lootFrom(BIRCH_SIGN), WoodType.BIRCH));
    public static final Block ACACIA_WALL_SIGN = Blocks.register("acacia_wall_sign", new WallSignBlock(AbstractBlock.Properties.create(Material.WOOD, MaterialColor.ADOBE).doesNotBlockMovement().hardnessAndResistance(1.0f).sound(SoundType.WOOD).lootFrom(ACACIA_SIGN), WoodType.ACACIA));
    public static final Block JUNGLE_WALL_SIGN = Blocks.register("jungle_wall_sign", new WallSignBlock(AbstractBlock.Properties.create(Material.WOOD, JUNGLE_LOG.getMaterialColor()).doesNotBlockMovement().hardnessAndResistance(1.0f).sound(SoundType.WOOD).lootFrom(JUNGLE_SIGN), WoodType.JUNGLE));
    public static final Block DARK_OAK_WALL_SIGN = Blocks.register("dark_oak_wall_sign", new WallSignBlock(AbstractBlock.Properties.create(Material.WOOD, DARK_OAK_LOG.getMaterialColor()).doesNotBlockMovement().hardnessAndResistance(1.0f).sound(SoundType.WOOD).lootFrom(DARK_OAK_SIGN), WoodType.DARK_OAK));
    public static final Block LEVER = Blocks.register("lever", new LeverBlock(AbstractBlock.Properties.create(Material.MISCELLANEOUS).doesNotBlockMovement().hardnessAndResistance(0.5f).sound(SoundType.WOOD)));
    public static final Block STONE_PRESSURE_PLATE = Blocks.register("stone_pressure_plate", new PressurePlateBlock(PressurePlateBlock.Sensitivity.MOBS, AbstractBlock.Properties.create(Material.ROCK).setRequiresTool().doesNotBlockMovement().hardnessAndResistance(0.5f)));
    public static final Block IRON_DOOR = Blocks.register("iron_door", new DoorBlock(AbstractBlock.Properties.create(Material.IRON, MaterialColor.IRON).setRequiresTool().hardnessAndResistance(5.0f).sound(SoundType.METAL).notSolid()));
    public static final Block OAK_PRESSURE_PLATE = Blocks.register("oak_pressure_plate", new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, AbstractBlock.Properties.create(Material.WOOD, OAK_PLANKS.getMaterialColor()).doesNotBlockMovement().hardnessAndResistance(0.5f).sound(SoundType.WOOD)));
    public static final Block SPRUCE_PRESSURE_PLATE = Blocks.register("spruce_pressure_plate", new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, AbstractBlock.Properties.create(Material.WOOD, SPRUCE_PLANKS.getMaterialColor()).doesNotBlockMovement().hardnessAndResistance(0.5f).sound(SoundType.WOOD)));
    public static final Block BIRCH_PRESSURE_PLATE = Blocks.register("birch_pressure_plate", new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, AbstractBlock.Properties.create(Material.WOOD, BIRCH_PLANKS.getMaterialColor()).doesNotBlockMovement().hardnessAndResistance(0.5f).sound(SoundType.WOOD)));
    public static final Block JUNGLE_PRESSURE_PLATE = Blocks.register("jungle_pressure_plate", new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, AbstractBlock.Properties.create(Material.WOOD, JUNGLE_PLANKS.getMaterialColor()).doesNotBlockMovement().hardnessAndResistance(0.5f).sound(SoundType.WOOD)));
    public static final Block ACACIA_PRESSURE_PLATE = Blocks.register("acacia_pressure_plate", new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, AbstractBlock.Properties.create(Material.WOOD, ACACIA_PLANKS.getMaterialColor()).doesNotBlockMovement().hardnessAndResistance(0.5f).sound(SoundType.WOOD)));
    public static final Block DARK_OAK_PRESSURE_PLATE = Blocks.register("dark_oak_pressure_plate", new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, AbstractBlock.Properties.create(Material.WOOD, DARK_OAK_PLANKS.getMaterialColor()).doesNotBlockMovement().hardnessAndResistance(0.5f).sound(SoundType.WOOD)));
    public static final Block REDSTONE_ORE = Blocks.register("redstone_ore", new RedstoneOreBlock(AbstractBlock.Properties.create(Material.ROCK).setRequiresTool().tickRandomly().setLightLevel(Blocks.getLightValueLit(9)).hardnessAndResistance(3.0f, 3.0f)));
    public static final Block REDSTONE_TORCH = Blocks.register("redstone_torch", new RedstoneTorchBlock(AbstractBlock.Properties.create(Material.MISCELLANEOUS).doesNotBlockMovement().zeroHardnessAndResistance().setLightLevel(Blocks.getLightValueLit(7)).sound(SoundType.WOOD)));
    public static final Block REDSTONE_WALL_TORCH = Blocks.register("redstone_wall_torch", new RedstoneWallTorchBlock(AbstractBlock.Properties.create(Material.MISCELLANEOUS).doesNotBlockMovement().zeroHardnessAndResistance().setLightLevel(Blocks.getLightValueLit(7)).sound(SoundType.WOOD).lootFrom(REDSTONE_TORCH)));
    public static final Block STONE_BUTTON = Blocks.register("stone_button", new StoneButtonBlock(AbstractBlock.Properties.create(Material.MISCELLANEOUS).doesNotBlockMovement().hardnessAndResistance(0.5f)));
    public static final Block SNOW = Blocks.register("snow", new SnowBlock(AbstractBlock.Properties.create(Material.SNOW).tickRandomly().hardnessAndResistance(0.1f).setRequiresTool().sound(SoundType.SNOW)));
    public static final Block ICE = Blocks.register("ice", new IceBlock(AbstractBlock.Properties.create(Material.ICE).slipperiness(0.98f).tickRandomly().hardnessAndResistance(0.5f).sound(SoundType.GLASS).notSolid().setAllowsSpawn(Blocks::lambda$static$7)));
    public static final Block SNOW_BLOCK = Blocks.register("snow_block", new Block(AbstractBlock.Properties.create(Material.SNOW_BLOCK).setRequiresTool().hardnessAndResistance(0.2f).sound(SoundType.SNOW)));
    public static final Block CACTUS = Blocks.register("cactus", new CactusBlock(AbstractBlock.Properties.create(Material.CACTUS).tickRandomly().hardnessAndResistance(0.4f).sound(SoundType.CLOTH)));
    public static final Block CLAY = Blocks.register("clay", new Block(AbstractBlock.Properties.create(Material.CLAY).hardnessAndResistance(0.6f).sound(SoundType.GROUND)));
    public static final Block SUGAR_CANE = Blocks.register("sugar_cane", new SugarCaneBlock(AbstractBlock.Properties.create(Material.PLANTS).doesNotBlockMovement().tickRandomly().zeroHardnessAndResistance().sound(SoundType.PLANT)));
    public static final Block JUKEBOX = Blocks.register("jukebox", new JukeboxBlock(AbstractBlock.Properties.create(Material.WOOD, MaterialColor.DIRT).hardnessAndResistance(2.0f, 6.0f)));
    public static final Block OAK_FENCE = Blocks.register("oak_fence", new FenceBlock(AbstractBlock.Properties.create(Material.WOOD, OAK_PLANKS.getMaterialColor()).hardnessAndResistance(2.0f, 3.0f).sound(SoundType.WOOD)));
    public static final Block PUMPKIN = Blocks.register("pumpkin", new PumpkinBlock(AbstractBlock.Properties.create(Material.GOURD, MaterialColor.ADOBE).hardnessAndResistance(1.0f).sound(SoundType.WOOD)));
    public static final Block NETHERRACK = Blocks.register("netherrack", new NetherrackBlock(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.NETHERRACK).setRequiresTool().hardnessAndResistance(0.4f).sound(SoundType.NETHERRACK)));
    public static final Block SOUL_SAND = Blocks.register("soul_sand", new SoulSandBlock(AbstractBlock.Properties.create(Material.SAND, MaterialColor.BROWN).hardnessAndResistance(0.5f).speedFactor(0.4f).sound(SoundType.SOUL_SAND).setAllowsSpawn(Blocks::alwaysAllowSpawn).setOpaque(Blocks::needsPostProcessing).setBlocksVision(Blocks::needsPostProcessing).setSuffocates(Blocks::needsPostProcessing)));
    public static final Block SOUL_SOIL = Blocks.register("soul_soil", new Block(AbstractBlock.Properties.create(Material.EARTH, MaterialColor.BROWN).hardnessAndResistance(0.5f).sound(SoundType.SOUL_SOIL)));
    public static final Block BASALT = Blocks.register("basalt", new RotatedPillarBlock(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.BLACK).setRequiresTool().hardnessAndResistance(1.25f, 4.2f).sound(SoundType.BASALT)));
    public static final Block POLISHED_BASALT = Blocks.register("polished_basalt", new RotatedPillarBlock(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.BLACK).setRequiresTool().hardnessAndResistance(1.25f, 4.2f).sound(SoundType.BASALT)));
    public static final Block SOUL_TORCH = Blocks.register("soul_torch", new TorchBlock(AbstractBlock.Properties.create(Material.MISCELLANEOUS).doesNotBlockMovement().zeroHardnessAndResistance().setLightLevel(Blocks::lambda$static$8).sound(SoundType.WOOD), ParticleTypes.SOUL_FIRE_FLAME));
    public static final Block SOUL_WALL_TORCH = Blocks.register("soul_wall_torch", new WallTorchBlock(AbstractBlock.Properties.create(Material.MISCELLANEOUS).doesNotBlockMovement().zeroHardnessAndResistance().setLightLevel(Blocks::lambda$static$9).sound(SoundType.WOOD).lootFrom(SOUL_TORCH), ParticleTypes.SOUL_FIRE_FLAME));
    public static final Block GLOWSTONE = Blocks.register("glowstone", new Block(AbstractBlock.Properties.create(Material.GLASS, MaterialColor.SAND).hardnessAndResistance(0.3f).sound(SoundType.GLASS).setLightLevel(Blocks::lambda$static$10)));
    public static final Block NETHER_PORTAL = Blocks.register("nether_portal", new NetherPortalBlock(AbstractBlock.Properties.create(Material.PORTAL).doesNotBlockMovement().tickRandomly().hardnessAndResistance(-1.0f).sound(SoundType.GLASS).setLightLevel(Blocks::lambda$static$11)));
    public static final Block CARVED_PUMPKIN = Blocks.register("carved_pumpkin", new CarvedPumpkinBlock(AbstractBlock.Properties.create(Material.GOURD, MaterialColor.ADOBE).hardnessAndResistance(1.0f).sound(SoundType.WOOD).setAllowsSpawn(Blocks::alwaysAllowSpawn)));
    public static final Block JACK_O_LANTERN = Blocks.register("jack_o_lantern", new CarvedPumpkinBlock(AbstractBlock.Properties.create(Material.GOURD, MaterialColor.ADOBE).hardnessAndResistance(1.0f).sound(SoundType.WOOD).setLightLevel(Blocks::lambda$static$12).setAllowsSpawn(Blocks::alwaysAllowSpawn)));
    public static final Block CAKE = Blocks.register("cake", new CakeBlock(AbstractBlock.Properties.create(Material.CAKE).hardnessAndResistance(0.5f).sound(SoundType.CLOTH)));
    public static final Block REPEATER = Blocks.register("repeater", new RepeaterBlock(AbstractBlock.Properties.create(Material.MISCELLANEOUS).zeroHardnessAndResistance().sound(SoundType.WOOD)));
    public static final Block WHITE_STAINED_GLASS = Blocks.register("white_stained_glass", Blocks.createStainedGlassFromColor(DyeColor.WHITE));
    public static final Block ORANGE_STAINED_GLASS = Blocks.register("orange_stained_glass", Blocks.createStainedGlassFromColor(DyeColor.ORANGE));
    public static final Block MAGENTA_STAINED_GLASS = Blocks.register("magenta_stained_glass", Blocks.createStainedGlassFromColor(DyeColor.MAGENTA));
    public static final Block LIGHT_BLUE_STAINED_GLASS = Blocks.register("light_blue_stained_glass", Blocks.createStainedGlassFromColor(DyeColor.LIGHT_BLUE));
    public static final Block YELLOW_STAINED_GLASS = Blocks.register("yellow_stained_glass", Blocks.createStainedGlassFromColor(DyeColor.YELLOW));
    public static final Block LIME_STAINED_GLASS = Blocks.register("lime_stained_glass", Blocks.createStainedGlassFromColor(DyeColor.LIME));
    public static final Block PINK_STAINED_GLASS = Blocks.register("pink_stained_glass", Blocks.createStainedGlassFromColor(DyeColor.PINK));
    public static final Block GRAY_STAINED_GLASS = Blocks.register("gray_stained_glass", Blocks.createStainedGlassFromColor(DyeColor.GRAY));
    public static final Block LIGHT_GRAY_STAINED_GLASS = Blocks.register("light_gray_stained_glass", Blocks.createStainedGlassFromColor(DyeColor.LIGHT_GRAY));
    public static final Block CYAN_STAINED_GLASS = Blocks.register("cyan_stained_glass", Blocks.createStainedGlassFromColor(DyeColor.CYAN));
    public static final Block PURPLE_STAINED_GLASS = Blocks.register("purple_stained_glass", Blocks.createStainedGlassFromColor(DyeColor.PURPLE));
    public static final Block BLUE_STAINED_GLASS = Blocks.register("blue_stained_glass", Blocks.createStainedGlassFromColor(DyeColor.BLUE));
    public static final Block BROWN_STAINED_GLASS = Blocks.register("brown_stained_glass", Blocks.createStainedGlassFromColor(DyeColor.BROWN));
    public static final Block GREEN_STAINED_GLASS = Blocks.register("green_stained_glass", Blocks.createStainedGlassFromColor(DyeColor.GREEN));
    public static final Block RED_STAINED_GLASS = Blocks.register("red_stained_glass", Blocks.createStainedGlassFromColor(DyeColor.RED));
    public static final Block BLACK_STAINED_GLASS = Blocks.register("black_stained_glass", Blocks.createStainedGlassFromColor(DyeColor.BLACK));
    public static final Block OAK_TRAPDOOR = Blocks.register("oak_trapdoor", new TrapDoorBlock(AbstractBlock.Properties.create(Material.WOOD, MaterialColor.WOOD).hardnessAndResistance(3.0f).sound(SoundType.WOOD).notSolid().setAllowsSpawn(Blocks::neverAllowSpawn)));
    public static final Block SPRUCE_TRAPDOOR = Blocks.register("spruce_trapdoor", new TrapDoorBlock(AbstractBlock.Properties.create(Material.WOOD, MaterialColor.OBSIDIAN).hardnessAndResistance(3.0f).sound(SoundType.WOOD).notSolid().setAllowsSpawn(Blocks::neverAllowSpawn)));
    public static final Block BIRCH_TRAPDOOR = Blocks.register("birch_trapdoor", new TrapDoorBlock(AbstractBlock.Properties.create(Material.WOOD, MaterialColor.SAND).hardnessAndResistance(3.0f).sound(SoundType.WOOD).notSolid().setAllowsSpawn(Blocks::neverAllowSpawn)));
    public static final Block JUNGLE_TRAPDOOR = Blocks.register("jungle_trapdoor", new TrapDoorBlock(AbstractBlock.Properties.create(Material.WOOD, MaterialColor.DIRT).hardnessAndResistance(3.0f).sound(SoundType.WOOD).notSolid().setAllowsSpawn(Blocks::neverAllowSpawn)));
    public static final Block ACACIA_TRAPDOOR = Blocks.register("acacia_trapdoor", new TrapDoorBlock(AbstractBlock.Properties.create(Material.WOOD, MaterialColor.ADOBE).hardnessAndResistance(3.0f).sound(SoundType.WOOD).notSolid().setAllowsSpawn(Blocks::neverAllowSpawn)));
    public static final Block DARK_OAK_TRAPDOOR = Blocks.register("dark_oak_trapdoor", new TrapDoorBlock(AbstractBlock.Properties.create(Material.WOOD, MaterialColor.BROWN).hardnessAndResistance(3.0f).sound(SoundType.WOOD).notSolid().setAllowsSpawn(Blocks::neverAllowSpawn)));
    public static final Block STONE_BRICKS = Blocks.register("stone_bricks", new Block(AbstractBlock.Properties.create(Material.ROCK).setRequiresTool().hardnessAndResistance(1.5f, 6.0f)));
    public static final Block MOSSY_STONE_BRICKS = Blocks.register("mossy_stone_bricks", new Block(AbstractBlock.Properties.create(Material.ROCK).setRequiresTool().hardnessAndResistance(1.5f, 6.0f)));
    public static final Block CRACKED_STONE_BRICKS = Blocks.register("cracked_stone_bricks", new Block(AbstractBlock.Properties.create(Material.ROCK).setRequiresTool().hardnessAndResistance(1.5f, 6.0f)));
    public static final Block CHISELED_STONE_BRICKS = Blocks.register("chiseled_stone_bricks", new Block(AbstractBlock.Properties.create(Material.ROCK).setRequiresTool().hardnessAndResistance(1.5f, 6.0f)));
    public static final Block INFESTED_STONE = Blocks.register("infested_stone", new SilverfishBlock(STONE, AbstractBlock.Properties.create(Material.CLAY).hardnessAndResistance(0.0f, 0.75f)));
    public static final Block INFESTED_COBBLESTONE = Blocks.register("infested_cobblestone", new SilverfishBlock(COBBLESTONE, AbstractBlock.Properties.create(Material.CLAY).hardnessAndResistance(0.0f, 0.75f)));
    public static final Block INFESTED_STONE_BRICKS = Blocks.register("infested_stone_bricks", new SilverfishBlock(STONE_BRICKS, AbstractBlock.Properties.create(Material.CLAY).hardnessAndResistance(0.0f, 0.75f)));
    public static final Block INFESTED_MOSSY_STONE_BRICKS = Blocks.register("infested_mossy_stone_bricks", new SilverfishBlock(MOSSY_STONE_BRICKS, AbstractBlock.Properties.create(Material.CLAY).hardnessAndResistance(0.0f, 0.75f)));
    public static final Block INFESTED_CRACKED_STONE_BRICKS = Blocks.register("infested_cracked_stone_bricks", new SilverfishBlock(CRACKED_STONE_BRICKS, AbstractBlock.Properties.create(Material.CLAY).hardnessAndResistance(0.0f, 0.75f)));
    public static final Block INFESTED_CHISELED_STONE_BRICKS = Blocks.register("infested_chiseled_stone_bricks", new SilverfishBlock(CHISELED_STONE_BRICKS, AbstractBlock.Properties.create(Material.CLAY).hardnessAndResistance(0.0f, 0.75f)));
    public static final Block BROWN_MUSHROOM_BLOCK = Blocks.register("brown_mushroom_block", new HugeMushroomBlock(AbstractBlock.Properties.create(Material.WOOD, MaterialColor.DIRT).hardnessAndResistance(0.2f).sound(SoundType.WOOD)));
    public static final Block RED_MUSHROOM_BLOCK = Blocks.register("red_mushroom_block", new HugeMushroomBlock(AbstractBlock.Properties.create(Material.WOOD, MaterialColor.RED).hardnessAndResistance(0.2f).sound(SoundType.WOOD)));
    public static final Block MUSHROOM_STEM = Blocks.register("mushroom_stem", new HugeMushroomBlock(AbstractBlock.Properties.create(Material.WOOD, MaterialColor.WOOL).hardnessAndResistance(0.2f).sound(SoundType.WOOD)));
    public static final Block IRON_BARS = Blocks.register("iron_bars", new PaneBlock(AbstractBlock.Properties.create(Material.IRON, MaterialColor.AIR).setRequiresTool().hardnessAndResistance(5.0f, 6.0f).sound(SoundType.METAL).notSolid()));
    public static final Block CHAIN = Blocks.register("chain", new ChainBlock(AbstractBlock.Properties.create(Material.IRON, MaterialColor.AIR).setRequiresTool().hardnessAndResistance(5.0f, 6.0f).sound(SoundType.CHAIN).notSolid()));
    public static final Block GLASS_PANE = Blocks.register("glass_pane", new PaneBlock(AbstractBlock.Properties.create(Material.GLASS).hardnessAndResistance(0.3f).sound(SoundType.GLASS).notSolid()));
    public static final Block MELON = Blocks.register("melon", new MelonBlock(AbstractBlock.Properties.create(Material.GOURD, MaterialColor.LIME).hardnessAndResistance(1.0f).sound(SoundType.WOOD)));
    public static final Block ATTACHED_PUMPKIN_STEM = Blocks.register("attached_pumpkin_stem", new AttachedStemBlock((StemGrownBlock)PUMPKIN, AbstractBlock.Properties.create(Material.PLANTS).doesNotBlockMovement().zeroHardnessAndResistance().sound(SoundType.WOOD)));
    public static final Block ATTACHED_MELON_STEM = Blocks.register("attached_melon_stem", new AttachedStemBlock((StemGrownBlock)MELON, AbstractBlock.Properties.create(Material.PLANTS).doesNotBlockMovement().zeroHardnessAndResistance().sound(SoundType.WOOD)));
    public static final Block PUMPKIN_STEM = Blocks.register("pumpkin_stem", new StemBlock((StemGrownBlock)PUMPKIN, AbstractBlock.Properties.create(Material.PLANTS).doesNotBlockMovement().tickRandomly().zeroHardnessAndResistance().sound(SoundType.STEM)));
    public static final Block MELON_STEM = Blocks.register("melon_stem", new StemBlock((StemGrownBlock)MELON, AbstractBlock.Properties.create(Material.PLANTS).doesNotBlockMovement().tickRandomly().zeroHardnessAndResistance().sound(SoundType.STEM)));
    public static final Block VINE = Blocks.register("vine", new VineBlock(AbstractBlock.Properties.create(Material.TALL_PLANTS).doesNotBlockMovement().tickRandomly().hardnessAndResistance(0.2f).sound(SoundType.VINE)));
    public static final Block OAK_FENCE_GATE = Blocks.register("oak_fence_gate", new FenceGateBlock(AbstractBlock.Properties.create(Material.WOOD, OAK_PLANKS.getMaterialColor()).hardnessAndResistance(2.0f, 3.0f).sound(SoundType.WOOD)));
    public static final Block BRICK_STAIRS = Blocks.register("brick_stairs", new StairsBlock(BRICKS.getDefaultState(), AbstractBlock.Properties.from(BRICKS)));
    public static final Block STONE_BRICK_STAIRS = Blocks.register("stone_brick_stairs", new StairsBlock(STONE_BRICKS.getDefaultState(), AbstractBlock.Properties.from(STONE_BRICKS)));
    public static final Block MYCELIUM = Blocks.register("mycelium", new MyceliumBlock(AbstractBlock.Properties.create(Material.ORGANIC, MaterialColor.PURPLE).tickRandomly().hardnessAndResistance(0.6f).sound(SoundType.PLANT)));
    public static final Block LILY_PAD = Blocks.register("lily_pad", new LilyPadBlock(AbstractBlock.Properties.create(Material.PLANTS).zeroHardnessAndResistance().sound(SoundType.LILY_PADS).notSolid()));
    public static final Block NETHER_BRICKS = Blocks.register("nether_bricks", new Block(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.NETHERRACK).setRequiresTool().hardnessAndResistance(2.0f, 6.0f).sound(SoundType.NETHER_BRICK)));
    public static final Block NETHER_BRICK_FENCE = Blocks.register("nether_brick_fence", new FenceBlock(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.NETHERRACK).setRequiresTool().hardnessAndResistance(2.0f, 6.0f).sound(SoundType.NETHER_BRICK)));
    public static final Block NETHER_BRICK_STAIRS = Blocks.register("nether_brick_stairs", new StairsBlock(NETHER_BRICKS.getDefaultState(), AbstractBlock.Properties.from(NETHER_BRICKS)));
    public static final Block NETHER_WART = Blocks.register("nether_wart", new NetherWartBlock(AbstractBlock.Properties.create(Material.PLANTS, MaterialColor.RED).doesNotBlockMovement().tickRandomly().sound(SoundType.NETHER_WART)));
    public static final Block ENCHANTING_TABLE = Blocks.register("enchanting_table", new EnchantingTableBlock(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.RED).setRequiresTool().hardnessAndResistance(5.0f, 1200.0f)));
    public static final Block BREWING_STAND = Blocks.register("brewing_stand", new BrewingStandBlock(AbstractBlock.Properties.create(Material.IRON).setRequiresTool().hardnessAndResistance(0.5f).setLightLevel(Blocks::lambda$static$13).notSolid()));
    public static final Block CAULDRON = Blocks.register("cauldron", new CauldronBlock(AbstractBlock.Properties.create(Material.IRON, MaterialColor.STONE).setRequiresTool().hardnessAndResistance(2.0f).notSolid()));
    public static final Block END_PORTAL = Blocks.register("end_portal", new EndPortalBlock(AbstractBlock.Properties.create(Material.PORTAL, MaterialColor.BLACK).doesNotBlockMovement().setLightLevel(Blocks::lambda$static$14).hardnessAndResistance(-1.0f, 3600000.0f).noDrops()));
    public static final Block END_PORTAL_FRAME = Blocks.register("end_portal_frame", new EndPortalFrameBlock(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.GREEN).sound(SoundType.GLASS).setLightLevel(Blocks::lambda$static$15).hardnessAndResistance(-1.0f, 3600000.0f).noDrops()));
    public static final Block END_STONE = Blocks.register("end_stone", new Block(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.SAND).setRequiresTool().hardnessAndResistance(3.0f, 9.0f)));
    public static final Block DRAGON_EGG = Blocks.register("dragon_egg", new DragonEggBlock(AbstractBlock.Properties.create(Material.DRAGON_EGG, MaterialColor.BLACK).hardnessAndResistance(3.0f, 9.0f).setLightLevel(Blocks::lambda$static$16).notSolid()));
    public static final Block REDSTONE_LAMP = Blocks.register("redstone_lamp", new RedstoneLampBlock(AbstractBlock.Properties.create(Material.REDSTONE_LIGHT).setLightLevel(Blocks.getLightValueLit(15)).hardnessAndResistance(0.3f).sound(SoundType.GLASS).setAllowsSpawn(Blocks::alwaysAllowSpawn)));
    public static final Block COCOA = Blocks.register("cocoa", new CocoaBlock(AbstractBlock.Properties.create(Material.PLANTS).tickRandomly().hardnessAndResistance(0.2f, 3.0f).sound(SoundType.WOOD).notSolid()));
    public static final Block SANDSTONE_STAIRS = Blocks.register("sandstone_stairs", new StairsBlock(SANDSTONE.getDefaultState(), AbstractBlock.Properties.from(SANDSTONE)));
    public static final Block EMERALD_ORE = Blocks.register("emerald_ore", new OreBlock(AbstractBlock.Properties.create(Material.ROCK).setRequiresTool().hardnessAndResistance(3.0f, 3.0f)));
    public static final Block ENDER_CHEST = Blocks.register("ender_chest", new EnderChestBlock(AbstractBlock.Properties.create(Material.ROCK).setRequiresTool().hardnessAndResistance(22.5f, 600.0f).setLightLevel(Blocks::lambda$static$17)));
    public static final Block TRIPWIRE_HOOK = Blocks.register("tripwire_hook", new TripWireHookBlock(AbstractBlock.Properties.create(Material.MISCELLANEOUS).doesNotBlockMovement()));
    public static final Block TRIPWIRE = Blocks.register("tripwire", new TripWireBlock((TripWireHookBlock)TRIPWIRE_HOOK, AbstractBlock.Properties.create(Material.MISCELLANEOUS).doesNotBlockMovement()));
    public static final Block EMERALD_BLOCK = Blocks.register("emerald_block", new Block(AbstractBlock.Properties.create(Material.IRON, MaterialColor.EMERALD).setRequiresTool().hardnessAndResistance(5.0f, 6.0f).sound(SoundType.METAL)));
    public static final Block SPRUCE_STAIRS = Blocks.register("spruce_stairs", new StairsBlock(SPRUCE_PLANKS.getDefaultState(), AbstractBlock.Properties.from(SPRUCE_PLANKS)));
    public static final Block BIRCH_STAIRS = Blocks.register("birch_stairs", new StairsBlock(BIRCH_PLANKS.getDefaultState(), AbstractBlock.Properties.from(BIRCH_PLANKS)));
    public static final Block JUNGLE_STAIRS = Blocks.register("jungle_stairs", new StairsBlock(JUNGLE_PLANKS.getDefaultState(), AbstractBlock.Properties.from(JUNGLE_PLANKS)));
    public static final Block COMMAND_BLOCK = Blocks.register("command_block", new CommandBlockBlock(AbstractBlock.Properties.create(Material.IRON, MaterialColor.BROWN).setRequiresTool().hardnessAndResistance(-1.0f, 3600000.0f).noDrops()));
    public static final Block BEACON = Blocks.register("beacon", new BeaconBlock(AbstractBlock.Properties.create(Material.GLASS, MaterialColor.DIAMOND).hardnessAndResistance(3.0f).setLightLevel(Blocks::lambda$static$18).notSolid().setOpaque(Blocks::isntSolid)));
    public static final Block COBBLESTONE_WALL = Blocks.register("cobblestone_wall", new WallBlock(AbstractBlock.Properties.from(COBBLESTONE)));
    public static final Block MOSSY_COBBLESTONE_WALL = Blocks.register("mossy_cobblestone_wall", new WallBlock(AbstractBlock.Properties.from(COBBLESTONE)));
    public static final Block FLOWER_POT = Blocks.register("flower_pot", new FlowerPotBlock(AIR, AbstractBlock.Properties.create(Material.MISCELLANEOUS).zeroHardnessAndResistance().notSolid()));
    public static final Block POTTED_OAK_SAPLING = Blocks.register("potted_oak_sapling", new FlowerPotBlock(OAK_SAPLING, AbstractBlock.Properties.create(Material.MISCELLANEOUS).zeroHardnessAndResistance().notSolid()));
    public static final Block POTTED_SPRUCE_SAPLING = Blocks.register("potted_spruce_sapling", new FlowerPotBlock(SPRUCE_SAPLING, AbstractBlock.Properties.create(Material.MISCELLANEOUS).zeroHardnessAndResistance().notSolid()));
    public static final Block POTTED_BIRCH_SAPLING = Blocks.register("potted_birch_sapling", new FlowerPotBlock(BIRCH_SAPLING, AbstractBlock.Properties.create(Material.MISCELLANEOUS).zeroHardnessAndResistance().notSolid()));
    public static final Block POTTED_JUNGLE_SAPLING = Blocks.register("potted_jungle_sapling", new FlowerPotBlock(JUNGLE_SAPLING, AbstractBlock.Properties.create(Material.MISCELLANEOUS).zeroHardnessAndResistance().notSolid()));
    public static final Block POTTED_ACACIA_SAPLING = Blocks.register("potted_acacia_sapling", new FlowerPotBlock(ACACIA_SAPLING, AbstractBlock.Properties.create(Material.MISCELLANEOUS).zeroHardnessAndResistance().notSolid()));
    public static final Block POTTED_DARK_OAK_SAPLING = Blocks.register("potted_dark_oak_sapling", new FlowerPotBlock(DARK_OAK_SAPLING, AbstractBlock.Properties.create(Material.MISCELLANEOUS).zeroHardnessAndResistance().notSolid()));
    public static final Block POTTED_FERN = Blocks.register("potted_fern", new FlowerPotBlock(FERN, AbstractBlock.Properties.create(Material.MISCELLANEOUS).zeroHardnessAndResistance().notSolid()));
    public static final Block POTTED_DANDELION = Blocks.register("potted_dandelion", new FlowerPotBlock(DANDELION, AbstractBlock.Properties.create(Material.MISCELLANEOUS).zeroHardnessAndResistance().notSolid()));
    public static final Block POTTED_POPPY = Blocks.register("potted_poppy", new FlowerPotBlock(POPPY, AbstractBlock.Properties.create(Material.MISCELLANEOUS).zeroHardnessAndResistance().notSolid()));
    public static final Block POTTED_BLUE_ORCHID = Blocks.register("potted_blue_orchid", new FlowerPotBlock(BLUE_ORCHID, AbstractBlock.Properties.create(Material.MISCELLANEOUS).zeroHardnessAndResistance().notSolid()));
    public static final Block POTTED_ALLIUM = Blocks.register("potted_allium", new FlowerPotBlock(ALLIUM, AbstractBlock.Properties.create(Material.MISCELLANEOUS).zeroHardnessAndResistance().notSolid()));
    public static final Block POTTED_AZURE_BLUET = Blocks.register("potted_azure_bluet", new FlowerPotBlock(AZURE_BLUET, AbstractBlock.Properties.create(Material.MISCELLANEOUS).zeroHardnessAndResistance().notSolid()));
    public static final Block POTTED_RED_TULIP = Blocks.register("potted_red_tulip", new FlowerPotBlock(RED_TULIP, AbstractBlock.Properties.create(Material.MISCELLANEOUS).zeroHardnessAndResistance().notSolid()));
    public static final Block POTTED_ORANGE_TULIP = Blocks.register("potted_orange_tulip", new FlowerPotBlock(ORANGE_TULIP, AbstractBlock.Properties.create(Material.MISCELLANEOUS).zeroHardnessAndResistance().notSolid()));
    public static final Block POTTED_WHITE_TULIP = Blocks.register("potted_white_tulip", new FlowerPotBlock(WHITE_TULIP, AbstractBlock.Properties.create(Material.MISCELLANEOUS).zeroHardnessAndResistance().notSolid()));
    public static final Block POTTED_PINK_TULIP = Blocks.register("potted_pink_tulip", new FlowerPotBlock(PINK_TULIP, AbstractBlock.Properties.create(Material.MISCELLANEOUS).zeroHardnessAndResistance().notSolid()));
    public static final Block POTTED_OXEYE_DAISY = Blocks.register("potted_oxeye_daisy", new FlowerPotBlock(OXEYE_DAISY, AbstractBlock.Properties.create(Material.MISCELLANEOUS).zeroHardnessAndResistance().notSolid()));
    public static final Block POTTED_CORNFLOWER = Blocks.register("potted_cornflower", new FlowerPotBlock(CORNFLOWER, AbstractBlock.Properties.create(Material.MISCELLANEOUS).zeroHardnessAndResistance().notSolid()));
    public static final Block POTTED_LILY_OF_THE_VALLEY = Blocks.register("potted_lily_of_the_valley", new FlowerPotBlock(LILY_OF_THE_VALLEY, AbstractBlock.Properties.create(Material.MISCELLANEOUS).zeroHardnessAndResistance().notSolid()));
    public static final Block POTTED_WITHER_ROSE = Blocks.register("potted_wither_rose", new FlowerPotBlock(WITHER_ROSE, AbstractBlock.Properties.create(Material.MISCELLANEOUS).zeroHardnessAndResistance().notSolid()));
    public static final Block POTTED_RED_MUSHROOM = Blocks.register("potted_red_mushroom", new FlowerPotBlock(RED_MUSHROOM, AbstractBlock.Properties.create(Material.MISCELLANEOUS).zeroHardnessAndResistance().notSolid()));
    public static final Block POTTED_BROWN_MUSHROOM = Blocks.register("potted_brown_mushroom", new FlowerPotBlock(BROWN_MUSHROOM, AbstractBlock.Properties.create(Material.MISCELLANEOUS).zeroHardnessAndResistance().notSolid()));
    public static final Block POTTED_DEAD_BUSH = Blocks.register("potted_dead_bush", new FlowerPotBlock(DEAD_BUSH, AbstractBlock.Properties.create(Material.MISCELLANEOUS).zeroHardnessAndResistance().notSolid()));
    public static final Block POTTED_CACTUS = Blocks.register("potted_cactus", new FlowerPotBlock(CACTUS, AbstractBlock.Properties.create(Material.MISCELLANEOUS).zeroHardnessAndResistance().notSolid()));
    public static final Block CARROTS = Blocks.register("carrots", new CarrotBlock(AbstractBlock.Properties.create(Material.PLANTS).doesNotBlockMovement().tickRandomly().zeroHardnessAndResistance().sound(SoundType.CROP)));
    public static final Block POTATOES = Blocks.register("potatoes", new PotatoBlock(AbstractBlock.Properties.create(Material.PLANTS).doesNotBlockMovement().tickRandomly().zeroHardnessAndResistance().sound(SoundType.CROP)));
    public static final Block OAK_BUTTON = Blocks.register("oak_button", new WoodButtonBlock(AbstractBlock.Properties.create(Material.MISCELLANEOUS).doesNotBlockMovement().hardnessAndResistance(0.5f).sound(SoundType.WOOD)));
    public static final Block SPRUCE_BUTTON = Blocks.register("spruce_button", new WoodButtonBlock(AbstractBlock.Properties.create(Material.MISCELLANEOUS).doesNotBlockMovement().hardnessAndResistance(0.5f).sound(SoundType.WOOD)));
    public static final Block BIRCH_BUTTON = Blocks.register("birch_button", new WoodButtonBlock(AbstractBlock.Properties.create(Material.MISCELLANEOUS).doesNotBlockMovement().hardnessAndResistance(0.5f).sound(SoundType.WOOD)));
    public static final Block JUNGLE_BUTTON = Blocks.register("jungle_button", new WoodButtonBlock(AbstractBlock.Properties.create(Material.MISCELLANEOUS).doesNotBlockMovement().hardnessAndResistance(0.5f).sound(SoundType.WOOD)));
    public static final Block ACACIA_BUTTON = Blocks.register("acacia_button", new WoodButtonBlock(AbstractBlock.Properties.create(Material.MISCELLANEOUS).doesNotBlockMovement().hardnessAndResistance(0.5f).sound(SoundType.WOOD)));
    public static final Block DARK_OAK_BUTTON = Blocks.register("dark_oak_button", new WoodButtonBlock(AbstractBlock.Properties.create(Material.MISCELLANEOUS).doesNotBlockMovement().hardnessAndResistance(0.5f).sound(SoundType.WOOD)));
    public static final Block SKELETON_SKULL = Blocks.register("skeleton_skull", new SkullBlock(SkullBlock.Types.SKELETON, AbstractBlock.Properties.create(Material.MISCELLANEOUS).hardnessAndResistance(1.0f)));
    public static final Block SKELETON_WALL_SKULL = Blocks.register("skeleton_wall_skull", new WallSkullBlock(SkullBlock.Types.SKELETON, AbstractBlock.Properties.create(Material.MISCELLANEOUS).hardnessAndResistance(1.0f).lootFrom(SKELETON_SKULL)));
    public static final Block WITHER_SKELETON_SKULL = Blocks.register("wither_skeleton_skull", new WitherSkeletonSkullBlock(AbstractBlock.Properties.create(Material.MISCELLANEOUS).hardnessAndResistance(1.0f)));
    public static final Block WITHER_SKELETON_WALL_SKULL = Blocks.register("wither_skeleton_wall_skull", new WitherSkeletonWallSkullBlock(AbstractBlock.Properties.create(Material.MISCELLANEOUS).hardnessAndResistance(1.0f).lootFrom(WITHER_SKELETON_SKULL)));
    public static final Block ZOMBIE_HEAD = Blocks.register("zombie_head", new SkullBlock(SkullBlock.Types.ZOMBIE, AbstractBlock.Properties.create(Material.MISCELLANEOUS).hardnessAndResistance(1.0f)));
    public static final Block ZOMBIE_WALL_HEAD = Blocks.register("zombie_wall_head", new WallSkullBlock(SkullBlock.Types.ZOMBIE, AbstractBlock.Properties.create(Material.MISCELLANEOUS).hardnessAndResistance(1.0f).lootFrom(ZOMBIE_HEAD)));
    public static final Block PLAYER_HEAD = Blocks.register("player_head", new SkullPlayerBlock(AbstractBlock.Properties.create(Material.MISCELLANEOUS).hardnessAndResistance(1.0f)));
    public static final Block PLAYER_WALL_HEAD = Blocks.register("player_wall_head", new SkullWallPlayerBlock(AbstractBlock.Properties.create(Material.MISCELLANEOUS).hardnessAndResistance(1.0f).lootFrom(PLAYER_HEAD)));
    public static final Block CREEPER_HEAD = Blocks.register("creeper_head", new SkullBlock(SkullBlock.Types.CREEPER, AbstractBlock.Properties.create(Material.MISCELLANEOUS).hardnessAndResistance(1.0f)));
    public static final Block CREEPER_WALL_HEAD = Blocks.register("creeper_wall_head", new WallSkullBlock(SkullBlock.Types.CREEPER, AbstractBlock.Properties.create(Material.MISCELLANEOUS).hardnessAndResistance(1.0f).lootFrom(CREEPER_HEAD)));
    public static final Block DRAGON_HEAD = Blocks.register("dragon_head", new SkullBlock(SkullBlock.Types.DRAGON, AbstractBlock.Properties.create(Material.MISCELLANEOUS).hardnessAndResistance(1.0f)));
    public static final Block DRAGON_WALL_HEAD = Blocks.register("dragon_wall_head", new WallSkullBlock(SkullBlock.Types.DRAGON, AbstractBlock.Properties.create(Material.MISCELLANEOUS).hardnessAndResistance(1.0f).lootFrom(DRAGON_HEAD)));
    public static final Block ANVIL = Blocks.register("anvil", new AnvilBlock(AbstractBlock.Properties.create(Material.ANVIL, MaterialColor.IRON).setRequiresTool().hardnessAndResistance(5.0f, 1200.0f).sound(SoundType.ANVIL)));
    public static final Block CHIPPED_ANVIL = Blocks.register("chipped_anvil", new AnvilBlock(AbstractBlock.Properties.create(Material.ANVIL, MaterialColor.IRON).setRequiresTool().hardnessAndResistance(5.0f, 1200.0f).sound(SoundType.ANVIL)));
    public static final Block DAMAGED_ANVIL = Blocks.register("damaged_anvil", new AnvilBlock(AbstractBlock.Properties.create(Material.ANVIL, MaterialColor.IRON).setRequiresTool().hardnessAndResistance(5.0f, 1200.0f).sound(SoundType.ANVIL)));
    public static final Block TRAPPED_CHEST = Blocks.register("trapped_chest", new TrappedChestBlock(AbstractBlock.Properties.create(Material.WOOD).hardnessAndResistance(2.5f).sound(SoundType.WOOD)));
    public static final Block LIGHT_WEIGHTED_PRESSURE_PLATE = Blocks.register("light_weighted_pressure_plate", new WeightedPressurePlateBlock(15, AbstractBlock.Properties.create(Material.IRON, MaterialColor.GOLD).setRequiresTool().doesNotBlockMovement().hardnessAndResistance(0.5f).sound(SoundType.WOOD)));
    public static final Block HEAVY_WEIGHTED_PRESSURE_PLATE = Blocks.register("heavy_weighted_pressure_plate", new WeightedPressurePlateBlock(150, AbstractBlock.Properties.create(Material.IRON).setRequiresTool().doesNotBlockMovement().hardnessAndResistance(0.5f).sound(SoundType.WOOD)));
    public static final Block COMPARATOR = Blocks.register("comparator", new ComparatorBlock(AbstractBlock.Properties.create(Material.MISCELLANEOUS).zeroHardnessAndResistance().sound(SoundType.WOOD)));
    public static final Block DAYLIGHT_DETECTOR = Blocks.register("daylight_detector", new DaylightDetectorBlock(AbstractBlock.Properties.create(Material.WOOD).hardnessAndResistance(0.2f).sound(SoundType.WOOD)));
    public static final Block REDSTONE_BLOCK = Blocks.register("redstone_block", new RedstoneBlock(AbstractBlock.Properties.create(Material.IRON, MaterialColor.TNT).setRequiresTool().hardnessAndResistance(5.0f, 6.0f).sound(SoundType.METAL).setOpaque(Blocks::isntSolid)));
    public static final Block NETHER_QUARTZ_ORE = Blocks.register("nether_quartz_ore", new OreBlock(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.NETHERRACK).setRequiresTool().hardnessAndResistance(3.0f, 3.0f).sound(SoundType.NETHER_ORE)));
    public static final Block HOPPER = Blocks.register("hopper", new HopperBlock(AbstractBlock.Properties.create(Material.IRON, MaterialColor.STONE).setRequiresTool().hardnessAndResistance(3.0f, 4.8f).sound(SoundType.METAL).notSolid()));
    public static final Block QUARTZ_BLOCK = Blocks.register("quartz_block", new Block(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.QUARTZ).setRequiresTool().hardnessAndResistance(0.8f)));
    public static final Block CHISELED_QUARTZ_BLOCK = Blocks.register("chiseled_quartz_block", new Block(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.QUARTZ).setRequiresTool().hardnessAndResistance(0.8f)));
    public static final Block QUARTZ_PILLAR = Blocks.register("quartz_pillar", new RotatedPillarBlock(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.QUARTZ).setRequiresTool().hardnessAndResistance(0.8f)));
    public static final Block QUARTZ_STAIRS = Blocks.register("quartz_stairs", new StairsBlock(QUARTZ_BLOCK.getDefaultState(), AbstractBlock.Properties.from(QUARTZ_BLOCK)));
    public static final Block ACTIVATOR_RAIL = Blocks.register("activator_rail", new PoweredRailBlock(AbstractBlock.Properties.create(Material.MISCELLANEOUS).doesNotBlockMovement().hardnessAndResistance(0.7f).sound(SoundType.METAL)));
    public static final Block DROPPER = Blocks.register("dropper", new DropperBlock(AbstractBlock.Properties.create(Material.ROCK).setRequiresTool().hardnessAndResistance(3.5f)));
    public static final Block WHITE_TERRACOTTA = Blocks.register("white_terracotta", new Block(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.WHITE_TERRACOTTA).setRequiresTool().hardnessAndResistance(1.25f, 4.2f)));
    public static final Block ORANGE_TERRACOTTA = Blocks.register("orange_terracotta", new Block(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.ORANGE_TERRACOTTA).setRequiresTool().hardnessAndResistance(1.25f, 4.2f)));
    public static final Block MAGENTA_TERRACOTTA = Blocks.register("magenta_terracotta", new Block(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.MAGENTA_TERRACOTTA).setRequiresTool().hardnessAndResistance(1.25f, 4.2f)));
    public static final Block LIGHT_BLUE_TERRACOTTA = Blocks.register("light_blue_terracotta", new Block(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.LIGHT_BLUE_TERRACOTTA).setRequiresTool().hardnessAndResistance(1.25f, 4.2f)));
    public static final Block YELLOW_TERRACOTTA = Blocks.register("yellow_terracotta", new Block(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.YELLOW_TERRACOTTA).setRequiresTool().hardnessAndResistance(1.25f, 4.2f)));
    public static final Block LIME_TERRACOTTA = Blocks.register("lime_terracotta", new Block(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.LIME_TERRACOTTA).setRequiresTool().hardnessAndResistance(1.25f, 4.2f)));
    public static final Block PINK_TERRACOTTA = Blocks.register("pink_terracotta", new Block(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.PINK_TERRACOTTA).setRequiresTool().hardnessAndResistance(1.25f, 4.2f)));
    public static final Block GRAY_TERRACOTTA = Blocks.register("gray_terracotta", new Block(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.GRAY_TERRACOTTA).setRequiresTool().hardnessAndResistance(1.25f, 4.2f)));
    public static final Block LIGHT_GRAY_TERRACOTTA = Blocks.register("light_gray_terracotta", new Block(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.LIGHT_GRAY_TERRACOTTA).setRequiresTool().hardnessAndResistance(1.25f, 4.2f)));
    public static final Block CYAN_TERRACOTTA = Blocks.register("cyan_terracotta", new Block(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.CYAN_TERRACOTTA).setRequiresTool().hardnessAndResistance(1.25f, 4.2f)));
    public static final Block PURPLE_TERRACOTTA = Blocks.register("purple_terracotta", new Block(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.PURPLE_TERRACOTTA).setRequiresTool().hardnessAndResistance(1.25f, 4.2f)));
    public static final Block BLUE_TERRACOTTA = Blocks.register("blue_terracotta", new Block(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.BLUE_TERRACOTTA).setRequiresTool().hardnessAndResistance(1.25f, 4.2f)));
    public static final Block BROWN_TERRACOTTA = Blocks.register("brown_terracotta", new Block(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.BROWN_TERRACOTTA).setRequiresTool().hardnessAndResistance(1.25f, 4.2f)));
    public static final Block GREEN_TERRACOTTA = Blocks.register("green_terracotta", new Block(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.GREEN_TERRACOTTA).setRequiresTool().hardnessAndResistance(1.25f, 4.2f)));
    public static final Block RED_TERRACOTTA = Blocks.register("red_terracotta", new Block(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.RED_TERRACOTTA).setRequiresTool().hardnessAndResistance(1.25f, 4.2f)));
    public static final Block BLACK_TERRACOTTA = Blocks.register("black_terracotta", new Block(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.BLACK_TERRACOTTA).setRequiresTool().hardnessAndResistance(1.25f, 4.2f)));
    public static final Block WHITE_STAINED_GLASS_PANE = Blocks.register("white_stained_glass_pane", new StainedGlassPaneBlock(DyeColor.WHITE, AbstractBlock.Properties.create(Material.GLASS).hardnessAndResistance(0.3f).sound(SoundType.GLASS).notSolid()));
    public static final Block ORANGE_STAINED_GLASS_PANE = Blocks.register("orange_stained_glass_pane", new StainedGlassPaneBlock(DyeColor.ORANGE, AbstractBlock.Properties.create(Material.GLASS).hardnessAndResistance(0.3f).sound(SoundType.GLASS).notSolid()));
    public static final Block MAGENTA_STAINED_GLASS_PANE = Blocks.register("magenta_stained_glass_pane", new StainedGlassPaneBlock(DyeColor.MAGENTA, AbstractBlock.Properties.create(Material.GLASS).hardnessAndResistance(0.3f).sound(SoundType.GLASS).notSolid()));
    public static final Block LIGHT_BLUE_STAINED_GLASS_PANE = Blocks.register("light_blue_stained_glass_pane", new StainedGlassPaneBlock(DyeColor.LIGHT_BLUE, AbstractBlock.Properties.create(Material.GLASS).hardnessAndResistance(0.3f).sound(SoundType.GLASS).notSolid()));
    public static final Block YELLOW_STAINED_GLASS_PANE = Blocks.register("yellow_stained_glass_pane", new StainedGlassPaneBlock(DyeColor.YELLOW, AbstractBlock.Properties.create(Material.GLASS).hardnessAndResistance(0.3f).sound(SoundType.GLASS).notSolid()));
    public static final Block LIME_STAINED_GLASS_PANE = Blocks.register("lime_stained_glass_pane", new StainedGlassPaneBlock(DyeColor.LIME, AbstractBlock.Properties.create(Material.GLASS).hardnessAndResistance(0.3f).sound(SoundType.GLASS).notSolid()));
    public static final Block PINK_STAINED_GLASS_PANE = Blocks.register("pink_stained_glass_pane", new StainedGlassPaneBlock(DyeColor.PINK, AbstractBlock.Properties.create(Material.GLASS).hardnessAndResistance(0.3f).sound(SoundType.GLASS).notSolid()));
    public static final Block GRAY_STAINED_GLASS_PANE = Blocks.register("gray_stained_glass_pane", new StainedGlassPaneBlock(DyeColor.GRAY, AbstractBlock.Properties.create(Material.GLASS).hardnessAndResistance(0.3f).sound(SoundType.GLASS).notSolid()));
    public static final Block LIGHT_GRAY_STAINED_GLASS_PANE = Blocks.register("light_gray_stained_glass_pane", new StainedGlassPaneBlock(DyeColor.LIGHT_GRAY, AbstractBlock.Properties.create(Material.GLASS).hardnessAndResistance(0.3f).sound(SoundType.GLASS).notSolid()));
    public static final Block CYAN_STAINED_GLASS_PANE = Blocks.register("cyan_stained_glass_pane", new StainedGlassPaneBlock(DyeColor.CYAN, AbstractBlock.Properties.create(Material.GLASS).hardnessAndResistance(0.3f).sound(SoundType.GLASS).notSolid()));
    public static final Block PURPLE_STAINED_GLASS_PANE = Blocks.register("purple_stained_glass_pane", new StainedGlassPaneBlock(DyeColor.PURPLE, AbstractBlock.Properties.create(Material.GLASS).hardnessAndResistance(0.3f).sound(SoundType.GLASS).notSolid()));
    public static final Block BLUE_STAINED_GLASS_PANE = Blocks.register("blue_stained_glass_pane", new StainedGlassPaneBlock(DyeColor.BLUE, AbstractBlock.Properties.create(Material.GLASS).hardnessAndResistance(0.3f).sound(SoundType.GLASS).notSolid()));
    public static final Block BROWN_STAINED_GLASS_PANE = Blocks.register("brown_stained_glass_pane", new StainedGlassPaneBlock(DyeColor.BROWN, AbstractBlock.Properties.create(Material.GLASS).hardnessAndResistance(0.3f).sound(SoundType.GLASS).notSolid()));
    public static final Block GREEN_STAINED_GLASS_PANE = Blocks.register("green_stained_glass_pane", new StainedGlassPaneBlock(DyeColor.GREEN, AbstractBlock.Properties.create(Material.GLASS).hardnessAndResistance(0.3f).sound(SoundType.GLASS).notSolid()));
    public static final Block RED_STAINED_GLASS_PANE = Blocks.register("red_stained_glass_pane", new StainedGlassPaneBlock(DyeColor.RED, AbstractBlock.Properties.create(Material.GLASS).hardnessAndResistance(0.3f).sound(SoundType.GLASS).notSolid()));
    public static final Block BLACK_STAINED_GLASS_PANE = Blocks.register("black_stained_glass_pane", new StainedGlassPaneBlock(DyeColor.BLACK, AbstractBlock.Properties.create(Material.GLASS).hardnessAndResistance(0.3f).sound(SoundType.GLASS).notSolid()));
    public static final Block ACACIA_STAIRS = Blocks.register("acacia_stairs", new StairsBlock(ACACIA_PLANKS.getDefaultState(), AbstractBlock.Properties.from(ACACIA_PLANKS)));
    public static final Block DARK_OAK_STAIRS = Blocks.register("dark_oak_stairs", new StairsBlock(DARK_OAK_PLANKS.getDefaultState(), AbstractBlock.Properties.from(DARK_OAK_PLANKS)));
    public static final Block SLIME_BLOCK = Blocks.register("slime_block", new SlimeBlock(AbstractBlock.Properties.create(Material.CLAY, MaterialColor.GRASS).slipperiness(0.8f).sound(SoundType.SLIME).notSolid()));
    public static final Block BARRIER = Blocks.register("barrier", new BarrierBlock(AbstractBlock.Properties.create(Material.BARRIER).hardnessAndResistance(-1.0f, 3600000.8f).noDrops().notSolid().setAllowsSpawn(Blocks::neverAllowSpawn)));
    public static final Block IRON_TRAPDOOR = Blocks.register("iron_trapdoor", new TrapDoorBlock(AbstractBlock.Properties.create(Material.IRON).setRequiresTool().hardnessAndResistance(5.0f).sound(SoundType.METAL).notSolid().setAllowsSpawn(Blocks::neverAllowSpawn)));
    public static final Block PRISMARINE = Blocks.register("prismarine", new Block(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.CYAN).setRequiresTool().hardnessAndResistance(1.5f, 6.0f)));
    public static final Block PRISMARINE_BRICKS = Blocks.register("prismarine_bricks", new Block(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.DIAMOND).setRequiresTool().hardnessAndResistance(1.5f, 6.0f)));
    public static final Block DARK_PRISMARINE = Blocks.register("dark_prismarine", new Block(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.DIAMOND).setRequiresTool().hardnessAndResistance(1.5f, 6.0f)));
    public static final Block PRISMARINE_STAIRS = Blocks.register("prismarine_stairs", new StairsBlock(PRISMARINE.getDefaultState(), AbstractBlock.Properties.from(PRISMARINE)));
    public static final Block PRISMARINE_BRICK_STAIRS = Blocks.register("prismarine_brick_stairs", new StairsBlock(PRISMARINE_BRICKS.getDefaultState(), AbstractBlock.Properties.from(PRISMARINE_BRICKS)));
    public static final Block DARK_PRISMARINE_STAIRS = Blocks.register("dark_prismarine_stairs", new StairsBlock(DARK_PRISMARINE.getDefaultState(), AbstractBlock.Properties.from(DARK_PRISMARINE)));
    public static final Block PRISMARINE_SLAB = Blocks.register("prismarine_slab", new SlabBlock(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.CYAN).setRequiresTool().hardnessAndResistance(1.5f, 6.0f)));
    public static final Block PRISMARINE_BRICK_SLAB = Blocks.register("prismarine_brick_slab", new SlabBlock(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.DIAMOND).setRequiresTool().hardnessAndResistance(1.5f, 6.0f)));
    public static final Block DARK_PRISMARINE_SLAB = Blocks.register("dark_prismarine_slab", new SlabBlock(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.DIAMOND).setRequiresTool().hardnessAndResistance(1.5f, 6.0f)));
    public static final Block SEA_LANTERN = Blocks.register("sea_lantern", new Block(AbstractBlock.Properties.create(Material.GLASS, MaterialColor.QUARTZ).hardnessAndResistance(0.3f).sound(SoundType.GLASS).setLightLevel(Blocks::lambda$static$19)));
    public static final Block HAY_BLOCK = Blocks.register("hay_block", new HayBlock(AbstractBlock.Properties.create(Material.ORGANIC, MaterialColor.YELLOW).hardnessAndResistance(0.5f).sound(SoundType.PLANT)));
    public static final Block WHITE_CARPET = Blocks.register("white_carpet", new CarpetBlock(DyeColor.WHITE, AbstractBlock.Properties.create(Material.CARPET, MaterialColor.SNOW).hardnessAndResistance(0.1f).sound(SoundType.CLOTH)));
    public static final Block ORANGE_CARPET = Blocks.register("orange_carpet", new CarpetBlock(DyeColor.ORANGE, AbstractBlock.Properties.create(Material.CARPET, MaterialColor.ADOBE).hardnessAndResistance(0.1f).sound(SoundType.CLOTH)));
    public static final Block MAGENTA_CARPET = Blocks.register("magenta_carpet", new CarpetBlock(DyeColor.MAGENTA, AbstractBlock.Properties.create(Material.CARPET, MaterialColor.MAGENTA).hardnessAndResistance(0.1f).sound(SoundType.CLOTH)));
    public static final Block LIGHT_BLUE_CARPET = Blocks.register("light_blue_carpet", new CarpetBlock(DyeColor.LIGHT_BLUE, AbstractBlock.Properties.create(Material.CARPET, MaterialColor.LIGHT_BLUE).hardnessAndResistance(0.1f).sound(SoundType.CLOTH)));
    public static final Block YELLOW_CARPET = Blocks.register("yellow_carpet", new CarpetBlock(DyeColor.YELLOW, AbstractBlock.Properties.create(Material.CARPET, MaterialColor.YELLOW).hardnessAndResistance(0.1f).sound(SoundType.CLOTH)));
    public static final Block LIME_CARPET = Blocks.register("lime_carpet", new CarpetBlock(DyeColor.LIME, AbstractBlock.Properties.create(Material.CARPET, MaterialColor.LIME).hardnessAndResistance(0.1f).sound(SoundType.CLOTH)));
    public static final Block PINK_CARPET = Blocks.register("pink_carpet", new CarpetBlock(DyeColor.PINK, AbstractBlock.Properties.create(Material.CARPET, MaterialColor.PINK).hardnessAndResistance(0.1f).sound(SoundType.CLOTH)));
    public static final Block GRAY_CARPET = Blocks.register("gray_carpet", new CarpetBlock(DyeColor.GRAY, AbstractBlock.Properties.create(Material.CARPET, MaterialColor.GRAY).hardnessAndResistance(0.1f).sound(SoundType.CLOTH)));
    public static final Block LIGHT_GRAY_CARPET = Blocks.register("light_gray_carpet", new CarpetBlock(DyeColor.LIGHT_GRAY, AbstractBlock.Properties.create(Material.CARPET, MaterialColor.LIGHT_GRAY).hardnessAndResistance(0.1f).sound(SoundType.CLOTH)));
    public static final Block CYAN_CARPET = Blocks.register("cyan_carpet", new CarpetBlock(DyeColor.CYAN, AbstractBlock.Properties.create(Material.CARPET, MaterialColor.CYAN).hardnessAndResistance(0.1f).sound(SoundType.CLOTH)));
    public static final Block PURPLE_CARPET = Blocks.register("purple_carpet", new CarpetBlock(DyeColor.PURPLE, AbstractBlock.Properties.create(Material.CARPET, MaterialColor.PURPLE).hardnessAndResistance(0.1f).sound(SoundType.CLOTH)));
    public static final Block BLUE_CARPET = Blocks.register("blue_carpet", new CarpetBlock(DyeColor.BLUE, AbstractBlock.Properties.create(Material.CARPET, MaterialColor.BLUE).hardnessAndResistance(0.1f).sound(SoundType.CLOTH)));
    public static final Block BROWN_CARPET = Blocks.register("brown_carpet", new CarpetBlock(DyeColor.BROWN, AbstractBlock.Properties.create(Material.CARPET, MaterialColor.BROWN).hardnessAndResistance(0.1f).sound(SoundType.CLOTH)));
    public static final Block GREEN_CARPET = Blocks.register("green_carpet", new CarpetBlock(DyeColor.GREEN, AbstractBlock.Properties.create(Material.CARPET, MaterialColor.GREEN).hardnessAndResistance(0.1f).sound(SoundType.CLOTH)));
    public static final Block RED_CARPET = Blocks.register("red_carpet", new CarpetBlock(DyeColor.RED, AbstractBlock.Properties.create(Material.CARPET, MaterialColor.RED).hardnessAndResistance(0.1f).sound(SoundType.CLOTH)));
    public static final Block BLACK_CARPET = Blocks.register("black_carpet", new CarpetBlock(DyeColor.BLACK, AbstractBlock.Properties.create(Material.CARPET, MaterialColor.BLACK).hardnessAndResistance(0.1f).sound(SoundType.CLOTH)));
    public static final Block TERRACOTTA = Blocks.register("terracotta", new Block(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.ADOBE).setRequiresTool().hardnessAndResistance(1.25f, 4.2f)));
    public static final Block COAL_BLOCK = Blocks.register("coal_block", new Block(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.BLACK).setRequiresTool().hardnessAndResistance(5.0f, 6.0f)));
    public static final Block PACKED_ICE = Blocks.register("packed_ice", new Block(AbstractBlock.Properties.create(Material.PACKED_ICE).slipperiness(0.98f).hardnessAndResistance(0.5f).sound(SoundType.GLASS)));
    public static final Block SUNFLOWER = Blocks.register("sunflower", new TallFlowerBlock(AbstractBlock.Properties.create(Material.TALL_PLANTS).doesNotBlockMovement().zeroHardnessAndResistance().sound(SoundType.PLANT)));
    public static final Block LILAC = Blocks.register("lilac", new TallFlowerBlock(AbstractBlock.Properties.create(Material.TALL_PLANTS).doesNotBlockMovement().zeroHardnessAndResistance().sound(SoundType.PLANT)));
    public static final Block ROSE_BUSH = Blocks.register("rose_bush", new TallFlowerBlock(AbstractBlock.Properties.create(Material.TALL_PLANTS).doesNotBlockMovement().zeroHardnessAndResistance().sound(SoundType.PLANT)));
    public static final Block PEONY = Blocks.register("peony", new TallFlowerBlock(AbstractBlock.Properties.create(Material.TALL_PLANTS).doesNotBlockMovement().zeroHardnessAndResistance().sound(SoundType.PLANT)));
    public static final Block TALL_GRASS = Blocks.register("tall_grass", new DoublePlantBlock(AbstractBlock.Properties.create(Material.TALL_PLANTS).doesNotBlockMovement().zeroHardnessAndResistance().sound(SoundType.PLANT)));
    public static final Block LARGE_FERN = Blocks.register("large_fern", new DoublePlantBlock(AbstractBlock.Properties.create(Material.TALL_PLANTS).doesNotBlockMovement().zeroHardnessAndResistance().sound(SoundType.PLANT)));
    public static final Block WHITE_BANNER = Blocks.register("white_banner", new BannerBlock(DyeColor.WHITE, AbstractBlock.Properties.create(Material.WOOD).doesNotBlockMovement().hardnessAndResistance(1.0f).sound(SoundType.WOOD)));
    public static final Block ORANGE_BANNER = Blocks.register("orange_banner", new BannerBlock(DyeColor.ORANGE, AbstractBlock.Properties.create(Material.WOOD).doesNotBlockMovement().hardnessAndResistance(1.0f).sound(SoundType.WOOD)));
    public static final Block MAGENTA_BANNER = Blocks.register("magenta_banner", new BannerBlock(DyeColor.MAGENTA, AbstractBlock.Properties.create(Material.WOOD).doesNotBlockMovement().hardnessAndResistance(1.0f).sound(SoundType.WOOD)));
    public static final Block LIGHT_BLUE_BANNER = Blocks.register("light_blue_banner", new BannerBlock(DyeColor.LIGHT_BLUE, AbstractBlock.Properties.create(Material.WOOD).doesNotBlockMovement().hardnessAndResistance(1.0f).sound(SoundType.WOOD)));
    public static final Block YELLOW_BANNER = Blocks.register("yellow_banner", new BannerBlock(DyeColor.YELLOW, AbstractBlock.Properties.create(Material.WOOD).doesNotBlockMovement().hardnessAndResistance(1.0f).sound(SoundType.WOOD)));
    public static final Block LIME_BANNER = Blocks.register("lime_banner", new BannerBlock(DyeColor.LIME, AbstractBlock.Properties.create(Material.WOOD).doesNotBlockMovement().hardnessAndResistance(1.0f).sound(SoundType.WOOD)));
    public static final Block PINK_BANNER = Blocks.register("pink_banner", new BannerBlock(DyeColor.PINK, AbstractBlock.Properties.create(Material.WOOD).doesNotBlockMovement().hardnessAndResistance(1.0f).sound(SoundType.WOOD)));
    public static final Block GRAY_BANNER = Blocks.register("gray_banner", new BannerBlock(DyeColor.GRAY, AbstractBlock.Properties.create(Material.WOOD).doesNotBlockMovement().hardnessAndResistance(1.0f).sound(SoundType.WOOD)));
    public static final Block LIGHT_GRAY_BANNER = Blocks.register("light_gray_banner", new BannerBlock(DyeColor.LIGHT_GRAY, AbstractBlock.Properties.create(Material.WOOD).doesNotBlockMovement().hardnessAndResistance(1.0f).sound(SoundType.WOOD)));
    public static final Block CYAN_BANNER = Blocks.register("cyan_banner", new BannerBlock(DyeColor.CYAN, AbstractBlock.Properties.create(Material.WOOD).doesNotBlockMovement().hardnessAndResistance(1.0f).sound(SoundType.WOOD)));
    public static final Block PURPLE_BANNER = Blocks.register("purple_banner", new BannerBlock(DyeColor.PURPLE, AbstractBlock.Properties.create(Material.WOOD).doesNotBlockMovement().hardnessAndResistance(1.0f).sound(SoundType.WOOD)));
    public static final Block BLUE_BANNER = Blocks.register("blue_banner", new BannerBlock(DyeColor.BLUE, AbstractBlock.Properties.create(Material.WOOD).doesNotBlockMovement().hardnessAndResistance(1.0f).sound(SoundType.WOOD)));
    public static final Block BROWN_BANNER = Blocks.register("brown_banner", new BannerBlock(DyeColor.BROWN, AbstractBlock.Properties.create(Material.WOOD).doesNotBlockMovement().hardnessAndResistance(1.0f).sound(SoundType.WOOD)));
    public static final Block GREEN_BANNER = Blocks.register("green_banner", new BannerBlock(DyeColor.GREEN, AbstractBlock.Properties.create(Material.WOOD).doesNotBlockMovement().hardnessAndResistance(1.0f).sound(SoundType.WOOD)));
    public static final Block RED_BANNER = Blocks.register("red_banner", new BannerBlock(DyeColor.RED, AbstractBlock.Properties.create(Material.WOOD).doesNotBlockMovement().hardnessAndResistance(1.0f).sound(SoundType.WOOD)));
    public static final Block BLACK_BANNER = Blocks.register("black_banner", new BannerBlock(DyeColor.BLACK, AbstractBlock.Properties.create(Material.WOOD).doesNotBlockMovement().hardnessAndResistance(1.0f).sound(SoundType.WOOD)));
    public static final Block WHITE_WALL_BANNER = Blocks.register("white_wall_banner", new WallBannerBlock(DyeColor.WHITE, AbstractBlock.Properties.create(Material.WOOD).doesNotBlockMovement().hardnessAndResistance(1.0f).sound(SoundType.WOOD).lootFrom(WHITE_BANNER)));
    public static final Block ORANGE_WALL_BANNER = Blocks.register("orange_wall_banner", new WallBannerBlock(DyeColor.ORANGE, AbstractBlock.Properties.create(Material.WOOD).doesNotBlockMovement().hardnessAndResistance(1.0f).sound(SoundType.WOOD).lootFrom(ORANGE_BANNER)));
    public static final Block MAGENTA_WALL_BANNER = Blocks.register("magenta_wall_banner", new WallBannerBlock(DyeColor.MAGENTA, AbstractBlock.Properties.create(Material.WOOD).doesNotBlockMovement().hardnessAndResistance(1.0f).sound(SoundType.WOOD).lootFrom(MAGENTA_BANNER)));
    public static final Block LIGHT_BLUE_WALL_BANNER = Blocks.register("light_blue_wall_banner", new WallBannerBlock(DyeColor.LIGHT_BLUE, AbstractBlock.Properties.create(Material.WOOD).doesNotBlockMovement().hardnessAndResistance(1.0f).sound(SoundType.WOOD).lootFrom(LIGHT_BLUE_BANNER)));
    public static final Block YELLOW_WALL_BANNER = Blocks.register("yellow_wall_banner", new WallBannerBlock(DyeColor.YELLOW, AbstractBlock.Properties.create(Material.WOOD).doesNotBlockMovement().hardnessAndResistance(1.0f).sound(SoundType.WOOD).lootFrom(YELLOW_BANNER)));
    public static final Block LIME_WALL_BANNER = Blocks.register("lime_wall_banner", new WallBannerBlock(DyeColor.LIME, AbstractBlock.Properties.create(Material.WOOD).doesNotBlockMovement().hardnessAndResistance(1.0f).sound(SoundType.WOOD).lootFrom(LIME_BANNER)));
    public static final Block PINK_WALL_BANNER = Blocks.register("pink_wall_banner", new WallBannerBlock(DyeColor.PINK, AbstractBlock.Properties.create(Material.WOOD).doesNotBlockMovement().hardnessAndResistance(1.0f).sound(SoundType.WOOD).lootFrom(PINK_BANNER)));
    public static final Block GRAY_WALL_BANNER = Blocks.register("gray_wall_banner", new WallBannerBlock(DyeColor.GRAY, AbstractBlock.Properties.create(Material.WOOD).doesNotBlockMovement().hardnessAndResistance(1.0f).sound(SoundType.WOOD).lootFrom(GRAY_BANNER)));
    public static final Block LIGHT_GRAY_WALL_BANNER = Blocks.register("light_gray_wall_banner", new WallBannerBlock(DyeColor.LIGHT_GRAY, AbstractBlock.Properties.create(Material.WOOD).doesNotBlockMovement().hardnessAndResistance(1.0f).sound(SoundType.WOOD).lootFrom(LIGHT_GRAY_BANNER)));
    public static final Block CYAN_WALL_BANNER = Blocks.register("cyan_wall_banner", new WallBannerBlock(DyeColor.CYAN, AbstractBlock.Properties.create(Material.WOOD).doesNotBlockMovement().hardnessAndResistance(1.0f).sound(SoundType.WOOD).lootFrom(CYAN_BANNER)));
    public static final Block PURPLE_WALL_BANNER = Blocks.register("purple_wall_banner", new WallBannerBlock(DyeColor.PURPLE, AbstractBlock.Properties.create(Material.WOOD).doesNotBlockMovement().hardnessAndResistance(1.0f).sound(SoundType.WOOD).lootFrom(PURPLE_BANNER)));
    public static final Block BLUE_WALL_BANNER = Blocks.register("blue_wall_banner", new WallBannerBlock(DyeColor.BLUE, AbstractBlock.Properties.create(Material.WOOD).doesNotBlockMovement().hardnessAndResistance(1.0f).sound(SoundType.WOOD).lootFrom(BLUE_BANNER)));
    public static final Block BROWN_WALL_BANNER = Blocks.register("brown_wall_banner", new WallBannerBlock(DyeColor.BROWN, AbstractBlock.Properties.create(Material.WOOD).doesNotBlockMovement().hardnessAndResistance(1.0f).sound(SoundType.WOOD).lootFrom(BROWN_BANNER)));
    public static final Block GREEN_WALL_BANNER = Blocks.register("green_wall_banner", new WallBannerBlock(DyeColor.GREEN, AbstractBlock.Properties.create(Material.WOOD).doesNotBlockMovement().hardnessAndResistance(1.0f).sound(SoundType.WOOD).lootFrom(GREEN_BANNER)));
    public static final Block RED_WALL_BANNER = Blocks.register("red_wall_banner", new WallBannerBlock(DyeColor.RED, AbstractBlock.Properties.create(Material.WOOD).doesNotBlockMovement().hardnessAndResistance(1.0f).sound(SoundType.WOOD).lootFrom(RED_BANNER)));
    public static final Block BLACK_WALL_BANNER = Blocks.register("black_wall_banner", new WallBannerBlock(DyeColor.BLACK, AbstractBlock.Properties.create(Material.WOOD).doesNotBlockMovement().hardnessAndResistance(1.0f).sound(SoundType.WOOD).lootFrom(BLACK_BANNER)));
    public static final Block RED_SANDSTONE = Blocks.register("red_sandstone", new Block(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.ADOBE).setRequiresTool().hardnessAndResistance(0.8f)));
    public static final Block CHISELED_RED_SANDSTONE = Blocks.register("chiseled_red_sandstone", new Block(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.ADOBE).setRequiresTool().hardnessAndResistance(0.8f)));
    public static final Block CUT_RED_SANDSTONE = Blocks.register("cut_red_sandstone", new Block(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.ADOBE).setRequiresTool().hardnessAndResistance(0.8f)));
    public static final Block RED_SANDSTONE_STAIRS = Blocks.register("red_sandstone_stairs", new StairsBlock(RED_SANDSTONE.getDefaultState(), AbstractBlock.Properties.from(RED_SANDSTONE)));
    public static final Block OAK_SLAB = Blocks.register("oak_slab", new SlabBlock(AbstractBlock.Properties.create(Material.WOOD, MaterialColor.WOOD).hardnessAndResistance(2.0f, 3.0f).sound(SoundType.WOOD)));
    public static final Block SPRUCE_SLAB = Blocks.register("spruce_slab", new SlabBlock(AbstractBlock.Properties.create(Material.WOOD, MaterialColor.OBSIDIAN).hardnessAndResistance(2.0f, 3.0f).sound(SoundType.WOOD)));
    public static final Block BIRCH_SLAB = Blocks.register("birch_slab", new SlabBlock(AbstractBlock.Properties.create(Material.WOOD, MaterialColor.SAND).hardnessAndResistance(2.0f, 3.0f).sound(SoundType.WOOD)));
    public static final Block JUNGLE_SLAB = Blocks.register("jungle_slab", new SlabBlock(AbstractBlock.Properties.create(Material.WOOD, MaterialColor.DIRT).hardnessAndResistance(2.0f, 3.0f).sound(SoundType.WOOD)));
    public static final Block ACACIA_SLAB = Blocks.register("acacia_slab", new SlabBlock(AbstractBlock.Properties.create(Material.WOOD, MaterialColor.ADOBE).hardnessAndResistance(2.0f, 3.0f).sound(SoundType.WOOD)));
    public static final Block DARK_OAK_SLAB = Blocks.register("dark_oak_slab", new SlabBlock(AbstractBlock.Properties.create(Material.WOOD, MaterialColor.BROWN).hardnessAndResistance(2.0f, 3.0f).sound(SoundType.WOOD)));
    public static final Block STONE_SLAB = Blocks.register("stone_slab", new SlabBlock(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.STONE).setRequiresTool().hardnessAndResistance(2.0f, 6.0f)));
    public static final Block SMOOTH_STONE_SLAB = Blocks.register("smooth_stone_slab", new SlabBlock(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.STONE).setRequiresTool().hardnessAndResistance(2.0f, 6.0f)));
    public static final Block SANDSTONE_SLAB = Blocks.register("sandstone_slab", new SlabBlock(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.SAND).setRequiresTool().hardnessAndResistance(2.0f, 6.0f)));
    public static final Block CUT_SANDSTONE_SLAB = Blocks.register("cut_sandstone_slab", new SlabBlock(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.SAND).setRequiresTool().hardnessAndResistance(2.0f, 6.0f)));
    public static final Block PETRIFIED_OAK_SLAB = Blocks.register("petrified_oak_slab", new SlabBlock(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.WOOD).setRequiresTool().hardnessAndResistance(2.0f, 6.0f)));
    public static final Block COBBLESTONE_SLAB = Blocks.register("cobblestone_slab", new SlabBlock(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.STONE).setRequiresTool().hardnessAndResistance(2.0f, 6.0f)));
    public static final Block BRICK_SLAB = Blocks.register("brick_slab", new SlabBlock(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.RED).setRequiresTool().hardnessAndResistance(2.0f, 6.0f)));
    public static final Block STONE_BRICK_SLAB = Blocks.register("stone_brick_slab", new SlabBlock(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.STONE).setRequiresTool().hardnessAndResistance(2.0f, 6.0f)));
    public static final Block NETHER_BRICK_SLAB = Blocks.register("nether_brick_slab", new SlabBlock(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.NETHERRACK).setRequiresTool().hardnessAndResistance(2.0f, 6.0f).sound(SoundType.NETHER_BRICK)));
    public static final Block QUARTZ_SLAB = Blocks.register("quartz_slab", new SlabBlock(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.QUARTZ).setRequiresTool().hardnessAndResistance(2.0f, 6.0f)));
    public static final Block RED_SANDSTONE_SLAB = Blocks.register("red_sandstone_slab", new SlabBlock(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.ADOBE).setRequiresTool().hardnessAndResistance(2.0f, 6.0f)));
    public static final Block CUT_RED_SANDSTONE_SLAB = Blocks.register("cut_red_sandstone_slab", new SlabBlock(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.ADOBE).setRequiresTool().hardnessAndResistance(2.0f, 6.0f)));
    public static final Block PURPUR_SLAB = Blocks.register("purpur_slab", new SlabBlock(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.MAGENTA).setRequiresTool().hardnessAndResistance(2.0f, 6.0f)));
    public static final Block SMOOTH_STONE = Blocks.register("smooth_stone", new Block(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.STONE).setRequiresTool().hardnessAndResistance(2.0f, 6.0f)));
    public static final Block SMOOTH_SANDSTONE = Blocks.register("smooth_sandstone", new Block(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.SAND).setRequiresTool().hardnessAndResistance(2.0f, 6.0f)));
    public static final Block SMOOTH_QUARTZ = Blocks.register("smooth_quartz", new Block(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.QUARTZ).setRequiresTool().hardnessAndResistance(2.0f, 6.0f)));
    public static final Block SMOOTH_RED_SANDSTONE = Blocks.register("smooth_red_sandstone", new Block(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.ADOBE).setRequiresTool().hardnessAndResistance(2.0f, 6.0f)));
    public static final Block SPRUCE_FENCE_GATE = Blocks.register("spruce_fence_gate", new FenceGateBlock(AbstractBlock.Properties.create(Material.WOOD, SPRUCE_PLANKS.getMaterialColor()).hardnessAndResistance(2.0f, 3.0f).sound(SoundType.WOOD)));
    public static final Block BIRCH_FENCE_GATE = Blocks.register("birch_fence_gate", new FenceGateBlock(AbstractBlock.Properties.create(Material.WOOD, BIRCH_PLANKS.getMaterialColor()).hardnessAndResistance(2.0f, 3.0f).sound(SoundType.WOOD)));
    public static final Block JUNGLE_FENCE_GATE = Blocks.register("jungle_fence_gate", new FenceGateBlock(AbstractBlock.Properties.create(Material.WOOD, JUNGLE_PLANKS.getMaterialColor()).hardnessAndResistance(2.0f, 3.0f).sound(SoundType.WOOD)));
    public static final Block ACACIA_FENCE_GATE = Blocks.register("acacia_fence_gate", new FenceGateBlock(AbstractBlock.Properties.create(Material.WOOD, ACACIA_PLANKS.getMaterialColor()).hardnessAndResistance(2.0f, 3.0f).sound(SoundType.WOOD)));
    public static final Block DARK_OAK_FENCE_GATE = Blocks.register("dark_oak_fence_gate", new FenceGateBlock(AbstractBlock.Properties.create(Material.WOOD, DARK_OAK_PLANKS.getMaterialColor()).hardnessAndResistance(2.0f, 3.0f).sound(SoundType.WOOD)));
    public static final Block SPRUCE_FENCE = Blocks.register("spruce_fence", new FenceBlock(AbstractBlock.Properties.create(Material.WOOD, SPRUCE_PLANKS.getMaterialColor()).hardnessAndResistance(2.0f, 3.0f).sound(SoundType.WOOD)));
    public static final Block BIRCH_FENCE = Blocks.register("birch_fence", new FenceBlock(AbstractBlock.Properties.create(Material.WOOD, BIRCH_PLANKS.getMaterialColor()).hardnessAndResistance(2.0f, 3.0f).sound(SoundType.WOOD)));
    public static final Block JUNGLE_FENCE = Blocks.register("jungle_fence", new FenceBlock(AbstractBlock.Properties.create(Material.WOOD, JUNGLE_PLANKS.getMaterialColor()).hardnessAndResistance(2.0f, 3.0f).sound(SoundType.WOOD)));
    public static final Block ACACIA_FENCE = Blocks.register("acacia_fence", new FenceBlock(AbstractBlock.Properties.create(Material.WOOD, ACACIA_PLANKS.getMaterialColor()).hardnessAndResistance(2.0f, 3.0f).sound(SoundType.WOOD)));
    public static final Block DARK_OAK_FENCE = Blocks.register("dark_oak_fence", new FenceBlock(AbstractBlock.Properties.create(Material.WOOD, DARK_OAK_PLANKS.getMaterialColor()).hardnessAndResistance(2.0f, 3.0f).sound(SoundType.WOOD)));
    public static final Block SPRUCE_DOOR = Blocks.register("spruce_door", new DoorBlock(AbstractBlock.Properties.create(Material.WOOD, SPRUCE_PLANKS.getMaterialColor()).hardnessAndResistance(3.0f).sound(SoundType.WOOD).notSolid()));
    public static final Block BIRCH_DOOR = Blocks.register("birch_door", new DoorBlock(AbstractBlock.Properties.create(Material.WOOD, BIRCH_PLANKS.getMaterialColor()).hardnessAndResistance(3.0f).sound(SoundType.WOOD).notSolid()));
    public static final Block JUNGLE_DOOR = Blocks.register("jungle_door", new DoorBlock(AbstractBlock.Properties.create(Material.WOOD, JUNGLE_PLANKS.getMaterialColor()).hardnessAndResistance(3.0f).sound(SoundType.WOOD).notSolid()));
    public static final Block ACACIA_DOOR = Blocks.register("acacia_door", new DoorBlock(AbstractBlock.Properties.create(Material.WOOD, ACACIA_PLANKS.getMaterialColor()).hardnessAndResistance(3.0f).sound(SoundType.WOOD).notSolid()));
    public static final Block DARK_OAK_DOOR = Blocks.register("dark_oak_door", new DoorBlock(AbstractBlock.Properties.create(Material.WOOD, DARK_OAK_PLANKS.getMaterialColor()).hardnessAndResistance(3.0f).sound(SoundType.WOOD).notSolid()));
    public static final Block END_ROD = Blocks.register("end_rod", new EndRodBlock(AbstractBlock.Properties.create(Material.MISCELLANEOUS).zeroHardnessAndResistance().setLightLevel(Blocks::lambda$static$20).sound(SoundType.WOOD).notSolid()));
    public static final Block CHORUS_PLANT = Blocks.register("chorus_plant", new ChorusPlantBlock(AbstractBlock.Properties.create(Material.PLANTS, MaterialColor.PURPLE).hardnessAndResistance(0.4f).sound(SoundType.WOOD).notSolid()));
    public static final Block CHORUS_FLOWER = Blocks.register("chorus_flower", new ChorusFlowerBlock((ChorusPlantBlock)CHORUS_PLANT, AbstractBlock.Properties.create(Material.PLANTS, MaterialColor.PURPLE).tickRandomly().hardnessAndResistance(0.4f).sound(SoundType.WOOD).notSolid()));
    public static final Block PURPUR_BLOCK = Blocks.register("purpur_block", new Block(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.MAGENTA).setRequiresTool().hardnessAndResistance(1.5f, 6.0f)));
    public static final Block PURPUR_PILLAR = Blocks.register("purpur_pillar", new RotatedPillarBlock(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.MAGENTA).setRequiresTool().hardnessAndResistance(1.5f, 6.0f)));
    public static final Block PURPUR_STAIRS = Blocks.register("purpur_stairs", new StairsBlock(PURPUR_BLOCK.getDefaultState(), AbstractBlock.Properties.from(PURPUR_BLOCK)));
    public static final Block END_STONE_BRICKS = Blocks.register("end_stone_bricks", new Block(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.SAND).setRequiresTool().hardnessAndResistance(3.0f, 9.0f)));
    public static final Block BEETROOTS = Blocks.register("beetroots", new BeetrootBlock(AbstractBlock.Properties.create(Material.PLANTS).doesNotBlockMovement().tickRandomly().zeroHardnessAndResistance().sound(SoundType.CROP)));
    public static final Block GRASS_PATH = Blocks.register("grass_path", new GrassPathBlock(AbstractBlock.Properties.create(Material.EARTH).hardnessAndResistance(0.65f).sound(SoundType.PLANT).setBlocksVision(Blocks::needsPostProcessing).setSuffocates(Blocks::needsPostProcessing)));
    public static final Block END_GATEWAY = Blocks.register("end_gateway", new EndGatewayBlock(AbstractBlock.Properties.create(Material.PORTAL, MaterialColor.BLACK).doesNotBlockMovement().setLightLevel(Blocks::lambda$static$21).hardnessAndResistance(-1.0f, 3600000.0f).noDrops()));
    public static final Block REPEATING_COMMAND_BLOCK = Blocks.register("repeating_command_block", new CommandBlockBlock(AbstractBlock.Properties.create(Material.IRON, MaterialColor.PURPLE).setRequiresTool().hardnessAndResistance(-1.0f, 3600000.0f).noDrops()));
    public static final Block CHAIN_COMMAND_BLOCK = Blocks.register("chain_command_block", new CommandBlockBlock(AbstractBlock.Properties.create(Material.IRON, MaterialColor.GREEN).setRequiresTool().hardnessAndResistance(-1.0f, 3600000.0f).noDrops()));
    public static final Block FROSTED_ICE = Blocks.register("frosted_ice", new FrostedIceBlock(AbstractBlock.Properties.create(Material.ICE).slipperiness(0.98f).tickRandomly().hardnessAndResistance(0.5f).sound(SoundType.GLASS).notSolid().setAllowsSpawn(Blocks::lambda$static$22)));
    public static final Block MAGMA_BLOCK = Blocks.register("magma_block", new MagmaBlock(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.NETHERRACK).setRequiresTool().setLightLevel(Blocks::lambda$static$23).tickRandomly().hardnessAndResistance(0.5f).setAllowsSpawn(Blocks::lambda$static$24).setNeedsPostProcessing(Blocks::needsPostProcessing).setEmmisiveRendering(Blocks::needsPostProcessing)));
    public static final Block NETHER_WART_BLOCK = Blocks.register("nether_wart_block", new Block(AbstractBlock.Properties.create(Material.ORGANIC, MaterialColor.RED).hardnessAndResistance(1.0f).sound(SoundType.WART)));
    public static final Block RED_NETHER_BRICKS = Blocks.register("red_nether_bricks", new Block(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.NETHERRACK).setRequiresTool().hardnessAndResistance(2.0f, 6.0f).sound(SoundType.NETHER_BRICK)));
    public static final Block BONE_BLOCK = Blocks.register("bone_block", new RotatedPillarBlock(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.SAND).setRequiresTool().hardnessAndResistance(2.0f).sound(SoundType.BONE)));
    public static final Block STRUCTURE_VOID = Blocks.register("structure_void", new StructureVoidBlock(AbstractBlock.Properties.create(Material.STRUCTURE_VOID).doesNotBlockMovement().noDrops()));
    public static final Block OBSERVER = Blocks.register("observer", new ObserverBlock(AbstractBlock.Properties.create(Material.ROCK).hardnessAndResistance(3.0f).setRequiresTool().setOpaque(Blocks::isntSolid)));
    public static final Block SHULKER_BOX = Blocks.register("shulker_box", Blocks.createShulkerBoxFromColorAndProperties(null, AbstractBlock.Properties.create(Material.SHULKER)));
    public static final Block WHITE_SHULKER_BOX = Blocks.register("white_shulker_box", Blocks.createShulkerBoxFromColorAndProperties(DyeColor.WHITE, AbstractBlock.Properties.create(Material.SHULKER, MaterialColor.SNOW)));
    public static final Block ORANGE_SHULKER_BOX = Blocks.register("orange_shulker_box", Blocks.createShulkerBoxFromColorAndProperties(DyeColor.ORANGE, AbstractBlock.Properties.create(Material.SHULKER, MaterialColor.ADOBE)));
    public static final Block MAGENTA_SHULKER_BOX = Blocks.register("magenta_shulker_box", Blocks.createShulkerBoxFromColorAndProperties(DyeColor.MAGENTA, AbstractBlock.Properties.create(Material.SHULKER, MaterialColor.MAGENTA)));
    public static final Block LIGHT_BLUE_SHULKER_BOX = Blocks.register("light_blue_shulker_box", Blocks.createShulkerBoxFromColorAndProperties(DyeColor.LIGHT_BLUE, AbstractBlock.Properties.create(Material.SHULKER, MaterialColor.LIGHT_BLUE)));
    public static final Block YELLOW_SHULKER_BOX = Blocks.register("yellow_shulker_box", Blocks.createShulkerBoxFromColorAndProperties(DyeColor.YELLOW, AbstractBlock.Properties.create(Material.SHULKER, MaterialColor.YELLOW)));
    public static final Block LIME_SHULKER_BOX = Blocks.register("lime_shulker_box", Blocks.createShulkerBoxFromColorAndProperties(DyeColor.LIME, AbstractBlock.Properties.create(Material.SHULKER, MaterialColor.LIME)));
    public static final Block PINK_SHULKER_BOX = Blocks.register("pink_shulker_box", Blocks.createShulkerBoxFromColorAndProperties(DyeColor.PINK, AbstractBlock.Properties.create(Material.SHULKER, MaterialColor.PINK)));
    public static final Block GRAY_SHULKER_BOX = Blocks.register("gray_shulker_box", Blocks.createShulkerBoxFromColorAndProperties(DyeColor.GRAY, AbstractBlock.Properties.create(Material.SHULKER, MaterialColor.GRAY)));
    public static final Block LIGHT_GRAY_SHULKER_BOX = Blocks.register("light_gray_shulker_box", Blocks.createShulkerBoxFromColorAndProperties(DyeColor.LIGHT_GRAY, AbstractBlock.Properties.create(Material.SHULKER, MaterialColor.LIGHT_GRAY)));
    public static final Block CYAN_SHULKER_BOX = Blocks.register("cyan_shulker_box", Blocks.createShulkerBoxFromColorAndProperties(DyeColor.CYAN, AbstractBlock.Properties.create(Material.SHULKER, MaterialColor.CYAN)));
    public static final Block PURPLE_SHULKER_BOX = Blocks.register("purple_shulker_box", Blocks.createShulkerBoxFromColorAndProperties(DyeColor.PURPLE, AbstractBlock.Properties.create(Material.SHULKER, MaterialColor.PURPLE_TERRACOTTA)));
    public static final Block BLUE_SHULKER_BOX = Blocks.register("blue_shulker_box", Blocks.createShulkerBoxFromColorAndProperties(DyeColor.BLUE, AbstractBlock.Properties.create(Material.SHULKER, MaterialColor.BLUE)));
    public static final Block BROWN_SHULKER_BOX = Blocks.register("brown_shulker_box", Blocks.createShulkerBoxFromColorAndProperties(DyeColor.BROWN, AbstractBlock.Properties.create(Material.SHULKER, MaterialColor.BROWN)));
    public static final Block GREEN_SHULKER_BOX = Blocks.register("green_shulker_box", Blocks.createShulkerBoxFromColorAndProperties(DyeColor.GREEN, AbstractBlock.Properties.create(Material.SHULKER, MaterialColor.GREEN)));
    public static final Block RED_SHULKER_BOX = Blocks.register("red_shulker_box", Blocks.createShulkerBoxFromColorAndProperties(DyeColor.RED, AbstractBlock.Properties.create(Material.SHULKER, MaterialColor.RED)));
    public static final Block BLACK_SHULKER_BOX = Blocks.register("black_shulker_box", Blocks.createShulkerBoxFromColorAndProperties(DyeColor.BLACK, AbstractBlock.Properties.create(Material.SHULKER, MaterialColor.BLACK)));
    public static final Block WHITE_GLAZED_TERRACOTTA = Blocks.register("white_glazed_terracotta", new GlazedTerracottaBlock(AbstractBlock.Properties.create(Material.ROCK, DyeColor.WHITE).setRequiresTool().hardnessAndResistance(1.4f)));
    public static final Block ORANGE_GLAZED_TERRACOTTA = Blocks.register("orange_glazed_terracotta", new GlazedTerracottaBlock(AbstractBlock.Properties.create(Material.ROCK, DyeColor.ORANGE).setRequiresTool().hardnessAndResistance(1.4f)));
    public static final Block MAGENTA_GLAZED_TERRACOTTA = Blocks.register("magenta_glazed_terracotta", new GlazedTerracottaBlock(AbstractBlock.Properties.create(Material.ROCK, DyeColor.MAGENTA).setRequiresTool().hardnessAndResistance(1.4f)));
    public static final Block LIGHT_BLUE_GLAZED_TERRACOTTA = Blocks.register("light_blue_glazed_terracotta", new GlazedTerracottaBlock(AbstractBlock.Properties.create(Material.ROCK, DyeColor.LIGHT_BLUE).setRequiresTool().hardnessAndResistance(1.4f)));
    public static final Block YELLOW_GLAZED_TERRACOTTA = Blocks.register("yellow_glazed_terracotta", new GlazedTerracottaBlock(AbstractBlock.Properties.create(Material.ROCK, DyeColor.YELLOW).setRequiresTool().hardnessAndResistance(1.4f)));
    public static final Block LIME_GLAZED_TERRACOTTA = Blocks.register("lime_glazed_terracotta", new GlazedTerracottaBlock(AbstractBlock.Properties.create(Material.ROCK, DyeColor.LIME).setRequiresTool().hardnessAndResistance(1.4f)));
    public static final Block PINK_GLAZED_TERRACOTTA = Blocks.register("pink_glazed_terracotta", new GlazedTerracottaBlock(AbstractBlock.Properties.create(Material.ROCK, DyeColor.PINK).setRequiresTool().hardnessAndResistance(1.4f)));
    public static final Block GRAY_GLAZED_TERRACOTTA = Blocks.register("gray_glazed_terracotta", new GlazedTerracottaBlock(AbstractBlock.Properties.create(Material.ROCK, DyeColor.GRAY).setRequiresTool().hardnessAndResistance(1.4f)));
    public static final Block LIGHT_GRAY_GLAZED_TERRACOTTA = Blocks.register("light_gray_glazed_terracotta", new GlazedTerracottaBlock(AbstractBlock.Properties.create(Material.ROCK, DyeColor.LIGHT_GRAY).setRequiresTool().hardnessAndResistance(1.4f)));
    public static final Block CYAN_GLAZED_TERRACOTTA = Blocks.register("cyan_glazed_terracotta", new GlazedTerracottaBlock(AbstractBlock.Properties.create(Material.ROCK, DyeColor.CYAN).setRequiresTool().hardnessAndResistance(1.4f)));
    public static final Block PURPLE_GLAZED_TERRACOTTA = Blocks.register("purple_glazed_terracotta", new GlazedTerracottaBlock(AbstractBlock.Properties.create(Material.ROCK, DyeColor.PURPLE).setRequiresTool().hardnessAndResistance(1.4f)));
    public static final Block BLUE_GLAZED_TERRACOTTA = Blocks.register("blue_glazed_terracotta", new GlazedTerracottaBlock(AbstractBlock.Properties.create(Material.ROCK, DyeColor.BLUE).setRequiresTool().hardnessAndResistance(1.4f)));
    public static final Block BROWN_GLAZED_TERRACOTTA = Blocks.register("brown_glazed_terracotta", new GlazedTerracottaBlock(AbstractBlock.Properties.create(Material.ROCK, DyeColor.BROWN).setRequiresTool().hardnessAndResistance(1.4f)));
    public static final Block GREEN_GLAZED_TERRACOTTA = Blocks.register("green_glazed_terracotta", new GlazedTerracottaBlock(AbstractBlock.Properties.create(Material.ROCK, DyeColor.GREEN).setRequiresTool().hardnessAndResistance(1.4f)));
    public static final Block RED_GLAZED_TERRACOTTA = Blocks.register("red_glazed_terracotta", new GlazedTerracottaBlock(AbstractBlock.Properties.create(Material.ROCK, DyeColor.RED).setRequiresTool().hardnessAndResistance(1.4f)));
    public static final Block BLACK_GLAZED_TERRACOTTA = Blocks.register("black_glazed_terracotta", new GlazedTerracottaBlock(AbstractBlock.Properties.create(Material.ROCK, DyeColor.BLACK).setRequiresTool().hardnessAndResistance(1.4f)));
    public static final Block WHITE_CONCRETE = Blocks.register("white_concrete", new Block(AbstractBlock.Properties.create(Material.ROCK, DyeColor.WHITE).setRequiresTool().hardnessAndResistance(1.8f)));
    public static final Block ORANGE_CONCRETE = Blocks.register("orange_concrete", new Block(AbstractBlock.Properties.create(Material.ROCK, DyeColor.ORANGE).setRequiresTool().hardnessAndResistance(1.8f)));
    public static final Block MAGENTA_CONCRETE = Blocks.register("magenta_concrete", new Block(AbstractBlock.Properties.create(Material.ROCK, DyeColor.MAGENTA).setRequiresTool().hardnessAndResistance(1.8f)));
    public static final Block LIGHT_BLUE_CONCRETE = Blocks.register("light_blue_concrete", new Block(AbstractBlock.Properties.create(Material.ROCK, DyeColor.LIGHT_BLUE).setRequiresTool().hardnessAndResistance(1.8f)));
    public static final Block YELLOW_CONCRETE = Blocks.register("yellow_concrete", new Block(AbstractBlock.Properties.create(Material.ROCK, DyeColor.YELLOW).setRequiresTool().hardnessAndResistance(1.8f)));
    public static final Block LIME_CONCRETE = Blocks.register("lime_concrete", new Block(AbstractBlock.Properties.create(Material.ROCK, DyeColor.LIME).setRequiresTool().hardnessAndResistance(1.8f)));
    public static final Block PINK_CONCRETE = Blocks.register("pink_concrete", new Block(AbstractBlock.Properties.create(Material.ROCK, DyeColor.PINK).setRequiresTool().hardnessAndResistance(1.8f)));
    public static final Block GRAY_CONCRETE = Blocks.register("gray_concrete", new Block(AbstractBlock.Properties.create(Material.ROCK, DyeColor.GRAY).setRequiresTool().hardnessAndResistance(1.8f)));
    public static final Block LIGHT_GRAY_CONCRETE = Blocks.register("light_gray_concrete", new Block(AbstractBlock.Properties.create(Material.ROCK, DyeColor.LIGHT_GRAY).setRequiresTool().hardnessAndResistance(1.8f)));
    public static final Block CYAN_CONCRETE = Blocks.register("cyan_concrete", new Block(AbstractBlock.Properties.create(Material.ROCK, DyeColor.CYAN).setRequiresTool().hardnessAndResistance(1.8f)));
    public static final Block PURPLE_CONCRETE = Blocks.register("purple_concrete", new Block(AbstractBlock.Properties.create(Material.ROCK, DyeColor.PURPLE).setRequiresTool().hardnessAndResistance(1.8f)));
    public static final Block BLUE_CONCRETE = Blocks.register("blue_concrete", new Block(AbstractBlock.Properties.create(Material.ROCK, DyeColor.BLUE).setRequiresTool().hardnessAndResistance(1.8f)));
    public static final Block BROWN_CONCRETE = Blocks.register("brown_concrete", new Block(AbstractBlock.Properties.create(Material.ROCK, DyeColor.BROWN).setRequiresTool().hardnessAndResistance(1.8f)));
    public static final Block GREEN_CONCRETE = Blocks.register("green_concrete", new Block(AbstractBlock.Properties.create(Material.ROCK, DyeColor.GREEN).setRequiresTool().hardnessAndResistance(1.8f)));
    public static final Block RED_CONCRETE = Blocks.register("red_concrete", new Block(AbstractBlock.Properties.create(Material.ROCK, DyeColor.RED).setRequiresTool().hardnessAndResistance(1.8f)));
    public static final Block BLACK_CONCRETE = Blocks.register("black_concrete", new Block(AbstractBlock.Properties.create(Material.ROCK, DyeColor.BLACK).setRequiresTool().hardnessAndResistance(1.8f)));
    public static final Block WHITE_CONCRETE_POWDER = Blocks.register("white_concrete_powder", new ConcretePowderBlock(WHITE_CONCRETE, AbstractBlock.Properties.create(Material.SAND, DyeColor.WHITE).hardnessAndResistance(0.5f).sound(SoundType.SAND)));
    public static final Block ORANGE_CONCRETE_POWDER = Blocks.register("orange_concrete_powder", new ConcretePowderBlock(ORANGE_CONCRETE, AbstractBlock.Properties.create(Material.SAND, DyeColor.ORANGE).hardnessAndResistance(0.5f).sound(SoundType.SAND)));
    public static final Block MAGENTA_CONCRETE_POWDER = Blocks.register("magenta_concrete_powder", new ConcretePowderBlock(MAGENTA_CONCRETE, AbstractBlock.Properties.create(Material.SAND, DyeColor.MAGENTA).hardnessAndResistance(0.5f).sound(SoundType.SAND)));
    public static final Block LIGHT_BLUE_CONCRETE_POWDER = Blocks.register("light_blue_concrete_powder", new ConcretePowderBlock(LIGHT_BLUE_CONCRETE, AbstractBlock.Properties.create(Material.SAND, DyeColor.LIGHT_BLUE).hardnessAndResistance(0.5f).sound(SoundType.SAND)));
    public static final Block YELLOW_CONCRETE_POWDER = Blocks.register("yellow_concrete_powder", new ConcretePowderBlock(YELLOW_CONCRETE, AbstractBlock.Properties.create(Material.SAND, DyeColor.YELLOW).hardnessAndResistance(0.5f).sound(SoundType.SAND)));
    public static final Block LIME_CONCRETE_POWDER = Blocks.register("lime_concrete_powder", new ConcretePowderBlock(LIME_CONCRETE, AbstractBlock.Properties.create(Material.SAND, DyeColor.LIME).hardnessAndResistance(0.5f).sound(SoundType.SAND)));
    public static final Block PINK_CONCRETE_POWDER = Blocks.register("pink_concrete_powder", new ConcretePowderBlock(PINK_CONCRETE, AbstractBlock.Properties.create(Material.SAND, DyeColor.PINK).hardnessAndResistance(0.5f).sound(SoundType.SAND)));
    public static final Block GRAY_CONCRETE_POWDER = Blocks.register("gray_concrete_powder", new ConcretePowderBlock(GRAY_CONCRETE, AbstractBlock.Properties.create(Material.SAND, DyeColor.GRAY).hardnessAndResistance(0.5f).sound(SoundType.SAND)));
    public static final Block LIGHT_GRAY_CONCRETE_POWDER = Blocks.register("light_gray_concrete_powder", new ConcretePowderBlock(LIGHT_GRAY_CONCRETE, AbstractBlock.Properties.create(Material.SAND, DyeColor.LIGHT_GRAY).hardnessAndResistance(0.5f).sound(SoundType.SAND)));
    public static final Block CYAN_CONCRETE_POWDER = Blocks.register("cyan_concrete_powder", new ConcretePowderBlock(CYAN_CONCRETE, AbstractBlock.Properties.create(Material.SAND, DyeColor.CYAN).hardnessAndResistance(0.5f).sound(SoundType.SAND)));
    public static final Block PURPLE_CONCRETE_POWDER = Blocks.register("purple_concrete_powder", new ConcretePowderBlock(PURPLE_CONCRETE, AbstractBlock.Properties.create(Material.SAND, DyeColor.PURPLE).hardnessAndResistance(0.5f).sound(SoundType.SAND)));
    public static final Block BLUE_CONCRETE_POWDER = Blocks.register("blue_concrete_powder", new ConcretePowderBlock(BLUE_CONCRETE, AbstractBlock.Properties.create(Material.SAND, DyeColor.BLUE).hardnessAndResistance(0.5f).sound(SoundType.SAND)));
    public static final Block BROWN_CONCRETE_POWDER = Blocks.register("brown_concrete_powder", new ConcretePowderBlock(BROWN_CONCRETE, AbstractBlock.Properties.create(Material.SAND, DyeColor.BROWN).hardnessAndResistance(0.5f).sound(SoundType.SAND)));
    public static final Block GREEN_CONCRETE_POWDER = Blocks.register("green_concrete_powder", new ConcretePowderBlock(GREEN_CONCRETE, AbstractBlock.Properties.create(Material.SAND, DyeColor.GREEN).hardnessAndResistance(0.5f).sound(SoundType.SAND)));
    public static final Block RED_CONCRETE_POWDER = Blocks.register("red_concrete_powder", new ConcretePowderBlock(RED_CONCRETE, AbstractBlock.Properties.create(Material.SAND, DyeColor.RED).hardnessAndResistance(0.5f).sound(SoundType.SAND)));
    public static final Block BLACK_CONCRETE_POWDER = Blocks.register("black_concrete_powder", new ConcretePowderBlock(BLACK_CONCRETE, AbstractBlock.Properties.create(Material.SAND, DyeColor.BLACK).hardnessAndResistance(0.5f).sound(SoundType.SAND)));
    public static final Block KELP = Blocks.register("kelp", new KelpTopBlock(AbstractBlock.Properties.create(Material.OCEAN_PLANT).doesNotBlockMovement().tickRandomly().zeroHardnessAndResistance().sound(SoundType.WET_GRASS)));
    public static final Block KELP_PLANT = Blocks.register("kelp_plant", new KelpBlock(AbstractBlock.Properties.create(Material.OCEAN_PLANT).doesNotBlockMovement().zeroHardnessAndResistance().sound(SoundType.WET_GRASS)));
    public static final Block DRIED_KELP_BLOCK = Blocks.register("dried_kelp_block", new Block(AbstractBlock.Properties.create(Material.ORGANIC, MaterialColor.GREEN).hardnessAndResistance(0.5f, 2.5f).sound(SoundType.PLANT)));
    public static final Block TURTLE_EGG = Blocks.register("turtle_egg", new TurtleEggBlock(AbstractBlock.Properties.create(Material.DRAGON_EGG, MaterialColor.SAND).hardnessAndResistance(0.5f).sound(SoundType.METAL).tickRandomly().notSolid()));
    public static final Block DEAD_TUBE_CORAL_BLOCK = Blocks.register("dead_tube_coral_block", new Block(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.GRAY).setRequiresTool().hardnessAndResistance(1.5f, 6.0f)));
    public static final Block DEAD_BRAIN_CORAL_BLOCK = Blocks.register("dead_brain_coral_block", new Block(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.GRAY).setRequiresTool().hardnessAndResistance(1.5f, 6.0f)));
    public static final Block DEAD_BUBBLE_CORAL_BLOCK = Blocks.register("dead_bubble_coral_block", new Block(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.GRAY).setRequiresTool().hardnessAndResistance(1.5f, 6.0f)));
    public static final Block DEAD_FIRE_CORAL_BLOCK = Blocks.register("dead_fire_coral_block", new Block(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.GRAY).setRequiresTool().hardnessAndResistance(1.5f, 6.0f)));
    public static final Block DEAD_HORN_CORAL_BLOCK = Blocks.register("dead_horn_coral_block", new Block(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.GRAY).setRequiresTool().hardnessAndResistance(1.5f, 6.0f)));
    public static final Block TUBE_CORAL_BLOCK = Blocks.register("tube_coral_block", new CoralBlock(DEAD_TUBE_CORAL_BLOCK, AbstractBlock.Properties.create(Material.ROCK, MaterialColor.BLUE).setRequiresTool().hardnessAndResistance(1.5f, 6.0f).sound(SoundType.CORAL)));
    public static final Block BRAIN_CORAL_BLOCK = Blocks.register("brain_coral_block", new CoralBlock(DEAD_BRAIN_CORAL_BLOCK, AbstractBlock.Properties.create(Material.ROCK, MaterialColor.PINK).setRequiresTool().hardnessAndResistance(1.5f, 6.0f).sound(SoundType.CORAL)));
    public static final Block BUBBLE_CORAL_BLOCK = Blocks.register("bubble_coral_block", new CoralBlock(DEAD_BUBBLE_CORAL_BLOCK, AbstractBlock.Properties.create(Material.ROCK, MaterialColor.PURPLE).setRequiresTool().hardnessAndResistance(1.5f, 6.0f).sound(SoundType.CORAL)));
    public static final Block FIRE_CORAL_BLOCK = Blocks.register("fire_coral_block", new CoralBlock(DEAD_FIRE_CORAL_BLOCK, AbstractBlock.Properties.create(Material.ROCK, MaterialColor.RED).setRequiresTool().hardnessAndResistance(1.5f, 6.0f).sound(SoundType.CORAL)));
    public static final Block HORN_CORAL_BLOCK = Blocks.register("horn_coral_block", new CoralBlock(DEAD_HORN_CORAL_BLOCK, AbstractBlock.Properties.create(Material.ROCK, MaterialColor.YELLOW).setRequiresTool().hardnessAndResistance(1.5f, 6.0f).sound(SoundType.CORAL)));
    public static final Block DEAD_TUBE_CORAL = Blocks.register("dead_tube_coral", new DeadCoralPlantBlock(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.GRAY).setRequiresTool().doesNotBlockMovement().zeroHardnessAndResistance()));
    public static final Block DEAD_BRAIN_CORAL = Blocks.register("dead_brain_coral", new DeadCoralPlantBlock(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.GRAY).setRequiresTool().doesNotBlockMovement().zeroHardnessAndResistance()));
    public static final Block DEAD_BUBBLE_CORAL = Blocks.register("dead_bubble_coral", new DeadCoralPlantBlock(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.GRAY).setRequiresTool().doesNotBlockMovement().zeroHardnessAndResistance()));
    public static final Block DEAD_FIRE_CORAL = Blocks.register("dead_fire_coral", new DeadCoralPlantBlock(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.GRAY).setRequiresTool().doesNotBlockMovement().zeroHardnessAndResistance()));
    public static final Block DEAD_HORN_CORAL = Blocks.register("dead_horn_coral", new DeadCoralPlantBlock(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.GRAY).setRequiresTool().doesNotBlockMovement().zeroHardnessAndResistance()));
    public static final Block TUBE_CORAL = Blocks.register("tube_coral", new CoralPlantBlock(DEAD_TUBE_CORAL, AbstractBlock.Properties.create(Material.OCEAN_PLANT, MaterialColor.BLUE).doesNotBlockMovement().zeroHardnessAndResistance().sound(SoundType.WET_GRASS)));
    public static final Block BRAIN_CORAL = Blocks.register("brain_coral", new CoralPlantBlock(DEAD_BRAIN_CORAL, AbstractBlock.Properties.create(Material.OCEAN_PLANT, MaterialColor.PINK).doesNotBlockMovement().zeroHardnessAndResistance().sound(SoundType.WET_GRASS)));
    public static final Block BUBBLE_CORAL = Blocks.register("bubble_coral", new CoralPlantBlock(DEAD_BUBBLE_CORAL, AbstractBlock.Properties.create(Material.OCEAN_PLANT, MaterialColor.PURPLE).doesNotBlockMovement().zeroHardnessAndResistance().sound(SoundType.WET_GRASS)));
    public static final Block FIRE_CORAL = Blocks.register("fire_coral", new CoralPlantBlock(DEAD_FIRE_CORAL, AbstractBlock.Properties.create(Material.OCEAN_PLANT, MaterialColor.RED).doesNotBlockMovement().zeroHardnessAndResistance().sound(SoundType.WET_GRASS)));
    public static final Block HORN_CORAL = Blocks.register("horn_coral", new CoralPlantBlock(DEAD_HORN_CORAL, AbstractBlock.Properties.create(Material.OCEAN_PLANT, MaterialColor.YELLOW).doesNotBlockMovement().zeroHardnessAndResistance().sound(SoundType.WET_GRASS)));
    public static final Block DEAD_TUBE_CORAL_FAN = Blocks.register("dead_tube_coral_fan", new CoralFanBlock(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.GRAY).setRequiresTool().doesNotBlockMovement().zeroHardnessAndResistance()));
    public static final Block DEAD_BRAIN_CORAL_FAN = Blocks.register("dead_brain_coral_fan", new CoralFanBlock(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.GRAY).setRequiresTool().doesNotBlockMovement().zeroHardnessAndResistance()));
    public static final Block DEAD_BUBBLE_CORAL_FAN = Blocks.register("dead_bubble_coral_fan", new CoralFanBlock(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.GRAY).setRequiresTool().doesNotBlockMovement().zeroHardnessAndResistance()));
    public static final Block DEAD_FIRE_CORAL_FAN = Blocks.register("dead_fire_coral_fan", new CoralFanBlock(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.GRAY).setRequiresTool().doesNotBlockMovement().zeroHardnessAndResistance()));
    public static final Block DEAD_HORN_CORAL_FAN = Blocks.register("dead_horn_coral_fan", new CoralFanBlock(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.GRAY).setRequiresTool().doesNotBlockMovement().zeroHardnessAndResistance()));
    public static final Block TUBE_CORAL_FAN = Blocks.register("tube_coral_fan", new CoralFinBlock(DEAD_TUBE_CORAL_FAN, AbstractBlock.Properties.create(Material.OCEAN_PLANT, MaterialColor.BLUE).doesNotBlockMovement().zeroHardnessAndResistance().sound(SoundType.WET_GRASS)));
    public static final Block BRAIN_CORAL_FAN = Blocks.register("brain_coral_fan", new CoralFinBlock(DEAD_BRAIN_CORAL_FAN, AbstractBlock.Properties.create(Material.OCEAN_PLANT, MaterialColor.PINK).doesNotBlockMovement().zeroHardnessAndResistance().sound(SoundType.WET_GRASS)));
    public static final Block BUBBLE_CORAL_FAN = Blocks.register("bubble_coral_fan", new CoralFinBlock(DEAD_BUBBLE_CORAL_FAN, AbstractBlock.Properties.create(Material.OCEAN_PLANT, MaterialColor.PURPLE).doesNotBlockMovement().zeroHardnessAndResistance().sound(SoundType.WET_GRASS)));
    public static final Block FIRE_CORAL_FAN = Blocks.register("fire_coral_fan", new CoralFinBlock(DEAD_FIRE_CORAL_FAN, AbstractBlock.Properties.create(Material.OCEAN_PLANT, MaterialColor.RED).doesNotBlockMovement().zeroHardnessAndResistance().sound(SoundType.WET_GRASS)));
    public static final Block HORN_CORAL_FAN = Blocks.register("horn_coral_fan", new CoralFinBlock(DEAD_HORN_CORAL_FAN, AbstractBlock.Properties.create(Material.OCEAN_PLANT, MaterialColor.YELLOW).doesNotBlockMovement().zeroHardnessAndResistance().sound(SoundType.WET_GRASS)));
    public static final Block DEAD_TUBE_CORAL_WALL_FAN = Blocks.register("dead_tube_coral_wall_fan", new DeadCoralWallFanBlock(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.GRAY).setRequiresTool().doesNotBlockMovement().zeroHardnessAndResistance().lootFrom(DEAD_TUBE_CORAL_FAN)));
    public static final Block DEAD_BRAIN_CORAL_WALL_FAN = Blocks.register("dead_brain_coral_wall_fan", new DeadCoralWallFanBlock(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.GRAY).setRequiresTool().doesNotBlockMovement().zeroHardnessAndResistance().lootFrom(DEAD_BRAIN_CORAL_FAN)));
    public static final Block DEAD_BUBBLE_CORAL_WALL_FAN = Blocks.register("dead_bubble_coral_wall_fan", new DeadCoralWallFanBlock(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.GRAY).setRequiresTool().doesNotBlockMovement().zeroHardnessAndResistance().lootFrom(DEAD_BUBBLE_CORAL_FAN)));
    public static final Block DEAD_FIRE_CORAL_WALL_FAN = Blocks.register("dead_fire_coral_wall_fan", new DeadCoralWallFanBlock(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.GRAY).setRequiresTool().doesNotBlockMovement().zeroHardnessAndResistance().lootFrom(DEAD_FIRE_CORAL_FAN)));
    public static final Block DEAD_HORN_CORAL_WALL_FAN = Blocks.register("dead_horn_coral_wall_fan", new DeadCoralWallFanBlock(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.GRAY).setRequiresTool().doesNotBlockMovement().zeroHardnessAndResistance().lootFrom(DEAD_HORN_CORAL_FAN)));
    public static final Block TUBE_CORAL_WALL_FAN = Blocks.register("tube_coral_wall_fan", new CoralWallFanBlock(DEAD_TUBE_CORAL_WALL_FAN, AbstractBlock.Properties.create(Material.OCEAN_PLANT, MaterialColor.BLUE).doesNotBlockMovement().zeroHardnessAndResistance().sound(SoundType.WET_GRASS).lootFrom(TUBE_CORAL_FAN)));
    public static final Block BRAIN_CORAL_WALL_FAN = Blocks.register("brain_coral_wall_fan", new CoralWallFanBlock(DEAD_BRAIN_CORAL_WALL_FAN, AbstractBlock.Properties.create(Material.OCEAN_PLANT, MaterialColor.PINK).doesNotBlockMovement().zeroHardnessAndResistance().sound(SoundType.WET_GRASS).lootFrom(BRAIN_CORAL_FAN)));
    public static final Block BUBBLE_CORAL_WALL_FAN = Blocks.register("bubble_coral_wall_fan", new CoralWallFanBlock(DEAD_BUBBLE_CORAL_WALL_FAN, AbstractBlock.Properties.create(Material.OCEAN_PLANT, MaterialColor.PURPLE).doesNotBlockMovement().zeroHardnessAndResistance().sound(SoundType.WET_GRASS).lootFrom(BUBBLE_CORAL_FAN)));
    public static final Block FIRE_CORAL_WALL_FAN = Blocks.register("fire_coral_wall_fan", new CoralWallFanBlock(DEAD_FIRE_CORAL_WALL_FAN, AbstractBlock.Properties.create(Material.OCEAN_PLANT, MaterialColor.RED).doesNotBlockMovement().zeroHardnessAndResistance().sound(SoundType.WET_GRASS).lootFrom(FIRE_CORAL_FAN)));
    public static final Block HORN_CORAL_WALL_FAN = Blocks.register("horn_coral_wall_fan", new CoralWallFanBlock(DEAD_HORN_CORAL_WALL_FAN, AbstractBlock.Properties.create(Material.OCEAN_PLANT, MaterialColor.YELLOW).doesNotBlockMovement().zeroHardnessAndResistance().sound(SoundType.WET_GRASS).lootFrom(HORN_CORAL_FAN)));
    public static final Block SEA_PICKLE = Blocks.register("sea_pickle", new SeaPickleBlock(AbstractBlock.Properties.create(Material.OCEAN_PLANT, MaterialColor.GREEN).setLightLevel(Blocks::lambda$static$25).sound(SoundType.SLIME).notSolid()));
    public static final Block BLUE_ICE = Blocks.register("blue_ice", new BreakableBlock(AbstractBlock.Properties.create(Material.PACKED_ICE).hardnessAndResistance(2.8f).slipperiness(0.989f).sound(SoundType.GLASS)));
    public static final Block CONDUIT = Blocks.register("conduit", new ConduitBlock(AbstractBlock.Properties.create(Material.GLASS, MaterialColor.DIAMOND).hardnessAndResistance(3.0f).setLightLevel(Blocks::lambda$static$26).notSolid()));
    public static final Block BAMBOO_SAPLING = Blocks.register("bamboo_sapling", new BambooSaplingBlock(AbstractBlock.Properties.create(Material.BAMBOO_SAPLING).tickRandomly().zeroHardnessAndResistance().doesNotBlockMovement().hardnessAndResistance(1.0f).sound(SoundType.BAMBOO_SAPLING)));
    public static final Block BAMBOO = Blocks.register("bamboo", new BambooBlock(AbstractBlock.Properties.create(Material.BAMBOO, MaterialColor.FOLIAGE).tickRandomly().zeroHardnessAndResistance().hardnessAndResistance(1.0f).sound(SoundType.BAMBOO).notSolid()));
    public static final Block POTTED_BAMBOO = Blocks.register("potted_bamboo", new FlowerPotBlock(BAMBOO, AbstractBlock.Properties.create(Material.MISCELLANEOUS).zeroHardnessAndResistance().notSolid()));
    public static final Block VOID_AIR = Blocks.register("void_air", new AirBlock(AbstractBlock.Properties.create(Material.AIR).doesNotBlockMovement().noDrops().setAir()));
    public static final Block CAVE_AIR = Blocks.register("cave_air", new AirBlock(AbstractBlock.Properties.create(Material.AIR).doesNotBlockMovement().noDrops().setAir()));
    public static final Block BUBBLE_COLUMN = Blocks.register("bubble_column", new BubbleColumnBlock(AbstractBlock.Properties.create(Material.BUBBLE_COLUMN).doesNotBlockMovement().noDrops()));
    public static final Block POLISHED_GRANITE_STAIRS = Blocks.register("polished_granite_stairs", new StairsBlock(POLISHED_GRANITE.getDefaultState(), AbstractBlock.Properties.from(POLISHED_GRANITE)));
    public static final Block SMOOTH_RED_SANDSTONE_STAIRS = Blocks.register("smooth_red_sandstone_stairs", new StairsBlock(SMOOTH_RED_SANDSTONE.getDefaultState(), AbstractBlock.Properties.from(SMOOTH_RED_SANDSTONE)));
    public static final Block MOSSY_STONE_BRICK_STAIRS = Blocks.register("mossy_stone_brick_stairs", new StairsBlock(MOSSY_STONE_BRICKS.getDefaultState(), AbstractBlock.Properties.from(MOSSY_STONE_BRICKS)));
    public static final Block POLISHED_DIORITE_STAIRS = Blocks.register("polished_diorite_stairs", new StairsBlock(POLISHED_DIORITE.getDefaultState(), AbstractBlock.Properties.from(POLISHED_DIORITE)));
    public static final Block MOSSY_COBBLESTONE_STAIRS = Blocks.register("mossy_cobblestone_stairs", new StairsBlock(MOSSY_COBBLESTONE.getDefaultState(), AbstractBlock.Properties.from(MOSSY_COBBLESTONE)));
    public static final Block END_STONE_BRICK_STAIRS = Blocks.register("end_stone_brick_stairs", new StairsBlock(END_STONE_BRICKS.getDefaultState(), AbstractBlock.Properties.from(END_STONE_BRICKS)));
    public static final Block STONE_STAIRS = Blocks.register("stone_stairs", new StairsBlock(STONE.getDefaultState(), AbstractBlock.Properties.from(STONE)));
    public static final Block SMOOTH_SANDSTONE_STAIRS = Blocks.register("smooth_sandstone_stairs", new StairsBlock(SMOOTH_SANDSTONE.getDefaultState(), AbstractBlock.Properties.from(SMOOTH_SANDSTONE)));
    public static final Block SMOOTH_QUARTZ_STAIRS = Blocks.register("smooth_quartz_stairs", new StairsBlock(SMOOTH_QUARTZ.getDefaultState(), AbstractBlock.Properties.from(SMOOTH_QUARTZ)));
    public static final Block GRANITE_STAIRS = Blocks.register("granite_stairs", new StairsBlock(GRANITE.getDefaultState(), AbstractBlock.Properties.from(GRANITE)));
    public static final Block ANDESITE_STAIRS = Blocks.register("andesite_stairs", new StairsBlock(ANDESITE.getDefaultState(), AbstractBlock.Properties.from(ANDESITE)));
    public static final Block RED_NETHER_BRICK_STAIRS = Blocks.register("red_nether_brick_stairs", new StairsBlock(RED_NETHER_BRICKS.getDefaultState(), AbstractBlock.Properties.from(RED_NETHER_BRICKS)));
    public static final Block POLISHED_ANDESITE_STAIRS = Blocks.register("polished_andesite_stairs", new StairsBlock(POLISHED_ANDESITE.getDefaultState(), AbstractBlock.Properties.from(POLISHED_ANDESITE)));
    public static final Block DIORITE_STAIRS = Blocks.register("diorite_stairs", new StairsBlock(DIORITE.getDefaultState(), AbstractBlock.Properties.from(DIORITE)));
    public static final Block POLISHED_GRANITE_SLAB = Blocks.register("polished_granite_slab", new SlabBlock(AbstractBlock.Properties.from(POLISHED_GRANITE)));
    public static final Block SMOOTH_RED_SANDSTONE_SLAB = Blocks.register("smooth_red_sandstone_slab", new SlabBlock(AbstractBlock.Properties.from(SMOOTH_RED_SANDSTONE)));
    public static final Block MOSSY_STONE_BRICK_SLAB = Blocks.register("mossy_stone_brick_slab", new SlabBlock(AbstractBlock.Properties.from(MOSSY_STONE_BRICKS)));
    public static final Block POLISHED_DIORITE_SLAB = Blocks.register("polished_diorite_slab", new SlabBlock(AbstractBlock.Properties.from(POLISHED_DIORITE)));
    public static final Block MOSSY_COBBLESTONE_SLAB = Blocks.register("mossy_cobblestone_slab", new SlabBlock(AbstractBlock.Properties.from(MOSSY_COBBLESTONE)));
    public static final Block END_STONE_BRICK_SLAB = Blocks.register("end_stone_brick_slab", new SlabBlock(AbstractBlock.Properties.from(END_STONE_BRICKS)));
    public static final Block SMOOTH_SANDSTONE_SLAB = Blocks.register("smooth_sandstone_slab", new SlabBlock(AbstractBlock.Properties.from(SMOOTH_SANDSTONE)));
    public static final Block SMOOTH_QUARTZ_SLAB = Blocks.register("smooth_quartz_slab", new SlabBlock(AbstractBlock.Properties.from(SMOOTH_QUARTZ)));
    public static final Block GRANITE_SLAB = Blocks.register("granite_slab", new SlabBlock(AbstractBlock.Properties.from(GRANITE)));
    public static final Block ANDESITE_SLAB = Blocks.register("andesite_slab", new SlabBlock(AbstractBlock.Properties.from(ANDESITE)));
    public static final Block RED_NETHER_BRICK_SLAB = Blocks.register("red_nether_brick_slab", new SlabBlock(AbstractBlock.Properties.from(RED_NETHER_BRICKS)));
    public static final Block POLISHED_ANDESITE_SLAB = Blocks.register("polished_andesite_slab", new SlabBlock(AbstractBlock.Properties.from(POLISHED_ANDESITE)));
    public static final Block DIORITE_SLAB = Blocks.register("diorite_slab", new SlabBlock(AbstractBlock.Properties.from(DIORITE)));
    public static final Block BRICK_WALL = Blocks.register("brick_wall", new WallBlock(AbstractBlock.Properties.from(BRICKS)));
    public static final Block PRISMARINE_WALL = Blocks.register("prismarine_wall", new WallBlock(AbstractBlock.Properties.from(PRISMARINE)));
    public static final Block RED_SANDSTONE_WALL = Blocks.register("red_sandstone_wall", new WallBlock(AbstractBlock.Properties.from(RED_SANDSTONE)));
    public static final Block MOSSY_STONE_BRICK_WALL = Blocks.register("mossy_stone_brick_wall", new WallBlock(AbstractBlock.Properties.from(MOSSY_STONE_BRICKS)));
    public static final Block GRANITE_WALL = Blocks.register("granite_wall", new WallBlock(AbstractBlock.Properties.from(GRANITE)));
    public static final Block STONE_BRICK_WALL = Blocks.register("stone_brick_wall", new WallBlock(AbstractBlock.Properties.from(STONE_BRICKS)));
    public static final Block NETHER_BRICK_WALL = Blocks.register("nether_brick_wall", new WallBlock(AbstractBlock.Properties.from(NETHER_BRICKS)));
    public static final Block ANDESITE_WALL = Blocks.register("andesite_wall", new WallBlock(AbstractBlock.Properties.from(ANDESITE)));
    public static final Block RED_NETHER_BRICK_WALL = Blocks.register("red_nether_brick_wall", new WallBlock(AbstractBlock.Properties.from(RED_NETHER_BRICKS)));
    public static final Block SANDSTONE_WALL = Blocks.register("sandstone_wall", new WallBlock(AbstractBlock.Properties.from(SANDSTONE)));
    public static final Block END_STONE_BRICK_WALL = Blocks.register("end_stone_brick_wall", new WallBlock(AbstractBlock.Properties.from(END_STONE_BRICKS)));
    public static final Block DIORITE_WALL = Blocks.register("diorite_wall", new WallBlock(AbstractBlock.Properties.from(DIORITE)));
    public static final Block SCAFFOLDING = Blocks.register("scaffolding", new ScaffoldingBlock(AbstractBlock.Properties.create(Material.MISCELLANEOUS, MaterialColor.SAND).doesNotBlockMovement().sound(SoundType.SCAFFOLDING).variableOpacity()));
    public static final Block LOOM = Blocks.register("loom", new LoomBlock(AbstractBlock.Properties.create(Material.WOOD).hardnessAndResistance(2.5f).sound(SoundType.WOOD)));
    public static final Block BARREL = Blocks.register("barrel", new BarrelBlock(AbstractBlock.Properties.create(Material.WOOD).hardnessAndResistance(2.5f).sound(SoundType.WOOD)));
    public static final Block SMOKER = Blocks.register("smoker", new SmokerBlock(AbstractBlock.Properties.create(Material.ROCK).setRequiresTool().hardnessAndResistance(3.5f).setLightLevel(Blocks.getLightValueLit(13))));
    public static final Block BLAST_FURNACE = Blocks.register("blast_furnace", new BlastFurnaceBlock(AbstractBlock.Properties.create(Material.ROCK).setRequiresTool().hardnessAndResistance(3.5f).setLightLevel(Blocks.getLightValueLit(13))));
    public static final Block CARTOGRAPHY_TABLE = Blocks.register("cartography_table", new CartographyTableBlock(AbstractBlock.Properties.create(Material.WOOD).hardnessAndResistance(2.5f).sound(SoundType.WOOD)));
    public static final Block FLETCHING_TABLE = Blocks.register("fletching_table", new FletchingTableBlock(AbstractBlock.Properties.create(Material.WOOD).hardnessAndResistance(2.5f).sound(SoundType.WOOD)));
    public static final Block GRINDSTONE = Blocks.register("grindstone", new GrindstoneBlock(AbstractBlock.Properties.create(Material.ANVIL, MaterialColor.IRON).setRequiresTool().hardnessAndResistance(2.0f, 6.0f).sound(SoundType.STONE)));
    public static final Block LECTERN = Blocks.register("lectern", new LecternBlock(AbstractBlock.Properties.create(Material.WOOD).hardnessAndResistance(2.5f).sound(SoundType.WOOD)));
    public static final Block SMITHING_TABLE = Blocks.register("smithing_table", new SmithingTableBlock(AbstractBlock.Properties.create(Material.WOOD).hardnessAndResistance(2.5f).sound(SoundType.WOOD)));
    public static final Block STONECUTTER = Blocks.register("stonecutter", new StonecutterBlock(AbstractBlock.Properties.create(Material.ROCK).setRequiresTool().hardnessAndResistance(3.5f)));
    public static final Block BELL = Blocks.register("bell", new BellBlock(AbstractBlock.Properties.create(Material.IRON, MaterialColor.GOLD).setRequiresTool().hardnessAndResistance(5.0f).sound(SoundType.ANVIL)));
    public static final Block LANTERN = Blocks.register("lantern", new LanternBlock(AbstractBlock.Properties.create(Material.IRON).setRequiresTool().hardnessAndResistance(3.5f).sound(SoundType.LANTERN).setLightLevel(Blocks::lambda$static$27).notSolid()));
    public static final Block SOUL_LANTERN = Blocks.register("soul_lantern", new LanternBlock(AbstractBlock.Properties.create(Material.IRON).setRequiresTool().hardnessAndResistance(3.5f).sound(SoundType.LANTERN).setLightLevel(Blocks::lambda$static$28).notSolid()));
    public static final Block CAMPFIRE = Blocks.register("campfire", new CampfireBlock(true, 1, AbstractBlock.Properties.create(Material.WOOD, MaterialColor.OBSIDIAN).hardnessAndResistance(2.0f).sound(SoundType.WOOD).setLightLevel(Blocks.getLightValueLit(15)).notSolid()));
    public static final Block SOUL_CAMPFIRE = Blocks.register("soul_campfire", new CampfireBlock(false, 2, AbstractBlock.Properties.create(Material.WOOD, MaterialColor.OBSIDIAN).hardnessAndResistance(2.0f).sound(SoundType.WOOD).setLightLevel(Blocks.getLightValueLit(10)).notSolid()));
    public static final Block SWEET_BERRY_BUSH = Blocks.register("sweet_berry_bush", new SweetBerryBushBlock(AbstractBlock.Properties.create(Material.PLANTS).tickRandomly().doesNotBlockMovement().sound(SoundType.SWEET_BERRY_BUSH)));
    public static final Block WARPED_STEM = Blocks.register("warped_stem", Blocks.createRotatableNetherBlock(MaterialColor.WARPED_STEM));
    public static final Block STRIPPED_WARPED_STEM = Blocks.register("stripped_warped_stem", Blocks.createRotatableNetherBlock(MaterialColor.WARPED_STEM));
    public static final Block WARPED_HYPHAE = Blocks.register("warped_hyphae", new RotatedPillarBlock(AbstractBlock.Properties.create(Material.NETHER_WOOD, MaterialColor.WARPED_HYPHAE).hardnessAndResistance(2.0f).sound(SoundType.HYPHAE)));
    public static final Block STRIPPED_WARPED_HYPHAE = Blocks.register("stripped_warped_hyphae", new RotatedPillarBlock(AbstractBlock.Properties.create(Material.NETHER_WOOD, MaterialColor.WARPED_HYPHAE).hardnessAndResistance(2.0f).sound(SoundType.HYPHAE)));
    public static final Block WARPED_NYLIUM = Blocks.register("warped_nylium", new NyliumBlock(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.WARPED_NYLIUM).setRequiresTool().hardnessAndResistance(0.4f).sound(SoundType.NYLIUM).tickRandomly()));
    public static final Block WARPED_FUNGUS = Blocks.register("warped_fungus", new FungusBlock(AbstractBlock.Properties.create(Material.PLANTS, MaterialColor.CYAN).zeroHardnessAndResistance().doesNotBlockMovement().sound(SoundType.FUNGUS), Blocks::lambda$static$29));
    public static final Block WARPED_WART_BLOCK = Blocks.register("warped_wart_block", new Block(AbstractBlock.Properties.create(Material.ORGANIC, MaterialColor.WARPED_WART).hardnessAndResistance(1.0f).sound(SoundType.WART)));
    public static final Block WARPED_ROOTS = Blocks.register("warped_roots", new NetherRootsBlock(AbstractBlock.Properties.create(Material.NETHER_PLANTS, MaterialColor.CYAN).doesNotBlockMovement().zeroHardnessAndResistance().sound(SoundType.ROOT)));
    public static final Block NETHER_SPROUTS = Blocks.register("nether_sprouts", new NetherSproutsBlock(AbstractBlock.Properties.create(Material.NETHER_PLANTS, MaterialColor.CYAN).doesNotBlockMovement().zeroHardnessAndResistance().sound(SoundType.NETHER_SPROUT)));
    public static final Block CRIMSON_STEM = Blocks.register("crimson_stem", Blocks.createRotatableNetherBlock(MaterialColor.CRIMSON_STEM));
    public static final Block STRIPPED_CRIMSON_STEM = Blocks.register("stripped_crimson_stem", Blocks.createRotatableNetherBlock(MaterialColor.CRIMSON_STEM));
    public static final Block CRIMSON_HYPHAE = Blocks.register("crimson_hyphae", new RotatedPillarBlock(AbstractBlock.Properties.create(Material.NETHER_WOOD, MaterialColor.CRIMSON_HYPHAE).hardnessAndResistance(2.0f).sound(SoundType.HYPHAE)));
    public static final Block STRIPPED_CRIMSON_HYPHAE = Blocks.register("stripped_crimson_hyphae", new RotatedPillarBlock(AbstractBlock.Properties.create(Material.NETHER_WOOD, MaterialColor.CRIMSON_HYPHAE).hardnessAndResistance(2.0f).sound(SoundType.HYPHAE)));
    public static final Block CRIMSON_NYLIUM = Blocks.register("crimson_nylium", new NyliumBlock(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.CRIMSON_NYLIUM).setRequiresTool().hardnessAndResistance(0.4f).sound(SoundType.NYLIUM).tickRandomly()));
    public static final Block CRIMSON_FUNGUS = Blocks.register("crimson_fungus", new FungusBlock(AbstractBlock.Properties.create(Material.PLANTS, MaterialColor.NETHERRACK).zeroHardnessAndResistance().doesNotBlockMovement().sound(SoundType.FUNGUS), Blocks::lambda$static$30));
    public static final Block SHROOMLIGHT = Blocks.register("shroomlight", new Block(AbstractBlock.Properties.create(Material.ORGANIC, MaterialColor.RED).hardnessAndResistance(1.0f).sound(SoundType.SHROOMLIGHT).setLightLevel(Blocks::lambda$static$31)));
    public static final Block WEEPING_VINES = Blocks.register("weeping_vines", new WeepingVinesTopBlock(AbstractBlock.Properties.create(Material.PLANTS, MaterialColor.NETHERRACK).tickRandomly().doesNotBlockMovement().zeroHardnessAndResistance().sound(SoundType.NETHER_VINE)));
    public static final Block WEEPING_VINES_PLANT = Blocks.register("weeping_vines_plant", new WeepingVinesBlock(AbstractBlock.Properties.create(Material.PLANTS, MaterialColor.NETHERRACK).doesNotBlockMovement().zeroHardnessAndResistance().sound(SoundType.NETHER_VINE)));
    public static final Block TWISTING_VINES = Blocks.register("twisting_vines", new TwistingVinesTopBlock(AbstractBlock.Properties.create(Material.PLANTS, MaterialColor.CYAN).tickRandomly().doesNotBlockMovement().zeroHardnessAndResistance().sound(SoundType.NETHER_VINE)));
    public static final Block TWISTING_VINES_PLANT = Blocks.register("twisting_vines_plant", new TwistingVinesBlock(AbstractBlock.Properties.create(Material.PLANTS, MaterialColor.CYAN).doesNotBlockMovement().zeroHardnessAndResistance().sound(SoundType.NETHER_VINE)));
    public static final Block CRIMSON_ROOTS = Blocks.register("crimson_roots", new NetherRootsBlock(AbstractBlock.Properties.create(Material.NETHER_PLANTS, MaterialColor.NETHERRACK).doesNotBlockMovement().zeroHardnessAndResistance().sound(SoundType.ROOT)));
    public static final Block CRIMSON_PLANKS = Blocks.register("crimson_planks", new Block(AbstractBlock.Properties.create(Material.NETHER_WOOD, MaterialColor.CRIMSON_STEM).hardnessAndResistance(2.0f, 3.0f).sound(SoundType.WOOD)));
    public static final Block WARPED_PLANKS = Blocks.register("warped_planks", new Block(AbstractBlock.Properties.create(Material.NETHER_WOOD, MaterialColor.WARPED_STEM).hardnessAndResistance(2.0f, 3.0f).sound(SoundType.WOOD)));
    public static final Block CRIMSON_SLAB = Blocks.register("crimson_slab", new SlabBlock(AbstractBlock.Properties.create(Material.NETHER_WOOD, CRIMSON_PLANKS.getMaterialColor()).hardnessAndResistance(2.0f, 3.0f).sound(SoundType.WOOD)));
    public static final Block WARPED_SLAB = Blocks.register("warped_slab", new SlabBlock(AbstractBlock.Properties.create(Material.NETHER_WOOD, WARPED_PLANKS.getMaterialColor()).hardnessAndResistance(2.0f, 3.0f).sound(SoundType.WOOD)));
    public static final Block CRIMSON_PRESSURE_PLATE = Blocks.register("crimson_pressure_plate", new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, AbstractBlock.Properties.create(Material.NETHER_WOOD, CRIMSON_PLANKS.getMaterialColor()).doesNotBlockMovement().hardnessAndResistance(0.5f).sound(SoundType.WOOD)));
    public static final Block WARPED_PRESSURE_PLATE = Blocks.register("warped_pressure_plate", new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, AbstractBlock.Properties.create(Material.NETHER_WOOD, WARPED_PLANKS.getMaterialColor()).doesNotBlockMovement().hardnessAndResistance(0.5f).sound(SoundType.WOOD)));
    public static final Block CRIMSON_FENCE = Blocks.register("crimson_fence", new FenceBlock(AbstractBlock.Properties.create(Material.NETHER_WOOD, CRIMSON_PLANKS.getMaterialColor()).hardnessAndResistance(2.0f, 3.0f).sound(SoundType.WOOD)));
    public static final Block WARPED_FENCE = Blocks.register("warped_fence", new FenceBlock(AbstractBlock.Properties.create(Material.NETHER_WOOD, WARPED_PLANKS.getMaterialColor()).hardnessAndResistance(2.0f, 3.0f).sound(SoundType.WOOD)));
    public static final Block CRIMSON_TRAPDOOR = Blocks.register("crimson_trapdoor", new TrapDoorBlock(AbstractBlock.Properties.create(Material.NETHER_WOOD, CRIMSON_PLANKS.getMaterialColor()).hardnessAndResistance(3.0f).sound(SoundType.WOOD).notSolid().setAllowsSpawn(Blocks::neverAllowSpawn)));
    public static final Block WARPED_TRAPDOOR = Blocks.register("warped_trapdoor", new TrapDoorBlock(AbstractBlock.Properties.create(Material.NETHER_WOOD, WARPED_PLANKS.getMaterialColor()).hardnessAndResistance(3.0f).sound(SoundType.WOOD).notSolid().setAllowsSpawn(Blocks::neverAllowSpawn)));
    public static final Block CRIMSON_FENCE_GATE = Blocks.register("crimson_fence_gate", new FenceGateBlock(AbstractBlock.Properties.create(Material.NETHER_WOOD, CRIMSON_PLANKS.getMaterialColor()).hardnessAndResistance(2.0f, 3.0f).sound(SoundType.WOOD)));
    public static final Block WARPED_FENCE_GATE = Blocks.register("warped_fence_gate", new FenceGateBlock(AbstractBlock.Properties.create(Material.NETHER_WOOD, WARPED_PLANKS.getMaterialColor()).hardnessAndResistance(2.0f, 3.0f).sound(SoundType.WOOD)));
    public static final Block CRIMSON_STAIRS = Blocks.register("crimson_stairs", new StairsBlock(CRIMSON_PLANKS.getDefaultState(), AbstractBlock.Properties.from(CRIMSON_PLANKS)));
    public static final Block WARPED_STAIRS = Blocks.register("warped_stairs", new StairsBlock(WARPED_PLANKS.getDefaultState(), AbstractBlock.Properties.from(WARPED_PLANKS)));
    public static final Block CRIMSON_BUTTON = Blocks.register("crimson_button", new WoodButtonBlock(AbstractBlock.Properties.create(Material.MISCELLANEOUS).doesNotBlockMovement().hardnessAndResistance(0.5f).sound(SoundType.WOOD)));
    public static final Block WARPED_BUTTON = Blocks.register("warped_button", new WoodButtonBlock(AbstractBlock.Properties.create(Material.MISCELLANEOUS).doesNotBlockMovement().hardnessAndResistance(0.5f).sound(SoundType.WOOD)));
    public static final Block CRIMSON_DOOR = Blocks.register("crimson_door", new DoorBlock(AbstractBlock.Properties.create(Material.NETHER_WOOD, CRIMSON_PLANKS.getMaterialColor()).hardnessAndResistance(3.0f).sound(SoundType.WOOD).notSolid()));
    public static final Block WARPED_DOOR = Blocks.register("warped_door", new DoorBlock(AbstractBlock.Properties.create(Material.NETHER_WOOD, WARPED_PLANKS.getMaterialColor()).hardnessAndResistance(3.0f).sound(SoundType.WOOD).notSolid()));
    public static final Block CRIMSON_SIGN = Blocks.register("crimson_sign", new StandingSignBlock(AbstractBlock.Properties.create(Material.NETHER_WOOD, CRIMSON_PLANKS.getMaterialColor()).doesNotBlockMovement().hardnessAndResistance(1.0f).sound(SoundType.WOOD), WoodType.CRIMSON));
    public static final Block WARPED_SIGN = Blocks.register("warped_sign", new StandingSignBlock(AbstractBlock.Properties.create(Material.NETHER_WOOD, WARPED_PLANKS.getMaterialColor()).doesNotBlockMovement().hardnessAndResistance(1.0f).sound(SoundType.WOOD), WoodType.WARPED));
    public static final Block CRIMSON_WALL_SIGN = Blocks.register("crimson_wall_sign", new WallSignBlock(AbstractBlock.Properties.create(Material.NETHER_WOOD, CRIMSON_PLANKS.getMaterialColor()).doesNotBlockMovement().hardnessAndResistance(1.0f).sound(SoundType.WOOD).lootFrom(CRIMSON_SIGN), WoodType.CRIMSON));
    public static final Block WARPED_WALL_SIGN = Blocks.register("warped_wall_sign", new WallSignBlock(AbstractBlock.Properties.create(Material.NETHER_WOOD, WARPED_PLANKS.getMaterialColor()).doesNotBlockMovement().hardnessAndResistance(1.0f).sound(SoundType.WOOD).lootFrom(WARPED_SIGN), WoodType.WARPED));
    public static final Block STRUCTURE_BLOCK = Blocks.register("structure_block", new StructureBlock(AbstractBlock.Properties.create(Material.IRON, MaterialColor.LIGHT_GRAY).setRequiresTool().hardnessAndResistance(-1.0f, 3600000.0f).noDrops()));
    public static final Block JIGSAW = Blocks.register("jigsaw", new JigsawBlock(AbstractBlock.Properties.create(Material.IRON, MaterialColor.LIGHT_GRAY).setRequiresTool().hardnessAndResistance(-1.0f, 3600000.0f).noDrops()));
    public static final Block COMPOSTER = Blocks.register("composter", new ComposterBlock(AbstractBlock.Properties.create(Material.WOOD).hardnessAndResistance(0.6f).sound(SoundType.WOOD)));
    public static final Block TARGET = Blocks.register("target", new TargetBlock(AbstractBlock.Properties.create(Material.ORGANIC, MaterialColor.QUARTZ).hardnessAndResistance(0.5f).sound(SoundType.PLANT)));
    public static final Block BEE_NEST = Blocks.register("bee_nest", new BeehiveBlock(AbstractBlock.Properties.create(Material.WOOD, MaterialColor.YELLOW).hardnessAndResistance(0.3f).sound(SoundType.WOOD)));
    public static final Block BEEHIVE = Blocks.register("beehive", new BeehiveBlock(AbstractBlock.Properties.create(Material.WOOD).hardnessAndResistance(0.6f).sound(SoundType.WOOD)));
    public static final Block HONEY_BLOCK = Blocks.register("honey_block", new HoneyBlock(AbstractBlock.Properties.create(Material.CLAY, MaterialColor.ADOBE).speedFactor(0.4f).jumpFactor(0.5f).notSolid().sound(SoundType.HONEY)));
    public static final Block HONEYCOMB_BLOCK = Blocks.register("honeycomb_block", new Block(AbstractBlock.Properties.create(Material.CLAY, MaterialColor.ADOBE).hardnessAndResistance(0.6f).sound(SoundType.CORAL)));
    public static final Block NETHERITE_BLOCK = Blocks.register("netherite_block", new Block(AbstractBlock.Properties.create(Material.IRON, MaterialColor.BLACK).setRequiresTool().hardnessAndResistance(50.0f, 1200.0f).sound(SoundType.NETHERITE)));
    public static final Block ANCIENT_DEBRIS = Blocks.register("ancient_debris", new Block(AbstractBlock.Properties.create(Material.IRON, MaterialColor.BLACK).setRequiresTool().hardnessAndResistance(30.0f, 1200.0f).sound(SoundType.ANCIENT_DEBRIS)));
    public static final Block CRYING_OBSIDIAN = Blocks.register("crying_obsidian", new CryingObsidianBlock(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.BLACK).setRequiresTool().hardnessAndResistance(50.0f, 1200.0f).setLightLevel(Blocks::lambda$static$32)));
    public static final Block RESPAWN_ANCHOR = Blocks.register("respawn_anchor", new RespawnAnchorBlock(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.BLACK).setRequiresTool().hardnessAndResistance(50.0f, 1200.0f).setLightLevel(Blocks::lambda$static$33)));
    public static final Block POTTED_CRIMSON_FUNGUS = Blocks.register("potted_crimson_fungus", new FlowerPotBlock(CRIMSON_FUNGUS, AbstractBlock.Properties.create(Material.MISCELLANEOUS).zeroHardnessAndResistance().notSolid()));
    public static final Block POTTED_WARPED_FUNGUS = Blocks.register("potted_warped_fungus", new FlowerPotBlock(WARPED_FUNGUS, AbstractBlock.Properties.create(Material.MISCELLANEOUS).zeroHardnessAndResistance().notSolid()));
    public static final Block POTTED_CRIMSON_ROOTS = Blocks.register("potted_crimson_roots", new FlowerPotBlock(CRIMSON_ROOTS, AbstractBlock.Properties.create(Material.MISCELLANEOUS).zeroHardnessAndResistance().notSolid()));
    public static final Block POTTED_WARPED_ROOTS = Blocks.register("potted_warped_roots", new FlowerPotBlock(WARPED_ROOTS, AbstractBlock.Properties.create(Material.MISCELLANEOUS).zeroHardnessAndResistance().notSolid()));
    public static final Block LODESTONE = Blocks.register("lodestone", new Block(AbstractBlock.Properties.create(Material.ANVIL).setRequiresTool().hardnessAndResistance(3.5f).sound(SoundType.LODESTONE)));
    public static final Block BLACKSTONE = Blocks.register("blackstone", new Block(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.BLACK).setRequiresTool().hardnessAndResistance(1.5f, 6.0f)));
    public static final Block BLACKSTONE_STAIRS = Blocks.register("blackstone_stairs", new StairsBlock(BLACKSTONE.getDefaultState(), AbstractBlock.Properties.from(BLACKSTONE)));
    public static final Block BLACKSTONE_WALL = Blocks.register("blackstone_wall", new WallBlock(AbstractBlock.Properties.from(BLACKSTONE)));
    public static final Block BLACKSTONE_SLAB = Blocks.register("blackstone_slab", new SlabBlock(AbstractBlock.Properties.from(BLACKSTONE).hardnessAndResistance(2.0f, 6.0f)));
    public static final Block POLISHED_BLACKSTONE = Blocks.register("polished_blackstone", new Block(AbstractBlock.Properties.from(BLACKSTONE).hardnessAndResistance(2.0f, 6.0f)));
    public static final Block POLISHED_BLACKSTONE_BRICKS = Blocks.register("polished_blackstone_bricks", new Block(AbstractBlock.Properties.from(POLISHED_BLACKSTONE).hardnessAndResistance(1.5f, 6.0f)));
    public static final Block CRACKED_POLISHED_BLACKSTONE_BRICKS = Blocks.register("cracked_polished_blackstone_bricks", new Block(AbstractBlock.Properties.from(POLISHED_BLACKSTONE_BRICKS)));
    public static final Block CHISELED_POLISHED_BLACKSTONE = Blocks.register("chiseled_polished_blackstone", new Block(AbstractBlock.Properties.from(POLISHED_BLACKSTONE).hardnessAndResistance(1.5f, 6.0f)));
    public static final Block POLISHED_BLACKSTONE_BRICK_SLAB = Blocks.register("polished_blackstone_brick_slab", new SlabBlock(AbstractBlock.Properties.from(POLISHED_BLACKSTONE_BRICKS).hardnessAndResistance(2.0f, 6.0f)));
    public static final Block POLISHED_BLACKSTONE_BRICK_STAIRS = Blocks.register("polished_blackstone_brick_stairs", new StairsBlock(POLISHED_BLACKSTONE_BRICKS.getDefaultState(), AbstractBlock.Properties.from(POLISHED_BLACKSTONE_BRICKS)));
    public static final Block POLISHED_BLACKSTONE_BRICK_WALL = Blocks.register("polished_blackstone_brick_wall", new WallBlock(AbstractBlock.Properties.from(POLISHED_BLACKSTONE_BRICKS)));
    public static final Block GILDED_BLACKSTONE = Blocks.register("gilded_blackstone", new Block(AbstractBlock.Properties.from(BLACKSTONE).sound(SoundType.GILDED_BLACKSTONE)));
    public static final Block POLISHED_BLACKSTONE_STAIRS = Blocks.register("polished_blackstone_stairs", new StairsBlock(POLISHED_BLACKSTONE.getDefaultState(), AbstractBlock.Properties.from(POLISHED_BLACKSTONE)));
    public static final Block POLISHED_BLACKSTONE_SLAB = Blocks.register("polished_blackstone_slab", new SlabBlock(AbstractBlock.Properties.from(POLISHED_BLACKSTONE)));
    public static final Block POLISHED_BLACKSTONE_PRESSURE_PLATE = Blocks.register("polished_blackstone_pressure_plate", new PressurePlateBlock(PressurePlateBlock.Sensitivity.MOBS, AbstractBlock.Properties.create(Material.ROCK, MaterialColor.BLACK).setRequiresTool().doesNotBlockMovement().hardnessAndResistance(0.5f)));
    public static final Block POLISHED_BLACKSTONE_BUTTON = Blocks.register("polished_blackstone_button", new StoneButtonBlock(AbstractBlock.Properties.create(Material.MISCELLANEOUS).doesNotBlockMovement().hardnessAndResistance(0.5f)));
    public static final Block POLISHED_BLACKSTONE_WALL = Blocks.register("polished_blackstone_wall", new WallBlock(AbstractBlock.Properties.from(POLISHED_BLACKSTONE)));
    public static final Block CHISELED_NETHER_BRICKS = Blocks.register("chiseled_nether_bricks", new Block(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.NETHERRACK).setRequiresTool().hardnessAndResistance(2.0f, 6.0f).sound(SoundType.NETHER_BRICK)));
    public static final Block CRACKED_NETHER_BRICKS = Blocks.register("cracked_nether_bricks", new Block(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.NETHERRACK).setRequiresTool().hardnessAndResistance(2.0f, 6.0f).sound(SoundType.NETHER_BRICK)));
    public static final Block QUARTZ_BRICKS = Blocks.register("quartz_bricks", new Block(AbstractBlock.Properties.from(QUARTZ_BLOCK)));

    private static ToIntFunction<BlockState> getLightValueLit(int n) {
        return arg_0 -> Blocks.lambda$getLightValueLit$34(n, arg_0);
    }

    private static Boolean neverAllowSpawn(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, EntityType<?> entityType) {
        return false;
    }

    private static Boolean alwaysAllowSpawn(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, EntityType<?> entityType) {
        return true;
    }

    private static Boolean allowsSpawnOnLeaves(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, EntityType<?> entityType) {
        return entityType == EntityType.OCELOT || entityType == EntityType.PARROT;
    }

    private static BedBlock createBedFromColor(DyeColor dyeColor) {
        return new BedBlock(dyeColor, AbstractBlock.Properties.create(Material.WOOL, arg_0 -> Blocks.lambda$createBedFromColor$35(dyeColor, arg_0)).sound(SoundType.WOOD).hardnessAndResistance(0.2f).notSolid());
    }

    private static RotatedPillarBlock createLogBlock(MaterialColor materialColor, MaterialColor materialColor2) {
        return new RotatedPillarBlock(AbstractBlock.Properties.create(Material.WOOD, arg_0 -> Blocks.lambda$createLogBlock$36(materialColor, materialColor2, arg_0)).hardnessAndResistance(2.0f).sound(SoundType.WOOD));
    }

    private static Block createRotatableNetherBlock(MaterialColor materialColor) {
        return new RotatedPillarBlock(AbstractBlock.Properties.create(Material.NETHER_WOOD, arg_0 -> Blocks.lambda$createRotatableNetherBlock$37(materialColor, arg_0)).hardnessAndResistance(2.0f).sound(SoundType.HYPHAE));
    }

    private static boolean needsPostProcessing(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos) {
        return false;
    }

    private static boolean isntSolid(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos) {
        return true;
    }

    private static StainedGlassBlock createStainedGlassFromColor(DyeColor dyeColor) {
        return new StainedGlassBlock(dyeColor, AbstractBlock.Properties.create(Material.GLASS, dyeColor).hardnessAndResistance(0.3f).sound(SoundType.GLASS).notSolid().setAllowsSpawn(Blocks::neverAllowSpawn).setOpaque(Blocks::isntSolid).setSuffocates(Blocks::isntSolid).setBlocksVision(Blocks::isntSolid));
    }

    private static LeavesBlock createLeavesBlock() {
        return new LeavesBlock(AbstractBlock.Properties.create(Material.LEAVES).hardnessAndResistance(0.2f).tickRandomly().sound(SoundType.PLANT).notSolid().setAllowsSpawn(Blocks::allowsSpawnOnLeaves).setSuffocates(Blocks::isntSolid).setBlocksVision(Blocks::isntSolid));
    }

    private static ShulkerBoxBlock createShulkerBoxFromColorAndProperties(DyeColor dyeColor, AbstractBlock.Properties properties) {
        AbstractBlock.IPositionPredicate iPositionPredicate = Blocks::lambda$createShulkerBoxFromColorAndProperties$38;
        return new ShulkerBoxBlock(dyeColor, properties.hardnessAndResistance(2.0f).variableOpacity().notSolid().setSuffocates(iPositionPredicate).setBlocksVision(iPositionPredicate));
    }

    private static PistonBlock createPiston(boolean bl) {
        AbstractBlock.IPositionPredicate iPositionPredicate = Blocks::lambda$createPiston$39;
        return new PistonBlock(bl, AbstractBlock.Properties.create(Material.PISTON).hardnessAndResistance(1.5f).setOpaque(Blocks::isntSolid).setSuffocates(iPositionPredicate).setBlocksVision(iPositionPredicate));
    }

    private static Block register(String string, Block block) {
        return Registry.register(Registry.BLOCK, string, block);
    }

    public static void cacheBlockStates() {
        Block.BLOCK_STATE_IDS.forEach(AbstractBlock.AbstractBlockState::cacheState);
    }

    private static boolean lambda$createPiston$39(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos) {
        return blockState.get(PistonBlock.EXTENDED) == false;
    }

    private static boolean lambda$createShulkerBoxFromColorAndProperties$38(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos) {
        TileEntity tileEntity = iBlockReader.getTileEntity(blockPos);
        if (!(tileEntity instanceof ShulkerBoxTileEntity)) {
            return false;
        }
        ShulkerBoxTileEntity shulkerBoxTileEntity = (ShulkerBoxTileEntity)tileEntity;
        return shulkerBoxTileEntity.func_235676_l_();
    }

    private static MaterialColor lambda$createRotatableNetherBlock$37(MaterialColor materialColor, BlockState blockState) {
        return materialColor;
    }

    private static MaterialColor lambda$createLogBlock$36(MaterialColor materialColor, MaterialColor materialColor2, BlockState blockState) {
        return blockState.get(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? materialColor : materialColor2;
    }

    private static MaterialColor lambda$createBedFromColor$35(DyeColor dyeColor, BlockState blockState) {
        return blockState.get(BedBlock.PART) == BedPart.FOOT ? dyeColor.getMapColor() : MaterialColor.WOOL;
    }

    private static int lambda$getLightValueLit$34(int n, BlockState blockState) {
        return blockState.get(BlockStateProperties.LIT) != false ? n : 0;
    }

    private static int lambda$static$33(BlockState blockState) {
        return RespawnAnchorBlock.getChargeScale(blockState, 15);
    }

    private static int lambda$static$32(BlockState blockState) {
        return 1;
    }

    private static int lambda$static$31(BlockState blockState) {
        return 0;
    }

    private static ConfiguredFeature lambda$static$30() {
        return Features.CRIMSON_FUNGI_PLANTED;
    }

    private static ConfiguredFeature lambda$static$29() {
        return Features.WARPED_FUNGI_PLANTED;
    }

    private static int lambda$static$28(BlockState blockState) {
        return 1;
    }

    private static int lambda$static$27(BlockState blockState) {
        return 0;
    }

    private static int lambda$static$26(BlockState blockState) {
        return 0;
    }

    private static int lambda$static$25(BlockState blockState) {
        return SeaPickleBlock.isInBadEnvironment(blockState) ? 0 : 3 + 3 * blockState.get(SeaPickleBlock.PICKLES);
    }

    private static boolean lambda$static$24(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, EntityType entityType) {
        return entityType.isImmuneToFire();
    }

    private static int lambda$static$23(BlockState blockState) {
        return 0;
    }

    private static boolean lambda$static$22(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, EntityType entityType) {
        return entityType == EntityType.POLAR_BEAR;
    }

    private static int lambda$static$21(BlockState blockState) {
        return 0;
    }

    private static int lambda$static$20(BlockState blockState) {
        return 1;
    }

    private static int lambda$static$19(BlockState blockState) {
        return 0;
    }

    private static int lambda$static$18(BlockState blockState) {
        return 0;
    }

    private static int lambda$static$17(BlockState blockState) {
        return 0;
    }

    private static int lambda$static$16(BlockState blockState) {
        return 0;
    }

    private static int lambda$static$15(BlockState blockState) {
        return 0;
    }

    private static int lambda$static$14(BlockState blockState) {
        return 0;
    }

    private static int lambda$static$13(BlockState blockState) {
        return 0;
    }

    private static int lambda$static$12(BlockState blockState) {
        return 0;
    }

    private static int lambda$static$11(BlockState blockState) {
        return 0;
    }

    private static int lambda$static$10(BlockState blockState) {
        return 0;
    }

    private static int lambda$static$9(BlockState blockState) {
        return 1;
    }

    private static int lambda$static$8(BlockState blockState) {
        return 1;
    }

    private static boolean lambda$static$7(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, EntityType entityType) {
        return entityType == EntityType.POLAR_BEAR;
    }

    private static TileEntityType lambda$static$6() {
        return TileEntityType.CHEST;
    }

    private static int lambda$static$5(BlockState blockState) {
        return 1;
    }

    private static int lambda$static$4(BlockState blockState) {
        return 0;
    }

    private static int lambda$static$3(BlockState blockState) {
        return 1;
    }

    private static int lambda$static$2(BlockState blockState) {
        return 1;
    }

    private static int lambda$static$1(BlockState blockState) {
        return 0;
    }

    private static int lambda$static$0(BlockState blockState) {
        return 0;
    }

    static {
        for (Block block : Registry.BLOCK) {
            for (BlockState blockState : block.getStateContainer().getValidStates()) {
                Block.BLOCK_STATE_IDS.add(blockState);
            }
            block.getLootTable();
        }
    }
}

