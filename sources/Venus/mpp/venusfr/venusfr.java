/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr;

import com.google.common.eventbus.EventBus;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import mpp.venusfr.MacroManager;
import mpp.venusfr.command.Command;
import mpp.venusfr.command.CommandDispatcher;
import mpp.venusfr.command.friends.FriendStorage;
import mpp.venusfr.command.impl.AdviceCommandFactoryImpl;
import mpp.venusfr.command.impl.ConsoleLogger;
import mpp.venusfr.command.impl.MinecraftLogger;
import mpp.venusfr.command.impl.MultiLogger;
import mpp.venusfr.command.impl.ParametersFactoryImpl;
import mpp.venusfr.command.impl.PrefixImpl;
import mpp.venusfr.command.impl.StandaloneCommandDispatcher;
import mpp.venusfr.command.impl.feature.BindCommand;
import mpp.venusfr.command.impl.feature.ConfigCommand;
import mpp.venusfr.command.impl.feature.FriendCommand;
import mpp.venusfr.command.impl.feature.GPSCommand;
import mpp.venusfr.command.impl.feature.HClipCommand;
import mpp.venusfr.command.impl.feature.ListCommand;
import mpp.venusfr.command.impl.feature.MacroCommand;
import mpp.venusfr.command.impl.feature.MemoryCommand;
import mpp.venusfr.command.impl.feature.RCTCommand;
import mpp.venusfr.command.impl.feature.StaffCommand;
import mpp.venusfr.command.impl.feature.VClipCommand;
import mpp.venusfr.command.staffs.StaffStorage;
import mpp.venusfr.config.ConfigStorage;
import mpp.venusfr.events.EventKey;
import mpp.venusfr.functions.api.Function;
import mpp.venusfr.functions.api.FunctionRegistry;
import mpp.venusfr.scripts.client.ScriptManager;
import mpp.venusfr.ui.NotificationManager;
import mpp.venusfr.ui.ab.factory.ItemFactoryImpl;
import mpp.venusfr.ui.ab.logic.ActivationLogic;
import mpp.venusfr.ui.ab.model.IItem;
import mpp.venusfr.ui.ab.model.ItemStorage;
import mpp.venusfr.ui.autobuy.AutoBuyConfig;
import mpp.venusfr.ui.autobuy.AutoBuyHandler;
import mpp.venusfr.ui.autobuy.AutoBuyUI;
import mpp.venusfr.ui.dropdown.DropDown;
import mpp.venusfr.ui.mainmenu.AltConfig;
import mpp.venusfr.ui.mainmenu.AltWidget;
import mpp.venusfr.ui.styles.Style;
import mpp.venusfr.ui.styles.StyleFactoryImpl;
import mpp.venusfr.ui.styles.StyleManager;
import mpp.venusfr.utils.TPSCalc;
import mpp.venusfr.utils.client.DiscordRPC;
import mpp.venusfr.utils.client.ServerTPS;
import mpp.venusfr.utils.drag.DragManager;
import mpp.venusfr.utils.drag.Dragging;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.StringTextComponent;
import via.ViaMCP;

public class venusfr {
    public static UserData userData = new UserData("Venus", 1);
    public boolean playerOnServer = false;
    public static final String CLIENT_NAME = "venusfr solutions";
    private static venusfr instance;
    private FunctionRegistry functionRegistry;
    private ConfigStorage configStorage;
    private CommandDispatcher commandDispatcher;
    private ServerTPS serverTPS;
    private MacroManager macroManager;
    private StyleManager styleManager;
    private final EventBus eventBus = new EventBus();
    private final ScriptManager scriptManager = new ScriptManager();
    private final File clientDir;
    private final File filesDir;
    private AltWidget altWidget;
    private AltConfig altConfig;
    private DropDown dropDown;
    private AutoBuyUI autoBuyUI;
    private AutoBuyConfig autoBuyConfig;
    private AutoBuyHandler autoBuyHandler;
    private ViaMCP viaMCP;
    private TPSCalc tpsCalc;
    private ActivationLogic activationLogic;
    private ItemStorage itemStorage;
    private NotificationManager notificationManager;
    public static long initTime;
    private final EventKey eventKey;

    public venusfr() {
        this.clientDir = new File(Minecraft.getInstance().gameDir + "\\venusfr");
        this.filesDir = new File(Minecraft.getInstance().gameDir + "\\venusfr\\files");
        this.autoBuyConfig = new AutoBuyConfig();
        this.eventKey = new EventKey(-1);
        instance = this;
        if (!this.clientDir.exists()) {
            this.clientDir.mkdirs();
        }
        if (!this.filesDir.exists()) {
            this.filesDir.mkdirs();
        }
        this.clientLoad();
        FriendStorage.load();
        StaffStorage.load();
        DiscordRPC.startRPC();
    }

    public Dragging createDrag(Function function, String string, float f, float f2) {
        DragManager.draggables.put(string, new Dragging(function, string, f, f2));
        return DragManager.draggables.get(string);
    }

