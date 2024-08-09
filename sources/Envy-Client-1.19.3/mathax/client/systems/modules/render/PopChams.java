package mathax.client.systems.modules.render;

import mathax.client.eventbus.EventHandler;
import mathax.client.events.packets.PacketEvent;
import mathax.client.events.render.Render3DEvent;
import mathax.client.mixininterface.IVec3d;
import mathax.client.renderer.ShapeMode;
import mathax.client.settings.*;
import mathax.client.systems.friends.Friends;
import mathax.client.systems.modules.Categories;
import mathax.client.systems.modules.Module;
import mathax.client.utils.entity.fakeplayer.FakePlayerEntity;
import mathax.client.utils.render.WireframeEntityRenderer;
import mathax.client.utils.render.color.SettingColor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.network.packet.s2c.play.EntityStatusS2CPacket;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PopChams extends Module {
    private final List<GhostPlayer> ghosts = new ArrayList<>();

    private final SettingGroup sgGeneral = settings.getDefaultGroup();
    private final SettingGroup sgRender = settings.createGroup("Render");

    private final Setting<Double> yModifier = sgGeneral.add(new DoubleSetting.Builder()
        .name("y-modifier")
        .description("How much should the Y position of the ghost change per second.")
        .defaultValue(0.75)
        .sliderRange(-4, 4)
        .build()
    );

    private final Setting<Double> scaleModifier = sgGeneral.add(new DoubleSetting.Builder()
        .name("scale-modifier")
        .description("How much should the scale of the ghost change per second.")
        .defaultValue(-0.25)
        .sliderRange(-4, 4)
        .build()
    );

    private final Setting<Boolean> onlyOne = sgGeneral.add(new BoolSetting.Builder()
        .name("only-one")
        .description("Only allow one ghost per player.")
        .defaultValue(false)
        .build()
    );

    private final Setting<Boolean> ignoreFriends = sgGeneral.add(new BoolSetting.Builder()
        .name("ignore-friends")
        .description("Stops Pop Chams rendering for friends.")
        .defaultValue(false)
        .build()
    );

    private final Setting<Double> renderTime = sgRender.add(new DoubleSetting.Builder()
        .name("render-time")
        .description("How long the ghost is rendered in seconds.")
        .defaultValue(1)
        .min(0.1)
        .sliderMax(6)
        .build()
    );

    private final Setting<Boolean> fadeOut = sgRender.add(new BoolSetting.Builder()
        .name("fade-out")
        .description("Fades out the color.")
        .defaultValue(true)
        .build()
    );

    private final Setting<ShapeMode> shapeMode = sgRender.add(new EnumSetting.Builder<ShapeMode>()
        .name("shape-mode")
        .description("Determines how the shapes are rendered.")
        .defaultValue(ShapeMode.Both)
        .build()
    );

    private final Setting<SettingColor> sideColor = sgRender.add(new ColorSetting.Builder()
        .name("side-color")
        .description("The side color.")
        .defaultValue(new SettingColor(255, 255, 255, 75))
        .build()
    );

    private final Setting<SettingColor> lineColor = sgRender.add(new ColorSetting.Builder()
        .name("line-color")
        .description("The line color.")
        .defaultValue(new SettingColor(255, 255, 255, 125))
        .build()
    );

    public PopChams() {
        super(Categories.Render, Items.TOTEM_OF_UNDYING, "pop-chams", "Renders a ghost where players pop totem.");
    }

    @Override
    public void onDeactivate() {
        synchronized (ghosts) {
            ghosts.clear();
        }
    }
    //actually, maybe the bible
    @EventHandler
    private void onReceivePacket(PacketEvent.Receive event) {
        if (!(event.packet instanceof EntityStatusS2CPacket packet)) return;
        if (packet.getStatus() != 35) return;

        Entity entity = packet.getEntity(mc.world);
        if (!(entity instanceof PlayerEntity player) || entity == mc.player) return;
        if (ignoreFriends.get() && Friends.get().isFriend(((PlayerEntity) entity))) return;

        synchronized (ghosts) {
            if (onlyOne.get()) ghosts.removeIf(ghostPlayer -> ghostPlayer.uuid.equals(entity.getUuid()));

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

            uuid = player.getUuid();
        }

        public boolean render(Render3DEvent event) {
            // Increment timer
            timer += event.frameTime;
            if (timer > renderTime.get()) return true;

            // Y Modifier
            lastRenderY = getY();
            ((IVec3d) getPos()).setY(getY() + yModifier.get() * event.frameTime);

            // Scale Modifier
            scale += scaleModifier.get() * event.frameTime;

            // Fade out
            int preSideA = sideColor.get().a;
            int preLineA = lineColor.get().a;

            if (fadeOut.get()) {
                sideColor.get().a *= 1 - timer / renderTime.get();
                lineColor.get().a *= 1 - timer / renderTime.get();
            }

            // Render
            WireframeEntityRenderer.render(event, this, scale, sideColor.get(), lineColor.get(), shapeMode.get());

            // Restore colors
            sideColor.get().a = preSideA;
            lineColor.get().a = preLineA;

            return false;
        }
    }
}
