package me.vaziak.sensation.client.impl.visual;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

import me.vaziak.sensation.Sensation;
import me.vaziak.sensation.client.api.Category;
import me.vaziak.sensation.client.api.Module;
import me.vaziak.sensation.client.api.event.annotations.Collect;
import me.vaziak.sensation.client.api.event.events.EventRender2D;
import me.vaziak.sensation.client.api.event.events.RenderNametagEvent;
import me.vaziak.sensation.client.api.property.impl.ColorProperty;
import me.vaziak.sensation.client.api.property.impl.StringsProperty;
import me.vaziak.sensation.client.impl.combat.AntiBot;
import me.vaziak.sensation.client.impl.combat.KillAura;
import me.vaziak.sensation.utils.anthony.ColorCreator;
import me.vaziak.sensation.utils.anthony.Draw;
import me.vaziak.sensation.utils.anthony.basicfont.Fonts;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author antja03
 */
public class ESP extends Module {

    private final StringsProperty prop_elements = new StringsProperty("Elements", "The ESP elements that will be drawn.",
            null, true, false, new String[] {"Box", "Health", "Name"}, new Boolean[] {false, false, false});

    private final StringsProperty prop_healthStyle = new StringsProperty("Health Style", "How the health element will be drawn.",
            () -> prop_elements.getValue().get("Health"), true, true, new String[] {"Number", "Bar"}, new Boolean[] {false, true});

    private final StringsProperty prop_entites = new StringsProperty("Entities", "The entites ESP will be drawn on.",
            () -> !prop_elements.getValue().isEmpty(), true, false, new String[] {"Players", "Monsters", "Animals", "Villagers", "Golems"}, new Boolean[] {false, false, false, false, false});

    private final StringsProperty prop_avoid = new StringsProperty("Avoid", "Flags that ESP will avoid.",
            () -> !prop_elements.getValue().isEmpty(), true, false, new String[] {"Invisible", "Teammate", "Bots"}, new Boolean[] {false, false, false});

    private final ColorProperty prop_boxColor = new ColorProperty("Box (default)", "The inner color of the box.", () -> prop_elements.getValue().get("Box"),
            1f, 0f, 1f, 255);

    private final ColorProperty prop_boxTargetColor = new ColorProperty("Box (target)", "The inner color of the box on your current Kill aura target.", () -> prop_elements.getValue().get("Box"),
            0f, 1f, .64f, 255);

    private final ColorProperty prop_boxOutlineColor = new ColorProperty("Box outline", "The outer color of the box.", () -> prop_elements.getValue().get("Box"),
            0f, 0f, 0f, 255);

    private final ColorProperty prop_nameColor = new ColorProperty("Name", "The color of the entities name.", () -> prop_elements.getValue().get("Name"),
            1f, 0f, 1f, 255);

    private final ColorProperty prop_healthColor = new ColorProperty("Health Number", "The color of the entities health number.", () -> prop_elements.getValue().get("Health") && prop_healthStyle.getValue().get("Number"),
            1f, 0f, 1f, 255);

    private FloatBuffer modelView = GLAllocation.createDirectFloatBuffer(16);
    private FloatBuffer projection = GLAllocation.createDirectFloatBuffer(16);
    private IntBuffer viewport = GLAllocation.createDirectIntBuffer(16);
    private FloatBuffer screenCords = GLAllocation.createDirectFloatBuffer(3);

    private final double[][] currentEntityPoints = new double[8][3];

    private double renderPosX1 = Double.MAX_VALUE;
    private double renderPosY1 = Double.MAX_VALUE;
    private double renderPosX2 = -1;
    private double renderPosY2 = -1;

    private KillAura cheat_killAura;

    public ESP() {
        super("ESP", Category.VISUAL);
        registerValue(prop_elements, prop_healthStyle, prop_entites, prop_avoid, prop_boxColor, prop_boxTargetColor, prop_boxOutlineColor, prop_nameColor, prop_healthColor);
    }

    @Override
    public void onEnable() {
        if (cheat_killAura == null) {
            cheat_killAura = ((KillAura) Sensation.instance.cheatManager.getCheatRegistry().get("Kill Aura"));
        }
    }

    @Collect
    public void onRenderNametag(RenderNametagEvent renderNametagEvent) {
        if (isValidEntity(renderNametagEvent.getEntity()) && prop_elements.getValue().get("Name"))
            renderNametagEvent.setCancelled(true);
    }

