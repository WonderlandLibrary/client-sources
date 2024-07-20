/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client;

import dev.intave.viamcp.ViaMCP;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TextComponentTranslation;
import org.lwjgl.opengl.Display;
import ru.govno.client.cfg.ConfigManager;
import ru.govno.client.clickgui.ClickGuiScreen;
import ru.govno.client.clickgui.GuiMusicTuner;
import ru.govno.client.event.EventManager;
import ru.govno.client.friendsystem.FriendManager;
import ru.govno.client.module.ModuleManager;
import ru.govno.client.module.modules.ClientColors;
import ru.govno.client.module.modules.PointRender;
import ru.govno.client.utils.ClientRP;
import ru.govno.client.utils.Command.CommandManager;
import ru.govno.client.utils.MacroMngr.MacrosManager;
import ru.govno.client.utils.Render.BlurUtil;
import ru.govno.client.utils.Render.GlassUtil;
import ru.govno.client.utils.Render.glsandbox.animbackground;
import ru.govno.client.utils.TPSDetect;

public class Client {
    public static BlurUtil blur;
    public static GlassUtil glass;
    public static String name;
    public static String version;
    public static Client instance;
    public static GuiMusicTuner clickGuiMusic;
    public static GuiMusicTuner mainGuiNoise;
    public static PointRender pointRenderer;
    public static ClientColors.ClientColorsHud clientColosUI;
    public static ConfigManager configManager;
    public static ModuleManager moduleManager;
    public static MacrosManager macrosManager;
    public static ClickGuiScreen clickGuiScreen;
    public static FriendManager friendManager;
    public static CommandManager commandManager;
    public static EventManager eventManager;
    public static boolean loadDefault;
    public static animbackground screenshader;
    public static long initTime;

