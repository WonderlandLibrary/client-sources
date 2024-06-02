/**
 * 
 */
package cafe.kagu.kagu.eventBus.impl;

import cafe.kagu.kagu.eventBus.Event;

/**
 * @author lavaflowglow
 *
 */
public class EventPlayerUpdate extends Event {

	/**
	 * @param eventPosition The position of the event
	 * @param isSprinting   True if the player is sprinting
	 * @param isSneaking    True if the player is sneaking
	 * @param onGround      True if the player is on the ground
	 * @param posX          The x position of the player
	 * @param posY          The y position of the player
	 * @param posZ          The z position of the player
	 * @param rotationYaw   The yaw rotation of the player
	 * @param rotationPitch The pitch rotation of the player
	 */
	public EventPlayerUpdate(EventPosition eventPosition, boolean isSprinting, boolean isSneaking, boolean onGround,
			double posX, double posY, double posZ, double motionX, double motionY, double motionZ, float rotationYaw,
			float rotationPitch) {
		super(eventPosition);
		this.isSprinting = isSprinting;
		this.isSneaking = isSneaking;
		this.onGround = onGround;
		this.posX = posX;
		this.posY = posY;
		this.posZ = posZ;
		this.motionX = motionX;
		this.motionY = motionY;
		this.motionZ = motionZ;
		this.rotationYaw = rotationYaw;
		this.rotationPitch = rotationPitch;
	}

	private boolean onGround, isSprinting, isSneaking;
	private double posX, posY, posZ, motionX, motionY, motionZ;
	private float rotationYaw, rotationPitch;

	/**
	 * @return the onGround
	 */
	public boolean isOnGround() {
		return onGround;
	}

	/**
	 * @param onGround the onGround to set
	 */
	public void setOnGround(boolean onGround) {
		this.onGround = onGround;
	}

	/**
	 * @return the isSprinting
	 */
	public boolean isSprinting() {
		return isSprinting;
	}

	/**
	 * @param isSprinting the isSprinting to set
	 */
	public void setSprinting(boolean isSprinting) {
		this.isSprinting = isSprinting;
	}

	/**
	 * @return the isSneaking
	 */
	public boolean isSneaking() {
		return isSneaking;
	}

	/**
	 * @param isSneaking the isSneaking to set
	 */
	public void setSneaking(boolean isSneaking) {
		this.isSneaking = isSneaking;
	}

	/**
	 * @return the posX
	 */
	public double getPosX() {
		return posX;
	}

	/**
	 * @param posX the posX to set
	 */
	public void setPosX(double posX) {
		this.posX = posX;
	}

	/**
	 * @return the posY
	 */
	public double getPosY() {
		return posY;
	}

	/**
	 * @param posY the posY to set
	 */
	public void setPosY(double posY) {
		this.posY = posY;
	}

	/**
	 * @return the posZ
	 */
	public double getPosZ() {
		return posZ;
	}

	/**
	 * @param posZ the posZ to set
	 */
	public void setPosZ(double posZ) {
		this.posZ = posZ;
	}

	/**
	 * @return the motionX
	 */
	public double getMotionX() {
		return motionX;
	}

	/**
	 * @param motionX the motionX to set
	 */
	public void setMotionX(double motionX) {
		this.motionX = motionX;
	}

	/**
	 * @return the motionY
	 */
	public double getMotionY() {
		return motionY;
	}

	/**
	 * @param motionY the motionY to set
	 */
	public void setMotionY(double motionY) {
		this.motionY = motionY;
	}

	/**
	 * @return the motionZ
	 */
	public double getMotionZ() {
		return motionZ;
	}

	/**
	 * @param motionZ the motionZ to set
	 */
	public void setMotionZ(double motionZ) {
		this.motionZ = motionZ;
	}

	/**
	 * @return the rotationYaw
	 */
	public float getRotationYaw() {
		return rotationYaw;
	}

	/**
	 * @param rotationYaw the rotationYaw to set
	 */
	public void setRotationYaw(float rotationYaw) {
		this.rotationYaw = rotationYaw;
	}

	/**
	 * @return the rotationPitch
	 */
	public float getRotationPitch() {
		return rotationPitch;
	}

	/**
	 * @param rotationPitch the rotationPitch to set
	 */
	public void setRotationPitch(float rotationPitch) {
		this.rotationPitch = rotationPitch;
	}

}
