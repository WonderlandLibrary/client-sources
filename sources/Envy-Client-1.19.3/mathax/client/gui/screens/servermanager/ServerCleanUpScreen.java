package mathax.client.gui.screens.servermanager;

import mathax.client.gui.GuiTheme;
import mathax.client.gui.WindowScreen;
import mathax.client.gui.widgets.containers.WTable;
import mathax.client.gui.widgets.pressable.WCheckbox;
import mathax.client.mixininterface.IMultiplayerScreen;
import mathax.client.utils.Version;
import mathax.client.utils.render.color.Color;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerServerListWidget;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.util.Formatting;

/*/--------------------------------------------/*/
/*/ Server Clean Up by JFronny                 /*/
/*/ https://github.com/JFronny/MeteorAdditions /*/
/*/--------------------------------------------/*/

public class ServerCleanUpScreen extends WindowScreen {
    private final WCheckbox removeAll = theme.checkbox(false);
    private final WCheckbox removeFailed = theme.checkbox(false);
    private final WCheckbox removeOutdated = theme.checkbox(false);
    private final WCheckbox removeUnknown = theme.checkbox(false);

    public ServerCleanUpScreen(GuiTheme theme, Screen parent) {
        super(theme, "Clean Up");

        this.parent = parent;
    }

    @Override
    public void initWidgets() {
        WTable table = add(new WTable()).widget();

        table.add(theme.label("Servers to remove:"));

        table.row();
        table.add(theme.label("Unknown hosts"));
        table.add(removeUnknown).widget();

        table.row();
        table.add(theme.label("Outdated"));
        table.add(removeOutdated).widget();

        table.row();
        table.add(theme.label("Failed ping"));
        table.add(removeFailed).widget();

        table.row();
        table.add(theme.label("Everything")).widget().color = new Color(255, 0, 0);
        table.add(removeAll).widget();

        table.row();
        table.add(theme.button("Execute")).expandX().widget().action = this::execute;
    }

    private void execute() {
        MultiplayerScreen multiplayerScreen = (MultiplayerScreen) parent;

        for (int i = multiplayerScreen.getServerList().size() - 1; i >= 0; i--) {
            ServerInfo server = multiplayerScreen.getServerList().get(i);

            if (removeAll.checked || shouldRemove(server)) multiplayerScreen.getServerList().remove(server);
        }

        saveServerList();
        client.setScreen(parent);
    }

    private boolean shouldRemove(ServerInfo server) {
        if (server == null) return false;
        if (removeUnknown.checked && isUnknownHost(server)) return true;
        if (removeOutdated.checked && !isSameProtocol(server)) return true;
        return removeFailed.checked && isFailedPing(server);
    }

    private boolean isUnknownHost(ServerInfo server) {
        if (server.label == null) return false;
        if (server.label.getString() == null) return false;
        return server.label.getString().equals(Formatting.DARK_RED + "Can't resolve hostname");
    }

    private boolean isSameProtocol(ServerInfo server) {
        return server.protocolVersion == Version.getMinecraftProtocol();
    }

    private boolean isFailedPing(ServerInfo server) {
        return server.ping != -2L && server.ping < 0L;
    }

    private void saveServerList() {
        MultiplayerScreen multiplayerScreen = (MultiplayerScreen) parent;

        multiplayerScreen.getServerList().saveFile();

        MultiplayerServerListWidget serverListSelector = ((IMultiplayerScreen)multiplayerScreen).getServerListWidget();

        serverListSelector.setSelected(null);
        serverListSelector.setServers(multiplayerScreen.getServerList());
    }
}
