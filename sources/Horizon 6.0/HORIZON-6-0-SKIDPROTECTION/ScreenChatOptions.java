package HORIZON-6-0-SKIDPROTECTION;

import java.io.IOException;

public class ScreenChatOptions extends GuiScreen
{
    private static final GameSettings.HorizonCode_Horizon_È[] HorizonCode_Horizon_È;
    private final GuiScreen Â;
    private final GameSettings Ý;
    private String Ø­áŒŠá;
    private String Âµá€;
    private int Ó;
    private static final String à = "CL_00000681";
    
    static {
        HorizonCode_Horizon_È = new GameSettings.HorizonCode_Horizon_È[] { GameSettings.HorizonCode_Horizon_È.£à, GameSettings.HorizonCode_Horizon_È.µà, GameSettings.HorizonCode_Horizon_È.ˆà, GameSettings.HorizonCode_Horizon_È.¥Æ, GameSettings.HorizonCode_Horizon_È.Ø­à, GameSettings.HorizonCode_Horizon_È.ŠÄ, GameSettings.HorizonCode_Horizon_È.ŒÏ, GameSettings.HorizonCode_Horizon_È.Çªà¢, GameSettings.HorizonCode_Horizon_È.Ñ¢á, GameSettings.HorizonCode_Horizon_È.È };
    }
    
    public ScreenChatOptions(final GuiScreen p_i1023_1_, final GameSettings p_i1023_2_) {
        this.Â = p_i1023_1_;
        this.Ý = p_i1023_2_;
    }
    
    @Override
    public void HorizonCode_Horizon_È() {
        int var1 = 0;
        this.Ø­áŒŠá = I18n.HorizonCode_Horizon_È("options.chat.title", new Object[0]);
        this.Âµá€ = I18n.HorizonCode_Horizon_È("options.multiplayer.title", new Object[0]);
        for (final GameSettings.HorizonCode_Horizon_È var5 : ScreenChatOptions.HorizonCode_Horizon_È) {
            if (var5.HorizonCode_Horizon_È()) {
                this.ÇŽÉ.add(new GuiOptionSlider(var5.Ý(), ScreenChatOptions.Çªà¢ / 2 - 155 + var1 % 2 * 160, ScreenChatOptions.Ê / 6 + 24 * (var1 >> 1), var5));
            }
            else {
                this.ÇŽÉ.add(new GuiOptionButton(var5.Ý(), ScreenChatOptions.Çªà¢ / 2 - 155 + var1 % 2 * 160, ScreenChatOptions.Ê / 6 + 24 * (var1 >> 1), var5, this.Ý.Ý(var5)));
            }
            ++var1;
        }
        if (var1 % 2 == 1) {
            ++var1;
        }
        this.Ó = ScreenChatOptions.Ê / 6 + 24 * (var1 >> 1);
        this.ÇŽÉ.add(new GuiButton(200, ScreenChatOptions.Çªà¢ / 2 - 100, ScreenChatOptions.Ê / 6 + 120, I18n.HorizonCode_Horizon_È("gui.done", new Object[0])));
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final GuiButton button) throws IOException {
        if (button.µà) {
            if (button.£à < 100 && button instanceof GuiOptionButton) {
                this.Ý.HorizonCode_Horizon_È(((GuiOptionButton)button).HorizonCode_Horizon_È(), 1);
                button.Å = this.Ý.Ý(GameSettings.HorizonCode_Horizon_È.HorizonCode_Horizon_È(button.£à));
            }
            if (button.£à == 200) {
                ScreenChatOptions.Ñ¢á.ŠÄ.Â();
                ScreenChatOptions.Ñ¢á.HorizonCode_Horizon_È(this.Â);
            }
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int mouseX, final int mouseY, final float partialTicks) {
        GuiUtils.HorizonCode_Horizon_È().HorizonCode_Horizon_È(0.0f, 0.0f, GuiScreen.Çªà¢, (float)GuiScreen.Ê, -8418163);
        this.HorizonCode_Horizon_È(this.É, this.Ø­áŒŠá, ScreenChatOptions.Çªà¢ / 2, 20, 16777215);
        this.HorizonCode_Horizon_È(this.É, this.Âµá€, ScreenChatOptions.Çªà¢ / 2, this.Ó + 7, 16777215);
        super.HorizonCode_Horizon_È(mouseX, mouseY, partialTicks);
        if (!Horizon.à¢.Ï.HorizonCode_Horizon_È.isRunning() && Horizon.Âµà && ScreenChatOptions.Ñ¢á.á == null) {
            Horizon.à¢.Ï.HorizonCode_Horizon_È(Horizon.à¢.áŒŠ + "/mainmenu/menusong.wav");
            Horizon.à¢.Ï.HorizonCode_Horizon_È(-28.0f);
        }
    }
}
