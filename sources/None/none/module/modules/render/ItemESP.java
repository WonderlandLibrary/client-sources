package none.module.modules.render;

import java.awt.Color;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import none.event.Event;
import none.event.RegisterEvent;
import none.event.events.Event2D;
import none.event.events.Event3D;
import none.module.Category;
import none.module.Module;
import none.utils.RenderingUtil;
import none.utils.Utils;
import none.utils.render.Colors;

public class ItemESP extends Module{

	public ItemESP() {
		super("ItemESP", "ItemESP", Category.RENDER, Keyboard.KEY_NONE);
	}

	@Override
	@RegisterEvent(events = {Event3D.class, Event2D.class})
	public void onEvent(Event event) {
		if (!isEnabled()) return;
		
		if (event instanceof Event3D) {
			for (Entity entity : mc.theWorld.loadedEntityList) {
				if (entity instanceof EntityItem) {
					EntityItem item = (EntityItem) entity;;
					drawESP(item, Color.GREEN.getRed(), Color.GREEN.getGreen(), Color.GREEN.getBlue(), 100);
				}
			}
		}
		
		if (event instanceof Event2D) {
			
		}
	}
	
	public void drawESP(EntityItem entity, int red, int green, int blue, int alpha) {
		double x = entity.lastTickPosX
				+ (entity.posX - entity.lastTickPosX) * Minecraft.getMinecraft().timer.renderPartialTicks;

		double y = entity.lastTickPosY
				+ (entity.posY - entity.lastTickPosY) * Minecraft.getMinecraft().timer.renderPartialTicks + entity.getEyeHeight() * 1.2;

		double z = entity.lastTickPosZ
				+ (entity.posZ - entity.lastTickPosZ) * Minecraft.getMinecraft().timer.renderPartialTicks;
		double width = Math.abs(entity.boundingBox.maxX - entity.boundingBox.minX) + 0.2;
		double height = Math.abs(entity.boundingBox.maxY - entity.boundingBox.minY) + 0.2;
		Vec3 vec = new Vec3(x - width/2, y, z - width/2);
        Vec3 vec2 = new Vec3(x + width/2, y + height, z + width/2);
        final float rot = Utils.getNeededRotationsForEntity(entity)[0];
        RenderManager renderManager = Minecraft.getMinecraft().getRenderManager();
        RenderingUtil.enableGL2D();
        RenderingUtil.pre3D();
        Minecraft.getMinecraft().entityRenderer.setupCameraTransform(Minecraft.getMinecraft().timer.renderPartialTicks, 2);
        RenderingUtil.glColor(Colors.getColor(red, green, blue, alpha));
        RenderingUtil.drawOutlinedBoundingBox(new AxisAlignedBB(
        		vec.xCoord - renderManager.renderPosX, vec.yCoord - renderManager.renderPosY, vec.zCoord - renderManager.renderPosZ,
        		vec2.xCoord - renderManager.renderPosX, vec2.yCoord - renderManager.renderPosY, vec2.zCoord - renderManager.renderPosZ));
        GL11.glColor4f(0.0f, 0.0f, 0.0f, 1.0f);
        RenderingUtil.post3D();
        RenderingUtil.disableGL2D();
    }
}
