package net.minecraft.client.renderer;

import net.minecraft.block.state.*;
import net.minecraft.init.*;
import net.minecraft.block.properties.*;
import net.minecraft.client.renderer.block.statemap.*;
import net.minecraft.client.resources.model.*;
import com.google.common.collect.*;
import net.minecraft.util.*;
import net.minecraft.block.*;
import java.util.*;
import net.minecraft.client.renderer.texture.*;

public class BlockModelShapes
{
    private final Map<IBlockState, IBakedModel> bakedModelStore;
    private static final String[] I;
    private final ModelManager modelManager;
    private final BlockStateMapper blockStateMapper;
    
    private static void I() {
        (I = new String[0x72 ^ 0x65])["".length()] = I("\u000f\u001f\u001d$&\u0010\u0017\u00155\u007f\u0000\u001a\u001c\".\u0011Y\u0003-$\f\u001d\u0000\u001e*\u0003\u001d", "bvsAE");
        BlockModelShapes.I[" ".length()] = I("'\u0013\u0005!/8\u001b\r0v(\u0016\u0004''9U\u0004&?#\u001e\u0002%\"", "JzkDL");
        BlockModelShapes.I["  ".length()] = I("=3\u000b2&\";\u0003#\u007f26\n4.#u\t631\u0005\u0016#,<6", "PZeWE");
        BlockModelShapes.I["   ".length()] = I("\u0004\u0002\u001f\u0014\f\u001b\n\u0017\u0005U\u000b\u0007\u001e\u0012\u0004\u001aD\u0006\u0010\u001b\f\u0019.\u0002\u001b\u0000\u0007\u001d", "ikqqo");
        BlockModelShapes.I[0x33 ^ 0x37] = I("5\u001f\u0002\u0010\u0007*\u0017\n\u0001^:\u001a\u0003\u0016\u000f+Y\u001f\u001a\u00114)\u001f\u0014\n<", "Xvlud");
        BlockModelShapes.I[0x98 ^ 0x9D] = I(" \u0006(?)?\u000e .p$\u001b#79b\r'(8$\n4", "MoFZJ");
        BlockModelShapes.I[0x60 ^ 0x66] = I("6\u001f\"\u0002\u000e\f\u0000", "isGcx");
        BlockModelShapes.I[0x4E ^ 0x49] = I("\u0016\u00024-\",\u001d", "InQLT");
        BlockModelShapes.I[0xB1 ^ 0xB9] = I("+?\u001b4\t", "tHzXe");
        BlockModelShapes.I[0x4F ^ 0x46] = I("\u000f+(\u0014#<*\u0018\u0012-1-", "POGaA");
        BlockModelShapes.I[0x85 ^ 0x8F] = I("3\u0000\u0016\n\u000f", "lszkm");
        BlockModelShapes.I[0x1B ^ 0x10] = I("<\u0000\u0005\u0005-", "cwjjA");
        BlockModelShapes.I[0x36 ^ 0x3A] = I("50*\n\u0018\u000f'", "jSKxh");
        BlockModelShapes.I[0x17 ^ 0x1A] = I(",\u0005!\b\u0005\u001d\u001316\u0004\u0012\u00041\f\u0002\u0016\u0012\n\n\u0000\u0012\u000f", "svUil");
        BlockModelShapes.I[0x1A ^ 0x14] = I("\u000e\u000469-?\u0012&\u0007#=\u00161+\u001b!\u0016,=", "QwBXD");
        BlockModelShapes.I[0xCC ^ 0xC3] = I("0';6\n\u00011+\b\u0004\u00035<$", "oTOWc");
        BlockModelShapes.I[0x67 ^ 0x77] = I("\u0013\u001d!,*", "LnMMH");
        BlockModelShapes.I[0x65 ^ 0x74] = I("\u0011\u0000\b\r\u001a", "Nsdlx");
        BlockModelShapes.I[0x51 ^ 0x43] = I("-'\u0003\r;\u0006/\u001e<-\u0015-", "rJlcH");
        BlockModelShapes.I[0x2C ^ 0x3F] = I("6%\t\r", "iIfjW");
        BlockModelShapes.I[0x28 ^ 0x3C] = I(".\r\t$", "qafCi");
        BlockModelShapes.I[0x1D ^ 0x8] = I("\u0018562>,6", "GEZSP");
        BlockModelShapes.I[0x78 ^ 0x6E] = I("\u001c52\u0019\u0004*(4", "CFSih");
    }
    
    public IBakedModel getModelForState(final IBlockState blockState) {
        IBakedModel missingModel = this.bakedModelStore.get(blockState);
        if (missingModel == null) {
            missingModel = this.modelManager.getMissingModel();
        }
        return missingModel;
    }
    
    public void registerBlockWithStateMapper(final Block block, final IStateMapper stateMapper) {
        this.blockStateMapper.registerBlockStateMapper(block, stateMapper);
    }
    
