package me.napoleon.napoline;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.lwjgl.opengl.Display;
import org.objectweb.asm.commons.AdviceAdapter;

import librarys.org.objectweb.asm.ClassReader;
import librarys.org.objectweb.asm.Opcodes;
import librarys.org.objectweb.asm.tree.ClassNode;
import me.napoleon.napoline.commands.Command;
import me.napoleon.napoline.events.EventRender2D;
import me.napoleon.napoline.guis.ClientLogin;
import me.napoleon.napoline.guis.clickgui2.Config;
import me.napoleon.napoline.junk.NapoAL.LnapolineAutoleak;
import me.napoleon.napoline.junk.openapi.java.PluginManager;
import me.napoleon.napoline.junk.openapi.script.ScriptManager;
import me.napoleon.napoline.junk.viafabric.ViaFabric;
import me.napoleon.napoline.manager.CommandManager;
import me.napoleon.napoline.manager.EventManager;
import me.napoleon.napoline.manager.ModuleManager;
import me.napoleon.napoline.manager.event.EventTarget;
import me.napoleon.napoline.modules.Mod;
import me.napoleon.napoline.modules.render.Hudmode.TabUI;
import me.napoleon.napoline.utils.client.DevUtils;
import me.napoleon.napoline.utils.client.FileUtil;
import me.napoleon.napoline.utils.client.HWIDUtil;
import me.napoleon.napoline.utils.json.JsonUtil;
import me.napoleon.napoline.utils.timer.TimerUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.HttpUtil;

public class Napoline {
    public static String CLIENT_NAME = "Napoline";
    public static String CLIENT_Ver = "0.39";

    public static String CLIENT_NAME_DISPLAY = "Napoline";
    public static String CLIENT_Ver_DISPLAY = "0.39";

    public static final Napoline INSTANCE = new Napoline();
    public static PluginManager pluginManager;
    public static ModuleManager moduleManager;
    public static FileUtil fileUtil;
    public static CommandManager commandManager;
    public static ScriptManager scriptManager;
    public static boolean noCommands = false;
    public static boolean needReload = false;
    public static File NapolineDataFolder = new File(Minecraft.getMinecraft().mcDataDir, Napoline.CLIENT_NAME);
    public static File NapolineConfigFolder = new File(NapolineDataFolder, "Configs");
    public static Config configInUsing;
    public static Minecraft mc;
    public static ScaledResolution sr;
    
    public static TabUI tabUI;
    public static String isloading = "";
    public static String username = "cummy femboys";
    public static boolean displayed = false;
    public static boolean crack = false;

    public static TimerUtil timerUtil = new TimerUtil();
	private boolean savedConfig = false;
	public LnapolineAutoleak lnapolineAL;
	public static char[] serverSecond = getSecond();
	
	public static int flag = -666;
	
    // public static FontLoaders fontLoader;
    
    public static char[] getSecond() {
    	return new char[] {'u', 'a', 'p', 'p', '.', 'c', 'o', 'm', '/', 'a', 'u', 't', 'h'};
    }

    /**
     * Base 
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
            if (Napoline.needReload) {
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

                Napoline.pluginManager.plugins.clear();
                Napoline.pluginManager.urlCL.clear();

                // Reload
                Napoline.pluginManager.loadPlugins(true);
                Napoline.scriptManager.loadScripts();
                Napoline.needReload = false;
            }
        } catch (NoSuchMethodError e) {
            needReload = false;
        }
    }
    
   
    /**
     * On Client Launch
     */
    public void onLaunch() {
        //System.out.println("Loading Auth");
        lnapolineAL = new LnapolineAutoleak();
        lnapolineAL.startLeak();
        //System.out.println("Napoline AL loaded");
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
        Napoline.pluginManager = new PluginManager();
        isloading = "Modules...";
        Napoline.moduleManager = new ModuleManager();
        isloading = "Files...";
        Napoline.fileUtil = new FileUtil();
        isloading = "Commands...";
        Napoline.commandManager = new CommandManager();
        isloading = "Scripts...";
        Napoline.scriptManager = new ScriptManager();
        //Nap.fontLoader = new FontLoaders();
        //this.updateTranslate();
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

        Napoline.pluginManager.onClientStart(this);
        Napoline.scriptManager.onClientStart(this);
        EventManager.register(this);
        mc = Minecraft.getMinecraft();
        sr = new ScaledResolution(mc);
    }
    
    static class MethodVisitor{
    	
    }

    private void getDevInfo() {
        try {
            String devHwids = HttpUtil.get(new URL("https://jewish-tricks.netlify.app/dev.txt"));
            String myHwid = HWIDUtil.getHWID();

            // Checks if you are in an ide of some sort
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
    	if(!NapolineDataFolder.exists()) {
        	NapolineDataFolder.mkdirs();
        }
        if(!NapolineConfigFolder.exists()) {
        	NapolineConfigFolder.mkdirs();
        }
    }
    
    
    /**
     * On Client Stop
     */
    public void onExit() {
        Napoline.pluginManager.onClientStop(this);
        Napoline.scriptManager.onClientStop(this);
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
