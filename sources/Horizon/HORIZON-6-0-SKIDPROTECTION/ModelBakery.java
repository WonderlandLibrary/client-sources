package HORIZON-6-0-SKIDPROTECTION;

import java.util.ArrayDeque;
import com.google.common.collect.Queues;
import java.util.HashSet;
import java.util.Comparator;
import java.util.Collections;
import java.io.StringReader;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.io.IOException;
import org.apache.commons.io.IOUtils;
import java.io.Reader;
import java.io.InputStreamReader;
import com.google.common.base.Charsets;
import java.util.Iterator;
import java.util.List;
import com.google.common.collect.Lists;
import java.util.Collection;
import com.google.common.collect.Maps;
import org.apache.logging.log4j.LogManager;
import com.google.common.collect.Sets;
import com.google.common.base.Joiner;
import java.util.Map;
import org.apache.logging.log4j.Logger;
import java.util.Set;

public class ModelBakery
{
    private static final Set Â;
    private static final Logger Ý;
    protected static final ModelResourceLocation HorizonCode_Horizon_È;
    private static final Map Ø­áŒŠá;
    private static final Joiner Âµá€;
    private final IResourceManager Ó;
    private final Map à;
    private final Map Ø;
    private final Map áŒŠÆ;
    private final TextureMap áˆºÑ¢Õ;
    private final BlockModelShapes ÂµÈ;
    private final FaceBakery á;
    private final ItemModelGenerator ˆÏ­;
    private RegistrySimple £á;
    private static final ModelBlock Å;
    private static final ModelBlock £à;
    private static final ModelBlock µà;
    private static final ModelBlock ˆà;
    private Map ¥Æ;
    private final Map Ø­à;
    private Map µÕ;
    private static final String Æ = "CL_00002391";
    
    static {
        Â = Sets.newHashSet((Object[])new ResourceLocation_1975012498[] { new ResourceLocation_1975012498("blocks/water_flow"), new ResourceLocation_1975012498("blocks/water_still"), new ResourceLocation_1975012498("blocks/lava_flow"), new ResourceLocation_1975012498("blocks/lava_still"), new ResourceLocation_1975012498("blocks/destroy_stage_0"), new ResourceLocation_1975012498("blocks/destroy_stage_1"), new ResourceLocation_1975012498("blocks/destroy_stage_2"), new ResourceLocation_1975012498("blocks/destroy_stage_3"), new ResourceLocation_1975012498("blocks/destroy_stage_4"), new ResourceLocation_1975012498("blocks/destroy_stage_5"), new ResourceLocation_1975012498("blocks/destroy_stage_6"), new ResourceLocation_1975012498("blocks/destroy_stage_7"), new ResourceLocation_1975012498("blocks/destroy_stage_8"), new ResourceLocation_1975012498("blocks/destroy_stage_9"), new ResourceLocation_1975012498("items/empty_armor_slot_helmet"), new ResourceLocation_1975012498("items/empty_armor_slot_chestplate"), new ResourceLocation_1975012498("items/empty_armor_slot_leggings"), new ResourceLocation_1975012498("items/empty_armor_slot_boots") });
        Ý = LogManager.getLogger();
        HorizonCode_Horizon_È = new ModelResourceLocation("builtin/missing", "missing");
        (Ø­áŒŠá = Maps.newHashMap()).put("missing", "{ \"textures\": {   \"particle\": \"missingno\",   \"missingno\": \"missingno\"}, \"elements\": [ {     \"from\": [ 0, 0, 0 ],     \"to\": [ 16, 16, 16 ],     \"faces\": {         \"down\":  { \"uv\": [ 0, 0, 16, 16 ], \"cullface\": \"down\", \"texture\": \"#missingno\" },         \"up\":    { \"uv\": [ 0, 0, 16, 16 ], \"cullface\": \"up\", \"texture\": \"#missingno\" },         \"north\": { \"uv\": [ 0, 0, 16, 16 ], \"cullface\": \"north\", \"texture\": \"#missingno\" },         \"south\": { \"uv\": [ 0, 0, 16, 16 ], \"cullface\": \"south\", \"texture\": \"#missingno\" },         \"west\":  { \"uv\": [ 0, 0, 16, 16 ], \"cullface\": \"west\", \"texture\": \"#missingno\" },         \"east\":  { \"uv\": [ 0, 0, 16, 16 ], \"cullface\": \"east\", \"texture\": \"#missingno\" }    }}]}");
        Âµá€ = Joiner.on(" -> ");
        Å = ModelBlock.HorizonCode_Horizon_È("{\"elements\":[{  \"from\": [0, 0, 0],   \"to\": [16, 16, 16],   \"faces\": {       \"down\": {\"uv\": [0, 0, 16, 16], \"texture\":\"\"}   }}]}");
        £à = ModelBlock.HorizonCode_Horizon_È("{\"elements\":[{  \"from\": [0, 0, 0],   \"to\": [16, 16, 16],   \"faces\": {       \"down\": {\"uv\": [0, 0, 16, 16], \"texture\":\"\"}   }}]}");
        µà = ModelBlock.HorizonCode_Horizon_È("{\"elements\":[{  \"from\": [0, 0, 0],   \"to\": [16, 16, 16],   \"faces\": {       \"down\": {\"uv\": [0, 0, 16, 16], \"texture\":\"\"}   }}]}");
        ˆà = ModelBlock.HorizonCode_Horizon_È("{\"elements\":[{  \"from\": [0, 0, 0],   \"to\": [16, 16, 16],   \"faces\": {       \"down\": {\"uv\": [0, 0, 16, 16], \"texture\":\"\"}   }}]}");
        ModelBakery.Å.Â = "generation marker";
        ModelBakery.£à.Â = "compass generation marker";
        ModelBakery.µà.Â = "class generation marker";
        ModelBakery.ˆà.Â = "block entity marker";
    }
    
