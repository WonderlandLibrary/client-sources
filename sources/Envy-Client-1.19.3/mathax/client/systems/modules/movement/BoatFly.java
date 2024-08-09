package mathax.client.systems.modules.movement;

import mathax.client.eventbus.EventHandler;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import mathax.client.events.entity.BoatMoveEvent;
import mathax.client.events.packets.PacketEvent;
import mathax.client.mixininterface.IVec3d;
import mathax.client.settings.*;
import mathax.client.systems.modules.Categories;
import mathax.client.systems.modules.Module;
import mathax.client.systems.modules.experimental.TestModule;
import mathax.client.utils.player.PlayerUtils;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.network.packet.s2c.play.VehicleMoveS2CPacket;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallback;

import java.awt.event.KeyEvent;
import java.util.Objects;
//lol

public class BoatFly extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();
    private final Setting<Boolean> vulcanbypass = sgGeneral.add(new BoolSetting.Builder()
        .name("VulcanBypass")
        .description("Boat fly bypasses for vulcan.")
        .defaultValue(false)
        .build()
    );

    private final Setting<Double> speed = sgGeneral.add(new DoubleSetting.Builder()
        .name("speed")
        .description("Horizontal speed in blocks per second.")
        .defaultValue(10)
        .min(0)
        .sliderRange(0, 50)
        .visible(() -> vulcanbypass.get() == false)
        .build()
    );

    private final Setting<Double> verticalSpeed = sgGeneral.add(new DoubleSetting.Builder()
        .name("vertical-speed")
        .description("Vertical speed in blocks per second.")
        .defaultValue(6)
        .min(0)
        .sliderRange(0, 20)
        .visible(() -> vulcanbypass.get() == false)
        .build()
    );

    private final Setting<Double> fallSpeed = sgGeneral.add(new DoubleSetting.Builder()
        .name("fall-speed")
        .description("How fast you fall in blocks per second.")
        .defaultValue(0.1)
        .min(0)
        .sliderRange(0, 1)
        .visible(() -> vulcanbypass.get() == false)
        .build()
    );

    private final Setting<Boolean> cancelServerPackets = sgGeneral.add(new BoolSetting.Builder()
        .name("cancel-server-packets")
        .description("Cancels incoming boat move packets.")
        .defaultValue(false)
        .build()
    );
    private final Setting<Boolean> landBoat = sgGeneral.add(new BoolSetting.Builder()
        .name("Land Boat")
        .description("Will land your boat for you. VULCAN ONLY!")
        .defaultValue(false)
        .visible(() -> vulcanbypass.get())
        .build()
    );

    private final Setting<Boolean> spinnyMode = sgGeneral.add(new BoolSetting.Builder()
        .name("SpinnyMode")
        .description("Makes your boat spinny.")
        .defaultValue(false)
        .build()
    );
    private final Setting<Integer> speede = sgGeneral.add(new IntSetting.Builder()
        .name("Speed")
        .description("Speed in eather way")
        .defaultValue(10)
        .range(-360, 360)
        .sliderRange(-360, 360)
        .visible(() -> spinnyMode.get())
        .build()
    );
    private final Setting<BoatFly.Mode> mode = sgGeneral.add(new EnumSetting.Builder<BoatFly.Mode>()
        .name("SpinnyModes")
        .description("Send via packet or client.")
        .defaultValue(Mode.Client)
        .visible(() -> spinnyMode.get())
        .build()
    );
    private final Setting<BoatFly.Mode2> mode2 = sgGeneral.add(new EnumSetting.Builder<BoatFly.Mode2>()
        .name("Bypass Mode.")
        .description("Velocity or UpdatePOS.")
        .defaultValue(Mode2.UpdatePOS)
        .visible(() -> vulcanbypass.get())
        .build()
    );
    private final Setting<BoatFly.Mode3> mode3 = sgGeneral.add(new EnumSetting.Builder<BoatFly.Mode3>()
        .name("Boat Visibility")
        .description("Makes your boat invisible if true.")
        .defaultValue(Mode3.Visible)
        .build()
    );
    public BoatFly() {
        super(Categories.Movement, Items.OAK_BOAT, "boat-fly", "Transforms your boat into a plane.");
    }
