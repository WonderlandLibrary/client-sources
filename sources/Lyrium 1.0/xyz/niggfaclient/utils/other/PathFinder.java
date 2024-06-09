// 
// Decompiled by Procyon v0.5.36
// 

package xyz.niggfaclient.utils.other;

import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.block.Block;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.network.Packet;
import xyz.niggfaclient.utils.network.PacketUtil;
import net.minecraft.util.EnumFacing;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.block.BlockFence;
import net.minecraft.block.material.Material;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;
import java.util.ArrayList;
import net.minecraft.client.Minecraft;

public class PathFinder
{
    public static Minecraft mc;
    public static double x;
    public static double y;
    public static double z;
    public static double xPreEn;
    public static double yPreEn;
    public static double zPreEn;
    public static double xPre;
    public static double yPre;
    public static double zPre;
    
    public static boolean tpToLocation(final double range, final double maxXZTP, final double maxYTP, final ArrayList<Vec3> positionsBack, final ArrayList<Vec3> positions, final BlockPos targetBlockPos) {
        positions.clear();
        positionsBack.clear();
        final double step = maxXZTP / range;
        int steps = 0;
        for (int i = 0; i < range; ++i) {
            ++steps;
            if (maxXZTP * steps > range) {
                break;
            }
        }
        final double posX = targetBlockPos.getX() + 0.5;
        final double posY = targetBlockPos.getY() + 1.0;
        final double posZ = targetBlockPos.getZ() + 0.5;
        PathFinder.xPreEn = posX;
        PathFinder.yPreEn = posY;
        PathFinder.zPreEn = posZ;
        PathFinder.xPre = PathFinder.mc.thePlayer.posX;
        PathFinder.yPre = PathFinder.mc.thePlayer.posY;
        PathFinder.zPre = PathFinder.mc.thePlayer.posZ;
        boolean up = false;
        MovingObjectPosition rayTrace1 = null;
        if (rayTraceWide(new Vec3(PathFinder.mc.thePlayer.posX, PathFinder.mc.thePlayer.posY, PathFinder.mc.thePlayer.posZ), new Vec3(posX, posY, posZ), false, false, true) || (rayTrace1 = rayTracePos(new Vec3(PathFinder.mc.thePlayer.posX, PathFinder.mc.thePlayer.posY + PathFinder.mc.thePlayer.getEyeHeight(), PathFinder.mc.thePlayer.posZ), new Vec3(posX, posY + PathFinder.mc.thePlayer.getEyeHeight(), posZ), false, false, true)) != null) {
            final MovingObjectPosition rayTrace2;
            if ((rayTrace2 = rayTracePos(new Vec3(PathFinder.mc.thePlayer.posX, PathFinder.mc.thePlayer.posY, PathFinder.mc.thePlayer.posZ), new Vec3(posX, PathFinder.mc.thePlayer.posY, posZ), false, false, true)) != null || (rayTrace1 = rayTracePos(new Vec3(PathFinder.mc.thePlayer.posX, PathFinder.mc.thePlayer.posY + PathFinder.mc.thePlayer.getEyeHeight(), PathFinder.mc.thePlayer.posZ), new Vec3(posX, PathFinder.mc.thePlayer.posY + PathFinder.mc.thePlayer.getEyeHeight(), posZ), false, false, true)) != null) {
                MovingObjectPosition trace = null;
                if (rayTrace2 == null) {
                    trace = rayTrace1;
                }
                if (rayTrace1 == null) {
                    trace = rayTrace2;
                }
                if (trace != null) {
                    if (trace.getBlockPos() == null) {
                        return false;
                    }
                    final BlockPos target = trace.getBlockPos();
                    up = true;
                    PathFinder.y = target.up().getY();
                    PathFinder.yPreEn = target.up().getY();
                    Block lastBlock = null;
                    boolean found = false;
                    for (int j = 0; j < maxYTP; ++j) {
                        final MovingObjectPosition tr = rayTracePos(new Vec3(PathFinder.mc.thePlayer.posX, target.getY() + j, PathFinder.mc.thePlayer.posZ), new Vec3(posX, target.getY() + j, posZ), false, false, true);
                        if (tr != null) {
                            if (tr.getBlockPos() != null) {
                                final BlockPos blockPos = tr.getBlockPos();
                                final Block block = PathFinder.mc.theWorld.getBlockState(blockPos).getBlock();
                                if (block.getMaterial() == Material.air) {
                                    final boolean fence = lastBlock instanceof BlockFence;
                                    PathFinder.y = target.getY() + j;
                                    PathFinder.yPreEn = target.getY() + j;
                                    if (fence) {
                                        ++PathFinder.y;
                                        ++PathFinder.yPreEn;
                                        if (j + 1 > maxYTP) {
                                            found = false;
                                            break;
                                        }
                                    }
                                    found = true;
                                    break;
                                }
                                lastBlock = block;
                            }
                        }
                    }
                    if (!found) {
                        return false;
                    }
                }
            }
            else {
                final MovingObjectPosition ent = rayTracePos(new Vec3(PathFinder.mc.thePlayer.posX, PathFinder.mc.thePlayer.posY, PathFinder.mc.thePlayer.posZ), new Vec3(posX, posY, posZ), false, false, false);
                if (ent != null && ent.entityHit == null) {
                    PathFinder.y = PathFinder.mc.thePlayer.posY;
                    PathFinder.yPreEn = PathFinder.mc.thePlayer.posY;
                }
                else {
                    PathFinder.y = PathFinder.mc.thePlayer.posY;
                    PathFinder.yPreEn = posY;
                }
            }
        }
        for (int k = 0; k < steps; ++k) {
            if (k == 1 && up) {
                PathFinder.x = PathFinder.mc.thePlayer.posX;
                PathFinder.y = PathFinder.yPreEn;
                PathFinder.z = PathFinder.mc.thePlayer.posZ;
                sendPacket(false, positionsBack, positions);
            }
            if (k != steps - 1) {
                final double difX = PathFinder.mc.thePlayer.posX - PathFinder.xPreEn;
                final double difY = PathFinder.mc.thePlayer.posY - PathFinder.yPreEn;
                final double difZ = PathFinder.mc.thePlayer.posZ - PathFinder.zPreEn;
                final double divider = step * k;
                PathFinder.x = PathFinder.mc.thePlayer.posX - difX * divider;
                PathFinder.y = PathFinder.mc.thePlayer.posY - difY * (up ? 1.0 : divider);
                PathFinder.z = PathFinder.mc.thePlayer.posZ - difZ * divider;
                sendPacket(false, positionsBack, positions);
            }
            else {
                final double difX = PathFinder.mc.thePlayer.posX - PathFinder.xPreEn;
                final double difY = PathFinder.mc.thePlayer.posY - PathFinder.yPreEn;
                final double difZ = PathFinder.mc.thePlayer.posZ - PathFinder.zPreEn;
                final double divider = step * k;
                PathFinder.x = PathFinder.mc.thePlayer.posX - difX * divider;
                PathFinder.y = PathFinder.mc.thePlayer.posY - difY * (up ? 1.0 : divider);
                PathFinder.z = PathFinder.mc.thePlayer.posZ - difZ * divider;
                sendPacket(false, positionsBack, positions);
                final double xDist = PathFinder.x - PathFinder.xPreEn;
                final double zDist = PathFinder.z - PathFinder.zPreEn;
                final double yDist = PathFinder.y - posY;
                final double dist = Math.sqrt(xDist * xDist + zDist * zDist);
                if (dist > 4.0) {
                    PathFinder.x = PathFinder.xPreEn;
                    PathFinder.y = PathFinder.yPreEn;
                    PathFinder.z = PathFinder.zPreEn;
                    sendPacket(false, positionsBack, positions);
                }
                else if (dist > 0.05 && up) {
                    PathFinder.x = PathFinder.xPreEn;
                    PathFinder.y = PathFinder.yPreEn;
                    PathFinder.z = PathFinder.zPreEn;
                    sendPacket(false, positionsBack, positions);
                }
                if (Math.abs(yDist) < maxYTP && PathFinder.mc.thePlayer.getDistance(posX, posY, posZ) >= 4.0) {
                    PathFinder.x = PathFinder.xPreEn;
                    PathFinder.y = posY;
                    PathFinder.z = PathFinder.zPreEn;
                    sendPacket(false, positionsBack, positions);
                    PacketUtil.sendPacketNoEvent(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, targetBlockPos, EnumFacing.UP));
                    PacketUtil.sendPacketNoEvent(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, targetBlockPos, EnumFacing.UP));
                }
            }
        }
        return true;
    }
    
    public static MovingObjectPosition rayTracePos(final Vec3 vec31, final Vec3 vec32, final boolean stopOnLiquid, final boolean ignoreBlockWithoutBoundingBox, final boolean returnLastUncollidableBlock) {
        final float[] rots = getFacePosRemote(vec32, vec31);
        final float yaw = rots[0];
        final double angleA = Math.toRadians(normalizeAngle(yaw));
        final double angleB = Math.toRadians(normalizeAngle(yaw) + 180.0f);
        final double size = 2.1;
        final double size2 = 2.1;
        final Vec3 left = new Vec3(vec31.xCoord + Math.cos(angleA) * size, vec31.yCoord, vec31.zCoord + Math.sin(angleA) * size);
        final Vec3 right = new Vec3(vec31.xCoord + Math.cos(angleB) * size, vec31.yCoord, vec31.zCoord + Math.sin(angleB) * size);
        final Vec3 left2 = new Vec3(vec32.xCoord + Math.cos(angleA) * size, vec32.yCoord, vec32.zCoord + Math.sin(angleA) * size);
        final Vec3 right2 = new Vec3(vec32.xCoord + Math.cos(angleB) * size, vec32.yCoord, vec32.zCoord + Math.sin(angleB) * size);
        new Vec3(vec31.xCoord + Math.cos(angleA) * size2, vec31.yCoord, vec31.zCoord + Math.sin(angleA) * size2);
        new Vec3(vec31.xCoord + Math.cos(angleB) * size2, vec31.yCoord, vec31.zCoord + Math.sin(angleB) * size2);
        new Vec3(vec32.xCoord + Math.cos(angleA) * size2, vec32.yCoord, vec32.zCoord + Math.sin(angleA) * size2);
        new Vec3(vec32.xCoord + Math.cos(angleB) * size2, vec32.yCoord, vec32.zCoord + Math.sin(angleB) * size2);
        final MovingObjectPosition trace1 = PathFinder.mc.theWorld.rayTraceBlocks(left, left2, stopOnLiquid, ignoreBlockWithoutBoundingBox, returnLastUncollidableBlock);
        final MovingObjectPosition trace2 = PathFinder.mc.theWorld.rayTraceBlocks(vec31, vec32, stopOnLiquid, ignoreBlockWithoutBoundingBox, returnLastUncollidableBlock);
        final MovingObjectPosition trace3 = PathFinder.mc.theWorld.rayTraceBlocks(right, right2, stopOnLiquid, ignoreBlockWithoutBoundingBox, returnLastUncollidableBlock);
        if (trace2 != null || trace1 != null || trace3 != null) {
            if (returnLastUncollidableBlock) {
                if (trace3 != null && (getBlock(trace3.getBlockPos()).getMaterial() != Material.air || trace3.entityHit != null)) {
                    return trace3;
                }
                if (trace1 != null && (getBlock(trace1.getBlockPos()).getMaterial() != Material.air || trace1.entityHit != null)) {
                    return trace1;
                }
                if (trace2 != null && (getBlock(trace2.getBlockPos()).getMaterial() != Material.air || trace2.entityHit != null)) {
                    return trace2;
                }
            }
            else {
                if (trace3 != null) {
                    return trace3;
                }
                if (trace1 != null) {
                    return trace1;
                }
                return trace2;
            }
        }
        if (trace2 != null) {
            return trace2;
        }
        if (trace3 == null) {
            return trace1;
        }
        return trace3;
    }
    
    public static boolean rayTraceWide(final Vec3 vec31, final Vec3 vec32, final boolean stopOnLiquid, final boolean ignoreBlockWithoutBoundingBox, final boolean returnLastUncollidableBlock) {
        float yaw = getFacePosRemote(vec32, vec31)[0];
        yaw = normalizeAngle(yaw);
        yaw += 180.0f;
        yaw = MathHelper.wrapAngleTo180_float(yaw);
        final double angleA = Math.toRadians(yaw);
        final double angleB = Math.toRadians(yaw + 180.0f);
        final double size = 2.1;
        final Vec3 left = new Vec3(vec31.xCoord + Math.cos(angleA) * size, vec31.yCoord, vec31.zCoord + Math.sin(angleA) * size);
        final Vec3 right = new Vec3(vec31.xCoord + Math.cos(angleB) * size, vec31.yCoord, vec31.zCoord + Math.sin(angleB) * size);
        final Vec3 left2 = new Vec3(vec32.xCoord + Math.cos(angleA) * size, vec32.yCoord, vec32.zCoord + Math.sin(angleA) * size);
        final Vec3 right2 = new Vec3(vec32.xCoord + Math.cos(angleB) * size, vec32.yCoord, vec32.zCoord + Math.sin(angleB) * size);
        final MovingObjectPosition trace1 = PathFinder.mc.theWorld.rayTraceBlocks(left, left2, stopOnLiquid, ignoreBlockWithoutBoundingBox, returnLastUncollidableBlock);
        final MovingObjectPosition trace2 = PathFinder.mc.theWorld.rayTraceBlocks(vec31, vec32, stopOnLiquid, ignoreBlockWithoutBoundingBox, returnLastUncollidableBlock);
        final MovingObjectPosition trace3 = PathFinder.mc.theWorld.rayTraceBlocks(right, right2, stopOnLiquid, ignoreBlockWithoutBoundingBox, returnLastUncollidableBlock);
        if (returnLastUncollidableBlock) {
            return (trace1 != null && getBlock(trace1.getBlockPos()).getMaterial() != Material.air) || (trace2 != null && getBlock(trace2.getBlockPos()).getMaterial() != Material.air) || (trace3 != null && getBlock(trace3.getBlockPos()).getMaterial() != Material.air);
        }
        return trace1 != null || trace2 != null || trace3 != null;
    }
    
    public static float[] getFacePosRemote(final Vec3 src, final Vec3 dest) {
        final double diffX = dest.xCoord - src.xCoord;
        final double diffY = dest.yCoord - src.yCoord;
        final double diffZ = dest.zCoord - src.zCoord;
        final double dist = MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ);
        final float yaw = (float)(Math.atan2(diffZ, diffX) * 180.0 / 3.141592653589793) - 90.0f;
        final float pitch = (float)(-(Math.atan2(diffY, dist) * 180.0 / 3.141592653589793));
        return new float[] { MathHelper.wrapAngleTo180_float(yaw), MathHelper.wrapAngleTo180_float(pitch) };
    }
    
    public static float normalizeAngle(final float angle) {
        return (angle + 360.0f) % 360.0f;
    }
    
    public static Block getBlock(final BlockPos pos) {
        return PathFinder.mc.theWorld.getBlockState(pos).getBlock();
    }
    
    public static void sendPacket(final boolean goingBack, final ArrayList<Vec3> positionsBack, final ArrayList<Vec3> positions) {
        final C03PacketPlayer.C04PacketPlayerPosition playerPacket = new C03PacketPlayer.C04PacketPlayerPosition(PathFinder.x, PathFinder.y, PathFinder.z, false);
        PacketUtil.sendPacketNoEvent(playerPacket);
        if (goingBack) {
            positionsBack.add(new Vec3(PathFinder.x, PathFinder.y, PathFinder.z));
            return;
        }
        positions.add(new Vec3(PathFinder.x, PathFinder.y, PathFinder.z));
    }
    
    static {
        PathFinder.mc = Minecraft.getMinecraft();
    }
}
