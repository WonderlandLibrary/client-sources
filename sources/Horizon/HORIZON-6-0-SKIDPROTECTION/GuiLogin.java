package HORIZON-6-0-SKIDPROTECTION;

import java.io.FileNotFoundException;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import org.lwjgl.opengl.Display;
import org.lwjgl.input.Keyboard;
import java.io.Writer;
import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.FileOutputStream;
import java.io.File;
import java.net.URISyntaxException;
import java.io.IOException;
import java.net.URI;
import java.awt.Desktop;

public final class GuiLogin extends GuiScreen
{
    private GuiLoginPasswordField Â;
    private GuiLoginFlatField Ý;
    private String Ø­áŒŠá;
    private int Âµá€;
    public static boolean HorizonCode_Horizon_È;
    private static final ResourceLocation_1975012498 Ó;
    
    static {
        Ó = new ResourceLocation_1975012498("textures/horizon/gui/bg.png");
    }
    
    public GuiLogin() {
        this.Ø­áŒŠá = "";
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final GuiButton guiButton) {
        if (guiButton.£à == 89) {
            Horizon.à¢.áˆºáˆºÈ.HorizonCode_Horizon_È(Horizon.à¢.áŒŠ + "/mainmenu/menuhit.wav");
            Horizon.à¢.áˆºáˆºÈ.HorizonCode_Horizon_È(-18.0f);
            if (Horizon.Âµà) {
                Horizon.Âµà = false;
                Horizon.à¢.Ï.HorizonCode_Horizon_È();
            }
            else {
                Horizon.Âµà = true;
            }
            GuiLogin.Ñ¢á.HorizonCode_Horizon_È(new GuiLogin());
        }
        switch (guiButton.£à) {
            case 98: {
                System.exit(0);
                break;
            }
            case 1888: {
                GuiLogin.HorizonCode_Horizon_È = !GuiLogin.HorizonCode_Horizon_È;
                break;
            }
            case 2: {
                try {
                    Desktop.getDesktop().browse(new URI("http://horizonco.de/horizon/beta/#register-slide"));
                }
                catch (IOException ex) {}
                catch (URISyntaxException ex2) {}
                break;
            }
            case 1: {
                GuiLogin.Ñ¢á.£á();
                break;
            }
            case 0: {
                try {
                    if (!GuiLogin.HorizonCode_Horizon_È) {
                        new File(Horizon.à¢.áŒŠ, "lastadata.ini").delete();
                    }
                    final String s = "http://horizonco.de/horizon/beta/client/login.php?username=";
                    final String s2 = "&password=";
                    if (Utils_1442407170.HorizonCode_Horizon_È(String.valueOf(s) + this.Ý.Ý() + s2 + this.Â.ÂµÈ()).equals("1")) {
                        if (Utils_1442407170.HorizonCode_Horizon_È(String.valueOf("http://horizonco.de/horizonclient/issuspended.php?user=") + this.Ý.Ý()).equals("YES")) {
                            this.Ø­áŒŠá = "§c" + "Your_Account_has_been_suspended_for_sharing!".replaceAll("_", " ");
                            return;
                        }
                        try {
                            Horizon.É = this.Ý.Ý();
                            if (Utils_1442407170.HorizonCode_Horizon_È(String.valueOf("http://horizonco.de/horizonclient/checkactivated.php?user=") + this.Ý.Ý()).equals("YES")) {
                                this.Ø­áŒŠá = "§a" + "Success";
                                CompressedStreamTools.HorizonCode_Horizon_È = true;
                                GuiLogin.Ñ¢á.HorizonCode_Horizon_È(new GuiMainMenu());
                                if (GuiLogin.HorizonCode_Horizon_È) {
                                    final BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(Horizon.à¢.áŒŠ, "lastadata.ini"))));
                                    bufferedWriter.write(String.valueOf(this.Ý.Ý()) + ":" + this.Â.ÂµÈ());
                                    bufferedWriter.close();
                                }
                                else {
                                    new File(Horizon.à¢.áŒŠ, "lastadata.ini").delete();
                                }
                            }
                            else {
                                this.Ø­áŒŠá = "§e" + "User_not_activated,_purchase_a_activation!".replaceAll("_", " ");
                            }
                        }
                        catch (Exception ex3) {
                            this.Ø­áŒŠá = "§c" + "No_Internet_connection_/_Server_down!".replaceAll("_", " ");
                            new File(Horizon.à¢.áŒŠ, "lastadata.ini").delete();
                        }
                    }
                    else if (Utils_1442407170.HorizonCode_Horizon_È(String.valueOf(s) + this.Ý.Ý() + s2 + this.Â.ÂµÈ()).equals("2")) {
                        this.Ø­áŒŠá = "§c" + "Nice_try_Faggot_;P".replaceAll("_", " ");
                        new File(Horizon.à¢.áŒŠ, "lastadata.ini").delete();
                    }
                    else {
                        this.Ø­áŒŠá = "§c" + "Wrong_Username_or_Password!".replaceAll("_", " ");
                        new File(Horizon.à¢.áŒŠ, "lastadata.ini").delete();
                    }
                }
                catch (Exception ex4) {
                    this.Ø­áŒŠá = "§c" + "No_Internet_connection_/_Server_down!".replaceAll("_", " ");
                    new File(Horizon.à¢.áŒŠ, "lastadata.ini").delete();
                }
                break;
            }
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int n, final int n2, final float n3) {
        ++this.Âµá€;
        final ScaledResolution scaledResolution = new ScaledResolution(GuiLogin.Ñ¢á, GuiLogin.Ñ¢á.Ó, GuiLogin.Ñ¢á.à);
        GuiLogin.Ñ¢á.¥à().HorizonCode_Horizon_È(GuiLogin.Ó);
        Gui_1808253012.HorizonCode_Horizon_È(0, 0, 0.0f, 0.0f, scaledResolution.HorizonCode_Horizon_È(), scaledResolution.Â(), scaledResolution.HorizonCode_Horizon_È(), scaledResolution.Â(), scaledResolution.HorizonCode_Horizon_È(), scaledResolution.Â());
        Gui_1808253012.HorizonCode_Horizon_È(0, 0, GuiLogin.Çªà¢, GuiLogin.Ê, 1342177280);
        this.HorizonCode_Horizon_È(UIFonts.áŒŠÆ, this.Ø­áŒŠá, GuiLogin.Çªà¢ / 2 + 5, 35, -1);
        Gui_1808253012.HorizonCode_Horizon_È(GuiLogin.Çªà¢ / 2 - 101, 58, GuiLogin.Çªà¢ / 2 + 79, 78, 1879048192);
        Gui_1808253012.HorizonCode_Horizon_È(GuiLogin.Çªà¢ / 2 + 80, 58, GuiLogin.Çªà¢ / 2 + 100, 78, 1879048192);
        UIFonts.Å.HorizonCode_Horizon_È("horizon", GuiScreen.Çªà¢ / 2 - (UIFonts.ÂµÈ.HorizonCode_Horizon_È("horizon") / 2 + 32), 61, -1);
        UIFonts.£á.HorizonCode_Horizon_È("Sign in", GuiScreen.Çªà¢ / 2 - (UIFonts.ÂµÈ.HorizonCode_Horizon_È("Sign in") / 2 - 95), 63, -1);
        this.Ý.Ó(16);
        if (this.Ý.Ý().isEmpty() && !this.Ý.ˆÏ­) {
            Gui_1808253012.Â(UIFonts.£á, "                              Username", GuiLogin.Çªà¢ / 2 - 190, 84, 1894576369);
        }
        else if (this.Ý.Ý().isEmpty() && this.Ý.ˆÏ­) {
            Gui_1808253012.Â(UIFonts.£á, "                              Username", GuiLogin.Çªà¢ / 2 - 190, 84, 1891484615);
        }
        if (this.Â.ÂµÈ().isEmpty() && !this.Â.á()) {
            Gui_1808253012.Â(UIFonts.£á, "                              Password", GuiLogin.Çªà¢ / 2 - 190, 106, 1894576369);
        }
        else if (this.Â.ÂµÈ().isEmpty() && this.Â.á()) {
            Gui_1808253012.Â(UIFonts.£á, "                              Password", GuiLogin.Çªà¢ / 2 - 190, 106, 1891484615);
        }
        this.Ý.HorizonCode_Horizon_È();
        this.Â.HorizonCode_Horizon_È();
        UIFonts.ˆÏ­.HorizonCode_Horizon_È("Remember me", GuiLogin.Çªà¢ / 2 - 23, 174, -1);
        super.HorizonCode_Horizon_È(n, n2, n3);
        if (!Horizon.à¢.Ï.HorizonCode_Horizon_È.isRunning() && Horizon.Âµà) {
            Horizon.à¢.Ï.HorizonCode_Horizon_È(Horizon.à¢.áŒŠ + "/mainmenu/menusong.wav");
            Horizon.à¢.Ï.HorizonCode_Horizon_È(-28.0f);
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È() {
        UIFonts.HorizonCode_Horizon_È();
        GuiLogin.HorizonCode_Horizon_È = false;
        final File file = new File(Horizon.à¢.áŒŠ, "lastadata.ini");
        if (file.exists()) {
            GuiLogin.HorizonCode_Horizon_È = true;
        }
        CompressedStreamTools.HorizonCode_Horizon_È = false;
        final int n = GuiLogin.Ê / 4 + 24;
        this.ÇŽÉ.add(new GuiMenuButton(0, GuiLogin.Çªà¢ / 2 - 101, 123, 201, 20, "Login"));
        this.ÇŽÉ.add(new GuiMenuButton(2, GuiLogin.Çªà¢ / 2 - 101, 145, 201, 15, "Dont have an Account? Register here!"));
        this.ÇŽÉ.add(new GuiInvisibleButton(98, GuiScreen.Çªà¢ / 2 + 80, 58, 20, 20, "X"));
        this.ÇŽÉ.add(new GuiCheckBoxButton(1888, GuiLogin.Çªà¢ / 2 - 50, 169, 18, 18, "×"));
        this.Ý = new GuiLoginFlatField(88, this.É, GuiLogin.Çªà¢ / 2 - 100, 79, 200, 20);
        this.Â = new GuiLoginPasswordField(this.É, GuiLogin.Çªà¢ / 2 - 100, 100, 200, 20);
        Keyboard.enableRepeatEvents(true);
        if (!file.exists()) {
            Display.setTitle("Horizon: Please Login!");
        }
        try {
            if (file.exists()) {
                final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    final String[] split = line.split(":");
                    final String p_146180_1_ = split[0];
                    final String p_146180_1_2 = split[1];
                    this.Ý.HorizonCode_Horizon_È(p_146180_1_);
                    this.Â.Â(p_146180_1_2);
                }
                bufferedReader.close();
            }
        }
        catch (FileNotFoundException ex) {}
        catch (IOException ex2) {}
        if (Horizon.à¢.Ø && file.exists()) {
            this.HorizonCode_Horizon_È(new GuiButton(0, -1, -1, ""));
        }
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final char c, final int n) {
        if (c == '\t') {
            if (!this.Ý.ÂµÈ()) {
                this.Ý.Â(true);
                this.Â.Âµá€(false);
            }
            else if (!this.Â.á()) {
                this.Ý.Â(false);
                this.Â.Âµá€(true);
            }
        }
        if (c == '\r') {
            this.HorizonCode_Horizon_È(this.ÇŽÉ.get(0));
        }
        this.Ý.HorizonCode_Horizon_È(c, n);
        this.Â.HorizonCode_Horizon_È(c, n);
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final int n, final int n2, final int n3) {
        try {
            super.HorizonCode_Horizon_È(n, n2, n3);
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
        this.Ý.HorizonCode_Horizon_È(n, n2, n3);
        this.Â.HorizonCode_Horizon_È(n, n2, n3);
    }
    
    @Override
    public void Ý() {
        this.Ý.Â();
        this.Â.ˆÏ­();
    }
}
