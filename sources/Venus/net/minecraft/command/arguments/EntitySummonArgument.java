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
import java.util.Arrays;
import java.util.Collection;
import net.minecraft.command.CommandSource;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.TranslationTextComponent;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class EntitySummonArgument
implements ArgumentType<ResourceLocation> {
    private static final Collection<String> EXAMPLES = Arrays.asList("minecraft:pig", "cow");
    public static final DynamicCommandExceptionType ENTITY_UNKNOWN_TYPE = new DynamicCommandExceptionType(EntitySummonArgument::lambda$static$0);

    public static EntitySummonArgument entitySummon() {
        return new EntitySummonArgument();
    }

    public static ResourceLocation getEntityId(CommandContext<CommandSource> commandContext, String string) throws CommandSyntaxException {
        return EntitySummonArgument.checkIfEntityExists(commandContext.getArgument(string, ResourceLocation.class));
    }

    private static ResourceLocation checkIfEntityExists(ResourceLocation resourceLocation) throws CommandSyntaxException {
        Registry.ENTITY_TYPE.getOptional(resourceLocation).filter(EntityType::isSummonable).orElseThrow(() -> EntitySummonArgument.lambda$checkIfEntityExists$1(resourceLocation));
        return resourceLocation;
    }

    @Override
    public ResourceLocation parse(StringReader stringReader) throws CommandSyntaxException {
        return EntitySummonArgument.checkIfEntityExists(ResourceLocation.read(stringReader));
    }

    @Override
    public Collection<String> getExamples() {
        return EXAMPLES;
    }

    @Override
    public Object parse(StringReader stringReader) throws CommandSyntaxException {
        return this.parse(stringReader);
    }

    private static CommandSyntaxException lambda$checkIfEntityExists$1(ResourceLocation resourceLocation) {
        return ENTITY_UNKNOWN_TYPE.create(resourceLocation);
    }

    private static Message lambda$static$0(Object object) {
        return new TranslationTextComponent("entity.notFound", object);
    }
}

