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
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.TranslationTextComponent;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class ParticleArgument
implements ArgumentType<IParticleData> {
    private static final Collection<String> EXAMPLES = Arrays.asList("foo", "foo:bar", "particle with options");
    public static final DynamicCommandExceptionType PARTICLE_NOT_FOUND = new DynamicCommandExceptionType(ParticleArgument::lambda$static$0);

    public static ParticleArgument particle() {
        return new ParticleArgument();
    }

    public static IParticleData getParticle(CommandContext<CommandSource> commandContext, String string) {
        return commandContext.getArgument(string, IParticleData.class);
    }

    @Override
    public IParticleData parse(StringReader stringReader) throws CommandSyntaxException {
        return ParticleArgument.parseParticle(stringReader);
    }

    @Override
    public Collection<String> getExamples() {
        return EXAMPLES;
    }

    public static IParticleData parseParticle(StringReader stringReader) throws CommandSyntaxException {
        ResourceLocation resourceLocation = ResourceLocation.read(stringReader);
        ParticleType<?> particleType = Registry.PARTICLE_TYPE.getOptional(resourceLocation).orElseThrow(() -> ParticleArgument.lambda$parseParticle$1(resourceLocation));
        return ParticleArgument.deserializeParticle(stringReader, particleType);
    }

    private static <T extends IParticleData> T deserializeParticle(StringReader stringReader, ParticleType<T> particleType) throws CommandSyntaxException {
        return particleType.getDeserializer().deserialize(particleType, stringReader);
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> commandContext, SuggestionsBuilder suggestionsBuilder) {
        return ISuggestionProvider.suggestIterable(Registry.PARTICLE_TYPE.keySet(), suggestionsBuilder);
    }

    @Override
    public Object parse(StringReader stringReader) throws CommandSyntaxException {
        return this.parse(stringReader);
    }

    private static CommandSyntaxException lambda$parseParticle$1(ResourceLocation resourceLocation) {
        return PARTICLE_NOT_FOUND.create(resourceLocation);
    }

    private static Message lambda$static$0(Object object) {
        return new TranslationTextComponent("particle.notFound", object);
    }
}

