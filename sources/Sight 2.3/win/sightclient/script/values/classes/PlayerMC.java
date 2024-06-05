package win.sightclient.script.values.classes;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.potion.Potion;
import net.minecraft.util.MathHelper;
import win.sightclient.event.Event;
import win.sightclient.script.events.EventScriptUpdate;
import win.sightclient.script.values.ClassMC;
import win.sightclient.utils.PlayerUtils;
import win.sightclient.utils.RotationUtils;
import win.sightclient.utils.minecraft.MoveUtils;

public class PlayerMC extends ClassMC {

	public double motionX;
	public double motionY;
	public double motionZ;
	
	public float rotationYaw;
	public float rotationPitch;
	
	public double posX;
	public double posY;
	public double posZ;
	
	public boolean onGround;
	public int ticksExisted;
	public float fallDistance;
	public boolean isCollidedHorizontally;
	public boolean isCollidedVertically;
	
	@Override
	public void preRun() {
		motionX = Minecraft.getMinecraft().thePlayer.motionX;
		motionY = Minecraft.getMinecraft().thePlayer.motionY;
		motionZ = Minecraft.getMinecraft().thePlayer.motionZ;
		
		rotationYaw = Minecraft.getMinecraft().thePlayer.rotationYaw;
		rotationPitch = Minecraft.getMinecraft().thePlayer.rotationPitch;
		
		posX = Minecraft.getMinecraft().thePlayer.posX;
		posY = Minecraft.getMinecraft().thePlayer.posY;
		posZ = Minecraft.getMinecraft().thePlayer.posZ;
		
		onGround = Minecraft.getMinecraft().thePlayer.onGround;
		ticksExisted = Minecraft.getMinecraft().thePlayer.ticksExisted;
		fallDistance = Minecraft.getMinecraft().thePlayer.fallDistance;
		
		isCollidedHorizontally = Minecraft.getMinecraft().thePlayer.isCollidedHorizontally;
		isCollidedVertically = Minecraft.getMinecraft().thePlayer.isCollidedVertically;
	}
	
	@Override
	public void postRun() {
		Minecraft.getMinecraft().thePlayer.motionX = motionX;
		Minecraft.getMinecraft().thePlayer.motionY = motionY;
		Minecraft.getMinecraft().thePlayer.motionZ = motionZ;
		
		Minecraft.getMinecraft().thePlayer.rotationYaw = rotationYaw;
		Minecraft.getMinecraft().thePlayer.rotationPitch = rotationPitch;
		
		Minecraft.getMinecraft().thePlayer.onGround = onGround;
		Minecraft.getMinecraft().thePlayer.ticksExisted = ticksExisted;
		Minecraft.getMinecraft().thePlayer.fallDistance = fallDistance;
		
		Minecraft.getMinecraft().thePlayer.isCollidedHorizontally = isCollidedHorizontally;
		Minecraft.getMinecraft().thePlayer.isCollidedVertically = isCollidedVertically;
	}
	
    public double getDistanceToEntity(EntityMC entityIn) {
    	return this.getDistanceToEntity(entityIn.getRealEntity());
    }
    
    public double getDistanceToEntity(Entity entityIn) {
        return Minecraft.getMinecraft().thePlayer.getDistanceToEntity(entityIn);
    }
	
	public void attack(EntityMC entity) {
		Minecraft.getMinecraft().playerController.attackEntity(Minecraft.getMinecraft().thePlayer, entity.getRealEntity());
	}
	
	public void faceEntity(EntityMC entity, EventScriptUpdate esu) {
		this.faceEntity(entity.getRealEntity(), esu);
	}
	
	public void faceEntity(Entity entity, EventScriptUpdate esu) {
		float[] rots = RotationUtils.getRotations(entity);
		esu.setYaw(rots[0]);
		esu.setPitch(rots[1]);
	}
	
	public void jump() {
		Minecraft.getMinecraft().thePlayer.jump();
		this.motionY = Minecraft.getMinecraft().thePlayer.motionY;
	}
	
	public void jump(double yVal) {
        this.motionY = yVal;

        if (Minecraft.getMinecraft().thePlayer.isPotionActive(Potion.jump))
        {
            this.motionY += (double)((float)(Minecraft.getMinecraft().thePlayer.getActivePotionEffect(Potion.jump).getAmplifier() + 1) * 0.1F);
        }

        if (Minecraft.getMinecraft().thePlayer.isSprinting())
        {
            float f = this.rotationYaw * 0.017453292F;
            this.motionX -= (double)(MathHelper.sin(f) * 0.2F);
            this.motionZ += (double)(MathHelper.cos(f) * 0.2F);
        }

        Minecraft.getMinecraft().thePlayer.isAirBorne = true;
	}
	
	public void setMotion(double speed) {
		MoveUtils.setMotion(null, speed);
		motionX = Minecraft.getMinecraft().thePlayer.motionX;
		motionZ = Minecraft.getMinecraft().thePlayer.motionZ;
	}
	
	public boolean isMoving() {
		return Minecraft.getMinecraft().gameSettings.keyBindForward.isKeyDown()
				|| Minecraft.getMinecraft().gameSettings.keyBindBack.isKeyDown()
				|| Minecraft.getMinecraft().gameSettings.keyBindLeft.isKeyDown()
				|| Minecraft.getMinecraft().gameSettings.keyBindRight.isKeyDown();
	}
	
	public void teleport(double amount) {
	    double rad = Math.toRadians(this.rotationYaw);
	    double mulX = (-Math.sin(rad)) * amount;
	    double mulZ = (Math.cos(rad)) * amount;
	    Minecraft.getMinecraft().thePlayer.setPosition(this.posX += mulX, this.posY, this.posZ += mulZ);
	}
	
	public double getBaseMoveSpeed() {
		return MoveUtils.getBaseSpeed();
	}
	
	public void sendChatMessage(String message) {
		Minecraft.getMinecraft().thePlayer.sendChatMessage(message);
	}
	
	public void damage() {
		PlayerUtils.damagePlayer(0);
	}
	
	public void swingItem() {
		Minecraft.getMinecraft().thePlayer.swingItem();
	}
	
	public void setRotationYaw(float yaw) {
		Minecraft.getMinecraft().thePlayer.rotationYaw = yaw;
		this.rotationYaw = yaw;
	}
	
	public void setRotationPitch(float pitch) {
		Minecraft.getMinecraft().thePlayer.rotationPitch = pitch;
		this.rotationPitch = pitch;
	}
	
	public EntityPlayerSP getRealEntity() {
		return Minecraft.getMinecraft().thePlayer;
	}
	
	public EntityLivingMC getEntity() {
		return new EntityLivingMC(Minecraft.getMinecraft().thePlayer);
	}
	
	public void setPosition(double x, double y, double z) {
		Minecraft.getMinecraft().thePlayer.setPosition(x, y, z);
	}
	
	public void setPositionAndUpdate(double x, double y, double z) {
		Minecraft.getMinecraft().thePlayer.setPositionAndUpdate(x, y, z);
	}
	
	public void setBlocking() {
		Minecraft.getMinecraft().thePlayer.setItemInUse(Minecraft.getMinecraft().thePlayer.getCurrentEquippedItem(), 71626);
	}
}
