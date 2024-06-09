package HORIZON-6-0-SKIDPROTECTION;

public class GuiOtherSettingsOF extends GuiScreen implements GuiYesNoCallback
{
    private GuiScreen Â;
    protected String HorizonCode_Horizon_È;
    private GameSettings Ý;
    private static GameSettings.HorizonCode_Horizon_È[] Ø­áŒŠá;
    private int Âµá€;
    private int Ó;
    private long à;
    
    static {
        GuiOtherSettingsOF.Ø­áŒŠá = new GameSettings.HorizonCode_Horizon_È[] { GameSettings.HorizonCode_Horizon_È.ˆáƒ, GameSettings.HorizonCode_Horizon_È.Û, GameSettings.HorizonCode_Horizon_È.£Ó, GameSettings.HorizonCode_Horizon_È.ˆÏ, GameSettings.HorizonCode_Horizon_È.Æ, GameSettings.HorizonCode_Horizon_È.Ñ¢à, GameSettings.HorizonCode_Horizon_È.Œ };
    }
    
    public GuiOtherSettingsOF(final GuiScreen guiscreen, final GameSettings gamesettings) {
        this.HorizonCode_Horizon_È = "Other Settings";
        this.Âµá€ = 0;
        this.Ó = 0;
        this.à = 0L;
        this.Â = guiscreen;
        this.Ý = gamesettings;
    }
    
