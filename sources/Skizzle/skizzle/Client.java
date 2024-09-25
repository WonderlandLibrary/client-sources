/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.Display
 */
package skizzle;

import com.github.creeper123123321.viafabric.ViaFabric;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import net.arikia.dev.drpc.DiscordUser;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import org.lwjgl.opengl.Display;
import skizzle.DiscordClient;
import skizzle.alts.Alt;
import skizzle.alts.AltManager;
import skizzle.commands.CommandManager;
import skizzle.events.Event;
import skizzle.events.listeners.EventChat;
import skizzle.events.listeners.EventPacket;
import skizzle.events.listeners.EventRenderGUI;
import skizzle.events.listeners.EventServerMotion;
import skizzle.files.FileManager;
import skizzle.files.ProfileManager;
import skizzle.fonts.Fonts;
import skizzle.friends.FriendManager;
import skizzle.modules.Module;
import skizzle.modules.ModuleManager;
import skizzle.modules.render.HUDModule;
import skizzle.modules.world.Timer;
import skizzle.newFont.FontUtil;
import skizzle.newFont.MinecraftFontRenderer;
import skizzle.scripts.ScriptManager;
import skizzle.ui.HUD;
import skizzle.ui.notifications.Notification;
import skizzle.ui.notifications.Notifications;
import skizzle.users.ServerManager;

public class Client {
    public static String lastUsername;
    public static CopyOnWriteArrayList<Module> modules;
    public static String discordLink;
    public static boolean showHud;
    public static String[] changeLog;
    public static boolean tabGUI;
    public static FriendManager friendManager;
    public static ArrayList<String> skizzleUsers;
    public static boolean ghostMode;
    public static int rgbSpeed;
    public static float hue;
    public static boolean discordRPC;
    public static boolean isVerified;
    public static int RGBColor2;
    public static int RGBColor;
    public static double drawMessages;
    public static float versionF;
    public static AltManager altManager;
    public static boolean showSpeed;
    public static Timer updateResetTimer;
    public static HUDModule hudModule;
    public static Notifications notifications;
    public static ArrayList<String> hasChecked;
    public static String[] readSettings;
    public static String skizzlePath;
    public static skizzle.util.Timer delayTimerStuff;
    public static String version;
    public static String currentProfile;
    public static ModuleManager moduleManager;
    public static boolean isBeta;
    public static String[] serverUsers;
    public static CommandManager commandManager;
    public static MinecraftFontRenderer fontNormal;
    public static HUD hud;
    public static boolean showFPS;
    public static Minecraft mc;
    public static DiscordUser discordUser;
    public static String name;
    public static String currentIP;
    public static FileManager fileManager;
    public static float[] serverRotations;
    public static ProfileManager profileManager;
    public static Calendar date;
    public static double delay;
    public static boolean rgbEnabled;

    public static void addNotification(Notification Nigga) {
        Client.notifications.notifs.add(Nigga);
    }

    public static List<Module> getModulesByCategory(Module.Category Nigga) {
        ArrayList<Module> Nigga2 = new ArrayList<Module>();
        for (Module Nigga3 : modules) {
            if (Nigga3.category != Nigga) continue;
            Nigga2.add(Nigga3);
        }
        return Nigga2;
    }

