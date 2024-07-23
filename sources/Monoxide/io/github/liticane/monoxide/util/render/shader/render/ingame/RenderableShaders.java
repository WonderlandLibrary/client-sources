package io.github.liticane.monoxide.util.render.shader.render.ingame;

import io.github.liticane.monoxide.util.render.shader.access.ShaderAccess;
import io.github.liticane.monoxide.util.render.shader.render.Type;
import io.github.liticane.monoxide.module.impl.hud.PostProcessingModule;
import io.github.liticane.monoxide.util.render.shader.data.ShaderRenderType;

import java.util.Arrays;

public interface RenderableShaders {

    static void render(boolean bloom, boolean blur, Runnable... runnables) {
    	if(bloom && PostProcessingModule.getInstance().isEnabled() && PostProcessingModule.getInstance().bloom.getValue()) {
			ShaderAccess.bloomShader.doRender(ShaderRenderType.OVERLAY, Type.QUADS, Arrays.asList(runnables));
    	}	
    	if(blur && PostProcessingModule.getInstance().isEnabled() && PostProcessingModule.getInstance().blur.getValue()) {
        	ShaderAccess.blurShader.doRender(ShaderRenderType.OVERLAY, Type.QUADS, Arrays.asList(runnables));
    	}
    }
    
    static void render(Runnable... runnables) {
    	RenderableShaders.render(true, true, runnables);
    }
    
    static void renderAndRun(boolean bloom, boolean blur, Runnable... runnables) {
    	RenderableShaders.render(bloom, blur, runnables);
        Arrays.asList(runnables).forEach(run -> run.run());
    }
    
    static void renderAndRun(Runnable... runnables) {
    	RenderableShaders.render(true, true, runnables);
        Arrays.asList(runnables).forEach(run -> run.run());
    }

}
