package HORIZON-6-0-SKIDPROTECTION;

public class GuiDetailSettingsOF extends GuiScreen
{
    private GuiScreen Â;
    protected String HorizonCode_Horizon_È;
    private GameSettings Ý;
    private static GameSettings.HorizonCode_Horizon_È[] Ø­áŒŠá;
    private int Âµá€;
    private int Ó;
    private long à;
    
    static {
        GuiDetailSettingsOF.Ø­áŒŠá = new GameSettings.HorizonCode_Horizon_È[] { GameSettings.HorizonCode_Horizon_È.Ä, GameSettings.HorizonCode_Horizon_È.Ñ¢Â, GameSettings.HorizonCode_Horizon_È.Ï­à, GameSettings.HorizonCode_Horizon_È.áˆºáˆºÈ, GameSettings.HorizonCode_Horizon_È.ˆÐƒØ­à, GameSettings.HorizonCode_Horizon_È.£Õ, GameSettings.HorizonCode_Horizon_È.Ï­Ô, GameSettings.HorizonCode_Horizon_È.ˆà¢, GameSettings.HorizonCode_Horizon_È.ŠÕ, GameSettings.HorizonCode_Horizon_È.ÂµÕ, GameSettings.HorizonCode_Horizon_È.Š };
    }
    
    public GuiDetailSettingsOF(final GuiScreen guiscreen, final GameSettings gamesettings) {
        this.HorizonCode_Horizon_È = "Detail Settings";
        this.Âµá€ = 0;
        this.Ó = 0;
        this.à = 0L;
        this.Â = guiscreen;
        this.Ý = gamesettings;
    }
    