    public static void onEvent(Event Nigga) {
        Object Nigga2;
        notifications.onEvent(Nigga);
        boolean cfr_ignored_0 = Nigga instanceof EventRenderGUI;
        if (Nigga instanceof EventChat) {
            commandManager.handleChat((EventChat)Nigga);
        }
        if (Nigga instanceof EventPacket) {
            EventPacket Nigga3 = (EventPacket)Nigga;
            if (Nigga3.getPacket() instanceof S02PacketChat) {
                S02PacketChat Nigga22 = (S02PacketChat)Nigga3.getPacket();
                if (Nigga22.chatComponent.getUnformattedText().contains(Qprot0.0("\u4fac\u71ce\u74ed\ue279\uabd2\uca27\u8c6f\u238f\u12c5\u00cd"))) {
                    Minecraft.getMinecraft().thePlayer.sendChatMessage(Qprot0.0("\u4fac\u71ce\u74ed\ue279\uabd2\uca27\u8c6f\u238f\u12c5\u00cd\u4058\uaf05\ucd12\u3784\udfe8\ud38b\u42e2\uf9fd\u52c7\ub4d6\ub601\u01e6\u5fc3\uadf4\u6c65\uf281\u2f1a\u9a93\u07d7\u215c\uae60\u8816\uc26d\ue8e3\ub108\u6db4\uddb1\u250a\u0f70\u8f57\u4c97\u3c14"));
                }
            }
            if (Nigga3.getPacket() instanceof S08PacketPlayerPosLook) {
                Nigga2 = (S08PacketPlayerPosLook)Nigga3.getPacket();
                EventServerMotion eventServerMotion = new EventServerMotion(((S08PacketPlayerPosLook)Nigga2).x, ((S08PacketPlayerPosLook)Nigga2).y, ((S08PacketPlayerPosLook)Nigga2).z, ((S08PacketPlayerPosLook)Nigga2).yaw, ((S08PacketPlayerPosLook)Nigga2).pitch);
                Client.onEvent(eventServerMotion);
                ((S08PacketPlayerPosLook)Nigga2).x = eventServerMotion.x;
                ((S08PacketPlayerPosLook)Nigga2).y = eventServerMotion.y;
                ((S08PacketPlayerPosLook)Nigga2).z = eventServerMotion.z;
                ((S08PacketPlayerPosLook)Nigga2).yaw = eventServerMotion.yaw;
                ((S08PacketPlayerPosLook)Nigga2).pitch = eventServerMotion.pitch;
            }
            if (Nigga3.getPacket() instanceof C03PacketPlayer) {
                Nigga2 = (C03PacketPlayer)Nigga3.getPacket();
                if (((C03PacketPlayer)Nigga2).rotating) {
                    serverRotations = new float[]{((C03PacketPlayer)Nigga2).getYaw(), ((C03PacketPlayer)Nigga2).getPitch()};
                }
            }
        }
        ScaledResolution Nigga3 = new ScaledResolution(mc, Client.mc.displayWidth, Client.mc.displayHeight);
        Nigga2 = fontNormal;
        if (Nigga instanceof EventRenderGUI) {
            drawMessages = 0.0;
        }
        for (Module module : modules) {
            int Nigga5 = (int)(System.currentTimeMillis() - module.animationTime.lastMS);
            ++Nigga5;
            if (module.name.equals(Qprot0.0("\u4fbb\u71ca\u74f1\ue26a\uabd2\uca21"))) continue;
            if (module.displayHud && module.animationTime.hasTimeElapsed((long)-917393626 ^ 0xFFFFFFFFC951AF26L, true)) {
                double Nigga6 = Nigga3.getScaledWidth() - (Nigga3.getScaledWidth() - ((MinecraftFontRenderer)Nigga2).getStringWidth(module.displayName) - 12);
                if (module.animStage > 0.0 && !module.animgoingin) {
                    module.animStage -= (double)Nigga5 / (500.0 / Nigga6);
                }
                if (module.animStage < Nigga6 + 1.0 && module.animgoingin) {
                    module.animStage += (double)Nigga5 / (500.0 / Nigga6);
                }
                if (module.animStageVerticle < Client.hudModule.spacing.getValue() && !module.animgoingin) {
                    module.animStageVerticle += (double)Nigga5 / 100.0;
                }
                if (module.animStageVerticle > -0.001 && module.animgoingin) {
                    module.animStageVerticle -= (double)Nigga5 / 100.0;
                }
                if (module.animStageVerticle > Client.hudModule.spacing.getValue()) {
                    module.animStageVerticle = Client.hudModule.spacing.getValue();
                }
                if (module.animStageVerticle < 0.0 && module.animStage > Nigga6) {
                    module.displayHud = false;
                    module.animStageVerticle = 0.0;
                }
            }
            if (module.isEnabled()) {
                if (module.animStage < 0.0) {
                    module.animStage = 0.0;
                }
                module.onEvent(Nigga);
            }
            module.onEventOverride(Nigga);
            module.animationTime.reset();
        }
    }

    public static int getPlayerPing() {
        return Client.getPlayerPing(Client.mc.thePlayer.getName());
    }

    public static int drawChatMessage(String Nigga, float Nigga2, float Nigga3, int Nigga4) {
        return Fonts.size18.drawString(Nigga, Nigga2, Nigga3, Nigga4, false);
    }

