// 
// Decompiled by Procyon v0.5.36
// 

package intent.AquaDev.aqua;

import de.liquiddev.ircclient.client.IrcPlayer;
import java.util.Arrays;
import java.util.Iterator;
import intent.AquaDev.aqua.modules.Module;
import events.Event;
import java.net.UnknownHostException;
import java.net.MalformedURLException;
import java.io.File;
import java.net.URL;
import intent.AquaDev.aqua.cape.GIF;
import intent.AquaDev.aqua.config.Config;
import viamcp.ViaMCP;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.opengl.GL11;
import java.nio.ByteBuffer;
import org.lwjgl.opengl.Display;
import java.util.Objects;
import de.liquiddev.ircclient.api.IrcApi;
import intent.AquaDev.aqua.utils.IrcChatListener;
import de.liquiddev.ircclient.client.ClientType;
import de.liquiddev.ircclient.client.IrcClientFactory;
import net.minecraft.client.Minecraft;
import intent.AquaDev.aqua.utils.ShaderBackgroundMM;
import intent.AquaDev.aqua.utils.ShaderBackground;
import intent.AquaDev.aqua.gui.novoline.ClickguiScreenNovoline;
import intent.AquaDev.aqua.utils.MouseWheelUtil;
import intent.AquaDev.aqua.command.CommandSystem;
import de.Hero.settings.SettingsManager;
import intent.AquaDev.aqua.utils.FileUtil;
import intent.AquaDev.aqua.modules.ModuleManager;
import de.liquiddev.ircclient.api.IrcClient;
import intent.AquaDev.aqua.cape.GIFManager;
import intent.AquaDev.aqua.cape.GifLoader;
import intent.AquaDev.aqua.utils.UnicodeFontRenderer9;
import intent.AquaDev.aqua.utils.UnicodeFontRenderer8;
import intent.AquaDev.aqua.utils.UnicodeFontRenderer7;
import intent.AquaDev.aqua.utils.UnicodeFontRenderer6;
import intent.AquaDev.aqua.utils.UnicodeFontRenderer5;
import intent.AquaDev.aqua.utils.UnicodeFontRenderer4;
import intent.AquaDev.aqua.utils.UnicodeFontRenderer3;
import intent.AquaDev.aqua.utils.UnicodeFontRenderer2;
import intent.AquaDev.aqua.utils.UnicodeFontRenderer;
import intent.AquaDev.aqua.discord.DiscordRPC;
import net.minecraft.util.ResourceLocation;

public class Aqua
{
    public static Aqua INSTANCE;
    private ResourceLocation resourceLocation;
    public static DiscordRPC discordRPC;
    public UnicodeFontRenderer comfortaa;
    public UnicodeFontRenderer comfortaa2;
    public UnicodeFontRenderer comfortaa3;
    public UnicodeFontRenderer comfortaa4;
    public UnicodeFontRenderer comfortaa5;
    public UnicodeFontRenderer comfortaaGamster;
    public UnicodeFontRenderer rise;
    public UnicodeFontRenderer2 japan;
    public UnicodeFontRenderer3 roboto;
    public UnicodeFontRenderer3 roboto2;
    public UnicodeFontRenderer3 robotoTabguiName;
    public UnicodeFontRenderer3 robotoBlockCount;
    public UnicodeFontRenderer3 robotoBold;
    public UnicodeFontRenderer3 robotoPanel;
    public UnicodeFontRenderer4 sigma;
    public UnicodeFontRenderer4 sigma2;
    public UnicodeFontRenderer4 sigma3;
    public UnicodeFontRenderer5 jelloClickguiPanelTop;
    public UnicodeFontRenderer5 jelloClickguiPanelBottom;
    public UnicodeFontRenderer5 jelloTabGUI;
    public UnicodeFontRenderer5 jelloTabGUIBottom;
    public UnicodeFontRenderer5 jelloClickguiSettings;
    public UnicodeFontRenderer6 novoline;
    public UnicodeFontRenderer6 novlineBigger;
    public UnicodeFontRenderer6 novolineSmall;
    public UnicodeFontRenderer7 verdana2;
    public UnicodeFontRenderer8 esp;
    public UnicodeFontRenderer9 tenacityNormal;
    public UnicodeFontRenderer9 tenacitySmall;
    public UnicodeFontRenderer9 tenacityBig;
    public UnicodeFontRenderer9 tenacity;
    public GifLoader GIFLoader;
    public GIFManager GIFmgr;
    public static String dev;
    public static String name;
    public static String build;
    public static boolean allowed;
    public IrcClient ircClient;
    public static ModuleManager moduleManager;
    public FileUtil fileUtil;
    public static SettingsManager setmgr;
    public static CommandSystem commandSystem;
    public MouseWheelUtil mouseWheelUtil;
    public ClickguiScreenNovoline clickGuiNovo;
    public ShaderBackground shaderBackground;
    public ShaderBackgroundMM shaderBackgroundMM;
    public long lastConnection;
    
