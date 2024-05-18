package us.dev.direkt.module.internal.render.esp.modes;

import org.lwjgl.opengl.EXTFramebufferObject;
import org.lwjgl.opengl.EXTPackedDepthStencil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.shader.Framebuffer;
import us.dev.direkt.event.internal.events.game.render.EventRender3D;
import us.dev.direkt.module.internal.render.esp.ESPMode;
import us.dev.dvent.Link;
import us.dev.dvent.Listener;

/**
 * @author Foundry
 */
public class BFCEMode extends ESPMode {
	public BFCEMode() {
        super("BFCE");
    }

	@Listener
    protected Link<EventRender3D> onRender3D = new Link<>(event -> {
	      Framebuffer fbo = Minecraft.getMinecraft().getFramebuffer();
	      if(fbo != null && fbo.depthBuffer > -1) {
	          EXTFramebufferObject.glDeleteRenderbuffersEXT(fbo.depthBuffer);
	  	      
	  	      int stencil_depth_buffer_ID = EXTFramebufferObject.glGenRenderbuffersEXT();
	  	      EXTFramebufferObject.glBindRenderbufferEXT(EXTFramebufferObject.GL_RENDERBUFFER_EXT, stencil_depth_buffer_ID);
	  	      
	  	      EXTFramebufferObject.glRenderbufferStorageEXT(EXTFramebufferObject.GL_RENDERBUFFER_EXT, EXTPackedDepthStencil.GL_DEPTH_STENCIL_EXT, Minecraft.displayWidth, Minecraft.displayHeight);
	  	      
	  	      EXTFramebufferObject.glFramebufferRenderbufferEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT, EXTFramebufferObject.GL_STENCIL_ATTACHMENT_EXT, EXTFramebufferObject.GL_RENDERBUFFER_EXT, stencil_depth_buffer_ID);
	  	      
	  	      EXTFramebufferObject.glFramebufferRenderbufferEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT, EXTFramebufferObject.GL_DEPTH_ATTACHMENT_EXT, EXTFramebufferObject.GL_RENDERBUFFER_EXT, stencil_depth_buffer_ID);
	          
	          fbo.depthBuffer = -1;
	      }
    });
	
}
