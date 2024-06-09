package lunadevs.luna.module.render;

import lunadevs.luna.category.Category;
import lunadevs.luna.friend.FriendManager;
import lunadevs.luna.module.Module;
import lunadevs.luna.utils.RenderUtils;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

import java.awt.*;

public class Tracers extends Module{

	public Tracers() {
		super("Tracers", 0, Category.RENDER, false);
	}

    @Override
    public void onEnable() {
        if(mc.gameSettings.viewBobbing=true){
        	mc.gameSettings.viewBobbing=false;
        	
            super.onEnable();
        }
    }

    @Override
    public void onDisable() {
        if(mc.gameSettings.viewBobbing=false){
            mc.gameSettings.viewBobbing=true;
            super.onDisable();
        }
    }

    @Override
	public void onRender() {
		if (!this.isEnabled) return;
		
			
		for(Object theObject : mc.theWorld.loadedEntityList) {
			if(!(theObject instanceof EntityLivingBase))
				continue;

			EntityLivingBase entity = (EntityLivingBase) theObject;

			if(entity instanceof EntityPlayer) {
				if(entity != mc.thePlayer && !entity.isInvisible())
					player(entity);
				continue;
			}
		}
		super.onRender();
	}

	public void player(EntityLivingBase entity) {

        Color tracercolor = Color.WHITE;
        int entitycolordistance = (int)mc.thePlayer.getDistanceSqToEntity(entity) / 85;
        if (entitycolordistance >= 200) {
            entitycolordistance = 199;
        }

        if ((!entity.isInvisible()) &&
                ((entity instanceof EntityPlayer))) {
            int green = entitycolordistance * 10;
            if (green >= 255) {
                green = 255;
            }
            tracercolor = new Color(255 - green, 0 + green, 0, 255);
            if (FriendManager.isFriend(entity.getName())) {
                tracercolor = new Color(0, 0, 255, 255);
            }

        }
		double xPos = (entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * mc.timer.renderPartialTicks) - mc.getRenderManager().renderPosX;
		double yPos = (entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * mc.timer.renderPartialTicks) - mc.getRenderManager().renderPosY;
		double zPos = (entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * mc.timer.renderPartialTicks) - mc.getRenderManager().renderPosZ;

		if(tracercolor!=Color.WHITE) {
            render(tracercolor, xPos, yPos, zPos);
        }
	}


	public void render(Color color, double x, double y, double z) {
		RenderUtils.drawTracerLine(x, y, z, color, 0.45F, 1F);
	}

	@Override
	public String getValue() {
		return null;
	}

}