    @Override
    public void HorizonCode_Horizon_È() {
        int i = 0;
        for (final GameSettings.HorizonCode_Horizon_È enumoptions : GuiDetailSettingsOF.Ø­áŒŠá) {
            final int x = GuiDetailSettingsOF.Çªà¢ / 2 - 155 + i % 2 * 160;
            final int y = GuiDetailSettingsOF.Ê / 6 + 21 * (i / 2) - 10;
            if (!enumoptions.HorizonCode_Horizon_È()) {
                this.ÇŽÉ.add(new GuiOptionButton(enumoptions.Ý(), x, y, enumoptions, this.Ý.Ý(enumoptions)));
            }
            else {
                this.ÇŽÉ.add(new GuiOptionSlider(enumoptions.Ý(), x, y, enumoptions));
            }
            ++i;
        }
        this.ÇŽÉ.add(new GuiButton(200, GuiDetailSettingsOF.Çªà¢ / 2 - 100, GuiDetailSettingsOF.Ê / 6 + 168 + 11, I18n.HorizonCode_Horizon_È("gui.done", new Object[0])));
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final GuiButton guibutton) {
        if (guibutton.µà) {
            if (guibutton.£à < 200 && guibutton instanceof GuiOptionButton) {
                this.Ý.HorizonCode_Horizon_È(((GuiOptionButton)guibutton).HorizonCode_Horizon_È(), 1);
                guibutton.Å = this.Ý.Ý(GameSettings.HorizonCode_Horizon_È.HorizonCode_Horizon_È(guibutton.£à));
            }
            if (guibutton.£à == 200) {
                GuiDetailSettingsOF.Ñ¢á.ŠÄ.Â();
                GuiDetailSettingsOF.Ñ¢á.HorizonCode_Horizon_È(this.Â);
            }
            if (guibutton.£à != GameSettings.HorizonCode_Horizon_È.Ñ¢Â.ordinal()) {
                final ScaledResolution scaledresolution = new ScaledResolution(GuiDetailSettingsOF.Ñ¢á, GuiDetailSettingsOF.Ñ¢á.Ó, GuiDetailSettingsOF.Ñ¢á.à);
                final int i = scaledresolution.HorizonCode_Horizon_È();
                final int j = scaledresolution.Â();
                this.HorizonCode_Horizon_È(GuiDetailSettingsOF.Ñ¢á, i, j);
            }
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int x, final int y, final float f) {
        this.£á();
        this.HorizonCode_Horizon_È(this.É, this.HorizonCode_Horizon_È, GuiDetailSettingsOF.Çªà¢ / 2, 20, 16777215);
        super.HorizonCode_Horizon_È(x, y, f);
        if (Math.abs(x - this.Âµá€) <= 5 && Math.abs(y - this.Ó) <= 5) {
            final short activateDelay = 700;
            if (System.currentTimeMillis() >= this.à + activateDelay) {
                final int x2 = GuiDetailSettingsOF.Çªà¢ / 2 - 150;
                int y2 = GuiDetailSettingsOF.Ê / 6 - 5;
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
        if (btnName.equals("Clouds")) {
            final String[] array = array2 = new String[7];
            array[0] = "Clouds";
            array[1] = "  Default - as set by setting Graphics";
            array[2] = "  Fast - lower quality, faster";
            array[3] = "  Fancy - higher quality, slower";
            array[4] = "  OFF - no clouds, fastest";
            array[5] = "Fast clouds are rendered 2D.";
            array[6] = "Fancy clouds are rendered 3D.";
        }
        else if (btnName.equals("Cloud Height")) {
            final String[] array3 = array2 = new String[3];
            array3[0] = "Cloud Height";
            array3[1] = "  OFF - default height";
            array3[2] = "  100% - above world height limit";
        }
        else if (btnName.equals("Trees")) {
            final String[] array4 = array2 = new String[6];
            array4[0] = "Trees";
            array4[1] = "  Default - as set by setting Graphics";
            array4[2] = "  Fast - lower quality, faster";
            array4[3] = "  Fancy - higher quality, slower";
            array4[4] = "Fast trees have opaque leaves.";
            array4[5] = "Fancy trees have transparent leaves.";
        }
        else if (btnName.equals("Grass")) {
            final String[] array5 = array2 = new String[6];
            array5[0] = "Grass";
            array5[1] = "  Default - as set by setting Graphics";
            array5[2] = "  Fast - lower quality, faster";
            array5[3] = "  Fancy - higher quality, slower";
            array5[4] = "Fast grass uses default side texture.";
            array5[5] = "Fancy grass uses biome side texture.";
        }
        else if (btnName.equals("Dropped Items")) {
            final String[] array6 = array2 = new String[4];
            array6[0] = "Dropped Items";
            array6[1] = "  Default - as set by setting Graphics";
            array6[2] = "  Fast - 2D dropped items, faster";
            array6[3] = "  Fancy - 3D dropped items, slower";
        }
        else if (btnName.equals("Water")) {
            final String[] array7 = array2 = new String[6];
            array7[0] = "Water";
            array7[1] = "  Default - as set by setting Graphics";
            array7[2] = "  Fast  - lower quality, faster";
            array7[3] = "  Fancy - higher quality, slower";
            array7[4] = "Fast water (1 pass) has some visual artifacts";
            array7[5] = "Fancy water (2 pass) has no visual artifacts";
        }
        else if (btnName.equals("Rain & Snow")) {
            final String[] array8 = array2 = new String[7];
            array8[0] = "Rain & Snow";
            array8[1] = "  Default - as set by setting Graphics";
            array8[2] = "  Fast  - light rain/snow, faster";
            array8[3] = "  Fancy - heavy rain/snow, slower";
            array8[4] = "  OFF - no rain/snow, fastest";
            array8[5] = "When rain is OFF the splashes and rain sounds";
            array8[6] = "are still active.";
        }
        else if (btnName.equals("Sky")) {
            final String[] array9 = array2 = new String[4];
            array9[0] = "Sky";
            array9[1] = "  ON - sky is visible, slower";
            array9[2] = "  OFF  - sky is not visible, faster";
            array9[3] = "When sky is OFF the moon and sun are still visible.";
        }
        else if (btnName.equals("Sun & Moon")) {
            final String[] array10 = array2 = new String[3];
            array10[0] = "Sun & Moon";
            array10[1] = "  ON - sun and moon are visible (default)";
            array10[2] = "  OFF  - sun and moon are not visible (faster)";
        }
        else if (btnName.equals("Stars")) {
            final String[] array11 = array2 = new String[3];
            array11[0] = "Stars";
            array11[1] = "  ON - stars are visible, slower";
            array11[2] = "  OFF  - stars are not visible, faster";
        }
        else if (btnName.equals("Depth Fog")) {
            final String[] array12 = array2 = new String[3];
            array12[0] = "Depth Fog";
            array12[1] = "  ON - fog moves closer at bedrock levels (default)";
            array12[2] = "  OFF - same fog at all levels";
        }
        else if (btnName.equals("Show Capes")) {
            final String[] array13 = array2 = new String[3];
            array13[0] = "Show Capes";
            array13[1] = "  ON - show player capes (default)";
            array13[2] = "  OFF - do not show player capes";
        }
        else if (btnName.equals("Held Item Tooltips")) {
            final String[] array14 = array2 = new String[3];
            array14[0] = "Held item tooltips";
            array14[1] = "  ON - show tooltips for held items (default)";
            array14[2] = "  OFF - do not show tooltips for held items";
        }
        else if (btnName.equals("Translucent Blocks")) {
            final String[] array15 = array2 = new String[6];
            array15[0] = "Translucent Blocks";
            array15[1] = "  Fancy - correct color blending (default)";
            array15[2] = "  Fast - fast color blending (faster)";
            array15[3] = "Controls the color blending of translucent blocks";
            array15[4] = "with different color (stained glass, water, ice)";
            array15[5] = "when placed behind each other with air between them.";
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
