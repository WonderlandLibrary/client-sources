/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.resources;

import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiListExtended;
import net.minecraft.client.gui.GuiScreenResourcePacks;
import net.minecraft.client.gui.GuiYesNo;
import net.minecraft.client.gui.GuiYesNoCallback;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;

public abstract class ResourcePackListEntry
implements GuiListExtended.IGuiListEntry {
    private static final ResourceLocation RESOURCE_PACKS_TEXTURE = new ResourceLocation("textures/gui/resource_packs.png");
    private static final ITextComponent INCOMPATIBLE = new TextComponentTranslation("resourcePack.incompatible", new Object[0]);
    private static final ITextComponent INCOMPATIBLE_OLD = new TextComponentTranslation("resourcePack.incompatible.old", new Object[0]);
    private static final ITextComponent INCOMPATIBLE_NEW = new TextComponentTranslation("resourcePack.incompatible.new", new Object[0]);
    protected final Minecraft mc;
    protected final GuiScreenResourcePacks resourcePacksGUI;

    public ResourcePackListEntry(GuiScreenResourcePacks resourcePacksGUIIn) {
        this.resourcePacksGUI = resourcePacksGUIIn;
        this.mc = Minecraft.getMinecraft();
    }

    @Override
    public void func_192634_a(int p_192634_1_, int p_192634_2_, int p_192634_3_, int p_192634_4_, int p_192634_5_, int p_192634_6_, int p_192634_7_, boolean p_192634_8_, float p_192634_9_) {
        int i1;
        int i = this.getResourcePackFormat();
        if (i != 3) {
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            Gui.drawRect(p_192634_2_ - 1, (double)(p_192634_3_ - 1), (double)(p_192634_2_ + p_192634_4_ - 9), (double)(p_192634_3_ + p_192634_5_ + 1), -8978432);
        }
        this.bindResourcePackIcon();
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        Gui.drawModalRectWithCustomSizedTexture(p_192634_2_, p_192634_3_, 0.0f, 0.0f, 32, 32, 32.0f, 32.0f);
        Object s = this.getResourcePackName();
        String s1 = this.getResourcePackDescription();
        if (this.showHoverOverlay() && (this.mc.gameSettings.touchscreen || p_192634_8_)) {
            this.mc.getTextureManager().bindTexture(RESOURCE_PACKS_TEXTURE);
            Gui.drawRect(p_192634_2_, (double)p_192634_3_, (double)(p_192634_2_ + 32), (double)(p_192634_3_ + 32), -1601138544);
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            int j = p_192634_6_ - p_192634_2_;
            int k = p_192634_7_ - p_192634_3_;
            if (i < 3) {
                s = INCOMPATIBLE.getFormattedText();
                s1 = INCOMPATIBLE_OLD.getFormattedText();
            } else if (i > 3) {
                s = INCOMPATIBLE.getFormattedText();
                s1 = INCOMPATIBLE_NEW.getFormattedText();
            }
            if (this.canMoveRight()) {
                if (j < 32) {
                    Gui.drawModalRectWithCustomSizedTexture(p_192634_2_, p_192634_3_, 0.0f, 32.0f, 32, 32, 256.0f, 256.0f);
                } else {
                    Gui.drawModalRectWithCustomSizedTexture(p_192634_2_, p_192634_3_, 0.0f, 0.0f, 32, 32, 256.0f, 256.0f);
                }
            } else {
                if (this.canMoveLeft()) {
                    if (j < 16) {
                        Gui.drawModalRectWithCustomSizedTexture(p_192634_2_, p_192634_3_, 32.0f, 32.0f, 32, 32, 256.0f, 256.0f);
                    } else {
                        Gui.drawModalRectWithCustomSizedTexture(p_192634_2_, p_192634_3_, 32.0f, 0.0f, 32, 32, 256.0f, 256.0f);
                    }
                }
                if (this.canMoveUp()) {
                    if (j < 32 && j > 16 && k < 16) {
                        Gui.drawModalRectWithCustomSizedTexture(p_192634_2_, p_192634_3_, 96.0f, 32.0f, 32, 32, 256.0f, 256.0f);
                    } else {
                        Gui.drawModalRectWithCustomSizedTexture(p_192634_2_, p_192634_3_, 96.0f, 0.0f, 32, 32, 256.0f, 256.0f);
                    }
                }
                if (this.canMoveDown()) {
                    if (j < 32 && j > 16 && k > 16) {
                        Gui.drawModalRectWithCustomSizedTexture(p_192634_2_, p_192634_3_, 64.0f, 32.0f, 32, 32, 256.0f, 256.0f);
                    } else {
                        Gui.drawModalRectWithCustomSizedTexture(p_192634_2_, p_192634_3_, 64.0f, 0.0f, 32, 32, 256.0f, 256.0f);
                    }
                }
            }
        }
        if ((i1 = this.mc.fontRendererObj.getStringWidth((String)s)) > 157) {
            s = this.mc.fontRendererObj.trimStringToWidth((String)s, 157 - this.mc.fontRendererObj.getStringWidth("...")) + "...";
        }
        this.mc.fontRendererObj.drawStringWithShadow((String)s, p_192634_2_ + 32 + 2, p_192634_3_ + 1, 0xFFFFFF);
        List<String> list = this.mc.fontRendererObj.listFormattedStringToWidth(s1, 157);
        for (int l = 0; l < 2 && l < list.size(); ++l) {
            this.mc.fontRendererObj.drawStringWithShadow(list.get(l), p_192634_2_ + 32 + 2, p_192634_3_ + 12 + 10 * l, 0x808080);
        }
    }

    protected abstract int getResourcePackFormat();

    protected abstract String getResourcePackDescription();

    protected abstract String getResourcePackName();

    protected abstract void bindResourcePackIcon();

    protected boolean showHoverOverlay() {
        return true;
    }

    protected boolean canMoveRight() {
        return !this.resourcePacksGUI.hasResourcePackEntry(this);
    }

    protected boolean canMoveLeft() {
        return this.resourcePacksGUI.hasResourcePackEntry(this);
    }

    protected boolean canMoveUp() {
        List<ResourcePackListEntry> list = this.resourcePacksGUI.getListContaining(this);
        int i = list.indexOf(this);
        return i > 0 && list.get(i - 1).showHoverOverlay();
    }

    protected boolean canMoveDown() {
        List<ResourcePackListEntry> list = this.resourcePacksGUI.getListContaining(this);
        int i = list.indexOf(this);
        return i >= 0 && i < list.size() - 1 && list.get(i + 1).showHoverOverlay();
    }

    @Override
    public boolean mousePressed(int slotIndex, int mouseX, int mouseY, int mouseEvent, int relativeX, int relativeY) {
        if (this.showHoverOverlay() && relativeX <= 32) {
            if (this.canMoveRight()) {
                this.resourcePacksGUI.markChanged();
                final int j = this.resourcePacksGUI.getSelectedResourcePacks().get(0).isServerPack() ? 1 : 0;
                int l = this.getResourcePackFormat();
                if (l == 3) {
                    this.resourcePacksGUI.getListContaining(this).remove(this);
                    this.resourcePacksGUI.getSelectedResourcePacks().add(j, this);
                } else {
                    String s = I18n.format("resourcePack.incompatible.confirm.title", new Object[0]);
                    String s1 = I18n.format("resourcePack.incompatible.confirm." + (l > 3 ? "new" : "old"), new Object[0]);
                    this.mc.displayGuiScreen(new GuiYesNo(new GuiYesNoCallback(){

                        @Override
                        public void confirmClicked(boolean result, int id) {
                            List<ResourcePackListEntry> list2 = ResourcePackListEntry.this.resourcePacksGUI.getListContaining(ResourcePackListEntry.this);
                            ResourcePackListEntry.this.mc.displayGuiScreen(ResourcePackListEntry.this.resourcePacksGUI);
                            if (result) {
                                list2.remove(ResourcePackListEntry.this);
                                ResourcePackListEntry.this.resourcePacksGUI.getSelectedResourcePacks().add(j, ResourcePackListEntry.this);
                            }
                        }
                    }, s, s1, 0));
                }
                return true;
            }
            if (relativeX < 16 && this.canMoveLeft()) {
                this.resourcePacksGUI.getListContaining(this).remove(this);
                this.resourcePacksGUI.getAvailableResourcePacks().add(0, this);
                this.resourcePacksGUI.markChanged();
                return true;
            }
            if (relativeX > 16 && relativeY < 16 && this.canMoveUp()) {
                List<ResourcePackListEntry> list1 = this.resourcePacksGUI.getListContaining(this);
                int k = list1.indexOf(this);
                list1.remove(this);
                list1.add(k - 1, this);
                this.resourcePacksGUI.markChanged();
                return true;
            }
            if (relativeX > 16 && relativeY > 16 && this.canMoveDown()) {
                List<ResourcePackListEntry> list = this.resourcePacksGUI.getListContaining(this);
                int i = list.indexOf(this);
                list.remove(this);
                list.add(i + 1, this);
                this.resourcePacksGUI.markChanged();
                return true;
            }
        }
        return false;
    }

    @Override
    public void func_192633_a(int p_192633_1_, int p_192633_2_, int p_192633_3_, float p_192633_4_) {
    }

    @Override
    public void mouseReleased(int slotIndex, int x, int y, int mouseEvent, int relativeX, int relativeY) {
    }

    public boolean isServerPack() {
        return false;
    }
}

