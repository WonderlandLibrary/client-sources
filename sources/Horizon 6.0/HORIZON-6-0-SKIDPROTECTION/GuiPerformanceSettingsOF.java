package HORIZON-6-0-SKIDPROTECTION;

public class GuiPerformanceSettingsOF extends GuiScreen
{
    private GuiScreen Â;
    protected String HorizonCode_Horizon_È;
    private GameSettings Ý;
    private static GameSettings.HorizonCode_Horizon_È[] Ø­áŒŠá;
    private int Âµá€;
    private int Ó;
    private long à;
    
    static {
        GuiPerformanceSettingsOF.Ø­áŒŠá = new GameSettings.HorizonCode_Horizon_È[] { GameSettings.HorizonCode_Horizon_È.Ø­Æ, GameSettings.HorizonCode_Horizon_È.ÇªÂµÕ, GameSettings.HorizonCode_Horizon_È.£ÂµÄ, GameSettings.HorizonCode_Horizon_È.Ø­Âµ, GameSettings.HorizonCode_Horizon_È.Œà, GameSettings.HorizonCode_Horizon_È.Ðƒá, GameSettings.HorizonCode_Horizon_È.áŒŠÔ, GameSettings.HorizonCode_Horizon_È.Ø­Ñ¢á€ };
    }
    
    public GuiPerformanceSettingsOF(final GuiScreen guiscreen, final GameSettings gamesettings) {
        this.HorizonCode_Horizon_È = "Performance Settings";
        this.Âµá€ = 0;
        this.Ó = 0;
        this.à = 0L;
        this.Â = guiscreen;
        this.Ý = gamesettings;
    }
    
