package HORIZON-6-0-SKIDPROTECTION;

import org.lwjgl.input.Keyboard;
import java.awt.datatransfer.Clipboard;
import java.io.Writer;
import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.io.File;
import java.io.IOException;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.datatransfer.DataFlavor;
import java.awt.Toolkit;

public final class GuiAltManager extends GuiScreen
{
    private GuiPasswordField HorizonCode_Horizon_È;
    private final GuiScreen Â;
    private AltLoginThread Ý;
    private GuiTextField Ø­áŒŠá;
    private GuiTextField Âµá€;
    private int Ó;
    private static final ResourceLocation_1975012498 à;
    
    static {
        à = new ResourceLocation_1975012498("textures/horizon/gui/bg.png");
    }
    
    public GuiAltManager(final GuiScreen previousScreen) {
        this.Â = previousScreen;
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final GuiButton button) {
        switch (button.£à) {
            case 6: {
                final Toolkit toolkit2 = Toolkit.getDefaultToolkit();
                final Clipboard clipboard2 = toolkit2.getSystemClipboard();
                String string2 = null;
                try {
                    string2 = (String)clipboard2.getData(DataFlavor.stringFlavor);
                }
                catch (UnsupportedFlavorException | IOException ex3) {
                    final Exception ex;
                    final Exception e = ex;
                    e.printStackTrace();
                }
                this.HorizonCode_Horizon_È.Â(string2);
                break;
            }
            case 5: {
                final Toolkit toolkit3 = Toolkit.getDefaultToolkit();
                final Clipboard clipboard3 = toolkit3.getSystemClipboard();
                String string3 = null;
                try {
                    string3 = (String)clipboard3.getData(DataFlavor.stringFlavor);
                }
                catch (UnsupportedFlavorException | IOException ex4) {
                    final Exception ex2;
                    final Exception e2 = ex2;
                    e2.printStackTrace();
                }
                this.Ø­áŒŠá.HorizonCode_Horizon_È(string3);
                break;
            }
            case 4: {
                Horizon.à¢.áˆºáˆºÈ.HorizonCode_Horizon_È(Horizon.à¢.áŒŠ + "/mainmenu/menuhit.wav");
                Horizon.à¢.áˆºáˆºÈ.HorizonCode_Horizon_È(-18.0f);
                GuiAltManager.Ñ¢á.HorizonCode_Horizon_È(new GuiSession(this));
                break;
            }
            case 3: {
                Horizon.à¢.áˆºáˆºÈ.HorizonCode_Horizon_È(Horizon.à¢.áŒŠ + "/mainmenu/menuhit.wav");
                Horizon.à¢.áˆºáˆºÈ.HorizonCode_Horizon_È(-18.0f);
                GuiAltManager.Ñ¢á.HorizonCode_Horizon_È(new GuiFastLogin());
                break;
            }
            case 2: {
                final File fl = new File(Horizon.à¢.áŒŠ, "lastaltadata.ini");
                try {
                    if (fl.exists()) {
                        final FileInputStream fstream = new FileInputStream(fl);
                        final BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
                        String strLine;
                        while ((strLine = br.readLine()) != null) {
                            final String[] split = strLine.split(":");
                            final String uname = split[0];
                            final String passwd = split[1];
                            this.Ø­áŒŠá.HorizonCode_Horizon_È(uname);
                            this.HorizonCode_Horizon_È.Â(passwd);
                        }
                        br.close();
                        this.HorizonCode_Horizon_È(new GuiButton(0, -1, -1, ""));
                    }
                }
                catch (FileNotFoundException ex5) {}
                catch (IOException ex6) {}
                break;
            }
            case 1: {
                GuiAltManager.Ñ¢á.HorizonCode_Horizon_È(this.Â);
                Horizon.à¢.áˆºáˆºÈ.HorizonCode_Horizon_È(Horizon.à¢.áŒŠ + "/mainmenu/menuback.wav");
                Horizon.à¢.áˆºáˆºÈ.HorizonCode_Horizon_È(-18.0f);
                break;
            }
            case 0: {
                final File f = new File(Horizon.à¢.áŒŠ, "lastaltadata.ini");
                try {
                    final FileOutputStream fos = new FileOutputStream(f);
                    final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
                    bw.write(String.valueOf(this.Ø­áŒŠá.Ý()) + ":" + this.HorizonCode_Horizon_È.ÂµÈ());
                    bw.close();
                }
                catch (Exception e3) {
                    f.delete();
                }
                (this.Ý = new AltLoginThread(this.Ø­áŒŠá.Ý(), this.HorizonCode_Horizon_È.ÂµÈ())).start();
                break;
            }
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int x, final int y, final float z) {
        ++this.Ó;
        final ScaledResolution scaledRes = new ScaledResolution(GuiAltManager.Ñ¢á, GuiAltManager.Ñ¢á.Ó, GuiAltManager.Ñ¢á.à);
        GuiAltManager.Ñ¢á.¥à().HorizonCode_Horizon_È(GuiAltManager.à);
        Gui_1808253012.HorizonCode_Horizon_È(0, 0, 0.0f, 0.0f, scaledRes.HorizonCode_Horizon_È(), scaledRes.Â(), scaledRes.HorizonCode_Horizon_È(), scaledRes.Â(), scaledRes.HorizonCode_Horizon_È(), scaledRes.Â());
        Gui_1808253012.HorizonCode_Horizon_È(0, 0, GuiAltManager.Çªà¢, GuiAltManager.Ê, 1325400064);
        this.HorizonCode_Horizon_È(GuiAltManager.Ñ¢á.µà, "Alt Login", GuiAltManager.Çªà¢ / 2 - 10, 20, -1);
        this.HorizonCode_Horizon_È(GuiAltManager.Ñ¢á.µà, (this.Ý == null) ? "§7Waiting..." : this.Ý.HorizonCode_Horizon_È(), GuiAltManager.Çªà¢ / 2 - 9, 29, -1);
        this.Ø­áŒŠá.HorizonCode_Horizon_È();
        this.HorizonCode_Horizon_È.HorizonCode_Horizon_È();
        if (this.Ø­áŒŠá.Ý().equals("")) {
            Gui_1808253012.Â(GuiAltManager.Ñ¢á.µà, "                   Username / E-Mail", GuiAltManager.Çªà¢ / 2 - 67, 66, -1);
        }
        if (this.HorizonCode_Horizon_È.ÂµÈ().equals("")) {
            Gui_1808253012.Â(GuiAltManager.Ñ¢á.µà, "                            Password", GuiAltManager.Çªà¢ / 2 - 60, 106, -1);
        }
        super.HorizonCode_Horizon_È(x, y, z);
        if (!Horizon.à¢.Ï.HorizonCode_Horizon_È.isRunning() && Horizon.Âµà) {
            Horizon.à¢.Ï.HorizonCode_Horizon_È(Horizon.à¢.áŒŠ + "/mainmenu/menusong.wav");
            Horizon.à¢.Ï.HorizonCode_Horizon_È(-28.0f);
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È() {
        final int var3 = GuiAltManager.Ê / 4 + 24;
        this.ÇŽÉ.add(new GuiMenuButton(0, GuiAltManager.Çªà¢ / 2 - 100, var3 + 72 + 12 - 24, "Login"));
        this.ÇŽÉ.add(new GuiMenuButton(1, GuiAltManager.Çªà¢ / 2 - 100, var3 + 72 + 12 + 48, "Back"));
        this.ÇŽÉ.add(new GuiMenuButton(2, GuiAltManager.Çªà¢ / 2 - 100, var3 + 72 + 12, "Login with Last Alt"));
        this.ÇŽÉ.add(new GuiMenuButton(3, GuiAltManager.Çªà¢ / 2 - 100, var3 + 72 + 12 + 24, 100, 20, "FastLogin"));
        this.ÇŽÉ.add(new GuiMenuButton(4, GuiAltManager.Çªà¢ / 2, var3 + 72 + 12 + 24, 100, 20, "SessionLogin"));
        this.ÇŽÉ.add(new GuiMenuButton(5, GuiAltManager.Çªà¢ / 2 + 110, 66, 40, 15, "Paste"));
        this.ÇŽÉ.add(new GuiMenuButton(6, GuiAltManager.Çªà¢ / 2 + 110, 106, 40, 15, "Paste"));
        this.Ø­áŒŠá = new GuiFlatField(var3, GuiAltManager.Ñ¢á.µà, GuiAltManager.Çªà¢ / 2 - 100, 60, 200, 20);
        this.HorizonCode_Horizon_È = new GuiPasswordField(GuiAltManager.Ñ¢á.µà, GuiAltManager.Çªà¢ / 2 - 100, 100, 200, 20);
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
        if (character == '\t') {
            if (!this.Ø­áŒŠá.ÂµÈ() && !this.HorizonCode_Horizon_È.á()) {
                this.Ø­áŒŠá.Â(true);
            }
            else {
                this.Ø­áŒŠá.Â(this.HorizonCode_Horizon_È.á());
                this.HorizonCode_Horizon_È.Âµá€(!this.Ø­áŒŠá.ÂµÈ());
            }
        }
        if (character == '\r') {
            this.HorizonCode_Horizon_È(this.ÇŽÉ.get(0));
        }
        this.Ø­áŒŠá.HorizonCode_Horizon_È(character, key);
        this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(character, key);
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final int x, final int y, final int button) {
        try {
            super.HorizonCode_Horizon_È(x, y, button);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        this.Ø­áŒŠá.HorizonCode_Horizon_È(x, y, button);
        this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(x, y, button);
    }
    
    @Override
    public void q_() {
        Keyboard.enableRepeatEvents(false);
    }
    
    @Override
    public void Ý() {
        this.Ø­áŒŠá.Â();
        this.HorizonCode_Horizon_È.ˆÏ­();
    }
}
