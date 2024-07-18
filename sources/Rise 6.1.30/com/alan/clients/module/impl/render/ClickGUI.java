package com.alan.clients.module.impl.render;

import com.alan.clients.Client;
import com.alan.clients.event.Listener;
import com.alan.clients.event.Priorities;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.input.GuiKeyBoardEvent;
import com.alan.clients.event.impl.render.Render2DEvent;
import com.alan.clients.module.Module;
import com.alan.clients.module.api.Category;
import com.alan.clients.module.api.ModuleInfo;
import org.lwjgl.input.Keyboard;
import rip.vantage.commons.util.time.StopWatch;

import static com.alan.clients.layer.Layers.BLOOM;
import static com.alan.clients.layer.Layers.REGULAR;

/**
 * Displays a GUI which can display and do various things
 */
@ModuleInfo(aliases = {"module.render.clickgui.name"}, description = "module.render.clickgui.description", category = Category.RENDER, keyBind = Keyboard.KEY_RSHIFT)
public final class ClickGUI extends Module {
    private final StopWatch stopWatch = new StopWatch();

    @Override
    public void onEnable() {
//        Client.INSTANCE.getEventBus().register(Client.INSTANCE.getClickGUI());
        mc.displayGuiScreen(Client.INSTANCE.getClickGUI());
        stopWatch.reset();
    }

    @Override
    public void onDisable() {
        mc.setIngameFocus();
        Keyboard.enableRepeatEvents(false);
        Client.INSTANCE.getEventBus().unregister(Client.INSTANCE.getClickGUI());
        threadPool.execute(() -> Client.INSTANCE.getConfigManager()
                .getLatestConfig().write());
    }

    @EventLink(value = Priorities.HIGH)
    public final Listener<Render2DEvent> onRender2D = event -> {
        getLayer(REGULAR, 2).add(() -> Client.INSTANCE.getClickGUI().render());
        getLayer(BLOOM, 3).add(() -> Client.INSTANCE.getClickGUI().bloom());
    };

    @EventLink
    public final Listener<GuiKeyBoardEvent> onKey = event -> {
        if (!stopWatch.finished(50)) return;

        if (event.getKeyCode() == this.getKey()) {
            this.mc.displayGuiScreen(null);

            if (this.mc.currentScreen == null) {
                this.mc.setIngameFocus();
            }
        }
    };
}
