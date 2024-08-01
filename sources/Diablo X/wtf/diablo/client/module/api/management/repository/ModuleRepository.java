package wtf.diablo.client.module.api.management.repository;

import best.azura.eventbus.handler.EventHandler;
import best.azura.eventbus.handler.Listener;
import com.google.common.collect.ClassToInstanceMap;
import com.google.common.collect.MutableClassToInstanceMap;
import wtf.diablo.client.event.impl.client.KeyEvent;
import wtf.diablo.client.module.api.data.AbstractModule;
import wtf.diablo.client.module.api.data.ModuleCategoryEnum;
import wtf.diablo.client.scripting.impl.data.functions.module.ModuleScriptingFunctions;

import java.util.Collection;
import java.util.stream.Collectors;

public final class ModuleRepository {
    private final ClassToInstanceMap<AbstractModule> moduleMap;

    private ModuleRepository(final Builder builder) {
        this.moduleMap = builder.moduleMap;
    }

    public void initialize() {
        this.moduleMap.keySet().forEach(clazz -> {
            try {
                final AbstractModule module = clazz.newInstance();
                this.moduleMap.put(clazz, module);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }

    public ClassToInstanceMap<AbstractModule> getModuleMap() {
        return moduleMap;
    }

    // dunno if this works :skull:
    public void put(final ModuleScriptingFunctions.ModuleScriptingClass abstractModule) {
        try {
            this.moduleMap.put(abstractModule.getClass(), abstractModule.getClass().newInstance());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @EventHandler
    private final Listener<KeyEvent> keyEventListener = e -> {
        getModules().forEach(module -> {
            if (module.getKey() == e.getKey()) {
                module.toggle();
            }
        });
    };

    public <T extends AbstractModule> T getModuleInstance(final Class<T> clazz) {
        return this.moduleMap.getInstance(clazz);
    }

    public  <T extends AbstractModule> T getModuleInstance(final String moduleName)
    {
        for (final AbstractModule module : this.getModules())
        {
            if (module.getName().equalsIgnoreCase(moduleName))
            {
                return (T) module;
            }
        }
        return null;
    }

    public AbstractModule getModuleByName(final String name) {
        return this.moduleMap.values().stream().filter(module -> module.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    public Collection<AbstractModule> getModulesByCategory(final ModuleCategoryEnum category) {
        return this.moduleMap.values().stream().filter(module -> module.getData().category() == category).collect(Collectors.toList());
    }

    public Collection<AbstractModule> getModules() {
        return this.moduleMap.values();
    }

    public static Builder builder() {
        return new Builder();
    }

    /**
     * The builder class for the ModuleHandler class.
     * We use this to "build" modules which is just essentially adding them with the method addModule
     *
     * @author Creida
     * @version 1.0.0
     * @since 1.0.0
     */
    public static final class Builder
    {
        private final ClassToInstanceMap<AbstractModule> moduleMap;

        private Builder()
        {
            this.moduleMap = MutableClassToInstanceMap.create();
        }

        /**
         * Adds a module to the module map.
         *
         * @param clazz the module class.
         * @return the builder.
         */
        public <T extends AbstractModule> Builder addModule(final Class<T> clazz)
        {
            try
            {
                this.moduleMap.put(clazz, null);
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
            return this;
        }

        /**
         * Builds the ModuleHandler class.
         *
         * @return the ModuleHandler class.
         */
        public ModuleRepository build()
        {
            return new ModuleRepository(this);
        }
    }
}
