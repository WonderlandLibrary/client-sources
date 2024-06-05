package xyz.vergoclient.modules.impl.movement;

import java.util.concurrent.CopyOnWriteArrayList;

import xyz.vergoclient.Vergo;
import xyz.vergoclient.event.Event;
import xyz.vergoclient.event.impl.EventTick;
import xyz.vergoclient.event.impl.EventUpdate;
import xyz.vergoclient.modules.Module;
import xyz.vergoclient.modules.OnEventInterface;
import xyz.vergoclient.settings.BooleanSetting;
import xyz.vergoclient.settings.ModeSetting;
import xyz.vergoclient.settings.NumberSetting;
import xyz.vergoclient.util.main.MovementUtils;
import xyz.vergoclient.util.main.TimerUtil;
import net.minecraft.init.Blocks;
import net.minecraft.network.Packet;
import net.minecraft.util.BlockPos;

public class AntiVoid extends Module implements OnEventInterface {

	public AntiVoid() {
		super("AntiVoid", Category.MOVEMENT);
	}
	
	public void onEnable() {
		antivoid = false;
		packets.clear();
	}
	
	public void onDisable() {
		packets.clear();
	}
	
	public ModeSetting mode = new ModeSetting("Mode", "Hypixel", "Hypixel");
	public NumberSetting fallDistance = new NumberSetting("Catch Distance", 6.5, 3, 30, 0.5);
	public BooleanSetting autoEnableScaffold = new BooleanSetting("Scaffold-Save", false);
	
	@Override
	public void loadSettings() {
		addSettings(mode, fallDistance, autoEnableScaffold);
	}
	
	private static transient CopyOnWriteArrayList<Packet> packets = new CopyOnWriteArrayList<Packet>();
	private static transient boolean antivoid = false;
	private static transient TimerUtil noSpam = new TimerUtil();
	
	public void onEvent(Event e) {
		
		if (e instanceof EventTick && e.isPre()) {
			setInfo(mode.getMode());
		}
		
		if (mc.thePlayer.capabilities.isFlying || mc.thePlayer.capabilities.allowFlying)
			return;
		if (e instanceof EventUpdate && e.isPre() && mc.thePlayer.fallDistance > fallDistance.getValueAsDouble() && mode.is("Hypixel") && Vergo.config.modFly.isDisabled()) {
			if (isOverVoid() && !antivoid) {
				((EventUpdate)e).x = mc.thePlayer.motionX;
				((EventUpdate)e).y = -999.0D;
				((EventUpdate)e).z = mc.thePlayer.motionZ;
				((EventUpdate)e).onGround = true;
				mc.thePlayer.prevRotationYaw = Float.MIN_VALUE;
				antivoid = true;
				mc.thePlayer.onGround = false;
				noSpam.reset();
				if (autoEnableScaffold.isEnabled() && Vergo.config.modScaffold.isDisabled())
					Vergo.config.modScaffold.toggle();
			}
		}
		else if (e instanceof EventUpdate && e.isPre() && mode.is("Hypixel")) {
			if (antivoid && MovementUtils.isOnGround(0.0001) && noSpam.hasTimeElapsed(500, false)) {
				antivoid = false;
			}
		}
		
	}
	
	private boolean isOverVoid() {
		
		boolean isOverVoid = true;
		BlockPos block = new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1, mc.thePlayer.posZ);
		
		for (double i = mc.thePlayer.posY + 1; i > 0; i -= 0.5) {
			
			if (isOverVoid) {
				
				try {
					if (mc.theWorld.getBlockState(block).getBlock() != Blocks.air) {
						
						isOverVoid = false;
						break;
						
					}
				} catch (Exception e) {
					
				}
				
			}
			
			block = block.add(0, -1, 0);
			
		}
		
		for (double i = 0; i < 10; i += 0.1) {
			if (MovementUtils.isOnGround(i) && isOverVoid) {
				isOverVoid = false;
				break;
			}
		}
		
		return isOverVoid;
	}
	
}
