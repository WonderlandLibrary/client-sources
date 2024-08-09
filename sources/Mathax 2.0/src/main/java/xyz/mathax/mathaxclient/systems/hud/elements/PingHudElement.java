package xyz.mathax.mathaxclient.systems.hud.elements;

import xyz.mathax.mathaxclient.systems.hud.DoubleTextHudElement;
import xyz.mathax.mathaxclient.systems.hud.Hud;
import net.minecraft.client.network.PlayerListEntry;

public class PingHudElement extends DoubleTextHudElement {
    public PingHudElement(Hud hud) {
        super(hud, "Ping", "Displays your ping.");
    }

    @Override
    protected String getLeft() {
        return name + ": ";
    }

    @Override
    protected String getRight() {
        if (isInEditor()) {
            return "0";
        }

        PlayerListEntry playerListEntry = mc.getNetworkHandler().getPlayerListEntry(mc.player.getUuid());
        if (playerListEntry != null) {
            return Integer.toString(playerListEntry.getLatency());
        }

        return "0";
    }
}