    public static String randomNickname() {
        String[] names = new String[]{"VegaLine", "RussianPirat", "Ladoga", "ny7oBKa", "IIIuPuHKa", "DataBase", "KoTuK", "nayk", "nykaH", "nykaJLKa", "IIIa7oMeP", "Ohtani", "Tango", "HardStyle", "GoToSleep", "Movietone", "7aIIIuK", "TpyCuKu", "TheKnight", "OnlySprint", "Press_W", "HowToWASD", "BloodFlow", "CutVeins", "Im_A_Cyber", "NextTime", "Killer", "Murauder", "AntiPetuh", "CMeTaHKa", "Enigma", "Doctor", "TheGhost", "GhostRunner", "Banana", "Ba3eJLuH", "MaCTyp6eK", "BaHTy3", "AliExpress", "Agressor", "Spasm", "SHAMAN", "optimist", "", "Banker", "JahMachine", "Cu7aPa", "nuBo", "CuM6uoT", "Venom", "Superman", "Supreme", "CeKcU_6ou", "SuperSpeed", "KnuckKnuck", "6o7aTbIPb", "SouthPark", "Simpson", "IIIaJLaIII", "3_Penetrate", "EmptySoul", "Firefly", "PlusTopka", "TryMe", "YouAreWeak", "MegaSexual", "Pikachu", "Pupsik", "Legenda", "SCP", "MyNumber", "YourToy", "SexShop", "Slayer", "Murderer", "CallMe", "PvpTeacher", "CrazyVega", "4ynuK", "6aToH", "LongPenis", "Caxap", "Infernal", "Vegota", "Vegator", "Vegoza", "Veglin", "Devo4ka", "SexySister", "NakedBody", "PlusZ4", "ThiefInLaw", "StrongTea", "BlackTea", "SmallAss", "SmallBoobs", "CoffeeDEV", "FireRider", "MilkyWay", "PeacefulBoy", "Lambada", "MagicSpeed", "ThrowMom", "StopPlay", "KillMother", "XDeadForGay", "ALTf4", "HowAreYou", "GoSex", "Falas", "Sediment", "OpenDoor", "ShitInTrap", "SuckItUp", "NeuroNET", "BunnyHop", "BmxSport", "GiveCoord", "eHoTuK", "KucKa", "3auKa", "4aIIIa", "HykaHax", "Sweet", "MoHuTop", "Me7aMa4o", "Miner", "BonAqua", "COK", "BANK", "Lucky", "SPECTATE", "7OBHO", "MyXA", "Owner", "5opka", "JUK", "FaceBreak", "SnapBody", "Psycho", "EasyWin", "SoHard", "Panties", "SoloGame", "Robot", "Surgeon", "_IMBA", "ShakeMe", "EnterMe", "GoAway", "TRUE", "while", "Pinky", "Pickup", "Stack", "GL11", "GLSL", "Garbage", "NoBanMe", "WiFi", "Tally", "Dream", "Mommy", "6aTya", "Pivovar", "Alkash", "Gangsta", "Counter", "Clitor", "HentaUser", "BrowseHent", "LoadChunk", "Panical", "Kakashka", "MinusVse", "Pavlik", "RusskiPirat", "GoodTrip", "6A6KA", "3000IQ", "0IQ", "VEGA33", "YOUR_BOSS", "CPacketGay", "4Bytes", "SinCos", "Yogurt", "SexInTrash", "TrashMyHome", "PenisUser", "VaginaLine", "VagLine", "Virginia", "NoReportMe", "Bluetouth", "PivoBak", "6AKJLAXA", "Opostrof", "Harming", "Cauldron", "Dripka", "Wurdulak", "Presedent", "Opstol", "Oposum", "Babayka", "O4KAPUK", "Dunozavr", "Cocacola", "Fantazy", "70JLA9I", "PedalKTLeave", "TolstoLobik", "nePDyH", "HABO3HUK", "KOT", "CKOT", "BISHOP", "4ukeH", "nanaxa", "Berkyt", "Matreshka", "HACBAU", "XAPEK", "Mopedik", "CKELET2013", "GodDrakona", "CoLHbiIIIKo", "HA77ETC", "PoM6uK", "PomaH", "6oM6UJLa", "MOH7OJl", "OutOfMemory", "PopkaDurak", "4nokVPupok", "Pinality", "Shaverma", "MOJLUCb", "MOJLuTBA", "CTEHA", "CKAJLA", "JohnWeak", "Plomba", "neKaPb", "Disconnect", "Oriflame", "Mojang", "TPPPPP", "EvilBoy", "DavaiEbaca", "TuMeuT", "Tapan", "600K7Puzo", "Poctelecom", "Interzet", "C_6oDUHA", "6yHTaPb", "Milka", "KOLBASA", "OhNo", "YesTea", "Mistik", "KuHDep", "Smippy", "Diamond", "KedpOBuK", "Lolikill", "CrazyGirl", "Kronos", "Kruasan", "MrProper", "HellCat", "Nameless", "Viper", "GamerYT", "slimer", "MrSlender", "Gromila", "BoomBoom", "Doshik", "BananaKenT", "NeonPlay", "Progibator", "Rubak", "MrMurzik", "Kenda", "DrShine", "cnacu6o", "Eclips", "ShadowFuse", "DrVega", "Bacardi", "UwU_FUF", "Exont", "Persik", "Mambl", "Rossamaha", "DrKraken", "MeWormy", "WaterBomb", "YourStarix", "nakeTuk", "Massik", "MineFOX", "BitCoin", "Avocado", "Chappi", "ECEQO", "Fondy", "StableFace", "JeBobby", "KrytoyKaka", "MagHyCEp", "I7evice", "LeSoulles", "EmptySoul", "KOMnOT", "MrPlay", "NGROK2009", "NoProblem", "MrPatric", "OkugAmas", "YaBuilder", "A7EHT007", "PussyGirl", "Triavan", "TyCoBKa", "UnsafeINV", "", "yKcyc_UFO", "Wendy", "Bendy", "XAOC", "ST6yP7", "XYNECI", "HENTAI", "YoutDaddy", "YouGurT", "EnumFacing", "HitVec3d", "JavaGypy", "VIWEBY", "ZamyPlay", "SUSUKI", "KPAX_TRAX", "Emiron", "UzeXilo", "Rembal", "Gejmer", "EvoNeon", "MrFazen", "ESHKERE", "FARADENZA", "EarWarm", "CMA3KA", "NaVi4oK", "A4_OMG", "YCYSAPO", "Booster", "BroDaga", "CastlePlay29", "DYWAHY", "Emirhan", "BezPontov", "Xilece", "Gigabait", "Griefer", "Goliaf", "Fallaut", "HERODOT", "KingKong", "NADOBNO", "ODIZIT", "Klawdy", "NCRurtler", "Fixik", "FINISHIST", "KPACOTA", "GlintEff", "Flexer", "NeverMore", "BludShaper", "PoSaN4Ik", "Goblin", "Aligator", "Zmeyka", "FieFoxe", "Homilion", "Locator", "kranpus", "HOLSON", "CocyD_ADA", "Anarant", "O6pUKoc", "MissTitan", "JellyKOT", "JellyCat", "LolGgYT", "MapTiNi", "GazVax", "Foxx4ever", "NaGiBaToP", "whiteKing", "KitKat", "VkEdAx", "Pro100Hy6", "Contex", "Durex", "Mr_Golem", "Moonlight", "CoolBoy", "6oTaH", "CaHa6uC", "MuJLaIIIKa", "AvtoBus", "ABOBA", "KanaTuK", "TpanoFob", "CAPSLOCK", "Sonic", "SONIK", "COHUK", "Tailss", "TAILSS", "TauJLC", "Ehidna", "exudHa", "Naklz", "HaKJL3", "coHuk", "parebuh", "nape6yx", "TEPOPNCT", "TPEHEP", "6OKCEP", "KARATE_TYAN", "Astolfo", "Itsuki", "Yotsuba", "Succub", "CyKKy6", "MuJLaIIIKa", "Chappie", "LeraPala4", "MegaSonic", "ME7A_COHUK", "SonicEzh", "IIaPe6yx", "Flamingo", "Pavlin", "VenusShop", "PinkRabbit", "EpicSonic", "EpicTailss", "Genius", "Valkiria"};
        String[] titles = new String[]{"DADA", "YA", "2001", "2002", "2003", "2004", "2005", "2006", "2007", "2008", "2009", "2010", "2011", "2012", "2013", "2014", "2015", "2016", "SUS", "SSS", "TAM", "TyT", "TaM", "Ok", "Pon", "LoL", "CHO", "4oo", "MaM", "Top", "PvP", "PVH", "DIK", "KAK", "SUN", "SIN", "COS", "FIT", "FAT", "HA", "AHH", "OHH", "UwU", "DA", "NaN", "RAP", "WoW", "SHO", "KA4", "Ka4", "AgA", "Fov", "LoVe", "TAN", "Mia", "Alt", "4el", "bot", "GlO", "Sir", "IO", "EX", "Mac", "Win", "Lin", "AC", "Bro", "6po", "6PO", "BRO", "mXn", "XiL", "TGN", "24", "228", "1337", "1488", "007", "001", "999", "333", "666", "111", "FBI", "FBR", "FuN", "FUN", "UFO", "OLD", "Old", "New", "OFF", "ON", "YES", "LIS", "NEO", "BAN", "OwO", "0_o", "0_0", "o_0", "IQ", "99K", "AK47", "SOS", "S0S", "SoS", "z0z", "zOz", "Zzz", "zzz", "ZZZ", "6y", "BU", "RAK", "PAK", "Pak", "MeM", "MoM", "M0M", "KAK", "TAK", "n0H", "BOSS", "RU", "ENG", "BAF", "BAD", "ZED", "oy", "Oy", "0y", "Big", "Air", "Dirt", "Dog", "CaT", "CAT", "KOT", "EYE", "CAN", "ToY", "ONE", "OIL", "HOT", "HoT", "VPN", "BnH", "Ty3", "GUN", "HZ", "XZ", "XYZ", "HZ", "XyZ", "HIS", "HER", "DOC", "COM", "DIS", "TOP", "1ST", "1st", "LORD", "DED", "ded", "HAK", "FUF", "IQQ", "KBH", "KVN", "HuH", "WWW", "RUN", "RuN", "run", "PRO", "100", "300", "3OO", "RAM", "DIR", "Yaw", "YAW", "TIP", "Tun", "Ton", "Tom", "Your", "AM", "FM", "YT", "yt", "Yt", "yT", "RUS", "KON", "FAK", "FUL", "RIL", "pul", "RW", "MST", "MEN", "MAN", "NO0", "SEX", "H2O", "H20", "LyT", "3000", "01", "KEK", "PUK", "nuk", "nyk", "nyK", "191", "192", "32O", "5OO", "320", "500", "777", "720", "480", "48O", "HUK", "BUS", "LUN", "LyH", "Fuu", "LaN", "LAN", "DIC", "HAA", "NON", "FAP", "4AK", "4on", "4EK", "4eK", "NVM", "BOG", "RIP", "SON", "XXL", "XXX", "GIT", "GAD", "8GB", "5G", "4G", "3G", "2G", "TX", "GTX", "RTX", "HOP", "TIR", "ufo", "MIR", "MAG", "ALI", "BOB", "GRO", "GOT", "ME", "SO", "Ay4", "MSK", "MCK", "RAY", "EVA", "EvA", "DEL", "ADD", "UP", "VK", "LOV", "AND", "AVG", "EGO", "YTY", "YoY", "I_I", "G_G", "D_D", "V_V", "F", "FF", "FFF", "LCM", "PCM", "CPS", "FPS", "GO", "G0", "70", "7UP", "JAZ", "GAZ", "7A3", "UFA", "HIT", "DAY", "DaY", "S00", "SCP", "FUK", "SIL", "COK", "SOK", "WAT", "WHO", "PUP", "PuP", "Py", "CPy", "SRU", "OII", "IO", "IS", "THE", "SHE", "nuc", "KXN", "VAL", "MIS", "HXI", "HI", "ByE", "WEB", "TNT", "BEE", "4CB", "III", "IVI", "POP", "C4", "BRUH", "Myp", "MyP", "NET", "CAR", "PET", "POV", "POG", "OKK", "ESP", "GOP", "G0P", "7on", "E6y", "BIT", "PIX", "AYE", "Aye", "PVP", "GAS", "REK", "rek", "PEK", "n0H", "RGB"};
        String name = names[(int)(((float)names.length - 1.0f) * (float)Math.random() * (((float)names.length - 1.0f) / (float)names.length))];
        String title = titles[(int)(((float)titles.length - 1.0f) * (float)Math.random() * (((float)titles.length - 1.0f) / (float)titles.length))];
        int size = (name + "_").length();
        return name + "_" + (16 - size == 0 ? "" : title);
    }

