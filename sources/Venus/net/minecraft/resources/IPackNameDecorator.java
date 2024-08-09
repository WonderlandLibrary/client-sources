/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.resources;

import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

public interface IPackNameDecorator {
    public static final IPackNameDecorator PLAIN = IPackNameDecorator.func_232629_a_();
    public static final IPackNameDecorator BUILTIN = IPackNameDecorator.create("pack.source.builtin");
    public static final IPackNameDecorator WORLD = IPackNameDecorator.create("pack.source.world");
    public static final IPackNameDecorator SERVER = IPackNameDecorator.create("pack.source.server");

    public ITextComponent decorate(ITextComponent var1);

    public static IPackNameDecorator func_232629_a_() {
        return IPackNameDecorator::lambda$func_232629_a_$0;
    }

    public static IPackNameDecorator create(String string) {
        TranslationTextComponent translationTextComponent = new TranslationTextComponent(string);
        return arg_0 -> IPackNameDecorator.lambda$create$1(translationTextComponent, arg_0);
    }

    private static ITextComponent lambda$create$1(ITextComponent iTextComponent, ITextComponent iTextComponent2) {
        return new TranslationTextComponent("pack.nameAndSource", iTextComponent2, iTextComponent).mergeStyle(TextFormatting.GRAY);
    }

    private static ITextComponent lambda$func_232629_a_$0(ITextComponent iTextComponent) {
        return iTextComponent;
    }
}

