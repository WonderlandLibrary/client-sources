/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.command.arguments;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.Arrays;
import java.util.Collection;
import net.minecraft.command.CommandSource;
import net.minecraft.command.arguments.ILocationArgument;
import net.minecraft.command.arguments.LocationInput;
import net.minecraft.command.arguments.LocationPart;
import net.minecraft.util.text.TranslationTextComponent;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class RotationArgument
implements ArgumentType<ILocationArgument> {
    private static final Collection<String> EXAMPLES = Arrays.asList("0 0", "~ ~", "~-5 ~5");
    public static final SimpleCommandExceptionType ROTATION_INCOMPLETE = new SimpleCommandExceptionType(new TranslationTextComponent("argument.rotation.incomplete"));

    public static RotationArgument rotation() {
        return new RotationArgument();
    }

    public static ILocationArgument getRotation(CommandContext<CommandSource> commandContext, String string) {
        return commandContext.getArgument(string, ILocationArgument.class);
    }

    @Override
    public ILocationArgument parse(StringReader stringReader) throws CommandSyntaxException {
        int n = stringReader.getCursor();
        if (!stringReader.canRead()) {
            throw ROTATION_INCOMPLETE.createWithContext(stringReader);
        }
        LocationPart locationPart = LocationPart.parseDouble(stringReader, false);
        if (stringReader.canRead() && stringReader.peek() == ' ') {
            stringReader.skip();
            LocationPart locationPart2 = LocationPart.parseDouble(stringReader, false);
            return new LocationInput(locationPart2, locationPart, new LocationPart(true, 0.0));
        }
        stringReader.setCursor(n);
        throw ROTATION_INCOMPLETE.createWithContext(stringReader);
    }

    @Override
    public Collection<String> getExamples() {
        return EXAMPLES;
    }

    @Override
    public Object parse(StringReader stringReader) throws CommandSyntaxException {
        return this.parse(stringReader);
    }
}

