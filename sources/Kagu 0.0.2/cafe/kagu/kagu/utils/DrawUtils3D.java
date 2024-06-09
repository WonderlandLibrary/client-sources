/**
 * 
 */
package cafe.kagu.kagu.utils;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;
import javax.vecmath.Vector4d;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

/**
 * @author lavaflowglow
 *
 */
public class DrawUtils3D {
	
	/**
	 * Draws a 3d box
	 * @param corner1X The x pos of the first corner
	 * @param corner1Y The y pos of the first corner
	 * @param corner1Z The z pos of the first corner
	 * @param corner2X The x pos of the second corner
	 * @param corner2Y The y pos of the second corner
	 * @param corner2Z The z pos of the second corner
	 * @param partialTicks The partial ticks
	 * @param color The color of the box
	 */
	public static void drawColored3DWorldBox(double corner1X, double corner1Y, double corner1Z, double corner2X, double corner2Y, double corner2Z, int color) {
		Tessellator tessellator = Tessellator.getInstance();
		WorldRenderer worldRenderer = tessellator.getWorldRenderer();
		Vector4d color4d = UiUtils.getVectorFromColor(color);
		
		// Fix the position of the render
		Vector3d renderOffsets = get3dWorldOffsets();
		corner1X -= renderOffsets.getX();
		corner1Y -= renderOffsets.getY();
		corner1Z -= renderOffsets.getZ();
		corner2X -= renderOffsets.getX();
		corner2Y -= renderOffsets.getY();
		corner2Z -= renderOffsets.getZ();
		
		// Begin
		worldRenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
		
		// Back side
		worldRenderer.pos(corner1X, corner1Y, corner1Z).color(color4d.getX(), color4d.getY(), color4d.getZ(), color4d.getW()).endVertex();
		worldRenderer.pos(corner2X, corner1Y, corner1Z).color(color4d.getX(), color4d.getY(), color4d.getZ(), color4d.getW()).endVertex();
		worldRenderer.pos(corner2X, corner2Y, corner1Z).color(color4d.getX(), color4d.getY(), color4d.getZ(), color4d.getW()).endVertex();
		worldRenderer.pos(corner1X, corner2Y, corner1Z).color(color4d.getX(), color4d.getY(), color4d.getZ(), color4d.getW()).endVertex();
		
		// Front side
		worldRenderer.pos(corner1X, corner1Y, corner2Z).color(color4d.getX(), color4d.getY(), color4d.getZ(), color4d.getW()).endVertex();
		worldRenderer.pos(corner2X, corner1Y, corner2Z).color(color4d.getX(), color4d.getY(), color4d.getZ(), color4d.getW()).endVertex();
		worldRenderer.pos(corner2X, corner2Y, corner2Z).color(color4d.getX(), color4d.getY(), color4d.getZ(), color4d.getW()).endVertex();
		worldRenderer.pos(corner1X, corner2Y, corner2Z).color(color4d.getX(), color4d.getY(), color4d.getZ(), color4d.getW()).endVertex();
		
		// Top
		worldRenderer.pos(corner1X, corner2Y, corner1Z).color(color4d.getX(), color4d.getY(), color4d.getZ(), color4d.getW()).endVertex();
		worldRenderer.pos(corner2X, corner2Y, corner1Z).color(color4d.getX(), color4d.getY(), color4d.getZ(), color4d.getW()).endVertex();
		worldRenderer.pos(corner2X, corner2Y, corner2Z).color(color4d.getX(), color4d.getY(), color4d.getZ(), color4d.getW()).endVertex();
		worldRenderer.pos(corner1X, corner2Y, corner2Z).color(color4d.getX(), color4d.getY(), color4d.getZ(), color4d.getW()).endVertex();
		
		// Bottom
		worldRenderer.pos(corner1X, corner1Y, corner1Z).color(color4d.getX(), color4d.getY(), color4d.getZ(), color4d.getW()).endVertex();
		worldRenderer.pos(corner2X, corner1Y, corner1Z).color(color4d.getX(), color4d.getY(), color4d.getZ(), color4d.getW()).endVertex();
		worldRenderer.pos(corner2X, corner1Y, corner2Z).color(color4d.getX(), color4d.getY(), color4d.getZ(), color4d.getW()).endVertex();
		worldRenderer.pos(corner1X, corner1Y, corner2Z).color(color4d.getX(), color4d.getY(), color4d.getZ(), color4d.getW()).endVertex();
		
		// Left side
		worldRenderer.pos(corner1X, corner1Y, corner1Z).color(color4d.getX(), color4d.getY(), color4d.getZ(), color4d.getW()).endVertex();
		worldRenderer.pos(corner1X, corner1Y, corner2Z).color(color4d.getX(), color4d.getY(), color4d.getZ(), color4d.getW()).endVertex();
		worldRenderer.pos(corner1X, corner2Y, corner2Z).color(color4d.getX(), color4d.getY(), color4d.getZ(), color4d.getW()).endVertex();
		worldRenderer.pos(corner1X, corner2Y, corner1Z).color(color4d.getX(), color4d.getY(), color4d.getZ(), color4d.getW()).endVertex();
		
		// Right side
		worldRenderer.pos(corner2X, corner1Y, corner1Z).color(color4d.getX(), color4d.getY(), color4d.getZ(), color4d.getW()).endVertex();
		worldRenderer.pos(corner2X, corner1Y, corner2Z).color(color4d.getX(), color4d.getY(), color4d.getZ(), color4d.getW()).endVertex();
		worldRenderer.pos(corner2X, corner2Y, corner2Z).color(color4d.getX(), color4d.getY(), color4d.getZ(), color4d.getW()).endVertex();
		worldRenderer.pos(corner2X, corner2Y, corner1Z).color(color4d.getX(), color4d.getY(), color4d.getZ(), color4d.getW()).endVertex();
		
		// Draw
		tessellator.draw();
		
	}
	
