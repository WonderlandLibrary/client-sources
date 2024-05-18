/*     */ package net.minecraft.client.renderer;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.Map;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockBed;
/*     */ import net.minecraft.block.BlockCactus;
/*     */ import net.minecraft.block.BlockColored;
/*     */ import net.minecraft.block.BlockCommandBlock;
/*     */ import net.minecraft.block.BlockDirt;
/*     */ import net.minecraft.block.BlockDispenser;
/*     */ import net.minecraft.block.BlockDoor;
/*     */ import net.minecraft.block.BlockDoublePlant;
/*     */ import net.minecraft.block.BlockDropper;
/*     */ import net.minecraft.block.BlockFenceGate;
/*     */ import net.minecraft.block.BlockFire;
/*     */ import net.minecraft.block.BlockFlowerPot;
/*     */ import net.minecraft.block.BlockHopper;
/*     */ import net.minecraft.block.BlockJukebox;
/*     */ import net.minecraft.block.BlockLeaves;
/*     */ import net.minecraft.block.BlockNewLeaf;
/*     */ import net.minecraft.block.BlockNewLog;
/*     */ import net.minecraft.block.BlockOldLeaf;
/*     */ import net.minecraft.block.BlockOldLog;
/*     */ import net.minecraft.block.BlockPlanks;
/*     */ import net.minecraft.block.BlockPrismarine;
/*     */ import net.minecraft.block.BlockQuartz;
/*     */ import net.minecraft.block.BlockRedSandstone;
/*     */ import net.minecraft.block.BlockRedstoneWire;
/*     */ import net.minecraft.block.BlockReed;
/*     */ import net.minecraft.block.BlockSand;
/*     */ import net.minecraft.block.BlockSandStone;
/*     */ import net.minecraft.block.BlockSapling;
/*     */ import net.minecraft.block.BlockSilverfish;
/*     */ import net.minecraft.block.BlockStem;
/*     */ import net.minecraft.block.BlockStone;
/*     */ import net.minecraft.block.BlockStoneBrick;
/*     */ import net.minecraft.block.BlockStoneSlab;
/*     */ import net.minecraft.block.BlockStoneSlabNew;
/*     */ import net.minecraft.block.BlockTNT;
/*     */ import net.minecraft.block.BlockTallGrass;
/*     */ import net.minecraft.block.BlockTripWire;
/*     */ import net.minecraft.block.BlockWall;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.renderer.block.statemap.BlockStateMapper;
/*     */ import net.minecraft.client.renderer.block.statemap.IStateMapper;
/*     */ import net.minecraft.client.renderer.block.statemap.StateMap;
/*     */ import net.minecraft.client.renderer.block.statemap.StateMapperBase;
/*     */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*     */ import net.minecraft.client.resources.model.IBakedModel;
/*     */ import net.minecraft.client.resources.model.ModelManager;
/*     */ import net.minecraft.client.resources.model.ModelResourceLocation;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ 
/*     */ 
/*     */ public class BlockModelShapes
/*     */ {
/*  61 */   private final Map<IBlockState, IBakedModel> bakedModelStore = Maps.newIdentityHashMap();
/*  62 */   private final BlockStateMapper blockStateMapper = new BlockStateMapper();
/*     */   
/*     */   private final ModelManager modelManager;
/*     */   
/*     */   public BlockModelShapes(ModelManager manager) {
/*  67 */     this.modelManager = manager;
/*  68 */     registerAllBlocks();
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockStateMapper getBlockStateMapper() {
/*  73 */     return this.blockStateMapper;
/*     */   }
/*     */ 
/*     */   
/*     */   public TextureAtlasSprite getTexture(IBlockState state) {
/*  78 */     Block block = state.getBlock();
/*  79 */     IBakedModel ibakedmodel = getModelForState(state);
/*     */     
/*  81 */     if (ibakedmodel == null || ibakedmodel == this.modelManager.getMissingModel()) {
/*     */       
/*  83 */       if (block == Blocks.wall_sign || block == Blocks.standing_sign || block == Blocks.chest || block == Blocks.trapped_chest || block == Blocks.standing_banner || block == Blocks.wall_banner)
/*     */       {
/*  85 */         return this.modelManager.getTextureMap().getAtlasSprite("minecraft:blocks/planks_oak");
/*     */       }
/*     */       
/*  88 */       if (block == Blocks.ender_chest)
/*     */       {
/*  90 */         return this.modelManager.getTextureMap().getAtlasSprite("minecraft:blocks/obsidian");
/*     */       }
/*     */       
/*  93 */       if (block == Blocks.flowing_lava || block == Blocks.lava)
/*     */       {
/*  95 */         return this.modelManager.getTextureMap().getAtlasSprite("minecraft:blocks/lava_still");
/*     */       }
/*     */       
/*  98 */       if (block == Blocks.flowing_water || block == Blocks.water)
/*     */       {
/* 100 */         return this.modelManager.getTextureMap().getAtlasSprite("minecraft:blocks/water_still");
/*     */       }
/*     */       
/* 103 */       if (block == Blocks.skull)
/*     */       {
/* 105 */         return this.modelManager.getTextureMap().getAtlasSprite("minecraft:blocks/soul_sand");
/*     */       }
/*     */       
/* 108 */       if (block == Blocks.barrier)
/*     */       {
/* 110 */         return this.modelManager.getTextureMap().getAtlasSprite("minecraft:items/barrier");
/*     */       }
/*     */     } 
/*     */     
/* 114 */     if (ibakedmodel == null)
/*     */     {
/* 116 */       ibakedmodel = this.modelManager.getMissingModel();
/*     */     }
/*     */     
/* 119 */     return ibakedmodel.getParticleTexture();
/*     */   }
/*     */ 
/*     */   
/*     */   public IBakedModel getModelForState(IBlockState state) {
/* 124 */     IBakedModel ibakedmodel = this.bakedModelStore.get(state);
/*     */     
/* 126 */     if (ibakedmodel == null)
/*     */     {
/* 128 */       ibakedmodel = this.modelManager.getMissingModel();
/*     */     }
/*     */     
/* 131 */     return ibakedmodel;
/*     */   }
/*     */ 
/*     */   
/*     */   public ModelManager getModelManager() {
/* 136 */     return this.modelManager;
/*     */   }
/*     */ 
/*     */   
/*     */   public void reloadModels() {
/* 141 */     this.bakedModelStore.clear();
/*     */     
/* 143 */     for (Map.Entry<IBlockState, ModelResourceLocation> entry : (Iterable<Map.Entry<IBlockState, ModelResourceLocation>>)this.blockStateMapper.putAllStateModelLocations().entrySet())
/*     */     {
/* 145 */       this.bakedModelStore.put(entry.getKey(), this.modelManager.getModel(entry.getValue()));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void registerBlockWithStateMapper(Block assoc, IStateMapper stateMapper) {
/* 151 */     this.blockStateMapper.registerBlockStateMapper(assoc, stateMapper);
/*     */   }
/*     */ 
/*     */   
/*     */   public void registerBuiltInBlocks(Block... builtIns) {
/* 156 */     this.blockStateMapper.registerBuiltInBlocks(builtIns);
/*     */   }
/*     */ 
/*     */   
/*     */   private void registerAllBlocks() {
/* 161 */     registerBuiltInBlocks(new Block[] { Blocks.air, (Block)Blocks.flowing_water, (Block)Blocks.water, (Block)Blocks.flowing_lava, (Block)Blocks.lava, (Block)Blocks.piston_extension, (Block)Blocks.chest, Blocks.ender_chest, Blocks.trapped_chest, Blocks.standing_sign, (Block)Blocks.skull, Blocks.end_portal, Blocks.barrier, Blocks.wall_sign, Blocks.wall_banner, Blocks.standing_banner });
/* 162 */     registerBlockWithStateMapper(Blocks.stone, (IStateMapper)(new StateMap.Builder()).withName((IProperty)BlockStone.VARIANT).build());
/* 163 */     registerBlockWithStateMapper(Blocks.prismarine, (IStateMapper)(new StateMap.Builder()).withName((IProperty)BlockPrismarine.VARIANT).build());
/* 164 */     registerBlockWithStateMapper((Block)Blocks.leaves, (IStateMapper)(new StateMap.Builder()).withName((IProperty)BlockOldLeaf.VARIANT).withSuffix("_leaves").ignore(new IProperty[] { (IProperty)BlockLeaves.CHECK_DECAY, (IProperty)BlockLeaves.DECAYABLE }).build());
/* 165 */     registerBlockWithStateMapper((Block)Blocks.leaves2, (IStateMapper)(new StateMap.Builder()).withName((IProperty)BlockNewLeaf.VARIANT).withSuffix("_leaves").ignore(new IProperty[] { (IProperty)BlockLeaves.CHECK_DECAY, (IProperty)BlockLeaves.DECAYABLE }).build());
/* 166 */     registerBlockWithStateMapper((Block)Blocks.cactus, (IStateMapper)(new StateMap.Builder()).ignore(new IProperty[] { (IProperty)BlockCactus.AGE }).build());
/* 167 */     registerBlockWithStateMapper((Block)Blocks.reeds, (IStateMapper)(new StateMap.Builder()).ignore(new IProperty[] { (IProperty)BlockReed.AGE }).build());
/* 168 */     registerBlockWithStateMapper(Blocks.jukebox, (IStateMapper)(new StateMap.Builder()).ignore(new IProperty[] { (IProperty)BlockJukebox.HAS_RECORD }).build());
/* 169 */     registerBlockWithStateMapper(Blocks.command_block, (IStateMapper)(new StateMap.Builder()).ignore(new IProperty[] { (IProperty)BlockCommandBlock.TRIGGERED }).build());
/* 170 */     registerBlockWithStateMapper(Blocks.cobblestone_wall, (IStateMapper)(new StateMap.Builder()).withName((IProperty)BlockWall.VARIANT).withSuffix("_wall").build());
/* 171 */     registerBlockWithStateMapper((Block)Blocks.double_plant, (IStateMapper)(new StateMap.Builder()).withName((IProperty)BlockDoublePlant.VARIANT).ignore(new IProperty[] { (IProperty)BlockDoublePlant.field_181084_N }).build());
/* 172 */     registerBlockWithStateMapper(Blocks.oak_fence_gate, (IStateMapper)(new StateMap.Builder()).ignore(new IProperty[] { (IProperty)BlockFenceGate.POWERED }).build());
/* 173 */     registerBlockWithStateMapper(Blocks.spruce_fence_gate, (IStateMapper)(new StateMap.Builder()).ignore(new IProperty[] { (IProperty)BlockFenceGate.POWERED }).build());
/* 174 */     registerBlockWithStateMapper(Blocks.birch_fence_gate, (IStateMapper)(new StateMap.Builder()).ignore(new IProperty[] { (IProperty)BlockFenceGate.POWERED }).build());
/* 175 */     registerBlockWithStateMapper(Blocks.jungle_fence_gate, (IStateMapper)(new StateMap.Builder()).ignore(new IProperty[] { (IProperty)BlockFenceGate.POWERED }).build());
/* 176 */     registerBlockWithStateMapper(Blocks.dark_oak_fence_gate, (IStateMapper)(new StateMap.Builder()).ignore(new IProperty[] { (IProperty)BlockFenceGate.POWERED }).build());
/* 177 */     registerBlockWithStateMapper(Blocks.acacia_fence_gate, (IStateMapper)(new StateMap.Builder()).ignore(new IProperty[] { (IProperty)BlockFenceGate.POWERED }).build());
/* 178 */     registerBlockWithStateMapper(Blocks.tripwire, (IStateMapper)(new StateMap.Builder()).ignore(new IProperty[] { (IProperty)BlockTripWire.DISARMED, (IProperty)BlockTripWire.POWERED }).build());
/* 179 */     registerBlockWithStateMapper((Block)Blocks.double_wooden_slab, (IStateMapper)(new StateMap.Builder()).withName((IProperty)BlockPlanks.VARIANT).withSuffix("_double_slab").build());
/* 180 */     registerBlockWithStateMapper((Block)Blocks.wooden_slab, (IStateMapper)(new StateMap.Builder()).withName((IProperty)BlockPlanks.VARIANT).withSuffix("_slab").build());
/* 181 */     registerBlockWithStateMapper(Blocks.tnt, (IStateMapper)(new StateMap.Builder()).ignore(new IProperty[] { (IProperty)BlockTNT.EXPLODE }).build());
/* 182 */     registerBlockWithStateMapper((Block)Blocks.fire, (IStateMapper)(new StateMap.Builder()).ignore(new IProperty[] { (IProperty)BlockFire.AGE }).build());
/* 183 */     registerBlockWithStateMapper((Block)Blocks.redstone_wire, (IStateMapper)(new StateMap.Builder()).ignore(new IProperty[] { (IProperty)BlockRedstoneWire.POWER }).build());
/* 184 */     registerBlockWithStateMapper(Blocks.oak_door, (IStateMapper)(new StateMap.Builder()).ignore(new IProperty[] { (IProperty)BlockDoor.POWERED }).build());
/* 185 */     registerBlockWithStateMapper(Blocks.spruce_door, (IStateMapper)(new StateMap.Builder()).ignore(new IProperty[] { (IProperty)BlockDoor.POWERED }).build());
/* 186 */     registerBlockWithStateMapper(Blocks.birch_door, (IStateMapper)(new StateMap.Builder()).ignore(new IProperty[] { (IProperty)BlockDoor.POWERED }).build());
/* 187 */     registerBlockWithStateMapper(Blocks.jungle_door, (IStateMapper)(new StateMap.Builder()).ignore(new IProperty[] { (IProperty)BlockDoor.POWERED }).build());
/* 188 */     registerBlockWithStateMapper(Blocks.acacia_door, (IStateMapper)(new StateMap.Builder()).ignore(new IProperty[] { (IProperty)BlockDoor.POWERED }).build());
/* 189 */     registerBlockWithStateMapper(Blocks.dark_oak_door, (IStateMapper)(new StateMap.Builder()).ignore(new IProperty[] { (IProperty)BlockDoor.POWERED }).build());
/* 190 */     registerBlockWithStateMapper(Blocks.iron_door, (IStateMapper)(new StateMap.Builder()).ignore(new IProperty[] { (IProperty)BlockDoor.POWERED }).build());
/* 191 */     registerBlockWithStateMapper(Blocks.wool, (IStateMapper)(new StateMap.Builder()).withName((IProperty)BlockColored.COLOR).withSuffix("_wool").build());
/* 192 */     registerBlockWithStateMapper(Blocks.carpet, (IStateMapper)(new StateMap.Builder()).withName((IProperty)BlockColored.COLOR).withSuffix("_carpet").build());
/* 193 */     registerBlockWithStateMapper(Blocks.stained_hardened_clay, (IStateMapper)(new StateMap.Builder()).withName((IProperty)BlockColored.COLOR).withSuffix("_stained_hardened_clay").build());
/* 194 */     registerBlockWithStateMapper((Block)Blocks.stained_glass_pane, (IStateMapper)(new StateMap.Builder()).withName((IProperty)BlockColored.COLOR).withSuffix("_stained_glass_pane").build());
/* 195 */     registerBlockWithStateMapper((Block)Blocks.stained_glass, (IStateMapper)(new StateMap.Builder()).withName((IProperty)BlockColored.COLOR).withSuffix("_stained_glass").build());
/* 196 */     registerBlockWithStateMapper(Blocks.sandstone, (IStateMapper)(new StateMap.Builder()).withName((IProperty)BlockSandStone.TYPE).build());
/* 197 */     registerBlockWithStateMapper(Blocks.red_sandstone, (IStateMapper)(new StateMap.Builder()).withName((IProperty)BlockRedSandstone.TYPE).build());
/* 198 */     registerBlockWithStateMapper((Block)Blocks.tallgrass, (IStateMapper)(new StateMap.Builder()).withName((IProperty)BlockTallGrass.TYPE).build());
/* 199 */     registerBlockWithStateMapper(Blocks.bed, (IStateMapper)(new StateMap.Builder()).ignore(new IProperty[] { (IProperty)BlockBed.OCCUPIED }).build());
/* 200 */     registerBlockWithStateMapper((Block)Blocks.yellow_flower, (IStateMapper)(new StateMap.Builder()).withName(Blocks.yellow_flower.getTypeProperty()).build());
/* 201 */     registerBlockWithStateMapper((Block)Blocks.red_flower, (IStateMapper)(new StateMap.Builder()).withName(Blocks.red_flower.getTypeProperty()).build());
/* 202 */     registerBlockWithStateMapper((Block)Blocks.stone_slab, (IStateMapper)(new StateMap.Builder()).withName((IProperty)BlockStoneSlab.VARIANT).withSuffix("_slab").build());
/* 203 */     registerBlockWithStateMapper((Block)Blocks.stone_slab2, (IStateMapper)(new StateMap.Builder()).withName((IProperty)BlockStoneSlabNew.VARIANT).withSuffix("_slab").build());
/* 204 */     registerBlockWithStateMapper(Blocks.monster_egg, (IStateMapper)(new StateMap.Builder()).withName((IProperty)BlockSilverfish.VARIANT).withSuffix("_monster_egg").build());
/* 205 */     registerBlockWithStateMapper(Blocks.stonebrick, (IStateMapper)(new StateMap.Builder()).withName((IProperty)BlockStoneBrick.VARIANT).build());
/* 206 */     registerBlockWithStateMapper(Blocks.dispenser, (IStateMapper)(new StateMap.Builder()).ignore(new IProperty[] { (IProperty)BlockDispenser.TRIGGERED }).build());
/* 207 */     registerBlockWithStateMapper(Blocks.dropper, (IStateMapper)(new StateMap.Builder()).ignore(new IProperty[] { (IProperty)BlockDropper.TRIGGERED }).build());
/* 208 */     registerBlockWithStateMapper(Blocks.log, (IStateMapper)(new StateMap.Builder()).withName((IProperty)BlockOldLog.VARIANT).withSuffix("_log").build());
/* 209 */     registerBlockWithStateMapper(Blocks.log2, (IStateMapper)(new StateMap.Builder()).withName((IProperty)BlockNewLog.VARIANT).withSuffix("_log").build());
/* 210 */     registerBlockWithStateMapper(Blocks.planks, (IStateMapper)(new StateMap.Builder()).withName((IProperty)BlockPlanks.VARIANT).withSuffix("_planks").build());
/* 211 */     registerBlockWithStateMapper(Blocks.sapling, (IStateMapper)(new StateMap.Builder()).withName((IProperty)BlockSapling.TYPE).withSuffix("_sapling").build());
/* 212 */     registerBlockWithStateMapper((Block)Blocks.sand, (IStateMapper)(new StateMap.Builder()).withName((IProperty)BlockSand.VARIANT).build());
/* 213 */     registerBlockWithStateMapper((Block)Blocks.hopper, (IStateMapper)(new StateMap.Builder()).ignore(new IProperty[] { (IProperty)BlockHopper.ENABLED }).build());
/* 214 */     registerBlockWithStateMapper(Blocks.flower_pot, (IStateMapper)(new StateMap.Builder()).ignore(new IProperty[] { (IProperty)BlockFlowerPot.LEGACY_DATA }).build());
/* 215 */     registerBlockWithStateMapper(Blocks.quartz_block, (IStateMapper)new StateMapperBase()
/*     */         {
/*     */           protected ModelResourceLocation getModelResourceLocation(IBlockState state)
/*     */           {
/* 219 */             BlockQuartz.EnumType blockquartz$enumtype = (BlockQuartz.EnumType)state.getValue((IProperty)BlockQuartz.VARIANT);
/*     */             
/* 221 */             switch (blockquartz$enumtype) {
/*     */ 
/*     */               
/*     */               default:
/* 225 */                 return new ModelResourceLocation("quartz_block", "normal");
/*     */               
/*     */               case null:
/* 228 */                 return new ModelResourceLocation("chiseled_quartz_block", "normal");
/*     */               
/*     */               case LINES_Y:
/* 231 */                 return new ModelResourceLocation("quartz_column", "axis=y");
/*     */               
/*     */               case LINES_X:
/* 234 */                 return new ModelResourceLocation("quartz_column", "axis=x");
/*     */               case LINES_Z:
/*     */                 break;
/* 237 */             }  return new ModelResourceLocation("quartz_column", "axis=z");
/*     */           }
/*     */         });
/*     */     
/* 241 */     registerBlockWithStateMapper((Block)Blocks.deadbush, (IStateMapper)new StateMapperBase()
/*     */         {
/*     */           protected ModelResourceLocation getModelResourceLocation(IBlockState state)
/*     */           {
/* 245 */             return new ModelResourceLocation("dead_bush", "normal");
/*     */           }
/*     */         });
/* 248 */     registerBlockWithStateMapper(Blocks.pumpkin_stem, (IStateMapper)new StateMapperBase()
/*     */         {
/*     */           protected ModelResourceLocation getModelResourceLocation(IBlockState state)
/*     */           {
/* 252 */             Map<IProperty, Comparable> map = Maps.newLinkedHashMap((Map)state.getProperties());
/*     */             
/* 254 */             if (state.getValue((IProperty)BlockStem.FACING) != EnumFacing.UP)
/*     */             {
/* 256 */               map.remove(BlockStem.AGE);
/*     */             }
/*     */             
/* 259 */             return new ModelResourceLocation((ResourceLocation)Block.blockRegistry.getNameForObject(state.getBlock()), getPropertyString(map));
/*     */           }
/*     */         });
/* 262 */     registerBlockWithStateMapper(Blocks.melon_stem, (IStateMapper)new StateMapperBase()
/*     */         {
/*     */           protected ModelResourceLocation getModelResourceLocation(IBlockState state)
/*     */           {
/* 266 */             Map<IProperty, Comparable> map = Maps.newLinkedHashMap((Map)state.getProperties());
/*     */             
/* 268 */             if (state.getValue((IProperty)BlockStem.FACING) != EnumFacing.UP)
/*     */             {
/* 270 */               map.remove(BlockStem.AGE);
/*     */             }
/*     */             
/* 273 */             return new ModelResourceLocation((ResourceLocation)Block.blockRegistry.getNameForObject(state.getBlock()), getPropertyString(map));
/*     */           }
/*     */         });
/* 276 */     registerBlockWithStateMapper(Blocks.dirt, (IStateMapper)new StateMapperBase()
/*     */         {
/*     */           protected ModelResourceLocation getModelResourceLocation(IBlockState state)
/*     */           {
/* 280 */             Map<IProperty, Comparable> map = Maps.newLinkedHashMap((Map)state.getProperties());
/* 281 */             String s = BlockDirt.VARIANT.getName((Enum)map.remove(BlockDirt.VARIANT));
/*     */             
/* 283 */             if (BlockDirt.DirtType.PODZOL != state.getValue((IProperty)BlockDirt.VARIANT))
/*     */             {
/* 285 */               map.remove(BlockDirt.SNOWY);
/*     */             }
/*     */             
/* 288 */             return new ModelResourceLocation(s, getPropertyString(map));
/*     */           }
/*     */         });
/* 291 */     registerBlockWithStateMapper((Block)Blocks.double_stone_slab, (IStateMapper)new StateMapperBase()
/*     */         {
/*     */           protected ModelResourceLocation getModelResourceLocation(IBlockState state)
/*     */           {
/* 295 */             Map<IProperty, Comparable> map = Maps.newLinkedHashMap((Map)state.getProperties());
/* 296 */             String s = BlockStoneSlab.VARIANT.getName((Enum)map.remove(BlockStoneSlab.VARIANT));
/* 297 */             map.remove(BlockStoneSlab.SEAMLESS);
/* 298 */             String s1 = ((Boolean)state.getValue((IProperty)BlockStoneSlab.SEAMLESS)).booleanValue() ? "all" : "normal";
/* 299 */             return new ModelResourceLocation(String.valueOf(s) + "_double_slab", s1);
/*     */           }
/*     */         });
/* 302 */     registerBlockWithStateMapper((Block)Blocks.double_stone_slab2, (IStateMapper)new StateMapperBase()
/*     */         {
/*     */           protected ModelResourceLocation getModelResourceLocation(IBlockState state)
/*     */           {
/* 306 */             Map<IProperty, Comparable> map = Maps.newLinkedHashMap((Map)state.getProperties());
/* 307 */             String s = BlockStoneSlabNew.VARIANT.getName((Enum)map.remove(BlockStoneSlabNew.VARIANT));
/* 308 */             map.remove(BlockStoneSlab.SEAMLESS);
/* 309 */             String s1 = ((Boolean)state.getValue((IProperty)BlockStoneSlabNew.SEAMLESS)).booleanValue() ? "all" : "normal";
/* 310 */             return new ModelResourceLocation(String.valueOf(s) + "_double_slab", s1);
/*     */           }
/*     */         });
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\client\renderer\BlockModelShapes.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */