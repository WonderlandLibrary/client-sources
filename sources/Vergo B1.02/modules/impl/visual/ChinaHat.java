package xyz.vergoclient.modules.impl.visual;

import org.lwjgl.util.glu.Cylinder;

import xyz.vergoclient.event.Event;
import xyz.vergoclient.event.impl.EventRender3D;
import xyz.vergoclient.modules.Module;
import xyz.vergoclient.modules.OnEventInterface;
import xyz.vergoclient.util.main.MiscellaneousUtils;
import xyz.vergoclient.util.main.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.Vec3;

public class ChinaHat extends Module implements OnEventInterface {

	public ChinaHat() {
		super("ChinaHat", Category.VISUAL);
	}
	
	@Override
	public void onEvent(Event e) {
		
		if (e instanceof EventRender3D && e.isPre() && mc.gameSettings.thirdPersonView != 0) {
			
			Vec3 pos = MiscellaneousUtils.getRenderEntityPos(mc.thePlayer);
			
			GlStateManager.pushMatrix();
			GlStateManager.pushAttrib();
			
			if (mc.thePlayer.isSneaking()) {
				GlStateManager.translate(0, -0.13, 0);
			}
			
			double pitchFixer = 0.2;
			float timer = Minecraft.getMinecraft().timer.renderPartialTicks;
	        double yaw = mc.thePlayer.prevRotationYawHead + (mc.thePlayer.rotationYawHead - mc.thePlayer.prevRotationYawHead) * timer;
//			double yaw = mc.thePlayer.prevRotationYawHead;
	        
	        if (RenderUtils.shouldSetCustomYaw) {
	        	yaw = RenderUtils.getCustomYaw();
	        }
	        
			GlStateManager.rotate((float) -yaw, 0, 1, 0);
			GlStateManager.rotate(90, 1, 0, 0);
			GlStateManager.translate(0, 0, -(mc.thePlayer.getEyeHeight() - pitchFixer));
			GlStateManager.rotate(RenderUtils.shouldSetCustomPitch ? RenderUtils.getCustomPitch() :mc.thePlayer.rotationPitch, 1, 0, 0);
			GlStateManager.translate(0, 0, -(0.55 + pitchFixer));
			
			GlStateManager.color(213.0f / 255, 173.0f / 255, 117.0f / 255);
			new Cylinder().draw(0, 0.65f, 0.35f, 20, 20);
			
			GlStateManager.scale(1, -1, 1);
			
			GlStateManager.color(153.0f / 255, 113.0f / 255, 57.0f / 255);
			new Cylinder().draw(0, 0.65f, 0.35f, 20, 20);
			
			GlStateManager.popAttrib();
			GlStateManager.popMatrix();
			
		}
		
	}

}
