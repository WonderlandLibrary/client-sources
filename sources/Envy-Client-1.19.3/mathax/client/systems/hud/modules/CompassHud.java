package mathax.client.systems.hud.modules;

import mathax.client.settings.*;
import mathax.client.utils.render.color.SettingColor;
import mathax.client.systems.hud.HUD;
import mathax.client.systems.hud.HudElement;
import mathax.client.systems.hud.HudRenderer;
import net.minecraft.util.math.MathHelper;

public class CompassHud extends HudElement {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    // General

    private final Setting<Mode> mode = sgGeneral.add(new EnumSetting.Builder<Mode>()
        .name("type")
        .description("Which type of direction information to show.")
        .defaultValue(Mode.Axis)
        .build()
    );

    private final Setting<SettingColor> color = sgGeneral.add(new ColorSetting.Builder()
        .name("color")
        .description("The color of the text.")
        .defaultValue(new SettingColor(255, 255, 255))
        .build()
    );

    private final Setting<SettingColor> northColor = sgGeneral.add(new ColorSetting.Builder()
        .name("north-color")
        .description("Color of North.")
        .defaultValue(new SettingColor(255, 0, 0))
        .build()
    );

    private final Setting<Double> scale = sgGeneral.add(new DoubleSetting.Builder()
        .name("scale")
        .description("The scale.")
        .defaultValue(2)
        .min(1)
        .sliderRange(1, 5)
        .build()
    );

    public CompassHud(HUD hud) {
        super(hud, "compass", "Displays a compass.", true);
    }

    @Override
    public void update(HudRenderer renderer) {
        box.setSize(100 * scale.get(), 100 * scale.get());
    }

    @Override
    public void render(HudRenderer renderer) {
        double x = box.getX() + (box.width / 2);
        double y = box.getY() + (box.height / 2);

        double pitch = isInEditor() ? 120 : MathHelper.clamp(mc.player.getPitch() + 30, -90, 90);
        pitch = Math.toRadians(pitch);

        double yaw = isInEditor() ? 180 : MathHelper.wrapDegrees(mc.player.getYaw());
        yaw = Math.toRadians(yaw);

        for (Direction direction : Direction.values()) {
            String axis = mode.get() == Mode.Axis ? direction.getAxis() : direction.name();

            renderer.text(
                axis,
                (x + getX(direction, yaw)) - (renderer.textWidth(axis) / 2),
                (y + getY(direction, yaw, pitch)) - (renderer.textHeight() / 2),
                direction == Direction.N ? northColor.get() : color.get());
        }
    }

    private double getX(Direction direction, double yaw) {
        return Math.sin(getPos(direction, yaw)) * scale.get() * 40;
    }

    private double getY(Direction direction, double yaw, double pitch) {
        return Math.cos(getPos(direction, yaw)) * Math.sin(pitch) * scale.get() * 40;
    }

    private double getPos(Direction direction, double yaw) {
        return yaw + direction.ordinal() * Math.PI / 2;
    }

    public enum Mode {
        Direction("Direction"),
        Axis("Axis");

        private final String title;

        Mode(String title) {
            this.title = title;
        }

        @Override
        public String toString() {
            return title;
        }
    }

    private enum Direction {
        N("N or Z-", "Z-"),
        W("W or X-", "X-"),
        S("S or Z+", "Z+"),
        E("E or X+", "X+");

        private final String title;
        private final String axis;

        Direction(String title, String axis) {
            this.title = title;
            this.axis = axis;
        }

        public String getAxis() {
            return axis;
        }

        @Override
        public String toString() {
            return title;
        }
    }
}
