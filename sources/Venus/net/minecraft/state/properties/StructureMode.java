/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.state.properties;

import net.minecraft.util.IStringSerializable;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public enum StructureMode implements IStringSerializable
{
    SAVE("save"),
    LOAD("load"),
    CORNER("corner"),
    DATA("data");

    private final String name;
    private final ITextComponent field_242702_f;

    private StructureMode(String string2) {
        this.name = string2;
        this.field_242702_f = new TranslationTextComponent("structure_block.mode_info." + string2);
    }

    @Override
    public String getString() {
        return this.name;
    }

    public ITextComponent func_242703_b() {
        return this.field_242702_f;
    }
}

