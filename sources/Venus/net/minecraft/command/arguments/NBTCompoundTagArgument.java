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
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.JsonToNBT;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class NBTCompoundTagArgument
implements ArgumentType<CompoundNBT> {
    private static final Collection<String> EXAMPLES = Arrays.asList("{}", "{foo=bar}");

    private NBTCompoundTagArgument() {
    }

    public static NBTCompoundTagArgument nbt() {
        return new NBTCompoundTagArgument();
    }

    public static <S> CompoundNBT getNbt(CommandContext<S> commandContext, String string) {
        return commandContext.getArgument(string, CompoundNBT.class);
    }

    @Override
    public CompoundNBT parse(StringReader stringReader) throws CommandSyntaxException {
        return new JsonToNBT(stringReader).readStruct();
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

