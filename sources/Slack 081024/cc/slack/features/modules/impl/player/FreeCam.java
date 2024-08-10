package cc.slack.features.modules.impl.player;

import cc.slack.events.impl.network.PacketEvent;
import cc.slack.events.impl.player.MotionEvent;
import cc.slack.features.modules.api.Category;
import cc.slack.features.modules.api.Module;
import cc.slack.features.modules.api.ModuleInfo;
import cc.slack.utils.player.MovementUtil;
import io.github.nevalackin.radbus.Listen;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.network.play.client.C03PacketPlayer;

@ModuleInfo(
        name = "FreeCam",
        category = Category.PLAYER
)
public class FreeCam extends Module {

    private EntityOtherPlayerMP entity;

    @Override
    public void onEnable() {
        entity = new EntityOtherPlayerMP(mc.theWorld, mc.thePlayer.getGameProfile());
        entity.rotationYawHead = mc.thePlayer.rotationYawHead;
        entity.renderYawOffset = mc.thePlayer.renderYawOffset;
        entity.copyLocationAndAnglesFrom(mc.thePlayer);
        mc.theWorld.addEntityToWorld(-6969, entity);
    }

    @Override
    public void onDisable() {
        mc.thePlayer.setPosition(entity.posX, entity.posY, entity.posZ);
        mc.theWorld.removeEntity(entity);
    }

    @Listen
    public void onPacket (PacketEvent event) {
        if (event.getPacket() instanceof C03PacketPlayer) {
            C03PacketPlayer p = event.getPacket();
            p.x = entity.posX;
            p.y = entity.posY;
            p.z = entity.posZ;
            p.yaw = entity.rotationYaw;
            p.pitch = entity.rotationPitch;
            event.setPacket(p);
        }
    }

    @Listen
    public void onMotion (MotionEvent event) {
        mc.thePlayer.motionY = 0;
        if(MovementUtil.isBindsMoving())
            MovementUtil.strafe(1f);
        if(mc.gameSettings.keyBindJump.isKeyDown()) MovementUtil.setVClip(0.5);
        if(mc.gameSettings.keyBindSneak.isKeyDown()) MovementUtil.setVClip(-0.5);
    }

}
