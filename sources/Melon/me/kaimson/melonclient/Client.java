package me.kaimson.melonclient;

import org.apache.logging.log4j.core.*;
import java.io.*;
import me.kaimson.melonclient.discord.*;
import me.kaimson.melonclient.cosmetics.*;
import me.kaimson.melonclient.features.modules.*;
import me.kaimson.melonclient.gui.utils.blur.*;
import me.kaimson.melonclient.gui.settings.*;
import me.kaimson.melonclient.config.*;
import me.kaimson.melonclient.features.modules.utils.*;
import me.kaimson.melonclient.utils.*;
import me.kaimson.melonclient.features.*;
import me.kaimson.melonclient.gui.utils.*;
import java.util.regex.*;
import org.apache.logging.log4j.*;

public class Client
{
    public static final Client INSTANCE;
    public static final Logger LOGGER;
    public static final String minecraftVersion = "1.8.9";
    public static final File dir;
    private final ave mc;
    public static final jy LOGO;
    public static final jy BACKGROUND;
    public static final jy TRANSPARENT;
    public static CustomFontRenderer titleRenderer;
    public static CustomFontRenderer titleRenderer2;
    public static CustomFontRenderer textRenderer;
    public static final CPSUtils left;
    public static final CPSUtils right;
    
    public Client() {
        this.mc = ave.A();
    }
    
    public void onPreInit() {
        try {
            DiscordIPC.INSTANCE.init();
            ModuleManager.INSTANCE.init();
            ConfigManager.INSTANCE.loadAll();
            new Thread(CosmeticManager::init, "Cosmetic Fetcher").start();
        }
        catch (Throwable $ex) {
            throw $ex;
        }
    }
    
    public void onPostInit() {
        Client.titleRenderer = new CustomFontRenderer("Lato Bold", 16.0f);
        Client.titleRenderer2 = new CustomFontRenderer("Lato Black", 16.0f);
        Client.textRenderer = new CustomFontRenderer("Lato Light", 16.0f);
    }
    
    public void onKeyPress(final int keycode) {
        if (ave.A().w && keycode == 54) {
            ave.A().a((axu)((SettingsBase.lastWindow == null) ? new GuiModules(null) : SettingsBase.lastWindow));
        }
    }
    
    public void onRenderOverlay() {
        if (this.mc.t.aC || this.mc.m instanceof GuiHUDEditor) {
            return;
        }
        PerspectiveModule.INSTANCE.onTick();
        HotbarModule.INSTANCE.onTick();
        BlurShader.INSTANCE.onRenderTick();
        if (this.mc.m instanceof GuiModules || this.mc.m instanceof GuiSettings) {
            return;
        }
        ModuleManager.INSTANCE.modules.stream().filter(module -> ModuleConfig.INSTANCE.isEnabled(module) && module.isRender()).forEach(module -> module.render((float)BoxUtils.getBoxOffX(module, (int)ModuleConfig.INSTANCE.getActualX(module), module.getWidth()), (float)BoxUtils.getBoxOffY(module, (int)ModuleConfig.INSTANCE.getActualY(module), module.getHeight())));
        Client.left.tick();
        Client.right.tick();
    }
    
    public void onShutdown() {
        DiscordIPC.INSTANCE.shutdown();
    }
    
    public static int getMainColor(final int alpha) {
        return GuiUtils.getRGB(SettingsManager.INSTANCE.mainColor.getColor(), alpha);
    }
    
    public static void info(final Object msg, final Object... objs) {
        Client.LOGGER.info("$[Melon Client] " + msg, objs);
    }
    
    public static void debug(final Object msg, final Object... objs) {
        Client.LOGGER.debug("$[Melon Client] " + msg, objs);
    }
    
    public static void warn(final Object msg, final Object... objs) {
        Client.LOGGER.warn("$[Melon Client] " + msg, objs);
    }
    
    public static void error(final Object msg, final Object... objs) {
        Client.LOGGER.error("$[Melon Client] " + msg, objs);
    }
    
    public static Pattern getSearchPattern(final String text) {
        return Pattern.compile("\\Q" + text.replace("*", "\\E.*\\Q") + "\\E", 2);
    }
    
    static {
        INSTANCE = new Client();
        LOGGER = (Logger)LogManager.getLogger();
        dir = new File(ave.A().v, "Melon Client");
        LOGO = new jy("melonclient/logo.png");
        BACKGROUND = new jy("melonclient/bg.png");
        TRANSPARENT = new jy("melonclient/transparent.png");
        left = new CPSUtils(CPSUtils.Type.LEFT);
        right = new CPSUtils(CPSUtils.Type.RIGHT);
    }
}
