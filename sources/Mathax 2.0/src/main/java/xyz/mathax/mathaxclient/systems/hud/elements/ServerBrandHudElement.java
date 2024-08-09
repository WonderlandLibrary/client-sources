package xyz.mathax.mathaxclient.systems.hud.elements;

import xyz.mathax.mathaxclient.systems.Systems;
import xyz.mathax.mathaxclient.systems.hud.DoubleTextHudElement;
import xyz.mathax.mathaxclient.systems.hud.Hud;
import xyz.mathax.mathaxclient.systems.themes.Themes;
import xyz.mathax.mathaxclient.utils.Utils;
import net.minecraft.util.StringHelper;

public class ServerBrandHudElement extends DoubleTextHudElement {
    public ServerBrandHudElement(Hud hud) {
        super(hud, "Server Brand", "Displays the brand of the server you're currently in.");
    }

    @Override
    protected String getLeft() {
        return name + ": ";
    }

    @Override
    protected String getRight() {
        if (!Utils.canUpdate() || mc.player.getServerBrand() == null) {
            return "None";
        }

        String brand = mc.player.getServerBrand();
        if (Systems.get(Themes.class).getTheme().customFont()) {
            brand = StringHelper.stripTextFormat(brand);
        }

        if (mc.isInSingleplayer() && brand.equals("fabric")) {
            brand = "Fabric";
        }

        return brand;
    }
}