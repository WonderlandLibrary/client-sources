/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.realms;

import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

public interface IErrorConsumer {
    public void func_230434_a_(ITextComponent var1);

    default public void func_237703_a_(String string) {
        this.func_230434_a_(new StringTextComponent(string));
    }
}

