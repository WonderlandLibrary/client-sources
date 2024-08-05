package fr.dog.util.render.font;

import fr.dog.util.InstanceAccess;
import net.minecraft.util.ResourceLocation;

import java.awt.*;
import java.io.IOException;
import java.util.HashMap;

public class Fonts implements InstanceAccess {
    private static final HashMap<Integer, TTFFontRenderer> OPEN_SANS_REGULAR = new HashMap<>();
    private static final HashMap<Integer, TTFFontRenderer> OPEN_SANS_MEDIUM = new HashMap<>();
    private static final HashMap<Integer, TTFFontRenderer> OPEN_SANS_BOLD = new HashMap<>();
    private static final HashMap<Integer, TTFFontRenderer> STRATUM2_MEDIUM = new HashMap<>();
    private static final HashMap<Integer, TTFFontRenderer> ESPICONS_MEDIUM = new HashMap<>();
    private static final HashMap<Integer, TTFFontRenderer> SAN_FRANCISCO = new HashMap<>();
    private static final HashMap<Integer, TTFFontRenderer> ROBOTO_MEDIUM = new HashMap<>();
    private static final HashMap<Integer, TTFFontRenderer> URBANIST_BOLD = new HashMap<>();
    private static final HashMap<Integer, TTFFontRenderer> MINECRAFT = new HashMap<>();
    private static final HashMap<Integer, TTFFontRenderer> MOJANG = new HashMap<>();
    private static final HashMap<Integer, TTFFontRenderer> MOON = new HashMap<>();

    public static TTFFontRenderer getOpenSansRegular(final int size) {
        return get(OPEN_SANS_REGULAR, size, "OpenSans-Regular", true, true);
    }
    public static TTFFontRenderer getSanFrancisco(final int size){
        return get(SAN_FRANCISCO, size, "SF-Pro", true, true);
    }
    public static TTFFontRenderer getESPIcons(final int size){
        return get(ESPICONS_MEDIUM, size, "esp-icons", true, true);
    }
    public static TTFFontRenderer getMojang(final int size){
        return get(MOJANG, size, "Mojang-Regular", true, true);
    }

    public static TTFFontRenderer getOpenSansMedium(final int size) {
        return get(OPEN_SANS_MEDIUM, size, "OpenSans-Medium", true, true);
    }
    public static TTFFontRenderer getOpenSansBold(final int size) {
        return get(OPEN_SANS_BOLD, size, "OpenSans-Bold", true, true);
    }
    public static TTFFontRenderer getRobotoMedium(final int size) {
        return get(ROBOTO_MEDIUM, size, "Roboto-Medium", true, true);
    }
    public static TTFFontRenderer getStratum2Medium(final int size) {
        return get(STRATUM2_MEDIUM , size, "Stratum2-Medium", true, true);
    }
    public static TTFFontRenderer getMinecraft(final int size) {
        return get(MINECRAFT, size, "MinecraftRegular", true, true);
    }
    public static TTFFontRenderer getUrbanistBold(final int size) {
        return get(URBANIST_BOLD, size, "Urbanist-Bold", true, true);
    }

    public static TTFFontRenderer getMoon(final int size) {
        return get(MOON, size, "Moon", true, true);
    }

    private static TTFFontRenderer get(HashMap<Integer, TTFFontRenderer> map, int size, String name, boolean antialiasing, boolean fractionalMetrics) {
        if (!map.containsKey(size)) {
            final Font font;

            try {
                font = Font.createFont(Font.TRUETYPE_FONT, mc.getResourceManager().getResource(new ResourceLocation("dogclient/font/" + name + ".ttf")).getInputStream()).deriveFont((float) size);
            } catch (final FontFormatException | IOException ignored) {
                return null;
            }

            map.put(size, new TTFFontRenderer(font, antialiasing, fractionalMetrics));
        }

        return map.get(size);
    }
}