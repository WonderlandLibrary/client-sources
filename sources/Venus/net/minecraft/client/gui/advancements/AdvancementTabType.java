/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui.advancements;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.item.ItemStack;

enum AdvancementTabType {
    ABOVE(0, 0, 28, 32, 8),
    BELOW(84, 0, 28, 32, 8),
    LEFT(0, 64, 32, 28, 5),
    RIGHT(96, 64, 32, 28, 5);

    private final int textureX;
    private final int textureY;
    private final int width;
    private final int height;
    private final int max;

    private AdvancementTabType(int n2, int n3, int n4, int n5, int n6) {
        this.textureX = n2;
        this.textureY = n3;
        this.width = n4;
        this.height = n5;
        this.max = n6;
    }

    public int getMax() {
        return this.max;
    }

    public void renderTabSelectorBackground(MatrixStack matrixStack, AbstractGui abstractGui, int n, int n2, boolean bl, int n3) {
        int n4 = this.textureX;
        if (n3 > 0) {
            n4 += this.width;
        }
        if (n3 == this.max - 1) {
            n4 += this.width;
        }
        int n5 = bl ? this.textureY + this.height : this.textureY;
        abstractGui.blit(matrixStack, n + this.getX(n3), n2 + this.getY(n3), n4, n5, this.width, this.height);
    }

    public void drawIcon(int n, int n2, int n3, ItemRenderer itemRenderer, ItemStack itemStack) {
        int n4 = n + this.getX(n3);
        int n5 = n2 + this.getY(n3);
        switch (1.$SwitchMap$net$minecraft$client$gui$advancements$AdvancementTabType[this.ordinal()]) {
            case 1: {
                n4 += 6;
                n5 += 9;
                break;
            }
            case 2: {
                n4 += 6;
                n5 += 6;
                break;
            }
            case 3: {
                n4 += 10;
                n5 += 5;
                break;
            }
            case 4: {
                n4 += 6;
                n5 += 5;
            }
        }
        itemRenderer.renderItemAndEffectIntoGuiWithoutEntity(itemStack, n4, n5);
    }

    public int getX(int n) {
        switch (1.$SwitchMap$net$minecraft$client$gui$advancements$AdvancementTabType[this.ordinal()]) {
            case 1: {
                return (this.width + 4) * n;
            }
            case 2: {
                return (this.width + 4) * n;
            }
            case 3: {
                return -this.width + 4;
            }
            case 4: {
                return 1;
            }
        }
        throw new UnsupportedOperationException("Don't know what this tab type is!" + this);
    }

    public int getY(int n) {
        switch (1.$SwitchMap$net$minecraft$client$gui$advancements$AdvancementTabType[this.ordinal()]) {
            case 1: {
                return -this.height + 4;
            }
            case 2: {
                return 1;
            }
            case 3: {
                return this.height * n;
            }
            case 4: {
                return this.height * n;
            }
        }
        throw new UnsupportedOperationException("Don't know what this tab type is!" + this);
    }

    public boolean inInsideTabSelector(int n, int n2, int n3, double d, double d2) {
        int n4 = n + this.getX(n3);
        int n5 = n2 + this.getY(n3);
        return d > (double)n4 && d < (double)(n4 + this.width) && d2 > (double)n5 && d2 < (double)(n5 + this.height);
    }
}