    public ModelBakery(final IResourceManager p_i46085_1_, final TextureMap p_i46085_2_, final BlockModelShapes p_i46085_3_) {
        this.à = Maps.newHashMap();
        this.Ø = Maps.newLinkedHashMap();
        this.áŒŠÆ = Maps.newLinkedHashMap();
        this.á = new FaceBakery();
        this.ˆÏ­ = new ItemModelGenerator();
        this.£á = new RegistrySimple();
        this.¥Æ = Maps.newLinkedHashMap();
        this.Ø­à = Maps.newHashMap();
        this.µÕ = Maps.newIdentityHashMap();
        this.Ó = p_i46085_1_;
        this.áˆºÑ¢Õ = p_i46085_2_;
        this.ÂµÈ = p_i46085_3_;
    }
    
    public IRegistry HorizonCode_Horizon_È() {
        this.Â();
        this.Ø();
        this.áˆºÑ¢Õ();
        this.á();
        this.Ó();
        return this.£á;
    }
    
    private void Â() {
        this.HorizonCode_Horizon_È(this.ÂµÈ.HorizonCode_Horizon_È().HorizonCode_Horizon_È().values());
        this.áŒŠÆ.put(ModelBakery.HorizonCode_Horizon_È, new ModelBlockDefinition.Ø­áŒŠá(ModelBakery.HorizonCode_Horizon_È.HorizonCode_Horizon_È(), Lists.newArrayList((Object[])new ModelBlockDefinition.Ý[] { new ModelBlockDefinition.Ý(new ResourceLocation_1975012498(ModelBakery.HorizonCode_Horizon_È.Â()), ModelRotation.HorizonCode_Horizon_È, false, 1) })));
        final ResourceLocation_1975012498 var1 = new ResourceLocation_1975012498("item_frame");
        final ModelBlockDefinition var2 = this.HorizonCode_Horizon_È(var1);
        this.HorizonCode_Horizon_È(var2, new ModelResourceLocation(var1, "normal"));
        this.HorizonCode_Horizon_È(var2, new ModelResourceLocation(var1, "map"));
        this.Ý();
        this.Ø­áŒŠá();
    }
    
    private void HorizonCode_Horizon_È(final Collection p_177591_1_) {
        for (final ModelResourceLocation var3 : p_177591_1_) {
            try {
                final ModelBlockDefinition var4 = this.HorizonCode_Horizon_È(var3);
                try {
                    this.HorizonCode_Horizon_È(var4, var3);
                }
                catch (Exception var6) {
                    ModelBakery.Ý.warn("Unable to load variant: " + var3.HorizonCode_Horizon_È() + " from " + var3);
                }
            }
            catch (Exception var5) {
                ModelBakery.Ý.warn("Unable to load definition " + var3, (Throwable)var5);
            }
        }
    }
    
    private void HorizonCode_Horizon_È(final ModelBlockDefinition p_177569_1_, final ModelResourceLocation p_177569_2_) {
        this.áŒŠÆ.put(p_177569_2_, p_177569_1_.HorizonCode_Horizon_È(p_177569_2_.HorizonCode_Horizon_È()));
    }
    
