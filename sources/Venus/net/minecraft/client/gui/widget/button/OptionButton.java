/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui.widget.button;

import java.util.List;
import java.util.Optional;
import net.minecraft.client.AbstractOption;
import net.minecraft.client.gui.IBidiTooltip;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.text.ITextComponent;
import net.optifine.gui.IOptionControl;

public class OptionButton
extends Button
implements IBidiTooltip,
IOptionControl {
    private final AbstractOption enumOptions;

    public OptionButton(int n, int n2, int n3, int n4, AbstractOption abstractOption, ITextComponent iTextComponent, Button.IPressable iPressable) {
        super(n, n2, n3, n4, iTextComponent, iPressable);
        this.enumOptions = abstractOption;
    }

    public AbstractOption func_238517_a_() {
        return this.enumOptions;
    }

    @Override
    public Optional<List<IReorderingProcessor>> func_241867_d() {
        return this.enumOptions.getOptionValues();
    }

    @Override
    public AbstractOption getControlOption() {
        return this.enumOptions;
    }
}

