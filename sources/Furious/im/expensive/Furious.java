package im.expensive;

import com.google.common.eventbus.EventBus;
import im.expensive.command.*;
import im.expensive.command.friends.FriendStorage;
import im.expensive.command.impl.*;
import im.expensive.command.impl.feature.*;
import im.expensive.command.staffs.StaffStorage;
import im.expensive.config.ConfigStorage;
import im.expensive.events.EventKey;
import im.expensive.functions.api.Function;
import im.expensive.functions.api.FunctionRegistry;
import im.expensive.functions.api.Notifications;
import im.expensive.scripts.client.ScriptManager;
import im.expensive.ui.NotificationManager;
import im.expensive.ui.ab.factory.ItemFactory;
import im.expensive.ui.ab.factory.ItemFactoryImpl;
import im.expensive.ui.ab.logic.ActivationLogic;
import im.expensive.ui.ab.model.IItem;
import im.expensive.ui.ab.model.ItemStorage;
import im.expensive.ui.ab.render.Window;
import im.expensive.ui.autobuy.AutoBuyConfig;
import im.expensive.ui.autobuy.AutoBuyHandler;
import im.expensive.ui.dropdown.DropDown;
import im.expensive.ui.mainmenu.AltConfig;
import im.expensive.ui.mainmenu.AltWidget;
import im.expensive.ui.styles.Style;
import im.expensive.ui.styles.StyleFactory;
import im.expensive.ui.styles.StyleFactoryImpl;
import im.expensive.ui.styles.StyleManager;
import im.expensive.utils.TPSCalc;
import im.expensive.utils.client.ServerTPS;
import im.expensive.utils.drag.DragManager;
import im.expensive.utils.drag.Dragging;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.StringTextComponent;
import org.lwjgl.glfw.GLFW;
import via.ViaMCP;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Furious {

    public static UserData userData;
    public boolean playerOnServer = false;
    public static final String CLIENT_NAME = "Furious API";

    @Getter
    private static Furious instance;

    private FunctionRegistry functionRegistry;
    private ConfigStorage configStorage;
    private CommandDispatcher commandDispatcher;
    private ServerTPS serverTPS;
    private MacroManager macroManager;
    private StyleManager styleManager;

    private final EventBus eventBus = new EventBus();
    private final ScriptManager scriptManager = new ScriptManager();

    private final File clientDir = new File(Minecraft.getInstance().gameDir + "\\furious");
    private final File filesDir = new File(Minecraft.getInstance().gameDir + "\\furious\\files");

    private AltWidget altWidget;
    private AltConfig altConfig;
    private DropDown dropDown;
    private Window autoBuyUI;

    private AutoBuyConfig autoBuyConfig = new AutoBuyConfig();
    private AutoBuyHandler autoBuyHandler;
    private ViaMCP viaMCP;
    private TPSCalc tpsCalc;
    private ActivationLogic activationLogic;
    private ItemStorage itemStorage;

    public Furious() {
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
    }



    public Dragging createDrag(Function module, String name, float x, float y) {
        DragManager.draggables.put(name, new Dragging(module, name, x, y));
        return DragManager.draggables.get(name);
    }

    private void clientLoad() {
        viaMCP = new ViaMCP();
        serverTPS = new ServerTPS();
        functionRegistry = new FunctionRegistry();
        macroManager = new MacroManager();
        configStorage = new ConfigStorage();
        functionRegistry.init();
        initCommands();
        initStyles();
        altWidget = new AltWidget();
        altConfig = new AltConfig();
        tpsCalc = new TPSCalc();
        Notifications.NOTIFICATION_MANAGER = new NotificationManager();

        try {
            autoBuyConfig.init();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            altConfig.init();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            configStorage.init();
        } catch (IOException e) {
            System.out.println("Ошибка при подгрузке конфига.");
        }
        try {
            macroManager.init();
        } catch (IOException e) {
            System.out.println("Ошибка при подгрузке конфига макросов.");
        }
        DragManager.load();
        dropDown = new DropDown(new StringTextComponent(""));
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

    private void initStyles() {
        StyleFactory styleFactory = new StyleFactoryImpl();
        List<Style> styles = new ArrayList<>();

        styles.add(styleFactory.createStyle("Kiss Love", new Color(255,192,203), new Color(255, 0, 106)));
        styles.add(styleFactory.createStyle("Red Hyper", new Color(255, 255, 255), new Color(255, 0, 0)));
        styles.add(styleFactory.createStyle("Rainbow", new Color(0, 0, 255), new Color(255, 255, 0)));
        styles.add(styleFactory.createStyle("Astolfo", new Color(16, 171, 185), new Color(0, 255, 235)));
        styles.add(styleFactory.createStyle("Violet", new Color(139,0,255), new Color(146,110,174)));
        styles.add(styleFactory.createStyle("Neon", new Color(254,1,154), new Color(4,217,255)));
        styles.add(styleFactory.createStyle("SoundMan", new Color(101, 67, 33), new Color(255,255,0)));
        styles.add(styleFactory.createStyle("Optimus", new Color(119,221,231), new Color(139,0,255)));
        styles.add(styleFactory.createStyle("Dark - Viol", new Color(43,0,61), new Color(139,0,255)));
        styles.add(styleFactory.createStyle("Blue - White", new Color(0,0,255), new Color(200,200,200)));
        styles.add(styleFactory.createStyle("Royal", new Color(65,105,225), new Color(120,81,169)));
        styles.add(styleFactory.createStyle("Ruby", new Color(202,1,71), new Color(155,45,48)));
        styles.add(styleFactory.createStyle("Lavender", new Color(230,230,250), new Color(255,240,245)));
        styles.add(styleFactory.createStyle("Cream", new Color(241, 230, 207), new Color(245,255,250)));

        styleManager = new StyleManager(styles, styles.get(0));
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class UserData {
        final String user;
        final int uid;
    }

}
