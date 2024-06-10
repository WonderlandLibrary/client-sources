package me.dinozoid.strife.module.implementations.visuals;

import com.google.common.base.Predicates;
import me.dinozoid.strife.alpine.listener.EventHandler;
import me.dinozoid.strife.alpine.listener.Listener;
import me.dinozoid.strife.event.implementations.render.Render2DEvent;
import me.dinozoid.strife.event.implementations.render.Render3DEvent;
import me.dinozoid.strife.module.Category;
import me.dinozoid.strife.module.Module;
import me.dinozoid.strife.module.ModuleInfo;
import me.dinozoid.strife.module.implementations.combat.KillAuraModule;
import me.dinozoid.strife.property.Property;
import me.dinozoid.strife.property.implementations.DoubleProperty;
import me.dinozoid.strife.property.implementations.EnumProperty;
import me.dinozoid.strife.property.implementations.MultiSelectEnumProperty;
import me.dinozoid.strife.util.render.RenderUtil;
import me.dinozoid.strife.util.world.WorldUtil;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.AxisAlignedBB;
import org.lwjgl.opengl.GL14;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import static org.lwjgl.opengl.GL11.*;

import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ModuleInfo(name = "ESP", renderName = "ESP", description = "See things through walls.", category = Category.VISUALS)
public class ESPModule extends Module {

    private final Map<EntityLivingBase, float[]> entityPositionMap = new HashMap<>();

    private final MultiSelectEnumProperty<Target> targetsProperty = new MultiSelectEnumProperty("Targets", Target.PLAYERS);
    private final MultiSelectEnumProperty<Element> elementsProperty = new MultiSelectEnumProperty("Elements", Element.BOX, Element.NAMETAGS, Element.HEALTH, Element.ARMOR);
    private Property<Boolean> boxProperty = new Property("Box", true);
    private DoubleProperty boxThicknessProperty = new DoubleProperty("Box Thickness", 1, 1, 10, 1, () -> boxProperty.value());
    private final EnumProperty<BoxMode> boxModeProperty = new EnumProperty("Targets", BoxMode.SOLID, () -> boxProperty.value());
    private Property<Boolean> nametagsProperty = new Property("Nametags", true);
    private Property<Boolean> healthProperty = new Property("Health", true);

    @EventHandler
    private final Listener<Render3DEvent> render3DListener = new Listener<>(event -> {
        final List<EntityLivingBase> livingEntities = WorldUtil.getLivingEntities(Predicates.and(KillAuraModule::isValid));
        entityPositionMap.clear();
        ScaledResolution scaledResolution = new ScaledResolution(mc);
        for(EntityLivingBase entity : livingEntities) {
            float x = (float) (RenderUtil.interpolate(entity.posX, entity.lastTickPosX, event.partialTicks()) - mc.getRenderManager().viewerPosX);
            float y = (float) (RenderUtil.interpolate(entity.posY, entity.lastTickPosY, event.partialTicks()) - mc.getRenderManager().viewerPosY);
            float z = (float) (RenderUtil.interpolate(entity.posZ, entity.lastTickPosZ, event.partialTicks()) - mc.getRenderManager().viewerPosZ);
            AxisAlignedBB bb = new AxisAlignedBB(x - entity.width / 2, y, z - entity.width / 2, x + entity.width / 2, y + entity.height, z + entity.width / 2);
            double[][] vectors = {{bb.minX, bb.minY, bb.minZ}, {bb.minX, bb.maxY, bb.minZ}, {bb.minX, bb.maxY, bb.maxZ}, {bb.minX, bb.minY, bb.maxZ}, {bb.maxX, bb.minY, bb.minZ}, {bb.maxX, bb.maxY, bb.minZ}, {bb.maxX, bb.maxY, bb.maxZ}, {bb.maxX, bb.minY, bb.maxZ}};
            Vector4f position = new Vector4f(Float.MAX_VALUE, Float.MAX_VALUE, -1.0F, -1.0F);
            for(double[] vec : vectors) {
                Vector3f projection = RenderUtil.project2D(scaledResolution.getScaleFactor(), (float)vec[0], (float)vec[1], (float)vec[2]);
                if(projection != null && projection.z > 0 && projection.z < 1) {
                    position.x = Math.min(position.x, projection.x);
                    position.y = Math.min(position.y, projection.y);
                    position.z = Math.max(position.z, projection.x);
                    position.w = Math.max(position.w, projection.y);
                }
            }
            entityPositionMap.put(entity, new float[]{position.x, position.z, position.y, position.w});
        }
    });

    @EventHandler
    private final Listener<Render2DEvent> render2DListener = new Listener<>(event -> {
        for (EntityLivingBase entity : entityPositionMap.keySet()) {
            float[] positions = entityPositionMap.get(entity);
            float x = positions[0];
            float z = positions[1];
            float y = positions[2];
            float w = positions[3];
            mc.entityRenderer.setupOverlayRendering();
            glPushMatrix();
            glDisable(GL_TEXTURE_2D);
            glEnable(GL_BLEND);
            for (Element element : elementsProperty.values()) {
                if (elementsProperty.selected(element)) {
                    switch (element) {
                        case BOX: {
                            if(boxModeProperty.value() == BoxMode.SOLID) {
                                glLineWidth(boxThicknessProperty.value().floatValue() * 4.5f);
                                glBegin(GL_LINE_LOOP);
                                RenderUtil.color(Color.BLACK);
                                glVertex2f(x, y);
                                glVertex2f(x, w);
                                glVertex2f(z, w);
                                glVertex2f(z, y);
                                glEnd();
                                glLineWidth(boxThicknessProperty.value().floatValue());
                                glBegin(GL_LINE_LOOP);
                                RenderUtil.color(Color.RED);
                                glVertex2f(x, y);
                                glVertex2f(x, w);
                                glVertex2f(z, w);
                                glVertex2f(z, y);
                                glEnd();
                            } else {

                            }
                        }
                        break;
                        case ARMOR: {

                        }
                        break;
                        case HEALTH: {
                            glLineWidth(boxThicknessProperty.value().floatValue() * 5.6f);
                            glBegin(GL_LINES);
                            RenderUtil.color(Color.BLACK);
                            glVertex2f(x - 3, y);
                            glVertex2f(x - 3, w);
                            glEnd();
                            glLineWidth(boxThicknessProperty.value().floatValue() / 2);
                            glBegin(GL_LINES);
                            Color color = Color.GREEN;
                            if(entity.getHealth() < entity.getMaxHealth() / 2) color = Color.YELLOW;
                            if(entity.getHealth() < entity.getMaxHealth() / 3) color = Color.ORANGE;
                            if(entity.getHealth() < entity.getMaxHealth() / 4) color = Color.RED;
                            RenderUtil.color(color);
                            glVertex2f(x - 3, y);
                            glVertex2f(x - 3, w - 1);
                            glEnd();
                        }
                        break;
                        case NAMETAGS: {

                        }
                        break;
                    }
                }
            }
            glDisable(GL_BLEND);
            glEnable(GL_TEXTURE_2D);
            glPopMatrix();
        }
    });

    public enum Element {
        BOX, NAMETAGS, HEALTH, ARMOR
    }

    public enum BoxMode {
        SOLID, CORNERS
    }

    public enum Target {
        PLAYERS, MOBS, ANIMALS, INVISIBLES
    }
}
