package wtf.shiyeno.modules.impl.render;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.renderer.WorldVertexBufferUploader;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.settings.PointOfView;
import org.lwjgl.opengl.GL11;
import wtf.shiyeno.events.Event;
import wtf.shiyeno.events.impl.render.EventRender;
import wtf.shiyeno.managment.Managment;
import wtf.shiyeno.modules.Function;
import wtf.shiyeno.modules.FunctionAnnotation;
import wtf.shiyeno.modules.Type;
import wtf.shiyeno.util.render.RenderUtil.IntColor;

@FunctionAnnotation(
        name = "ChinaHat",
        type = Type.Render
)
public class ChinaHatFunction extends Function {
    public ChinaHatFunction() {
    }

    public void onEvent(Event event) {
        if (mc.player != null && mc.world != null) {
            if (event instanceof EventRender) {
                EventRender e = (EventRender)event;
                if (e.isRender3D()) {
                    if (mc.gameSettings.getPointOfView() == PointOfView.FIRST_PERSON) {
                        return;
                    }

                    EntityRendererManager rm = mc.getRenderManager();
                    double x = mc.player.lastTickPosX + (mc.player.getPosX() - mc.player.lastTickPosX) * (double)e.partialTicks - rm.info.getProjectedView().getX();
                    double y = mc.player.lastTickPosY + (mc.player.getPosY() - mc.player.lastTickPosY) * (double)e.partialTicks - rm.info.getProjectedView().getY() + 0.03;
                    double z = mc.player.lastTickPosZ + (mc.player.getPosZ() - mc.player.lastTickPosZ) * (double)e.partialTicks - rm.info.getProjectedView().getZ();
                    float height = mc.player.getHeight();
                    RenderSystem.pushMatrix();
                    GL11.glDepthMask(false);
                    GL11.glEnable(2848);
                    GL11.glHint(3154, 4354);
                    RenderSystem.disableTexture();
                    RenderSystem.enableBlend();
                    RenderSystem.disableAlphaTest();
                    RenderSystem.shadeModel(7425);
                    RenderSystem.disableCull();
                    RenderSystem.lineWidth(1.5F);
                    RenderSystem.color4f(-1.0F, -1.0F, -1.0F, -1.0F);
                    buffer.begin(8, DefaultVertexFormats.POSITION_COLOR);
                    float[] colors = null;

                    int i;
                    for(i = 0; i <= 360; ++i) {
                        colors = IntColor.rgb(Managment.STYLE_MANAGER.getCurrentStyle().getColor(i));
                        buffer.pos(x, y + (double)height + 0.2, z).color(colors[0], colors[1], colors[2], 0.5F).endVertex();
                        buffer.pos(x + Math.cos(Math.toRadians((double)i)) * 0.5, y + (double)height, z + Math.sin(Math.toRadians((double)i)) * 0.5).color(colors[0], colors[1], colors[2], 0.5F).endVertex();
                    }

                    buffer.finishDrawing();
                    WorldVertexBufferUploader.draw(buffer);
                    RenderSystem.color4f(-1.0F, -1.0F, -1.0F, -1.0F);
                    buffer.begin(2, DefaultVertexFormats.POSITION_COLOR);

                    for(i = 0; i <= 360; ++i) {
                        buffer.pos(x + Math.cos(Math.toRadians((double)i)) * 0.5, y + (double)height, z + Math.sin(Math.toRadians((double)i)) * 0.5).color(colors[0], colors[1], colors[2], 0.5F).endVertex();
                    }

                    buffer.finishDrawing();
                    WorldVertexBufferUploader.draw(buffer);
                    RenderSystem.enableCull();
                    RenderSystem.disableBlend();
                    RenderSystem.enableTexture();
                    RenderSystem.enableAlphaTest();
                    GL11.glDepthMask(true);
                    GL11.glDisable(2848);
                    GL11.glHint(3154, 4354);
                    RenderSystem.shadeModel(7424);
                    RenderSystem.popMatrix();
                }
            }
        }
    }
}