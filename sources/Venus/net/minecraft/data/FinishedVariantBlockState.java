/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.data;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Pair;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Stream;
import net.minecraft.block.Block;
import net.minecraft.data.BlockModelDefinition;
import net.minecraft.data.BlockStateVariantBuilder;
import net.minecraft.data.IFinishedBlockState;
import net.minecraft.data.VariantPropertyBuilder;
import net.minecraft.state.Property;
import net.minecraft.util.Util;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class FinishedVariantBlockState
implements IFinishedBlockState {
    private final Block field_240115_a_;
    private final List<BlockModelDefinition> field_240116_b_;
    private final Set<Property<?>> field_240117_c_ = Sets.newHashSet();
    private final List<BlockStateVariantBuilder> field_240118_d_ = Lists.newArrayList();

    private FinishedVariantBlockState(Block block, List<BlockModelDefinition> list) {
        this.field_240115_a_ = block;
        this.field_240116_b_ = list;
    }

    public FinishedVariantBlockState func_240125_a_(BlockStateVariantBuilder blockStateVariantBuilder) {
        blockStateVariantBuilder.func_230527_b_().forEach(this::lambda$func_240125_a_$0);
        this.field_240118_d_.add(blockStateVariantBuilder);
        return this;
    }

    @Override
    public JsonElement get() {
        Stream<Pair<VariantPropertyBuilder, List<BlockModelDefinition>>> stream = Stream.of(Pair.of(VariantPropertyBuilder.func_240187_a_(), this.field_240116_b_));
        for (BlockStateVariantBuilder object2 : this.field_240118_d_) {
            Map<VariantPropertyBuilder, List<BlockModelDefinition>> map = object2.func_240132_a_();
            stream = stream.flatMap(arg_0 -> FinishedVariantBlockState.lambda$get$2(map, arg_0));
        }
        TreeMap treeMap = new TreeMap();
        stream.forEach(arg_0 -> FinishedVariantBlockState.lambda$get$3(treeMap, arg_0));
        JsonObject jsonObject = new JsonObject();
        jsonObject.add("variants", Util.make(new JsonObject(), arg_0 -> FinishedVariantBlockState.lambda$get$4(treeMap, arg_0)));
        return jsonObject;
    }

    private static List<BlockModelDefinition> func_240127_a_(List<BlockModelDefinition> list, List<BlockModelDefinition> list2) {
        ImmutableList.Builder builder = ImmutableList.builder();
        list.forEach(arg_0 -> FinishedVariantBlockState.lambda$func_240127_a_$6(list2, builder, arg_0));
        return builder.build();
    }

    @Override
    public Block func_230524_a_() {
        return this.field_240115_a_;
    }

    public static FinishedVariantBlockState func_240119_a_(Block block) {
        return new FinishedVariantBlockState(block, ImmutableList.of(BlockModelDefinition.getNewModelDefinition()));
    }

    public static FinishedVariantBlockState func_240120_a_(Block block, BlockModelDefinition blockModelDefinition) {
        return new FinishedVariantBlockState(block, ImmutableList.of(blockModelDefinition));
    }

    public static FinishedVariantBlockState func_240121_a_(Block block, BlockModelDefinition ... blockModelDefinitionArray) {
        return new FinishedVariantBlockState(block, ImmutableList.copyOf(blockModelDefinitionArray));
    }

    @Override
    public Object get() {
        return this.get();
    }

    private static void lambda$func_240127_a_$6(List list, ImmutableList.Builder builder, BlockModelDefinition blockModelDefinition) {
        list.forEach(arg_0 -> FinishedVariantBlockState.lambda$func_240127_a_$5(builder, blockModelDefinition, arg_0));
    }

    private static void lambda$func_240127_a_$5(ImmutableList.Builder builder, BlockModelDefinition blockModelDefinition, BlockModelDefinition blockModelDefinition2) {
        builder.add(BlockModelDefinition.mergeDefinitions(blockModelDefinition, blockModelDefinition2));
    }

    private static void lambda$get$4(Map map, JsonObject jsonObject) {
        map.forEach(jsonObject::add);
    }

    private static void lambda$get$3(Map map, Pair pair) {
        JsonElement jsonElement = map.put(((VariantPropertyBuilder)pair.getFirst()).func_240191_b_(), BlockModelDefinition.serialize((List)pair.getSecond()));
    }

    private static Stream lambda$get$2(Map map, Pair pair) {
        return map.entrySet().stream().map(arg_0 -> FinishedVariantBlockState.lambda$get$1(pair, arg_0));
    }

    private static Pair lambda$get$1(Pair pair, Map.Entry entry) {
        VariantPropertyBuilder variantPropertyBuilder = ((VariantPropertyBuilder)pair.getFirst()).func_240189_a_((VariantPropertyBuilder)entry.getKey());
        List<BlockModelDefinition> list = FinishedVariantBlockState.func_240127_a_((List)pair.getSecond(), (List)entry.getValue());
        return Pair.of(variantPropertyBuilder, list);
    }

    private void lambda$func_240125_a_$0(Property property) {
        if (this.field_240115_a_.getStateContainer().getProperty(property.getName()) != property) {
            throw new IllegalStateException("Property " + property + " is not defined for block " + this.field_240115_a_);
        }
        if (!this.field_240117_c_.add(property)) {
            throw new IllegalStateException("Values of property " + property + " already defined for block " + this.field_240115_a_);
        }
    }
}

