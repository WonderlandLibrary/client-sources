package net.minecraft.src;

public class GuiControls extends GuiScreen
{
    private GuiScreen parentScreen;
    protected String screenTitle;
    private GameSettings options;
    private int buttonId;
    
    public GuiControls(final GuiScreen par1GuiScreen, final GameSettings par2GameSettings) {
        this.screenTitle = "Controls";
        this.buttonId = -1;
        this.parentScreen = par1GuiScreen;
        this.options = par2GameSettings;
    }
    
    private int getLeftBorder() {
        return this.width / 2 - 155;
    }
    
    @Override
    public void initGui() {
        final StringTranslate var1 = StringTranslate.getInstance();
        final int var2 = this.getLeftBorder();
        for (int var3 = 0; var3 < this.options.keyBindings.length; ++var3) {
            this.buttonList.add(new GuiSmallButton(var3, var2 + var3 % 2 * 160, this.height / 6 + 24 * (var3 >> 1), 70, 20, this.options.getOptionDisplayString(var3)));
        }
        this.buttonList.add(new GuiButton(200, this.width / 2 - 100, this.height / 6 + 168, var1.translateKey("gui.done")));
        this.screenTitle = var1.translateKey("controls.title");
    }
    
    @Override
    protected void actionPerformed(final GuiButton par1GuiButton) {
        for (int var2 = 0; var2 < this.options.keyBindings.length; ++var2) {
            this.buttonList.get(var2).displayString = this.options.getOptionDisplayString(var2);
        }
        if (par1GuiButton.id == 200) {
            this.mc.displayGuiScreen(this.parentScreen);
        }
        else {
            this.buttonId = par1GuiButton.id;
            par1GuiButton.displayString = "> " + this.options.getOptionDisplayString(par1GuiButton.id) + " <";
        }
    }
    
    @Override
    protected void mouseClicked(final int par1, final int par2, final int par3) {
        if (this.buttonId >= 0) {
            this.options.setKeyBinding(this.buttonId, -100 + par3);
            this.buttonList.get(this.buttonId).displayString = this.options.getOptionDisplayString(this.buttonId);
            this.buttonId = -1;
            KeyBinding.resetKeyBindingArrayAndHash();
        }
        else {
            super.mouseClicked(par1, par2, par3);
        }
    }
    
    @Override
    protected void keyTyped(final char par1, final int par2) {
        if (this.buttonId >= 0) {
            this.options.setKeyBinding(this.buttonId, par2);
            this.buttonList.get(this.buttonId).displayString = this.options.getOptionDisplayString(this.buttonId);
            this.buttonId = -1;
            KeyBinding.resetKeyBindingArrayAndHash();
        }
        else {
            super.keyTyped(par1, par2);
        }
    }
    
    @Override
    public void drawScreen(final int par1, final int par2, final float par3) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRenderer, this.screenTitle, this.width / 2, 20, 16777215);
        final int var4 = this.getLeftBorder();
        for (int var5 = 0; var5 < this.options.keyBindings.length; ++var5) {
            boolean var6 = false;
            for (int var7 = 0; var7 < this.options.keyBindings.length; ++var7) {
                if (var7 != var5 && this.options.keyBindings[var5].keyCode == this.options.keyBindings[var7].keyCode) {
                    var6 = true;
                    break;
                }
            }
            if (this.buttonId == var5) {
                this.buttonList.get(var5).displayString = EnumChatFormatting.WHITE + "> " + EnumChatFormatting.YELLOW + "??? " + EnumChatFormatting.WHITE + "<";
            }
            else if (var6) {
                this.buttonList.get(var5).displayString = EnumChatFormatting.RED + this.options.getOptionDisplayString(var5);
            }
            else {
                this.buttonList.get(var5).displayString = this.options.getOptionDisplayString(var5);
            }
            this.drawString(this.fontRenderer, this.options.getKeyBindingDescription(var5), var4 + var5 % 2 * 160 + 70 + 6, this.height / 6 + 24 * (var5 >> 1) + 7, -1);
        }
        super.drawScreen(par1, par2, par3);
    }
}
