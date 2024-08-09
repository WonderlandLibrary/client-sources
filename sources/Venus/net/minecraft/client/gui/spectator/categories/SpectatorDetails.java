/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui.spectator.categories;

import com.google.common.base.MoreObjects;
import java.util.List;
import net.minecraft.client.gui.spectator.ISpectatorMenuObject;
import net.minecraft.client.gui.spectator.ISpectatorMenuView;
import net.minecraft.client.gui.spectator.SpectatorMenu;

public class SpectatorDetails {
    private final ISpectatorMenuView category;
    private final List<ISpectatorMenuObject> items;
    private final int selectedSlot;

    public SpectatorDetails(ISpectatorMenuView iSpectatorMenuView, List<ISpectatorMenuObject> list, int n) {
        this.category = iSpectatorMenuView;
        this.items = list;
        this.selectedSlot = n;
    }

    public ISpectatorMenuObject getObject(int n) {
        return n >= 0 && n < this.items.size() ? MoreObjects.firstNonNull(this.items.get(n), SpectatorMenu.EMPTY_SLOT) : SpectatorMenu.EMPTY_SLOT;
    }

    public int getSelectedSlot() {
        return this.selectedSlot;
    }
}

