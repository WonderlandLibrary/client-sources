package xyz.mathax.mathaxclient.systems.hud.elements;

import xyz.mathax.mathaxclient.gui.renderer.OverlayRenderer;
import xyz.mathax.mathaxclient.settings.BoolSetting;
import xyz.mathax.mathaxclient.settings.DoubleSetting;
import xyz.mathax.mathaxclient.settings.Setting;
import xyz.mathax.mathaxclient.settings.SettingGroup;
import xyz.mathax.mathaxclient.systems.hud.Hud;
import xyz.mathax.mathaxclient.systems.hud.HudElement;
import xyz.mathax.mathaxclient.systems.modules.Modules;
import xyz.mathax.mathaxclient.utils.player.PlayerUtils;
import xyz.mathax.mathaxclient.systems.modules.render.Freecam;

public class PositionHudElement extends HudElement {
    private final String left1 = "XYZ ";
    private double left1Width;
    private String right1;

    private String left2;
    private double left2Width;
    private String right2;

    private final SettingGroup generalSettings = settings.createGroup("General");
    private final SettingGroup spoofSettings = settings.createGroup("Spoof");

    // General

    private final Setting<Boolean> accurateSetting = generalSettings.add(new BoolSetting.Builder()
            .name("Accurate")
            .description("Show position with decimal points.")
            .defaultValue(true)
            .build()
    );

    private final Setting<Boolean> oppositeDimensionSetting = generalSettings.add(new BoolSetting.Builder()
            .name("Opposite dimension")
            .description("Display the coordinates of the opposite dimension (Nether or Overworld).")
            .defaultValue(true)
            .build()
    );

    // Spoof

    //TODO: Ability to move while spoofing.

    private final Setting<Boolean> spoofSetting = spoofSettings.add(new BoolSetting.Builder()
            .name("Enabled")
            .description("Spoof your current position.")
            .defaultValue(false)
            .build()
    );

    private final Setting<Double> spoofXSetting = spoofSettings.add(new DoubleSetting.Builder()
            .name("X")
            .description("Determine the X value.")
            .defaultValue(0.0)
            .min(0.0)
            .sliderRange(0.0, 30000000)
            .build()
    );

    private final Setting<Double> spoofYSetting = spoofSettings.add(new DoubleSetting.Builder()
            .name("Y")
            .description("Determine the Y value.")
            .defaultValue(120.0)
            .sliderRange(-64.0, 320.0)
            .build()
    );

    private final Setting<Double> spoofZSetting = spoofSettings.add(new DoubleSetting.Builder()
            .name("Z")
            .description("Determine the Z value.")
            .defaultValue(0.0)
            .min(0.0)
            .sliderRange(0.0, 30000000)
            .build()
    );

    public PositionHudElement(Hud hud) {
        super(hud, "Position", "Displays your coordinates in the world.", true);
    }

    @Override
    public void update(OverlayRenderer renderer) {
        left1Width = renderer.textWidth(left1);
        left2 = null;
        right2 = null;

        double height = renderer.textHeight();
        if (oppositeDimensionSetting.get()) {
            height = height * 2 + 2;
        }

        if (isInEditor()) {
            right1 = accurateSetting.get() ? "0,0 0,0 0,0" : "0 0 0";
            if (oppositeDimensionSetting.get()) {
                left2 = "XYZ ";
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
        if (spoofSetting.get()) {
            x = spoofXSetting.get();
            y = spoofYSetting.get();
            z = spoofZSetting.get();
        } else {
            x = freecam.isEnabled() ? mc.gameRenderer.getCamera().getBlockPos().getX() : mc.player.getBlockX();
            y = freecam.isEnabled() ? mc.gameRenderer.getCamera().getBlockPos().getY() : mc.player.getBlockY();
            z = freecam.isEnabled() ? mc.gameRenderer.getCamera().getBlockPos().getZ() : mc.player.getBlockZ();
        }

        if (accurateSetting.get()) {
            right1 = String.format("%.1f %.1f %.1f", x, y, z);
        } else {
            right1 = String.format("%d %d %d", (int) x, (int) y, (int) z);
        }

        if (oppositeDimensionSetting.get()) {
            switch (PlayerUtils.getDimension()) {
                case Overworld -> {
                    left2 = "Nether XYZ ";
                    right2 = accurateSetting.get() ? String.format("%.1f %.1f %.1f", x / 8.0, y, z / 8.0) : String.format("%d %d %d", (int) x / 8, (int) y, (int) z / 8);
                }
                case Nether -> {
                    left2 = "Overworld XYZ ";
                    right2 = accurateSetting.get() ? String.format("%.1f %.1f %.1f", x * 8.0, y, z * 8.0) : String.format("%d %d %d", (int) x * 8, (int) y, (int) z * 8);
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
    public void render(OverlayRenderer renderer) {
        double x = box.getX();
        double y = box.getY();

        double xOffset = box.alignX(left1Width + renderer.textWidth(right1));
        double yOffset = oppositeDimensionSetting.get() ? renderer.textHeight() + 2 : 0;

        if (left2 != null) {
            renderer.text(left2, x, y, hud.primaryColorSetting.get());
            renderer.text(right2, x + left2Width, y, hud.secondaryColorSetting.get());
        }

        renderer.text(left1, x + xOffset, y + yOffset, hud.primaryColorSetting.get());
        renderer.text(right1, x + xOffset + left1Width, y + yOffset, hud.secondaryColorSetting.get());
    }
}