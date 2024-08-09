package meteordevelopment.meteorclient.systems.modules.combat;

import meteordevelopment.meteorclient.events.entity.player.SendMovementPacketsEvent;
import meteordevelopment.meteorclient.events.packets.PacketEvent;
import meteordevelopment.meteorclient.events.render.Render3DEvent;
import meteordevelopment.meteorclient.renderer.ShapeMode;
import meteordevelopment.meteorclient.settings.*;
import meteordevelopment.meteorclient.systems.friends.Friends;
import meteordevelopment.meteorclient.systems.modules.Categories;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.systems.modules.Modules;
import meteordevelopment.meteorclient.utils.player.ChatUtils;
import meteordevelopment.meteorclient.utils.render.WireframeEntityRenderer;
import meteordevelopment.meteorclient.utils.render.color.SettingColor;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.Packet;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import static meteordevelopment.orbit.EventPriority.HIGHEST;

public final class BackTrackRise extends Module {

    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    public BackTrackRise() {
        super(Categories.Combat, "BackTrackRise", "Abuses \"lag\" to hit enemies from far away. Skidded from Rise (sry Alan)");
    }

    public final Setting<Integer> amount = sgGeneral.add(new IntSetting.Builder()
        .name("Amount")
        .description("The amount of packets to delay.")
        .defaultValue(20)
        .min(1)
        .sliderMax(100)
        .build()
    );

    public final Setting<Integer> forward = sgGeneral.add(new IntSetting.Builder()
        .name("Forward")
        .description("idk man.")
        .defaultValue(20)
        .min(1)
        .sliderMax(100)
        .build()
    );

    public final Setting<Boolean> debug = sgGeneral.add(new BoolSetting.Builder()
        .name("Debug")
        .description("Helps with Debugging!")
        .defaultValue(true)
        .build()
    );

    public final Setting<Boolean> render = sgGeneral.add(new BoolSetting.Builder()
        .name("Render")
        .description("Render shit")
        .defaultValue(true)
        .build()
    );

    private final Setting<SettingColor> color1 = sgGeneral.add(new ColorSetting.Builder()
        .name("side-color")
        .description("The side color.")
        .visible(render::get)
        .defaultValue(new SettingColor(255, 255, 255, 25))
        .build()
    );

    private LivingEntity target;
    private List<Vec3d> pastPositions = new ArrayList<>();
    private List<Vec3d> forwardPositions = new ArrayList<>();
    private List<Vec3d> positions = new ArrayList<>();
    private final Deque<Packet<?>> packets = new ArrayDeque<>();

    private boolean auraIsEnabled = Modules.get().isActive(Aura.class);
    // public boolean triggerBotEnabled = (Modules.get().isActive(TriggerBot.class) && !Modules.get().isActive(Aura.class));

    private int ticks;

    // TODO: make this shit work

    @Override
    public void onActivate() {
        //if (mc.player != null || mc.world != null || Modules.get().isActive(TriggerBot.class) || Modules.get().get(TriggerBot.class).entityResult.getEntity() != null || Modules.get().get(TriggerBot.class).entityResult.getEntity() instanceof PlayerEntity || !(Modules.get().get(TriggerBot.class).entityResult.getEntity() instanceof ArmorStandEntity) || !(Modules.get().get(TriggerBot.class).entityResult.getEntity() instanceof TntEntity))
        //    tbotEntity = Objects.requireNonNull(Modules.get().get(TriggerBot.class).entityResult.getEntity());

        super.onActivate();
    }

    @Override
    public void onDeactivate() {
        target = null;
        positions.clear();
        pastPositions.clear();
        forwardPositions.clear();
        packets.clear();
    }

    @EventHandler
    private void onPreMotion(final SendMovementPacketsEvent.Pre e) {
        if (mc.player == null || mc.world == null)
            return;

        if (mc.player.age < 5) {
            this.onDeactivate();
            return;
        }

        if (auraIsEnabled)
            target = (LivingEntity) Modules.get().get(Aura.class).getTarget();

        if (mc.player.isDead()) {target = null; return;}

        //if (triggerBotEnabled && tbotEntity instanceof PlayerEntity)
        //    target = Objects.requireNonNull((LivingEntity) Modules.get().get(TriggerBot.class).entityResult.getEntity());

        assert mc.world != null;
        for (PlayerEntity player : mc.world.getPlayers()) {
            // Check if the player is the same as the Minecraft client's player
            if (player == mc.player) continue;

            // Check if the player is in creative mode
            if (player.getAbilities().creativeMode) continue;

            // Check if the player is a friend
            if (Friends.get().isFriend(player)) continue;

            // Check if the player is closer than the specified range
            if (player.distanceTo(mc.player) >= 6) continue;

            // If all conditions pass, set the target to the current player
            target = player;
        }
        if (target != null && target.isDead())
            target = null;

        if (target != null && (target.isDead() || !target.isAlive())) target = null;

        if (target == null) return;

        pastPositions.add(new Vec3d(target.getX(), target.getY(), target.getZ()));

        final double deltaX = (target.getX() - target.prevZ * 2);
        final double deltaZ = (target.getZ() - target.prevZ * 2);

        forwardPositions.clear();
        int i = 0;
        while (forward.get() > forwardPositions.size()) {
            i++;
            forwardPositions.add(new Vec3d(target.getX() + deltaX, target.getY(), target.getZ() + deltaZ * i));
        }

        while (pastPositions.size() > amount.get()) {
            pastPositions.remove(0);
        }

        positions.clear();
        positions.addAll(forwardPositions);
        positions.addAll(pastPositions);

        ticks++;
    }

    @SuppressWarnings("unchecked")
    @EventHandler(priority = HIGHEST)
    private void onPacketSend(final PacketEvent.Receive e) {
        if (mc.player == null || mc.world == null || target == null)
            return;

        Packet<?> p = e.packet;

        packets.add(p);
        e.setCancelled(true);
        if (debug.get())
            ChatUtils.addMessage(Text.literal("Cancelled packet!"));
        if (amount.get() <= pastPositions.size()) {
            for (final Packet coolPacket : packets) coolPacket.apply(mc.getNetworkHandler());
            pastPositions.clear();
            packets.clear();
            if (debug.get())
                ChatUtils.addMessage(Text.literal("Reset the module!!"));
        }
    }

    @EventHandler
    private void onRender(final Render3DEvent e) {
        if (target != null && !positions.isEmpty()) drawBoundingBox(e, target);

        //for (int i = 0; pastPositions.size() > i; i++) {
        //    drawBoundingBox(e, );
        //    pastPositions.get(i).
        //}
    }

    private void drawBoundingBox(Render3DEvent event, Entity entity) {
        WireframeEntityRenderer.render(event, entity, 1, color1.get(), color1.get(), ShapeMode.Sides);
    }
}
