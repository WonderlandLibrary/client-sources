/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui.widget;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;

public class ToggleWidget
extends Widget {
    protected ResourceLocation resourceLocation;
    protected boolean stateTriggered;
    protected int xTexStart;
    protected int yTexStart;
    protected int xDiffTex;
    protected int yDiffTex;

    public ToggleWidget(int n, int n2, int n3, int n4, boolean bl) {
        super(n, n2, n3, n4, StringTextComponent.EMPTY);
        this.stateTriggered = bl;
    }

    public void initTextureValues(int n, int n2, int n3, int n4, ResourceLocation resourceLocation) {
        this.xTexStart = n;
        this.yTexStart = n2;
        this.xDiffTex = n3;
        this.yDiffTex = n4;
        this.resourceLocation = resourceLocation;
    }

    public void setStateTriggered(boolean bl) {
        this.stateTriggered = bl;
    }

    public boolean isStateTriggered() {
        return this.stateTriggered;
    }

    public void setPosition(int n, int n2) {
        this.x = n;
        this.y = n2;
    }

    @Override
    public void renderButton(MatrixStack matrixStack, int n, int n2, float f) {
        Minecraft minecraft = Minecraft.getInstance();
        minecraft.getTextureManager().bindTexture(this.resourceLocation);
        RenderSystem.disableDepthTest();
        int n3 = this.xTexStart;
        int n4 = this.yTexStart;
        if (this.stateTriggered) {
            n3 += this.xDiffTex;
        }
        if (this.isHovered()) {
            n4 += this.yDiffTex;
        }
        this.blit(matrixStack, this.x, this.y, n3, n4, this.width, this.height);
        RenderSystem.enableDepthTest();
    }
}

