package us.dev.direkt.module;

import com.google.common.collect.ImmutableSet;
import us.dev.api.interfaces.Lockable;
import us.dev.api.interfaces.Toggleable;
import us.dev.api.property.Property;
import us.dev.direkt.Direkt;
import us.dev.direkt.Wrapper;
import us.dev.direkt.event.internal.events.system.module.EventPostModuleLoading;
import us.dev.direkt.event.internal.events.system.module.EventUpdateModuleLabel;
import us.dev.direkt.file.internal.files.ModulesFile;
import us.dev.direkt.keybind.Bindable;
import us.dev.direkt.keybind.Keybind;
import us.dev.direkt.module.property.ModProperty;
import us.dev.direkt.module.property.Propertied;
import us.dev.direkt.module.property.annotations.Exposed;
import us.dev.dvent.Link;
import us.dev.dvent.Listener;

import java.lang.reflect.Field;
import java.util.ArrayDeque;
import java.util.LinkedHashSet;
import java.util.Queue;
import java.util.Set;


/**
 * @author Foundry
 */
public abstract class ToggleableModule extends Module implements Toggleable, Bindable, Propertied, Lockable {
    private static final Queue<ToggleableModule> propertyPopulatorQueue = new ArrayDeque<>();

    private final Set<ModProperty<?>> properties = new LinkedHashSet<>();
    private boolean running;
    private Keybind keybind;
    private String displayName;
    private boolean locked;

    public ToggleableModule() {
        super();
        this.displayName = this.getLabel();
        Direkt.getInstance().getKeybindManager().register((this.keybind = new Keybind(-1) {
            @Override
            public void keyPressed() {
                ToggleableModule.this.toggle();
            }
        }));
        propertyPopulatorQueue.add(this);
    }

    @Override
    public void onEnable() {}

    @Override
    public void onDisable() {}

    @Override
    public boolean isRunning() {
        return this.running;
    }

    @Override
    public boolean isLocked() {
        return this.locked;
    }

    @Override
    public void setLocked(boolean isLocked) {
        this.locked = isLocked;
    }

    @Override
    public void setRunning(boolean running) {
        setRunning(running, true);
    }

    public void setRunning(boolean running, boolean shouldSave) {
        if (!this.isLocked()) {
            this.running = running;
            if (this.running) {
                Direkt.getInstance().getEventManager().register(this);
                this.onEnable();
            } else {
                Direkt.getInstance().getEventManager().unregister(this);
                this.onDisable();
            }
            if (shouldSave) {
                Direkt.getInstance().getFileManager().getFile(ModulesFile.class).save();
            }
        } else {
            Wrapper.addChatMessage("This module is currently locked.");
        }
    }

    @Override
    public Keybind getKeybind() {
        return this.keybind;
    }

    @Deprecated
    protected <T extends Property> T addProperty(T p) {
        this.properties.add(new ModProperty<Object>(this, p));
        return p;
    }

    public <T> Property<T> getProperty(Class<T> valueClass, String label) {
        for (ModProperty<?> property : properties) {
            if (property.getType().equals(valueClass) && property.getLabel().equals(label)) {
                @SuppressWarnings("unchecked")
                final Property<T> resultProperty = (Property<T>) property.getProperty();
                return resultProperty;
            }
        }
        return null;
    }

    @Override
    public Set<ModProperty> getProperties() {
        return ImmutableSet.copyOf(this.properties);
    }

    protected void setDisplayName(String displayName) {
        this.displayName = displayName;
        Direkt.getInstance().getEventManager().call(new EventUpdateModuleLabel());
    }

    public String getDisplayName() {
        return this.displayName;
    }

    static {
        Direkt.getInstance().getEventManager().register(new Object() {
           @Listener
           protected Link<EventPostModuleLoading> onWorldLoad = new Link<>(event -> {
               ToggleableModule mod;
               while ((mod = propertyPopulatorQueue.poll()) != null) {
                   for (Field field : mod.getClass().getDeclaredFields()) {
                       if (Property.class.isAssignableFrom(field.getType())) {
                           final Exposed exposingAnnotation = field.getDeclaredAnnotation(Exposed.class);
                           if (exposingAnnotation != null) {
                               field.setAccessible(true);
                               try {
                                   mod.properties.add(new ModProperty<>(mod, (Property<?>) field.get(mod), exposingAnnotation));
                               } catch (IllegalAccessException e) {
                                   e.printStackTrace();
                               }
                           }
                       }
                   }
               }
               Direkt.getInstance().getEventManager().unregister(this);
           }, 0);
        });
    }
}
