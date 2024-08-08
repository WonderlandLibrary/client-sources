package net.futureclient.client.modules.world.fucker;

import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.client.CPacketAnimation;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.util.EnumHand;
import net.futureclient.client.bj;
import net.futureclient.client.ZH;
import net.minecraft.item.ItemSeedFood;
import net.minecraft.item.ItemSeeds;
import net.minecraft.util.EnumFacing;
import net.minecraft.init.Blocks;
import net.futureclient.client.ZG;
import net.minecraft.item.ItemHoe;
import net.futureclient.client.va;
import net.minecraft.util.math.BlockPos;
import net.futureclient.client.IG;
import net.futureclient.client.Oa;
import net.futureclient.client.events.EventMotion;
import net.futureclient.client.events.Event;
import net.futureclient.client.modules.world.Fucker;
import net.futureclient.client.KF;
import net.futureclient.client.n;

public class Listener1 extends n<KF>
{
    public final Fucker k;
    
    public Listener1(final Fucker k) {
        this.k = k;
        super();
    }
    
    public void M(final Event event) {
        this.M((EventMotion)event);
    }
    
    public void M(final EventMotion eventMotion) {
        this.k.e(String.format("Fucker §7[§F%s§7]", Fucker.M(this.k).M()));
        switch (Oa.D[eventMotion.M().ordinal()]) {
            case 1: {
                final BlockPos e = Fucker.e(this.k);
                if (Fucker.M(this.k) == null || (e != null && !Fucker.M(this.k).equals((Object)e))) {
                    Fucker.e(this.k, 0.0f);
                }
                if (Fucker.M(this.k) != null && IG.M(Fucker.M(this.k)) > Fucker.e(this.k).B().floatValue() * Fucker.e(this.k).B().floatValue()) {
                    Fucker.M(this.k, null);
                }
                Label_0339: {
                    Listener1 listener1 = null;
                    Label_0331: {
                        if (e == null) {
                            if (Fucker.M(this.k) == null) {
                                break Label_0339;
                            }
                            if (!Fucker.getMinecraft13().world.isAirBlock(Fucker.M(this.k))) {
                                if (((va.Ca)Fucker.M(this.k).M()).equals((Object)va.Ca.D) && ZG.M((Class)ItemHoe.class) != null) {
                                    if (Fucker.getMinecraft18().world.getBlockState(Fucker.M(this.k)).getBlock().equals(Blocks.FARMLAND)) {
                                        listener1 = this;
                                        break Label_0331;
                                    }
                                    break Label_0339;
                                }
                                else if (Fucker.getMinecraft2().world.getBlockState(Fucker.M(this.k).offset(EnumFacing.UP)).getBlock().equals(Blocks.AIR) && ZG.M((Class)ItemSeeds.class) != null && ZG.M((Class)ItemSeedFood.class) != null) {
                                    break Label_0339;
                                }
                            }
                        }
                        listener1 = this;
                    }
                    Fucker.M(listener1.k, e);
                }
                if (Fucker.M(this.k) != null && Fucker.b(this.k).M()) {
                    final float[] m = ZH.M(Fucker.M(this.k), IG.M(Fucker.M(this.k)));
                    eventMotion.M(Fucker.M(this.k, m[0]));
                    eventMotion.e(Fucker.b(this.k, m[1]));
                    return;
                }
                break;
            }
            case 2:
                if (Fucker.M(this.k) == null) {
                    return;
                }
                if (Fucker.e(this.k) > 0) {
                    Fucker.M(this.k);
                    return;
                }
                if (Fucker.e(this.k).M()) {
                    if (Fucker.M(this.k) != null && Fucker.M(this.k).e(Fucker.M(this.k).B().floatValue() * 1000.0f)) {
                        final RayTraceResult i = bj.M(Fucker.e(this.k), Fucker.M(this.k));
                        Fucker.getMinecraft14().player.connection.sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(Fucker.M(this.k), Fucker.M(this.k), EnumHand.MAIN_HAND, (float)i.hitVec.x, (float)i.hitVec.y, (float)i.hitVec.z));
                        Fucker.getMinecraft16().player.connection.sendPacket((Packet)new CPacketAnimation(EnumHand.MAIN_HAND));
                        Fucker.M(this.k).e();
                        return;
                    }
                    break;
                }
                else {
                    if (Fucker.b(this.k) == 0.0f) {
                        Fucker.getMinecraft20().player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, Fucker.M(this.k), Fucker.M(this.k)));
                        if (Fucker.getMinecraft11().player.capabilities.isCreativeMode || Fucker.getMinecraft5().world.getBlockState(Fucker.M(this.k)).getPlayerRelativeBlockHardness((EntityPlayer)Fucker.getMinecraft9().player, (World)Fucker.getMinecraft15().world, Fucker.M(this.k)) >= 1.0f) {
                            Fucker.e(this.k, 0.0f);
                            Fucker.getMinecraft6().player.swingArm(EnumHand.MAIN_HAND);
                            Fucker.getMinecraft17().playerController.onPlayerDestroyBlock(Fucker.M(this.k));
                            return;
                        }
                    }
                    Fucker.e(this.k, Fucker.b(this.k) + Fucker.getMinecraft8().world.getBlockState(Fucker.M(this.k)).getPlayerRelativeBlockHardness((EntityPlayer)Fucker.getMinecraft().player, (World)Fucker.getMinecraft12().world, Fucker.M(this.k)) * Fucker.M(this.k).B().floatValue());
                    Fucker.getMinecraft19().world.sendBlockBreakProgress(Fucker.getMinecraft4().player.getEntityId(), Fucker.M(this.k), (int)(Fucker.b(this.k) * 10.0f) - 1);
                    if (Fucker.b(this.k) >= 1.0f) {
                        Fucker.getMinecraft1().player.swingArm(EnumHand.MAIN_HAND);
                        Fucker.getMinecraft3().player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, Fucker.M(this.k), Fucker.M(this.k)));
                        Fucker.getMinecraft10().playerController.onPlayerDestroyBlock(Fucker.M(this.k));
                        Fucker.M(this.k, (byte)4);
                        Fucker.e(this.k, 0.0f);
                        return;
                    }
                    if (Fucker.M(this.k).M()) {
                        Fucker.getMinecraft7().player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, Fucker.M(this.k), Fucker.M(this.k)));
                        break;
                    }
                    break;
                }
                break;
        }
    }
}
