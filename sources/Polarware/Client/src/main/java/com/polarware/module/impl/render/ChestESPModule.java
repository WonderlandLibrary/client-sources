package com.polarware.module.impl.render;

import com.polarware.module.Module;
import com.polarware.module.api.Category;
import com.polarware.module.api.ModuleInfo;
import com.polarware.event.bus.Listener;
import com.polarware.event.annotations.EventLink;
import com.polarware.event.impl.render.Render3DEvent;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityEnderChest;

import java.util.ConcurrentModificationException;

@ModuleInfo(name = "module.render.chestesp.name", description = "module.render.chestesp.description", category = Category.RENDER)
public final class ChestESPModule extends Module {

    @EventLink()
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

        NORMAL_POST_BLOOM_RUNNABLES.add(runnable);
    };
}