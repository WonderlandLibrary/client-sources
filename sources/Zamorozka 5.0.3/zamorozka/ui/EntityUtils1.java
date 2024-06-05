package zamorozka.ui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextFormatting;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Random;

public class EntityUtils1
{
    public static Minecraft mc = Minecraft.getMinecraft();
    public static float[] rotationsToBlock = null;

    public static float[] getRotationsNeeded(Entity entity)
    {
        if (entity == null)
        {
            return null;
        }
        else
        {
            double diffX = entity.posX - mc.player.posX;
            double diffY1;

            if (entity instanceof EntityLivingBase)
            {
                EntityLivingBase entityLivingBase = (EntityLivingBase)entity;
                diffY1 = entityLivingBase.posY + (double)entityLivingBase.getEyeHeight() * 0.9D - (mc.player.posY + (double)Minecraft.getMinecraft().player.getEyeHeight());
            }
            else
            {
                diffY1 = (entity.boundingBox.minY + entity.boundingBox.maxY) / 2.0D - (mc.player.posY + (double)Minecraft.getMinecraft().player.getEyeHeight());
            }

            double diffZ = entity.posZ - mc.player.posZ;
            double dist = (double)MathHelper.sqrt(diffX * diffX + diffZ * diffZ);
            float yaw = (float)(Math.atan2(diffZ, diffX) * 180.0D / Math.PI) - 90.0F;
            float pitch = (float)(-(Math.atan2(diffY1, dist) * 180.0D / Math.PI));
            return new float[] {mc.player.rotationYaw + MathHelper.wrapDegrees(yaw - mc.player.rotationYaw), mc.player.rotationPitch + MathHelper.wrapDegrees(pitch - mc.player.rotationPitch)};
        }
    }
    
    public static boolean isLowHealth(EntityLivingBase entity, EntityLivingBase entityPriority) {
		return entityPriority == null || entity.getHealth() < entityPriority.getHealth();
	}
	
	public static boolean isClosest(EntityLivingBase entity, EntityLivingBase entityPriority) {
		return entityPriority == null || mc.player.getDistanceToEntity(entity) <mc.player.getDistanceToEntity(entityPriority);
	}
	
    public static boolean isInAttackFOV(EntityLivingBase entity, int fov) {
        return getDistanceFromMouse(entity) <= fov;
    }
    
	public static int getDistanceFromMouse(final EntityLivingBase entity) {
		final float[] neededRotations = getRotationsNeeded(entity);
		if (neededRotations != null) {
			final float neededYaw = mc.player.rotationYaw - neededRotations[0];
			final float neededPitch = mc.player.rotationPitch - neededRotations[1];
			final float distanceFromMouse = MathHelper.sqrt(neededYaw * neededYaw + neededPitch * neededPitch * 2.0f);
			return (int) distanceFromMouse;
		}
		return -1;
	}

    public static float Pitch(EntityLivingBase ent)
    {
        double x = ent.posX - mc.player.posX;
        double y = ent.posY - mc.player.posY;
        double z = ent.posZ - mc.player.posZ;
        y = y / (double)mc.player.getDistanceToEntity(ent);
        double pitch = Math.asin(y) * 57.0D;
        pitch = -pitch;
        return (float)pitch;
    }

    public static float Yaw(EntityLivingBase ent)
    {
        double x = ent.posX - mc.player.posX;
        double y = ent.posY - mc.player.posY;
        double z = ent.posZ - mc.player.posZ;
        double yaw = Math.atan2(x, z) * 57.0D;
        yaw = -yaw;
        return (float)yaw;
    }
	public static boolean isCorrectEntity(Object o, boolean ignoreFriends)
	{
		boolean condition = false;
		if(ignoreFriends && o instanceof EntityPlayer)

					condition = false;
		return condition;
	}
	public static EntityLivingBase getClosestEntity(boolean ignoreFriends)
	{
		EntityLivingBase closestEntity = null;
		for(Object o : Minecraft.getMinecraft().world.loadedEntityList)
			if(isCorrectEntity(o, ignoreFriends))
			{
				EntityLivingBase en = (EntityLivingBase)o;
				if(!(o instanceof EntityPlayerSP) && !en.isDead && en.getHealth() > 0 && Minecraft.getMinecraft().player.canEntityBeSeen(en))
					if(closestEntity == null || Minecraft.getMinecraft().player.getDistanceToEntity(en) < Minecraft.getMinecraft().player.getDistanceToEntity(closestEntity))
						closestEntity = en;
			}
		return closestEntity;
	}

    public static Vec3d getEyesPos()
    {
        return new Vec3d(mc.player.posX, mc.player.posY + (double)mc.player.getEyeHeight(), mc.player.posZ);
    }