    @SuppressWarnings("unused")
    @Collect
	public void onRenderOverlay(EventRender2D e) {
		/*for(Object entity : mc.theWorld.loadedEntityList){
				Entity ent = (Entity) entity;
				
	    	if (isValidEntity(ent)) { 
	    		
				double sneak = ent.isSneaking() ? .2 : 0; 
				double playeradd = 0;
				double posX = ent.lastTickPosX + (ent.posX - ent.lastTickPosX) * e.getPartialTicks() - RenderManager.renderPosX;
				double posY = ent.lastTickPosY + (ent.posY - ent.lastTickPosY) * e.getPartialTicks() - RenderManager.renderPosY;
				double posZ = ent.lastTickPosZ + (ent.posZ - ent.lastTickPosZ) * e.getPartialTicks() - RenderManager.renderPosZ;
				

				AxisAlignedBB box = 
						new AxisAlignedBB(
								
								ent.getEntityBoundingBox().minX - ent.posX + posX,
								ent.getEntityBoundingBox().minY - (ent.posY + sneak) + posY,
								ent.getEntityBoundingBox().minZ - ent.posZ + posZ,
								ent.getEntityBoundingBox().maxX - ent.posX + posX,
								ent.getEntityBoundingBox().maxY - (ent.posY + sneak) + posY,
								ent.getEntityBoundingBox().maxZ - ent.posZ + posZ
						
								);
				
		        if (ent instanceof EntityLivingBase) {
		            float f1 = 0.01F;
		            float f = ent.width / 2.0F;
		            RenderGlobal.func_181563_a(
							new AxisAlignedBB(
									ent.getEntityBoundingBox().minX - (double) f,
									ent.getEntityBoundingBox().minY + (double) ent.getEyeHeight()
									- 0.009999999776482582D,
									ent.getEntityBoundingBox().minZ - (double) f,
									ent.getEntityBoundingBox().maxX + (double) f,
									ent.getEntityBoundingBox().maxY + (double) ent.getEyeHeight()
									+ 0.009999999776482582D,
									ent.getEntityBoundingBox().maxZ + (double) f),
							255, 0, 0, 255);
				}
				GL11.glPushMatrix();
				GL11.glTranslated(posX, posY, posZ);
				GL11.glRotatef(-ent.rotationYaw, 0, ent.height, 0);
				GL11.glTranslated(-posX, -posY, -posZ); 
				GlStateManager.pushMatrix();
				Color c = this.prop_boxColor.getValue();
				GL11.glColor4f(c.getRed(),c.getGreen(),c.getBlue(), c.getAlpha()); 
				GL11.glEnable(GL11.GL_BLEND);  
				GL11.glDisable(GL11.GL_TEXTURE_2D);
				GL11.glBegin(3);
				GL11.glVertex3d(box.minX, box.minY, box.minZ);
				GL11.glVertex3d(box.maxX, box.minY, box.minZ);
				GL11.glVertex3d(box.maxX, box.minY, box.maxZ);
				GL11.glVertex3d(box.minX, box.minY, box.maxZ);
				GL11.glVertex3d(box.minX, box.minY, box.minZ);
				GL11.glEnd();
				GL11.glBegin(2);
				GL11.glVertex3d(box.minX, box.maxY, box.minZ);
				GL11.glVertex3d(box.maxX, box.maxY, box.minZ);
				GL11.glVertex3d(box.maxX, box.maxY, box.maxZ);
				GL11.glVertex3d(box.minX, box.maxY, box.maxZ);
				GL11.glVertex3d(box.minX, box.maxY, box.minZ);
				GL11.glEnd();
				GL11.glBegin(1);
				GL11.glVertex3d(box.minX, box.minY, box.minZ);
				GL11.glVertex3d(box.minX, box.maxY, box.minZ);
				GL11.glVertex3d(box.maxX, box.minY, box.minZ);
				GL11.glVertex3d(box.maxX, box.maxY, box.minZ);
				GL11.glVertex3d(box.maxX, box.minY, box.maxZ);
				GL11.glVertex3d(box.maxX, box.maxY, box.maxZ);
				GL11.glVertex3d(box.minX, box.minY, box.maxZ);
				GL11.glVertex3d(box.minX, box.maxY, box.maxZ);
				GL11.glEnd(); 
				GL11.glEnable(GL11.GL_TEXTURE_2D); 
				GL11.glDisable(GL11.GL_BLEND); 
				GlStateManager.popMatrix();
				
				GL11.glPopMatrix();
			}
		} */
        GL11.glPushMatrix();
        entityLoop: for (EntityLivingBase entity : collectEntities()) {
            Minecraft.getMinecraft().entityRenderer.setupCameraTransform(Minecraft.getMinecraft().timer.renderPartialTicks, 0);

            AxisAlignedBB boundingBox = entity.getEntityBoundingBox().expand(0.05f, 0.05f, 0.05f).offset(0, 0.05, 0);
            currentEntityPoints[0] = project2D(boundingBox.minX - Minecraft.getMinecraft().getRenderManager().renderPosX, boundingBox.minY - Minecraft.getMinecraft().getRenderManager().renderPosY, boundingBox.minZ - Minecraft.getMinecraft().getRenderManager().renderPosZ);
            currentEntityPoints [1] = project2D(boundingBox.minX - Minecraft.getMinecraft().getRenderManager().renderPosX, boundingBox.minY - Minecraft.getMinecraft().getRenderManager().renderPosY, boundingBox.maxZ - Minecraft.getMinecraft().getRenderManager().renderPosZ);
            currentEntityPoints[2] = project2D(boundingBox.maxX - Minecraft.getMinecraft().getRenderManager().renderPosX, boundingBox.minY - Minecraft.getMinecraft().getRenderManager().renderPosY, boundingBox.minZ - Minecraft.getMinecraft().getRenderManager().renderPosZ);
            currentEntityPoints[3] = project2D(boundingBox.maxX - Minecraft.getMinecraft().getRenderManager().renderPosX, boundingBox.minY - Minecraft.getMinecraft().getRenderManager().renderPosY, boundingBox.maxZ - Minecraft.getMinecraft().getRenderManager().renderPosZ);
            currentEntityPoints[4] = project2D(boundingBox.minX - Minecraft.getMinecraft().getRenderManager().renderPosX, boundingBox.maxY - Minecraft.getMinecraft().getRenderManager().renderPosY, boundingBox.minZ - Minecraft.getMinecraft().getRenderManager().renderPosZ);
            currentEntityPoints[5] = project2D(boundingBox.minX - Minecraft.getMinecraft().getRenderManager().renderPosX, boundingBox.maxY - Minecraft.getMinecraft().getRenderManager().renderPosY, boundingBox.maxZ - Minecraft.getMinecraft().getRenderManager().renderPosZ);
            currentEntityPoints[6] = project2D(boundingBox.maxX - Minecraft.getMinecraft().getRenderManager().renderPosX, boundingBox.maxY - Minecraft.getMinecraft().getRenderManager().renderPosY, boundingBox.minZ - Minecraft.getMinecraft().getRenderManager().renderPosZ);
            currentEntityPoints[7] = project2D(boundingBox.maxX - Minecraft.getMinecraft().getRenderManager().renderPosX, boundingBox.maxY - Minecraft.getMinecraft().getRenderManager().renderPosY, boundingBox.maxZ - Minecraft.getMinecraft().getRenderManager().renderPosZ);

            renderPosX1 = Double.MAX_VALUE;
            renderPosY1 = Double.MAX_VALUE;
            renderPosX2 = -1;
            renderPosY2 = -1;

            pointChecks: for (double[] point : currentEntityPoints) {
                if (point == null)
                    continue entityLoop;

                if (point[2] < 0.0f || point[2] > 1.0f)
                    continue entityLoop;

                point[1] = (Display.getHeight() / 2 - point[1]);

                if (point[0] < renderPosX1)
                    renderPosX1 = point[0];

                if (point[0] > renderPosX2)
                    renderPosX2 = point[0];

                if (point[1] < renderPosY1)
                    renderPosY1 = point[1];

                if (point[1] > renderPosY2)
                    renderPosY2 = point[1];
            }

            int guiScale = mc.gameSettings.guiScale;
            mc.gameSettings.guiScale = 2;
            Minecraft.getMinecraft().entityRenderer.setupOverlayRendering();
            mc.gameSettings.guiScale = guiScale;
            if (cheat_killAura == null) {
                cheat_killAura = ((KillAura) Sensation.instance.cheatManager.getCheatRegistry().get("Kill Aura"));
            }
            if (prop_elements.getValue().get("Box")) {
                //LEFT
                if (cheat_killAura != null && cheat_killAura.getCurrentTarget() != null) {
                    Draw.drawRectangle(renderPosX1, renderPosY1, renderPosX1 + 0.5, renderPosY2, cheat_killAura.getCurrentTarget() != null && cheat_killAura.getCurrentTarget() == entity || entity.getName().toLowerCase().contains("bobicraft") ? prop_boxTargetColor.getValue().getRGB() : prop_boxColor.getValue().getRGB());
                    //TOP
                    Draw.drawRectangle(renderPosX1, renderPosY1, renderPosX2, renderPosY1 + 0.5, cheat_killAura.getCurrentTarget() != null && cheat_killAura.getCurrentTarget() == entity || entity.getName().toLowerCase().contains("bobicraft") ? prop_boxTargetColor.getValue().getRGB() : prop_boxColor.getValue().getRGB());
                    //RIGHT
                    Draw.drawRectangle(renderPosX2 - 0.5, renderPosY1, renderPosX2, renderPosY2, cheat_killAura.getCurrentTarget() != null && cheat_killAura.getCurrentTarget() == entity || entity.getName().toLowerCase().contains("bobicraft") ? prop_boxTargetColor.getValue().getRGB() : prop_boxColor.getValue().getRGB());
                    //BOTTOM
                    Draw.drawRectangle(renderPosX1, renderPosY2 - 0.5, renderPosX2, renderPosY2, cheat_killAura.getCurrentTarget() != null && cheat_killAura.getCurrentTarget() == entity || entity.getName().toLowerCase().contains("bobicraft") ? prop_boxTargetColor.getValue().getRGB() : prop_boxColor.getValue().getRGB());
                }
                //LEFT
                Draw.drawRectangle(renderPosX1 + 0.5, renderPosY1 + 0.5, renderPosX1 + 1.0, renderPosY2 - 0.5, prop_boxOutlineColor.getValue().getRGB());
                //TOP
                Draw.drawRectangle(renderPosX1 + 0.5, renderPosY1 + 0.5, renderPosX2 - 0.5, renderPosY1 + 1.0, prop_boxOutlineColor.getValue().getRGB());
                //RIGHT
                Draw.drawRectangle(renderPosX2 - 1.0, renderPosY1 + 0.5, renderPosX2 - 0.5, renderPosY2 - 0.5, prop_boxOutlineColor.getValue().getRGB());
                //BOTTOM
                Draw.drawRectangle(renderPosX1 + 0.5, renderPosY2 - 1.0, renderPosX2 - 0.5, renderPosY2 - 0.5, prop_boxOutlineColor.getValue().getRGB());

                //LEFT
                Draw.drawRectangle(renderPosX1, renderPosY1 - 0.5, renderPosX1 - 0.5, renderPosY2 + 0.5, prop_boxOutlineColor.getValue().getRGB());
                //TOP
                Draw.drawRectangle(renderPosX1 - 0.5, renderPosY1, renderPosX2 + 0.5, renderPosY1 - 0.5, prop_boxOutlineColor.getValue().getRGB());
                //RIGHT
                Draw.drawRectangle(renderPosX2 + 0.5, renderPosY1 - 0.5, renderPosX2, renderPosY2 + 0.5, prop_boxOutlineColor.getValue().getRGB());
                //BOTTOM
                Draw.drawRectangle(renderPosX1 - 0.5, renderPosY2 + 0.5, renderPosX2 + 0.5, renderPosY2, prop_boxOutlineColor.getValue().getRGB());
            }

            if (prop_elements.getValue().get("Name")) {
                GlStateManager.pushMatrix();

                float scale = 1.0f / (mc.thePlayer.getDistanceToEntity(entity) / 10f);
                if (scale < 0.85f)
                    scale = 0.85f;
                if (scale > 1.0f)
                    scale = 1.0f;

                GlStateManager.scale(scale, scale, scale);

                Fonts.f14.drawCenteredStringWithShadow(entity.getName(), (renderPosX1 + ((renderPosX2 - renderPosX1) / 2)) / scale, ((renderPosY1) / scale) - 6,  prop_nameColor.getValue().getRGB());
                GlStateManager.popMatrix();
            }

            if (prop_elements.getValue().get("Health")) {
                if (prop_healthStyle.getValue().get("Bar")) {

                    double hpPercentage = entity.getHealth() / 20;
                    if (hpPercentage > 1)
                        hpPercentage = 1;
                    else if (hpPercentage < 0)
                        hpPercentage = 0;

                    double height = (renderPosY2 - renderPosY1) * hpPercentage;

                    int r = (int) (230 + (50 - 230) * hpPercentage);
                    int g = (int) (50 + (230 - 50) * hpPercentage);
                    int b = 50;

                    Draw.drawRectangle(renderPosX1 - 2, renderPosY1, renderPosX1 - 1, renderPosY2, ColorCreator.create(10, 10, 10, 180));
                    Draw.drawRectangle(renderPosX1 - 2, renderPosY2 - height, renderPosX1 - 1, renderPosY2, ColorCreator.create(r, g, b, 255));
                }

                if (prop_healthStyle.getValue().get("Number")) {
                    GlStateManager.pushMatrix();

                    float scale = 1.0f / (mc.thePlayer.getDistanceToEntity(entity) / 10f);
                    if (scale < 0.9f)
                        scale = 0.9f;
                    if (scale > 1.0f)
                        scale = 1.0f;

                    GlStateManager.scale(scale, scale, scale);


                    if (prop_healthStyle.getValue().get("Bar")) {
                        double hpPercentage = entity.getHealth() / 20;
                        if (hpPercentage > 1)
                            hpPercentage = 1;
                        else if (hpPercentage < 0)
                            hpPercentage = 0;

                        double height = (renderPosY2 - renderPosY1) * hpPercentage;

                        Fonts.f12.drawStringWithShadow(String.valueOf((int) (entity.getHealth() / 20 * 100)) + "%",
                                renderPosX1 / scale - 4 - Fonts.f12.getStringWidth(String.valueOf((int) (entity.getHealth() / 20 * 100)) + "%"),
                                renderPosY2 / scale - height - 3,
                                prop_healthColor.getValue().getRGB());
                    } else {
                        Fonts.f12.drawStringWithShadow(String.valueOf((int) (entity.getHealth() / 20 * 100)) + "%",
                                renderPosX1 / scale - 4 - Fonts.f12.getStringWidth(String.valueOf((int) (entity.getHealth() / 20 * 100)) + "%"),
                                (renderPosY1 + 2) / scale,
                                prop_healthColor.getValue().getRGB());
                    }

                    GlStateManager.popMatrix();
                }
            }
        }
        GL11.glPopMatrix();
        GlStateManager.enableBlend();
        Minecraft.getMinecraft().entityRenderer.setupOverlayRendering();
    }
    
