package xyz.mathax.mathaxclient.utils.render;

import net.minecraft.client.util.math.MatrixStack;
import xyz.mathax.mathaxclient.MatHax;
import xyz.mathax.mathaxclient.addons.AddonManager;
import xyz.mathax.mathaxclient.addons.MatHaxAddon;
import xyz.mathax.mathaxclient.utils.network.versions.Versions;
import xyz.mathax.mathaxclient.utils.render.color.Color;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static xyz.mathax.mathaxclient.MatHax.mc;

public class TitleScreenCredits {
    private static final List<Credit> credits = new ArrayList<>();

    private static void init() {
        add(MatHax.ADDON);
        for (MatHaxAddon addon : AddonManager.ADDONS) {
            add(addon);
        }

        credits.sort(Comparator.comparingInt(value -> value.sections.get(0).text.equals("MatHax ") ? Integer.MIN_VALUE : -value.width));
    }

    private static void add(MatHaxAddon addon) {
        Credit credit = new Credit(addon);

        credit.sections.add(new Section(addon.name, addon.color));
        credit.sections.add(new Section(" " + Versions.getStylized(addon.version), Color.WHITE));
        credit.sections.add(new Section(" by ", Color.LIGHT_GRAY));

        for (int i = 0; i < addon.authors.length; i++) {
            if (i > 0) {
                credit.sections.add(new Section(i == addon.authors.length - 1 ? " & " : ", ", Color.LIGHT_GRAY));
            }

            credit.sections.add(new Section(addon.authors[i], Color.WHITE));
        }

        credit.calculateWidth();
        credits.add(credit);
    }

    public static void render(MatrixStack matrixStack) {
        if (credits.isEmpty()) {
            init();
        }

        int y = 2;
        for (Credit credit : credits) {
            int x = mc.currentScreen.width - 2 - credit.width;
            synchronized (credit.sections) {
                for (Section section : credit.sections) {
                    mc.textRenderer.drawWithShadow(matrixStack, section.text, x, y, Color.fromRGBA(section.color));

                    x += section.width;
                }
            }

            y += mc.textRenderer.fontHeight + 2;
        }
    }

    private static class Credit {
        public final MatHaxAddon addon;

        public final List<Section> sections = new ArrayList<>();

        public int width;

        public Credit(MatHaxAddon addon) {
            this.addon = addon;
        }

        public void calculateWidth() {
            width = 0;
            for (Section section : sections) {
                width += section.width;
            }
        }
    }

    private static class Section {
        public final String text;

        public final Color color;

        public final int width;

        public Section(String text, Color color) {
            this.text = text;
            this.color = color;
            this.width = mc.textRenderer.getWidth(text);
        }
    }
}