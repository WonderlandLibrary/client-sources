/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui.widget.button;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class LockIconButton
extends Button {
    private boolean locked;

    public LockIconButton(int n, int n2, Button.IPressable iPressable) {
        super(n, n2, 20, 20, new TranslationTextComponent("narrator.button.difficulty_lock"), iPressable);
    }

    @Override
    protected IFormattableTextComponent getNarrationMessage() {
        return super.getNarrationMessage().appendString(". ").append(this.isLocked() ? new TranslationTextComponent("narrator.button.difficulty_lock.locked") : new TranslationTextComponent("narrator.button.difficulty_lock.unlocked"));
    }

    public boolean isLocked() {
        return this.locked;
    }

    public void setLocked(boolean bl) {
        this.locked = bl;
    }

    @Override
    public void renderButton(MatrixStack matrixStack, int n, int n2, float f) {
        Minecraft.getInstance().getTextureManager().bindTexture(Button.WIDGETS_LOCATION);
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        Icon icon = !this.active ? (this.locked ? Icon.LOCKED_DISABLED : Icon.UNLOCKED_DISABLED) : (this.isHovered() ? (this.locked ? Icon.LOCKED_HOVER : Icon.UNLOCKED_HOVER) : (this.locked ? Icon.LOCKED : Icon.UNLOCKED));
        this.blit(matrixStack, this.x, this.y, icon.getX(), icon.getY(), this.width, this.height);
    }

    static enum Icon {
        LOCKED(0, 146),
        LOCKED_HOVER(0, 166),
        LOCKED_DISABLED(0, 186),
        UNLOCKED(20, 146),
        UNLOCKED_HOVER(20, 166),
        UNLOCKED_DISABLED(20, 186);

        private final int x;
        private final int y;

        private Icon(int n2, int n3) {
            this.x = n2;
            this.y = n3;
        }

        public int getX() {
            return this.x;
        }

        public int getY() {
            return this.y;
        }
    }
}

