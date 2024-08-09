package im.expensive;

import com.google.common.eventbus.EventBus;
import com.jagrosh.discordipc.IPCClient;
import com.jagrosh.discordipc.IPCListener;
import com.jagrosh.discordipc.entities.Packet;
import com.jagrosh.discordipc.entities.RichPresence;
import com.jagrosh.discordipc.entities.pipe.PipeStatus;
import com.jagrosh.discordipc.exceptions.NoDiscordClientException;
import im.expensive.command.*;
import im.expensive.command.friends.FriendStorage;
import im.expensive.command.impl.*;
import im.expensive.command.impl.feature.*;
import im.expensive.command.staffs.StaffStorage;
import im.expensive.config.ConfigStorage;
import im.expensive.discord.DiscordLogger;
import im.expensive.events.EventKey;
import im.expensive.functions.api.Function;
import im.expensive.functions.api.FunctionRegistry;
import im.expensive.functions.impl.combat.killAura.rotation.RotationHandler;
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
import im.expensive.ui.midnight.ClickGui;
import im.expensive.ui.styles.Style;
import im.expensive.ui.styles.StyleFactory;
import im.expensive.ui.styles.StyleFactoryImpl;
import im.expensive.ui.styles.StyleManager;
import im.expensive.utils.TPSCalc;
import im.expensive.utils.client.ServerTPS;
import im.expensive.utils.drag.DragManager;
import im.expensive.utils.drag.Dragging;
import im.expensive.utils.font.Fonts;
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
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Я просто делаю свой клиент, я свободный человек, пасщю что хочу
 * Газ (А-а)
 * Бро, я напастил авто тотем уже в три лет
 * Не ври, что ты не пастер, это не писаный клиент
 * Деус — модель, уши размером с живот макавто
 * Ты нервничаешь, бесишь сам себя, когда сурсов нет
 * У, у, у, у, у, я больших мазиков съедатель (А-а)
 * Больших уши уничтожитель, мазикавыпиватель (А-а)
 * Пастирских читов игратель, тебя выключатель (У-у)
 * Белый, но во мне краситель, к пастингу привыкатель (Е-е)
 * Бро, это shit skidding, я им рассказыватель (отвечаю)
 * Большие бидоны мазика — силой слова я их поедатель (У)
 * У меня большой живот — я его показыватель
 * Бро реал скидет, зовёт ся falok(Fals3r)
 * Эй, чит восьми из десяти mc.player — говно, я таким сру утром
 * Четыре бегина делят дерьмо — это на двух клиентах
 * Мой любимый кодер стал пастером — уже не авто тотем макслоло
 * Мазик рушит стены моего желудка
 * Она долго запускаться, как будто лоудер говно
 * Кодеры купаются в мазике, бля, они срут в коде
 * У меня есть шкильники качки, да, они жрут мазик
 * Они сразу поломают кисть, они не жмут руку
 * Ты пастишь реже ,чем dedinside — тя там не видно (Ха)
 * Я сейчас official, иногда ворую с клиентов (У-у)
 * Бро откинулся в Актобе — щас он летит к нам (У-у)
 * Кто этот ебаный Пастер, чё он нам пиздит там? (У-у)
 * Я могу потыкать ему этим Кодом прям в бошку (Гр-ра)
 * Подлетай к моему аирДропу — я залутаю его (Ха, макавто)
 * Я могу пастить даже спиной к монику, ты не сможешь так (Не сможешь)
 * Пока мне делали glow, у тебя пастили под носом, е, а-а
 * [Аутро]
 * У, у, у, у, у, я больших мазиков съедатель
 * Больших уши уничтожитель, мазикавыпиватель
 * Пастирских читов игратель, тебя выключатель
 * Давай спастим wintware, короче
 */

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Expensive {

    public static UserData userData;
    public boolean playerOnServer = false;
    public static final String CLIENT_NAME = "expensive solutions";

    // Экземпляр Expensive
    @Getter
    private static Expensive instance;

    // Менеджеры
    private FunctionRegistry functionRegistry;
    private ConfigStorage configStorage;
    private CommandDispatcher commandDispatcher;
    private ServerTPS serverTPS;
    private MacroManager macroManager;
    private StyleManager styleManager;

    // Менеджер событий и скриптов
    private final EventBus eventBus = new EventBus();

    // Директории
    private final File clientDir = new File(Minecraft.getInstance().gameDir + "\\expensive");
    private final File filesDir = new File(Minecraft.getInstance().gameDir + "\\expensive\\files");

    // Элементы интерфейса
    private AltWidget altWidget;
    private AltConfig altConfig;
    private ClickGui dropDown;
    private Window autoBuyUI;

    // Конфигурация и обработчики
    private AutoBuyConfig autoBuyConfig = new AutoBuyConfig();
    private AutoBuyHandler autoBuyHandler;
    private ViaMCP viaMCP;
    private TPSCalc tpsCalc;
    private ActivationLogic activationLogic;
    private ItemStorage itemStorage;
    private RotationHandler rotationHandler;

    public Expensive() {
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


    final IPCClient client = new IPCClient(1236549348909776957L);

    public void startRPC() {

        client.setListener(new IPCListener() {
            @Override
            public void onPacketReceived(IPCClient client, Packet packet) {
                IPCListener.super.onPacketReceived(client, packet);
            }

            @Override
            public void onReady(IPCClient client) {
                UserData userData = Expensive.userData;
                RichPresence.Builder builder = new RichPresence.Builder();
                builder
                        .setDetails("User: " + userData.getUser())
                        .setState("UID: " + userData.getUid())
                        .setStartTimestamp(OffsetDateTime.now())
                        .setLargeImage("avatar", "Let's remember the well-forgotten old");
                client.sendRichPresence(builder.build());
            }
        });
        try {
            client.connect();
        } catch (NoDiscordClientException e) {
            System.out.println("DiscordRPC: " + e.getMessage());
        }
    }

    public void stopRPC() {
        if (client.getStatus() == PipeStatus.CONNECTED) client.close();
    }

    public Dragging createDrag(Function module, String name, float x, float y) {
        DragManager.draggables.put(name, new Dragging(module, name, x, y));
        return DragManager.draggables.get(name);
    }
    private void clientLoad() {
        functionRegistry = new FunctionRegistry();
        viaMCP = new ViaMCP();
        serverTPS = new ServerTPS();
        macroManager = new MacroManager();
        configStorage = new ConfigStorage();
        initCommands();
        initStyles();
        altWidget = new AltWidget();
        altConfig = new AltConfig();
        tpsCalc = new TPSCalc();
        rotationHandler = new RotationHandler();


        userData = new UserData("src Fixed by krakazybra", 1);


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
        dropDown = new ClickGui();
        initAutoBuy();
        autoBuyUI = new Window(new StringTextComponent(""), itemStorage);
        //autoBuyUI = new AutoBuyUI(new StringTextComponent("A"));
        autoBuyHandler = new AutoBuyHandler();
        autoBuyConfig = new AutoBuyConfig();
        Fonts.init();
        eventBus.register(this);
    }


    public void onKeyPressed(int key) {
        if (functionRegistry.getSelfDestruct().enabled) return;
        Expensive.getInstance().getEventBus().post(new EventKey(key));

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
        commands.add(new WayCommand(prefix, logger));
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
        styles.add(styleFactory.createStyle("Морской", new Color(5, 63, 111), new Color(133, 183, 246)));
        styles.add(styleFactory.createStyle("Малиновый", new Color(109, 10, 40), new Color(239, 96, 136)));
        styles.add(styleFactory.createStyle("Черничный", new Color(78, 5, 127), new Color(193, 140, 234)));
        styles.add(styleFactory.createStyle("Необычный", new Color(243, 160, 232), new Color(171, 250, 243)));
        styles.add(styleFactory.createStyle("Огненный", new Color(194, 21, 0), new Color(255, 197, 0)));
        styles.add(styleFactory.createStyle("Металлический", new Color(40, 39, 39), new Color(178, 178, 178)));
        styles.add(styleFactory.createStyle("Прикольный", new Color(82, 241, 171), new Color(66, 172, 245)));
        styles.add(styleFactory.createStyle("Новогодний", new Color(190, 5, 60), new Color(255, 255, 255)));
        styles.add(styleFactory.createStyle("Кастомный", new Color(255, 255, 255), new Color(255, 255, 255)));
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
