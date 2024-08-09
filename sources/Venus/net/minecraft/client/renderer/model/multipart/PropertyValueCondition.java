/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.model.multipart;

import com.google.common.base.MoreObjects;
import com.google.common.base.Splitter;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.model.multipart.ICondition;
import net.minecraft.state.Property;
import net.minecraft.state.StateContainer;

public class PropertyValueCondition
implements ICondition {
    private static final Splitter SPLITTER = Splitter.on('|').omitEmptyStrings();
    private final String key;
    private final String value;

    public PropertyValueCondition(String string, String string2) {
        this.key = string;
        this.value = string2;
    }

    @Override
    public Predicate<BlockState> getPredicate(StateContainer<Block, BlockState> stateContainer) {
        Predicate<BlockState> predicate;
        List<String> list;
        boolean bl;
        Property<?> property = stateContainer.getProperty(this.key);
        if (property == null) {
            throw new RuntimeException(String.format("Unknown property '%s' on '%s'", this.key, stateContainer.getOwner().toString()));
        }
        String string = this.value;
        boolean bl2 = bl = !string.isEmpty() && string.charAt(0) == '!';
        if (bl) {
            string = string.substring(1);
        }
        if ((list = SPLITTER.splitToList(string)).isEmpty()) {
            throw new RuntimeException(String.format("Empty value '%s' for property '%s' on '%s'", this.value, this.key, stateContainer.getOwner().toString()));
        }
        if (list.size() == 1) {
            predicate = this.makePropertyPredicate(stateContainer, property, string);
        } else {
            List list2 = list.stream().map(arg_0 -> this.lambda$getPredicate$0(stateContainer, property, arg_0)).collect(Collectors.toList());
            predicate = arg_0 -> PropertyValueCondition.lambda$getPredicate$2(list2, arg_0);
        }
        return bl ? predicate.negate() : predicate;
    }

    private Predicate<BlockState> makePropertyPredicate(StateContainer<Block, BlockState> stateContainer, Property<?> property, String string) {
        Optional<?> optional = property.parseValue(string);
        if (!optional.isPresent()) {
            throw new RuntimeException(String.format("Unknown value '%s' for property '%s' on '%s' in '%s'", string, this.key, stateContainer.getOwner().toString(), this.value));
        }
        return arg_0 -> PropertyValueCondition.lambda$makePropertyPredicate$3(property, optional, arg_0);
    }

    public String toString() {
        return MoreObjects.toStringHelper(this).add("key", this.key).add("value", this.value).toString();
    }

    private static boolean lambda$makePropertyPredicate$3(Property property, Optional optional, BlockState blockState) {
        return blockState.get(property).equals(optional.get());
    }

    private static boolean lambda$getPredicate$2(List list, BlockState blockState) {
        return list.stream().anyMatch(arg_0 -> PropertyValueCondition.lambda$getPredicate$1(blockState, arg_0));
    }

    private static boolean lambda$getPredicate$1(BlockState blockState, Predicate predicate) {
        return predicate.test(blockState);
    }

    private Predicate lambda$getPredicate$0(StateContainer stateContainer, Property property, String string) {
        return this.makePropertyPredicate(stateContainer, property, string);
    }
}

