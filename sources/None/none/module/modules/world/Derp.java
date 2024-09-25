package none.module.modules.world;

import org.lwjgl.input.Keyboard;

import none.Client;
import none.event.Event;
import none.event.RegisterEvent;
import none.event.events.EventPreMotionUpdate;
import none.module.Category;
import none.module.Module;
import none.module.modules.combat.Killaura;
import none.utils.MoveUtils;
import none.utils.RotationUtils;
import none.utils.Utils;
import none.valuesystem.BooleanValue;
import none.valuesystem.ModeValue;

public class Derp extends Module{

	public Derp() {
		super("Derp", "Derp", Category.WORLD, Keyboard.KEY_NONE);
	}
	
	private String[] modes = {"Smooth", "Random"};
	private ModeValue mode = new ModeValue("Derp-Mode", "Smooth", modes);
	
	private BooleanValue keepsprint = new BooleanValue("KeepSprint", false);
	
	public static String[] ModeSide = {"Client", "Server"};
	public static ModeValue SideMode = new ModeValue("Side-Mode", "Client", ModeSide);
	
	private float yaw = -360, pitch = -88;
	boolean returning;
	boolean returning2;
	public static float[] rotations = new float[2];
	
	public float getYaw() {
		return yaw;
	}
	public void setYaw(float yaw) {
		this.yaw = yaw;
	}
	public float getPitch() {
		return pitch;
	}
	public void setPitch(float pitch) {
		this.pitch = pitch;
	}
	
	@Override
	@RegisterEvent(events = {EventPreMotionUpdate.class})
	public void onEvent(Event event) {
		if (!isEnabled()) return;
		
		if (SideMode.getSelected().equalsIgnoreCase("Server")) {
			if (Client.instance.moduleManager.killaura.isEnabled()) {
				return;
			}else if (Client.instance.moduleManager.scaffold.isEnabled()) {
				return;
			}
		}
		
		if (event instanceof EventPreMotionUpdate) {
			EventPreMotionUpdate e = (EventPreMotionUpdate) event;
			
			if (e.isPre()) {
				if (SideMode.getSelected().equalsIgnoreCase("Server")) {
					if (keepsprint.getObject() && MoveUtils.isMoveKeyPressed()) {
						mc.thePlayer.setSprinting(true);
					}else {
						mc.thePlayer.setSprinting(false);
					}
				}
				
				if (mode.getSelected().equalsIgnoreCase("Smooth")) {
					if (yaw <= 180 && returning) {
			        	setYaw(getYaw() + 10);
			        }
			        
			        if (yaw >= -180 && !returning) {
			        	setYaw(getYaw() - 10);
			        }
			        
			        if (yaw < -180 && !returning) {
			        	setYaw(-180);
			        	returning = !returning;
			        }
			        
			        if (yaw > 180) {
			        	setYaw(180);
			        	returning = !returning;
			        }
			        
			        if (pitch <= 85 && returning2) {
			        	setPitch(getPitch() + 5);
			        }
			        
			        if (pitch >= -85 && !returning2) {
			        	setPitch(getPitch() - 5);
			        }
			        
			        if (pitch < -85 && !returning2) {
			        	setPitch(-85);
			        	returning2 = !returning2;
			        }
			        
			        if (pitch > 85) {
			        	setPitch(85);
			        	returning2 = !returning2;
			        }
				}else if (mode.getSelected().equalsIgnoreCase("Random")) {
					if (mc.thePlayer.ticksExisted % 2 == 0) {
						setYaw(Utils.random(-180, 180));
						setPitch(Utils.random(-90, 90));
					}
				}
				if (SideMode.getSelected().equalsIgnoreCase("Server")) {
					e.setYaw(yaw);
					e.setPitch(pitch);
				}else if (SideMode.getSelected().equalsIgnoreCase("Client")) {					
					rotations[0] = yaw;
					rotations[1] = pitch;
				}
			}
		}
	}

}
