package HORIZON-6-0-SKIDPROTECTION;

import java.util.LinkedHashMap;
import java.util.Iterator;
import com.google.common.collect.Maps;
import java.util.Map;

public class BlockModelShapes
{
    private final Map HorizonCode_Horizon_È;
    private final BlockStateMapper Â;
    private final ModelManager Ý;
    private static final String Ø­áŒŠá = "CL_00002529";
    
    public BlockModelShapes(final ModelManager p_i46245_1_) {
        this.HorizonCode_Horizon_È = Maps.newIdentityHashMap();
        this.Â = new BlockStateMapper();
        this.Ý = p_i46245_1_;
        this.Ø­áŒŠá();
    }
    
    public BlockStateMapper HorizonCode_Horizon_È() {
        return this.Â;
    }
    
    public TextureAtlasSprite HorizonCode_Horizon_È(final IBlockState p_178122_1_) {
        final Block var2 = p_178122_1_.Ý();
        IBakedModel var3 = this.Â(p_178122_1_);
        if (var3 == null || var3 == this.Ý.HorizonCode_Horizon_È()) {
            if (var2 == Blocks.¥Ä || var2 == Blocks.£Õ || var2 == Blocks.ˆáƒ || var2 == Blocks.ÇŽ || var2 == Blocks.Ï­áŠ || var2 == Blocks.Ñ¢Ô) {
                return this.Ý.Â().HorizonCode_Horizon_È("minecraft:blocks/planks_oak");
            }
            if (var2 == Blocks.¥áŒŠà) {
                return this.Ý.Â().HorizonCode_Horizon_È("minecraft:blocks/obsidian");
            }
            if (var2 == Blocks.á || var2 == Blocks.ˆÏ­) {
                return this.Ý.Â().HorizonCode_Horizon_È("minecraft:blocks/lava_still");
            }
            if (var2 == Blocks.áˆºÑ¢Õ || var2 == Blocks.ÂµÈ) {
                return this.Ý.Â().HorizonCode_Horizon_È("minecraft:blocks/water_still");
            }
            if (var2 == Blocks.ÇªÈ) {
                return this.Ý.Â().HorizonCode_Horizon_È("minecraft:blocks/soul_sand");
            }
            if (var2 == Blocks.¥ÇªÅ) {
                return this.Ý.Â().HorizonCode_Horizon_È("minecraft:items/barrier");
            }
        }
        if (var3 == null) {
            var3 = this.Ý.HorizonCode_Horizon_È();
        }
        return var3.Âµá€();
    }
    
    public IBakedModel Â(final IBlockState p_178125_1_) {
        IBakedModel var2 = this.HorizonCode_Horizon_È.get(p_178125_1_);
        if (var2 == null) {
            var2 = this.Ý.HorizonCode_Horizon_È();
        }
        return var2;
    }
    
    public ModelManager Â() {
        return this.Ý;
    }
    
    public void Ý() {
        this.HorizonCode_Horizon_È.clear();
        for (final Map.Entry var2 : this.Â.HorizonCode_Horizon_È().entrySet()) {
            this.HorizonCode_Horizon_È.put(var2.getKey(), this.Ý.HorizonCode_Horizon_È(var2.getValue()));
        }
    }
    
    public void HorizonCode_Horizon_È(final Block p_178121_1_, final IStateMapper p_178121_2_) {
        this.Â.HorizonCode_Horizon_È(p_178121_1_, p_178121_2_);
    }
    
    public void HorizonCode_Horizon_È(final Block... p_178123_1_) {
        this.Â.HorizonCode_Horizon_È(p_178123_1_);
    }
    
