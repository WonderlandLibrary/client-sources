package net.minecraft.client.gui;

import net.minecraft.client.settings.*;
import com.google.common.collect.*;
import net.minecraft.client.resources.*;
import me.satisfactory.base.gui.*;
import java.util.*;

public class GuiSnooper extends GuiScreen
{
    private final GuiScreen field_146608_a;
    private final GameSettings game_settings_2;
    private final java.util.List field_146604_g;
    private final java.util.List field_146609_h;
    private String field_146610_i;
    private String[] field_146607_r;
    private List field_146606_s;
    private GuiButton field_146605_t;
    
    public GuiSnooper(final GuiScreen p_i1061_1_, final GameSettings p_i1061_2_) {
        this.field_146604_g = Lists.newArrayList();
        this.field_146609_h = Lists.newArrayList();
        this.field_146608_a = p_i1061_1_;
        this.game_settings_2 = p_i1061_2_;
    }
    
    @Override
    public void initGui() {
        this.field_146610_i = I18n.format("options.snooper.title", new Object[0]);
        final String var1 = I18n.format("options.snooper.desc", new Object[0]);
        final ArrayList var2 = Lists.newArrayList();
        for (final String var4 : this.fontRendererObj.listFormattedStringToWidth(var1, GuiSnooper.width - 30)) {
            var2.add(var4);
        }
        this.field_146607_r = var2.toArray(new String[0]);
        this.field_146604_g.clear();
        this.field_146609_h.clear();
        this.buttonList.add(this.field_146605_t = new DarkButton(1, GuiSnooper.width / 2 - 152, GuiSnooper.height - 30, 150, 20, this.game_settings_2.getKeyBinding(GameSettings.Options.SNOOPER_ENABLED)));
        this.buttonList.add(new DarkButton(2, GuiSnooper.width / 2 + 2, GuiSnooper.height - 30, 150, 20, I18n.format("gui.done", new Object[0])));
        final boolean var5 = GuiSnooper.mc.getIntegratedServer() != null && GuiSnooper.mc.getIntegratedServer().getPlayerUsageSnooper() != null;
        for (final Map.Entry var7 : new TreeMap(GuiSnooper.mc.getPlayerUsageSnooper().getCurrentStats()).entrySet()) {
            this.field_146604_g.add((var5 ? "C " : "") + var7.getKey());
            this.field_146609_h.add(this.fontRendererObj.trimStringToWidth(var7.getValue(), GuiSnooper.width - 220));
        }
        if (var5) {
            for (final Map.Entry var7 : new TreeMap(GuiSnooper.mc.getIntegratedServer().getPlayerUsageSnooper().getCurrentStats()).entrySet()) {
                this.field_146604_g.add("S " + var7.getKey());
                this.field_146609_h.add(this.fontRendererObj.trimStringToWidth(var7.getValue(), GuiSnooper.width - 220));
            }
        }
        this.field_146606_s = new List();
    }
    
    @Override
    public void handleMouseInput() {
        super.handleMouseInput();
        this.field_146606_s.func_178039_p();
    }
    
    @Override
    protected void actionPerformed(final GuiButton button) {
        if (button.enabled) {
            if (button.id == 2) {
                this.game_settings_2.saveOptions();
                this.game_settings_2.saveOptions();
                GuiSnooper.mc.displayGuiScreen(this.field_146608_a);
            }
            if (button.id == 1) {
                this.game_settings_2.setOptionValue(GameSettings.Options.SNOOPER_ENABLED, 1);
                this.field_146605_t.displayString = this.game_settings_2.getKeyBinding(GameSettings.Options.SNOOPER_ENABLED);
            }
        }
    }
    
    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        this.drawDefaultBackground();
        this.field_146606_s.drawScreen(mouseX, mouseY, partialTicks);
        Gui.drawCenteredString(this.fontRendererObj, this.field_146610_i, GuiSnooper.width / 2, 8, 16777215);
        int var4 = 22;
        for (final String var8 : this.field_146607_r) {
            Gui.drawCenteredString(this.fontRendererObj, var8, GuiSnooper.width / 2, var4, 8421504);
            var4 += this.fontRendererObj.FONT_HEIGHT;
        }
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
    
    class List extends GuiSlot
    {
        public List() {
            super(GuiSnooper.mc, GuiSnooper.width, GuiSnooper.height, 80, GuiSnooper.height - 40, GuiSnooper.this.fontRendererObj.FONT_HEIGHT + 1);
        }
        
        @Override
        protected int getSize() {
            return GuiSnooper.this.field_146604_g.size();
        }
        
        @Override
        protected void elementClicked(final int slotIndex, final boolean isDoubleClick, final int mouseX, final int mouseY) {
        }
        
        @Override
        protected boolean isSelected(final int slotIndex) {
            return false;
        }
        
        @Override
        protected void drawBackground() {
        }
        
        @Override
        protected void drawSlot(final int p_180791_1_, final int p_180791_2_, final int p_180791_3_, final int p_180791_4_, final int p_180791_5_, final int p_180791_6_) {
            GuiSnooper.this.fontRendererObj.drawString(GuiSnooper.this.field_146604_g.get(p_180791_1_), 10, p_180791_3_, 16777215);
            GuiSnooper.this.fontRendererObj.drawString(GuiSnooper.this.field_146609_h.get(p_180791_1_), 230, p_180791_3_, 16777215);
        }
        
        @Override
        protected int getScrollBarX() {
            return this.width - 10;
        }
    }
}
