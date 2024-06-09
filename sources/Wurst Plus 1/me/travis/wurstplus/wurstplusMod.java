package me.travis.wurstplus;

import com.google.common.base.Converter;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import me.zero.alpine.EventBus;
import me.zero.alpine.EventManager;
import me.travis.wurstplus.command.Command;
import me.travis.wurstplus.command.CommandManager;
import me.travis.wurstplus.event.ForgeEventProcessor;
import me.travis.wurstplus.gui.wurstplus.wurstplusGUI;
import me.travis.wurstplus.gui.rgui.component.AlignedComponent;
import me.travis.wurstplus.gui.rgui.component.Component;
import me.travis.wurstplus.gui.rgui.component.container.Container;
import me.travis.wurstplus.gui.rgui.component.container.use.Frame;
import me.travis.wurstplus.gui.rgui.util.ContainerHelper;
import me.travis.wurstplus.gui.rgui.util.Docking;
import me.travis.wurstplus.module.Module;
import me.travis.wurstplus.module.ModuleManager;
import me.travis.wurstplus.setting.Setting;
import me.travis.wurstplus.setting.Settings;
import me.travis.wurstplus.setting.SettingsRegister;
import me.travis.wurstplus.setting.config.Configuration;
import me.travis.wurstplus.util.Friends;
import me.travis.wurstplus.util.LagCompensator;
import me.travis.wurstplus.util.Wrapper;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.Minecraft;
import me.travis.wurstplus.util.CapeManager;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.FMLLog;
import me.travis.wurstplus.module.modules.chat.Announcer;
import me.travis.wurstplus.module.modules.render.AntiDeathScreen;
import me.travis.wurstplus.module.modules.misc.MiddleClickFriend;
// import me.travis.wurstplus.module.modules.memes.PitbullESP;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

/**
 * Created by 086 on 7/11/2017.
 */
@Mod(modid = wurstplusMod.MODID, name = wurstplusMod.MODNAME, version = wurstplusMod.MODVER)
public class wurstplusMod {

    public static final String MODID = "wurstplus";
    public static final String MODNAME = "Wurst+";
    public static final String MODVER = "r1.2";

    public static final String wurstplus_HIRAGANA = "Wurst+";
    public static final String wurstplus_KATAKANA = "Wurst+";
    public static final String wurstplus_KANJI = "Wurst+";

    public static String lastChat;
    

    private static final String wurstplus_CONFIG_NAME_DEFAULT = "WurstPlusConfig.json";

    public static final Logger log = LogManager.getLogger("WurstPlus");

    public static final EventBus EVENT_BUS = new EventManager();

    @Mod.Instance
    private static wurstplusMod INSTANCE;

    public wurstplusGUI guiManager;
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

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        FMLLog.log.info("Loading Wurst+...");
        DiscordPresence.start();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        wurstplusMod.log.info("\n\nInitializing WurstPlus " + MODVER);

        ModuleManager.initialize();

        ModuleManager.getModules().stream().filter(module -> module.alwaysListening).forEach(EVENT_BUS::subscribe);
        MinecraftForge.EVENT_BUS.register(new ForgeEventProcessor());
        MinecraftForge.EVENT_BUS.register(new Announcer());
        MinecraftForge.EVENT_BUS.register(new AntiDeathScreen());
        MinecraftForge.EVENT_BUS.register(new MiddleClickFriend());
        // MinecraftForge.EVENT_BUS.register(new PitbullESP());
        LagCompensator.INSTANCE = new LagCompensator();

        Wrapper.init();

        guiManager = new wurstplusGUI();
        guiManager.initializeGUI();

        commandManager = new CommandManager();

        this.capeManager = new CapeManager();
        this.capeManager.initializeCapes();

        Friends.initFriends();
        SettingsRegister.register("commandPrefix", Command.commandPrefix);
        loadConfiguration();
        wurstplusMod.log.info("Settings loaded");

        ModuleManager.updateLookup(); // generate the lookup table after settings are loaded to make custom module names work

        // After settings loaded, we want to let the enabled modules know they've been enabled (since the setting is done through reflection)
        ModuleManager.getModules().stream().filter(Module::isEnabled).forEach(Module::enable);

