package mathax.client.mixininterface;

import net.minecraft.client.gui.screen.multiplayer.MultiplayerServerListWidget;
import net.minecraft.client.network.ServerInfo;

public interface IMultiplayerScreen {
    public MultiplayerServerListWidget getServerListSelector();

    public void connectToServer(ServerInfo server);

    MultiplayerServerListWidget getServerListWidget();
}
