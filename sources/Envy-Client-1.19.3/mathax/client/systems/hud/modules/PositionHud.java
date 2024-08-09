package mathax.client.systems.hud.modules;

import mathax.client.settings.BoolSetting;
import mathax.client.settings.DoubleSetting;
import mathax.client.settings.Setting;
import mathax.client.settings.SettingGroup;
import mathax.client.systems.modules.Modules;
import mathax.client.utils.player.PlayerUtils;
import mathax.client.systems.modules.render.Freecam;
import mathax.client.systems.hud.HUD;
import mathax.client.systems.hud.HudElement;
import mathax.client.systems.hud.HudRenderer;

public class PositionHud extends HudElement {
    private final String left1 = "XYZ ";
    private double left1Width;
    private String right1;

    private String left2;
    private double left2Width;
    private String right2;

    private final SettingGroup sgGeneral = settings.getDefaultGroup();
    private final SettingGroup sgSpoof = settings.createGroup("Spoof");

    // General

    private final Setting<Boolean> accurate = sgGeneral.add(new BoolSetting.Builder()
        .name("accurate")
        .description("Shows position with decimal points.")
        .defaultValue(true)
        .build()
    );

    private final Setting<Boolean> oppositeDimension = sgGeneral.add(new BoolSetting.Builder()
        .name("opposite-dimension")
        .description("Displays the coordinates of the opposite dimension (Nether or Overworld).")
        .defaultValue(true)
        .build()
    );

    // Spoof

    private final Setting<Boolean> spoofX = sgSpoof.add(new BoolSetting.Builder()
        .name("x")
        .description("Spoofs the X coordinate.")
        .defaultValue(false)
        .build()
    );

    private final Setting<Double> xValue = sgSpoof.add(new DoubleSetting.Builder()
        .name("x-value")
        .description("Determines the X value.")
        .defaultValue(0.0)
        .min(0.0)
        .sliderRange(0.0, 30000000)
        .visible(spoofX::get)
        .build()
    );

    private final Setting<Boolean> spoofY = sgSpoof.add(new BoolSetting.Builder()
        .name("y")
        .description("Spoofs the Y coordinate.")
        .defaultValue(false)
        .build()
    );

    private final Setting<Double> yValue = sgSpoof.add(new DoubleSetting.Builder()
        .name("y-value")
        .description("Determines the Y value.")
        .defaultValue(120.0)
        .sliderRange(-64.0, 320.0)
        .visible(spoofY::get)
        .build()
    );

    private final Setting<Boolean> spoofZ = sgSpoof.add(new BoolSetting.Builder()
        .name("z")
        .description("Spoofs the Z coordinate.")
        .defaultValue(false)
        .build()
    );

    private final Setting<Double> zValue = sgSpoof.add(new DoubleSetting.Builder()
        .name("z-value")
        .description("Determines the Z value.")
        .defaultValue(0.0)
        .min(0.0)
        .sliderRange(0.0, 30000000)
        .visible(spoofZ::get)
        .build()
    );

    public PositionHud(HUD hud) {
        super(hud, "position", "Displays your coordinates in the world.", true);
    }

    @Override
    public void update(HudRenderer renderer) {
        left1Width = renderer.textWidth(left1);
        left2 = null;
        right2 = null;

        double height = renderer.textHeight();
        if (oppositeDimension.get()) height = height * 2 + 2;

        if (isInEditor()) {
            right1 = accurate.get() ? "0,0 0,0 0,0" : "0 0 0";
            if (oppositeDimension.get()) {
                left2 = "Editor XYZ ";
                right2 = right1;
            }

            double widthEditor = left1Width + renderer.textWidth(right1);

            if (left2 != null) {
                left2Width = renderer.textWidth(left2);
                widthEditor = Math.max(widthEditor, left2Width + renderer.textWidth(right2));
            }

            box.setSize(widthEditor, height);
            return;
        }

        Freecam freecam = Modules.get().get(Freecam.class);
        double x, y, z;

        if (accurate.get()) {
            if (spoofX.get()) x = xValue.get();
            else x = freecam.isActive() ? mc.gameRenderer.getCamera().getPos().x : mc.player.getX();

            if (spoofY.get()) y = yValue.get();
            else y = freecam.isActive() ? mc.gameRenderer.getCamera().getPos().y - mc.player.getEyeHeight(mc.player.getPose()) : mc.player.getY();

            if (spoofZ.get()) z = zValue.get();
            else z = freecam.isActive() ? mc.gameRenderer.getCamera().getPos().z : mc.player.getZ();

            right1 = String.format("%.1f %.1f %.1f", x, y, z);
        } else {
            if (spoofX.get()) x = xValue.get();
            else x = freecam.isActive() ? mc.gameRenderer.getCamera().getBlockPos().getX() : mc.player.getBlockX();

            if (spoofY.get()) y = yValue.get();
            else y = freecam.isActive() ? mc.gameRenderer.getCamera().getBlockPos().getY() : mc.player.getBlockY();

            if (spoofZ.get()) z = zValue.get();
            else z = freecam.isActive() ? mc.gameRenderer.getCamera().getBlockPos().getZ() : mc.player.getBlockZ();

            right1 = String.format("%d %d %d", (int) x, (int) y, (int) z);
        }

        if (oppositeDimension.get()) {
            switch (PlayerUtils.getDimension()) {
                case Overworld -> {
                    left2 = "Nether XYZ ";
                    right2 = accurate.get() ? String.format("%.1f %.1f %.1f", x / 8.0, y, z / 8.0) : String.format("%d %d %d", (int) x / 8, (int) y, (int) z / 8);
                }
                case Nether -> {
                    left2 = "Overworld XYZ ";
                    right2 = accurate.get() ? String.format("%.1f %.1f %.1f", x * 8.0, y, z * 8.0) : String.format("%d %d %d", (int) x * 8, (int) y, (int) z * 8);
                }
            }
        }

        double width = left1Width + renderer.textWidth(right1);

        if (left2 != null) {
            left2Width = renderer.textWidth(left2);
            width = Math.max(width, left2Width + renderer.textWidth(right2));
        }

        box.setSize(width, height);
    }

    @Override
    public void render(HudRenderer renderer) {
        double x = box.getX();
        double y = box.getY();

        double xOffset = box.alignX(left1Width + renderer.textWidth(right1));
        double yOffset = oppositeDimension.get() ? renderer.textHeight() + 2 : 0;

        if (left2 != null) {
            renderer.text(left2, x, y, hud.primaryColor.get());
            renderer.text(right2, x + left2Width, y, hud.secondaryColor.get());
        }

        renderer.text(left1, x + xOffset, y + yOffset, hud.primaryColor.get());
        renderer.text(right1, x + xOffset + left1Width, y + yOffset, hud.secondaryColor.get());
    }
}
