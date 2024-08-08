package me.zeroeightsix.kami;

import com.google.common.base.Converter;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import me.zero.alpine.EventBus;
import me.zero.alpine.EventManager;
import me.zeroeightsix.kami.command.Command;
import me.zeroeightsix.kami.command.CommandManager;
import me.zeroeightsix.kami.event.ForgeEventProcessor;
import me.zeroeightsix.kami.gui.kami.KamiGUI;
import me.zeroeightsix.kami.gui.rgui.component.AlignedComponent;
import me.zeroeightsix.kami.gui.rgui.component.Component;
import me.zeroeightsix.kami.gui.rgui.component.container.use.Frame;
import me.zeroeightsix.kami.gui.rgui.util.ContainerHelper;
import me.zeroeightsix.kami.gui.rgui.util.Docking;
import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.module.ModuleManager;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;
import me.zeroeightsix.kami.setting.SettingsRegister;
import me.zeroeightsix.kami.setting.config.Configuration;
import me.zeroeightsix.kami.util.*;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

/**
 * Created by 086 on 7/11/2017.
 * Updated 1 December 2019 by hub
 */
@Mod(modid = KamiMod.MODID, name = KamiMod.MODNAME, version = KamiMod.MODVER)
public class KamiMod {

    public static final String MODID = "hephaestus";
    public static final String MODNAME = "Hephaestus";

    // Version Number -> major.minor.patch
    // major = e.g. big client architecture improvement milestone
    // minor = new module / feature
    // patch = bugfix or refactor
    public static final String MODVER = "0.3";

    public static final String NAME_UNICODE = "VikNet";

    public static final Logger LOGGER = LogManager.getLogger("Hephaestus");
    public static final EventBus EVENT_BUS = new EventManager();

    private static final String CONFIG_NAME_DEFAULT = "Hephaestus_Config.json";

    @Mod.Instance
    private static KamiMod INSTANCE;

    public GuiManager guiManager;
    public KamiGUI kamiGUI;
    public CommandManager commandManager;
    public CapeManager capeManager;

    private Setting<JsonObject> guiStateSetting = Settings.custom("gui", new JsonObject(), new Converter<JsonObject, JsonObject>() {
        @Override
        protected JsonObject doForward(JsonObject jsonObject) {
            return jsonObject;
        }

        @Override
        protected JsonObject doBackward(JsonObject jsonObject) {
            return jsonObject;
        }
    }).buildAndRegister("");

