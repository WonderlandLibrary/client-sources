package com.client.glowclient;

import net.minecraft.client.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import com.client.glowclient.utils.*;
import net.minecraft.client.multiplayer.*;
import java.util.*;
import java.util.function.*;
import com.client.glowclient.modules.player.*;

public final class HB
{
    private static final Minecraft b;
    
    public static boolean E(final BlockPos blockPos) {
        final Vec3d k = hd.k();
        final Vec3d vec3d = new Vec3d((Vec3i)blockPos);
        final double n = 0.5;
        final Vec3d add = vec3d.add(n, n, n);
        final EnumFacing[] values;
        final int length = (values = EnumFacing.values()).length;
        int n2;
        int i = n2 = 0;
        while (i < length) {
            final EnumFacing enumFacing = values[n2];
            final BlockPos offset;
            final Vec3d add2;
            if (pB.M(offset = blockPos.offset(enumFacing)) && k.squareDistanceTo(add2 = add.add(new Vec3d(enumFacing.getDirectionVec()).scale(0.5))) <= 36.0) {
                final boolean b = true;
                ControllerUtils.rightClickBlock(offset, enumFacing.getOpposite(), add2);
                return b;
            }
            i = ++n2;
        }
        return false;
    }
    
    public static void M(final BlockPos blockPos) {
        final Vec3d k = hd.k();
        final Vec3d vec3d = new Vec3d((Vec3i)blockPos);
        final double n = 0.5;
        final Vec3d add = vec3d.add(n, n, n);
        final double squareDistanceTo = k.squareDistanceTo(add);
        final EnumFacing[] values;
        final int length = (values = EnumFacing.values()).length;
        int n2;
        int i = n2 = 0;
        while (i < length) {
            final EnumFacing enumFacing = values[n2];
            if (k.squareDistanceTo(add.add(new Vec3d(enumFacing.getDirectionVec()).scale(0.5))) < squareDistanceTo) {
                PacketUtils.sendPacket2((Packet)new CPacketPlayerDigging(CPacketPlayerDigging$Action.START_DESTROY_BLOCK, blockPos, enumFacing));
                PacketUtils.sendPacket2((Packet)new CPacketPlayerDigging(CPacketPlayerDigging$Action.STOP_DESTROY_BLOCK, blockPos, enumFacing));
                return;
            }
            i = ++n2;
        }
    }
    
    static {
        b = Minecraft.getMinecraft();
    }
    
    public static Iterable<BlockPos> M(final int n, final O o) {
        final BlockPos blockPos = new BlockPos(hd.k());
        return (Iterable<BlockPos>)HB::M;
    }
    
    private static boolean M(final Vec3d vec3d, final double n, final O o, final BlockPos blockPos) {
        return vec3d.squareDistanceTo(new Vec3d((Vec3i)blockPos)) <= n && o.M(blockPos);
    }
    
    public static boolean e(final BlockPos blockPos) {
        final Vec3d k = hd.k();
        final Vec3d vec3d = new Vec3d((Vec3i)blockPos);
        final double n = 0.5;
        final Vec3d add = vec3d.add(n, n, n);
        final double squareDistanceTo = k.squareDistanceTo(add);
        final EnumFacing[] values;
        final int length = (values = EnumFacing.values()).length;
        int n2;
        int i = n2 = 0;
        while (i < length) {
            final EnumFacing enumFacing = values[n2];
            final Vec3d add2 = add.add(new Vec3d(enumFacing.getDirectionVec()).scale(0.5));
            final double squareDistanceTo2;
            if ((squareDistanceTo2 = k.squareDistanceTo(add2)) <= 18.0625 && squareDistanceTo2 < squareDistanceTo) {
                final WorldClient world = MinecraftHelper.getWorld();
                final Vec3d vec3d2 = k;
                final Vec3d vec3d3 = add2;
                final boolean b = true;
                final boolean b2 = false;
                if (world.rayTraceBlocks(vec3d2, vec3d3, b2, b, b2) == null) {
                    if (!hd.A(add2)) {
                        return true;
                    }
                    ControllerUtils.rightClickBlock(blockPos, enumFacing, add2);
                    kb.M();
                    try {
                        HB.b.rightClickDelayTimer = 4;
                    }
                    catch (Exception ex) {}
                    return true;
                }
            }
            i = ++n2;
        }
        return false;
    }
    
