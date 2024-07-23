package io.github.liticane.monoxide.util.render.font;

import de.florianmichael.rclasses.storage.Storage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.ResourceLocation;
import io.github.liticane.monoxide.util.render.font.renderer.MonoxideFontRenderer;

import java.awt.*;

public class FontStorage extends Storage<FontEntry> {

    private static FontStorage instance;

    @Override
    public void init() {
        this.add(
                this.createFontEntry("SF UI", 14),
                this.createFontEntry("SF UI", 15),
                this.createFontEntry("SF UI", 16),
                this.createFontEntry("SF UI", 17),
                this.createFontEntry("SF UI", 18),
                this.createFontEntry("SF UI", 19),
                this.createFontEntry("SF UI", 20),
                this.createFontEntry("SF UI", 21),
                this.createFontEntry("SF UI", 24),
                this.createFontEntry("SF UI", 31),
                this.createFontEntry("SF UI Medium", 12),
                this.createFontEntry("SF UI Medium", 15),
                this.createFontEntry("SF UI Medium", 16),
                this.createFontEntry("SF UI Medium", 17),
                this.createFontEntry("SF UI Medium", 18),
                this.createFontEntry("SF UI Medium", 19),
                this.createFontEntry("SF UI Medium", 20),
                this.createFontEntry("SF UI Medium", 21),
                this.createFontEntry("SF UI Medium", 24),
                this.createFontEntry("Tahoma", 16),
                this.createFontEntry("ESP", 21),
                this.createFontEntry("ESP", 80),
                this.createFontEntry("Pangram Bold", 80),
                this.createFontEntry("Pangram Bold", 20),
                this.createFontEntry("Pangram Regular", 16),
                this.createFontEntry("Pangram Regular", 17),
                this.createFontEntry("Product Sans", 17),
                this.createFontEntry("Product Sans", 18),
                this.createFontEntry("Product Sans", 19),
                this.createFontEntry("Product Sans", 20),
                this.createFontEntry("Product Sans", 21),
                this.createFontEntry("SFUI Medium", 16),
                this.createFontEntry("Rubik", 18),
                this.createFontEntry("Arial", 17),
                this.createFontEntry("Arial", 18),
                this.createFontEntry("Arial", 19),
                this.createFontEntry("Arial", 20),
                this.createFontEntry("Arial", 21),
                this.createFontEntry("Arial", 24),
                this.createFontEntry("ArialMT", 16),
                this.createFontEntry("SF Regular", 17),
                this.createFontEntry("SF Regular", 18),
                this.createFontEntry("SF Regular", 19),
                this.createFontEntry("SF Regular", 20),
                this.createFontEntry("SF Regular", 21),
                this.createFontEntry("SF Semibold", 17),
                this.createFontEntry("SF Semibold", 18),
                this.createFontEntry("SF Semibold", 19),
                this.createFontEntry("SF Semibold", 20),
                this.createFontEntry("SF Semibold", 21),
                this.createFontEntry("Volte Semibold", 17),
                this.createFontEntry("Volte Semibold", 18),
                this.createFontEntry("Volte Semibold", 19),
                this.createFontEntry("Volte Semibold", 20),
                this.createFontEntry("Volte Semibold", 21),
                this.createFontEntry("IcoMoon", 12),
                this.createFontEntry("Consolas", 17),
                this.createFontEntry("Consolas", 18),
                this.createFontEntry("Consolas", 19),
                this.createFontEntry("Tahoma", 17),
                this.createFontEntry("Tahoma", 18),
                this.createFontEntry("Tahoma", 19),
                this.createFontEntry("Raleway Regular", 30),
                this.createFontEntry("Raleway Regular", 35),
                this.createFontEntry("Greycliff Medium", 18),
                this.createFontEntry("Greycliff Medium", 19),
                this.createFontEntry("Greycliff Medium", 21),
                this.createFontEntry("Greycliff Bold", 24),
                this.createFontEntry("Porter Bold", 23),
                this.createFontEntry("Android 101", 100, true));
    }

    public FontRenderer findFont(String name, float size) {
        FontEntry foundFontEntry = this.getList().stream().filter(fontEntry -> fontEntry.getName().equalsIgnoreCase(name) && fontEntry.getSize() == size).findFirst().orElse(null);
        assert foundFontEntry != null;
        return foundFontEntry.getModernFontRenderer();
    }

    private FontEntry createFontEntry(String name, float size) {
        return this.createFontEntry(name, size, false);
    }

    private FontEntry createFontEntry(String name, float size, boolean otf) {
        return new FontEntry(name, size, new MonoxideFontRenderer(this.getFontFromFile(new ResourceLocation(String.format("atani/%s.%s", name, otf ? "otf" : "ttf")), size)));
    }

    private Font getFontFromFile(ResourceLocation loc, float fontSize) {
        try {
            Font output = Font.createFont(Font.PLAIN, Minecraft.getMinecraft().getResourceManager().getResource(loc).getInputStream());
            output = output.deriveFont(fontSize);
            return output;
        } catch (Exception var5) {
            return null;
        }
    }

    public static FontStorage getInstance() {
        return instance;
    }

    public static void setInstance(FontStorage instance) {
        FontStorage.instance = instance;
    }
}
