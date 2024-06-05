package net.minecraft.src;

import java.util.*;

public class GuiSnooper extends GuiScreen
{
    private final GuiScreen snooperGuiScreen;
    private final GameSettings snooperGameSettings;
    private final List field_74098_c;
    private final List field_74096_d;
    private String snooperTitle;
    private String[] field_74101_n;
    private GuiSnooperList snooperList;
    private GuiButton buttonAllowSnooping;
    
    public GuiSnooper(final GuiScreen par1GuiScreen, final GameSettings par2GameSettings) {
        this.field_74098_c = new ArrayList();
        this.field_74096_d = new ArrayList();
        this.snooperGuiScreen = par1GuiScreen;
        this.snooperGameSettings = par2GameSettings;
    }
    
    @Override
    public void initGui() {
        this.snooperTitle = StatCollector.translateToLocal("options.snooper.title");
        final String var1 = StatCollector.translateToLocal("options.snooper.desc");
        final ArrayList var2 = new ArrayList();
        for (final String var4 : this.fontRenderer.listFormattedStringToWidth(var1, this.width - 30)) {
            var2.add(var4);
        }
        this.field_74101_n = var2.toArray(new String[0]);
        this.field_74098_c.clear();
        this.field_74096_d.clear();
        this.buttonList.add(this.buttonAllowSnooping = new GuiButton(1, this.width / 2 - 152, this.height - 30, 150, 20, this.snooperGameSettings.getKeyBinding(EnumOptions.SNOOPER_ENABLED)));
        this.buttonList.add(new GuiButton(2, this.width / 2 + 2, this.height - 30, 150, 20, StatCollector.translateToLocal("gui.done")));
        final boolean var5 = this.mc.getIntegratedServer() != null && this.mc.getIntegratedServer().getPlayerUsageSnooper() != null;
        for (final Map.Entry var7 : new TreeMap(this.mc.getPlayerUsageSnooper().getCurrentStats()).entrySet()) {
            this.field_74098_c.add(String.valueOf(var5 ? "C " : "") + var7.getKey());
            this.field_74096_d.add(this.fontRenderer.trimStringToWidth(var7.getValue(), this.width - 220));
        }
        if (var5) {
            for (final Map.Entry var7 : new TreeMap(this.mc.getIntegratedServer().getPlayerUsageSnooper().getCurrentStats()).entrySet()) {
                this.field_74098_c.add("S " + var7.getKey());
                this.field_74096_d.add(this.fontRenderer.trimStringToWidth(var7.getValue(), this.width - 220));
            }
        }
        this.snooperList = new GuiSnooperList(this);
    }
    
    @Override
    protected void actionPerformed(final GuiButton par1GuiButton) {
        if (par1GuiButton.enabled) {
            if (par1GuiButton.id == 2) {
                this.snooperGameSettings.saveOptions();
                this.snooperGameSettings.saveOptions();
                this.mc.displayGuiScreen(this.snooperGuiScreen);
            }
            if (par1GuiButton.id == 1) {
                this.snooperGameSettings.setOptionValue(EnumOptions.SNOOPER_ENABLED, 1);
                this.buttonAllowSnooping.displayString = this.snooperGameSettings.getKeyBinding(EnumOptions.SNOOPER_ENABLED);
            }
        }
    }
    
    @Override
    public void drawScreen(final int par1, final int par2, final float par3) {
        this.drawDefaultBackground();
        this.snooperList.drawScreen(par1, par2, par3);
        this.drawCenteredString(this.fontRenderer, this.snooperTitle, this.width / 2, 8, 16777215);
        int var4 = 22;
        for (final String var8 : this.field_74101_n) {
            this.drawCenteredString(this.fontRenderer, var8, this.width / 2, var4, 8421504);
            var4 += this.fontRenderer.FONT_HEIGHT;
        }
        super.drawScreen(par1, par2, par3);
    }
    
    static List func_74095_a(final GuiSnooper par0GuiSnooper) {
        return par0GuiSnooper.field_74098_c;
    }
    
    static List func_74094_b(final GuiSnooper par0GuiSnooper) {
        return par0GuiSnooper.field_74096_d;
    }
}
