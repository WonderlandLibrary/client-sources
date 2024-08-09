package src.Wiksi;

import com.google.common.eventbus.EventBus;
import src.Wiksi.command.*;
import src.Wiksi.command.friends.FriendStorage;
import src.Wiksi.command.impl.*;
import src.Wiksi.command.impl.feature.*;
import src.Wiksi.command.staffs.StaffStorage;
import src.Wiksi.config.ConfigStorage;
import src.Wiksi.events.EventKey;
import src.Wiksi.functions.api.Function;
import src.Wiksi.functions.api.FunctionRegistry;
import src.Wiksi.functions.api.NaksonPaster;
import src.Wiksi.scripts.client.ScriptManager;
import src.Wiksi.ui.NotificationManager;
import src.Wiksi.ui.ab.factory.ItemFactory;
import src.Wiksi.ui.ab.factory.ItemFactoryImpl;
import src.Wiksi.ui.ab.logic.ActivationLogic;
import src.Wiksi.ui.ab.model.IItem;
import src.Wiksi.ui.ab.model.ItemStorage;
import src.Wiksi.ui.autobuy.AutoBuyConfig;
import src.Wiksi.ui.autobuy.AutoBuyHandler;
import src.Wiksi.ui.dropdown.DropDown;
import src.Wiksi.ui.mainmenu.AltConfig;
import src.Wiksi.ui.mainmenu.AltWidget;
import src.Wiksi.ui.styles.Style;
import src.Wiksi.ui.styles.StyleFactory;
import src.Wiksi.ui.styles.StyleFactoryImpl;
import src.Wiksi.ui.styles.StyleManager;
import src.Wiksi.utils.TPSCalc;

import src.Wiksi.utils.client.ServerTPS;
import src.Wiksi.utils.drag.DragManager;
import src.Wiksi.utils.drag.Dragging;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
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
public class Wiksi {

    public static UserData userData;
    public static FontRenderer[] FontLight;
    public boolean playerOnServer = false;
    public static final String CLIENT_NAME = "Wiksi";

    // Экземпляр Wiksi
    @Getter
    private static Wiksi instance;

    // Менеджеры
    private FunctionRegistry functionRegistry;
    private ConfigStorage configStorage;
    private CommandDispatcher commandDispatcher;
    private ServerTPS serverTPS;
    private MacroManager macroManager;
    private StyleManager styleManager;

    // Менеджер событий и скриптов
    private final EventBus eventBus = new EventBus();
    private final ScriptManager scriptManager = new ScriptManager();
    // Директории
    private final File clientDir = new File(Minecraft.getInstance().gameDir + "\\Wiksi\\configs");
    private final File filesDir = new File(Minecraft.getInstance().gameDir + "\\Wiksi\\files");

    // Элементы интерфейса
    private AltWidget altWidget;
    private AltConfig altConfig;
    private DropDown dropDown;
    private Window autoBuyUI;
    public static long initTime;

    // Конфигурация и обработчики
    private AutoBuyConfig autoBuyConfig = new AutoBuyConfig();
    private AutoBuyHandler autoBuyHandler;
    private ViaMCP viaMCP;
    private TPSCalc tpsCalc;
    private ActivationLogic activationLogic;
    private ItemStorage itemStorage;
    private DragManager dragManager;