    private static float[] getNeededRotations2(Vec3d vec)
    {
        Vec3d eyesPos = getEyesPos();
        double diffX = vec.xCoord - eyesPos.xCoord;
        double diffY = vec.yCoord - eyesPos.yCoord;
        double diffZ = vec.zCoord - eyesPos.zCoord;
        double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);
        float yaw = (float)Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0F;
        float pitch = (float)(-Math.toDegrees(Math.atan2(diffY, diffXZ)));
        return new float[] {mc.player.rotationYaw + MathHelper.wrapDegrees(yaw - mc.player.rotationYaw), mc.player.rotationPitch + MathHelper.wrapDegrees(pitch - mc.player.rotationPitch)};
    }

    public static void faceVectorPacketInstant(Vec3d vec)
    {
        rotationsToBlock = getNeededRotations2(vec);

        
        mc.player.connection.sendPacket(new CPacketPlayer.Rotation(rotationsToBlock[0],rotationsToBlock[1],false));

    }

    public static boolean isMoving(Entity e)
    {
        return e.motionX != 0.0D && e.motionZ != 0.0D && (e.motionY != 0.0D || e.motionY > 0.0D);
    }

    public static void damagePlayer()
    {
        double x = mc.player.posX;
        double y = mc.player.posY;
        double z = mc.player.posZ;

        for (int i = 0; i < 65; ++i)
        {
            mc.player.connection.sendPacket(new CPacketPlayer.Position(x, y + 0.049D, z, false));
            mc.player.connection.sendPacket(new CPacketPlayer.Position(x, y, z, false));
        }

        mc.player.connection.sendPacket(new CPacketPlayer.Position(x, y, z, true));
    }

    public static float[] facePacketEntity(Entity par1Entity, float par2, float par3)
    {
        new Random();
        double var4 = par1Entity.posX - mc.player.posX;
        double var6 = par1Entity.posZ - mc.player.posZ;
        double var8;

        if (par1Entity instanceof EntityLivingBase)
        {
            EntityLivingBase var10 = (EntityLivingBase)par1Entity;
            var8 = var10.posY + ((double)var10.getEyeHeight() - 0.6D) - (mc.player.posY + (double)mc.player.getEyeHeight());
        }
        else
        {
            var8 = (par1Entity.boundingBox.minY + par1Entity.boundingBox.maxY) / 2.0D - (mc.player.posY + (double)mc.player.getEyeHeight());
        }

        double var14 = (double)MathHelper.sqrt(var4 * var4 + var6 * var6);
        float var12 = (float)(Math.atan2(var6, var4) * 180.0D / Math.PI) - 90.0F;
        float var13 = (float)(-(Math.atan2(var8, var14) * 180.0D / Math.PI));
        return new float[] {EntityLiving.updateRotation(mc.player.rotationYaw, var12, par2), EntityLiving.updateRotation(mc.player.rotationPitch, var13, par3)};
    }

    public static double round(double value, int places)
    {
        if (places < 0)
        {
            throw new IllegalArgumentException();
        }
        else
        {
            BigDecimal bd = new BigDecimal(value);
            bd = bd.setScale(places, RoundingMode.HALF_UP);
            return bd.doubleValue();
        }
    }


	public static ArrayList<EntityLivingBase> getCloseEntities(boolean ignoreFriends, float range)
	{
		ArrayList<EntityLivingBase> closeEntities = new ArrayList<EntityLivingBase>();
		for(Object o : Minecraft.getMinecraft().world.loadedEntityList)
			if(isCorrectEntity(o, ignoreFriends))
			{
				EntityLivingBase en = (EntityLivingBase)o;
				if(!(o instanceof EntityPlayerSP)
					&& !en.isDead && en.getHealth() > 0
					&& Minecraft.getMinecraft().player.canEntityBeSeen(en)
					&& Minecraft.getMinecraft().player.getDistanceToEntity(en) <= range)
					closeEntities.add(en);
			}
		return closeEntities;
	}

	public synchronized static void faceEntityPacket(EntityLivingBase entity)
	{
		float[] rotations = getRotationsNeeded(entity);
		if(rotations != null)
		{
			float yaw = rotations[0];
			float pitch = rotations[1];
		}
	}
	
	public static float[] getEntityRotations(Entity target) {
        final double var4 = target.posX - mc.player.posX;
        final double var6 = target.posZ -mc.player.posZ;
        final double var8 = target.posY + target.getEyeHeight() / 1.3 - (mc.player.posY + mc.player.getEyeHeight());
        final double var14 = MathHelper.sqrt_double(var4 * var4 + var6 * var6);
        final float yaw = (float) (Math.atan2(var6, var4) * 180.0D / Math.PI) - 90.0F;
        final float pitch = (float) -(Math.atan2(var8, var14) * 180.0D / Math.PI);
        return new float[]{yaw, pitch};
    }
	
	public static boolean isTeams(final EntityPlayer otherEntity) {
        boolean b = false;
        TextFormatting TextFormatting = null;
        TextFormatting TextFormatting2 = null;
        if (otherEntity != null) {
            for (final TextFormatting TextFormatting3 : TextFormatting.values()) {
                if (TextFormatting3 != TextFormatting.RESET) {
                    if (mc.player.getDisplayName().getFormattedText().contains(TextFormatting3.toString()) && TextFormatting == null) {
                        TextFormatting = TextFormatting3;
                    }
                    if (otherEntity.getDisplayName().getFormattedText().contains(TextFormatting3.toString()) && TextFormatting2 == null) {
                        TextFormatting2 = TextFormatting3;
                    }
                }
            }
            try {
                if (TextFormatting != null && TextFormatting2 != null) {
                    b = (TextFormatting != TextFormatting2);
                } else if (mc.player.getTeam() != null) {
                    b = !mc.player.isOnSameTeam(otherEntity);
                } else if (mc.player.inventory.armorInventory.get(3).getItem() instanceof ItemBlock) {
                    b = !ItemStack.areItemStacksEqual(mc.player.inventory.armorInventory.get(3), otherEntity.inventory.armorInventory.get(3));
                }
            } catch (Exception ignored) {
                ;
            }
        }
        return b;
    }

}
