package dev.luvbeeq.baritone.api.command.datatypes;

import dev.luvbeeq.baritone.api.command.exception.CommandException;
import dev.luvbeeq.baritone.api.command.helpers.TabCompleteHelper;
import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

import java.util.regex.Pattern;
import java.util.stream.Stream;

public enum BlockById implements IDatatypeFor<Block> {
    INSTANCE;

    /**
     * Matches (domain:)?name? where domain and name are [a-z0-9_.-]+ and [a-z0-9/_.-]+ respectively.
     */
    private static Pattern PATTERN = Pattern.compile("(?:[a-z0-9_.-]+:)?[a-z0-9/_.-]*");

    @Override
    public Block get(IDatatypeContext ctx) throws CommandException {
        ResourceLocation id = new ResourceLocation(ctx.getConsumer().getString());
        Block block;
        if ((block = Registry.BLOCK.getOptional(id).orElse(null)) == null) {
            throw new IllegalArgumentException("no block found by that id");
        }
        return block;
    }

    @Override
    public Stream<String> tabComplete(IDatatypeContext ctx) throws CommandException {
        String arg = ctx.getConsumer().getString();

        if (!PATTERN.matcher(arg).matches()) {
            return Stream.empty();
        }

        return new TabCompleteHelper()
                .append(
                        Registry.BLOCK.keySet()
                                .stream()
                                .map(Object::toString)
                )
                .filterPrefixNamespaced(arg)
                .sortAlphabetically()
                .stream();
    }
}
