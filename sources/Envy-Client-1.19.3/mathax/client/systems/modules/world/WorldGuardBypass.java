package mathax.client.systems.modules.world;

import mathax.client.eventbus.EventHandler;
import mathax.client.events.world.TickEvent;
import mathax.client.settings.BoolSetting;
import mathax.client.settings.Setting;
import mathax.client.settings.SettingGroup;
import mathax.client.systems.modules.Categories;
import mathax.client.systems.modules.Module;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.text.Text;

public class WorldGuardBypass extends Module {

    public WorldGuardBypass () {
        super(Categories.World, Items.WOODEN_AXE, "WGBypass", "Bypasses WorldGuards region protection.");
    }

    public SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<Boolean> FlyKickBypass = sgGeneral.add(new BoolSetting.Builder()
        .name("Anti Fly Kick")
        .description("Prevents you from getting kicked for flying.")
        .defaultValue(true)
        .build()
    );

    int tick = 0;

    @Override
    public boolean onActivate() {
        assert mc.player != null;
        mc.player.sendMessage(Text.of("Activated WGBypass"), false);
        mc.player.getAbilities().allowFlying = true;
        mc.player.getAbilities().flying = true;
        return false;
    }

    @EventHandler
    private void onTick(TickEvent.Pre event) {
        if (mc.player != null) {
            if (mc.options.forwardKey.isPressed()) {
                moveForward();
            } else if (mc.options.backKey.isPressed()) {
                moveBackward();
            } else if (mc.options.leftKey.isPressed()) {
                strafeLeft();
            } else if (mc.options.rightKey.isPressed()) {
                strafeRight();
            } else if (mc.options.jumpKey.isPressed()) {
                moveUp();
            } else if (mc.options.sneakKey.isPressed()) {
                moveDown();
            }
        }
    }

    private void moveForward() {
        assert mc.player != null;
        double yaw = Math.toRadians(mc.player.getYaw());

        double x = -Math.sin(yaw);
        double z = Math.cos(yaw);

        sendMovementPacket(x, 0, z);
    }

    private void moveBackward() {
        assert mc.player != null;
        double yaw = Math.toRadians(mc.player.getYaw());

        double x = Math.sin(yaw);
        double z = -Math.cos(yaw);

        sendMovementPacket(x, 0, z);
    }

    private void strafeLeft() {
        assert mc.player != null;
        double yaw = Math.toRadians(mc.player.getYaw() - 90);

        double x = -Math.sin(yaw);
        double z = Math.cos(yaw);

        sendMovementPacket(x, 0, z);
    }

    private void strafeRight() {
        assert mc.player != null;
        double yaw = Math.toRadians(mc.player.getYaw() + 90);

        double x = -Math.sin(yaw);
        double z = Math.cos(yaw);

        sendMovementPacket(x, 0, z);
    }

    private void moveUp() {
        sendMovementPacket(0, 0.06, 0);
    }

    private void moveDown() {
        sendMovementPacket(0, -0.06, 0);
    }

    private void sendMovementPacket(double x, double y, double z) {
        double speed = 0.06;
        double yspeed = 0.12;
        double xOffset = x * speed;
        double yOffset = y * yspeed;
        double zOffset = z * speed;

        assert mc.player != null;

        tick++;

        // fly kick bypass
        if (FlyKickBypass.get()) {
            if (tick % 20 == 0) {
                down();
                return;
            }
        }

        mc.player.networkHandler.getConnection().send(new PlayerMoveC2SPacket.PositionAndOnGround(mc.player.getX() + xOffset, mc.player.getY() + yOffset, mc.player.getZ() + zOffset, mc.player.isOnGround()));
        mc.player.networkHandler.getConnection().send(new PlayerMoveC2SPacket.PositionAndOnGround(mc.player.getX() + 420, mc.player.getY(), mc.player.getZ() + 420, mc.player.isOnGround()));
    }

    private void down() {
        assert mc.player != null;
        mc.player.setPosition(mc.player.getX(), mc.player.getY() - 0.12, mc.player.getZ());
    }
}
