package net.futureclient.client.modules.world.autotunnel;

import net.futureclient.client.events.Event;
import net.minecraft.block.Block;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.util.EnumHand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.futureclient.loader.mixin.common.entity.living.player.wrapper.IPlayerControllerMP;
import net.futureclient.client.ZG;
import net.futureclient.client.ZH;
import net.minecraft.world.World;
import net.futureclient.client.IG;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.EnumFacing;
import net.futureclient.client.Na;
import net.futureclient.client.xa;
import net.futureclient.client.ba;
import net.futureclient.client.modules.world.Scaffold;
import net.futureclient.client.AC;
import net.futureclient.client.pg;
import net.futureclient.client.modules.miscellaneous.AutoEat;
import net.futureclient.client.events.EventMotion;
import net.futureclient.client.modules.world.AutoTunnel;
import net.futureclient.client.KF;
import net.futureclient.client.n;

public class Listener1 extends n<KF>
{
    public final AutoTunnel k;
    
    public Listener1(final AutoTunnel k) {
        this.k = k;
        super();
    }
    
    public void M(final EventMotion eventMotion) {
        final AutoEat autoEat;
        if ((autoEat = (AutoEat)pg.M().M().M((Class)AC.class)).M() && autoEat.b()) {
            return;
        }
        if (((Scaffold)pg.M().M().M((Class)ba.class)).j != null) {
            return;
        }
        if (AutoTunnel.getMinecraft64().playerController == null) {
            return;
        }
        Label_2136: {
            switch (xa.k[eventMotion.M().ordinal()]) {
                case 1:
                    switch (xa.D[((Na.oa)AutoTunnel.M(this.k).M()).ordinal()]) {
                        case 1: {
                            AutoTunnel.M(this.k, false);
                            AutoTunnel.M(this.k, AutoTunnel.getMinecraft58().player.getHorizontalFacing());
                            AutoTunnel.e(this.k, AutoTunnel.M(this.k).equals((Object)EnumFacing.EAST) || AutoTunnel.M(this.k).equals((Object)EnumFacing.WEST));
                            final int n = 5;
                            int n2 = 5;
                            final int n3 = n;
                            int i = n2;
                            while (i >= -n3) {
                                int n4;
                                int j = n4 = -n3;
                                while (j < n3) {
                                    int n5;
                                    int k = n5 = -n3;
                                    while (k < n3) {
                                        AutoTunnel.M(this.k, new BlockPos((int)Math.floor(AutoTunnel.getMinecraft35().player.posX) + (AutoTunnel.M(this.k) ? n4 : 0), (int)Math.floor(AutoTunnel.getMinecraft66().player.posY) + n2, (int)Math.floor(AutoTunnel.getMinecraft18().player.posZ) + (AutoTunnel.M(this.k) ? 0 : n5)));
                                        if (AutoTunnel.getMinecraft28().player.getDistanceSq(AutoTunnel.getMinecraft53().player.posX + n4, AutoTunnel.getMinecraft31().player.posY + n2, AutoTunnel.getMinecraft71().player.posZ + n5) <= 0.0) {
                                            final Block block = AutoTunnel.getMinecraft15().world.getBlockState(AutoTunnel.M(this.k)).getBlock();
                                            if (AutoTunnel.M(this.k).e(AutoTunnel.M(this.k).B().floatValue() * 1000.0f) && AutoTunnel.M(this.k).getY() >= AutoTunnel.getMinecraft22().player.posY && AutoTunnel.M(this.k).getY() <= AutoTunnel.getMinecraft69().player.posY + 1.0 && IG.e(AutoTunnel.M(this.k)) && !AutoTunnel.M(this.k).contains(block) && (AutoTunnel.getMinecraft76().world.getBlockState(AutoTunnel.M(this.k)).getBlockHardness((World)AutoTunnel.getMinecraft12().world, AutoTunnel.M(this.k)) != -1.0f || AutoTunnel.getMinecraft38().playerController.isInCreativeMode())) {
                                                AutoTunnel.M(this.k, true);
                                                final float[] m = ZH.M(AutoTunnel.M(this.k), IG.M(AutoTunnel.M(this.k)));
                                                eventMotion.M(AutoTunnel.e(this.k, m[0]));
                                                eventMotion.e(AutoTunnel.M(this.k, m[1]));
                                                return;
                                            }
                                        }
                                        k = ++n5;
                                    }
                                    j = ++n4;
                                }
                                i = --n2;
                            }
                            break Label_2136;
                        }
                        case 2: {
                            if (!AutoTunnel.M(this.k).e(AutoTunnel.M(this.k).B().floatValue() * 1000.0f)) {
                                break Label_2136;
                            }
                            final double cos = Math.cos(Math.toRadians(AutoTunnel.getMinecraft54().player.rotationYaw + 90.0f));
                            final double sin = Math.sin(Math.toRadians(AutoTunnel.getMinecraft9().player.rotationYaw + 90.0f));
                            final BlockPos blockPos = new BlockPos(AutoTunnel.getMinecraft43().player.posX + (1.0 * cos + 0.0 * sin), AutoTunnel.getMinecraft73().player.posY + 1.0, AutoTunnel.getMinecraft77().player.posZ + (1.0 * sin - 0.0 * cos));
                            final Block block2 = AutoTunnel.getMinecraft21().world.getBlockState(blockPos).getBlock();
                            Listener1 listener1 = null;
                            Label_0980: {
                                if (AutoTunnel.getMinecraft26().player.inventory.getCurrentItem().getMaxDamage() - AutoTunnel.getMinecraft25().player.inventory.getCurrentItem().getItemDamage() < 5) {
                                    final byte e;
                                    if ((e = ZG.e(AutoTunnel.getMinecraft67().world.getBlockState(blockPos))) == -1) {
                                        return;
                                    }
                                    if (e < 9) {
                                        AutoTunnel.getMinecraft20().player.inventory.currentItem = e;
                                        ((IPlayerControllerMP)AutoTunnel.getMinecraft46().playerController).syncCurrentPlayItemWrapper();
                                        listener1 = this;
                                        break Label_0980;
                                    }
                                    AutoTunnel.getMinecraft6().playerController.windowClick(0, (int)e, AutoTunnel.getMinecraft3().player.inventory.currentItem, ClickType.SWAP, (EntityPlayer)AutoTunnel.getMinecraft11().player);
                                }
                                listener1 = this;
                            }
                            if (!AutoTunnel.M(listener1.k).contains(block2)) {
                                AutoTunnel.getMinecraft51().player.swingArm(EnumHand.MAIN_HAND);
                                AutoTunnel.getMinecraft34().player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, blockPos, EnumFacing.DOWN));
                                AutoTunnel.getMinecraft8().player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, blockPos, EnumFacing.DOWN));
                                AutoTunnel.M(this.k).e();
                            }
                            final BlockPos blockPos2 = new BlockPos(AutoTunnel.getMinecraft().player.posX + (1.0 * cos + 0.0 * sin), AutoTunnel.getMinecraft32().player.posY, AutoTunnel.getMinecraft52().player.posZ + (1.0 * sin - 0.0 * cos));
                            if (!AutoTunnel.M(this.k).contains(AutoTunnel.getMinecraft70().world.getBlockState(blockPos2).getBlock())) {
                                AutoTunnel.getMinecraft59().player.swingArm(EnumHand.MAIN_HAND);
                                AutoTunnel.getMinecraft68().player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, blockPos2, EnumFacing.DOWN));
                                AutoTunnel.getMinecraft56().player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, blockPos2, EnumFacing.DOWN));
                                AutoTunnel.M(this.k).e();
                            }
                            final BlockPos blockPos3 = new BlockPos(AutoTunnel.getMinecraft14().player.posX + (0.0 * cos + 0.0 * sin), AutoTunnel.getMinecraft37().player.posY + 1.0, AutoTunnel.getMinecraft5().player.posZ + (0.0 * sin - 0.0 * cos));
                            if (!AutoTunnel.M(this.k).contains(AutoTunnel.getMinecraft30().world.getBlockState(blockPos3).getBlock())) {
                                AutoTunnel.getMinecraft62().player.swingArm(EnumHand.MAIN_HAND);
                                AutoTunnel.getMinecraft39().player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, blockPos3, EnumFacing.DOWN));
                                AutoTunnel.getMinecraft1().player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, blockPos3, EnumFacing.DOWN));
                                AutoTunnel.M(this.k).e();
                            }
                            final BlockPos blockPos4 = new BlockPos(AutoTunnel.getMinecraft55().player.posX + (0.0 * cos + 0.0 * sin), AutoTunnel.getMinecraft27().player.posY, AutoTunnel.getMinecraft47().player.posZ + (0.0 * sin - 0.0 * cos));
                            if (!AutoTunnel.M(this.k).contains(AutoTunnel.getMinecraft2().world.getBlockState(blockPos4).getBlock())) {
                                AutoTunnel.getMinecraft13().player.swingArm(EnumHand.MAIN_HAND);
                                AutoTunnel.getMinecraft10().player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, blockPos4, EnumFacing.DOWN));
                                AutoTunnel.getMinecraft75().player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, blockPos4, EnumFacing.DOWN));
                                AutoTunnel.M(this.k).e();
                            }
                            final BlockPos blockPos5 = new BlockPos(AutoTunnel.getMinecraft61().player.posX + (0.0 * cos + 0.0 * sin), AutoTunnel.getMinecraft65().player.posY + 1.0, AutoTunnel.getMinecraft23().player.posZ + (0.0 * sin - 0.0 * cos));
                            if (!AutoTunnel.M(this.k).contains(AutoTunnel.getMinecraft60().world.getBlockState(blockPos5).getBlock())) {
                                AutoTunnel.getMinecraft44().player.swingArm(EnumHand.MAIN_HAND);
                                AutoTunnel.getMinecraft63().player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, blockPos5, EnumFacing.DOWN));
                                AutoTunnel.getMinecraft7().player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, blockPos5, EnumFacing.DOWN));
                                AutoTunnel.M(this.k).e();
                            }
                            final BlockPos blockPos6 = new BlockPos(AutoTunnel.getMinecraft17().player.posX + (0.0 * cos + 0.0 * sin), AutoTunnel.getMinecraft24().player.posY, AutoTunnel.getMinecraft42().player.posZ + (0.0 * sin - 0.0 * cos));
                            if (!AutoTunnel.M(this.k).contains(AutoTunnel.getMinecraft45().world.getBlockState(blockPos6).getBlock())) {
                                AutoTunnel.getMinecraft33().player.swingArm(EnumHand.MAIN_HAND);
                                AutoTunnel.getMinecraft57().player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, blockPos6, EnumFacing.DOWN));
                                AutoTunnel.getMinecraft16().player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, blockPos6, EnumFacing.DOWN));
                                AutoTunnel.M(this.k).e();
                                break;
                            }
                            break Label_2136;
                        }
                    }
                    return;
                case 2:
                    if (AutoTunnel.M(this.k).M() != Na.oa.a || !AutoTunnel.e(this.k)) {
                        break;
                    }
                    if (AutoTunnel.getMinecraft41().player.inventory.getCurrentItem().getMaxDamage() - AutoTunnel.getMinecraft50().player.inventory.getCurrentItem().getItemDamage() < 5) {
                        final byte e2;
                        if ((e2 = ZG.e(AutoTunnel.getMinecraft19().world.getBlockState(AutoTunnel.M(this.k)))) == -1) {
                            return;
                        }
                        if (e2 < 9) {
                            AutoTunnel.getMinecraft36().player.inventory.currentItem = e2;
                            ((IPlayerControllerMP)AutoTunnel.getMinecraft48().playerController).syncCurrentPlayItemWrapper();
                        }
                        else {
                            AutoTunnel.getMinecraft72().playerController.windowClick(0, (int)e2, AutoTunnel.getMinecraft4().player.inventory.currentItem, ClickType.SWAP, (EntityPlayer)AutoTunnel.getMinecraft49().player);
                        }
                    }
                    AutoTunnel.getMinecraft40().player.swingArm(EnumHand.MAIN_HAND);
                    AutoTunnel.getMinecraft29().playerController.onPlayerDamageBlock(AutoTunnel.M(this.k), IG.M(AutoTunnel.M(this.k)));
                    if (((IPlayerControllerMP)AutoTunnel.getMinecraft74().playerController).getCurBlockDamageMP() >= 1.0) {
                        AutoTunnel.M(this.k).e();
                        break;
                    }
                    break;
            }
        }
    }
    
    public void M(final Event event) {
        this.M((EventMotion)event);
    }
}
