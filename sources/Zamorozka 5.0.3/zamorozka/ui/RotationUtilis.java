package zamorozka.ui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.math.*;
import optifine.Reflector;
import zamorozka.event.events.EventMoveFlying;
import zamorozka.event.events.EventPreMotionUpdates;
import zamorozka.main.Zamorozka;
import zamorozka.modules.COMBAT.KillAura;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public final class RotationUtilis
        implements MCUtil {
	public static double smoothAimSpeed = 2.5;
    public static final List<Float> YAW_SPEEDS = null;
    public static Rotation currentRotation;
    public static Rotation lastRotation = new Rotation(0.0F, 0.0F);
    public static boolean moveToRotation;
    public static boolean jumpFix;
    private float field_76336_a;
    private float field_76334_b;
    private float field_76335_c;
    
	public static double targetedarea;
	public static boolean changingArea;
	public static boolean blocking;
	public static boolean reverse;
	public static int delay;
	public static int index;
	public static int maxYaw;
	public static int reachVL;
	public static int hitCounter;
	public static int maxPitch;
	public static int targetIndex;
	public static int rotationSwap;
	public static int timesAttacked;
	public static int offset;
	public static int waitTicks;
	public static float currentYaw, currentPitch, pitchincrease, animated = 20F;
	public static Entity lastAimedTarget;
	public static EntityLivingBase target;
	public static EntityLivingBase currentEntity;

	private final Random RANDOM_NUMBER_GENERATOR = new Random();
    
    static Random rand = new Random();

    public static Rotation fixedRotations(double paramDouble1, double paramDouble2) {
        float f1 = mc.gameSettings.mouseSensitivity * 0.6F + 0.2F;
        float f2 = f1 * f1 * f1 * 8.0F;
        float f3 = (float) (paramDouble1 - lastRotation.yaw);
        float f4 = (float) (paramDouble2 - lastRotation.pitch);
        f3 = (float) (f3 - f3 % (f2 * 0.15D));
        f4 = (float) (f4 - f4 % (f2 * 0.15D));
        return new Rotation(lastRotation.yaw + f3, MathHelper.clamp(lastRotation.pitch + f4, -90.0F, 90.0F));
    }
    
	public static double normalizeAngle(double angle) {
		return (angle + 360) % 360;
	}

	public static float normalizeAngle(float angle) {
		return (angle + 360) % 360;
	}
    
	public static float[] getFacePosEntity(Entity en) {
		if (en == null) {
			return new float[] { Minecraft.getMinecraft().player.rotationYawHead,
					Minecraft.getMinecraft().player.rotationPitch };
		}
		return getFacePos(new Vec3d(en.posX - 0.5, en.posY + (en.getEyeHeight() - en.height / 1.5), en.posZ - 0.5));
	}
	
	public static Float[] getLookAngles(net.minecraft.util.math.Vec3d vec) {
		Float[] angles = new Float[2];
		Minecraft mc = Minecraft.getMinecraft();
		angles[0] = Float.valueOf((float) (Math.atan2(mc.player.posZ - vec.zCoord, mc.player.posX - vec.xCoord) / Math.PI * 180.0D) + 90.0F);
		float heightdiff = (float) (mc.player.posY + mc.player.getEyeHeight() - vec.yCoord);
		float distance = (float) Math.sqrt((mc.player.posZ - vec.zCoord) * (mc.player.posZ - vec.zCoord) + (mc.player.posX - vec.xCoord) * (mc.player.posX - vec.xCoord));
		angles[1] = Float.valueOf((float) (Math.atan2(heightdiff, distance) / Math.PI * 180.0D));
		return angles;
	}
	
	public static boolean isLookingAtEntity1(Entity e) {
		return isLookingAt2(e.getPositionEyes((Minecraft.getMinecraft()).timer.renderPartialTicks));
	}
	
	public static boolean isLookingAt2(net.minecraft.util.math.Vec3d vec) {
		Float[] targetangles = getLookAngles(vec);
		targetangles = getLookAngles(vec);
		float change = Math.abs(MathHelper.wrapAngleTo180_float(targetangles[0].floatValue() - Zamorozka.getFakeYaw())) / 0.6f;
		return (change < 20.0F);
	}
	
	public static float[] getVodkaRotations(Entity e) {
		// Variables
		double diffX = target.posX - mc.player.posX;
		double diffZ = target.posZ - mc.player.posZ;
		double diffY;

		// Getting ~center of entity
		if (target instanceof EntityLivingBase) {
			EntityLivingBase entitylivingbase = (EntityLivingBase) target;
			float randomed = RandomUtils.nextFloat(
					(float) (entitylivingbase.posY + entitylivingbase.getEyeHeight() / 3F),
					(float) (entitylivingbase.posY + entitylivingbase.getEyeHeight()
							- entitylivingbase.getEyeHeight() / 5F));
			// System.out.println((randomed));
			diffY = randomed - (mc.player.posY + (double) mc.player.getEyeHeight());
		} else {
			diffY = RandomUtils.nextFloat((float) target.getEntityBoundingBox().minY,
					(float) target.getEntityBoundingBox().maxY)
					- (mc.player.posY + (double) mc.player.getEyeHeight());
		}

		// Distance between player and entity
	    double dist = MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ);

		// Getting needed rotations
	    float yaw = (float) (((Math.atan2(diffZ, diffX) * 180.0 / Math.PI) - 90.0f))
				+ RandomUtils.nextFloat(-2F, 2F);
		float pitch = (float) (-(Math.atan2(diffY, dist) * 180.0 / Math.PI));

		// WTF WHY ITS NOT CLAMPING AHHH
		// System.out.println(wrapAngleTo180_float(yaw - mc.player.rotationYaw) + " " + (yaw
		// - mc.player.rotationYaw));
		yaw = (mc.player.rotationYaw
				+ GCDFix.getFixedRotation(MathHelper.wrapAngleTo180_float(yaw - mc.player.rotationYaw)));
		pitch = mc.player.rotationPitch
				+ GCDFix.getFixedRotation(MathHelper.wrapAngleTo180_float(pitch - mc.player.rotationPitch));
		pitch = MathHelper.clamp_float(pitch, -90F, 90F);
		return new float[] { yaw, pitch };
	}
	
	public static float[] getFacePosEntityRemote(EntityLivingBase facing, Entity en) {
		if (en == null) {
			return new float[] { facing.rotationYawHead, facing.rotationPitch };
		}
		return getFacePosRemote(new Vec3d(facing.posX, facing.posY + en.getEyeHeight(), facing.posZ),
				new Vec3d(en.posX, en.posY + en.getEyeHeight(), en.posZ));
	}
	
	public static Object[] getEntity(double distance, double expand, float partialTicks) {
		Entity var2 = mc.getRenderViewEntity();
		Entity entity = null;
		if (var2 != null && mc.world != null) {
			double var3;
			mc.mcProfiler.startSection("pick");
			double var5 = var3 = distance;
			Vec3d var7 = var2.getPositionEyes(0.0f);
			Vec3d var8 = var2.getLook(0.0f);
			Vec3d var9 = var7.addVector(var8.xCoord * var3, var8.yCoord * var3, var8.zCoord * var3);
			Vec3d var10 = null;
			float var11 = 1.0f;
			List var12 = mc.world.getEntitiesWithinAABBExcludingEntity(var2, var2.getEntityBoundingBox()
					.addCoord(var8.xCoord * var3, var8.yCoord * var3, var8.zCoord * var3).expand(var11, var11, var11));
			double var13 = var5;
			for (int var15 = 0; var15 < var12.size(); ++var15) {
				double var20;
				Entity var16 = (Entity) var12.get(var15);
				if (!var16.canBeCollidedWith())
					continue;
				float var17 = var16.getCollisionBorderSize();
				AxisAlignedBB var18 = var16.getEntityBoundingBox().expand(var17, var17, var17);
				var18 = var18.expand(expand, expand, expand);
				RayTraceResult var19 = var18.calculateIntercept(var7, var9);
				if (var18.isVecInside(var7)) {
					if (!(0.0 < var13) && var13 != 0.0)
						continue;
					entity = var16;
					var10 = var19 == null ? var7 : var19.hitVec;
					var13 = 0.0;
					continue;
				}
				if (var19 == null || !((var20 = var7.distanceTo(var19.hitVec)) < var13) && var13 != 0.0)
					continue;
				boolean canRiderInteract = false;
				if (Reflector.ForgeEntity_canRiderInteract.exists()) {
					canRiderInteract = Reflector.callBoolean(var16, Reflector.ForgeEntity_canRiderInteract,
							new Object[0]);
				}
				if (var16 == var2.ridingEntity && !canRiderInteract) {
					if (var13 != 0.0)
						continue;
					entity = var16;
					var10 = var19.hitVec;
					continue;
				}
				entity = var16;
				var10 = var19.hitVec;
				var13 = var20;
			}
			if (var13 < var5 && !(entity instanceof EntityLivingBase) && !(entity instanceof EntityItemFrame)) {
				entity = null;
			}
			mc.mcProfiler.endSection();
			if (entity == null || var10 == null) {
				return null;
			}
			return new Object[] { entity, var10 };
		}
		return null;
	}

	public static Entity getEntity(double distance) {
		if (getEntity(distance, 0.0, 0.0f) == null) {
			return null;
		}
		return (Entity) getEntity(distance, 0.0, 0.0f)[0];
	}
	
	protected static float getRotation(float currentRotation, float targetRotation, float maxIncrement) {
		float deltaAngle = MathHelper.wrapAngleTo180_float(targetRotation - currentRotation);
		if (deltaAngle > maxIncrement) {
			deltaAngle = maxIncrement;
		}
		if (deltaAngle < -maxIncrement) {
			deltaAngle = -maxIncrement;
		}
		return currentRotation + deltaAngle / 2.0f;
	}
	
	public static void faceTarget(Entity target, float yawspeed, float pitchspeed) {
		EntityPlayerSP player = mc.player;
		float yaw = getAngles(target)[1];
		float pitch = getAngles(target)[0];
		player.rotationYaw = getRotation(player.rotationYaw, yaw, yawspeed);
		player.rotationPitch = getRotation(player.rotationPitch, pitch, pitchspeed);
	}

	public static float[] getAngles(Entity entity) {
		double x = entity.posX - mc.player.posX;
		double z = entity.posZ - mc.player.posZ;
		double y = entity instanceof EntityEnderman ? entity.posY - mc.player.posY
				: entity.posY + ((double) entity.getEyeHeight() - 1.9) - mc.player.posY
						+ ((double) mc.player.getEyeHeight() - 1.9);
		double helper = MathHelper.sqrt_double(x * x + z * z);
		float newYaw = (float) Math.toDegrees(-Math.atan(x / z));
		float newPitch = (float) (-Math.toDegrees(Math.atan(y / helper)));
		if (z < 0.0 && x < 0.0) {
			newYaw = (float) (90.0 + Math.toDegrees(Math.atan(z / x)));
		} else if (z < 0.0 && x > 0.0) {
			newYaw = (float) (-90.0 + Math.toDegrees(Math.atan(z / x)));
		}
		return new float[] { newPitch, newYaw };
	}

	public static void smoothFacePos(Vec3d vec) {
		double diffX = vec.xCoord + 0.5 - Minecraft.getMinecraft().player.posX;
		double diffY = vec.yCoord + 0.5
				- (Minecraft.getMinecraft().player.posY + Minecraft.getMinecraft().player.getEyeHeight());
		double diffZ = vec.zCoord + 0.5 - Minecraft.getMinecraft().player.posZ;
		double dist = MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ);
		float yaw = (float) (Math.atan2(diffZ, diffX) * 180.0D / Math.PI) - 90.0F;

		float pitch = (float) -(Math.atan2(diffY, dist) * 180.0D / Math.PI);

		boolean aim = false;
		float max = 5;
		float yawChange = 0;
		if ((MathHelper.wrapAngleTo180_float(yaw - Minecraft.getMinecraft().player.rotationYaw)) > max * 2) {
			aim = true;
			yawChange = max;
		} else if ((MathHelper.wrapAngleTo180_float(yaw - Minecraft.getMinecraft().player.rotationYaw)) < -max * 2) {
			aim = true;
			yawChange = -max;
		}
		float pitchChange = 0;
		if ((MathHelper.wrapAngleTo180_float(pitch - Minecraft.getMinecraft().player.rotationPitch)) > max * 4) {
			aim = true;
			pitchChange = max;
		} else if ((MathHelper.wrapAngleTo180_float(pitch - Minecraft.getMinecraft().player.rotationPitch)) < -max
				* 4) {
			aim = true;
			pitchChange = -max;
		}
		if (aim) {
			Minecraft.getMinecraft().player.rotationYaw += (MathHelper
					.wrapAngleTo180_float(yaw - Minecraft.getMinecraft().player.rotationYaw))
					/ (KillauraUtil.getSmoothAimSpeed() * (rand.nextDouble() * 2 + 1));
			Minecraft.getMinecraft().player.rotationPitch += (MathHelper
					.wrapAngleTo180_float(pitch - Minecraft.getMinecraft().player.rotationPitch))
					/ (KillauraUtil.getSmoothAimSpeed() * (rand.nextDouble() * 2 + 1));
		}

	}
	
	public static float constrainAngle(float angle) {
		angle = angle % 360F;

		while (angle <= -180) {
			angle = angle + 360;
		}

		while (angle > 180) {
			angle = angle - 360;
		}
		return angle;
	}
	
	public static EntityLivingBase rayCast(float yaw, float pitch) {
		if (mc.world != null && mc.player != null) {
			Vec3d position = mc.player.getPositionEyes(mc.timer.renderPartialTicks);
			Vec3d lookVector = mc.player.getVectorForRotation(currentPitch, currentYaw);
			double reachDistance = Zamorozka.settingsManager.getSettingByName("AttackRange").getValDouble();
			Entity pointedEntity = null;
			List var5 = mc.world.getEntitiesWithinAABBExcludingEntity(mc.player,
					mc.player.getEntityBoundingBox()
							.addCoord(lookVector.xCoord * mc.playerController.getBlockReachDistance(),
									lookVector.yCoord * mc.playerController.getBlockReachDistance(),
									lookVector.zCoord * mc.playerController.getBlockReachDistance())
							.expand(reachDistance,reachDistance,reachDistance));
			for (int var6 = 0; var6 < var5.size(); ++var6) {
				Entity currentEntity = (Entity) var5.get(var6);

				if (currentEntity.canBeCollidedWith()) {
					RayTraceResult objPosition = currentEntity.getEntityBoundingBox()
							.expand((double) currentEntity.getCollisionBorderSize(),
									(double) currentEntity.getCollisionBorderSize(),
									(double) currentEntity.getCollisionBorderSize())
							.contract(0.1, 0.1, 0.1)
							.calculateIntercept(position, position.addVector(lookVector.xCoord * reachDistance,
									lookVector.yCoord * reachDistance, lookVector.zCoord * reachDistance));
					if (objPosition != null) {
						double distance = position.distanceTo(objPosition.hitVec);
						if (distance < reachDistance) {
							if (currentEntity == mc.player.ridingEntity
									&& !(Reflector.ForgeEntity_canRiderInteract.exists() && Reflector
											.callBoolean(currentEntity, Reflector.ForgeEntity_canRiderInteract))
									&& reachDistance == 0.0D) {
								pointedEntity = currentEntity;
							} else {
								pointedEntity = currentEntity;
								reachDistance = distance;
							}
						}
					}
				}
			}
			if (pointedEntity != null && (pointedEntity instanceof EntityLivingBase))
				return (EntityLivingBase) pointedEntity;
		}
		return null;
	}
	
	public static void aim(EventPreMotionUpdates e, int mode) {
		AngleUtility angleUtility = new AngleUtility(70, 250, 70, 200);
		Vector3<Double> enemyCoords = new Vector3<>( target.getEntityBoundingBox().minX + (target.getEntityBoundingBox().maxX - target.getEntityBoundingBox().minX) / 2, (target instanceof EntityPig || target instanceof EntitySpider ? target.getEntityBoundingBox().minY - target.getEyeHeight() / 1.5 : target.posY) - Math.abs(target.posY - mc.player.posY) / (1.5 + Math.abs(target.posY - mc.player.posY)), target.getEntityBoundingBox().minZ + (target.getEntityBoundingBox().maxZ - target.getEntityBoundingBox().minZ) / 2);
		Vector3<Double> myCoords = new Vector3<>(mc.player.getEntityBoundingBox().minX
				+ (mc.player.getEntityBoundingBox().maxX - mc.player.getEntityBoundingBox().minX) / 2,
				mc.player.posY,
				mc.player.getEntityBoundingBox().minZ
						+ (mc.player.getEntityBoundingBox().maxZ - mc.player.getEntityBoundingBox().minZ)
								/ 2);
		AngleUtility.Angle srcAngle = new AngleUtility.Angle(currentYaw, currentPitch);
		AngleUtility.Angle dstAngle = angleUtility.calculateAngle(enemyCoords, myCoords, target, rotationSwap);
		AngleUtility.Angle newSmoothing = angleUtility.smoothAngle(dstAngle, srcAngle, 300, 40 * 30);

		double x = target.posX - mc.player.posX + (target.lastTickPosX - target.posX) / 2;
		double z = target.posZ - mc.player.posZ + (target.lastTickPosZ - target.posZ) / 2;
		float destinationYaw = 0;
		if (lastAimedTarget != target) {
			index = 3;
			changingArea = false;
			targetedarea = rotationSwap = 0;
		}
 	   
		lastAimedTarget = target;
		double smooth = (1 + ((20) * .01));
		destinationYaw = constrainAngle(currentYaw - (float) -(Math.atan2(x, z) * (58 + targetedarea)));
		float pitch = newSmoothing.getPitch();
		if (pitch > 90f) {
			pitch = 90f;
		} else if (pitch < -90.0f) {
			pitch = -90.0f;
		}
		smooth = smooth + (mc.player.getDistanceToEntity(target) * .1
				+ (mc.player.ticksExisted % 4 == 0 ? 40 * .001 : 0));
		destinationYaw = (float) (currentYaw - destinationYaw / smooth);
		boolean ticks = mc.player.ticksExisted % 20 == 0;
		if (mc.player.ticksExisted % 15 == 0) {
			if (rotationSwap++ >= 3) {
				rotationSwap = 0;
			}
			pitchincrease += changingArea ? MathUtils.getRandomInRange(-.055, -.075) : MathUtils.getRandomInRange(.055, .075);
		}
		if (pitchincrease >= .9) {
			changingArea = true;
		}
		if (pitchincrease <= -.15) {
			changingArea = false;
		}
		if (mode == 1) {
			float playerYaw = mc.player.rotationYaw;
			float playerPitch = mc.player.rotationPitch;
			float f = mc.gameSettings.mouseSensitivity * 0.6F + 0.2F;
			float f1 = f * f * f * 8.0F;
			float f2 = (float) maxYaw * f1;
			float f3 = (float) maxPitch * f1;
			if (Math.abs(playerYaw - destinationYaw) > 2) {
				if (rayCast(playerYaw, playerPitch) == null) {
					if (playerYaw > destinationYaw) {
						maxYaw -= MathUtils.getRandomInRange(5, 7);
					} else {
						maxYaw += MathUtils.getRandomInRange(5, 7);
					}
				} else {
					maxYaw *= .5;
				}
			} else {
				maxYaw *= .5;
			}
			if (Math.abs(playerPitch - AngleUtility.getRotations(target)[1]) > 2) {
				if (rayCast(playerYaw, playerPitch) == null) {
					if (playerPitch > AngleUtility.getRotations(target)[1]) {
						maxPitch += MathUtils.getRandomInRange(1, 3);
					} else {
						maxPitch -= MathUtils.getRandomInRange(1, 3);
					}
				} else {
					maxPitch *= .5;
				}
			} else {
				maxPitch *= .5;
			}
			float yaw2 = MathHelper.clamp((float) ((double) playerPitch - (double) f3 * 0.15D), -90.0F, 90.0F);
			float pitch2 = (float) ((double) playerYaw + (double) f2 * 0.15D);
			e.setYaw(yaw2);
			e.setPitch(pitch2);
			mc.player.rotationYawHead = yaw2;
			mc.player.rotationPitchHead = pitch2;
			mc.player.renderYawOffset = yaw2;
		} else if (mode == 2) {
			float theYaw = (float) MathUtils.preciseRound(destinationYaw, 1) + (ticks ? .243437f : .14357f);
			float thePitch = (float) MathUtils.preciseRound(pitch, 1) + (ticks ? .1335f : .13351f);
			currentPitch = thePitch;
			currentYaw = theYaw;
			if (KillAura.target != null) {
				e.setPitch(currentPitch = thePitch);
				e.setYaw(currentYaw = theYaw);
			}
		} else if (mode == 3) {
			float theYaw = (float) MathUtils.preciseRound(AngleUtility.getRotations(target)[0], 1) + (ticks ? .243437f : .14357f);
			float thePitch = (float) MathUtils.preciseRound(AngleUtility.getRotations(target)[1], 1) + (ticks ? .1335f : .13351f);
			currentPitch = thePitch;
			currentYaw = theYaw;
			if (KillAura.target != null) {
				e.setPitch(currentPitch);
				e.setYaw(currentYaw);
			}
		}
	}
	
	
	/**
	 * 
	 * @param vec
	 * @return index 0 = yaw | index 1 = pitch
	 */
	public static float[] getFacePos(Vec3d vec) {
		double diffX = vec.xCoord + 0.5 - Minecraft.getMinecraft().player.posX;
		double diffY = vec.yCoord + 0.5
				- (Minecraft.getMinecraft().player.posY + Minecraft.getMinecraft().player.getEyeHeight());
		double diffZ = vec.zCoord + 0.5 - Minecraft.getMinecraft().player.posZ;
		double dist = MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ);
		float yaw = (float) (Math.atan2(diffZ, diffX) * 180.0D / Math.PI) - 90.0F;
		float pitch = (float) -(Math.atan2(diffY, dist) * 180.0D / Math.PI);
		return new float[] {
				Minecraft.getMinecraft().player.rotationYaw
						+ MathHelper.wrapAngleTo180_float(yaw - Minecraft.getMinecraft().player.rotationYaw),
				Minecraft.getMinecraft().player.rotationPitch
						+ MathHelper.wrapAngleTo180_float(pitch - Minecraft.getMinecraft().player.rotationPitch) };
	}

	/**
	 * 
	 * 
	 * @return index 0 = yaw | index 1 = pitch
	 */
	public static float[] getFacePosRemote(Vec3d src, Vec3d dest) {
		double diffX = dest.xCoord - src.xCoord;
		double diffY = dest.yCoord - (src.yCoord);
		double diffZ = dest.zCoord - src.zCoord;
		double dist = MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ);
		float yaw = (float) (Math.atan2(diffZ, diffX) * 180.0D / Math.PI) - 90.0F;
		float pitch = (float) -(Math.atan2(diffY, dist) * 180.0D / Math.PI);
		return new float[] {MathHelper.wrapAngleTo180_float(yaw),
				MathHelper.wrapAngleTo180_float(pitch) };
	}

	public static void smoothFacePos(Vec3d vec, double addSmoothing) {
		double diffX = vec.xCoord + 0.5 - Minecraft.getMinecraft().player.posX;
		double diffY = vec.yCoord + 0.5
				- (Minecraft.getMinecraft().player.posY + Minecraft.getMinecraft().player.getEyeHeight());
		double diffZ = vec.zCoord + 0.5 - Minecraft.getMinecraft().player.posZ;
		double dist = MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ);
		float yaw = (float) (Math.atan2(diffZ, diffX) * 180.0D / Math.PI) - 90.0F;

		float pitch = (float) -(Math.atan2(diffY, dist) * 180.0D / Math.PI);

		Minecraft.getMinecraft().player.rotationYaw += (MathHelper
				.wrapAngleTo180_float(yaw - Minecraft.getMinecraft().player.rotationYaw))
				/ (KillauraUtil.getSmoothAimSpeed() * addSmoothing);
		Minecraft.getMinecraft().player.rotationPitch += (MathHelper
				.wrapAngleTo180_float(pitch - Minecraft.getMinecraft().player.rotationPitch))
				/ (KillauraUtil.getSmoothAimSpeed() * addSmoothing);
	}

    /*public static float[] calculateSilentStrafe(EventMoveFlying paramEventMoveFlying) {
        int i = (int) ((MathHelper.wrapAngleTo180_float(mc.player.rotationYaw - currentRotation.yaw) + 180.0F) / 45.0F);
        float f1 = paramEventMoveFlying.getYaw();
        float f2 = paramEventMoveFlying.
        float f3 = 0.0F;
        float f4 = 0.0F;
        switch (i) {
            case 0:
                f3 = f2;
                f4 = f1;
                break;
            case 1:
                f3 += f2;
                f4 -= f2;
                f3 += f1;
                f4 += f1;
                break;
            case 2:
                f3 = f1;
                f4 = -f2;
                break;
            case 3:
                f3 -= f2;
                f4 -= f2;
                f3 += f1;
                f4 -= f1;
                break;
            case 4:
                f3 = -f2;
                f4 = -f1;
                break;
            case 5:
                f3 -= f2;
                f4 += f2;
                f3 -= f1;
                f4 -= f1;
                break;
            case 6:
                f3 = -f1;
                f4 = f2;
                break;
            case 7:
                f3 += f2;
                f4 += f2;
                f3 -= f1;
                f4 += f1;
        }
        if ((f3 > 1.0F) || ((f3 < 0.9F) && (f3 > 0.3F)) || (f3 < -1.0F) || ((f3 > -0.9F) && (f3 < -0.3F))) {
            f3 *= 0.5F;
        }
        if ((f4 > 1.0F) || ((f4 < 0.9F) && (f4 > 0.3F)) || (f4 < -1.0F) || ((f4 > -0.9F) && (f4 < -0.3F))) {
            f4 *= 0.5F;
        }
        return new float[]{f4, f3};
    }*/
    
    private static float updateRotation3(float paramFloat1, float paramFloat2, float paramFloat3) {
        float f = MathHelper.wrapDegrees(paramFloat2 - paramFloat1);
        if (f > paramFloat3)
          f = paramFloat3; 
        if (f < -paramFloat3)
          f = -paramFloat3; 
        return paramFloat1 + f;
      }
      
      public static void faceBlock(BlockPos paramBlockPos) {
        double d1 = paramBlockPos.getX() + 0.5D - (Minecraft.getMinecraft()).player.posX;
        double d2 = paramBlockPos.getZ() + 0.5D - (Minecraft.getMinecraft()).player.posZ;
        double d3 = ((paramBlockPos.getY() + paramBlockPos.up().getY()) + 0.75D) / 2.0D - (Minecraft.getMinecraft()).player.posY + (Minecraft.getMinecraft()).player.getEyeHeight();
        double d4 = MathHelper.sqrt(d1 * d1 + d2 * d2);
        float f1 = (float)(MathHelper.atan2(d2, d1) * 57.29577951308232D) - 90.0F;
        float f2 = (float)-(MathHelper.atan2(d3, d4) * 57.29577951308232D);
        float f3 = updateRotation3((Minecraft.getMinecraft()).player.rotationPitch, f2, Float.MAX_VALUE);
        float f4 = updateRotation3((Minecraft.getMinecraft()).player.rotationYaw, f1, Float.MAX_VALUE);
        Minecraft.getMinecraft().getConnection().sendPacket((Packet)new CPacketPlayer.Rotation(f4, f3, (Minecraft.getMinecraft()).player.onGround));
      }
    public static void updateRotations(float paramFloat1, float paramFloat2) {
        mc.player.rotationYawHead = paramFloat1;
        mc.player.rotationPitchHead = paramFloat2;
        while (mc.player.rotationYawHead - mc.player.prevRotationYawHead < -180.0F) {
        	mc.player.prevRotationYawHead -= 360.0F;
        }
        while (mc.player.rotationYawHead - mc.player.prevRotationYawHead >= 180.0F) {
        	mc.player.prevRotationYawHead += 360.0F;
        }
    }

    public static void resetRotations() {
        currentRotation = null;
        moveToRotation = false;
    }

    public final float updateRotation(float paramFloat1, float paramFloat2, float paramFloat3) {
        float f = MathHelper.wrapAngleTo180_float(paramFloat2 - paramFloat1);
        if (f > paramFloat3) {
            f = paramFloat3;
        }
        if (f < -paramFloat3) {
            f = -paramFloat3;
        }
        return paramFloat1 + f;
    }

    public final float calcRot(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4) {
        float f = MathHelper.wrapAngleTo180_float(paramFloat2 - paramFloat1);
        if (Math.abs(f) > 90.0F) {
            paramFloat4 += paramFloat3;
        } else if (paramFloat4 > 20.0F) {
            paramFloat4 -= paramFloat3;
        } else {
            paramFloat4 += paramFloat3;
        }
        return MathHelper.clamp(paramFloat4, 0.0F, 180.0F);
    }

    public final float[] rotationsToEntity(Entity paramEntity) {
        double d1 = paramEntity.posX + (paramEntity.posX - paramEntity.prevPosX) * 2.0D - mc.player.posX - mc.player.motionX * 2.0D;
        double d2 = paramEntity.posY + paramEntity.getEyeHeight() - mc.player.posY - mc.player.getEyeHeight();
        double d3 = paramEntity.posZ + (paramEntity.posZ - paramEntity.prevPosZ) * 2.0D - mc.player.posZ - mc.player.motionZ * 2.0D;
        return new float[]{MathHelper.wrapAngleTo180_float((float) Math.toDegrees(Math.atan2(d3, d1)) - 90.0F), (float) -Math.toDegrees(Math.atan2(d2, Math.hypot(d1, d3)))};
    }

    public final float[] rotationsToVector(Vec3d paramVec3) {
        Vec3d localVec31 = mc.player.getPositionEyes(1.0F);
        Vec3d localVec32 = paramVec3.subtract(localVec31);
        return new float[]{(float) Math.toDegrees(Math.atan2(localVec32.zCoord, localVec32.xCoord)) - 90.0F, (float) -Math.toDegrees(Math.atan2(localVec32.yCoord, Math.hypot(localVec32.xCoord, localVec32.zCoord)))};
    }

    public final float[] rotationsToEntityWithBow(Entity paramEntity) {
        double d1 = Math.sqrt(mc.player.getDistanceToEntity(paramEntity) * mc.player.getDistanceToEntity(paramEntity)) / 1.5D;
        double d2 = paramEntity.posX + (paramEntity.posX - paramEntity.prevPosX) * d1 - mc.player.posX;
        double d3 = paramEntity.posZ + (paramEntity.posZ - paramEntity.prevPosZ) * d1 - mc.player.posZ;
        double d4 = paramEntity.posY + (paramEntity.posY - paramEntity.prevPosY) + mc.player.getDistanceToEntity(paramEntity) * mc.player.getDistanceToEntity(paramEntity) / 300.0F + paramEntity.getEyeHeight() - mc.player.posY - mc.player.getEyeHeight() - mc.player.motionY;
        return new float[]{(float) Math.toDegrees(Math.atan2(d3, d2)) - 90.0F, (float) -Math.toDegrees(Math.atan2(d4, Math.hypot(d2, d3)))};
    }

    public final float calculateRotation(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5) {
        float f = MathHelper.wrapAngleTo180_float(paramFloat2 - paramFloat1);
        if ((f < -paramFloat4) || (f > paramFloat5)) {
            return paramFloat1 + MathHelper.clamp(f, -paramFloat3, paramFloat3);
        }
        return paramFloat1;
    }

    public final void collect(float paramFloat) {
        if (paramFloat < 5.0F) {
            return;
        }
        if (YAW_SPEEDS.size() > 50) {
            YAW_SPEEDS.remove(Collections.min(YAW_SPEEDS));
            return;
        }
        YAW_SPEEDS.add(Float.valueOf(paramFloat));
    }

    public final float readSpeed() {
        if (YAW_SPEEDS.isEmpty()) {
            return 20.0F;
        }
        return ((Float) YAW_SPEEDS.get(ThreadLocalRandom.current().nextInt(0, YAW_SPEEDS.size() - 1))).floatValue();
    }

    public final float[] rotationsToPos(BlockPos paramBlockPos) {
        double d1 = paramBlockPos.getX() + 0.4D - mc.player.posX;
        double d2 = paramBlockPos.getY() + 0.5D - mc.player.posY - mc.player.getEyeHeight();
        double d3 = paramBlockPos.getZ() + 0.4D - mc.player.posZ;
        return new float[]{(float) Math.toDegrees(Math.atan2(d3, d1)) - 90.0F, (float) -Math.toDegrees(Math.atan2(d2, Math.hypot(d1, d3)))};
    }
}