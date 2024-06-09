package me.protocol_client.utils;

import me.protocol_client.Protocol;
import me.protocol_client.Wrapper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C0BPacketEntityAction;

public class EntityUtils
{
	public static boolean lookChanged;
	public static float yaw;
	public static float pitch;
	private static boolean set = false;
	private static EntityPlayer reference;
	
	public static EntityPlayer getReference() {
        return reference == null ? reference = Wrapper.getPlayer() : ((set) ? Wrapper.getPlayer() : reference);
    }

	public static double[] teleportToPosition(double[] startPosition, double[] endPosition, double setOffset, double slack, boolean extendOffset, boolean onGround) {
        boolean wasSneaking = false;

        if (Wrapper.getPlayer().isSneaking())
            wasSneaking = true;

        double startX = startPosition[0];
        double startY = startPosition[1];
        double startZ = startPosition[2];

        double endX = endPosition[0];
        double endY = endPosition[1];
        double endZ = endPosition[2];

        double distance = Math.abs(startX - startY) + Math.abs(startY - endY) + Math.abs(startZ - endZ);

        int count = 0;
        while (distance > slack) {
            distance = Math.abs(startX - endX) + Math.abs(startY - endY) + Math.abs(startZ - endZ);

            if (count > 120) {
                break;
            }

            double offset = extendOffset && (count & 0x1) == 0 ? setOffset + 0.15D : setOffset;

            double diffX = startX - endX;
            double diffY = startY - endY;
            double diffZ = startZ - endZ;

            if (diffX < 0.0D) {
                if (Math.abs(diffX) > offset) {
                    startX += offset;
                } else {
                    startX += Math.abs(diffX);
                }
            }
            if (diffX > 0.0D) {
                if (Math.abs(diffX) > offset) {
                    startX -= offset;
                } else {
                    startX -= Math.abs(diffX);
                }
            }
            if (diffY < 0.0D) {
                if (Math.abs(diffY) > offset) {
                    startY += offset;
                } else {
                    startY += Math.abs(diffY);
                }
            }
            if (diffY > 0.0D) {
                if (Math.abs(diffY) > offset) {
                    startY -= offset;
                } else {
                    startY -= Math.abs(diffY);
                }
            }
            if (diffZ < 0.0D) {
                if (Math.abs(diffZ) > offset) {
                    startZ += offset;
                } else {
                    startZ += Math.abs(diffZ);
                }
            }
            if (diffZ > 0.0D) {
                if (Math.abs(diffZ) > offset) {
                    startZ -= offset;
                } else {
                    startZ -= Math.abs(diffZ);
                }
            }

            if (wasSneaking) {
            	Wrapper.mc().getNetHandler().addToSendQueue(new C0BPacketEntityAction(Wrapper.getPlayer(), C0BPacketEntityAction.Action.STOP_SNEAKING));
            }

            Wrapper.mc().getNetHandler().getNetworkManager().sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(startX, startY, startZ, onGround));
            count++;
        }

        if (wasSneaking) {
            Wrapper.mc().getNetHandler().addToSendQueue(new C0BPacketEntityAction(Wrapper.getPlayer(), C0BPacketEntityAction.Action.START_SNEAKING));
        }

        return new double[]{startX, startY, startZ};
    }
	public static EntityPlayer getPlayerbyName(String name){
		EntityPlayer player = null;
		for(Object o : Wrapper.getWorld().loadedEntityList){
			if(o instanceof EntityPlayer){
				EntityPlayer p = (EntityPlayer)o;
				if(p.getName().equalsIgnoreCase(name)){
					player = p;
				}
			}
		}
		return player;
	}
}