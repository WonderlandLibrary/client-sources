package arsenic.module;

import arsenic.main.Nexus;
import arsenic.module.impl.visual.Notifications;
import arsenic.module.property.IReliable;
import arsenic.module.property.Property;
import arsenic.module.property.PropertyInfo;
import arsenic.module.property.SerializableProperty;
import arsenic.utils.interfaces.IContainer;
import arsenic.utils.interfaces.ISerializable;
import arsenic.utils.minecraft.PlayerUtils;
import com.google.gson.JsonObject;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Module implements IContainer<Property<?>>, ISerializable {

    protected static final Minecraft mc = Minecraft.getMinecraft();
    protected static final Gui gui;

    static {
        try {
            gui = Gui.class.newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    protected static final Nexus client = Nexus.getInstance();

    private final String name;
    private final String description;
    private final ModuleCategory category;
    private boolean enabled;
    private boolean hidden;
    private String displayName;
    private int keybind;

    private boolean registered;

    private final List<Property<?>> properties = new ArrayList<>();
    private final List<SerializableProperty<?>> serializableProperties = new ArrayList<>();

    public Module() {
        if (!this.getClass().isAnnotationPresent(ModuleInfo.class)) {
            throw new IllegalArgumentException("No @ModuleInfo on class " + this.getClass().getCanonicalName());
        }

        final ModuleInfo info = this.getClass().getDeclaredAnnotation(ModuleInfo.class);

        name = info.name();
        displayName = name;
        description = info.description();
        category = info.category();
        enabled = info.enabled();
        hidden = info.hidden();
        keybind = info.keybind();

        if (info.enabled()) {
            setEnabledSilently(true);
        }
    }

    //so fucked but works
    //sigmas gonna kill me
    public final void registerProperties() throws IllegalAccessException {
        for (final Field field : getClass().getFields()) {
            Object fieldObject = field.get(this);
            if (!(fieldObject instanceof Property<?>) || fieldObject == null) {
                continue;
            }

            Property<?> property = (Property<?>) fieldObject;
            properties.add(property);
            if (property instanceof SerializableProperty<?>) {
                serializableProperties.add((SerializableProperty<?>) property);
            }
        }
        for (final Field field : getClass().getDeclaredFields()) {
            try {
                if (!field.isAccessible()) {
                    field.setAccessible(true);
                }
                if (field.isAnnotationPresent(PropertyInfo.class)) {
                    Property<?> property = (Property<?>) field.get(this);
                    final PropertyInfo info = field.getDeclaredAnnotation(PropertyInfo.class);
                    for (final Field field2 : getClass().getDeclaredFields()) {
                        if (!field2.isAccessible()) {
                            field2.setAccessible(true);
                        }
                        Object obj = field2.get(this);
                        if (!(obj instanceof SerializableProperty)) {
                            continue;
                        }
                        SerializableProperty<?> p = (SerializableProperty<?>) field2.get(this);
                        if (p instanceof IReliable && p.getJsonKey().equals(info.reliesOn())) {
                            property.setVisible(((IReliable) p).valueCheck(info.value()));
                            break;
                        }
                    }
                }
            } catch (ClassCastException e) {
                throw new RuntimeException("The @PropertyInfo is only meant for properties & the target should only be Serializable properties that implement IReliable");
            }
        }
    }

    //triggers when the module is enabled
    protected void onEnable() {
    }

    //triggers when the module is disabled
    protected void onDisable() {
    }

    @Override
    public final String getName() {
        return name;
    }

    public final String getDescription() {
        return description;
    }

    public final ModuleCategory getCategory() {
        return category;
    }

    public final boolean isEnabled() {
        return enabled;
    }

    public final void toggle() {
        setEnabled(!enabled);
    }

    public final void setEnabled(boolean enabled) {
        if (this.enabled != enabled) {
            this.enabled = enabled;

            if (enabled) {
                Notifications notifications = Nexus.getNexus().getModuleManager().getModuleByClass(Notifications.class);
                if (notifications != null && notifications.isEnabled()) {
                    PlayerUtils.addWaterMarkedMessageToChat("Enabled " + this.getName());
                }
                onEnable();

                // prevents registering when it gets disabled on enable
                if (this.enabled) {
                    Nexus.getInstance().getEventManager().subscribe(this);
                    registered = true;
                }
            } else {
                if (registered) {
                    registered = false;
                    Nexus.getInstance().getEventManager().unsubscribe(this);
                }
                Notifications notifications = Nexus.getNexus().getModuleManager().getModuleByClass(Notifications.class);
                if (notifications != null && notifications.isEnabled()) {
                    PlayerUtils.addWaterMarkedMessageToChat("Disabled " + this.getName());
                }
                onDisable();
            }
        }
    }

    public final void setEnabledSilently(boolean enabled) {
        if (this.enabled != enabled) {
            this.enabled = enabled;

            if (enabled) {
                Nexus.getInstance().getEventManager().subscribe(this);
                registered = true;
            } else if (registered) {
                registered = false;
                Nexus.getInstance().getEventManager().unsubscribe(this);
            }
        }
    }

    public boolean isHidden() {
        return hidden;
    }

    public final void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public final String getDisplayName() {
        return displayName;
    }

    public final void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public final int getKeybind() {
        return keybind;
    }

    public final void setKeybind(int keybind) {
        this.keybind = keybind;
    }

    @Override
    public final Collection<Property<?>> getContents() {
        return properties;
    }

    public final List<? extends Property<?>> getProperties() {
        return properties;
    }

    @Override
    public final void loadFromJson(JsonObject obj) {
        try {

            keybind = obj.get("bind").getAsInt();
            setEnabledSilently(obj.get("enabled").getAsBoolean());

            serializableProperties
                    .forEach(property -> property.loadFromJson(obj.getAsJsonObject(property.getJsonKey())));
        } catch (NullPointerException | IllegalArgumentException e) {
            Nexus.getNexus().getLogger().info("Error loading {}'s config (If this the first launch or the first launch after an update ignore this)", getName());
        }
        postApplyConfig();
    }

    //triggers after the config has been applied
    protected void postApplyConfig() {
    }

    @Override
    public final JsonObject saveInfoToJson(JsonObject obj) {

        obj.addProperty("bind", keybind);
        obj.addProperty("enabled", enabled);

        serializableProperties.forEach(property -> property.addToJson(obj));
        return obj;
    }

    @Override
    public final String getJsonKey() {
        return name;
    }
}