    public static void startup() {
        String[] Nigga;
        ModuleManager.getModules();
        ScriptManager.addScripts();
        DiscordClient.getInstance().init();
        if (ServerManager.sendPacketWithResponse(Qprot0.0("\u4fb8\u71c3\u74ea\ue7ff\ua154\uca39\u8c26\u23c9\u1719"), Client.getMotherboardSN()).equals(Qprot0.0("\u4fa8\u71c4"))) {
            isVerified = true;
            System.out.println(Qprot0.0("\u4f98\u71c3\u74ea\ue7ff\ua154\uca39\u8c26\u23c9\u1719"));
        }
        commandManager.setup();
        try {
            new ViaFabric().onInitialize();
            System.out.println(Qprot0.0("\u4fa3\u71c4\u74e2\ue7ef\ua154\uca31\u8c6f\u23ec\u1704\u0a1a\u4035\uaf2f\ucd31"));
        }
        catch (Exception Nigga2) {
            System.out.println(Qprot0.0("\u4faa\u71d9\u74f1\ue7e4\ua143\uca75\u8c23\u23d5\u170c\u0a1f\u4011\uaf02\ucd06\u3202\ud55e\ud383\u42e8\uf9d5\u572c\ube20\ub613\u018a"));
            Nigga2.printStackTrace();
        }
        String Nigga3 = fileManager.readFile(Qprot0.0("\u4f8e\u71c7\u74f7\ue7f8"));
        String[] arrstring = Nigga = Nigga3.split("\n");
        int n = Nigga.length;
        for (int i = 0; i < n; ++i) {
            String Nigga4 = arrstring[i];
            if (!Nigga4.contains(":")) continue;
            String[] Nigga5 = Nigga4.split(":");
            altManager.getRegistry().add(new Alt(Nigga5[0], Nigga5[1], Nigga5[0]));
        }
        Fonts.loadFonts();
        FontUtil.bootstrap();
        updateResetTimer = new Timer();
        fontNormal = FontUtil.cleanSmall;
        new Thread(Client::lambda$0).start();
        FriendManager.friends = FriendManager.setFriends();
        Display.setTitle((String)(String.valueOf(name) + Qprot0.0("\u4fcf\u719a\u74ad\ue7b3\ua111\uca29\u8c6f") + version));
        new FileManager().updateSettings();
    }

    public static void onServerConnect() {
    }

    public static boolean booleanThingy(boolean Nigga, boolean Nigga2) {
        return !Nigga || Nigga2;
    }

    static {
        delayTimerStuff = new skizzle.util.Timer();
        currentProfile = Qprot0.0("\u4f8b\u71ce\u74e5\uab3e\u7570\uca39\u8c3b");
        readSettings = new FileManager().readSettings();
        mc = Minecraft.getMinecraft();
        isBeta = false;
        discordLink = Qprot0.0("\u4f87\u71df\u74f7\uab2f\u7576\uca6f\u8c60\u2395\u5bdd\ude3c\u401b\uaf42\ucd06\u7e91\u0113\ud399\u42e2\uf9f1\u1bc1\u6a3e\ub645\u01cf");
        versionF = Float.intBitsToFloat(1.04348499E9f ^ 0x7F12513F);
        showSpeed = true;
        tabGUI = false;
        rgbEnabled = false;
        discordRPC = true;
        rgbSpeed = 5;
        showFPS = true;
        showHud = true;
        ghostMode = false;
        name = Qprot0.0("\u4fbc\u71c0\u74ea\uab25\u757f\uca39\u8c2a");
        version = Qprot0.0("\u4f8d\u719a\u74b2");
        modules = new CopyOnWriteArrayList();
        hud = new HUD();
        hue = (float)(System.currentTimeMillis() % ((long)1001721908 ^ 0x3BB503BCL)) / Float.intBitsToFloat(9.8419027E8f ^ 0x7F35CD30);
        RGBColor = Color.HSBtoRGB(hue, Float.intBitsToFloat(1.08840794E9f ^ 0x7F5FC950), Float.intBitsToFloat(1.08444992E9f ^ 0x7F2364AD));
        RGBColor2 = Color.HSBtoRGB(hue, Float.intBitsToFloat(1.10037069E9f ^ 0x7EA56031), Float.intBitsToFloat(1.10537946E9f ^ 0x7EFB591B));
        fontNormal = FontUtil.mediumfont;
        date = Calendar.getInstance();
        currentIP = Qprot0.0("\u4fbc\u71c2\u74ed\uab38\u7569\uca30\u8c6f\u23ea\u5bd5\ude2e\u4001\uaf09\ucd13");
        skizzlePath = String.valueOf(Minecraft.getMinecraft().mcDataDir.getAbsolutePath()) + "\\" + name + "\\";
        fileManager = new FileManager();
        moduleManager = new ModuleManager();
        notifications = new Notifications();
        friendManager = new FriendManager();
        profileManager = new ProfileManager();
        altManager = new AltManager();
        commandManager = new CommandManager();
        drawMessages = 0.0;
        skizzleUsers = new ArrayList();
        hasChecked = new ArrayList();
        updateResetTimer = new Timer();
        lastUsername = null;
        isVerified = false;
        serverUsers = new String[]{""};
        serverRotations = new float[]{Float.intBitsToFloat(2.12693734E9f ^ 0x7EC67CC5), Float.intBitsToFloat(2.13881971E9f ^ 0x7F7BCC7B)};
    }

