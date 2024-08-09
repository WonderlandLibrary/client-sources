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
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.client.gui.advancements.AdvancementEntryGui;
import net.minecraft.client.gui.advancements.AdvancementTabGui;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.multiplayer.ClientAdvancementManager;
import net.minecraft.client.network.play.ClientPlayNetHandler;
import net.minecraft.network.play.client.CSeenAdvancementsPacket;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class AdvancementsScreen
extends Screen
implements ClientAdvancementManager.IListener {
    private static final ResourceLocation WINDOW = new ResourceLocation("textures/gui/advancements/window.png");
    private static final ResourceLocation TABS = new ResourceLocation("textures/gui/advancements/tabs.png");
    private static final ITextComponent SAD_LABEL = new TranslationTextComponent("advancements.sad_label");
    private static final ITextComponent EMPTY = new TranslationTextComponent("advancements.empty");
    private static final ITextComponent GUI_LABEL = new TranslationTextComponent("gui.advancements");
    private final ClientAdvancementManager clientAdvancementManager;
    private final Map<Advancement, AdvancementTabGui> tabs = Maps.newLinkedHashMap();
    private AdvancementTabGui selectedTab;
    private boolean isScrolling;

    public AdvancementsScreen(ClientAdvancementManager clientAdvancementManager) {
        super(NarratorChatListener.EMPTY);
        this.clientAdvancementManager = clientAdvancementManager;
    }

    @Override
    protected void init() {
        this.tabs.clear();
        this.selectedTab = null;
        this.clientAdvancementManager.setListener(this);
        if (this.selectedTab == null && !this.tabs.isEmpty()) {
            this.clientAdvancementManager.setSelectedTab(this.tabs.values().iterator().next().getAdvancement(), false);
        } else {
            this.clientAdvancementManager.setSelectedTab(this.selectedTab == null ? null : this.selectedTab.getAdvancement(), false);
        }
    }

    @Override
    public void onClose() {
        this.clientAdvancementManager.setListener(null);
        ClientPlayNetHandler clientPlayNetHandler = this.minecraft.getConnection();
        if (clientPlayNetHandler != null) {
            clientPlayNetHandler.sendPacket(CSeenAdvancementsPacket.closedScreen());
        }
    }

    @Override
    public boolean mouseClicked(double d, double d2, int n) {
        if (n == 0) {
            int n2 = (this.width - 252) / 2;
            int n3 = (this.height - 140) / 2;
            for (AdvancementTabGui advancementTabGui : this.tabs.values()) {
                if (!advancementTabGui.isInsideTabSelector(n2, n3, d, d2)) continue;
                this.clientAdvancementManager.setSelectedTab(advancementTabGui.getAdvancement(), false);
                break;
            }
        }
        return super.mouseClicked(d, d2, n);
    }

    @Override
    public boolean keyPressed(int n, int n2, int n3) {
        if (this.minecraft.gameSettings.keyBindAdvancements.matchesKey(n, n2)) {
            this.minecraft.displayGuiScreen(null);
            this.minecraft.mouseHelper.grabMouse();
            return false;
        }
        return super.keyPressed(n, n2, n3);
    }

    @Override
    public void render(MatrixStack matrixStack, int n, int n2, float f) {
        int n3 = (this.width - 252) / 2;
        int n4 = (this.height - 140) / 2;
        this.renderBackground(matrixStack);
        this.drawWindowBackground(matrixStack, n, n2, n3, n4);
        this.renderWindow(matrixStack, n3, n4);
        this.drawWindowTooltips(matrixStack, n, n2, n3, n4);
    }

    @Override
    public boolean mouseDragged(double d, double d2, int n, double d3, double d4) {
        if (n != 0) {
            this.isScrolling = false;
            return true;
        }
        if (!this.isScrolling) {
            this.isScrolling = true;
        } else if (this.selectedTab != null) {
            this.selectedTab.dragSelectedGui(d3, d4);
        }
        return false;
    }

    private void drawWindowBackground(MatrixStack matrixStack, int n, int n2, int n3, int n4) {
        AdvancementTabGui advancementTabGui = this.selectedTab;
        if (advancementTabGui == null) {
            AdvancementsScreen.fill(matrixStack, n3 + 9, n4 + 18, n3 + 9 + 234, n4 + 18 + 113, -16777216);
            int n5 = n3 + 9 + 117;
            AdvancementsScreen.drawCenteredString(matrixStack, this.font, EMPTY, n5, n4 + 18 + 56 - 4, -1);
            AdvancementsScreen.drawCenteredString(matrixStack, this.font, SAD_LABEL, n5, n4 + 18 + 113 - 9, -1);
        } else {
            RenderSystem.pushMatrix();
            RenderSystem.translatef(n3 + 9, n4 + 18, 0.0f);
            advancementTabGui.drawTabBackground(matrixStack);
            RenderSystem.popMatrix();
            RenderSystem.depthFunc(515);
            RenderSystem.disableDepthTest();
        }
    }

    public void renderWindow(MatrixStack matrixStack, int n, int n2) {
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.enableBlend();
        this.minecraft.getTextureManager().bindTexture(WINDOW);
        this.blit(matrixStack, n, n2, 0, 0, 252, 140);
        if (this.tabs.size() > 1) {
            this.minecraft.getTextureManager().bindTexture(TABS);
            for (AdvancementTabGui advancementTabGui : this.tabs.values()) {
                advancementTabGui.renderTabSelectorBackground(matrixStack, n, n2, advancementTabGui == this.selectedTab);
            }
            RenderSystem.enableRescaleNormal();
            RenderSystem.defaultBlendFunc();
            for (AdvancementTabGui advancementTabGui : this.tabs.values()) {
                advancementTabGui.drawIcon(n, n2, this.itemRenderer);
            }
            RenderSystem.disableBlend();
        }
        this.font.func_243248_b(matrixStack, GUI_LABEL, n + 8, n2 + 6, 0x404040);
    }

    private void drawWindowTooltips(MatrixStack matrixStack, int n, int n2, int n3, int n4) {
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        if (this.selectedTab != null) {
            RenderSystem.pushMatrix();
            RenderSystem.enableDepthTest();
            RenderSystem.translatef(n3 + 9, n4 + 18, 400.0f);
            this.selectedTab.drawTabTooltips(matrixStack, n - n3 - 9, n2 - n4 - 18, n3, n4);
            RenderSystem.disableDepthTest();
            RenderSystem.popMatrix();
        }
        if (this.tabs.size() > 1) {
            for (AdvancementTabGui advancementTabGui : this.tabs.values()) {
                if (!advancementTabGui.isInsideTabSelector(n3, n4, n, n2)) continue;
                this.renderTooltip(matrixStack, advancementTabGui.getTitle(), n, n2);
            }
        }
    }

    @Override
    public void rootAdvancementAdded(Advancement advancement) {
        AdvancementTabGui advancementTabGui = AdvancementTabGui.create(this.minecraft, this, this.tabs.size(), advancement);
        if (advancementTabGui != null) {
            this.tabs.put(advancement, advancementTabGui);
        }
    }

    @Override
    public void rootAdvancementRemoved(Advancement advancement) {
    }

    @Override
    public void nonRootAdvancementAdded(Advancement advancement) {
        AdvancementTabGui advancementTabGui = this.getTab(advancement);
        if (advancementTabGui != null) {
            advancementTabGui.addAdvancement(advancement);
        }
    }

    @Override
    public void nonRootAdvancementRemoved(Advancement advancement) {
    }

    @Override
    public void onUpdateAdvancementProgress(Advancement advancement, AdvancementProgress advancementProgress) {
        AdvancementEntryGui advancementEntryGui = this.getAdvancementGui(advancement);
        if (advancementEntryGui != null) {
            advancementEntryGui.setAdvancementProgress(advancementProgress);
        }
    }

    @Override
    public void setSelectedTab(@Nullable Advancement advancement) {
        this.selectedTab = this.tabs.get(advancement);
    }

    @Override
    public void advancementsCleared() {
        this.tabs.clear();
        this.selectedTab = null;
    }

    @Nullable
    public AdvancementEntryGui getAdvancementGui(Advancement advancement) {
        AdvancementTabGui advancementTabGui = this.getTab(advancement);
        return advancementTabGui == null ? null : advancementTabGui.getAdvancementGui(advancement);
    }

    @Nullable
    private AdvancementTabGui getTab(Advancement advancement) {
        while (advancement.getParent() != null) {
            advancement = advancement.getParent();
        }
        return this.tabs.get(advancement);
    }
}

