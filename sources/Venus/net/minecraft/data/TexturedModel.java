/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.data;

import com.google.gson.JsonElement;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.block.Block;
import net.minecraft.data.ModelTextures;
import net.minecraft.data.ModelsUtil;
import net.minecraft.data.StockModelShapes;
import net.minecraft.util.ResourceLocation;

public class TexturedModel {
    public static final ISupplier field_240434_a_ = TexturedModel.func_240461_a_(ModelTextures::func_240345_a_, StockModelShapes.CUBE_ALL);
    public static final ISupplier field_240435_b_ = TexturedModel.func_240461_a_(ModelTextures::func_240345_a_, StockModelShapes.CUBE_MIRRORED_ALL);
    public static final ISupplier field_240436_c_ = TexturedModel.func_240461_a_(ModelTextures::func_240375_j_, StockModelShapes.CUBE_COLUMN);
    public static final ISupplier field_240437_d_ = TexturedModel.func_240461_a_(ModelTextures::func_240375_j_, StockModelShapes.CUBE_COLUMN_HORIZONTAL);
    public static final ISupplier field_240438_e_ = TexturedModel.func_240461_a_(ModelTextures::func_240379_m_, StockModelShapes.CUBE_BOTTOM_TOP);
    public static final ISupplier field_240439_f_ = TexturedModel.func_240461_a_(ModelTextures::func_240377_k_, StockModelShapes.CUBE_TOP);
    public static final ISupplier field_240440_g_ = TexturedModel.func_240461_a_(ModelTextures::func_240390_x_, StockModelShapes.ORIENTABLE);
    public static final ISupplier field_240441_h_ = TexturedModel.func_240461_a_(ModelTextures::func_240389_w_, StockModelShapes.ORIENTABLE_WITH_BOTTOM);
    public static final ISupplier field_240442_i_ = TexturedModel.func_240461_a_(ModelTextures::func_240368_f_, StockModelShapes.CARPET);
    public static final ISupplier field_240443_j_ = TexturedModel.func_240461_a_(ModelTextures::func_240371_h_, StockModelShapes.TEMPLATE_GLAZED_TERRACOTTA);
    public static final ISupplier field_240444_k_ = TexturedModel.func_240461_a_(ModelTextures::func_240373_i_, StockModelShapes.CORAL_FAN);
    public static final ISupplier field_240445_l_ = TexturedModel.func_240461_a_(ModelTextures::func_240383_q_, StockModelShapes.PARTICLE);
    public static final ISupplier field_240446_m_ = TexturedModel.func_240461_a_(ModelTextures::func_240392_z_, StockModelShapes.TEMPLATE_ANVIL);
    public static final ISupplier field_240447_n_ = TexturedModel.func_240461_a_(ModelTextures::func_240345_a_, StockModelShapes.LEAVES);
    public static final ISupplier field_240448_o_ = TexturedModel.func_240461_a_(ModelTextures::func_240386_t_, StockModelShapes.TEMPLATE_LANTERN);
    public static final ISupplier field_240449_p_ = TexturedModel.func_240461_a_(ModelTextures::func_240386_t_, StockModelShapes.TEMPLATE_HANGING_LANTERN);
    public static final ISupplier field_240450_q_ = TexturedModel.func_240461_a_(ModelTextures::func_240353_b_, StockModelShapes.TEMPLATE_SEAGRASS);
    public static final ISupplier field_240451_r_ = TexturedModel.func_240461_a_(ModelTextures::func_240378_l_, StockModelShapes.CUBE_COLUMN);
    public static final ISupplier field_240452_s_ = TexturedModel.func_240461_a_(ModelTextures::func_240378_l_, StockModelShapes.CUBE_COLUMN_HORIZONTAL);
    public static final ISupplier field_240453_t_ = TexturedModel.func_240461_a_(ModelTextures::func_240380_n_, StockModelShapes.CUBE_BOTTOM_TOP);
    public static final ISupplier field_240454_u_ = TexturedModel.func_240461_a_(ModelTextures::func_240381_o_, StockModelShapes.CUBE_COLUMN);
    private final ModelTextures field_240455_v_;
    private final ModelsUtil field_240456_w_;

    private TexturedModel(ModelTextures modelTextures, ModelsUtil modelsUtil) {
        this.field_240455_v_ = modelTextures;
        this.field_240456_w_ = modelsUtil;
    }

    public ModelsUtil func_240457_a_() {
        return this.field_240456_w_;
    }

    public ModelTextures func_240464_b_() {
        return this.field_240455_v_;
    }

    public TexturedModel func_240460_a_(Consumer<ModelTextures> consumer) {
        consumer.accept(this.field_240455_v_);
        return this;
    }

    public ResourceLocation func_240459_a_(Block block, BiConsumer<ResourceLocation, Supplier<JsonElement>> biConsumer) {
        return this.field_240456_w_.func_240228_a_(block, this.field_240455_v_, biConsumer);
    }

    public ResourceLocation func_240458_a_(Block block, String string, BiConsumer<ResourceLocation, Supplier<JsonElement>> biConsumer) {
        return this.field_240456_w_.func_240229_a_(block, string, this.field_240455_v_, biConsumer);
    }

    private static ISupplier func_240461_a_(Function<Block, ModelTextures> function, ModelsUtil modelsUtil) {
        return arg_0 -> TexturedModel.lambda$func_240461_a_$0(function, modelsUtil, arg_0);
    }

    public static TexturedModel func_240463_a_(ResourceLocation resourceLocation) {
        return new TexturedModel(ModelTextures.func_240356_b_(resourceLocation), StockModelShapes.CUBE_ALL);
    }

    private static TexturedModel lambda$func_240461_a_$0(Function function, ModelsUtil modelsUtil, Block block) {
        return new TexturedModel((ModelTextures)function.apply(block), modelsUtil);
    }

    @FunctionalInterface
    public static interface ISupplier {
        public TexturedModel get(Block var1);

        default public ResourceLocation func_240466_a_(Block block, BiConsumer<ResourceLocation, Supplier<JsonElement>> biConsumer) {
            return this.get(block).func_240459_a_(block, biConsumer);
        }

        default public ResourceLocation func_240465_a_(Block block, String string, BiConsumer<ResourceLocation, Supplier<JsonElement>> biConsumer) {
            return this.get(block).func_240458_a_(block, string, biConsumer);
        }

        default public ISupplier func_240467_a_(Consumer<ModelTextures> consumer) {
            return arg_0 -> this.lambda$func_240467_a_$0(consumer, arg_0);
        }

        private TexturedModel lambda$func_240467_a_$0(Consumer consumer, Block block) {
            return this.get(block).func_240460_a_(consumer);
        }
    }
}

