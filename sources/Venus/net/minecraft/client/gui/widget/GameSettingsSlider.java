/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui.widget;

import net.minecraft.client.GameSettings;
import net.minecraft.client.gui.widget.AbstractSlider;
import net.minecraft.util.text.StringTextComponent;

public abstract class GameSettingsSlider
extends AbstractSlider {
    protected final GameSettings settings;

    protected GameSettingsSlider(GameSettings gameSettings, int n, int n2, int n3, int n4, double d) {
        super(n, n2, n3, n4, StringTextComponent.EMPTY, d);
        this.settings = gameSettings;
    }
}

