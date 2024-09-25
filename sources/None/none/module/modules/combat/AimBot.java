package none.module.modules.combat;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSword;
import none.Client;
import none.event.Event;
import none.event.RegisterEvent;
import none.event.events.EventPreMotionUpdate;
import none.friend.FriendManager;
import none.module.Category;
import none.module.Module;
import none.utils.ChatUtil;
import none.utils.RotationUtils;
import none.utils.Targeter;
import none.utils.TeamUtils;
import none.utils.Utils;
import none.valuesystem.BooleanValue;
import none.valuesystem.NumberValue;

public class AimBot extends Module{

	public AimBot() {
		super("Aimbot", "Aimbot", Category.COMBAT, Keyboard.KEY_NONE);
	}
	
	private NumberValue<Double> range = new NumberValue<>("Range", 4.2, 3.5, 6.0);
	private NumberValue<Integer> speedyaw = new NumberValue<>("Speed-Yaw", 40, 1, 100);
	private NumberValue<Integer> speedpitch = new NumberValue<>("Speed-pitch", 40, 1, 100);
	private NumberValue<Integer> fovyaw = new NumberValue<>("FOV-Yaw", 180, 1, 180);
	private NumberValue<Integer> fovpitch = new NumberValue<>("FOV-pitch", 90, 1, 90);
	
	private BooleanValue randomYaw = new BooleanValue("RandomYaw", false);
	private BooleanValue randomPitch = new BooleanValue("RandomPitch", false);
	
	private BooleanValue checksword = new BooleanValue("Only-Sword", true);
	private BooleanValue teams = new BooleanValue("Teams", false);
	private BooleanValue onClick = new BooleanValue("onClick", false);
	
	private EntityLivingBase target;
	
	@Override
	protected void onEnable() {
		super.onEnable();
		target = null;
	}
	
	@Override
	protected void onDisable() {
		super.onDisable();
		target = null;
	}
	
	@Override
	@RegisterEvent(events = {EventPreMotionUpdate.class})
	public void onEvent(Event event) {
		if (!isEnabled()) return;
		
		if (event instanceof EventPreMotionUpdate) {
			EventPreMotionUpdate em = (EventPreMotionUpdate)event;
			if (em.isPre()) {
				target = getBestEntity();
			}else if (em.isPost() && mc.currentScreen == null) {
				if (mc.thePlayer.getHeldItem() == null || mc.thePlayer.getHeldItem().getItem() == null) {
                    return;
                }
				final Item heldItem = mc.thePlayer.getHeldItem().getItem();
                if (checksword.getObject() && heldItem != null) {
                    if (!(heldItem instanceof ItemSword)) {
                        return;
                    }
                }
				if (target != null && ((onClick.getObject() && Mouse.isButtonDown(0)) || !onClick.getObject())) {
					stepAngle();
				}
			}
		}
	}
	
	private int randomNumber() {
        return -1 + (int) (Math.random() * ((1 - (-1)) + 1));
    }
	
	private void stepAngle() {
        float yawFactor = speedyaw.getInteger() / 10F;
        float pitchFactor = speedpitch.getInteger() / 10F;
        double xz = randomYaw.getObject() ? 0.03 : 0;
        double y = randomPitch.getObject() ? 0.03 : 0;
        float targetYaw = RotationUtils.getYawChange(mc.thePlayer.rotationYaw, target.posX + (randomNumber() * xz), target.posZ + (randomNumber() * xz));

        if (targetYaw > 0 && targetYaw > yawFactor) {
            mc.thePlayer.rotationYaw += yawFactor;
        } else if (targetYaw < 0 && targetYaw < -yawFactor) {
            mc.thePlayer.rotationYaw -= yawFactor;
        } else {
            mc.thePlayer.rotationYaw += targetYaw;
        }

        float targetPitch = RotationUtils.getPitchChange(mc.thePlayer.rotationPitch, target, target.posY + (randomNumber() * y));

        if (targetPitch > 0 && targetPitch > pitchFactor) {
            mc.thePlayer.rotationPitch += pitchFactor;
        } else if (targetPitch < 0 && targetPitch < -pitchFactor) {
            mc.thePlayer.rotationPitch -= pitchFactor;
        } else {
            mc.thePlayer.rotationPitch += targetPitch;
        }

    }
	
	private EntityLivingBase getBestEntity() {
        List<EntityLivingBase> loaded = new CopyOnWriteArrayList<>();
        for (Object o : mc.theWorld.getLoadedEntityList()) {
            if (o instanceof EntityLivingBase) {
                EntityLivingBase ent = (EntityLivingBase) o;
                if (isValid(ent)
                        && fovCheck(ent)) {
//                    if (ent == Killaura.vip) {
//                        return ent;
//                    }
                    loaded.add(ent);
                }
            }
        }
        if (loaded.isEmpty()) {
            return null;
        }
        try {
            loaded.sort((o1, o2) -> {
                float[] rot1 = RotationUtils.getRotations(o1);
                float[] rot2 = RotationUtils.getRotations(o2);
                return (int) ((RotationUtils.getDistanceBetweenAngles(mc.thePlayer.rotationYaw, rot1[0])
                        + RotationUtils.getDistanceBetweenAngles(mc.thePlayer.rotationPitch, rot1[1]))
                        - (RotationUtils.getDistanceBetweenAngles(mc.thePlayer.rotationYaw, rot2[0])
                        + RotationUtils.getDistanceBetweenAngles(mc.thePlayer.rotationPitch, rot2[1])));
            });
        } catch (Exception e) {
            ChatUtil.printChat("Exception with TM: " + e.getMessage());
        }
        return loaded.get(0);
    }

    private boolean fovCheck(EntityLivingBase ent) {
        float[] rotations = RotationUtils.getRotations(ent);
        float dist = mc.thePlayer.getDistanceToEntity(ent);
        if (dist == 0) {
            dist = 1;
        }
        float yawDist = RotationUtils.getDistanceBetweenAngles(mc.thePlayer.rotationYaw, rotations[0]);
        float pitchDist = RotationUtils.getDistanceBetweenAngles(mc.thePlayer.rotationPitch, rotations[1]);
        float fovYaw = (fovyaw.getInteger() * 3) / dist;
        float fovPitch = (fovpitch.getInteger() * 3) / dist;
        return yawDist < fovYaw && pitchDist < fovPitch;
    }
    
    private boolean isValid(Entity entity) {
    	double range = this.range.getDouble();
    	if (Targeter.isTarget(entity) && !entity.isDead) {
    		boolean team = false;
    		if (entity instanceof EntityPlayer) {
    			EntityPlayer player = (EntityPlayer) entity;
    			if (Client.instance.moduleManager.auraTeams.isEnabled()) {
    				if (AuraTeams.player.contains(player)) {
    					return false;
    				}
    			}else {
	    			if (!teams.getObject() || (teams.getObject() && !TeamUtils.isTeam(mc.thePlayer, (EntityPlayer)entity))) {
	    				
	    			}else {
	    				return false;
	    			}
    			}
    			
    			if (!Client.instance.moduleManager.noFriends.isEnabled() && FriendManager.isFriend(player.getName())) {
    				return false;
    			}
    		}
    		return mc.thePlayer.getDistanceToEntity(entity) <= range && Utils.canEntityBeSeen((EntityLivingBase)entity);
    	}
//    	if (Targeter.isTarget(entity) && !entity.isDead) {
//    		return entity.getDistance(-35, 72, 38) <= range && (Utils.canEntityBeSeen((EntityLivingBase)entity) || walls.getObject());
//    	}
    	return false;
    }
}
