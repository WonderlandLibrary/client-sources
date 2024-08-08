package net.futureclient.client.modules.world.nuker;

import net.minecraft.block.state.IBlockState;
import net.minecraft.network.Packet;
import net.minecraft.util.EnumFacing;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.block.BlockAir;
import net.futureclient.loader.mixin.common.entity.living.player.wrapper.IPlayerControllerMP;
import net.minecraft.util.EnumHand;
import net.futureclient.client.ZH;
import net.futureclient.client.IG;
import net.minecraft.util.math.BlockPos;
import net.futureclient.client.ka;
import net.futureclient.client.Aa;
import net.futureclient.client.events.EventMotion;
import net.futureclient.client.events.Event;
import net.futureclient.client.modules.world.Nuker;
import net.futureclient.client.KF;
import net.futureclient.client.n;

public class Listener1 extends n<KF>
{
    public final Nuker k;
    
    public Listener1(final Nuker k) {
        this.k = k;
        super();
    }
    
    public void M(final Event event) {
        this.M((EventMotion)event);
    }
    
    public void M(final EventMotion eventMotion) {
        this.k.e(String.format("Nuker §7[§F%s§7]", Nuker.M(this.k).M()));
        Label_0830: {
            switch (Aa.D[((ka.la)Nuker.M(this.k).M()).ordinal()]) {
                case 1:
                case 2:
                case 3:
                    switch (Aa.k[eventMotion.M().ordinal()]) {
                        case 1: {
                            Nuker.M(this.k, false);
                            int intValue;
                            final byte b = (byte)(intValue = Nuker.M(this.k).B().intValue());
                            int i = intValue;
                            while (i >= -b) {
                                int n;
                                int j = n = -b;
                                while (j < b) {
                                    int n2;
                                    int k = n2 = -b;
                                    while (k < b) {
                                        Nuker.M(this.k, new BlockPos((int)Math.floor(Nuker.getMinecraft11().player.posX) + n, (int)Math.floor(Nuker.getMinecraft4().player.posY) + intValue, (int)Math.floor(Nuker.getMinecraft3().player.posZ) + n2));
                                        if (Nuker.getMinecraft6().player.getDistanceSq(Nuker.getMinecraft12().player.posX + n, Nuker.getMinecraft().player.posY + intValue, Nuker.getMinecraft5().player.posZ + n2) <= 0.0) {
                                            final IBlockState blockState = Nuker.getMinecraft7().world.getBlockState(Nuker.M(this.k));
                                            if (Nuker.M(this.k, blockState, blockState.getBlock())) {
                                                Nuker.M(this.k, true);
                                                if (!Nuker.M(this.k).M()) {
                                                    return;
                                                }
                                                final float[] m = ZH.M(Nuker.M(this.k), IG.M(Nuker.M(this.k)));
                                                eventMotion.M(Nuker.M(this.k, m[0]));
                                                eventMotion.e(Nuker.e(this.k, m[1]));
                                                return;
                                            }
                                        }
                                        k = ++n2;
                                    }
                                    j = ++n;
                                }
                                i = --intValue;
                            }
                            break Label_0830;
                        }
                        case 2:
                            if (!Nuker.M(this.k)) {
                                break Label_0830;
                            }
                            Nuker.getMinecraft2().player.swingArm(EnumHand.MAIN_HAND);
                            Nuker.getMinecraft1().playerController.onPlayerDamageBlock(Nuker.M(this.k), IG.M(Nuker.M(this.k)));
                            if (((IPlayerControllerMP)Nuker.getMinecraft18().playerController).getCurBlockDamageMP() >= 1.0) {
                                Nuker.M(this.k).e();
                                break;
                            }
                            break Label_0830;
                    }
                    return;
                case 4:
                    switch (Aa.k[eventMotion.M().ordinal()]) {
                        case 2:
                            if (Nuker.getMinecraft9().player.isCreative()) {
                                int n3;
                                int l = n3 = -Nuker.M(this.k).B().intValue();
                                while (l <= Nuker.M(this.k).B().intValue()) {
                                    int intValue2;
                                    int n4 = intValue2 = Nuker.M(this.k).B().intValue();
                                    while (n4 >= -Nuker.M(this.k).B().intValue()) {
                                        int n6;
                                        int n5 = n6 = -Nuker.M(this.k).B().intValue();
                                        while (n5 <= Nuker.M(this.k).B().intValue()) {
                                            final BlockPos blockPos = new BlockPos((int)(Nuker.getMinecraft16().player.posX + n3), (int)(Nuker.getMinecraft15().player.posY + intValue2), (int)(Nuker.getMinecraft14().player.posZ + n6));
                                            if (!(Nuker.getMinecraft10().world.getBlockState(blockPos).getBlock() instanceof BlockAir)) {
                                                Nuker.getMinecraft8().player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, blockPos, EnumFacing.DOWN));
                                                Nuker.getMinecraft13().player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, blockPos, EnumFacing.DOWN));
                                            }
                                            n5 = ++n6;
                                        }
                                        n4 = --intValue2;
                                    }
                                    l = ++n3;
                                }
                                break Label_0830;
                            }
                            break Label_0830;
                    }
                    break;
            }
        }
    }
}