    @Override
    public void HorizonCode_Horizon_È() {
        int i = 0;
        for (final GameSettings.HorizonCode_Horizon_È enumoptions : GuiPerformanceSettingsOF.Ø­áŒŠá) {
            final int x = GuiPerformanceSettingsOF.Çªà¢ / 2 - 155 + i % 2 * 160;
            final int y = GuiPerformanceSettingsOF.Ê / 6 + 21 * (i / 2) - 10;
            if (!enumoptions.HorizonCode_Horizon_È()) {
                this.ÇŽÉ.add(new GuiOptionButton(enumoptions.Ý(), x, y, enumoptions, this.Ý.Ý(enumoptions)));
            }
            else {
                this.ÇŽÉ.add(new GuiOptionSlider(enumoptions.Ý(), x, y, enumoptions));
            }
            ++i;
        }
        this.ÇŽÉ.add(new GuiButton(200, GuiPerformanceSettingsOF.Çªà¢ / 2 - 100, GuiPerformanceSettingsOF.Ê / 6 + 168 + 11, I18n.HorizonCode_Horizon_È("gui.done", new Object[0])));
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final GuiButton guibutton) {
        if (guibutton.µà) {
            if (guibutton.£à < 200 && guibutton instanceof GuiOptionButton) {
                this.Ý.HorizonCode_Horizon_È(((GuiOptionButton)guibutton).HorizonCode_Horizon_È(), 1);
                guibutton.Å = this.Ý.Ý(GameSettings.HorizonCode_Horizon_È.HorizonCode_Horizon_È(guibutton.£à));
            }
            if (guibutton.£à == 200) {
                GuiPerformanceSettingsOF.Ñ¢á.ŠÄ.Â();
                GuiPerformanceSettingsOF.Ñ¢á.HorizonCode_Horizon_È(this.Â);
            }
            if (guibutton.£à != GameSettings.HorizonCode_Horizon_È.Ñ¢Â.ordinal()) {
                final ScaledResolution scaledresolution = new ScaledResolution(GuiPerformanceSettingsOF.Ñ¢á, GuiPerformanceSettingsOF.Ñ¢á.Ó, GuiPerformanceSettingsOF.Ñ¢á.à);
                final int i = scaledresolution.HorizonCode_Horizon_È();
                final int j = scaledresolution.Â();
                this.HorizonCode_Horizon_È(GuiPerformanceSettingsOF.Ñ¢á, i, j);
            }
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int x, final int y, final float f) {
        this.£á();
        this.HorizonCode_Horizon_È(this.É, this.HorizonCode_Horizon_È, GuiPerformanceSettingsOF.Çªà¢ / 2, 20, 16777215);
        super.HorizonCode_Horizon_È(x, y, f);
        if (Math.abs(x - this.Âµá€) <= 5 && Math.abs(y - this.Ó) <= 5) {
            final short activateDelay = 700;
            if (System.currentTimeMillis() >= this.à + activateDelay) {
                final int x2 = GuiPerformanceSettingsOF.Çªà¢ / 2 - 150;
                int y2 = GuiPerformanceSettingsOF.Ê / 6 - 5;
                if (y <= y2 + 98) {
                    y2 += 105;
                }
                final int x3 = x2 + 150 + 150;
                final int y3 = y2 + 84 + 10;
                final GuiButton btn = this.HorizonCode_Horizon_È(x, y);
                if (btn != null) {
                    final String s = this.Â(btn.Å);
                    final String[] lines = this.HorizonCode_Horizon_È(s);
                    if (lines == null) {
                        return;
                    }
                    Gui_1808253012.HorizonCode_Horizon_È(x2, y2, x3, y3, -536870912, -536870912);
                    for (int i = 0; i < lines.length; ++i) {
                        final String line = lines[i];
                        this.É.HorizonCode_Horizon_È(line, x2 + 5, (float)(y2 + 5 + i * 11), 14540253);
                    }
                }
            }
        }
        else {
            this.Âµá€ = x;
            this.Ó = y;
            this.à = System.currentTimeMillis();
        }
    }
    
    private String[] HorizonCode_Horizon_È(final String btnName) {
        String[] array2;
        if (btnName.equals("Smooth FPS")) {
            final String[] array = array2 = new String[5];
            array[0] = "Stabilizes FPS by flushing the graphic driver buffers";
            array[1] = "  OFF - no stabilization, FPS may fluctuate";
            array[2] = "  ON - FPS stabilization";
            array[3] = "This option is graphics driver dependant and its effect";
            array[4] = "is not always visible";
        }
        else if (btnName.equals("Smooth World")) {
            final String[] array3 = array2 = new String[5];
            array3[0] = "Removes lag spikes caused by the internal server.";
            array3[1] = "  OFF - no stabilization, FPS may fluctuate";
            array3[2] = "  ON - FPS stabilization";
            array3[3] = "Stabilizes FPS by distributing the internal server load.";
            array3[4] = "Effective only for local worlds (single player).";
        }
        else if (btnName.equals("Load Far")) {
            final String[] array4 = array2 = new String[6];
            array4[0] = "Loads the world chunks at distance Far.";
            array4[1] = "Switching the render distance does not cause all chunks ";
            array4[2] = "to be loaded again.";
            array4[3] = "  OFF - world chunks loaded up to render distance";
            array4[4] = "  ON - world chunks loaded at distance Far, allows";
            array4[5] = "       fast render distance switching";
        }
        else if (btnName.equals("Preloaded Chunks")) {
            final String[] array5 = array2 = new String[6];
            array5[0] = "Defines an area in which no chunks will be loaded";
            array5[1] = "  OFF - after 5m new chunks will be loaded";
            array5[2] = "  2 - after 32m  new chunks will be loaded";
            array5[3] = "  8 - after 128m new chunks will be loaded";
            array5[4] = "Higher values need more time to load all the chunks";
            array5[5] = "and may decrease the FPS.";
        }
        else if (btnName.equals("Chunk Updates")) {
            final String[] array6 = array2 = new String[6];
            array6[0] = "Chunk updates";
            array6[1] = " 1 - (default) slower world loading, higher FPS";
            array6[2] = " 3 - faster world loading, lower FPS";
            array6[3] = " 5 - fastest world loading, lowest FPS";
            array6[4] = "Number of chunk updates per rendered frame,";
            array6[5] = "higher values may destabilize the framerate.";
        }
        else if (btnName.equals("Dynamic Updates")) {
            final String[] array7 = array2 = new String[5];
            array7[0] = "Dynamic chunk updates";
            array7[1] = " OFF - (default) standard chunk updates per frame";
            array7[2] = " ON - more updates while the player is standing still";
            array7[3] = "Dynamic updates force more chunk updates while";
            array7[4] = "the player is standing still to load the world faster.";
        }
        else if (btnName.equals("Lazy Chunk Loading")) {
            final String[] array8 = array2 = new String[7];
            array8[0] = "Lazy Chunk Loading";
            array8[1] = " OFF - default server chunk loading";
            array8[2] = " ON - lazy server chunk loading (smoother)";
            array8[3] = "Smooths the integrated server chunk loading by";
            array8[4] = "distributing the chunks over several ticks.";
            array8[5] = "Turn it OFF if parts of the world do not load correctly.";
            array8[6] = "Effective only for local worlds and single-core CPU.";
        }
        else if (btnName.equals("Fast Math")) {
            final String[] array9 = array2 = new String[5];
            array9[0] = "Fast Math";
            array9[1] = " OFF - standard math (default)";
            array9[2] = " ON - faster math";
            array9[3] = "Uses optimized sin() and cos() functions which can";
            array9[4] = "better utilize the CPU cache and increase the FPS.";
        }
        else if (btnName.equals("Fast Render")) {
            final String[] array10 = array2 = new String[7];
            array10[0] = "Fast Render";
            array10[1] = " OFF - standard rendering (default)";
            array10[2] = " ON - faster rendering";
            array10[3] = "Uses optimized rendering algorithm which decreases";
            array10[4] = "the GPU load and may substantionally increase the FPS.";
            array10[5] = "You can turn if OFF if you notice flickering textures";
            array10[6] = "on some blocks.";
        }
        else {
            array2 = null;
        }
        return array2;
    }
    
    private String Â(final String displayString) {
        final int pos = displayString.indexOf(58);
        return (pos < 0) ? displayString : displayString.substring(0, pos);
    }
    
    private GuiButton HorizonCode_Horizon_È(final int i, final int j) {
        for (int k = 0; k < this.ÇŽÉ.size(); ++k) {
            final GuiButton btn = this.ÇŽÉ.get(k);
            final int btnWidth = GuiVideoSettings.Â(btn);
            final int btnHeight = GuiVideoSettings.Ý(btn);
            final boolean flag = i >= btn.ˆÏ­ && j >= btn.£á && i < btn.ˆÏ­ + btnWidth && j < btn.£á + btnHeight;
            if (flag) {
                return btn;
            }
        }
        return null;
    }
}
