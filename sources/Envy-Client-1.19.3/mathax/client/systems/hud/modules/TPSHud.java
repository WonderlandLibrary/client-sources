package mathax.client.systems.hud.modules;

import mathax.client.utils.world.TickRate;
import mathax.client.systems.hud.DoubleTextHudElement;
import mathax.client.systems.hud.HUD;

public class TPSHud extends DoubleTextHudElement {
    public TPSHud(HUD hud) {
        super(hud, "tps", "Displays the server's TPS.", true);
    }

    @Override
    protected String getLeft() {
        return "TPS: ";
    }

    @Override
    protected String getRight() {
        return String.format("%.1f", TickRate.INSTANCE.getTickRate()).replace(",", ".");
    }
}
