/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.text;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import javax.annotation.Nullable;
import net.minecraft.command.CommandSource;
import net.minecraft.entity.Entity;
import net.minecraft.util.text.IFormattableTextComponent;

public interface ITargetedTextComponent {
    public IFormattableTextComponent func_230535_a_(@Nullable CommandSource var1, @Nullable Entity var2, int var3) throws CommandSyntaxException;
}

