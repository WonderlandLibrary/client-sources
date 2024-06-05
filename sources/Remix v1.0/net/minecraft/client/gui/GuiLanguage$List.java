package net.minecraft.client.gui;

import net.minecraft.client.*;
import com.google.common.collect.*;
import java.util.*;
import net.minecraft.client.resources.*;
import net.minecraft.client.settings.*;

class List extends GuiSlot
{
    private final java.util.List field_148176_l;
    private final Map field_148177_m;
    
    public List(final Minecraft mcIn) {
        super(mcIn, GuiLanguage.width, GuiLanguage.height, 32, GuiLanguage.height - 65 + 4, 18);
        this.field_148176_l = Lists.newArrayList();
        this.field_148177_m = Maps.newHashMap();
        for (final Language var4 : GuiLanguage.access$000(GuiLanguage.this).getLanguages()) {
            this.field_148177_m.put(var4.getLanguageCode(), var4);
            this.field_148176_l.add(var4.getLanguageCode());
        }
    }
    
    @Override
    protected int getSize() {
        return this.field_148176_l.size();
    }
    
    @Override
    protected void elementClicked(final int slotIndex, final boolean isDoubleClick, final int mouseX, final int mouseY) {
        final Language var5 = this.field_148177_m.get(this.field_148176_l.get(slotIndex));
        GuiLanguage.access$000(GuiLanguage.this).setCurrentLanguage(var5);
        GuiLanguage.access$100(GuiLanguage.this).language = var5.getLanguageCode();
        this.mc.refreshResources();
        GuiLanguage.this.fontRendererObj.setUnicodeFlag(GuiLanguage.access$000(GuiLanguage.this).isCurrentLocaleUnicode() || GuiLanguage.access$100(GuiLanguage.this).forceUnicodeFont);
        GuiLanguage.this.fontRendererObj.setBidiFlag(GuiLanguage.access$000(GuiLanguage.this).isCurrentLanguageBidirectional());
        GuiLanguage.access$200(GuiLanguage.this).displayString = I18n.format("gui.done", new Object[0]);
        GuiLanguage.access$300(GuiLanguage.this).displayString = GuiLanguage.access$100(GuiLanguage.this).getKeyBinding(GameSettings.Options.FORCE_UNICODE_FONT);
        GuiLanguage.access$100(GuiLanguage.this).saveOptions();
    }
    
    @Override
    protected boolean isSelected(final int slotIndex) {
        return this.field_148176_l.get(slotIndex).equals(GuiLanguage.access$000(GuiLanguage.this).getCurrentLanguage().getLanguageCode());
    }
    
    @Override
    protected int getContentHeight() {
        return this.getSize() * 18;
    }
    
    @Override
    protected void drawBackground() {
        GuiLanguage.this.drawDefaultBackground();
    }
    
    @Override
    protected void drawSlot(final int p_180791_1_, final int p_180791_2_, final int p_180791_3_, final int p_180791_4_, final int p_180791_5_, final int p_180791_6_) {
        GuiLanguage.this.fontRendererObj.setBidiFlag(true);
        final GuiLanguage this$0 = GuiLanguage.this;
        Gui.drawCenteredString(GuiLanguage.this.fontRendererObj, this.field_148177_m.get(this.field_148176_l.get(p_180791_1_)).toString(), this.width / 2, p_180791_3_ + 1, 16777215);
        GuiLanguage.this.fontRendererObj.setBidiFlag(GuiLanguage.access$000(GuiLanguage.this).getCurrentLanguage().isBidirectional());
    }
}
