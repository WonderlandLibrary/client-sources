package wtf.diablo.client.scripting.impl.data.functions.module;

import best.azura.eventbus.handler.EventHandler;
import best.azura.eventbus.handler.Listener;
import wtf.diablo.client.core.impl.Diablo;
import wtf.diablo.client.event.impl.client.KeyEvent;
import wtf.diablo.client.event.impl.client.renderering.OverlayEvent;
import wtf.diablo.client.event.impl.network.RecievePacketEvent;
import wtf.diablo.client.event.impl.network.SendPacketEvent;
import wtf.diablo.client.event.impl.player.motion.MotionEvent;
import wtf.diablo.client.module.api.data.AbstractModule;
import wtf.diablo.client.module.api.data.ModuleCategoryEnum;
import wtf.diablo.client.scripting.api.Script;

public final class ModuleScriptingFunctions {
    private final Script script;

    public ModuleScriptingFunctions(final Script script) {
        this.script = script;
    }

    public AbstractModule register(final String name, final String description, final String category) {
        final ModuleScriptingClass module = new ModuleScriptingClass(name, description, ModuleCategoryEnum.valueOf(category.toUpperCase()));
        this.script.setAbstractModule(module);
        Diablo.getInstance().getModuleRepository().put(new ModuleScriptingClass(name, description, ModuleCategoryEnum.valueOf(category.toUpperCase())));
        return module;
    }

    public class ModuleScriptingClass extends AbstractModule {
        private final String name, description;
        private final ModuleCategoryEnum category;

        public ModuleScriptingClass(final String name, final String description, final ModuleCategoryEnum category) {
            this.name = name;
            this.description = description;
            this.category = category;
        }

        @Override
        public String getName() {
            return this.name;
        }

        @Override
        public String getDescription() {
            return this.description;
        }

        @Override
        public ModuleCategoryEnum getModuleCategoryEnum() {
            return this.category;
        }


        @EventHandler
        Listener<OverlayEvent> eventRender2DListener = e -> {
            script.registerEvent("EventRender2D", e);
        };

        @EventHandler
        Listener<RecievePacketEvent> eventRecievePacketListener = e -> {
            script.registerEvent("EventReceivePacket", e);
        };

        @EventHandler
        Listener<SendPacketEvent> eventSendPacketListener = e -> {
            script.registerEvent("EventSendPacket", e);
        };

        @EventHandler
        Listener<KeyEvent> eventKeyListener = e -> {
            script.registerEvent("EventKey", e);
        };

        @EventHandler
        Listener<MotionEvent> eventMotionListener = e -> {
            script.registerEvent("EventMotion", e);
        };
    }

}
