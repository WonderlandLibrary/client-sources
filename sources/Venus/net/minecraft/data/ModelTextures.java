/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.data;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;
import net.minecraft.block.Block;
import net.minecraft.data.StockTextureAliases;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

public class ModelTextures {
    private final Map<StockTextureAliases, ResourceLocation> field_240337_a_ = Maps.newHashMap();
    private final Set<StockTextureAliases> field_240338_b_ = Sets.newHashSet();

    public ModelTextures func_240349_a_(StockTextureAliases stockTextureAliases, ResourceLocation resourceLocation) {
        this.field_240337_a_.put(stockTextureAliases, resourceLocation);
        return this;
    }

    public Stream<StockTextureAliases> func_240342_a_() {
        return this.field_240338_b_.stream();
    }

    public ModelTextures func_240355_b_(StockTextureAliases stockTextureAliases, StockTextureAliases stockTextureAliases2) {
        this.field_240337_a_.put(stockTextureAliases2, this.field_240337_a_.get(stockTextureAliases));
        this.field_240338_b_.add(stockTextureAliases2);
        return this;
    }

    public ResourceLocation func_240348_a_(StockTextureAliases stockTextureAliases) {
        for (StockTextureAliases stockTextureAliases2 = stockTextureAliases; stockTextureAliases2 != null; stockTextureAliases2 = stockTextureAliases2.getAlias()) {
            ResourceLocation resourceLocation = this.field_240337_a_.get(stockTextureAliases2);
            if (resourceLocation == null) continue;
            return resourceLocation;
        }
        throw new IllegalStateException("Can't find texture for slot " + stockTextureAliases);
    }

    public ModelTextures func_240360_c_(StockTextureAliases stockTextureAliases, ResourceLocation resourceLocation) {
        ModelTextures modelTextures = new ModelTextures();
        modelTextures.field_240337_a_.putAll(this.field_240337_a_);
        modelTextures.field_240338_b_.addAll(this.field_240338_b_);
        modelTextures.func_240349_a_(stockTextureAliases, resourceLocation);
        return modelTextures;
    }

    public static ModelTextures func_240345_a_(Block block) {
        ResourceLocation resourceLocation = ModelTextures.func_240341_C_(block);
        return ModelTextures.func_240356_b_(resourceLocation);
    }

    public static ModelTextures func_240353_b_(Block block) {
        ResourceLocation resourceLocation = ModelTextures.func_240341_C_(block);
        return ModelTextures.func_240350_a_(resourceLocation);
    }

    public static ModelTextures func_240350_a_(ResourceLocation resourceLocation) {
        return new ModelTextures().func_240349_a_(StockTextureAliases.TEXTURE, resourceLocation);
    }

    public static ModelTextures func_240356_b_(ResourceLocation resourceLocation) {
        return new ModelTextures().func_240349_a_(StockTextureAliases.ALL, resourceLocation);
    }

    public static ModelTextures func_240358_c_(Block block) {
        return ModelTextures.func_240364_d_(StockTextureAliases.CROSS, ModelTextures.func_240341_C_(block));
    }

    public static ModelTextures func_240361_c_(ResourceLocation resourceLocation) {
        return ModelTextures.func_240364_d_(StockTextureAliases.CROSS, resourceLocation);
    }

    public static ModelTextures func_240362_d_(Block block) {
        return ModelTextures.func_240364_d_(StockTextureAliases.PLANT, ModelTextures.func_240341_C_(block));
    }

    public static ModelTextures func_240365_d_(ResourceLocation resourceLocation) {
        return ModelTextures.func_240364_d_(StockTextureAliases.PLANT, resourceLocation);
    }

    public static ModelTextures func_240366_e_(Block block) {
        return ModelTextures.func_240364_d_(StockTextureAliases.RAIL, ModelTextures.func_240341_C_(block));
    }

    public static ModelTextures func_240367_e_(ResourceLocation resourceLocation) {
        return ModelTextures.func_240364_d_(StockTextureAliases.RAIL, resourceLocation);
    }