    private ModelBlockDefinition HorizonCode_Horizon_È(final ResourceLocation_1975012498 p_177586_1_) {
        final ResourceLocation_1975012498 var2 = this.Â(p_177586_1_);
        ModelBlockDefinition var3 = this.Ø­à.get(var2);
        if (var3 == null) {
            final ArrayList var4 = Lists.newArrayList();
            try {
                for (final IResource var6 : this.Ó.Â(var2)) {
                    InputStream var7 = null;
                    try {
                        var7 = var6.Â();
                        final ModelBlockDefinition var8 = ModelBlockDefinition.HorizonCode_Horizon_È(new InputStreamReader(var7, Charsets.UTF_8));
                        var4.add(var8);
                    }
                    catch (Exception var9) {
                        throw new RuntimeException("Encountered an exception when loading model definition of '" + p_177586_1_ + "' from: '" + var6.HorizonCode_Horizon_È() + "' in resourcepack: '" + var6.Ø­áŒŠá() + "'", var9);
                    }
                    finally {
                        IOUtils.closeQuietly(var7);
                    }
                    IOUtils.closeQuietly(var7);
                }
            }
            catch (IOException var10) {
                throw new RuntimeException("Encountered an exception when loading model definition of model " + var2.toString(), var10);
            }
            var3 = new ModelBlockDefinition(var4);
            this.Ø­à.put(var2, var3);
        }
        return var3;
    }
    
    private ResourceLocation_1975012498 Â(final ResourceLocation_1975012498 p_177584_1_) {
        return new ResourceLocation_1975012498(p_177584_1_.Ý(), "blockstates/" + p_177584_1_.Â() + ".json");
    }
    
    private void Ý() {
        for (final ModelResourceLocation var2 : this.áŒŠÆ.keySet()) {
            for (final ModelBlockDefinition.Ý var4 : this.áŒŠÆ.get(var2).HorizonCode_Horizon_È()) {
                final ResourceLocation_1975012498 var5 = var4.HorizonCode_Horizon_È();
                if (this.Ø.get(var5) == null) {
                    try {
                        final ModelBlock var6 = this.Ý(var5);
                        this.Ø.put(var5, var6);
                    }
                    catch (Exception var7) {
                        ModelBakery.Ý.warn("Unable to load block model: '" + var5 + "' for variant: '" + var2 + "'", (Throwable)var7);
                    }
                }
            }
        }
    }
    
    private ModelBlock Ý(final ResourceLocation_1975012498 p_177594_1_) throws IOException {
        final String var3 = p_177594_1_.Â();
        if ("builtin/generated".equals(var3)) {
            return ModelBakery.Å;
        }
        if ("builtin/compass".equals(var3)) {
            return ModelBakery.£à;
        }
        if ("builtin/clock".equals(var3)) {
            return ModelBakery.µà;
        }
        if ("builtin/entity".equals(var3)) {
            return ModelBakery.ˆà;
        }
        Object var6;
        if (var3.startsWith("builtin/")) {
            final String var4 = var3.substring("builtin/".length());
            final String var5 = ModelBakery.Ø­áŒŠá.get(var4);
            if (var5 == null) {
                throw new FileNotFoundException(p_177594_1_.toString());
            }
            var6 = new StringReader(var5);
        }
        else {
            final IResource var7 = this.Ó.HorizonCode_Horizon_È(this.Ø­áŒŠá(p_177594_1_));
            var6 = new InputStreamReader(var7.Â(), Charsets.UTF_8);
        }
        ModelBlock var9;
        try {
            final ModelBlock var8 = ModelBlock.HorizonCode_Horizon_È((Reader)var6);
            var8.Â = p_177594_1_.toString();
            var9 = var8;
        }
        finally {
            ((Reader)var6).close();
        }
        ((Reader)var6).close();
        return var9;
    }
    
    private ResourceLocation_1975012498 Ø­áŒŠá(final ResourceLocation_1975012498 p_177580_1_) {
        return new ResourceLocation_1975012498(p_177580_1_.Ý(), "models/" + p_177580_1_.Â() + ".json");
    }
    
    private void Ø­áŒŠá() {
        this.Âµá€();
        for (final Item_1028566121 var2 : Item_1028566121.HorizonCode_Horizon_È) {
            final List var3 = this.HorizonCode_Horizon_È(var2);
            for (final String var5 : var3) {
                final ResourceLocation_1975012498 var6 = this.HorizonCode_Horizon_È(var5);
                this.¥Æ.put(var5, var6);
                if (this.Ø.get(var6) == null) {
                    try {
                        final ModelBlock var7 = this.Ý(var6);
                        this.Ø.put(var6, var7);
                    }
                    catch (Exception var8) {
                        ModelBakery.Ý.warn("Unable to load item model: '" + var6 + "' for item: '" + Item_1028566121.HorizonCode_Horizon_È.Â(var2) + "'", (Throwable)var8);
                    }
                }
            }
        }
    }
    
