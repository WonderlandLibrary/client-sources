package us.dev.direkt.module.internal.render;

import org.lwjgl.opengl.GL11;

import us.dev.direkt.Direkt;
import us.dev.direkt.Wrapper;
import us.dev.direkt.module.ModCategory;
import us.dev.direkt.module.annotations.ModData;
import us.dev.direkt.module.ToggleableModule;
import us.dev.direkt.module.annotations.traits.TransientStatus;

@ModData(label = "Cave Finder", category = ModCategory.RENDER)
@TransientStatus
public class CaveFinder extends ToggleableModule {
	
	@Override
	public void onEnable(){
		if(!Direkt.getInstance().getModuleManager().getModule(NoRender.class).isRunning())
		Wrapper.getMinecraft().renderGlobal.loadRenderers();
	}
	
	@Override
	public void onDisable(){
		if(!Direkt.getInstance().getModuleManager().getModule(NoRender.class).isRunning())
		Wrapper.getMinecraft().renderGlobal.loadRenderers();
	}
	
	public void startRenderCaves(){
		GL11.glCullFace(GL11.GL_FRONT);
	}
	
	public void endRenderCaves(){
		GL11.glCullFace(GL11.GL_BACK);
	}
	
}
