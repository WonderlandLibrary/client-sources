package HORIZON-6-0-SKIDPROTECTION;

import java.io.IOException;

public class GuiOptions extends GuiScreen implements GuiYesNoCallback
{
    private static final GameSettings.HorizonCode_Horizon_È[] Â;
    private final GuiScreen Ý;
    private final GameSettings Ø­áŒŠá;
    private GuiButton Âµá€;
    protected String HorizonCode_Horizon_È;
    private static final String Ó = "CL_00000700";
    
    static {
        Â = new GameSettings.HorizonCode_Horizon_È[] { GameSettings.HorizonCode_Horizon_È.Ý };
    }
    
    public GuiOptions(final GuiScreen p_i1046_1_, final GameSettings p_i1046_2_) {
        this.HorizonCode_Horizon_È = "Options";
        this.Ý = p_i1046_1_;
        this.Ø­áŒŠá = p_i1046_2_;
    }
    
    @Override
    public void HorizonCode_Horizon_È() {
        int var1 = 0;
        this.HorizonCode_Horizon_È = I18n.HorizonCode_Horizon_È("options.title", new Object[0]);
        for (final GameSettings.HorizonCode_Horizon_È var5 : GuiOptions.Â) {
            if (var5.HorizonCode_Horizon_È()) {
                this.ÇŽÉ.add(new GuiFlatSlider(var5.Ý(), GuiOptions.Çªà¢ / 2 - 155 + var1 % 2 * 160, GuiOptions.Ê / 6 - 12 + 24 * (var1 >> 1), var5));
            }
            else {
                final GuiOptionsFlatButton var6 = new GuiOptionsFlatButton(var5.Ý(), GuiOptions.Çªà¢ / 2 - 155 + var1 % 2 * 160, GuiOptions.Ê / 6 - 12 + 24 * (var1 >> 1), var5, this.Ø­áŒŠá.Ý(var5));
                this.ÇŽÉ.add(var6);
            }
            ++var1;
        }
        if (GuiOptions.Ñ¢á.áŒŠÆ != null) {
            final EnumDifficulty var7 = GuiOptions.Ñ¢á.áŒŠÆ.ŠÂµà();
            this.Âµá€ = new GuiMenuButton(108, GuiOptions.Çªà¢ / 2 - 155 + var1 % 2 * 160, GuiOptions.Ê / 6 - 12 + 24 * (var1 >> 1), 150, 20, this.HorizonCode_Horizon_È(var7));
            this.ÇŽÉ.add(this.Âµá€);
            if (GuiOptions.Ñ¢á.ÇŽÉ() && !GuiOptions.Ñ¢á.áŒŠÆ.ŒÏ().¥Æ()) {
                this.Âµá€.HorizonCode_Horizon_È(this.Âµá€.Ø­áŒŠá());
            }
            else {
                this.Âµá€.µà = false;
            }
        }
        this.ÇŽÉ.add(new GuiMenuButton(110, GuiOptions.Çªà¢ / 2 - 155, GuiOptions.Ê / 6 + 48 - 6, 150, 20, I18n.HorizonCode_Horizon_È("options.skinCustomisation", new Object[0])));
        this.ÇŽÉ.add(new GuiMenuButton(8675309, GuiOptions.Çªà¢ / 2 + 5, GuiOptions.Ê / 6 + 48 - 6, 150, 20, "Super Secret Settings...") {
            private static final String Â = "CL_00000701";
            
            @Override
            public void HorizonCode_Horizon_È(final SoundHandler soundHandlerIn) {
                final SoundEventAccessorComposite var2 = soundHandlerIn.HorizonCode_Horizon_È(SoundCategory.à, SoundCategory.Âµá€, SoundCategory.Ó, SoundCategory.Ø, SoundCategory.Ø­áŒŠá);
                if (var2 != null) {
                    soundHandlerIn.HorizonCode_Horizon_È(PositionedSoundRecord.HorizonCode_Horizon_È(var2.Ø­áŒŠá(), 0.5f));
                }
            }
        });
        this.ÇŽÉ.add(new GuiMenuButton(106, GuiOptions.Çªà¢ / 2 - 155, GuiOptions.Ê / 6 + 72 - 6, 150, 20, I18n.HorizonCode_Horizon_È("options.sounds", new Object[0])));
        this.ÇŽÉ.add(new GuiMenuButton(107, GuiOptions.Çªà¢ / 2 + 5, GuiOptions.Ê / 6 + 72 - 6, 150, 20, I18n.HorizonCode_Horizon_È("options.stream", new Object[0])));
        this.ÇŽÉ.add(new GuiMenuButton(101, GuiOptions.Çªà¢ / 2 - 155, GuiOptions.Ê / 6 + 96 - 6, 150, 20, I18n.HorizonCode_Horizon_È("options.video", new Object[0])));
        this.ÇŽÉ.add(new GuiMenuButton(100, GuiOptions.Çªà¢ / 2 + 5, GuiOptions.Ê / 6 + 96 - 6, 150, 20, I18n.HorizonCode_Horizon_È("options.controls", new Object[0])));
        this.ÇŽÉ.add(new GuiMenuButton(102, GuiOptions.Çªà¢ / 2 - 155, GuiOptions.Ê / 6 + 120 - 6, 150, 20, I18n.HorizonCode_Horizon_È("options.language", new Object[0])));
        this.ÇŽÉ.add(new GuiMenuButton(103, GuiOptions.Çªà¢ / 2 + 5, GuiOptions.Ê / 6 + 120 - 6, 150, 20, I18n.HorizonCode_Horizon_È("options.multiplayer.title", new Object[0])));
        this.ÇŽÉ.add(new GuiMenuButton(105, GuiOptions.Çªà¢ / 2 - 155, GuiOptions.Ê / 6 + 144 - 6, 150, 20, I18n.HorizonCode_Horizon_È("options.resourcepack", new Object[0])));
        this.ÇŽÉ.add(new GuiMenuButton(104, GuiOptions.Çªà¢ / 2 + 5, GuiOptions.Ê / 6 + 144 - 6, 150, 20, I18n.HorizonCode_Horizon_È("options.snooper.view", new Object[0])));
        this.ÇŽÉ.add(new GuiMenuButton(200, GuiOptions.Çªà¢ / 2 - 100, GuiOptions.Ê / 6 + 168, I18n.HorizonCode_Horizon_È("gui.done", new Object[0])));
    }
    
