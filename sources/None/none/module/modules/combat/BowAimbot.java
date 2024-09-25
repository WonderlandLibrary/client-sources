package none.module.modules.combat;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemEgg;
import net.minecraft.item.ItemSnowball;
import none.Client;
import none.event.Event;
import none.event.RegisterEvent;
import none.event.events.EventPreMotionUpdate;
import none.friend.FriendManager;
import none.module.Category;
import none.module.Module;
import none.module.modules.world.Murder;
import none.utils.RotationUtils;
import none.utils.Targeter;
import none.utils.TeamUtils;
import none.valuesystem.BooleanValue;
import none.valuesystem.ModeValue;

public class BowAimbot extends Module{
	
	public BowAimbot() {
		super("BowAimbot", "BowAimbot", Category.COMBAT, Keyboard.KEY_NONE);
	}
	
	private String[] aimmode = {"Client-Side", "Server-Side"};
	public ModeValue aimmodes = new ModeValue("AimMode", "Client-Side", aimmode);
	
	private BooleanValue teams = new BooleanValue("Team", true);
	
    public static EntityLivingBase target;
    public float[] rotations = new float[2]; 
	
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
			EventPreMotionUpdate e = (EventPreMotionUpdate) event;
			
			if (e.isPre()) {
				if(shouldAim()){
		              target = getTarg();
		               if (target != null) {
	                    	rotations = RotationUtils.getBowAngles(target);
	                    	mc.thePlayer.setSprinting(false);
	                        e.setSprinting(false);
	                        if (aimmodes.getSelected().equalsIgnoreCase("Client-Side")) {
	                        	e.setYaw(rotations[0]);
	                        	e.setPitch(rotations[1]);
	                        }else if (aimmodes.getSelected().equalsIgnoreCase("Server-Side")) {
	                        	e.setYaw(rotations[0]);
	                        	e.setPitch(rotations[1]);
	                        	mc.thePlayer.rotationYaw = rotations[0];
	                        	mc.thePlayer.rotationPitch = rotations[1];
	                        }
	                    }
	                }
	            }
		}
	}
	
	public static boolean shouldAim(){
		Minecraft mc = Minecraft.getMinecraft();
    	if(mc.thePlayer.inventory.getCurrentItem() == null || !(mc.thePlayer.inventory.getCurrentItem().getItem() instanceof ItemBow || mc.thePlayer.inventory.getCurrentItem().getItem() instanceof ItemSnowball || mc.thePlayer.inventory.getCurrentItem().getItem() instanceof ItemEgg))
    		return false;
    	if(Client.instance.moduleManager.fastBow.isEnabled() && mc.gameSettings.keyBindUseItem.pressed)
    		return true;
    	if(mc.thePlayer.inventory.getCurrentItem().getItem() instanceof ItemSnowball || mc.thePlayer.inventory.getCurrentItem().getItem() instanceof ItemEgg)
            return true;
    	if(mc.thePlayer.isUsingItem() && mc.thePlayer.inventory.getCurrentItem().getItem() instanceof ItemBow)
            return true;
    	return false;
    }
    private EntityLivingBase getTarg() {
        List<EntityLivingBase> loaded = new ArrayList();
        for (Object o : mc.theWorld.getLoadedEntityList()) {
            if (o instanceof EntityLivingBase) {
                EntityLivingBase ent = (EntityLivingBase) o;
                if (Targeter.isTarget(ent)) {
	                if (mc.thePlayer.canEntityBeSeen(ent) && mc.thePlayer.getDistanceToEntity(ent) < 65) {
	//                    if (ent == Killaura.vip) {
	//                        return ent;
	//                    }
	                	if (ent instanceof EntityPlayer) {
	                		if (Client.instance.moduleManager.murder.isEnabled()) {
	                			if (((EntityPlayer)ent).isMurderer)
	                			loaded.add(ent);
	                		}else {
			                	if (teams.getObject()) {
			                		EntityPlayer player = (EntityPlayer) ent;
			            			if (!teams.getObject() || (teams.getObject() && !TeamUtils.isTeam(mc.thePlayer, (EntityPlayer)ent))) {
			            				loaded.add(ent);
			            			}
			                	}else {
			                		if ((Client.instance.moduleManager.noFriends.isEnabled() || (!Client.instance.moduleManager.noFriends.isEnabled() && !FriendManager.isFriend(ent.getName())))) {
			                			loaded.add(ent);
			                		}
			                	}
	                		}
	                	}
	                }
                }
            }
        }
        if (loaded.isEmpty()) {
            return null;
        }
        loaded.sort((o1, o2) -> {
            float[] rot1 = RotationUtils.getRotations(o1);
            float[] rot2 = RotationUtils.getRotations(o2);
            return (int) ((RotationUtils.getDistanceBetweenAngles(mc.thePlayer.rotationYaw, rot1[0])
                    + RotationUtils.getDistanceBetweenAngles(mc.thePlayer.rotationPitch, rot1[1]))
                    - (RotationUtils.getDistanceBetweenAngles(mc.thePlayer.rotationYaw, rot2[0])
                    + RotationUtils.getDistanceBetweenAngles(mc.thePlayer.rotationPitch, rot2[1])));
        });
        EntityLivingBase target = loaded.get(0);
        return target;
    }
}
