package wtf.diablo.client.module.impl.render;

import best.azura.eventbus.handler.EventHandler;
import best.azura.eventbus.handler.Listener;
import net.minecraft.entity.player.EntityPlayer;
import wtf.diablo.client.event.impl.client.renderering.Render3DEvent;
import wtf.diablo.client.module.api.data.AbstractModule;
import wtf.diablo.client.module.api.data.ModuleCategoryEnum;
import wtf.diablo.client.module.api.data.ModuleMetaData;
import wtf.diablo.client.setting.impl.ColorSetting;
import wtf.diablo.client.util.render.RenderUtil;

import java.awt.*;

@ModuleMetaData(name = "Tracers", description = "Draws lines to entities", category = ModuleCategoryEnum.RENDER)
public final class TracersModule extends AbstractModule {
    private final ColorSetting tracerColor = new ColorSetting("Tracer Color", Color.WHITE);

    public TracersModule() {
        this.registerSettings(tracerColor);
    }

    @EventHandler
    private final Listener<Render3DEvent> render3DEventListener = e -> {
        if (mc.theWorld == null || mc.thePlayer == null) return;

        for (final EntityPlayer player : mc.theWorld.playerEntities) {
            if (player != mc.thePlayer && !player.isDead && !player.isInvisible()) {
                RenderUtil.drawLineToPosition(player.posX, player.posY, player.posZ, 1f, tracerColor.getValue().getRGB());
            }
        }
    };
}
