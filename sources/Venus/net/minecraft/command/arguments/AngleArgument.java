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
import net.minecraft.command.arguments.LocationPart;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TranslationTextComponent;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class AngleArgument
implements ArgumentType<Result> {
    private static final Collection<String> field_242990_b = Arrays.asList("0", "~", "~-5");
    public static final SimpleCommandExceptionType field_242989_a = new SimpleCommandExceptionType(new TranslationTextComponent("argument.angle.incomplete"));

    public static AngleArgument func_242991_a() {
        return new AngleArgument();
    }

    public static float func_242992_a(CommandContext<CommandSource> commandContext, String string) {
        return commandContext.getArgument(string, Result.class).func_242995_a(commandContext.getSource());
    }

    @Override
    public Result parse(StringReader stringReader) throws CommandSyntaxException {
        if (!stringReader.canRead()) {
            throw field_242989_a.createWithContext(stringReader);
        }
        boolean bl = LocationPart.isRelative(stringReader);
        float f = stringReader.canRead() && stringReader.peek() != ' ' ? stringReader.readFloat() : 0.0f;
        return new Result(f, bl);
    }

    @Override
    public Collection<String> getExamples() {
        return field_242990_b;
    }

    @Override
    public Object parse(StringReader stringReader) throws CommandSyntaxException {
        return this.parse(stringReader);
    }

    public static final class Result {
        private final float field_242993_a;
        private final boolean field_242994_b;

        private Result(float f, boolean bl) {
            this.field_242993_a = f;
            this.field_242994_b = bl;
        }

        public float func_242995_a(CommandSource commandSource) {
            return MathHelper.wrapDegrees(this.field_242994_b ? this.field_242993_a + commandSource.getRotation().y : this.field_242993_a);
        }
    }
}

