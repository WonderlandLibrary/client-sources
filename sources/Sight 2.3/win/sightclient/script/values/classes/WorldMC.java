package win.sightclient.script.values.classes;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import win.sightclient.script.values.ClassMC;

public class WorldMC extends ClassMC {

	public List<EntityLivingMC> getLivingEntities() {
		List<EntityLivingMC> list = new ArrayList<EntityLivingMC>();
		
		for (int i = 0; i < Minecraft.getMinecraft().theWorld.loadedEntityList.size(); i++) {
			Entity e = Minecraft.getMinecraft().theWorld.loadedEntityList.get(i);
			
			if (e instanceof EntityLivingBase) {
				EntityLivingMC elb = new EntityLivingMC((EntityLivingBase) e);
				list.add(elb);
			}
		}
		return list;
	}
	
	public List<EntityMC> getEntities() {
		List<EntityMC> list = new ArrayList<EntityMC>();
		
		for (int i = 0; i < Minecraft.getMinecraft().theWorld.loadedEntityList.size(); i++) {
			Entity e = Minecraft.getMinecraft().theWorld.loadedEntityList.get(i);
			list.add(new EntityMC(e));
		}
		
		return list;
	}
	
	public void setWorldTime(long time) {
		Minecraft.getMinecraft().theWorld.setWorldTime(time);
	}
}