int timer = 0;
    boolean landing = false;
    boolean verifyLanding = true;
    @EventHandler
    private void onBoatMove(BoatMoveEvent event) {
        if (mode3.get() == Mode3.Visible) {
            assert mc.player != null;
            Objects.requireNonNull(mc.player.getVehicle()).setInvisible(false);
        }
        else {
            assert mc.player != null;
            Objects.requireNonNull(mc.player.getVehicle()).setInvisible(true);
        }
        timer++;
        if (event.boat.getPrimaryPassenger() != mc.player) return;

        if (spinnyMode.get()) {
    assert mc.player != null;
    if (mode.get() == Mode.Packet) {
        mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.LookAndOnGround(Objects.requireNonNull(mc.player.getVehicle()).prevYaw + speede.get(), mc.player.getVehicle().getPitch(), mc.player.isOnGround()));

    }
    else {
        Objects.requireNonNull(mc.player.getVehicle()).setYaw(mc.player.getVehicle().prevYaw + speede.get());

    }
}
    else {
    assert mc.player != null;
    event.boat.setYaw(mc.player.getYaw());
}
if (vulcanbypass.get()) {

    timer++;
    if (mc.options.jumpKey.isPressed()) {
        if (!landing) {
            if (timer > 11) {
                mc.player.getVehicle().setVelocity(0, 0, 0);
                Objects.requireNonNull(mc.player.getVehicle()).updatePosition(mc.player.getVehicle().getX(), mc.player.getVehicle().getY() + 5, mc.player.getVehicle().getZ());
                mc.player.getVehicle().fallDistance = 0;

                timer = 0;
            }
        }
    }
    else if (mc.options.sprintKey.isPressed() && landBoat.get() || landing) {
        if (verifyLanding) {
            info("Landing your boat.");
            verifyLanding = false;
        }

        landing = true;
        if (timer > 10) {
            if (mc.player.getVehicle().isOnGround()) {
                info("Landed!");
                landing = false;
                verifyLanding = true;
            }
            mc.player.getVehicle().fallDistance = 0;
            mc.player.getVehicle().setVelocity(0, 0, 0);
            timer = 0;
        }

    }
    else {
        if (!landing) {
            if (timer > 20) {
                if (Objects.requireNonNull(mc.player.getVehicle()).isOnGround()) return;
                Objects.requireNonNull(mc.player.getVehicle()).updatePosition(mc.player.getVehicle().getX(), mc.player.getVehicle().getY() + 2.5, mc.player.getVehicle().getZ());
                mc.player.getVehicle().fallDistance = 0;
                mc.player.getVehicle().setVelocity(mc.player.getVehicle().getVelocity().x, 0, mc.player.getVehicle().getVelocity().z);

                timer = 0;
            }
        }
    }
}
else {
    landing = false;
    timer = 0;
    Vec3d vel = PlayerUtils.getHorizontalVelocity(speed.get());
    double velX = vel.getX();
    double velY = 0;
    double velZ = vel.getZ();

    if (mc.options.jumpKey.isPressed()) velY += verticalSpeed.get() / 20;
    if (mc.options.sprintKey.isPressed()) velY -= verticalSpeed.get() / 20;
    else velY -= fallSpeed.get() / 20;

    ((IVec3d) event.boat.getVelocity()).set(velX, velY, velZ);
}

    }

    @EventHandler
    private void onReceivePacket(PacketEvent.Receive event) {
        if (event.packet instanceof VehicleMoveS2CPacket && cancelServerPackets.get()) event.cancel();
    }
    public enum Mode {
        Packet("Packet"),

        Client("Client");
        private final String title;

        Mode(String title) {
            this.title = title;
        }

        @Override
        public String toString() {
            return title;
        }
    }
    public enum Mode2 {
        Velocity("Velocity"),

        UpdatePOS("UpdatePOS");
        private final String title;

        Mode2(String title) {
            this.title = title;
        }

        @Override
        public String toString() {
            return title;
        }
    }
    public enum Mode3 {
        Invisible("Invisible"),

        Visible("Visible");
        private final String title;

        Mode3(String title) {
            this.title = title;
        }

        @Override
        public String toString() {
            return title;
        }
    }

}
