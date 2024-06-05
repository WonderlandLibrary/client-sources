package gq.vapu.czfclient.Module.Modules.Render;

import gq.vapu.czfclient.API.EventHandler;
import gq.vapu.czfclient.API.Events.Render.EventRender3D;
import gq.vapu.czfclient.API.Value.Option;
import gq.vapu.czfclient.Module.Module;
import gq.vapu.czfclient.Module.ModuleType;
import gq.vapu.czfclient.Util.Render.ColorUtils;
import gq.vapu.czfclient.Util.Render.RenderUtil;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.util.AxisAlignedBB;

import java.awt.*;

public class ItemESP extends Module {
    public Option<Boolean> rainbow = new Option<>("Rainbow", "rainbow", true);

    public ItemESP() {
        super("ItemESP", new String[]{}, ModuleType.Render);
        this.addValues(rainbow);
    }

    @EventHandler
    public void onRender(EventRender3D event) {
        for (Entity e : mc.theWorld.loadedEntityList) {
            if (!(e instanceof EntityItem))
                continue;
            final double x = e.lastTickPosX + (e.posX - e.lastTickPosX) * mc.timer.renderPartialTicks
                    - RenderManager.renderPosX;
            final double y = e.lastTickPosY + (e.posY - e.lastTickPosY) * mc.timer.renderPartialTicks
                    - RenderManager.renderPosY;
            final double z = e.lastTickPosZ + (e.posZ - e.lastTickPosZ) * mc.timer.renderPartialTicks
                    - RenderManager.renderPosZ;
            final AxisAlignedBB entityBox = e.getEntityBoundingBox();
            final AxisAlignedBB axisAlignedBB = new AxisAlignedBB(
                    entityBox.minX - e.posX + x - 0.05D,
                    entityBox.minY - e.posY + y,
                    entityBox.minZ - e.posZ + z - 0.05D,
                    entityBox.maxX - e.posX + x + 0.05D,
                    entityBox.maxY - e.posY + y + 0.15D,
                    entityBox.maxZ - e.posZ + z + 0.05D
            );
            GlStateManager.pushMatrix();
            RenderUtil.startDrawing();
            if (rainbow.getValue())
                RenderUtil.glColor(ColorUtils.getRainbow().getRGB());
            else
                RenderUtil.glColor(new Color(210, 132, 246).getRGB());
            RenderUtil.drawOutlinedBoundingBox(axisAlignedBB);
            RenderUtil.stopDrawing();
            GlStateManager.popMatrix();
        }
    }
}
