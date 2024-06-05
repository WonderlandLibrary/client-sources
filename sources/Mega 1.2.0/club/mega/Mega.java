package club.mega;

import club.mega.gui.click.ClickGUI;
import club.mega.command.CommandRegistry;
import club.mega.event.impl.*;
import club.mega.file.ModuleSaver;
import club.mega.fontrenderer.FontManager;
import club.mega.gui.altmanager.GuiAltManager;
import club.mega.gui.changelog.Changelog;
import club.mega.gui.TabGUI;
import club.mega.interfaces.MinecraftInterface;
import club.mega.module.ModuleManager;
import club.mega.module.impl.hud.TabGui;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.ScaledResolution;
import rip.hippo.lwjeb.annotation.Handler;
import rip.hippo.lwjeb.bus.PubSub;
import rip.hippo.lwjeb.configuration.BusConfigurations;
import rip.hippo.lwjeb.configuration.config.impl.BusPubSubConfiguration;
import rip.hippo.lwjeb.message.scan.impl.MethodAndFieldBasedMessageScanner;

public enum Mega implements MinecraftInterface {

    INSTANCE;

    private final PubSub<String> pubSub = new PubSub<>(new BusConfigurations.Builder().setConfiguration(BusPubSubConfiguration.class, () -> {
        BusPubSubConfiguration busPubSubConfiguration = BusPubSubConfiguration.getDefault();
        busPubSubConfiguration.setScanner(new MethodAndFieldBasedMessageScanner<>());
        return busPubSubConfiguration;
    }).build());
    private Changelog changelog;

    private ModuleManager moduleManager;
    private CommandRegistry commandRegistry;
    private FontManager fontManager;
    private ClickGUI clickGUI;
    private GuiAltManager altManager;
    private TabGUI tabGUI;

    public final void startUp() {
        pubSub.subscribe(this);

        fontManager = new FontManager();
        moduleManager = new ModuleManager();
        commandRegistry = new CommandRegistry();
        clickGUI = new ClickGUI();
        altManager = new GuiAltManager(new GuiMainMenu());
        tabGUI = new TabGUI();
        changelog = new Changelog();

        ModuleSaver.load();

        Runtime.getRuntime().addShutdownHook(new Thread(this::shutDown));
    }

    public final void shutDown() {
        pubSub.unsubscribe(this);
        altManager.loadAlts();

        ModuleSaver.save();
    }

    @Handler
    public final void resize(final EventResize event) {
        changelog = new Changelog();
    }

    @Handler
    public final void key(final EventKey event) {
        moduleManager.getModules().forEach(module -> {
            if (module.getKey() == event.getKey())
                module.toggle();
        });

        tabGUI.keyTyped(event);
    }

    @Handler
    public final void render2D(final EventRender2D event) {
        if (moduleManager.isToggled(TabGui.class))
        tabGUI.renderTabGUI(event);
    }

    @Handler
    public final void chat(final EventChat event) {
        if (event.getMessage().startsWith(".")) {
            event.setCancelled(true);
            getCommandRegistry().execute(event.getMessage().substring(1));
        }
    }

    public final PubSub getPubSub() {
        return pubSub;
    }

    public final Changelog getChangelog() {
        return changelog;
    }

    public final ModuleManager getModuleManager() {
        return moduleManager;
    }

    public final CommandRegistry getCommandRegistry() {
        return commandRegistry;
    }

    public final FontManager getFontManager() {
        return fontManager;
    }

    public final ClickGUI getClickGUI() {
        return clickGUI;
    }

    public final void fixClickGui() {
        clickGUI = new ClickGUI();
        MC.displayGuiScreen(null);
        MC.displayGuiScreen(clickGUI);
    }

    public final GuiAltManager getAltManager() {
        return altManager;
    }

    public final String getName() {
        return "Mega";
    }

    public final String getVersion() {
        return "1.2.0";

        /*
        The usual method I have seen is X.Y.Z, which generally corresponds to major.minor.patch:
        Major version numbers change whenever there is some significant change being introduced. For example, a large or potentially backward-incompatible change to a software package.
        Minor version numbers change when a new, minor feature is introduced or when a set of smaller features is rolled out.
        Patch numbers change when a new build of the software is released to customers. This is normally for small bug-fixes or the like.
         */
    }

    public final String getDev() {
        return "Exeos, LCA_MODZ, Felix1337, Kroko";
    }

    public final String getUserName() {
        return "Exeos";
    }

}
