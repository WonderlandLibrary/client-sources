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

    public SpectatorDetails(ISpectatorMenuView p_i45494_1_, List<ISpectatorMenuObject> p_i45494_2_, int p_i45494_3_) {
        this.category = p_i45494_1_;
        this.items = p_i45494_2_;
        this.selectedSlot = p_i45494_3_;
    }

    public ISpectatorMenuObject getObject(int p_178680_1_) {
        return p_178680_1_ >= 0 && p_178680_1_ < this.items.size() ? MoreObjects.firstNonNull(this.items.get(p_178680_1_), SpectatorMenu.EMPTY_SLOT) : SpectatorMenu.EMPTY_SLOT;
    }

    public int getSelectedSlot() {
        return this.selectedSlot;
    }
}

