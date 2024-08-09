/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.command.arguments;

import com.google.gson.JsonParseException;
import com.mojang.brigadier.Message;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import java.util.Arrays;
import java.util.Collection;
import net.minecraft.command.CommandSource;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class ComponentArgument
implements ArgumentType<ITextComponent> {
    private static final Collection<String> EXAMPLES = Arrays.asList("\"hello world\"", "\"\"", "\"{\"text\":\"hello world\"}", "[\"\"]");
    public static final DynamicCommandExceptionType COMPONENT_INVALID = new DynamicCommandExceptionType(ComponentArgument::lambda$static$0);

    private ComponentArgument() {
    }

    public static ITextComponent getComponent(CommandContext<CommandSource> commandContext, String string) {
        return commandContext.getArgument(string, ITextComponent.class);
    }

    public static ComponentArgument component() {
        return new ComponentArgument();
    }

    @Override
    public ITextComponent parse(StringReader stringReader) throws CommandSyntaxException {
        try {
            IFormattableTextComponent iFormattableTextComponent = ITextComponent.Serializer.getComponentFromReader(stringReader);
            if (iFormattableTextComponent == null) {
                throw COMPONENT_INVALID.createWithContext(stringReader, "empty");
            }
            return iFormattableTextComponent;
        } catch (JsonParseException jsonParseException) {
            String string = jsonParseException.getCause() != null ? jsonParseException.getCause().getMessage() : jsonParseException.getMessage();
            throw COMPONENT_INVALID.createWithContext(stringReader, string);
        }
    }

    @Override
    public Collection<String> getExamples() {
        return EXAMPLES;
    }

    @Override
    public Object parse(StringReader stringReader) throws CommandSyntaxException {
        return this.parse(stringReader);
    }

    private static Message lambda$static$0(Object object) {
        return new TranslationTextComponent("argument.component.invalid", object);
    }
}

