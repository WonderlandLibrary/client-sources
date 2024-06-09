package lunadevs.luna.module.render;

import org.lwjgl.input.Keyboard;

import lunadevs.luna.category.Category;
import lunadevs.luna.main.Luna;
import lunadevs.luna.module.Module;
import lunadevs.luna.utils.RenderUtils;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

public class ESP extends Module{

	public ESP() {
		super("ESP", 0, Category.RENDER, false);
	}

//	@Override
//	public void onRender() {
//		if (!this.isEnabled) return;
//		for(Object theObject : mc.theWorld.loadedEntityList) {
//			if(!(theObject instanceof EntityLivingBase))
//				continue;
//			
//			EntityLivingBase entity = (EntityLivingBase) theObject;
//			
//			if(entity instanceof EntityPlayer) {
//				if(entity != mc.thePlayer && !entity.isInvisible())
//					player(entity);
//				continue;
//			}
//			
//		}
//		super.onRender();
//	}
//	
//	public void player(EntityLivingBase entity) {
//        float red = 46;
//        float green = 123;
//        float blue = 247;
//		
//		double xPos = (entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * mc.timer.renderPartialTicks) - mc.getRenderManager().renderPosX;
//		double yPos = (entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * mc.timer.renderPartialTicks) - mc.getRenderManager().renderPosY;
//		double zPos = (entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * mc.timer.renderPartialTicks) - mc.getRenderManager().renderPosZ;
//		
//		render(red, green, blue, xPos, yPos, zPos, entity.width - 0.2F, entity.height+0.3F);
//	}
//	
//	
//	public void render(float red, float green, float blue, double x, double y, double z, float width, float height) {
//        RenderUtils.drawEntityESP(x, y, z, width, height, red, green, blue, 0.15F, 0F, 0F, 0F, 1F, 1F);
//    }
	
	@Override
	public void onEnable(){
		super.onEnable();
	}
	
	@Override
	public void onDisable(){
		super.onDisable();
	}
	
	@Override
	public String getValue() {
		return null;
	}

}