    @Override
    public void HorizonCode_Horizon_È() {
        int i = 0;
        for (final GameSettings.HorizonCode_Horizon_È enumoptions : GuiOtherSettingsOF.Ø­áŒŠá) {
            final int x = GuiOtherSettingsOF.Çªà¢ / 2 - 155 + i % 2 * 160;
            final int y = GuiOtherSettingsOF.Ê / 6 + 21 * (i / 2) - 10;
            if (!enumoptions.HorizonCode_Horizon_È()) {
                this.ÇŽÉ.add(new GuiOptionButton(enumoptions.Ý(), x, y, enumoptions, this.Ý.Ý(enumoptions)));
            }
            else {
                this.ÇŽÉ.add(new GuiOptionSlider(enumoptions.Ý(), x, y, enumoptions));
            }
            ++i;
        }
        this.ÇŽÉ.add(new GuiButton(210, GuiOtherSettingsOF.Çªà¢ / 2 - 100, GuiOtherSettingsOF.Ê / 6 + 168 + 11 - 44, "Reset Video Settings..."));
        this.ÇŽÉ.add(new GuiButton(200, GuiOtherSettingsOF.Çªà¢ / 2 - 100, GuiOtherSettingsOF.Ê / 6 + 168 + 11, I18n.HorizonCode_Horizon_È("gui.done", new Object[0])));
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final GuiButton guibutton) {
        if (guibutton.µà) {
            if (guibutton.£à < 200 && guibutton instanceof GuiOptionButton) {
                this.Ý.HorizonCode_Horizon_È(((GuiOptionButton)guibutton).HorizonCode_Horizon_È(), 1);
                guibutton.Å = this.Ý.Ý(GameSettings.HorizonCode_Horizon_È.HorizonCode_Horizon_È(guibutton.£à));
            }
            if (guibutton.£à == 200) {
                GuiOtherSettingsOF.Ñ¢á.ŠÄ.Â();
                GuiOtherSettingsOF.Ñ¢á.HorizonCode_Horizon_È(this.Â);
            }
            if (guibutton.£à == 210) {
                GuiOtherSettingsOF.Ñ¢á.ŠÄ.Â();
                final GuiYesNo scaledresolution = new GuiYesNo(this, "Reset all video settings to their default values?", "", 9999);
                GuiOtherSettingsOF.Ñ¢á.HorizonCode_Horizon_È(scaledresolution);
            }
            if (guibutton.£à != GameSettings.HorizonCode_Horizon_È.Ñ¢Â.ordinal()) {
                final ScaledResolution scaledresolution2 = new ScaledResolution(GuiOtherSettingsOF.Ñ¢á, GuiOtherSettingsOF.Ñ¢á.Ó, GuiOtherSettingsOF.Ñ¢á.à);
                final int i = scaledresolution2.HorizonCode_Horizon_È();
                final int j = scaledresolution2.Â();
                this.HorizonCode_Horizon_È(GuiOtherSettingsOF.Ñ¢á, i, j);
            }
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final boolean flag, final int i) {
        if (flag) {
            GuiOtherSettingsOF.Ñ¢á.ŠÄ.Ø();
        }
        GuiOtherSettingsOF.Ñ¢á.HorizonCode_Horizon_È(this);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int x, final int y, final float f) {
        this.£á();
        this.HorizonCode_Horizon_È(this.É, this.HorizonCode_Horizon_È, GuiOtherSettingsOF.Çªà¢ / 2, 20, 16777215);
        super.HorizonCode_Horizon_È(x, y, f);
        if (Math.abs(x - this.Âµá€) <= 5 && Math.abs(y - this.Ó) <= 5) {
            final short activateDelay = 700;
            if (System.currentTimeMillis() >= this.à + activateDelay) {
                final int x2 = GuiOtherSettingsOF.Çªà¢ / 2 - 150;
                int y2 = GuiOtherSettingsOF.Ê / 6 - 5;
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
        final String[] array9;
        if (btnName.equals("Autosave")) {
            final String[] array2;
            final String[] array = array2 = new String[3];
            array[0] = "Autosave interval";
            array[1] = "Default autosave interval (2s) is NOT RECOMMENDED.";
            array[2] = "Autosave causes the famous Lag Spike of Death.";
        }
        else if (btnName.equals("Lagometer")) {
            final String[] array2;
            final String[] array3 = array2 = new String[8];
            array3[0] = "Shows the lagometer on the debug screen (F3).";
            array3[1] = "* Orange - Memory garbage collection";
            array3[2] = "* Cyan - Tick";
            array3[3] = "* Blue - Scheduled executables";
            array3[4] = "* Purple - Chunk upload";
            array3[5] = "* Red - Chunk updates";
            array3[6] = "* Yellow - Visibility check";
            array3[7] = "* Green - Render terrain";
        }
        else if (btnName.equals("Debug Profiler")) {
            final String[] array2;
            final String[] array4 = array2 = new String[5];
            array4[0] = "Debug Profiler";
            array4[1] = "  ON - debug profiler is active, slower";
            array4[2] = "  OFF - debug profiler is not active, faster";
            array4[3] = "The debug profiler collects and shows debug information";
            array4[4] = "when the debug screen is open (F3)";
        }
        else if (btnName.equals("Time")) {
            final String[] array2;
            final String[] array5 = array2 = new String[6];
            array5[0] = "Time";
            array5[1] = " Default - normal day/night cycles";
            array5[2] = " Day Only - day only";
            array5[3] = " Night Only - night only";
            array5[4] = "The time setting is only effective in CREATIVE mode";
            array5[5] = "and for local worlds.";
        }
        else if (btnName.equals("Weather")) {
            final String[] array2;
            final String[] array6 = array2 = new String[5];
            array6[0] = "Weather";
            array6[1] = "  ON - weather is active, slower";
            array6[2] = "  OFF - weather is not active, faster";
            array6[3] = "The weather controls rain, snow and thunderstorms.";
            array6[4] = "Weather control is only possible for local worlds.";
        }
        else if (btnName.equals("Fullscreen")) {
            final String[] array2;
            final String[] array7 = array2 = new String[5];
            array7[0] = "Fullscreen";
            array7[1] = "  ON - use fullscreen mode";
            array7[2] = "  OFF - use window mode";
            array7[3] = "Fullscreen mode may be faster or slower than";
            array7[4] = "window mode, depending on the graphics card.";
        }
        else if (btnName.equals("Fullscreen Mode")) {
            final String[] array2;
            final String[] array8 = array2 = new String[5];
            array8[0] = "Fullscreen mode";
            array8[1] = "  Default - use desktop screen resolution, slower";
            array8[2] = "  WxH - use custom screen resolution, may be faster";
            array8[3] = "The selected resolution is used in fullscreen mode (F11).";
            array8[4] = "Lower resolutions should generally be faster.";
        }
        else if (btnName.equals("3D Anaglyph")) {
            array9 = new String[] { "3D mode used with red-cyan 3D glasses." };
        }
        return array9;
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
