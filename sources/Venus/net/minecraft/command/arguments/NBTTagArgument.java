/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.command.arguments;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.Arrays;
import java.util.Collection;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.JsonToNBT;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class NBTTagArgument
implements ArgumentType<INBT> {
    private static final Collection<String> field_218087_a = Arrays.asList("0", "0b", "0l", "0.0", "\"foo\"", "{foo=bar}", "[0]");

    private NBTTagArgument() {
    }

    public static NBTTagArgument func_218085_a() {
        return new NBTTagArgument();
    }

    public static <S> INBT func_218086_a(CommandContext<S> commandContext, String string) {
        return commandContext.getArgument(string, INBT.class);
    }

    @Override
    public INBT parse(StringReader stringReader) throws CommandSyntaxException {
        return new JsonToNBT(stringReader).readValue();
    }

    @Override
    public Collection<String> getExamples() {
        return field_218087_a;
    }

    @Override
    public Object parse(StringReader stringReader) throws CommandSyntaxException {
        return this.parse(stringReader);
    }
}