    public static void M(final Iterable<BlockPos> iterable) {
        final Vec3d k = hd.k();
        final Iterator<BlockPos> iterator = iterable.iterator();
    Label_0011:
        while (true) {
            Iterator<BlockPos> iterator2 = iterator;
        Label_0012:
            while (iterator2.hasNext()) {
                final BlockPos blockPos = iterator.next();
                final Vec3d vec3d = new Vec3d((Vec3i)blockPos);
                final double n = 0.5;
                final Vec3d add = vec3d.add(n, n, n);
                final double squareDistanceTo = k.squareDistanceTo(add);
                final EnumFacing[] values;
                final int length = (values = EnumFacing.values()).length;
                int n2;
                int i = n2 = 0;
                while (i < length) {
                    final EnumFacing enumFacing = values[n2];
                    if (k.squareDistanceTo(add.add(new Vec3d(enumFacing.getDirectionVec()).scale(0.5))) < squareDistanceTo) {
                        PacketUtils.sendPacket2((Packet)new CPacketPlayerDigging(CPacketPlayerDigging$Action.START_DESTROY_BLOCK, blockPos, enumFacing));
                        PacketUtils.sendPacket2((Packet)new CPacketPlayerDigging(CPacketPlayerDigging$Action.STOP_DESTROY_BLOCK, blockPos, enumFacing));
                        iterator2 = iterator;
                        continue Label_0012;
                    }
                    i = ++n2;
                }
                continue Label_0011;
            }
            break;
        }
    }
    
    public static boolean k(final BlockPos blockPos) {
        final Vec3d k = hd.k();
        final Vec3d vec3d = new Vec3d((Vec3i)blockPos);
        final double n = 0.5;
        final Vec3d add = vec3d.add(n, n, n);
        final double squareDistanceTo = k.squareDistanceTo(add);
        final EnumFacing[] values;
        final int length = (values = EnumFacing.values()).length;
        int n2;
        int i = n2 = 0;
        while (i < length) {
            final EnumFacing enumFacing = values[n2];
            final Vec3d add2 = add.add(new Vec3d(enumFacing.getDirectionVec()).scale(0.5));
            final Vec3d vec3d2 = k;
            final Vec3d vec3d3 = add2;
            final double squareDistanceTo2 = vec3d2.squareDistanceTo(vec3d3);
            hd.M(vec3d3);
            if (squareDistanceTo2 <= 18.0625 && squareDistanceTo2 < squareDistanceTo) {
                final WorldClient world = MinecraftHelper.getWorld();
                final Vec3d vec3d4 = k;
                final Vec3d vec3d5 = add2;
                final boolean b = true;
                final boolean b2 = false;
                if (world.rayTraceBlocks(vec3d4, vec3d5, b2, b, b2) == null) {
                    if (!HB.b.playerController.onPlayerDamageBlock(blockPos, enumFacing)) {
                        return false;
                    }
                    kb.A();
                    return true;
                }
            }
            i = ++n2;
        }
        return false;
    }
    
    private static void M(final ArrayDeque arrayDeque, final BlockPos blockPos) {
        arrayDeque.push(blockPos);
    }
    
    public static boolean A(final BlockPos blockPos) {
        final Vec3d k = hd.k();
        final Vec3d vec3d = new Vec3d((Vec3i)blockPos);
        final double n = 0.5;
        final Vec3d add = vec3d.add(n, n, n);
        final double squareDistanceTo = k.squareDistanceTo(add);
        final EnumFacing[] values;
        final int length = (values = EnumFacing.values()).length;
        int n2;
        int i = n2 = 0;
        while (i < length) {
            final EnumFacing enumFacing = values[n2];
            final BlockPos offset;
            final Vec3d vec3d2;
            final Vec3d add2;
            if (pB.M(offset = blockPos.offset(enumFacing)) && k.squareDistanceTo(add2 = add.add((vec3d2 = new Vec3d(enumFacing.getDirectionVec())).scale(0.5))) <= 18.0625 && squareDistanceTo <= k.squareDistanceTo(add.add(vec3d2))) {
                final WorldClient world = HB.b.world;
                final Vec3d vec3d3 = k;
                final Vec3d vec3d4 = add2;
                final boolean b = true;
                final boolean b2 = false;
                if (world.rayTraceBlocks(vec3d3, vec3d4, b2, b, b2) == null) {
                    final boolean b3 = true;
                    final BlockPos blockPos2 = offset;
                    final EnumFacing enumFacing2 = enumFacing;
                    hd.M(add2);
                    ControllerUtils.rightClickBlock(blockPos2, enumFacing2.getOpposite(), add2);
                    kb.M();
                    HB.b.rightClickDelayTimer = 4;
                    return b3;
                }
            }
            i = ++n2;
        }
        return false;
    }
    
