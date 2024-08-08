package net.futureclient.client.modules.movement.nofall;

import net.minecraft.world.World;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.init.Items;
import net.futureclient.client.lB;
import net.futureclient.client.IG;
import net.minecraft.block.BlockLiquid;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.futureclient.client.dd;
import net.futureclient.client.pg;
import net.futureclient.client.modules.render.Freecam;
import net.futureclient.client.Ic;
import net.futureclient.client.events.EventMotion;
import net.futureclient.client.events.Event;
import net.futureclient.client.modules.movement.NoFall;
import net.futureclient.client.KF;
import net.futureclient.client.n;

public class Listener2 extends n<KF>
{
    public final NoFall k;
    
    public Listener2(final NoFall k) {
        this.k = k;
        super();
    }
    
    public void M(final Event event) {
        this.M((EventMotion)event);
    }
    
    public void M(final EventMotion eventMotion) {
        this.k.e(String.format("NoFall §7[§F%s§7]", NoFall.M(this.k).M()));
        if (!((Ic.zc)NoFall.M(this.k).M()).equals((Object)Ic.zc.a)) {
            return;
        }
        final Freecam freecam = (Freecam)pg.M().M().M((Class)dd.class);
        final Vec3d positionVector = NoFall.getMinecraft9().player.getPositionVector();
        final RayTraceResult rayTraceBlocks = NoFall.getMinecraft().world.rayTraceBlocks(positionVector, new Vec3d(positionVector.x, positionVector.y - 0.0, positionVector.z), true);
        if (NoFall.getMinecraft15().player.fallDistance < 5.0f || rayTraceBlocks == null || !rayTraceBlocks.typeOfHit.equals((Object)RayTraceResult.Type.BLOCK) || NoFall.getMinecraft16().world.getBlockState(rayTraceBlocks.getBlockPos()).getBlock() instanceof BlockLiquid || !NoFall.getMinecraft10().player.inventory.hasItemStack(NoFall.M(this.k)) || IG.e() || IG.M(false) || (freecam != null && freecam.M())) {
            return;
        }
        Label_0492: {
            switch (lB.D[eventMotion.M().ordinal()]) {
                case 1: {
                    eventMotion.e(90.0f);
                    int currentItem = -1;
                    int i = 44;
                    int n = 44;
                    while (true) {
                        while (i >= 36) {
                            if (NoFall.getMinecraft17().player.inventoryContainer.getSlot(n).getStack().getItem() == Items.WATER_BUCKET) {
                                final int n2 = currentItem = n - 36;
                                if (n2 != -1) {
                                    NoFall.getMinecraft21().player.inventory.currentItem = currentItem;
                                    return;
                                }
                                break Label_0492;
                            }
                            else {
                                i = --n;
                            }
                        }
                        final int n2 = currentItem;
                        continue;
                    }
                }
                case 2: {
                    final RayTraceResult rayTraceBlocks2;
                    if ((rayTraceBlocks2 = NoFall.getMinecraft6().world.rayTraceBlocks(positionVector, new Vec3d(positionVector.x, positionVector.y - 0.0, positionVector.z), true)) != null && rayTraceBlocks2.typeOfHit.equals((Object)RayTraceResult.Type.BLOCK) && !(NoFall.getMinecraft2().world.getBlockState(rayTraceBlocks2.getBlockPos()).getBlock() instanceof BlockLiquid) && NoFall.M(this.k).e(1000L)) {
                        eventMotion.e(90.0f);
                        NoFall.getMinecraft12().playerController.processRightClick((EntityPlayer)NoFall.getMinecraft14().player, (World)NoFall.getMinecraft18().world, EnumHand.MAIN_HAND);
                        NoFall.M(this.k).e();
                        break;
                    }
                    break;
                }
            }
        }
    }
}
