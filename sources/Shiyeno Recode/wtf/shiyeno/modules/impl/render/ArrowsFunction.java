package wtf.shiyeno.modules.impl.render;

import com.mojang.blaze3d.platform.GlStateManager;
import java.awt.Color;
import java.util.Iterator;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import wtf.shiyeno.events.Event;
import wtf.shiyeno.events.impl.render.EventRender;
import wtf.shiyeno.managment.Managment;
import wtf.shiyeno.modules.Function;
import wtf.shiyeno.modules.FunctionAnnotation;
import wtf.shiyeno.modules.Type;
import wtf.shiyeno.util.font.Fonts;
import wtf.shiyeno.util.font.styled.StyledFont;
import wtf.shiyeno.util.render.BloomHelper;
import wtf.shiyeno.util.render.ColorUtil;
import wtf.shiyeno.util.render.RenderUtil.Render2D;

@FunctionAnnotation(
        name = "Arrows",
        type = Type.Render
)
public class ArrowsFunction extends Function {
    final StyledFont medium;

    public ArrowsFunction() {
        this.medium = Fonts.msMedium[16];
    }

    public void onEvent(Event event) {
        if (event instanceof EventRender render) {
            if (render.isRender2D()) {
                this.render2D(render);
            }
        }
    }

    private void render2D(EventRender render) {
        Iterator var2 = mc.world.getPlayers().iterator();

        while(var2.hasNext()) {
            Entity entity = (Entity)var2.next();
            if (entity != mc.player && entity.botEntity) {
                double x = entity.lastTickPosX + (entity.getPosX() - entity.lastTickPosX) * (double)mc.getRenderPartialTicks() - mc.getRenderManager().info.getProjectedView().getX();
                double z = entity.lastTickPosZ + (entity.getPosZ() - entity.lastTickPosZ) * (double)mc.getRenderPartialTicks() - mc.getRenderManager().info.getProjectedView().getZ();
                double cos = (double)MathHelper.cos((double)mc.player.rotationYaw * 0.017453292519943295);
                double sin = (double)MathHelper.sin((double)mc.player.rotationYaw * 0.017453292519943295);
                double rotY = -(z * cos - x * sin);
                double rotX = -(x * cos + z * sin);
                float angle = (float)(Math.atan2(rotY, rotX) * 180.0 / Math.PI);
                double x2 = (double)(50.0F * MathHelper.cos(Math.toRadians((double)angle)) + (float)render.scaledResolution.scaledWidth() / 2.0F);
                double y2 = (double)(50.0F * MathHelper.sin(Math.toRadians((double)angle)) + (float)render.scaledResolution.scaledHeight() / 2.0F);
                GlStateManager.pushMatrix();
                GlStateManager.disableBlend();
                GlStateManager.translated(x2, y2, 0.0);
                GlStateManager.rotatef(angle, 0.0F, 0.0F, 1.0F);
                int clr = Managment.FRIEND_MANAGER.isFriend(entity.getName().getString()) ? ColorUtil.rgba(0, 255, 0, 255) : Managment.STYLE_MANAGER.getCurrentStyle().getColor(entity.getEntityId());
                Render2D.drawTriangle(-4.0F, -1.0F, 5.0F, 7.0F, new Color(0, 0, 0, 32));
                Render2D.drawTriangle(-3.0F, 0.0F, 4.0F, 5.0F, new Color(clr));
                BloomHelper.registerRenderCall(() -> {
                    GlStateManager.pushMatrix();
                    GlStateManager.disableBlend();
                    GlStateManager.translated(x2, y2, 0.0);
                    GlStateManager.rotatef(angle, 0.0F, 0.0F, 1.0F);
                    Render2D.drawTriangle(-3.0F, 0.0F, 4.0F, 5.0F, new Color(clr));
                    GlStateManager.enableBlend();
                    GlStateManager.popMatrix();
                });
                GlStateManager.enableBlend();
                GlStateManager.popMatrix();
            }
        }
    }
}