package io.github.liticane.monoxide.module.impl.hud;

import io.github.liticane.monoxide.util.render.font.FontStorage;
import io.github.liticane.monoxide.module.api.Module;
import io.github.liticane.monoxide.module.api.data.ModuleData;
import io.github.liticane.monoxide.module.api.ModuleCategory;
import io.github.liticane.monoxide.value.impl.ModeValue;
import io.github.liticane.monoxide.listener.event.minecraft.render.Render2DEvent;
import io.github.liticane.monoxide.listener.radbus.Listen;
import io.github.liticane.monoxide.util.render.color.ColorUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

import java.awt.*;

@ModuleData(name = "WaterMark", description = "Shows a watermark", category = ModuleCategory.HUD)
public class WaterMarkModule extends Module {

    public ModeValue mode = new ModeValue("Watermark Mode", this, new String[] { "None", "Simple", "Monoxide" });

    @Listen
    public void onRender2DEvent(Render2DEvent event) {
        switch (mode.getValue()) {
            case "None": {
                break;
            }
            case "Simple": {
                mc.fontRendererObj.drawStringWithShadow(
                        "M" + "§fonobition " + "§7[FPS: §f" + Minecraft.getDebugFPS() + "§7]",
                        5, 5, ColorUtil.getRainbow(5000, 0)
                );
                break;
            }
        }
    }

}
