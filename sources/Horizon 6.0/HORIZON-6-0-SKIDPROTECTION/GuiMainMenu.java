package HORIZON-6-0-SKIDPROTECTION;

import java.util.Iterator;
import org.lwjgl.opengl.Display;
import java.net.URISyntaxException;
import java.net.URI;
import java.awt.Desktop;
import java.awt.TrayIcon;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Calendar;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class GuiMainMenu extends GuiScreen implements GuiYesNoCallback
{
    private static final AtomicInteger á;
    private static final Random ˆÏ­;
    private float £á;
    private String Å;
    float HorizonCode_Horizon_È;
    int Â;
    public static ParticleEngine Ý;
    private String £à;
    private GuiButton µà;
    private int ˆà;
    private DynamicTexture ¥Æ;
    private boolean Ø­à;
    private final Object µÕ;
    private String Æ;
    private String áƒ;
    private String á€;
    private static final ResourceLocation_1975012498 Õ;
    private static final ResourceLocation_1975012498 à¢;
    private static final ResourceLocation_1975012498 ŠÂµà;
    public static int Ø­áŒŠá;
    public static int Âµá€;
    private int ¥à;
    private int Âµà;
    private int Ç;
    private int È;
    private int áŠ;
    private int ˆáŠ;
    public int Ó;
    public int à;
    public GuiScreen Ø;
    public static boolean áŒŠÆ;
    public boolean áˆºÑ¢Õ;
    private int áŒŠ;
    private int £ÂµÄ;
    public int ÂµÈ;
    private ResourceLocation_1975012498 Ø­Âµ;
    private static final String Ä = "CL_00001154";
    private static boolean Ñ¢Â;
    private static TimeHelper Ï­à;
    
    static {
        á = new AtomicInteger(0);
        ˆÏ­ = new Random();
        Õ = new ResourceLocation_1975012498("texts/splashes.txt");
        à¢ = new ResourceLocation_1975012498("textures/gui/title/minecraft.png");
        ŠÂµà = new ResourceLocation_1975012498("textures/horizon/gui/bg.png");
        GuiMainMenu.Ø­áŒŠá = 0;
        GuiMainMenu.Âµá€ = 0;
        GuiMainMenu.áŒŠÆ = false;
        GuiMainMenu.Ñ¢Â = false;
        GuiMainMenu.Ï­à = new TimeHelper();
    }
    
    public GuiMainMenu() {
        this.Å = "Connecting to Database...";
        this.HorizonCode_Horizon_È = 0.0f;
        this.Â = 0;
        this.Ø­à = true;
        this.µÕ = new Object();
        this.Ó = 0;
        this.áˆºÑ¢Õ = false;
        this.áŒŠ = 0;
        this.£ÂµÄ = 1;
        this.ÂµÈ = 80;
    }
    
    @Override
    public void Ý() {
        ++this.ˆà;
        if (Horizon.É.isEmpty()) {
            System.exit(0);
        }
        final Calendar instance = Calendar.getInstance();
        instance.setTime(new Date());
        if ((instance.get(2) + 1 == 12 && instance.get(5) == 24) || instance.get(5) == 25 || instance.get(5) == 26) {
            GuiMainMenu.Ý.Ø­áŒŠá();
            if (GuiMainMenu.Ï­à.Â(250L)) {
                GuiMainMenu.Ý.Ý();
                GuiMainMenu.Ï­à.Ø­áŒŠá();
            }
        }
    }
    
    @Override
    public boolean Ø­áŒŠá() {
        return false;
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final char c, final int n) throws IOException {
    }
    
    @Override
    public void HorizonCode_Horizon_È() throws IllegalArgumentException {
        áˆºÑ¢Õ();
        if (!new File(Horizon.à¢.áŒŠ, "client.data").exists()) {
            GuiMainMenu.Ñ¢á.HorizonCode_Horizon_È(new GuiFirstStart());
            return;
        }
        this.Â = 0;
        this.Å = "Connecting to Database...";
        this.Ó = 0;
        GuiMainMenu.áŒŠÆ = false;
        this.à = 50;
        GuiMainMenu.Ñ¢Â = false;
        this.áŒŠ = 0;
        this.áˆºÑ¢Õ = true;
        final int n = GuiScreen.Ê / 4 + 48;
        this.ÇŽÉ.add(new GuiResourceButton(0, GuiMainMenu.Çªà¢ / 2 - 70, n + 72 + 30 - 75, 40, 40, "Options", new ResourceLocation_1975012498("textures/horizon/gui/icons/settings.jpg")));
        this.ÇŽÉ.add(new GuiResourceButton(1, GuiMainMenu.Çªà¢ / 2 - 70, n + 72 + 30 - 125, 40, 40, I18n.HorizonCode_Horizon_È("menu.singleplayer", new Object[0]), new ResourceLocation_1975012498("textures/horizon/gui/icons/singleplayer.jpg")));
        this.ÇŽÉ.add(new GuiResourceButton(2, GuiMainMenu.Çªà¢ / 2 - 17, n + 72 + 30 - 125, 40, 40, I18n.HorizonCode_Horizon_È("menu.multiplayer", new Object[0]), new ResourceLocation_1975012498("textures/horizon/gui/icons/multiplayer.jpg")));
        this.ÇŽÉ.add(new GuiResourceButton(4, GuiMainMenu.Çªà¢ / 2 + 35, n + 72 + 30 - 75, 40, 40, I18n.HorizonCode_Horizon_È("menu.quit", new Object[0]), new ResourceLocation_1975012498("textures/horizon/gui/icons/exit.jpg")));
        this.ÇŽÉ.add(new GuiResourceButton(5, GuiMainMenu.Çªà¢ / 2 + 35, n + 72 + 30 - 125, 40, 40, "Alt Login", new ResourceLocation_1975012498("textures/horizon/gui/icons/altmanager.jpg")));
        this.ÇŽÉ.add(new GuiResourceButton(7, GuiMainMenu.Çªà¢ / 2 - 17, n + 72 + 30 - 75, 40, 40, "Server Status", new ResourceLocation_1975012498("textures/horizon/gui/icons/serverstatus.jpg")));
        this.ÇŽÉ.add(new GuiRoundMetroButton(88, GuiMainMenu.Çªà¢ - 44, GuiMainMenu.Ê - 100, 20, 20, ""));
        this.ÇŽÉ.add(new GuiRoundMetroButton(89, GuiMainMenu.Çªà¢ - 44, GuiMainMenu.Ê - 40, 20, 20, ""));
        try {
            try {
                GhostTray.HorizonCode_Horizon_È();
            }
            catch (Exception ex) {}
        }
        catch (IllegalArgumentException ex2) {}
        if (Horizon.à¢.Ø) {
            GhostTray.HorizonCode_Horizon_È.displayMessage("Horizon Client", "Im the GhostMode Notificator", TrayIcon.MessageType.INFO);
            Horizon.à¢.Ø = false;
        }
        try {
            new Thread("Versions Checking") {
                @Override
                public void run() {
                    try {
                        GuiMainMenu.HorizonCode_Horizon_È(GuiMainMenu.this, Utils.HorizonCode_Horizon_È("http://horizonco.de/horizonclient/version.php"));
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        }
        catch (Exception ex3) {}
        if (!this.Å.equalsIgnoreCase("Connecting to Database...")) {
            this.HorizonCode_Horizon_È = Float.parseFloat(this.Å);
        }
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final GuiButton guiButton) throws IOException {
        áˆºÑ¢Õ();
        if (guiButton.£à == 0) {
            Horizon.à¢.áˆºáˆºÈ.HorizonCode_Horizon_È(Horizon.à¢.áŒŠ + "/mainmenu/menuhit.wav");
            Horizon.à¢.áˆºáˆºÈ.HorizonCode_Horizon_È(-18.0f);
            if (!Horizon.ŒÏ) {
                GuiMainMenu.Ñ¢á.HorizonCode_Horizon_È(new GuiOptions(this, GuiMainMenu.Ñ¢á.ŠÄ));
                return;
            }
            GuiMainMenu.áŒŠÆ = true;
            this.Ø = new GuiOptions(this, GuiMainMenu.Ñ¢á.ŠÄ);
        }
        if (guiButton.£à == 8) {
            Horizon.à¢.áˆºáˆºÈ.HorizonCode_Horizon_È(Horizon.à¢.áŒŠ + "/mainmenu/menuhit.wav");
            Horizon.à¢.áˆºáˆºÈ.HorizonCode_Horizon_È(-18.0f);
            final File file = new File(Horizon.à¢.áŒŠ, "lastadata.ini");
            if (file.exists()) {
                file.delete();
            }
            if (!Horizon.ŒÏ) {
                GuiMainMenu.Ñ¢á.HorizonCode_Horizon_È(new GuiLogin());
                return;
            }
            GuiMainMenu.áŒŠÆ = true;
            this.Ø = new GuiLogin();
        }
        if (guiButton.£à == 5) {
            Horizon.à¢.áˆºáˆºÈ.HorizonCode_Horizon_È(Horizon.à¢.áŒŠ + "/mainmenu/menuhit.wav");
            Horizon.à¢.áˆºáˆºÈ.HorizonCode_Horizon_È(-18.0f);
            if (!Horizon.ŒÏ) {
                GuiMainMenu.Ñ¢á.HorizonCode_Horizon_È(new GuiAltManager(this));
                return;
            }
            GuiMainMenu.áŒŠÆ = true;
            this.Ø = new GuiAltManager(this);
        }
        if (guiButton.£à == 10) {
            try {
                Desktop.getDesktop().browse(new URI("http://horizonco.de/horizon"));
            }
            catch (URISyntaxException ex) {}
        }
        if (guiButton.£à == 1) {
            Horizon.à¢.áˆºáˆºÈ.HorizonCode_Horizon_È(Horizon.à¢.áŒŠ + "/mainmenu/menuhit.wav");
            Horizon.à¢.áˆºáˆºÈ.HorizonCode_Horizon_È(-18.0f);
            if (!Horizon.ŒÏ) {
                GuiMainMenu.Ñ¢á.HorizonCode_Horizon_È(new GuiSelectWorld(this));
                return;
            }
            GuiMainMenu.áŒŠÆ = true;
            this.Ø = new GuiSelectWorld(this);
        }
        if (guiButton.£à == 2) {
            Horizon.à¢.áˆºáˆºÈ.HorizonCode_Horizon_È(Horizon.à¢.áŒŠ + "/mainmenu/menuhit.wav");
            Horizon.à¢.áˆºáˆºÈ.HorizonCode_Horizon_È(-18.0f);
            if (!Horizon.ŒÏ) {
                GuiMainMenu.Ñ¢á.HorizonCode_Horizon_È(new GuiMultiplayer(this));
                return;
            }
            GuiMainMenu.áŒŠÆ = true;
            this.Ø = new GuiMultiplayer(this);
        }
        if (guiButton.£à == 7) {
            Horizon.à¢.áˆºáˆºÈ.HorizonCode_Horizon_È(Horizon.à¢.áŒŠ + "/mainmenu/menuhit.wav");
            Horizon.à¢.áˆºáˆºÈ.HorizonCode_Horizon_È(-18.0f);
            if (!Horizon.ŒÏ) {
                GuiMainMenu.Ñ¢á.HorizonCode_Horizon_È(new GuiMinecraftStatus());
                return;
            }
            GuiMainMenu.áŒŠÆ = true;
            this.Ø = new GuiMinecraftStatus();
        }
        if (guiButton.£à == 4) {
            Horizon.à¢.áˆºáˆºÈ.HorizonCode_Horizon_È(Horizon.à¢.áŒŠ + "/mainmenu/menuhit.wav");
            Horizon.à¢.áˆºáˆºÈ.HorizonCode_Horizon_È(-18.0f);
            if (!Horizon.ŒÏ) {
                GuiMainMenu.Ñ¢á.£á();
                return;
            }
            GuiMainMenu.áŒŠÆ = true;
            this.Ø = null;
        }
        if (guiButton.£à == 88) {
            Horizon.à¢.áˆºáˆºÈ.HorizonCode_Horizon_È(Horizon.à¢.áŒŠ + "/mainmenu/menuhit.wav");
            Horizon.à¢.áˆºáˆºÈ.HorizonCode_Horizon_È(-18.0f);
            if (Horizon.ŒÏ) {
                Horizon.ŒÏ = false;
            }
            else {
                Horizon.ŒÏ = true;
            }
        }
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
        }
        if (guiButton.£à == 6) {
            if (GuiMultiplayer.Â == null) {
                return;
            }
            GuiMultiplayer.HorizonCode_Horizon_È(GuiMultiplayer.Â);
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final boolean b, final int n) {
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int n, final int n2, final float n3) {
        if (Horizon.É.isEmpty()) {
            System.exit(0);
        }
        final int n4 = GuiScreen.Ê / 4 + 48;
        if (Horizon.à¢.Ï.HorizonCode_Horizon_È.isRunning() && !Horizon.Âµà) {
            Horizon.à¢.Ï.HorizonCode_Horizon_È();
            if (Horizon.à¢.Ï.HorizonCode_Horizon_È.isRunning() && !Horizon.Âµà) {
                Horizon.à¢.Ï.HorizonCode_Horizon_È();
                if (Horizon.à¢.Ï.HorizonCode_Horizon_È.isRunning() && !Horizon.Âµà) {
                    Horizon.à¢.Ï.HorizonCode_Horizon_È();
                }
            }
        }
        ++GuiMainMenu.Ø­áŒŠá;
        if (this.ÂµÈ > 10 && GuiMainMenu.áŒŠÆ) {
            --this.ÂµÈ;
        }
        final ScaledResolution scaledResolution = new ScaledResolution(GuiMainMenu.Ñ¢á, GuiMainMenu.Ñ¢á.Ó, GuiMainMenu.Ñ¢á.à);
        GuiMainMenu.Ñ¢á.¥à().HorizonCode_Horizon_È(GuiMainMenu.ŠÂµà);
        Gui_1808253012.HorizonCode_Horizon_È(0, 0, 0.0f, 0.0f, scaledResolution.HorizonCode_Horizon_È(), scaledResolution.Â(), scaledResolution.HorizonCode_Horizon_È(), scaledResolution.Â(), scaledResolution.HorizonCode_Horizon_È(), scaledResolution.Â());
        Gui_1808253012.HorizonCode_Horizon_È(0, 0, GuiMainMenu.Çªà¢, GuiMainMenu.Ê, 1325400064);
        Gui_1808253012.HorizonCode_Horizon_È(GuiMainMenu.Çªà¢ / 2 + 120 - this.Ó * 15, n4 + 72 + 30 - 180, GuiMainMenu.Çªà¢ / 2 - 110 - this.Ó * 15, n4 + 72 + 30 - 20, 805306368);
        if (GuiMainMenu.áŒŠÆ) {
            ++this.Ó;
            if (this.Ó >= 50) {
                if (this.Ø == null) {
                    GuiMainMenu.Ñ¢á.£á();
                }
                else {
                    GuiMainMenu.Ñ¢á.HorizonCode_Horizon_È(this.Ø);
                }
                GuiMainMenu.Ñ¢Â = false;
            }
            for (final GuiButton guiButton : this.ÇŽÉ) {
                guiButton.£á += this.Ó;
            }
        }
        final Calendar instance = Calendar.getInstance();
        instance.setTime(new Date());
        if ((instance.get(2) + 1 == 12 && instance.get(5) == 24) || instance.get(5) == 25 || instance.get(5) == 26) {
            GuiMainMenu.Ý.Â();
        }
        final int n5 = GuiMainMenu.Çªà¢ / 2 - 274 / 2;
        if (Horizon.ÂµÈ) {
            Display.setTitle("Minecraft 1.8");
        }
        else {
            Display.setTitle(String.valueOf("Horizon") + " " + Horizon.Ý);
        }
        final int rgb = ColorUtil.HorizonCode_Horizon_È(200000000L, 1.0f).getRGB();
        UIFonts.áˆºÑ¢Õ.HorizonCode_Horizon_È(String.valueOf("Horizon Client") + " " + "v" + Horizon.Ý + " " + "by HorizonCode", 3, 1 - this.Ó, rgb);
        try {
            if (this.HorizonCode_Horizon_È == Horizon.Ý) {
                UIFonts.áˆºÑ¢Õ.HorizonCode_Horizon_È("Your Version is UpToDate", 3, GuiMainMenu.Çªà¢ / 2 + 13 + this.Ó, -1875981199);
            }
            else if (this.HorizonCode_Horizon_È > Horizon.Ý) {
                UIFonts.áˆºÑ¢Õ.HorizonCode_Horizon_È("Your Version is out of Date", 3, GuiMainMenu.Çªà¢ / 2 + 13 + this.Ó, -1863889860);
            }
            else if (this.HorizonCode_Horizon_È < Horizon.Ý) {
                UIFonts.áˆºÑ¢Õ.HorizonCode_Horizon_È("Your Version is OVER 9000!!!!", 3, GuiMainMenu.Çªà¢ / 2 + 13 + this.Ó, rgb);
            }
        }
        catch (Exception ex) {}
        --this.à;
        if (this.à < 0) {
            this.à = new Random().nextInt("Horizon".length() * 30);
            this.áŒŠ = 0;
        }
        UIFonts.ÂµÈ.HorizonCode_Horizon_È("Horizon", GuiScreen.Çªà¢ / 2 - (UIFonts.ÂµÈ.HorizonCode_Horizon_È("Horizon") / 2 - 2), n4 - 70 - this.Ó * 3, -1185802);
        if (Horizon.ŒÏ) {
            UIFonts.áˆºÑ¢Õ.HorizonCode_Horizon_È("Animations: ON", GuiScreen.Çªà¢ - 89 + this.Ó * 3, GuiMainMenu.Ê - 75, -1);
        }
        else {
            UIFonts.áˆºÑ¢Õ.HorizonCode_Horizon_È("Animations: OFF", GuiScreen.Çªà¢ - 94 + this.Ó * 3, GuiMainMenu.Ê - 75, -1);
        }
        if (Horizon.Âµà) {
            UIFonts.áˆºÑ¢Õ.HorizonCode_Horizon_È("Music: ON", GuiScreen.Çªà¢ - 61 + this.Ó * 3, GuiMainMenu.Ê - 15, -1);
        }
        else {
            UIFonts.áˆºÑ¢Õ.HorizonCode_Horizon_È("Music: OFF", GuiScreen.Çªà¢ - 63 + this.Ó * 3, GuiMainMenu.Ê - 15, -1);
        }
        Horizon.ÂµÈ = false;
        super.HorizonCode_Horizon_È(n, n2, n3);
        if (!Horizon.à¢.Ï.HorizonCode_Horizon_È.isRunning() && Horizon.Âµà) {
            Horizon.à¢.Ï.HorizonCode_Horizon_È();
            Horizon.à¢.Ï.HorizonCode_Horizon_È(Horizon.à¢.áŒŠ + "/mainmenu/menusong.wav");
            Horizon.à¢.Ï.HorizonCode_Horizon_È(-28.0f);
        }
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final int n, final int n2, final int n3) throws IOException {
        áˆºÑ¢Õ();
        if (n >= 0 && n <= 10 && n2 >= 0 && n2 <= 10) {
            GuiMainMenu.Ñ¢á.HorizonCode_Horizon_È(new UI_CookieClicker());
        }
        if (Horizon.É.isEmpty()) {
            System.exit(0);
        }
        super.HorizonCode_Horizon_È(n, n2, n3);
    }
    
    @Override
    public void q_() {
        áˆºÑ¢Õ();
        if (Horizon.É.isEmpty()) {
            System.exit(0);
        }
        GuiMainMenu.áŒŠÆ = false;
        super.q_();
    }
    
    static /* synthetic */ void HorizonCode_Horizon_È(final GuiMainMenu guiMainMenu, final String å) {
        guiMainMenu.Å = å;
    }
}