    public static ModelTextures func_240368_f_(Block block) {
        return ModelTextures.func_240364_d_(StockTextureAliases.WOOL, ModelTextures.func_240341_C_(block));
    }

    public static ModelTextures func_240369_g_(Block block) {
        return ModelTextures.func_240364_d_(StockTextureAliases.STEM, ModelTextures.func_240341_C_(block));
    }

    public static ModelTextures func_240346_a_(Block block, Block block2) {
        return new ModelTextures().func_240349_a_(StockTextureAliases.STEM, ModelTextures.func_240341_C_(block)).func_240349_a_(StockTextureAliases.UPPERSTEM, ModelTextures.func_240341_C_(block2));
    }

    public static ModelTextures func_240371_h_(Block block) {
        return ModelTextures.func_240364_d_(StockTextureAliases.PATTERN, ModelTextures.func_240341_C_(block));
    }

    public static ModelTextures func_240373_i_(Block block) {
        return ModelTextures.func_240364_d_(StockTextureAliases.FAN, ModelTextures.func_240341_C_(block));
    }

    public static ModelTextures func_240370_g_(ResourceLocation resourceLocation) {
        return ModelTextures.func_240364_d_(StockTextureAliases.CROP, resourceLocation);
    }

    public static ModelTextures func_240354_b_(Block block, Block block2) {
        return new ModelTextures().func_240349_a_(StockTextureAliases.PANE, ModelTextures.func_240341_C_(block)).func_240349_a_(StockTextureAliases.EDGE, ModelTextures.func_240347_a_(block2, "_top"));
    }

    public static ModelTextures func_240364_d_(StockTextureAliases stockTextureAliases, ResourceLocation resourceLocation) {
        return new ModelTextures().func_240349_a_(stockTextureAliases, resourceLocation);
    }

    public static ModelTextures func_240375_j_(Block block) {
        return new ModelTextures().func_240349_a_(StockTextureAliases.SIDE, ModelTextures.func_240347_a_(block, "_side")).func_240349_a_(StockTextureAliases.END, ModelTextures.func_240347_a_(block, "_top"));
    }

    public static ModelTextures func_240377_k_(Block block) {
        return new ModelTextures().func_240349_a_(StockTextureAliases.SIDE, ModelTextures.func_240347_a_(block, "_side")).func_240349_a_(StockTextureAliases.TOP, ModelTextures.func_240347_a_(block, "_top"));
    }

    public static ModelTextures func_240378_l_(Block block) {
        return new ModelTextures().func_240349_a_(StockTextureAliases.SIDE, ModelTextures.func_240341_C_(block)).func_240349_a_(StockTextureAliases.END, ModelTextures.func_240347_a_(block, "_top"));
    }

    public static ModelTextures func_240351_a_(ResourceLocation resourceLocation, ResourceLocation resourceLocation2) {
        return new ModelTextures().func_240349_a_(StockTextureAliases.SIDE, resourceLocation).func_240349_a_(StockTextureAliases.END, resourceLocation2);
    }

    public static ModelTextures func_240379_m_(Block block) {
        return new ModelTextures().func_240349_a_(StockTextureAliases.SIDE, ModelTextures.func_240347_a_(block, "_side")).func_240349_a_(StockTextureAliases.TOP, ModelTextures.func_240347_a_(block, "_top")).func_240349_a_(StockTextureAliases.BOTTOM, ModelTextures.func_240347_a_(block, "_bottom"));
    }

    public static ModelTextures func_240380_n_(Block block) {
        ResourceLocation resourceLocation = ModelTextures.func_240341_C_(block);
        return new ModelTextures().func_240349_a_(StockTextureAliases.WALL, resourceLocation).func_240349_a_(StockTextureAliases.SIDE, resourceLocation).func_240349_a_(StockTextureAliases.TOP, ModelTextures.func_240347_a_(block, "_top")).func_240349_a_(StockTextureAliases.BOTTOM, ModelTextures.func_240347_a_(block, "_bottom"));
    }

