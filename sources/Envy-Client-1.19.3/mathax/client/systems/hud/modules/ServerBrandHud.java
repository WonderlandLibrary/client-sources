package mathax.client.systems.hud.modules;

import mathax.client.systems.config.Config;
import mathax.client.utils.Utils;
import mathax.client.systems.hud.DoubleTextHudElement;
import mathax.client.systems.hud.HUD;

public class ServerBrandHud extends DoubleTextHudElement {
    public ServerBrandHud(HUD hud) {
        super(hud, "server-brand", "Displays the brand of the server you're currently in.", true);
    }

    @Override
    protected String getLeft() {
        return "Server Brand: ";
    }

    @Override
    protected String getRight() {
        return getServerBrand();
    }

    private String getServerBrand() {
        if (!Utils.canUpdate() || mc.player.getServerBrand() == null) return "None";

        String brand = mc.player.getServerBrand();
        if (!Config.get().customFont.get()) brand = decolorize(brand);
        if (mc.isInSingleplayer() && brand.equals("fabric")) brand = "Fabric";
        return brand;
    }

    private String decolorize(String brand) {
        brand = brand.replace("§a", "");
        brand = brand.replace("§b", "");
        brand = brand.replace("§c", "");
        brand = brand.replace("§d", "");
        brand = brand.replace("§e", "");
        brand = brand.replace("§1", "");
        brand = brand.replace("§2", "");
        brand = brand.replace("§3", "");
        brand = brand.replace("§4", "");
        brand = brand.replace("§5", "");
        brand = brand.replace("§6", "");
        brand = brand.replace("§7", "");
        brand = brand.replace("§8", "");
        brand = brand.replace("§9", "");
        brand = brand.replace("§k", "");
        brand = brand.replace("§l", "");
        brand = brand.replace("§m", "");
        brand = brand.replace("§n", "");
        brand = brand.replace("§o", "");
        brand = brand.replace("§r", "");
        brand = brand.replace("&a", "");
        brand = brand.replace("&b", "");
        brand = brand.replace("&c", "");
        brand = brand.replace("&d", "");
        brand = brand.replace("&e", "");
        brand = brand.replace("&1", "");
        brand = brand.replace("&2", "");
        brand = brand.replace("&3", "");
        brand = brand.replace("&4", "");
        brand = brand.replace("&5", "");
        brand = brand.replace("&6", "");
        brand = brand.replace("&7", "");
        brand = brand.replace("&8", "");
        brand = brand.replace("&9", "");
        brand = brand.replace("&k", "");
        brand = brand.replace("&l", "");
        brand = brand.replace("&m", "");
        brand = brand.replace("&n", "");
        brand = brand.replace("&o", "");
        brand = brand.replace("&r", "");
        return brand;
    }
}
