package HORIZON-6-0-SKIDPROTECTION;

public class GuiQualitySettingsOF extends GuiScreen
{
    private GuiScreen Â;
    protected String HorizonCode_Horizon_È;
    private GameSettings Ý;
    private static GameSettings.HorizonCode_Horizon_È[] Ø­áŒŠá;
    private int Âµá€;
    private int Ó;
    private long à;
    
    static {
        GuiQualitySettingsOF.Ø­áŒŠá = new GameSettings.HorizonCode_Horizon_È[] { GameSettings.HorizonCode_Horizon_È.Ê, GameSettings.HorizonCode_Horizon_È.áŒŠ, GameSettings.HorizonCode_Horizon_È.Ðƒáƒ, GameSettings.HorizonCode_Horizon_È.£É, GameSettings.HorizonCode_Horizon_È.áˆºÇŽØ, GameSettings.HorizonCode_Horizon_È.áˆº, GameSettings.HorizonCode_Horizon_È.£Ï, GameSettings.HorizonCode_Horizon_È.ÇŽá, GameSettings.HorizonCode_Horizon_È.áŒŠá€, GameSettings.HorizonCode_Horizon_È.¥Ï, GameSettings.HorizonCode_Horizon_È.£áŒŠá, GameSettings.HorizonCode_Horizon_È.Šà, GameSettings.HorizonCode_Horizon_È.Ñ¢Ç, GameSettings.HorizonCode_Horizon_È.Ñ¢Ó };
    }
    
    public GuiQualitySettingsOF(final GuiScreen guiscreen, final GameSettings gamesettings) {
        this.HorizonCode_Horizon_È = "Quality Settings";
        this.Âµá€ = 0;
        this.Ó = 0;
        this.à = 0L;
        this.Â = guiscreen;
        this.Ý = gamesettings;
    }
    
