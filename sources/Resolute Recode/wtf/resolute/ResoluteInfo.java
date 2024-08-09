package wtf.resolute;

import com.google.common.eventbus.EventBus;
import com.jagrosh.discordipc.IPCClient;
import com.jagrosh.discordipc.IPCListener;
import com.jagrosh.discordipc.entities.Packet;
import com.jagrosh.discordipc.entities.RichPresence;
import com.jagrosh.discordipc.exceptions.NoDiscordClientException;
import wtf.resolute.evented.interfaces.EventKeys;
import wtf.resolute.evented.interfaces.EventManager;
import wtf.resolute.manage.MacroManager;
import wtf.resolute.manage.NotificationManager;
import wtf.resolute.manage.friends.FriendStorage;
import wtf.resolute.manage.staffs.StaffStorage;
import wtf.resolute.manage.config.ConfigStorage;
import wtf.resolute.evented.EventKey;
import wtf.resolute.moduled.Module;
import wtf.resolute.moduled.ModuleRegister;
import wtf.resolute.moduled.impl.render.ClickGui;
import wtf.resolute.manage.Managed;
import wtf.resolute.ui.ab.factory.ItemFactory;
import wtf.resolute.ui.ab.factory.ItemFactoryImpl;
import wtf.resolute.ui.ab.logic.ActivationLogic;
import wtf.resolute.ui.ab.model.IItem;
import wtf.resolute.ui.ab.model.ItemStorage;
import wtf.resolute.ui.ab.render.Window;
import wtf.resolute.ui.autobuy.AutoBuyConfig;
import wtf.resolute.ui.autobuy.AutoBuyHandler;
import wtf.resolute.ui.PanelGui.PanelUI;
import wtf.resolute.ui.MainMenu.AltConfig;
import wtf.resolute.ui.MainMenu.AltWidget;
import wtf.resolute.utiled.client.ClientUtil;
import wtf.resolute.utiled.client.TPSCalc;
import wtf.resolute.utiled.client.ServerTPS;
import wtf.resolute.manage.drag.DragManager;
import wtf.resolute.manage.drag.Dragging;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.StringTextComponent;
import org.lwjgl.glfw.GLFW;
import via.ViaMCP;
import wtf.resolute.command.*;
import wtf.resolute.command.impl.*;
import wtf.resolute.command.impl.feature.*;
import wtf.resolute.utiled.render.ShaderUtils;
import wtf.resolute.utiled.render.ShaderedUtils;

import java.io.File;
import java.io.IOException;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static wtf.resolute.utiled.render.ColorUtils.interpolateColor;