    public static void shutdown() {
        DiscordClient.getInstance().shutdown();
    }

    public static void drawString(String Nigga, int Nigga2, int Nigga3, int Nigga4, boolean Nigga5) {
        if (ModuleManager.hudModule.fontSetting.getMode().equals(Qprot0.0("\u4fa1\u71c4\u74f7\ud3eb\u9d50\uca3b\u8c28"))) {
            FontRenderer Nigga6 = Minecraft.getMinecraft().fontRendererObj;
            if (Nigga5) {
                Nigga6.drawStringWithShadow(Nigga, Nigga2, Nigga3, Nigga4);
            } else {
                Nigga6.drawStringNormal(Nigga, Nigga2, Nigga3, Nigga4);
            }
        } else {
            MinecraftFontRenderer Nigga7 = FontUtil.cleanSmall;
            if (Nigga5) {
                Nigga7.drawStringWithShadow(Nigga, Nigga2, Nigga3, Nigga4);
            } else {
                Nigga7.drawString(Nigga, Nigga2, Nigga3, Nigga4);
            }
        }
    }

    public static void drawMessage(String Nigga, int Nigga2) {
        ScaledResolution Nigga3 = new ScaledResolution(mc, Client.mc.displayWidth, Client.mc.displayHeight);
        FontUtil.cleanSmall.drawStringWithShadow(Nigga, (double)Nigga3.getScaledWidth() / 2.0 - (double)Fonts.size18.getStringWidth(Nigga) / 2.0, (float)Nigga3.getScaledHeight() / Float.intBitsToFloat(1.05640826E9f ^ 0x7EF782B3) + Float.intBitsToFloat(1.01829165E9f ^ 0x7D91E5D7) + (float)(11.0 * drawMessages), Nigga2);
        drawMessages += 1.0;
    }

    public void setup() {
    }

    public Client() {
        Client Nigga;
    }

    public static void keyPress(int Nigga) {
        for (Module Nigga2 : modules) {
            if (Nigga2.getKey() != Nigga || !Nigga2.canToggle()) continue;
            Nigga2.toggle();
        }
    }

