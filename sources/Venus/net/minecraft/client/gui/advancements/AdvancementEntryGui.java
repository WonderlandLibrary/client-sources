/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui.advancements;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.advancements.DisplayInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.advancements.AdvancementState;
import net.minecraft.client.gui.advancements.AdvancementTabGui;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.CharacterManager;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.ITextProperties;
import net.minecraft.util.text.LanguageMap;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentUtils;

public class AdvancementEntryGui
extends AbstractGui {
    private static final ResourceLocation WIDGETS = new ResourceLocation("textures/gui/advancements/widgets.png");
    private static final int[] LINE_BREAK_VALUES = new int[]{0, 10, -10, 25, -25};
    private final AdvancementTabGui guiAdvancementTab;
    private final Advancement advancement;
    private final DisplayInfo displayInfo;
    private final IReorderingProcessor title;
    private final int width;
    private final List<IReorderingProcessor> description;
    private final Minecraft minecraft;
    private AdvancementEntryGui parent;
    private final List<AdvancementEntryGui> children = Lists.newArrayList();
    private AdvancementProgress advancementProgress;
    private final int x;
    private final int y;

    public AdvancementEntryGui(AdvancementTabGui advancementTabGui, Minecraft minecraft, Advancement advancement, DisplayInfo displayInfo) {
        this.guiAdvancementTab = advancementTabGui;
        this.advancement = advancement;
        this.displayInfo = displayInfo;
        this.minecraft = minecraft;
        this.title = LanguageMap.getInstance().func_241870_a(minecraft.fontRenderer.func_238417_a_(displayInfo.getTitle(), 163));
        this.x = MathHelper.floor(displayInfo.getX() * 28.0f);
        this.y = MathHelper.floor(displayInfo.getY() * 27.0f);
        int n = advancement.getRequirementCount();
        int n2 = String.valueOf(n).length();
        int n3 = n > 1 ? minecraft.fontRenderer.getStringWidth("  ") + minecraft.fontRenderer.getStringWidth("0") * n2 * 2 + minecraft.fontRenderer.getStringWidth("/") : 0;
        int n4 = 29 + minecraft.fontRenderer.func_243245_a(this.title) + n3;
        this.description = LanguageMap.getInstance().func_244260_a(this.getDescriptionLines(TextComponentUtils.func_240648_a_(displayInfo.getDescription().deepCopy(), Style.EMPTY.setFormatting(displayInfo.getFrame().getFormat())), n4));
        for (IReorderingProcessor iReorderingProcessor : this.description) {
            n4 = Math.max(n4, minecraft.fontRenderer.func_243245_a(iReorderingProcessor));
        }
        this.width = n4 + 3 + 5;
    }

    private static float getTextWidth(CharacterManager characterManager, List<ITextProperties> list) {
        return (float)list.stream().mapToDouble(characterManager::func_238356_a_).max().orElse(0.0);
    }

    private List<ITextProperties> getDescriptionLines(ITextComponent iTextComponent, int n) {
        CharacterManager characterManager = this.minecraft.fontRenderer.getCharacterManager();
        List<ITextProperties> list = null;
        float f = Float.MAX_VALUE;
        for (int n2 : LINE_BREAK_VALUES) {
            List<ITextProperties> list2 = characterManager.func_238362_b_(iTextComponent, n - n2, Style.EMPTY);
            float f2 = Math.abs(AdvancementEntryGui.getTextWidth(characterManager, list2) - (float)n);
            if (f2 <= 10.0f) {
                return list2;
            }
            if (!(f2 < f)) continue;
            f = f2;
            list = list2;
        }
        return list;
    }

    @Nullable
    private AdvancementEntryGui getFirstVisibleParent(Advancement advancement) {
        while ((advancement = advancement.getParent()) != null && advancement.getDisplay() == null) {
        }
        return advancement != null && advancement.getDisplay() != null ? this.guiAdvancementTab.getAdvancementGui(advancement) : null;
    }

    public void drawConnectionLineToParent(MatrixStack matrixStack, int n, int n2, boolean bl) {
        if (this.parent != null) {
            int n3;
            int n4 = n + this.parent.x + 13;
            int n5 = n + this.parent.x + 26 + 4;
            int n6 = n2 + this.parent.y + 13;
            int n7 = n + this.x + 13;
            int n8 = n2 + this.y + 13;
            int n9 = n3 = bl ? -16777216 : -1;
            if (bl) {
                this.hLine(matrixStack, n5, n4, n6 - 1, n3);
                this.hLine(matrixStack, n5 + 1, n4, n6, n3);
                this.hLine(matrixStack, n5, n4, n6 + 1, n3);
                this.hLine(matrixStack, n7, n5 - 1, n8 - 1, n3);
                this.hLine(matrixStack, n7, n5 - 1, n8, n3);
                this.hLine(matrixStack, n7, n5 - 1, n8 + 1, n3);
                this.vLine(matrixStack, n5 - 1, n8, n6, n3);
                this.vLine(matrixStack, n5 + 1, n8, n6, n3);
            } else {
                this.hLine(matrixStack, n5, n4, n6, n3);
                this.hLine(matrixStack, n7, n5, n8, n3);
                this.vLine(matrixStack, n5, n8, n6, n3);
            }
        }
        for (AdvancementEntryGui advancementEntryGui : this.children) {
            advancementEntryGui.drawConnectionLineToParent(matrixStack, n, n2, bl);
        }
    }

    /*
     * WARNING - void declaration
     */
    public void drawAdvancement(MatrixStack matrixStack, int n, int n2) {
        if (!this.displayInfo.isHidden() || this.advancementProgress != null && this.advancementProgress.isDone()) {
            void var5_8;
            float f;
            float f2 = f = this.advancementProgress == null ? 0.0f : this.advancementProgress.getPercent();
            if (f >= 1.0f) {
                AdvancementState object = AdvancementState.OBTAINED;
            } else {
                AdvancementState advancementState = AdvancementState.UNOBTAINED;
            }
            this.minecraft.getTextureManager().bindTexture(WIDGETS);
            this.blit(matrixStack, n + this.x + 3, n2 + this.y, this.displayInfo.getFrame().getIcon(), 128 + var5_8.getId() * 26, 26, 26);
            this.minecraft.getItemRenderer().renderItemAndEffectIntoGuiWithoutEntity(this.displayInfo.getIcon(), n + this.x + 8, n2 + this.y + 5);
        }
        for (AdvancementEntryGui advancementEntryGui : this.children) {
            advancementEntryGui.drawAdvancement(matrixStack, n, n2);
        }
    }

    public void setAdvancementProgress(AdvancementProgress advancementProgress) {
        this.advancementProgress = advancementProgress;
    }

    public void addGuiAdvancement(AdvancementEntryGui advancementEntryGui) {
        this.children.add(advancementEntryGui);
    }

    public void drawAdvancementHover(MatrixStack matrixStack, int n, int n2, float f, int n3, int n4) {
        AdvancementState advancementState;
        AdvancementState advancementState2;
        AdvancementState advancementState3;
        boolean bl = n3 + n + this.x + this.width + 26 >= this.guiAdvancementTab.getScreen().width;
        String string = this.advancementProgress == null ? null : this.advancementProgress.getProgressText();
        int n5 = string == null ? 0 : this.minecraft.fontRenderer.getStringWidth(string);
        boolean bl2 = 113 - n2 - this.y - 26 <= 6 + this.description.size() * 9;
        float f2 = this.advancementProgress == null ? 0.0f : this.advancementProgress.getPercent();
        int n6 = MathHelper.floor(f2 * (float)this.width);
        if (f2 >= 1.0f) {
            n6 = this.width / 2;
            advancementState3 = AdvancementState.OBTAINED;
            advancementState2 = AdvancementState.OBTAINED;
            advancementState = AdvancementState.OBTAINED;
        } else if (n6 < 2) {
            n6 = this.width / 2;
            advancementState3 = AdvancementState.UNOBTAINED;
            advancementState2 = AdvancementState.UNOBTAINED;
            advancementState = AdvancementState.UNOBTAINED;
        } else if (n6 > this.width - 2) {
            n6 = this.width / 2;
            advancementState3 = AdvancementState.OBTAINED;
            advancementState2 = AdvancementState.OBTAINED;
            advancementState = AdvancementState.UNOBTAINED;
        } else {
            advancementState3 = AdvancementState.OBTAINED;
            advancementState2 = AdvancementState.UNOBTAINED;
            advancementState = AdvancementState.UNOBTAINED;
        }
        int n7 = this.width - n6;
        this.minecraft.getTextureManager().bindTexture(WIDGETS);
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.enableBlend();
        int n8 = n2 + this.y;
        int n9 = bl ? n + this.x - this.width + 26 + 6 : n + this.x;
        int n10 = 32 + this.description.size() * 9;
        if (!this.description.isEmpty()) {
            if (bl2) {
                this.drawDescriptionBox(matrixStack, n9, n8 + 26 - n10, this.width, n10, 10, 200, 26, 0, 52);
            } else {
                this.drawDescriptionBox(matrixStack, n9, n8, this.width, n10, 10, 200, 26, 0, 52);
            }
        }
        this.blit(matrixStack, n9, n8, 0, advancementState3.getId() * 26, n6, 26);
        this.blit(matrixStack, n9 + n6, n8, 200 - n7, advancementState2.getId() * 26, n7, 26);
        this.blit(matrixStack, n + this.x + 3, n2 + this.y, this.displayInfo.getFrame().getIcon(), 128 + advancementState.getId() * 26, 26, 26);
        if (bl) {
            this.minecraft.fontRenderer.func_238407_a_(matrixStack, this.title, n9 + 5, n2 + this.y + 9, -1);
            if (string != null) {
                this.minecraft.fontRenderer.drawStringWithShadow(matrixStack, string, n + this.x - n5, n2 + this.y + 9, -1);
            }
        } else {
            this.minecraft.fontRenderer.func_238407_a_(matrixStack, this.title, n + this.x + 32, n2 + this.y + 9, -1);
            if (string != null) {
                this.minecraft.fontRenderer.drawStringWithShadow(matrixStack, string, n + this.x + this.width - n5 - 5, n2 + this.y + 9, -1);
            }
        }
        if (bl2) {
            for (int i = 0; i < this.description.size(); ++i) {
                this.minecraft.fontRenderer.func_238422_b_(matrixStack, this.description.get(i), n9 + 5, n8 + 26 - n10 + 7 + i * 9, -5592406);
            }
        } else {
            for (int i = 0; i < this.description.size(); ++i) {
                this.minecraft.fontRenderer.func_238422_b_(matrixStack, this.description.get(i), n9 + 5, n2 + this.y + 9 + 17 + i * 9, -5592406);
            }
        }
        this.minecraft.getItemRenderer().renderItemAndEffectIntoGuiWithoutEntity(this.displayInfo.getIcon(), n + this.x + 8, n2 + this.y + 5);
    }

    protected void drawDescriptionBox(MatrixStack matrixStack, int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8, int n9) {
        this.blit(matrixStack, n, n2, n8, n9, n5, n5);
        this.drawDescriptionBoxBorder(matrixStack, n + n5, n2, n3 - n5 - n5, n5, n8 + n5, n9, n6 - n5 - n5, n7);
        this.blit(matrixStack, n + n3 - n5, n2, n8 + n6 - n5, n9, n5, n5);
        this.blit(matrixStack, n, n2 + n4 - n5, n8, n9 + n7 - n5, n5, n5);
        this.drawDescriptionBoxBorder(matrixStack, n + n5, n2 + n4 - n5, n3 - n5 - n5, n5, n8 + n5, n9 + n7 - n5, n6 - n5 - n5, n7);
        this.blit(matrixStack, n + n3 - n5, n2 + n4 - n5, n8 + n6 - n5, n9 + n7 - n5, n5, n5);
        this.drawDescriptionBoxBorder(matrixStack, n, n2 + n5, n5, n4 - n5 - n5, n8, n9 + n5, n6, n7 - n5 - n5);
        this.drawDescriptionBoxBorder(matrixStack, n + n5, n2 + n5, n3 - n5 - n5, n4 - n5 - n5, n8 + n5, n9 + n5, n6 - n5 - n5, n7 - n5 - n5);
        this.drawDescriptionBoxBorder(matrixStack, n + n3 - n5, n2 + n5, n5, n4 - n5 - n5, n8 + n6 - n5, n9 + n5, n6, n7 - n5 - n5);
    }

    protected void drawDescriptionBoxBorder(MatrixStack matrixStack, int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8) {
        for (int i = 0; i < n3; i += n7) {
            int n9 = n + i;
            int n10 = Math.min(n7, n3 - i);
            for (int j = 0; j < n4; j += n8) {
                int n11 = n2 + j;
                int n12 = Math.min(n8, n4 - j);
                this.blit(matrixStack, n9, n11, n5, n6, n10, n12);
            }
        }
    }

    public boolean isMouseOver(int n, int n2, int n3, int n4) {
        if (!this.displayInfo.isHidden() || this.advancementProgress != null && this.advancementProgress.isDone()) {
            int n5 = n + this.x;
            int n6 = n5 + 26;
            int n7 = n2 + this.y;
            int n8 = n7 + 26;
            return n3 >= n5 && n3 <= n6 && n4 >= n7 && n4 <= n8;
        }
        return true;
    }

    public void attachToParent() {
        if (this.parent == null && this.advancement.getParent() != null) {
            this.parent = this.getFirstVisibleParent(this.advancement);
            if (this.parent != null) {
                this.parent.addGuiAdvancement(this);
            }
        }
    }

    public int getY() {
        return this.y;
    }

    public int getX() {
        return this.x;
    }
}

