package net.futureclient.client.modules.movement.jesus;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.util.math.BlockPos;
import net.futureclient.client.IG;
import net.futureclient.client.Cb;
import net.futureclient.client.jd;
import net.futureclient.client.dd;
import net.futureclient.client.pg;
import net.futureclient.client.modules.render.Freecam;
import net.futureclient.client.events.EventMotion;
import net.futureclient.client.events.Event;
import net.futureclient.client.modules.movement.Jesus;
import net.futureclient.client.KF;
import net.futureclient.client.n;

public class Listener1 extends n<KF>
{
    public final Jesus k;
    
    public Listener1(final Jesus k) {
        this.k = k;
        super();
    }
    
    public void M(final Event event) {
        this.M((EventMotion)event);
    }
    
    public void M(final EventMotion eventMotion) {
        this.k.e(String.format("Jesus §7[§F%s§7]", Jesus.M(this.k).M()));
        final Freecam freecam = (Freecam)pg.M().M().M((Class)dd.class);
        if (Jesus.getMinecraft5().player.isDead || Jesus.getMinecraft18().player.isSneaking() || !Jesus.M(this.k).e(800L) || freecam.M()) {
            return;
        }
        Label_0542: {
            switch (jd.D[((Cb.gA)Jesus.M(this.k).M()).ordinal()]) {
                case 1:
                    switch (jd.k[eventMotion.M().ordinal()]) {
                        case 1: {
                            if (IG.M(false) && !Jesus.getMinecraft9().player.isSneaking()) {
                                Jesus.getMinecraft15().player.onGround = false;
                            }
                            final Block block = Jesus.getMinecraft16().world.getBlockState(new BlockPos((int)Math.floor(Jesus.getMinecraft20().player.posX), (int)Math.floor(Jesus.getMinecraft22().player.posY), (int)Math.floor(Jesus.getMinecraft().player.posZ))).getBlock();
                            if (Jesus.M(this.k) && !Jesus.getMinecraft26().player.capabilities.isFlying && !Jesus.getMinecraft4().player.isInWater()) {
                                if (Jesus.getMinecraft11().player.motionY < 4.24399158E-315 || Jesus.getMinecraft21().player.onGround || Jesus.getMinecraft8().player.isOnLadder()) {
                                    Jesus.M(this.k, false);
                                    return;
                                }
                                Jesus.getMinecraft17().player.motionY = Jesus.getMinecraft29().player.motionY / 0.0 + 5.941588215E-315;
                                final EntityPlayerSP player = Jesus.getMinecraft10().player;
                                player.motionY -= 1.1815343767E-314;
                            }
                            if (Jesus.getMinecraft27().player.isInWater() || Jesus.getMinecraft23().player.isInLava()) {
                                Jesus.getMinecraft2().player.motionY = 1.273197475E-314;
                                return;
                            }
                            if (!Jesus.getMinecraft25().player.isInLava() && block instanceof BlockLiquid && Jesus.getMinecraft19().player.motionY < 1.273197475E-314) {
                                Jesus.getMinecraft14().player.motionY = 0.0;
                                Jesus.M(this.k, true);
                                break;
                            }
                            break Label_0542;
                        }
                    }
                    return;
                case 2:
                    if (IG.e() && Jesus.getMinecraft31().player.fallDistance < 3.0f && !Jesus.getMinecraft3().player.isSneaking()) {
                        Jesus.getMinecraft6().player.motionY = 1.273197475E-314;
                        break;
                    }
                    break;
            }
        }
    }
}
