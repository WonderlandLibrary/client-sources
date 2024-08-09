/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.text;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.ITextProperties;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponent;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class KeybindTextComponent
extends TextComponent {
    private static Function<String, Supplier<ITextComponent>> displaySupplierFunction = KeybindTextComponent::lambda$static$1;
    private final String keybind;
    private Supplier<ITextComponent> displaySupplier;

    public KeybindTextComponent(String string) {
        this.keybind = string;
    }

    public static void func_240696_a_(Function<String, Supplier<ITextComponent>> function) {
        displaySupplierFunction = function;
    }

    private ITextComponent func_240698_i_() {
        if (this.displaySupplier == null) {
            this.displaySupplier = displaySupplierFunction.apply(this.keybind);
        }
        return this.displaySupplier.get();
    }

    @Override
    public <T> Optional<T> func_230533_b_(ITextProperties.ITextAcceptor<T> iTextAcceptor) {
        return this.func_240698_i_().getComponent(iTextAcceptor);
    }

    @Override
    public <T> Optional<T> func_230534_b_(ITextProperties.IStyledTextAcceptor<T> iStyledTextAcceptor, Style style) {
        return this.func_240698_i_().getComponentWithStyle(iStyledTextAcceptor, style);
    }

    @Override
    public KeybindTextComponent copyRaw() {
        return new KeybindTextComponent(this.keybind);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (!(object instanceof KeybindTextComponent)) {
            return true;
        }
        KeybindTextComponent keybindTextComponent = (KeybindTextComponent)object;
        return this.keybind.equals(keybindTextComponent.keybind) && super.equals(object);
    }

    @Override
    public String toString() {
        return "KeybindComponent{keybind='" + this.keybind + "', siblings=" + this.siblings + ", style=" + this.getStyle() + "}";
    }

    public String getKeybind() {
        return this.keybind;
    }

    @Override
    public TextComponent copyRaw() {
        return this.copyRaw();
    }

    @Override
    public IFormattableTextComponent copyRaw() {
        return this.copyRaw();
    }

    private static Supplier lambda$static$1(String string) {
        return () -> KeybindTextComponent.lambda$static$0(string);
    }

    private static ITextComponent lambda$static$0(String string) {
        return new StringTextComponent(string);
    }
}