    public static String getMotherboardSN() {
        String Nigga = "";
        try {
            String Nigga2;
            File Nigga3 = File.createTempFile(Qprot0.0("\u4f9d\u71ce\u74e2\u6272\u2a22\uca3a\u8c38\u23ce\u9297"), Qprot0.0("\u4fc1\u71dd\u74e1\u626d"));
            Nigga3.deleteOnExit();
            FileWriter Nigga4 = new FileWriter(Nigga3);
            Nigga4.write(Qprot0.0("\u4fbc\u71ce\u74f7\u623e\u2a25\uca37\u8c25\u23ed\u92b5\u8149\u402b\uaf09\ucd13\ub7c1\u5e1a\ud389\u42ec\uf9b8\ud2c7\u352b\ub66e\u01cf\u5fde\u2dd9\ued8f\uf282\u2f1b\u9ab2\u87df\ua0fc\uae2c\u8802\uc261\u68be\u30ea\u6db6\uddb2\u251f\u8f7d\u0ef5\u4caf\u3c61\u5710\ufc6f\ucaab\ue9b4\ua141\u7c7f\ud588\u69a4\u6302\uf453\uf2ae\uf573\u7fdb\u6acf\u6921\u0317\u702e\u8f75\ue580\u0991\u2df6\ufb61\u5a8a\u5e9c\u4a9d\u10b3\ubf98\u8157\uc6d9\ub0b3\ud83a\u59d7\ucc71\ub6a2\u46a6\u0986\uf794\uf7bc\u0905\ud42a\uf912\u1dc1\u86fc\u64eb\u708d\u33d8\u0ccf\u8850\uabbd\uc36a\u8684\ua84f\u0672\u910a\u9441\ue0d9\ue15f\u8391\u6cee\uc307\u1037\u4a3d\u09cf\u4f88\u2d90\u3535\u950f\u9a7a\ua228\u192f\u503d\ufe72\uffbf\ue144\ubf5a\uaf51\u26a5\ubb65\ucf91\u7a7f\u057e\u6b94\ue7a8\u6895\u22e6\uea52\ufbda\u245a\u3d3f\uc584\u0d8d\uc5f2\u053e\udc9c\ub7a9\u7e92\u01a9\ua04d\u418f\u9cd3\u5752\ua2bb\u2ae7\u149f\u122a\u77c4\ub48c\u234b\u89de\ue3bc\uf2c1\u443e\uac2d\ue91d\ucd24\u7989\u91b3\u1760\uaa30\uf037\u3d69\u4a05\u8f73\u5032\u38c2\udb72\u0724\uff31\ua64a\ue905\u7553\u3ca5\u40e1\u34b4\u1996\u9f31\u4da8\u2d64\u902a\ud355\u8e22\u430c\ue267\u23fc\u6616\u2a93\ucd60\ud8ab\u74f2J\u63e1\u48cb\u2558\u23cf\uf0e3\uc894\uc2cd\u067c\ucd10\ud5af\u17ee\u5163\uebcc\uf98e\ub0a0\u7cd3\u34f2\ua8ef\u5fd3\u4f94\ua47c\u7067\u863b\u9aab\ue5be\ue95e\u2cd5\u2130\uc233\u0a8a\u791b\uef45\u749a\u2503\ued1c\u4752\uce28\u953e\u574b\u9e3b\u8352\u6b54\u086b\u7c74\ub7de\u204a\ue1b0\u5d34\uf2aa\u9757\u3678\ue83c\uc06e\u0352\u127f\uc69c\u6723\ua0e3\u2da5\u997d"));
            Nigga4.close();
            Process Nigga5 = Runtime.getRuntime().exec(Qprot0.0("\u4f8c\u71d8\u74e0\u626c\u2a23\uca25\u8c3b\u239a\u92d7\u812f\u4036\uaf03\ucd2d\ub7d8\u5e14\ud385\u42a9") + Nigga3.getPath());
            BufferedReader Nigga6 = new BufferedReader(new InputStreamReader(Nigga5.getInputStream()));
            while ((Nigga2 = Nigga6.readLine()) != null) {
                Nigga = String.valueOf(Nigga) + Nigga2;
            }
            Nigga6.close();
        }
        catch (Exception Nigga7) {
            Nigga7.printStackTrace();
        }
        return Nigga.trim();
    }

    public static void drawString(String Nigga, float Nigga2, float Nigga3, int Nigga4, boolean Nigga5) {
    }

    public static void lambda$0() {
        try {
            changeLog = ServerManager.sendPacketWithResponse(Qprot0.0("\u4fac\u71c3\u74e2\u270b\ue178\uca30\u8c23\u23d5\ud7e4\u4a12\u401d\uaf18"), Qprot0.0("\u4f81\u71c4\u74ed\u2700")).split("\n");
        }
        catch (Exception Nigga) {
            Nigga.printStackTrace();
            changeLog = Qprot0.0("\u4fa9\u71ca\u74ea\u2709\ue17a\uca31\u8c6f\u23ce\ud7ec\u4a75\u401f\uaf09\ucd15\uf2e9\u9548\ud386\u42ac\uf9fb\u97e9\ufe3f\ub647\u01cd\u5fcf\u6881\u26d7\uf28f").split(Qprot0.0("\u4fca\u71c5\u74ef\u2740"));
        }
    }