    public ModelManager getModelManager() {
        return this.modelManager;
    }
    
    private void registerAllBlocks() {
        final Block[] array = new Block[0x8B ^ 0x9B];
        array["".length()] = Blocks.air;
        array[" ".length()] = Blocks.flowing_water;
        array["  ".length()] = Blocks.water;
        array["   ".length()] = Blocks.flowing_lava;
        array[0x43 ^ 0x47] = Blocks.lava;
        array[0x66 ^ 0x63] = Blocks.piston_extension;
        array[0x85 ^ 0x83] = Blocks.chest;
        array[0x51 ^ 0x56] = Blocks.ender_chest;
        array[0x6B ^ 0x63] = Blocks.trapped_chest;
        array[0x80 ^ 0x89] = Blocks.standing_sign;
        array[0xB7 ^ 0xBD] = Blocks.skull;
        array[0x31 ^ 0x3A] = Blocks.end_portal;
        array[0x4C ^ 0x40] = Blocks.barrier;
        array[0x2A ^ 0x27] = Blocks.wall_sign;
        array[0x8E ^ 0x80] = Blocks.wall_banner;
        array[0x8E ^ 0x81] = Blocks.standing_banner;
        this.registerBuiltInBlocks(array);
        this.registerBlockWithStateMapper(Blocks.stone, new StateMap.Builder().withName(BlockStone.VARIANT).build());
        this.registerBlockWithStateMapper(Blocks.prismarine, new StateMap.Builder().withName(BlockPrismarine.VARIANT).build());
        final BlockLeaves leaves = Blocks.leaves;
        final StateMap.Builder withSuffix = new StateMap.Builder().withName(BlockOldLeaf.VARIANT).withSuffix(BlockModelShapes.I[0x2E ^ 0x28]);
        final IProperty[] array2 = new IProperty["  ".length()];
        array2["".length()] = BlockLeaves.CHECK_DECAY;
        array2[" ".length()] = BlockLeaves.DECAYABLE;
        this.registerBlockWithStateMapper(leaves, withSuffix.ignore((IProperty<?>[])array2).build());
        final BlockLeaves leaves2 = Blocks.leaves2;
        final StateMap.Builder withSuffix2 = new StateMap.Builder().withName(BlockNewLeaf.VARIANT).withSuffix(BlockModelShapes.I[0xB6 ^ 0xB1]);
        final IProperty[] array3 = new IProperty["  ".length()];
        array3["".length()] = BlockLeaves.CHECK_DECAY;
        array3[" ".length()] = BlockLeaves.DECAYABLE;
        this.registerBlockWithStateMapper(leaves2, withSuffix2.ignore((IProperty<?>[])array3).build());
        final BlockCactus cactus = Blocks.cactus;
        final StateMap.Builder builder = new StateMap.Builder();
        final IProperty[] array4 = new IProperty[" ".length()];
        array4["".length()] = BlockCactus.AGE;
        this.registerBlockWithStateMapper(cactus, builder.ignore((IProperty<?>[])array4).build());
        final BlockReed reeds = Blocks.reeds;
        final StateMap.Builder builder2 = new StateMap.Builder();
        final IProperty[] array5 = new IProperty[" ".length()];
        array5["".length()] = BlockReed.AGE;
        this.registerBlockWithStateMapper(reeds, builder2.ignore((IProperty<?>[])array5).build());
        final Block jukebox = Blocks.jukebox;
        final StateMap.Builder builder3 = new StateMap.Builder();
        final IProperty[] array6 = new IProperty[" ".length()];
        array6["".length()] = BlockJukebox.HAS_RECORD;
        this.registerBlockWithStateMapper(jukebox, builder3.ignore((IProperty<?>[])array6).build());
        final Block command_block = Blocks.command_block;
        final StateMap.Builder builder4 = new StateMap.Builder();
        final IProperty[] array7 = new IProperty[" ".length()];
        array7["".length()] = BlockCommandBlock.TRIGGERED;
        this.registerBlockWithStateMapper(command_block, builder4.ignore((IProperty<?>[])array7).build());
        this.registerBlockWithStateMapper(Blocks.cobblestone_wall, new StateMap.Builder().withName(BlockWall.VARIANT).withSuffix(BlockModelShapes.I[0x5C ^ 0x54]).build());
        final BlockDoublePlant double_plant = Blocks.double_plant;
        final StateMap.Builder withName = new StateMap.Builder().withName(BlockDoublePlant.VARIANT);
        final IProperty[] array8 = new IProperty[" ".length()];
        array8["".length()] = BlockDoublePlant.field_181084_N;
        this.registerBlockWithStateMapper(double_plant, withName.ignore((IProperty<?>[])array8).build());
        final Block oak_fence_gate = Blocks.oak_fence_gate;
        final StateMap.Builder builder5 = new StateMap.Builder();
        final IProperty[] array9 = new IProperty[" ".length()];
        array9["".length()] = BlockFenceGate.POWERED;
        this.registerBlockWithStateMapper(oak_fence_gate, builder5.ignore((IProperty<?>[])array9).build());
        final Block spruce_fence_gate = Blocks.spruce_fence_gate;
        final StateMap.Builder builder6 = new StateMap.Builder();
        final IProperty[] array10 = new IProperty[" ".length()];
        array10["".length()] = BlockFenceGate.POWERED;
        this.registerBlockWithStateMapper(spruce_fence_gate, builder6.ignore((IProperty<?>[])array10).build());
        final Block birch_fence_gate = Blocks.birch_fence_gate;
        final StateMap.Builder builder7 = new StateMap.Builder();
        final IProperty[] array11 = new IProperty[" ".length()];
        array11["".length()] = BlockFenceGate.POWERED;
        this.registerBlockWithStateMapper(birch_fence_gate, builder7.ignore((IProperty<?>[])array11).build());
        final Block jungle_fence_gate = Blocks.jungle_fence_gate;
        final StateMap.Builder builder8 = new StateMap.Builder();
        final IProperty[] array12 = new IProperty[" ".length()];
        array12["".length()] = BlockFenceGate.POWERED;
        this.registerBlockWithStateMapper(jungle_fence_gate, builder8.ignore((IProperty<?>[])array12).build());
        final Block dark_oak_fence_gate = Blocks.dark_oak_fence_gate;
        final StateMap.Builder builder9 = new StateMap.Builder();
        final IProperty[] array13 = new IProperty[" ".length()];
        array13["".length()] = BlockFenceGate.POWERED;
        this.registerBlockWithStateMapper(dark_oak_fence_gate, builder9.ignore((IProperty<?>[])array13).build());
        final Block acacia_fence_gate = Blocks.acacia_fence_gate;
        final StateMap.Builder builder10 = new StateMap.Builder();
        final IProperty[] array14 = new IProperty[" ".length()];
        array14["".length()] = BlockFenceGate.POWERED;
        this.registerBlockWithStateMapper(acacia_fence_gate, builder10.ignore((IProperty<?>[])array14).build());
        final Block tripwire = Blocks.tripwire;
        final StateMap.Builder builder11 = new StateMap.Builder();
        final IProperty[] array15 = new IProperty["  ".length()];
        array15["".length()] = BlockTripWire.DISARMED;
        array15[" ".length()] = BlockTripWire.POWERED;
        this.registerBlockWithStateMapper(tripwire, builder11.ignore((IProperty<?>[])array15).build());
        this.registerBlockWithStateMapper(Blocks.double_wooden_slab, new StateMap.Builder().withName(BlockPlanks.VARIANT).withSuffix(BlockModelShapes.I[0xE ^ 0x7]).build());
        this.registerBlockWithStateMapper(Blocks.wooden_slab, new StateMap.Builder().withName(BlockPlanks.VARIANT).withSuffix(BlockModelShapes.I[0x5 ^ 0xF]).build());
        final Block tnt = Blocks.tnt;
        final StateMap.Builder builder12 = new StateMap.Builder();
        final IProperty[] array16 = new IProperty[" ".length()];
        array16["".length()] = BlockTNT.EXPLODE;
        this.registerBlockWithStateMapper(tnt, builder12.ignore((IProperty<?>[])array16).build());
        final BlockFire fire = Blocks.fire;
        final StateMap.Builder builder13 = new StateMap.Builder();
        final IProperty[] array17 = new IProperty[" ".length()];
        array17["".length()] = BlockFire.AGE;
        this.registerBlockWithStateMapper(fire, builder13.ignore((IProperty<?>[])array17).build());
        final BlockRedstoneWire redstone_wire = Blocks.redstone_wire;
        final StateMap.Builder builder14 = new StateMap.Builder();
        final IProperty[] array18 = new IProperty[" ".length()];
        array18["".length()] = BlockRedstoneWire.POWER;
        this.registerBlockWithStateMapper(redstone_wire, builder14.ignore((IProperty<?>[])array18).build());
        final Block oak_door = Blocks.oak_door;
        final StateMap.Builder builder15 = new StateMap.Builder();
        final IProperty[] array19 = new IProperty[" ".length()];
        array19["".length()] = BlockDoor.POWERED;
        this.registerBlockWithStateMapper(oak_door, builder15.ignore((IProperty<?>[])array19).build());
        final Block spruce_door = Blocks.spruce_door;
        final StateMap.Builder builder16 = new StateMap.Builder();
        final IProperty[] array20 = new IProperty[" ".length()];
        array20["".length()] = BlockDoor.POWERED;
        this.registerBlockWithStateMapper(spruce_door, builder16.ignore((IProperty<?>[])array20).build());
        final Block birch_door = Blocks.birch_door;
        final StateMap.Builder builder17 = new StateMap.Builder();
        final IProperty[] array21 = new IProperty[" ".length()];
        array21["".length()] = BlockDoor.POWERED;
        this.registerBlockWithStateMapper(birch_door, builder17.ignore((IProperty<?>[])array21).build());
        final Block jungle_door = Blocks.jungle_door;
        final StateMap.Builder builder18 = new StateMap.Builder();
        final IProperty[] array22 = new IProperty[" ".length()];
        array22["".length()] = BlockDoor.POWERED;
        this.registerBlockWithStateMapper(jungle_door, builder18.ignore((IProperty<?>[])array22).build());
        final Block acacia_door = Blocks.acacia_door;
        final StateMap.Builder builder19 = new StateMap.Builder();
        final IProperty[] array23 = new IProperty[" ".length()];
        array23["".length()] = BlockDoor.POWERED;
        this.registerBlockWithStateMapper(acacia_door, builder19.ignore((IProperty<?>[])array23).build());
        final Block dark_oak_door = Blocks.dark_oak_door;
        final StateMap.Builder builder20 = new StateMap.Builder();
        final IProperty[] array24 = new IProperty[" ".length()];
        array24["".length()] = BlockDoor.POWERED;
        this.registerBlockWithStateMapper(dark_oak_door, builder20.ignore((IProperty<?>[])array24).build());
        final Block iron_door = Blocks.iron_door;
        final StateMap.Builder builder21 = new StateMap.Builder();
        final IProperty[] array25 = new IProperty[" ".length()];
        array25["".length()] = BlockDoor.POWERED;
        this.registerBlockWithStateMapper(iron_door, builder21.ignore((IProperty<?>[])array25).build());
        this.registerBlockWithStateMapper(Blocks.wool, new StateMap.Builder().withName(BlockColored.COLOR).withSuffix(BlockModelShapes.I[0x8F ^ 0x84]).build());
        this.registerBlockWithStateMapper(Blocks.carpet, new StateMap.Builder().withName(BlockColored.COLOR).withSuffix(BlockModelShapes.I[0x79 ^ 0x75]).build());
        this.registerBlockWithStateMapper(Blocks.stained_hardened_clay, new StateMap.Builder().withName(BlockColored.COLOR).withSuffix(BlockModelShapes.I[0x19 ^ 0x14]).build());
        this.registerBlockWithStateMapper(Blocks.stained_glass_pane, new StateMap.Builder().withName(BlockColored.COLOR).withSuffix(BlockModelShapes.I[0x14 ^ 0x1A]).build());
        this.registerBlockWithStateMapper(Blocks.stained_glass, new StateMap.Builder().withName(BlockColored.COLOR).withSuffix(BlockModelShapes.I[0x24 ^ 0x2B]).build());
        this.registerBlockWithStateMapper(Blocks.sandstone, new StateMap.Builder().withName(BlockSandStone.TYPE).build());
        this.registerBlockWithStateMapper(Blocks.red_sandstone, new StateMap.Builder().withName(BlockRedSandstone.TYPE).build());
        this.registerBlockWithStateMapper(Blocks.tallgrass, new StateMap.Builder().withName(BlockTallGrass.TYPE).build());
        final Block bed = Blocks.bed;
        final StateMap.Builder builder22 = new StateMap.Builder();
        final IProperty[] array26 = new IProperty[" ".length()];
        array26["".length()] = BlockBed.OCCUPIED;
        this.registerBlockWithStateMapper(bed, builder22.ignore((IProperty<?>[])array26).build());
        this.registerBlockWithStateMapper(Blocks.yellow_flower, new StateMap.Builder().withName(Blocks.yellow_flower.getTypeProperty()).build());
        this.registerBlockWithStateMapper(Blocks.red_flower, new StateMap.Builder().withName(Blocks.red_flower.getTypeProperty()).build());
        this.registerBlockWithStateMapper(Blocks.stone_slab, new StateMap.Builder().withName(BlockStoneSlab.VARIANT).withSuffix(BlockModelShapes.I[0x47 ^ 0x57]).build());
        this.registerBlockWithStateMapper(Blocks.stone_slab2, new StateMap.Builder().withName(BlockStoneSlabNew.VARIANT).withSuffix(BlockModelShapes.I[0xAE ^ 0xBF]).build());
        this.registerBlockWithStateMapper(Blocks.monster_egg, new StateMap.Builder().withName(BlockSilverfish.VARIANT).withSuffix(BlockModelShapes.I[0x18 ^ 0xA]).build());
        this.registerBlockWithStateMapper(Blocks.stonebrick, new StateMap.Builder().withName(BlockStoneBrick.VARIANT).build());
        final Block dispenser = Blocks.dispenser;
        final StateMap.Builder builder23 = new StateMap.Builder();
        final IProperty[] array27 = new IProperty[" ".length()];
        array27["".length()] = BlockDispenser.TRIGGERED;
        this.registerBlockWithStateMapper(dispenser, builder23.ignore((IProperty<?>[])array27).build());
        final Block dropper = Blocks.dropper;
        final StateMap.Builder builder24 = new StateMap.Builder();
        final IProperty[] array28 = new IProperty[" ".length()];
        array28["".length()] = BlockDropper.TRIGGERED;
        this.registerBlockWithStateMapper(dropper, builder24.ignore((IProperty<?>[])array28).build());
        this.registerBlockWithStateMapper(Blocks.log, new StateMap.Builder().withName(BlockOldLog.VARIANT).withSuffix(BlockModelShapes.I[0x74 ^ 0x67]).build());
        this.registerBlockWithStateMapper(Blocks.log2, new StateMap.Builder().withName(BlockNewLog.VARIANT).withSuffix(BlockModelShapes.I[0xAA ^ 0xBE]).build());
        this.registerBlockWithStateMapper(Blocks.planks, new StateMap.Builder().withName(BlockPlanks.VARIANT).withSuffix(BlockModelShapes.I[0x11 ^ 0x4]).build());
        this.registerBlockWithStateMapper(Blocks.sapling, new StateMap.Builder().withName(BlockSapling.TYPE).withSuffix(BlockModelShapes.I[0x7A ^ 0x6C]).build());
        this.registerBlockWithStateMapper(Blocks.sand, new StateMap.Builder().withName(BlockSand.VARIANT).build());
        final BlockHopper hopper = Blocks.hopper;
        final StateMap.Builder builder25 = new StateMap.Builder();
        final IProperty[] array29 = new IProperty[" ".length()];
        array29["".length()] = BlockHopper.ENABLED;
        this.registerBlockWithStateMapper(hopper, builder25.ignore((IProperty<?>[])array29).build());
        final Block flower_pot = Blocks.flower_pot;
        final StateMap.Builder builder26 = new StateMap.Builder();
        final IProperty[] array30 = new IProperty[" ".length()];
        array30["".length()] = BlockFlowerPot.LEGACY_DATA;
        this.registerBlockWithStateMapper(flower_pot, builder26.ignore((IProperty<?>[])array30).build());
        this.registerBlockWithStateMapper(Blocks.quartz_block, new StateMapperBase(this) {
            final BlockModelShapes this$0;
            private static final String[] I;
            private static int[] $SWITCH_TABLE$net$minecraft$block$BlockQuartz$EnumType;
            
            private static void I() {
                (I = new String[0x9C ^ 0x96])["".length()] = I("\u001c\u001c)<%\u00176*\">\u000e\u0002", "miHNQ");
                BlockModelShapes$1.I[" ".length()] = I(";? *#9", "UPRGB");
                BlockModelShapes$1.I["  ".length()] = I("\u000e9%?\u001c\u00014(\u0013\b\u00180>8\u000323 #\u001a\u0006", "mQLLy");
                BlockModelShapes$1.I["   ".length()] = I("!\t1;$#", "OfCVE");
                BlockModelShapes$1.I[0x34 ^ 0x30] = I("\u001d\u0006\u0013\u001f\u0015\u0016,\u0011\u0002\r\u0019\u001e\u001c", "lsrma");
                BlockModelShapes$1.I[0x6 ^ 0x3] = I("\r\n8\u0010W\u0015", "lrQcj");
                BlockModelShapes$1.I[0x24 ^ 0x22] = I("+\u0010-(' :/5?/\b\"", "ZeLZS");
                BlockModelShapes$1.I[0xC6 ^ 0xC1] = I("\u0018\u0000\u001c\u0017W\u0001", "yxudj");
                BlockModelShapes$1.I[0xCF ^ 0xC7] = I("5\r*+0>'(6(1\u0015%", "DxKYD");
                BlockModelShapes$1.I[0x74 ^ 0x7D] = I("\u000e\t#!D\u0015", "oqJRy");
            }
            
            @Override
            protected ModelResourceLocation getModelResourceLocation(final IBlockState blockState) {
                switch ($SWITCH_TABLE$net$minecraft$block$BlockQuartz$EnumType()[blockState.getValue(BlockQuartz.VARIANT).ordinal()]) {
                    default: {
                        return new ModelResourceLocation(BlockModelShapes$1.I["".length()], BlockModelShapes$1.I[" ".length()]);
                    }
                    case 2: {
                        return new ModelResourceLocation(BlockModelShapes$1.I["  ".length()], BlockModelShapes$1.I["   ".length()]);
                    }
                    case 3: {
                        return new ModelResourceLocation(BlockModelShapes$1.I[0x69 ^ 0x6D], BlockModelShapes$1.I[0x27 ^ 0x22]);
                    }
                    case 4: {
                        return new ModelResourceLocation(BlockModelShapes$1.I[0x5D ^ 0x5B], BlockModelShapes$1.I[0x9B ^ 0x9C]);
                    }
                    case 5: {
                        return new ModelResourceLocation(BlockModelShapes$1.I[0x7B ^ 0x73], BlockModelShapes$1.I[0x24 ^ 0x2D]);
                    }
                }
            }
            
            private static String I(final String s, final String s2) {
                final StringBuilder sb = new StringBuilder();
                final char[] charArray = s2.toCharArray();
                int length = "".length();
                final char[] charArray2 = s.toCharArray();
                final int length2 = charArray2.length;
                int i = "".length();
                while (i < length2) {
                    sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
                    ++length;
                    ++i;
                    "".length();
                    if (3 == 1) {
                        throw null;
                    }
                }
                return sb.toString();
            }
            
            static int[] $SWITCH_TABLE$net$minecraft$block$BlockQuartz$EnumType() {
                final int[] $switch_TABLE$net$minecraft$block$BlockQuartz$EnumType = BlockModelShapes$1.$SWITCH_TABLE$net$minecraft$block$BlockQuartz$EnumType;
                if ($switch_TABLE$net$minecraft$block$BlockQuartz$EnumType != null) {
                    return $switch_TABLE$net$minecraft$block$BlockQuartz$EnumType;
                }
                final int[] $switch_TABLE$net$minecraft$block$BlockQuartz$EnumType2 = new int[BlockQuartz.EnumType.values().length];
                try {
                    $switch_TABLE$net$minecraft$block$BlockQuartz$EnumType2[BlockQuartz.EnumType.CHISELED.ordinal()] = "  ".length();
                    "".length();
                    if (3 < 2) {
                        throw null;
                    }
                }
                catch (NoSuchFieldError noSuchFieldError) {}
                try {
                    $switch_TABLE$net$minecraft$block$BlockQuartz$EnumType2[BlockQuartz.EnumType.DEFAULT.ordinal()] = " ".length();
                    "".length();
                    if (false) {
                        throw null;
                    }
                }
                catch (NoSuchFieldError noSuchFieldError2) {}
                try {
                    $switch_TABLE$net$minecraft$block$BlockQuartz$EnumType2[BlockQuartz.EnumType.LINES_X.ordinal()] = (0x4 ^ 0x0);
                    "".length();
                    if (0 >= 2) {
                        throw null;
                    }
                }
                catch (NoSuchFieldError noSuchFieldError3) {}
                try {
                    $switch_TABLE$net$minecraft$block$BlockQuartz$EnumType2[BlockQuartz.EnumType.LINES_Y.ordinal()] = "   ".length();
                    "".length();
                    if (4 < 4) {
                        throw null;
                    }
                }
                catch (NoSuchFieldError noSuchFieldError4) {}
                try {
                    $switch_TABLE$net$minecraft$block$BlockQuartz$EnumType2[BlockQuartz.EnumType.LINES_Z.ordinal()] = (0x4C ^ 0x49);
                    "".length();
                    if (1 < -1) {
                        throw null;
                    }
                }
                catch (NoSuchFieldError noSuchFieldError5) {}
                return BlockModelShapes$1.$SWITCH_TABLE$net$minecraft$block$BlockQuartz$EnumType = $switch_TABLE$net$minecraft$block$BlockQuartz$EnumType2;
            }
            
            static {
                I();
            }
        });
        this.registerBlockWithStateMapper(Blocks.deadbush, new StateMapperBase(this) {
            final BlockModelShapes this$0;
            private static final String[] I;
            
            static {
                I();
            }
            
            private static void I() {
                (I = new String["  ".length()])["".length()] = I("+\u001c\u0010\u0014/-\f\u0002\u0018", "Oyqpp");
                BlockModelShapes$2.I[" ".length()] = I("\u0018>;\u0019(\u001a", "vQItI");
            }
            
            @Override
            protected ModelResourceLocation getModelResourceLocation(final IBlockState blockState) {
                return new ModelResourceLocation(BlockModelShapes$2.I["".length()], BlockModelShapes$2.I[" ".length()]);
            }
            
            private static String I(final String s, final String s2) {
                final StringBuilder sb = new StringBuilder();
                final char[] charArray = s2.toCharArray();
                int length = "".length();
                final char[] charArray2 = s.toCharArray();
                final int length2 = charArray2.length;
                int i = "".length();
                while (i < length2) {
                    sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
                    ++length;
                    ++i;
                    "".length();
                    if (4 < 0) {
                        throw null;
                    }
                }
                return sb.toString();
            }
        });
        this.registerBlockWithStateMapper(Blocks.pumpkin_stem, new StateMapperBase(this) {
            final BlockModelShapes this$0;
            
            @Override
            protected ModelResourceLocation getModelResourceLocation(final IBlockState blockState) {
                final LinkedHashMap linkedHashMap = Maps.newLinkedHashMap((Map)blockState.getProperties());
                if (blockState.getValue((IProperty<Comparable>)BlockStem.FACING) != EnumFacing.UP) {
                    linkedHashMap.remove(BlockStem.AGE);
                }
                return new ModelResourceLocation(Block.blockRegistry.getNameForObject(blockState.getBlock()), this.getPropertyString(linkedHashMap));
            }
            
            private static String I(final String s, final String s2) {
                final StringBuilder sb = new StringBuilder();
                final char[] charArray = s2.toCharArray();
                int length = "".length();
                final char[] charArray2 = s.toCharArray();
                final int length2 = charArray2.length;
                int i = "".length();
                while (i < length2) {
                    sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
                    ++length;
                    ++i;
                    "".length();
                    if (2 <= -1) {
                        throw null;
                    }
                }
                return sb.toString();
            }
        });
        this.registerBlockWithStateMapper(Blocks.melon_stem, new StateMapperBase(this) {
            final BlockModelShapes this$0;
            
            @Override
            protected ModelResourceLocation getModelResourceLocation(final IBlockState blockState) {
                final LinkedHashMap linkedHashMap = Maps.newLinkedHashMap((Map)blockState.getProperties());
                if (blockState.getValue((IProperty<Comparable>)BlockStem.FACING) != EnumFacing.UP) {
                    linkedHashMap.remove(BlockStem.AGE);
                }
                return new ModelResourceLocation(Block.blockRegistry.getNameForObject(blockState.getBlock()), this.getPropertyString(linkedHashMap));
            }
            
            private static String I(final String s, final String s2) {
                final StringBuilder sb = new StringBuilder();
                final char[] charArray = s2.toCharArray();
                int length = "".length();
                final char[] charArray2 = s.toCharArray();
                final int length2 = charArray2.length;
                int i = "".length();
                while (i < length2) {
                    sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
                    ++length;
                    ++i;
                    "".length();
                    if (3 != 3) {
                        throw null;
                    }
                }
                return sb.toString();
            }
        });
        this.registerBlockWithStateMapper(Blocks.dirt, new StateMapperBase(this) {
            final BlockModelShapes this$0;
            
            @Override
            protected ModelResourceLocation getModelResourceLocation(final IBlockState blockState) {
                final LinkedHashMap linkedHashMap = Maps.newLinkedHashMap((Map)blockState.getProperties());
                final String name = BlockDirt.VARIANT.getName(linkedHashMap.remove(BlockDirt.VARIANT));
                if (BlockDirt.DirtType.PODZOL != blockState.getValue(BlockDirt.VARIANT)) {
                    linkedHashMap.remove(BlockDirt.SNOWY);
                }
                return new ModelResourceLocation(name, this.getPropertyString(linkedHashMap));
            }
            
            private static String I(final String s, final String s2) {
                final StringBuilder sb = new StringBuilder();
                final char[] charArray = s2.toCharArray();
                int length = "".length();
                final char[] charArray2 = s.toCharArray();
                final int length2 = charArray2.length;
                int i = "".length();
                while (i < length2) {
                    sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
                    ++length;
                    ++i;
                    "".length();
                    if (false) {
                        throw null;
                    }
                }
                return sb.toString();
            }
        });
        this.registerBlockWithStateMapper(Blocks.double_stone_slab, new StateMapperBase(this) {
            private static final String[] I;
            final BlockModelShapes this$0;
            
            @Override
            protected ModelResourceLocation getModelResourceLocation(final IBlockState blockState) {
                final LinkedHashMap linkedHashMap = Maps.newLinkedHashMap((Map)blockState.getProperties());
                final String name = BlockStoneSlab.VARIANT.getName(linkedHashMap.remove(BlockStoneSlab.VARIANT));
                linkedHashMap.remove(BlockStoneSlab.SEAMLESS);
                String s;
                if (blockState.getValue((IProperty<Boolean>)BlockStoneSlab.SEAMLESS)) {
                    s = BlockModelShapes$6.I["".length()];
                    "".length();
                    if (-1 >= 4) {
                        throw null;
                    }
                }
                else {
                    s = BlockModelShapes$6.I[" ".length()];
                }
                return new ModelResourceLocation(String.valueOf(name) + BlockModelShapes$6.I["  ".length()], s);
            }
            
            private static void I() {
                (I = new String["   ".length()])["".length()] = I("\u0011\u0001\u0001", "pmmjO");
                BlockModelShapes$6.I[" ".length()] = I("=\u000e\u0018/4?", "SajBU");
                BlockModelShapes$6.I["  ".length()] = I("7\u0016(\u0012/\u0004\u0017\u0018\u0014!\t\u0010", "hrGgM");
            }
            
            static {
                I();
            }
            
            private static String I(final String s, final String s2) {
                final StringBuilder sb = new StringBuilder();
                final char[] charArray = s2.toCharArray();
                int length = "".length();
                final char[] charArray2 = s.toCharArray();
                final int length2 = charArray2.length;
                int i = "".length();
                while (i < length2) {
                    sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
                    ++length;
                    ++i;
                    "".length();
                    if (4 < 2) {
                        throw null;
                    }
                }
                return sb.toString();
            }
        });
        this.registerBlockWithStateMapper(Blocks.double_stone_slab2, new StateMapperBase(this) {
            private static final String[] I;
            final BlockModelShapes this$0;
            
            @Override
            protected ModelResourceLocation getModelResourceLocation(final IBlockState blockState) {
                final LinkedHashMap linkedHashMap = Maps.newLinkedHashMap((Map)blockState.getProperties());
                final String name = BlockStoneSlabNew.VARIANT.getName(linkedHashMap.remove(BlockStoneSlabNew.VARIANT));
                linkedHashMap.remove(BlockStoneSlab.SEAMLESS);
                String s;
                if (blockState.getValue((IProperty<Boolean>)BlockStoneSlabNew.SEAMLESS)) {
                    s = BlockModelShapes$7.I["".length()];
                    "".length();
                    if (3 != 3) {
                        throw null;
                    }
                }
                else {
                    s = BlockModelShapes$7.I[" ".length()];
                }
                return new ModelResourceLocation(String.valueOf(name) + BlockModelShapes$7.I["  ".length()], s);
            }
            
            static {
                I();
            }
            
            private static void I() {
                (I = new String["   ".length()])["".length()] = I("/ \b", "NLdZk");
                BlockModelShapes$7.I[" ".length()] = I("\u0000>5\b\r\u0002", "nQGel");
                BlockModelShapes$7.I["  ".length()] = I("\u001a\u0012\u001c\u0000;)\u0013,\u00065$\u0014", "EvsuY");
            }
            
            private static String I(final String s, final String s2) {
                final StringBuilder sb = new StringBuilder();
                final char[] charArray = s2.toCharArray();
                int length = "".length();
                final char[] charArray2 = s.toCharArray();
                final int length2 = charArray2.length;
                int i = "".length();
                while (i < length2) {
                    sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
                    ++length;
                    ++i;
                    "".length();
                    if (4 <= 0) {
                        throw null;
                    }
                }
                return sb.toString();
            }
        });
    }
    
