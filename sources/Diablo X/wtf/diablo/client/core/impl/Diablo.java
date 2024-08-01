package wtf.diablo.client.core.impl;

import best.azura.eventbus.core.EventBus;
import by.radioegor146.nativeobfuscator.Native;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.ViaAPI;
import de.florianmichael.viamcp.ViaMCP;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.lwjgl.opengl.Display;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import wtf.diablo.auth.DiabloAPI;
import wtf.diablo.auth.DiabloRank;
import wtf.diablo.auth.DiabloSession;
import wtf.diablo.client.command.api.management.CommandRepository;
import wtf.diablo.client.command.impl.*;
import wtf.diablo.client.config.ConfigManager;
import wtf.diablo.client.connect.ConnectionHandler;
import wtf.diablo.client.core.api.BuildTypeEnum;
import wtf.diablo.client.core.api.ClientInformation;
import wtf.diablo.client.discord.DiscordController;
import wtf.diablo.client.friend.FriendRepository;
import wtf.diablo.client.gui.draggable.DraggableElementHandler;
import wtf.diablo.client.listener.ClientListener;
import wtf.diablo.client.module.api.management.IModule;
import wtf.diablo.client.module.api.management.repository.ModuleRepository;
import wtf.diablo.client.module.impl.combat.*;
import wtf.diablo.client.module.impl.combat.criticals.CriticalsModule;
import wtf.diablo.client.module.impl.combat.killaura.KillAuraModule;
import wtf.diablo.client.module.impl.exploit.disabler.DisablerModule;
import wtf.diablo.client.module.impl.misc.*;
import wtf.diablo.client.module.impl.misc.sessioninfo.SessionInfoModule;
import wtf.diablo.client.module.impl.movement.FlightModule;
import wtf.diablo.client.module.impl.movement.LongJumpModule;
import wtf.diablo.client.module.impl.movement.SprintModule;
import wtf.diablo.client.module.impl.movement.StrafeModule;
import wtf.diablo.client.module.impl.movement.speed.SpeedModule;
import wtf.diablo.client.module.impl.movement.velocity.VelocityModule;
import wtf.diablo.client.module.impl.player.*;
import wtf.diablo.client.module.impl.player.nofall.NoFallModule;
import wtf.diablo.client.module.impl.player.noslow.NoSlowdownModule;
import wtf.diablo.client.module.impl.player.scaffoldrecode.ScaffoldRecodeModule;
import wtf.diablo.client.module.impl.render.*;
import wtf.diablo.client.notification.Notification;
import wtf.diablo.client.notification.NotificationManager;
import wtf.diablo.client.notification.NotificationType;
import wtf.diablo.client.setting.api.AbstractSetting;
import wtf.diablo.client.setting.impl.ModeSetting;
import wtf.diablo.client.setting.impl.MultiModeSetting;
import wtf.diablo.client.util.Constants;
import wtf.diablo.hwid.HwidUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Native
public final class Diablo {
    private static final Diablo INSTANCE = new Diablo();
    private static final Logger LOGGER = LoggerFactory.getLogger(Diablo.class);

    private final DraggableElementHandler draggableElementHandler;
    private final NotificationManager notificationManager;
    private final ClientInformation clientInformation;
    private final CommandRepository commandRepository;
    private final DiscordController discordController;
    private final ModuleRepository moduleRepository;
    private final ConfigManager configManager;
    private final FriendRepository friendRepository;
    private final ConnectionHandler connectionHandler;

   // private final ScriptRepository scriptRepository;

    private final EventBus eventBus;

    private final File mainDirectory, configDirectory, scriptingDirectory;

    private final DiabloSession diabloSession;

