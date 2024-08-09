package xyz.mathax.mathaxclient.systems.modules.render;

import xyz.mathax.mathaxclient.eventbus.EventHandler;
import xyz.mathax.mathaxclient.events.entity.player.TotemPopEvent;
import xyz.mathax.mathaxclient.events.render.Render3DEvent;
import xyz.mathax.mathaxclient.mixininterface.IVec3d;
import xyz.mathax.mathaxclient.renderer.ShapeMode;
import xyz.mathax.mathaxclient.systems.modules.Category;
import xyz.mathax.mathaxclient.systems.modules.Module;
import xyz.mathax.mathaxclient.utils.entity.fakeplayer.FakePlayerEntity;
import xyz.mathax.mathaxclient.utils.render.WireframeEntityRenderer;
import xyz.mathax.mathaxclient.utils.render.color.Color;
import xyz.mathax.mathaxclient.utils.render.color.SettingColor;
import net.minecraft.entity.player.PlayerEntity;
import xyz.mathax.mathaxclient.settings.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PopChams extends Module {
    private final List<GhostPlayer> ghosts = new ArrayList<>();

    private final SettingGroup generalSettings = settings.createGroup("General");

    // General

    private final Setting<Boolean> onlyOneSetting = generalSettings.add(new BoolSetting.Builder()
            .name("Only one")
            .description("Only allow one ghost per player.")
            .defaultValue(false)
            .build()
    );

    private final Setting<Double> renderTimeSetting = generalSettings.add(new DoubleSetting.Builder()
            .name("Render time")
            .description("How long the ghost is rendered in seconds.")
            .defaultValue(1)
            .min(0.1)
            .sliderMax(6)
            .build()
    );

    private final Setting<Double> yModifierSetting = generalSettings.add(new DoubleSetting.Builder()
            .name("Y modifier")
            .description("How much should the Y position of the ghost change per second.")
            .defaultValue(0.75)
            .sliderRange(-4, 4)
            .build()
    );

    private final Setting<Double> scaleModifierSetting = generalSettings.add(new DoubleSetting.Builder()
            .name("Scale modifier")
            .description("How much should the scale of the ghost change per second.")
            .defaultValue(-0.25)
            .sliderRange(-4, 4)
            .build()
    );

    private final Setting<Boolean> fadeOutSetting = generalSettings.add(new BoolSetting.Builder()
            .name("Fade out")
            .description("Fade out the color.")
            .defaultValue(true)
            .build()
    );

    private final Setting<ShapeMode> shapeModeSetting = generalSettings.add(new EnumSetting.Builder<ShapeMode>()
            .name("Shape mode")
            .description("How the shapes are rendered.")
            .defaultValue(ShapeMode.Both)
            .build()
    );

    private final Setting<SettingColor> sideColorSetting = generalSettings.add(new ColorSetting.Builder()
            .name("Side color")
            .description("The side color.")
            .defaultValue(new SettingColor(Color.WHITE, 75))
            .build()
    );

    private final Setting<SettingColor> lineColorSetting = generalSettings.add(new ColorSetting.Builder()
            .name("Line color")
            .description("The line color.")
            .defaultValue(new SettingColor(Color.WHITE, 150))
            .build()
    );

    public PopChams(Category category) {
        super(category, "Pop Chams", "Renders a ghost where players pop totem.");
    }

    @Override
    public void onDisable() {
        synchronized (ghosts) {
            ghosts.clear();
        }
    }

    @EventHandler
    private void onTotemPop(TotemPopEvent event) {
        PlayerEntity player = mc.world.getPlayerByUuid(event.player.getUuid());
        if (player == mc.player) {
            return;
        }

        synchronized (ghosts) {
            if (onlyOneSetting.get()) {
                ghosts.removeIf(ghostPlayer -> ghostPlayer.uuid.equals(player.getUuid()));
            }

            ghosts.add(new GhostPlayer(player));
        }
    }

    @EventHandler
    private void onRender3D(Render3DEvent event) {
        synchronized (ghosts) {
            ghosts.removeIf(ghostPlayer -> ghostPlayer.render(event));
        }
    }

    private class GhostPlayer extends FakePlayerEntity {
        private final UUID uuid;
        private double timer, scale = 1;

        public GhostPlayer(PlayerEntity player) {
            super(player, "ghost", 20, false);

            this.uuid = player.getUuid();
        }

        public boolean render(Render3DEvent event) {
            // Increment timer
            timer += event.frameTime;
            if (timer > renderTimeSetting.get()) {
                return true;
            }

            // Y Modifier
            lastRenderY = getY();
            ((IVec3d) getPos()).setY(getY() + yModifierSetting.get() * event.frameTime);

            // Scale Modifier
            scale += scaleModifierSetting.get() * event.frameTime;

            // Fade out
            int preSideA = sideColorSetting.get().a;
            int preLineA = lineColorSetting.get().a;
            if (fadeOutSetting.get()) {
                sideColorSetting.get().a *= 1 - timer / renderTimeSetting.get();
                lineColorSetting.get().a *= 1 - timer / renderTimeSetting.get();
            }

            // Render
            WireframeEntityRenderer.render(event, this, scale, sideColorSetting.get(), lineColorSetting.get(), shapeModeSetting.get());

            // Restore colors
            sideColorSetting.get().a = preSideA;
            lineColorSetting.get().a = preLineA;

            return false;
        }
    }
}