	/**
	 * Gets the render offsets for the 3d world
	 * @return The render offsets
	 */
	public static Vector3d get3dWorldOffsets() {
		double posX = Minecraft.getMinecraft().getRenderManager().getRenderPosX();
		double posY = Minecraft.getMinecraft().getRenderManager().getRenderPosY();
		double posZ = Minecraft.getMinecraft().getRenderManager().getRenderPosZ();
		
		return new Vector3d(posX, posY, posZ);
	}
	
	/**
	 * Gets the render offsets for the player
	 * @return The render offsets
	 */
	public static Vector3d get3dPlayerOffsets() {
		double posX = Minecraft.getMinecraft().getRenderViewEntity().posX;
		double posY = Minecraft.getMinecraft().getRenderViewEntity().posY;
		double posZ = Minecraft.getMinecraft().getRenderViewEntity().posZ;
		return new Vector3d(posX, posY, posZ);
	}
	
	/**
	 * Gets the render position for an entity
	 * @param entity The entity to get the position for
	 * @return The render position of the entity
	 */
    public static Vector3d get3dEntityOffsets(EntityLivingBase entity) {
        float partialTicks = Minecraft.getMinecraft().getTimer().getRenderPartialTicks();
        double x = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * partialTicks;
        double y = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * partialTicks;
        double z = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * partialTicks;
        return new Vector3d(x, y, z);
    }
	
    private static final FloatBuffer windowPosition = BufferUtils.createFloatBuffer(4);
    private static final IntBuffer viewport = GLAllocation.createDirectIntBuffer(16);
    private static final FloatBuffer modelMatrix = GLAllocation.createDirectFloatBuffer(16);
    private static final FloatBuffer projectionMatrix = GLAllocation.createDirectFloatBuffer(16);
    
    /**
     * Takes world coordinates and returns the position on scren where they render 
     * Only works during the 3d render event. Not 100% sure why, my guess is that minecraft sets the projection matrix before the 3d render event
     * @param x The x pos of the 3d location
     * @param y The y pos of the 3d location
     * @param z The z pos of the 3d location
     * @param offsets The offsets for the render
     * @return The location where the 3d location would render on screen
     */
    public static Vector3f project2D(float x, float y, float z, Vector3d offsets) {
    	return project2D(x, y, z, offsets, true);
    }
    
    /**
     * Takes world coordinates and returns the position on scren where they render 
     * Only works during the 3d render event. Not 100% sure why, my guess is that minecraft sets the projection matrix before the 3d render event
     * @param x The x pos of the 3d location
     * @param y The y pos of the 3d location
     * @param z The z pos of the 3d location
     * @param offsets The offsets for the render
     * @param accountForScaledResolution set to true if you want to account for the scaled resolution in the gui screen, seto to false if you just want the raw point on the display
     * @return The location where the 3d location would render on screen
     */
    public static Vector3f project2D(float x, float y, float z, Vector3d offsets, boolean accountForScaledResolution) {
    	
    	// Offset the coords
    	x -= offsets.getX();
    	y -= offsets.getY();
    	z -= offsets.getZ();
    	
    	// Used for gui scaling because minecraft's code sucks and has a fucking gui scale setting for some reason
		ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
		int scaleFactor = scaledResolution.getScaleFactor();
		if (!accountForScaledResolution)
			scaleFactor = 1;
    	
        GL11.glGetFloat(GL11.GL_MODELVIEW_MATRIX, modelMatrix); // Fills the model matrix buffer
        GL11.glGetFloat(GL11.GL_PROJECTION_MATRIX, projectionMatrix); // Fills the projection matrix buffer
        GL11.glGetInteger(GL11.GL_VIEWPORT, viewport); // Fills the viewport buffer
        
        // Use glu to do math for us, we can then do more math to get world2screen coords
        if (GLU.gluProject(x, y, z, modelMatrix, projectionMatrix, viewport, windowPosition)) {
            return new Vector3f(windowPosition.get(0) / scaleFactor,
                    (Minecraft.getMinecraft().displayHeight - windowPosition.get(1)) / scaleFactor, windowPosition.get(2));
        }

        return null;
    }
    
}
