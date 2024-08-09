/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.data;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.List;
import java.util.function.Supplier;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.data.BlockModelDefinition;
import net.minecraft.data.IFinishedBlockState;
import net.minecraft.data.IMultiPartPredicateBuilder;
import net.minecraft.state.StateContainer;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class FinishedMultiPartBlockState
implements IFinishedBlockState {
    private final Block field_240104_a_;
    private final List<Part> field_240105_b_ = Lists.newArrayList();

    private FinishedMultiPartBlockState(Block block) {
        this.field_240104_a_ = block;
    }

    @Override
    public Block func_230524_a_() {
        return this.field_240104_a_;
    }

    public static FinishedMultiPartBlockState func_240106_a_(Block block) {
        return new FinishedMultiPartBlockState(block);
    }

    public FinishedMultiPartBlockState func_240112_a_(List<BlockModelDefinition> list) {
        this.field_240105_b_.add(new Part(list));
        return this;
    }

    public FinishedMultiPartBlockState func_240111_a_(BlockModelDefinition blockModelDefinition) {
        return this.func_240112_a_(ImmutableList.of(blockModelDefinition));
    }

    public FinishedMultiPartBlockState func_240109_a_(IMultiPartPredicateBuilder iMultiPartPredicateBuilder, List<BlockModelDefinition> list) {
        this.field_240105_b_.add(new ConditionalPart(iMultiPartPredicateBuilder, list));
        return this;
    }

    public FinishedMultiPartBlockState func_240110_a_(IMultiPartPredicateBuilder iMultiPartPredicateBuilder, BlockModelDefinition ... blockModelDefinitionArray) {
        return this.func_240109_a_(iMultiPartPredicateBuilder, ImmutableList.copyOf(blockModelDefinitionArray));
    }

    public FinishedMultiPartBlockState func_240108_a_(IMultiPartPredicateBuilder iMultiPartPredicateBuilder, BlockModelDefinition blockModelDefinition) {
        return this.func_240109_a_(iMultiPartPredicateBuilder, ImmutableList.of(blockModelDefinition));
    }

    @Override
    public JsonElement get() {
        StateContainer<Block, BlockState> stateContainer = this.field_240104_a_.getStateContainer();
        this.field_240105_b_.forEach(arg_0 -> FinishedMultiPartBlockState.lambda$get$0(stateContainer, arg_0));
        JsonArray jsonArray = new JsonArray();
        this.field_240105_b_.stream().map(Part::get).forEach(jsonArray::add);
        JsonObject jsonObject = new JsonObject();
        jsonObject.add("multipart", jsonArray);
        return jsonObject;
    }

    @Override
    public Object get() {
        return this.get();
    }

    private static void lambda$get$0(StateContainer stateContainer, Part part) {
        part.func_230525_a_(stateContainer);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    static class Part
    implements Supplier<JsonElement> {
        private final List<BlockModelDefinition> field_240114_a_;

        private Part(List<BlockModelDefinition> list) {
            this.field_240114_a_ = list;
        }

        public void func_230525_a_(StateContainer<?, ?> stateContainer) {
        }

        public void func_230526_a_(JsonObject jsonObject) {
        }

        @Override
        public JsonElement get() {
            JsonObject jsonObject = new JsonObject();
            this.func_230526_a_(jsonObject);
            jsonObject.add("apply", BlockModelDefinition.serialize(this.field_240114_a_));
            return jsonObject;
        }

        @Override
        public Object get() {
            return this.get();
        }
    }

    static class ConditionalPart
    extends Part {
        private final IMultiPartPredicateBuilder field_240113_a_;

        private ConditionalPart(IMultiPartPredicateBuilder iMultiPartPredicateBuilder, List<BlockModelDefinition> list) {
            super(list);
            this.field_240113_a_ = iMultiPartPredicateBuilder;
        }

        @Override
        public void func_230525_a_(StateContainer<?, ?> stateContainer) {
            this.field_240113_a_.func_230523_a_(stateContainer);
        }

        @Override
        public void func_230526_a_(JsonObject jsonObject) {
            jsonObject.add("when", (JsonElement)this.field_240113_a_.get());
        }
    }
}

