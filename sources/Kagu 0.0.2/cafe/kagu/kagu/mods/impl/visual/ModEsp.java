/**
 * 
 */
package cafe.kagu.kagu.mods.impl.visual;

import java.util.ArrayList;

import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;
import javax.vecmath.Vector4d;

import org.lwjgl.opengl.GL11;

import cafe.kagu.kagu.Kagu;
import cafe.kagu.kagu.eventBus.EventHandler;
import cafe.kagu.kagu.eventBus.Handler;
import cafe.kagu.kagu.eventBus.impl.EventRender2D;
import cafe.kagu.kagu.eventBus.impl.EventRender3D;
import cafe.kagu.kagu.eventBus.impl.EventEntityRender;
import cafe.kagu.kagu.eventBus.impl.EventTick;
import cafe.kagu.kagu.font.FontRenderer;
import cafe.kagu.kagu.font.FontUtils;
import cafe.kagu.kagu.mods.Module;
import cafe.kagu.kagu.mods.ModuleManager;
import cafe.kagu.kagu.mods.impl.player.ModAntiBot;
import cafe.kagu.kagu.settings.SettingDependency;
import cafe.kagu.kagu.settings.impl.BooleanSetting;
import cafe.kagu.kagu.settings.impl.DoubleSetting;
import cafe.kagu.kagu.settings.impl.ModeSetting;
import cafe.kagu.kagu.utils.DrawUtils3D;
import cafe.kagu.kagu.utils.MiscUtils;
import cafe.kagu.kagu.utils.UiUtils;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityWaterMob;
import net.minecraft.entity.passive.IAnimals;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;

/**
 * @author lavaflowglow
 *
 */
public class ModEsp extends Module {
	
	public ModEsp() {
		super("ESP", Category.VISUAL);
		setSettings(mode, boxExpand, chams, renderInvisibleModels, targetAll, targetPlayers, targetAnimals, targetMobs, targetSelf);
	}
	
	// ESP modes
	private ModeSetting mode = new ModeSetting("Mode", "Kagu 2D", "Kagu 2D", "Simple White", "Test");
	
	// Expand box size
	private DoubleSetting boxExpand = new DoubleSetting("Box Expand", 0, 0, 1, 0.01);
	
	// Chams
	private BooleanSetting chams = new BooleanSetting("Chams", true);
	
	// Render targets
	private BooleanSetting targetAll = new BooleanSetting("Everything ESP", true);
	private BooleanSetting targetPlayers = (BooleanSetting) new BooleanSetting("Player ESP", true).setDependency(targetAll::isDisabled);
	private BooleanSetting targetAnimals = (BooleanSetting) new BooleanSetting("Animal ESP", false).setDependency(targetAll::isDisabled);
	private BooleanSetting targetMobs = (BooleanSetting) new BooleanSetting("Mob ESP", false).setDependency(targetAll::isDisabled);
	private BooleanSetting targetSelf = (BooleanSetting) new BooleanSetting("Self ESP", false).setDependency(() -> {return targetAll.isEnabled() || targetPlayers.isEnabled();});
			
	// Invisible
	private BooleanSetting renderInvisibleModels = new BooleanSetting("Render Invisible Models", false);
	
	@Override
	public void onDisable() {
		draw2dEntities.clear();
	}
	
	/**
	 * @return the renderInvisibleModels
	 */
	public BooleanSetting getRenderInvisibleModels() {
		return renderInvisibleModels;
	}
	
	private ArrayList<EspEntity> draw2dEntities = new ArrayList<EspEntity>();
	
	@EventHandler
	private Handler<EventRender2D> onRender2D = e -> {
		if (e.isPost())
			return;
		
		switch (mode.getMode()) {
			
			case "Simple White": {
				GL11.glLineWidth(1);
				UiUtils.enableWireframe();
				for (EspEntity ent : draw2dEntities) {
					Gui.drawRect(ent.getLeft(), ent.getTop(), ent.getRight(), ent.getBottom(), 0xffffffff);
				}
				UiUtils.disableWireframe();
			}break;
			
			case "Kagu 2D":{
				
				FontRenderer nametagFr = FontUtils.STRATUM2_MEDIUM_18_AA;
				
				GlStateManager.pushMatrix();
				GlStateManager.pushAttrib();
				GlStateManager.enableBlend();
				GlStateManager.enableAlpha();
				
				for (EspEntity ent : draw2dEntities) {
					
					try {
						double healthPercent = ent.getEntityLivingBase().getHealth() / ent.getEntityLivingBase().getMaxHealth();
						int lerpedHealthColor = UiUtils.getColorFromVector(UiUtils.lerpColor(new Vector4d(1, 0, 0, 1), new Vector4d(0, 1, 0, 1), 
								1 - (healthPercent)));
						double lineWidth = 2;
						double infoBarGap = lineWidth * 3;
						double nametagGap = lineWidth;
						int white = 0xffffffff;
						int black = 0xff000000;
						int blue = 0xff0000ff;
						double cornerSize = 0.25;
						
						// Top left corner
						Gui.drawRect(ent.getLeft() - lineWidth, ent.getTop() - lineWidth,
								ent.getLeft() + (lineWidth / 2), ent.getTop() + (ent.getBottom() - ent.getTop()) * cornerSize, black);
						Gui.drawRect(ent.getLeft() - lineWidth, ent.getTop() - lineWidth,
								ent.getLeft() + (ent.getRight() - ent.getLeft()) * cornerSize, ent.getTop() + (lineWidth / 2), black);
						Gui.drawRect(ent.getLeft() - (lineWidth / 2), ent.getTop() - (lineWidth / 2),
								ent.getLeft(), ent.getTop() + (ent.getBottom() - ent.getTop()) * cornerSize - (lineWidth / 2), white);
						Gui.drawRect(ent.getLeft() - (lineWidth / 2), ent.getTop() - (lineWidth / 2),
								ent.getLeft() + (ent.getRight() - ent.getLeft()) * cornerSize - (lineWidth / 2), ent.getTop(), white);
						
						// Bottom left corner
						Gui.drawRect(ent.getLeft() - lineWidth, ent.getBottom() - lineWidth,
								ent.getLeft() + (lineWidth / 2), ent.getBottom() + (ent.getTop() - ent.getBottom()) * cornerSize, black);
						Gui.drawRect(ent.getLeft() - lineWidth, ent.getBottom() - lineWidth,
								ent.getLeft() + (ent.getRight() - ent.getLeft()) * cornerSize, ent.getBottom() + (lineWidth / 2), black);
						Gui.drawRect(ent.getLeft() - (lineWidth / 2), ent.getBottom() - (lineWidth / 2),
								ent.getLeft(), ent.getBottom() + (ent.getTop() - ent.getBottom()) * cornerSize + (lineWidth / 2), white);
						Gui.drawRect(ent.getLeft() - (lineWidth / 2), ent.getBottom() - (lineWidth / 2),
								ent.getLeft() + (ent.getRight() - ent.getLeft()) * cornerSize - (lineWidth / 2), ent.getBottom(), white);
						
						// Bottom right corner
						Gui.drawRect(ent.getRight() - lineWidth, ent.getBottom() - lineWidth,
								ent.getRight() + (lineWidth / 2), ent.getBottom() + (ent.getTop() - ent.getBottom()) * cornerSize, black);
						Gui.drawRect(ent.getRight() + (lineWidth / 2), ent.getBottom() - lineWidth,
								ent.getRight() + (ent.getLeft() - ent.getRight()) * cornerSize, ent.getBottom() + (lineWidth / 2), black);
						Gui.drawRect(ent.getRight() - (lineWidth / 2), ent.getBottom() - (lineWidth / 2),
								ent.getRight(), ent.getBottom() + (ent.getTop() - ent.getBottom()) * cornerSize + (lineWidth / 2), white);
						Gui.drawRect(ent.getRight(), ent.getBottom() - (lineWidth / 2),
								ent.getRight() + (ent.getLeft() - ent.getRight()) * cornerSize + (lineWidth / 2), ent.getBottom(), white);
						
						// Top right corner
						Gui.drawRect(ent.getRight() - lineWidth, ent.getTop() - lineWidth,
								ent.getRight() + (lineWidth / 2), ent.getTop() + (ent.getBottom() - ent.getTop()) * cornerSize, black);
						Gui.drawRect(ent.getRight() + (lineWidth / 2), ent.getTop() - lineWidth,
								ent.getRight() + (ent.getLeft() - ent.getRight()) * cornerSize, ent.getTop() + (lineWidth / 2), black);
						Gui.drawRect(ent.getRight() - (lineWidth / 2), ent.getTop() - (lineWidth / 2),
								ent.getRight(), ent.getTop() + (ent.getBottom() - ent.getTop()) * cornerSize - (lineWidth / 2), white);
						Gui.drawRect(ent.getRight(), ent.getTop() - (lineWidth / 2),
								ent.getRight() + (ent.getLeft() - ent.getRight()) * cornerSize + (lineWidth / 2), ent.getTop(), white);
						
						// Health bar
						Gui.drawRect(ent.getLeft() - Math.min(Math.max((ent.getRight() - ent.getLeft()) * 0.25, 3), infoBarGap),
								ent.getTop() - lineWidth,
								ent.getLeft() - Math.min(Math.max((ent.getRight() - ent.getLeft()) * 0.25, 3), infoBarGap) - lineWidth, ent.getBottom() + (lineWidth / 2), black);
						Gui.drawRect(ent.getLeft() - Math.min(Math.max((ent.getRight() - ent.getLeft()) * 0.25, 3), infoBarGap),
								(ent.getBottom() + (lineWidth / 2)) + ((ent.getTop() - lineWidth) - (ent.getBottom() + (lineWidth / 2))) * healthPercent,
								ent.getLeft() - Math.min(Math.max((ent.getRight() - ent.getLeft()) * 0.25, 3), infoBarGap) - lineWidth, ent.getBottom() + (lineWidth / 2), healthPercent >= 1 ? blue : lerpedHealthColor);
						
						// Nametags
						String name = MiscUtils.removeFormatting(ent.getEntityLivingBase().getName());
						double nametagScaling = ((ent.getRight() - ent.getLeft()) * (ent.getEntityLivingBase() instanceof EntityPlayer ? 2 : 0.7)) / nametagFr.getStringWidth(name);
						GlStateManager.pushMatrix();
						if (nametagScaling < 1) {
							GlStateManager.translate((ent.getLeft() + ent.getRight()) * 0.5, ent.getTop() - nametagGap - (nametagFr.getFontHeight() * nametagScaling), 0);
							GlStateManager.scale(nametagScaling, nametagScaling, nametagScaling);
							GlStateManager.translate(-((ent.getLeft() + ent.getRight()) * 0.5), -(ent.getTop() - nametagGap - (nametagFr.getFontHeight() * nametagScaling)), 0);
						}
						
						nametagFr.drawCenteredString(name, (ent.getLeft() + ent.getRight()) * 0.5, ent.getTop() - nametagGap - (nametagFr.getFontHeight() * (nametagScaling >= 1 ? 1 : nametagScaling)), 0xffffffff, true);
						
						GlStateManager.popMatrix();
						
					}catch(Exception e1) {}
					
				}
				
				GlStateManager.popAttrib();
				GlStateManager.popMatrix();
				GL11.glEnable(GL11.GL_BLEND);
				GlStateManager.enableBlend();
				
			}break;
			
		}
	};
	
	@EventHandler
	private Handler<EventRender3D> onRender3D = e -> {
		
		switch(mode.getMode()) {
			case "Kagu 2D":
			case "Simple White":{
				ArrayList<EspEntity> draw2dEntities = new ArrayList<EspEntity>();
				double expand = boxExpand.getValue();
				for (Entity ent : mc.theWorld.loadedEntityList) {
					
					// Only get living entities
					if (!(ent instanceof EntityLivingBase)) {
						continue;
					}
					
					// Antibot
					if (Kagu.getModuleManager().getModule(ModAntiBot.class).isEnabled() && ent instanceof EntityPlayer && Kagu.getModuleManager().getModule(ModAntiBot.class).isBot((EntityPlayer)ent))
						continue;
					
					// Render targeting
					if (targetAll.isDisabled()) {
						if (targetPlayers.isEnabled() && ent instanceof EntityPlayer);
						else if (targetAnimals.isEnabled() && (ent instanceof EntityAnimal || ent instanceof EntityWaterMob));
						else if (targetMobs.isEnabled() && ent instanceof EntityMob);
						else continue;
					}
					
					EntityLivingBase entityLivingBase = (EntityLivingBase)ent;
					
					// Ignore the player if in first person
					if (entityLivingBase == mc.thePlayer && (mc.gameSettings.thirdPersonView == 0 || targetSelf.isDisabled()))
						continue;
					
					double left = Integer.MAX_VALUE, top = Integer.MAX_VALUE, right = Integer.MIN_VALUE, bottom = Integer.MIN_VALUE;
					
					// Get bounding box
					double collisionBorderSize = entityLivingBase.getCollisionBorderSize();
					AxisAlignedBB boundingBox = entityLivingBase.getEntityBoundingBox().expand(expand + collisionBorderSize, expand + collisionBorderSize, expand + collisionBorderSize);
					// Correct bounding box coords to be smooth with the entity's interpolation
					if (entityLivingBase != mc.thePlayer) {
						
						Vector3d entityRenderCoords = DrawUtils3D.get3dEntityOffsets(entityLivingBase);
						
						// Center the offsets interpolated coords to the entity
						entityRenderCoords.setX(entityRenderCoords.getX() - ((boundingBox.maxX - boundingBox.minX) / 2));
						entityRenderCoords.setZ(entityRenderCoords.getZ() - ((boundingBox.maxZ - boundingBox.minZ) / 2));
						
						// Recreate the bounding box with the interpolated coords
						boundingBox = new AxisAlignedBB(entityRenderCoords.getX(), entityRenderCoords.getY(), entityRenderCoords.getZ(), entityRenderCoords.getX() + (boundingBox.maxX - boundingBox.minX), entityRenderCoords.getY() + (boundingBox.maxY - boundingBox.minY), entityRenderCoords.getZ() + (boundingBox.maxZ - boundingBox.minZ));
						
					}
					
					// All the corners for the bounding box -> screen coords for each position
					Vector3d offsets = entityLivingBase == mc.thePlayer ? DrawUtils3D.get3dPlayerOffsets() : DrawUtils3D.get3dWorldOffsets();
					Vector3f backBl = DrawUtils3D.project2D((float)boundingBox.minX, (float)boundingBox.minY, (float)boundingBox.minZ, offsets);
					Vector3f backBr = DrawUtils3D.project2D((float)boundingBox.maxX, (float)boundingBox.minY, (float)boundingBox.minZ, offsets);
					Vector3f backTl = DrawUtils3D.project2D((float)boundingBox.minX, (float)boundingBox.maxY, (float)boundingBox.minZ, offsets);
					Vector3f backTr = DrawUtils3D.project2D((float)boundingBox.maxX, (float)boundingBox.maxY, (float)boundingBox.minZ, offsets);
					Vector3f frontBl = DrawUtils3D.project2D((float)boundingBox.minX, (float)boundingBox.minY, (float)boundingBox.maxZ, offsets);
					Vector3f frontBr = DrawUtils3D.project2D((float)boundingBox.maxX, (float)boundingBox.minY, (float)boundingBox.maxZ, offsets);
					Vector3f frontTl = DrawUtils3D.project2D((float)boundingBox.minX, (float)boundingBox.maxY, (float)boundingBox.maxZ, offsets);
					Vector3f frontTr = DrawUtils3D.project2D((float)boundingBox.maxX, (float)boundingBox.maxY, (float)boundingBox.maxZ, offsets);
					Vector3f[] corners = new Vector3f[] {backBl, backBr, backTl, backTr, 
														 frontBl, frontBr, frontTl, frontTr};
					
					// Calculate the best box coords
					for (Vector3f corner : corners) {
						if (corner == null || corner.getZ() < 0 || corner.getZ() >= 1)
							continue;
						
						left = Math.min(left, corner.getX());
						top = Math.min(top, corner.getY());
						right = Math.max(right, corner.getX());
						bottom = Math.max(bottom, corner.getY());
					}
					
					// Remove ones where we couldn't get all the render coords
					if (left == Integer.MAX_VALUE || top == Integer.MAX_VALUE || right == Integer.MIN_VALUE || bottom == Integer.MIN_VALUE) {
						continue;
					}
					
					draw2dEntities.add(new EspEntity(entityLivingBase, left, top, right, bottom));
					
				}
				
				this.draw2dEntities = draw2dEntities;
			}break;
		}
		
	};
	
	@EventHandler
	private Handler<EventTick> onTick = e -> {
		if (e.isPost())
			return;
		setInfo(mode.getMode());
	};
	
	@EventHandler
	private Handler<EventEntityRender> onEntityRender = e -> {
		
		// Antibot
		if (Kagu.getModuleManager().getModule(ModAntiBot.class).isEnabled() && ((EventEntityRender) e).getEntity() instanceof EntityPlayer
				&& Kagu.getModuleManager().getModule(ModAntiBot.class).isBot((EntityPlayer) ((EventEntityRender) e).getEntity()))
			return;
		
		// Chams
		if (chams.isEnabled()) {
			if (!(e.getEntity() instanceof EntityLivingBase))
				return;
			
			if (targetAll.isDisabled()) {
				if (targetPlayers.isEnabled() && e.getEntity() instanceof EntityPlayer);
				else if (targetAnimals.isEnabled() && (e.getEntity() instanceof EntityAnimal || e.getEntity() instanceof EntityWaterMob));
				else if (targetMobs.isEnabled() && e.getEntity() instanceof EntityMob);
				else return;
			}
			
			if (e.isPre()) {
				GL11.glEnable(GL11.GL_POLYGON_OFFSET_FILL);
				GL11.glPolygonOffset(1.0f, -1099998.0f);
			}else {
				GL11.glPolygonOffset(1.0f, 1099998.0f);
				GL11.glDisable(GL11.GL_POLYGON_OFFSET_FILL);
			}
		}
		
	};
	
	public static class EspEntity {

		/**
		 * @param entityLivingBase The entity to render
		 * @param left The left of the box
		 * @param top The top of the box
		 * @param right The right of the box
		 * @param bottom The bottom of the box
		 */
		public EspEntity(EntityLivingBase entityLivingBase, double left, double top, double right, double bottom) {
			super();
			this.entityLivingBase = entityLivingBase;
			this.left = left;
			this.top = top;
			this.right = right;
			this.bottom = bottom;
		}
		
		private EntityLivingBase entityLivingBase;
		private double left, top, right, bottom;
		
		/**
		 * @return the entityLivingBase
		 */
		public EntityLivingBase getEntityLivingBase() {
			return entityLivingBase;
		}
		
		/**
		 * @return the left
		 */
		public double getLeft() {
			return left;
		}

		/**
		 * @return the top
		 */
		public double getTop() {
			return top;
		}

		/**
		 * @return the right
		 */
		public double getRight() {
			return right;
		}

		/**
		 * @return the bottom
		 */
		public double getBottom() {
			return bottom;
		}
		
	}
	
	/**
	 * @return the mode
	 */
	public ModeSetting getMode() {
		return mode;
	}
	
	/**
	 * @return the chams
	 */
	public BooleanSetting getChams() {
		return chams;
	}
	
}