    private void Ø­áŒŠá() {
        this.HorizonCode_Horizon_È(Blocks.Â, Blocks.áˆºÑ¢Õ, Blocks.ÂµÈ, Blocks.á, Blocks.ˆÏ­, Blocks.¥à, Blocks.ˆáƒ, Blocks.¥áŒŠà, Blocks.ÇŽ, Blocks.£Õ, Blocks.ÇªÈ, Blocks.Ï­Ä, Blocks.¥ÇªÅ, Blocks.¥Ä, Blocks.Ñ¢Ô, Blocks.Ï­áŠ);
        this.HorizonCode_Horizon_È(Blocks.Ý, new StateMap.HorizonCode_Horizon_È().HorizonCode_Horizon_È(BlockStone.Õ).HorizonCode_Horizon_È());
        this.HorizonCode_Horizon_È(Blocks.ÇŽáˆºÈ, new StateMap.HorizonCode_Horizon_È().HorizonCode_Horizon_È(BlockPrismarine.Õ).HorizonCode_Horizon_È());
        this.HorizonCode_Horizon_È(Blocks.µÕ, new StateMap.HorizonCode_Horizon_È().HorizonCode_Horizon_È(BlockOldLeaf.È).HorizonCode_Horizon_È("_leaves").HorizonCode_Horizon_È(BlockLeaves.à¢, BlockLeaves.Õ).HorizonCode_Horizon_È());
        this.HorizonCode_Horizon_È(Blocks.Æ, new StateMap.HorizonCode_Horizon_È().HorizonCode_Horizon_È(BlockNewLeaf.È).HorizonCode_Horizon_È("_leaves").HorizonCode_Horizon_È(BlockLeaves.à¢, BlockLeaves.Õ).HorizonCode_Horizon_È());
        this.HorizonCode_Horizon_È(Blocks.Ñ¢Ç, new StateMap.HorizonCode_Horizon_È().HorizonCode_Horizon_È(new IProperty[] { BlockCactus.Õ }).HorizonCode_Horizon_È());
        this.HorizonCode_Horizon_È(Blocks.Ðƒáƒ, new StateMap.HorizonCode_Horizon_È().HorizonCode_Horizon_È(new IProperty[] { BlockReed.Õ }).HorizonCode_Horizon_È());
        this.HorizonCode_Horizon_È(Blocks.Ðƒà, new StateMap.HorizonCode_Horizon_È().HorizonCode_Horizon_È(new IProperty[] { BlockJukebox.Õ }).HorizonCode_Horizon_È());
        this.HorizonCode_Horizon_È(Blocks.ŠÑ¢Ó, new StateMap.HorizonCode_Horizon_È().HorizonCode_Horizon_È(new IProperty[] { BlockCommandBlock.Õ }).HorizonCode_Horizon_È());
        this.HorizonCode_Horizon_È(Blocks.Ï­Ó, new StateMap.HorizonCode_Horizon_È().HorizonCode_Horizon_È(BlockWall.Ç).HorizonCode_Horizon_È("_wall").HorizonCode_Horizon_È());
        this.HorizonCode_Horizon_È(Blocks.ÇªÇªÉ, new StateMap.HorizonCode_Horizon_È().HorizonCode_Horizon_È(BlockDoublePlant.Õ).HorizonCode_Horizon_È());
        this.HorizonCode_Horizon_È(Blocks.ŠáˆºÂ, new StateMap.HorizonCode_Horizon_È().HorizonCode_Horizon_È(new IProperty[] { BlockFenceGate.à¢ }).HorizonCode_Horizon_È());
        this.HorizonCode_Horizon_È(Blocks.Ø­Ñ¢Ï­Ø­áˆº, new StateMap.HorizonCode_Horizon_È().HorizonCode_Horizon_È(new IProperty[] { BlockFenceGate.à¢ }).HorizonCode_Horizon_È());
        this.HorizonCode_Horizon_È(Blocks.ŒÂ, new StateMap.HorizonCode_Horizon_È().HorizonCode_Horizon_È(new IProperty[] { BlockFenceGate.à¢ }).HorizonCode_Horizon_È());
        this.HorizonCode_Horizon_È(Blocks.Ï­Ï, new StateMap.HorizonCode_Horizon_È().HorizonCode_Horizon_È(new IProperty[] { BlockFenceGate.à¢ }).HorizonCode_Horizon_È());
        this.HorizonCode_Horizon_È(Blocks.ŠØ, new StateMap.HorizonCode_Horizon_È().HorizonCode_Horizon_È(new IProperty[] { BlockFenceGate.à¢ }).HorizonCode_Horizon_È());
        this.HorizonCode_Horizon_È(Blocks.ˆÐƒØ, new StateMap.HorizonCode_Horizon_È().HorizonCode_Horizon_È(new IProperty[] { BlockFenceGate.à¢ }).HorizonCode_Horizon_È());
        this.HorizonCode_Horizon_È(Blocks.áŒŠÈ, new StateMap.HorizonCode_Horizon_È().HorizonCode_Horizon_È(BlockTripWire.¥à, BlockTripWire.Õ).HorizonCode_Horizon_È());
        this.HorizonCode_Horizon_È(Blocks.ŒÓ, new StateMap.HorizonCode_Horizon_È().HorizonCode_Horizon_È(BlockPlanks.Õ).HorizonCode_Horizon_È("_double_slab").HorizonCode_Horizon_È());
        this.HorizonCode_Horizon_È(Blocks.ÇŽÊ, new StateMap.HorizonCode_Horizon_È().HorizonCode_Horizon_È(BlockPlanks.Õ).HorizonCode_Horizon_È("_slab").HorizonCode_Horizon_È());
        this.HorizonCode_Horizon_È(Blocks.Ñ¢Â, new StateMap.HorizonCode_Horizon_È().HorizonCode_Horizon_È(new IProperty[] { BlockTNT.Õ }).HorizonCode_Horizon_È());
        this.HorizonCode_Horizon_È(Blocks.Ô, new StateMap.HorizonCode_Horizon_È().HorizonCode_Horizon_È(new IProperty[] { BlockFire.Õ }).HorizonCode_Horizon_È());
        this.HorizonCode_Horizon_È(Blocks.Œ, new StateMap.HorizonCode_Horizon_È().HorizonCode_Horizon_È(new IProperty[] { BlockRedstoneWire.Âµà }).HorizonCode_Horizon_È());
        this.HorizonCode_Horizon_È(Blocks.Ï­Ô, new StateMap.HorizonCode_Horizon_È().HorizonCode_Horizon_È(new IProperty[] { BlockDoor.¥à }).HorizonCode_Horizon_È());
        this.HorizonCode_Horizon_È(Blocks.Œà, new StateMap.HorizonCode_Horizon_È().HorizonCode_Horizon_È(new IProperty[] { BlockDoor.¥à }).HorizonCode_Horizon_È());
        this.HorizonCode_Horizon_È(Blocks.Ðƒá, new StateMap.HorizonCode_Horizon_È().HorizonCode_Horizon_È(new IProperty[] { BlockDoor.¥à }).HorizonCode_Horizon_È());
        this.HorizonCode_Horizon_È(Blocks.ˆÏ, new StateMap.HorizonCode_Horizon_È().HorizonCode_Horizon_È(new IProperty[] { BlockDoor.¥à }).HorizonCode_Horizon_È());
        this.HorizonCode_Horizon_È(Blocks.áˆºÇŽØ, new StateMap.HorizonCode_Horizon_È().HorizonCode_Horizon_È(new IProperty[] { BlockDoor.¥à }).HorizonCode_Horizon_È());
        this.HorizonCode_Horizon_È(Blocks.ÇªÂµÕ, new StateMap.HorizonCode_Horizon_È().HorizonCode_Horizon_È(new IProperty[] { BlockDoor.¥à }).HorizonCode_Horizon_È());
        this.HorizonCode_Horizon_È(Blocks.ŠÓ, new StateMap.HorizonCode_Horizon_È().HorizonCode_Horizon_È(new IProperty[] { BlockDoor.¥à }).HorizonCode_Horizon_È());
        this.HorizonCode_Horizon_È(Blocks.ŠÂµà, new StateMap.HorizonCode_Horizon_È().HorizonCode_Horizon_È(BlockColored.Õ).HorizonCode_Horizon_È("_wool").HorizonCode_Horizon_È());
        this.HorizonCode_Horizon_È(Blocks.áˆºÂ, new StateMap.HorizonCode_Horizon_È().HorizonCode_Horizon_È(BlockColored.Õ).HorizonCode_Horizon_È("_carpet").HorizonCode_Horizon_È());
        this.HorizonCode_Horizon_È(Blocks.Ø­Â, new StateMap.HorizonCode_Horizon_È().HorizonCode_Horizon_È(BlockColored.Õ).HorizonCode_Horizon_È("_stained_hardened_clay").HorizonCode_Horizon_È());
        this.HorizonCode_Horizon_È(Blocks.Ø­áƒ, new StateMap.HorizonCode_Horizon_È().HorizonCode_Horizon_È(BlockColored.Õ).HorizonCode_Horizon_È("_stained_glass_pane").HorizonCode_Horizon_È());
        this.HorizonCode_Horizon_È(Blocks.ÐƒáŒŠÂµÐƒÕ, new StateMap.HorizonCode_Horizon_È().HorizonCode_Horizon_È(BlockColored.Õ).HorizonCode_Horizon_È("_stained_glass").HorizonCode_Horizon_È());
        this.HorizonCode_Horizon_È(Blocks.ŒÏ, new StateMap.HorizonCode_Horizon_È().HorizonCode_Horizon_È(BlockSandStone.Õ).HorizonCode_Horizon_È());
        this.HorizonCode_Horizon_È(Blocks.áˆºÛ, new StateMap.HorizonCode_Horizon_È().HorizonCode_Horizon_È(BlockRedSandstone.Õ).HorizonCode_Horizon_È());
        this.HorizonCode_Horizon_È(Blocks.áƒ, new StateMap.HorizonCode_Horizon_È().HorizonCode_Horizon_È(BlockTallGrass.Õ).HorizonCode_Horizon_È());
        this.HorizonCode_Horizon_È(Blocks.Ê, new StateMap.HorizonCode_Horizon_È().HorizonCode_Horizon_È(new IProperty[] { BlockBed.à¢ }).HorizonCode_Horizon_È());
        this.HorizonCode_Horizon_È(Blocks.Âµà, new StateMap.HorizonCode_Horizon_È().HorizonCode_Horizon_È(Blocks.Âµà.áŠ()).HorizonCode_Horizon_È());
        this.HorizonCode_Horizon_È(Blocks.Ç, new StateMap.HorizonCode_Horizon_È().HorizonCode_Horizon_È(Blocks.Ç.áŠ()).HorizonCode_Horizon_È());
        this.HorizonCode_Horizon_È(Blocks.Ø­Âµ, new StateMap.HorizonCode_Horizon_È().HorizonCode_Horizon_È(BlockStoneSlab.ŠÂµà).HorizonCode_Horizon_È("_slab").HorizonCode_Horizon_È());
        this.HorizonCode_Horizon_È(Blocks.µØ, new StateMap.HorizonCode_Horizon_È().HorizonCode_Horizon_È(BlockStoneSlabNew.ŠÂµà).HorizonCode_Horizon_È("_slab").HorizonCode_Horizon_È());
        this.HorizonCode_Horizon_È(Blocks.ÐƒÂ, new StateMap.HorizonCode_Horizon_È().HorizonCode_Horizon_È(BlockSilverfish.Õ).HorizonCode_Horizon_È("_monster_egg").HorizonCode_Horizon_È());
        this.HorizonCode_Horizon_È(Blocks.£áƒ, new StateMap.HorizonCode_Horizon_È().HorizonCode_Horizon_È(BlockStoneBrick.Õ).HorizonCode_Horizon_È());
        this.HorizonCode_Horizon_È(Blocks.Ñ¢á, new StateMap.HorizonCode_Horizon_È().HorizonCode_Horizon_È(new IProperty[] { BlockDispenser.à¢ }).HorizonCode_Horizon_È());
        this.HorizonCode_Horizon_È(Blocks.áŒŠÓ, new StateMap.HorizonCode_Horizon_È().HorizonCode_Horizon_È(new IProperty[] { BlockDropper.à¢ }).HorizonCode_Horizon_È());
        this.HorizonCode_Horizon_È(Blocks.¥Æ, new StateMap.HorizonCode_Horizon_È().HorizonCode_Horizon_È(BlockOldLog.à¢).HorizonCode_Horizon_È("_log").HorizonCode_Horizon_È());
        this.HorizonCode_Horizon_È(Blocks.Ø­à, new StateMap.HorizonCode_Horizon_È().HorizonCode_Horizon_È(BlockNewLog.à¢).HorizonCode_Horizon_È("_log").HorizonCode_Horizon_È());
        this.HorizonCode_Horizon_È(Blocks.à, new StateMap.HorizonCode_Horizon_È().HorizonCode_Horizon_È(BlockPlanks.Õ).HorizonCode_Horizon_È("_planks").HorizonCode_Horizon_È());
        this.HorizonCode_Horizon_È(Blocks.Ø, new StateMap.HorizonCode_Horizon_È().HorizonCode_Horizon_È(BlockSapling.Õ).HorizonCode_Horizon_È("_sapling").HorizonCode_Horizon_È());
        this.HorizonCode_Horizon_È(Blocks.£á, new StateMap.HorizonCode_Horizon_È().HorizonCode_Horizon_È(BlockSand.Õ).HorizonCode_Horizon_È());
        this.HorizonCode_Horizon_È(Blocks.áˆºÉ, new StateMap.HorizonCode_Horizon_È().HorizonCode_Horizon_È(new IProperty[] { BlockHopper.à¢ }).HorizonCode_Horizon_È());
        this.HorizonCode_Horizon_È(Blocks.ŠáŒŠà¢, new StateMap.HorizonCode_Horizon_È().HorizonCode_Horizon_È(new IProperty[] { BlockFlowerPot.Õ }).HorizonCode_Horizon_È());
        this.HorizonCode_Horizon_È(Blocks.Ø­È, new StateMapperBase() {
            private static final String Ý = "CL_00002528";
            
            @Override
            protected ModelResourceLocation HorizonCode_Horizon_È(final IBlockState p_178132_1_) {
                final BlockQuartz.HorizonCode_Horizon_È var2 = (BlockQuartz.HorizonCode_Horizon_È)p_178132_1_.HorizonCode_Horizon_È(BlockQuartz.Õ);
                switch (BlockModelShapes.HorizonCode_Horizon_È.HorizonCode_Horizon_È[var2.ordinal()]) {
                    default: {
                        return new ModelResourceLocation("quartz_block", "normal");
                    }
                    case 2: {
                        return new ModelResourceLocation("chiseled_quartz_block", "normal");
                    }
                    case 3: {
                        return new ModelResourceLocation("quartz_column", "axis=y");
                    }
                    case 4: {
                        return new ModelResourceLocation("quartz_column", "axis=x");
                    }
                    case 5: {
                        return new ModelResourceLocation("quartz_column", "axis=z");
                    }
                }
            }
        });
        this.HorizonCode_Horizon_È(Blocks.á€, new StateMapperBase() {
            private static final String Ý = "CL_00002527";
            
            @Override
            protected ModelResourceLocation HorizonCode_Horizon_È(final IBlockState p_178132_1_) {
                return new ModelResourceLocation("dead_bush", "normal");
            }
        });
        this.HorizonCode_Horizon_È(Blocks.ÇªÉ, new StateMapperBase() {
            private static final String Ý = "CL_00002526";
            
            @Override
            protected ModelResourceLocation HorizonCode_Horizon_È(final IBlockState p_178132_1_) {
                final LinkedHashMap var2 = Maps.newLinkedHashMap((Map)p_178132_1_.Â());
                if (p_178132_1_.HorizonCode_Horizon_È(BlockStem.à¢) != EnumFacing.Â) {
                    var2.remove(BlockStem.Õ);
                }
                return new ModelResourceLocation((ResourceLocation_1975012498)Block.HorizonCode_Horizon_È.Â(p_178132_1_.Ý()), this.HorizonCode_Horizon_È(var2));
            }
        });
        this.HorizonCode_Horizon_È(Blocks.ŠÏ­áˆºá, new StateMapperBase() {
            private static final String Ý = "CL_00002525";
            
            @Override
            protected ModelResourceLocation HorizonCode_Horizon_È(final IBlockState p_178132_1_) {
                final LinkedHashMap var2 = Maps.newLinkedHashMap((Map)p_178132_1_.Â());
                if (p_178132_1_.HorizonCode_Horizon_È(BlockStem.à¢) != EnumFacing.Â) {
                    var2.remove(BlockStem.Õ);
                }
                return new ModelResourceLocation((ResourceLocation_1975012498)Block.HorizonCode_Horizon_È.Â(p_178132_1_.Ý()), this.HorizonCode_Horizon_È(var2));
            }
        });
        this.HorizonCode_Horizon_È(Blocks.Âµá€, new StateMapperBase() {
            private static final String Ý = "CL_00002524";
            
            @Override
            protected ModelResourceLocation HorizonCode_Horizon_È(final IBlockState p_178132_1_) {
                final LinkedHashMap var2 = Maps.newLinkedHashMap((Map)p_178132_1_.Â());
                final String var3 = BlockDirt.Õ.HorizonCode_Horizon_È((Comparable)var2.remove(BlockDirt.Õ));
                if (BlockDirt.HorizonCode_Horizon_È.Ý != p_178132_1_.HorizonCode_Horizon_È(BlockDirt.Õ)) {
                    var2.remove(BlockDirt.à¢);
                }
                return new ModelResourceLocation(var3, this.HorizonCode_Horizon_È(var2));
            }
        });
        this.HorizonCode_Horizon_È(Blocks.£ÂµÄ, new StateMapperBase() {
            private static final String Ý = "CL_00002523";
            
            @Override
            protected ModelResourceLocation HorizonCode_Horizon_È(final IBlockState p_178132_1_) {
                final LinkedHashMap var2 = Maps.newLinkedHashMap((Map)p_178132_1_.Â());
                final String var3 = BlockStoneSlab.ŠÂµà.HorizonCode_Horizon_È((Comparable)var2.remove(BlockStoneSlab.ŠÂµà));
                var2.remove(BlockStoneSlab.à¢);
                final String var4 = p_178132_1_.HorizonCode_Horizon_È(BlockStoneSlab.à¢) ? "all" : "normal";
                return new ModelResourceLocation(String.valueOf(var3) + "_double_slab", var4);
            }
        });
        this.HorizonCode_Horizon_È(Blocks.ÇªØ, new StateMapperBase() {
            private static final String Ý = "CL_00002522";
            
            @Override
            protected ModelResourceLocation HorizonCode_Horizon_È(final IBlockState p_178132_1_) {
                final LinkedHashMap var2 = Maps.newLinkedHashMap((Map)p_178132_1_.Â());
                final String var3 = BlockStoneSlabNew.ŠÂµà.HorizonCode_Horizon_È((Comparable)var2.remove(BlockStoneSlabNew.ŠÂµà));
                var2.remove(BlockStoneSlab.à¢);
                final String var4 = p_178132_1_.HorizonCode_Horizon_È(BlockStoneSlabNew.à¢) ? "all" : "normal";
                return new ModelResourceLocation(String.valueOf(var3) + "_double_slab", var4);
            }
        });
    }
    
