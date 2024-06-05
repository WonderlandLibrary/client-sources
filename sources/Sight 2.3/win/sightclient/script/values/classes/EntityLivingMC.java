package win.sightclient.script.values.classes;

import net.minecraft.entity.EntityLivingBase;

public class EntityLivingMC extends EntityMC {

	private EntityLivingBase elb;
	
	public EntityLivingMC(EntityLivingBase e) {
		super(e);
		this.elb = e;
	}

	public float getHealth() {
		return elb.getHealth();
	}
	
	public float getMaxHealth() {
		return elb.getMaxHealth();
	}
}
