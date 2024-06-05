package net.minecraft.src;

public class GuiPerformanceSettingsOF extends GuiScreen
{
    private GuiScreen prevScreen;
    protected String title;
    private GameSettings settings;
    private static EnumOptions[] enumOptions;
    private int lastMouseX;
    private int lastMouseY;
    private long mouseStillTime;
    
    static {
        GuiPerformanceSettingsOF.enumOptions = new EnumOptions[] { EnumOptions.SMOOTH_FPS, EnumOptions.SMOOTH_WORLD, EnumOptions.LOAD_FAR, EnumOptions.PRELOADED_CHUNKS, EnumOptions.CHUNK_UPDATES, EnumOptions.CHUNK_UPDATES_DYNAMIC, EnumOptions.LAZY_CHUNK_LOADING };
    }
    
    public GuiPerformanceSettingsOF(final GuiScreen var1, final GameSettings var2) {
        this.title = "Performance Settings";
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
        for (final EnumOptions var6 : GuiPerformanceSettingsOF.enumOptions) {
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
        if (var1.equals("Smooth FPS")) {
            final String[] array = array2 = new String[5];
            array[0] = "Stabilizes FPS by flushing the graphic driver buffers";
            array[1] = "  OFF - no stabilization, FPS may fluctuate";
            array[2] = "  ON - FPS stabilization";
            array[3] = "This option is graphic driver dependant and its effect";
            array[4] = "is not always visible";
        }
        else if (var1.equals("Smooth World")) {
            final String[] array3 = array2 = new String[5];
            array3[0] = "Removes lag spikes caused by the internal server.";
            array3[1] = "  OFF - no stabilization, FPS may fluctuate";
            array3[2] = "  ON - FPS stabilization";
            array3[3] = "Stabilizes FPS by distributing the internal server load.";
            array3[4] = "Effective only for local worlds and single-core CPU.";
        }
        else if (var1.equals("Load Far")) {
            final String[] array4 = array2 = new String[6];
            array4[0] = "Loads the world chunks at distance Far.";
            array4[1] = "Switching the render distance does not cause all chunks ";
            array4[2] = "to be loaded again.";
            array4[3] = "  OFF - world chunks loaded up to render distance";
            array4[4] = "  ON - world chunks loaded at distance Far, allows";
            array4[5] = "       fast render distance switching";
        }
        else if (var1.equals("Preloaded Chunks")) {
            final String[] array5 = array2 = new String[5];
            array5[0] = "Defines an area in which no chunks will be loaded";
            array5[1] = "  OFF - after 5m new chunks will be loaded";
            array5[2] = "  2 - after 32m  new chunks will be loaded";
            array5[3] = "  8 - after 128m new chunks will be loaded";
            array5[4] = "Higher values need more time to load all the chunks";
        }
        else if (var1.equals("Chunk Updates")) {
            final String[] array6 = array2 = new String[4];
            array6[0] = "Chunk updates per frame";
            array6[1] = " 1 - (default) slower world loading, higher FPS";
            array6[2] = " 3 - faster world loading, lower FPS";
            array6[3] = " 5 - fastest world loading, lowest FPS";
        }
        else if (var1.equals("Dynamic Updates")) {
            final String[] array7 = array2 = new String[5];
            array7[0] = "Dynamic chunk updates";
            array7[1] = " OFF - (default) standard chunk updates per frame";
            array7[2] = " ON - more updates while the player is standing still";
            array7[3] = "Dynamic updates force more chunk updates while";
            array7[4] = "the player is standing still to load the world faster.";
        }
        else if (var1.equals("Lazy Chunk Loading")) {
            final String[] array8 = array2 = new String[7];
            array8[0] = "Lazy Chunk Loading";
            array8[1] = " OFF - default server chunk loading";
            array8[2] = " ON - lazy server chunk loading (smoother)";
            array8[3] = "Smooths the integrated server chunk loading by";
            array8[4] = "distributing the chunks over several ticks.";
            array8[5] = "Turn it OFF if parts of the world do not load correctly.";
            array8[6] = "Effective only for local worlds and single-core CPU.";
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
