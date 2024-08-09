/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.text;

import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponent;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class StringTextComponent
extends TextComponent {
    public static final ITextComponent EMPTY = new StringTextComponent("");
    private final String text;

    public StringTextComponent(String string) {
        this.text = string;
    }

    public String getText() {
        return this.text;
    }

    @Override
    public String getUnformattedComponentText() {
        return this.text;
    }

    @Override
    public StringTextComponent copyRaw() {
        return new StringTextComponent(this.text);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (!(object instanceof StringTextComponent)) {
            return true;
        }
        StringTextComponent stringTextComponent = (StringTextComponent)object;
        return this.text.equals(stringTextComponent.getText()) && super.equals(object);
    }

    @Override
    public String toString() {
        return "TextComponent{text='" + this.text + "', siblings=" + this.siblings + ", style=" + this.getStyle() + "}";
    }

    @Override
    public TextComponent copyRaw() {
        return this.copyRaw();
    }

    @Override
    public IFormattableTextComponent copyRaw() {
        return this.copyRaw();
    }
}

