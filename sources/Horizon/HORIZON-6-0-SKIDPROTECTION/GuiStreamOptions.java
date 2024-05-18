package HORIZON-6-0-SKIDPROTECTION;

import java.io.IOException;

public class GuiStreamOptions extends GuiScreen
{
    private static final GameSettings.HorizonCode_Horizon_È[] HorizonCode_Horizon_È;
    private static final GameSettings.HorizonCode_Horizon_È[] Â;
    private final GuiScreen Ý;
    private final GameSettings Ø­áŒŠá;
    private String Âµá€;
    private String Ó;
    private int à;
    private boolean Ø;
    private static final String áŒŠÆ = "CL_00001841";
    
    static {
        HorizonCode_Horizon_È = new GameSettings.HorizonCode_Horizon_È[] { GameSettings.HorizonCode_Horizon_È.ˆá, GameSettings.HorizonCode_Horizon_È.á€, GameSettings.HorizonCode_Horizon_È.áƒ, GameSettings.HorizonCode_Horizon_È.à¢, GameSettings.HorizonCode_Horizon_È.ÇŽÕ, GameSettings.HorizonCode_Horizon_È.É, GameSettings.HorizonCode_Horizon_È.Âµà, GameSettings.HorizonCode_Horizon_È.Õ };
        Â = new GameSettings.HorizonCode_Horizon_È[] { GameSettings.HorizonCode_Horizon_È.ŠÂµà, GameSettings.HorizonCode_Horizon_È.¥à };
    }
    
    public GuiStreamOptions(final GuiScreen p_i1073_1_, final GameSettings p_i1073_2_) {
        this.Ø = false;
        this.Ý = p_i1073_1_;
        this.Ø­áŒŠá = p_i1073_2_;
    }
    