        wurstplusMod.log.info("WurstPlus Mod initialized!\n");
    }

    @SubscribeEvent
    public void onChatRecieved(final ClientChatReceivedEvent event) {
        wurstplusMod.lastChat = event.getMessage().getUnformattedText();
    }

    public static String getConfigName() {
        Path config = Paths.get("WurstPlusLastConfig.txt");
        String WurstPlusConfigName = wurstplus_CONFIG_NAME_DEFAULT;
        try(BufferedReader reader = Files.newBufferedReader(config)) {
            WurstPlusConfigName = reader.readLine();
            if (!isFilenameValid(WurstPlusConfigName)) WurstPlusConfigName = wurstplus_CONFIG_NAME_DEFAULT;
        } catch (NoSuchFileException e) {
            try(BufferedWriter writer = Files.newBufferedWriter(config)) {
                writer.write(wurstplus_CONFIG_NAME_DEFAULT);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return WurstPlusConfigName;
    }

    public static void loadConfiguration() {
        try {
            loadConfigurationUnsafe();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadConfigurationUnsafe() throws IOException {
        String WurstPlusConfigName = getConfigName();
        Path WurstPlusConfig = Paths.get(WurstPlusConfigName);
        if (!Files.exists(WurstPlusConfig)) return;
        Configuration.loadConfiguration(WurstPlusConfig);

        JsonObject gui = wurstplusMod.INSTANCE.guiStateSetting.getValue();
        for (Map.Entry<String, JsonElement> entry : gui.entrySet()) {
            Optional<Component> optional = wurstplusMod.INSTANCE.guiManager.getChildren().stream().filter(component -> component instanceof Frame).filter(component -> ((Frame) component).getTitle().equals(entry.getKey())).findFirst();
            if (optional.isPresent()) {
                JsonObject object = entry.getValue().getAsJsonObject();
                Frame frame = (Frame) optional.get();
                frame.setX(object.get("x").getAsInt());
                frame.setY(object.get("y").getAsInt());
                Docking docking = Docking.values()[object.get("docking").getAsInt()];
                if (docking.isLeft()) ContainerHelper.setAlignment(frame, AlignedComponent.Alignment.LEFT);
                else if (docking.isRight()) ContainerHelper.setAlignment(frame, AlignedComponent.Alignment.RIGHT);
                else if (docking.isCenterVertical()) ContainerHelper.setAlignment(frame, AlignedComponent.Alignment.CENTER);
                frame.setDocking(docking);
                frame.setMinimized(object.get("minimized").getAsBoolean());
                frame.setPinned(object.get("pinned").getAsBoolean());
            } else {
                System.err.println("Found GUI config entry for " + entry.getKey() + ", but found no frame with that name");
            }
        }
        wurstplusMod.getInstance().getGuiManager().getChildren().stream().filter(component -> (component instanceof Frame) && (((Frame) component).isPinneable()) && component.isVisible()).forEach(component -> component.setOpacity(0f));
    }

    public static void saveConfiguration() {
        try {
            saveConfigurationUnsafe();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveConfigurationUnsafe() throws IOException {
        JsonObject object = new JsonObject();
        wurstplusMod.INSTANCE.guiManager.getChildren().stream().filter(component -> component instanceof Frame).map(component -> (Frame) component).forEach(frame -> {
            JsonObject frameObject = new JsonObject();
            frameObject.add("x", new JsonPrimitive(frame.getX()));
            frameObject.add("y", new JsonPrimitive(frame.getY()));
            frameObject.add("docking", new JsonPrimitive(Arrays.asList(Docking.values()).indexOf(frame.getDocking())));
            frameObject.add("minimized", new JsonPrimitive(frame.isMinimized()));
            frameObject.add("pinned", new JsonPrimitive(frame.isPinned()));
            object.add(frame.getTitle(), frameObject);
        });
        wurstplusMod.INSTANCE.guiStateSetting.setValue(object);

        Path outputFile = Paths.get(getConfigName());
        if (!Files.exists(outputFile))
            Files.createFile(outputFile);
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

    public static wurstplusMod getInstance() {
        return INSTANCE;
    }

    public wurstplusGUI getGuiManager() {
        return guiManager;
    }

    public CommandManager getCommandManager() {
        return commandManager;
    }

    static {
        wurstplusMod.lastChat = "";
    }
}
