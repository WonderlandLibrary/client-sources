/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui.advancements;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.DisplayInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.advancements.AdvancementEntryGui;
import net.minecraft.client.gui.advancements.AdvancementTabType;
import net.minecraft.client.gui.advancements.AdvancementsScreen;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;

public class AdvancementTabGui
extends AbstractGui {
    private final Minecraft minecraft;
    private final AdvancementsScreen screen;
    private final AdvancementTabType type;
    private final int index;
    private final Advancement advancement;
    private final DisplayInfo display;
    private final ItemStack icon;
    private final ITextComponent title;
    private final AdvancementEntryGui root;
    private final Map<Advancement, AdvancementEntryGui> guis = Maps.newLinkedHashMap();
    private double scrollX;
    private double scrollY;
    private int minX = Integer.MAX_VALUE;
    private int minY = Integer.MAX_VALUE;
    private int maxX = Integer.MIN_VALUE;
    private int maxY = Integer.MIN_VALUE;
    private float fade;
    private boolean centered;

    public AdvancementTabGui(Minecraft minecraft, AdvancementsScreen advancementsScreen, AdvancementTabType advancementTabType, int n, Advancement advancement, DisplayInfo displayInfo) {
        this.minecraft = minecraft;
        this.screen = advancementsScreen;
        this.type = advancementTabType;
        this.index = n;
        this.advancement = advancement;
        this.display = displayInfo;
        this.icon = displayInfo.getIcon();
        this.title = displayInfo.getTitle();
        this.root = new AdvancementEntryGui(this, minecraft, advancement, displayInfo);
        this.addGuiAdvancement(this.root, advancement);
    }

    public Advancement getAdvancement() {
        return this.advancement;
    }

    public ITextComponent getTitle() {
        return this.title;
    }

    public void renderTabSelectorBackground(MatrixStack matrixStack, int n, int n2, boolean bl) {
        this.type.renderTabSelectorBackground(matrixStack, this, n, n2, bl, this.index);
    }

    public void drawIcon(int n, int n2, ItemRenderer itemRenderer) {
        this.type.drawIcon(n, n2, this.index, itemRenderer, this.icon);
    }

    public void drawTabBackground(MatrixStack matrixStack) {
        if (!this.centered) {
            this.scrollX = 117 - (this.maxX + this.minX) / 2;
            this.scrollY = 56 - (this.maxY + this.minY) / 2;
            this.centered = true;
        }
        RenderSystem.pushMatrix();
        RenderSystem.enableDepthTest();
        RenderSystem.translatef(0.0f, 0.0f, 950.0f);
        RenderSystem.colorMask(false, false, false, false);
        AdvancementTabGui.fill(matrixStack, 4680, 2260, -4680, -2260, -16777216);
        RenderSystem.colorMask(true, true, true, true);
        RenderSystem.translatef(0.0f, 0.0f, -950.0f);
        RenderSystem.depthFunc(518);
        AdvancementTabGui.fill(matrixStack, 234, 113, 0, 0, -16777216);
        RenderSystem.depthFunc(515);
        ResourceLocation resourceLocation = this.display.getBackground();
        if (resourceLocation != null) {
            this.minecraft.getTextureManager().bindTexture(resourceLocation);
        } else {
            this.minecraft.getTextureManager().bindTexture(TextureManager.RESOURCE_LOCATION_EMPTY);
        }
        int n = MathHelper.floor(this.scrollX);
        int n2 = MathHelper.floor(this.scrollY);
        int n3 = n % 16;
        int n4 = n2 % 16;
        for (int i = -1; i <= 15; ++i) {
            for (int j = -1; j <= 8; ++j) {
                AdvancementTabGui.blit(matrixStack, n3 + 16 * i, n4 + 16 * j, 0.0f, 0.0f, 16, 16, 16, 16);
            }
        }
        this.root.drawConnectionLineToParent(matrixStack, n, n2, false);
        this.root.drawConnectionLineToParent(matrixStack, n, n2, true);
        this.root.drawAdvancement(matrixStack, n, n2);
        RenderSystem.depthFunc(518);
        RenderSystem.translatef(0.0f, 0.0f, -950.0f);
        RenderSystem.colorMask(false, false, false, false);
        AdvancementTabGui.fill(matrixStack, 4680, 2260, -4680, -2260, -16777216);
        RenderSystem.colorMask(true, true, true, true);
        RenderSystem.translatef(0.0f, 0.0f, 950.0f);
        RenderSystem.depthFunc(515);
        RenderSystem.popMatrix();
    }

    public void drawTabTooltips(MatrixStack matrixStack, int n, int n2, int n3, int n4) {
        RenderSystem.pushMatrix();
        RenderSystem.translatef(0.0f, 0.0f, 200.0f);
        AdvancementTabGui.fill(matrixStack, 0, 0, 234, 113, MathHelper.floor(this.fade * 255.0f) << 24);
        boolean bl = false;
        int n5 = MathHelper.floor(this.scrollX);
        int n6 = MathHelper.floor(this.scrollY);
        if (n > 0 && n < 234 && n2 > 0 && n2 < 113) {
            for (AdvancementEntryGui advancementEntryGui : this.guis.values()) {
                if (!advancementEntryGui.isMouseOver(n5, n6, n, n2)) continue;
                bl = true;
                advancementEntryGui.drawAdvancementHover(matrixStack, n5, n6, this.fade, n3, n4);
                break;
            }
        }
        RenderSystem.popMatrix();
        this.fade = bl ? MathHelper.clamp(this.fade + 0.02f, 0.0f, 0.3f) : MathHelper.clamp(this.fade - 0.04f, 0.0f, 1.0f);
    }

    public boolean isInsideTabSelector(int n, int n2, double d, double d2) {
        return this.type.inInsideTabSelector(n, n2, this.index, d, d2);
    }

    @Nullable
    public static AdvancementTabGui create(Minecraft minecraft, AdvancementsScreen advancementsScreen, int n, Advancement advancement) {
        if (advancement.getDisplay() == null) {
            return null;
        }
        for (AdvancementTabType advancementTabType : AdvancementTabType.values()) {
            if (n < advancementTabType.getMax()) {
                return new AdvancementTabGui(minecraft, advancementsScreen, advancementTabType, n, advancement, advancement.getDisplay());
            }
            n -= advancementTabType.getMax();
        }
        return null;
    }

    public void dragSelectedGui(double d, double d2) {
        if (this.maxX - this.minX > 234) {
            this.scrollX = MathHelper.clamp(this.scrollX + d, (double)(-(this.maxX - 234)), 0.0);
        }
        if (this.maxY - this.minY > 113) {
            this.scrollY = MathHelper.clamp(this.scrollY + d2, (double)(-(this.maxY - 113)), 0.0);
        }
    }

    public void addAdvancement(Advancement advancement) {
        if (advancement.getDisplay() != null) {
            AdvancementEntryGui advancementEntryGui = new AdvancementEntryGui(this, this.minecraft, advancement, advancement.getDisplay());
            this.addGuiAdvancement(advancementEntryGui, advancement);
        }
    }

    private void addGuiAdvancement(AdvancementEntryGui advancementEntryGui, Advancement advancement) {
        this.guis.put(advancement, advancementEntryGui);
        int n = advancementEntryGui.getX();
        int n2 = n + 28;
        int n3 = advancementEntryGui.getY();
        int n4 = n3 + 27;
        this.minX = Math.min(this.minX, n);
        this.maxX = Math.max(this.maxX, n2);
        this.minY = Math.min(this.minY, n3);
        this.maxY = Math.max(this.maxY, n4);
        for (AdvancementEntryGui advancementEntryGui2 : this.guis.values()) {
            advancementEntryGui2.attachToParent();
        }
    }

    @Nullable
    public AdvancementEntryGui getAdvancementGui(Advancement advancement) {
        return this.guis.get(advancement);
    }

    public AdvancementsScreen getScreen() {
        return this.screen;
    }
}

