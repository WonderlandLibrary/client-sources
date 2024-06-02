/**
 * 
 */
package cafe.kagu.kagu.utils;

import org.lwjgl.opengl.EXTFramebufferObject;
import org.lwjgl.opengl.EXTPackedDepthStencil;
import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.shader.Framebuffer;

/**
 * @author lavaflowglow
 *
 */
public class StencilUtil {
	
	/**
	 * Called when the client starts
	 */
	public static void start() {
		Framebuffer framebuffer = Minecraft.getMinecraft().getFramebuffer();
		framebuffer.bindFramebuffer(false);
		GL11.glClear(GL11.GL_STENCIL_BUFFER_BIT);
		GL11.glEnable(GL11.GL_STENCIL_TEST);
		if (framebuffer.depthBuffer > -1) {
			setupVbo(framebuffer);
			framebuffer.depthBuffer = -1;
		}
	}
	
	/**
	 * Sets up the frame buffer object
	 * @param framebuffer The framebuffer to setup
	 */
	private static void setupVbo(Framebuffer framebuffer) {
        EXTFramebufferObject.glDeleteRenderbuffersEXT(framebuffer.depthBuffer);
        final int stencilDepthBufferID = EXTFramebufferObject.glGenRenderbuffersEXT();
        EXTFramebufferObject.glBindRenderbufferEXT(EXTFramebufferObject.GL_RENDERBUFFER_EXT, stencilDepthBufferID);
        EXTFramebufferObject.glRenderbufferStorageEXT(EXTFramebufferObject.GL_RENDERBUFFER_EXT, EXTPackedDepthStencil.GL_DEPTH_STENCIL_EXT, Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight);
        EXTFramebufferObject.glFramebufferRenderbufferEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT, EXTFramebufferObject.GL_STENCIL_ATTACHMENT_EXT, EXTFramebufferObject.GL_RENDERBUFFER_EXT, stencilDepthBufferID);
        EXTFramebufferObject.glFramebufferRenderbufferEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT, EXTFramebufferObject.GL_DEPTH_ATTACHMENT_EXT, EXTFramebufferObject.GL_RENDERBUFFER_EXT, stencilDepthBufferID);
	}
	
	/**
	 * Enables stencil test
	 */
	public static void enableStencilTest() {
		GL11.glEnable(GL11.GL_STENCIL_TEST);
	}
	
	/**
	 * Disables stencil test
	 */
	public static void disableStencilTest() {
		enableWrite();
		clearStencil();
		disableWrite();
		GL11.glDisable(GL11.GL_STENCIL_TEST);
		GL11.glStencilFunc(GL11.GL_ALWAYS, 1, 0xff); // Set test to always pass, that way it will always write
	}
	
	/**
	 * Clears the stencil, write must be enabled for this to happen correctly
	 */
	public static void clearStencil() {
		GL11.glClear(GL11.GL_STENCIL_BUFFER_BIT); // Clear the stencil bit
		GL11.glClearStencil(0); // Clear the stencil
	}
	
	/**
	 * Enables writing to the stencil
	 */
	public static void enableWrite() {
		enableWrite(0xff);
	}
	
	/**
	 * Enables writing to the stencil
	 * @param write What to write to the stencil
	 */
	public static void enableWrite(int write) {
		Framebuffer framebuffer = Minecraft.getMinecraft().getFramebuffer();
		if (framebuffer.depthBuffer > -1) {
			setupVbo(framebuffer);
			framebuffer.depthBuffer = -1;
		}
		GL11.glStencilFunc(GL11.GL_ALWAYS, 1, 0xff); // Set test to always pass, that way it will always write
		setWrite(write); // Enable writing
	}
	
	/**
	 * Disables writing to the stencil
	 */
	public static void disableWrite() {
		setWrite(0x00); // Disable writing
	}
	
	/**
	 * Sets what is written to the stencil
	 */
	public static void setWrite(int write) {
		GL11.glStencilMask(write); // Sets writing
	}
	
	/**
	 * @param compare The GL_ALWAYS stuff, I don't know how to explain it
	 * @param value The value to compare the bit on the stencil to
	 */
	public static void glStencilFunc(int compare, int value) {
		GL11.glStencilFunc(compare, value, 0xff);
	}
	
	/**
	 * Sets the outcome of the stencil test
	 * @param fail What to do if both the stencil test and depth fails
	 * @param stencilPassDepthFail What to do if the stencil test passes but the depth test fails
	 * @param bothPass What to do if both the stencil test and depth pass
	 */
	public static void setTestOutcome(int fail, int stencilPassDepthFail, int bothPass) {
		GL11.glStencilOp(fail, stencilPassDepthFail, bothPass);
	}
	
}