    private void clientLoad() {
        this.viaMCP = new ViaMCP();
        this.serverTPS = new ServerTPS();
        this.functionRegistry = new FunctionRegistry();
        this.macroManager = new MacroManager();
        this.configStorage = new ConfigStorage();
        this.functionRegistry.init();
        this.initCommands();
        this.initStyles();
        this.altWidget = new AltWidget();
        this.altConfig = new AltConfig();
        this.tpsCalc = new TPSCalc();
        this.notificationManager = new NotificationManager();
        try {
            this.autoBuyConfig.init();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        try {
            this.altConfig.init();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        try {
            this.configStorage.init();
        } catch (IOException iOException) {
            System.out.println("\u041e\u0448\u0438\u0431\u043a\u0430 \u043f\u0440\u0438 \u043f\u043e\u0434\u0433\u0440\u0443\u0437\u043a\u0435 \u043a\u043e\u043d\u0444\u0438\u0433\u0430.");
        }
        try {
            this.macroManager.init();
        } catch (IOException iOException) {
            System.out.println("\u041e\u0448\u0438\u0431\u043a\u0430 \u043f\u0440\u0438 \u043f\u043e\u0434\u0433\u0440\u0443\u0437\u043a\u0435 \u043a\u043e\u043d\u0444\u0438\u0433\u0430 \u043c\u0430\u043a\u0440\u043e\u0441\u043e\u0432.");
        }
        DragManager.load();
        this.dropDown = new DropDown(new StringTextComponent(""));
        this.initAutoBuy();
        this.autoBuyUI = new AutoBuyUI(new StringTextComponent(""));
        this.autoBuyHandler = new AutoBuyHandler();
        this.autoBuyConfig = new AutoBuyConfig();
        this.eventBus.register(this);
        initTime = System.currentTimeMillis();
    }

    public void onKeyPressed(int n) {
        this.eventKey.setKey(n);
        this.eventBus.post(this.eventKey);
        this.macroManager.onKeyPressed(n);
        if (n == 344) {
            Minecraft.getInstance().displayGuiScreen(this.dropDown);
        }
        if (this.functionRegistry.getAutoBuyUI().isState() && (Integer)this.functionRegistry.getAutoBuyUI().setting.get() == n) {
            Minecraft.getInstance().displayGuiScreen(this.autoBuyUI);
        }
    }

    private void initAutoBuy() {
        ItemFactoryImpl itemFactoryImpl = new ItemFactoryImpl();
        CopyOnWriteArrayList<IItem> copyOnWriteArrayList = new CopyOnWriteArrayList<IItem>();
        this.itemStorage = new ItemStorage(copyOnWriteArrayList, itemFactoryImpl);
        this.activationLogic = new ActivationLogic(this.itemStorage, this.eventBus);
    }

    private void initCommands() {
        Minecraft minecraft = Minecraft.getInstance();
        MultiLogger multiLogger = new MultiLogger(List.of((Object)new ConsoleLogger(), (Object)new MinecraftLogger()));
        ArrayList<Command> arrayList = new ArrayList<Command>();
        PrefixImpl prefixImpl = new PrefixImpl();
        arrayList.add(new ListCommand(arrayList, multiLogger));
        arrayList.add(new FriendCommand(prefixImpl, multiLogger, minecraft));
        arrayList.add(new BindCommand(prefixImpl, multiLogger));
        arrayList.add(new GPSCommand(prefixImpl, multiLogger));
        arrayList.add(new ConfigCommand(this.configStorage, prefixImpl, multiLogger));
        arrayList.add(new MacroCommand(this.macroManager, prefixImpl, multiLogger));
        arrayList.add(new VClipCommand(prefixImpl, multiLogger, minecraft));
        arrayList.add(new HClipCommand(prefixImpl, multiLogger, minecraft));
        arrayList.add(new StaffCommand(prefixImpl, multiLogger));
        arrayList.add(new MemoryCommand(multiLogger));
        arrayList.add(new RCTCommand(multiLogger, minecraft));
        AdviceCommandFactoryImpl adviceCommandFactoryImpl = new AdviceCommandFactoryImpl(multiLogger);
        ParametersFactoryImpl parametersFactoryImpl = new ParametersFactoryImpl();
        this.commandDispatcher = new StandaloneCommandDispatcher(arrayList, adviceCommandFactoryImpl, prefixImpl, parametersFactoryImpl, multiLogger);
    }

    private void initStyles() {
        StyleFactoryImpl styleFactoryImpl = new StyleFactoryImpl();
        ArrayList<Style> arrayList = new ArrayList<Style>();
        arrayList.add(styleFactoryImpl.createStyle("\u0421\u0438\u043d\u0438\u0439", new Color(18, 0, 255), new Color(0, 229, 255)));
        arrayList.add(styleFactoryImpl.createStyle("\u0420\u043e\u0437\u043e\u0432\u0432\u044b\u0439", new Color(222, 25, 220), new Color(222, 25, 220)));
        arrayList.add(styleFactoryImpl.createStyle("\u0424\u0438\u043e\u043b\u0435\u0442\u043e\u0432\u044b\u0439", new Color(78, 5, 127), new Color(193, 140, 234)));
        arrayList.add(styleFactoryImpl.createStyle("\u041d\u0435\u043e\u0431\u044b\u0447\u043d\u044b\u0439", new Color(243, 160, 232), new Color(171, 250, 243)));
        arrayList.add(styleFactoryImpl.createStyle("\u041e\u0433\u043d\u0435\u043d\u043d\u044b\u0439", new Color(194, 21, 0), new Color(255, 197, 0)));
        arrayList.add(styleFactoryImpl.createStyle("\u0421\u0435\u0440\u044b\u0439", new Color(40, 39, 39), new Color(178, 178, 178)));
        arrayList.add(styleFactoryImpl.createStyle("\u0417\u0435\u043b\u0435\u043d\u0435\u043d\u044c\u043a\u0438\u0439", new Color(82, 241, 171), new Color(66, 172, 245)));
        arrayList.add(styleFactoryImpl.createStyle("\u041a\u043b\u0430\u0441\u0441\u043d\u044b\u0439", new Color(16, 106, 210), new Color(163, 44, 223)));
        arrayList.add(styleFactoryImpl.createStyle("\u0410\u0441\u0442\u043e\u043b\u044c\u0444\u043e", new Color(241, 95, 233), new Color(118, 172, 215)));
        arrayList.add(styleFactoryImpl.createStyle("\u0411\u0430\u0433\u0440\u043e\u0432\u044b\u0439", new Color(190, 5, 60), new Color(61, 61, 80)));
        arrayList.add(styleFactoryImpl.createStyle("\u0411\u0438\u0440\u044e\u0437\u043e\u0432\u044b\u0439", new Color(82, 241, 171), new Color(66, 172, 245)));
        arrayList.add(styleFactoryImpl.createStyle("\u0424\u0438\u043e\u043b\u0435\u0442\u043e\u0432\u043e", new Color(117, 0, 147), new Color(0, 43, 108)));
        arrayList.add(styleFactoryImpl.createStyle("\u0414\u0430\u0440\u043a-\u0444\u0438\u043e\u043b", new Color(16, 0, 37), new Color(159, 67, 255)));
        arrayList.add(styleFactoryImpl.createStyle("\u0411\u0435\u043b\u044b\u0439", new Color(255, 255, 255), new Color(126, 126, 126)));
        arrayList.add(styleFactoryImpl.createStyle("\u041a\u0438\u0441\u043b\u043e\u0442\u043d\u044b\u0439", new Color(85, 222, 170), new Color(239, 236, 42)));
        arrayList.add(styleFactoryImpl.createStyle("\u0427\u0435\u0440\u043d\u043e \u043a\u0440\u0430\u0441\u043d\u044b\u0439", new Color(16, 0, 37), new Color(241, 20, 20)));
        this.styleManager = new StyleManager(arrayList, (Style)arrayList.get(0));
    }

    public boolean isPlayerOnServer() {
        return this.playerOnServer;
    }

    public FunctionRegistry getFunctionRegistry() {
        return this.functionRegistry;
    }

    public ConfigStorage getConfigStorage() {
        return this.configStorage;
    }

    public CommandDispatcher getCommandDispatcher() {
        return this.commandDispatcher;
    }

    public ServerTPS getServerTPS() {
        return this.serverTPS;
    }

    public MacroManager getMacroManager() {
        return this.macroManager;
    }

    public StyleManager getStyleManager() {
        return this.styleManager;
    }

    public EventBus getEventBus() {
        return this.eventBus;
    }

    public ScriptManager getScriptManager() {
        return this.scriptManager;
    }

    public File getClientDir() {
        return this.clientDir;
    }

    public File getFilesDir() {
        return this.filesDir;
    }

    public AltWidget getAltWidget() {
        return this.altWidget;
    }

    public AltConfig getAltConfig() {
        return this.altConfig;
    }

    public DropDown getDropDown() {
        return this.dropDown;
    }

    public AutoBuyUI getAutoBuyUI() {
        return this.autoBuyUI;
    }

    public AutoBuyConfig getAutoBuyConfig() {
        return this.autoBuyConfig;
    }

    public AutoBuyHandler getAutoBuyHandler() {
        return this.autoBuyHandler;
    }

    public ViaMCP getViaMCP() {
        return this.viaMCP;
    }

    public TPSCalc getTpsCalc() {
        return this.tpsCalc;
    }

    public ActivationLogic getActivationLogic() {
        return this.activationLogic;
    }

    public ItemStorage getItemStorage() {
        return this.itemStorage;
    }

    public NotificationManager getNotificationManager() {
        return this.notificationManager;
    }

    public EventKey getEventKey() {
        return this.eventKey;
    }

    public static venusfr getInstance() {
        return instance;
    }

    public static class UserData {
        final String user;
        final int uid;

        public String getUser() {
            return this.user;
        }

        public int getUid() {
            return this.uid;
        }

        public UserData(String string, int n) {
            this.user = string;
            this.uid = n;
        }
    }
}

