package net.minecraft.src;

import java.util.*;

class GuiSlotLanguage extends GuiSlot
{
    private ArrayList field_77251_g;
    private TreeMap field_77253_h;
    final GuiLanguage languageGui;
    
    public GuiSlotLanguage(final GuiLanguage par1GuiLanguage) {
        super(par1GuiLanguage.mc, par1GuiLanguage.width, par1GuiLanguage.height, 32, par1GuiLanguage.height - 65 + 4, 18);
        this.languageGui = par1GuiLanguage;
        this.field_77253_h = StringTranslate.getInstance().getLanguageList();
        this.field_77251_g = new ArrayList();
        for (final String var3 : this.field_77253_h.keySet()) {
            this.field_77251_g.add(var3);
        }
    }
    
    @Override
    protected int getSize() {
        return this.field_77251_g.size();
    }
    
    @Override
    protected void elementClicked(final int par1, final boolean par2) {
        StringTranslate.getInstance().setLanguage(this.field_77251_g.get(par1), false);
        this.languageGui.mc.fontRenderer.setUnicodeFlag(StringTranslate.getInstance().isUnicode());
        GuiLanguage.getGameSettings(this.languageGui).language = this.field_77251_g.get(par1);
        this.languageGui.fontRenderer.setBidiFlag(StringTranslate.isBidirectional(GuiLanguage.getGameSettings(this.languageGui).language));
        GuiLanguage.getDoneButton(this.languageGui).displayString = StringTranslate.getInstance().translateKey("gui.done");
        GuiLanguage.getGameSettings(this.languageGui).saveOptions();
    }
    
    @Override
    protected boolean isSelected(final int par1) {
        return this.field_77251_g.get(par1).equals(StringTranslate.getInstance().getCurrentLanguage());
    }
    
    @Override
    protected int getContentHeight() {
        return this.getSize() * 18;
    }
    
    @Override
    protected void drawBackground() {
        this.languageGui.drawDefaultBackground();
    }
    
    @Override
    protected void drawSlot(final int par1, final int par2, final int par3, final int par4, final Tessellator par5Tessellator) {
        this.languageGui.fontRenderer.setBidiFlag(true);
        this.languageGui.drawCenteredString(this.languageGui.fontRenderer, this.field_77253_h.get(this.field_77251_g.get(par1)), this.languageGui.width / 2, par3 + 1, 16777215);
        this.languageGui.fontRenderer.setBidiFlag(StringTranslate.isBidirectional(GuiLanguage.getGameSettings(this.languageGui).language));
    }
}
