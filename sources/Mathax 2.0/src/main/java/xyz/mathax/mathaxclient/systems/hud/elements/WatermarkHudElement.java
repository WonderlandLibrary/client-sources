package xyz.mathax.mathaxclient.systems.hud.elements;

import xyz.mathax.mathaxclient.MatHax;
import xyz.mathax.mathaxclient.eventbus.EventHandler;
import xyz.mathax.mathaxclient.events.game.GameJoinedEvent;
import xyz.mathax.mathaxclient.gui.renderer.OverlayRenderer;
import xyz.mathax.mathaxclient.renderer.GL;
import xyz.mathax.mathaxclient.renderer.Renderer2D;
import xyz.mathax.mathaxclient.renderer.text.Section;
import xyz.mathax.mathaxclient.settings.*;
import xyz.mathax.mathaxclient.systems.hud.Hud;
import xyz.mathax.mathaxclient.systems.hud.TripleTextHudElement;
import xyz.mathax.mathaxclient.utils.misc.MatHaxIdentifier;
import xyz.mathax.mathaxclient.utils.network.versions.Versions;
import xyz.mathax.mathaxclient.utils.render.color.Color;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public class WatermarkHudElement extends TripleTextHudElement {
    private static final Identifier MATHAX_LOGO = new MatHaxIdentifier("icons/1080.png");

    public static boolean didntCheckForUpdate = true;

    private final SettingGroup generalSettings = settings.createGroup("General");

    // General

    private final Setting<Mode> modeSetting = generalSettings.add(new EnumSetting.Builder<Mode>()
            .name("Mode")
            .description("Watermark style to use.")
            .defaultValue(Mode.Both)
            .build()
    );

    private final Setting<Double> scaleSetting = generalSettings.add(new DoubleSetting.Builder()
            .name("Scale")
            .description("Scale of the icon.")
            .defaultValue(1)
            .min(1)
            .sliderRange(1, 5)
            .visible(() -> modeSetting.get() == Mode.Icon)
            .build()
    );

    private final Setting<Boolean> updateCheckerSetting = generalSettings.add(new BoolSetting.Builder()
            .name("Update Checker")
            .description("Checks if a new version of MatHax is available.")
            .defaultValue(true)
            .build()
    );

    public WatermarkHudElement(Hud hud) {
        super(hud, "Watermark", "Displays a MatHax watermark.", true);
    }

    @EventHandler
    private void onGameJoined(GameJoinedEvent event) {
        didntCheckForUpdate = true;
    }

    protected String getLeft() {
        return MatHax.NAME + " ";
    }

    protected String getCenter() {
        return Versions.getStylized();
    }

    protected String getRight() {
        if (updateCheckerSetting.get()) {
            if (didntCheckForUpdate) {
                didntCheckForUpdate = false;
                Versions.checkForUpdate();
            }

            if (Versions.isUpdateAvailable()) {
                return " [Outdated | Latest version: " + Versions.getStylized(true) + "]";
            }
        }

        return "";
    }

    @Override
    public void render(OverlayRenderer renderer) {
        double textWidth = renderer.textWidth(getLeft()) + renderer.textWidth(getCenter()) + renderer.textWidth(getRight());
        switch (modeSetting.get()) {
            case Text -> {
                box.setSize(textWidth, renderer.textHeight());

                renderer.text(getText(), box.getX(), box.getY());
            }
            case Icon -> box.setSize(renderer.textHeight() * scaleSetting.get(),  renderer.textHeight() * scaleSetting.get());
            default -> {
                box.setSize(renderer.textHeight() + 2 + textWidth, renderer.textHeight());

                renderer.text(getText(), box.getX() + 2 + renderer.textHeight(), box.getY() + 2);
            }
        }

        GL.bindTexture(MATHAX_LOGO);
        Renderer2D.TEXTURE.begin();
        Renderer2D.TEXTURE.texturedQuad(box.getX(), box.getY(), box.width - (modeSetting.get() != Mode.Text ? textWidth : 0), box.height, Color.WHITE);
        Renderer2D.TEXTURE.render(null);
    }

    public List<Section> getText() {
        List<Section> sections = new ArrayList<>();
        sections.add(new Section(getLeft(), hud.primaryColorSetting.get()));
        sections.add(new Section(getCenter(), hud.secondaryColorSetting.get()));
        sections.add(new Section(getRight(), hud.primaryColorSetting.get()));
        return sections;
    }

    public enum Mode {
        Text("Text"),
        Icon("Icon"),
        Both("Both");

        private final String name;

        Mode(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}