    public void AquaStart() throws MalformedURLException {
        Aqua.INSTANCE = this;
        final String ign = Minecraft.getMinecraft().session.getUsername();
        this.ircClient = IrcClientFactory.getDefault().createIrcClient(ClientType.FANTA, "6XK7pNhw3wUxJwCL", ign, "b" + Aqua.build);
        this.ircClient.getApiManager().registerApi(new IrcChatListener());
        final String beta = (Objects.equals(this.ircClient.getNickname(), "luigiaqua") || Objects.equals(Aqua.INSTANCE.ircClient.getNickname(), "LCA_MODZ")) ? " Beta" : "";
        Display.setTitle("" + Aqua.name + " b" + Aqua.build + beta + " by " + Aqua.dev);
        final ByteBuffer buffer;
        final int id;
        this.ircClient.getApiManager().registerCustomDataListener((sender, tag, data) -> {
            buffer = ByteBuffer.wrap(data);
            id = buffer.getInt();
            if (tag.toLowerCase().startsWith("report_")) {
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/report " + tag.split("report_")[1] + " hacking confirm");
            }
            return;
        });
        Aqua.moduleManager = new ModuleManager();
        if (GL11.glGetString(7937).contains("Nvidia") || GL11.glGetString(7937).contains("AMD") || GL11.glGetString(7937).contains("RX")) {
            System.out.println("Found GraphicCard");
            Aqua.discordRPC.start();
        }
        Aqua.commandSystem = new CommandSystem();
        (this.fileUtil = new FileUtil()).createFolder();
        this.fileUtil.createPicFolder();
        this.fileUtil.createConfigFolder();
        this.fileUtil.loadKeys();
        this.fileUtil.loadModules();
        this.mouseWheelUtil = new MouseWheelUtil();
        this.clickGuiNovo = new ClickguiScreenNovoline(null);
        try {
            ViaMCP.getInstance().start();
            ViaMCP.getInstance().initAsyncSlider();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        new Config("settings.txt", false, true).loadOnStart();
        this.GIFLoader = new GifLoader();
        (this.GIFmgr = new GIFManager()).addGif(new GIF("test2", "test2"));
        this.GIFmgr.addGif(new GIF("rias2", "rias2"));
        this.GIFmgr.addGif(new GIF("rias", new URL("https://i.ibb.co/qdM3pq7/rias.gif")));
        this.GIFmgr.addGif(new GIF("aqua", new URL("https://i.imgur.com/aNm2z1W.gif")));
        this.GIFmgr.addGif(new GIF("anime", new URL("https://media.tenor.com/9xGP1UCWLw4AAAAM/weathering-with-you-hina-amano.gif")));
        for (final File f : FileUtil.PIC.listFiles()) {
            Aqua.setmgr.getSetting("GuiElementsMode").modes = this.addToStringArray(Aqua.setmgr.getSetting("GuiElementsMode").getModes(), f.getName().split("\\.")[0]);
        }
        Runtime.getRuntime().addShutdownHook(new Thread(this::shutdown));
    }
    
    public Aqua() throws UnknownHostException {
        this.shaderBackground = new ShaderBackground();
        this.shaderBackgroundMM = new ShaderBackgroundMM();
        Aqua.INSTANCE = this;
        if (GL11.glGetString(7937).contains("NVIDIA") || GL11.glGetString(7937).contains("AMD") || GL11.glGetString(7937).contains("RX")) {
            System.out.println("Found GraphicCard");
            Aqua.discordRPC.start();
        }
        this.shaderBackground = new ShaderBackground();
        this.shaderBackgroundMM = new ShaderBackgroundMM();
        this.japan = UnicodeFontRenderer2.getFontFromAssets("Japanese", 40, 0, 0.0f, 1.0f);
        this.comfortaa = UnicodeFontRenderer.getFontFromAssets("Comfortaa-Regular", 40, 0, 0.0f, 1.0f);
        this.comfortaa2 = UnicodeFontRenderer.getFontFromAssets("Comfortaa-Regular", 37, 0, 0.0f, 1.0f);
        this.rise = UnicodeFontRenderer.getFontFromAssets("Comfortaa-Regular", 18, 0, 0.0f, 1.0f);
        this.comfortaa3 = UnicodeFontRenderer.getFontFromAssets("Comfortaa-Regular", 20, 0, 0.0f, 1.0f);
        this.comfortaa4 = UnicodeFontRenderer.getFontFromAssets("Comfortaa-Regular", 17, 0, 0.0f, 1.0f);
        this.comfortaa5 = UnicodeFontRenderer.getFontFromAssets("Comfortaa-Regular", 15, 0, 0.0f, 1.0f);
        this.comfortaaGamster = UnicodeFontRenderer.getFontFromAssets("Comfortaa-Regular", 80, 0, 0.0f, 1.0f);
        this.roboto = UnicodeFontRenderer3.getFontFromAssets("Roboto-Thin", 70, 0, 0.0f, 1.0f);
        this.robotoPanel = UnicodeFontRenderer3.getFontFromAssets("Roboto-Light", 18, 0, 0.0f, 1.0f);
        this.robotoBold = UnicodeFontRenderer3.getFontFromAssets("Roboto-Light", 23, 0, 0.0f, 1.0f);
        this.roboto2 = UnicodeFontRenderer3.getFontFromAssets("Roboto-Thin", 25, 0, 0.0f, 1.0f);
        this.robotoTabguiName = UnicodeFontRenderer3.getFontFromAssets("Roboto-Thin", 50, 0, 0.0f, 1.0f);
        this.robotoBlockCount = UnicodeFontRenderer3.getFontFromAssets("Roboto-Thin", 18, 0, 0.0f, 1.0f);
        this.sigma = UnicodeFontRenderer4.getFontFromAssets("Geomanist-Regular", 20, 0, 0.0f, 1.0f);
        this.sigma2 = UnicodeFontRenderer4.getFontFromAssets("Geomanist-Regular", 30, 0, 0.0f, 1.0f);
        this.sigma3 = UnicodeFontRenderer4.getFontFromAssets("Geomanist-Regular", 16, 0, 0.0f, 1.0f);
        this.jelloClickguiPanelTop = UnicodeFontRenderer5.getFontFromAssets("jelloregular", 25, 0, 0.0f, 1.0f);
        this.jelloClickguiSettings = UnicodeFontRenderer5.getFontFromAssets("jelloregular", 15, 0, 0.0f, 1.0f);
        this.jelloClickguiPanelBottom = UnicodeFontRenderer5.getFontFromAssets("jelloregular", 20, 0, 0.0f, 1.0f);
        this.jelloTabGUI = UnicodeFontRenderer5.getFontFromAssets("jellolight", 45, 0, 0.0f, 1.0f);
        this.jelloTabGUIBottom = UnicodeFontRenderer5.getFontFromAssets("jellolight", 20, 0, 0.0f, 1.0f);
        this.novoline = UnicodeFontRenderer6.getFontOnPC("Arial", 18, 0, 0.0f, 1.0f);
        this.novlineBigger = UnicodeFontRenderer6.getFontOnPC("Arial", 22, 0, 0.0f, 1.0f);
        this.novolineSmall = UnicodeFontRenderer6.getFontOnPC("Arial", 15, 0, 0.0f, 1.0f);
        this.verdana2 = UnicodeFontRenderer7.getFontFromAssets("verdana", 16, 0, 0.0f, 1.0f);
        this.esp = UnicodeFontRenderer8.getFontFromAssets("esp", 55, 0, 0.0f, 1.0f);
        this.tenacity = UnicodeFontRenderer9.getFontFromAssets("tenacity", 40, 0, 0.0f, 1.0f);
        this.tenacityBig = UnicodeFontRenderer9.getFontFromAssets("tenacity", 22, 0, 0.0f, 1.0f);
        this.tenacityNormal = UnicodeFontRenderer9.getFontFromAssets("tenacity", 20, 0, 0.0f, 1.0f);
        this.tenacitySmall = UnicodeFontRenderer9.getFontFromAssets("tenacity", 15, 0, 0.0f, 1.0f);
    }
    
    public void shutdown() {
        this.fileUtil.saveKeys();
        this.fileUtil.saveModules();
        new Config("settings.txt", false, true).saveCurrent();
    }
    
    public void onEvent(final Event e) {
        try {
            if (Minecraft.getMinecraft().thePlayer == null) {
                return;
            }
            for (final Module mod : Aqua.moduleManager.modules) {
                if (!mod.isToggled()) {
                    continue;
                }
                if (e == null || mod == null) {
                    return;
                }
                mod.onEvent(e);
            }
        }
        catch (Exception ex) {}
    }
    
    public String[] addToStringArray(final String[] oldArray, final String newString) {
        final String[] newArray = Arrays.copyOf(oldArray, oldArray.length + 1);
        newArray[oldArray.length] = newString;
        return newArray;
    }
    
    static {
        Aqua.discordRPC = new DiscordRPC();
        Aqua.dev = "LCA_MODZ";
        Aqua.name = "Aqua";
        Aqua.build = "12";
        Aqua.allowed = true;
        Aqua.setmgr = new SettingsManager();
    }
}
