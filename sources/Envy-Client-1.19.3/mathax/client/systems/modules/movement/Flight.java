package mathax.client.systems.modules.movement;

import mathax.client.eventbus.EventHandler;
import mathax.client.events.entity.player.PlayerMoveEvent;
import mathax.client.events.packets.PacketEvent;
import mathax.client.events.world.TickEvent;
import mathax.client.mixin.PlayerMoveC2SPacketAccessor;
import mathax.client.settings.*;
import mathax.client.systems.modules.Categories;
import mathax.client.systems.modules.Module;
import mathax.client.utils.Utils;
import mathax.client.utils.player.MoveHelper;
import net.minecraft.block.AirBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.math.Vec3d;

public class Flight extends Module {
    private double lastY = Double.MAX_VALUE;
    double startHeight;

    private long lastModifiedTime = 0;

    private float lastYaw;

    private boolean flip;

    private int delayLeft;
    private int offLeft;

    private final SettingGroup sgGeneral = settings.getDefaultGroup();
    private final SettingGroup sgAntiKick = settings.createGroup("Anti Kick");

    // General
    private final Setting<Mode> mode = sgGeneral.add(new EnumSetting.Builder<Mode>()
        .name("mode")
        .description("Determines how Flight operates.")
        .defaultValue(Mode.Abilities)
        .build()
    );

    private final Setting<Double> speed = sgGeneral.add(new DoubleSetting.Builder()
        .name("speed")
        .description("Your speed when flying.")
        .defaultValue(0.1)
        .min(0.0)
        .sliderRange(0.0, 2.0)
        .visible(() -> mode.get() != Mode.Creative)
        .build()
    );

    private final Setting<Boolean> verticalSpeedMatch = sgGeneral.add(new BoolSetting.Builder()
        .name("vertical-speed-match")
        .description("Matches your vertical speed to your horizontal speed, otherwise uses vanilla ratio.")
        .defaultValue(false)
        .visible(() -> mode.get() != Mode.Creative)
        .build()
    );

    private final Setting<Boolean> instantFlight = sgGeneral.add(new BoolSetting.Builder()
        .name("fly-on-enable")
        .description("Makes you automatically fly when enabling Flight.")
        .defaultValue(false)
        .visible(() -> mode.get() == Mode.Creative)
        .build()
    );

    private final Setting<Boolean> Clipping = sgGeneral.add(new BoolSetting.Builder()
        .name("clipping")
        .description("If Should Clip")
        .defaultValue(false)
        .visible(() -> mode.get() == Mode.Vulcan)
        .build()
    );

    private final Setting<Double> ClipAmount = sgGeneral.add(new DoubleSetting.Builder()
        .name("clip-amount")
        .description("Clip Amount")
        .defaultValue(10)
        .min(1)
        .sliderRange(1, 100)
        .visible(() -> Clipping.get() && mode.get() == Mode.Vulcan)
        .build()
    );

    private final Setting<Boolean> tickEnabler = sgGeneral.add(new BoolSetting.Builder()
        .name("update-every-tick")
        .description("Updates your flying ability every tick, enable only if flying doesn't work without this turned on.")
        .defaultValue(true)
        .visible(() -> mode.get() == Mode.Creative)
        .build()
    );

    // Anti Kick

    private final Setting<AntiKickMode> antiKickMode = sgAntiKick.add(new EnumSetting.Builder<AntiKickMode>()
        .name("mode")
        .description("The mode for anti kick.")
        .defaultValue(AntiKickMode.Packet)
        .visible(() -> mode.get() != Mode.Creative)
        .build()
    );

    private final Setting<Integer> delay = sgAntiKick.add(new IntSetting.Builder()
        .name("delay")
        .description("The amount of delay, in ticks, between toggles in normal mode.")
        .defaultValue(80)
        .range(1, 5000)
        .sliderRange(1, 200)
        .visible(() -> antiKickMode.get() == AntiKickMode.Normal && mode.get() != Mode.Creative)
        .build()
    );

