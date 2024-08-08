package in.momin5.cookieclient.client.modules.movement;

import in.momin5.cookieclient.CookieClient;
import in.momin5.cookieclient.api.event.events.PacketEvent;
import in.momin5.cookieclient.api.event.events.PlayerMoveEvent;
import in.momin5.cookieclient.api.event.events.PlayerUpdateEvent;
import in.momin5.cookieclient.api.module.Category;
import in.momin5.cookieclient.api.module.Module;
import in.momin5.cookieclient.api.setting.settings.SettingNumber;
import in.momin5.cookieclient.api.util.utils.misc.TimerUtil;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.network.play.server.SPacketExplosion;
import net.minecraftforge.common.MinecraftForge;

public class Strafe extends Module {
    public Strafe(){
        super("Strafe", Category.MOVEMENT);
    }

    private TimerUtil knockbackTimer = new TimerUtil();

    public SettingNumber speed = register( new SettingNumber("Speed", this, 0.285, 0.001,1.000,0.001));
    public SettingNumber height = register( new SettingNumber("JumpHeight", this, 0.41, 0.01,2.00,0.01));

    @Override
    public void onEnable(){
        super.onEnable();
    }

    @EventHandler
    private Listener<PlayerUpdateEvent> onPlayerUpdate = new Listener<>(event ->  {
        if (mc.player.isRiding() || !mc.player.onGround || !mc.renderViewEntity.equals(mc.player)) {
            return;
        }
        if(isMoving(mc.player)) {
            mc.player.motionY = height.getValue();
        }
    });

    @EventHandler
    private Listener<PlayerMoveEvent> onPlayerMove = new Listener<>(event -> {
        if (!mc.renderViewEntity.equals(mc.player)) {
            return;
        }

        float playerSpeed = (float)speed.getValue();
        float moveForward = mc.player.movementInput.moveForward;
        float moveStrafe = mc.player.movementInput.moveStrafe;
        float rotationYaw = mc.player.rotationYaw;

        if (mc.player.isPotionActive(MobEffects.SPEED)) {
            final int amplifier = mc.player.getActivePotionEffect(MobEffects.SPEED).getAmplifier();
            playerSpeed *= (1.0f + 0.2f * (amplifier + 1));
        }

        if (moveForward == 0.0f && moveStrafe == 0.0f) {
            event.x = (0.0d);
            event.z = (0.0d);
        } else {
            if (moveForward != 0.0f) {
                if (moveStrafe > 0.0f) {
                    rotationYaw += ((moveForward > 0.0f) ? -45 : 45);
                } else if (moveStrafe < 0.0f) {
                    rotationYaw += ((moveForward > 0.0f) ? 45 : -45);
                }

                moveStrafe = 0.0f;
                if (moveForward > 0.0f) {
                    moveForward = 1.0f;
                } else if (moveForward < 0.0f) {
                    moveForward = -1.0f;
                }
            }

            event.x = ((moveForward * playerSpeed) * Math.cos(Math.toRadians((rotationYaw + 90.0f))) + (moveStrafe * playerSpeed) * Math.sin(Math.toRadians((rotationYaw + 90.0f))));
            event.z = ((moveForward * playerSpeed) * Math.sin(Math.toRadians((rotationYaw + 90.0f))) - (moveStrafe * playerSpeed) * Math.cos(Math.toRadians((rotationYaw + 90.0f))));
        }

        event.cancel();
    });

    @EventHandler
    private Listener<PacketEvent> packetEvent = new Listener<>(event -> {
        if (event.getPacket() instanceof SPacketExplosion) {
            SPacketExplosion packet = (SPacketExplosion)event.getPacket();

            double boostedX = packet.getMotionX();
            double boostedZ = packet.getMotionZ();
            knockbackTimer.reset();
        }
    });

    public boolean isMoving(EntityLivingBase entity) {
        return entity.moveForward != 0 || entity.moveStrafing != 0;
    }

    @Override
    public void onDisable(){
        MinecraftForge.EVENT_BUS.unregister(this);
        CookieClient.EVENT_BUS.unsubscribe(this);
    }
}
