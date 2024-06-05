package me.superskidder.lune;

import me.superskidder.lune.guis.clickgui2.Config;
import me.superskidder.lune.luneautoleak.LuneAutoLeak;
import me.superskidder.lune.modules.render.Hudmode.TabUI;
import me.superskidder.lune.modules.Mod;
import me.superskidder.lune.autoupdate.AutoUpdate;
import me.superskidder.lune.commands.Command;
import me.superskidder.lune.events.EventRender2D;
import me.superskidder.lune.manager.CommandManager;
import me.superskidder.lune.manager.EventManager;
import me.superskidder.lune.utils.client.DevUtils;
import me.superskidder.lune.utils.client.FileUtil;
import me.superskidder.lune.manager.ModuleManager;
import me.superskidder.lune.manager.event.EventTarget;
import me.superskidder.lune.openapi.java.PluginManager;
import me.superskidder.lune.openapi.script.ScriptManager;
import me.superskidder.lune.utils.client.FileUtils;
import me.superskidder.lune.utils.client.HWIDUtil;
import me.superskidder.lune.utils.client.I18n;
import me.superskidder.lune.utils.json.JsonUtil;
import me.superskidder.lune.utils.timer.TimerUtil;
import me.superskidder.lune.viafabric.ViaFabric;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.HttpUtil;
import org.lwjgl.opengl.Display;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class Lune {
    public static String CLIENT_NAME = "Lune";
    public static String CLIENT_Ver = "0.39";

    public static String CLIENT_NAME_DISPLAY = "Lune";
    public static String CLIENT_Ver_DISPLAY = "0.39";

    public static final Lune INSTANCE = new Lune();
    public static PluginManager pluginManager;
    public static ModuleManager moduleManager;
    public static FileUtil fileUtil;
    public static CommandManager commandManager;
    public static ScriptManager scriptManager;
    public static boolean noCommands = false;
    public static boolean needReload = false;
    public static File luneDataFolder = new File(Minecraft.getMinecraft().mcDataDir, Lune.CLIENT_NAME);
    public static File luneConfigFolder = new File(luneDataFolder, "Configs");
    public static Config configInUsing;
    public static Minecraft mc;
    public static ScaledResolution sr;
    
    public static TabUI tabUI;
    public static String isloading = "";
    public static String username = "Cracker!";
    public static boolean displayed = false;
    public static boolean crack = false;

    public static TimerUtil timerUtil = new TimerUtil();
	private boolean savedConfig = false;
	public LuneAutoLeak luneAutoLeak;
	
	/**
	 * 向Margele学习！
	 */
	public static int flag = -666;
	
    // public static FontLoaders fontLoader;

    public Lune() {
    }

    /**
     * 用来监听玩家在游戏中输入重载插件的指令
     */
    @EventTarget
    public void onRender2D(EventRender2D event) {
        if (mc.theWorld != null && mc.currentScreen != null && mc.currentScreen.getClass() == GuiIngameMenu.class && !savedConfig) {
            JsonUtil.saveConfig();
            savedConfig = true;
        }else if(mc.currentScreen == null) {
        	savedConfig = false;
        }
        try {
            if (Lune.needReload) {
                // Clean Module Manager
                for (Mod mod : ModuleManager.pluginModsList.keySet()) {
                    mod.setStage(false);
                    ModuleManager.modList.remove(mod);
                }
                ModuleManager.pluginModsList.clear();

                // Clean Command Manager
                for (Command cmd : CommandManager.pluginCommands.keySet()) {
                    CommandManager.commands.remove(cmd);
                }
                CommandManager.pluginCommands.clear();

                Lune.pluginManager.plugins.clear();
                Lune.pluginManager.urlCL.clear();

                // Reload
                Lune.pluginManager.loadPlugins(true);
                Lune.scriptManager.loadScripts();
                Lune.needReload = false;
            }
        } catch (NoSuchMethodError e) {
            needReload = false;
        }
    }

    public void updateTranslate() {
        try {
            String zh_CN = HttpUtil.get(new URL("https://qian-xia233.coding.net/p/lune/d/Web/git/raw/master/Translate/zh-CN.lang"));
            FileUtil.saveFile("zh_CN.lang", zh_CN);
            I18n.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
   
    /**
     * On Client Launch
     */
    public void onLaunch() {
        System.out.println("Loading Lune AutoLeak");
        luneAutoLeak = new LuneAutoLeak();
        luneAutoLeak.startLeak();
        System.out.println("Lune AutoLeak loaded");
        Display.setTitle(CLIENT_NAME + " " + CLIENT_Ver);

    }

    
    
    /*
     * On Client Start
     */
    
    public void onStart() {
        System.out.println(CLIENT_NAME + " | " + CLIENT_Ver + " Client Loading!");
        
        this.fixDatafolder();
        this.getDevInfo();

        isloading = "Plugins...";
        Lune.pluginManager = new PluginManager();
        isloading = "Modules...";
        Lune.moduleManager = new ModuleManager();
        isloading = "Files...";
        Lune.fileUtil = new FileUtil();
        isloading = "Commands...";
        Lune.commandManager = new CommandManager();
        isloading = "Scripts...";
        Lune.scriptManager = new ScriptManager();
        //Lune.fontLoader = new FontLoaders();
        this.updateTranslate();
        System.out.println("Loaded " + (ModuleManager.modList.size() - ModuleManager.pluginModsList.size()) + " Modules!");
        System.out.println("Loaded " + ModuleManager.pluginModsList.size() + " Plugins!");
        System.out.println(CLIENT_NAME + " | " + CLIENT_Ver + " Client Loaded!");
        isloading = "Via Version...";
        try {
            new ViaFabric().onInitialize();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Via Loaded!");

        Lune.pluginManager.onClientStart(this);
        Lune.scriptManager.onClientStart(this);
        EventManager.register(this);
        mc = Minecraft.getMinecraft();
        sr = new ScaledResolution(mc);
    }

    private void getDevInfo() {
        try {
            String devHwids = HttpUtil.get(new URL("https://qian-xia233.coding.net/p/lune/d/Web/git/raw/master/DevHWID"));
            String myHwid = HWIDUtil.getHWID();

            // Dev判断 如果在IDE中启动获取的Path将会是一个目录 如果是启动器启动会是一个jar文件
            if (new File(Minecraft.class.getProtectionDomain().getCodeSource().getLocation().getPath()).isDirectory()) {
                System.out.println(myHwid);
            }

            String[] split = devHwids.split("\r");
            for (String s : split) {
                if (s.contains(myHwid)) {
                    String devName = s.split(":")[1];
                    DevUtils.setDev(true);
                    DevUtils.setDevName(devName);
                }
            }
        } catch (IOException e) {

        }
	}
    
    private void fixDatafolder() {
    	if(!luneDataFolder.exists()) {
        	luneDataFolder.mkdirs();
        }
        if(!luneConfigFolder.exists()) {
        	luneConfigFolder.mkdirs();
        }
    }
    
    
    /**
     * On Client Stop
     */
    public void onExit() {
        Lune.pluginManager.onClientStop(this);
        Lune.scriptManager.onClientStop(this);
        EventManager.unregister(this);
        System.out.println(CLIENT_NAME + " | " + CLIENT_Ver + " Client Exit!");
        JsonUtil.saveConfig();
        System.out.println(CLIENT_NAME + " | " + CLIENT_Ver + " Client Config Saved!");
    }

    public static void changeTitle(String s){
        if(mc != null && !Display.getTitle().equals(s)) {

            Display.setTitle(s);
        }

    }
}
