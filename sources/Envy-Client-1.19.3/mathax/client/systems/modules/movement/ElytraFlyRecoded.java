package mathax.client.systems.modules.movement;

import mathax.client.eventbus.EventHandler;
import mathax.client.events.world.TickEvent;
import mathax.client.settings.*;
import mathax.client.systems.modules.Categories;
import mathax.client.systems.modules.Module;
import net.minecraft.entity.MovementType;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.math.Vec3d;

//When I started this only me and god knew how it worked, now only god knows how it works.
public class ElytraFlyRecoded extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();
    private double lockY = 0;

    //TODO: Make this anti bullshit.
    public ElytraFlyRecoded() {
        super(Categories.Movement, Items.ELYTRA, "ElytraFlyRecoded", "Port of Vayze Clients Elytra Fly! | EXPERIMENTAL");
    }

    private boolean isFrozen = false;


    private final Setting<Integer> multiplier = sgGeneral.add(new IntSetting.Builder()
        .name("Speed")
        .description("Speed of elytra")
        .defaultValue(1)
        .range(1, 10)
        .sliderRange(1, 10)
        .build()
    );

    private final Setting<Boolean> ylock = sgGeneral.add(new BoolSetting.Builder()
        .name("Lock Y")
        .description("Locks Y position to the Y you started at")
        .defaultValue(false)
        .build()
    );
    private final Setting<Integer> leftspeed = sgGeneral.add(new IntSetting.Builder()
        .name("Left Speed")
        .description("Left Speed of elytra")
        .defaultValue(1)
        .range(1, 10)
        .sliderRange(1, 10)
        .build()
    );
    private final Setting<Integer> rightspeed = sgGeneral.add(new IntSetting.Builder()
        .name("Right Speed")
        .description("Right Speed of elytra")
        .defaultValue(1)
        .range(1, 10)
        .sliderRange(1, 10)
        .build()
    );
    private final Setting<Integer> descendspeed = sgGeneral.add(new IntSetting.Builder()
        .name("Descend Speed")
        .description("Movement down Speed of elytra")
        .defaultValue(1)
        .range(1, 10)
        .sliderRange(1, 10)
        .build()
    );

    private final Setting<Boolean> spin = sgGeneral.add(new BoolSetting.Builder()
        .name("Spin")
        .description("Spinny Elytra")
        .defaultValue(false)
        .build()
    );

    private final Setting<Mode> mode = sgGeneral.add(new EnumSetting.Builder<Mode>()
        .name("mode")
        .description("How to treat the lava.")
        .defaultValue(Mode.CursorLock)
        .build()
    );


    @EventHandler
    public boolean onActivate() {
        lockY = mc.player.getY(); //How the fuck does this work
        return false; //hes prob gay
    }

    @EventHandler
    private void onTick() {
        if (mc.player.isOnGround()) {

            mc.player.jump();
            mc.player.startFallFlying();
        }
    }


    @EventHandler
    private boolean onTick(TickEvent.Pre event) { //Why the fuck does this need Post tick D:
        assert mc.player != null;
        boolean grounded = !mc.player.isOnGround();
        if (mc.player.isOnGround()) {
            grounded = mc.player.isOnGround();
        } else if (!mc.player.isOnGround()) {
            grounded = !mc.player.isOnGround();
        }

        if (mc.player.isFallFlying()) { //Fuck this shit

            if (mc.options.jumpKey.isPressed()) {

                float pitchFloatPacket = -90f;
                assert mc.player != null;
                mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.LookAndOnGround(mc.player.getYaw(), pitchFloatPacket, !mc.player.isOnGround()));
                if (!isFrozen) {
                    // Freeze the player's Y-axis movement
                    mc.player.setVelocity(Vec3d.ZERO);
                    isFrozen = true;
                } else {
                    // Allow Y-axis movement when space bar is released
                    isFrozen = false;
                }
            }


        }


        return false;
    }

    @EventHandler
    private void onTick(TickEvent.Post event) {
        assert mc.player != null;
        if (mc.player.isFallFlying()) {
            if (ylock.get()) { //This is absolute bullshit
                mc.player.setPos(mc.player.getX(), lockY, mc.player.getZ());
            }
            if (spin.get()) {
                mc.player.bodyYaw = mc.player.prevBodyYaw + 2;
                mc.player.headYaw = mc.player.prevHeadYaw + 2;
            }
            boolean moveForward = mc.options.forwardKey.isPressed();
            boolean moveBackward = mc.options.backKey.isPressed();
            boolean moveLeft = mc.options.leftKey.isPressed();
            boolean moveRight = mc.options.rightKey.isPressed();

            if (moveForward && (moveLeft || moveRight)) {
                float yaw = mc.player.getYaw(); // Get the player's yaw rotation
                double radian;
                if (moveLeft) {
                    radian = Math.toRadians(yaw - 45.0); // Calculate the diagonal angle for forward and left
                } else {
                    radian = Math.toRadians(yaw + 45.0); // Calculate the diagonal angle for forward and right
                }

                double speed = multiplier.get(); // Adjust the speed as desired
                double velX = -Math.sin(radian) * speed; // Calculate the x component of velocity
                double velZ = Math.cos(radian) * speed; // Calculate the z component of velocity

                mc.player.setVelocity(velX, mc.player.getVelocity().y, velZ);
            } else if (moveForward) {
                if (mode.get() == Mode.CursorLock) {
                    Vec3d velocity = mc.player.getRotationVector().multiply(multiplier.get()); // Adjust the speed by changing the multiplier
                    mc.player.setVelocity(velocity.x, velocity.y, velocity.z);
                } else {
                    Vec3d forward = mc.player.getRotationVector(); // Get the player's forward direction vector
                    double speed = multiplier.get(); // Adjust the speed as desired
                    double velX = forward.x * speed; // Calculate the x component of velocity
                    double velZ = forward.z * speed; // Calculate the z component of velocity
                    mc.player.setVelocity(velX, 0.0, velZ);
                }
            } else if (moveBackward && (moveLeft || moveRight)) {
                float yaw = mc.player.getYaw(); // Get the player's yaw rotation
                double radian;
                if (moveLeft) {
                    radian = Math.toRadians(yaw + 45.0); // Calculate the diagonal angle for backward and left
                } else {
                    radian = Math.toRadians(yaw - 45.0); // Calculate the diagonal angle for backward and right
                }

                double speed = multiplier.get(); // Adjust the speed as desired
                double velX = Math.sin(radian) * speed; // Calculate the x component of velocity
                double velZ = -Math.cos(radian) * speed; // Calculate the z component of velocity

                mc.player.setVelocity(velX, mc.player.getVelocity().y, velZ);
            }

            if (mc.options.sneakKey.isPressed()) {
                mc.player.setVelocity(mc.player.getVelocity().x, descendspeed.get() * -1, mc.player.getVelocity().z);
            }
            else {
                mc.player.setNoGravity(false);
            }
        } else {
            mc.player.setNoGravity(false);
        }
    }
    public enum Mode {
        CursorLock("CursorLock"),
        YLock("Y-Lock");
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
