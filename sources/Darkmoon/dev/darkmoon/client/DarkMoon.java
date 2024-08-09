package dev.darkmoon.client;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;
import de.florianmichael.viamcp.ViaMCP;
import dev.darkmoon.client.command.CommandManager;
import dev.darkmoon.client.event.input.EventInputKey;
import dev.darkmoon.client.event.input.EventMouse;
import dev.darkmoon.client.event.render.EventRender2D;
import dev.darkmoon.client.manager.config.ConfigManager;
import dev.darkmoon.client.manager.friend.FriendManager;
import dev.darkmoon.client.manager.macro.MacroManager;
import dev.darkmoon.client.manager.proxy.ProxyManager;
import dev.darkmoon.client.manager.staff.StaffManager;
import dev.darkmoon.client.manager.theme.ThemeManager;
import dev.darkmoon.client.module.Module;
import dev.darkmoon.client.module.ModuleManager;
import dev.darkmoon.client.module.impl.movement.Timer;
import dev.darkmoon.client.module.impl.render.Hud;
import dev.darkmoon.client.module.impl.util.DiscordRPC;
import dev.darkmoon.client.ui.csgui.CsGui;
import dev.darkmoon.client.ui.menu.altmanager.alt.AltFileManager;
import dev.darkmoon.client.ui.menu.main.GuiMainMenuElement;
import dev.darkmoon.client.ui.menu.proxy.GuiProxy;
import dev.darkmoon.client.manager.dragging.DragManager;
import dev.darkmoon.client.manager.lastAccount.LastAccountManager;
import dev.darkmoon.client.manager.quickjoin.ServerManager;
import dev.darkmoon.client.utility.math.CapeRenderer;
import dev.darkmoon.client.utility.math.ScaleMath;
import dev.darkmoon.client.utility.misc.DiscordPresence;
import dev.darkmoon.client.utility.render.ColorUtility;
import dev.darkmoon.client.utility.render.ColorUtility2;
import dev.darkmoon.client.utility.render.RenderUtility;
import dev.darkmoon.client.utility.render.animation.AnimationMath;
import dev.darkmoon.client.utility.render.font.Fonts;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.util.math.MathHelper;

import java.awt.*;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Getter
@Setter
public class DarkMoon {
    public static String NAME = "DarkMoon";
    public static String VERSION = "1.0";
    public static String BUILD_TYPE = "Release";
    public static String EDITION = "1.12.2 Edition";

    @Getter
    private static final DarkMoon instance = new DarkMoon();

    private final ScaleMath scaleMath = new ScaleMath(2);

    private ModuleManager moduleManager;
    private final ConfigManager configManager = new ConfigManager();
    private ThemeManager themeManager;
    private CommandManager commandManager;
    private AltFileManager altFileManager;
    private LastAccountManager lastAccountManager;
    private ServerManager serverManager;
    private FriendManager friendManager;
    private StaffManager staffManager;
    private MacroManager macroManager;
    private ProxyManager proxyManager;
    private DragManager dragManager;

    private CsGui csGui;

    private UserInfo userInfo;
    private GuiProxy guiProxy;

    public void start() {
        userInfo = new UserInfo("!deadsouls.", 1, "Admin");
        themeManager = new ThemeManager();
        moduleManager = new ModuleManager();
        commandManager = new CommandManager();
        if (!getInstance().getModuleManager().getModule(DiscordRPC.class).isEnabled()) {
            getInstance().getModuleManager().getModule(DiscordRPC.class).setToggled(true);
        }
        try {
            configManager.loadConfig("autocfg");
            altFileManager = new AltFileManager();
            altFileManager.init();
            lastAccountManager = new LastAccountManager();
            lastAccountManager.init();
            serverManager = new ServerManager();
            serverManager.init();
            dragManager = new DragManager();
            dragManager.init();
            friendManager = new FriendManager();
            friendManager.init();
            staffManager = new StaffManager();
            staffManager.init();
            macroManager = new MacroManager();
            macroManager.init();
            proxyManager = new ProxyManager();
            proxyManager.init();
        } catch (Exception ignored) {
            ignored.printStackTrace();
        }
        DiscordPresence.startDiscord();
        guiProxy = new GuiProxy(new GuiMultiplayer(new GuiMainMenuElement()));
        csGui = new CsGui();

        for (RenderPlayer render : Minecraft.getMinecraft().getRenderManager().getSkinMap().values()) {
            render.addLayer(new CapeRenderer(render));
        }

        ViaMCP.create();
        ViaMCP.INSTANCE.initAsyncSlider();

        EventManager.register(this);
        Runtime.getRuntime().addShutdownHook(new Thread(this::shutdown));

    }

