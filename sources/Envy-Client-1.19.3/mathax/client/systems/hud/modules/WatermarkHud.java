package mathax.client.systems.hud.modules;

import mathax.client.renderer.GL;
import mathax.client.renderer.Renderer2D;
import mathax.client.settings.*;
import mathax.client.systems.modules.Modules;
import mathax.client.utils.Version;
import mathax.client.utils.render.color.Color;
import mathax.client.systems.modules.client.ClientSpoof;
import mathax.client.systems.hud.HUD;
import mathax.client.systems.hud.HudRenderer;
import mathax.client.systems.hud.TripleTextHudElement;
import net.minecraft.util.Identifier;

public class WatermarkHud extends TripleTextHudElement {
    private final ClientSpoof cs = Modules.get().get(ClientSpoof.class);

    private static final Identifier MATHAX_LOGO = new Identifier("mathax", "textures/icons/icon.png");
    private static final Identifier METEOR_LOGO = new Identifier("mathax", "textures/icons/meteor.png");
    private final Color TEXTURE_COLOR = new Color(255, 255, 255, 255);

    private String versionString;

    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    // General

    private final Setting<Mode> mode = sgGeneral.add(new EnumSetting.Builder<Mode>()
        .name("mode")
        .description("Determines what watermark style to use.")
        .defaultValue(Mode.Both)
        .build()
    );

    private final Setting<Double> scale = sgGeneral.add(new DoubleSetting.Builder()
        .name("scale")
        .description("The scale of the icon.")
        .defaultValue(1)
        .min(1)
        .sliderRange(1, 5)
        .visible(() -> mode.get() == Mode.Icon)
        .build()
    );

    private final Setting<Boolean> updateCheck = sgGeneral.add(new BoolSetting.Builder()
        .name("update-checker")
        .description("Checks if a new version of MatHax is available.")
        .defaultValue(true)
        .build()
    );

    public WatermarkHud(HUD hud) {
        super(hud, "watermark", "Displays a MatHax+ watermark.", true);
    }

    protected String getLeft() {
        if (cs.changeWatermark()) return cs.watermarkText.get() + " ";
        return "EnvyClient ";
    }

    protected String getRight() {
        if (cs.changeVersion()) return "v" + cs.versionText.get();
        return Version.getStylized();
    }

    protected String getEnd() {
        if (!updateCheck.get() || cs.changeVersion()) return "";
        return checkForUpdate();
    }

    @Override
    public void update(HudRenderer renderer) {
        if (mode.get() == Mode.Text) {
            double textWidth = renderer.textWidth(getLeft()) + renderer.textWidth(getRight()) + renderer.textWidth(getEnd());
            box.setSize(textWidth, renderer.textHeight());

            double x = box.getX();
            double y = box.getY();

            renderer.text(getLeft(), x, y, hud.primaryColor.get());
            renderer.text(getRight(), x + renderer.textWidth(getLeft()), y, hud.secondaryColor.get());
            renderer.text(getEnd(), x + textWidth - renderer.textWidth(getEnd()), y, hud.primaryColor.get());
        } else if (mode.get() == Mode.Icon) {
            double width = renderer.textHeight() * scale.get();
            double height = renderer.textHeight() * scale.get();

            box.setSize(width,  height);
        } else {
            double textWidth = renderer.textWidth(getLeft()) + renderer.textWidth(getRight()) + renderer.textWidth(getEnd());
            double width = renderer.textHeight();
            double height = renderer.textHeight();

            box.setSize(width + textWidth, height);

            double x = box.getX();
            double y = box.getY();

            renderer.text(getLeft(), x + renderer.textHeight(), y, hud.primaryColor.get());
            renderer.text(getRight(), x + renderer.textHeight() + renderer.textWidth(getLeft()), y, hud.secondaryColor.get());
            renderer.text(getEnd(), x + renderer.textHeight() + textWidth - renderer.textWidth(getEnd()), y, hud.primaryColor.get());
        }
    }

    @Override
    public void render(HudRenderer renderer) {
        double x;
        double y;

        switch (mode.get()) {
            case Icon:
                x = box.getX();
                y = box.getY();

                drawIcon((int) x, (int) y, 0);
            case Both:
                x = box.getX();
                y = box.getY();

                double textWidth = renderer.textWidth(getLeft()) + renderer.textWidth(getRight()) + renderer.textWidth(getEnd());
                drawIcon((int) x, (int) y, (int) textWidth);
        }
    }

    private void drawIcon(int x, int y, int textWidth) {
        int w = 0;

        switch (mode.get()) {
            case Icon -> w = (int) box.width;
            case Both -> w = (int) box.width - textWidth;
        }

        int h = (int) box.height;

        Identifier LOGO;
        if (cs.changeWatermarkIcon()) LOGO = METEOR_LOGO;
        else LOGO = MATHAX_LOGO;

        GL.bindTexture(LOGO);
        Renderer2D.TEXTURE.begin();
        Renderer2D.TEXTURE.texQuad(x, y, w, h, TEXTURE_COLOR);
        Renderer2D.TEXTURE.render(null);
    }

    public String checkForUpdate() {
        if (versionString == null) versionString = "";

        if (Version.UpdateChecker.checkForLatest) {
            Version.UpdateChecker.checkForLatest = false;

            switch (Version.UpdateChecker.checkLatest()) {
                case Cant_Check -> versionString = " [Could not get latest version]";
                case Newer_Found -> versionString = " [Outdated | Latest version: v" + Version.UpdateChecker.getLatest() + "]";
                default -> versionString = "";
            }
        }

        return versionString;
    }

    public enum Mode {
        Text("Text"),
        Icon("Icon"),
        Both("Both");

        private final String title;

        Mode(String title) {
            this.title = title;
        }

        @Override
        public String toString() {
            return title;
        }
    }
}
