package dev.excellent.client.module.impl.render;

import dev.excellent.api.event.impl.render.Render2DEvent;
import dev.excellent.api.interfaces.event.Listener;
import dev.excellent.client.module.api.Category;
import dev.excellent.client.module.api.Module;
import dev.excellent.client.module.api.ModuleInfo;
import dev.excellent.impl.font.Font;
import dev.excellent.impl.font.Fonts;
import dev.excellent.impl.util.math.Mathf;
import dev.excellent.impl.util.render.RectUtil;
import dev.excellent.impl.util.render.RenderUtil;
import dev.excellent.impl.util.render.color.ColorUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.TNTEntity;
import net.minecraft.util.text.TextFormatting;
import org.joml.Vector2d;
import org.joml.Vector3d;

@ModuleInfo(name = "TNT Timer", description = "Показывает время через которое взорвётся ТНТ", category = Category.RENDER)
public class TNTTimer extends Module {
    private final Listener<Render2DEvent> onRender2D = event -> {
        for (Entity entity : mc.world.getAllEntities()) {
            if (entity instanceof TNTEntity tnt) {
                final String name = Mathf.round(tnt.getFuse() / 20.0F, 1) + " сек";
                Vector3d pos = RenderUtil.interpolate(tnt, mc.getRenderPartialTicks());
                Vector2d vec = RenderUtil.project2D(pos.x, pos.y + tnt.getHeight() + 0.5, pos.z);
                if (vec == null) return;

                Font font = Fonts.INTER_BOLD.get(14);

                float width = font.getWidth(name) + 4;
                float height = font.getHeight();
                int black = ColorUtil.getColor(0, 0, 0, 128);

                RectUtil.drawRect(event.getMatrix(), (float) (vec.x - width / 2), (float) vec.y, (float) (vec.x + width / 2), (float) (vec.y + height), black);
                font.drawCenter(event.getMatrix(), TextFormatting.RED + name, vec.x, vec.y + 0.5, -1);
            }
        }
    };
}
