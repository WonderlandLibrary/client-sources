package dev.excellent;

import com.google.common.base.MoreObjects;
import dev.excellent.api.event.EventBase;
import dev.excellent.api.event.EventBus;
import dev.excellent.api.interfaces.client.IAccess;
import dev.excellent.api.interfaces.shader.IShader;
import dev.excellent.client.command.CommandManager;
import dev.excellent.client.component.ComponentManager;
import dev.excellent.client.friend.FriendFile;
import dev.excellent.client.friend.FriendManager;
import dev.excellent.client.macros.MacrosFile;
import dev.excellent.client.macros.MacrosManager;
import dev.excellent.client.module.api.ModuleManager;
import dev.excellent.client.module.impl.misc.autobuy.manager.AutoBuyFile;
import dev.excellent.client.module.impl.misc.autobuy.manager.AutoBuyManager;
import dev.excellent.client.notification.NotificationManager;
import dev.excellent.client.rotation.FreeLookHandler;
import dev.excellent.client.rotation.RotationHandler;
import dev.excellent.client.screen.account.api.AccountFile;
import dev.excellent.client.screen.account.api.AccountManager;
import dev.excellent.client.screen.clickgui.ClickGuiScreen;
import dev.excellent.client.screen.theme.ThemeWidget;
import dev.excellent.client.staff.StaffFile;
import dev.excellent.client.staff.StaffManager;
import dev.excellent.client.target.TargetHandler;
import dev.excellent.impl.client.ClientInfo;
import dev.excellent.impl.client.Release;
import dev.excellent.impl.client.config.ConfigFile;
import dev.excellent.impl.client.config.ConfigManager;
import dev.excellent.impl.client.theme.ThemeManager;
import dev.excellent.impl.util.file.FileManager;
import dev.excellent.impl.util.server.ServerTPS;
import dev.luvbeeq.baritone.api.BaritoneAPI;
import dev.luvbeeq.discord.rpc.DiscordManager;
import dev.luvbeeq.via.ViaMCP;
import dev.waveycapes.WaveyCapesBase;
import i.gishreloaded.deadcode.api.DeadCodeProfile;
import i.gishreloaded.protection.annotation.Native;
import i.gishreloaded.protection.annotation.Protect;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import net.minecraft.util.Util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Log4j2
@Getter
public class Excellent implements IShader, IAccess {
    @Getter
    private static final Excellent inst = new Excellent();

    private final ClientInfo info = new ClientInfo(
            MoreObjects.firstNonNull(Constants.version, "undefined"),
            MoreObjects.firstNonNull(Constants.gitcommit, "undefined"),
            MoreObjects.firstNonNull(Constants.build, new SimpleDateFormat("MMMM dd, yyyy", Locale.ENGLISH)
                    .format(new Date())),
            Release.BETA
    );
    public static final String AUTODOB = "mc.funtime.su";
    @Getter
    private static final boolean DEBUG = Excellent.getInst().getInfo().getRelease().equals(Release.DEV);
    private final long initTime = System.currentTimeMillis();

    private EventBus<EventBase> eventBus;
    private ModuleManager moduleManager;
    @Setter
    private DeadCodeProfile profile;
    private AutoBuyManager autoBuyManager;

    private CommandManager commandManager;
    private ComponentManager componentManager;
    private ConfigManager configManager;
    private FriendManager friendManager;
    private StaffManager staffManager;
    private AccountManager accountManager;
    private MacrosManager macrosManager;
    private FileManager fileManager;
    private ThemeManager themeManager;
    private NotificationManager notificationManager;
    private ClickGuiScreen clickGui;
    private DiscordManager discordManager;
    private ServerTPS serverTPS;
    private ViaMCP viaMCP;
    private WaveyCapesBase waveyCapesBase;

    @Protect(Protect.Type.ULTRA)
    @Native
    public void init() {
        log.info(String.format("%s -> initializing.", getInfo().getName()));
        profile = DeadCodeProfile.create();

        fileManager = new FileManager();
        fileManager.init();

        eventBus = new EventBus<>();

        new TargetHandler();
        new FreeLookHandler();
        new RotationHandler();

        componentManager = new ComponentManager();
        componentManager.init();

        moduleManager = new ModuleManager();
        moduleManager.init();

        commandManager = new CommandManager();
        commandManager.init();


        themeManager = new ThemeManager();

        notificationManager = new NotificationManager();
        notificationManager.init();

        configManager = new ConfigManager();
        configManager.init();

        friendManager = new FriendManager();
        friendManager.init();

        staffManager = new StaffManager();
        staffManager.init();

        accountManager = new AccountManager();
        accountManager.init();

        macrosManager = new MacrosManager();
        macrosManager.init();

        autoBuyManager = new AutoBuyManager();
        autoBuyManager.init();

        waveyCapesBase = new WaveyCapesBase();
        waveyCapesBase.init();

        config();
        friends();
        staffs();
        macros();
        accounts();
        autobuy();

        clickGui = new ClickGuiScreen();

        themeWidget = new ThemeWidget();

        serverTPS = new ServerTPS();
        viaMCP = new ViaMCP();

        discordManager = new DiscordManager();
        discordManager.init();

        BaritoneAPI.init();
        BaritoneAPI.getProvider().getPrimaryBaritone();

        shaderInit();

        log.info(String.format("%s -> initialized.", getInfo().getName()));
    }

    private void shaderInit() {
        DEPTH_SHADER.initialize();
    }

    @Native
    public void config() {
        configManager.update();
        final ConfigFile config = configManager.get("default", true);
        if (config != null) {
            if (config.read()) configManager.set();
        } else configManager.set();
    }

    @Native
    public void friends() {
        final FriendFile friend = friendManager.get();
        if (friend != null) {
            if (friend.read()) friendManager.set();
        } else friendManager.set();
    }

    @Native
    public void staffs() {
        final StaffFile staff = staffManager.get();
        if (staff != null) {
            if (staff.read()) staffManager.set();
        } else staffManager.set();
    }

    @Native
    public void macros() {
        final MacrosFile macros = macrosManager.get();
        if (macros != null) {
            if (macros.read()) macrosManager.set();
        } else macrosManager.set();
    }

    @Native
    public void accounts() {
        final AccountFile account = accountManager.get();
        if (account != null) {
            if (account.read()) accountManager.set();
        } else accountManager.set();
    }

    @Native
    public void autobuy() {
        final AutoBuyFile autoBuyFile = autoBuyManager.get();
        if (autoBuyFile != null) {
            if (autoBuyFile.read()) autoBuyManager.set();
        } else autoBuyManager.set();
    }

    public String getFormattedExpireDate() {
        Date expireDate = profile.getExpireDate();
        if (expireDate == null) {
            return "Never";
        }
        String pattern = "dd/MM/yyyy HH:mm:ss";
        DateFormat df = new SimpleDateFormat(pattern);
        return df.format(expireDate);
    }

    public ThemeWidget themeWidget;

}
