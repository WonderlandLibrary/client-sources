package dev.star.module;

import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import dev.star.Client;
import dev.star.commands.CommandHandler;
import dev.star.commands.impl.*;
import dev.star.config.ConfigManager;
import dev.star.config.DragManager;
import dev.star.event.impl.network.PacketBlinkHandler;
import dev.star.module.api.BackgroundProcess;
import dev.star.module.api.ModuleCollection;
import dev.star.module.impl.combat.*;
import dev.star.module.impl.display.*;
import dev.star.module.impl.exploit.*;
import dev.star.module.impl.misc.*;
import dev.star.module.impl.movement.*;
import dev.star.module.impl.player.*;
import dev.star.module.impl.render.*;
import dev.star.gui.mainmenu.UserLogin;
import dev.star.gui.altmanager.GuiAltManagerScreen;
import dev.star.utils.render.Theme;
import dev.star.utils.server.PingerUtils;
import net.minecraft.client.Minecraft;
import dev.star.utils.addons.viamcp.vialoadingbase.ViaLoadingBase;
import dev.star.utils.addons.viamcp.viamcp.ViaMCP;
import store.intent.intentguard.annotation.Bootstrap;
import store.intent.intentguard.annotation.Native;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;

@Native
public class ProtectedLaunch {

    private static final HashMap<Object, Module> modules = new HashMap<>();

    @Native
    @Bootstrap
    public static void start() {

        Client.INSTANCE.setModuleCollection(new ModuleCollection());

        UserLogin userLogin = new UserLogin();

        // Combat
        modules.put(Teams.class, new Teams());
        modules.put(Velocity.class, new Velocity());
        modules.put(AutoHead.class, new AutoHead());
        modules.put(KillAura.class, new KillAura());
        modules.put(Criticals.class, new Criticals());
        modules.put(AntiFireball.class, new AntiFireball());

        // Exploit
        modules.put(Disabler.class, new Disabler());
        modules.put(AutoHypixel.class, new AutoHypixel());

        // Misc
        modules.put(MCF.class, new MCF());
        modules.put(Killsults.class, new Killsults());

        // Movement
        modules.put(Speed.class, new Speed());
        modules.put(Sprint.class, new Sprint());
        modules.put(LongJump.class, new LongJump());
        modules.put(Scaffold.class, new Scaffold());
        modules.put(InventoryMove.class, new InventoryMove());

        // Player
        modules.put(Blink.class, new Blink());
        modules.put(Timer.class, new Timer());
        modules.put(NoFall.class, new NoFall());
        modules.put(NoSlow.class, new NoSlow());
        modules.put(BedAura.class, new BedAura());
        modules.put(Freecam.class, new Freecam());
        modules.put(AutoTool.class, new AutoTool());
        modules.put(AntiVoid.class, new AntiVoid());
        modules.put(SafeWalk.class, new SafeWalk());
        modules.put(AutoArmor.class, new AutoArmor());
        modules.put(SpeedMine.class, new SpeedMine());
        modules.put(FastPlace.class, new FastPlace());
        modules.put(InvManager.class, new InvManager());
        modules.put(ChestStealer.class, new ChestStealer());

        // Render
        modules.put(ESP2D.class, new ESP2D());
        modules.put(Glint.class, new Glint());
        modules.put(Chams.class, new Chams());
        modules.put(BedESP.class, new BedESP());
        modules.put(Camera.class, new Camera());
        modules.put(Streamer.class, new Streamer());
        modules.put(Ambience.class, new Ambience());
        modules.put(NoHurtCam.class, new NoHurtCam());
        modules.put(Brightness.class, new Brightness());
        modules.put(Animations.class, new Animations());
        modules.put(DMGParticle.class, new DMGParticle());
        modules.put(ClickGUIMod.class, new ClickGUIMod());
        modules.put(ItemPhysics.class, new ItemPhysics());

        // DisPlay
        modules.put(SpotifyMod.class, new SpotifyMod());
        modules.put(Nametags.class,  new Nametags());
        modules.put(Statistics.class, new Statistics());
        modules.put(HUDMod.class, new HUDMod(userLogin));
        modules.put(ArrayListMod.class, new ArrayListMod());
        modules.put(TargetHUDMod.class, new TargetHUDMod());
        modules.put(ScoreboardMod.class, new ScoreboardMod());
        modules.put(PostProcessing.class, new PostProcessing());
        modules.put(NotificationsMod.class, new NotificationsMod());

        Theme.init();
        Client.INSTANCE.setPingerUtils(new PingerUtils());
        Client.INSTANCE.setPacketBlinkHandler(new PacketBlinkHandler());
        Client.INSTANCE.getModuleCollection().setModules(modules);

        CommandHandler commandHandler = new CommandHandler();
        commandHandler.commands.addAll(Arrays.asList(
                new FriendCommand(), new CopyNameCommand(), new BindCommand(), new UnbindCommand(),
                new SettingCommand(), new HelpCommand(), new VClipCommand(),
                new ClearBindsCommand(), new ClearConfigCommand(), new LoadCommand(),
                new ToggleCommand(),  new SaveCommand(),  new DeleteCommand()
        ));

        Client.INSTANCE.setCommandHandler(commandHandler);
        Client.INSTANCE.getEventProtocol().register(new BackgroundProcess());
        Client.INSTANCE.setConfigManager(new ConfigManager());

        ConfigManager.defaultConfig = new File(Minecraft.getMinecraft().mcDataDir + "/Star/Config.json");
        Client.INSTANCE.getConfigManager().collectConfigs();
        if (ConfigManager.defaultConfig.exists()) {
            Client.INSTANCE.getConfigManager().loadConfig(Client.INSTANCE.getConfigManager().readConfigData(ConfigManager.defaultConfig.toPath()), true);
        }

        DragManager.loadDragData();

        Client.INSTANCE.setAltManager(new GuiAltManagerScreen());

        try {
            Client.LOGGER.info("Starting ViaMCP...");
            ViaMCP.create();
            // In case you want a version slider like in the Minecraft options, you can use this code here, please choose one of those:
            ViaMCP.INSTANCE.initAsyncSlider(); // For top left aligned slider
            ViaLoadingBase.getInstance().reload(ProtocolVersion.v1_8);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SafeVarargs
    private static void addModules(Class<? extends Module>... classes) {
        for (Class<? extends Module> moduleClass : classes) {
            try {
                modules.put(moduleClass, moduleClass.newInstance());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
