/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.command.arguments;

import com.mojang.brigadier.Message;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.command.CommandSource;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class DimensionArgument
implements ArgumentType<ResourceLocation> {
    private static final Collection<String> EXAMPLES = Stream.of(World.OVERWORLD, World.THE_NETHER).map(DimensionArgument::lambda$static$0).collect(Collectors.toList());
    private static final DynamicCommandExceptionType INVALID_DIMENSION_EXCEPTION = new DynamicCommandExceptionType(DimensionArgument::lambda$static$1);

    @Override
    public ResourceLocation parse(StringReader stringReader) throws CommandSyntaxException {
        return ResourceLocation.read(stringReader);
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> commandContext, SuggestionsBuilder suggestionsBuilder) {
        return commandContext.getSource() instanceof ISuggestionProvider ? ISuggestionProvider.func_212476_a(((ISuggestionProvider)commandContext.getSource()).func_230390_p_().stream().map(RegistryKey::getLocation), suggestionsBuilder) : Suggestions.empty();
    }

    @Override
    public Collection<String> getExamples() {
        return EXAMPLES;
    }

    public static DimensionArgument getDimension() {
        return new DimensionArgument();
    }

    public static ServerWorld getDimensionArgument(CommandContext<CommandSource> commandContext, String string) throws CommandSyntaxException {
        ResourceLocation resourceLocation = commandContext.getArgument(string, ResourceLocation.class);
        RegistryKey<World> registryKey = RegistryKey.getOrCreateKey(Registry.WORLD_KEY, resourceLocation);
        ServerWorld serverWorld = commandContext.getSource().getServer().getWorld(registryKey);
        if (serverWorld == null) {
            throw INVALID_DIMENSION_EXCEPTION.create(resourceLocation);
        }
        return serverWorld;
    }

    @Override
    public Object parse(StringReader stringReader) throws CommandSyntaxException {
        return this.parse(stringReader);
    }

    private static Message lambda$static$1(Object object) {
        return new TranslationTextComponent("argument.dimension.invalid", object);
    }

    private static String lambda$static$0(RegistryKey registryKey) {
        return registryKey.getLocation().toString();
    }
}