    @Override
    public void HorizonCode_Horizon_È() {
        int var1 = 0;
        this.Âµá€ = I18n.HorizonCode_Horizon_È("options.stream.title", new Object[0]);
        this.Ó = I18n.HorizonCode_Horizon_È("options.stream.chat.title", new Object[0]);
        for (final GameSettings.HorizonCode_Horizon_È var5 : GuiStreamOptions.HorizonCode_Horizon_È) {
            if (var5.HorizonCode_Horizon_È()) {
                this.ÇŽÉ.add(new GuiOptionSlider(var5.Ý(), GuiStreamOptions.Çªà¢ / 2 - 155 + var1 % 2 * 160, GuiStreamOptions.Ê / 6 + 24 * (var1 >> 1), var5));
            }
            else {
                this.ÇŽÉ.add(new GuiOptionButton(var5.Ý(), GuiStreamOptions.Çªà¢ / 2 - 155 + var1 % 2 * 160, GuiStreamOptions.Ê / 6 + 24 * (var1 >> 1), var5, this.Ø­áŒŠá.Ý(var5)));
            }
            ++var1;
        }
        if (var1 % 2 == 1) {
            ++var1;
        }
        this.à = GuiStreamOptions.Ê / 6 + 24 * (var1 >> 1) + 6;
        var1 += 2;
        for (final GameSettings.HorizonCode_Horizon_È var5 : GuiStreamOptions.Â) {
            if (var5.HorizonCode_Horizon_È()) {
                this.ÇŽÉ.add(new GuiOptionSlider(var5.Ý(), GuiStreamOptions.Çªà¢ / 2 - 155 + var1 % 2 * 160, GuiStreamOptions.Ê / 6 + 24 * (var1 >> 1), var5));
            }
            else {
                this.ÇŽÉ.add(new GuiOptionButton(var5.Ý(), GuiStreamOptions.Çªà¢ / 2 - 155 + var1 % 2 * 160, GuiStreamOptions.Ê / 6 + 24 * (var1 >> 1), var5, this.Ø­áŒŠá.Ý(var5)));
            }
            ++var1;
        }
        this.ÇŽÉ.add(new GuiButton(200, GuiStreamOptions.Çªà¢ / 2 - 155, GuiStreamOptions.Ê / 6 + 168, 150, 20, I18n.HorizonCode_Horizon_È("gui.done", new Object[0])));
        final GuiButton var6 = new GuiButton(201, GuiStreamOptions.Çªà¢ / 2 + 5, GuiStreamOptions.Ê / 6 + 168, 150, 20, I18n.HorizonCode_Horizon_È("options.stream.ingestSelection", new Object[0]));
        var6.µà = ((GuiStreamOptions.Ñ¢á.Ä().áˆºÑ¢Õ() && GuiStreamOptions.Ñ¢á.Ä().¥Æ().length > 0) || GuiStreamOptions.Ñ¢á.Ä().Æ());
        this.ÇŽÉ.add(var6);
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final GuiButton button) throws IOException {
        if (button.µà) {
            if (button.£à < 100 && button instanceof GuiOptionButton) {
                final GameSettings.HorizonCode_Horizon_È var2 = ((GuiOptionButton)button).HorizonCode_Horizon_È();
                this.Ø­áŒŠá.HorizonCode_Horizon_È(var2, 1);
                button.Å = this.Ø­áŒŠá.Ý(GameSettings.HorizonCode_Horizon_È.HorizonCode_Horizon_È(button.£à));
                if (GuiStreamOptions.Ñ¢á.Ä().ÂµÈ() && var2 != GameSettings.HorizonCode_Horizon_È.ŠÂµà && var2 != GameSettings.HorizonCode_Horizon_È.¥à) {
                    this.Ø = true;
                }
            }
            else if (button instanceof GuiOptionSlider) {
                if (button.£à == GameSettings.HorizonCode_Horizon_È.ÇŽÕ.Ý()) {
                    GuiStreamOptions.Ñ¢á.Ä().£à();
                }
                else if (button.£à == GameSettings.HorizonCode_Horizon_È.É.Ý()) {
                    GuiStreamOptions.Ñ¢á.Ä().£à();
                }
                else if (GuiStreamOptions.Ñ¢á.Ä().ÂµÈ()) {
                    this.Ø = true;
                }
            }
            if (button.£à == 200) {
                GuiStreamOptions.Ñ¢á.ŠÄ.Â();
                GuiStreamOptions.Ñ¢á.HorizonCode_Horizon_È(this.Ý);
            }
            else if (button.£à == 201) {
                GuiStreamOptions.Ñ¢á.ŠÄ.Â();
                GuiStreamOptions.Ñ¢á.HorizonCode_Horizon_È(new GuiIngestServers(this));
            }
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int mouseX, final int mouseY, final float partialTicks) {
        GuiUtils.HorizonCode_Horizon_È().HorizonCode_Horizon_È(0.0f, 0.0f, GuiScreen.Çªà¢, (float)GuiScreen.Ê, -8418163);
        this.HorizonCode_Horizon_È(this.É, this.Âµá€, GuiStreamOptions.Çªà¢ / 2, 20, 16777215);
        this.HorizonCode_Horizon_È(this.É, this.Ó, GuiStreamOptions.Çªà¢ / 2, this.à, 16777215);
        if (this.Ø) {
            this.HorizonCode_Horizon_È(this.É, EnumChatFormatting.ˆÏ­ + I18n.HorizonCode_Horizon_È("options.stream.changes", new Object[0]), GuiStreamOptions.Çªà¢ / 2, 20 + this.É.HorizonCode_Horizon_È, 16777215);
        }
        super.HorizonCode_Horizon_È(mouseX, mouseY, partialTicks);
        if (!Horizon.à¢.Ï.HorizonCode_Horizon_È.isRunning() && Horizon.Âµà && GuiStreamOptions.Ñ¢á.á == null) {
            Horizon.à¢.Ï.HorizonCode_Horizon_È(Horizon.à¢.áŒŠ + "/mainmenu/menusong.wav");
            Horizon.à¢.Ï.HorizonCode_Horizon_È(-28.0f);
        }
    }
}
