package xyz.gucciclient.modules.mods.combat;

import xyz.gucciclient.modules.*;
import xyz.gucciclient.values.*;
import net.minecraftforge.client.event.*;
import xyz.gucciclient.utils.*;
import net.minecraft.entity.player.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import java.util.*;

public class Hog1Arm extends Module
{
    private static NumberValue distance;
    private static final AxisAlignedBB ZERO_AABB;
    private AxisAlignedBB boundingBox;
    
    public Hog1Arm() {
        super("Reach", 0, Category.COMBAT);
        this.boundingBox = Hog1Arm.ZERO_AABB;
        this.addValue(Hog1Arm.distance);
    }
    
    @SubscribeEvent
    public void mouse(final MouseEvent event) {
        if (Wrapper.getPlayer() != null && event.buttonstate && event.button == 0 && (Wrapper.getMinecraft().objectMouseOver == null || Wrapper.getMinecraft().objectMouseOver.entityHit == null) && this.j(Hog1Arm.distance.getValue()) != null) {
            Wrapper.getPlayerController().attackEntity((EntityPlayer)Wrapper.getPlayer(), this.j(Hog1Arm.distance.getValue()));
        }
    }
    
    private Entity j(final double distance) {
        final MovingObjectPosition r = this.mc.thePlayer.rayTrace(distance, 1.0f);
        double distanceTo = distance;
        final Vec3 getPosition = this.mc.thePlayer.getPositionEyes(1.0f);
        if (r != null) {
            distanceTo = r.hitVec.distanceTo(getPosition);
        }
        final Vec3 ad = this.mc.thePlayer.getLook(1.0f);
        final Vec3 addVector = getPosition.addVector(ad.xCoord * distance, ad.yCoord * distance, ad.zCoord * distance);
        Entity entity = null;
        final List a = this.mc.theWorld.getEntitiesWithinAABBExcludingEntity((Entity)this.mc.thePlayer, this.boundingBox.addCoord(ad.xCoord * distance, ad.yCoord * distance, ad.zCoord * distance).expand(1.0, 1.0, 1.0));
        double d = distanceTo;
        for (int n3 = 0, i = 0; i < a.size(); i = ++n3) {
            final Entity entity2 = (Entity) a.get(n3);
            if (entity2.canBeCollidedWith()) {
                final Entity entity3 = entity2;
                final float getCollisionBorderSize = entity3.getCollisionBorderSize();
                final AxisAlignedBB expand = this.boundingBox.expand((double)getCollisionBorderSize, (double)getCollisionBorderSize, (double)getCollisionBorderSize);
                final MovingObjectPosition calculateIntercept = expand.calculateIntercept(getPosition, addVector);
                if (expand.isVecInside(getPosition)) {
                    if (0.0 < d || d == 0.0) {
                        entity = entity2;
                        d = 0.0;
                    }
                }
                else {
                    final double j;
                    if (calculateIntercept != null && ((j = getPosition.distanceTo(calculateIntercept.hitVec)) < d || d == 0.0)) {
                        if (entity2 == this.mc.thePlayer.ridingEntity) {
                            if (d == 0.0) {
                                entity = entity2;
                            }
                        }
                        else {
                            entity = entity2;
                            d = j;
                        }
                    }
                }
            }
        }
        return entity;
    }
    
    public static double getDistance() {
        return Hog1Arm.distance.getValue();
    }
    
    static {
        ZERO_AABB = new AxisAlignedBB(0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
        Hog1Arm.distance = new NumberValue("Range", 3.2, 3.0, 6.0);
    }
}
