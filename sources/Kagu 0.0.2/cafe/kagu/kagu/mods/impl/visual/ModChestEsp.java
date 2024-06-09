/**
 * 
 */
package cafe.kagu.kagu.mods.impl.visual;

import java.awt.Color;

import javax.vecmath.Vector4d;

import org.lwjgl.opengl.GL11;

import cafe.kagu.kagu.eventBus.EventHandler;
import cafe.kagu.kagu.eventBus.Handler;
import cafe.kagu.kagu.eventBus.impl.EventRender3D;
import cafe.kagu.kagu.mods.Module;
import cafe.kagu.kagu.settings.impl.DoubleSetting;
import cafe.kagu.kagu.settings.impl.IntegerSetting;
import cafe.kagu.kagu.utils.DrawUtils3D;
import cafe.kagu.kagu.utils.StencilUtil;
import cafe.kagu.kagu.utils.UiUtils;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityEnderChest;

/**
 * @author lavaflowglow
 *
 */
public class ModChestEsp extends Module {
	
	public ModChestEsp() {
		super("ChestESP", Category.VISUAL);
		setSettings(visibleRed, visibleGreen, visibleBlue, visibleAlpha, 
					hiddenRed, hiddenGreen, hiddenBlue, hiddenAlpha);
	}
	
	// Visible chest esp colors
	private IntegerSetting visibleRed = new IntegerSetting("Visible R", 165, 0, 255, 1);
	private IntegerSetting visibleGreen = new IntegerSetting("Visible G", 224, 0, 255, 1);
	private IntegerSetting visibleBlue = new IntegerSetting("Visible B", 254, 0, 255, 1);
	private IntegerSetting visibleAlpha = new IntegerSetting("Visible A", 100, 0, 255, 1);
	
	// Hidden chest esp colors
	private IntegerSetting hiddenRed = new IntegerSetting("Hidden R", 213, 0, 255, 1);
	private IntegerSetting hiddenGreen = new IntegerSetting("Hidden G", 140, 0, 255, 1);
	private IntegerSetting hiddenBlue = new IntegerSetting("Hidden B", 255, 0, 255, 1);
	private IntegerSetting hiddenAlpha = new IntegerSetting("Hidden A", 100, 0, 255, 1);
	
	@EventHandler
	private Handler<EventRender3D> on3DRender = e -> {
		
		Vector4d visibleChestColor = new Vector4d(visibleRed.getValue() / 255d, visibleGreen.getValue() / 255d, visibleBlue.getValue() / 255d, visibleAlpha.getValue() / 255d);
		Vector4d hiddenChestColor = new Vector4d(hiddenRed.getValue() / 255d, hiddenGreen.getValue() / 255d, hiddenBlue.getValue() / 255d, hiddenAlpha.getValue() / 255d);
		double expand = 0.005;
		
		GlStateManager.pushMatrix();
		GlStateManager.pushAttrib();
		GlStateManager.enableDepth();
		GlStateManager.depthMask(false);
		
		StencilUtil.enableStencilTest();
		StencilUtil.enableWrite();
		StencilUtil.clearStencil();
		
		// Setup stencil
		for (TileEntity tileEntity : mc.theWorld.loadedTileEntityList) {
			if (!(tileEntity instanceof TileEntityChest || tileEntity instanceof TileEntityEnderChest))
				continue;
			
			StencilUtil.glStencilFunc(GL11.GL_NOTEQUAL, 1);
			StencilUtil.setTestOutcome(GL11.GL_KEEP, GL11.GL_KEEP, GL11.GL_REPLACE);
			DrawUtils3D.drawColored3DWorldBox(tileEntity.getPos().getX() - expand, tileEntity.getPos().getY() - expand, tileEntity.getPos().getZ() - expand, 
					tileEntity.getPos().getX() + 1 + expand, tileEntity.getPos().getY() + 1 + expand, tileEntity.getPos().getZ() + 1 + expand, 
					 0x00000000);
			

		}
		
		// Draw esp
		expand -= 0.05;
		StencilUtil.setTestOutcome(GL11.GL_KEEP, GL11.GL_KEEP, GL11.GL_KEEP);
		
		GL11.glEnable(GL11.GL_POLYGON_OFFSET_FILL);
		GL11.glPolygonOffset(1.0f, -1099998.0f);
		
		for (TileEntity tileEntity : mc.theWorld.loadedTileEntityList) {
			if (!(tileEntity instanceof TileEntityChest || tileEntity instanceof TileEntityEnderChest))
				continue;
			
			// Visible render
			StencilUtil.glStencilFunc(GL11.GL_EQUAL, 1);
			DrawUtils3D.drawColored3DWorldBox(tileEntity.getPos().getX() - expand, tileEntity.getPos().getY() - expand, tileEntity.getPos().getZ() - expand, 
					tileEntity.getPos().getX() + 1 + expand, tileEntity.getPos().getY() + 1 + expand, tileEntity.getPos().getZ() + 1 + expand, 
					 UiUtils.getColorFromVector(visibleChestColor));
			
			// Hidden render
			StencilUtil.glStencilFunc(GL11.GL_NOTEQUAL, 1);
			DrawUtils3D.drawColored3DWorldBox(tileEntity.getPos().getX() - expand, tileEntity.getPos().getY() - expand, tileEntity.getPos().getZ() - expand, 
					tileEntity.getPos().getX() + 1 + expand, tileEntity.getPos().getY() + 1 + expand, tileEntity.getPos().getZ() + 1 + expand, 
					 UiUtils.getColorFromVector(hiddenChestColor));
			
		}
		
		GL11.glPolygonOffset(1.0f, 1099998.0f);
		GL11.glDisable(GL11.GL_POLYGON_OFFSET_FILL);
		
		StencilUtil.disableStencilTest();
		
		GlStateManager.depthMask(true);
		GlStateManager.popAttrib();
		GlStateManager.popMatrix();
	};
	
}
