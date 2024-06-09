package lunadevs.luna.module.render;

import lunadevs.luna.category.Category;
import lunadevs.luna.module.Module;
import lunadevs.luna.utils.RenderUtils;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;

public class MobESP extends Module {

	public MobESP() {
		super("MobESP", 0, Category.RENDER, false);
	}

	@Override
	public void onRender() {
		if (!this.isEnabled)
			return;

		for (Object theObject : mc.theWorld.loadedEntityList) {
			if (!(theObject instanceof EntityLivingBase))
				continue;

			EntityLivingBase entity = (EntityLivingBase) theObject;

			if(entity instanceof EntityMob) {
				mob(entity);
				continue;
			}
			
			if(entity instanceof EntityAnimal) {
				animal(entity);
				continue;
			}
		}

	}


	public void animal(EntityLivingBase entity) {
		float red = 0F;
		float green = 0.5F;
		float blue = 0F;

		double xPos = (entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * mc.timer.renderPartialTicks)
				- mc.getRenderManager().renderPosX;
		double yPos = (entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * mc.timer.renderPartialTicks)
				- mc.getRenderManager().renderPosY;
		double zPos = (entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * mc.timer.renderPartialTicks)
				- mc.getRenderManager().renderPosZ;

		render(red, green, blue, xPos, yPos, zPos, entity.width, entity.height);
	}

    public void mob(EntityLivingBase entity){
            float red = 1F;
            float green = 0F;
            float blue = 0F;

            double xPos = (entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * mc.timer.renderPartialTicks) - mc.getRenderManager().renderPosX;
            double yPos = (entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * mc.timer.renderPartialTicks) - mc.getRenderManager().renderPosY;
            double zPos = (entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * mc.timer.renderPartialTicks) - mc.getRenderManager().renderPosZ;
           
            render(red, green, blue, xPos, yPos, zPos, entity.width, entity.height);
    }
    
	public void render(float red, float green, float blue, double x, double y, double z, float width, float height) {
		RenderUtils.drawEntityESP(x, y, z, width, height, red, green, blue, 0.45F, 0F, 0F, 0F, 1F, 1.5F);
	}

	public String getValue() {
		return null;
	}

}
