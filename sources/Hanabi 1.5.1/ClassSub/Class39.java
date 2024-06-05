package ClassSub;

import net.minecraft.client.*;
import net.minecraft.block.material.*;
import net.minecraft.entity.*;
import net.minecraft.network.*;
import net.minecraft.block.*;
import net.minecraft.enchantment.*;
import net.minecraft.potion.*;
import net.minecraft.network.play.client.*;
import net.minecraft.util.*;
import java.util.*;
import net.minecraft.client.entity.*;
import net.minecraft.entity.player.*;
import cn.Hanabi.modules.Player.*;
import cn.Hanabi.modules.Combat.*;
import net.minecraft.entity.item.*;
import net.minecraft.item.*;
import net.minecraft.block.state.*;

public class Class39
{
    private static Minecraft mc;
    private static Random rand;
    public static boolean spectator;
    public static ArrayList<Integer> blackList;
    static double x;
    static double y;
    static double z;
    static double xPreEn;
    static double yPreEn;
    static double zPreEn;
    static double xPre;
    static double yPre;
    static double zPre;
    private static Vec3 lastLoc;
    
    
    private static void preInfiniteReach(final double n, final double n2, final double n3, final ArrayList<Vec3> list, final ArrayList<Vec3> list2, final Vec3 vec3, final boolean b, final boolean b2, final boolean b3, final boolean b4, final boolean b5) {
    }
    
    private static void postInfiniteReach() {
    }
    
    public static boolean infiniteReach(final double n, final double n2, final double n3, final ArrayList<Vec3> list, final ArrayList<Vec3> list2, final EntityLivingBase entityLivingBase) {
        int n4 = 0;
        Class39.xPreEn = entityLivingBase.posX;
        Class39.yPreEn = entityLivingBase.posY;
        Class39.zPreEn = entityLivingBase.posZ;
        Class39.xPre = Class39.mc.thePlayer.posX;
        Class39.yPre = Class39.mc.thePlayer.posY;
        Class39.zPre = Class39.mc.thePlayer.posZ;
        int n5 = 1;
        boolean b = false;
        final boolean isSneaking = Class39.mc.thePlayer.isSneaking();
        list2.clear();
        list.clear();
        final double n6 = n2 / n;
        int n7 = 0;
        for (int n8 = 0; n8 < n; ++n8) {
            ++n7;
            if (n2 * n7 > n) {
                break;
            }
        }
        MovingObjectPosition movingObjectPosition = null;
        if (rayTraceWide(new Vec3(Class39.mc.thePlayer.posX, Class39.mc.thePlayer.posY, Class39.mc.thePlayer.posZ), new Vec3(entityLivingBase.posX, entityLivingBase.posY, entityLivingBase.posZ), false, false, true) || (movingObjectPosition = rayTracePos(new Vec3(Class39.mc.thePlayer.posX, Class39.mc.thePlayer.posY + Class39.mc.thePlayer.getEyeHeight(), Class39.mc.thePlayer.posZ), new Vec3(entityLivingBase.posX, entityLivingBase.posY + Class39.mc.thePlayer.getEyeHeight(), entityLivingBase.posZ), false, false, true)) != null) {
            final MovingObjectPosition rayTracePos;
            if ((rayTracePos = rayTracePos(new Vec3(Class39.mc.thePlayer.posX, Class39.mc.thePlayer.posY, Class39.mc.thePlayer.posZ), new Vec3(entityLivingBase.posX, Class39.mc.thePlayer.posY, entityLivingBase.posZ), false, false, true)) != null || (movingObjectPosition = rayTracePos(new Vec3(Class39.mc.thePlayer.posX, Class39.mc.thePlayer.posY + Class39.mc.thePlayer.getEyeHeight(), Class39.mc.thePlayer.posZ), new Vec3(entityLivingBase.posX, Class39.mc.thePlayer.posY + Class39.mc.thePlayer.getEyeHeight(), entityLivingBase.posZ), false, false, true)) != null) {
                MovingObjectPosition movingObjectPosition2 = null;
                if (rayTracePos == null) {
                    movingObjectPosition2 = movingObjectPosition;
                }
                if (movingObjectPosition == null) {
                    movingObjectPosition2 = rayTracePos;
                }
                if (movingObjectPosition2 != null) {
                    if (movingObjectPosition2.getBlockPos() == null) {
                        return false;
                    }
                    final BlockPos getBlockPos = movingObjectPosition2.getBlockPos();
                    b = true;
                    Class39.y = getBlockPos.up().getY();
                    Class39.yPreEn = getBlockPos.up().getY();
                    Block block = null;
                    Boolean b2 = false;
                    for (int n9 = 0; n9 < n3; ++n9) {
                        final MovingObjectPosition rayTracePos2 = rayTracePos(new Vec3(Class39.mc.thePlayer.posX, (double)(getBlockPos.getY() + n9), Class39.mc.thePlayer.posZ), new Vec3(entityLivingBase.posX, (double)(getBlockPos.getY() + n9), entityLivingBase.posZ), false, false, true);
                        if (rayTracePos2 != null) {
                            if (rayTracePos2.getBlockPos() != null) {
                                final Block getBlock = Class39.mc.theWorld.getBlockState(rayTracePos2.getBlockPos()).getBlock();
                                if (getBlock.getMaterial() == Material.air) {
                                    final boolean b3 = block instanceof BlockFence;
                                    Class39.y = getBlockPos.getY() + n9;
                                    Class39.yPreEn = getBlockPos.getY() + n9;
                                    if (b3) {
                                        ++Class39.y;
                                        ++Class39.yPreEn;
                                        if (n9 + 1 > n3) {
                                            b2 = false;
                                            break;
                                        }
                                    }
                                    b2 = true;
                                    break;
                                }
                                block = getBlock;
                            }
                        }
                    }
                    final double n10 = Class39.mc.thePlayer.posX - Class39.xPreEn;
                    final double n11 = Class39.mc.thePlayer.posZ - Class39.zPreEn;
                    if (!b2) {
                        return false;
                    }
                }
            }
            else {
                final MovingObjectPosition rayTracePos3 = rayTracePos(new Vec3(Class39.mc.thePlayer.posX, Class39.mc.thePlayer.posY, Class39.mc.thePlayer.posZ), new Vec3(entityLivingBase.posX, entityLivingBase.posY, entityLivingBase.posZ), false, false, false);
                if (rayTracePos3 != null && rayTracePos3.entityHit == null) {
                    Class39.y = Class39.mc.thePlayer.posY;
                    Class39.yPreEn = Class39.mc.thePlayer.posY;
                }
                else {
                    Class39.y = Class39.mc.thePlayer.posY;
                    Class39.yPreEn = entityLivingBase.posY;
                }
            }
        }
        if (n5 == 0) {
            return false;
        }
        if (isSneaking) {
            Class39.mc.getNetHandler().getNetworkManager().sendPacket((Packet)new C0BPacketEntityAction((Entity)Class39.mc.thePlayer, C0BPacketEntityAction.Action.STOP_SNEAKING));
        }
        for (int i = 0; i < n7; ++i) {
            ++n4;
            if (i == 1 && b) {
                Class39.x = Class39.mc.thePlayer.posX;
                Class39.y = Class39.yPreEn;
                Class39.z = Class39.mc.thePlayer.posZ;
                sendPacket(false, list, list2);
            }
            if (i != n7 - 1) {
                final double n12 = Class39.mc.thePlayer.posX - Class39.xPreEn;
                final double n13 = Class39.mc.thePlayer.posY - Class39.yPreEn;
                final double n14 = Class39.mc.thePlayer.posZ - Class39.zPreEn;
                final double n15 = n6 * i;
                Class39.x = Class39.mc.thePlayer.posX - n12 * n15;
                Class39.y = Class39.mc.thePlayer.posY - n13 * (b ? 1.0 : n15);
                Class39.z = Class39.mc.thePlayer.posZ - n14 * n15;
                sendPacket(false, list, list2);
            }
            else {
                final double n16 = Class39.mc.thePlayer.posX - Class39.xPreEn;
                final double n17 = Class39.mc.thePlayer.posY - Class39.yPreEn;
                final double n18 = Class39.mc.thePlayer.posZ - Class39.zPreEn;
                final double n19 = n6 * i;
                Class39.x = Class39.mc.thePlayer.posX - n16 * n19;
                Class39.y = Class39.mc.thePlayer.posY - n17 * (b ? 1.0 : n19);
                Class39.z = Class39.mc.thePlayer.posZ - n18 * n19;
                sendPacket(false, list, list2);
                final double n20 = Class39.x - Class39.xPreEn;
                final double n21 = Class39.z - Class39.zPreEn;
                final double n22 = Class39.y - entityLivingBase.posY;
                final double sqrt = Math.sqrt(n20 * n20 + n21 * n21);
                if (sqrt > 4.0) {
                    Class39.x = Class39.xPreEn;
                    Class39.y = Class39.yPreEn;
                    Class39.z = Class39.zPreEn;
                    sendPacket(false, list, list2);
                }
                else if (sqrt > 0.05 && b) {
                    Class39.x = Class39.xPreEn;
                    Class39.y = Class39.yPreEn;
                    Class39.z = Class39.zPreEn;
                    sendPacket(false, list, list2);
                }
                if (Math.abs(n22) < n3 && Class39.mc.thePlayer.getDistanceToEntity((Entity)entityLivingBase) >= 4.0f) {
                    Class39.x = Class39.xPreEn;
                    Class39.y = entityLivingBase.posY;
                    Class39.z = Class39.zPreEn;
                    sendPacket(false, list, list2);
                    attackInf(entityLivingBase);
                }
                else {
                    n5 = 0;
                }
            }
        }
        for (int j = list2.size() - 2; j > -1; --j) {
            Class39.x = list2.get(j).xCoord;
            Class39.y = list2.get(j).yCoord;
            Class39.z = list2.get(j).zCoord;
            sendPacket(false, list, list2);
        }
        Class39.x = Class39.mc.thePlayer.posX;
        Class39.y = Class39.mc.thePlayer.posY;
        Class39.z = Class39.mc.thePlayer.posZ;
        sendPacket(false, list, list2);
        if (n5 == 0) {
            if (isSneaking) {
                Class39.mc.getNetHandler().getNetworkManager().sendPacket((Packet)new C0BPacketEntityAction((Entity)Class39.mc.thePlayer, C0BPacketEntityAction.Action.START_SNEAKING));
            }
            list2.clear();
            list.clear();
            return false;
        }
        if (isSneaking) {
            Class39.mc.getNetHandler().getNetworkManager().sendPacket((Packet)new C0BPacketEntityAction((Entity)Class39.mc.thePlayer, C0BPacketEntityAction.Action.START_SNEAKING));
        }
        return true;
    }
    