    public static void run() {
        ClientRP.getInstance().init();
        ClientRP.getInstance().getDiscordRP().start();
        new TPSDetect();
        clientColosUI = new ClientColors.ClientColorsHud();
        blur = new BlurUtil();
        glass = new GlassUtil();
        configManager = new ConfigManager();
        commandManager = new CommandManager();
        moduleManager = new ModuleManager();
        pointRenderer = new PointRender();
        macrosManager = new MacrosManager();
        eventManager = new EventManager();
        clickGuiScreen = new ClickGuiScreen();
        friendManager = new FriendManager();
        Display.setTitle(name + " " + version);
        configManager.saveConfig("nulled");
    }

    public static void initVia() {
        try {
            ViaMCP.create();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void msg(String s, boolean prefix) {
        s = (prefix ? "\u00a7bVegaLine > " : "") + "\u00a77" + (String)s;
        s = (String)s;
        Minecraft.player.addChatMessage(new TextComponentTranslation(((String)s).replace("&", "??"), new Object[0]));
    }

    public static void msg(int s, boolean prefix) {
        String a = "" + s;
        a = (prefix ? "\u00a7bVegaLine > " : "") + "\u00a77" + a;
        Minecraft.player.addChatMessage(new TextComponentTranslation(a.replace("&", "??"), new Object[0]));
    }

    public static void msg(boolean s, boolean prefix) {
        String a = "" + s;
        a = (prefix ? "\u00a7bVegaLine > " : "") + "\u00a77" + a;
        Minecraft.player.addChatMessage(new TextComponentTranslation(a.replace("&", "??"), new Object[0]));
    }

    public static void msg(double s, boolean prefix) {
        String a = "" + s;
        a = (prefix ? "\u00a7bVegaLine > " : "") + "\u00a77" + a;
        Minecraft.player.addChatMessage(new TextComponentTranslation(a.replace("&", "??"), new Object[0]));
    }

    public static void msg(float s, boolean prefix) {
        String a = "" + s;
        a = (prefix ? "\u00a7bVegaLine > " : "") + "\u00a77" + a;
        Minecraft.player.addChatMessage(new TextComponentTranslation(a.replace("&", "??"), new Object[0]));
    }

    static {
        name = "Vegaline Client ";
        version = "#00032";
        instance = new Client();
        clickGuiMusic = new GuiMusicTuner("clickguimusic1", 0.0f);
        mainGuiNoise = new GuiMusicTuner("main_noise", 0.4f);
        loadDefault = true;
        initTime = System.currentTimeMillis();
    }
}