    private boolean isValidEntity(Entity entity) {
    	AntiBot ab = (AntiBot) Sensation.instance.cheatManager.getCheatRegistry().get("Anti Bot");
        if (entity == mc.thePlayer || (prop_avoid.getValue().get("Bots") && ab.ignoredEntities.contains(entity)) || (prop_avoid.getValue().get("Invisible") && entity.isInvisible()))
            return false;

        boolean selectedEntity =
                (entity instanceof EntityPlayer && prop_entites.getValue().get("Players"))
                        || (entity instanceof EntityVillager && prop_entites.getValue().get("Villagers"))
                        || (entity instanceof EntityGolem && prop_entites.getValue().get("Golems"))
                        || (entity instanceof EntityAnimal && prop_entites.getValue().get("Animals"))
                        || (entity instanceof EntityMob && prop_entites.getValue().get("Monsters"));

        boolean invis = !prop_avoid.getValue().get("Invisible") || (!entity.isInvisible() && !entity.isInvisibleToPlayer(mc.thePlayer));
        boolean npcCheck = true;

        if (mc.getCurrentServerData() != null && mc.getCurrentServerData().serverIP.contains("hypixel")) {
            if (entity.getDisplayName().toString().contains("§8[NPC]")) {
                npcCheck = false;
            }
        }

        return selectedEntity && invis && npcCheck;
    }

    public List<EntityLivingBase> collectEntities() {
        return mc.theWorld.loadedEntityList.stream()
                .filter(EntityLivingBase.class::isInstance).map(EntityLivingBase.class::cast)
                .filter(entity -> isValidEntity(entity))
                .collect(Collectors.toList());
    }

    private double[] project2D(double posX, double posY, double posZ) {
        modelView = GLAllocation.createDirectFloatBuffer(16);
        projection = GLAllocation.createDirectFloatBuffer(16);
        viewport = GLAllocation.createDirectIntBuffer(16);
        screenCords = GLAllocation.createDirectFloatBuffer(3);

        modelView.clear();
        projection.clear();
        viewport.clear();
        screenCords.clear();

        GL11.glGetFloat(2982, modelView);
        GL11.glGetFloat(2983, projection);
        GL11.glGetInteger(2978, viewport);

        if (GLU.gluProject((float) posX, (float) posY, (float) posZ, modelView, projection, viewport, screenCords)) {
            return new double[] {screenCords.get(0) / 2.0f, screenCords.get(1) / 2.0f, screenCords.get(2)};
        }

        return null;
    }

}