package cc.slack.start;

import cc.slack.events.Event;
import cc.slack.events.impl.game.ChatEvent;
import cc.slack.events.impl.input.KeyEvent;
import cc.slack.features.commands.CMDManager;
import cc.slack.features.commands.api.CMD;
import cc.slack.features.config.configManager;
import cc.slack.features.friends.FriendManager;
import cc.slack.features.modules.ModuleManager;
import cc.slack.features.modules.impl.movement.Sprint;
import cc.slack.features.modules.impl.other.RichPresence;
import cc.slack.features.modules.impl.other.Targets;
import cc.slack.features.modules.impl.other.Tweaks;
import cc.slack.features.modules.impl.render.Animations;
import cc.slack.features.modules.impl.render.Interface;
import cc.slack.features.modules.impl.render.TargetHUD;
import cc.slack.ui.altmanager.AccountManager;
import cc.slack.utils.client.ClientInfo;
import cc.slack.utils.EventUtil;
import cc.slack.utils.other.PrintUtil;
import de.florianmichael.viamcp.ViaMCP;
import io.github.nevalackin.radbus.Listen;
import io.github.nevalackin.radbus.PubSub;
import lombok.Getter;
import org.lwjgl.opengl.Display;

import java.util.Arrays;


@Getter
@SuppressWarnings("unused")
public class Slack {

    @Getter
    public static final Slack instance = new Slack();
    public final ClientInfo info = new ClientInfo("Slack", "v2.0", ClientInfo.VersionType.BETA);
    private final PubSub<Event> eventBus = PubSub.newInstance(System.err::println);

    private final ModuleManager moduleManager = new ModuleManager();
    private final CMDManager cmdManager = new CMDManager();
    private FriendManager friendManager;

    public final String[] changelog = new String[]{""};

    public final String DiscordServer = "https://discord.gg/nwR9AyjnK8";
    public final String Website = "https://slackclient.github.io/";

    public void start() {
        PrintUtil.print("Initializing " + info.getName());
        Display.setTitle(info.getName() + " " + info.getVersion() + " (" + info.getType() + ")");

        EventUtil.register(this);
        moduleManager.initialize();
        cmdManager.initialize();
        configManager.init();
        AccountManager.start();
        friendManager = new FriendManager();



        // Default Modules
        moduleManager.getInstance(RichPresence.class).toggle();
        moduleManager.getInstance(Animations.class).toggle();
        moduleManager.getInstance(Interface.class).toggle();
        moduleManager.getInstance(Sprint.class).toggle();
        moduleManager.getInstance(Tweaks.class).toggle();
        moduleManager.getInstance(TargetHUD.class).toggle();
        moduleManager.getInstance(Targets.class).toggle();


        try {
            ViaMCP.create();
            ViaMCP.INSTANCE.initAsyncSlider();
        } catch (Exception e) {
            // Dont more StrackTrace
        }
    }

    public void shutdown() {
        EventUtil.unRegister(this);
        configManager.stop();
    }

    // Event Stuff

    @Listen
    public void handleKey(KeyEvent e) {
        moduleManager.getModules().forEach(module -> {
            if (module.getKey() == e.getKey())
                module.toggle();
        });
    }

    @Listen
    public void handleChat(ChatEvent e) {
        String message = e.getMessage();

        if (!message.startsWith(cmdManager.getPrefix())) return;
        e.cancel();

        message = message.substring(cmdManager.getPrefix().length());

        if (message.split("\\s")[0].equalsIgnoreCase("")) {
            PrintUtil.message("This is §cSlack's§f prefix for ingame client commands. Type §c.help §fto get started.");
            return;
        }

        if (message.split("\\s").length > 0) {
            final String cmdName = message.split("\\s")[0];

            for (CMD cmd : cmdManager.getCommands()) {
                if (cmd.getName().equalsIgnoreCase(cmdName) || cmd.getAlias().equalsIgnoreCase(cmdName)) {
                    cmd.onCommand(Arrays.copyOfRange(message.split("\\s"), 1, message.split("\\s").length), message);
                    return;
                }
            }

            PrintUtil.message("\"" + message + "\" is not a recognized command. Use §c.help §fto get other commands.");
        }
    }

    public FriendManager getFriendManager() {
        return friendManager;
    }

    public enum NotificationStyle {
        GRAY, SUCCESS, FAIL, WARN
    }

    public void addNotification(String bigText, String smallText, Long duration, NotificationStyle style) {
        instance.getModuleManager().getInstance(Interface.class).addNotification(bigText, smallText, duration, style);
    }
}
