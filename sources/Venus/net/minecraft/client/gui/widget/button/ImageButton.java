/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui.widget.button;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

public class ImageButton
extends Button {
    private final ResourceLocation resourceLocation;
    private final int xTexStart;
    private final int yTexStart;
    private final int yDiffText;
    private final int textureWidth;
    private final int textureHeight;

    public ImageButton(int n, int n2, int n3, int n4, int n5, int n6, int n7, ResourceLocation resourceLocation, Button.IPressable iPressable) {
        this(n, n2, n3, n4, n5, n6, n7, resourceLocation, 256, 256, iPressable);
    }

    public ImageButton(int n, int n2, int n3, int n4, int n5, int n6, int n7, ResourceLocation resourceLocation, int n8, int n9, Button.IPressable iPressable) {
        this(n, n2, n3, n4, n5, n6, n7, resourceLocation, n8, n9, iPressable, StringTextComponent.EMPTY);
    }

    public ImageButton(int n, int n2, int n3, int n4, int n5, int n6, int n7, ResourceLocation resourceLocation, int n8, int n9, Button.IPressable iPressable, ITextComponent iTextComponent) {
        this(n, n2, n3, n4, n5, n6, n7, resourceLocation, n8, n9, iPressable, field_238486_s_, iTextComponent);
    }

    public ImageButton(int n, int n2, int n3, int n4, int n5, int n6, int n7, ResourceLocation resourceLocation, int n8, int n9, Button.IPressable iPressable, Button.ITooltip iTooltip, ITextComponent iTextComponent) {
        super(n, n2, n3, n4, iTextComponent, iPressable, iTooltip);
        this.textureWidth = n8;
        this.textureHeight = n9;
        this.xTexStart = n5;
        this.yTexStart = n6;
        this.yDiffText = n7;
        this.resourceLocation = resourceLocation;
    }

    public void setPosition(int n, int n2) {
        this.x = n;
        this.y = n2;
    }

    @Override
    public void renderButton(MatrixStack matrixStack, int n, int n2, float f) {
        Minecraft minecraft = Minecraft.getInstance();
        minecraft.getTextureManager().bindTexture(this.resourceLocation);
        int n3 = this.yTexStart;
        if (this.isHovered()) {
            n3 += this.yDiffText;
        }
        RenderSystem.enableDepthTest();
        ImageButton.blit(matrixStack, this.x, this.y, this.xTexStart, n3, this.width, this.height, this.textureWidth, this.textureHeight);
        if (this.isHovered()) {
            this.renderToolTip(matrixStack, n, n2);
        }
    }
}

