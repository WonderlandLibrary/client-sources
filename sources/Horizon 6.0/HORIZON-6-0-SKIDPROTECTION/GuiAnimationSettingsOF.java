package HORIZON-6-0-SKIDPROTECTION;

public class GuiAnimationSettingsOF extends GuiScreen
{
    private GuiScreen Â;
    protected String HorizonCode_Horizon_È;
    private GameSettings Ý;
    private static GameSettings.HorizonCode_Horizon_È[] Ø­áŒŠá;
    
    static {
        GuiAnimationSettingsOF.Ø­áŒŠá = new GameSettings.HorizonCode_Horizon_È[] { GameSettings.HorizonCode_Horizon_È.ÇŽá€, GameSettings.HorizonCode_Horizon_È.Ï, GameSettings.HorizonCode_Horizon_È.Ô, GameSettings.HorizonCode_Horizon_È.ÇªÓ, GameSettings.HorizonCode_Horizon_È.Ø­á, GameSettings.HorizonCode_Horizon_È.ˆÉ, GameSettings.HorizonCode_Horizon_È.Ï­Ï­Ï, GameSettings.HorizonCode_Horizon_È.£Â, GameSettings.HorizonCode_Horizon_È.áŒŠÏ, GameSettings.HorizonCode_Horizon_È.áŒŠáŠ, GameSettings.HorizonCode_Horizon_È.ˆÓ, GameSettings.HorizonCode_Horizon_È.¥Ä, GameSettings.HorizonCode_Horizon_È.ÇªÔ, GameSettings.HorizonCode_Horizon_È.ŠÓ, GameSettings.HorizonCode_Horizon_È.ÇªØ­, GameSettings.HorizonCode_Horizon_È.Ðƒà, GameSettings.HorizonCode_Horizon_È.Å };
    }
    
    public GuiAnimationSettingsOF(final GuiScreen guiscreen, final GameSettings gamesettings) {
        this.HorizonCode_Horizon_È = "Animation Settings";
        this.Â = guiscreen;
        this.Ý = gamesettings;
    }
    
    @Override
    public void HorizonCode_Horizon_È() {
        int i = 0;
        for (final GameSettings.HorizonCode_Horizon_È enumoptions : GuiAnimationSettingsOF.Ø­áŒŠá) {
            final int x = GuiAnimationSettingsOF.Çªà¢ / 2 - 155 + i % 2 * 160;
            final int y = GuiAnimationSettingsOF.Ê / 6 + 21 * (i / 2) - 10;
            if (!enumoptions.HorizonCode_Horizon_È()) {
                this.ÇŽÉ.add(new GuiOptionButton(enumoptions.Ý(), x, y, enumoptions, this.Ý.Ý(enumoptions)));
            }
            else {
                this.ÇŽÉ.add(new GuiOptionSlider(enumoptions.Ý(), x, y, enumoptions));
            }
            ++i;
        }
        this.ÇŽÉ.add(new GuiButton(210, GuiAnimationSettingsOF.Çªà¢ / 2 - 155, GuiAnimationSettingsOF.Ê / 6 + 168 + 11, 70, 20, "All ON"));
        this.ÇŽÉ.add(new GuiButton(211, GuiAnimationSettingsOF.Çªà¢ / 2 - 155 + 80, GuiAnimationSettingsOF.Ê / 6 + 168 + 11, 70, 20, "All OFF"));
        this.ÇŽÉ.add(new GuiOptionButton(200, GuiAnimationSettingsOF.Çªà¢ / 2 + 5, GuiAnimationSettingsOF.Ê / 6 + 168 + 11, I18n.HorizonCode_Horizon_È("gui.done", new Object[0])));
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final GuiButton guibutton) {
        if (guibutton.µà) {
            if (guibutton.£à < 200 && guibutton instanceof GuiOptionButton) {
                this.Ý.HorizonCode_Horizon_È(((GuiOptionButton)guibutton).HorizonCode_Horizon_È(), 1);
                guibutton.Å = this.Ý.Ý(GameSettings.HorizonCode_Horizon_È.HorizonCode_Horizon_È(guibutton.£à));
            }
            if (guibutton.£à == 200) {
                GuiAnimationSettingsOF.Ñ¢á.ŠÄ.Â();
                GuiAnimationSettingsOF.Ñ¢á.HorizonCode_Horizon_È(this.Â);
            }
            if (guibutton.£à == 210) {
                GuiAnimationSettingsOF.Ñ¢á.ŠÄ.HorizonCode_Horizon_È(true);
            }
            if (guibutton.£à == 211) {
                GuiAnimationSettingsOF.Ñ¢á.ŠÄ.HorizonCode_Horizon_È(false);
            }
            if (guibutton.£à != GameSettings.HorizonCode_Horizon_È.Ñ¢Â.ordinal()) {
                final ScaledResolution scaledresolution = new ScaledResolution(GuiAnimationSettingsOF.Ñ¢á, GuiAnimationSettingsOF.Ñ¢á.Ó, GuiAnimationSettingsOF.Ñ¢á.à);
                final int i = scaledresolution.HorizonCode_Horizon_È();
                final int j = scaledresolution.Â();
                this.HorizonCode_Horizon_È(GuiAnimationSettingsOF.Ñ¢á, i, j);
            }
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int i, final int j, final float f) {
        this.£á();
        this.HorizonCode_Horizon_È(this.É, this.HorizonCode_Horizon_È, GuiAnimationSettingsOF.Çªà¢ / 2, 20, 16777215);
        super.HorizonCode_Horizon_È(i, j, f);
    }
}
