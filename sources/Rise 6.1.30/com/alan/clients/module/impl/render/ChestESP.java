package com.alan.clients.module.impl.render;

import com.alan.clients.event.Listener;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.render.Render3DEvent;
import com.alan.clients.module.Module;
import com.alan.clients.module.api.Category;
import com.alan.clients.module.api.ModuleInfo;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityEnderChest;

import java.util.ConcurrentModificationException;

import static com.alan.clients.layer.Layers.BLOOM;

@ModuleInfo(aliases = {"module.render.chestesp.name"}, description = "module.render.chestesp.description", category = Category.RENDER)
public final class ChestESP extends Module {

    @EventLink
    public final Listener<Render3DEvent> onRender3D = event -> {
        final Runnable runnable = () -> {
            try {
                mc.theWorld.loadedTileEntityList.forEach(entity -> {
                    if (entity instanceof TileEntityChest || entity instanceof TileEntityEnderChest) {
                        RendererLivingEntity.setShaderBrightness(getTheme().getFirstColor());
                        TileEntityRendererDispatcher.instance.renderBasicTileEntity(entity, event.getPartialTicks());
                        RendererLivingEntity.unsetShaderBrightness();
                    }
                });
            } catch (final ConcurrentModificationException ignored) {
            }
        };

        getLayer(BLOOM).add(runnable);
    };
}