    public void shutdown() {
        dragManager.save();
        proxyManager.save();
        altFileManager.saveAll();
        lastAccountManager.save();
        configManager.saveConfig("autocfg");
    }

    @EventTarget
    public void onInputKey(EventInputKey eventInputKey) {
        for (Module module : moduleManager.getModules()) {
            if (module.getBind() == eventInputKey.getKey()) {
                module.toggle();
            }
        }
        macroManager.onKeyPressed(eventInputKey.getKey());
    }

    @EventTarget
    public void onMouse(EventMouse eventMouse) {
        for (Module module : moduleManager.getModules()) {
            if (module.getMouseBind() == eventMouse.getButton() && eventMouse.getButton() > 2) {
                module.toggle();
            }
        }
        macroManager.onMousePressed(eventMouse.getButton());
    }
    @EventTarget
    public void onRender2D(EventRender2D event) {
        Timer timer = DarkMoon.getInstance().getModuleManager().timer;
        if (Hud.elements.get(4)) {
            float var7 = 100f / timer.timerSpeed.get();
            float var8 = Math.min(Timer.violation, var7);
            Color color11 = DarkMoon.getInstance().getThemeManager().getCurrentStyleTheme().getColors()[0];
            Color gradientColor2 = ColorUtility2.interpolateColorsBackAndForth(Hud.hueInterpolate.get() ? 15 : 4, 90, DarkMoon.getInstance().getThemeManager().getCurrentStyleTheme().getColors()[0], DarkMoon.getInstance().getThemeManager().getCurrentStyleTheme().getColors()[1], Hud.hueInterpolate.get());
            Color gradientColor3 = ColorUtility2.interpolateColorsBackAndForth(Hud.hueInterpolate.get() ? 15 : 4, 180, DarkMoon.getInstance().getThemeManager().getCurrentStyleTheme().getColors()[0], DarkMoon.getInstance().getThemeManager().getCurrentStyleTheme().getColors()[1], Hud.hueInterpolate.get());
            Color color22 = DarkMoon.getInstance().getThemeManager().getCurrentStyleTheme().getColors()[1];
            timer.animWidth = (float)MathHelper.clampedLerp(timer.animWidth, (var7 - var8) / var7, 25.0 * AnimationMath.deltaTime());
            RenderUtility.drawCircle((float)timer.timerIndicatorDraggable.getX() + 49.0f, (float)timer.timerIndicatorDraggable.getY() + 4.0f, 0.0f, timer.animWidth * 360.0f + 1.0f, 13.45f, ColorUtility.getColor(90).getRGB(), 5);
                RenderUtility.drawRoundCircle((float) timer.timerIndicatorDraggable.getX() + 49.0f, (float) timer.timerIndicatorDraggable.getY() + 4.0f, 26.0f, 25.0f, new Color(28, 28, 28, 255).getRGB());
            Fonts.mntssb16.drawCenteredString((int)Math.floor((var7 - var8) / var7 * 100.0f) + "%", (double)timer.timerIndicatorDraggable.getX() - -49.25, (double)((float)timer.timerIndicatorDraggable.getY() + 1.05f), Color.WHITE.getRGB());
        }
    }
    public static double deltaTime() {
        return Minecraft.getDebugFPS() > 0 ? 1.0 / (double)Minecraft.getDebugFPS() : 1.0;
    }
}
