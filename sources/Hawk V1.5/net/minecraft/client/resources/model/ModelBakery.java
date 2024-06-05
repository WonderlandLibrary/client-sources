package net.minecraft.client.resources.model;

import com.google.common.base.Charsets;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Queues;
import com.google.common.collect.Sets;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import net.minecraft.client.renderer.BlockModelShapes;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.BlockPart;
import net.minecraft.client.renderer.block.model.BlockPartFace;
import net.minecraft.client.renderer.block.model.FaceBakery;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemModelGenerator;
import net.minecraft.client.renderer.block.model.ModelBlock;
import net.minecraft.client.renderer.block.model.ModelBlockDefinition;
import net.minecraft.client.renderer.texture.IIconCreator;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IRegistry;
import net.minecraft.util.RegistrySimple;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ModelBakery {
   private static final ModelBlock MODEL_GENERATED;
   private final Map models = Maps.newLinkedHashMap();
   private static final Joiner field_177601_e;
   private static final ModelBlock MODEL_CLOCK;
   private final TextureMap textureMap;
   private static final Set field_177602_b = Sets.newHashSet(new ResourceLocation[]{new ResourceLocation("blocks/water_flow"), new ResourceLocation("blocks/water_still"), new ResourceLocation("blocks/lava_flow"), new ResourceLocation("blocks/lava_still"), new ResourceLocation("blocks/destroy_stage_0"), new ResourceLocation("blocks/destroy_stage_1"), new ResourceLocation("blocks/destroy_stage_2"), new ResourceLocation("blocks/destroy_stage_3"), new ResourceLocation("blocks/destroy_stage_4"), new ResourceLocation("blocks/destroy_stage_5"), new ResourceLocation("blocks/destroy_stage_6"), new ResourceLocation("blocks/destroy_stage_7"), new ResourceLocation("blocks/destroy_stage_8"), new ResourceLocation("blocks/destroy_stage_9"), new ResourceLocation("items/empty_armor_slot_helmet"), new ResourceLocation("items/empty_armor_slot_chestplate"), new ResourceLocation("items/empty_armor_slot_leggings"), new ResourceLocation("items/empty_armor_slot_boots")});
   private Map itemLocations = Maps.newLinkedHashMap();
   protected static final ModelResourceLocation MODEL_MISSING = new ModelResourceLocation("builtin/missing", "missing");
   private final BlockModelShapes blockModelShapes;
   private final IResourceManager resourceManager;
   private static final String __OBFID = "CL_00002391";
   private RegistrySimple bakedRegistry = new RegistrySimple();
   private static final ModelBlock MODEL_COMPASS;
   private static final ModelBlock MODEL_ENTITY;
   private static final Logger LOGGER = LogManager.getLogger();
   private final Map field_177614_t = Maps.newHashMap();
   private final Map variants = Maps.newLinkedHashMap();
   private Map variantNames = Maps.newIdentityHashMap();
   private static final Map BUILT_IN_MODELS = Maps.newHashMap();
   private final Map field_177599_g = Maps.newHashMap();
   private final FaceBakery field_177607_l = new FaceBakery();
   private final ItemModelGenerator itemModelGenerator = new ItemModelGenerator();

   private void registerVariant(ModelBlockDefinition var1, ModelResourceLocation var2) {
      this.variants.put(var2, var1.func_178330_b(var2.func_177518_c()));
   }

   private List func_177573_e(ResourceLocation var1) {
      ArrayList var2 = Lists.newArrayList(new ResourceLocation[]{var1});
      ResourceLocation var3 = var1;

      while((var3 = this.func_177576_f(var3)) != null) {
         var2.add(0, var3);
      }

      return var2;
   }

   private ResourceLocation getBlockStateLocation(ResourceLocation var1) {
      return new ResourceLocation(var1.getResourceDomain(), String.valueOf((new StringBuilder("blockstates/")).append(var1.getResourcePath()).append(".json")));
   }

   private void func_177595_c() {
      Iterator var1 = this.variants.keySet().iterator();

      while(var1.hasNext()) {
         ModelResourceLocation var2 = (ModelResourceLocation)var1.next();
         Iterator var3 = ((ModelBlockDefinition.Variants)this.variants.get(var2)).getVariants().iterator();

         while(var3.hasNext()) {
            ModelBlockDefinition.Variant var4 = (ModelBlockDefinition.Variant)var3.next();
            ResourceLocation var5 = var4.getModelLocation();
            if (this.models.get(var5) == null) {
               try {
                  ModelBlock var6 = this.loadModel(var5);
                  this.models.put(var5, var6);
               } catch (Exception var7) {
                  LOGGER.warn(String.valueOf((new StringBuilder("Unable to load block model: '")).append(var5).append("' for variant: '").append(var2).append("'")), var7);
               }
            }
         }
      }

   }

   private boolean func_177581_b(ModelBlock var1) {
      if (var1 == null) {
         return false;
      } else {
         ModelBlock var2 = var1.getRootModel();
         return var2 == MODEL_GENERATED || var2 == MODEL_COMPASS || var2 == MODEL_CLOCK;
      }
   }

   private void func_177577_b() {
      this.loadVariants(this.blockModelShapes.getBlockStateMapper().func_178446_a().values());
      this.variants.put(MODEL_MISSING, new ModelBlockDefinition.Variants(MODEL_MISSING.func_177518_c(), Lists.newArrayList(new ModelBlockDefinition.Variant[]{new ModelBlockDefinition.Variant(new ResourceLocation(MODEL_MISSING.getResourcePath()), ModelRotation.X0_Y0, false, 1)})));
      ResourceLocation var1 = new ResourceLocation("item_frame");
      ModelBlockDefinition var2 = this.getModelBlockDefinition(var1);
      this.registerVariant(var2, new ModelResourceLocation(var1, "normal"));
      this.registerVariant(var2, new ModelResourceLocation(var1, "map"));
      this.func_177595_c();
      this.loadItemModels();
   }

   public ModelBakery(IResourceManager var1, TextureMap var2, BlockModelShapes var3) {
      this.resourceManager = var1;
      this.textureMap = var2;
      this.blockModelShapes = var3;
   }

   static {
      BUILT_IN_MODELS.put("missing", "{ \"textures\": {   \"particle\": \"missingno\",   \"missingno\": \"missingno\"}, \"elements\": [ {     \"from\": [ 0, 0, 0 ],     \"to\": [ 16, 16, 16 ],     \"faces\": {         \"down\":  { \"uv\": [ 0, 0, 16, 16 ], \"cullface\": \"down\", \"texture\": \"#missingno\" },         \"up\":    { \"uv\": [ 0, 0, 16, 16 ], \"cullface\": \"up\", \"texture\": \"#missingno\" },         \"north\": { \"uv\": [ 0, 0, 16, 16 ], \"cullface\": \"north\", \"texture\": \"#missingno\" },         \"south\": { \"uv\": [ 0, 0, 16, 16 ], \"cullface\": \"south\", \"texture\": \"#missingno\" },         \"west\":  { \"uv\": [ 0, 0, 16, 16 ], \"cullface\": \"west\", \"texture\": \"#missingno\" },         \"east\":  { \"uv\": [ 0, 0, 16, 16 ], \"cullface\": \"east\", \"texture\": \"#missingno\" }    }}]}");
      field_177601_e = Joiner.on(" -> ");
      MODEL_GENERATED = ModelBlock.deserialize("{\"elements\":[{  \"from\": [0, 0, 0],   \"to\": [16, 16, 16],   \"faces\": {       \"down\": {\"uv\": [0, 0, 16, 16], \"texture\":\"\"}   }}]}");
      MODEL_COMPASS = ModelBlock.deserialize("{\"elements\":[{  \"from\": [0, 0, 0],   \"to\": [16, 16, 16],   \"faces\": {       \"down\": {\"uv\": [0, 0, 16, 16], \"texture\":\"\"}   }}]}");
      MODEL_CLOCK = ModelBlock.deserialize("{\"elements\":[{  \"from\": [0, 0, 0],   \"to\": [16, 16, 16],   \"faces\": {       \"down\": {\"uv\": [0, 0, 16, 16], \"texture\":\"\"}   }}]}");
      MODEL_ENTITY = ModelBlock.deserialize("{\"elements\":[{  \"from\": [0, 0, 0],   \"to\": [16, 16, 16],   \"faces\": {       \"down\": {\"uv\": [0, 0, 16, 16], \"texture\":\"\"}   }}]}");
      MODEL_GENERATED.field_178317_b = "generation marker";
      MODEL_COMPASS.field_178317_b = "compass generation marker";
      MODEL_CLOCK.field_178317_b = "class generation marker";
      MODEL_ENTITY.field_178317_b = "block entity marker";
   }

   static Map access$0(ModelBakery var0) {
      return var0.field_177599_g;
   }

   private ModelBlock loadModel(ResourceLocation var1) throws IOException {
      String var2 = var1.getResourcePath();
      if ("builtin/generated".equals(var2)) {
         return MODEL_GENERATED;
      } else if ("builtin/compass".equals(var2)) {
         return MODEL_COMPASS;
      } else if ("builtin/clock".equals(var2)) {
         return MODEL_CLOCK;
      } else if ("builtin/entity".equals(var2)) {
         return MODEL_ENTITY;
      } else {
         Object var3;
         if (var2.startsWith("builtin/")) {
            String var4 = var2.substring("builtin/".length());
            String var5 = (String)BUILT_IN_MODELS.get(var4);
            if (var5 == null) {
               throw new FileNotFoundException(var1.toString());
            }

            var3 = new StringReader(var5);
         } else {
            IResource var9 = this.resourceManager.getResource(this.getModelLocation(var1));
            var3 = new InputStreamReader(var9.getInputStream(), Charsets.UTF_8);
         }

         try {
            ModelBlock var10 = ModelBlock.deserialize((Reader)var3);
            var10.field_178317_b = var1.toString();
            ((Reader)var3).close();
            return var10;
         } finally {
            ((Reader)var3).close();
         }
      }
   }

   private ResourceLocation getItemLocation(String var1) {
      ResourceLocation var2 = new ResourceLocation(var1);
      return new ResourceLocation(var2.getResourceDomain(), String.valueOf((new StringBuilder("item/")).append(var2.getResourcePath())));
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

   private void func_177597_h() {
      this.func_177574_i();
      Iterator var1 = this.models.values().iterator();

      while(var1.hasNext()) {
         ModelBlock var2 = (ModelBlock)var1.next();
         var2.getParentFromMap(this.models);
      }

      ModelBlock.func_178312_b(this.models);
   }

   private void func_177574_i() {
      ArrayDeque var1 = Queues.newArrayDeque();
      HashSet var2 = Sets.newHashSet();
      Iterator var3 = this.models.keySet().iterator();

      ResourceLocation var4;
      ResourceLocation var5;
      while(var3.hasNext()) {
         var5 = (ResourceLocation)var3.next();
         var2.add(var5);
         var4 = ((ModelBlock)this.models.get(var5)).getParentLocation();
         if (var4 != null) {
            var1.add(var4);
         }
      }

      while(!var1.isEmpty()) {
         var5 = (ResourceLocation)var1.pop();

         try {
            if (this.models.get(var5) != null) {
               continue;
            }

            ModelBlock var6 = this.loadModel(var5);
            this.models.put(var5, var6);
            var4 = var6.getParentLocation();
            if (var4 != null && !var2.contains(var4)) {
               var1.add(var4);
            }
         } catch (Exception var7) {
            LOGGER.warn(String.valueOf((new StringBuilder("In parent chain: ")).append(field_177601_e.join(this.func_177573_e(var5))).append("; unable to load model: '").append(var5).append("'")), var7);
         }

         var2.add(var5);
      }

   }

   private boolean isCustomRenderer(ModelBlock var1) {
      if (var1 == null) {
         return false;
      } else {
         ModelBlock var2 = var1.getRootModel();
         return var2 == MODEL_ENTITY;
      }
   }

   private void bakeItemModels() {
      Iterator var1 = this.itemLocations.values().iterator();

      while(var1.hasNext()) {
         ResourceLocation var2 = (ResourceLocation)var1.next();
         ModelBlock var3 = (ModelBlock)this.models.get(var2);
         if (this.func_177581_b(var3)) {
            ModelBlock var4 = this.func_177582_d(var3);
            if (var4 != null) {
               var4.field_178317_b = var2.toString();
            }

            this.models.put(var2, var4);
         } else if (this.isCustomRenderer(var3)) {
            this.models.put(var2, var3);
         }
      }

      var1 = this.field_177599_g.values().iterator();

      while(var1.hasNext()) {
         TextureAtlasSprite var5 = (TextureAtlasSprite)var1.next();
         if (!var5.hasAnimationMetadata()) {
            var5.clearFramesTextureData();
         }
      }

   }

   private void bakeBlockModels() {
      Iterator var1 = this.variants.keySet().iterator();

      while(var1.hasNext()) {
         ModelResourceLocation var2 = (ModelResourceLocation)var1.next();
         WeightedBakedModel.Builder var3 = new WeightedBakedModel.Builder();
         int var4 = 0;
         Iterator var5 = ((ModelBlockDefinition.Variants)this.variants.get(var2)).getVariants().iterator();

         while(true) {
            while(var5.hasNext()) {
               ModelBlockDefinition.Variant var6 = (ModelBlockDefinition.Variant)var5.next();
               ModelBlock var7 = (ModelBlock)this.models.get(var6.getModelLocation());
               if (var7 != null && var7.isResolved()) {
                  ++var4;
                  var3.add(this.bakeModel(var7, var6.getRotation(), var6.isUvLocked()), var6.getWeight());
               } else {
                  LOGGER.warn(String.valueOf((new StringBuilder("Missing model for: ")).append(var2)));
               }
            }

            if (var4 == 0) {
               LOGGER.warn(String.valueOf((new StringBuilder("No weighted models for: ")).append(var2)));
            } else if (var4 == 1) {
               this.bakedRegistry.putObject(var2, var3.first());
            } else {
               this.bakedRegistry.putObject(var2, var3.build());
            }
            break;
         }
      }

      var1 = this.itemLocations.entrySet().iterator();

      while(true) {
         while(var1.hasNext()) {
            Entry var8 = (Entry)var1.next();
            ResourceLocation var9 = (ResourceLocation)var8.getValue();
            ModelResourceLocation var10 = new ModelResourceLocation((String)var8.getKey(), "inventory");
            ModelBlock var11 = (ModelBlock)this.models.get(var9);
            if (var11 != null && var11.isResolved()) {
               if (this.isCustomRenderer(var11)) {
                  this.bakedRegistry.putObject(var10, new BuiltInModel(new ItemCameraTransforms(var11.getThirdPersonTransform(), var11.getFirstPersonTransform(), var11.getHeadTransform(), var11.getInGuiTransform())));
               } else {
                  this.bakedRegistry.putObject(var10, this.bakeModel(var11, ModelRotation.X0_Y0, false));
               }
            } else {
               LOGGER.warn(String.valueOf((new StringBuilder("Missing model for: ")).append(var9)));
            }
         }

         return;
      }
   }

   private List getVariantNames(Item var1) {
      List var2 = (List)this.variantNames.get(var1);
      if (var2 == null) {
         var2 = Collections.singletonList(((ResourceLocation)Item.itemRegistry.getNameForObject(var1)).toString());
      }

      return var2;
   }

   private Set func_177575_g() {
      HashSet var1 = Sets.newHashSet();
      ArrayList var2 = Lists.newArrayList(this.variants.keySet());
      Collections.sort(var2, new Comparator(this) {
         final ModelBakery this$0;
         private static final String __OBFID = "CL_00002390";

         public int compare(Object var1, Object var2) {
            return this.func_177505_a((ModelResourceLocation)var1, (ModelResourceLocation)var2);
         }

         {
            this.this$0 = var1;
         }

         public int func_177505_a(ModelResourceLocation var1, ModelResourceLocation var2) {
            return var1.toString().compareTo(var2.toString());
         }
      });
      Iterator var3 = var2.iterator();

      while(var3.hasNext()) {
         ModelResourceLocation var4 = (ModelResourceLocation)var3.next();
         ModelBlockDefinition.Variants var5 = (ModelBlockDefinition.Variants)this.variants.get(var4);
         Iterator var6 = var5.getVariants().iterator();

         while(var6.hasNext()) {
            ModelBlockDefinition.Variant var7 = (ModelBlockDefinition.Variant)var6.next();
            ModelBlock var8 = (ModelBlock)this.models.get(var7.getModelLocation());
            if (var8 == null) {
               LOGGER.warn(String.valueOf((new StringBuilder("Missing model for: ")).append(var4)));
            } else {
               var1.addAll(this.func_177585_a(var8));
            }
         }
      }

      var1.addAll(field_177602_b);
      return var1;
   }

   private IBakedModel bakeModel(ModelBlock var1, ModelRotation var2, boolean var3) {
      TextureAtlasSprite var4 = (TextureAtlasSprite)this.field_177599_g.get(new ResourceLocation(var1.resolveTextureName("particle")));
      SimpleBakedModel.Builder var5 = (new SimpleBakedModel.Builder(var1)).func_177646_a(var4);
      Iterator var6 = var1.getElements().iterator();

      while(var6.hasNext()) {
         BlockPart var7 = (BlockPart)var6.next();
         Iterator var8 = var7.field_178240_c.keySet().iterator();

         while(var8.hasNext()) {
            EnumFacing var9 = (EnumFacing)var8.next();
            BlockPartFace var10 = (BlockPartFace)var7.field_178240_c.get(var9);
            TextureAtlasSprite var11 = (TextureAtlasSprite)this.field_177599_g.get(new ResourceLocation(var1.resolveTextureName(var10.field_178242_d)));
            if (var10.field_178244_b == null) {
               var5.func_177648_a(this.func_177589_a(var7, var10, var11, var9, var2, var3));
            } else {
               var5.func_177650_a(var2.func_177523_a(var10.field_178244_b), this.func_177589_a(var7, var10, var11, var9, var2, var3));
            }
         }
      }

      return var5.func_177645_b();
   }

   private void func_177572_j() {
      Set var1 = this.func_177575_g();
      var1.addAll(this.func_177571_k());
      var1.remove(TextureMap.field_174945_f);
      IIconCreator var2 = new IIconCreator(this, var1) {
         private static final String __OBFID = "CL_00002389";
         final ModelBakery this$0;
         private final Set val$var1;

         public void func_177059_a(TextureMap var1) {
            Iterator var2 = this.val$var1.iterator();

            while(var2.hasNext()) {
               ResourceLocation var3 = (ResourceLocation)var2.next();
               TextureAtlasSprite var4 = var1.func_174942_a(var3);
               ModelBakery.access$0(this.this$0).put(var3, var4);
            }

         }

         {
            this.this$0 = var1;
            this.val$var1 = var2;
         }
      };
      this.textureMap.func_174943_a(this.resourceManager, var2);
      this.field_177599_g.put(new ResourceLocation("missingno"), this.textureMap.func_174944_f());
   }

   private ModelBlock func_177582_d(ModelBlock var1) {
      return this.itemModelGenerator.func_178392_a(this.textureMap, var1);
   }

   private void loadItemModels() {
      this.registerVariantNames();
      Iterator var1 = Item.itemRegistry.iterator();

      while(var1.hasNext()) {
         Item var2 = (Item)var1.next();
         List var3 = this.getVariantNames(var2);
         Iterator var4 = var3.iterator();

         while(var4.hasNext()) {
            String var5 = (String)var4.next();
            ResourceLocation var6 = this.getItemLocation(var5);
            this.itemLocations.put(var5, var6);
            if (this.models.get(var6) == null) {
               try {
                  ModelBlock var7 = this.loadModel(var6);
                  this.models.put(var6, var7);
               } catch (Exception var8) {
                  LOGGER.warn(String.valueOf((new StringBuilder("Unable to load item model: '")).append(var6).append("' for item: '").append(Item.itemRegistry.getNameForObject(var2)).append("'")), var8);
               }
            }
         }
      }

   }

   public IRegistry setupModelRegistry() {
      this.func_177577_b();
      this.func_177597_h();
      this.func_177572_j();
      this.bakeItemModels();
      this.bakeBlockModels();
      return this.bakedRegistry;
   }

   private BakedQuad func_177589_a(BlockPart var1, BlockPartFace var2, TextureAtlasSprite var3, EnumFacing var4, ModelRotation var5, boolean var6) {
      return this.field_177607_l.func_178414_a(var1.field_178241_a, var1.field_178239_b, var2, var3, var4, var5, var1.field_178237_d, var6, var1.field_178238_e);
   }

   private Set func_177571_k() {
      HashSet var1 = Sets.newHashSet();
      Iterator var2 = this.itemLocations.values().iterator();

      while(true) {
         while(true) {
            ModelBlock var4;
            do {
               if (!var2.hasNext()) {
                  return var1;
               }

               ResourceLocation var3 = (ResourceLocation)var2.next();
               var4 = (ModelBlock)this.models.get(var3);
            } while(var4 == null);

            var1.add(new ResourceLocation(var4.resolveTextureName("particle")));
            Iterator var5;
            ResourceLocation var6;
            if (this.func_177581_b(var4)) {
               for(var5 = ItemModelGenerator.LAYERS.iterator(); var5.hasNext(); var1.add(var6)) {
                  String var11 = (String)var5.next();
                  var6 = new ResourceLocation(var4.resolveTextureName(var11));
                  if (var4.getRootModel() == MODEL_COMPASS && !TextureMap.field_174945_f.equals(var6)) {
                     TextureAtlasSprite.func_176603_b(var6.toString());
                  } else if (var4.getRootModel() == MODEL_CLOCK && !TextureMap.field_174945_f.equals(var6)) {
                     TextureAtlasSprite.func_176602_a(var6.toString());
                  }
               }
            } else if (!this.isCustomRenderer(var4)) {
               var5 = var4.getElements().iterator();

               while(var5.hasNext()) {
                  BlockPart var7 = (BlockPart)var5.next();
                  Iterator var8 = var7.field_178240_c.values().iterator();

                  while(var8.hasNext()) {
                     BlockPartFace var9 = (BlockPartFace)var8.next();
                     ResourceLocation var10 = new ResourceLocation(var4.resolveTextureName(var9.field_178242_d));
                     var1.add(var10);
                  }
               }
            }
         }
      }
   }

   private ResourceLocation func_177576_f(ResourceLocation var1) {
      Iterator var2 = this.models.entrySet().iterator();

      Entry var3;
      ModelBlock var4;
      do {
         if (!var2.hasNext()) {
            return null;
         }

         var3 = (Entry)var2.next();
         var4 = (ModelBlock)var3.getValue();
      } while(var4 == null || !var1.equals(var4.getParentLocation()));

      return (ResourceLocation)var3.getKey();
   }

   private Set func_177585_a(ModelBlock var1) {
      HashSet var2 = Sets.newHashSet();
      Iterator var3 = var1.getElements().iterator();

      while(var3.hasNext()) {
         BlockPart var4 = (BlockPart)var3.next();
         Iterator var5 = var4.field_178240_c.values().iterator();

         while(var5.hasNext()) {
            BlockPartFace var6 = (BlockPartFace)var5.next();
            ResourceLocation var7 = new ResourceLocation(var1.resolveTextureName(var6.field_178242_d));
            var2.add(var7);
         }
      }

      var2.add(new ResourceLocation(var1.resolveTextureName("particle")));
      return var2;
   }

   private ModelBlockDefinition getModelBlockDefinition(ResourceLocation var1) {
      ResourceLocation var2 = this.getBlockStateLocation(var1);
      ModelBlockDefinition var3 = (ModelBlockDefinition)this.field_177614_t.get(var2);
      if (var3 == null) {
         ArrayList var4 = Lists.newArrayList();

         try {
            Iterator var5 = this.resourceManager.getAllResources(var2).iterator();

            while(var5.hasNext()) {
               IResource var6 = (IResource)var5.next();
               InputStream var7 = null;

               try {
                  var7 = var6.getInputStream();
                  ModelBlockDefinition var8 = ModelBlockDefinition.func_178331_a(new InputStreamReader(var7, Charsets.UTF_8));
                  var4.add(var8);
               } catch (Exception var13) {
                  throw new RuntimeException(String.valueOf((new StringBuilder("Encountered an exception when loading model definition of '")).append(var1).append("' from: '").append(var6.func_177241_a()).append("' in resourcepack: '").append(var6.func_177240_d()).append("'")), var13);
               } finally {
                  IOUtils.closeQuietly(var7);
               }
            }
         } catch (IOException var15) {
            throw new RuntimeException(String.valueOf((new StringBuilder("Encountered an exception when loading model definition of model ")).append(var2.toString())), var15);
         }

         var3 = new ModelBlockDefinition(var4);
         this.field_177614_t.put(var2, var3);
      }

      return var3;
   }

   private void loadVariants(Collection var1) {
      Iterator var2 = var1.iterator();

      while(var2.hasNext()) {
         ModelResourceLocation var3 = (ModelResourceLocation)var2.next();

         try {
            ModelBlockDefinition var4 = this.getModelBlockDefinition(var3);

            try {
               this.registerVariant(var4, var3);
            } catch (Exception var6) {
               LOGGER.warn(String.valueOf((new StringBuilder("Unable to load variant: ")).append(var3.func_177518_c()).append(" from ").append(var3)));
            }
         } catch (Exception var7) {
            LOGGER.warn(String.valueOf((new StringBuilder("Unable to load definition ")).append(var3)), var7);
         }
      }

   }

   private ResourceLocation getModelLocation(ResourceLocation var1) {
      return new ResourceLocation(var1.getResourceDomain(), String.valueOf((new StringBuilder("models/")).append(var1.getResourcePath()).append(".json")));
   }
}
