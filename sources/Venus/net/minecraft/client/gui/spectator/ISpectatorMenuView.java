/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui.spectator;

import java.util.List;
import net.minecraft.client.gui.spectator.ISpectatorMenuObject;
import net.minecraft.util.text.ITextComponent;

public interface ISpectatorMenuView {
    public List<ISpectatorMenuObject> getItems();

    public ITextComponent getPrompt();
}