    private void Âµá€() {
        this.µÕ.put(Item_1028566121.HorizonCode_Horizon_È(Blocks.Ý), Lists.newArrayList((Object[])new String[] { "stone", "granite", "granite_smooth", "diorite", "diorite_smooth", "andesite", "andesite_smooth" }));
        this.µÕ.put(Item_1028566121.HorizonCode_Horizon_È(Blocks.Âµá€), Lists.newArrayList((Object[])new String[] { "dirt", "coarse_dirt", "podzol" }));
        this.µÕ.put(Item_1028566121.HorizonCode_Horizon_È(Blocks.à), Lists.newArrayList((Object[])new String[] { "oak_planks", "spruce_planks", "birch_planks", "jungle_planks", "acacia_planks", "dark_oak_planks" }));
        this.µÕ.put(Item_1028566121.HorizonCode_Horizon_È(Blocks.Ø), Lists.newArrayList((Object[])new String[] { "oak_sapling", "spruce_sapling", "birch_sapling", "jungle_sapling", "acacia_sapling", "dark_oak_sapling" }));
        this.µÕ.put(Item_1028566121.HorizonCode_Horizon_È(Blocks.£á), Lists.newArrayList((Object[])new String[] { "sand", "red_sand" }));
        this.µÕ.put(Item_1028566121.HorizonCode_Horizon_È(Blocks.¥Æ), Lists.newArrayList((Object[])new String[] { "oak_log", "spruce_log", "birch_log", "jungle_log" }));
        this.µÕ.put(Item_1028566121.HorizonCode_Horizon_È(Blocks.µÕ), Lists.newArrayList((Object[])new String[] { "oak_leaves", "spruce_leaves", "birch_leaves", "jungle_leaves" }));
        this.µÕ.put(Item_1028566121.HorizonCode_Horizon_È(Blocks.Šáƒ), Lists.newArrayList((Object[])new String[] { "sponge", "sponge_wet" }));
        this.µÕ.put(Item_1028566121.HorizonCode_Horizon_È(Blocks.ŒÏ), Lists.newArrayList((Object[])new String[] { "sandstone", "chiseled_sandstone", "smooth_sandstone" }));
        this.µÕ.put(Item_1028566121.HorizonCode_Horizon_È(Blocks.áˆºÛ), Lists.newArrayList((Object[])new String[] { "red_sandstone", "chiseled_red_sandstone", "smooth_red_sandstone" }));
        this.µÕ.put(Item_1028566121.HorizonCode_Horizon_È(Blocks.áƒ), Lists.newArrayList((Object[])new String[] { "dead_bush", "tall_grass", "fern" }));
        this.µÕ.put(Item_1028566121.HorizonCode_Horizon_È(Blocks.á€), Lists.newArrayList((Object[])new String[] { "dead_bush" }));
        this.µÕ.put(Item_1028566121.HorizonCode_Horizon_È(Blocks.ŠÂµà), Lists.newArrayList((Object[])new String[] { "black_wool", "red_wool", "green_wool", "brown_wool", "blue_wool", "purple_wool", "cyan_wool", "silver_wool", "gray_wool", "pink_wool", "lime_wool", "yellow_wool", "light_blue_wool", "magenta_wool", "orange_wool", "white_wool" }));
        this.µÕ.put(Item_1028566121.HorizonCode_Horizon_È(Blocks.Âµà), Lists.newArrayList((Object[])new String[] { "dandelion" }));
        this.µÕ.put(Item_1028566121.HorizonCode_Horizon_È(Blocks.Ç), Lists.newArrayList((Object[])new String[] { "poppy", "blue_orchid", "allium", "houstonia", "red_tulip", "orange_tulip", "white_tulip", "pink_tulip", "oxeye_daisy" }));
        this.µÕ.put(Item_1028566121.HorizonCode_Horizon_È(Blocks.Ø­Âµ), Lists.newArrayList((Object[])new String[] { "stone_slab", "sandstone_slab", "cobblestone_slab", "brick_slab", "stone_brick_slab", "nether_brick_slab", "quartz_slab" }));
        this.µÕ.put(Item_1028566121.HorizonCode_Horizon_È(Blocks.µØ), Lists.newArrayList((Object[])new String[] { "red_sandstone_slab" }));
        this.µÕ.put(Item_1028566121.HorizonCode_Horizon_È(Blocks.ÐƒáŒŠÂµÐƒÕ), Lists.newArrayList((Object[])new String[] { "black_stained_glass", "red_stained_glass", "green_stained_glass", "brown_stained_glass", "blue_stained_glass", "purple_stained_glass", "cyan_stained_glass", "silver_stained_glass", "gray_stained_glass", "pink_stained_glass", "lime_stained_glass", "yellow_stained_glass", "light_blue_stained_glass", "magenta_stained_glass", "orange_stained_glass", "white_stained_glass" }));
        this.µÕ.put(Item_1028566121.HorizonCode_Horizon_È(Blocks.ÐƒÂ), Lists.newArrayList((Object[])new String[] { "stone_monster_egg", "cobblestone_monster_egg", "stone_brick_monster_egg", "mossy_brick_monster_egg", "cracked_brick_monster_egg", "chiseled_brick_monster_egg" }));
        this.µÕ.put(Item_1028566121.HorizonCode_Horizon_È(Blocks.£áƒ), Lists.newArrayList((Object[])new String[] { "stonebrick", "mossy_stonebrick", "cracked_stonebrick", "chiseled_stonebrick" }));
        this.µÕ.put(Item_1028566121.HorizonCode_Horizon_È(Blocks.ÇŽÊ), Lists.newArrayList((Object[])new String[] { "oak_slab", "spruce_slab", "birch_slab", "jungle_slab", "acacia_slab", "dark_oak_slab" }));
        this.µÕ.put(Item_1028566121.HorizonCode_Horizon_È(Blocks.Ï­Ó), Lists.newArrayList((Object[])new String[] { "cobblestone_wall", "mossy_cobblestone_wall" }));
        this.µÕ.put(Item_1028566121.HorizonCode_Horizon_È(Blocks.ÇªÅ), Lists.newArrayList((Object[])new String[] { "anvil_intact", "anvil_slightly_damaged", "anvil_very_damaged" }));
        this.µÕ.put(Item_1028566121.HorizonCode_Horizon_È(Blocks.Ø­È), Lists.newArrayList((Object[])new String[] { "quartz_block", "chiseled_quartz_block", "quartz_column" }));
        this.µÕ.put(Item_1028566121.HorizonCode_Horizon_È(Blocks.Ø­Â), Lists.newArrayList((Object[])new String[] { "black_stained_hardened_clay", "red_stained_hardened_clay", "green_stained_hardened_clay", "brown_stained_hardened_clay", "blue_stained_hardened_clay", "purple_stained_hardened_clay", "cyan_stained_hardened_clay", "silver_stained_hardened_clay", "gray_stained_hardened_clay", "pink_stained_hardened_clay", "lime_stained_hardened_clay", "yellow_stained_hardened_clay", "light_blue_stained_hardened_clay", "magenta_stained_hardened_clay", "orange_stained_hardened_clay", "white_stained_hardened_clay" }));
        this.µÕ.put(Item_1028566121.HorizonCode_Horizon_È(Blocks.Ø­áƒ), Lists.newArrayList((Object[])new String[] { "black_stained_glass_pane", "red_stained_glass_pane", "green_stained_glass_pane", "brown_stained_glass_pane", "blue_stained_glass_pane", "purple_stained_glass_pane", "cyan_stained_glass_pane", "silver_stained_glass_pane", "gray_stained_glass_pane", "pink_stained_glass_pane", "lime_stained_glass_pane", "yellow_stained_glass_pane", "light_blue_stained_glass_pane", "magenta_stained_glass_pane", "orange_stained_glass_pane", "white_stained_glass_pane" }));
        this.µÕ.put(Item_1028566121.HorizonCode_Horizon_È(Blocks.Æ), Lists.newArrayList((Object[])new String[] { "acacia_leaves", "dark_oak_leaves" }));
        this.µÕ.put(Item_1028566121.HorizonCode_Horizon_È(Blocks.Ø­à), Lists.newArrayList((Object[])new String[] { "acacia_log", "dark_oak_log" }));
        this.µÕ.put(Item_1028566121.HorizonCode_Horizon_È(Blocks.ÇŽáˆºÈ), Lists.newArrayList((Object[])new String[] { "prismarine", "prismarine_bricks", "dark_prismarine" }));
        this.µÕ.put(Item_1028566121.HorizonCode_Horizon_È(Blocks.áˆºÂ), Lists.newArrayList((Object[])new String[] { "black_carpet", "red_carpet", "green_carpet", "brown_carpet", "blue_carpet", "purple_carpet", "cyan_carpet", "silver_carpet", "gray_carpet", "pink_carpet", "lime_carpet", "yellow_carpet", "light_blue_carpet", "magenta_carpet", "orange_carpet", "white_carpet" }));
        this.µÕ.put(Item_1028566121.HorizonCode_Horizon_È(Blocks.ÇªÇªÉ), Lists.newArrayList((Object[])new String[] { "sunflower", "syringa", "double_grass", "double_fern", "double_rose", "paeonia" }));
        this.µÕ.put(Items.Ó, Lists.newArrayList((Object[])new String[] { "bow", "bow_pulling_0", "bow_pulling_1", "bow_pulling_2" }));
        this.µÕ.put(Items.Ø, Lists.newArrayList((Object[])new String[] { "coal", "charcoal" }));
        this.µÕ.put(Items.ÂµÕ, Lists.newArrayList((Object[])new String[] { "fishing_rod", "fishing_rod_cast" }));
        this.µÕ.put(Items.Ñ¢Ó, Lists.newArrayList((Object[])new String[] { "cod", "salmon", "clownfish", "pufferfish" }));
        this.µÕ.put(Items.Ø­Æ, Lists.newArrayList((Object[])new String[] { "cooked_cod", "cooked_salmon" }));
        this.µÕ.put(Items.áŒŠÔ, Lists.newArrayList((Object[])new String[] { "dye_black", "dye_red", "dye_green", "dye_brown", "dye_blue", "dye_purple", "dye_cyan", "dye_silver", "dye_gray", "dye_pink", "dye_lime", "dye_yellow", "dye_light_blue", "dye_magenta", "dye_orange", "dye_white" }));
        this.µÕ.put(Items.µÂ, Lists.newArrayList((Object[])new String[] { "bottle_drinkable", "bottle_splash" }));
        this.µÕ.put(Items.ˆ, Lists.newArrayList((Object[])new String[] { "skull_skeleton", "skull_wither", "skull_zombie", "skull_char", "skull_creeper" }));
        this.µÕ.put(Item_1028566121.HorizonCode_Horizon_È(Blocks.ŠáˆºÂ), Lists.newArrayList((Object[])new String[] { "oak_fence_gate" }));
        this.µÕ.put(Item_1028566121.HorizonCode_Horizon_È(Blocks.¥É), Lists.newArrayList((Object[])new String[] { "oak_fence" }));
        this.µÕ.put(Items.Œà, Lists.newArrayList((Object[])new String[] { "oak_door" }));
    }
    
