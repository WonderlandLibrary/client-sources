/**
 * 
 */
package cafe.kagu.kagu.ui.widgets;

import java.awt.Color;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import cafe.kagu.kagu.utils.DrawUtils3D;
import cafe.kagu.kagu.utils.MathUtils;
import cafe.kagu.kagu.utils.RotationUtils;
import cafe.kagu.kagu.utils.UiUtils;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;

/**
 * @author DistastefulBannock
 * A triangle color picker for mc, doesn't support alpha values
 */
public class WidgetColorPicker {
	
	public static final int DRAW_BACKGROUND = 0b0001;
	public static final int CLOSE_ON_MISS_CLICK = 0b0010;
	public static final int DISPLAY_CONFIRM_BUTTON = 0b0100;
	public static final int DISPLAY_RESET_BUTTON = 0b1000;
	
	private int flags;
	private Color color, defaultColor;
	private Runnable onClose, onColorUpdate;
	private boolean leftMouseDown = false, draggingHueWheel = false, draggingTriangle = false;
	
	/**
	 * @param flags The flags you want for the color picker
	 * @param color The current color to display
	 * @param defaultColor The default color
	 * @param onClose A runnable that gets ran when the color picker is closed
	 * @param onColorUpdate A runnable that gets ran when the color gets updated
	 */
	public WidgetColorPicker(int flags, int color, int defaultColor, Runnable onClose, Runnable onColorUpdate) {
		this.flags = flags;
		this.color = new Color(color);
		this.defaultColor = new Color(defaultColor);
		this.onClose = onClose;
		this.onColorUpdate = onColorUpdate;
	}
	
