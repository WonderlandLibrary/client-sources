package win.sightclient.script.values.classes;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import win.sightclient.script.values.ClassMC;

public class EntityMC extends ClassMC {

	protected Entity e;
	
	public double posX;
	public double posY;
	public double posZ;
	
	public EntityMC(Entity e) {
		this.e = e;
		this.posX = e.posX;
		this.posY = e.posY;
		this.posZ = e.posY;
	}
	
	@Override
	public void preRun() {
		this.posX = e.posX;
		this.posY = e.posY;
		this.posZ = e.posZ;
	}
	
	public boolean isPlayed() {
		return this.getRealEntity() instanceof EntityPlayer;
	}
	
	public boolean canAttack() {
		return this.getRealEntity() instanceof EntityLivingBase;
	}
	
	public boolean isEntityAlive() {
		return this.getRealEntity().isEntityAlive();
	}
	
	public boolean isMainPlayer() {
		return this.getRealEntity() == Minecraft.getMinecraft().thePlayer;
	}
	
	public Entity getRealEntity() {
		return e;
	}
}