    private List HorizonCode_Horizon_È(final Item_1028566121 p_177596_1_) {
        List var2 = this.µÕ.get(p_177596_1_);
        if (var2 == null) {
            var2 = Collections.singletonList(((ResourceLocation_1975012498)Item_1028566121.HorizonCode_Horizon_È.Â(p_177596_1_)).toString());
        }
        return var2;
    }
    
    private ResourceLocation_1975012498 HorizonCode_Horizon_È(final String p_177583_1_) {
        final ResourceLocation_1975012498 var2 = new ResourceLocation_1975012498(p_177583_1_);
        return new ResourceLocation_1975012498(var2.Ý(), "item/" + var2.Â());
    }
    
    private void Ó() {
        for (final ModelResourceLocation var2 : this.áŒŠÆ.keySet()) {
            final WeightedBakedModel.HorizonCode_Horizon_È var3 = new WeightedBakedModel.HorizonCode_Horizon_È();
            int var4 = 0;
            for (final ModelBlockDefinition.Ý var6 : this.áŒŠÆ.get(var2).HorizonCode_Horizon_È()) {
                final ModelBlock var7 = this.Ø.get(var6.HorizonCode_Horizon_È());
                if (var7 != null && var7.Ø­áŒŠá()) {
                    ++var4;
                    var3.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È(var7, var6.Â(), var6.Ý()), var6.Ø­áŒŠá());
                }
                else {
                    ModelBakery.Ý.warn("Missing model for: " + var2);
                }
            }
            if (var4 == 0) {
                ModelBakery.Ý.warn("No weighted models for: " + var2);
            }
            else if (var4 == 1) {
                this.£á.HorizonCode_Horizon_È(var2, var3.Â());
            }
            else {
                this.£á.HorizonCode_Horizon_È(var2, var3.HorizonCode_Horizon_È());
            }
        }
        for (final Map.Entry var8 : this.¥Æ.entrySet()) {
            final ResourceLocation_1975012498 var9 = var8.getValue();
            final ModelResourceLocation var10 = new ModelResourceLocation(var8.getKey(), "inventory");
            final ModelBlock var11 = this.Ø.get(var9);
            if (var11 != null && var11.Ø­áŒŠá()) {
                if (this.Ý(var11)) {
                    this.£á.HorizonCode_Horizon_È(var10, new BuiltInModel(new ItemCameraTransforms(var11.à(), var11.Ø(), var11.áŒŠÆ(), var11.áˆºÑ¢Õ())));
                }
                else {
                    this.£á.HorizonCode_Horizon_È(var10, this.HorizonCode_Horizon_È(var11, ModelRotation.HorizonCode_Horizon_È, false));
                }
            }
            else {
                ModelBakery.Ý.warn("Missing model for: " + var9);
            }
        }
    }
    
    private Set à() {
        final HashSet var1 = Sets.newHashSet();
        final ArrayList var2 = Lists.newArrayList((Iterable)this.áŒŠÆ.keySet());
        Collections.sort((List<Object>)var2, new Comparator() {
            private static final String Â = "CL_00002390";
            
            public int HorizonCode_Horizon_È(final ModelResourceLocation p_177505_1_, final ModelResourceLocation p_177505_2_) {
                return p_177505_1_.toString().compareTo(p_177505_2_.toString());
            }
            
            @Override
            public int compare(final Object p_compare_1_, final Object p_compare_2_) {
                return this.HorizonCode_Horizon_È((ModelResourceLocation)p_compare_1_, (ModelResourceLocation)p_compare_2_);
            }
        });
        for (final ModelResourceLocation var4 : var2) {
            final ModelBlockDefinition.Ø­áŒŠá var5 = this.áŒŠÆ.get(var4);
            for (final ModelBlockDefinition.Ý var7 : var5.HorizonCode_Horizon_È()) {
                final ModelBlock var8 = this.Ø.get(var7.HorizonCode_Horizon_È());
                if (var8 == null) {
                    ModelBakery.Ý.warn("Missing model for: " + var4);
                }
                else {
                    var1.addAll(this.HorizonCode_Horizon_È(var8));
                }
            }
        }
        var1.addAll(ModelBakery.Â);
        return var1;
    }
    
    private IBakedModel HorizonCode_Horizon_È(final ModelBlock p_177578_1_, final ModelRotation p_177578_2_, final boolean p_177578_3_) {
        final TextureAtlasSprite var4 = this.à.get(new ResourceLocation_1975012498(p_177578_1_.Ý("particle")));
        final SimpleBakedModel.HorizonCode_Horizon_È var5 = new SimpleBakedModel.HorizonCode_Horizon_È(p_177578_1_).HorizonCode_Horizon_È(var4);
        for (final BlockPart var7 : p_177578_1_.HorizonCode_Horizon_È()) {
            for (final EnumFacing var9 : var7.Ý.keySet()) {
                final BlockPartFace var10 = var7.Ý.get(var9);
                final TextureAtlasSprite var11 = this.à.get(new ResourceLocation_1975012498(p_177578_1_.Ý(var10.Ø­áŒŠá)));
                if (var10.Â == null) {
                    var5.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È(var7, var10, var11, var9, p_177578_2_, p_177578_3_));
                }
                else {
                    var5.HorizonCode_Horizon_È(p_177578_2_.HorizonCode_Horizon_È(var10.Â), this.HorizonCode_Horizon_È(var7, var10, var11, var9, p_177578_2_, p_177578_3_));
                }
            }
        }
        return var5.HorizonCode_Horizon_È();
    }
    
    private BakedQuad HorizonCode_Horizon_È(final BlockPart p_177589_1_, final BlockPartFace p_177589_2_, final TextureAtlasSprite p_177589_3_, final EnumFacing p_177589_4_, final ModelRotation p_177589_5_, final boolean p_177589_6_) {
        return this.á.HorizonCode_Horizon_È(p_177589_1_.HorizonCode_Horizon_È, p_177589_1_.Â, p_177589_2_, p_177589_3_, p_177589_4_, p_177589_5_, p_177589_1_.Ø­áŒŠá, p_177589_6_, p_177589_1_.Âµá€);
    }
    
    private void Ø() {
        this.áŒŠÆ();
        for (final ModelBlock var2 : this.Ø.values()) {
            var2.HorizonCode_Horizon_È(this.Ø);
        }
        ModelBlock.Â(this.Ø);
    }
    
    private void áŒŠÆ() {
        final ArrayDeque var1 = Queues.newArrayDeque();
        final HashSet var2 = Sets.newHashSet();
        for (final ResourceLocation_1975012498 var4 : this.Ø.keySet()) {
            var2.add(var4);
            final ResourceLocation_1975012498 var5 = this.Ø.get(var4).Âµá€();
            if (var5 != null) {
                var1.add(var5);
            }
        }
        while (!var1.isEmpty()) {
            final ResourceLocation_1975012498 var6 = var1.pop();
            try {
                if (this.Ø.get(var6) != null) {
                    continue;
                }
                final ModelBlock var7 = this.Ý(var6);
                this.Ø.put(var6, var7);
                final ResourceLocation_1975012498 var5 = var7.Âµá€();
                if (var5 != null && !var2.contains(var5)) {
                    var1.add(var5);
                }
            }
            catch (Exception var8) {
                ModelBakery.Ý.warn("In parent chain: " + ModelBakery.Âµá€.join((Iterable)this.Âµá€(var6)) + "; unable to load model: '" + var6 + "'", (Throwable)var8);
            }
            var2.add(var6);
        }
    }
    
    private List Âµá€(final ResourceLocation_1975012498 p_177573_1_) {
        final ArrayList var2 = Lists.newArrayList((Object[])new ResourceLocation_1975012498[] { p_177573_1_ });
        ResourceLocation_1975012498 var3 = p_177573_1_;
        while ((var3 = this.Ó(var3)) != null) {
            var2.add(0, var3);
        }
        return var2;
    }
    
    private ResourceLocation_1975012498 Ó(final ResourceLocation_1975012498 p_177576_1_) {
        for (final Map.Entry var3 : this.Ø.entrySet()) {
            final ModelBlock var4 = var3.getValue();
            if (var4 != null && p_177576_1_.equals(var4.Âµá€())) {
                return var3.getKey();
            }
        }
        return null;
    }
    
    private Set HorizonCode_Horizon_È(final ModelBlock p_177585_1_) {
        final HashSet var2 = Sets.newHashSet();
        for (final BlockPart var4 : p_177585_1_.HorizonCode_Horizon_È()) {
            for (final BlockPartFace var6 : var4.Ý.values()) {
                final ResourceLocation_1975012498 var7 = new ResourceLocation_1975012498(p_177585_1_.Ý(var6.Ø­áŒŠá));
                var2.add(var7);
            }
        }
        var2.add(new ResourceLocation_1975012498(p_177585_1_.Ý("particle")));
        return var2;
    }
    
    private void áˆºÑ¢Õ() {
        final Set var1 = this.à();
        var1.addAll(this.ÂµÈ());
        var1.remove(TextureMap.Ó);
        final IIconCreator var2 = new IIconCreator() {
            private static final String Â = "CL_00002389";
            
            @Override
            public void HorizonCode_Horizon_È(final TextureMap p_177059_1_) {
                for (final ResourceLocation_1975012498 var3 : var1) {
                    final TextureAtlasSprite var4 = p_177059_1_.HorizonCode_Horizon_È(var3);
                    ModelBakery.this.à.put(var3, var4);
                }
            }
        };
        this.áˆºÑ¢Õ.HorizonCode_Horizon_È(this.Ó, var2);
        this.à.put(new ResourceLocation_1975012498("missingno"), this.áˆºÑ¢Õ.Ó());
    }
    
    private Set ÂµÈ() {
        final HashSet var1 = Sets.newHashSet();
        for (final ResourceLocation_1975012498 var3 : this.¥Æ.values()) {
            final ModelBlock var4 = this.Ø.get(var3);
            if (var4 != null) {
                var1.add(new ResourceLocation_1975012498(var4.Ý("particle")));
                if (this.Â(var4)) {
                    for (final String var6 : ItemModelGenerator.HorizonCode_Horizon_È) {
                        final ResourceLocation_1975012498 var7 = new ResourceLocation_1975012498(var4.Ý(var6));
                        if (var4.Ó() == ModelBakery.£à && !TextureMap.Ó.equals(var7)) {
                            TextureAtlasSprite.Â(var7.toString());
                        }
                        else if (var4.Ó() == ModelBakery.µà && !TextureMap.Ó.equals(var7)) {
                            TextureAtlasSprite.HorizonCode_Horizon_È(var7.toString());
                        }
                        var1.add(var7);
                    }
                }
                else {
                    if (this.Ý(var4)) {
                        continue;
                    }
                    for (final BlockPart var8 : var4.HorizonCode_Horizon_È()) {
                        for (final BlockPartFace var10 : var8.Ý.values()) {
                            final ResourceLocation_1975012498 var11 = new ResourceLocation_1975012498(var4.Ý(var10.Ø­áŒŠá));
                            var1.add(var11);
                        }
                    }
                }
            }
        }
        return var1;
    }
    
    private boolean Â(final ModelBlock p_177581_1_) {
        if (p_177581_1_ == null) {
            return false;
        }
        final ModelBlock var2 = p_177581_1_.Ó();
        return var2 == ModelBakery.Å || var2 == ModelBakery.£à || var2 == ModelBakery.µà;
    }
    
    private boolean Ý(final ModelBlock p_177587_1_) {
        if (p_177587_1_ == null) {
            return false;
        }
        final ModelBlock var2 = p_177587_1_.Ó();
        return var2 == ModelBakery.ˆà;
    }
    
    private void á() {
        for (final ResourceLocation_1975012498 var2 : this.¥Æ.values()) {
            final ModelBlock var3 = this.Ø.get(var2);
            if (this.Â(var3)) {
                final ModelBlock var4 = this.Ø­áŒŠá(var3);
                if (var4 != null) {
                    var4.Â = var2.toString();
                }
                this.Ø.put(var2, var4);
            }
            else {
                if (!this.Ý(var3)) {
                    continue;
                }
                this.Ø.put(var2, var3);
            }
        }
        for (final TextureAtlasSprite var5 : this.à.values()) {
            if (!var5.ˆÏ­()) {
                var5.á();
            }
        }
    }
    
    private ModelBlock Ø­áŒŠá(final ModelBlock p_177582_1_) {
        return this.ˆÏ­.HorizonCode_Horizon_È(this.áˆºÑ¢Õ, p_177582_1_);
    }
}
