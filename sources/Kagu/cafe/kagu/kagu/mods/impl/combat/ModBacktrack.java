/**
 * 
 */
package cafe.kagu.kagu.mods.impl.combat;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.vecmath.Vector3d;

import cafe.kagu.kagu.eventBus.EventHandler;
import cafe.kagu.kagu.eventBus.Handler;
import cafe.kagu.kagu.eventBus.impl.EventSettingUpdate;
import cafe.kagu.kagu.eventBus.impl.EventTick;
import cafe.kagu.kagu.mods.Module;
import cafe.kagu.kagu.settings.impl.DoubleSetting;
import cafe.kagu.kagu.settings.impl.IntegerSetting;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;

/**
 * @author lavaflowglow
 *
 */
public class ModBacktrack extends Module {
	
	public ModBacktrack() {
		super("Backtrack", Category.COMBAT);
		setSettings(backtrackTicks, backtrackEnableDistance);
	}
	
	private Map<EntityLivingBase, Vector3d[]> backtracks = new ConcurrentHashMap<>();
	
	private IntegerSetting backtrackTicks = new IntegerSetting("Backtrack Ticks", 4, 1, 40, 1);
	private DoubleSetting backtrackEnableDistance = new DoubleSetting("Backtrack Enable Distance", 15, 10, 100, 1);
	
	private int backtrackBlinkTickOverride = backtrackTicks.getValue();
	
	@EventHandler
	private Handler<EventTick> onTick = e -> {
		if (e.isPost())
			return;
		
		// Set info
		setInfo(backtrackTicks.getValue() + " ticks");
		
		// Update backtracks
		for (Entity entity : mc.theWorld.getLoadedEntityList()) {
			if (!(entity instanceof EntityLivingBase) || entity == mc.thePlayer)
				continue;
			
			EntityLivingBase entityLivingBase = (EntityLivingBase)entity;
			
			// Remove/ignore the entity if it's too far away
			if (mc.thePlayer.getDistanceToEntity(entityLivingBase) > backtrackEnableDistance.getValue()) {
				backtracks.remove(entityLivingBase);
				continue;
			}
			
			// Add the entity if it's not currently backtracked
			if (!backtracks.containsKey(entityLivingBase)) {
				backtracks.put(entityLivingBase, new Vector3d[backtrackTicks.getValue()]);
			}
			
			// Update backtrack positions
			Vector3d prevPos = new Vector3d(entityLivingBase.posX, entityLivingBase.posY, entityLivingBase.posZ);
			int i = 0;
			Vector3d[] positions = backtracks.get(entityLivingBase);
			for (Vector3d pos : positions) {
				positions[i] = prevPos;
				prevPos = pos;
				i++;
				
			}
			
		}
		
		// Remove entities that are not loaded in the world anymore
		for (EntityLivingBase entityLivingBase : backtracks.keySet()) {
			if (!mc.theWorld.getLoadedEntityList().contains(entityLivingBase)) {
				backtracks.remove(entityLivingBase);
			}
		}
		
	};
	
	@EventHandler
	private Handler<EventSettingUpdate> onSettingUpdate = e -> {
		if (e.getSetting() != backtrackTicks)
			return;
		backtracks.clear();
		backtrackBlinkTickOverride = backtrackTicks.getValue();
	};
	
	/**
	 * @return the backtracks
	 */
	public Map<EntityLivingBase, Vector3d[]> getBacktracks() {
		return backtracks;
	}
	
	/**
	 * @return the backtrackTicks
	 */
	public IntegerSetting getBacktrackTicks() {
		return backtrackTicks;
	}
	
	/**
	 * @return the backtrackBlinkTickOverride
	 */
	public int getBacktrackBlinkTickOverride() {
		return backtrackBlinkTickOverride;
	}
	
	/**
	 * @param backtrackBlinkTickOverride the backtrackBlinkTickOverride to set
	 */
	public void setBacktrackBlinkTickOverride(int backtrackBlinkTickOverride) {
		this.backtrackBlinkTickOverride = backtrackBlinkTickOverride;
	}
	
}
