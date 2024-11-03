package dev.star;

import dev.star.commands.CommandHandler;
import dev.star.config.ConfigManager;
import dev.star.config.DragManager;
import dev.star.event.EventProtocol;
import dev.star.event.impl.network.PacketBlinkHandler;
import dev.star.module.Module;
import dev.star.module.api.ModuleCollection;
import dev.star.gui.altmanager.Alt;
import dev.star.gui.altmanager.GuiAltManagerScreen;
import dev.star.utils.Utils;
import dev.star.utils.client.ReleaseType;
import dev.star.utils.misc.DiscordRPC;
import dev.star.utils.objects.DiscordAccount;
import dev.star.utils.objects.Dragging;
import dev.star.utils.server.PingerUtils;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.*;
import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Getter
@Setter
public class Client implements Utils {
    public static final Client INSTANCE = new Client();
    public static final String NAME = "Star";
    public static final String VERSION = "2.3";
    public static final ReleaseType RELEASE = ReleaseType.BETA;
    public static final Logger LOGGER = LogManager.getLogger(NAME);
    public static final File DIRECTORY = new File(mc.mcDataDir, NAME);
    public ResourceLocation cape = new ResourceLocation("Star/Capes/c1.png");
    public Alt currentSessionAlt;
    public String user = "";
    public String uid = "";

    private final EventProtocol eventProtocol = new EventProtocol();
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private ModuleCollection moduleCollection;
    private ConfigManager configManager;
    private GuiAltManagerScreen altManager;
    private CommandHandler commandHandler;
    private PingerUtils pingerUtils;
    private PacketBlinkHandler packetBlinkHandler;

    private DiscordRPC discordRPC;
    private DiscordAccount discordAccount;

    public static boolean updateGuiScale;
    public static int prevGuiScale;

    public String getVersion() {
        return VERSION + (RELEASE != ReleaseType.PUBLIC ? " (" + RELEASE.getName() + ")" : "");
    }

    public final Color getClientColor() {
        return new Color(214, 214, 214);
    }

    public final Color getAlternateClientColor() {
        return new Color(28, 167, 222);
    }

    public boolean isEnabled(Class<? extends Module> c) {
        Module m = INSTANCE.moduleCollection.get(c);
        return m != null && m.isEnabled();
    }

    public Dragging createDrag(Module module, String name, float x, float y) {
        DragManager.draggables.put(name, new Dragging(module, name, x, y));
        return DragManager.draggables.get(name);
    }

}
