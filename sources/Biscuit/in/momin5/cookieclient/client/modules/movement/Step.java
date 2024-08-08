package in.momin5.cookieclient.client.modules.movement;

import in.momin5.cookieclient.CookieClient;
import in.momin5.cookieclient.api.module.Category;
import in.momin5.cookieclient.api.module.Module;
import in.momin5.cookieclient.api.module.ModuleManager;
import in.momin5.cookieclient.api.setting.settings.SettingMode;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraftforge.common.MinecraftForge;

import java.text.DecimalFormat;

public class Step extends Module {
    public Step(){
        super("Step", Category.MOVEMENT);
    }

    public SettingMode mode = register(new SettingMode("Mode",this,"Normal","Normal","Vanilla"));
    private int ticks = 0;

    @Override
    public void onEnable(){
        MinecraftForge.EVENT_BUS.register(this);
        CookieClient.EVENT_BUS.subscribe(this);
    }

    @Override
    public void onDisable(){
        CookieClient.EVENT_BUS.unsubscribe(this);
        MinecraftForge.EVENT_BUS.unregister(this);
    }

    @Override
    public void onUpdate() {
        if (mc.world == null || mc.player == null) {
            return;
        }

        if (mc.player.isInWater() || mc.player.isInLava() || mc.player.isOnLadder() || mc.gameSettings.keyBindJump.isKeyDown()) {
            return;
        }

        if (ModuleManager.isModuleEnabled(Speed.class))
            return;

        if (mode.getMode().equalsIgnoreCase("Normal")) {
            double[] dir = forward(0.1);
            boolean twofive = false;
            boolean two = false;
            boolean onefive = false;
            boolean one = false;
            if (mc.world.getCollisionBoxes(mc.player, mc.player.getEntityBoundingBox().offset(dir[0], 2.6, dir[1])).isEmpty() && !mc.world.getCollisionBoxes(mc.player, mc.player.getEntityBoundingBox().offset(dir[0], 2.4, dir[1])).isEmpty()) {
                twofive = true;
            }
            if (mc.world.getCollisionBoxes(mc.player, mc.player.getEntityBoundingBox().offset(dir[0], 2.1, dir[1])).isEmpty() && !mc.world.getCollisionBoxes(mc.player, mc.player.getEntityBoundingBox().offset(dir[0], 1.9, dir[1])).isEmpty()) {
                two = true;
            }
            if (mc.world.getCollisionBoxes(mc.player, mc.player.getEntityBoundingBox().offset(dir[0], 1.6, dir[1])).isEmpty() && !mc.world.getCollisionBoxes(mc.player, mc.player.getEntityBoundingBox().offset(dir[0], 1.4, dir[1])).isEmpty()) {
                onefive = true;
            }
            if (mc.world.getCollisionBoxes(mc.player, mc.player.getEntityBoundingBox().offset(dir[0], 1.0, dir[1])).isEmpty() && !mc.world.getCollisionBoxes(mc.player, mc.player.getEntityBoundingBox().offset(dir[0], 0.6, dir[1])).isEmpty()) {
                one = true;
            }
            if (mc.player.collidedHorizontally && (mc.player.moveForward != 0.0f || mc.player.moveStrafing != 0.0f) && mc.player.onGround) {
                if (one && 2 >= 1.0) {
                    final double[] oneOffset = {0.42, 0.753};
                    for (int i = 0; i < oneOffset.length; i++) {
                        mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + oneOffset[i], mc.player.posZ, mc.player.onGround));
                    }
                    mc.player.setPosition(mc.player.posX, mc.player.posY + 1.0, mc.player.posZ);
                    this.ticks = 1;
                }
                if (onefive && 2 >= 1.5) {
                    final double[] oneFiveOffset = {0.42, 0.75, 1.0, 1.16, 1.23, 1.2};
                    for (int i = 0; i < oneFiveOffset.length; i++) {
                        mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + oneFiveOffset[i], mc.player.posZ, mc.player.onGround));
                    }
                    mc.player.setPosition(mc.player.posX, mc.player.posY + 1.5, mc.player.posZ);
                    this.ticks = 1;
                }
                if (two && 2 >= 2.0) {
                    final double[] twoOffset = {0.42, 0.78, 0.63, 0.51, 0.9, 1.21, 1.45, 1.43};
                    for (int i = 0; i < twoOffset.length; i++) {
                        mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + twoOffset[i], mc.player.posZ, mc.player.onGround));
                    }

                    mc.player.setPosition(mc.player.posX, mc.player.posY + 2.0, mc.player.posZ);
                    this.ticks = 2;
                }
                if (twofive && 2 >= 2.5) {
                    final double[] twoFiveOffset = {0.425, 0.821, 0.699, 0.599, 1.022, 1.372, 1.652, 1.869, 2.019, 1.907};
                    for (int i = 0; i < twoFiveOffset.length; i++) {
                        mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + twoFiveOffset[i], mc.player.posZ, mc.player.onGround));
                    }

                    mc.player.setPosition(mc.player.posX, mc.player.posY + 2.5, mc.player.posZ);
                    this.ticks = 2;
                }
            }
        }
        if (mode.getMode().equalsIgnoreCase("Vanilla")) {
            DecimalFormat df = new DecimalFormat("#");
            mc.player.stepHeight = Float.parseFloat(df.format(2));
        }
    }

    public static double[] forward(final double speed) {
        float forward = mc.player.movementInput.moveForward;
        float side = mc.player.movementInput.moveStrafe;
        float yaw = mc.player.prevRotationYaw + (mc.player.rotationYaw - mc.player.prevRotationYaw) * mc.getRenderPartialTicks();
        if (forward != 0.0f) {
            if (side > 0.0f) {
                yaw += ((forward > 0.0f) ? -45 : 45);
            } else if (side < 0.0f) {
                yaw += ((forward > 0.0f) ? 45 : -45);
            }
            side = 0.0f;
            if (forward > 0.0f) {
                forward = 1.0f;
            } else if (forward < 0.0f) {
                forward = -1.0f;
            }
        }
        final double sin = Math.sin(Math.toRadians(yaw + 90.0f));
        final double cos = Math.cos(Math.toRadians(yaw + 90.0f));
        final double posX = forward * speed * cos + side * speed * sin;
        final double posZ = forward * speed * sin - side * speed * cos;
        return new double[]{posX, posZ};
    }

}
