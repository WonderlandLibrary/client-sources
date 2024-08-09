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
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import net.minecraft.command.CommandSource;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.TranslationTextComponent;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class EnchantmentArgument
implements ArgumentType<Enchantment> {
    private static final Collection<String> EXAMPLES = Arrays.asList("unbreaking", "silk_touch");
    public static final DynamicCommandExceptionType ENCHANTMENT_UNKNOWN = new DynamicCommandExceptionType(EnchantmentArgument::lambda$static$0);

    public static EnchantmentArgument enchantment() {
        return new EnchantmentArgument();
    }

    public static Enchantment getEnchantment(CommandContext<CommandSource> commandContext, String string) {
        return commandContext.getArgument(string, Enchantment.class);
    }

    @Override
    public Enchantment parse(StringReader stringReader) throws CommandSyntaxException {
        ResourceLocation resourceLocation = ResourceLocation.read(stringReader);
        return Registry.ENCHANTMENT.getOptional(resourceLocation).orElseThrow(() -> EnchantmentArgument.lambda$parse$1(resourceLocation));
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> commandContext, SuggestionsBuilder suggestionsBuilder) {
        return ISuggestionProvider.suggestIterable(Registry.ENCHANTMENT.keySet(), suggestionsBuilder);
    }

    @Override
    public Collection<String> getExamples() {
        return EXAMPLES;
    }

    @Override
    public Object parse(StringReader stringReader) throws CommandSyntaxException {
        return this.parse(stringReader);
    }

    private static CommandSyntaxException lambda$parse$1(ResourceLocation resourceLocation) {
        return ENCHANTMENT_UNKNOWN.create(resourceLocation);
    }

    private static Message lambda$static$0(Object object) {
        return new TranslationTextComponent("enchantment.unknown", object);
    }
}

