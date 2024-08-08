package net.futureclient.client;

import net.futureclient.client.modules.movement.BoatFly;
import net.minecraft.util.math.BlockPos;
import net.futureclient.client.events.EventUpdate;
import net.futureclient.client.events.Event;

public class Rb extends n<lF>
{
    public final VA k;
    
    public Rb(final VA k) {
        this.k = k;
        super();
    }
    
    public void M(final Event event) {
        this.M((EventUpdate)event);
    }
    
    public void M(final EventUpdate eventUpdate) {
        this.k.e(String.format("EntitySpeed §7[§F%s§7]", VA.M(this.k).B().floatValue()));
        if (VA.getMinecraft21().player.getRidingEntity() != null) {
            final double cos = Math.cos(Math.toRadians(VA.getMinecraft9().player.rotationYaw + 90.0f));
            final double sin = Math.sin(Math.toRadians(VA.getMinecraft8().player.rotationYaw + 90.0f));
            final BlockPos blockPos = new BlockPos(VA.getMinecraft18().player.posX + (0.0 * cos + 0.0 * sin), VA.getMinecraft22().player.posY - 1.0, VA.getMinecraft10().player.posZ + (0.0 * sin - 0.0 * cos));
            final BlockPos blockPos2 = new BlockPos(VA.getMinecraft13().player.posX + (0.0 * cos + 0.0 * sin), VA.getMinecraft11().player.posY - 0.0, VA.getMinecraft25().player.posZ + (0.0 * sin - 0.0 * cos));
            if (!VA.getMinecraft17().player.getRidingEntity().onGround && !VA.getMinecraft2().world.getBlockState(blockPos).getMaterial().blocksMovement() && !VA.getMinecraft4().world.getBlockState(blockPos2).getMaterial().blocksMovement() && this.k.k.M()) {
                ZG.M(0.0);
                VA.M(this.k).e();
                return;
            }
            if (VA.getMinecraft23().world.getBlockState(new BlockPos(VA.getMinecraft6().player.posX + (0.0 * cos + 0.0 * sin), VA.getMinecraft14().player.posY, VA.getMinecraft20().player.posZ + (0.0 * sin - 0.0 * cos))).getMaterial().blocksMovement() && this.k.k.M()) {
                ZG.M(0.0);
                VA.M(this.k).e();
                return;
            }
            if (VA.getMinecraft1().world.getBlockState(new BlockPos(VA.getMinecraft15().player.posX + (1.0 * cos + 0.0 * sin), VA.getMinecraft5().player.posY + 1.0, VA.getMinecraft().player.posZ + (1.0 * sin - 0.0 * cos))).getMaterial().blocksMovement() && this.k.k.M()) {
                ZG.M(0.0);
                VA.M(this.k).e();
                return;
            }
            if (VA.getMinecraft24().gameSettings.keyBindJump.isKeyDown()) {
                VA.e(this.k).e();
            }
            if (VA.M(this.k).M(10000L) || !this.k.k.M()) {
                final BoatFly boatFly;
                if (!VA.getMinecraft16().player.getRidingEntity().isInWater() && (boatFly = (BoatFly)pg.M().M().M((Class)dB.class)) != null && !boatFly.M() && !VA.getMinecraft12().gameSettings.keyBindJump.isKeyDown() && VA.e(this.k).e(1000L) && !IG.e()) {
                    if (VA.getMinecraft7().player.getRidingEntity().onGround) {
                        VA.getMinecraft19().player.getRidingEntity().motionY = 1.273197475E-314;
                    }
                    VA.getMinecraft3().player.getRidingEntity().motionY = 1.273197475E-314;
                }
                ZG.M((double)VA.M(this.k).B().floatValue());
                VA.M(this.k).e();
            }
        }
    }
}