    public static ModelTextures func_240381_o_(Block block) {
        ResourceLocation resourceLocation = ModelTextures.func_240341_C_(block);
        return new ModelTextures().func_240349_a_(StockTextureAliases.WALL, resourceLocation).func_240349_a_(StockTextureAliases.SIDE, resourceLocation).func_240349_a_(StockTextureAliases.END, ModelTextures.func_240347_a_(block, "_top"));
    }

    public static ModelTextures func_240382_p_(Block block) {
        return new ModelTextures().func_240349_a_(StockTextureAliases.TOP, ModelTextures.func_240347_a_(block, "_top")).func_240349_a_(StockTextureAliases.BOTTOM, ModelTextures.func_240347_a_(block, "_bottom"));
    }

    public static ModelTextures func_240383_q_(Block block) {
        return new ModelTextures().func_240349_a_(StockTextureAliases.PARTICLE, ModelTextures.func_240341_C_(block));
    }

    public static ModelTextures func_240372_h_(ResourceLocation resourceLocation) {
        return new ModelTextures().func_240349_a_(StockTextureAliases.PARTICLE, resourceLocation);
    }

    public static ModelTextures func_240384_r_(Block block) {
        return new ModelTextures().func_240349_a_(StockTextureAliases.FIRE, ModelTextures.func_240347_a_(block, "_0"));
    }

    public static ModelTextures func_240385_s_(Block block) {
        return new ModelTextures().func_240349_a_(StockTextureAliases.FIRE, ModelTextures.func_240347_a_(block, "_1"));
    }

    public static ModelTextures func_240386_t_(Block block) {
        return new ModelTextures().func_240349_a_(StockTextureAliases.LANTERN, ModelTextures.func_240341_C_(block));
    }

    public static ModelTextures func_240387_u_(Block block) {
        return new ModelTextures().func_240349_a_(StockTextureAliases.TORCH, ModelTextures.func_240341_C_(block));
    }

    public static ModelTextures func_240374_i_(ResourceLocation resourceLocation) {
        return new ModelTextures().func_240349_a_(StockTextureAliases.TORCH, resourceLocation);
    }

    public static ModelTextures func_240343_a_(Item item) {
        return new ModelTextures().func_240349_a_(StockTextureAliases.PARTICLE, ModelTextures.func_240357_c_(item));
    }

    public static ModelTextures func_240388_v_(Block block) {
        return new ModelTextures().func_240349_a_(StockTextureAliases.SIDE, ModelTextures.func_240347_a_(block, "_side")).func_240349_a_(StockTextureAliases.FRONT, ModelTextures.func_240347_a_(block, "_front")).func_240349_a_(StockTextureAliases.BACK, ModelTextures.func_240347_a_(block, "_back"));
    }

    public static ModelTextures func_240389_w_(Block block) {
        return new ModelTextures().func_240349_a_(StockTextureAliases.SIDE, ModelTextures.func_240347_a_(block, "_side")).func_240349_a_(StockTextureAliases.FRONT, ModelTextures.func_240347_a_(block, "_front")).func_240349_a_(StockTextureAliases.TOP, ModelTextures.func_240347_a_(block, "_top")).func_240349_a_(StockTextureAliases.BOTTOM, ModelTextures.func_240347_a_(block, "_bottom"));
    }

    public static ModelTextures func_240390_x_(Block block) {
        return new ModelTextures().func_240349_a_(StockTextureAliases.SIDE, ModelTextures.func_240347_a_(block, "_side")).func_240349_a_(StockTextureAliases.FRONT, ModelTextures.func_240347_a_(block, "_front")).func_240349_a_(StockTextureAliases.TOP, ModelTextures.func_240347_a_(block, "_top"));
    }

    public static ModelTextures func_240391_y_(Block block) {
        return new ModelTextures().func_240349_a_(StockTextureAliases.SIDE, ModelTextures.func_240347_a_(block, "_side")).func_240349_a_(StockTextureAliases.FRONT, ModelTextures.func_240347_a_(block, "_front")).func_240349_a_(StockTextureAliases.END, ModelTextures.func_240347_a_(block, "_end"));
    }

