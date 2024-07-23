package io.github.liticane.monoxide.module.impl.hud;

import io.github.liticane.monoxide.module.api.Module;
import io.github.liticane.monoxide.module.api.data.ModuleData;
import io.github.liticane.monoxide.module.api.ModuleCategory;
import io.github.liticane.monoxide.module.impl.hud.clientOverlay.IClientOverlayComponent;
import io.github.liticane.monoxide.module.ModuleManager;
import io.github.liticane.monoxide.listener.event.minecraft.render.Render2DEvent;
import io.github.liticane.monoxide.listener.radbus.Listen;
import io.github.liticane.monoxide.util.math.atomic.AtomicFloat;

import java.util.ArrayList;
import java.util.Comparator;

@ModuleData(name = "ClientOverlay", description = "Displays a neat little overlay", category = ModuleCategory.HUD)
public class ClientOverlayModule extends Module {
    private ArrayList<IClientOverlayComponent> clientOverlayComponents = new ArrayList<>();
    @Listen
    public void on2D(Render2DEvent render2DEvent) {
        AtomicFloat leftY = new AtomicFloat(0);
        AtomicFloat rightY = new AtomicFloat(0);
        if (clientOverlayComponents.isEmpty()) {
            clientOverlayComponents.add(ModuleManager.getInstance().getModule(ModuleListModule.class));
        }
        clientOverlayComponents.sort(Comparator.comparingInt(IClientOverlayComponent::getPriority));
        for(IClientOverlayComponent clientOverlayComponent : clientOverlayComponents) {
            clientOverlayComponent.draw(render2DEvent, leftY, rightY);
        }
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }
}
