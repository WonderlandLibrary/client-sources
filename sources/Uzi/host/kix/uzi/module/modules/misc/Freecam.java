package host.kix.uzi.module.modules.misc;

import com.darkmagician6.eventapi.SubscribeEvent;
import com.mojang.authlib.GameProfile;
import host.kix.uzi.events.*;
import host.kix.uzi.module.Module;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.network.play.client.C03PacketPlayer;

import java.util.List;

/**
 * Created by k1x on 4/23/17.
 */
public class Freecam extends Module {

    private double startX;
    private double startY;
    private double startZ;
    private float yaw;
    private float pitch;

    public Freecam() {
        super("Freecam", 0, Category.MISC);
    }

    @SubscribeEvent
    public void update(UpdateEvent event) {
        List boxes = this.mc.theWorld.getCollidingBoundingBoxes(this.mc.thePlayer, this.mc.thePlayer.getEntityBoundingBox().expand(0.5D, 0.5D, 0.5D));
        this.mc.thePlayer.noClip = (!boxes.isEmpty());
        if (!this.mc.thePlayer.capabilities.isFlying) {
            this.mc.thePlayer.capabilities.isFlying = true;
        }
        if (this.mc.inGameHasFocus) {
            if (this.mc.gameSettings.keyBindJump.getIsKeyPressed()) {
                this.mc.thePlayer.motionY = 0.4D;
            }
            if (this.mc.gameSettings.keyBindSneak.getIsKeyPressed()) {
                this.mc.thePlayer.motionY = -0.4D;
            }
        }
    }

    @SubscribeEvent
    public void packet(SentPacketEvent event) {
        if (((event.getPacket() instanceof C03PacketPlayer)) &&
                (!event.isCancelled())) {
            event.setCancelled(true);
        }
    }

    @SubscribeEvent
    public void motion(MotionEvent event) {
        event.x = (event.x * 2);
        event.z = (event.z * 2);
    }

    @SubscribeEvent
    public void pushOutOfBlocks(PushOutOfBlocksEvent e){
        e.setCancelled(true);
    }

    @SubscribeEvent
    public void boundingBox(BoundingBoxEvent e){
        if(mc.thePlayer.isCollided) {
            e.setBoundingBox(null);
        }
    }

    @SubscribeEvent
    public void culling(CullingEvent e){
        e.setCancelled(true);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        this.mc.renderGlobal.loadRenderers();
        this.startX = this.mc.thePlayer.posX;
        this.startY = this.mc.thePlayer.posY;
        this.startZ = this.mc.thePlayer.posZ;
        this.yaw = this.mc.thePlayer.rotationYaw;
        this.pitch = this.mc.thePlayer.rotationPitch;
        EntityOtherPlayerMP entity = new EntityOtherPlayerMP(this.mc.theWorld, new GameProfile(this.mc.thePlayer.getUniqueID(), this.mc.thePlayer.getCommandSenderEntity().getName()));
        this.mc.theWorld.addEntityToWorld(64199, entity);
        entity.setPositionAndRotation(this.startX, this.mc.thePlayer.getEntityBoundingBox().minY, this.startZ, this.yaw, this.pitch);
        entity.setSneaking(this.mc.thePlayer.isSneaking());
    }

    @Override
    public void onDisable() {
        super.onDisable();
        this.mc.renderGlobal.loadRenderers();
        this.mc.thePlayer.setPositionAndRotation(this.startX, this.startY, this.startZ, this.yaw, this.pitch);
        this.mc.thePlayer.noClip = false;
        this.mc.theWorld.removeEntityFromWorld(64199);
        this.mc.thePlayer.capabilities.isFlying = false;
    }
}
