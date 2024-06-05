package net.minecraft.client.resources.model;

import com.google.common.base.Charsets;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Queues;
import com.google.common.collect.Sets;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.function.Function;
import net.aED;
import net.qj;
import net.minecraft.client.renderer.BlockModelShapes;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.BlockPart;
import net.minecraft.client.renderer.block.model.BlockPartFace;
import net.minecraft.client.renderer.block.model.FaceBakery;
import net.minecraft.client.renderer.block.model.ItemModelGenerator;
import net.minecraft.client.renderer.block.model.ModelBlock;
import net.minecraft.client.renderer.block.model.ModelBlockDefinition;
import net.minecraft.client.renderer.block.model.ModelBlockDefinition$Variants;
import net.minecraft.client.renderer.texture.IIconCreator;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.model.BuiltInModel;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.client.resources.model.ModelRotation;
import net.minecraft.client.resources.model.SimpleBakedModel$Builder;
import net.minecraft.client.resources.model.WeightedBakedModel$Builder;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IRegistry;
import net.minecraft.util.RegistrySimple;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ModelBakery {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final Set LOCATIONS_BUILTIN_TEXTURES = Sets.newHashSet(new ResourceLocation[]{new ResourceLocation("blocks/water_flow"), new ResourceLocation("blocks/water_still"), new ResourceLocation("blocks/lava_flow"), new ResourceLocation("blocks/lava_still"), new ResourceLocation("blocks/destroy_stage_0"), new ResourceLocation("blocks/destroy_stage_1"), new ResourceLocation("blocks/destroy_stage_2"), new ResourceLocation("blocks/destroy_stage_3"), new ResourceLocation("blocks/destroy_stage_4"), new ResourceLocation("blocks/destroy_stage_5"), new ResourceLocation("blocks/destroy_stage_6"), new ResourceLocation("blocks/destroy_stage_7"), new ResourceLocation("blocks/destroy_stage_8"), new ResourceLocation("blocks/destroy_stage_9"), new ResourceLocation("items/empty_armor_slot_helmet"), new ResourceLocation("items/empty_armor_slot_chestplate"), new ResourceLocation("items/empty_armor_slot_leggings"), new ResourceLocation("items/empty_armor_slot_boots")});
   protected static final ModelResourceLocation MODEL_MISSING = new ModelResourceLocation("builtin/missing", "missing");
   private static final Map BUILT_IN_MODELS = Maps.newHashMap();
   private static final Joiner JOINER = Joiner.on(" -> ");
   private final IResourceManager resourceManager;
   private final Map sprites = Maps.newHashMap();
   private final Map models = Maps.newLinkedHashMap();
   private final Map variants = Maps.newLinkedHashMap();
   private final TextureMap textureMap;
   private final BlockModelShapes blockModelShapes;
   private final FaceBakery faceBakery = new FaceBakery();
   private final ItemModelGenerator itemModelGenerator = new ItemModelGenerator();
   private final RegistrySimple bakedRegistry = new RegistrySimple();
   private static final ModelBlock MODEL_GENERATED = ModelBlock.deserialize("{\"elements\":[{  \"from\": [0, 0, 0],   \"to\": [16, 16, 16],   \"faces\": {       \"down\": {\"uv\": [0, 0, 16, 16], \"texture\":\"\"}   }}]}");
   private static final ModelBlock MODEL_COMPASS = ModelBlock.deserialize("{\"elements\":[{  \"from\": [0, 0, 0],   \"to\": [16, 16, 16],   \"faces\": {       \"down\": {\"uv\": [0, 0, 16, 16], \"texture\":\"\"}   }}]}");
   private static final ModelBlock MODEL_CLOCK = ModelBlock.deserialize("{\"elements\":[{  \"from\": [0, 0, 0],   \"to\": [16, 16, 16],   \"faces\": {       \"down\": {\"uv\": [0, 0, 16, 16], \"texture\":\"\"}   }}]}");
   private static final ModelBlock MODEL_ENTITY = ModelBlock.deserialize("{\"elements\":[{  \"from\": [0, 0, 0],   \"to\": [16, 16, 16],   \"faces\": {       \"down\": {\"uv\": [0, 0, 16, 16], \"texture\":\"\"}   }}]}");
   private final Map itemLocations = Maps.newLinkedHashMap();
   private final Map blockDefinitions = Maps.newHashMap();
   private final Map variantNames = Maps.newIdentityHashMap();

   public ModelBakery(IResourceManager var1, TextureMap var2, BlockModelShapes var3) {
      this.resourceManager = var1;
      this.textureMap = var2;
      this.blockModelShapes = var3;
   }

   public IRegistry setupModelRegistry() {
      this.loadVariantItemModels();
      this.loadModelsCheck();
      this.loadSprites();
      this.bakeItemModels();
      this.bakeBlockModels();
      return this.bakedRegistry;
   }

   private void loadVariantItemModels() {
      this.loadVariants(this.blockModelShapes.getBlockStateMapper().putAllStateModelLocations().values());
      this.variants.put(MODEL_MISSING, new ModelBlockDefinition$Variants(MODEL_MISSING.getVariant(), Lists.newArrayList(new qj[]{new qj(new ResourceLocation(MODEL_MISSING.getResourcePath()), ModelRotation.X0_Y0, false, 1)})));
      ResourceLocation var1 = new ResourceLocation("item_frame");
      ModelBlockDefinition var2 = this.getModelBlockDefinition(var1);
      this.registerVariant(var2, new ModelResourceLocation(var1, "normal"));
      this.registerVariant(var2, new ModelResourceLocation(var1, "map"));
      this.loadVariantModels();
      this.loadItemModels();
   }

   private void loadVariants(Collection var1) {
      for(ModelResourceLocation var3 : var1) {
         try {
            ModelBlockDefinition var4 = this.getModelBlockDefinition(var3);
            ModelBakery var10000 = this;
            ModelBlockDefinition var10001 = var4;
            ModelResourceLocation var10002 = var3;

            try {
               var10000.registerVariant(var10001, var10002);
            } catch (Exception var6) {
               LOGGER.warn("Unable to load variant: " + var3.getVariant() + " from " + var3);
            }
         } catch (Exception var7) {
            LOGGER.warn("Unable to load definition " + var3, var7);
         }
      }

   }

   private void registerVariant(ModelBlockDefinition var1, ModelResourceLocation var2) {
      this.variants.put(var2, var1.getVariants(var2.getVariant()));
   }

   private ModelBlockDefinition getModelBlockDefinition(ResourceLocation param1) {
      // $FF: Couldn't be decompiled
   }

   private ResourceLocation getBlockStateLocation(ResourceLocation var1) {
      return new ResourceLocation(var1.getResourceDomain(), "blockstates/" + var1.getResourcePath() + ".json");
   }

   private void loadVariantModels() {
      for(ModelResourceLocation var2 : this.variants.keySet()) {
         for(qj var4 : ((ModelBlockDefinition$Variants)this.variants.get(var2)).getVariants()) {
            ResourceLocation var5 = var4.c();
            if(this.models.get(var5) == null) {
               try {
                  ModelBlock var6 = this.loadModel(var5);
                  this.models.put(var5, var6);
               } catch (Exception var7) {
                  LOGGER.warn("Unable to load block model: \'" + var5 + "\' for variant: \'" + var2 + "\'", var7);
               }
            }
         }
      }

   }

   private ModelBlock loadModel(ResourceLocation var1) throws IOException {
      String var2 = var1.getResourcePath();
      if("builtin/generated".equals(var2)) {
         return MODEL_GENERATED;
      } else if("builtin/compass".equals(var2)) {
         return MODEL_COMPASS;
      } else if("builtin/clock".equals(var2)) {
         return MODEL_CLOCK;
      } else if("builtin/entity".equals(var2)) {
         return MODEL_ENTITY;
      } else if(var2.startsWith("builtin/")) {
         String var10 = var2.substring("builtin/".length());
         String var11 = (String)BUILT_IN_MODELS.get(var10);
         throw new FileNotFoundException(var1.toString());
      } else {
         IResource var4 = this.resourceManager.getResource(this.getModelLocation(var1));
         InputStreamReader var3 = new InputStreamReader(var4.getInputStream(), Charsets.UTF_8);
         InputStreamReader var10000 = var3;

         try {
            ModelBlock var5 = ModelBlock.deserialize((Reader)var10000);
            var5.name = var1.toString();
            var9 = var5;
         } finally {
            var3.close();
         }

         return var9;
      }
   }

   private ResourceLocation getModelLocation(ResourceLocation var1) {
      return new ResourceLocation(var1.getResourceDomain(), "models/" + var1.getResourcePath() + ".json");
   }

   private void loadItemModels() {
      this.registerVariantNames();

      for(Item var2 : Item.itemRegistry) {
         for(String var4 : this.getVariantNames(var2)) {
            ResourceLocation var5 = this.getItemLocation(var4);
            this.itemLocations.put(var4, var5);
            if(this.models.get(var5) == null) {
               try {
                  ModelBlock var6 = this.loadModel(var5);
                  this.models.put(var5, var6);
               } catch (Exception var7) {
                  LOGGER.warn("Unable to load item model: \'" + var5 + "\' for item: \'" + Item.itemRegistry.getNameForObject(var2) + "\'", var7);
               }
            }
         }
      }

   }

   private void registerVariantNames() {
      this.variantNames.put(Item.getItemFromBlock(Blocks.stone), Lists.newArrayList(new String[]{"stone", "granite", "granite_smooth", "diorite", "diorite_smooth", "andesite", "andesite_smooth"}));
      this.variantNames.put(Item.getItemFromBlock(Blocks.dirt), Lists.newArrayList(new String[]{"dirt", "coarse_dirt", "podzol"}));
      this.variantNames.put(Item.getItemFromBlock(Blocks.planks), Lists.newArrayList(new String[]{"oak_planks", "spruce_planks", "birch_planks", "jungle_planks", "acacia_planks", "dark_oak_planks"}));
      this.variantNames.put(Item.getItemFromBlock(Blocks.sapling), Lists.newArrayList(new String[]{"oak_sapling", "spruce_sapling", "birch_sapling", "jungle_sapling", "acacia_sapling", "dark_oak_sapling"}));
      this.variantNames.put(Item.getItemFromBlock(Blocks.sand), Lists.newArrayList(new String[]{"sand", "red_sand"}));
      this.variantNames.put(Item.getItemFromBlock(Blocks.log), Lists.newArrayList(new String[]{"oak_log", "spruce_log", "birch_log", "jungle_log"}));
      this.variantNames.put(Item.getItemFromBlock(Blocks.leaves), Lists.newArrayList(new String[]{"oak_leaves", "spruce_leaves", "birch_leaves", "jungle_leaves"}));
      this.variantNames.put(Item.getItemFromBlock(Blocks.sponge), Lists.newArrayList(new String[]{"sponge", "sponge_wet"}));
      this.variantNames.put(Item.getItemFromBlock(Blocks.sandstone), Lists.newArrayList(new String[]{"sandstone", "chiseled_sandstone", "smooth_sandstone"}));
      this.variantNames.put(Item.getItemFromBlock(Blocks.red_sandstone), Lists.newArrayList(new String[]{"red_sandstone", "chiseled_red_sandstone", "smooth_red_sandstone"}));
      this.variantNames.put(Item.getItemFromBlock(Blocks.tallgrass), Lists.newArrayList(new String[]{"dead_bush", "tall_grass", "fern"}));
      this.variantNames.put(Item.getItemFromBlock(Blocks.deadbush), Lists.newArrayList(new String[]{"dead_bush"}));
      this.variantNames.put(Item.getItemFromBlock(Blocks.wool), Lists.newArrayList(new String[]{"black_wool", "red_wool", "green_wool", "brown_wool", "blue_wool", "purple_wool", "cyan_wool", "silver_wool", "gray_wool", "pink_wool", "lime_wool", "yellow_wool", "light_blue_wool", "magenta_wool", "orange_wool", "white_wool"}));
      this.variantNames.put(Item.getItemFromBlock(Blocks.yellow_flower), Lists.newArrayList(new String[]{"dandelion"}));
      this.variantNames.put(Item.getItemFromBlock(Blocks.red_flower), Lists.newArrayList(new String[]{"poppy", "blue_orchid", "allium", "houstonia", "red_tulip", "orange_tulip", "white_tulip", "pink_tulip", "oxeye_daisy"}));
      this.variantNames.put(Item.getItemFromBlock(Blocks.stone_slab), Lists.newArrayList(new String[]{"stone_slab", "sandstone_slab", "cobblestone_slab", "brick_slab", "stone_brick_slab", "nether_brick_slab", "quartz_slab"}));
      this.variantNames.put(Item.getItemFromBlock(Blocks.stone_slab2), Lists.newArrayList(new String[]{"red_sandstone_slab"}));
      this.variantNames.put(Item.getItemFromBlock(Blocks.stained_glass), Lists.newArrayList(new String[]{"black_stained_glass", "red_stained_glass", "green_stained_glass", "brown_stained_glass", "blue_stained_glass", "purple_stained_glass", "cyan_stained_glass", "silver_stained_glass", "gray_stained_glass", "pink_stained_glass", "lime_stained_glass", "yellow_stained_glass", "light_blue_stained_glass", "magenta_stained_glass", "orange_stained_glass", "white_stained_glass"}));
      this.variantNames.put(Item.getItemFromBlock(Blocks.monster_egg), Lists.newArrayList(new String[]{"stone_monster_egg", "cobblestone_monster_egg", "stone_brick_monster_egg", "mossy_brick_monster_egg", "cracked_brick_monster_egg", "chiseled_brick_monster_egg"}));
      this.variantNames.put(Item.getItemFromBlock(Blocks.stonebrick), Lists.newArrayList(new String[]{"stonebrick", "mossy_stonebrick", "cracked_stonebrick", "chiseled_stonebrick"}));
      this.variantNames.put(Item.getItemFromBlock(Blocks.wooden_slab), Lists.newArrayList(new String[]{"oak_slab", "spruce_slab", "birch_slab", "jungle_slab", "acacia_slab", "dark_oak_slab"}));
      this.variantNames.put(Item.getItemFromBlock(Blocks.cobblestone_wall), Lists.newArrayList(new String[]{"cobblestone_wall", "mossy_cobblestone_wall"}));
      this.variantNames.put(Item.getItemFromBlock(Blocks.anvil), Lists.newArrayList(new String[]{"anvil_intact", "anvil_slightly_damaged", "anvil_very_damaged"}));
      this.variantNames.put(Item.getItemFromBlock(Blocks.quartz_block), Lists.newArrayList(new String[]{"quartz_block", "chiseled_quartz_block", "quartz_column"}));
      this.variantNames.put(Item.getItemFromBlock(Blocks.stained_hardened_clay), Lists.newArrayList(new String[]{"black_stained_hardened_clay", "red_stained_hardened_clay", "green_stained_hardened_clay", "brown_stained_hardened_clay", "blue_stained_hardened_clay", "purple_stained_hardened_clay", "cyan_stained_hardened_clay", "silver_stained_hardened_clay", "gray_stained_hardened_clay", "pink_stained_hardened_clay", "lime_stained_hardened_clay", "yellow_stained_hardened_clay", "light_blue_stained_hardened_clay", "magenta_stained_hardened_clay", "orange_stained_hardened_clay", "white_stained_hardened_clay"}));
      this.variantNames.put(Item.getItemFromBlock(Blocks.stained_glass_pane), Lists.newArrayList(new String[]{"black_stained_glass_pane", "red_stained_glass_pane", "green_stained_glass_pane", "brown_stained_glass_pane", "blue_stained_glass_pane", "purple_stained_glass_pane", "cyan_stained_glass_pane", "silver_stained_glass_pane", "gray_stained_glass_pane", "pink_stained_glass_pane", "lime_stained_glass_pane", "yellow_stained_glass_pane", "light_blue_stained_glass_pane", "magenta_stained_glass_pane", "orange_stained_glass_pane", "white_stained_glass_pane"}));
      this.variantNames.put(Item.getItemFromBlock(Blocks.leaves2), Lists.newArrayList(new String[]{"acacia_leaves", "dark_oak_leaves"}));
      this.variantNames.put(Item.getItemFromBlock(Blocks.log2), Lists.newArrayList(new String[]{"acacia_log", "dark_oak_log"}));
      this.variantNames.put(Item.getItemFromBlock(Blocks.prismarine), Lists.newArrayList(new String[]{"prismarine", "prismarine_bricks", "dark_prismarine"}));
      this.variantNames.put(Item.getItemFromBlock(Blocks.carpet), Lists.newArrayList(new String[]{"black_carpet", "red_carpet", "green_carpet", "brown_carpet", "blue_carpet", "purple_carpet", "cyan_carpet", "silver_carpet", "gray_carpet", "pink_carpet", "lime_carpet", "yellow_carpet", "light_blue_carpet", "magenta_carpet", "orange_carpet", "white_carpet"}));
      this.variantNames.put(Item.getItemFromBlock(Blocks.double_plant), Lists.newArrayList(new String[]{"sunflower", "syringa", "double_grass", "double_fern", "double_rose", "paeonia"}));
      this.variantNames.put(Items.bow, Lists.newArrayList(new String[]{"bow", "bow_pulling_0", "bow_pulling_1", "bow_pulling_2"}));
      this.variantNames.put(Items.coal, Lists.newArrayList(new String[]{"coal", "charcoal"}));
      this.variantNames.put(Items.fishing_rod, Lists.newArrayList(new String[]{"fishing_rod", "fishing_rod_cast"}));
      this.variantNames.put(Items.fish, Lists.newArrayList(new String[]{"cod", "salmon", "clownfish", "pufferfish"}));
      this.variantNames.put(Items.cooked_fish, Lists.newArrayList(new String[]{"cooked_cod", "cooked_salmon"}));
      this.variantNames.put(Items.dye, Lists.newArrayList(new String[]{"dye_black", "dye_red", "dye_green", "dye_brown", "dye_blue", "dye_purple", "dye_cyan", "dye_silver", "dye_gray", "dye_pink", "dye_lime", "dye_yellow", "dye_light_blue", "dye_magenta", "dye_orange", "dye_white"}));
      this.variantNames.put(Items.potionitem, Lists.newArrayList(new String[]{"bottle_drinkable", "bottle_splash"}));
      this.variantNames.put(Items.skull, Lists.newArrayList(new String[]{"skull_skeleton", "skull_wither", "skull_zombie", "skull_char", "skull_creeper"}));
      this.variantNames.put(Item.getItemFromBlock(Blocks.oak_fence_gate), Lists.newArrayList(new String[]{"oak_fence_gate"}));
      this.variantNames.put(Item.getItemFromBlock(Blocks.oak_fence), Lists.newArrayList(new String[]{"oak_fence"}));
      this.variantNames.put(Items.oak_door, Lists.newArrayList(new String[]{"oak_door"}));
   }

   private List getVariantNames(Item var1) {
      List var2 = (List)this.variantNames.get(var1);
      var2 = Collections.singletonList(((ResourceLocation)Item.itemRegistry.getNameForObject(var1)).toString());
      return var2;
   }

   private ResourceLocation getItemLocation(String var1) {
      ResourceLocation var2 = new ResourceLocation(var1);
      return new ResourceLocation(var2.getResourceDomain(), "item/" + var2.getResourcePath());
   }

   private void bakeBlockModels() {
      for(ModelResourceLocation var2 : this.variants.keySet()) {
         WeightedBakedModel$Builder var3 = new WeightedBakedModel$Builder();
         int var4 = 0;

         for(qj var6 : ((ModelBlockDefinition$Variants)this.variants.get(var2)).getVariants()) {
            ModelBlock var7 = (ModelBlock)this.models.get(var6.c());
            if(var7.isResolved()) {
               ++var4;
               var3.add(this.bakeModel(var7, var6.d(), var6.a()), var6.b());
            } else {
               LOGGER.warn("Missing model for: " + var2);
            }
         }

         LOGGER.warn("No weighted models for: " + var2);
      }

      for(Entry var9 : this.itemLocations.entrySet()) {
         ResourceLocation var10 = (ResourceLocation)var9.getValue();
         ModelResourceLocation var11 = new ModelResourceLocation((String)var9.getKey(), "inventory");
         ModelBlock var12 = (ModelBlock)this.models.get(var10);
         if(var12.isResolved()) {
            if(this.d(var12)) {
               this.bakedRegistry.putObject(var11, new BuiltInModel(var12.func_181682_g()));
            } else {
               this.bakedRegistry.putObject(var11, this.bakeModel(var12, ModelRotation.X0_Y0, false));
            }
         } else {
            LOGGER.warn("Missing model for: " + var10);
         }
      }

   }

   private Set getVariantsTextureLocations() {
      HashSet var1 = Sets.newHashSet();
      ArrayList var2 = Lists.newArrayList(this.variants.keySet());
      var2.sort(Comparator.comparing(ModelResourceLocation::toString));

      for(ModelResourceLocation var4 : var2) {
         ModelBlockDefinition$Variants var5 = (ModelBlockDefinition$Variants)this.variants.get(var4);

         for(qj var7 : var5.getVariants()) {
            ModelBlock var8 = (ModelBlock)this.models.get(var7.c());
            LOGGER.warn("Missing model for: " + var4);
         }
      }

      var1.addAll(LOCATIONS_BUILTIN_TEXTURES);
      return var1;
   }

   private IBakedModel bakeModel(ModelBlock var1, ModelRotation var2, boolean var3) {
      TextureAtlasSprite var4 = (TextureAtlasSprite)this.sprites.get(new ResourceLocation(var1.resolveTextureName("particle")));
      SimpleBakedModel$Builder var5 = (new SimpleBakedModel$Builder(var1)).setTexture(var4);

      for(BlockPart var7 : var1.getElements()) {
         for(EnumFacing var9 : var7.mapFaces.keySet()) {
            BlockPartFace var10 = (BlockPartFace)var7.mapFaces.get(var9);
            TextureAtlasSprite var11 = (TextureAtlasSprite)this.sprites.get(new ResourceLocation(var1.resolveTextureName(var10.texture)));
            if(var10.cullFace == null) {
               var5.addGeneralQuad(this.makeBakedQuad(var7, var10, var11, var9, var2, var3));
            } else {
               var5.addFaceQuad(var2.rotateFace(var10.cullFace), this.makeBakedQuad(var7, var10, var11, var9, var2, var3));
            }
         }
      }

      return var5.makeBakedModel();
   }

   private BakedQuad makeBakedQuad(BlockPart var1, BlockPartFace var2, TextureAtlasSprite var3, EnumFacing var4, ModelRotation var5, boolean var6) {
      return aED.a(this.faceBakery, var1.positionFrom, var1.positionTo, var2, var3, var4, var5, var1.partRotation, var6, var1.shade);
   }

   private void loadModelsCheck() {
      this.loadModels();

      for(ModelBlock var2 : this.models.values()) {
         var2.getParentFromMap(this.models);
      }

      ModelBlock.checkModelHierarchy(this.models);
   }

   private void loadModels() {
      ArrayDeque var1 = Queues.newArrayDeque();
      HashSet var2 = Sets.newHashSet();

      for(ResourceLocation var4 : this.models.keySet()) {
         var2.add(var4);
         ResourceLocation var5 = ((ModelBlock)this.models.get(var4)).getParentLocation();
         var1.add(var5);
      }

      while(!var1.isEmpty()) {
         ResourceLocation var7 = (ResourceLocation)var1.pop();

         try {
            if(this.models.get(var7) != null) {
               continue;
            }

            ModelBlock var8 = this.loadModel(var7);
            this.models.put(var7, var8);
            ResourceLocation var9 = var8.getParentLocation();
            if(!var2.contains(var9)) {
               var1.add(var9);
            }
         } catch (Exception var6) {
            LOGGER.warn("In parent chain: " + JOINER.join(this.getParentPath(var7)) + "; unable to load model: \'" + var7 + "\'", var6);
         }

         var2.add(var7);
      }

   }

   private List getParentPath(ResourceLocation var1) {
      ArrayList var2 = Lists.newArrayList(new ResourceLocation[]{var1});
      ResourceLocation var3 = var1;

      while((var3 = this.getParentLocation(var3)) != null) {
         var2.add(0, var3);
      }

      return var2;
   }

   private ResourceLocation getParentLocation(ResourceLocation var1) {
      for(Entry var3 : this.models.entrySet()) {
         ModelBlock var4 = (ModelBlock)var3.getValue();
         if(var1.equals(var4.getParentLocation())) {
            return (ResourceLocation)var3.getKey();
         }
      }

      return null;
   }

   private Set getTextureLocations(ModelBlock var1) {
      HashSet var2 = Sets.newHashSet();

      for(BlockPart var4 : var1.getElements()) {
         for(BlockPartFace var6 : var4.mapFaces.values()) {
            var2.add(new ResourceLocation(var1.resolveTextureName(var6.texture)));
         }
      }

      var2.add(new ResourceLocation(var1.resolveTextureName("particle")));
      return var2;
   }

   private void loadSprites() {
      Set var1 = this.getVariantsTextureLocations();
      var1.addAll(this.getItemsTextureLocations());
      var1.remove(TextureMap.LOCATION_MISSING_TEXTURE);
      IIconCreator var2 = this::lambda$loadSprites$0;
      this.textureMap.loadSprites(this.resourceManager, var2);
      this.sprites.put(new ResourceLocation("missingno"), this.textureMap.getMissingSprite());
   }

   private Set getItemsTextureLocations() {
      HashSet var1 = Sets.newHashSet();

      for(ResourceLocation var3 : this.itemLocations.values()) {
         ModelBlock var4 = (ModelBlock)this.models.get(var3);
         var1.add(new ResourceLocation(var4.resolveTextureName("particle")));
         if(this.c(var4)) {
            for(String var10 : ItemModelGenerator.LAYERS) {
               ResourceLocation var11 = new ResourceLocation(var4.resolveTextureName(var10));
               if(var4.getRootModel() == MODEL_COMPASS && !TextureMap.LOCATION_MISSING_TEXTURE.equals(var11)) {
                  TextureAtlasSprite.setLocationNameCompass(var11.toString());
               } else if(var4.getRootModel() == MODEL_CLOCK && !TextureMap.LOCATION_MISSING_TEXTURE.equals(var11)) {
                  TextureAtlasSprite.setLocationNameClock(var11.toString());
               }

               var1.add(var11);
            }
         } else if(!this.d(var4)) {
            for(BlockPart var6 : var4.getElements()) {
               for(BlockPartFace var8 : var6.mapFaces.values()) {
                  var1.add(new ResourceLocation(var4.resolveTextureName(var8.texture)));
               }
            }
         }
      }

      return var1;
   }

   private boolean c(ModelBlock var1) {
      return false;
   }

   private boolean d(ModelBlock var1) {
      return false;
   }

   private void bakeItemModels() {
      for(ResourceLocation var2 : this.itemLocations.values()) {
         ModelBlock var3 = (ModelBlock)this.models.get(var2);
         if(this.c(var3)) {
            ModelBlock var4 = this.makeItemModel(var3);
            var4.name = var2.toString();
            this.models.put(var2, var4);
         } else if(this.d(var3)) {
            this.models.put(var2, var3);
         }
      }

      for(TextureAtlasSprite var6 : this.sprites.values()) {
         if(!var6.hasAnimationMetadata()) {
            var6.clearFramesTextureData();
         }
      }

   }

   private ModelBlock makeItemModel(ModelBlock var1) {
      return this.itemModelGenerator.makeItemModel(this.textureMap, var1);
   }

   private void lambda$loadSprites$0(Set var1, TextureMap var2) {
      for(ResourceLocation var4 : var1) {
         this.sprites.put(var4, var2.b(var4));
      }

   }

   static {
      BUILT_IN_MODELS.put("missing", "{ \"textures\": {   \"particle\": \"missingno\",   \"missingno\": \"missingno\"}, \"elements\": [ {     \"from\": [ 0, 0, 0 ],     \"to\": [ 16, 16, 16 ],     \"faces\": {         \"down\":  { \"uv\": [ 0, 0, 16, 16 ], \"cullface\": \"down\", \"texture\": \"#missingno\" },         \"up\":    { \"uv\": [ 0, 0, 16, 16 ], \"cullface\": \"up\", \"texture\": \"#missingno\" },         \"north\": { \"uv\": [ 0, 0, 16, 16 ], \"cullface\": \"north\", \"texture\": \"#missingno\" },         \"south\": { \"uv\": [ 0, 0, 16, 16 ], \"cullface\": \"south\", \"texture\": \"#missingno\" },         \"west\":  { \"uv\": [ 0, 0, 16, 16 ], \"cullface\": \"west\", \"texture\": \"#missingno\" },         \"east\":  { \"uv\": [ 0, 0, 16, 16 ], \"cullface\": \"east\", \"texture\": \"#missingno\" }    }}]}");
      MODEL_GENERATED.name = "generation marker";
      MODEL_COMPASS.name = "compass generation marker";
      MODEL_CLOCK.name = "class generation marker";
      MODEL_ENTITY.name = "block entity marker";
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
