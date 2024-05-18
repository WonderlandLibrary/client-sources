package HORIZON-6-0-SKIDPROTECTION;

import org.lwjgl.input.Keyboard;
import java.awt.datatransfer.Clipboard;
import java.io.IOException;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.datatransfer.DataFlavor;
import java.awt.Toolkit;

public final class GuiFastLogin extends GuiScreen
{
    private AltLoginThread HorizonCode_Horizon_È;
    private GuiTextField Â;
    private String Ý;
    private String Ø­áŒŠá;
    private String Âµá€;
    private int Ó;
    private static final ResourceLocation_1975012498 à;
    
    static {
        à = new ResourceLocation_1975012498("textures/horizon/gui/bg.png");
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final GuiButton button) {
        Label_0142: {
            switch (button.£à) {
                case 2: {
                    final Toolkit toolkit = Toolkit.getDefaultToolkit();
                    final Clipboard clipboard = toolkit.getSystemClipboard();
                    String string = null;
                    try {
                        string = (String)clipboard.getData(DataFlavor.stringFlavor);
                    }
                    catch (UnsupportedFlavorException | IOException ex2) {
                        final Exception ex;
                        final Exception e = ex;
                        e.printStackTrace();
                    }
                    this.Â.HorizonCode_Horizon_È(string);
                    break;
                }
                case 1: {
                    GuiFastLogin.Ñ¢á.HorizonCode_Horizon_È(new GuiMainMenu());
                    Horizon.à¢.áˆºáˆºÈ.HorizonCode_Horizon_È(Horizon.à¢.áŒŠ + "/mainmenu/menuback.wav");
                    Horizon.à¢.áˆºáˆºÈ.HorizonCode_Horizon_È(-18.0f);
                    break;
                }
                case 3: {
                    break Label_0142;
                }
                case 0: {
                    Label_0243: {
                        break Label_0243;
                        try {
                            try {
                                if (!this.Âµá€.equals("") && this.Âµá€.contains(":")) {
                                    final String var12 = this.Âµá€.replaceAll(":", " ");
                                    final String[] var13 = var12.split(" ");
                                    this.Ø­áŒŠá = var13[1];
                                    this.Ý = var13[0];
                                    (this.HorizonCode_Horizon_È = new AltLoginThread(var13[0], var13[1])).start();
                                    break;
                                }
                                break;
                            }
                            catch (ReportedException ex3) {}
                        }
                        catch (NullPointerException ex4) {
                            break;
                        }
                        try {
                            try {
                                if (!this.Â.Ý().equals("") && this.Â.Ý().contains(":")) {
                                    final String var12 = this.Â.Ý().replaceAll(":", " ");
                                    final String[] var13 = var12.split(" ");
                                    this.Ø­áŒŠá = var13[1];
                                    this.Ý = var13[0];
                                    this.Âµá€ = this.Â.Ý();
                                    (this.HorizonCode_Horizon_È = new AltLoginThread(var13[0], var13[1])).start();
                                    break;
                                }
                                break;
                            }
                            catch (ReportedException ex5) {}
                        }
                        catch (NullPointerException ex6) {}
                    }
                    break;
                }
            }
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int x, final int y, final float z) {
        ++this.Ó;
        final ScaledResolution scaledRes = new ScaledResolution(GuiFastLogin.Ñ¢á, GuiFastLogin.Ñ¢á.Ó, GuiFastLogin.Ñ¢á.à);
        GuiFastLogin.Ñ¢á.¥à().HorizonCode_Horizon_È(GuiFastLogin.à);
        Gui_1808253012.HorizonCode_Horizon_È(0, 0, 0.0f, 0.0f, scaledRes.HorizonCode_Horizon_È(), scaledRes.Â(), scaledRes.HorizonCode_Horizon_È(), scaledRes.Â(), scaledRes.HorizonCode_Horizon_È(), scaledRes.Â());
        Gui_1808253012.HorizonCode_Horizon_È(0, 0, GuiFastLogin.Çªà¢, GuiFastLogin.Ê, 1325400064);
        this.HorizonCode_Horizon_È(GuiFastLogin.Ñ¢á.µà, "FastAlt Login", GuiFastLogin.Çªà¢ / 2 - 10, 20, -1);
        this.HorizonCode_Horizon_È(GuiFastLogin.Ñ¢á.µà, (this.HorizonCode_Horizon_È == null) ? "§7Waiting..." : this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(), GuiFastLogin.Çªà¢ / 2 - 9, 29, -1);
        this.Â.Ó(100);
        this.Â.HorizonCode_Horizon_È();
        if (this.Â.Ý().isEmpty()) {
            Gui_1808253012.Â(GuiFastLogin.Ñ¢á.µà, "                              EMail:Password", GuiFastLogin.Çªà¢ / 2 - 96, 66, -1);
        }
        super.HorizonCode_Horizon_È(x, y, z);
        if (!Horizon.à¢.Ï.HorizonCode_Horizon_È.isRunning() && Horizon.Âµà) {
            Horizon.à¢.Ï.HorizonCode_Horizon_È(Horizon.à¢.áŒŠ + "/mainmenu/menusong.wav");
            Horizon.à¢.Ï.HorizonCode_Horizon_È(-28.0f);
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È() {
        this.Ý = "null";
        this.Ø­áŒŠá = "null";
        final int var3 = GuiFastLogin.Ê / 4 + 24;
        this.ÇŽÉ.add(new GuiMenuButton(0, GuiFastLogin.Çªà¢ / 2 - 100, var3 + 72 + 12, "Login"));
        this.ÇŽÉ.add(new GuiMenuButton(3, GuiFastLogin.Çªà¢ / 2 - 100, var3 + 72 + 12 + 24, "Login with last Alt"));
        this.ÇŽÉ.add(new GuiMenuButton(1, GuiFastLogin.Çªà¢ / 2 - 100, var3 + 72 + 12 + 48, "Back"));
        this.ÇŽÉ.add(new GuiMenuButton(2, GuiFastLogin.Çªà¢ / 2 + 110, 66, 40, 15, "Paste"));
        this.Â = new GuiFlatField(var3, GuiFastLogin.Ñ¢á.µà, GuiFastLogin.Çªà¢ / 2 - 100, 60, 200, 20);
        Keyboard.enableRepeatEvents(true);
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final char character, final int key) {
        try {
            super.HorizonCode_Horizon_È(character, key);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        if (character == '\t' && !this.Â.ÂµÈ()) {
            this.Â.Â(true);
        }
        if (character == '\r') {
            this.HorizonCode_Horizon_È(this.ÇŽÉ.get(0));
        }
        this.Â.HorizonCode_Horizon_È(character, key);
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final int x, final int y, final int button) {
        try {
            super.HorizonCode_Horizon_È(x, y, button);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        this.Â.HorizonCode_Horizon_È(x, y, button);
    }
    
    @Override
    public void q_() {
        Keyboard.enableRepeatEvents(false);
    }
    
    @Override
    public void Ý() {
        this.Â.Â();
    }
}