    public void registerBuiltInBlocks(final Block... array) {
        this.blockStateMapper.registerBuiltInBlocks(array);
    }
    
    static {
        I();
    }
    
    public BlockStateMapper getBlockStateMapper() {
        return this.blockStateMapper;
    }
    
    public BlockModelShapes(final ModelManager modelManager) {
        this.bakedModelStore = (Map<IBlockState, IBakedModel>)Maps.newIdentityHashMap();
        this.blockStateMapper = new BlockStateMapper();
        this.modelManager = modelManager;
        this.registerAllBlocks();
    }
    
    public void reloadModels() {
        this.bakedModelStore.clear();
        final Iterator<Map.Entry<IBlockState, ModelResourceLocation>> iterator = this.blockStateMapper.putAllStateModelLocations().entrySet().iterator();
        "".length();
        if (2 <= -1) {
            throw null;
        }
        while (iterator.hasNext()) {
            final Map.Entry<IBlockState, ModelResourceLocation> entry = iterator.next();
            this.bakedModelStore.put(entry.getKey(), this.modelManager.getModel(entry.getValue()));
        }
    }
    
    public TextureAtlasSprite getTexture(final IBlockState blockState) {
        final Block block = blockState.getBlock();
        IBakedModel bakedModel = this.getModelForState(blockState);
        if (bakedModel == null || bakedModel == this.modelManager.getMissingModel()) {
            if (block == Blocks.wall_sign || block == Blocks.standing_sign || block == Blocks.chest || block == Blocks.trapped_chest || block == Blocks.standing_banner || block == Blocks.wall_banner) {
                return this.modelManager.getTextureMap().getAtlasSprite(BlockModelShapes.I["".length()]);
            }
            if (block == Blocks.ender_chest) {
                return this.modelManager.getTextureMap().getAtlasSprite(BlockModelShapes.I[" ".length()]);
            }
            if (block == Blocks.flowing_lava || block == Blocks.lava) {
                return this.modelManager.getTextureMap().getAtlasSprite(BlockModelShapes.I["  ".length()]);
            }
            if (block == Blocks.flowing_water || block == Blocks.water) {
                return this.modelManager.getTextureMap().getAtlasSprite(BlockModelShapes.I["   ".length()]);
            }
            if (block == Blocks.skull) {
                return this.modelManager.getTextureMap().getAtlasSprite(BlockModelShapes.I[0x20 ^ 0x24]);
            }
            if (block == Blocks.barrier) {
                return this.modelManager.getTextureMap().getAtlasSprite(BlockModelShapes.I[0x77 ^ 0x72]);
            }
        }
        if (bakedModel == null) {
            bakedModel = this.modelManager.getMissingModel();
        }
        return bakedModel.getParticleTexture();
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (1 <= -1) {
                throw null;
            }
        }
        return sb.toString();
    }
}
