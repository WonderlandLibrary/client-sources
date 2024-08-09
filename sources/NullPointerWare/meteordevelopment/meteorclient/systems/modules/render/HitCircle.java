package meteordevelopment.meteorclient.systems.modules.render;

import meteordevelopment.meteorclient.events.render.Render3DEvent;
import meteordevelopment.meteorclient.renderer.ShapeMode;
import meteordevelopment.meteorclient.settings.*;
import meteordevelopment.meteorclient.systems.friends.Friends;
import meteordevelopment.meteorclient.systems.modules.Categories;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.systems.modules.Modules;
import meteordevelopment.meteorclient.systems.modules.combat.Aura;
import meteordevelopment.meteorclient.utils.render.RenderUtils;
import meteordevelopment.meteorclient.utils.render.WireframeEntityRenderer;
import meteordevelopment.meteorclient.utils.render.color.SettingColor;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;

public final class HitCircle extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();
    private final SettingGroup sgColors = settings.createGroup("Colors");


    private final Setting<Double> range = sgGeneral.add(new DoubleSetting.Builder()
        .name("range")
        .description("The range of the circle!")
        .defaultValue(0)
        .range(0, 6)
        .sliderMax(6)
        .build()
    );

    public final Setting<Double> height = sgGeneral.add(new DoubleSetting.Builder()
        .name("Height")
        .description("Height")
        .defaultValue(0)
        .range(0, 2)
        .sliderMax(2)
        .build()
    );

    public final Setting<Double> width = sgGeneral.add(new DoubleSetting.Builder()
        .name("Width")
        .description("Width of the line!")
        .defaultValue(0)
        .range(0, 1)
        .sliderMax(2)
        .build()
    );

    private final Setting<Boolean> auraRange = sgGeneral.add(new BoolSetting.Builder()
        .name("Aura-Range")
        .description("sets the range of the circle to the range of killaura")
        .defaultValue(false)
        .build()
    );

    private final Setting<Boolean> renderPlayers = sgColors.add(new BoolSetting.Builder()
        .name("Render-players")
        .description("Render players inside of the circle in a different color.")
        .defaultValue(false)
        .build()
    );

    private final Setting<SettingColor> color  = sgColors.add(new ColorSetting.Builder()
        .name("Color")
        .description("The color of the circle!")
        .defaultValue(new SettingColor(205, 205, 205, 127))
        .build()
    );

    private final Setting<SettingColor> playerColor  = sgColors.add(new ColorSetting.Builder()
        .name("Player-Color")
        .description("The color players should be rendered in!")
        .defaultValue(new SettingColor(205, 205, 205, 127))
        .visible(renderPlayers::get)
        .build()
    );

    private LivingEntity target;

    public HitCircle() {
        super(Categories.Render, "HitCircle", "Renders a circle!");
    }

    @EventHandler
    private void onRender(final Render3DEvent e) {
        if (mc.player == null || mc.world == null)
            return;

        RenderUtils.renderCircle(getRange(), height.get(), color.get(), mc.player, e);

        /*Vec3d pos = mc.player.getPos().subtract(RenderUtils.getInterpolationOffset(mc.player));

        double lastX = 0;
        double lastZ = getRange();
        for (int angle = 0; angle <= 360; angle += 6) {
            float cos = MathHelper.cos((float) Math.toRadians(angle));
            float sin = MathHelper.sin((float) Math.toRadians(angle));

            double x = getRange() * sin;
            double z = getRange() * cos;

            e.renderer.line(
                pos.x + lastX, pos.y + height.get(), pos.z + lastZ,
                pos.x + x, pos.y + height.get(), pos.z + z, color.get()
            );
            lastX = x;
            lastZ = z;
        }*/

        if (renderPlayers.get()) {
            if (mc.world.getPlayers().isEmpty())
                return;

            for (PlayerEntity player : mc.world.getPlayers()) {
                // Check if the player is the same as the Minecraft client's player
                if (player == mc.player) continue;

                // Check if the player is in creative mode
                if (player.getAbilities().creativeMode) continue;

                // Check if the player is a friend
                if (Friends.get().isFriend(player)) continue;

                // Check if the player is closer than the specified range
                if (player.distanceTo(mc.player) > range.get()) continue;

                // If all conditions pass, draw boundingbox on player
                drawBoundingBox(e, player);
                // break;
            }
        }
    }

    private double getRange() {
        if (auraRange.get() && Modules.get().get(Aura.class).isActive())
            Modules.get().get(Aura.class).calculateRange();

        return range.get();
    }

    private void drawBoundingBox(Render3DEvent event, Entity entity) {
        WireframeEntityRenderer.render(event, entity, 1, playerColor.get(), playerColor.get(), ShapeMode.Sides);
    }
}
