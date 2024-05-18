package HORIZON-6-0-SKIDPROTECTION;

import java.io.File;

public class Horizon
{
    public static Horizon HorizonCode_Horizon_È;
    public static HWID Â;
    public static float Ý;
    public static String Ø­áŒŠá;
    public static String Âµá€;
    public static String Ó;
    public static String à;
    public boolean Ø;
    public static int áŒŠÆ;
    public float áˆºÑ¢Õ;
    public static boolean ÂµÈ;
    public static boolean á;
    public static boolean ˆÏ­;
    public static boolean £á;
    public static boolean Å;
    public static boolean £à;
    public static boolean µà;
    public static boolean ˆà;
    public static boolean ¥Æ;
    public static boolean Ø­à;
    public static double µÕ;
    public static float Æ;
    public static int Šáƒ;
    public static float Ï­Ðƒà;
    public static boolean áŒŠà;
    public static int ŠÄ;
    public static boolean Ñ¢á;
    public static boolean ŒÏ;
    public static boolean Çªà¢;
    public static boolean Ê;
    public String ÇŽÉ;
    public String ˆá;
    public static boolean ÇŽÕ;
    public static String É;
    public static boolean áƒ;
    public static long á€;
    public static double Õ;
    public static Horizon à¢;
    public static NameTags ŠÂµà;
    public static boolean ¥à;
    public static boolean Âµà;
    public int Ç;
    public int È;
    public int áŠ;
    public String ˆáŠ;
    public File áŒŠ;
    public IRCClient £ÂµÄ;
    public BindManager Ø­Âµ;
    public GuiDragUI Ä;
    public PanelManager Ñ¢Â;
    public MsgNotifier Ï­à;
    public SoundPlayer áˆºáˆºÈ;
    public VoicePlayer ÇŽá€;
    public MusicPlayer Ï;
    public Notification Ô;
    public MessageNotifier ÇªÓ;
    public ModuleManager áˆºÏ;
    private static final EventManager ˆáƒ;
    
    static {
        Horizon.É = "H&E";
        Horizon.Ý = 6.0f;
        Horizon.Ø­áŒŠá = "§8[§c" + "Horizon" + "§8] §7";
        Horizon.Âµá€ = "red";
        Horizon.Ó = "metro";
        Horizon.à = "Arial";
        Horizon.áŒŠÆ = 0;
        Horizon.ÂµÈ = false;
        Horizon.á = true;
        Horizon.ˆÏ­ = false;
        Horizon.£á = false;
        Horizon.Å = true;
        Horizon.£à = false;
        Horizon.µà = false;
        Horizon.ˆà = false;
        Horizon.¥Æ = false;
        Horizon.Ø­à = false;
        Horizon.µÕ = 4.0;
        Horizon.Æ = 100.0f;
        Horizon.Šáƒ = 5;
        Horizon.Ï­Ðƒà = 1.5f;
        Horizon.áŒŠà = false;
        Horizon.ŠÄ = 10;
        Horizon.Ñ¢á = false;
        Horizon.ŒÏ = true;
        Horizon.Çªà¢ = false;
        Horizon.Ê = false;
        Horizon.áƒ = true;
        Horizon.Õ = 2.45;
        Horizon.¥à = false;
        Horizon.Âµà = true;
        ˆáƒ = new EventManager();
    }
    
    public HWID HorizonCode_Horizon_È() {
        return Horizon.Â;
    }
    
    public Horizon() {
        this.Ø = true;
        this.áˆºÑ¢Õ = 0.35f;
        this.Ç = 0;
        this.È = 0;
        this.áŠ = 0;
        this.ˆáŠ = "";
        Horizon.HorizonCode_Horizon_È = this;
    }
    
    public static EventManager Â() {
        return Horizon.ˆáƒ;
    }
    
    public void Ý() {
        Horizon.à¢ = this;
        this.Ñ¢Â = new PanelManager();
        this.ÇªÓ = new MessageNotifier();
        this.Ï­à = new MsgNotifier();
        this.áˆºáˆºÈ = new SoundPlayer();
        this.Ï = new MusicPlayer();
        this.ÇŽá€ = new VoicePlayer();
        this.áŒŠ = new File(Minecraft.áŒŠà().ŒÏ, "Horizon");
        if (!this.áŒŠ.exists()) {
            this.áŒŠ.mkdir();
        }
        GuiMainMenu.Ý = new ParticleEngine();
        UIFonts.HorizonCode_Horizon_È();
        Horizon.ˆáƒ.HorizonCode_Horizon_È();
        EventManager_1550089733.HorizonCode_Horizon_È(new LSD());
        EventManager_1550089733.HorizonCode_Horizon_È(new FADE());
        EventManager_1550089733.HorizonCode_Horizon_È(new Updating());
        this.Ô = new Notification();
        this.áˆºÏ = new ModuleManager();
        (this.Ø­Âµ = new BindManager()).Â();
        ModuleManager.HorizonCode_Horizon_È(Gui.class).Ø­áŒŠá(54);
        Minecraft.áŒŠà().Šáƒ.HorizonCode_Horizon_È = new NewChat(Minecraft.áŒŠà());
        this.áˆºÏ.Ø­áŒŠá();
        Horizon.à¢.Ï.HorizonCode_Horizon_È(Horizon.à¢.áŒŠ + "/mainmenu/menusong.wav");
        Horizon.à¢.Ï.HorizonCode_Horizon_È(-28.0f);
        FileUtils.Â("spam");
        Horizon.Â = new HWID();
    }
}