    // whatever this is, we should clean it up lmao
    public static String getConfigName() {
        Path config = Paths.get("Hephaestus_LastConfig.txt");
        String kamiConfigName = CONFIG_NAME_DEFAULT;
        try (BufferedReader reader = Files.newBufferedReader(config)) {
            kamiConfigName = reader.readLine();
            if (!isFilenameValid(kamiConfigName)) {
                kamiConfigName = CONFIG_NAME_DEFAULT;
            }
        } catch (NoSuchFileException e) {
            try (BufferedWriter writer = Files.newBufferedWriter(config)) {
                writer.write(CONFIG_NAME_DEFAULT);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } catch (IOException e) {
            //e.printStackTrace();
        }
        return kamiConfigName;
    }

    public static void loadConfiguration() {
        try {
            loadConfigurationUnsafe();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void loadConfigurationUnsafe() throws IOException {
        String kamiConfigName = getConfigName();
        Path kamiConfig = Paths.get(kamiConfigName);
        if (!Files.exists(kamiConfig)) {
            return;
        }
        Configuration.loadConfiguration(kamiConfig);

        JsonObject gui = KamiMod.INSTANCE.guiStateSetting.getValue();
        for (Map.Entry<String, JsonElement> entry : gui.entrySet()) {
            Optional<Component> optional = KamiMod.INSTANCE.kamiGUI.getChildren().stream().filter(component -> component instanceof Frame).filter(component -> ((Frame) component).getTitle().equals(entry.getKey())).findFirst();
            if (optional.isPresent()) {
                JsonObject object = entry.getValue().getAsJsonObject();
                Frame frame = (Frame) optional.get();
                frame.setX(object.get("x").getAsInt());
                frame.setY(object.get("y").getAsInt());
                Docking docking = Docking.values()[object.get("docking").getAsInt()];
                if (docking.isLeft()) {
                    ContainerHelper.setAlignment(frame, AlignedComponent.Alignment.LEFT);
                } else if (docking.isRight()) {
                    ContainerHelper.setAlignment(frame, AlignedComponent.Alignment.RIGHT);
                } else if
                (docking.isCenterVertical()) {
                    ContainerHelper.setAlignment(frame, AlignedComponent.Alignment.CENTER);
                }
                frame.setDocking(docking);
                frame.setMinimized(object.get("minimized").getAsBoolean());
                frame.setPinned(object.get("pinned").getAsBoolean());
            } else {
                System.err.println("Found GUI config entry for " + entry.getKey() + ", but found no frame with that name");
            }
        }
        KamiMod.getInstance().getKamiGUI().getChildren().stream().filter(component -> (component instanceof Frame) && (((Frame) component).isPinneable()) && component.isVisible()).forEach(component -> component.setOpacity(0f));
    }

    public static void saveConfiguration() {
        try {
            saveConfigurationUnsafe();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveConfigurationUnsafe() throws IOException {
        JsonObject object = new JsonObject();
        KamiMod.INSTANCE.kamiGUI.getChildren().stream().filter(component -> component instanceof Frame).map(component -> (Frame) component).forEach(frame -> {
            JsonObject frameObject = new JsonObject();
            frameObject.add("x", new JsonPrimitive(frame.getX()));
            frameObject.add("y", new JsonPrimitive(frame.getY()));
            frameObject.add("docking", new JsonPrimitive(Arrays.asList(Docking.values()).indexOf(frame.getDocking())));
            frameObject.add("minimized", new JsonPrimitive(frame.isMinimized()));
            frameObject.add("pinned", new JsonPrimitive(frame.isPinned()));
            object.add(frame.getTitle(), frameObject);
        });
        KamiMod.INSTANCE.guiStateSetting.setValue(object);

        Path outputFile = Paths.get(getConfigName());
        if (!Files.exists(outputFile)) {
            Files.createFile(outputFile);
        }
        Configuration.saveConfiguration(outputFile);
        ModuleManager.getModules().forEach(Module::destroy);
    }

    public static boolean isFilenameValid(String file) {
        File f = new File(file);
        try {
            f.getCanonicalPath();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public static KamiMod getInstance() {
        return INSTANCE;
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {

    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        KamiMod.LOGGER.info("\n\nInitializing Hephaestus " + MODVER);

        ReflectionHelper.init();

        ModuleManager.initialize();

        ModuleManager.getModules().stream().filter(module -> module.alwaysListening).forEach(EVENT_BUS::subscribe);
        MinecraftForge.EVENT_BUS.register(new ForgeEventProcessor());
        LagCompensator.INSTANCE = new LagCompensator();

        Wrapper.init();

        guiManager = new GuiManager();

        kamiGUI = new KamiGUI();
        kamiGUI.initializeGUI();

        commandManager = new CommandManager();

        capeManager = new CapeManager();
        capeManager.initializeCapes();

        Friends.initFriends();
        SettingsRegister.register("commandPrefix", Command.commandPrefix);
        loadConfiguration();
        KamiMod.LOGGER.info("Settings loaded");

        ModuleManager.updateLookup(); // generate the lookup table after settings are loaded to make custom module names work

        // Hard override to disable certain modules
        if (ModuleManager.getModuleByName("FakePlayer").isEnabled()) {
            ModuleManager.getModuleByName("FakePlayer").disable();
        }

        // After settings loaded, we want to let the enabled modules know they've been enabled (since the setting is done through reflection)
        ModuleManager.getModules().stream().filter(Module::isEnabled).forEach(Module::enable);

        // Hard override to enable certain modules
        if (ModuleManager.getModuleByName("GUI").isDisabled()) {
            ModuleManager.getModuleByName("GUI").enable();
        }

        KamiMod.LOGGER.info(MODNAME + " initialized!\n");

    }

    public KamiGUI getKamiGUI() {
        return kamiGUI;
    }

    public CommandManager getCommandManager() {
        return commandManager;
    }

}
