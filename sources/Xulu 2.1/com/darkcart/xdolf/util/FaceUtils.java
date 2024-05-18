package com.darkcart.xdolf.util;

import com.darkcart.xdolf.Wrapper;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;

public class FaceUtils
{
	private static boolean fakeRotation;
	private static float serverYaw;
	private static float serverPitch;
	
	public static Vec3d getEyesPos()
	{
		return new Vec3d(Wrapper.getPlayer().posX,
			Wrapper.getPlayer().posY + Wrapper.getPlayer().getEyeHeight(),
			Wrapper.getPlayer().posZ);
	}
	
	public static Vec3d getClientLookVec()
	{
		float f = MathUtils.cos(-Wrapper.getPlayer().rotationYaw * 0.017453292F
			- (float)Math.PI);
		float f1 = MathUtils.sin(-Wrapper.getPlayer().rotationYaw * 0.017453292F
			- (float)Math.PI);
		float f2 =
			-MathUtils.cos(-Wrapper.getPlayer().rotationPitch * 0.017453292F);
		float f3 =
				MathUtils.sin(-Wrapper.getPlayer().rotationPitch * 0.017453292F);
		return new Vec3d(f1 * f2, f3, f * f2);
	}
	
	public static Vec3d getServerLookVec()
	{
		float f = MathUtils.cos(-serverYaw * 0.017453292F - (float)Math.PI);
		float f1 = MathUtils.sin(-serverYaw * 0.017453292F - (float)Math.PI);
		float f2 = -MathUtils.cos(-serverPitch * 0.017453292F);
		float f3 = MathUtils.sin(-serverPitch * 0.017453292F);
		return new Vec3d(f1 * f2, f3, f * f2);
	}
	
	private static float[] getNeededRotations(Vec3d vec)
	{
		Vec3d eyesPos = getEyesPos();
		
		double diffX = vec.xCoord - eyesPos.xCoord;
		double diffY = vec.yCoord - eyesPos.yCoord;
		double diffZ = vec.zCoord - eyesPos.zCoord;
		
		double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);
		
		float yaw = (float)Math.toDegrees(Math.atan2(diffZ, diffX)) - 90F;
		float pitch = (float)-Math.toDegrees(Math.atan2(diffY, diffXZ));
		
		return new float[]{MathUtils.wrapDegrees(yaw), MathUtils.wrapDegrees(pitch)};
	}
	
	public static float limitAngleChange(float current, float intended,
		float maxChange)
	{
		float change = MathUtils.wrapDegrees(intended - current);
		
		change = MathUtils.clamp(change, -maxChange, maxChange);
		
		return MathUtils.wrapDegrees(current + change);
	}
	
	public static boolean faceVectorPacket(Vec3d vec)
	{
		// use fake rotation in next packet
		fakeRotation = true;
		
		float[] rotations = getNeededRotations(vec);
		
		serverYaw = limitAngleChange(serverYaw, rotations[0], 30);
		serverPitch = rotations[1];
		
		return Math.abs(serverYaw - rotations[0]) < 1F;
	}
	
	public static void faceVectorPacketInstant(Vec3d vec)
	{
		float[] rotations = getNeededRotations(vec);
		
		Wrapper.sendPacket(new CPacketPlayer.Rotation(rotations[0],
			rotations[1], Wrapper.getPlayer().onGround));
	}
	
	public static boolean faceVectorClient(Vec3d vec)
	{
		float[] rotations = getNeededRotations(vec);
		
		float oldYaw = Wrapper.getPlayer().prevRotationYaw;
		float oldPitch = Wrapper.getPlayer().prevRotationPitch;
		
		Wrapper.getPlayer().rotationYaw =
			limitAngleChange(oldYaw, rotations[0], 30);
		Wrapper.getPlayer().rotationPitch = rotations[1];
		
		return Math.abs(oldYaw - rotations[0])
			+ Math.abs(oldPitch - rotations[1]) < 1F;
	}
	
	public static boolean faceEntityClient(EntityEnderCrystal e)
	{
		// get position & rotation
		Vec3d eyesPos = getEyesPos();
		Vec3d lookVec = getServerLookVec();
		
		// try to face center of boundingBox
		AxisAlignedBB bb = e.boundingBox;
		if(faceVectorClient(bb.getCenter()))
			return true;
		
		// if not facing center, check if facing anything in boundingBox
		return bb.calculateIntercept(eyesPos,
			eyesPos.add(lookVec.scale(6))) != null;
	}
	
	public static boolean faceEntityPacket(Entity entity)
	{
		// get position & rotation
		Vec3d eyesPos = getEyesPos();
		Vec3d lookVec = getServerLookVec();
		
		// try to face center of boundingBox
		AxisAlignedBB bb = entity.boundingBox;
		if(faceVectorPacket(bb.getCenter()))
			return true;
		
		// if not facing center, check if facing anything in boundingBox
		return bb.calculateIntercept(eyesPos,
			eyesPos.add(lookVec.scale(6))) != null;
	}
	
	public static boolean faceVectorForWalking(Vec3d vec)
	{
		float[] rotations = getNeededRotations(vec);
		
		float oldYaw = Wrapper.getPlayer().prevRotationYaw;
		
		Wrapper.getPlayer().rotationYaw =
			limitAngleChange(oldYaw, rotations[0], 30);
		Wrapper.getPlayer().rotationPitch = 0;
		
		return Math.abs(oldYaw - rotations[0]) < 1F;
	}
	
	public static float getAngleToClientRotation(Vec3d vec)
	{
		float[] needed = getNeededRotations(vec);
		
		float diffYaw =
			MathUtils.wrapDegrees(Wrapper.getPlayer().rotationYaw) - needed[0];
		float diffPitch =
			MathUtils.wrapDegrees(Wrapper.getPlayer().rotationPitch) - needed[1];
		
		float angle =
			(float)Math.sqrt(diffYaw * diffYaw + diffPitch * diffPitch);
		
		return angle;
	}
	
	public static float getAngleToServerRotation(Vec3d vec)
	{
		float[] needed = getNeededRotations(vec);
		
		float diffYaw = serverYaw - needed[0];
		float diffPitch = serverPitch - needed[1];
		
		float angle =
			(float)Math.sqrt(diffYaw * diffYaw + diffPitch * diffPitch);
		
		return angle;
	}
	
	public static void updateServerRotation()
	{
		// disable fake rotation in next packet unless manually enabled again
		if(fakeRotation)
		{
			fakeRotation = false;
			return;
		}
		
		// slowly synchronize server rotation with client
		serverYaw =
			limitAngleChange(serverYaw, Wrapper.getPlayer().rotationYaw, 30);
		serverPitch = Wrapper.getPlayer().rotationPitch;
	}
	
	public static float getServerYaw()
	{
		return serverYaw;
	}
	
	public static float getServerPitch()
	{
		return serverPitch;
	}
}