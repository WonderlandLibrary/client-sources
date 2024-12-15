package com.alan.clients.layer;

import com.alan.clients.Client;
import com.alan.clients.event.Listener;
import com.alan.clients.event.Priorities;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.other.GameEvent;
import com.alan.clients.event.impl.render.Render2DEvent;
import com.alan.clients.event.impl.render.Render3DEvent;
import com.alan.clients.event.impl.render.RenderGUIEvent;
import com.alan.clients.module.impl.render.Interface;
import com.alan.clients.ui.ingame.GuiIngameCache;
import com.alan.clients.util.shader.base.RiseShader;
import com.alan.clients.util.shader.base.ShaderRenderType;
import lombok.Getter;
import lombok.SneakyThrows;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;

import java.util.LinkedHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class LayerManager {
    @Getter
    private final LinkedHashMap<Integer, LinkedHashMap<Layers, Layer>> layers = new LinkedHashMap<>();
    private final int maxLayers = 3;
    private boolean shaders;

    @SneakyThrows
    public LayerManager() {
        for (int i = 0; i <= maxLayers; i++) {
            layers.put(i, new LinkedHashMap<>());

            for (Layers layer : Layers.values()) {
                this.layers.get(i).put(layer, new Layer(layer.getType().getShader() == null ? null :
                        (RiseShader) layer.getType().getShader().newInstance()));
            }
        }

        Client.INSTANCE.getEventBus().register(this);
    }

    public Layer get(Layers layer) {
        return get(layer, 0);
    }

    public Layer get(Layers layer, int group) {
        return this.layers.get(group).get(layer);
    }

    @EventLink(value = Priorities.EXTREMELY_LOW)
    public final Listener<RenderGUIEvent> renderGUI = event -> render(ShaderRenderType.OVERLAY);

    @EventLink(value = Priorities.EXTREMELY_LOW)
    public final Listener<Render2DEvent> render2D = event -> {
        GuiIngameCache.renderGameOverlay(0);
        render(ShaderRenderType.OVERLAY);
    };

    @EventLink()
    public final Listener<GameEvent> onGame = event ->
            shaders = Client.INSTANCE.getModuleManager()
                    .get(Interface.class).shaders.getValue();

    @EventLink(value = Priorities.EXTREMELY_LOW)
    public final Listener<Render3DEvent> render3D = event -> render(ShaderRenderType.CAMERA);

    private void render(ShaderRenderType type) {
        if (Client.DEVELOPMENT_SWITCH) {
            AtomicInteger active = new AtomicInteger();

            layers.forEach(((group, layers) -> {
                layers.values().forEach(layer -> {
                    if (layer.getShader() != null && !layer.getRunnables().isEmpty()) {
                        active.getAndIncrement();
                    }

//                    if (!layer.getRunnables().isEmpty())System.out.println(type + " " + group + " " + layer.shader);
                });
            }));

//            System.out.println("Done displaying");
            if (active.get() > 2 && Minecraft.getMinecraft().currentScreen == null) {
//                Shouldnt be in production
//                System.out.println("To many shader layers rendering " + active.get());
            }
        }

        layers.forEach(((group, layers) -> layers.values().forEach(layer -> {
            if (layer.getShader() == null || shaders) layer.run(type);
        })));
        layers.forEach((groups, map) -> map.forEach((layer, items) -> items.clear()));

        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.disableLighting();
        GlStateManager.enableAlpha();
    }
}