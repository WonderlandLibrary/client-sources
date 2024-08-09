/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui.spectator;

import com.google.common.base.MoreObjects;
import com.google.common.collect.Lists;
import com.mojang.blaze3d.matrix.MatrixStack;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.SpectatorGui;
import net.minecraft.client.gui.spectator.BaseSpectatorGroup;
import net.minecraft.client.gui.spectator.ISpectatorMenuObject;
import net.minecraft.client.gui.spectator.ISpectatorMenuRecipient;
import net.minecraft.client.gui.spectator.ISpectatorMenuView;
import net.minecraft.client.gui.spectator.categories.SpectatorDetails;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class SpectatorMenu {
    private static final ISpectatorMenuObject CLOSE_ITEM = new EndSpectatorObject();
    private static final ISpectatorMenuObject SCROLL_LEFT = new MoveMenuObject(-1, true);
    private static final ISpectatorMenuObject SCROLL_RIGHT_ENABLED = new MoveMenuObject(1, true);
    private static final ISpectatorMenuObject SCROLL_RIGHT_DISABLED = new MoveMenuObject(1, false);
    private static final ITextComponent field_243477_f = new TranslationTextComponent("spectatorMenu.close");
    private static final ITextComponent field_243478_g = new TranslationTextComponent("spectatorMenu.previous_page");
    private static final ITextComponent field_243479_h = new TranslationTextComponent("spectatorMenu.next_page");
    public static final ISpectatorMenuObject EMPTY_SLOT = new ISpectatorMenuObject(){

        @Override
        public void selectItem(SpectatorMenu spectatorMenu) {
        }

        @Override
        public ITextComponent getSpectatorName() {
            return StringTextComponent.EMPTY;
        }

        @Override
        public void func_230485_a_(MatrixStack matrixStack, float f, int n) {
        }

        @Override
        public boolean isEnabled() {
            return true;
        }
    };
    private final ISpectatorMenuRecipient listener;
    private ISpectatorMenuView category = new BaseSpectatorGroup();
    private int selectedSlot = -1;
    private int page;

    public SpectatorMenu(ISpectatorMenuRecipient iSpectatorMenuRecipient) {
        this.listener = iSpectatorMenuRecipient;
    }

    public ISpectatorMenuObject getItem(int n) {
        int n2 = n + this.page * 6;
        if (this.page > 0 && n == 0) {
            return SCROLL_LEFT;
        }
        if (n == 7) {
            return n2 < this.category.getItems().size() ? SCROLL_RIGHT_ENABLED : SCROLL_RIGHT_DISABLED;
        }
        if (n == 8) {
            return CLOSE_ITEM;
        }
        return n2 >= 0 && n2 < this.category.getItems().size() ? MoreObjects.firstNonNull(this.category.getItems().get(n2), EMPTY_SLOT) : EMPTY_SLOT;
    }

    public List<ISpectatorMenuObject> getItems() {
        ArrayList<ISpectatorMenuObject> arrayList = Lists.newArrayList();
        for (int i = 0; i <= 8; ++i) {
            arrayList.add(this.getItem(i));
        }
        return arrayList;
    }

    public ISpectatorMenuObject getSelectedItem() {
        return this.getItem(this.selectedSlot);
    }

    public ISpectatorMenuView getSelectedCategory() {
        return this.category;
    }

    public void selectSlot(int n) {
        ISpectatorMenuObject iSpectatorMenuObject = this.getItem(n);
        if (iSpectatorMenuObject != EMPTY_SLOT) {
            if (this.selectedSlot == n && iSpectatorMenuObject.isEnabled()) {
                iSpectatorMenuObject.selectItem(this);
            } else {
                this.selectedSlot = n;
            }
        }
    }

    public void exit() {
        this.listener.onSpectatorMenuClosed(this);
    }

    public int getSelectedSlot() {
        return this.selectedSlot;
    }

    public void selectCategory(ISpectatorMenuView iSpectatorMenuView) {
        this.category = iSpectatorMenuView;
        this.selectedSlot = -1;
        this.page = 0;
    }

    public SpectatorDetails getCurrentPage() {
        return new SpectatorDetails(this.category, this.getItems(), this.selectedSlot);
    }

    static class EndSpectatorObject
    implements ISpectatorMenuObject {
        private EndSpectatorObject() {
        }

        @Override
        public void selectItem(SpectatorMenu spectatorMenu) {
            spectatorMenu.exit();
        }

        @Override
        public ITextComponent getSpectatorName() {
            return field_243477_f;
        }

        @Override
        public void func_230485_a_(MatrixStack matrixStack, float f, int n) {
            Minecraft.getInstance().getTextureManager().bindTexture(SpectatorGui.SPECTATOR_WIDGETS);
            AbstractGui.blit(matrixStack, 0, 0, 128.0f, 0.0f, 16, 16, 256, 256);
        }

        @Override
        public boolean isEnabled() {
            return false;
        }
    }

    static class MoveMenuObject
    implements ISpectatorMenuObject {
        private final int direction;
        private final boolean enabled;

        public MoveMenuObject(int n, boolean bl) {
            this.direction = n;
            this.enabled = bl;
        }

        @Override
        public void selectItem(SpectatorMenu spectatorMenu) {
            spectatorMenu.page += this.direction;
        }

        @Override
        public ITextComponent getSpectatorName() {
            return this.direction < 0 ? field_243478_g : field_243479_h;
        }

        @Override
        public void func_230485_a_(MatrixStack matrixStack, float f, int n) {
            Minecraft.getInstance().getTextureManager().bindTexture(SpectatorGui.SPECTATOR_WIDGETS);
            if (this.direction < 0) {
                AbstractGui.blit(matrixStack, 0, 0, 144.0f, 0.0f, 16, 16, 256, 256);
            } else {
                AbstractGui.blit(matrixStack, 0, 0, 160.0f, 0.0f, 16, 16, 256, 256);
            }
        }

        @Override
        public boolean isEnabled() {
            return this.enabled;
        }
    }
}