    public static ModelTextures func_240392_z_(Block block) {
        return new ModelTextures().func_240349_a_(StockTextureAliases.TOP, ModelTextures.func_240347_a_(block, "_top"));
    }

    public static ModelTextures func_240359_c_(Block block, Block block2) {
        return new ModelTextures().func_240349_a_(StockTextureAliases.PARTICLE, ModelTextures.func_240347_a_(block, "_front")).func_240349_a_(StockTextureAliases.DOWN, ModelTextures.func_240341_C_(block2)).func_240349_a_(StockTextureAliases.UP, ModelTextures.func_240347_a_(block, "_top")).func_240349_a_(StockTextureAliases.NORTH, ModelTextures.func_240347_a_(block, "_front")).func_240349_a_(StockTextureAliases.EAST, ModelTextures.func_240347_a_(block, "_side")).func_240349_a_(StockTextureAliases.SOUTH, ModelTextures.func_240347_a_(block, "_side")).func_240349_a_(StockTextureAliases.WEST, ModelTextures.func_240347_a_(block, "_front"));
    }

    public static ModelTextures func_240363_d_(Block block, Block block2) {
        return new ModelTextures().func_240349_a_(StockTextureAliases.PARTICLE, ModelTextures.func_240347_a_(block, "_front")).func_240349_a_(StockTextureAliases.DOWN, ModelTextures.func_240341_C_(block2)).func_240349_a_(StockTextureAliases.UP, ModelTextures.func_240347_a_(block, "_top")).func_240349_a_(StockTextureAliases.NORTH, ModelTextures.func_240347_a_(block, "_front")).func_240349_a_(StockTextureAliases.SOUTH, ModelTextures.func_240347_a_(block, "_front")).func_240349_a_(StockTextureAliases.EAST, ModelTextures.func_240347_a_(block, "_side")).func_240349_a_(StockTextureAliases.WEST, ModelTextures.func_240347_a_(block, "_side"));
    }

    public static ModelTextures func_240339_A_(Block block) {
        return new ModelTextures().func_240349_a_(StockTextureAliases.LIT_LOG, ModelTextures.func_240347_a_(block, "_log_lit")).func_240349_a_(StockTextureAliases.FIRE, ModelTextures.func_240347_a_(block, "_fire"));
    }

    public static ModelTextures func_240352_b_(Item item) {
        return new ModelTextures().func_240349_a_(StockTextureAliases.LAYER_ZERO, ModelTextures.func_240357_c_(item));
    }

    public static ModelTextures func_240340_B_(Block block) {
        return new ModelTextures().func_240349_a_(StockTextureAliases.LAYER_ZERO, ModelTextures.func_240341_C_(block));
    }

    public static ModelTextures func_240376_j_(ResourceLocation resourceLocation) {
        return new ModelTextures().func_240349_a_(StockTextureAliases.LAYER_ZERO, resourceLocation);
    }

    public static ResourceLocation func_240341_C_(Block block) {
        ResourceLocation resourceLocation = Registry.BLOCK.getKey(block);
        return new ResourceLocation(resourceLocation.getNamespace(), "block/" + resourceLocation.getPath());
    }

    public static ResourceLocation func_240347_a_(Block block, String string) {
        ResourceLocation resourceLocation = Registry.BLOCK.getKey(block);
        return new ResourceLocation(resourceLocation.getNamespace(), "block/" + resourceLocation.getPath() + string);
    }

    public static ResourceLocation func_240357_c_(Item item) {
        ResourceLocation resourceLocation = Registry.ITEM.getKey(item);
        return new ResourceLocation(resourceLocation.getNamespace(), "item/" + resourceLocation.getPath());
    }

    public static ResourceLocation func_240344_a_(Item item, String string) {
        ResourceLocation resourceLocation = Registry.ITEM.getKey(item);
        return new ResourceLocation(resourceLocation.getNamespace(), "item/" + resourceLocation.getPath() + string);
    }
}