    private Diablo() {
        //System.setProperty("sessionID", System.getenv("sessionID")); //manually set when in dev mode

        final String sessionID = System.getProperty("sessionID");
        this.diabloSession = new DiabloSession(sessionID);
        this.diabloSession.update();

        if (diabloSession.getHwid() == null) {
            throw new RuntimeException("HWID is null, exiting...");
        }

        if (diabloSession.getUsername() == null) {
            throw new RuntimeException("Username is null, exiting...");
        }

        if (diabloSession.getRank() == null) {
           throw new RuntimeException("Rank is null, exiting...");
        }

        if (diabloSession.getToken() == null) {
            throw new RuntimeException("Token is null, exiting...");
        }

        System.out.println("HWID: " + diabloSession.getHwid());
        System.out.println("Username: " + diabloSession.getUsername());
        System.out.println("Rank: " + diabloSession.getRank());
        System.out.println("Token: " + diabloSession.getToken());

        System.out.println("Current HWID: " + HwidUtil.generateHwid());

        final HttpResponse<String> contentResponse = Unirest.get("https://diablo.wtf/api/v1/auth/hwid/isSet")
                .header("Authorization", this.diabloSession.getToken())
                .asString();

        if (contentResponse.getStatus() != 200) {
            throw new RuntimeException("Failed to get content response, exiting...");
        }

        final JsonObject contentBody = Constants.GSON.fromJson(contentResponse.getBody(), JsonObject.class);
        final boolean hwidSet = contentBody.get("isSet").getAsBoolean();

        if (!hwidSet) {
            final JsonObject hwid = new JsonObject();
            hwid.addProperty("hwid", HwidUtil.generateHwid());

            final HttpResponse<String> hwidResponse = Unirest.post("https://diablo.wtf/api/v1/auth/hwid/set")
                    .header("Authorization", this.diabloSession.getToken())
                    .header("Content-Type", "application/json")
                    .body(Constants.GSON.toJson(hwid))
                    .asString();

            if (hwidResponse.getStatus() != 200) {
                throw new RuntimeException("Failed to set HWID, exiting...");
            }
        }

        if (!diabloSession.getHwid().equals(HwidUtil.generateHwid())) {
            throw new RuntimeException("HWID mismatch, exiting...");
        }

        this.mainDirectory = new File("Diablo");
        this.configDirectory = new File(this.mainDirectory, "config");
        this.scriptingDirectory = new File(this.mainDirectory, "scripts");
        this.friendRepository = new FriendRepository(this.scriptingDirectory);

        this.moduleRepository = ModuleRepository.builder()
                .addModule(HudModule.class)
                .addModule(FlightModule.class)
                .addModule(SprintModule.class)
                .addModule(ClickGuiModule.class)
                .addModule(NoFallModule.class)
                .addModule(SpeedModule.class)
                .addModule(ColorModule.class)
                .addModule(KillAuraModule.class)
                .addModule(DisablerModule.class)
                .addModule(AutoHypixelModule.class)
                .addModule(CriticalsModule.class)
                .addModule(VelocityModule.class)
                .addModule(AutoPotionModule.class)
                .addModule(AutoWeaponModule.class)
                .addModule(ChestStealerModule.class)
                .addModule(AnimationsModule.class)
                .addModule(FullBrightModule.class)
                .addModule(TimeChangerModule.class)
                .addModule(FakePlayerModule.class)
                .addModule(ChamsModule.class)
                .addModule(ChestChamsModule.class)
                .addModule(ArrayListModule.class)
                .addModule(GlintModule.class)
                .addModule(TargetStrafeModule.class)
                .addModule(RadarModule.class)
                .addModule(TracersModule.class)
                .addModule(NoSlowdownModule.class)
                .addModule(TargetHUDModule.class)
                .addModule(NotificationsModule.class)
                .addModule(KeepSprintModule.class)
                .addModule(SpotifyPlayerModule.class)
                .addModule(IRCModule.class)
                .addModule(CustomCapeModule.class)
                .addModule(SessionInfoModule.class)
                .addModule(ItemOffsetModule.class)
                .addModule(LightningDetectorModule.class)
                .addModule(AutoToolModule.class)
                .addModule(LongJumpModule.class)
                .addModule(ESPModule.class)
                .addModule(AutoDisableModule.class)
                .addModule(EntityCullingModule.class)
                .addModule(CompassModule.class)
                .addModule(CrosshairModule.class)
                .addModule(WTapModule.class)
                .addModule(AntiVoidModule.class)
                .addModule(TrailModule.class)
                .addModule(SkeletonESPModule.class)
                .addModule(OutlineModule.class)
                .addModule(BlinkModule.class)
                .addModule(NoRotateModule.class)
                .addModule(ScaffoldRecodeModule.class)
                .addModule(StreamerModeModule.class)
                .addModule(CustomSkyModule.class)
                .addModule(RevealModule.class)
                .addModule(TimerModule.class)
                .addModule(MiddleClickFriendModule.class)
                .addModule(ChatSpammerModule.class)
                .addModule(FastBreakModule.class)
                .addModule(InventoryMoveModule.class)
                .addModule(StepModule.class)
                .addModule(BedBreakerModule.class)
                .addModule(FastPlaceModule.class)
                .addModule(StrafeModule.class)
                .addModule(EffectsModule.class)
                .addModule(InventoryManagerModule.class)
                .addModule(FreecamModule.class)
                .addModule(PhaseModule.class)
                .addModule(OffArrowsModule.class)
                .addModule(PlayerListModule.class)
                .build();

        this.commandRepository = CommandRepository.builder()
                .addCommand(ToggleCommand.class)
                .addCommand(BindCommand.class)
                .addCommand(OpenFolderCommand.class)
                .addCommand(ConfigCommand.class)
                .addCommand(IRCCommand.class)
                .addCommand(OnlineCommand.class)
                .addCommand(PanicCommand.class)
                .addCommand(HelpCommand.class)
                .addCommand(QuitCommand.class)
                .addCommand(FriendCommand.class)
                .addCommand(SetClientName.class)
                .build();

        this.mainDirectory.mkdirs();
        this.configDirectory.mkdirs();
        this.eventBus = new EventBus();
        this.configManager = new ConfigManager(this.configDirectory);
        this.clientInformation = new ClientInformation("Diablo X", "1.0-beta23", BuildTypeEnum.BETA);
        this.notificationManager = new NotificationManager(this.eventBus);
        this.draggableElementHandler = new DraggableElementHandler(this.eventBus);
        this.discordController = new DiscordController(1119030324874191020L, notificationManager, this.eventBus);
      //  this.scriptRepository = new ScriptRepositoryImpl(new File(mainDirectory, "scripts"));
        new ClientListener(this.eventBus);
        this.connectionHandler = new ConnectionHandler(this.eventBus);
    }

