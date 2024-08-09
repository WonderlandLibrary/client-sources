package xyz.mathax.mathaxclient.systems.hud.elements;

import xyz.mathax.mathaxclient.systems.hud.DoubleTextHudElement;
import xyz.mathax.mathaxclient.systems.hud.Hud;
import xyz.mathax.mathaxclient.utils.Utils;

public class ServerHudElement extends DoubleTextHudElement {
    public ServerHudElement(Hud hud) {
        super(hud, "Server", "Displays the server you're currently in.");
    }

    @Override
    protected String getLeft() {
        return name + ": ";
    }

    @Override
    protected String getRight() {
        if (!Utils.canUpdate()) {
            return "None";
        }

        return Utils.getWorldName();
    }
}



