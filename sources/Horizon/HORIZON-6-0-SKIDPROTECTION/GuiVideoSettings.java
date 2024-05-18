package HORIZON-6-0-SKIDPROTECTION;

import java.io.IOException;

public class GuiVideoSettings extends GuiScreen
{
    private GuiScreen Â;
    protected String HorizonCode_Horizon_È;
    private GameSettings Ý;
    private boolean Ø­áŒŠá;
    private static GameSettings.HorizonCode_Horizon_È[] Âµá€;
    private static final String Ó = "CL_00000718";
    private int à;
    private int Ø;
    private long áŒŠÆ;
    
    static {
        GuiVideoSettings.Âµá€ = new GameSettings.HorizonCode_Horizon_È[] { GameSettings.HorizonCode_Horizon_È.á, GameSettings.HorizonCode_Horizon_È.Ó, GameSettings.HorizonCode_Horizon_È.ˆÏ­, GameSettings.HorizonCode_Horizon_È.áŒŠÆ, GameSettings.HorizonCode_Horizon_È.áˆºÏ, GameSettings.HorizonCode_Horizon_È.à, GameSettings.HorizonCode_Horizon_È.£á, GameSettings.HorizonCode_Horizon_È.Ï­Ðƒà, GameSettings.HorizonCode_Horizon_È.Ø­áŒŠá, GameSettings.HorizonCode_Horizon_È.Ç, GameSettings.HorizonCode_Horizon_È.áŠ, GameSettings.HorizonCode_Horizon_È.ˆáŠ, GameSettings.HorizonCode_Horizon_È.Ø };
    }
    
    public GuiVideoSettings(final GuiScreen par1GuiScreen, final GameSettings par2GameSettings) {
        this.HorizonCode_Horizon_È = "Video Settings";
        this.à = 0;
        this.Ø = 0;
        this.áŒŠÆ = 0L;
        this.Â = par1GuiScreen;
        this.Ý = par2GameSettings;
    }
    