	/**
	 * Call this on screen draw to hook it into a gui
	 * @param x The x pos to render the color picker at
	 * @param y The y pos to render the color picker at
	 * @param width The width of the color picker
	 * @param height The height of the color picker
	 * @param mouseX The x position of the mouse
	 * @param mouseY The y position of the mouse
	 */
	public void draw(int x, int y, int width, int height, int mouseX, int mouseY) {
		
		// Never release bug fix
		if (!Mouse.isInsideWindow())
			leftMouseDown = false;
		
		// Draw background
		if ((flags & DRAW_BACKGROUND) != 0) {
			Gui.drawRect(x, y, x + width, y - height, 0xff1e1e1e);
		}
		
		// Draw the hue wheel
		int hueWheelWidth = width / 11;
		int originX = x + width / 2;
		int originY = y - height / 2;
		
		Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        
		GlStateManager.pushMatrix();
		GlStateManager.pushAttrib();
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        
        // The color wheel itself
        worldrenderer.begin(GL11.GL_QUAD_STRIP, DefaultVertexFormats.POSITION_COLOR);
		for (int rotate = 0; rotate <= 360; rotate++) {
			Color color = Color.getHSBColor(rotate / 360f, 1, 1);
			int yaw = rotate - 90;
			double xFar = originX + Math.cos(Math.toRadians(yaw)) * (width / 2);
			double yFar = originY + Math.sin(Math.toRadians(yaw)) * (height / 2);
			double xClose = originX + Math.cos(Math.toRadians(yaw)) * (width / 2 - hueWheelWidth);
			double yClose = originY + Math.sin(Math.toRadians(yaw)) * (height / 2 - hueWheelWidth);
			worldrenderer.pos(xFar, yFar, 0).color(color.getRed() / 255d, color.getGreen() / 255d, color.getBlue() / 255d, 1d).endVertex();
			worldrenderer.pos(xClose, yClose, 0).color(color.getRed() / 255d, color.getGreen() / 255d, color.getBlue() / 255d, 1d).endVertex();
		}
		tessellator.draw();
		
		// The hue pointer
		{
			GL11.glEnable(GL11.GL_LINE_SMOOTH);
			int yaw = (int) (Color.RGBtoHSB(this.color.getRed(), this.color.getGreen(), this.color.getBlue(), null)[0] * 360) - 90;
			double xFar = originX + Math.cos(Math.toRadians(yaw)) * (width / 2);
			double yFar = originY + Math.sin(Math.toRadians(yaw)) * (height / 2);
			double xClose = originX + Math.cos(Math.toRadians(yaw)) * (width / 2 - hueWheelWidth);
			double yClose = originY + Math.sin(Math.toRadians(yaw)) * (height / 2 - hueWheelWidth);
			GL11.glLineWidth(0.5f);
			worldrenderer.begin(GL11.GL_LINES, DefaultVertexFormats.POSITION_COLOR);
			worldrenderer.pos(xFar, yFar, 0).color(0, 0, 0, 1d).endVertex();
			worldrenderer.pos(xClose, yClose, 0).color(0, 0, 0, 1d).endVertex();
			tessellator.draw();
		}
		
		// Brightness and saturation triangle
		GL11.glDisable(GL11.GL_CULL_FACE);
        worldrenderer.begin(GL11.GL_TRIANGLES, DefaultVertexFormats.POSITION_COLOR);
        {
        	int yaw = (int) (Color.RGBtoHSB(this.color.getRed(), this.color.getGreen(), this.color.getBlue(), null)[0] * 360) - 90;
    		for (int rotate = 0; rotate < 360; rotate += 120) {
    			Color color = this.color;
    			switch (rotate) {
    				case 0:{
    					color = this.color;
    				}break;
    				case 120:{
    					color = Color.WHITE;
    				}break;
    				case 240:{
    					color = Color.BLACK;
    				}break;
    			}
    			int yaw1 = yaw + rotate;
    			double xClose = originX + Math.cos(Math.toRadians(yaw1)) * (width / 2 - hueWheelWidth);
    			double yClose = originY + Math.sin(Math.toRadians(yaw1)) * (height / 2 - hueWheelWidth);
    			worldrenderer.pos(xClose, yClose, 0).color(color.getRed() / 255d, color.getGreen() / 255d, color.getBlue() / 255d, 1d).endVertex();
    		}
        }
		tessellator.draw();
		
        GlStateManager.shadeModel(GL11.GL_FLAT);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
        GlStateManager.popAttrib();
        GlStateManager.popMatrix();
        
        // Click logic
        if (!leftMouseDown) {
        	draggingHueWheel = false;
        	draggingTriangle = false;
        }
        
        if (!draggingTriangle && !draggingHueWheel && leftMouseDown) {
        	double mouseDist = MathUtils.getDistance2D(mouseX, mouseY, originX, originY);
        	if (mouseDist > width / 2 - hueWheelWidth && mouseDist < width / 2) {
        		draggingHueWheel = true;
        	}
        }
        
        austist:
        if (!draggingTriangle && !draggingTriangle && leftMouseDown) {
    		// Check to make sure the mouse click is inside of the triangle
        	double mouseDist = MathUtils.getDistance2D(mouseX, mouseY, originX, originY);
        	if (mouseDist > width / 2) {
        		break austist;
        	}
    		int yaw = (int) (Color.RGBtoHSB(this.color.getRed(), this.color.getGreen(), this.color.getBlue(), null)[0] * 360) - 90;
    		
    		// Check to see if the cursor is inside of the triangle
			double x1 = originX + Math.cos(Math.toRadians(yaw)) * (width / 2 - hueWheelWidth);
			double y1 = originY + Math.sin(Math.toRadians(yaw)) * (height / 2 - hueWheelWidth);
			double x2 = originX + Math.cos(Math.toRadians(yaw + 120)) * (width / 2 - hueWheelWidth);
			double y2 = originY + Math.sin(Math.toRadians(yaw + 120)) * (height / 2 - hueWheelWidth);
			double x3 = originX + Math.cos(Math.toRadians(yaw + 240)) * (width / 2 - hueWheelWidth);
			double y3 = originY + Math.sin(Math.toRadians(yaw + 240)) * (height / 2 - hueWheelWidth);
    		boolean insideTriangle = MathUtils.isInsideTriangle(x1, y1, x2, y2, x3, y3, mouseX, mouseY);
    		if (insideTriangle)
    			draggingTriangle = true;
        }
        
        // Handle dragging hue wheel
        if (draggingHueWheel) {
        	
        	// If they are currently dragging the hue wheel then calculate the angle the mouse is from the center and set the new hue
        	float angle = RotationUtils.getRotations(new double[] {mouseX, 0, mouseY}, new double[] {originX, 0, originY})[0];
        	float[] currentColorHsb = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);
        	Color oldColor = this.color;
        	color = Color.getHSBColor(angle / 360f, currentColorHsb[1], currentColorHsb[2]);
        	
        	// Run the update callback if the hue changed
        	if (!oldColor.equals(color) && onColorUpdate != null) {
        		onColorUpdate.run();
        	}
        	
        }
        
        // Handle dragging triangle
        else if (draggingTriangle) {
        	System.out.println("test " + System.nanoTime());
        }
        
	}
	
	/**
	 * Call this on mouse click to hook it into a gui
	 * @param button The mouse button clicked
	 */
	public void mouseClick(int button) {
		if (button == 0)
			leftMouseDown = true;
	}
	
	/**
	 * Call this on mouse release to hook it into a gui
	 * @param mouseX The x position of the mouse
	 * @param mouseY The y position of the mouse
	 */
	public void mouseRelease(int button) {
		if (button == 0)
			leftMouseDown = false;
	}
	
	/**
	 * @param color the color to set
	 */
	public void setColor(int color) {
		this.color = new Color(color);
	}
	
	/**
	 * @return the color
	 */
	public int getColor() {
		return color.getRGB();
	}
	
	/**
	 * @return the draggingHueWheel
	 */
	public boolean isDraggingHueWheel() {
		return draggingHueWheel;
	}
	
}