    public static Iterable<BlockPos> D(final double n, final boolean b, final O o) {
        final ArrayDeque<BlockPos> arrayDeque = new ArrayDeque<BlockPos>();
        M(n, b, o).forEach((Consumer<? super BlockPos>)HB::M);
        return arrayDeque;
    }
    
    public static boolean D(final BlockPos blockPos) {
        final Vec3d k = hd.k();
        final Vec3d vec3d = new Vec3d((Vec3i)blockPos);
        final double n = 0.5;
        final Vec3d add = vec3d.add(n, n, n);
        final double squareDistanceTo = k.squareDistanceTo(add);
        final EnumFacing[] values;
        final int length = (values = EnumFacing.values()).length;
        int n2;
        int i = n2 = 0;
        while (i < length) {
            final Vec3d add2 = add.add(new Vec3d(values[n2].getDirectionVec()).scale(0.5));
            final double squareDistanceTo2;
            if ((squareDistanceTo2 = k.squareDistanceTo(add2)) <= 18.0625 && squareDistanceTo2 < squareDistanceTo) {
                final WorldClient world = MinecraftHelper.getWorld();
                final Vec3d vec3d2 = k;
                final Vec3d vec3d3 = add2;
                final boolean b = true;
                final boolean b2 = false;
                if (world.rayTraceBlocks(vec3d2, vec3d3, b2, b, b2) == null) {
                    final Vec3d vec3d4 = add2;
                    AutoTool.M(blockPos);
                    return hd.A(vec3d4) || true;
                }
            }
            i = ++n2;
        }
        return false;
    }
    
    private static Iterator M(final BlockPos blockPos, final Vec3d vec3d, final double n, final boolean b, final O o) {
        return new XC(blockPos, vec3d, n, b, o);
    }
    
    public static boolean M(final BlockPos blockPos) {
        final Vec3d k = hd.k();
        final Vec3d vec3d = new Vec3d((Vec3i)blockPos);
        final double n = 0.5;
        final Vec3d add = vec3d.add(n, n, n);
        final double squareDistanceTo = k.squareDistanceTo(add);
        final EnumFacing[] values;
        final int length = (values = EnumFacing.values()).length;
        int n2;
        int i = n2 = 0;
        while (i < length) {
            final EnumFacing enumFacing = values[n2];
            final Vec3d add2 = add.add(new Vec3d(enumFacing.getDirectionVec()).scale(0.5));
            final double squareDistanceTo2;
            if ((squareDistanceTo2 = k.squareDistanceTo(add2)) <= 36.0 && squareDistanceTo2 < squareDistanceTo) {
                final boolean b = true;
                ControllerUtils.rightClickBlock(blockPos, enumFacing, add2);
                return b;
            }
            i = ++n2;
        }
        return false;
    }
    
    public static Iterable<BlockPos> M(final double n, final boolean b, final O o) {
        final Vec3d vec3d = new Vec3d((Vec3i)new BlockPos(HB.b.player.posX, HB.b.player.posY + 1.0, HB.b.player.posZ));
        return (Iterable<BlockPos>)HB::M;
    }
    
    public static Iterable<BlockPos> M(final double n, final O o) {
        final Vec3d k = hd.k();
        final double n2 = 0.5;
        return M((int)Math.ceil(n), HB::M);
    }
    
    private static Iterator M(final BlockPos blockPos, final BlockPos blockPos2, final O o) {
        return new Yc(blockPos, blockPos2, o);
    }
    
    public HB() {
        super();
    }
}
