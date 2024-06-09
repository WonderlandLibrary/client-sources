package HORIZON-6-0-SKIDPROTECTION;

import java.io.IOException;
import org.lwjgl.input.Keyboard;

public class GuiSession extends GuiScreen
{
    private GuiScreen Â;
    private GuiFlatField Ý;
    private GuiFlatField Ø­áŒŠá;
    private String Âµá€;
    public Minecraft HorizonCode_Horizon_È;
    private int Ó;
    private static final ResourceLocation_1975012498 à;
    
    static {
        à = new ResourceLocation_1975012498("textures/horizon/gui/bg.png");
    }
    
    public GuiSession(final GuiScreen guiscreen) {
        this.HorizonCode_Horizon_È = Minecraft.áŒŠà();
        this.Â = guiscreen;
    }
    
    @Override
    public void Ý() {
        this.Ý.Â();
        this.Ø­áŒŠá.Â();
    }
    
    @Override
    public void q_() {
        Keyboard.enableRepeatEvents(false);
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final GuiButton guibutton) {
        if (!guibutton.µà) {
            return;
        }
        if (guibutton.£à == 1) {
            this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(this.Â);
            Horizon.à¢.áˆºáˆºÈ.HorizonCode_Horizon_È(Horizon.à¢.áŒŠ + "/mainmenu/menuback.wav");
            Horizon.à¢.áˆºáˆºÈ.HorizonCode_Horizon_È(-18.0f);
        }
        else if (guibutton.£à == 0) {
            final String[] split = this.Ø­áŒŠá.Ý().trim().split(":");
            HorizonCode_Horizon_È(this.Ý.Ý().trim(), split[0], split[1]);
            this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(new GuiMainMenu());
            Horizon.à¢.áˆºáˆºÈ.HorizonCode_Horizon_È(Horizon.à¢.áŒŠ + "/mainmenu/menuback.wav");
            Horizon.à¢.áˆºáˆºÈ.HorizonCode_Horizon_È(-18.0f);
        }
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final char c, final int i) {
        this.Ý.HorizonCode_Horizon_È(c, i);
        this.Ø­áŒŠá.HorizonCode_Horizon_È(c, i);
        if (c == '\t') {
            if (this.Ý.ÂµÈ()) {
                this.Ý.Â(false);
                this.Ø­áŒŠá.Â(true);
            }
            else if (this.Ø­áŒŠá.ÂµÈ()) {
                this.Ý.Â(true);
                this.Ø­áŒŠá.Â(false);
            }
        }
        if (c == '\r') {
            this.HorizonCode_Horizon_È(this.ÇŽÉ.get(0));
        }
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final int i, final int j, final int k) throws IOException {
        super.HorizonCode_Horizon_È(i, j, k);
        this.Ý.HorizonCode_Horizon_È(i, j, k);
        this.Ø­áŒŠá.HorizonCode_Horizon_È(i, j, k);
    }
    
    @Override
    public void HorizonCode_Horizon_È() {
        Keyboard.enableRepeatEvents(true);
        this.ÇŽÉ.clear();
        this.ÇŽÉ.add(new GuiMenuButton(0, GuiSession.Çªà¢ / 2 - 100, GuiSession.Ê / 4 + 96 + 24, "Login"));
        this.ÇŽÉ.add(new GuiMenuButton(1, GuiSession.Çªà¢ / 2 - 100, GuiSession.Ê / 4 + 120 + 24, "Back"));
        this.Ý = new GuiFlatField(88, this.É, GuiSession.Çªà¢ / 2 - 100, 96, 200, 20);
        this.Ø­áŒŠá = new GuiFlatField(88, this.É, GuiSession.Çªà¢ / 2 - 100, 136, 200, 20);
        this.Ý.Ó(512);
        this.Ø­áŒŠá.Ó(1024);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int i, final int j, final float f) {
        ++this.Ó;
        final ScaledResolution scaledRes = new ScaledResolution(this.HorizonCode_Horizon_È, this.HorizonCode_Horizon_È.Ó, this.HorizonCode_Horizon_È.à);
        this.HorizonCode_Horizon_È.¥à().HorizonCode_Horizon_È(GuiSession.à);
        Gui_1808253012.HorizonCode_Horizon_È(0, 0, 0.0f, 0.0f, scaledRes.HorizonCode_Horizon_È(), scaledRes.Â(), scaledRes.HorizonCode_Horizon_È(), scaledRes.Â(), scaledRes.HorizonCode_Horizon_È(), scaledRes.Â());
        Gui_1808253012.HorizonCode_Horizon_È(0, 0, GuiSession.Çªà¢, GuiSession.Ê, 1325400064);
        this.HorizonCode_Horizon_È(this.É, "Session Login", GuiSession.Çªà¢ / 2, GuiSession.Ê / 4 - 60 + 20, 16777215);
        if (!this.Ý.ˆÏ­ || this.Ý.Ý().isEmpty()) {
            Gui_1808253012.Â(this.É, "Username", GuiSession.Çªà¢ / 2 + 50, 103, -1);
        }
        if (!this.Ø­áŒŠá.ˆÏ­ || this.Ø­áŒŠá.Ý().isEmpty()) {
            Gui_1808253012.Â(this.É, "SessionID", GuiSession.Çªà¢ / 2 + 50, 143, -1);
        }
        this.Ý.HorizonCode_Horizon_È();
        this.Ø­áŒŠá.HorizonCode_Horizon_È();
        if (this.Âµá€ != null) {
            this.HorizonCode_Horizon_È(this.É, "§cError: " + this.Âµá€, GuiSession.Çªà¢ / 2, GuiSession.Ê / 4 + 72 + 12, 16777215);
        }
        super.HorizonCode_Horizon_È(i, j, f);
        if (!Horizon.à¢.Ï.HorizonCode_Horizon_È.isRunning() && Horizon.Âµà) {
            Horizon.à¢.Ï.HorizonCode_Horizon_È(Horizon.à¢.áŒŠ + "/mainmenu/menusong.wav");
            Horizon.à¢.Ï.HorizonCode_Horizon_È(-28.0f);
        }
    }
    
    public static boolean HorizonCode_Horizon_È(final String username, final String token, final String playerid) {
        final Minecraft mc = Minecraft.áŒŠà();
        mc.£à.HorizonCode_Horizon_È = username;
        mc.£à.Ý = token;
        mc.£à.Â = playerid;
        return true;
    }
}
