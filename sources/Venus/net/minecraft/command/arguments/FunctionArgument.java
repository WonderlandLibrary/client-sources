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
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import net.minecraft.command.CommandSource;
import net.minecraft.command.FunctionObject;
import net.minecraft.tags.ITag;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class FunctionArgument
implements ArgumentType<IResult> {
    private static final Collection<String> EXAMPLES = Arrays.asList("foo", "foo:bar", "#foo");
    private static final DynamicCommandExceptionType FUNCTION_UNKNOWN_TAG = new DynamicCommandExceptionType(FunctionArgument::lambda$static$0);
    private static final DynamicCommandExceptionType FUNCTION_UNKNOWN = new DynamicCommandExceptionType(FunctionArgument::lambda$static$1);

    public static FunctionArgument function() {
        return new FunctionArgument();
    }

    @Override
    public IResult parse(StringReader stringReader) throws CommandSyntaxException {
        if (stringReader.canRead() && stringReader.peek() == '#') {
            stringReader.skip();
            ResourceLocation resourceLocation = ResourceLocation.read(stringReader);
            return new IResult(){
                final ResourceLocation val$resourcelocation1;
                final FunctionArgument this$0;
                {
                    this.this$0 = functionArgument;
                    this.val$resourcelocation1 = resourceLocation;
                }

                @Override
                public Collection<FunctionObject> create(CommandContext<CommandSource> commandContext) throws CommandSyntaxException {
                    ITag<FunctionObject> iTag = FunctionArgument.func_218111_d(commandContext, this.val$resourcelocation1);
                    return iTag.getAllElements();
                }

                @Override
                public Pair<ResourceLocation, Either<FunctionObject, ITag<FunctionObject>>> func_218102_b(CommandContext<CommandSource> commandContext) throws CommandSyntaxException {
                    return Pair.of(this.val$resourcelocation1, Either.right(FunctionArgument.func_218111_d(commandContext, this.val$resourcelocation1)));
                }
            };
        }
        ResourceLocation resourceLocation = ResourceLocation.read(stringReader);
        return new IResult(){
            final ResourceLocation val$resourcelocation;
            final FunctionArgument this$0;
            {
                this.this$0 = functionArgument;
                this.val$resourcelocation = resourceLocation;
            }

            @Override
            public Collection<FunctionObject> create(CommandContext<CommandSource> commandContext) throws CommandSyntaxException {
                return Collections.singleton(FunctionArgument.func_218108_c(commandContext, this.val$resourcelocation));
            }

            @Override
            public Pair<ResourceLocation, Either<FunctionObject, ITag<FunctionObject>>> func_218102_b(CommandContext<CommandSource> commandContext) throws CommandSyntaxException {
                return Pair.of(this.val$resourcelocation, Either.left(FunctionArgument.func_218108_c(commandContext, this.val$resourcelocation)));
            }
        };
    }

    private static FunctionObject func_218108_c(CommandContext<CommandSource> commandContext, ResourceLocation resourceLocation) throws CommandSyntaxException {
        return commandContext.getSource().getServer().getFunctionManager().get(resourceLocation).orElseThrow(() -> FunctionArgument.lambda$func_218108_c$2(resourceLocation));
    }

    private static ITag<FunctionObject> func_218111_d(CommandContext<CommandSource> commandContext, ResourceLocation resourceLocation) throws CommandSyntaxException {
        ITag<FunctionObject> iTag = commandContext.getSource().getServer().getFunctionManager().getFunctionTag(resourceLocation);
        if (iTag == null) {
            throw FUNCTION_UNKNOWN_TAG.create(resourceLocation.toString());
        }
        return iTag;
    }

    public static Collection<FunctionObject> getFunctions(CommandContext<CommandSource> commandContext, String string) throws CommandSyntaxException {
        return commandContext.getArgument(string, IResult.class).create(commandContext);
    }

    public static Pair<ResourceLocation, Either<FunctionObject, ITag<FunctionObject>>> func_218110_b(CommandContext<CommandSource> commandContext, String string) throws CommandSyntaxException {
        return commandContext.getArgument(string, IResult.class).func_218102_b(commandContext);
    }

    @Override
    public Collection<String> getExamples() {
        return EXAMPLES;
    }

    @Override
    public Object parse(StringReader stringReader) throws CommandSyntaxException {
        return this.parse(stringReader);
    }

    private static CommandSyntaxException lambda$func_218108_c$2(ResourceLocation resourceLocation) {
        return FUNCTION_UNKNOWN.create(resourceLocation.toString());
    }

    private static Message lambda$static$1(Object object) {
        return new TranslationTextComponent("arguments.function.unknown", object);
    }

    private static Message lambda$static$0(Object object) {
        return new TranslationTextComponent("arguments.function.tag.unknown", object);
    }

    public static interface IResult {
        public Collection<FunctionObject> create(CommandContext<CommandSource> var1) throws CommandSyntaxException;

        public Pair<ResourceLocation, Either<FunctionObject, ITag<FunctionObject>>> func_218102_b(CommandContext<CommandSource> var1) throws CommandSyntaxException;
    }
}