    @Override
    public void HorizonCode_Horizon_È() {
        this.HorizonCode_Horizon_È = I18n.HorizonCode_Horizon_È("options.videoTitle", new Object[0]);
        this.ÇŽÉ.clear();
        this.Ø­áŒŠá = false;
        final String[] var2;
        final String[] var1 = var2 = new String[] { "sun.arch.data.model", "com.ibm.vm.bitmode", "os.arch" };
        for (int var3 = var1.length, var4 = 0; var4 < var3; ++var4) {
            final String var5 = var2[var4];
            final String var6 = System.getProperty(var5);
            if (var6 != null && var6.contains("64")) {
                this.Ø­áŒŠá = true;
                break;
            }
        }
        final boolean var7 = false;
        final boolean var8 = !this.Ø­áŒŠá;
        final GameSettings.HorizonCode_Horizon_È[] var9 = GuiVideoSettings.Âµá€;
        final int var10 = var9.length;
        final boolean var11 = false;
        int var12;
        for (var12 = 0; var12 < var10; ++var12) {
            final GameSettings.HorizonCode_Horizon_È y = var9[var12];
            final int x = GuiVideoSettings.Çªà¢ / 2 - 155 + var12 % 2 * 160;
            final int y2 = GuiVideoSettings.Ê / 6 + 21 * (var12 / 2) - 10;
            if (y.HorizonCode_Horizon_È()) {
                this.ÇŽÉ.add(new GuiOptionFlatSlider(y.Ý(), x, y2, y));
            }
            else {
                this.ÇŽÉ.add(new GuiOptionsFlatButton(y.Ý(), x, y2, y, this.Ý.Ý(y)));
            }
        }
        int var13 = GuiVideoSettings.Ê / 6 + 21 * (var12 / 2) - 10;
        final boolean var14 = false;
        int x = GuiVideoSettings.Çªà¢ / 2 - 155 + 160;
        this.ÇŽÉ.add(new GuiOptionsFlatButton(202, x, var13, "Quality..."));
        var13 += 21;
        x = GuiVideoSettings.Çªà¢ / 2 - 155 + 0;
        this.ÇŽÉ.add(new GuiOptionsFlatButton(201, x, var13, "Details..."));
        x = GuiVideoSettings.Çªà¢ / 2 - 155 + 160;
        this.ÇŽÉ.add(new GuiOptionsFlatButton(212, x, var13, "Performance..."));
        var13 += 21;
        x = GuiVideoSettings.Çªà¢ / 2 - 155 + 0;
        this.ÇŽÉ.add(new GuiOptionsFlatButton(211, x, var13, "Animations..."));
        x = GuiVideoSettings.Çªà¢ / 2 - 155 + 160;
        this.ÇŽÉ.add(new GuiOptionsFlatButton(222, x, var13, "Other..."));
        this.ÇŽÉ.add(new GuiMenuButton(200, GuiVideoSettings.Çªà¢ / 2 - 100, GuiVideoSettings.Ê / 6 + 168 + 11, I18n.HorizonCode_Horizon_È("gui.done", new Object[0])));
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final GuiButton par1GuiButton) throws IOException {
        if (par1GuiButton.µà) {
            final int var2 = this.Ý.ŠÑ¢Ó;
            if (par1GuiButton.£à < 200 && par1GuiButton instanceof GuiOptionsFlatButton) {
                this.Ý.HorizonCode_Horizon_È(((GuiOptionsFlatButton)par1GuiButton).Â(), 1);
                par1GuiButton.Å = this.Ý.Ý(GameSettings.HorizonCode_Horizon_È.HorizonCode_Horizon_È(par1GuiButton.£à));
            }
            if (par1GuiButton.£à == 200) {
                GuiVideoSettings.Ñ¢á.ŠÄ.Â();
                GuiVideoSettings.Ñ¢á.HorizonCode_Horizon_È(this.Â);
            }
            if (this.Ý.ŠÑ¢Ó != var2) {
                final ScaledResolution scr = new ScaledResolution(GuiVideoSettings.Ñ¢á, GuiVideoSettings.Ñ¢á.Ó, GuiVideoSettings.Ñ¢á.à);
                final int var3 = scr.HorizonCode_Horizon_È();
                final int var4 = scr.Â();
                this.HorizonCode_Horizon_È(GuiVideoSettings.Ñ¢á, var3, var4);
            }
            if (par1GuiButton.£à == 201) {
                GuiVideoSettings.Ñ¢á.ŠÄ.Â();
                final GuiDetailSettingsOF scr2 = new GuiDetailSettingsOF(this, this.Ý);
                GuiVideoSettings.Ñ¢á.HorizonCode_Horizon_È(scr2);
            }
            if (par1GuiButton.£à == 202) {
                GuiVideoSettings.Ñ¢á.ŠÄ.Â();
                final GuiQualitySettingsOF scr3 = new GuiQualitySettingsOF(this, this.Ý);
                GuiVideoSettings.Ñ¢á.HorizonCode_Horizon_È(scr3);
            }
            if (par1GuiButton.£à == 211) {
                GuiVideoSettings.Ñ¢á.ŠÄ.Â();
                final GuiAnimationSettingsOF scr4 = new GuiAnimationSettingsOF(this, this.Ý);
                GuiVideoSettings.Ñ¢á.HorizonCode_Horizon_È(scr4);
            }
            if (par1GuiButton.£à == 212) {
                GuiVideoSettings.Ñ¢á.ŠÄ.Â();
                final GuiPerformanceSettingsOF scr5 = new GuiPerformanceSettingsOF(this, this.Ý);
                GuiVideoSettings.Ñ¢á.HorizonCode_Horizon_È(scr5);
            }
            if (par1GuiButton.£à == 222) {
                GuiVideoSettings.Ñ¢á.ŠÄ.Â();
                final GuiOtherSettingsOF scr6 = new GuiOtherSettingsOF(this, this.Ý);
                GuiVideoSettings.Ñ¢á.HorizonCode_Horizon_È(scr6);
            }
            if (par1GuiButton.£à == GameSettings.HorizonCode_Horizon_È.áˆºÏ.ordinal()) {
                return;
            }
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int x, final int y, final float z) {
        this.£á();
        this.HorizonCode_Horizon_È(UIFonts.áŒŠÆ, this.HorizonCode_Horizon_È, GuiVideoSettings.Çªà¢ / 2, 10, 16777215);
        if (this.Ø­áŒŠá || this.Ý.Ý > 8) {}
        super.HorizonCode_Horizon_È(x, y, z);
        if (Math.abs(x - this.à) <= 5 && Math.abs(y - this.Ø) <= 5) {
            final short activateDelay = 700;
            if (System.currentTimeMillis() >= this.áŒŠÆ + activateDelay) {
                final int x2 = GuiVideoSettings.Çªà¢ / 2 - 150;
                int y2 = GuiVideoSettings.Ê / 6 - 5;
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
            this.à = x;
            this.Ø = y;
            this.áŒŠÆ = System.currentTimeMillis();
        }
        if (!Horizon.à¢.Ï.HorizonCode_Horizon_È.isRunning() && Horizon.Âµà && GuiVideoSettings.Ñ¢á.á == null) {
            Horizon.à¢.Ï.HorizonCode_Horizon_È(Horizon.à¢.áŒŠ + "/mainmenu/menusong.wav");
            Horizon.à¢.Ï.HorizonCode_Horizon_È(-28.0f);
        }
    }
    
    private String[] HorizonCode_Horizon_È(final String btnName) {
        String[] array2;
        if (btnName.equals("Graphics")) {
            final String[] array = array2 = new String[5];
            array[0] = "Visual quality";
            array[1] = "  Fast  - lower quality, faster";
            array[2] = "  Fancy - higher quality, slower";
            array[3] = "Changes the appearance of clouds, leaves, water,";
            array[4] = "shadows and grass sides.";
        }
        else if (btnName.equals("Render Distance")) {
            final String[] array3 = array2 = new String[8];
            array3[0] = "Visible distance";
            array3[1] = "  2 Tiny - 32m (fastest)";
            array3[2] = "  4 Short - 64m (faster)";
            array3[3] = "  8 Normal - 128m";
            array3[4] = "  16 Far - 256m (slower)";
            array3[5] = "  32 Extreme - 512m (slowest!)";
            array3[6] = "The Extreme view distance is very resource demanding!";
            array3[7] = "Values over 16 Far are only effective in local worlds.";
        }
        else if (btnName.equals("Smooth Lighting")) {
            final String[] array4 = array2 = new String[4];
            array4[0] = "Smooth lighting";
            array4[1] = "  OFF - no smooth lighting (faster)";
            array4[2] = "  Minimum - simple smooth lighting (slower)";
            array4[3] = "  Maximum - complex smooth lighting (slowest)";
        }
        else if (btnName.equals("Smooth Lighting Level")) {
            final String[] array5 = array2 = new String[4];
            array5[0] = "Smooth lighting level";
            array5[1] = "  OFF - no smooth lighting (faster)";
            array5[2] = "  1% - light smooth lighting (slower)";
            array5[3] = "  100% - dark smooth lighting (slower)";
        }
        else if (btnName.equals("Max Framerate")) {
            final String[] array6 = array2 = new String[6];
            array6[0] = "Max framerate";
            array6[1] = "  VSync - limit to monitor framerate (60, 30, 20)";
            array6[2] = "  5-255 - variable";
            array6[3] = "  Unlimited - no limit (fastest)";
            array6[4] = "The framerate limit decreases the FPS even if";
            array6[5] = "the limit value is not reached.";
        }
        else if (btnName.equals("View Bobbing")) {
            final String[] array7 = array2 = new String[2];
            array7[0] = "More realistic movement.";
            array7[1] = "When using mipmaps set it to OFF for best results.";
        }
        else if (btnName.equals("GUI Scale")) {
            final String[] array8 = array2 = new String[2];
            array8[0] = "GUI Scale";
            array8[1] = "Smaller GUI might be faster";
        }
        else if (btnName.equals("Server Textures")) {
            final String[] array9 = array2 = new String[2];
            array9[0] = "Server textures";
            array9[1] = "Use the resource pack recommended by the server";
        }
        else if (btnName.equals("Advanced OpenGL")) {
            final String[] array10 = array2 = new String[6];
            array10[0] = "Detect and render only visible geometry";
            array10[1] = "  OFF - all geometry is rendered (slower)";
            array10[2] = "  Fast - only visible geometry is rendered (fastest)";
            array10[3] = "  Fancy - conservative, avoids visual artifacts (faster)";
            array10[4] = "The option is available only if it is supported by the ";
            array10[5] = "graphic card.";
        }
        else if (btnName.equals("Fog")) {
            final String[] array11 = array2 = new String[6];
            array11[0] = "Fog type";
            array11[1] = "  Fast - faster fog";
            array11[2] = "  Fancy - slower fog, looks better";
            array11[3] = "  OFF - no fog, fastest";
            array11[4] = "The fancy fog is available only if it is supported by the ";
            array11[5] = "graphic card.";
        }
        else if (btnName.equals("Fog Start")) {
            final String[] array12 = array2 = new String[4];
            array12[0] = "Fog start";
            array12[1] = "  0.2 - the fog starts near the player";
            array12[2] = "  0.8 - the fog starts far from the player";
            array12[3] = "This option usually does not affect the performance.";
        }
        else if (btnName.equals("Brightness")) {
            final String[] array13 = array2 = new String[5];
            array13[0] = "Increases the brightness of darker objects";
            array13[1] = "  OFF - standard brightness";
            array13[2] = "  100% - maximum brightness for darker objects";
            array13[3] = "This options does not change the brightness of ";
            array13[4] = "fully black objects";
        }
        else if (btnName.equals("Chunk Loading")) {
            final String[] array14 = array2 = new String[8];
            array14[0] = "Chunk Loading";
            array14[1] = "  Default - unstable FPS when loading chunks";
            array14[2] = "  Smooth - stable FPS";
            array14[3] = "  Multi-Core - stable FPS, 3x faster world loading";
            array14[4] = "Smooth and Multi-Core remove the stuttering and ";
            array14[5] = "freezes caused by chunk loading.";
            array14[6] = "Multi-Core can speed up 3x the world loading and";
            array14[7] = "increase FPS by using a second CPU core.";
        }
        else if (btnName.equals("Alternate Blocks")) {
            final String[] array15 = array2 = new String[3];
            array15[0] = "Alternate Blocks";
            array15[1] = "Uses alternative block models for some blocks.";
            array15[2] = "Depends on the selected resource pack.";
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
            final boolean flag = i >= btn.ˆÏ­ && j >= btn.£á && i < btn.ˆÏ­ + btn.ÂµÈ && j < btn.£á + btn.á;
            if (flag) {
                return btn;
            }
        }
        return null;
    }
    
    public static int Â(final GuiButton btn) {
        return btn.ÂµÈ;
    }
    
    public static int Ý(final GuiButton btn) {
        return btn.á;
    }
}