    public Wiksi() {
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
        NaksonPaster.NOTIFICATION_MANAGER = new NotificationManager();
        userData = new UserData("aqqxk", 1);
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
        //autoBuyUI = new AutoBuyUI(new StringTextComponent("A"));
        autoBuyHandler = new AutoBuyHandler();
        autoBuyConfig = new AutoBuyConfig();
        eventBus.register(this);
        initTime = System.currentTimeMillis();
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
        StyleFactoryImpl styleFactory = new StyleFactoryImpl();
        ArrayList<Style> styles = new ArrayList<Style>();
        styles.add(styleFactory.createStyle("\u041c\u043e\u0440\u0441\u043a\u043e\u0439", new Color(5, 63, 111), new Color(133, 183, 246)));
        styles.add(styleFactory.createStyle("\u0421\u0432\u0435\u0442\u043b\u043e-\u0441\u0438\u043d\u0438\u0439", new Color(224, 255, 255), new Color(224, 255, 255)));
        styles.add(styleFactory.createStyle("\u041d\u0435\u0436\u043d\u044b\u0439", new Color(185, 255, 247), new Color(185, 255, 247)));
        styles.add(styleFactory.createStyle("\u0413\u0440\u0430\u043d\u0430\u0442\u043e\u0432\u044b\u0439", new Color(109, 10, 40), new Color(239, 96, 136)));
        styles.add(styleFactory.createStyle("\u0427\u0435\u0440\u043d\u0438\u0447\u043d\u044b\u0439", new Color(78, 5, 127), new Color(193, 140, 234)));
        styles.add(styleFactory.createStyle("\u041d\u0435\u043e\u0431\u044b\u0447\u043d\u044b\u0439", new Color(243, 160, 232), new Color(171, 250, 243)));
        styles.add(styleFactory.createStyle("\u041e\u0433\u043d\u0435\u043d\u043d\u044b\u0439", new Color(194, 21, 0), new Color(255, 197, 0)));
        styles.add(styleFactory.createStyle("\u041c\u0435\u0442\u0430\u043b\u043b\u0438\u0447\u0435\u0441\u043a\u0438\u0439", new Color(40, 39, 39), new Color(178, 178, 178)));
        styles.add(styleFactory.createStyle("\u041f\u0440\u0438\u043a\u043e\u043b\u044c\u043d\u044b\u0439", new Color(82, 241, 171), new Color(66, 172, 245)));
        styles.add(styleFactory.createStyle("\u0412\u0435\u0441\u0435\u043d\u043d\u0438\u0439", new Color(35, 243, 3), new Color(255, 255, 255)));
        styles.add(styleFactory.createStyle("\u0428\u0438\u043a\u0430\u0440\u043d\u044b\u0439", new Color(121, 255, 0), new Color(245, 58, 209)));
        styles.add(styleFactory.createStyle("\u0420\u043e\u0437\u043e\u0432\u044b\u0439 \u041b\u0438\u043c\u043e\u043d\u0430\u0434", new Color(248, 9, 9), new Color(255, 31, 251, 255)));
        styles.add(styleFactory.createStyle("\u041e\u0440\u0430\u043d\u044c\u0436\u0435\u0432\u043e\u0435 \u0421\u043e\u043b\u043d\u0446\u0435", new Color(239, 240, 81), new Color(239, 0, 0)));
        styles.add(styleFactory.createStyle("\u041d\u043e\u0432\u044b\u0439 \u0413\u043e\u0434", new Color(0, 151, 68), new Color(239, 0, 0)));
        styles.add(styleFactory.createStyle("\u0414\u044c\u044f\u0432\u043e\u043b", new Color(99, 36, 0), new Color(0, 0, 0)));
        styles.add(styleFactory.createStyle("\u041a\u0440\u043e\u0432\u0430\u0432\u0430\u044f \u0441\u043c\u0435\u0440\u0442\u044c", new Color(166, 36, 0), new Color(0, 0, 0)));
        styles.add(styleFactory.createStyle("\u0421\u043b\u0435\u0433\u043a\u0430 \u043a\u0440\u0430\u0441\u043d\u044b\u0439 \u043b\u0438\u043c\u043e\u043d", new Color(166, 12, 0), new Color(200, 213, 123)));
        styles.add(styleFactory.createStyle("\u0417\u043e\u043b\u043e\u0442\u043e", new Color(255, 204, 0), new Color(0, 0, 0)));
        styles.add(styleFactory.createStyle("\u042f\u0431\u043b\u043e\u043a\u043e \u041c\u0430\u043d\u0433\u043e", new Color(255, 102, 20), new Color(102, 255, 102)));
        styles.add(styleFactory.createStyle("\u0412\u0438\u0448\u043d\u0451\u0432\u044b\u0439 \u041b\u0438\u043c\u043e\u043d\u0430\u0434", new Color(255, 102, 203), new Color(166, 36, 0)));
        styles.add(styleFactory.createStyle("\u041b\u0435\u0434\u0435\u043d\u043e\u0439 \u041b\u0438\u043c\u043e\u043d\u0430\u0434", new Color(102, 255, 255), new Color(255, 102, 204)));
        styles.add(styleFactory.createStyle("\u0427\u0435\u0440\u043d\u0438\u043a\u0430 \u042f\u0431\u043b\u043e\u043a\u043e", new Color(78, 5, 127), new Color(36, 255, 0)));
        styles.add(styleFactory.createStyle("\u0427\u0435\u0440\u043d\u0438\u0447\u043d\u043e\u0435 \u0412\u0438\u043d\u043e", new Color(78, 5, 127), new Color(79, 18, 56)));
        styles.add(styleFactory.createStyle("\u0413\u043e\u043b\u0443\u0431\u043e\u0432\u0430\u0442\u043e \u0427\u0435\u0440\u043d\u0438\u0447\u043d\u044b\u0439", new Color(6, 89, 122), new Color(79, 18, 56)));
        styles.add(styleFactory.createStyle("\u041f\u043e\u043b\u0438\u0446\u0438\u044f", new Color(230, 7, 7), new Color(79, 7, 234)));
        styles.add(styleFactory.createStyle("\u041f\u0440\u0438\u043a\u043e\u043b\u044c\u043d\u0430\u044f", new Color(5, 8, 102), new Color(250, 8, 234)));
        styles.add(styleFactory.createStyle("\u0422\u043e\u043a\u0441\u0438\u0447\u043d\u0430\u044f", new Color(20, 232, 52), new Color(55, 99, 79)));
        styles.add(styleFactory.createStyle("\u041c\u044f\u0442\u0430", new Color(102, 255, 203), new Color(155, 255, 155)));
        styles.add(styleFactory.createStyle("\u041c\u044f\u0442\u043d\u043e\u0435 \u041c\u043e\u0440\u043e\u0436\u0435\u043d\u043e\u0435", new Color(102, 255, 102), new Color(102, 255, 255)));
        styles.add(styleFactory.createStyle("\u0422\u0440\u0430\u0432\u0430 \u0432 \u043a\u0440\u043e\u0432\u0438", new Color(67, 81, 52), new Color(251, 79, 111)));
        styles.add(styleFactory.createStyle("\u0427\u0435\u0440\u043d\u044b\u0439 \u043f\u0435\u043d\u0438\u0441", new Color(0, 0, 0), new Color(0, 0, 0)));
        styles.add(styleFactory.createStyle("\u041d\u043e\u0432\u043e\u0433\u043e\u0434\u043d\u044f\u044f", new Color(255, 0, 0), new Color(255, 255, 255)));
        styles.add(styleFactory.createStyle("\u041a\u0440\u043e\u0432\u0430\u0432\u0430\u044f \u041a\u0430\u043a\u0430\u0448\u043a\u0430", new Color(109, 10, 40), new Color(129, 76, 6)));
        styles.add(styleFactory.createStyle("\u043d\u043e\u0440\u043c\u0430\u043b\u044c\u043d\u0430\u044f", new Color(255, 0, 0), new Color(124, 255, 255)));
        styles.add(styleFactory.createStyle("\u043d\u043e\u0440\u043c\u0430\u043b\u044c\u043d\u0430\u044f2", new Color(137, 99, 255), new Color(0, 25, 115)));
        Color[] astolfoColors = new Color[]{new Color(255, 204, 0), new Color(255, 102, 204), new Color(102, 255, 255), new Color(255, 153, 0), new Color(102, 255, 102), new Color(255, 102, 102), new Color(0, 40, 134), new Color(55, 104, 123), new Color(255, 148, 137), new Color(79, 7, 0), new Color(137, 19, 5), new Color(119, 99, 255), new Color(79, 2, 118), new Color(79, 7, 75)};
        String themeName = "Двухцветная";
        for (int i = 0; i < astolfoColors.length; ++i) {
            styles.add(styleFactory.createStyle(themeName + " " + (i + 1), astolfoColors[i], astolfoColors[(i + 1) % astolfoColors.length]));
        }
        this.styleManager = new StyleManager(styles, (Style)styles.get(0));
    }
    public float round() {
        return 2F;
    }

    public float shadow() {
        return 8F;
    }
    @Getter
    @Setter
    @AllArgsConstructor
    public static class UserData {
        final String user;
        final int uid;
    }
}
