package net.minecraft.client.triton.utils;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.pathfinding.PathFinder;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.util.Vec3;
import net.minecraft.world.pathfinder.WalkNodeProcessor;

public class PathFind {

	public EntityPlayer pos;
	public PathFinder pathFinder = new PathFinder(new WalkNodeProcessor());
	private float yaw;
	public static float fakeYaw;
	public static float fakePitch;

	public PathFind(String name) {
		for (Object i : ClientUtils.world().loadedEntityList) {
			if(i instanceof EntityPlayer && i != null) {
				EntityPlayer player = (EntityPlayer)i;
				if(player.getName().contains(name)) {
					this.pos = player;
				}
			}
		}
		if(this.pos != null) {
			move();
			float[] rot = getRotationTo(pos.getPositionVector());
			this.fakeYaw = rot[0];
			this.fakePitch = rot[1];
		}
	}

	public void move() {
		if (ClientUtils.player().getDistance(pos.posX + 0.5, pos.posY + 0.5, pos.posZ + 0.5) > 0.3) {						
			PathEntity pe = pathFinder.func_176188_a(ClientUtils.world(), ClientUtils.player(), pos, 40);
			if (pe != null) {
				if (pe.getCurrentPathLength() > 1) {
					PathPoint point = pe.getPathPointFromIndex(1);
					float[] rot = getRotationTo(new Vec3(point.xCoord + 0.5, point.yCoord + 0.5, point.zCoord + 0.5));

					this.yaw = rot[0];
					ClientUtils.player().motionX = ClientUtils.player().motionZ = 0;
					double offset = 0.26D;
					double newx = Math.sin((yaw) * (float) Math.PI / 180.0F) * offset;
					double newz = Math.cos((yaw) * (float) Math.PI / 180.0F) * offset;
					ClientUtils.player().motionX -= newx;
					ClientUtils.player().motionZ += newz;
					if(ClientUtils.player().onGround && ClientUtils.player().isCollidedHorizontally) {
						ClientUtils.player().jump();
					}
				}
			}
		}
	}	
	
	public static float angleDifference(float to, float from) {
		return ((((to - from) % 360f) + 540f) % 360f) - 180f;
	}

	public float[] getRotationTo(Vec3 pos) {
		double xD = ClientUtils.player().posX - pos.xCoord;
		double yD = (ClientUtils.player().posY + ClientUtils.player().getEyeHeight()) - pos.yCoord;
		double zD = ClientUtils.player().posZ - pos.zCoord;
		double yaw = Math.atan2(zD, xD);
		double pitch = Math.atan2(yD, Math.sqrt(Math.pow(xD, 2) + Math.pow(zD, 2)));
		return new float[] { (float) Math.toDegrees(yaw) + 90, (float) Math.toDegrees(pitch) };
	}

}