    private final Setting<Integer> offTime = sgAntiKick.add(new IntSetting.Builder()
        .name("off-time")
        .description("The amount of delay, in ticks, that Flight is toggled off for in normal mode.")
        .defaultValue(5)
        .range(1, 20)
        .sliderRange(1, 20)
        .visible(() -> antiKickMode.get() == AntiKickMode.Normal && mode.get() != Mode.Creative)
        .build()
    );

    private final Setting<Double> LowHealthDisable = sgGeneral.add(new DoubleSetting.Builder()
        .name("LowHealthDisable")
        .description("Disables the module when your health is below this value.")
        .defaultValue(4)
        .min(0.5)
        .sliderMax(20)
        .build()
    );

    public Flight() {
        super(Categories.Movement, Items.COMMAND_BLOCK, "flight", "Allows you to fly. No Fall is recommended with this module.");
    }

    @Override
    public boolean onActivate() {
        delayLeft = delay.get();
        offLeft = offTime.get();
        startHeight = mc.player.getY();

        if (mode.get() == Mode.Abilities && !mc.player.isSpectator()) {
            mc.player.getAbilities().flying = true;
            if (mc.player.getAbilities().creativeMode) return false;
            mc.player.getAbilities().allowFlying = true;
        } else if (mode.get() == Mode.Creative && !mc.player.getAbilities().creativeMode) {
            mc.player.getAbilities().allowFlying = true;

            if (instantFlight.get()) mc.player.getAbilities().flying = true;
        }
        return false;
    }

    @Override
    public void onDeactivate() {
        if (mode.get() == Mode.Abilities && !mc.player.isSpectator()) {
            mc.player.getAbilities().flying = false;
            mc.player.getAbilities().setFlySpeed(0.05f);
            if (mc.player.getAbilities().creativeMode) return;
            mc.player.getAbilities().allowFlying = false;
        } else if (mode.get() == Mode.Creative && !mc.player.getAbilities().creativeMode) mc.player.getAbilities().allowFlying = false;
    }

    @EventHandler
    private void onPreTick(TickEvent.Pre event) {
        if (mode.get() == Mode.Creative) {
            if (tickEnabler.get()) mc.player.getAbilities().allowFlying = true;
            return;
        }

        float currentYaw = mc.player.getYaw();

        if (mc.player.fallDistance >= 3f && currentYaw == lastYaw && mc.player.getVelocity().length() < 0.003d) {
            mc.player.setYaw(currentYaw + (flip ? 1 : -1));
            flip = !flip;
        }

        lastYaw = currentYaw;
    }

