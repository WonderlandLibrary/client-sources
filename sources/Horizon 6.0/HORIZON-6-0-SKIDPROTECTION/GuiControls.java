package HORIZON-6-0-SKIDPROTECTION;

import java.io.IOException;

public class GuiControls extends GuiScreen
{
    private static final GameSettings.HorizonCode_Horizon_È[] Ø­áŒŠá;
    private GuiScreen Âµá€;
    protected String HorizonCode_Horizon_È;
    private GameSettings Ó;
    public KeyBinding Â;
    public long Ý;
    private GuiKeyBindingList à;
    private GuiButton Ø;
    private static final String áŒŠÆ = "CL_00000736";
    
    static {
        Ø­áŒŠá = new GameSettings.HorizonCode_Horizon_È[] { GameSettings.HorizonCode_Horizon_È.HorizonCode_Horizon_È, GameSettings.HorizonCode_Horizon_È.Â, GameSettings.HorizonCode_Horizon_È.áŒŠà };
    }
    
    public GuiControls(final GuiScreen p_i1027_1_, final GameSettings p_i1027_2_) {
        this.HorizonCode_Horizon_È = "Controls";
        this.Â = null;
        this.Âµá€ = p_i1027_1_;
        this.Ó = p_i1027_2_;
    }
    
    @Override
    public void HorizonCode_Horizon_È() {
        this.à = new GuiKeyBindingList(this, GuiControls.Ñ¢á);
        this.ÇŽÉ.add(new GuiMenuButton(200, GuiControls.Çªà¢ / 2 - 155, GuiControls.Ê - 29, 150, 20, I18n.HorizonCode_Horizon_È("gui.done", new Object[0])));
        this.ÇŽÉ.add(this.Ø = new GuiMenuButton(201, GuiControls.Çªà¢ / 2 - 155 + 160, GuiControls.Ê - 29, 150, 20, I18n.HorizonCode_Horizon_È("controls.resetAll", new Object[0])));
        this.HorizonCode_Horizon_È = I18n.HorizonCode_Horizon_È("controls.title", new Object[0]);
        int var1 = 0;
        for (final GameSettings.HorizonCode_Horizon_È var5 : GuiControls.Ø­áŒŠá) {
            if (var5.HorizonCode_Horizon_È()) {
                this.ÇŽÉ.add(new GuiOptionFlatSlider(var5.Ý(), GuiControls.Çªà¢ / 2 - 155 + var1 % 2 * 160, 18 + 24 * (var1 >> 1), var5));
            }
            else {
                this.ÇŽÉ.add(new GuiOptionsFlatButton(var5.Ý(), GuiControls.Çªà¢ / 2 - 155 + var1 % 2 * 160, 18 + 24 * (var1 >> 1), var5, this.Ó.Ý(var5)));
            }
            ++var1;
        }
    }
    
    @Override
    public void n_() throws IOException {
        super.n_();
        this.à.Ø();
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final GuiButton button) throws IOException {
        if (button.£à == 200) {
            GuiControls.Ñ¢á.HorizonCode_Horizon_È(this.Âµá€);
        }
        else if (button.£à == 201) {
            for (final KeyBinding var5 : GuiControls.Ñ¢á.ŠÄ.ÇŽØ) {
                var5.Â(var5.Ø());
            }
            KeyBinding.Â();
        }
        else if (button.£à < 100 && button instanceof GuiOptionsFlatButton) {
            this.Ó.HorizonCode_Horizon_È(((GuiOptionsFlatButton)button).Â(), 1);
            button.Å = this.Ó.Ý(GameSettings.HorizonCode_Horizon_È.HorizonCode_Horizon_È(button.£à));
        }
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
        if (this.Â != null) {
            this.Ó.HorizonCode_Horizon_È(this.Â, -100 + mouseButton);
            this.Â = null;
            KeyBinding.Â();
        }
        else if (mouseButton != 0 || !this.à.Â(mouseX, mouseY, mouseButton)) {
            super.HorizonCode_Horizon_È(mouseX, mouseY, mouseButton);
        }
    }
    
    @Override
    protected void Â(final int mouseX, final int mouseY, final int state) {
        if (state != 0 || !this.à.Ý(mouseX, mouseY, state)) {
            super.Â(mouseX, mouseY, state);
        }
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final char typedChar, final int keyCode) throws IOException {
        if (this.Â != null) {
            if (keyCode == 1) {
                this.Ó.HorizonCode_Horizon_È(this.Â, 0);
            }
            else if (keyCode != 0) {
                this.Ó.HorizonCode_Horizon_È(this.Â, keyCode);
            }
            else if (typedChar > '\0') {
                this.Ó.HorizonCode_Horizon_È(this.Â, typedChar + 'Ā');
            }
            this.Â = null;
            this.Ý = Minecraft.áƒ();
            KeyBinding.Â();
        }
        else {
            super.HorizonCode_Horizon_È(typedChar, keyCode);
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int mouseX, final int mouseY, final float partialTicks) {
        this.£á();
        this.à.HorizonCode_Horizon_È(mouseX, mouseY, partialTicks);
        this.HorizonCode_Horizon_È(UIFonts.áŒŠÆ, this.HorizonCode_Horizon_È, GuiControls.Çªà¢ / 2, 2, 16777215);
        boolean var4 = true;
        for (final KeyBinding var8 : this.Ó.ÇŽØ) {
            if (var8.áŒŠÆ() != var8.Ø()) {
                var4 = false;
                break;
            }
        }
        this.Ø.µà = !var4;
        super.HorizonCode_Horizon_È(mouseX, mouseY, partialTicks);
        if (!Horizon.à¢.Ï.HorizonCode_Horizon_È.isRunning() && Horizon.Âµà && GuiControls.Ñ¢á.á == null) {
            Horizon.à¢.Ï.HorizonCode_Horizon_È(Horizon.à¢.áŒŠ + "/mainmenu/menusong.wav");
            Horizon.à¢.Ï.HorizonCode_Horizon_È(-28.0f);
        }
    }
}
