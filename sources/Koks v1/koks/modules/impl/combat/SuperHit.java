package koks.modules.impl.combat;

import com.sun.javafx.geom.Vec3d;
import koks.event.Event;
import koks.event.impl.BlockReachEvent;
import koks.event.impl.EventUpdate;
import koks.event.impl.MouseOverEvent;
import koks.modules.Module;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;

/**
 * @author avox | lmao | kroko
 * @created on 06.09.2020 : 00:12
 */
public class SuperHit extends Module {

    public SuperHit() {
        super("SuperHit", "You hit from big distance", Category.COMBAT);
    }

    public void setPosition(double x, double y, double z) {
        mc.thePlayer.setPosition(x,y,z);
        mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(x,y,z,mc.thePlayer.onGround));
    }

    public MovingObjectPosition rayTrace(double reach) {
        Vec3 positionEyes = mc.thePlayer.getPositionEyes(1.0F);
        Vec3 lookVec = mc.thePlayer.getLookVec();
        Vec3 ray = positionEyes.addVector(lookVec.xCoord * reach, lookVec.yCoord * reach, lookVec.zCoord * reach);
        return mc.theWorld.rayTraceBlocks(positionEyes,ray,!mc.thePlayer.isInWater(), false ,false);
    }

    @Override
    public void onEvent(Event event) {
        if (event instanceof MouseOverEvent) {
            ((MouseOverEvent) event).setReach(250D);
            ((MouseOverEvent) event).setFlag(false);
        }
        if (event instanceof BlockReachEvent) {
            ((BlockReachEvent) event).setReach(250F);
        }
        if (event instanceof EventUpdate) {
            if (mc.gameSettings.keyBindAttack.pressed) {
                if (mc.objectMouseOver.entityHit != null && mc.objectMouseOver.entityHit instanceof EntityPlayer && !mc.objectMouseOver.entityHit.getName().contains("-") && !mc.objectMouseOver.entityHit.isInvisible()) {
                    MovingObjectPosition ray = rayTrace(250D);
                    if (ray == null) return;
                    Entity entity = mc.objectMouseOver.entityHit;
                    System.out.println(entity);
                    BlockPos oldPos = mc.thePlayer.getPosition();
                    BlockPos newPos = entity.getPosition();

                    double distance = mc.thePlayer.getDistance(newPos.getX(), newPos.getY(), newPos.getZ());
                    for (double d = 0; d < distance; d += 3) {
                        setPosition(mc.thePlayer.posX + (newPos.getX() - mc.thePlayer.getHorizontalFacing().getFrontOffsetX() - mc.thePlayer.posX) * d / distance, mc.thePlayer.posY + (newPos.getY() - mc.thePlayer.getHorizontalFacing().getFrontOffsetY() - mc.thePlayer.posY) * d / distance, mc.thePlayer.posZ + (newPos.getZ() - mc.thePlayer.getHorizontalFacing().getFrontOffsetZ() - mc.thePlayer.posZ) * d / distance);
                    }
                    setPosition(newPos.getX(),newPos.getY(),newPos.getZ());
                    mc.playerController.attackEntity(mc.thePlayer, entity);

                    double distanceOld = mc.thePlayer.getDistance(oldPos.getX(), oldPos.getY(), oldPos.getZ());
                    for (double d = 0; d < distanceOld; d += 3) {
                        setPosition(mc.thePlayer.posX + (oldPos.getX() - mc.thePlayer.getHorizontalFacing().getFrontOffsetX() - mc.thePlayer.posX) * d / distance, mc.thePlayer.posY + (oldPos.getY() - mc.thePlayer.getHorizontalFacing().getFrontOffsetY() - mc.thePlayer.posY) * d / distance, mc.thePlayer.posZ + (oldPos.getZ() - mc.thePlayer.getHorizontalFacing().getFrontOffsetZ() - mc.thePlayer.posZ) * d / distance);
                    }
                    setPosition(oldPos.getX(),oldPos.getY() + 0.5,oldPos.getZ());
                    setPosition(oldPos.getX(),oldPos.getY(),oldPos.getZ());
                    mc.gameSettings.keyBindAttack.pressed = false;
                }
            }
            }

    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }
}