    public String HorizonCode_Horizon_È(final EnumDifficulty p_175355_1_) {
        final ChatComponentText var2 = new ChatComponentText("");
        var2.HorizonCode_Horizon_È(new ChatComponentTranslation("options.difficulty", new Object[0]));
        var2.Â(": ");
        var2.HorizonCode_Horizon_È(new ChatComponentTranslation(p_175355_1_.Â(), new Object[0]));
        return var2.áŒŠÆ();
    }
    
    @Override
    public void HorizonCode_Horizon_È(final boolean result, final int id) {
        GuiOptions.Ñ¢á.HorizonCode_Horizon_È(this);
        if (id == 109 && result && GuiOptions.Ñ¢á.áŒŠÆ != null) {
            GuiOptions.Ñ¢á.áŒŠÆ.ŒÏ().Âµá€(true);
            this.Âµá€.µà = false;
        }
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final GuiButton button) throws IOException {
        if (button.µà) {
            if (button.£à < 100 && button instanceof GuiOptionButton) {
                final GameSettings.HorizonCode_Horizon_È var2 = ((GuiOptionButton)button).HorizonCode_Horizon_È();
                this.Ø­áŒŠá.HorizonCode_Horizon_È(var2, 1);
                button.Å = this.Ø­áŒŠá.Ý(GameSettings.HorizonCode_Horizon_È.HorizonCode_Horizon_È(button.£à));
            }
            if (button.£à == 108) {
                GuiOptions.Ñ¢á.áŒŠÆ.ŒÏ().HorizonCode_Horizon_È(EnumDifficulty.HorizonCode_Horizon_È(GuiOptions.Ñ¢á.áŒŠÆ.ŠÂµà().HorizonCode_Horizon_È() + 1));
                this.Âµá€.Å = this.HorizonCode_Horizon_È(GuiOptions.Ñ¢á.áŒŠÆ.ŠÂµà());
            }
            if (button.£à == 109) {
                GuiOptions.Ñ¢á.HorizonCode_Horizon_È(new GuiYesNo(this, new ChatComponentTranslation("difficulty.lock.title", new Object[0]).áŒŠÆ(), new ChatComponentTranslation("difficulty.lock.question", new Object[] { new ChatComponentTranslation(GuiOptions.Ñ¢á.áŒŠÆ.ŒÏ().Ï­Ðƒà().Â(), new Object[0]) }).áŒŠÆ(), 109));
            }
            if (button.£à == 110) {
                GuiOptions.Ñ¢á.ŠÄ.Â();
                GuiOptions.Ñ¢á.HorizonCode_Horizon_È(new GuiCustomizeSkin(this));
            }
            if (button.£à == 8675309) {
                GuiOptions.Ñ¢á.µÕ.Ý();
            }
            if (button.£à == 101) {
                GuiOptions.Ñ¢á.ŠÄ.Â();
                GuiOptions.Ñ¢á.HorizonCode_Horizon_È(new GuiVideoSettings(this, this.Ø­áŒŠá));
            }
            if (button.£à == 100) {
                GuiOptions.Ñ¢á.ŠÄ.Â();
                GuiOptions.Ñ¢á.HorizonCode_Horizon_È(new GuiControls(this, this.Ø­áŒŠá));
            }
            if (button.£à == 102) {
                GuiOptions.Ñ¢á.ŠÄ.Â();
                GuiOptions.Ñ¢á.HorizonCode_Horizon_È(new GuiLanguage(this, this.Ø­áŒŠá, GuiOptions.Ñ¢á.È()));
            }
            if (button.£à == 103) {
                GuiOptions.Ñ¢á.ŠÄ.Â();
                GuiOptions.Ñ¢á.HorizonCode_Horizon_È(new ScreenChatOptions(this, this.Ø­áŒŠá));
            }
            if (button.£à == 104) {
                GuiOptions.Ñ¢á.ŠÄ.Â();
                GuiOptions.Ñ¢á.HorizonCode_Horizon_È(new GuiSnooper(this, this.Ø­áŒŠá));
            }
            if (button.£à == 200) {
                Horizon.à¢.áˆºáˆºÈ.HorizonCode_Horizon_È(Horizon.à¢.áŒŠ + "/mainmenu/menuback.wav");
                Horizon.à¢.áˆºáˆºÈ.HorizonCode_Horizon_È(-18.0f);
                GuiOptions.Ñ¢á.ŠÄ.Â();
                GuiOptions.Ñ¢á.HorizonCode_Horizon_È(this.Ý);
            }
            if (button.£à == 105) {
                GuiOptions.Ñ¢á.ŠÄ.Â();
                GuiOptions.Ñ¢á.HorizonCode_Horizon_È(new GuiScreenResourcePacks(this));
            }
            if (button.£à == 106) {
                GuiOptions.Ñ¢á.ŠÄ.Â();
                GuiOptions.Ñ¢á.HorizonCode_Horizon_È(new GuiScreenOptionsSounds(this, this.Ø­áŒŠá));
            }
            if (button.£à == 107) {
                GuiOptions.Ñ¢á.ŠÄ.Â();
                final IStream var3 = GuiOptions.Ñ¢á.Ä();
                if (var3.áŒŠÆ() && var3.ŠÄ()) {
                    GuiOptions.Ñ¢á.HorizonCode_Horizon_È(new GuiStreamOptions(this, this.Ø­áŒŠá));
                }
                else {
                    GuiStreamUnavailable.HorizonCode_Horizon_È(this);
                }
            }
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int mouseX, final int mouseY, final float partialTicks) {
        GuiUtils.HorizonCode_Horizon_È().HorizonCode_Horizon_È(0.0f, 0.0f, GuiScreen.Çªà¢, (float)GuiScreen.Ê, -8418163);
        this.HorizonCode_Horizon_È(this.É, this.HorizonCode_Horizon_È, GuiOptions.Çªà¢ / 2, 15, 16777215);
        super.HorizonCode_Horizon_È(mouseX, mouseY, partialTicks);
        if (!Horizon.à¢.Ï.HorizonCode_Horizon_È.isRunning() && Horizon.Âµà && GuiOptions.Ñ¢á.á == null) {
            Horizon.à¢.Ï.HorizonCode_Horizon_È(Horizon.à¢.áŒŠ + "/mainmenu/menusong.wav");
            Horizon.à¢.Ï.HorizonCode_Horizon_È(-28.0f);
        }
    }
}
