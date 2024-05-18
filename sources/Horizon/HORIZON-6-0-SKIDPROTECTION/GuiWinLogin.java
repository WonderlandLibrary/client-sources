package HORIZON-6-0-SKIDPROTECTION;

import java.io.IOException;

public class GuiWinLogin extends GuiScreen
{
    private GuiWinFlatField HorizonCode_Horizon_È;
    private GuiWinPasswordField Â;
    private int Ý;
    private boolean Ø­áŒŠá;
    private boolean Âµá€;
    private boolean Ó;
    private TimeHelper à;
    private static final ResourceLocation_1975012498 Ø;
    private static final ResourceLocation_1975012498 áŒŠÆ;
    private static final ResourceLocation_1975012498 áˆºÑ¢Õ;
    
    static {
        Ø = new ResourceLocation_1975012498("textures/horizon/gui/bg.png");
        áŒŠÆ = new ResourceLocation_1975012498("textures/horizon/gui/lockbg.jpg");
        áˆºÑ¢Õ = new ResourceLocation_1975012498("textures/horizon/gui/profile.png");
    }
    
    public GuiWinLogin() {
        this.Ý = 0;
        this.Ø­áŒŠá = false;
        this.Âµá€ = false;
        this.Ó = false;
        this.à = new TimeHelper();
    }
    
    @Override
    public void HorizonCode_Horizon_È() {
        this.ÇŽÉ.add(new GuiWinButton(0, GuiWinLogin.Çªà¢ / 2 + 85, GuiWinLogin.Ê / 2 + 4, 13, 13, "»"));
        this.HorizonCode_Horizon_È = new GuiWinFlatField(88, this.É, GuiWinLogin.Çªà¢ / 2 - 30, GuiWinLogin.Ê / 2 - 20, 130, 20);
        this.Â = new GuiWinPasswordField(this.É, GuiWinLogin.Çªà¢ / 2 - 30, GuiWinLogin.Ê / 2 + 0, 130, 20);
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final GuiButton button) throws IOException {
        switch (button.£à) {
            case 0: {
                GuiWinLogin.Ñ¢á.HorizonCode_Horizon_È(new GuiMainMenu());
                break;
            }
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int mouseX, final int mouseY, final float partialTicks) {
        if (this.Ø­áŒŠá && this.Âµá€) {
            if (this.Ý >= -45) {
                this.Ý -= 18;
            }
            else {
                this.Ý -= 45;
            }
        }
        if (this.Ý <= -GuiWinLogin.Çªà¢ - 85) {
            this.Âµá€ = false;
            this.Ó = true;
            this.Ý = -GuiWinLogin.Çªà¢ - 85;
        }
        if (this.à.Â(240000L) && this.Ó) {
            this.Ø­áŒŠá = false;
            if (this.Ý >= -25) {
                this.Ó = false;
                this.Ý = -25;
                this.à.Ø­áŒŠá();
            }
            this.Ý += 25;
        }
        final ScaledResolution scaledRes = new ScaledResolution(GuiWinLogin.Ñ¢á, GuiWinLogin.Ñ¢á.Ó, GuiWinLogin.Ñ¢á.à);
        this.Ý(-1);
        GuiWinLogin.Ñ¢á.¥à().HorizonCode_Horizon_È(GuiWinLogin.áˆºÑ¢Õ);
        Gui_1808253012.HorizonCode_Horizon_È(GuiWinLogin.Çªà¢ / 2 - 130, GuiWinLogin.Ê / 2 - 60, 0.0f, 0.0f, 90, 90, 90, 90, 90.0f, 90.0f);
        Gui_1808253012.Â(UIFonts.Å, "Please Login", GuiWinLogin.Çªà¢ / 2 - 32, GuiWinLogin.Ê / 2 - 60, -1);
        this.HorizonCode_Horizon_È.HorizonCode_Horizon_È();
        this.Â.HorizonCode_Horizon_È();
        super.HorizonCode_Horizon_È(mouseX, mouseY, partialTicks);
        GuiWinLogin.Ñ¢á.¥à().HorizonCode_Horizon_È(GuiWinLogin.áŒŠÆ);
        Gui_1808253012.HorizonCode_Horizon_È(0, 0 + this.Ý, 0.0f, 0.0f, scaledRes.HorizonCode_Horizon_È(), scaledRes.Â(), scaledRes.HorizonCode_Horizon_È(), scaledRes.Â(), scaledRes.HorizonCode_Horizon_È(), scaledRes.Â());
        Gui_1808253012.Â(UIFonts.¥Æ, Utils.Â(), 9, GuiWinLogin.Ê - 90 + this.Ý, -1);
        Gui_1808253012.Â(UIFonts.µà, Utils.Ý(), 12, GuiWinLogin.Ê - 40 + this.Ý, -1);
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final char character, final int key) {
        if (character == '\t') {
            if (!this.HorizonCode_Horizon_È.ÂµÈ()) {
                this.HorizonCode_Horizon_È.Â(true);
                this.Â.Âµá€(false);
            }
            else if (!this.Â.á()) {
                this.HorizonCode_Horizon_È.Â(false);
                this.Â.Âµá€(true);
            }
        }
        if (character == '\r') {
            try {
                this.HorizonCode_Horizon_È(this.ÇŽÉ.get(0));
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(character, key);
        this.Â.HorizonCode_Horizon_È(character, key);
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final int x, final int y, final int button) {
        try {
            if (button == 0) {
                this.Ø­áŒŠá = true;
                this.Âµá€ = true;
                this.à.Ø­áŒŠá();
            }
            super.HorizonCode_Horizon_È(x, y, button);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(x, y, button);
        this.Â.HorizonCode_Horizon_È(x, y, button);
    }
    
    @Override
    public void Ý() {
        this.HorizonCode_Horizon_È.Â();
        this.Â.ˆÏ­();
    }
}