    public static Diablo getInstance() {
        return INSTANCE;
    }

    public void initialize() {
        this.moduleRepository.initialize();
        //this.configManager.initialize();

        this.discordController.initialize();
        this.friendRepository.initialize();

        this.discordController.updatePresence("Starting up...");
        this.eventBus.register(moduleRepository);
        this.eventBus.register(commandRepository);

        Display.setTitle(this.clientInformation.getName() + " - " + this.clientInformation.getBuild() + " [" + this.clientInformation.getVersion() + "]");
        Display.setResizable(true);

        final Notification authNotification = new Notification("Login successful!", "Successfully authenticated as " + this.diabloSession.getUsername() + "!", 10000L, NotificationType.SUCCESS);
        this.notificationManager.addNotification(authNotification);

        try {
            ViaMCP.create();

            ViaMCP.INSTANCE.initAsyncSlider(5, 5,110, 20); // For custom position and size slider
        } catch (Exception e) {
            e.printStackTrace();
        }
        //performFeatureCopy();
    }

    public void shutdown() {
        this.configManager.shutdown();
    }

    public ClientInformation getClientInformation() {
        return this.clientInformation;
    }

    public BuildTypeEnum getBuildType() {
        return this.clientInformation.getVersion();
    }

    public ModuleRepository getModuleRepository() {
        return this.moduleRepository;
    }

    public EventBus getEventBus() {
        return this.eventBus;
    }

    public DraggableElementHandler getDraggableElementHandler() {
        return this.draggableElementHandler;
    }

    public NotificationManager getNotificationManager() {
        return this.notificationManager;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public File getMainDirectory() {
        return mainDirectory;
    }

    public DiabloSession getDiabloSession() {
        return diabloSession;
    }

    public CommandRepository getCommandRepository() {
        return commandRepository;
    }

    public FriendRepository getFriendRepository() {
        return friendRepository;
    }

    @Native
    public static boolean verifyDiabloSession(final DiabloSession diabloSession) {
        final String token = diabloSession.getToken();
        final String hwid = diabloSession.getHwid();
        final String username = diabloSession.getUsername();
        final DiabloRank rank = diabloSession.getRank();
        final int uid = diabloSession.getUid();

        final String cachedToken = System.getProperty("sessionID");

        final HttpResponse<String> response = Unirest.get(DiabloAPI.USER_ENDPOINT)
                .header("Authorization", "Bearer " + cachedToken)
                .header("HWID", hwid)
                .asString();

        final JsonObject jsonObject = new JsonParser().parse(response.getBody()).getAsJsonObject();

        if (jsonObject.has("error")) {
            return false;
        }

        final String cachedUsername = jsonObject.get("username").getAsString();

        if (!cachedUsername.equals(username)) {
            return false;
        }

        final String cachedRank = jsonObject.get("rank").getAsString();

        if (!cachedRank.equals(rank.name())) {
            return false;
        }

        final int cachedUid = jsonObject.get("uid").getAsInt();
        if (cachedUid != uid) {
            return false;
        }

        final String cachedHwid = jsonObject.get("hwid").getAsString();
        if (!cachedHwid.equals(hwid)) {
            return false;
        }

        final String cachedSessionID = jsonObject.get("sessionID").getAsString();

        if (!cachedSessionID.equals(token)) {
            return false;
        }

        System.setProperty("sessionID", cachedSessionID);

        return true;
    }

   /* public ScriptRepository getScriptRepository() {
        return scriptRepository;
    } */

    public File getScriptingDirectory() {
        return scriptingDirectory;
    }
}