package pw.latematt.xiv.mod.mods.render;

import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import org.lwjgl.input.Keyboard;
import pw.latematt.timer.Timer;
import pw.latematt.xiv.XIV;
import pw.latematt.xiv.event.Listener;
import pw.latematt.xiv.event.events.*;
import pw.latematt.xiv.mod.Mod;
import pw.latematt.xiv.mod.ModType;
import pw.latematt.xiv.mod.mods.movement.Sneak;
import pw.latematt.xiv.utils.EntityUtils;

/**
 * @author Jack
 * @author Matthew
 */
public class Freecam extends Mod {
    private final Listener packetListener, motionListener, moveListener, pushOutOfBlocksListener, cullingListener;
    private EntityOtherPlayerMP entity;
    private Timer timer = new Timer();

    public Freecam() {
        super("Freecam", ModType.RENDER, Keyboard.KEY_V);

        motionListener = new Listener<MotionUpdateEvent>() {
            @Override
            public void onEventCalled(MotionUpdateEvent event) {
                mc.thePlayer.renderArmPitch += 400.0F;

                if (timer.hasReached(7000L) && mc.thePlayer.motionX != 0 && mc.thePlayer.motionY != 0 && mc.thePlayer.motionZ != 0) {
                    mc.renderGlobal.loadRenderers();
                    timer.reset();
                }
            }
        };

        moveListener = new Listener<MotionEvent>() {
            @Override
            public void onEventCalled(MotionEvent event) {
                mc.thePlayer.noClip = true;
                double speed = 4.0D;

                if (mc.gameSettings.ofKeyBindZoom.getIsKeyPressed()) {
                    speed /= 2.0D;
                }

                event.setMotionY(0.0D);
                mc.thePlayer.motionY = 0.0D;

                event.setMotionX(event.getMotionX() * speed);
                event.setMotionZ(event.getMotionZ() * speed);

                if (mc.thePlayer.movementInput.jump) {
                    event.setMotionY(speed / 4.0D);
                } else if (mc.thePlayer.movementInput.sneak) {
                    event.setMotionY(-(speed / 4.0D));
                }

                // Update entity state
                clonePlayer(entity);
            }
        };

        packetListener = new Listener<SendPacketEvent>() {
            @Override
            public void onEventCalled(SendPacketEvent event) {
                if (entity == null) {
                    entity = new EntityOtherPlayerMP(mc.theWorld, mc.thePlayer.getGameProfile());
                    entity.copyLocationAndAnglesFrom(mc.thePlayer);
                    entity.rotationYawHead = mc.thePlayer.rotationYawHead;
                    clonePlayer(entity);
                    Sneak sneak = (Sneak) XIV.getInstance().getModManager().find("sneak");
                    entity.setSneaking(mc.thePlayer.isSneaking() || sneak != null && sneak.isEnabled());

                    mc.theWorld.addEntityToWorld(-1, entity);
                    mc.renderGlobal.loadRenderers();
                }

                if (event.getPacket() instanceof C0BPacketEntityAction) {
                    event.setCancelled(true);
                }

                if (event.getPacket() instanceof C03PacketPlayer && entity != null) {
                    C03PacketPlayer packetPlayer = (C03PacketPlayer) event.getPacket();
                    packetPlayer.setX(entity.posX);
                    packetPlayer.setY(entity.posY);
                    packetPlayer.setZ(entity.posZ);
                    packetPlayer.setYaw(entity.rotationYaw);
                    packetPlayer.setPitch(entity.rotationPitch);
                }
            }
        };

        pushOutOfBlocksListener = new Listener<PushOutOfBlocksEvent>() {
            @Override
            public void onEventCalled(PushOutOfBlocksEvent event) {
                event.setCancelled(true);
            }
        };

        cullingListener = new Listener<CullingEvent>() {
            @Override
            public void onEventCalled(CullingEvent event) {
                event.setCancelled(true);
            }
        };
    }

    @Override
    public void onEnabled() {
        if (mc.thePlayer != null) {
            entity = new EntityOtherPlayerMP(mc.theWorld, mc.thePlayer.getGameProfile());
            entity.copyLocationAndAnglesFrom(mc.thePlayer);
            entity.rotationYawHead = mc.thePlayer.rotationYawHead;
            clonePlayer(entity);

            Sneak sneak = (Sneak) XIV.getInstance().getModManager().find("sneak");
            entity.setSneaking(mc.thePlayer.isSneaking() || sneak != null && sneak.isEnabled());

            mc.theWorld.addEntityToWorld(-1, entity);
            mc.renderGlobal.loadRenderers();
        }

        XIV.getInstance().getListenerManager().add(packetListener, motionListener, moveListener, pushOutOfBlocksListener, cullingListener);

        EntityUtils.setReference(entity);
    }

    @Override
    public void onDisabled() {
        EntityUtils.setReference(mc.thePlayer);

        XIV.getInstance().getListenerManager().remove(packetListener, motionListener, moveListener, pushOutOfBlocksListener, cullingListener);

        if (mc.thePlayer != null && entity != null) {
            mc.thePlayer.noClip = false;
            mc.thePlayer.copyLocationAndAnglesFrom(entity);
            mc.theWorld.removeEntityFromWorld(-1);
            entity = null;
            mc.renderGlobal.loadRenderers();

            if (!mc.thePlayer.isSneaking()) {
                mc.getNetHandler().addToSendQueue(new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.STOP_SNEAKING));
            }
        }
    }

    public void clonePlayer(EntityOtherPlayerMP entity) {
        if (entity != null) {
            entity.swingProgress = mc.thePlayer.swingProgress;
            entity.swingProgressInt = mc.thePlayer.swingProgressInt;
            entity.isSwingInProgress = mc.thePlayer.isSwingInProgress;
            if (mc.thePlayer.getItemInUse() != null) {
                entity.setItemInUse(mc.thePlayer.getItemInUse(), mc.thePlayer.getItemInUseCount());
            }
            entity.setEating(mc.thePlayer.isEating());
            entity.setInvisible(mc.thePlayer.isInvisible());
            entity.setHealth(mc.thePlayer.getHealth());
            entity.setAbsorptionAmount(mc.thePlayer.getAbsorptionAmount());
            entity.clonePlayer(mc.thePlayer, true);
        }
    }
}
