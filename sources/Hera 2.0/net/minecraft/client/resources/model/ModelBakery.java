/*     */ package net.minecraft.client.resources.model;
/*     */ 
/*     */ import com.google.common.base.Charsets;
/*     */ import com.google.common.base.Joiner;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.google.common.collect.Queues;
/*     */ import com.google.common.collect.Sets;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.Reader;
/*     */ import java.io.StringReader;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.Deque;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.client.renderer.BlockModelShapes;
/*     */ import net.minecraft.client.renderer.block.model.BakedQuad;
/*     */ import net.minecraft.client.renderer.block.model.BlockPart;
/*     */ import net.minecraft.client.renderer.block.model.BlockPartFace;
/*     */ import net.minecraft.client.renderer.block.model.FaceBakery;
/*     */ import net.minecraft.client.renderer.block.model.ItemModelGenerator;
/*     */ import net.minecraft.client.renderer.block.model.ModelBlock;
/*     */ import net.minecraft.client.renderer.block.model.ModelBlockDefinition;
/*     */ import net.minecraft.client.renderer.texture.IIconCreator;
/*     */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*     */ import net.minecraft.client.renderer.texture.TextureMap;
/*     */ import net.minecraft.client.resources.IResource;
/*     */ import net.minecraft.client.resources.IResourceManager;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.IRegistry;
/*     */ import net.minecraft.util.RegistrySimple;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import org.apache.commons.io.IOUtils;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class ModelBakery
/*     */ {
/*  49 */   private static final Set<ResourceLocation> LOCATIONS_BUILTIN_TEXTURES = Sets.newHashSet((Object[])new ResourceLocation[] { new ResourceLocation("blocks/water_flow"), new ResourceLocation("blocks/water_still"), new ResourceLocation("blocks/lava_flow"), new ResourceLocation("blocks/lava_still"), new ResourceLocation("blocks/destroy_stage_0"), new ResourceLocation("blocks/destroy_stage_1"), new ResourceLocation("blocks/destroy_stage_2"), new ResourceLocation("blocks/destroy_stage_3"), new ResourceLocation("blocks/destroy_stage_4"), new ResourceLocation("blocks/destroy_stage_5"), new ResourceLocation("blocks/destroy_stage_6"), new ResourceLocation("blocks/destroy_stage_7"), new ResourceLocation("blocks/destroy_stage_8"), new ResourceLocation("blocks/destroy_stage_9"), new ResourceLocation("items/empty_armor_slot_helmet"), new ResourceLocation("items/empty_armor_slot_chestplate"), new ResourceLocation("items/empty_armor_slot_leggings"), new ResourceLocation("items/empty_armor_slot_boots") });
/*  50 */   private static final Logger LOGGER = LogManager.getLogger();
/*  51 */   protected static final ModelResourceLocation MODEL_MISSING = new ModelResourceLocation("builtin/missing", "missing");
/*  52 */   private static final Map<String, String> BUILT_IN_MODELS = Maps.newHashMap();
/*  53 */   private static final Joiner JOINER = Joiner.on(" -> ");
/*     */   private final IResourceManager resourceManager;
/*  55 */   private final Map<ResourceLocation, TextureAtlasSprite> sprites = Maps.newHashMap();
/*  56 */   private final Map<ResourceLocation, ModelBlock> models = Maps.newLinkedHashMap();
/*  57 */   private final Map<ModelResourceLocation, ModelBlockDefinition.Variants> variants = Maps.newLinkedHashMap();
/*     */   private final TextureMap textureMap;
/*     */   private final BlockModelShapes blockModelShapes;
/*  60 */   private final FaceBakery faceBakery = new FaceBakery();
/*  61 */   private final ItemModelGenerator itemModelGenerator = new ItemModelGenerator();
/*  62 */   private RegistrySimple<ModelResourceLocation, IBakedModel> bakedRegistry = new RegistrySimple();
/*  63 */   private static final ModelBlock MODEL_GENERATED = ModelBlock.deserialize("{\"elements\":[{  \"from\": [0, 0, 0],   \"to\": [16, 16, 16],   \"faces\": {       \"down\": {\"uv\": [0, 0, 16, 16], \"texture\":\"\"}   }}]}");
/*  64 */   private static final ModelBlock MODEL_COMPASS = ModelBlock.deserialize("{\"elements\":[{  \"from\": [0, 0, 0],   \"to\": [16, 16, 16],   \"faces\": {       \"down\": {\"uv\": [0, 0, 16, 16], \"texture\":\"\"}   }}]}");
/*  65 */   private static final ModelBlock MODEL_CLOCK = ModelBlock.deserialize("{\"elements\":[{  \"from\": [0, 0, 0],   \"to\": [16, 16, 16],   \"faces\": {       \"down\": {\"uv\": [0, 0, 16, 16], \"texture\":\"\"}   }}]}");
/*  66 */   private static final ModelBlock MODEL_ENTITY = ModelBlock.deserialize("{\"elements\":[{  \"from\": [0, 0, 0],   \"to\": [16, 16, 16],   \"faces\": {       \"down\": {\"uv\": [0, 0, 16, 16], \"texture\":\"\"}   }}]}");
/*  67 */   private Map<String, ResourceLocation> itemLocations = Maps.newLinkedHashMap();
/*  68 */   private final Map<ResourceLocation, ModelBlockDefinition> blockDefinitions = Maps.newHashMap();
/*  69 */   private Map<Item, List<String>> variantNames = Maps.newIdentityHashMap();
/*     */ 
/*     */   
/*     */   public ModelBakery(IResourceManager p_i46085_1_, TextureMap p_i46085_2_, BlockModelShapes p_i46085_3_) {
/*  73 */     this.resourceManager = p_i46085_1_;
/*  74 */     this.textureMap = p_i46085_2_;
/*  75 */     this.blockModelShapes = p_i46085_3_;
/*     */   }
/*     */ 
/*     */   
/*     */   public IRegistry<ModelResourceLocation, IBakedModel> setupModelRegistry() {
/*  80 */     loadVariantItemModels();
/*  81 */     loadModelsCheck();
/*  82 */     loadSprites();
/*  83 */     bakeItemModels();
/*  84 */     bakeBlockModels();
/*  85 */     return (IRegistry<ModelResourceLocation, IBakedModel>)this.bakedRegistry;
/*     */   }
/*     */ 
/*     */   
/*     */   private void loadVariantItemModels() {
/*  90 */     loadVariants(this.blockModelShapes.getBlockStateMapper().putAllStateModelLocations().values());
/*  91 */     this.variants.put(MODEL_MISSING, new ModelBlockDefinition.Variants(MODEL_MISSING.getVariant(), Lists.newArrayList((Object[])new ModelBlockDefinition.Variant[] { new ModelBlockDefinition.Variant(new ResourceLocation(MODEL_MISSING.getResourcePath()), ModelRotation.X0_Y0, false, 1) })));
/*  92 */     ResourceLocation resourcelocation = new ResourceLocation("item_frame");
/*  93 */     ModelBlockDefinition modelblockdefinition = getModelBlockDefinition(resourcelocation);
/*  94 */     registerVariant(modelblockdefinition, new ModelResourceLocation(resourcelocation, "normal"));
/*  95 */     registerVariant(modelblockdefinition, new ModelResourceLocation(resourcelocation, "map"));
/*  96 */     loadVariantModels();
/*  97 */     loadItemModels();
/*     */   }
/*     */ 
/*     */   
/*     */   private void loadVariants(Collection<ModelResourceLocation> p_177591_1_) {
/* 102 */     for (ModelResourceLocation modelresourcelocation : p_177591_1_) {
/*     */ 
/*     */       
/*     */       try {
/* 106 */         ModelBlockDefinition modelblockdefinition = getModelBlockDefinition(modelresourcelocation);
/*     */ 
/*     */         
/*     */         try {
/* 110 */           registerVariant(modelblockdefinition, modelresourcelocation);
/*     */         }
/* 112 */         catch (Exception var6) {
/*     */           
/* 114 */           LOGGER.warn("Unable to load variant: " + modelresourcelocation.getVariant() + " from " + modelresourcelocation);
/*     */         }
/*     */       
/* 117 */       } catch (Exception exception) {
/*     */         
/* 119 */         LOGGER.warn("Unable to load definition " + modelresourcelocation, exception);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void registerVariant(ModelBlockDefinition p_177569_1_, ModelResourceLocation p_177569_2_) {
/* 126 */     this.variants.put(p_177569_2_, p_177569_1_.getVariants(p_177569_2_.getVariant()));
/*     */   }
/*     */ 
/*     */   
/*     */   private ModelBlockDefinition getModelBlockDefinition(ResourceLocation p_177586_1_) {
/* 131 */     ResourceLocation resourcelocation = getBlockStateLocation(p_177586_1_);
/* 132 */     ModelBlockDefinition modelblockdefinition = this.blockDefinitions.get(resourcelocation);
/*     */     
/* 134 */     if (modelblockdefinition == null) {
/*     */       
/* 136 */       List<ModelBlockDefinition> list = Lists.newArrayList();
/*     */ 
/*     */       
/*     */       try {
/* 140 */         for (IResource iresource : this.resourceManager.getAllResources(resourcelocation)) {
/*     */           
/* 142 */           InputStream inputstream = null;
/*     */ 
/*     */           
/*     */           try {
/* 146 */             inputstream = iresource.getInputStream();
/* 147 */             ModelBlockDefinition modelblockdefinition1 = ModelBlockDefinition.parseFromReader(new InputStreamReader(inputstream, Charsets.UTF_8));
/* 148 */             list.add(modelblockdefinition1);
/*     */           }
/* 150 */           catch (Exception exception) {
/*     */             
/* 152 */             throw new RuntimeException("Encountered an exception when loading model definition of '" + p_177586_1_ + "' from: '" + iresource.getResourceLocation() + "' in resourcepack: '" + iresource.getResourcePackName() + "'", exception);
/*     */           }
/*     */           finally {
/*     */             
/* 156 */             IOUtils.closeQuietly(inputstream);
/*     */           }
/*     */         
/*     */         } 
/* 160 */       } catch (IOException ioexception) {
/*     */         
/* 162 */         throw new RuntimeException("Encountered an exception when loading model definition of model " + resourcelocation.toString(), ioexception);
/*     */       } 
/*     */       
/* 165 */       modelblockdefinition = new ModelBlockDefinition(list);
/* 166 */       this.blockDefinitions.put(resourcelocation, modelblockdefinition);
/*     */     } 
/*     */     
/* 169 */     return modelblockdefinition;
/*     */   }
/*     */ 
/*     */   
/*     */   private ResourceLocation getBlockStateLocation(ResourceLocation p_177584_1_) {
/* 174 */     return new ResourceLocation(p_177584_1_.getResourceDomain(), "blockstates/" + p_177584_1_.getResourcePath() + ".json");
/*     */   }
/*     */ 
/*     */   
/*     */   private void loadVariantModels() {
/* 179 */     for (ModelResourceLocation modelresourcelocation : this.variants.keySet()) {
/*     */       
/* 181 */       for (ModelBlockDefinition.Variant modelblockdefinition$variant : ((ModelBlockDefinition.Variants)this.variants.get(modelresourcelocation)).getVariants()) {
/*     */         
/* 183 */         ResourceLocation resourcelocation = modelblockdefinition$variant.getModelLocation();
/*     */         
/* 185 */         if (this.models.get(resourcelocation) == null)
/*     */           
/*     */           try {
/*     */             
/* 189 */             ModelBlock modelblock = loadModel(resourcelocation);
/* 190 */             this.models.put(resourcelocation, modelblock);
/*     */           }
/* 192 */           catch (Exception exception) {
/*     */             
/* 194 */             LOGGER.warn("Unable to load block model: '" + resourcelocation + "' for variant: '" + modelresourcelocation + "'", exception);
/*     */           }  
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private ModelBlock loadModel(ResourceLocation p_177594_1_) throws IOException {
/*     */     Reader reader;
/*     */     ModelBlock modelblock1;
/* 203 */     String s = p_177594_1_.getResourcePath();
/*     */     
/* 205 */     if ("builtin/generated".equals(s))
/*     */     {
/* 207 */       return MODEL_GENERATED;
/*     */     }
/* 209 */     if ("builtin/compass".equals(s))
/*     */     {
/* 211 */       return MODEL_COMPASS;
/*     */     }
/* 213 */     if ("builtin/clock".equals(s))
/*     */     {
/* 215 */       return MODEL_CLOCK;
/*     */     }
/* 217 */     if ("builtin/entity".equals(s))
/*     */     {
/* 219 */       return MODEL_ENTITY;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 225 */     if (s.startsWith("builtin/")) {
/*     */       
/* 227 */       String s1 = s.substring("builtin/".length());
/* 228 */       String s2 = BUILT_IN_MODELS.get(s1);
/*     */       
/* 230 */       if (s2 == null)
/*     */       {
/* 232 */         throw new FileNotFoundException(p_177594_1_.toString());
/*     */       }
/*     */       
/* 235 */       reader = new StringReader(s2);
/*     */     }
/*     */     else {
/*     */       
/* 239 */       IResource iresource = this.resourceManager.getResource(getModelLocation(p_177594_1_));
/* 240 */       reader = new InputStreamReader(iresource.getInputStream(), Charsets.UTF_8);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 247 */       ModelBlock modelblock = ModelBlock.deserialize(reader);
/* 248 */       modelblock.name = p_177594_1_.toString();
/* 249 */       modelblock1 = modelblock;
/*     */     }
/*     */     finally {
/*     */       
/* 253 */       reader.close();
/*     */     } 
/*     */     
/* 256 */     return modelblock1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private ResourceLocation getModelLocation(ResourceLocation p_177580_1_) {
/* 262 */     return new ResourceLocation(p_177580_1_.getResourceDomain(), "models/" + p_177580_1_.getResourcePath() + ".json");
/*     */   }
/*     */ 
/*     */   
/*     */   private void loadItemModels() {
/* 267 */     registerVariantNames();
/*     */     
/* 269 */     for (Item item : Item.itemRegistry) {
/*     */       
/* 271 */       for (String s : getVariantNames(item)) {
/*     */         
/* 273 */         ResourceLocation resourcelocation = getItemLocation(s);
/* 274 */         this.itemLocations.put(s, resourcelocation);
/*     */         
/* 276 */         if (this.models.get(resourcelocation) == null) {
/*     */           
/*     */           try {
/*     */             
/* 280 */             ModelBlock modelblock = loadModel(resourcelocation);
/* 281 */             this.models.put(resourcelocation, modelblock);
/*     */           }
/* 283 */           catch (Exception exception) {
/*     */             
/* 285 */             LOGGER.warn("Unable to load item model: '" + resourcelocation + "' for item: '" + Item.itemRegistry.getNameForObject(item) + "'", exception);
/*     */           } 
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void registerVariantNames() {
/* 294 */     this.variantNames.put(Item.getItemFromBlock(Blocks.stone), Lists.newArrayList((Object[])new String[] { "stone", "granite", "granite_smooth", "diorite", "diorite_smooth", "andesite", "andesite_smooth" }));
/* 295 */     this.variantNames.put(Item.getItemFromBlock(Blocks.dirt), Lists.newArrayList((Object[])new String[] { "dirt", "coarse_dirt", "podzol" }));
/* 296 */     this.variantNames.put(Item.getItemFromBlock(Blocks.planks), Lists.newArrayList((Object[])new String[] { "oak_planks", "spruce_planks", "birch_planks", "jungle_planks", "acacia_planks", "dark_oak_planks" }));
/* 297 */     this.variantNames.put(Item.getItemFromBlock(Blocks.sapling), Lists.newArrayList((Object[])new String[] { "oak_sapling", "spruce_sapling", "birch_sapling", "jungle_sapling", "acacia_sapling", "dark_oak_sapling" }));
/* 298 */     this.variantNames.put(Item.getItemFromBlock((Block)Blocks.sand), Lists.newArrayList((Object[])new String[] { "sand", "red_sand" }));
/* 299 */     this.variantNames.put(Item.getItemFromBlock(Blocks.log), Lists.newArrayList((Object[])new String[] { "oak_log", "spruce_log", "birch_log", "jungle_log" }));
/* 300 */     this.variantNames.put(Item.getItemFromBlock((Block)Blocks.leaves), Lists.newArrayList((Object[])new String[] { "oak_leaves", "spruce_leaves", "birch_leaves", "jungle_leaves" }));
/* 301 */     this.variantNames.put(Item.getItemFromBlock(Blocks.sponge), Lists.newArrayList((Object[])new String[] { "sponge", "sponge_wet" }));
/* 302 */     this.variantNames.put(Item.getItemFromBlock(Blocks.sandstone), Lists.newArrayList((Object[])new String[] { "sandstone", "chiseled_sandstone", "smooth_sandstone" }));
/* 303 */     this.variantNames.put(Item.getItemFromBlock(Blocks.red_sandstone), Lists.newArrayList((Object[])new String[] { "red_sandstone", "chiseled_red_sandstone", "smooth_red_sandstone" }));
/* 304 */     this.variantNames.put(Item.getItemFromBlock((Block)Blocks.tallgrass), Lists.newArrayList((Object[])new String[] { "dead_bush", "tall_grass", "fern" }));
/* 305 */     this.variantNames.put(Item.getItemFromBlock((Block)Blocks.deadbush), Lists.newArrayList((Object[])new String[] { "dead_bush" }));
/* 306 */     this.variantNames.put(Item.getItemFromBlock(Blocks.wool), Lists.newArrayList((Object[])new String[] { "black_wool", "red_wool", "green_wool", "brown_wool", "blue_wool", "purple_wool", "cyan_wool", "silver_wool", "gray_wool", "pink_wool", "lime_wool", "yellow_wool", "light_blue_wool", "magenta_wool", "orange_wool", "white_wool" }));
/* 307 */     this.variantNames.put(Item.getItemFromBlock((Block)Blocks.yellow_flower), Lists.newArrayList((Object[])new String[] { "dandelion" }));
/* 308 */     this.variantNames.put(Item.getItemFromBlock((Block)Blocks.red_flower), Lists.newArrayList((Object[])new String[] { "poppy", "blue_orchid", "allium", "houstonia", "red_tulip", "orange_tulip", "white_tulip", "pink_tulip", "oxeye_daisy" }));
/* 309 */     this.variantNames.put(Item.getItemFromBlock((Block)Blocks.stone_slab), Lists.newArrayList((Object[])new String[] { "stone_slab", "sandstone_slab", "cobblestone_slab", "brick_slab", "stone_brick_slab", "nether_brick_slab", "quartz_slab" }));
/* 310 */     this.variantNames.put(Item.getItemFromBlock((Block)Blocks.stone_slab2), Lists.newArrayList((Object[])new String[] { "red_sandstone_slab" }));
/* 311 */     this.variantNames.put(Item.getItemFromBlock((Block)Blocks.stained_glass), Lists.newArrayList((Object[])new String[] { "black_stained_glass", "red_stained_glass", "green_stained_glass", "brown_stained_glass", "blue_stained_glass", "purple_stained_glass", "cyan_stained_glass", "silver_stained_glass", "gray_stained_glass", "pink_stained_glass", "lime_stained_glass", "yellow_stained_glass", "light_blue_stained_glass", "magenta_stained_glass", "orange_stained_glass", "white_stained_glass" }));
/* 312 */     this.variantNames.put(Item.getItemFromBlock(Blocks.monster_egg), Lists.newArrayList((Object[])new String[] { "stone_monster_egg", "cobblestone_monster_egg", "stone_brick_monster_egg", "mossy_brick_monster_egg", "cracked_brick_monster_egg", "chiseled_brick_monster_egg" }));
/* 313 */     this.variantNames.put(Item.getItemFromBlock(Blocks.stonebrick), Lists.newArrayList((Object[])new String[] { "stonebrick", "mossy_stonebrick", "cracked_stonebrick", "chiseled_stonebrick" }));
/* 314 */     this.variantNames.put(Item.getItemFromBlock((Block)Blocks.wooden_slab), Lists.newArrayList((Object[])new String[] { "oak_slab", "spruce_slab", "birch_slab", "jungle_slab", "acacia_slab", "dark_oak_slab" }));
/* 315 */     this.variantNames.put(Item.getItemFromBlock(Blocks.cobblestone_wall), Lists.newArrayList((Object[])new String[] { "cobblestone_wall", "mossy_cobblestone_wall" }));
/* 316 */     this.variantNames.put(Item.getItemFromBlock(Blocks.anvil), Lists.newArrayList((Object[])new String[] { "anvil_intact", "anvil_slightly_damaged", "anvil_very_damaged" }));
/* 317 */     this.variantNames.put(Item.getItemFromBlock(Blocks.quartz_block), Lists.newArrayList((Object[])new String[] { "quartz_block", "chiseled_quartz_block", "quartz_column" }));
/* 318 */     this.variantNames.put(Item.getItemFromBlock(Blocks.stained_hardened_clay), Lists.newArrayList((Object[])new String[] { "black_stained_hardened_clay", "red_stained_hardened_clay", "green_stained_hardened_clay", "brown_stained_hardened_clay", "blue_stained_hardened_clay", "purple_stained_hardened_clay", "cyan_stained_hardened_clay", "silver_stained_hardened_clay", "gray_stained_hardened_clay", "pink_stained_hardened_clay", "lime_stained_hardened_clay", "yellow_stained_hardened_clay", "light_blue_stained_hardened_clay", "magenta_stained_hardened_clay", "orange_stained_hardened_clay", "white_stained_hardened_clay" }));
/* 319 */     this.variantNames.put(Item.getItemFromBlock((Block)Blocks.stained_glass_pane), Lists.newArrayList((Object[])new String[] { "black_stained_glass_pane", "red_stained_glass_pane", "green_stained_glass_pane", "brown_stained_glass_pane", "blue_stained_glass_pane", "purple_stained_glass_pane", "cyan_stained_glass_pane", "silver_stained_glass_pane", "gray_stained_glass_pane", "pink_stained_glass_pane", "lime_stained_glass_pane", "yellow_stained_glass_pane", "light_blue_stained_glass_pane", "magenta_stained_glass_pane", "orange_stained_glass_pane", "white_stained_glass_pane" }));
/* 320 */     this.variantNames.put(Item.getItemFromBlock((Block)Blocks.leaves2), Lists.newArrayList((Object[])new String[] { "acacia_leaves", "dark_oak_leaves" }));
/* 321 */     this.variantNames.put(Item.getItemFromBlock(Blocks.log2), Lists.newArrayList((Object[])new String[] { "acacia_log", "dark_oak_log" }));
/* 322 */     this.variantNames.put(Item.getItemFromBlock(Blocks.prismarine), Lists.newArrayList((Object[])new String[] { "prismarine", "prismarine_bricks", "dark_prismarine" }));
/* 323 */     this.variantNames.put(Item.getItemFromBlock(Blocks.carpet), Lists.newArrayList((Object[])new String[] { "black_carpet", "red_carpet", "green_carpet", "brown_carpet", "blue_carpet", "purple_carpet", "cyan_carpet", "silver_carpet", "gray_carpet", "pink_carpet", "lime_carpet", "yellow_carpet", "light_blue_carpet", "magenta_carpet", "orange_carpet", "white_carpet" }));
/* 324 */     this.variantNames.put(Item.getItemFromBlock((Block)Blocks.double_plant), Lists.newArrayList((Object[])new String[] { "sunflower", "syringa", "double_grass", "double_fern", "double_rose", "paeonia" }));
/* 325 */     this.variantNames.put(Items.bow, Lists.newArrayList((Object[])new String[] { "bow", "bow_pulling_0", "bow_pulling_1", "bow_pulling_2" }));
/* 326 */     this.variantNames.put(Items.coal, Lists.newArrayList((Object[])new String[] { "coal", "charcoal" }));
/* 327 */     this.variantNames.put(Items.fishing_rod, Lists.newArrayList((Object[])new String[] { "fishing_rod", "fishing_rod_cast" }));
/* 328 */     this.variantNames.put(Items.fish, Lists.newArrayList((Object[])new String[] { "cod", "salmon", "clownfish", "pufferfish" }));
/* 329 */     this.variantNames.put(Items.cooked_fish, Lists.newArrayList((Object[])new String[] { "cooked_cod", "cooked_salmon" }));
/* 330 */     this.variantNames.put(Items.dye, Lists.newArrayList((Object[])new String[] { "dye_black", "dye_red", "dye_green", "dye_brown", "dye_blue", "dye_purple", "dye_cyan", "dye_silver", "dye_gray", "dye_pink", "dye_lime", "dye_yellow", "dye_light_blue", "dye_magenta", "dye_orange", "dye_white" }));
/* 331 */     this.variantNames.put(Items.potionitem, Lists.newArrayList((Object[])new String[] { "bottle_drinkable", "bottle_splash" }));
/* 332 */     this.variantNames.put(Items.skull, Lists.newArrayList((Object[])new String[] { "skull_skeleton", "skull_wither", "skull_zombie", "skull_char", "skull_creeper" }));
/* 333 */     this.variantNames.put(Item.getItemFromBlock(Blocks.oak_fence_gate), Lists.newArrayList((Object[])new String[] { "oak_fence_gate" }));
/* 334 */     this.variantNames.put(Item.getItemFromBlock(Blocks.oak_fence), Lists.newArrayList((Object[])new String[] { "oak_fence" }));
/* 335 */     this.variantNames.put(Items.oak_door, Lists.newArrayList((Object[])new String[] { "oak_door" }));
/*     */   }
/*     */ 
/*     */   
/*     */   private List<String> getVariantNames(Item p_177596_1_) {
/* 340 */     List<String> list = this.variantNames.get(p_177596_1_);
/*     */     
/* 342 */     if (list == null)
/*     */     {
/* 344 */       list = Collections.singletonList(((ResourceLocation)Item.itemRegistry.getNameForObject(p_177596_1_)).toString());
/*     */     }
/*     */     
/* 347 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   private ResourceLocation getItemLocation(String p_177583_1_) {
/* 352 */     ResourceLocation resourcelocation = new ResourceLocation(p_177583_1_);
/* 353 */     return new ResourceLocation(resourcelocation.getResourceDomain(), "item/" + resourcelocation.getResourcePath());
/*     */   }
/*     */ 
/*     */   
/*     */   private void bakeBlockModels() {
/* 358 */     for (ModelResourceLocation modelresourcelocation : this.variants.keySet()) {
/*     */       
/* 360 */       WeightedBakedModel.Builder weightedbakedmodel$builder = new WeightedBakedModel.Builder();
/* 361 */       int i = 0;
/*     */       
/* 363 */       for (ModelBlockDefinition.Variant modelblockdefinition$variant : ((ModelBlockDefinition.Variants)this.variants.get(modelresourcelocation)).getVariants()) {
/*     */         
/* 365 */         ModelBlock modelblock = this.models.get(modelblockdefinition$variant.getModelLocation());
/*     */         
/* 367 */         if (modelblock != null && modelblock.isResolved()) {
/*     */           
/* 369 */           i++;
/* 370 */           weightedbakedmodel$builder.add(bakeModel(modelblock, modelblockdefinition$variant.getRotation(), modelblockdefinition$variant.isUvLocked()), modelblockdefinition$variant.getWeight());
/*     */           
/*     */           continue;
/*     */         } 
/* 374 */         LOGGER.warn("Missing model for: " + modelresourcelocation);
/*     */       } 
/*     */ 
/*     */       
/* 378 */       if (i == 0) {
/*     */         
/* 380 */         LOGGER.warn("No weighted models for: " + modelresourcelocation); continue;
/*     */       } 
/* 382 */       if (i == 1) {
/*     */         
/* 384 */         this.bakedRegistry.putObject(modelresourcelocation, weightedbakedmodel$builder.first());
/*     */         
/*     */         continue;
/*     */       } 
/* 388 */       this.bakedRegistry.putObject(modelresourcelocation, weightedbakedmodel$builder.build());
/*     */     } 
/*     */ 
/*     */     
/* 392 */     for (Map.Entry<String, ResourceLocation> entry : this.itemLocations.entrySet()) {
/*     */       
/* 394 */       ResourceLocation resourcelocation = entry.getValue();
/* 395 */       ModelResourceLocation modelresourcelocation1 = new ModelResourceLocation(entry.getKey(), "inventory");
/* 396 */       ModelBlock modelblock1 = this.models.get(resourcelocation);
/*     */       
/* 398 */       if (modelblock1 != null && modelblock1.isResolved()) {
/*     */         
/* 400 */         if (isCustomRenderer(modelblock1)) {
/*     */           
/* 402 */           this.bakedRegistry.putObject(modelresourcelocation1, new BuiltInModel(modelblock1.func_181682_g()));
/*     */           
/*     */           continue;
/*     */         } 
/* 406 */         this.bakedRegistry.putObject(modelresourcelocation1, bakeModel(modelblock1, ModelRotation.X0_Y0, false));
/*     */         
/*     */         continue;
/*     */       } 
/*     */       
/* 411 */       LOGGER.warn("Missing model for: " + resourcelocation);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private Set<ResourceLocation> getVariantsTextureLocations() {
/* 418 */     Set<ResourceLocation> set = Sets.newHashSet();
/* 419 */     List<ModelResourceLocation> list = Lists.newArrayList(this.variants.keySet());
/* 420 */     Collections.sort(list, new Comparator<ModelResourceLocation>()
/*     */         {
/*     */           public int compare(ModelResourceLocation p_compare_1_, ModelResourceLocation p_compare_2_)
/*     */           {
/* 424 */             return p_compare_1_.toString().compareTo(p_compare_2_.toString());
/*     */           }
/*     */         });
/*     */     
/* 428 */     for (ModelResourceLocation modelresourcelocation : list) {
/*     */       
/* 430 */       ModelBlockDefinition.Variants modelblockdefinition$variants = this.variants.get(modelresourcelocation);
/*     */       
/* 432 */       for (ModelBlockDefinition.Variant modelblockdefinition$variant : modelblockdefinition$variants.getVariants()) {
/*     */         
/* 434 */         ModelBlock modelblock = this.models.get(modelblockdefinition$variant.getModelLocation());
/*     */         
/* 436 */         if (modelblock == null) {
/*     */           
/* 438 */           LOGGER.warn("Missing model for: " + modelresourcelocation);
/*     */           
/*     */           continue;
/*     */         } 
/* 442 */         set.addAll(getTextureLocations(modelblock));
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 447 */     set.addAll(LOCATIONS_BUILTIN_TEXTURES);
/* 448 */     return set;
/*     */   }
/*     */ 
/*     */   
/*     */   private IBakedModel bakeModel(ModelBlock modelBlockIn, ModelRotation modelRotationIn, boolean uvLocked) {
/* 453 */     TextureAtlasSprite textureatlassprite = this.sprites.get(new ResourceLocation(modelBlockIn.resolveTextureName("particle")));
/* 454 */     SimpleBakedModel.Builder simplebakedmodel$builder = (new SimpleBakedModel.Builder(modelBlockIn)).setTexture(textureatlassprite);
/*     */     
/* 456 */     for (BlockPart blockpart : modelBlockIn.getElements()) {
/*     */       
/* 458 */       for (EnumFacing enumfacing : blockpart.mapFaces.keySet()) {
/*     */         
/* 460 */         BlockPartFace blockpartface = (BlockPartFace)blockpart.mapFaces.get(enumfacing);
/* 461 */         TextureAtlasSprite textureatlassprite1 = this.sprites.get(new ResourceLocation(modelBlockIn.resolveTextureName(blockpartface.texture)));
/*     */         
/* 463 */         if (blockpartface.cullFace == null) {
/*     */           
/* 465 */           simplebakedmodel$builder.addGeneralQuad(makeBakedQuad(blockpart, blockpartface, textureatlassprite1, enumfacing, modelRotationIn, uvLocked));
/*     */           
/*     */           continue;
/*     */         } 
/* 469 */         simplebakedmodel$builder.addFaceQuad(modelRotationIn.rotateFace(blockpartface.cullFace), makeBakedQuad(blockpart, blockpartface, textureatlassprite1, enumfacing, modelRotationIn, uvLocked));
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 474 */     return simplebakedmodel$builder.makeBakedModel();
/*     */   }
/*     */ 
/*     */   
/*     */   private BakedQuad makeBakedQuad(BlockPart p_177589_1_, BlockPartFace p_177589_2_, TextureAtlasSprite p_177589_3_, EnumFacing p_177589_4_, ModelRotation p_177589_5_, boolean p_177589_6_) {
/* 479 */     return this.faceBakery.makeBakedQuad(p_177589_1_.positionFrom, p_177589_1_.positionTo, p_177589_2_, p_177589_3_, p_177589_4_, p_177589_5_, p_177589_1_.partRotation, p_177589_6_, p_177589_1_.shade);
/*     */   }
/*     */ 
/*     */   
/*     */   private void loadModelsCheck() {
/* 484 */     loadModels();
/*     */     
/* 486 */     for (ModelBlock modelblock : this.models.values())
/*     */     {
/* 488 */       modelblock.getParentFromMap(this.models);
/*     */     }
/*     */     
/* 491 */     ModelBlock.checkModelHierarchy(this.models);
/*     */   }
/*     */ 
/*     */   
/*     */   private void loadModels() {
/* 496 */     Deque<ResourceLocation> deque = Queues.newArrayDeque();
/* 497 */     Set<ResourceLocation> set = Sets.newHashSet();
/*     */     
/* 499 */     for (ResourceLocation resourcelocation : this.models.keySet()) {
/*     */       
/* 501 */       set.add(resourcelocation);
/* 502 */       ResourceLocation resourcelocation1 = ((ModelBlock)this.models.get(resourcelocation)).getParentLocation();
/*     */       
/* 504 */       if (resourcelocation1 != null)
/*     */       {
/* 506 */         deque.add(resourcelocation1);
/*     */       }
/*     */     } 
/*     */     
/* 510 */     while (!deque.isEmpty()) {
/*     */       
/* 512 */       ResourceLocation resourcelocation2 = deque.pop();
/*     */ 
/*     */       
/*     */       try {
/* 516 */         if (this.models.get(resourcelocation2) != null) {
/*     */           continue;
/*     */         }
/*     */ 
/*     */         
/* 521 */         ModelBlock modelblock = loadModel(resourcelocation2);
/* 522 */         this.models.put(resourcelocation2, modelblock);
/* 523 */         ResourceLocation resourcelocation3 = modelblock.getParentLocation();
/*     */         
/* 525 */         if (resourcelocation3 != null && !set.contains(resourcelocation3))
/*     */         {
/* 527 */           deque.add(resourcelocation3);
/*     */         }
/*     */       }
/* 530 */       catch (Exception exception) {
/*     */         
/* 532 */         LOGGER.warn("In parent chain: " + JOINER.join(getParentPath(resourcelocation2)) + "; unable to load model: '" + resourcelocation2 + "'", exception);
/*     */       } 
/*     */       
/* 535 */       set.add(resourcelocation2);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private List<ResourceLocation> getParentPath(ResourceLocation p_177573_1_) {
/* 541 */     List<ResourceLocation> list = Lists.newArrayList((Object[])new ResourceLocation[] { p_177573_1_ });
/* 542 */     ResourceLocation resourcelocation = p_177573_1_;
/*     */     
/* 544 */     while ((resourcelocation = getParentLocation(resourcelocation)) != null)
/*     */     {
/* 546 */       list.add(0, resourcelocation);
/*     */     }
/*     */     
/* 549 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   private ResourceLocation getParentLocation(ResourceLocation p_177576_1_) {
/* 554 */     for (Map.Entry<ResourceLocation, ModelBlock> entry : this.models.entrySet()) {
/*     */       
/* 556 */       ModelBlock modelblock = entry.getValue();
/*     */       
/* 558 */       if (modelblock != null && p_177576_1_.equals(modelblock.getParentLocation()))
/*     */       {
/* 560 */         return entry.getKey();
/*     */       }
/*     */     } 
/*     */     
/* 564 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   private Set<ResourceLocation> getTextureLocations(ModelBlock p_177585_1_) {
/* 569 */     Set<ResourceLocation> set = Sets.newHashSet();
/*     */     
/* 571 */     for (BlockPart blockpart : p_177585_1_.getElements()) {
/*     */       
/* 573 */       for (BlockPartFace blockpartface : blockpart.mapFaces.values()) {
/*     */         
/* 575 */         ResourceLocation resourcelocation = new ResourceLocation(p_177585_1_.resolveTextureName(blockpartface.texture));
/* 576 */         set.add(resourcelocation);
/*     */       } 
/*     */     } 
/*     */     
/* 580 */     set.add(new ResourceLocation(p_177585_1_.resolveTextureName("particle")));
/* 581 */     return set;
/*     */   }
/*     */ 
/*     */   
/*     */   private void loadSprites() {
/* 586 */     final Set<ResourceLocation> set = getVariantsTextureLocations();
/* 587 */     set.addAll(getItemsTextureLocations());
/* 588 */     set.remove(TextureMap.LOCATION_MISSING_TEXTURE);
/* 589 */     IIconCreator iiconcreator = new IIconCreator()
/*     */       {
/*     */         public void registerSprites(TextureMap iconRegistry)
/*     */         {
/* 593 */           for (ResourceLocation resourcelocation : set) {
/*     */             
/* 595 */             TextureAtlasSprite textureatlassprite = iconRegistry.registerSprite(resourcelocation);
/* 596 */             ModelBakery.this.sprites.put(resourcelocation, textureatlassprite);
/*     */           } 
/*     */         }
/*     */       };
/* 600 */     this.textureMap.loadSprites(this.resourceManager, iiconcreator);
/* 601 */     this.sprites.put(new ResourceLocation("missingno"), this.textureMap.getMissingSprite());
/*     */   }
/*     */ 
/*     */   
/*     */   private Set<ResourceLocation> getItemsTextureLocations() {
/* 606 */     Set<ResourceLocation> set = Sets.newHashSet();
/*     */     
/* 608 */     for (ResourceLocation resourcelocation : this.itemLocations.values()) {
/*     */       
/* 610 */       ModelBlock modelblock = this.models.get(resourcelocation);
/*     */       
/* 612 */       if (modelblock != null) {
/*     */         
/* 614 */         set.add(new ResourceLocation(modelblock.resolveTextureName("particle")));
/*     */         
/* 616 */         if (hasItemModel(modelblock)) {
/*     */           
/* 618 */           for (String s : ItemModelGenerator.LAYERS) {
/*     */             
/* 620 */             ResourceLocation resourcelocation2 = new ResourceLocation(modelblock.resolveTextureName(s));
/*     */             
/* 622 */             if (modelblock.getRootModel() == MODEL_COMPASS && !TextureMap.LOCATION_MISSING_TEXTURE.equals(resourcelocation2)) {
/*     */               
/* 624 */               TextureAtlasSprite.setLocationNameCompass(resourcelocation2.toString());
/*     */             }
/* 626 */             else if (modelblock.getRootModel() == MODEL_CLOCK && !TextureMap.LOCATION_MISSING_TEXTURE.equals(resourcelocation2)) {
/*     */               
/* 628 */               TextureAtlasSprite.setLocationNameClock(resourcelocation2.toString());
/*     */             } 
/*     */             
/* 631 */             set.add(resourcelocation2);
/*     */           }  continue;
/*     */         } 
/* 634 */         if (!isCustomRenderer(modelblock))
/*     */         {
/* 636 */           for (BlockPart blockpart : modelblock.getElements()) {
/*     */             
/* 638 */             for (BlockPartFace blockpartface : blockpart.mapFaces.values()) {
/*     */               
/* 640 */               ResourceLocation resourcelocation1 = new ResourceLocation(modelblock.resolveTextureName(blockpartface.texture));
/* 641 */               set.add(resourcelocation1);
/*     */             } 
/*     */           } 
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 648 */     return set;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean hasItemModel(ModelBlock p_177581_1_) {
/* 653 */     if (p_177581_1_ == null)
/*     */     {
/* 655 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 659 */     ModelBlock modelblock = p_177581_1_.getRootModel();
/* 660 */     return !(modelblock != MODEL_GENERATED && modelblock != MODEL_COMPASS && modelblock != MODEL_CLOCK);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isCustomRenderer(ModelBlock p_177587_1_) {
/* 666 */     if (p_177587_1_ == null)
/*     */     {
/* 668 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 672 */     ModelBlock modelblock = p_177587_1_.getRootModel();
/* 673 */     return (modelblock == MODEL_ENTITY);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void bakeItemModels() {
/* 679 */     for (ResourceLocation resourcelocation : this.itemLocations.values()) {
/*     */       
/* 681 */       ModelBlock modelblock = this.models.get(resourcelocation);
/*     */       
/* 683 */       if (hasItemModel(modelblock)) {
/*     */         
/* 685 */         ModelBlock modelblock1 = makeItemModel(modelblock);
/*     */         
/* 687 */         if (modelblock1 != null)
/*     */         {
/* 689 */           modelblock1.name = resourcelocation.toString();
/*     */         }
/*     */         
/* 692 */         this.models.put(resourcelocation, modelblock1); continue;
/*     */       } 
/* 694 */       if (isCustomRenderer(modelblock))
/*     */       {
/* 696 */         this.models.put(resourcelocation, modelblock);
/*     */       }
/*     */     } 
/*     */     
/* 700 */     for (TextureAtlasSprite textureatlassprite : this.sprites.values()) {
/*     */       
/* 702 */       if (!textureatlassprite.hasAnimationMetadata())
/*     */       {
/* 704 */         textureatlassprite.clearFramesTextureData();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private ModelBlock makeItemModel(ModelBlock p_177582_1_) {
/* 711 */     return this.itemModelGenerator.makeItemModel(this.textureMap, p_177582_1_);
/*     */   }
/*     */ 
/*     */   
/*     */   static {
/* 716 */     BUILT_IN_MODELS.put("missing", "{ \"textures\": {   \"particle\": \"missingno\",   \"missingno\": \"missingno\"}, \"elements\": [ {     \"from\": [ 0, 0, 0 ],     \"to\": [ 16, 16, 16 ],     \"faces\": {         \"down\":  { \"uv\": [ 0, 0, 16, 16 ], \"cullface\": \"down\", \"texture\": \"#missingno\" },         \"up\":    { \"uv\": [ 0, 0, 16, 16 ], \"cullface\": \"up\", \"texture\": \"#missingno\" },         \"north\": { \"uv\": [ 0, 0, 16, 16 ], \"cullface\": \"north\", \"texture\": \"#missingno\" },         \"south\": { \"uv\": [ 0, 0, 16, 16 ], \"cullface\": \"south\", \"texture\": \"#missingno\" },         \"west\":  { \"uv\": [ 0, 0, 16, 16 ], \"cullface\": \"west\", \"texture\": \"#missingno\" },         \"east\":  { \"uv\": [ 0, 0, 16, 16 ], \"cullface\": \"east\", \"texture\": \"#missingno\" }    }}]}");
/* 717 */     MODEL_GENERATED.name = "generation marker";
/* 718 */     MODEL_COMPASS.name = "compass generation marker";
/* 719 */     MODEL_CLOCK.name = "class generation marker";
/* 720 */     MODEL_ENTITY.name = "block entity marker";
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\client\resources\model\ModelBakery.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */