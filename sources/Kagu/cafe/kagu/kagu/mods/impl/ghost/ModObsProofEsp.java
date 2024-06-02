/**
 * 
 */
package cafe.kagu.kagu.mods.impl.ghost;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.util.ArrayList;

import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;
import javax.vecmath.Vector4d;

import org.lwjgl.opengl.GL11;

import cafe.kagu.kagu.Kagu;
import cafe.kagu.kagu.eventBus.EventHandler;
import cafe.kagu.kagu.eventBus.Handler;
import cafe.kagu.kagu.eventBus.impl.EventEntityRender;
import cafe.kagu.kagu.eventBus.impl.EventRender2D;
import cafe.kagu.kagu.eventBus.impl.EventRender3D;
import cafe.kagu.kagu.eventBus.impl.EventRenderObs;
import cafe.kagu.kagu.eventBus.impl.EventTick;
import cafe.kagu.kagu.font.FontRenderer;
import cafe.kagu.kagu.font.FontUtils;
import cafe.kagu.kagu.mods.Module;
import cafe.kagu.kagu.mods.ModuleManager;
import cafe.kagu.kagu.mods.impl.player.ModAntiBot;
import cafe.kagu.kagu.mods.impl.visual.ModEsp.EspEntity;
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
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityWaterMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;

/**
 * @author DistastefulBannock
 *
 */
public class ModObsProofEsp extends Module {
	
	public ModObsProofEsp() {
		super("ObsProofEsp", Category.GHOST);
		setSettings(boxExpand, targetAll, targetPlayers, targetAnimals, targetMobs, targetSelf);
	}
	
	private ModeSetting mode = new ModeSetting("Mode", "Kagu OBS", "Kagu OBS");
	
	// Expand box size
	private DoubleSetting boxExpand = new DoubleSetting("Box Expand", 0, 0, 1, 0.01);
	
	// Render targets
	private BooleanSetting targetAll = new BooleanSetting("Everything ESP", true);
	private BooleanSetting targetPlayers = (BooleanSetting) new BooleanSetting("Player ESP", true).setDependency(targetAll::isDisabled);
	private BooleanSetting targetAnimals = (BooleanSetting) new BooleanSetting("Animal ESP", false).setDependency(targetAll::isDisabled);
	private BooleanSetting targetMobs = (BooleanSetting) new BooleanSetting("Mob ESP", false).setDependency(targetAll::isDisabled);
	private BooleanSetting targetSelf = (BooleanSetting) new BooleanSetting("Self ESP", false).setDependency(() -> {return targetAll.isEnabled() || targetPlayers.isEnabled();});
	
	@Override
	public void onEnable() {
		Kagu.getModuleManager().getModule(ModObsProofUi.class).enable();
		if (Kagu.getModuleManager().getModule(ModObsProofUi.class).isDisabled()) {
			toggle();
			return;
		}
	}
	
	@Override
	public void onDisable() {
		draw2dEntities.clear();
	}
	
	private ArrayList<EspEntity> draw2dEntities = new ArrayList<EspEntity>();
	
	private static final Color BANNOCK_SONA_BLUE = new Color(165, 224, 254, 255);
	private static final Color BANNOCK_SONA_PURPLE = new Color(214, 140, 255, 255);
	@EventHandler
	private Handler<EventRenderObs> onRenderObs = e -> {
		if (e.isPost())
			return;
		Graphics2D graphics2d = e.getGraphics();
		
		switch (mode.getMode()) {
			case "Kagu OBS":{
				graphics2d.setStroke(new BasicStroke(2));
				for (EspEntity espEntity : draw2dEntities) {
					int x = (int) espEntity.getLeft();
					int y = (int) espEntity.getTop();
					int width = (int) (espEntity.getRight() - x);
					int height = (int) (espEntity.getBottom() - y);
//					graphics2d.setColor(Color.BLACK);
//					graphics2d.drawRect(x + 2, y + 2, width - 1, height - 1);
//					graphics2d.drawRect(x - 1, y - 1, width + 2, height + 2);
//					graphics2d.setColor(BANNOCK_SONA_BLUE);
//					graphics2d.drawRect(x + 1, y + 1, width - 2, height - 2);
//					graphics2d.setColor(BANNOCK_SONA_PURPLE);
//					graphics2d.drawRect(x - 1, y - 1, width + 1, height + 1);
					
					// First corner
					int lineWidth = 1;
					int infoBarGap = lineWidth * 3;
					int nametagGap = lineWidth;
					double cornerSize = 0.35;
					
					// Black
					graphics2d.setColor(Color.BLACK);
					
					// TL
					graphics2d.fillRect(x, y, lineWidth * 3, (int)(height * cornerSize));
					graphics2d.fillRect(x, y, (int)(width * cornerSize), lineWidth * 3);
					
					// BL
					graphics2d.fillRect(x, (int) (y + height * (1 - cornerSize)), lineWidth * 3, (int)(height * cornerSize));
					graphics2d.fillRect(x, y + height - lineWidth * 3, (int)(width * cornerSize), lineWidth * 3);
					
					// TR
					graphics2d.fillRect(x + width - lineWidth * 3, y, lineWidth * 3, (int)(height * cornerSize));
					graphics2d.fillRect((int) (x + width * (1 - cornerSize)), y, (int)(width * cornerSize), lineWidth * 3);
					
					// BR
					graphics2d.fillRect(x + width - lineWidth * 3, (int) (y + height * (1 - cornerSize)), lineWidth * 3, (int)(Math.ceil(height * cornerSize)));
					graphics2d.fillRect((int) (x + width * (1 - cornerSize)), y + height - lineWidth * 3, (int)(Math.ceil(width * cornerSize)), lineWidth * 3);
					
					// White
					graphics2d.setColor(Color.WHITE);
					
					// TL
					graphics2d.fillRect(x + lineWidth, y + lineWidth, lineWidth, (int)(height * cornerSize) - lineWidth * 2);
					graphics2d.fillRect(x + lineWidth, y + lineWidth, (int)(width * cornerSize) - lineWidth * 2, lineWidth);
					
					// BL
					graphics2d.fillRect(x + lineWidth, (int) (y + height * (1 - cornerSize)) + lineWidth, lineWidth, (int)(height * cornerSize) - lineWidth * 2);
					graphics2d.fillRect(x + lineWidth, y + height - lineWidth * 2, (int)(width * cornerSize) - lineWidth * 2, lineWidth);
					
					// TR
					graphics2d.fillRect(x + width - lineWidth * 2, y + lineWidth, lineWidth, (int)(height * cornerSize) - lineWidth * 2);
					graphics2d.fillRect((int) (x + width * (1 - cornerSize)) + lineWidth, y + lineWidth, (int)(width * cornerSize) - lineWidth * 2, lineWidth);
					
					// BR
					graphics2d.fillRect(x + width - lineWidth * 2, (int) (y + height * (1 - cornerSize)) + lineWidth, lineWidth, (int)(Math.ceil(height * cornerSize)) - lineWidth * 2);
					graphics2d.fillRect((int) (x + width * (1 - cornerSize)) + lineWidth, y + height - lineWidth * 2, (int)(Math.ceil(width * cornerSize)) - lineWidth * 2, lineWidth);
					
				}
			}break;
		}
		
	};
	