    public static void openWebLink(String Nigga) {
        try {
            URI Nigga2 = new URI(Nigga);
            Class<?> Nigga3 = Class.forName(Qprot0.0("\u4f85\u71ca\u74f5\u6a53\u3240\uca34\u8c38\u23ce\u9afa\u9960\u401d\uaf1f\ucd0a\ubfef\u4638\ud39a"));
            Object Nigga4 = Nigga3.getMethod(Qprot0.0("\u4f88\u71ce\u74f7\u6a76\u320b\uca26\u8c24\u23ce\u9abb\u9954"), new Class[0]).invoke(null, new Object[0]);
            Nigga3.getMethod(Qprot0.0("\u4f8d\u71d9\u74ec\u6a45\u321d\uca30"), URI.class).invoke(Nigga4, Nigga2);
        }
        catch (Throwable Nigga5) {
            Minecraft.getMinecraft();
            Minecraft.logger.error(Qprot0.0("\u4fac\u71c4\u74f6\u6a5e\u320a\uca3b\u8c68\u23ce\u9af4\u994b\u4008\uaf09\ucd0f\ubfbb\u463b\ud383\u42e7\uf9f3"), Nigga5);
        }
    }

    public static String translate(String Nigga, String Nigga2, String Nigga3) throws IOException {
        String Nigga4;
        String Nigga5 = Qprot0.0("\u4f87\u71df\u74f7\u54c8\u1093\uca6f\u8c60\u2395\ua42d\ubbc9\u400a\uaf05\ucd11\u8165\u64f7\ud38d\u42e6\uf9f7\ue43b\u0fcd\ub64c\u0184\u5fc9\u1b5f\ud72a\uf2c7\u2f13\u9ab0\ub16e\u9a0c\uae61\u8806\uc227\u5e05\u0a02\u6d90\udd94\u250d\ub9d1\u3406\u4c91\u3c45\u5770\ucaff\uf017\ue9b6\ua176\u7c49\ue326\u5314\u6306\uf450\uf2ec\uc390\u4529\u6a80\u696f\u0329\u46b2\ub5ca\ue5d2\u09a3\u2ded\ucdd8\u6002\u5eb1\u4aba\u1097\u8914\ubba5\uc682\ub0e2\ud804\u6f5b\uf6f0\ub692\u4681\u09ae\uc113\ucd3d\u0933\ud40b\uf919\u2b75\ubc64\u6497\u70a2\u33f4\u3a38\ub2fe\uaba3\uc356\u8699\u9ed9\u3cd7\u911e\u946c\ue08d\ud7c1\ub95a\u6ca6\uc36f\u102b\u7cf7\u3364\u4fa5\u2d8d\u3513\ua38b\ua08b\ua26d\u197d\u5078\uc8d1\uc558\ue15a\ubf0a") + URLEncoder.encode(Nigga3, Qprot0.0("\u4fba\u71ff\u74c5\u5495\u10d8")) + Qprot0.0("\u4fc9\u71df\u74e2\u54ca\u1087\uca30\u8c3b\u2387") + Nigga2 + Qprot0.0("\u4fc9\u71d8\u74ec\u54cd\u1092\uca36\u8c2a\u2387") + Nigga;
        System.out.println(Qprot0.0("\u4faa\u71ea\u74c6\u54f9\u10a5\uca14\u8c0b\u2380\ua47e") + Nigga5);
        URL Nigga6 = new URL(Nigga5);
        StringBuilder Nigga7 = new StringBuilder();
        HttpURLConnection Nigga8 = (HttpURLConnection)Nigga6.openConnection();
        Nigga8.setRequestProperty(Qprot0.0("\u4fba\u71d8\u74e6\u54ca\u10cd\uca14\u8c28\u23df\ua430\ubbde"), Qprot0.0("\u4fa2\u71c4\u74f9\u54d1\u108c\uca39\u8c2e\u2395\ua46b\ubb84\u4048"));
        BufferedReader Nigga9 = new BufferedReader(new InputStreamReader(Nigga8.getInputStream()));
        while ((Nigga4 = Nigga9.readLine()) != null) {
            Nigga7.append(Nigga4);
        }
        Nigga9.close();
        return Nigga7.toString();
    }

    public static int getPlayerPing(String Nigga) {
        if (Client.mc.thePlayer != null) {
            for (Object Nigga2 : Minecraft.getMinecraft().thePlayer.sendQueue.func_175106_d()) {
                try {
                    NetworkPlayerInfo Nigga3 = (NetworkPlayerInfo)Nigga2;
                    if (!Nigga.equals(Nigga3.gameProfile.getName())) continue;
                    return Nigga3.responseTime;
                }
                catch (Exception exception) {}
            }
        }
        return 0;
    }
}

