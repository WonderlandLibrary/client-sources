/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.nbt;

import java.io.DataOutput;
import java.io.IOException;
import net.minecraft.nbt.INBTType;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;

public interface INBT {
    public static final TextFormatting SYNTAX_HIGHLIGHTING_KEY = TextFormatting.AQUA;
    public static final TextFormatting SYNTAX_HIGHLIGHTING_STRING = TextFormatting.GREEN;
    public static final TextFormatting SYNTAX_HIGHLIGHTING_NUMBER = TextFormatting.GOLD;
    public static final TextFormatting SYNTAX_HIGHLIGHTING_NUMBER_TYPE = TextFormatting.RED;

    public void write(DataOutput var1) throws IOException;

    public String toString();

    public byte getId();

    public INBTType<?> getType();

    public INBT copy();

    default public String getString() {
        return this.toString();
    }

    default public ITextComponent toFormattedComponent() {
        return this.toFormattedComponent("", 0);
    }

    public ITextComponent toFormattedComponent(String var1, int var2);
}

