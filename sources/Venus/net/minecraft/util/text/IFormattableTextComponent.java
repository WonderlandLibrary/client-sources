/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.text;

import java.util.function.UnaryOperator;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;

public interface IFormattableTextComponent
extends ITextComponent {
    public IFormattableTextComponent setStyle(Style var1);

    default public IFormattableTextComponent appendString(String string) {
        return this.append(new StringTextComponent(string));
    }

    public IFormattableTextComponent append(ITextComponent var1);

    default public IFormattableTextComponent modifyStyle(UnaryOperator<Style> unaryOperator) {
        this.setStyle((Style)unaryOperator.apply(this.getStyle()));
        return this;
    }

    default public IFormattableTextComponent mergeStyle(Style style) {
        this.setStyle(style.mergeStyle(this.getStyle()));
        return this;
    }

    default public IFormattableTextComponent mergeStyle(TextFormatting ... textFormattingArray) {
        this.setStyle(this.getStyle().createStyleFromFormattings(textFormattingArray));
        return this;
    }

    default public IFormattableTextComponent mergeStyle(TextFormatting textFormatting) {
        this.setStyle(this.getStyle().applyFormatting(textFormatting));
        return this;
    }
}

