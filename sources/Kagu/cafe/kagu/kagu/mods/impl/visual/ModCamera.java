/**
 * 
 */
package cafe.kagu.kagu.mods.impl.visual;

import cafe.kagu.kagu.eventBus.EventHandler;
import cafe.kagu.kagu.eventBus.Handler;
import cafe.kagu.kagu.eventBus.impl.EventCheatRenderTick;
import cafe.kagu.kagu.mods.Module;
import cafe.kagu.kagu.settings.impl.BooleanSetting;
import cafe.kagu.kagu.settings.impl.DoubleSetting;

/**
 * @author DistastefulBannock
 *
 */
public class ModCamera extends Module {

	public ModCamera() {
		super("Camera", Category.VISUAL);
		setSettings(cameraClip, animations, animationSpeed, distance);
	}
	
	private BooleanSetting cameraClip = new BooleanSetting("Camera Clip", true);
	private BooleanSetting animations = new BooleanSetting("Animations", false);
	private DoubleSetting animationSpeed = new DoubleSetting("Animation Speed", 0.1, 0.05, 0.5, 0.05).setDependency(animations::isEnabled);
	private DoubleSetting distance = new DoubleSetting("Distance", 4, 1, 16, 0.25);
	
	private float wantedYaw = 0;
	private float wantedPitch = 0;
	private float wantedFunny = 0;
	private double wantedDistance = 0;
	private float currentYaw = 0;
	private float currentPitch = 0;
	private float currentFunny = 0;
	private double currentDistance = 0;

	@EventHandler
	private Handler<EventCheatRenderTick> onEventCheatRenderTick = e -> {
		if (e.isPost())
			return;
		
		if (mc.gameSettings.thirdPersonView == 0) {
			wantedYaw = 0;
			wantedPitch = 0;
			wantedDistance = 0;
			wantedFunny = 0;
			currentYaw = 0;
			currentPitch = 0;
			currentDistance = 0;
			currentFunny = 0;
			return;
		}
		
		double animationSpeed = this.animationSpeed.getValue();
		
		if (currentYaw < wantedYaw) {
			currentYaw += (wantedYaw - currentYaw) * animationSpeed;
		}else {
			currentYaw -= (currentYaw - wantedYaw) * animationSpeed;
		}
		
		if (currentPitch < wantedPitch) {
			currentPitch += (wantedPitch - currentPitch) * animationSpeed;
		}else {
			currentPitch -= (currentPitch - wantedPitch) * animationSpeed;
		}
		
		if (currentDistance < wantedDistance) {
			currentDistance += (wantedDistance - currentDistance) * animationSpeed;
		}else {
			currentDistance -= (currentDistance - wantedDistance) * animationSpeed;
		}
		
		if (currentFunny < wantedFunny) {
			currentFunny += (wantedFunny - currentFunny) * animationSpeed;
		}else {
			currentFunny -= (currentFunny - wantedFunny) * animationSpeed;
		}
		
	};
	
	/**
	 * @return the cameraClip
	 */
	public BooleanSetting getCameraClip() {
		return cameraClip;
	}
	
	/**
	 * @return the animations
	 */
	public BooleanSetting getAnimations() {
		return animations;
	}
	
	/**
	 * @return the distance
	 */
	public DoubleSetting getDistance() {
		return distance;
	}
	
	/**
	 * @return the wantedYaw
	 */
	public float getWantedYaw() {
		return wantedYaw;
	}

	/**
	 * @param wantedYaw the wantedYaw to set
	 */
	public void setWantedYaw(float wantedYaw) {
		this.wantedYaw = wantedYaw;
	}

	/**
	 * @return the wantedPitch
	 */
	public float getWantedPitch() {
		return wantedPitch;
	}

	/**
	 * @param wantedPitch the wantedPitch to set
	 */
	public void setWantedPitch(float wantedPitch) {
		this.wantedPitch = wantedPitch;
	}
	
	/**
	 * @return the wantedFunny
	 */
	public float getWantedFunny() {
		return wantedFunny;
	}
	
	/**
	 * @param wantedFunny the wantedFunny to set
	 */
	public void setWantedFunny(float wantedFunny) {
		this.wantedFunny = wantedFunny;
	}
	
	/**
	 * @return the wantedDistance
	 */
	public double getWantedDistance() {
		return wantedDistance;
	}

	/**
	 * @param wantedDistance the wantedDistance to set
	 */
	public void setWantedDistance(double wantedDistance) {
		this.wantedDistance = wantedDistance;
	}

	/**
	 * @return the currentYaw
	 */
	public float getCurrentYaw() {
		return currentYaw;
	}
	
	/**
	 * @return the currentPitch
	 */
	public float getCurrentPitch() {
		return currentPitch;
	}
	
	/**
	 * @return the currentFunny
	 */
	public float getCurrentFunny() {
		return currentFunny;
	}
	
	/**
	 * @return the currentDistance
	 */
	public double getCurrentDistance() {
		return currentDistance;
	}
	
}
