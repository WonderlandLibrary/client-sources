package net.minecraft.src;

public class GuiDetailSettingsOF extends GuiScreen
{
    private GuiScreen prevScreen;
    protected String title;
    private GameSettings settings;
    private static EnumOptions[] enumOptions;
    private int lastMouseX;
    private int lastMouseY;
    private long mouseStillTime;
    
    static {
        GuiDetailSettingsOF.enumOptions = new EnumOptions[] { EnumOptions.CLOUDS, EnumOptions.CLOUD_HEIGHT, EnumOptions.TREES, EnumOptions.GRASS, EnumOptions.WATER, EnumOptions.RAIN, EnumOptions.SKY, EnumOptions.STARS, EnumOptions.SUN_MOON, EnumOptions.SHOW_CAPES, EnumOptions.DEPTH_FOG, EnumOptions.HELD_ITEM_TOOLTIPS, EnumOptions.DROPPED_ITEMS };
    }
    
    public GuiDetailSettingsOF(final GuiScreen var1, final GameSettings var2) {
        this.title = "Detail Settings";
        this.lastMouseX = 0;
        this.lastMouseY = 0;
        this.mouseStillTime = 0L;
        this.prevScreen = var1;
        this.settings = var2;
    }
    
    @Override
    public void initGui() {
        final StringTranslate var1 = StringTranslate.getInstance();
        int var2 = 0;
        for (final EnumOptions var6 : GuiDetailSettingsOF.enumOptions) {
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
        this.buttonList.add(new GuiButton(200, this.width / 2 - 100, this.height / 6 + 168 + 11, var1.translateKey("gui.done")));
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
        if (Math.abs(var1 - this.lastMouseX) <= 5 && Math.abs(var2 - this.lastMouseY) <= 5) {
            final short var4 = 700;
            if (System.currentTimeMillis() >= this.mouseStillTime + var4) {
                final int var5 = this.width / 2 - 150;
                int var6 = this.height / 6 - 5;
                if (var2 <= var6 + 98) {
                    var6 += 105;
                }
                final int var7 = var5 + 150 + 150;
                final int var8 = var6 + 84 + 10;
                final GuiButton var9 = this.getSelectedButton(var1, var2);
                if (var9 != null) {
                    final String var10 = this.getButtonName(var9.displayString);
                    final String[] var11 = this.getTooltipLines(var10);
                    if (var11 == null) {
                        return;
                    }
                    this.drawGradientRect(var5, var6, var7, var8, -536870912, -536870912);
                    for (int var12 = 0; var12 < var11.length; ++var12) {
                        final String var13 = var11[var12];
                        this.fontRenderer.drawStringWithShadow(var13, var5 + 5, var6 + 5 + var12 * 11, 14540253);
                    }
                }
            }
        }
        else {
            this.lastMouseX = var1;
            this.lastMouseY = var2;
            this.mouseStillTime = System.currentTimeMillis();
        }
    }
    
    private String[] getTooltipLines(final String var1) {
        String[] array2;
        if (var1.equals("Clouds")) {
            final String[] array = array2 = new String[7];
            array[0] = "Clouds";
            array[1] = "  Default - as set by setting Graphics";
            array[2] = "  Fast - lower quality, faster";
            array[3] = "  Fancy - higher quality, slower";
            array[4] = "  OFF - no clouds, fastest";
            array[5] = "Fast clouds are rendered 2D.";
            array[6] = "Fancy clouds are rendered 3D.";
        }
        else if (var1.equals("Cloud Height")) {
            final String[] array3 = array2 = new String[3];
            array3[0] = "Cloud Height";
            array3[1] = "  OFF - default height";
            array3[2] = "  100% - above world height limit";
        }
        else if (var1.equals("Trees")) {
            final String[] array4 = array2 = new String[6];
            array4[0] = "Trees";
            array4[1] = "  Default - as set by setting Graphics";
            array4[2] = "  Fast - lower quality, faster";
            array4[3] = "  Fancy - higher quality, slower";
            array4[4] = "Fast trees have opaque leaves.";
            array4[5] = "Fancy trees have transparent leaves.";
        }
        else if (var1.equals("Grass")) {
            final String[] array5 = array2 = new String[6];
            array5[0] = "Grass";
            array5[1] = "  Default - as set by setting Graphics";
            array5[2] = "  Fast - lower quality, faster";
            array5[3] = "  Fancy - higher quality, slower";
            array5[4] = "Fast grass uses default side texture.";
            array5[5] = "Fancy grass uses biome side texture.";
        }
        else if (var1.equals("Dropped Items")) {
            final String[] array6 = array2 = new String[4];
            array6[0] = "Dropped Items";
            array6[1] = "  Default - as set by setting Graphics";
            array6[2] = "  Fast - 2D dropped items, faster";
            array6[3] = "  Fancy - 3D dropped items, slower";
        }
        else if (var1.equals("Water")) {
            final String[] array7 = array2 = new String[6];
            array7[0] = "Water";
            array7[1] = "  Default - as set by setting Graphics";
            array7[2] = "  Fast  - lower quality, faster";
            array7[3] = "  Fancy - higher quality, slower";
            array7[4] = "Fast water (1 pass) has some visual artifacts";
            array7[5] = "Fancy water (2 pass) has no visual artifacts";
        }
        else if (var1.equals("Rain & Snow")) {
            final String[] array8 = array2 = new String[7];
            array8[0] = "Rain & Snow";
            array8[1] = "  Default - as set by setting Graphics";
            array8[2] = "  Fast  - light rain/snow, faster";
            array8[3] = "  Fancy - heavy rain/snow, slower";
            array8[4] = "  OFF - no rain/snow, fastest";
            array8[5] = "When rain is OFF the splashes and rain sounds";
            array8[6] = "are still active.";
        }
        else if (var1.equals("Sky")) {
            final String[] array9 = array2 = new String[4];
            array9[0] = "Sky";
            array9[1] = "  ON - sky is visible, slower";
            array9[2] = "  OFF  - sky is not visible, faster";
            array9[3] = "When sky is OFF the moon and sun are still visible.";
        }
        else if (var1.equals("Stars")) {
            final String[] array10 = array2 = new String[3];
            array10[0] = "Stars";
            array10[1] = "  ON - stars are visible, slower";
            array10[2] = "  OFF  - stars are not visible, faster";
        }
        else if (var1.equals("Depth Fog")) {
            final String[] array11 = array2 = new String[3];
            array11[0] = "Depth Fog";
            array11[1] = "  ON - fog moves closer at bedrock levels (default)";
            array11[2] = "  OFF - same fog at all levels";
        }
        else if (var1.equals("Show Capes")) {
            final String[] array12 = array2 = new String[3];
            array12[0] = "Show Capes";
            array12[1] = "  ON - show player capes (default)";
            array12[2] = "  OFF - do not show player capes";
        }
        else {
            array2 = null;
        }
        return array2;
    }
    
    private String getButtonName(final String var1) {
        final int var2 = var1.indexOf(58);
        return (var2 < 0) ? var1 : var1.substring(0, var2);
    }
    
    private GuiButton getSelectedButton(final int var1, final int var2) {
        for (int var3 = 0; var3 < this.buttonList.size(); ++var3) {
            final GuiButton var4 = this.buttonList.get(var3);
            final boolean var5 = var1 >= var4.xPosition && var2 >= var4.yPosition && var1 < var4.xPosition + var4.width && var2 < var4.yPosition + var4.height;
            if (var5) {
                return var4;
            }
        }
        return null;
    }
}
