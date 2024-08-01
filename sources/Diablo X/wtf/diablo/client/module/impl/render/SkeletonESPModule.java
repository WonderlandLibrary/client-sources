package wtf.diablo.client.module.impl.render;

import best.azura.eventbus.handler.EventHandler;
import best.azura.eventbus.handler.Listener;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.opengl.GL11;
import wtf.diablo.client.event.impl.client.renderering.Render3DEvent;
import wtf.diablo.client.module.api.data.AbstractModule;
import wtf.diablo.client.module.api.data.ModuleCategoryEnum;
import wtf.diablo.client.module.api.data.ModuleMetaData;
import wtf.diablo.client.setting.impl.ColorSetting;
import wtf.diablo.client.setting.impl.NumberSetting;
import wtf.diablo.client.util.render.RenderUtil;

import java.awt.*;
import java.util.HashMap;

@ModuleMetaData(name = "Skeleton ESP", description = "Draws a skeleton around players", category = ModuleCategoryEnum.RENDER)
public final class SkeletonESPModule extends AbstractModule {
    private final HashMap<EntityPlayer, ModelPlayer> entities = new HashMap<>();
    private final NumberSetting<Double> width = new NumberSetting<>("Width", 1.0, 0.5, 5D, 0.5D);
    private final ColorSetting color = new ColorSetting("Color", Color.WHITE);

    public SkeletonESPModule() {
        this.registerSettings(width, color);
    }

    @EventHandler
    private final Listener<Render3DEvent> render3DEventListener = e -> {
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GlStateManager.disableDepth();
        GlStateManager.disableTexture2D();
        GL11.glHint(GL11.GL_LINE_SMOOTH_HINT, GL11.GL_NICEST);
        GL11.glEnable(GL11.GL_COLOR_MATERIAL);

        GlStateManager.depthMask(false);

        for (final EntityPlayer entity : entities.keySet()) {
            if(!entity.equals(mc.thePlayer)) {
                ModelPlayer model = entities.get(entity);
                RenderUtil.drawSkeleton(e, entity, model, width.getValue(), color.getValue());
            }
        }

        GlStateManager.depthMask(true);
        GlStateManager.disableBlend();
        GlStateManager.enableTexture2D();
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GlStateManager.enableDepth();
        GlStateManager.popMatrix();
        entities.clear();
    };

    public HashMap<EntityPlayer, ModelPlayer> getEntities() {
        return entities;
    }
}
