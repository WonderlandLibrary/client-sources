/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.settings;

import java.util.function.BooleanSupplier;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;

public class ToggleableKeyBinding
extends KeyBinding {
    private final BooleanSupplier getterToggle;

    public ToggleableKeyBinding(String string, int n, String string2, BooleanSupplier booleanSupplier) {
        super(string, InputMappings.Type.KEYSYM, n, string2);
        this.getterToggle = booleanSupplier;
    }

    @Override
    public void setPressed(boolean bl) {
        if (this.getterToggle.getAsBoolean()) {
            if (bl) {
                super.setPressed(!this.isKeyDown());
            }
        } else {
            super.setPressed(bl);
        }
    }
}