    @Override
    public void HorizonCode_Horizon_È() {
        int i = 0;
        for (final GameSettings.HorizonCode_Horizon_È enumoptions : GuiQualitySettingsOF.Ø­áŒŠá) {
            final int x = GuiQualitySettingsOF.Çªà¢ / 2 - 155 + i % 2 * 160;
            final int y = GuiQualitySettingsOF.Ê / 6 + 21 * (i / 2) - 10;
            if (!enumoptions.HorizonCode_Horizon_È()) {
                this.ÇŽÉ.add(new GuiOptionButton(enumoptions.Ý(), x, y, enumoptions, this.Ý.Ý(enumoptions)));
            }
            else {
                this.ÇŽÉ.add(new GuiOptionSlider(enumoptions.Ý(), x, y, enumoptions));
            }
            ++i;
        }
        this.ÇŽÉ.add(new GuiButton(200, GuiQualitySettingsOF.Çªà¢ / 2 - 100, GuiQualitySettingsOF.Ê / 6 + 168 + 11, I18n.HorizonCode_Horizon_È("gui.done", new Object[0])));
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final GuiButton guibutton) {
        if (guibutton.µà) {
            if (guibutton.£à < 200 && guibutton instanceof GuiOptionButton) {
                this.Ý.HorizonCode_Horizon_È(((GuiOptionButton)guibutton).HorizonCode_Horizon_È(), 1);
                guibutton.Å = this.Ý.Ý(GameSettings.HorizonCode_Horizon_È.HorizonCode_Horizon_È(guibutton.£à));
            }
            if (guibutton.£à == 200) {
                GuiQualitySettingsOF.Ñ¢á.ŠÄ.Â();
                GuiQualitySettingsOF.Ñ¢á.HorizonCode_Horizon_È(this.Â);
            }
            if (guibutton.£à != GameSettings.HorizonCode_Horizon_È.Ñ¢Â.ordinal()) {
                final ScaledResolution scaledresolution = new ScaledResolution(GuiQualitySettingsOF.Ñ¢á, GuiQualitySettingsOF.Ñ¢á.Ó, GuiQualitySettingsOF.Ñ¢á.à);
                final int i = scaledresolution.HorizonCode_Horizon_È();
                final int j = scaledresolution.Â();
                this.HorizonCode_Horizon_È(GuiQualitySettingsOF.Ñ¢á, i, j);
            }
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int x, final int y, final float f) {
        this.£á();
        this.HorizonCode_Horizon_È(this.É, this.HorizonCode_Horizon_È, GuiQualitySettingsOF.Çªà¢ / 2, 20, 16777215);
        super.HorizonCode_Horizon_È(x, y, f);
        if (Math.abs(x - this.Âµá€) <= 5 && Math.abs(y - this.Ó) <= 5) {
            final short activateDelay = 700;
            if (System.currentTimeMillis() >= this.à + activateDelay) {
                final int x2 = GuiQualitySettingsOF.Çªà¢ / 2 - 150;
                int y2 = GuiQualitySettingsOF.Ê / 6 - 5;
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
                        int col = 14540253;
                        if (line.endsWith("!")) {
                            col = 16719904;
                        }
                        this.É.HorizonCode_Horizon_È(line, x2 + 5, (float)(y2 + 5 + i * 11), col);
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
        if (btnName.equals("Mipmap Levels")) {
            final String[] array = array2 = new String[6];
            array[0] = "Visual effect which makes distant objects look better";
            array[1] = "by smoothing the texture details";
            array[2] = "  OFF - no smoothing";
            array[3] = "  1 - minimum smoothing";
            array[4] = "  4 - maximum smoothing";
            array[5] = "This option usually does not affect the performance.";
        }
        else if (btnName.equals("Mipmap Type")) {
            final String[] array3 = array2 = new String[5];
            array3[0] = "Visual effect which makes distant objects look better";
            array3[1] = "by smoothing the texture details";
            array3[2] = "  Nearest - rough smoothing";
            array3[3] = "  Linear - fine smoothing";
            array3[4] = "This option usually does not affect the performance.";
        }
        else if (btnName.equals("Anisotropic Filtering")) {
            final String[] array4 = array2 = new String[5];
            array4[0] = "Anisotropic Filtering";
            array4[1] = " OFF - (default) standard texture detail (faster)";
            array4[2] = " 2-16 - finer details in mipmapped textures (slower)";
            array4[3] = "The Anisotropic Filtering restores details in mipmapped";
            array4[4] = "textures. Higher values may decrease the FPS.";
        }
        else if (btnName.equals("Antialiasing")) {
            final String[] array5 = array2 = new String[8];
            array5[0] = "Antialiasing";
            array5[1] = " OFF - (default) no antialiasing (faster)";
            array5[2] = " 2-16 - antialiased lines and edges (slower)";
            array5[3] = "The Antialiasing smooths jagged lines and ";
            array5[4] = "sharp color transitions.";
            array5[5] = "Higher values may substantially decrease the FPS.";
            array5[6] = "Not all levels are supported by all graphics cards.";
            array5[7] = "Effective after a RESTART!";
        }
        else if (btnName.equals("Clear Water")) {
            final String[] array6 = array2 = new String[3];
            array6[0] = "Clear Water";
            array6[1] = "  ON - clear, transparent water";
            array6[2] = "  OFF - default water";
        }
        else if (btnName.equals("Better Grass")) {
            final String[] array7 = array2 = new String[4];
            array7[0] = "Better Grass";
            array7[1] = "  OFF - default side grass texture, fastest";
            array7[2] = "  Fast - full side grass texture, slower";
            array7[3] = "  Fancy - dynamic side grass texture, slowest";
        }
        else if (btnName.equals("Better Snow")) {
            final String[] array8 = array2 = new String[5];
            array8[0] = "Better Snow";
            array8[1] = "  OFF - default snow, faster";
            array8[2] = "  ON - better snow, slower";
            array8[3] = "Shows snow under transparent blocks (fence, tall grass)";
            array8[4] = "when bordering with snow blocks";
        }
        else if (btnName.equals("Random Mobs")) {
            final String[] array9 = array2 = new String[5];
            array9[0] = "Random Mobs";
            array9[1] = "  OFF - no random mobs, faster";
            array9[2] = "  ON - random mobs, slower";
            array9[3] = "Random mobs uses random textures for the game creatures.";
            array9[4] = "It needs a texture pack which has multiple mob textures.";
        }
        else if (btnName.equals("Swamp Colors")) {
            final String[] array10 = array2 = new String[4];
            array10[0] = "Swamp Colors";
            array10[1] = "  ON - use swamp colors (default), slower";
            array10[2] = "  OFF - do not use swamp colors, faster";
            array10[3] = "The swamp colors affect grass, leaves, vines and water.";
        }
        else if (btnName.equals("Smooth Biomes")) {
            final String[] array11 = array2 = new String[6];
            array11[0] = "Smooth Biomes";
            array11[1] = "  ON - smoothing of biome borders (default), slower";
            array11[2] = "  OFF - no smoothing of biome borders, faster";
            array11[3] = "The smoothing of biome borders is done by sampling and";
            array11[4] = "averaging the color of all surrounding blocks.";
            array11[5] = "Affected are grass, leaves, vines and water.";
        }
        else if (btnName.equals("Custom Fonts")) {
            final String[] array12 = array2 = new String[5];
            array12[0] = "Custom Fonts";
            array12[1] = "  ON - uses custom fonts (default), slower";
            array12[2] = "  OFF - uses default font, faster";
            array12[3] = "The custom fonts are supplied by the current";
            array12[4] = "texture pack";
        }
        else if (btnName.equals("Custom Colors")) {
            final String[] array13 = array2 = new String[5];
            array13[0] = "Custom Colors";
            array13[1] = "  ON - uses custom colors (default), slower";
            array13[2] = "  OFF - uses default colors, faster";
            array13[3] = "The custom colors are supplied by the current";
            array13[4] = "texture pack";
        }
        else if (btnName.equals("Show Capes")) {
            final String[] array14 = array2 = new String[3];
            array14[0] = "Show Capes";
            array14[1] = "  ON - show player capes (default)";
            array14[2] = "  OFF - do not show player capes";
        }
        else if (btnName.equals("Connected Textures")) {
            final String[] array15 = array2 = new String[8];
            array15[0] = "Connected Textures";
            array15[1] = "  OFF - no connected textures (default)";
            array15[2] = "  Fast - fast connected textures";
            array15[3] = "  Fancy - fancy connected textures";
            array15[4] = "Connected textures joins the textures of glass,";
            array15[5] = "sandstone and bookshelves when placed next to";
            array15[6] = "each other. The connected textures are supplied";
            array15[7] = "by the current texture pack.";
        }
        else if (btnName.equals("Far View")) {
            final String[] array16 = array2 = new String[7];
            array16[0] = "Far View";
            array16[1] = " OFF - (default) standard view distance";
            array16[2] = " ON - 3x view distance";
            array16[3] = "Far View is very resource demanding!";
            array16[4] = "3x view distance => 9x chunks to be loaded => FPS / 9";
            array16[5] = "Standard view distances: 32, 64, 128, 256";
            array16[6] = "Far view distances: 96, 192, 384, 512";
        }
        else if (btnName.equals("Natural Textures")) {
            final String[] array17 = array2 = new String[8];
            array17[0] = "Natural Textures";
            array17[1] = "  OFF - no natural textures (default)";
            array17[2] = "  ON - use natural textures";
            array17[3] = "Natural textures remove the gridlike pattern";
            array17[4] = "created by repeating blocks of the same type.";
            array17[5] = "It uses rotated and flipped variants of the base";
            array17[6] = "block texture. The configuration for the natural";
            array17[7] = "textures is supplied by the current texture pack";
        }
        else if (btnName.equals("Custom Sky")) {
            final String[] array18 = array2 = new String[5];
            array18[0] = "Custom Sky";
            array18[1] = "  ON - custom sky textures (default), slow";
            array18[2] = "  OFF - default sky, faster";
            array18[3] = "The custom sky textures are supplied by the current";
            array18[4] = "texture pack";
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
