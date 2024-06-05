package net.minecraft.src;

public class GuiAnimationSettingsOF extends GuiScreen
{
    private GuiScreen prevScreen;
    protected String title;
    private GameSettings settings;
    private static EnumOptions[] enumOptions;
    
    static {
        GuiAnimationSettingsOF.enumOptions = new EnumOptions[] { EnumOptions.ANIMATED_WATER, EnumOptions.ANIMATED_LAVA, EnumOptions.ANIMATED_FIRE, EnumOptions.ANIMATED_PORTAL, EnumOptions.ANIMATED_REDSTONE, EnumOptions.ANIMATED_EXPLOSION, EnumOptions.ANIMATED_FLAME, EnumOptions.ANIMATED_SMOKE, EnumOptions.VOID_PARTICLES, EnumOptions.WATER_PARTICLES, EnumOptions.RAIN_SPLASH, EnumOptions.PORTAL_PARTICLES, EnumOptions.POTION_PARTICLES, EnumOptions.DRIPPING_WATER_LAVA, EnumOptions.ANIMATED_TERRAIN, EnumOptions.ANIMATED_ITEMS, EnumOptions.ANIMATED_TEXTURES, EnumOptions.PARTICLES };
    }
    
    public GuiAnimationSettingsOF(final GuiScreen var1, final GameSettings var2) {
        this.title = "Animation Settings";
        this.prevScreen = var1;
        this.settings = var2;
    }
    
    @Override
    public void initGui() {
        final StringTranslate var1 = StringTranslate.getInstance();
        int var2 = 0;
        for (final EnumOptions var6 : GuiAnimationSettingsOF.enumOptions) {
            final int var7 = this.width / 2 - 155 + var2 % 2 * 160;
            final int var8 = this.height / 6 + 21 * (var2 / 2) - 10;
            if (!var6.getEnumFloat()) {
                this.buttonList.add(new GuiSmallButton(var6.returnEnumOrdinal(), var7, var8, var6, this.settings.getKeyBinding(var6)));
            }
            else {
                this.buttonList.add(new GuiSlider(var6.returnEnumOrdinal(), var7, var8, var6, this.settings.getKeyBinding(var6), this.settings.getOptionFloatValue(var6)));
            }
            ++var2;
        }
        this.buttonList.add(new GuiButton(210, this.width / 2 - 155, this.height / 6 + 168 + 11, 70, 20, "All ON"));
        this.buttonList.add(new GuiButton(211, this.width / 2 - 155 + 80, this.height / 6 + 168 + 11, 70, 20, "All OFF"));
        this.buttonList.add(new GuiSmallButton(200, this.width / 2 + 5, this.height / 6 + 168 + 11, var1.translateKey("gui.done")));
    }
    
    @Override
    protected void actionPerformed(final GuiButton var1) {
        if (var1.enabled) {
            if (var1.id < 100 && var1 instanceof GuiSmallButton) {
                this.settings.setOptionValue(((GuiSmallButton)var1).returnEnumOptions(), 1);
                var1.displayString = this.settings.getKeyBinding(EnumOptions.getEnumOptions(var1.id));
            }
            if (var1.id == 200) {
                this.mc.gameSettings.saveOptions();
                this.mc.displayGuiScreen(this.prevScreen);
            }
            if (var1.id == 210) {
                this.mc.gameSettings.setAllAnimations(true);
            }
            if (var1.id == 211) {
                this.mc.gameSettings.setAllAnimations(false);
            }
            if (var1.id != EnumOptions.CLOUD_HEIGHT.ordinal()) {
                final ScaledResolution var2 = new ScaledResolution(this.mc.gameSettings, this.mc.displayWidth, this.mc.displayHeight);
                final int var3 = ScaledResolution.getScaledWidth();
                final int var4 = ScaledResolution.getScaledHeight();
                this.setWorldAndResolution(this.mc, var3, var4);
            }
        }
    }
    
    @Override
    public void drawScreen(final int var1, final int var2, final float var3) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRenderer, this.title, this.width / 2, 20, 16777215);
        super.drawScreen(var1, var2, var3);
    }
}