    public static boolean infiniteReach(final double n, final double n2, final double n3, final ArrayList<Vec3> list, final ArrayList<Vec3> list2, final BlockPos blockPos) {
        list2.clear();
        list.clear();
        final double n4 = n2 / n;
        int n5 = 0;
        for (int n6 = 0; n6 < n; ++n6) {
            ++n5;
            if (n2 * n5 > n) {
                break;
            }
        }
        int n7 = 0;
        final double xPreEn = blockPos.getX() + 0.5;
        final double y = blockPos.getY() + 1.0;
        final double zPreEn = blockPos.getZ() + 0.5;
        Class39.xPreEn = xPreEn;
        Class39.yPreEn = y;
        Class39.zPreEn = zPreEn;
        Class39.xPre = Class39.mc.thePlayer.posX;
        Class39.yPre = Class39.mc.thePlayer.posY;
        Class39.zPre = Class39.mc.thePlayer.posZ;
        int n8 = 1;
        boolean b = false;
        final boolean isSneaking = Class39.mc.thePlayer.isSneaking();
        MovingObjectPosition movingObjectPosition = null;
        if (rayTraceWide(new Vec3(Class39.mc.thePlayer.posX, Class39.mc.thePlayer.posY, Class39.mc.thePlayer.posZ), new Vec3(xPreEn, y, zPreEn), false, false, true) || (movingObjectPosition = rayTracePos(new Vec3(Class39.mc.thePlayer.posX, Class39.mc.thePlayer.posY + Class39.mc.thePlayer.getEyeHeight(), Class39.mc.thePlayer.posZ), new Vec3(xPreEn, y + Class39.mc.thePlayer.getEyeHeight(), zPreEn), false, false, true)) != null) {
            final MovingObjectPosition rayTracePos;
            if ((rayTracePos = rayTracePos(new Vec3(Class39.mc.thePlayer.posX, Class39.mc.thePlayer.posY, Class39.mc.thePlayer.posZ), new Vec3(xPreEn, Class39.mc.thePlayer.posY, zPreEn), false, false, true)) != null || (movingObjectPosition = rayTracePos(new Vec3(Class39.mc.thePlayer.posX, Class39.mc.thePlayer.posY + Class39.mc.thePlayer.getEyeHeight(), Class39.mc.thePlayer.posZ), new Vec3(xPreEn, Class39.mc.thePlayer.posY + Class39.mc.thePlayer.getEyeHeight(), zPreEn), false, false, true)) != null) {
                MovingObjectPosition movingObjectPosition2 = null;
                if (rayTracePos == null) {
                    movingObjectPosition2 = movingObjectPosition;
                }
                if (movingObjectPosition == null) {
                    movingObjectPosition2 = rayTracePos;
                }
                if (movingObjectPosition2 != null) {
                    if (movingObjectPosition2.getBlockPos() == null) {
                        return false;
                    }
                    final BlockPos getBlockPos = movingObjectPosition2.getBlockPos();
                    b = true;
                    Class39.y = getBlockPos.up().getY();
                    Class39.yPreEn = getBlockPos.up().getY();
                    Block block = null;
                    Boolean b2 = false;
                    for (int n9 = 0; n9 < n3; ++n9) {
                        final MovingObjectPosition rayTracePos2 = rayTracePos(new Vec3(Class39.mc.thePlayer.posX, (double)(getBlockPos.getY() + n9), Class39.mc.thePlayer.posZ), new Vec3(xPreEn, (double)(getBlockPos.getY() + n9), zPreEn), false, false, true);
                        if (rayTracePos2 != null) {
                            if (rayTracePos2.getBlockPos() != null) {
                                final Block getBlock = Class39.mc.theWorld.getBlockState(rayTracePos2.getBlockPos()).getBlock();
                                if (getBlock.getMaterial() == Material.air) {
                                    final boolean b3 = block instanceof BlockFence;
                                    Class39.y = getBlockPos.getY() + n9;
                                    Class39.yPreEn = getBlockPos.getY() + n9;
                                    if (b3) {
                                        ++Class39.y;
                                        ++Class39.yPreEn;
                                        if (n9 + 1 > n3) {
                                            b2 = false;
                                            break;
                                        }
                                    }
                                    b2 = true;
                                    break;
                                }
                                block = getBlock;
                            }
                        }
                    }
                    final double n10 = Class39.mc.thePlayer.posX - Class39.xPreEn;
                    final double n11 = Class39.mc.thePlayer.posZ - Class39.zPreEn;
                    if (!b2) {
                        return false;
                    }
                }
            }
            else {
                final MovingObjectPosition rayTracePos3 = rayTracePos(new Vec3(Class39.mc.thePlayer.posX, Class39.mc.thePlayer.posY, Class39.mc.thePlayer.posZ), new Vec3(xPreEn, y, zPreEn), false, false, false);
                if (rayTracePos3 != null && rayTracePos3.entityHit == null) {
                    Class39.y = Class39.mc.thePlayer.posY;
                    Class39.yPreEn = Class39.mc.thePlayer.posY;
                }
                else {
                    Class39.y = Class39.mc.thePlayer.posY;
                    Class39.yPreEn = y;
                }
            }
        }
        if (n8 == 0) {
            return false;
        }
        if (isSneaking) {
            Class39.mc.getNetHandler().getNetworkManager().sendPacket((Packet)new C0BPacketEntityAction((Entity)Class39.mc.thePlayer, C0BPacketEntityAction.Action.STOP_SNEAKING));
        }
        for (int i = 0; i < n5; ++i) {
            ++n7;
            if (i == 1 && b) {
                Class39.x = Class39.mc.thePlayer.posX;
                Class39.y = Class39.yPreEn;
                Class39.z = Class39.mc.thePlayer.posZ;
                sendPacket(false, list, list2);
            }
            if (i != n5 - 1) {
                final double n12 = Class39.mc.thePlayer.posX - Class39.xPreEn;
                final double n13 = Class39.mc.thePlayer.posY - Class39.yPreEn;
                final double n14 = Class39.mc.thePlayer.posZ - Class39.zPreEn;
                final double n15 = n4 * i;
                Class39.x = Class39.mc.thePlayer.posX - n12 * n15;
                Class39.y = Class39.mc.thePlayer.posY - n13 * (b ? 1.0 : n15);
                Class39.z = Class39.mc.thePlayer.posZ - n14 * n15;
                sendPacket(false, list, list2);
            }
            else {
                final double n16 = Class39.mc.thePlayer.posX - Class39.xPreEn;
                final double n17 = Class39.mc.thePlayer.posY - Class39.yPreEn;
                final double n18 = Class39.mc.thePlayer.posZ - Class39.zPreEn;
                final double n19 = n4 * i;
                Class39.x = Class39.mc.thePlayer.posX - n16 * n19;
                Class39.y = Class39.mc.thePlayer.posY - n17 * (b ? 1.0 : n19);
                Class39.z = Class39.mc.thePlayer.posZ - n18 * n19;
                sendPacket(false, list, list2);
                final double n20 = Class39.x - Class39.xPreEn;
                final double n21 = Class39.z - Class39.zPreEn;
                final double n22 = Class39.y - y;
                final double sqrt = Math.sqrt(n20 * n20 + n21 * n21);
                if (sqrt > 4.0) {
                    Class39.x = Class39.xPreEn;
                    Class39.y = Class39.yPreEn;
                    Class39.z = Class39.zPreEn;
                    sendPacket(false, list, list2);
                }
                else if (sqrt > 0.05 && b) {
                    Class39.x = Class39.xPreEn;
                    Class39.y = Class39.yPreEn;
                    Class39.z = Class39.zPreEn;
                    sendPacket(false, list, list2);
                }
                if (Math.abs(n22) < n3 && Class39.mc.thePlayer.getDistance(xPreEn, y, zPreEn) >= 4.0) {
                    Class39.x = Class39.xPreEn;
                    Class39.y = y;
                    Class39.z = Class39.zPreEn;
                    sendPacket(false, list, list2);
                    Class39.mc.thePlayer.swingItem();
                    Class39.mc.getNetHandler().getNetworkManager().sendPacket((Packet)new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, blockPos, EnumFacing.UP));
                    Class39.mc.getNetHandler().getNetworkManager().sendPacket((Packet)new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, blockPos, EnumFacing.UP));
                }
                else {
                    n8 = 0;
                }
            }
        }
        for (int j = list2.size() - 2; j > -1; --j) {
            Class39.x = list2.get(j).xCoord;
            Class39.y = list2.get(j).yCoord;
            Class39.z = list2.get(j).zCoord;
            sendPacket(false, list, list2);
        }
        Class39.x = Class39.mc.thePlayer.posX;
        Class39.y = Class39.mc.thePlayer.posY;
        Class39.z = Class39.mc.thePlayer.posZ;
        sendPacket(false, list, list2);
        if (n8 == 0) {
            if (isSneaking) {
                Class39.mc.getNetHandler().getNetworkManager().sendPacket((Packet)new C0BPacketEntityAction((Entity)Class39.mc.thePlayer, C0BPacketEntityAction.Action.START_SNEAKING));
            }
            list2.clear();
            list.clear();
            return false;
        }
        if (isSneaking) {
            Class39.mc.getNetHandler().getNetworkManager().sendPacket((Packet)new C0BPacketEntityAction((Entity)Class39.mc.thePlayer, C0BPacketEntityAction.Action.START_SNEAKING));
        }
        return true;
    }
    
    public static boolean infiniteReach(final Vec3 vec3, final Vec3 vec4, final double n, final double n2, final double n3, final ArrayList<Vec3> list, final ArrayList<Vec3> list2) {
        list2.clear();
        list.clear();
        final double n4 = n2 / n;
        int n5 = 0;
        for (int n6 = 0; n6 < n; ++n6) {
            ++n5;
            if (n2 * n5 > n) {
                break;
            }
        }
        int n7 = 0;
        Class39.xPreEn = vec4.xCoord;
        Class39.yPreEn = vec4.yCoord;
        Class39.zPreEn = vec4.zCoord;
        Class39.xPre = Class39.mc.thePlayer.posX;
        Class39.yPre = Class39.mc.thePlayer.posY;
        Class39.zPre = Class39.mc.thePlayer.posZ;
        int n8 = 1;
        boolean b = false;
        final boolean isSneaking = Class39.mc.thePlayer.isSneaking();
        MovingObjectPosition movingObjectPosition = null;
        if (rayTraceWide(new Vec3(Class39.mc.thePlayer.posX, Class39.mc.thePlayer.posY, Class39.mc.thePlayer.posZ), new Vec3(vec4.xCoord, vec4.yCoord, vec4.zCoord), false, false, true) || (movingObjectPosition = rayTracePos(new Vec3(Class39.mc.thePlayer.posX, Class39.mc.thePlayer.posY + Class39.mc.thePlayer.getEyeHeight(), Class39.mc.thePlayer.posZ), new Vec3(vec4.xCoord, vec4.yCoord + Class39.mc.thePlayer.getEyeHeight(), vec4.zCoord), false, false, true)) != null) {
            final MovingObjectPosition rayTracePos;
            if ((rayTracePos = rayTracePos(new Vec3(Class39.mc.thePlayer.posX, Class39.mc.thePlayer.posY, Class39.mc.thePlayer.posZ), new Vec3(vec4.xCoord, Class39.mc.thePlayer.posY, vec4.zCoord), false, false, true)) != null || (movingObjectPosition = rayTracePos(new Vec3(Class39.mc.thePlayer.posX, Class39.mc.thePlayer.posY + Class39.mc.thePlayer.getEyeHeight(), Class39.mc.thePlayer.posZ), new Vec3(vec4.xCoord, Class39.mc.thePlayer.posY + Class39.mc.thePlayer.getEyeHeight(), vec4.zCoord), false, false, true)) != null) {
                MovingObjectPosition movingObjectPosition2 = null;
                if (rayTracePos == null) {
                    movingObjectPosition2 = movingObjectPosition;
                }
                if (movingObjectPosition == null) {
                    movingObjectPosition2 = rayTracePos;
                }
                if (movingObjectPosition2 != null) {
                    if (movingObjectPosition2.getBlockPos() == null) {
                        return false;
                    }
                    final BlockPos getBlockPos = movingObjectPosition2.getBlockPos();
                    b = true;
                    Class39.y = getBlockPos.up().getY();
                    Class39.yPreEn = getBlockPos.up().getY();
                    Block block = null;
                    Boolean b2 = false;
                    for (int n9 = 0; n9 < n3; ++n9) {
                        final MovingObjectPosition rayTracePos2 = rayTracePos(new Vec3(Class39.mc.thePlayer.posX, (double)(getBlockPos.getY() + n9), Class39.mc.thePlayer.posZ), new Vec3(vec4.xCoord, (double)(getBlockPos.getY() + n9), vec4.zCoord), false, false, true);
                        if (rayTracePos2 != null) {
                            if (rayTracePos2.getBlockPos() != null) {
                                final Block getBlock = Class39.mc.theWorld.getBlockState(rayTracePos2.getBlockPos()).getBlock();
                                if (getBlock.getMaterial() == Material.air) {
                                    final boolean b3 = block instanceof BlockFence;
                                    Class39.y = getBlockPos.getY() + n9;
                                    Class39.yPreEn = getBlockPos.getY() + n9;
                                    if (b3) {
                                        ++Class39.y;
                                        ++Class39.yPreEn;
                                        if (n9 + 1 > n3) {
                                            b2 = false;
                                            break;
                                        }
                                    }
                                    b2 = true;
                                    break;
                                }
                                block = getBlock;
                            }
                        }
                    }
                    final double n10 = Class39.mc.thePlayer.posX - Class39.xPreEn;
                    final double n11 = Class39.mc.thePlayer.posZ - Class39.zPreEn;
                    if (!b2) {
                        return false;
                    }
                }
            }
            else {
                final MovingObjectPosition rayTracePos3 = rayTracePos(new Vec3(Class39.mc.thePlayer.posX, Class39.mc.thePlayer.posY, Class39.mc.thePlayer.posZ), new Vec3(vec4.xCoord, vec4.yCoord, vec4.zCoord), false, false, false);
                if (rayTracePos3 != null && rayTracePos3.entityHit == null) {
                    Class39.y = Class39.mc.thePlayer.posY;
                    Class39.yPreEn = Class39.mc.thePlayer.posY;
                }
                else {
                    Class39.y = Class39.mc.thePlayer.posY;
                    Class39.yPreEn = vec4.yCoord;
                }
            }
        }
        if (n8 == 0) {
            return false;
        }
        if (isSneaking) {
            Class39.mc.getNetHandler().getNetworkManager().sendPacket((Packet)new C0BPacketEntityAction((Entity)Class39.mc.thePlayer, C0BPacketEntityAction.Action.STOP_SNEAKING));
        }
        for (int i = 0; i < n5; ++i) {
            ++n7;
            if (i == 1 && b) {
                Class39.x = Class39.mc.thePlayer.posX;
                Class39.y = Class39.yPreEn;
                Class39.z = Class39.mc.thePlayer.posZ;
                sendPacket(false, list, list2);
            }
            if (i != n5 - 1) {
                final double n12 = Class39.mc.thePlayer.posX - Class39.xPreEn;
                final double n13 = Class39.mc.thePlayer.posY - Class39.yPreEn;
                final double n14 = Class39.mc.thePlayer.posZ - Class39.zPreEn;
                final double n15 = n4 * i;
                Class39.x = Class39.mc.thePlayer.posX - n12 * n15;
                Class39.y = Class39.mc.thePlayer.posY - n13 * (b ? 1.0 : n15);
                Class39.z = Class39.mc.thePlayer.posZ - n14 * n15;
                sendPacket(false, list, list2);
            }
            else {
                final double n16 = Class39.mc.thePlayer.posX - Class39.xPreEn;
                final double n17 = Class39.mc.thePlayer.posY - Class39.yPreEn;
                final double n18 = Class39.mc.thePlayer.posZ - Class39.zPreEn;
                final double n19 = n4 * i;
                Class39.x = Class39.mc.thePlayer.posX - n16 * n19;
                Class39.y = Class39.mc.thePlayer.posY - n17 * (b ? 1.0 : n19);
                Class39.z = Class39.mc.thePlayer.posZ - n18 * n19;
                sendPacket(false, list, list2);
                final double n20 = Class39.x - Class39.xPreEn;
                final double n21 = Class39.z - Class39.zPreEn;
                final double n22 = Class39.y - vec4.yCoord;
                final double sqrt = Math.sqrt(n20 * n20 + n21 * n21);
                if (sqrt > 4.0) {
                    Class39.x = Class39.xPreEn;
                    Class39.y = Class39.yPreEn;
                    Class39.z = Class39.zPreEn;
                    sendPacket(false, list, list2);
                }
                else if (sqrt > 0.05 && b) {
                    Class39.x = Class39.xPreEn;
                    Class39.y = Class39.yPreEn;
                    Class39.z = Class39.zPreEn;
                    sendPacket(false, list, list2);
                }
                if (Math.abs(n22) < n3) {
                    Class39.x = Class39.xPreEn;
                    Class39.y = vec4.yCoord;
                    Class39.z = Class39.zPreEn;
                    sendPacket(false, list, list2);
                }
                else {
                    n8 = 0;
                }
            }
        }
        for (int j = list2.size() - 2; j > -1; --j) {
            Class39.x = list2.get(j).xCoord;
            Class39.y = list2.get(j).yCoord;
            Class39.z = list2.get(j).zCoord;
            sendPacket(false, list, list2);
        }
        Class39.x = Class39.mc.thePlayer.posX;
        Class39.y = Class39.mc.thePlayer.posY;
        Class39.z = Class39.mc.thePlayer.posZ;
        sendPacket(false, list, list2);
        if (n8 == 0) {
            if (isSneaking) {
                Class39.mc.getNetHandler().getNetworkManager().sendPacket((Packet)new C0BPacketEntityAction((Entity)Class39.mc.thePlayer, C0BPacketEntityAction.Action.START_SNEAKING));
            }
            list2.clear();
            list.clear();
            return false;
        }
        if (isSneaking) {
            Class39.mc.getNetHandler().getNetworkManager().sendPacket((Packet)new C0BPacketEntityAction((Entity)Class39.mc.thePlayer, C0BPacketEntityAction.Action.START_SNEAKING));
        }
        return true;
    }
    
    public static void attackInf(final EntityLivingBase entityLivingBase) {
        Class39.mc.thePlayer.swingItem();
        Class39.mc.getNetHandler().getNetworkManager().sendPacket((Packet)new C02PacketUseEntity((Entity)entityLivingBase, C02PacketUseEntity.Action.ATTACK));
        final float getModifierForCreature = EnchantmentHelper.getModifierForCreature(Class39.mc.thePlayer.getHeldItem(), entityLivingBase.getCreatureAttribute());
        final boolean b = Class39.mc.thePlayer.fallDistance > 0.0f && !Class39.mc.thePlayer.onGround && !Class39.mc.thePlayer.isOnLadder() && !Class39.mc.thePlayer.isInWater() && !Class39.mc.thePlayer.isPotionActive(Potion.blindness) && Class39.mc.thePlayer.ridingEntity == null;
        if (getModifierForCreature > 0.0f) {
            Class39.mc.thePlayer.onEnchantmentCritical((Entity)entityLivingBase);
        }
    }
    
    public static void sendPacket(final boolean b, final ArrayList<Vec3> list, final ArrayList<Vec3> list2) {
        Class39.mc.getNetHandler().getNetworkManager().sendPacket((Packet)new C03PacketPlayer.C04PacketPlayerPosition(Class39.x, Class39.y, Class39.z, true));
        if (b) {
            list.add(new Vec3(Class39.x, Class39.y, Class39.z));
            return;
        }
        list2.add(new Vec3(Class39.x, Class39.y, Class39.z));
    }
    
    public static MovingObjectPosition rayTracePos(final Vec3 vec3, final Vec3 vec4, final boolean b, final boolean b2, final boolean b3) {
        final float n = getFacePosRemote(vec4, vec3)[0];
        final double radians = Math.toRadians(normalizeAngle(n));
        final double radians2 = Math.toRadians(normalizeAngle(n) + 180.0f);
        final double n2 = 2.1;
        final double n3 = 2.1;
        final Vec3 vec5 = new Vec3(vec3.xCoord + Math.cos(radians) * n2, vec3.yCoord, vec3.zCoord + Math.sin(radians) * n2);
        final Vec3 vec6 = new Vec3(vec3.xCoord + Math.cos(radians2) * n2, vec3.yCoord, vec3.zCoord + Math.sin(radians2) * n2);
        final Vec3 vec7 = new Vec3(vec4.xCoord + Math.cos(radians) * n2, vec4.yCoord, vec4.zCoord + Math.sin(radians) * n2);
        final Vec3 vec8 = new Vec3(vec4.xCoord + Math.cos(radians2) * n2, vec4.yCoord, vec4.zCoord + Math.sin(radians2) * n2);
        final Vec3 vec9 = new Vec3(vec3.xCoord + Math.cos(radians) * n3, vec3.yCoord, vec3.zCoord + Math.sin(radians) * n3);
        final Vec3 vec10 = new Vec3(vec3.xCoord + Math.cos(radians2) * n3, vec3.yCoord, vec3.zCoord + Math.sin(radians2) * n3);
        final Vec3 vec11 = new Vec3(vec4.xCoord + Math.cos(radians) * n3, vec4.yCoord, vec4.zCoord + Math.sin(radians) * n3);
        final Vec3 vec12 = new Vec3(vec4.xCoord + Math.cos(radians2) * n3, vec4.yCoord, vec4.zCoord + Math.sin(radians2) * n3);
        final MovingObjectPosition rayTraceBlocks = Class39.mc.theWorld.rayTraceBlocks(vec5, vec7, b, b2, b3);
        final MovingObjectPosition rayTraceBlocks2 = Class39.mc.theWorld.rayTraceBlocks(vec3, vec4, b, b2, b3);
        final MovingObjectPosition rayTraceBlocks3 = Class39.mc.theWorld.rayTraceBlocks(vec6, vec8, b, b2, b3);
        final MovingObjectPosition movingObjectPosition = null;
        final MovingObjectPosition movingObjectPosition2 = null;
        if (rayTraceBlocks2 != null || rayTraceBlocks != null || rayTraceBlocks3 != null || movingObjectPosition != null || movingObjectPosition2 != null) {
            if (b3) {
                if (movingObjectPosition2 != null && (getBlock(movingObjectPosition2.getBlockPos()).getMaterial() != Material.air || movingObjectPosition2.entityHit != null)) {
                    return movingObjectPosition2;
                }
                if (movingObjectPosition != null && (getBlock(movingObjectPosition.getBlockPos()).getMaterial() != Material.air || movingObjectPosition.entityHit != null)) {
                    return movingObjectPosition;
                }
                if (rayTraceBlocks3 != null && (getBlock(rayTraceBlocks3.getBlockPos()).getMaterial() != Material.air || rayTraceBlocks3.entityHit != null)) {
                    return rayTraceBlocks3;
                }
                if (rayTraceBlocks != null && (getBlock(rayTraceBlocks.getBlockPos()).getMaterial() != Material.air || rayTraceBlocks.entityHit != null)) {
                    return rayTraceBlocks;
                }
                if (rayTraceBlocks2 != null && (getBlock(rayTraceBlocks2.getBlockPos()).getMaterial() != Material.air || rayTraceBlocks2.entityHit != null)) {
                    return rayTraceBlocks2;
                }
            }
            else {
                if (movingObjectPosition2 != null) {
                    return movingObjectPosition2;
                }
                if (movingObjectPosition != null) {
                    return movingObjectPosition;
                }
                if (rayTraceBlocks3 != null) {
                    return rayTraceBlocks3;
                }
                if (rayTraceBlocks != null) {
                    return rayTraceBlocks;
                }
                if (rayTraceBlocks2 != null) {
                    return rayTraceBlocks2;
                }
            }
        }
        if (rayTraceBlocks2 != null) {
            return rayTraceBlocks2;
        }
        if (rayTraceBlocks3 != null) {
            return rayTraceBlocks3;
        }
        if (rayTraceBlocks != null) {
            return rayTraceBlocks;
        }
        if (movingObjectPosition2 != null) {
            return movingObjectPosition2;
        }
        if (movingObjectPosition == null) {
            return null;
        }
        return movingObjectPosition;
    }
    
    public static boolean rayTraceWide(final Vec3 vec3, final Vec3 vec4, final boolean b, final boolean b2, final boolean b3) {
        final float wrapAngleTo180_float = MathHelper.wrapAngleTo180_float(normalizeAngle(getFacePosRemote(vec4, vec3)[0]) + 180.0f);
        final double radians = Math.toRadians(wrapAngleTo180_float);
        final double radians2 = Math.toRadians(wrapAngleTo180_float + 180.0f);
        final double n = 2.1;
        final double n2 = 2.1;
        final Vec3 vec5 = new Vec3(vec3.xCoord + Math.cos(radians) * n, vec3.yCoord, vec3.zCoord + Math.sin(radians) * n);
        final Vec3 vec6 = new Vec3(vec3.xCoord + Math.cos(radians2) * n, vec3.yCoord, vec3.zCoord + Math.sin(radians2) * n);
        final Vec3 vec7 = new Vec3(vec4.xCoord + Math.cos(radians) * n, vec4.yCoord, vec4.zCoord + Math.sin(radians) * n);
        final Vec3 vec8 = new Vec3(vec4.xCoord + Math.cos(radians2) * n, vec4.yCoord, vec4.zCoord + Math.sin(radians2) * n);
        final Vec3 vec9 = new Vec3(vec3.xCoord + Math.cos(radians) * n2, vec3.yCoord, vec3.zCoord + Math.sin(radians) * n2);
        final Vec3 vec10 = new Vec3(vec3.xCoord + Math.cos(radians2) * n2, vec3.yCoord, vec3.zCoord + Math.sin(radians2) * n2);
        final Vec3 vec11 = new Vec3(vec4.xCoord + Math.cos(radians) * n2, vec4.yCoord, vec4.zCoord + Math.sin(radians) * n2);
        final Vec3 vec12 = new Vec3(vec4.xCoord + Math.cos(radians2) * n2, vec4.yCoord, vec4.zCoord + Math.sin(radians2) * n2);
        final MovingObjectPosition rayTraceBlocks = Class39.mc.theWorld.rayTraceBlocks(vec5, vec7, b, b2, b3);
        final MovingObjectPosition rayTraceBlocks2 = Class39.mc.theWorld.rayTraceBlocks(vec3, vec4, b, b2, b3);
        final MovingObjectPosition rayTraceBlocks3 = Class39.mc.theWorld.rayTraceBlocks(vec6, vec8, b, b2, b3);
        final MovingObjectPosition movingObjectPosition = null;
        final MovingObjectPosition movingObjectPosition2 = null;
        if (b3) {
            return (rayTraceBlocks != null && getBlock(rayTraceBlocks.getBlockPos()).getMaterial() != Material.air) || (rayTraceBlocks2 != null && getBlock(rayTraceBlocks2.getBlockPos()).getMaterial() != Material.air) || (rayTraceBlocks3 != null && getBlock(rayTraceBlocks3.getBlockPos()).getMaterial() != Material.air) || (movingObjectPosition != null && getBlock(movingObjectPosition.getBlockPos()).getMaterial() != Material.air) || (movingObjectPosition2 != null && getBlock(movingObjectPosition2.getBlockPos()).getMaterial() != Material.air);
        }
        return rayTraceBlocks != null || rayTraceBlocks2 != null || rayTraceBlocks3 != null || movingObjectPosition2 != null || movingObjectPosition != null;
    }
    
    public static void blinkToPosFromPos(final Vec3 vec3, final Vec3 vec4, final double n) {
        final double n2 = vec3.xCoord - vec4.xCoord;
        final double n3 = vec3.yCoord - vec4.yCoord;
        final double n4 = vec3.zCoord - vec4.zCoord;
        final double xCoord = vec3.xCoord;
        final double yCoord = vec3.yCoord;
        final double zCoord = vec3.zCoord;
        final double xCoord2 = vec4.xCoord;
        final double yCoord2 = vec4.yCoord;
        final double zCoord2 = vec4.zCoord;
        final double sqrt = Math.sqrt(n2 * n2 + n3 * n3 + n4 * n4);
        final double n5 = n / sqrt;
        int n6 = 0;
        for (int n7 = 0; n7 < sqrt; ++n7) {
            ++n6;
            if (n * n6 > sqrt) {
                break;
            }
        }
        for (int i = 0; i < n6; ++i) {
            final double n8 = xCoord - xCoord2;
            final double n9 = yCoord - yCoord2;
            final double n10 = zCoord - zCoord2;
            final double n11 = n5 * i;
            Class39.mc.thePlayer.sendQueue.addToSendQueue((Packet)new C03PacketPlayer.C04PacketPlayerPosition(xCoord - n8 * n11, yCoord - n9 * n11, zCoord - n10 * n11, true));
        }
        Class39.mc.thePlayer.sendQueue.addToSendQueue((Packet)new C03PacketPlayer.C04PacketPlayerPosition(xCoord2, yCoord2, zCoord2, true));
    }
    
    public static boolean isBlacklisted(final Entity entity) {
        final Iterator<Integer> iterator = Class39.blackList.iterator();
        while (iterator.hasNext()) {
            if (entity.getEntityId() == iterator.next()) {
                return true;
            }
        }
        return false;
    }
    
    public static ArrayList<EntityLivingBase> getClosestEntitiesToEntity(final float n, final Entity entity) {
        final ArrayList<Entity> list = (ArrayList<Entity>)new ArrayList<EntityLivingBase>();
        for (final EntityLivingBase next : Minecraft.getMinecraft().theWorld.loadedEntityList) {
            if (isNotItem(next) && !entity.isEntityEqual((Entity)next)) {
                final EntityLivingBase entityLivingBase = next;
                if (entity.getDistanceToEntity((Entity)entityLivingBase) >= n) {
                    continue;
                }
                list.add(entityLivingBase);
            }
        }
        return (ArrayList<EntityLivingBase>)list;
    }
    
    public float getDistanceToEntityFromEntity(final Entity entity, final Entity entity2) {
        final float n = (float)(entity.posX - entity2.posX);
        final float n2 = (float)(entity.posY - entity2.posY);
        final float n3 = (float)(entity.posZ - entity2.posZ);
        return MathHelper.sqrt_float(n * n + n2 * n2 + n3 * n3);
    }
    
    public static ArrayList<EntityLivingBase> getClosestEntities(final float n) {
        final ArrayList<Entity> list = (ArrayList<Entity>)new ArrayList<EntityLivingBase>();
        for (final EntityLivingBase next : Minecraft.getMinecraft().theWorld.loadedEntityList) {
            if (isNotItem(next) && !(next instanceof EntityPlayerSP)) {
                final EntityLivingBase entityLivingBase = next;
                if (!validEntity((Entity)entityLivingBase)) {
                    continue;
                }
                if (Minecraft.getMinecraft().thePlayer.getDistanceToEntity((Entity)entityLivingBase) >= n) {
                    continue;
                }
                list.add(entityLivingBase);
            }
        }
        return (ArrayList<EntityLivingBase>)list;
    }
    
    private static boolean validEntity(final Entity entity) {
        return entity != null && entity != Class39.mc.thePlayer && !entity.getName().equalsIgnoreCase("?6Dealer") && !entity.isDead && !Class39.mc.thePlayer.isDead && (!(entity instanceof EntityPlayer) || (!Teams.isOnSameTeam(entity) && !AntiBot.isBot(entity))) && entity instanceof EntityLivingBase;
    }
    
    public static EntityLivingBase getClosestEntity(final float n) {
        EntityLivingBase entityLivingBase = null;
        float getDistanceToEntity = n;
        for (final EntityLivingBase next : Minecraft.getMinecraft().theWorld.loadedEntityList) {
            if (isNotItem(next) && !(next instanceof EntityPlayerSP)) {
                final EntityLivingBase entityLivingBase2 = next;
                if (!validEntity((Entity)entityLivingBase2)) {
                    continue;
                }
                if (Minecraft.getMinecraft().thePlayer.getDistanceToEntity((Entity)entityLivingBase2) >= getDistanceToEntity) {
                    continue;
                }
                getDistanceToEntity = Minecraft.getMinecraft().thePlayer.getDistanceToEntity((Entity)entityLivingBase2);
                entityLivingBase = entityLivingBase2;
            }
        }
        return entityLivingBase;
    }
    
    public static EntityLivingBase getClosestEntitySkipValidCheck(final float n) {
        EntityLivingBase entityLivingBase = null;
        float getDistanceToEntity = n;
        for (final EntityLivingBase next : Minecraft.getMinecraft().theWorld.loadedEntityList) {
            if (isNotItem(next) && !(next instanceof EntityPlayerSP)) {
                final EntityLivingBase entityLivingBase2 = next;
                if (Minecraft.getMinecraft().thePlayer.getDistanceToEntity((Entity)entityLivingBase2) >= getDistanceToEntity) {
                    continue;
                }
                getDistanceToEntity = Minecraft.getMinecraft().thePlayer.getDistanceToEntity((Entity)entityLivingBase2);
                entityLivingBase = entityLivingBase2;
            }
        }
        return entityLivingBase;
    }
    
    public static EntityLivingBase getClosestEntityToEntity(final float n, final Entity entity) {
        EntityLivingBase entityLivingBase = null;
        float getDistanceToEntity = n;
        for (final EntityLivingBase next : Minecraft.getMinecraft().theWorld.loadedEntityList) {
            if (isNotItem(next) && !entity.isEntityEqual((Entity)next)) {
                final EntityLivingBase entityLivingBase2 = next;
                if (entity.getDistanceToEntity((Entity)entityLivingBase2) >= getDistanceToEntity) {
                    continue;
                }
                getDistanceToEntity = entity.getDistanceToEntity((Entity)entityLivingBase2);
                entityLivingBase = entityLivingBase2;
            }
        }
        return entityLivingBase;
    }
    
    public static boolean isNotItem(final Object o) {
        return o instanceof EntityLivingBase;
    }
    
    public static void faceEntity(final Entity entity) {
        facePos(new Vec3(entity.posX - 0.5, entity.posY + (entity.getEyeHeight() - entity.height / 1.5), entity.posZ - 0.5));
    }
    
    public static void faceBlock(final BlockPos blockPos) {
        facePos(getVec3(blockPos));
    }
    
    public static Vec3 getVec3(final BlockPos blockPos) {
        return new Vec3((double)blockPos.getX(), (double)blockPos.getY(), (double)blockPos.getZ());
    }
    
    public static BlockPos getBlockPos(final Vec3 vec3) {
        return new BlockPos(vec3.xCoord, vec3.yCoord, vec3.zCoord);
    }
    
    public static void facePos(final Vec3 vec3) {
        final double n = vec3.xCoord + 0.5 - Minecraft.getMinecraft().thePlayer.posX;
        final double n2 = vec3.yCoord + 0.5 - (Minecraft.getMinecraft().thePlayer.posY + Minecraft.getMinecraft().thePlayer.getEyeHeight());
        final double n3 = vec3.zCoord + 0.5 - Minecraft.getMinecraft().thePlayer.posZ;
        final double n4 = MathHelper.sqrt_double(n * n + n3 * n3);
        final float n5 = (float)(Math.atan2(n3, n) * 180.0 / 3.141592653589793) - 90.0f;
        final float n6 = (float)(-(Math.atan2(n2, n4) * 180.0 / 3.141592653589793));
        Minecraft.getMinecraft().thePlayer.rotationYaw += MathHelper.wrapAngleTo180_float(n5 - Minecraft.getMinecraft().thePlayer.rotationYaw);
        Minecraft.getMinecraft().thePlayer.rotationPitch += MathHelper.wrapAngleTo180_float(n6 - Minecraft.getMinecraft().thePlayer.rotationPitch);
    }
    
    public static float[] getFacePos(final Vec3 vec3) {
        final double n = vec3.xCoord + 0.5 - Minecraft.getMinecraft().thePlayer.posX;
        final double n2 = vec3.yCoord + 0.5 - (Minecraft.getMinecraft().thePlayer.posY + Minecraft.getMinecraft().thePlayer.getEyeHeight());
        final double n3 = vec3.zCoord + 0.5 - Minecraft.getMinecraft().thePlayer.posZ;
        return new float[] { Minecraft.getMinecraft().thePlayer.rotationYaw + MathHelper.wrapAngleTo180_float((float)(Math.atan2(n3, n) * 180.0 / 3.141592653589793) - 90.0f - Minecraft.getMinecraft().thePlayer.rotationYaw), Minecraft.getMinecraft().thePlayer.rotationPitch + MathHelper.wrapAngleTo180_float((float)(-(Math.atan2(n2, MathHelper.sqrt_double(n * n + n3 * n3)) * 180.0 / 3.141592653589793)) - Minecraft.getMinecraft().thePlayer.rotationPitch) };
    }
    
    public static float[] getFacePosRemote(final Vec3 vec3, final Vec3 vec4) {
        final double n = vec4.xCoord - vec3.xCoord;
        final double n2 = vec4.yCoord - vec3.yCoord;
        final double n3 = vec4.zCoord - vec3.zCoord;
        return new float[] { MathHelper.wrapAngleTo180_float((float)(Math.atan2(n3, n) * 180.0 / 3.141592653589793) - 90.0f), MathHelper.wrapAngleTo180_float((float)(-(Math.atan2(n2, MathHelper.sqrt_double(n * n + n3 * n3)) * 180.0 / 3.141592653589793))) };
    }
    
    public static float[] getFacePosEntity(final Entity entity) {
        if (entity == null) {
            return new float[] { Minecraft.getMinecraft().thePlayer.rotationYawHead, Minecraft.getMinecraft().thePlayer.rotationPitch };
        }
        return getFacePos(new Vec3(entity.posX - 0.5, entity.posY + (entity.getEyeHeight() - entity.height / 1.5), entity.posZ - 0.5));
    }
    
    public static float[] getFacePosEntityRemote(final EntityLivingBase entityLivingBase, final Entity entity) {
        if (entity == null) {
            return new float[] { entityLivingBase.rotationYawHead, entityLivingBase.rotationPitch };
        }
        return getFacePosRemote(new Vec3(entityLivingBase.posX, entityLivingBase.posY + entity.getEyeHeight(), entityLivingBase.posZ), new Vec3(entity.posX, entity.posY + entity.getEyeHeight(), entity.posZ));
    }
    
    public static void smoothFacePos(final Vec3 vec3) {
        final double n = vec3.xCoord + 0.5 - Minecraft.getMinecraft().thePlayer.posX;
        final double n2 = vec3.yCoord + 0.5 - (Minecraft.getMinecraft().thePlayer.posY + Minecraft.getMinecraft().thePlayer.getEyeHeight());
        final double n3 = vec3.zCoord + 0.5 - Minecraft.getMinecraft().thePlayer.posZ;
        final double n4 = MathHelper.sqrt_double(n * n + n3 * n3);
        final float n5 = (float)(Math.atan2(n3, n) * 180.0 / 3.141592653589793) - 90.0f;
        final float n6 = (float)(-(Math.atan2(n2, n4) * 180.0 / 3.141592653589793));
        final float n7 = 5.0f;
        if (MathHelper.wrapAngleTo180_float(n5 - Minecraft.getMinecraft().thePlayer.rotationYaw) <= n7 * 2.0f) {
            if (MathHelper.wrapAngleTo180_float(n5 - Minecraft.getMinecraft().thePlayer.rotationYaw) < -n7 * 2.0f) {}
        }
        if (MathHelper.wrapAngleTo180_float(n6 - Minecraft.getMinecraft().thePlayer.rotationPitch) <= n7 * 4.0f) {
            if (MathHelper.wrapAngleTo180_float(n6 - Minecraft.getMinecraft().thePlayer.rotationPitch) < -n7 * 4.0f) {}
        }
    }
    
    public static void smoothFacePos(final Vec3 vec3, final double n) {
        final double n2 = vec3.xCoord + 0.5 - Minecraft.getMinecraft().thePlayer.posX;
        final double n3 = vec3.yCoord + 0.5 - (Minecraft.getMinecraft().thePlayer.posY + Minecraft.getMinecraft().thePlayer.getEyeHeight());
        final double n4 = vec3.zCoord + 0.5 - Minecraft.getMinecraft().thePlayer.posZ;
        final double n5 = MathHelper.sqrt_double(n2 * n2 + n4 * n4);
        final float n6 = (float)(Math.atan2(n4, n2) * 180.0 / 3.141592653589793) - 90.0f;
        final float n7 = (float)(-(Math.atan2(n3, n5) * 180.0 / 3.141592653589793));
    }
    
    public static float getPlayerBlockDistance(final BlockPos blockPos) {
        return getPlayerBlockDistance(blockPos.getX(), blockPos.getY(), blockPos.getZ());
    }
    
    public static float getPlayerBlockDistance(final double n, final double n2, final double n3) {
        return getBlockDistance((float)(Minecraft.getMinecraft().thePlayer.posX - n), (float)(Minecraft.getMinecraft().thePlayer.posY - n2), (float)(Minecraft.getMinecraft().thePlayer.posZ - n3));
    }
    
    public static float getBlockDistance(final float n, final float n2, final float n3) {
        return MathHelper.sqrt_float((n - 0.5f) * (n - 0.5f) + (n2 - 0.5f) * (n2 - 0.5f) + (n3 - 0.5f) * (n3 - 0.5f));
    }
    
    public static ArrayList<EntityItem> getNearbyItems(final int n) {
        final ArrayList<Entity> list = (ArrayList<Entity>)new ArrayList<EntityItem>();
        for (final EntityItem next : Minecraft.getMinecraft().theWorld.getLoadedEntityList()) {
            if (!(next instanceof EntityItem)) {
                continue;
            }
            final EntityItem entityItem = next;
            if (Minecraft.getMinecraft().thePlayer.getDistanceToEntity((Entity)entityItem) >= n) {
                continue;
            }
            list.add(entityItem);
        }
        return (ArrayList<EntityItem>)list;
    }
    
    public static EntityItem getClosestItem(final float n) {
        EntityItem entityItem = null;
        for (final EntityItem next : Minecraft.getMinecraft().theWorld.getLoadedEntityList()) {
            if (!(next instanceof EntityItem)) {
                continue;
            }
            final EntityItem entityItem2 = next;
            if (Minecraft.getMinecraft().thePlayer.getDistanceToEntity((Entity)entityItem2) >= n) {
                continue;
            }
            entityItem = entityItem2;
        }
        return entityItem;
    }
    
    public static Entity getClosestItemOrXPOrb(final float n) {
        Entity entity = null;
        for (final Entity next : Minecraft.getMinecraft().theWorld.getLoadedEntityList()) {
            if (!(next instanceof EntityItem) && !(next instanceof EntityXPOrb)) {
                continue;
            }
            final Entity entity2 = next;
            if (Minecraft.getMinecraft().thePlayer.getDistanceToEntity(entity2) >= n) {
                continue;
            }
            entity = entity2;
        }
        return entity;
    }
    
    private static final float limitAngleChange(final float n, final float n2, final float n3) {
        float n4 = n2 - n;
        if (n4 > n3) {
            n4 = n3;
        }
        else if (n4 < -n3) {
            n4 = -n3;
        }
        return n + n4;
    }
    
    public static double normalizeAngle(final double n) {
        return (n + 360.0) % 360.0;
    }
    
    public static float normalizeAngle(final float n) {
        return (n + 360.0f) % 360.0f;
    }
    
    public static int getItemIndexHotbar(final int n) {
        for (int i = 0; i < 9; ++i) {
            final ItemStack getStackInSlot = Class39.mc.thePlayer.inventory.getStackInSlot(i);
            if (getStackInSlot != null) {
                if (n == Item.getIdFromItem(getStackInSlot.getItem())) {
                    return i;
                }
            }
        }
        return -1;
    }
    
    public static boolean isBlockPosAir(final BlockPos blockPos) {
        return Class39.mc.theWorld.getBlockState(blockPos).getBlock().getMaterial() == Material.air;
    }
    
    public static Block getBlockRelativeToEntity(final Entity entity, final double n) {
        return getBlock(new BlockPos(entity.posX, entity.posY + n, entity.posZ));
    }
    
    public static BlockPos getBlockPosRelativeToEntity(final Entity entity, final double n) {
        return new BlockPos(entity.posX, entity.posY + n, entity.posZ);
    }
    
    public static Block getBlock(final BlockPos blockPos) {
        return Class39.mc.theWorld.getBlockState(blockPos).getBlock();
    }
    
    public static Vec3 getLastGroundLocation() {
        return Class39.lastLoc;
    }
    
    public static void updateLastGroundLocation() {
        if (Class39.mc.thePlayer.onGround) {
            Class39.lastLoc = new Vec3(Class39.mc.thePlayer.posX, Class39.mc.thePlayer.posY, Class39.mc.thePlayer.posZ);
        }
    }
    
    public static IBlockState getBlockState(final BlockPos blockPos) {
        return Class39.mc.theWorld.getBlockState(blockPos);
    }
    
    static {
        Class39.mc = Minecraft.getMinecraft();
        Class39.rand = new Random();
        Class39.blackList = new ArrayList<Integer>();
        Class39.lastLoc = null;
    }
}