    static final class HorizonCode_Horizon_È
    {
        static final int[] HorizonCode_Horizon_È;
        private static final String Â = "CL_00002521";
        
        static {
            HorizonCode_Horizon_È = new int[BlockQuartz.HorizonCode_Horizon_È.values().length];
            try {
                BlockModelShapes.HorizonCode_Horizon_È.HorizonCode_Horizon_È[BlockQuartz.HorizonCode_Horizon_È.HorizonCode_Horizon_È.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                BlockModelShapes.HorizonCode_Horizon_È.HorizonCode_Horizon_È[BlockQuartz.HorizonCode_Horizon_È.Â.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
            try {
                BlockModelShapes.HorizonCode_Horizon_È.HorizonCode_Horizon_È[BlockQuartz.HorizonCode_Horizon_È.Ý.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError3) {}
            try {
                BlockModelShapes.HorizonCode_Horizon_È.HorizonCode_Horizon_È[BlockQuartz.HorizonCode_Horizon_È.Ø­áŒŠá.ordinal()] = 4;
            }
            catch (NoSuchFieldError noSuchFieldError4) {}
            try {
                BlockModelShapes.HorizonCode_Horizon_È.HorizonCode_Horizon_È[BlockQuartz.HorizonCode_Horizon_È.Âµá€.ordinal()] = 5;
            }
            catch (NoSuchFieldError noSuchFieldError5) {}
        }
    }
}
