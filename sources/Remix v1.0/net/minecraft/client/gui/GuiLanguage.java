package net.minecraft.client.gui;

import net.minecraft.client.settings.*;
import net.minecraft.client.*;
import com.google.common.collect.*;
import net.minecraft.client.resources.*;
import java.util.*;

public class GuiLanguage extends GuiScreen
{
    private final GameSettings game_settings_3;
    private final LanguageManager field_146454_h;
    protected GuiScreen field_146453_a;
    private List field_146450_f;
    private GuiOptionButton field_146455_i;
    private GuiOptionButton field_146452_r;
    
    public GuiLanguage(final GuiScreen p_i1043_1_, final GameSettings p_i1043_2_, final LanguageManager p_i1043_3_) {
        this.field_146453_a = p_i1043_1_;
        this.game_settings_3 = p_i1043_2_;
        this.field_146454_h = p_i1043_3_;
    }
    
    @Override
    public void initGui() {
        this.buttonList.add(this.field_146455_i = new GuiOptionButton(100, GuiLanguage.width / 2 - 155, GuiLanguage.height - 38, GameSettings.Options.FORCE_UNICODE_FONT, this.game_settings_3.getKeyBinding(GameSettings.Options.FORCE_UNICODE_FONT)));
        this.buttonList.add(this.field_146452_r = new GuiOptionButton(6, GuiLanguage.width / 2 - 155 + 160, GuiLanguage.height - 38, I18n.format("gui.done", new Object[0])));
        (this.field_146450_f = new List(GuiLanguage.mc)).registerScrollButtons(7, 8);
    }
    
    @Override
    public void handleMouseInput() {
        super.handleMouseInput();
        this.field_146450_f.func_178039_p();
    }
    
    @Override
    protected void actionPerformed(final GuiButton button) {
        if (button.enabled) {
            switch (button.id) {
                case 5: {
                    break;
                }
                case 6: {
                    GuiLanguage.mc.displayGuiScreen(this.field_146453_a);
                    break;
                }
                case 100: {
                    if (button instanceof GuiOptionButton) {
                        this.game_settings_3.setOptionValue(((GuiOptionButton)button).returnEnumOptions(), 1);
                        button.displayString = this.game_settings_3.getKeyBinding(GameSettings.Options.FORCE_UNICODE_FONT);
                        final ScaledResolution var2 = new ScaledResolution(GuiLanguage.mc, GuiLanguage.mc.displayWidth, GuiLanguage.mc.displayHeight);
                        final int var3 = var2.getScaledWidth();
                        final int var4 = var2.getScaledHeight();
                        this.setWorldAndResolution(GuiLanguage.mc, var3, var4);
                        break;
                    }
                    break;
                }
                default: {
                    this.field_146450_f.actionPerformed(button);
                    break;
                }
            }
        }
    }
    
    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        this.field_146450_f.drawScreen(mouseX, mouseY, partialTicks);
        Gui.drawCenteredString(this.fontRendererObj, I18n.format("options.language", new Object[0]), GuiLanguage.width / 2, 16, 16777215);
        Gui.drawCenteredString(this.fontRendererObj, "(" + I18n.format("options.languageWarning", new Object[0]) + ")", GuiLanguage.width / 2, GuiLanguage.height - 56, 8421504);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
    
    class List extends GuiSlot
    {
        private final java.util.List field_148176_l;
        private final Map field_148177_m;
        
        public List(final Minecraft mcIn) {
            super(mcIn, GuiLanguage.width, GuiLanguage.height, 32, GuiLanguage.height - 65 + 4, 18);
            this.field_148176_l = Lists.newArrayList();
            this.field_148177_m = Maps.newHashMap();
            for (final Language var4 : GuiLanguage.this.field_146454_h.getLanguages()) {
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
            GuiLanguage.this.field_146454_h.setCurrentLanguage(var5);
            GuiLanguage.this.game_settings_3.language = var5.getLanguageCode();
            this.mc.refreshResources();
            GuiLanguage.this.fontRendererObj.setUnicodeFlag(GuiLanguage.this.field_146454_h.isCurrentLocaleUnicode() || GuiLanguage.this.game_settings_3.forceUnicodeFont);
            GuiLanguage.this.fontRendererObj.setBidiFlag(GuiLanguage.this.field_146454_h.isCurrentLanguageBidirectional());
            GuiLanguage.this.field_146452_r.displayString = I18n.format("gui.done", new Object[0]);
            GuiLanguage.this.field_146455_i.displayString = GuiLanguage.this.game_settings_3.getKeyBinding(GameSettings.Options.FORCE_UNICODE_FONT);
            GuiLanguage.this.game_settings_3.saveOptions();
        }
        
        @Override
        protected boolean isSelected(final int slotIndex) {
            return this.field_148176_l.get(slotIndex).equals(GuiLanguage.this.field_146454_h.getCurrentLanguage().getLanguageCode());
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
            GuiLanguage.this.fontRendererObj.setBidiFlag(GuiLanguage.this.field_146454_h.getCurrentLanguage().isBidirectional());
        }
    }
}