    @EventHandler
    private void onPostTick(TickEvent.Post event) {
        Block floor = mc.world.getBlockState(mc.player.getBlockPos().down()).getBlock();
        if (mode.get() == Mode.Creative) return;

        if (antiKickMode.get() == AntiKickMode.Normal && delayLeft > 0) delayLeft--;

        else if (antiKickMode.get() == AntiKickMode.Normal && delayLeft <= 0 && offLeft > 0) {
            offLeft --;

            if (mode.get() == Mode.Abilities) {
                mc.player.getAbilities().flying = false;
                mc.player.getAbilities().setFlySpeed(0.05f);
                if (mc.player.getAbilities().creativeMode) return;
                mc.player.getAbilities().allowFlying = false;
            }

            return;
        } else if (antiKickMode.get() == AntiKickMode.Normal && delayLeft <=0 && offLeft <= 0) {
            delayLeft = delay.get();
            offLeft = offTime.get();
        }

        if (mc.player.getYaw() != lastYaw) mc.player.setYaw(lastYaw);

        switch (mode.get()) {
            case Velocity -> {
                mc.player.getAbilities().flying = false;
                mc.player.airStrafingSpeed = speed.get().floatValue() * (mc.player.isSprinting() ? 15f : 10f);
                mc.player.setVelocity(0, 0, 0);
                Vec3d initialVelocity = mc.player.getVelocity();
                if (mc.options.jumpKey.isPressed()) mc.player.setVelocity(initialVelocity.add(0, speed.get() * (verticalSpeedMatch.get() ? 10f : 5f), 0));
                if (mc.options.sneakKey.isPressed()) mc.player.setVelocity(initialVelocity.subtract(0, speed.get() * (verticalSpeedMatch.get() ? 10f : 5f), 0));
            }
            case Abilities -> {
                if (mc.player.isSpectator()) return;
                mc.player.getAbilities().setFlySpeed(speed.get().floatValue());
                mc.player.getAbilities().flying = true;
                if (mc.player.getAbilities().creativeMode) return;
                mc.player.getAbilities().allowFlying = true;
            }
        }
        if (mode.get() == Mode.VulcanNew) {
            if (mc.player == null) return;

            //get the block beneath me and check if it is a block that can be stood on
            if (mc.world.getBlockState(mc.player.getBlockPos().down()).getBlock() != Blocks.AIR) {
                mc.player.setPos(mc.player.getX(), mc.player.getY() + 0.2, mc.player.getZ());
                mc.player.setVelocity(mc.player.getVelocity().x, 0, mc.player.getVelocity().z);
                mc.player.fallDistance = 0f;
                mc.player.setOnGround(false);
                if (mc.player.getVelocity().y < 0.3) {
                    mc.player.setVelocity(mc.player.getVelocity().x, 0.5, mc.player.getVelocity().z);
                }
            }
        }
    }

    @EventHandler
    private void onPlayerMove(PlayerMoveEvent event) {

        double clipHeight = startHeight - ClipAmount.get();
        //System.out.println("The Player Height is " + mc.player.getY() + "\n And the clip height is " + mc.player.getY());

        if (mc.player.fallDistance > 2) {
            mc.player.setOnGround(true);
            mc.player.fallDistance = 0f;
        }
        if (mc.player.age % 3 == 0) {
            MoveHelper.motionYPlus(0.026);
        } else {
            MoveHelper.motionY(-0.0991);
        }
        if (Clipping.get() && clipHeight == mc.player.getY()) {
            mc.player.updatePosition(mc.player.getX(), mc.player.getY() + ClipAmount.get(), mc.player.getZ());
        }
    }

    @EventHandler
    private void onSendPacket(PacketEvent.Send event) {
        if (!(event.packet instanceof PlayerMoveC2SPacket) || antiKickMode.get() != AntiKickMode.Packet || mode.get() == Mode.Creative) return;

        PlayerMoveC2SPacket packet = (PlayerMoveC2SPacket) event.packet;
        long currentTime = Utils.getCurrentTimeMillis();
        double currentY = packet.getY(Double.MAX_VALUE);
        if (currentY != Double.MAX_VALUE) {
            if (currentTime - lastModifiedTime > 1000 && lastY != Double.MAX_VALUE && mc.world.getBlockState(mc.player.getBlockPos().down()).isAir()) {
                ((PlayerMoveC2SPacketAccessor) packet).setY(lastY - 0.03130D);
                lastModifiedTime = currentTime;
            } else lastY = currentY;
        }
    }

    @EventHandler
    public void onTick() {
        if (mc.player.getHealth() < LowHealthDisable.get()) {
            toggle();
        }
    }

    public enum Mode {
        Abilities("Abilities"),
        Velocity("Velocity"),
        Creative("Creative"),
        VulcanNew("VulcanNew"),
        Vulcan("Vulcan");


        private final String title;

        Mode(String title) {
            this.title = title;
        }

        @Override
        public String toString() {
            return title;
        }
    }

    public enum AntiKickMode {
        Normal("Normal"),
        Packet("Packet"),
        None("None");

        private final String title;

        AntiKickMode(String title) {
            this.title = title;
        }

        @Override
        public String toString() {
            return title;
        }
    }
}