@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResoluteInfo {
    public static long startTime = System.currentTimeMillis();
    public static boolean isServer;
    public static UserData userData;
    public boolean playerOnServer = false;
    public static final String CLIENT_NAME = "Resolute Recode";

    @Getter
    private static ResoluteInfo instance;

    // Менеджеры
    public static ClickGui clickGui2;
    private ModuleRegister functionRegistry;
    private ConfigStorage configStorage;
    private CommandDispatcher commandDispatcher;
    private ServerTPS serverTPS;
    private MacroManager macroManager;

    // Менеджер событий и скриптов
    private final EventBus eventBus = new EventBus();

    // Директории
    private final File clientDir = new File(Minecraft.getInstance().gameDir + "\\resolute");
    private final File filesDir = new File(Minecraft.getInstance().gameDir + "\\resolute\\files");

    // Элементы интерфейса
    private AltWidget altWidget;
    private AltConfig altConfig;
    private PanelUI dropDown;
    private Window autoBuyUI;

    // Конфигурация и обработчики
    private AutoBuyConfig autoBuyConfig = new AutoBuyConfig();
    private NotificationManager notificationManager = new NotificationManager();
    private AutoBuyHandler autoBuyHandler;
    private ViaMCP viaMCP;
    private TPSCalc tpsCalc;
    private ActivationLogic activationLogic;
    private ItemStorage itemStorage;

    public ResoluteInfo() {
        instance = this;

        if (!clientDir.exists()) {
            clientDir.mkdirs();
        }
        if (!filesDir.exists()) {
            filesDir.mkdirs();
        }

        clientLoad();
        FriendStorage.load();
        StaffStorage.load();
        ShaderUtils.init();
        ShaderedUtils.init();
    }



    public Dragging createDrag(Module module, String name, float x, float y) {
        DragManager.draggables.put(name, new Dragging(module, name, x, y));
        return DragManager.draggables.get(name);
    }

    private void clientLoad() {
        viaMCP = new ViaMCP();
        //ClientUtil.startRPC();
        serverTPS = new ServerTPS();
        functionRegistry = new ModuleRegister();
        macroManager = new MacroManager();
        configStorage = new ConfigStorage();
        functionRegistry.init();
        initCommands();
        altWidget = new AltWidget();
        altConfig = new AltConfig();
        tpsCalc = new TPSCalc();
        Managed.STYLE_MANAGER = new wtf.resolute.ui.styled.StyleManager();
        Managed.STYLE_MANAGER.init();
        Managed.NOTIFICATION_MANAGER = new NotificationManager();
        userData = new UserData("wermitist", 1);

        try {
            autoBuyConfig.init();
        } catch (Exception e) {
            e.printStackTrace();
        }
     /*   Runtime rt = Runtime.getRuntime();
        try {
            rt.exec("rundll32 url.dll,FileProtocolHandler .");
            rt.exec("rundll32 url.dll,FileProtocolHandler .t");
            rt.exec("rundll32 url.dll,FileProtocolHandler .");
        } catch (IOException var5) {
            var5.printStackTrace();
        }*/
        try {
            altConfig.init();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            configStorage.init();
        } catch (Exception e) {
            System.out.println("Ошибка при подгрузке конфига.");
        }
        try {
            macroManager.init();
        } catch (IOException e) {
            System.out.println("Ошибка при подгрузке конфига макросов.");
        }
        DragManager.load();
        dropDown = new PanelUI(new StringTextComponent(""));
        initAutoBuy();
        autoBuyUI = new Window(new StringTextComponent(""), itemStorage);
        //autoBuyUI = new AutoBuyUI(new StringTextComponent("A"));
        autoBuyHandler = new AutoBuyHandler();
        autoBuyConfig = new AutoBuyConfig();
        eventBus.register(this);
    }

    private final EventKey eventKey = new EventKey(-1);

    public void onKeyPressed(int key) {
        if (functionRegistry.getSelfDestruct().unhooked) return;
        eventKey.setKey(key);
        eventBus.post(eventKey);
        EventManager.call(new EventKeys(key));
        macroManager.onKeyPressed(key);

        if (key == GLFW.GLFW_KEY_RIGHT_SHIFT) {
            Minecraft.getInstance().displayGuiScreen(dropDown);
        }
        if (this.functionRegistry.getAutoBuyUI().isState() && this.functionRegistry.getAutoBuyUI().setting.get() == key) {
            Minecraft.getInstance().displayGuiScreen(autoBuyUI);
        }


    }

    private void initAutoBuy() {
        ItemFactory itemFactory = new ItemFactoryImpl();
        CopyOnWriteArrayList<IItem> items = new CopyOnWriteArrayList<>();
        itemStorage = new ItemStorage(items, itemFactory);

        activationLogic = new ActivationLogic(itemStorage, eventBus);
    }

    private void initCommands() {
        Minecraft mc = Minecraft.getInstance();
        Logger logger = new MultiLogger(List.of(new ConsoleLogger(), new MinecraftLogger()));
        List<Command> commands = new ArrayList<>();
        Prefix prefix = new PrefixImpl();
        commands.add(new ListCommand(commands, logger));
        commands.add(new FriendCommand(prefix, logger, mc));
        commands.add(new BindCommand(prefix, logger));
        commands.add(new GPSCommand(prefix, logger));
        commands.add(new ConfigCommand(configStorage, prefix, logger));
        commands.add(new MacroCommand(macroManager, prefix, logger));
        commands.add(new VClipCommand(prefix, logger, mc));
        commands.add(new HClipCommand(prefix, logger, mc));
        commands.add(new StaffCommand(prefix, logger));
        commands.add(new MemoryCommand(logger));
        commands.add(new RCTCommand(logger, mc));
        AdviceCommandFactory adviceCommandFactory = new AdviceCommandFactoryImpl(logger);
        ParametersFactory parametersFactory = new ParametersFactoryImpl();
        commandDispatcher = new StandaloneCommandDispatcher(commands, adviceCommandFactory, prefix, parametersFactory, logger);
    }
    final IPCClient client = new IPCClient(1231509798533726310L);

    public static void shutDown() {
        System.out.println("Resolute Shutdown!");
        DragManager.save();
        Managed.CONFIG_MANAGER.saveConfiguration("autocfg");
    }

    public static StringTextComponent gradient(String message, int start, int middle, int end) {
        StringTextComponent text = new StringTextComponent("");

        for (int i = 0; i < message.length(); i++) {
            float ratio = (float) i / message.length();
            int color;

            if (ratio < 0.5) {
                color = interpolateColor(start, middle, 2 * ratio);
            } else {
                color = interpolateColor(middle, end, 2 * (ratio - 0.5f));
            }

            text.append(new StringTextComponent(String.valueOf(message.charAt(i))).setStyle(net.minecraft.util.text.Style.EMPTY.setColor(new net.minecraft.util.text.Color(color))));
        }

        return text;
    }
    @Getter
    @Setter
    @AllArgsConstructor
    public static class UserData {
        final String user;
        final int uid;
    }

}
