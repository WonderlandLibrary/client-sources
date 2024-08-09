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
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.command.CommandSource;
import net.minecraft.util.text.TranslationTextComponent;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class UUIDArgument
implements ArgumentType<UUID> {
    public static final SimpleCommandExceptionType field_239191_a_ = new SimpleCommandExceptionType(new TranslationTextComponent("argument.uuid.invalid"));
    private static final Collection<String> field_239192_b_ = Arrays.asList("dd12be42-52a9-4a91-a8a1-11c01849e498");
    private static final Pattern field_239193_c_ = Pattern.compile("^([-A-Fa-f0-9]+)");

    public static UUID func_239195_a_(CommandContext<CommandSource> commandContext, String string) {
        return commandContext.getArgument(string, UUID.class);
    }

    public static UUIDArgument func_239194_a_() {
        return new UUIDArgument();
    }

    @Override
    public UUID parse(StringReader stringReader) throws CommandSyntaxException {
        String string = stringReader.getRemaining();
        Matcher matcher = field_239193_c_.matcher(string);
        if (matcher.find()) {
            String string2 = matcher.group(1);
            try {
                UUID uUID = UUID.fromString(string2);
                stringReader.setCursor(stringReader.getCursor() + string2.length());
                return uUID;
            } catch (IllegalArgumentException illegalArgumentException) {
                // empty catch block
            }
        }
        throw field_239191_a_.create();
    }

    @Override
    public Collection<String> getExamples() {
        return field_239192_b_;
    }

    @Override
    public Object parse(StringReader stringReader) throws CommandSyntaxException {
        return this.parse(stringReader);
    }
}