	@EventHandler
	private Handler<EventTick> onTick = e -> {
		if (e.isPost())
			return;
		setInfo(mode.getMode());
		if (Kagu.getModuleManager().getModule(ModObsProofUi.class).isDisabled())
			toggle();
	};
	
	@EventHandler
	private Handler<EventRender3D> onRender3D = e -> {
		
		switch(mode.getMode()) {
			case "Kagu OBS":{
				ArrayList<EspEntity> draw2dEntities = new ArrayList<EspEntity>();
				double expand = boxExpand.getValue();
				for (Entity ent : mc.theWorld.loadedEntityList) {
					
					// Only get living entities
					if (!(ent instanceof EntityLivingBase)) {
						continue;
					}
					
					// Antibot
					if (Kagu.getModuleManager().getModule(ModAntiBot.class).isEnabled() && ent instanceof EntityPlayer 
							&& Kagu.getModuleManager().getModule(ModAntiBot.class).isBot((EntityPlayer)ent))
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
//						Vector3d entityRenderCoords = new Vector3d(entityLivingBase.posX, entityLivingBase.posY, entityLivingBase.posZ);
						
						// Center the offsets interpolated coords to the entity
						entityRenderCoords.setX(entityRenderCoords.getX() - ((boundingBox.maxX - boundingBox.minX) / 2));
						entityRenderCoords.setZ(entityRenderCoords.getZ() - ((boundingBox.maxZ - boundingBox.minZ) / 2));
						
						// Recreate the bounding box with the interpolated coords
						boundingBox = new AxisAlignedBB(entityRenderCoords.getX(), entityRenderCoords.getY(), entityRenderCoords.getZ(), entityRenderCoords.getX() + (boundingBox.maxX - boundingBox.minX), entityRenderCoords.getY() + (boundingBox.maxY - boundingBox.minY), entityRenderCoords.getZ() + (boundingBox.maxZ - boundingBox.minZ));
						
					}
					
					// All the corners for the bounding box -> screen coords for each position
					Vector3d offsets = entityLivingBase == mc.thePlayer ? DrawUtils3D.get3dPlayerOffsets() : DrawUtils3D.get3dWorldOffsets();
					Vector3f backBl = DrawUtils3D.project2D((float)boundingBox.minX, (float)boundingBox.minY, (float)boundingBox.minZ, offsets, false);
					Vector3f backBr = DrawUtils3D.project2D((float)boundingBox.maxX, (float)boundingBox.minY, (float)boundingBox.minZ, offsets, false);
					Vector3f backTl = DrawUtils3D.project2D((float)boundingBox.minX, (float)boundingBox.maxY, (float)boundingBox.minZ, offsets, false);
					Vector3f backTr = DrawUtils3D.project2D((float)boundingBox.maxX, (float)boundingBox.maxY, (float)boundingBox.minZ, offsets, false);
					Vector3f frontBl = DrawUtils3D.project2D((float)boundingBox.minX, (float)boundingBox.minY, (float)boundingBox.maxZ, offsets, false);
					Vector3f frontBr = DrawUtils3D.project2D((float)boundingBox.maxX, (float)boundingBox.minY, (float)boundingBox.maxZ, offsets, false);
					Vector3f frontTl = DrawUtils3D.project2D((float)boundingBox.minX, (float)boundingBox.maxY, (float)boundingBox.maxZ, offsets, false);
					Vector3f frontTr = DrawUtils3D.project2D((float)boundingBox.maxX, (float)boundingBox.maxY, (float)boundingBox.maxZ, offsets, false);
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
	
}
