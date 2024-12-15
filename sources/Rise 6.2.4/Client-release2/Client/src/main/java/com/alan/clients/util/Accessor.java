package com.alan.clients.util;

import com.alan.clients.Client;
import com.alan.clients.component.Component;
import com.alan.clients.layer.Layer;
import com.alan.clients.layer.LayerManager;
import com.alan.clients.layer.Layers;
import com.alan.clients.module.Module;
import com.alan.clients.ui.click.standard.RiseClickGUI;
import com.alan.clients.ui.theme.Themes;
import com.google.gson.Gson;
import net.minecraft.client.Minecraft;

public interface Accessor {
    Minecraft mc = Minecraft.getMinecraft();

    default Client getInstance() {
        return Client.INSTANCE;
    }

    default LayerManager getLayerManager() {
        return getInstance().getLayerManager();
    }

    default RiseClickGUI getClickGUI() {
        return getInstance().getClickGUI();
    }

    default Layer getLayer(Layers layer) {
        return getLayerManager().get(layer);
    }

    default Layer getLayer(Layers layer, int group) {
        return getLayerManager().get(layer, group);
    }

    default <T extends Component> T getComponent(Class<T> component) {
        return getInstance().getComponentManager().get(component);
    }

    default Themes getTheme() {
        return getInstance().getThemeManager().getTheme();
    }

    default <T extends Module> T getModule(final Class<T> clazz) {
        return getInstance().getModuleManager().get(clazz);
    }

    default Gson getGSON() {
        return getInstance().getGSON();
    }

    default Minecraft getClient() {
        return Minecraft.getMinecraft();
    }
}
